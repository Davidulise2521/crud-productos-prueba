import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoService } from '../../services/producto.service';

@Component({
  selector: 'app-producto-form',
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.css']
})
export class ProductoFormComponent implements OnInit {
  form: FormGroup;
  id: number | null = null;
  loading = false;
  saving = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private service: ProductoService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      marca: [''],
      categoria: [''],
      precio: [null, [Validators.required, Validators.min(0.01)]],
      existencias: [null, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = +idParam;
      this.cargarProducto(this.id);
    }
  }

  cargarProducto(id: number) {
    this.loading = true;
    this.service.obtenerProducto(id).subscribe({
      next: (prod) => {
        this.form.patchValue(prod);
        this.loading = false;
        if (this.id) {
          this.form.get('existencias')?.disable(); // Modificar existencias con Ajuste 
        }
      },
      error: () => {
        this.error = 'No se pudo cargar el producto';
        this.loading = false;
      }
    });
  }

  guardar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;
    const prod = this.form.getRawValue();

    const request = this.id
      ? this.service.actualizarProducto(this.id, prod)
      : this.service.crearProducto(prod);

    request.subscribe({
      next: () => {
        this.router.navigate(['/productos']);
      },
      error: (err: any) => {
        this.error = err.error?.error || 'Error al guardar el producto';
        this.saving = false;
      }
    });
  }
}
