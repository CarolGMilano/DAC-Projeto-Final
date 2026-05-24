-- Estrutura validada do banco de cliente 
CREATE DATABASE mscliente;

DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
  id SERIAL PRIMARY KEY,
  idUsuario VARCHAR NOT NULL,
  cpf VARCHAR(14) UNIQUE NOT NULL,
  nome VARCHAR(100) NOT NULL,
  telefone VARCHAR(14) NOT NULL,
  salario DECIMAL(10,2) NOT NULL,
  endereco VARCHAR(255) NOT NULL,
  cep VARCHAR(14) NOT NULL,
  cidade VARCHAR(255) NOT NULL,
  estado VARCHAR(255) NOT NULL,
  cpfGerente VARCHAR(14) NOT NULL,
  motivoRejeicao VARCHAR(255),
  dataResposta TIMESTAMP,
  ativo VARCHAR(50) NOT NULL
);