CREATE TABLE manutencoes
(
    id_manutencao  SERIAL PRIMARY KEY,
    id_drone       INT          NOT NULL,
    descricao      VARCHAR(255) NOT NULL,
    data_agendada  DATE         NOT NULL,
    data_realizada DATE,
    status         VARCHAR(10)  NOT NULL DEFAULT 'pendente',
    FOREIGN KEY (id_drone) REFERENCES Drones (id_drone) ON DELETE CASCADE
);
