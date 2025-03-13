import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { SellerComponent } from '../seller.component';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
  @Input() letestOrder: any;

  @Input() orders: any = [];
  selectedOrder: any;

  constructor(private app: AppComponent, private http: HttpClient, private seller: SellerComponent) {
    // console.log(this.orders);
  }

  change(order: any, newStatus: number) {
    // console.log(this.orders);
    // console.log("new status:" + newStatus + "id" + order.orderId);
    const url = `${this.app.baseUrl}seller/updateStatus/${order.orderId}`;
    this.http.put(url, newStatus).subscribe(
      data => {
        if (data == 0) {
          window.alert('Something is Wrong');
        } else {
          // console.log("done");
          // console.log(order.status);
          this.seller.sort(order.status);
        }
      },
      error => {
        console.error('There was an error!', error);
        window.alert('Failed to fetch new orders');
      }
    );
  }
}
