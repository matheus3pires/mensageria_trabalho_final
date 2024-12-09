CREATE TABLE drones
(
    id_drone   SERIAL PRIMARY KEY,
    modelo     VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    status     VARCHAR(10)  NOT NULL DEFAULT 'ativo'
);