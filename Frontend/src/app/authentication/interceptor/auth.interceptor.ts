import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../service/authentication/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthenticationService) {
  }

  // nie widziało mi mojego serwisu, jeśli byłby problem
  intercept(request: HttpRequest<any>, handler: HttpHandler): Observable<HttpEvent<any>> {
    if (request.url.includes('${this.authService.url}/user/login')) {
      return handler.handle(request);
    }
    if (request.url.includes('${this.authService.url}/user/register')) {
      return handler.handle(request);
    }
    if (request.url.includes('${this.authService.url}/user/resetPassword')) {
      return handler.handle(request);
    }

    this.authService.loadToken();
    const token = this.authService.getToken();
    const newRequest = request.clone({setHeaders: { Authorization: 'Bearer ${token}'}});
    return handler.handle(newRequest);
  }
}
