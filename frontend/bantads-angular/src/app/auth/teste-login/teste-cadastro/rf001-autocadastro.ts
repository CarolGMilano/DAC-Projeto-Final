import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { Cliente } from '../../shared/models/cliente.model';
import { Clienteservice } from '../../services/clienteservice';
import { Endereco } from '../../shared/models/endereco';
import { EnderecoService } from '../../services/endereco-service';



@Component({
  selector: 'app-rf001-autocadastro',
  imports: [
    ReactiveFormsModule,
    FormsModule,
  ],
  templateUrl: './rf001-autocadastro.html',
  styleUrl: './rf001-autocadastro.css'
})
export class Rf001Autocadastro {
  clienteNovo: Cliente = new Cliente(0, '', '', '', 'cliente', false, '', '', '');
  
  formularioAutoCadastro: FormGroup;
  constructor(private fb: FormBuilder, private clienteService: Clienteservice, private enderecoService: EnderecoService) {
    this.formularioAutoCadastro = this.fb.group({
      cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      cep: ['', [Validators.required, Validators.pattern(/^\d{5}-?\d{3}$/)]], 
      telefone: ['', [Validators.required, Validators.pattern(/^\d{10,11}$/)]], 
    });
  }

  onSubmit() {
  if (this.formularioAutoCadastro.valid) {
    const user = this.formularioAutoCadastro.value;
    this.clienteService.buscarCep(user.cep).subscribe({
      next: (dados: any) => {
        // Preenche o endereço apenas se CEP for válido
        const endereco = new Endereco();
        endereco.cep = user.cep;
        endereco.logradouro = dados.logradouro;
        endereco.bairro = dados.bairro;
        endereco.cidade = dados.localidade;
        endereco.uf = dados.uf;

        /*
        Criação de novo endereço: Cria nova tupla Endereço no back
        */

        const listaEnderecos = this.enderecoService.listarTodos

        console.log("Endereço:", endereco);

        this.enderecoService.inserir(endereco).subscribe({
          next: (enderecoSalvo: Endereco) => {
            console.log("endereçoSalvo:", enderecoSalvo);
            this.clienteNovo = {
              nome: user.nome,
              email: user.email,
              senha: Math.floor(1000 + Math.random() * 9000).toString(),
              perfil: "cliente",
              ativo: true,
              cpf: user.cpf,
              telefone: user.telefone,     
              endereco: {id: enderecoSalvo.id},
                            
            }

            console.log("clienteNovo", this.clienteNovo);

             this.clienteService.inserir(this.clienteNovo).subscribe({
              next: (clienteSalvo: Cliente) => {
                console.log("Resposta do backend: ", clienteSalvo); // PROVISÓRIO
                console.log("Novo usuário cadastrado:", clienteSalvo);
                alert(`Usuário cadastrado!\nSenha enviada para ${user.email}: ${clienteSalvo.senha}`);
              },

              error: (err: any) => {
                console.error('Erro ao cadastrar no backend', err);
                console.log("Status:", err.status);
                console.log("Headers: ", err.headers);
                console.log("Mensagem: ", err.message);
                alert('Erro ao cadastrar.')
              }
            })
 



          }
        });

        //Dá pra guardar o id do endereço novo criado em uma variavel

/*         // Insere no serviço
        this.clienteService.inserir(this.clienteNovo).subscribe({
          next: (resp: any) => {
            console.log("Resposta do backend: ", resp); // PROVISÓRIO
            console.log("Novo usuário cadastrado:", this.clienteNovo);
            alert(`Usuário cadastrado!\nSenha enviada para ${user.email}: ${this.clienteNovo.senha}`);    
          },
          error: (err: any) => {
            console.error('Erro ao cadastrar no backend', err);
            console.log("Status:", err.status);
            console.log("Headers: ", err.headers);
            console.log("Mensagem: ", err.message);
            alert('Erro ao cadastrar.')
          } 
        }); */
      },
      error: (err) => {
        console.error('Endereço não encontrado', err);
        alert('CEP inválido! Não foi possível cadastrar o usuário.');
      }


    });
  }
}


}
