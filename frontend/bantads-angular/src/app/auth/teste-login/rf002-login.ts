import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule,Validators, FormControl, FormGroup} from '@angular/forms';
import {  Router } from '@angular/router';
import { Login } from '../../shared/models/login.model';
import { Loginservice } from '../../services/loginservice';
import { Usuario } from '../../shared/models/usuario.model';

@Component({
  selector: 'app-rf002-login',
  templateUrl: './rf002-login.html',
  styleUrl: './rf002-login.css',
  imports: [ReactiveFormsModule],
})
export class Rf002Login implements OnInit {
  login: Login = new Login();
  message: string = '';
  usuarioLogado: Usuario = new Usuario();
  constructor(
    private loginService: Loginservice,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loginService.usuario$.subscribe(usuario => {
      this.usuarioLogado = usuario!;
    });  
    this.redirecionar();
  }

  loginForm = new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });

  handleSubmit() {
    if (this.loginForm.valid) {
      this.login.email = this.loginForm.value.email!;
      this.login.senha = this.loginForm.value.password!;
      this.logarUsuario(this.login);
    } else {
      this.message = 'Preencha todos os campos corretamente.';
    }
  }

  logarUsuario(login: Login) {
    this.loginService.login(login).subscribe({
      next: (data) => {
        if (data !== null) {
          this.loginService.usuarioLogado = data;
          this.message = 'Login realizado com sucesso!';
          this.redirecionar();
        } else {
          this.message = 'Usuário/Senha inválidos.';
        }
      },
      error: () => {
        this.message = 'Erro ao tentar logar.';
      }
    });
  }


  redirecionar() {
    console.log(this.usuarioLogado);
    if (this.usuarioLogado) {
      if (this.usuarioLogado.perfil.toLowerCase() === 'funcionario') {
        this.router.navigate(['/funcionario']);
      } else {
        this.router.navigate(['/cliente']);
      }
    }
  }
}