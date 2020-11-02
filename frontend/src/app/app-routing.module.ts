import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastroPessoaV2Component } from './pessoa/cadastro-pessoa-v2/cadastro-pessoa-v2.component';
import { CadastroPessoaComponent } from './pessoa/cadastro-pessoa/cadastro-pessoa.component';
import { ListarPessoasComponent } from './pessoa/listar-pessoas/listar-pessoas.component';
import { LoginComponent } from './seguranca/login/login.component';

const routes: Routes = [
  { path: '', redirectTo: 'pessoas', pathMatch: 'full' },
  { path: 'pessoas', component: ListarPessoasComponent },
  { path: 'cadastro', component: CadastroPessoaComponent },
  { path: 'cadastroV2', component: CadastroPessoaV2Component },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
