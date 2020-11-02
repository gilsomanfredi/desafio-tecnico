import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Pessoa } from '../model/v1/pessoa';
import { PessoaV2Service } from '../service/v2/pessoa-v2.service';

@Component({
  selector: 'app-listar-pessoas',
  templateUrl: './listar-pessoas.component.html',
  styleUrls: ['./listar-pessoas.component.css']
})
export class ListarPessoasComponent implements OnInit {

  pessoas: Observable<Pessoa[]>;

  constructor(private pessoaV2Service: PessoaV2Service) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.pessoas = this.pessoaV2Service.getAll();
  }

  delete(id: number) {
    this.pessoaV2Service.delete(id)
      .subscribe(
        data => {
          console.log(data);
          this.load();
        },
        error => console.log(error));
  }

}
