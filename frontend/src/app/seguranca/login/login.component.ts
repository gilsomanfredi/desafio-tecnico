import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  erro = false;
  msg: string;

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {
    this.auth.logout();
  }

  login(usuario: string, senha: string) {
    this.erro = false;
    this.auth.login(usuario, senha)
      .then(() => {
        this.router.navigate(['/pessoas']);
      })
      .catch(err => {
        this.erro = true;
        this.msg = err;
      });
  }

}
