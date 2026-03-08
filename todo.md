## Lista de Tarefas

**Legenda:**

🟦 Carolina | 🟩 Cauã | 🟧 Diogo | 🟨 Vinicius | 🟪 Vítor

---

### Sumário 📑

* [Etapa 01: Front-end](#etapa-01-front-end)

---

### Etapa 01: Front-end 
   **Total**: 13 telas

> 📅 Prazo para finalização da Etapa 01: **16/04**

---

<details>
  <summary><span style="font-size:20px; font-weight:bold;">Telas iniciais</span></summary>
  <br>

   **Total**: 2 telas

* #### Tela de Login (R02)

   * [ ] Criar layout básico da tela 
   * [ ] Criar formulário (email / senha) 
   * [ ] Adicionar validações básicas 
   * [ ] Criar botão de login 
   * [ ] Simular envio para backend 
   * [ ] Testar navegação após login 

* #### Tela de Autocadastro (R01)

   * [ ] Criar componente de cadastro
   * [ ] Criar formulário de cadastro 
   * [ ] Campos: nome, CPF, email, senha 
   * [ ] Adicionar validações básicas 
   * [ ] Criar botão de envio 
   * [ ] Simular envio para backend 
   * [ ] Ajustar layout da tela 
</details>

---

<details>
  <summary><span style="font-size:20px; font-weight:bold;">Telas do Cliente</span></summary>
  <br>

   **Total**: 4 telas

* #### Dashboard do Cliente (R03)
   * [ ] Criar layout da página 
   * [ ] Exibir saldo da conta 
   * [ ] Criar atalhos para operações 
   * [ ] Criar atalho para extrato 
   * [ ] Criar atalho para perfil 
   * [ ] Testar navegação entre páginas 

* #### Perfil do Cliente (R04)

   * [ ] Criar formulário de edição 
   * [ ] Preencher campos com dados mockados 
   * [ ] Permitir alteração de dados 
   * [ ] Criar botão de salvar alterações 
   * [ ] Simular envio para backend 

* #### Operações Bancárias (R05, R06, R07)

   * #### Estrutura da página
      * [ ] Criar layout da página de operações 
      * [ ] Criar menu interno (Depósito / Saque / Transferência) 
      * [ ] Implementar troca de conteúdo interno 

   * #### Depósito
      * [ ] Campo de valor 
      * [ ] Botão de confirmar 
      * [ ] Simular envio 

   * #### Saque
      * [ ] Campo de valor 
      * [ ] Botão de confirmar 
      * [ ] Simular envio 

   * #### Transferência
      * [ ] Campo de conta destino 
      * [ ] Campo de valor 
      * [ ] Botão de confirmar 
      * [ ] Simular envio 

* #### Extrato (R08)
   * [ ] Criar tabela de transações 
   * [ ] Colunas: data, tipo, valor, saldo 
   * [ ] Criar dados mockados em JSON 
   * [ ] Exibir lista de transações 
   * [ ] Ajustar layout 
</details>

---

<details>
  <summary><span style="font-size:20px; font-weight:bold;">Telas do Gerente</span></summary>
  <br>

   **Total**: 4 telas

* #### Dashboard do Gerente (R09, R10, R11)

   * [ ] Criar layout da página 
   * [ ] Criar tabela de pedidos de autocadastro 
   * [ ] Exibir CPF, Nome e Salário 
   * [ ] Criar botão de Aprovar 
   * [ ] Criar botão de Recusar 
   * [ ] Criar modal/formulário para motivo de rejeição 
   * [ ] Simular envio das ações para backend 

* #### Lista de Clientes (R12)

   * [ ] Criar tabela de clientes 
   * [ ] Exibir CPF, Nome, Cidade, Estado, Saldo e Limite 
   * [ ] Implementar ordenação por nome 
   * [ ] Criar campo de busca por CPF ou Nome 
   * [ ] Criar link para visualizar detalhes do cliente 

* #### Consultar Cliente por CPF (R13)

   * [ ] Criar campo de busca por CPF 
   * [ ] Criar botão de consulta 
   * [ ] Exibir dados completos do cliente 
   * [ ] Exibir dados da conta (saldo e limite) 

* #### Top 3 Clientes por Saldo (R14)

   * [ ] Criar tabela de clientes 
   * [ ] Exibir CPF, Nome, Cidade, Estado e Saldo 
   * [ ] Ordenar por saldo (decrescente) 
   * [ ] Limitar exibição aos 3 maiores saldos 
</details>

---

<details>
  <summary><span style="font-size:20px; font-weight:bold;">Telas do Administrador</span></summary>
  <br>

   **Total**: 3 telas

* #### Dashboard do Administrador (R15)

   * [ ] Criar layout da página 
   * [ ] Criar tabela de gerentes 
   * [ ] Exibir quantidade de clientes por gerente 
   * [ ] Exibir soma de saldos positivos 
   * [ ] Exibir soma de saldos negativos 
   * [ ] Ordenar gerentes por maior saldo positivo 
   * [ ] Criar dados mockados 

* #### Relatório de Clientes (R16)

   * [ ] Criar tabela de clientes 
   * [ ] Exibir CPF do cliente 
   * [ ] Exibir nome do cliente 
   * [ ] Exibir e-mail do cliente 
   * [ ] Exibir salário 
   * [ ] Exibir número da conta 
   * [ ] Exibir saldo da conta 
   * [ ] Exibir limite do cliente 
   * [ ] Exibir CPF do gerente 
   * [ ] Exibir nome do gerente 
   * [ ] Ordenar clientes por nome (crescente) 

* #### CRUD de Gerentes (R17, R18, R19, R20)

   * #### Listagem de Gerentes
      * [ ] Criar tabela de gerentes 
      * [ ] Exibir nome, CPF, e-mail e telefone 
      * [ ] Ordenar por nome (crescente) 

   * #### Inserção de Gerente
      * [ ] Criar formulário de cadastro 
      * [ ] Campos: nome, CPF, e-mail, telefone e senha 
      * [ ] Criar botão de salvar 
      * [ ] Simular lógica de atribuição automática de contas 

   * #### Alteração de Gerente
      * [ ] Permitir edição de nome 
      * [ ] Permitir edição de e-mail 
      * [ ] Permitir alteração de senha 
      * [ ] Criar botão de salvar alterações 

   * #### Remoção de Gerente
      * [ ] Criar botão de remover gerente 
      * [ ] Redistribuir contas para gerente com menos contas 
      * [ ] Impedir remoção do último gerente 
</details>

---