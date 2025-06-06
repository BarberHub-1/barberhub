-- Inserir usuários
INSERT INTO usuario (id, email, senha, tipo, nome, cpf, telefone, status, nome_proprietario, nome_estabelecimento, cnpj, endereco, cidade, cep) VALUES
(1, 'admin@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMINISTRADOR', 'Administrador', '123.456.789-00', '11999999999', 'APROVADO', NULL, NULL, NULL, NULL, NULL, NULL),
(2, 'cliente@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'CLIENTE', 'João Silva', '987.654.321-00', '11988888888', 'APROVADO', NULL, NULL, NULL, 'Centro', 'São Paulo', '01234-567'),
(3, 'estabelecimento@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ESTABELECIMENTO', NULL, NULL, '11977777777', 'APROVADO', 'Carlos Oliveira', 'Barbearia Estilo', '12.345.678/0001-95', 'Rua das Flores, 123', 'São Paulo', '01234-567'),
(4, 'novo.admin@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMINISTRADOR', 'Maria Santos', '999.999.999-99', '11944444444', 'APROVADO', NULL, NULL, NULL, NULL, NULL, NULL);

-- Inserir serviços
INSERT INTO servico (id, descricao, preco, duracao_minutos, tipo, estabelecimento_id) VALUES
(1, 'Corte de Cabelo Tradicional', 50.00, 30, 'CORTE_DE_CABELO', 3),
(2, 'Barba Completa', 30.00, 20, 'BARBA', 3),
(3, 'Hidratação Profunda', 80.00, 45, 'HIDRATACAO', 3),
(4, 'Luzes Californianas', 150.00, 120, 'LUZES', 3),
(5, 'Design de Sobrancelha', 25.00, 15, 'SOBRANCELHA', 3);

-- Inserir profissionais
INSERT INTO profissional (id, nome, email, telefone, estabelecimento_id) VALUES
(1, 'João Silva', 'joao@email.com', '11966666666', 3),
(2, 'Maria Santos', 'maria@email.com', '11955555555', 3),
(3, 'Pedro Oliveira', 'pedro@email.com', '11933333333', 3);

-- Inserir horários de funcionamento
INSERT INTO horarios_funcionamento (id, dia_semana, horario_abertura, horario_fechamento, estabelecimento_id) VALUES
(1, 'SEGUNDA', '09:00', '18:00', 3),
(2, 'TERCA', '09:00', '18:00', 3),
(3, 'QUARTA', '09:00', '18:00', 3),
(4, 'QUINTA', '09:00', '18:00', 3),
(5, 'SEXTA', '09:00', '18:00', 3),
(6, 'SABADO', '09:00', '14:00', 3);

-- Inserir agendamentos
INSERT INTO agendamento (id, data_hora, status, cliente_id, estabelecimento_id) VALUES
(1, '2024-05-27 10:00:00', 'AGENDADA', 2, 3),
(2, '2024-05-28 14:00:00', 'AGENDADA', 2, 3);

-- Inserir serviços dos agendamentos
INSERT INTO agendamento_servico (agendamento_id, servico_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4);

-- Inserir avaliações
INSERT INTO avaliacao (id, nota, comentario, data_avaliacao, agendamento_id, estabelecimento_id) VALUES
(1, 5, 'Excelente atendimento! Profissionais muito atenciosos.', '2024-05-27 11:00:00', 1, 3),
(2, 4, 'Muito bom atendimento, ambiente agradável!', '2024-05-28 15:00:00', 2, 3); 