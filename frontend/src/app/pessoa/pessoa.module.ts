import { NgModule } from '@angular/core';

import { CadastroPessoaComponent } from './cadastro-pessoa/cadastro-pessoa.component';
import { CadastroPessoaV2Component } from './cadastro-pessoa-v2/cadastro-pessoa-v2.component';
import { ListarPessoasComponent } from './listar-pessoas/listar-pessoas.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMaskModule } from 'ngx-mask';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    CadastroPessoaComponent,
    CadastroPessoaV2Component,
    ListarPessoasComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    NgxMaskModule.forRoot()
  ],
  providers: []
})
export class PessoaModule { }
