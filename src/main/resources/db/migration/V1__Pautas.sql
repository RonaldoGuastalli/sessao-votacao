CREATE TABLE IF NOT EXISTS PAUTA (
  ID SERIAL PRIMARY KEY,
  DESCRICAO VARCHAR(400) NOT NULL,
  DATA_CADASTRO TIMESTAMP NOT NULL,
  DATA_DESATIVACAO TIMESTAMP NULL DEFAULT NULL
);