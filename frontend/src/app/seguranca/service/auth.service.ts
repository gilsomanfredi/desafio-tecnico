import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
// import 'rxjs/add/operators/toPromisse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private endpoint = '/oauth/token';

  constructor(private http: HttpClient) { }

  login(usuario: string, senha: string): Promise<Object> {
    const headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic Q2FkYXN0cm9QZXNzb2FDbGllbnQ6QzRkNHN0cjBQM3NzMDQ='
    };

    const body = `username=${usuario}&password=${senha}&grant_type=password`;

    return this.http.post(this.getUrl(), body, { headers })
    .toPromise().then((response: any) => {
      this.armazenarToken(response);
      return Promise.resolve(true);
    })
    .catch((err: any) => {
      if (err.status === 400 && err.error.error === 'invalid_grant') {
        return Promise.reject('Usuário ou senha inválidos');
      }

      return Promise.reject('Problema ao fazer login, por favor verifique');
    });
  }

  private armazenarToken(userToken: any) {
    localStorage.setItem('userToken', userToken.access_token);
  }

  carregarToken(): string {
    return localStorage.getItem('userToken');
  }

  logout() {
    localStorage.removeItem('userToken');
  }

  getUrl() {
    return environment.baseUrl.concat(this.endpoint);
  }
}
