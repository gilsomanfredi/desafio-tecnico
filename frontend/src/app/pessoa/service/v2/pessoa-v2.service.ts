import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PessoaV2 } from 'src/app/pessoa/model/v2/pessoa-v2';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PessoaV2Service {

  private endpoint = '/pessoa/v2';

  constructor(private http: HttpClient) { }

  getById(id: number): Observable<Object> {
    return this.http.get(`${this.getUrl()}/${id}`);
  }

  insert(pessoa: PessoaV2): Observable<Object> {
    return this.http.post(`${this.getUrl()}`, pessoa);
  }

  update(id: number, pessoa: PessoaV2): Observable<Object> {
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
