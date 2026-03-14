CREATE DATABASE sistema_veiculos

USE sistema_veiculos;

CREATE TABLE marca (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE
    ) ENGINE=InnoDB;


CREATE TABLE modelo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    marca_id BIGINT NOT NULL,
    CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id) REFERENCES marca(id)
    ) ENGINE=InnoDB;


CREATE TABLE veiculo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cor VARCHAR(20) NOT NULL,
    ano_fabricacao INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quilometragem INT NOT NULL,
    status ENUM('DISPONIVEL', 'VENDIDO', 'RESERVADO') NOT NULL,
    data_cadastro DATE NOT NULL DEFAULT (CURDATE()),
    modelo_id BIGINT NOT NULL,
    INDEX idx_status (status),
    INDEX idx_ano (ano_fabricacao),
    INDEX idx_preco (preco),
    CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id) REFERENCES modelo(id)
    ) ENGINE=InnoDB;


INSERT INTO marca (nome) VALUES
 ('Toyota'), ('Honda'), ('Ford'), ('Chevrolet'), ('Volkswagen');

INSERT INTO modelo (nome, marca_id) VALUES
 ('Corolla', 1), ('Hilux', 1),
 ('Civic', 2), ('HR-V', 2),
 ('Focus', 3), ('Ranger', 3),
 ('Onix', 4), ('S10', 4), ('Golf', 5),
 ('Amarok', 5);

INSERT INTO veiculo (cor, ano_fabricacao, preco, quilometragem, status, modelo_id) VALUES
            ('Prata', 2022, 95000.00, 15000, 'DISPONIVEL', 1),
            ('Preto', 2023, 115000.00, 5000, 'DISPONIVEL', 3),
            ('Branco', 2021, 180000.00, 30000, 'DISPONIVEL', 2),
            ('Vermelho', 2020, 55000.00, 45000, 'VENDIDO', 7),
            ('Azul', 2022, 130000.00, 12000, 'RESERVADO', 10);
