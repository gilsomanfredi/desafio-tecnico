import { Component, OnInit } from '@angular/core';
import { NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { Pessoa } from '../model/v1/pessoa';
import { PessoaService } from '../service/v1/pessoa.service';
import { CustomNgbDateAdapter, CustomNgbDateParserFormatter } from '../../util/custom-date-format';

@Component({
  selector: 'app-cadastro-pessoa',
  templateUrl: './cadastro-pessoa.component.html',
  styleUrls: ['./cadastro-pessoa.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomNgbDateAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomNgbDateParserFormatter}
  ]
})
export class CadastroPessoaComponent implements OnInit {

  pessoa: Pessoa = new Pessoa();
  submitted = false;

  constructor(private pessoaService: PessoaService) { }

  ngOnInit() {
  }

  novo(): void {
    this.submitted = false;
    this.pessoa = new Pessoa();
  }

  salvar() {
    this.pessoaService.insert(this.pessoa)
      .subscribe(data => {
        this.submitted = true;
      }, error => console.log(error));
    this.pessoa = new Pessoa();
  }

  onSubmit() {
    this.salvar();
  }
}
