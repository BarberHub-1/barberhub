-- Alterar a coluna foto para LONGTEXT
ALTER TABLE usuario MODIFY COLUMN foto LONGTEXT;

-- Configurar o banco de dados para usar UTF-8
CREATE DATABASE IF NOT EXISTS barberhub_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE barberhub_db;

-- Configurar todas as tabelas existentes para usar UTF-8
ALTER TABLE administrador CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE agendamentos CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE agendamento_servico CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE avaliacoes CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE cliente CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE estabelecimento CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE horarios_funcionamento CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE profissionais CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE profissional_servico CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE servico CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE usuario CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- As tabelas serão criadas pelo Hibernate com base nas entidades
-- Garanta que as configurações de conexão no application.properties estejam corretas. 

CREATE TABLE usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    nome VARCHAR(255),
    cpf VARCHAR(14) UNIQUE,
    telefone VARCHAR(15),
    status VARCHAR(50),
    nome_proprietario VARCHAR(255),
    nome_estabelecimento VARCHAR(255),
    cnpj VARCHAR(18) UNIQUE,
    foto TEXT,
    rua VARCHAR(255),
    numero INT,
    bairro VARCHAR(255)
); 