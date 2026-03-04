# Organização do Projeto 📋
## Sumário 📑

- [Configuração do Projeto](#configuração-do-projeto)
- [Fluxo de Contribuição para a Main](#fluxo-de-contribuição-para-a-main)

## Configuração do Projeto
### Tecnologias e Versões (Frontend) 💻
- **Angular**: 20.1.6
- **Typescript**: ~5.8.2
- **Node.js**: 22.18.0  
- **npm**: 10.9.3  
- **Tailwindcss**: ^4.1.12

O projeto já está configurado para as versões acima. Para começar a usar:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/CarolGMilano/DAC-Projeto-Final.git

2. **Entre na pasta do projeto**:
   ```bash
   cd frontend/bantads-angular

3. **Instale as dependências**:
   ```bash
   npm install

4. **Rode o projeto**:
   ```bash
   ng serve

5. **Abra no navegador**:
   ```bash
   http://localhost:4200/

Se as versões do Angular, TypeScript, Node.js e npm estiverem corretas, o projeto deve rodar normalmente.

## Fluxo de Contribuição para a Main

> Nosso repositório é protegido, então **não é permitido fazer push diretamente na `main`**.  
> Todas as alterações devem passar por uma branch secundária e depois ser integradas via **Pull Request** no **GitHub**.

### Passo a Passo 📝

#### 💡 Dica Importante

Sempre atualize sua branch com a **`main`** antes de abrir o **PR**:

```bash
git checkout nome-da-sua-branch
git pull origin main
```

---

1. **Crie uma branch para sua funcionalidade ou correção:**
   ```bash
   git checkout -b nome-da-sua-branch

2. **Commite suas alterações;**
3. **Envie sua branch para o repositório remoto:**
   ```bash
   git push origin nome-da-sua-branch

4. **Abra um Pull Request no GitHub da `sua-branch` para a `main`;**
5. **Confirme o Merge da Pull Request.**