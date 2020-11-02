import { Component, OnInit } from '@angular/core';
import { NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { PessoaV2 } from '../model/v2/pessoa-v2';
import { PessoaV2Service } from '../service/v2/pessoa-v2.service';
import { CustomNgbDateAdapter, CustomNgbDateParserFormatter } from '../../util/custom-date-format';

@Component({
  selector: 'app-cadastro-pessoa-v2',
  templateUrl: './cadastro-pessoa-v2.component.html',
  styleUrls: ['./cadastro-pessoa-v2.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomNgbDateAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomNgbDateParserFormatter}
  ]
})
export class CadastroPessoaV2Component implements OnInit {

  pessoa: PessoaV2 = new PessoaV2();
  submitted = false;

  constructor(private pessoaService: PessoaV2Service) { }

  ngOnInit() {
  }

  novo(): void {
    this.submitted = false;
    this.pessoa = new PessoaV2();
  }

  salvar() {
    this.pessoaService.insert(this.pessoa)
      .subscribe(data => console.log(data), error => console.log(error));
    this.pessoa = new PessoaV2();
  }

  onSubmit() {
    this.submitted = true;
    this.salvar();
  }
}
