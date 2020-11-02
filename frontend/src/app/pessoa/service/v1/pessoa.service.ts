import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pessoa } from 'src/app/pessoa/model/v1/pessoa';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  private endpoint = '/pessoa';

  constructor(private http: HttpClient) { }

  getById(id: number): Observable<Object> {
    return this.http.get(`${this.getUrl()}/${id}`);
  }

  insert(pessoa: Pessoa): Observable<Object> {
    return this.http.post(`${this.getUrl()}`, pessoa);
  }

  update(id: number, pessoa: Pessoa): Observable<Object> {
    return this.http.put(`${this.getUrl()}/${id}`, pessoa);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.getUrl()}/${id}`, { responseType: 'text' });
  }

  getAll(): Observable<any> {
    return this.http.get(`${this.getUrl()}`);
  }

  getUrl() {
    return environment.baseUrl.concat(this.endpoint);
  }
}
