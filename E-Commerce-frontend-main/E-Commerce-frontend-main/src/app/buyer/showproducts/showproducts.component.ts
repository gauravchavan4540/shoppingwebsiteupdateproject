import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-showproducts',
  templateUrl: './showproducts.component.html',
  styleUrls: ['./showproducts.component.css']
})
export class ShowproductsComponent implements OnInit {
  categories: any;
  products: any = [];
  catid: number = 0; // Default to 0 to load all products
  minprice: number = 0;
  maxprice: number = 200000;
  minrating: number = 0;
  cartItems: any = [];
  rating: number = 0;
  imageUrls: any = [];
  showCart: boolean = false;
  orders: any[] = [];
  totalAmount: number = 0;
  quantity: number = 0;

  // Pagination properties
  paginatedProducts: any[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 8;
  totalPages: number = 1;
  pages: number[] = [];

  newQuantity: number = 1;
  afterDiscountPrice: number = 0;

  isLoading: boolean = false;
  letestOrder: any;

  constructor(private http: HttpClient, private app: AppComponent, private snackBar: MatSnackBar, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.loadCategories();
    this.loadProduct();
  }

  loadCategories(): void {
    const url = `${this.app.baseUrl}admin/getAllCategories`;
    this.http.get(url).subscribe({
      next: (data: any) => this.categories = data,
      error: () => this.showMessage('Something went wrong while loading categories.')
    });
  }

  getSanitizedUrl(imageUrl: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
  }

  loadProduct(): void {
    this.isLoading = true;
    let obj: any[] = [this.catid, this.minprice, this.maxprice, this.minrating];
    // console.log(obj);

    let url = `${this.app.baseUrl}buyer/getProductsBuyer`;

    if (this.catid == 0) {
      url = `${this.app.baseUrl}buyer/getAllProducts`;
    }
    // console.log(url);

    this.http.post(url, obj).subscribe({
      next: (data: any) => {
        // console.log('hii');
        if (data && data.length > 0) {
          this.products = data;
          // console.log(this.products);
          this.products.forEach((product: any) => {
            if (typeof product.imageUrls === 'string') {
              product.imageUrls = JSON.parse(product.imageUrls);
            }
          });
          // console.log(this.products);

        } else {
          this.showMessage('No products found with the given criteria.');
        }
        this.isLoading = false;
      },
      error: () => {
        // console.log('Hello');
        this.showMessage('Something went wrong while loading products.');
        this.isLoading = false;
      }
    });
  }

  printRating(p: any): void {
    if (p.ratingChange !== 0) {
      const url = `${this.app.baseUrl}buyer/giveRatingToProduct/${p.id}/${this.app.id}/${p.ratingChange}`;
      this.http.get(url).subscribe({
        next: (data: any) => {
          if (data) {
            this.showMessage('Rating updated successfully.');
          } else {
            this.showMessage('Something went wrong while updating the rating.');
          }
        },
        error: () => this.showMessage('Something went wrong while updating the rating.')
      });
    }
  }

  addToCart(p: any): void {
    this.isLoading = true;
    const url = `${this.app.baseUrl}buyer/addToCart/${p.id}/${this.app.id}`;
    this.http.get(url).subscribe({
      next: (data: any) => {
        // console.log(data);

        if (data === 1) {
          this.showMessage('Already added in the cart.');
        } else if (data === 2) {
          this.showMessage('Added to cart successfully.');
          this.loadCartProducts();
        } else {
          this.showMessage('Something went wrong while adding to the cart.');
        }
        this.isLoading = false;
      },
      error: () => {
        this.showMessage('Something went wrong while adding to the cart.');
        this.isLoading = false;
      }
    });
  }
  removeItemFromCart(productId: number): void {
    this.isLoading = true;
    const url = `${this.app.baseUrl}buyer/removeFromCart/${productId}/${this.app.id}`;
    // console.log(url);

    this.http.delete(url).subscribe({
      next: (data: any) => {
        // console.log(data);

        if (data === 1) {
          this.showMessage('Item removed successfully.');
          this.loadCartProducts(); // Reload cart products after removal
        } else {
          this.showMessage('Failed to remove the item.');
        }
        this.isLoading = false;
      },
      error: () => {
        this.showMessage('Something went wrong while removing the item.');
        this.isLoading = false;
      }
    });
  }

  loadCartProducts(): void {
    this.isLoading = true;
    const url = `${this.app.baseUrl}buyer/getCartProducts/${this.app.id}`;
    this.http.get(url).subscribe({
      next: (data: any) => {
        // console.log(data);
        if (!data || data.length === 0) {
          this.showMessage('Cart is empty.');
          this.showCart = false;
        } else {
          this.cartItems = data;
          // console.log(this.cartItems);
          this.cartItems.forEach((item: any) => {
            item.newQuantity = item.newQuantity || 1;
            if (typeof item.imageUrls === 'string') {
              item.imageUrls = JSON.parse(item.imageUrls);
            }
          });

          // console.log(this.cartItems);

          this.updateTotalAmount();
          this.showCart = true;
          this.showMessage('Cart loaded successfully.');
        }
        this.isLoading = false;
      },
      error: () => {
        this.showMessage('Something went wrong while loading the cart.');
        this.isLoading = false;
      }
    });
  }

  updateTotalAmount(): void {
    this.totalAmount = this.cartItems.reduce((sum: number, item: { newQuantity: number; }) => {
      this.newQuantity = item.newQuantity && !isNaN(item.newQuantity) && item.newQuantity > 0 ? item.newQuantity : 1;
      return sum + this.calculateDiscountedAmount(item) * this.newQuantity;
    }, 0);
  }

  calculateDiscountedAmount(item: any): number {
    if (item.newQuantity > item.quantity) {
      this.showMessage(`Only ${item.quantity} ${item.name} available in stock.`);
      item.newQuantity = 1; // Reset to a valid value
      return 0;
    }
    const discount = item.discount || 0;
    this.afterDiscountPrice = item.price * (1 - discount / 100);
    return this.afterDiscountPrice;
  }

  handleQuantityChange(item: any): void {
    if (isNaN(item.newQuantity) || item.newQuantity <= 0) {
      this.showMessage('Invalid quantity entered. Setting to 1.');
      item.newQuantity = 1; // Reset to a default value
    } else {
      item.newQuantity = item.newQuantity ?? 1; // Fallback to 1 if null or undefined
    }
    this.updateTotalAmount();
  }


  showMessage(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  placeOrder(): void {
    this.isLoading = true;
    const orderPayload = {
      prodIds: this.cartItems.map((item: any) => item.id),
      prodQuantities: this.cartItems.map((item: any) => item.newQuantity),
    };
    // console.log(orderPayload);
    const url = `${this.app.baseUrl}buyer/placeOrder/${this.app.id}/${this.totalAmount}`;
    this.http.post(url, orderPayload).subscribe({
      next: (response: any) => {
        // console.log(response);
        if (response === 1) {
          this.cartItems = [];
          this.showCart = false;
          this.showMessage('Order placed successfully.');

          // const url = `${this.app.baseUrl}seller/letestOrder/${this.app.id}`;
          // this.http.get(url).subscribe(data => {
          //   if (data == null) {
          //     window.alert('Something is wrong');
          //   } else {
          //     this.letestOrder = data;
          //     console.log(this.letestOrder);
          //   }
          // });
        } else {
          this.showMessage('Something went wrong while placing the order.');
        }
        this.isLoading = false;
      },
      error: () => {
        this.showMessage('Something went wrong while placing the order.');
        this.isLoading = false;
      }
    });
  }
}