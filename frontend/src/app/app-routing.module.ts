import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastroPessoaV2Component } from './cadastro-pessoa-v2/cadastro-pessoa-v2.component';
import { CadastroPessoaComponent } from './cadastro-pessoa/cadastro-pessoa.component';
import { ListarPessoasComponent } from './listar-pessoas/listar-pessoas.component';

const routes: Routes = [
  { path: '', redirectTo: 'employee', pathMatch: 'full' },
  { path: 'pessoas', component: ListarPessoasComponent },
  { path: 'cadastro', component: CadastroPessoaComponent },
  { path: 'cadastroV2', component: CadastroPessoaV2Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
