import { Component } from '@angular/core';
import { AppComponent } from '../app.component';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WithTokenInterceptor } from '../service/with-token.interceptor';

interface ApiResponse {
  status: number;
  message: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(public http: HttpClient, public app: AppComponent, public snackBar: MatSnackBar) {
  }
  user: any = {};
  isLogin: boolean = true; // Default to login form
  isLoading: boolean = false;
  username: string = '';
  password: string = '';

  login() {
    this.isLoading = true;
    const url = `${this.app.baseUrl}login/log`;
    const obj = [this.username, this.password];

    this.http.post(url, obj).subscribe((data: any) => {
      if (!data) {
        this.showMessage('All fields are manditory')
      } else {
        if (data.id < 1) {
          this.showMessage(data.errorMessage)
        } else {
          // console.log(data);
          this.isLoading = false;
          this.app.id = data.id;
          this.app.whatToShow = data.accountType;
          // localStorage.setItem("token", data.token);
          this.showMessage('Login successful')
        }
      }
    });
  }

  signup() {
    this.isLoading = true;
    if (!this.user.name || !this.user.userName || !this.user.password) {
      this.showMessage("All fields are mandatory")
      this.isLoading = false;
      return;
    }
    this.user.accountType = 3;
    const url = this.app.baseUrl + 'signup/register';
    this.http.post<ApiResponse>(url, this.user).subscribe((response: ApiResponse) => {
      if (response.status === 1) {
        this.showMessage(response.message)
        this.user = {}; // Reset user object
        this.isLogin = true; // Switch back to login form
        this.isLoading = false;
      } else {
        this.showMessage(response.message)
        this.isLoading = false;
      }
    });
  }
  showMessage(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, // Duration in milliseconds
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }
}