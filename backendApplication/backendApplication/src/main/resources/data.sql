-- Inserir usuários
INSERT INTO usuario (id, email, senha, tipo, nome, cpf, telefone, status, nome_proprietario, nome_estabelecimento, cnpj, endereco, cidade, cep) VALUES
(1, 'admin@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMINISTRADOR', 'Administrador', '123.456.789-00', '11999999999', 'APROVADO', NULL, NULL, NULL, NULL, NULL, NULL),
(2, 'cliente@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'CLIENTE', 'João Silva', '987.654.321-00', '11988888888', 'APROVADO', NULL, NULL, NULL, 'Centro', 'São Paulo', '01234-567'),
(3, 'estabelecimento@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ESTABELECIMENTO', NULL, NULL, '11977777777', 'APROVADO', 'Carlos Oliveira', 'Barbearia Estilo', '12.345.678/0001-95', 'Rua das Flores, 123', 'São Paulo', '01234-567'),
(4, 'novo.admin@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMINISTRADOR', 'Maria Santos', '999.999.999-99', '11944444444', 'APROVADO', NULL, NULL, NULL, NULL, NULL, NULL),
(5, 'elite@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ESTABELECIMENTO', NULL, NULL, '11922223333', 'APROVADO', 'Eduardo Lima', 'Barbearia Elite', '23.456.789/0001-10', 'Av. Paulista, 1000', 'São Paulo', '01310-100'),
(6, 'vintage@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ESTABELECIMENTO', NULL, NULL, '11933334444', 'APROVADO', 'Roberto Souza', 'Vintage Barber', '34.567.890/0001-20', 'Rua Augusta, 500', 'São Paulo', '01305-000'),
(7, 'modern@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ESTABELECIMENTO', NULL, NULL, '11944445555', 'APROVADO', 'Lucas Martins', 'Modern Barber', '45.678.901/0001-30', 'Rua Oscar Freire, 250', 'São Paulo', '01426-001');

-- Inserir serviços
INSERT INTO servico (id, descricao, preco, duracao_minutos, tipo, estabelecimento_id) VALUES
(1, 'Corte de Cabelo Tradicional', 50.00, 30, 'CORTE_DE_CABELO', 3),
(2, 'Barba Completa', 30.00, 20, 'BARBA', 3),
(3, 'Hidratação Profunda', 80.00, 45, 'HIDRATACAO', 3),
(4, 'Luzes Californianas', 150.00, 120, 'LUZES', 3),
(5, 'Design de Sobrancelha', 25.00, 15, 'SOBRANCELHA', 3),
(6, 'Corte Social', 60.00, 40, 'CORTE_DE_CABELO', 5),
(7, 'Barba desenhada', 35.00, 25, 'BARBA', 5),
(8, 'Hidratação Capilar', 90.00, 50, 'HIDRATACAO', 5),
(9, 'Corte Clássico', 55.00, 35, 'CORTE_DE_CABELO', 6),
(10, 'Barba Completa', 32.00, 20, 'BARBA', 6),
(11, 'Luzes', 160.00, 110, 'LUZES', 6),
(12, 'Corte Moderno', 70.00, 45, 'CORTE_DE_CABELO', 7),
(13, 'Barba Express', 28.00, 15, 'BARBA', 7),
(14, 'Sobrancelha', 22.00, 10, 'SOBRANCELHA', 7);

-- Inserir profissionais
INSERT INTO profissional (id, nome, email, telefone, estabelecimento_id) VALUES
(1, 'João Silva', 'joao@email.com', '11966666666', 3),
(2, 'Maria Santos', 'maria@email.com', '11955555555', 3),
(3, 'Pedro Oliveira', 'pedro@email.com', '11933333333', 3),
(4, 'Felipe Ramos', 'felipe@email.com', '11955556666', 5),
(5, 'André Silva', 'andre@email.com', '11966667777', 6),
(6, 'Bruno Costa', 'bruno@email.com', '11977778888', 7);

-- Inserir horários de funcionamento
INSERT INTO horarios_funcionamento (id, dia_semana, horario_abertura, horario_fechamento, estabelecimento_id) VALUES
(1, 'SEGUNDA', '09:00', '18:00', 3),
(2, 'TERCA', '09:00', '18:00', 3),
(3, 'QUARTA', '09:00', '18:00', 3),
(4, 'QUINTA', '09:00', '18:00', 3),
(5, 'SEXTA', '09:00', '18:00', 3),
(6, 'SABADO', '09:00', '14:00', 3),
(7, 'SEGUNDA', '10:00', '19:00', 5),
(8, 'TERCA', '10:00', '19:00', 5),
(9, 'QUARTA', '10:00', '19:00', 5),
(10, 'QUINTA', '10:00', '19:00', 5),
(11, 'SEXTA', '10:00', '19:00', 5),
(12, 'SABADO', '09:00', '15:00', 5),
(13, 'SEGUNDA', '09:00', '18:00', 6),
(14, 'TERCA', '09:00', '18:00', 6),
(15, 'QUARTA', '09:00', '18:00', 6),
(16, 'QUINTA', '09:00', '18:00', 6),
(17, 'SEXTA', '09:00', '18:00', 6),
(18, 'SABADO', '09:00', '14:00', 6),
(19, 'SEGUNDA', '11:00', '20:00', 7),
(20, 'TERCA', '11:00', '20:00', 7),
(21, 'QUARTA', '11:00', '20:00', 7),
(22, 'QUINTA', '11:00', '20:00', 7),
(23, 'SEXTA', '11:00', '20:00', 7),
(24, 'SABADO', '10:00', '16:00', 7);

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