CREATE TABLE cliente (
  id SERIAL PRIMARY KEY,
  idUsuario INT NOT NULL,
  cpf VARCHAR(14) UNIQUE NOT NULL,
  nome VARCHAR(100) NOT NULL,
  salario DECIMAL(10,2) NOT NULL
);

CREATE TABLE endereco (
  id SERIAL PRIMARY KEY,
  idCliente INT NOT NULL,
  cep VARCHAR(8) NOT NULL,
  logradouro VARCHAR(200) NOT NULL,
  numero INT,
  complemento VARCHAR(150),
  cidade VARCHAR(200) NOT NULL,
  estado VARCHAR(200) NOT NULL
);

INSERT INTO cliente (idUsuario, cpf, nome, salario) VALUES
(1, '12912861012', 'Catharyna', 10000.00),
(2, '09506382000', 'Cleuddônio', 20000.00),
(3, '85733854057', 'Catianna', 3000.00),
(4, '58872160006', 'Cutardo', 500.00),
(5, '76179646090', 'Coândrya', 1500.00);

INSERT INTO endereco (idCliente, cep, logradouro, numero, complemento, cidade, estado) VALUES
(1, '80000000', 'Rua A', 10, 'apto 1', 'Curitiba', 'PR'),
(2, '80000000', 'Rua B', NULL, 'casa', 'Curitiba', 'PR'),
(3, '80000000', 'Rua C', 30, NULL, 'Curitiba', 'PR'),
(4, '80000000', 'Rua D', NULL, 'fundos', 'Curitiba', 'PR'),
(5, '80000000', 'Rua E', 50, NULL, 'Curitiba', 'PR');