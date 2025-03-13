import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'E-Commerce';
  // baseUrl = 'http://35.172.163.234:8080/amazon-clone/'
  baseUrl = 'http://localhost:8080/'
  id: number = 0;
  whatToShow: number = 0;
}