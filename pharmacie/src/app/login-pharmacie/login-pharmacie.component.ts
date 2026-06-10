import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
@Component({
  selector: 'app-login-pharmacie',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login-pharmacie.component.html',
  styleUrls: ['./login-pharmacie.component.css']
})
export class LoginPharmacieComponent {
  isLoginMode = true;
  isLoading = false;
  errorMessage = '';

  formData = { name: '', email: '', password: '' };

  constructor(private auth: AuthService, private router: Router) {}

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    this.errorMessage = '';
  }

  submit() {
    if (!this.formData.email || !this.formData.password) {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const obs = this.isLoginMode
      ? this.auth.loginPharmacie(this.formData.email, this.formData.password)
      : this.auth.signupPharmacie(this.formData.name, this.formData.email, this.formData.password);

    obs.subscribe({
      next: (res: any) => {
        this.isLoading = false;
        if (res.status === 'success') {
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = res.message;
        }
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Erreur de connexion au serveur PHP.';
      }
    });
  }
}