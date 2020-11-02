import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './service/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private auth: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const access_token = this.auth.carregarToken();

    if (access_token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${access_token}`
        }
      });
    }

    return next.handle(request);
  }
}
