-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 27/05/2025 às 00:55
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `barberhub_db`
--

CREATE DATABASE IF NOT EXISTS `barberhub_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `barberhub_db`;

-- --------------------------------------------------------

--
-- Estrutura para tabela `administrador`
--

CREATE TABLE `administrador` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `senha` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `agendamentos`
--

CREATE TABLE `agendamentos` (
  `id` bigint(20) NOT NULL,
  `data_hora` datetime(6) NOT NULL,
  `status` enum('AGENDADA','CANCELADA','CONCLUIDA') NOT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `estabelecimento_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `agendamento_servico`
--

CREATE TABLE `agendamento_servico` (
  `id` bigint(20) NOT NULL,
  `agendamento_id` bigint(20) NOT NULL,
  `servico_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `avaliacoes`
--

CREATE TABLE `avaliacoes` (
  `id` bigint(20) NOT NULL,
  `comentario` varchar(500) DEFAULT NULL,
  `data_avaliacao` datetime(6) NOT NULL,
  `nota` int(11) NOT NULL,
  `agendamento_id` bigint(20) NOT NULL,
  `estabelecimento_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL,
  `bairro` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `cpf` bigint(20) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `rua` int(11) DEFAULT NULL,
  `senha` varchar(255) NOT NULL,
  `telefone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`id`, `bairro`, `cidade`, `cpf`, `email`, `estado`, `foto`, `nome`, `numero`, `rua`, `senha`, `telefone`) VALUES
(1, 'Centro', 'São Paulo', 9007199254740991, 'cliente@exemplo.com', 'SP', 'foto.jpg', 'João Silva', 123, 456, 'senha123', '(11) 98765-4321'),
(6, 'Centro', 'São Paulo', 12345678900, 'cliente@exemplo.com', 'SP', 'url-ou-base64-da-foto', 'João Silva', 123, 456, 'senhaSegura123', '(11) 98765-4321');

-- --------------------------------------------------------

--
-- Estrutura para tabela `estabelecimento`
--

CREATE TABLE `estabelecimento` (
  `id` bigint(20) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `cidade` varchar(100) NOT NULL,
  `cnpj` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `endereco` varchar(200) NOT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `nome_estabelecimento` varchar(100) NOT NULL,
  `nome_proprietario` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `status` enum('PENDENTE','APROVADO','REJEITADO') DEFAULT NULL,
  `telefone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `estabelecimento`
--

INSERT INTO `estabelecimento` (`id`, `cep`, `cidade`, `cnpj`, `email`, `endereco`, `foto`, `nome_estabelecimento`, `nome_proprietario`, `senha`, `status`, `telefone`) VALUES
(1, 'string', 'string', '12.345.678/0001-95', 'string', 'string', NULL, 'string', 'string', 'string', 'PENDENTE', '(11) 98765-4321');

-- --------------------------------------------------------

--
-- Estrutura para tabela `horarios_funcionamento`
--

CREATE TABLE `horarios_funcionamento` (
  `id` bigint(20) NOT NULL,
  `dia_semana` enum('DOMINGO','QUARTA','QUINTA','SABADO','SEGUNDA','SEXTA','TERCA') NOT NULL,
  `horario_abertura` varchar(255) NOT NULL,
  `horario_fechamento` varchar(255) NOT NULL,
  `estabelecimento_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `horarios_funcionamento`
--

INSERT INTO `horarios_funcionamento` (`id`, `dia_semana`, `horario_abertura`, `horario_fechamento`, `estabelecimento_id`) VALUES
(2, 'DOMINGO', '08:00', '18:00', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `profissionais`
--

CREATE TABLE `profissionais` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `estabelecimento_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `profissionais`
--

INSERT INTO `profissionais` (`id`, `email`, `nome`, `telefone`, `estabelecimento_id`) VALUES
(1, 'string@email.com', 'string', 'string', 1),
(2, 'string@reyt.uku', 'string', 'string', 1),
(3, 'string@reyt.uku', 'string', 'string', 1),
(4, 'string@reyt.uku', 'string', 'string', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `profissional_servico`
--

CREATE TABLE `profissional_servico` (
  `profissional_id` bigint(20) NOT NULL,
  `servico_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `profissional_servico`
--

INSERT INTO `profissional_servico` (`profissional_id`, `servico_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `servico`
--

CREATE TABLE `servico` (
  `id` bigint(20) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `duracao_minutos` int(11) NOT NULL,
  `preco` double NOT NULL,
  `tipo` enum('BARBA','CORTE_DE_CABELO','HIDRATACAO','LUZES','SOBRANCELHA') DEFAULT NULL,
  `estabelecimento_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `servico`
--

INSERT INTO `servico` (`id`, `descricao`, `duracao_minutos`, `preco`, `tipo`, `estabelecimento_id`) VALUES
(1, 'stringstri', 20, 10.1, 'CORTE_DE_CABELO', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKh121ki47maojpkmvdvqf87ybo` (`email`);

--
-- Índices de tabela `agendamentos`
--
ALTER TABLE `agendamentos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbwg0xnsp8mxtf0933d39hlg40` (`cliente_id`),
  ADD KEY `FK1redjfj9rvxxmxyc6arradl8t` (`estabelecimento_id`);

--
-- Índices de tabela `agendamento_servico`
--
ALTER TABLE `agendamento_servico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpxgov7cq0pg5srw6px678u65g` (`agendamento_id`),
  ADD KEY `FK9fujots3a3pydg7pogni24sjp` (`servico_id`);

--
-- Índices de tabela `avaliacoes`
--
ALTER TABLE `avaliacoes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKok1nkt6o2q0pwc0l3r72mgv9b` (`agendamento_id`),
  ADD KEY `FKm2uca6oj2pmsdd8yvei68c8us` (`estabelecimento_id`);

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKcmxo70m08n43599l3h0h07cc6` (`email`);

--
-- Índices de tabela `estabelecimento`
--
ALTER TABLE `estabelecimento`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbwt2qnevynt1k8hrdtki2tvuq` (`cnpj`),
  ADD UNIQUE KEY `UK103mbcg4ta1ghjrn7cvv2855x` (`email`);

--
-- Índices de tabela `horarios_funcionamento`
--
ALTER TABLE `horarios_funcionamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeihaudkrt0ue0hap7b7hrsqtx` (`estabelecimento_id`);

--
-- Índices de tabela `profissionais`
--
ALTER TABLE `profissionais`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKciy3q6i9qg6j6m5u2dm9v1qau` (`estabelecimento_id`);

--
-- Índices de tabela `profissional_servico`
--
ALTER TABLE `profissional_servico`
  ADD KEY `FKouca0bkihu6b472631llper5o` (`servico_id`),
  ADD KEY `FKjo8b0anep0cugtakhxtrnpsxu` (`profissional_id`);

--
-- Índices de tabela `servico`
--
ALTER TABLE `servico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3bcjw71htpxur3bmhl30bxidc` (`estabelecimento_id`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK5171l57faosmj8myawaucatdw` (`email`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `administrador`
--
ALTER TABLE `administrador`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `agendamentos`
--
ALTER TABLE `agendamentos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `agendamento_servico`
--
ALTER TABLE `agendamento_servico`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `avaliacoes`
--
ALTER TABLE `avaliacoes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `estabelecimento`
--
ALTER TABLE `estabelecimento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `horarios_funcionamento`
--
ALTER TABLE `horarios_funcionamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `profissionais`
--
ALTER TABLE `profissionais`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `servico`
--
ALTER TABLE `servico`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `agendamentos`
--
ALTER TABLE `agendamentos`
  ADD CONSTRAINT `FK1redjfj9rvxxmxyc6arradl8t` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`),
  ADD CONSTRAINT `FKbwg0xnsp8mxtf0933d39hlg40` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Restrições para tabelas `agendamento_servico`
--
ALTER TABLE `agendamento_servico`
  ADD CONSTRAINT `FK9fujots3a3pydg7pogni24sjp` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`),
  ADD CONSTRAINT `FKpxgov7cq0pg5srw6px678u65g` FOREIGN KEY (`agendamento_id`) REFERENCES `agendamentos` (`id`);

--
-- Restrições para tabelas `avaliacoes`
--
ALTER TABLE `avaliacoes`
  ADD CONSTRAINT `FKm2uca6oj2pmsdd8yvei68c8us` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`),
  ADD CONSTRAINT `FKok1nkt6o2q0pwc0l3r72mgv9b` FOREIGN KEY (`agendamento_id`) REFERENCES `agendamentos` (`id`);

--
-- Restrições para tabelas `horarios_funcionamento`
--
ALTER TABLE `horarios_funcionamento`
  ADD CONSTRAINT `FKeihaudkrt0ue0hap7b7hrsqtx` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`);

--
-- Restrições para tabelas `profissionais`
--
ALTER TABLE `profissionais`
  ADD CONSTRAINT `FKciy3q6i9qg6j6m5u2dm9v1qau` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`);

--
-- Restrições para tabelas `profissional_servico`
--
ALTER TABLE `profissional_servico`
  ADD CONSTRAINT `FKjo8b0anep0cugtakhxtrnpsxu` FOREIGN KEY (`profissional_id`) REFERENCES `profissionais` (`id`),
  ADD CONSTRAINT `FKouca0bkihu6b472631llper5o` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`);

--
-- Restrições para tabelas `servico`
--
ALTER TABLE `servico`
  ADD CONSTRAINT `FK3bcjw71htpxur3bmhl30bxidc` FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- Inserir estabelecimentos de exemplo
INSERT INTO estabelecimento (nome_proprietario, nome_estabelecimento, cnpj, endereco, cidade, cep, telefone, foto, status, email, senha)
VALUES 
('João Silva', 'Barbearia do João', '12345678901234', 'Rua das Flores, 123', 'São Paulo', '01234-567', '(11) 99999-9999', 'https://via.placeholder.com/300x200?text=Barbearia+do+Joao', 'APROVADO', 'barbearia.joao@exemplo.com', 'senha123'),
('Maria Santos', 'Barbearia Moderna', '98765432109876', 'Av. Paulista, 1000', 'São Paulo', '01310-100', '(11) 98888-8888', 'https://via.placeholder.com/300x200?text=Barbearia+Moderna', 'APROVADO', 'barbearia.moderna@exemplo.com', 'senha123'),
('Pedro Oliveira', 'Barbearia Clássica', '45678912345678', 'Rua Augusta, 500', 'São Paulo', '01304-000', '(11) 97777-7777', 'https://via.placeholder.com/300x200?text=Barbearia+Classica', 'APROVADO', 'barbearia.classica@exemplo.com', 'senha123');

-- Inserir serviços para os estabelecimentos
INSERT INTO servico (descricao, preco, duracao_minutos, tipo, estabelecimento_id)
VALUES 
('Corte de Cabelo', 50.00, 30, 'CORTE_DE_CABELO', 1),
('Barba', 30.00, 20, 'BARBA', 1),
('Hidratação', 40.00, 40, 'HIDRATACAO', 1),
('Corte de Cabelo', 45.00, 30, 'CORTE_DE_CABELO', 2),
('Barba', 35.00, 20, 'BARBA', 2),
('Luzes', 120.00, 120, 'LUZES', 2),
('Corte de Cabelo', 55.00, 30, 'CORTE_DE_CABELO', 3),
('Barba', 40.00, 20, 'BARBA', 3),
('Sobrancelha', 20.00, 15, 'SOBRANCELHA', 3);
