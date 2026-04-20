-- Enums 
CREATE TABLE tipoUsuario (
  id SERIAL PRIMARY KEY,
  descricao VARCHAR(150) NOT NULL
);

CREATE TABLE auth (
  id SERIAL PRIMARY KEY,
  email VARCHAR(100) UNIQUE NOT NULL,
  senha VARCHAR(100) NOT NULL,
  salt VARCHAR(150) NOT NULL,
  idTipo INT NOT NULL,
  ativo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO tipoUsuario (descricao) VALUES
('Cliente'),
('Gerente'),
('Administrador');
  
--Lembrando aque ali na senha será criptografada e o salt é gerado para aquela senha. 
INSERT INTO auth (email, senha, salt, idTipo) VALUES
('cli1@bantads.com.br', 'tads', 'tads', 1),
('cli2@bantads.com.br', 'tads', 'tads', 1),
('cli3@bantads.com.br', 'tads', 'tads', 1),
('cli4@bantads.com.br', 'tads', 'tads', 1),
('cli5@bantads.com.br', 'tads', 'tads', 1),
('ger1@bantads.com.br', 'tads', 'tads', 2),
('ger2@bantads.com.br', 'tads', 'tads', 2),
('ger3@bantads.com.br', 'tads', 'tads', 2),
('adm1@bantads.com.br', 'tads', 'tads', 3);