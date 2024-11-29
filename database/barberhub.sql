-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 29/11/2024 às 03:45
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
-- Banco de dados: `barberhub`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `agendamento`
--

CREATE TABLE `agendamento` (
  `agendamentoId` int(11) NOT NULL,
  `estabelecimentoId` int(11) DEFAULT NULL,
  `profissionalId` int(11) DEFAULT NULL,
  `servicoId` int(11) DEFAULT NULL,
  `clienteId` int(11) DEFAULT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `preco` decimal(10,2) DEFAULT NULL,
  `desconto` decimal(5,2) DEFAULT 0.00,
  `status` varchar(50) DEFAULT 'Agendada' COMMENT 'Agendado, Cancelado, Finalizado'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `agendamento`
--

INSERT INTO `agendamento` (`agendamentoId`, `estabelecimentoId`, `profissionalId`, `servicoId`, `clienteId`, `data`, `hora`, `preco`, `desconto`, `status`) VALUES
(1, 1, 2, 3, 4, '2025-01-09', '14:30:00', 150.00, 10.00, 'Agendada'),
(2, 3, 2, 3, 5, '2024-11-26', '10:00:00', 120.00, 5.00, 'Cancelado'),
(3, 2, 3, 2, 6, '2024-10-18', '11:30:00', 200.00, 20.00, 'Finalizado'),
(4, 4, 1, 1, 7, '2025-02-05', '13:00:00', 100.00, 0.00, 'Agendada'),
(5, 1, 2, 4, 8, '2025-01-13', '15:00:00', 180.00, 10.00, 'Agendada'),
(6, 2, 3, 3, 9, '2025-01-14', '16:30:00', 150.00, 15.00, 'Agendada');

-- --------------------------------------------------------

--
-- Estrutura para tabela `avaliacao`
--

CREATE TABLE `avaliacao` (
  `avaliacaoId` int(11) NOT NULL,
  `agendamentoId` int(11) NOT NULL,
  `avaliado` int(11) DEFAULT NULL COMMENT '1 ESTABELECIMENTO, 2 PROFISSIONAL',
  `nota` int(11) DEFAULT NULL CHECK (`nota` between 1 and 5),
  `comentario` text DEFAULT NULL,
  `dataAvaliacao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `clienteId` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `cep` varchar(20) DEFAULT NULL,
  `rua` varchar(100) DEFAULT NULL,
  `numero` varchar(10) DEFAULT NULL,
  `complemento` varchar(50) DEFAULT NULL,
  `bairro` varchar(50) DEFAULT NULL,
  `cidade` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `foto` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`clienteId`, `nome`, `email`, `senha`, `telefone`, `cep`, `rua`, `numero`, `complemento`, `bairro`, `cidade`, `estado`, `foto`) VALUES
(1, 'Ana Pereira', 'ana.pereira@mail.com', 'senha321', '(11) 98765-4321', NULL, NULL, NULL, NULL, NULL, 'São Paulo', 'SP', NULL),
(2, 'Carlos Dias', 'carlos.dias@mail.com', 'senha654', '(11) 97654-3210', NULL, NULL, NULL, NULL, NULL, 'São Paulo', 'SP', NULL),
(3, 'Fernanda Lima', 'fernanda.lima@mail.com', 'senha987', '(11) 96543-2109', NULL, NULL, NULL, NULL, NULL, 'São Paulo', 'SP', NULL),
(4, 'Gustavo Almeida', 'gustavo.almeida@mail.com', 'senha111', '(11) 95432-1098', NULL, NULL, NULL, NULL, NULL, 'São Paulo', 'SP', NULL),
(5, 'Patricia Rocha', 'patricia.rocha@mail.com', 'senha222', '(11) 94321-0987', NULL, NULL, NULL, NULL, NULL, 'São Paulo', 'SP', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `denuncia`
--

CREATE TABLE `denuncia` (
  `denunciaId` int(11) NOT NULL,
  `estabelecimentoId` int(11) DEFAULT NULL,
  `clienteId` int(11) DEFAULT NULL,
  `descricao` text NOT NULL,
  `dataDenuncia` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `disponibilidade`
--

CREATE TABLE `disponibilidade` (
  `disponibilidadeId` int(11) NOT NULL,
  `profissionalId` int(11) DEFAULT NULL,
  `data` date NOT NULL,
  `horaInicio` time NOT NULL,
  `horaFim` time NOT NULL,
  `em_agendamento` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `estabelecimento`
--

CREATE TABLE `estabelecimento` (
  `estabelecimentoId` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `cep` varchar(20) NOT NULL,
  `rua` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `complemento` varchar(50) DEFAULT NULL,
  `bairro` varchar(50) NOT NULL,
  `cidade` varchar(50) NOT NULL,
  `estado` varchar(50) NOT NULL,
  `statusCadastroId` int(11) DEFAULT NULL,
  `dataCadastro` timestamp NOT NULL DEFAULT current_timestamp(),
  `foto` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `estabelecimento`
--

INSERT INTO `estabelecimento` (`estabelecimentoId`, `nome`, `email`, `senha`, `telefone`, `cep`, `rua`, `numero`, `complemento`, `bairro`, `cidade`, `estado`, `statusCadastroId`, `dataCadastro`, `foto`) VALUES
(1, 'Barber Shop São Paulo', 'contato@barbershopsao.com', 'senha123', '(11) 99999-0001', '01001-000', 'Av. Paulista', '1000', 'Conjunto 101', 'Centro', 'São Paulo', 'SP', 1, '2024-11-24 19:46:32', NULL),
(6, 'Barbearia Hermanos', 'contato@hermanos.com', 'senha358', '(11) 91234-5678', '04562-002', 'Rua dos Pinheiros', '123', 'Sala 10', 'Pinheiros', 'São Paulo', 'SP', 1, '2024-11-29 01:29:16', NULL),
(7, 'Black Jack', 'contato@blackjack.com', 'senha847', '(11) 92345-6789', '02345-678', 'Avenida Brasil', '45', NULL, 'Jardim Paulista', 'São Paulo', 'SP', 2, '2024-11-29 01:29:16', NULL),
(8, 'Salão Bela Vista', 'contato@belavista.com', 'senha24912', '(11) 93456-7890', '01234-567', 'Rua da Consolação', '890', 'Andar 2', 'Consolação', 'São Paulo', 'SP', 3, '2024-11-29 01:29:16', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `estabelecimentotiposervico`
--

CREATE TABLE `estabelecimentotiposervico` (
  `estabelecimentoId` int(11) NOT NULL,
  `tipoServicoId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `estabelecimentotiposervico`
--

INSERT INTO `estabelecimentotiposervico` (`estabelecimentoId`, `tipoServicoId`) VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 3),
(3, 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pagamento`
--

CREATE TABLE `pagamento` (
  `pagamentoId` int(11) NOT NULL,
  `agendamentoId` int(11) DEFAULT NULL,
  `valor` decimal(10,2) NOT NULL,
  `metodo` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `profissional`
--

CREATE TABLE `profissional` (
  `profissionalId` int(11) NOT NULL,
  `estabelecimentoId` int(11) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `servico` varchar(255) DEFAULT NULL,
  `cep` varchar(20) DEFAULT NULL,
  `rua` varchar(100) DEFAULT NULL,
  `numero` varchar(10) DEFAULT NULL,
  `complemento` varchar(50) DEFAULT NULL,
  `bairro` varchar(50) DEFAULT NULL,
  `cidade` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `foto` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `profissional`
--

INSERT INTO `profissional` (`profissionalId`, `estabelecimentoId`, `nome`, `servico`, `cep`, `rua`, `numero`, `complemento`, `bairro`, `cidade`, `estado`, `foto`) VALUES
(1, 6, 'João Souza', 'Corte', '04562-002', 'Rua dos Pinheiros', '123', 'Sala 10', 'Pinheiros', 'São Paulo', 'SP', NULL),
(2, 6, 'Mariano Oliveira', 'Corte', '04562-002', 'Rua dos Pinheiros', '123', 'Sala 10', 'Pinheiros', 'São Paulo', 'SP', NULL),
(3, 7, 'Lucas Ferreira', 'Barba', '02345-678', 'Avenida Brasil', '45', NULL, 'Jardim Paulista', 'São Paulo', 'SP', NULL),
(4, 7, 'Fernando Costa', 'Corte', '02345-678', 'Avenida Brasil', '45', NULL, 'Jardim Paulista', 'São Paulo', 'SP', NULL),
(5, 8, 'Pedro Lima', 'Corte', '01234-567', 'Rua da Consolação', '890', 'Andar 2', 'Consolação', 'São Paulo', 'SP', NULL),
(6, 1, 'Carlos Alves', 'Barba Modelada', '01001-000', 'Av. Paulista', '1000', 'Conjunto 101', 'Centro', 'São Paulo', 'SP', NULL),
(7, 1, 'Ana Mendes', 'Corte Infantil', '01001-000', 'Av. Paulista', '1000', 'Conjunto 101', 'Centro', 'São Paulo', 'SP', NULL),
(8, 1, 'Roberto Santos', 'Barba Completa', '01001-000', 'Av. Paulista', '1000', 'Conjunto 101', 'Centro', 'São Paulo', 'SP', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `profissionalservico`
--

CREATE TABLE `profissionalservico` (
  `profissionalId` int(11) NOT NULL,
  `servicoId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `profissionalservico`
--

INSERT INTO `profissionalservico` (`profissionalId`, `servicoId`) VALUES
(1, 1),
(1, 2),
(2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `servico`
--

CREATE TABLE `servico` (
  `servicoId` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `descricao` text DEFAULT NULL,
  `tipoServicoId` int(11) NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `duracao` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `servico`
--

INSERT INTO `servico` (`servicoId`, `nome`, `descricao`, `tipoServicoId`, `preco`, `duracao`) VALUES
(1, 'Corte Clássico', 'Corte de cabelo clássico masculino', 1, 40.00, 30),
(2, 'Barba Modelada', 'Barba modelada com acabamentos perfeitos', 2, 30.00, 20),
(3, 'Corte Masculino', 'Corte de cabelo masculino tradicional', 3, 30.00, 30),
(4, 'Corte Infantil', 'Corte de cabelo para crianças', 4, 25.00, 25),
(5, 'Aparar Barba', 'Aparar e modelar a barba', 5, 20.00, 20),
(6, 'Barba Completa', 'Corte completo e modelagem da barba com navalha', 6, 35.00, 30);

-- --------------------------------------------------------

--
-- Estrutura para tabela `sistema`
--

CREATE TABLE `sistema` (
  `sistemaId` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `sistema`
--

INSERT INTO `sistema` (`sistemaId`, `nome`, `logo`, `email`, `telefone`) VALUES
(1, 'Matheus', NULL, 'matheus.sil.goncalves@gmail.com', '986879080'),
(2, 'Laura', 'gfhfhf', 'laura.barros@gmail.com', '982356491');

-- --------------------------------------------------------

--
-- Estrutura para tabela `statuscadastro`
--

CREATE TABLE `statuscadastro` (
  `statusCadastroId` int(11) NOT NULL,
  `descricao` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `statuscadastro`
--

INSERT INTO `statuscadastro` (`statusCadastroId`, `descricao`) VALUES
(1, 'Ativo'),
(2, 'Inativo'),
(3, 'Pendente'),
(4, 'Suspenso');

-- --------------------------------------------------------

--
-- Estrutura para tabela `tiposervico`
--

CREATE TABLE `tiposervico` (
  `tipoServicoId` int(11) NOT NULL,
  `servico` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tiposervico`
--

INSERT INTO `tiposervico` (`tipoServicoId`, `servico`) VALUES
(1, 'Corte de Cabelo'),
(2, 'Barba'),
(3, 'Pacote Completo');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `agendamento`
--
ALTER TABLE `agendamento`
  ADD PRIMARY KEY (`agendamentoId`),
  ADD KEY `estabelecimentoId` (`estabelecimentoId`),
  ADD KEY `profissionalId` (`profissionalId`),
  ADD KEY `clienteId` (`clienteId`),
  ADD KEY `servicoId` (`servicoId`);

--
-- Índices de tabela `avaliacao`
--
ALTER TABLE `avaliacao`
  ADD PRIMARY KEY (`avaliacaoId`),
  ADD KEY `agendamentoId` (`agendamentoId`);

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`clienteId`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Índices de tabela `denuncia`
--
ALTER TABLE `denuncia`
  ADD PRIMARY KEY (`denunciaId`),
  ADD KEY `estabelecimentoId` (`estabelecimentoId`),
  ADD KEY `clienteId` (`clienteId`);

--
-- Índices de tabela `disponibilidade`
--
ALTER TABLE `disponibilidade`
  ADD PRIMARY KEY (`disponibilidadeId`),
  ADD KEY `profissionalId` (`profissionalId`);

--
-- Índices de tabela `estabelecimento`
--
ALTER TABLE `estabelecimento`
  ADD PRIMARY KEY (`estabelecimentoId`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `statusCadastroId` (`statusCadastroId`);

--
-- Índices de tabela `estabelecimentotiposervico`
--
ALTER TABLE `estabelecimentotiposervico`
  ADD PRIMARY KEY (`estabelecimentoId`,`tipoServicoId`),
  ADD KEY `tipoServicoId` (`tipoServicoId`);

--
-- Índices de tabela `pagamento`
--
ALTER TABLE `pagamento`
  ADD PRIMARY KEY (`pagamentoId`),
  ADD KEY `agendamentoId` (`agendamentoId`);

--
-- Índices de tabela `profissional`
--
ALTER TABLE `profissional`
  ADD PRIMARY KEY (`profissionalId`),
  ADD KEY `estabelecimentoId` (`estabelecimentoId`);

--
-- Índices de tabela `profissionalservico`
--
ALTER TABLE `profissionalservico`
  ADD PRIMARY KEY (`profissionalId`,`servicoId`),
  ADD KEY `servicoId` (`servicoId`);

--
-- Índices de tabela `servico`
--
ALTER TABLE `servico`
  ADD PRIMARY KEY (`servicoId`),
  ADD KEY `tipoServicoId` (`tipoServicoId`);

--
-- Índices de tabela `sistema`
--
ALTER TABLE `sistema`
  ADD PRIMARY KEY (`sistemaId`);

--
-- Índices de tabela `statuscadastro`
--
ALTER TABLE `statuscadastro`
  ADD PRIMARY KEY (`statusCadastroId`),
  ADD UNIQUE KEY `descricao` (`descricao`);

--
-- Índices de tabela `tiposervico`
--
ALTER TABLE `tiposervico`
  ADD PRIMARY KEY (`tipoServicoId`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `agendamento`
--
ALTER TABLE `agendamento`
  MODIFY `agendamentoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `avaliacao`
--
ALTER TABLE `avaliacao`
  MODIFY `avaliacaoId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `clienteId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de tabela `denuncia`
--
ALTER TABLE `denuncia`
  MODIFY `denunciaId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `disponibilidade`
--
ALTER TABLE `disponibilidade`
  MODIFY `disponibilidadeId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `estabelecimento`
--
ALTER TABLE `estabelecimento`
  MODIFY `estabelecimentoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `pagamento`
--
ALTER TABLE `pagamento`
  MODIFY `pagamentoId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `profissional`
--
ALTER TABLE `profissional`
  MODIFY `profissionalId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de tabela `servico`
--
ALTER TABLE `servico`
  MODIFY `servicoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de tabela `sistema`
--
ALTER TABLE `sistema`
  MODIFY `sistemaId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `statuscadastro`
--
ALTER TABLE `statuscadastro`
  MODIFY `statusCadastroId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `tiposervico`
--
ALTER TABLE `tiposervico`
  MODIFY `tipoServicoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `avaliacao`
--
ALTER TABLE `avaliacao`
  ADD CONSTRAINT `avaliacao_ibfk_1` FOREIGN KEY (`agendamentoId`) REFERENCES `agendamento` (`agendamentoId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
