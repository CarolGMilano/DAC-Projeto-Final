-- Estrutura validada do banco de gerente 

DROP TABLE IF EXISTS gerente;

CREATE TABLE gerente (
  id SERIAL PRIMARY KEY,
  idUsuario VARCHAR NOT NULL,
  cpf VARCHAR(14) UNIQUE NOT NULL,
  nome VARCHAR(100) NOT NULL,
  ativo VARCHAR(50) NOT NULL
);