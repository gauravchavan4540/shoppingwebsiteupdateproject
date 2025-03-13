import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class WithTokenInterceptor implements HttpInterceptor {
  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // URLs to skip the token injection
    const skipUrls = ['login/log', 'signup/register'];

    // Check if the request URL contains any of the skip URLs
    if (skipUrls.some(url => req.url.includes(url))) {
      console.log(req.url);
      return next.handle(req);
    }

    const token = localStorage.getItem('token');
    console.log(token);

    const authReq = req.clone({
      setHeaders: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });
    console.log(authReq);
    // Pass the modified request
    return next.handle(authReq);
  }
}
