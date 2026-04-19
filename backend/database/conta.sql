CREATE TABLE conta (
  id SERIAL PRIMARY KEY,
  idCliente INT NOT NULL,
  numero VARCHAR(14) UNIQUE NOT NULL,
  dataCriacao DATE NOT NULL,
  saldo DECIMAL(10,2) NOT NULL,
  limite DECIMAL(10,2) NOT NULL,
  idGerente INT NOT NULL
);

INSERT INTO conta (idCliente, numero, dataCriacao, saldo, limite, idGerente) VALUES
(1, '1291', '2000-01-01', 800.00, 5000.00, 1),
(2, '0950', '1990-10-10', -10000.00, 10000.00, 2),
(3, '8573', '2012-12-12', -1000.00, 1500.00, 3),
(4, '5887', '2022-02-22', 150000.00, 0.00, 1),
(5, '7617', '2025-01-01', 1500.00, 0.00, 2);