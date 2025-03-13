import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-seller',
  templateUrl: './seller.component.html',
  styleUrls: ['./seller.component.css']
})
export class SellerComponent {

  whatToShow: number = 0;
  products: any = [];
  categories: any = [];
  orders: any;
  // sortedOrder:any;
  name: string = '';

  constructor(public http: HttpClient, public app: AppComponent) {
    
    let url = app.baseUrl + 'login/getName' + app.id;
    http.get(url).subscribe((data: any) => {
      if (data == null) {
        window.alert('something is wrong');
      }
      else {
        this.name = data[0];
      }
    })
  }
  changeWhatToShow(num: number) {
    this.whatToShow = num;
    if (this.whatToShow == 1) {
      this.loadProduct();
    }
    //  else if (this.whatToShow == 2) {
    //   // this.newOrder();
    // }
    return;
  }
  // newOrder(): void {
  //   const url = this.app.baseUrl + 'seller/newOrder';
  //   this.http.get(url).subscribe(
  //     data => {
  //       if (data == null) {
  //         window.alert('Something is Wrong');
  //       } else {
  //         this.orders = data;
  //         console.log(this.orders);
  //       }
  //     },
  //     error => {
  //       console.error('There was an error!', error);
  //       window.alert('Failed to fetch new orders');
  //     }
  //   );
  // }
  loadProduct() {
    const url = `${this.app.baseUrl}seller/getAllProducts${this.app.id}`;
    this.http.get(url).subscribe(data => {
      // console.log(data)
      if (data == null) {
        window.alert('Something is Wrong');
      } else {
        this.products = data;
        // console.log(this.products);
        this.products.forEach((product: any) => {
          if (typeof product.imageUrls === 'string') {
            product.imageUrls = JSON.parse(product.imageUrls);
          }
        });
        // console.log(this.products);

      }
    });
  }
  sort(status: number) {
    const url = `${this.app.baseUrl}seller/sortWithStatus${status}`;
    this.http.get(url).subscribe(
      data => {
        if (data == null) {
          window.alert('Something is Wrong');
        } else {
          this.orders = data;
          this.changeWhatToShow(2);
          // console.log(this.orders);
        }
      },
      error => {
        console.error('There was an error!', error);
        window.alert('Failed to fetch new orders');
      }
    );
  }
}