-- Enum
CREATE TABLE tipoMovimentacao (
  id SERIAL PRIMARY KEY,
  descricao VARCHAR(150) NOT NULL
);

CREATE TABLE movimentacao (
  id SERIAL PRIMARY KEY,
  idConta INT NOT NULL,
  dataHora TIMESTAMP NOT NULL,
  tipo INT NOT NULL,
  idClienteOrigem INT NOT NULL,
  idClienteDestino INT,
  valor DECIMAL(10,2) NOT NULL
);

INSERT INTO tipoMovimentacao (descricao) VALUES
('Depósito'),
('Saque'),
('Transferência');

INSERT INTO movimentacao (idConta, dataHora, tipo, idClienteOrigem, idClienteDestino, valor) VALUES
(1, '2020-01-01 10:00:00', 1, 1, NULL, 1000.00),
(1, '2020-01-01 11:00:00', 1, 1, NULL, 900.00),
(1, '2020-01-01 12:00:00', 2, 1, NULL, 550.00),
(1, '2020-01-01 13:00:00', 2, 1, NULL, 350.00),
(1, '2020-01-10 15:00:00', 1, 1, NULL, 2000.00),
(1, '2020-01-15 08:00:00', 2, 1, NULL, 500.00),
(1, '2020-01-20 12:00:00', 3, 1, 2, 1700.00),

(2, '2025-01-01 12:00:00', 1, 2, NULL, 1000.00),
(2, '2025-01-02 10:00:00', 1, 2, NULL, 5000.00),
(2, '2025-01-10 10:00:00', 2, 2, NULL, 200.00),
(2, '2025-02-05 10:00:00', 1, 2, NULL, 7000.00),

(3, '2025-05-05 10:00:00', 1, 3, NULL, 1000.00),
(3, '2025-05-06 10:00:00', 2, 3, NULL, 2000.00),

(4, '2025-06-01 10:00:00', 1, 4, NULL, 150000.00),

(5, '2025-07-01 10:00:00', 1, 5, NULL, 1500.00);