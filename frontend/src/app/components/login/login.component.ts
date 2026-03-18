import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  correo: string = '';
  contrasena: string = '';
  error: string = '';

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.getToken()) {
      this.router.navigate(['/productos']);
    }
  }

  onLogin() {
    if (this.authService.login(this.correo, this.contrasena)) {
      this.router.navigate(['/productos']);
      setTimeout(() => window.location.reload(), 100);
    } else {
      this.error = 'Credenciales inválidas. Usa admin@crud.com / admin';
    }
  }
}
