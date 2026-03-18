import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this.loggedIn.asObservable();

  constructor() { }

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  login(correo: string, contrasena: string): boolean {
    if (correo === 'admin@crud.com' && contrasena === 'admin') {
      localStorage.setItem('token', 'simulated-jwt-token-12345');
      this.loggedIn.next(true);
      return true;
    }
    return false;
  }

  logout(): void {
    localStorage.removeItem('token');
    this.loggedIn.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
