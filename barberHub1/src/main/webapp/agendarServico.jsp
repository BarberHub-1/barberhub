<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agendar Serviço</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

	<!-- Barra de Navegação --> 
	<nav class="navbar navbar-expand-lg navbar-dark bg-light">
	    <a class="navbar-brand text-dark" href="home.jsp">
	        <img src="http://localhost:8080/barberHub1/uploads/Logo.png" alt="BarberHub Logo" height="60" class="d-inline-block align-top">
	    </a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Alternar navegação">
	        <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarNav">
	        <ul class="navbar-nav ml-auto">
	            <li class="nav-item">
	                <a class="nav-link text-dark" href="home.jsp">Início</a>
	            </li>
	            <li class="nav-item">
	                <a class="nav-link text-dark" href="barbearias.jsp">Barbearias</a>
	            </li>
	            <li class="nav-item">
	                <a class="nav-link text-dark" href="promocoes.jsp">Promoções</a>
	            </li>
	            <li class="nav-item">
	                <a class="nav-link text-dark" href="agendamentosClientes.jsp">Meus Agendamentos</a>
	            </li>
	            <li class="nav-item">
	                <a class="nav-link text-dark" href="login.jsp">Login</a>
	            </li>
	        </ul>
	    </div>
	</nav>

	<div class="container my-5">
	    <h2 class="text-center mb-4">Agendar Serviço</h2>
	
	    <!-- Mensagem de status -->
	    <div id="mensagemStatus" style="display: none;" class="alert alert-danger"></div>
	    
	    <!-- Formulário -->
	    <div id="dadosServico" style="display: none;">
	        <form id="formAgendamento">
	            <input type="hidden" name="estabelecimentoId" id="estabelecimentoId" value="">
	
	            <!-- Seleção de Profissional -->
	            <div class="mb-3">
	                <label for="profissional" class="form-label">Selecione um profissional:</label>
	                <select class="form-select" id="profissional" name="profissional" required>
	                    <option value="">Carregando profissionais...</option>
	                </select>
	            </div>
	
	            <!-- Seleção de Serviço -->
	            <div class="mb-3">
	                <label for="servico" class="form-label">Selecione um serviço:</label>
	                <select class="form-select" id="servico" name="servico" required>
	                    <option value="">Selecione um profissional primeiro...</option>
	                </select>
	            </div>
	
	            <!-- Data do Agendamento -->
	            <div class="mb-3">
	                <label for="data" class="form-label">Data do Agendamento:</label>
	                <input type="date" class="form-control" id="data" name="data" required>
	            </div>
	
	            <!-- Hora do Agendamento -->
	            <div class="mb-3">
	                <label for="hora" class="form-label">Hora do Agendamento:</label>
	                <input type="time" class="form-control" id="hora" name="hora" required>
	            </div>
	
	            <!-- Preço Total -->
	            <div class="mb-3">
	                <label for="preco" class="form-label">Preço Total (R$):</label>
	                <input type="text" class="form-control" id="preco" name="preco" readonly>
	            </div>
	
	            <!-- Botão de confirmação -->
	            <button type="button" id="btnConfirmar" class="btn btn-primary">Confirmar Agendamento</button>
	        </form>
	    </div>
	</div>

	<!-- Rodapé -->
    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p>© 2024 BarberHub - Todos os direitos reservados.</p>
    </footer>

<script>
    $(document).ready(function () {
        const urlParams = new URLSearchParams(window.location.search);
        const estabelecimentoId = urlParams.get('id');

        if (estabelecimentoId) {
            $('#estabelecimentoId').val(estabelecimentoId);

            
            $.ajax({
                url: '/barberHub1/profissional',
                type: 'GET',
                data: { estabelecimentoId: estabelecimentoId },
                dataType: 'json',
                success: function (response) {
                    const profissionalSelect = $('#profissional');
                    profissionalSelect.empty();
                    if (response.length > 0) {
                        profissionalSelect.append('<option value="">Selecione um profissional...</option>');
                        response.forEach(profissional => {
                            profissionalSelect.append('<option value=' + profissional.profissionalId + '>' + profissional.nome + '</option>S');
                        });
                        $('#mensagemStatus').hide();
                        $('#dadosServico').show();
                    } else {
                        $('#mensagemStatus').text('Nenhum profissional encontrado para este estabelecimento.').show();
                        $('#dadosServico').hide();
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Erro ao buscar profissionais:', error);
                    $('#mensagemStatus').text('Erro ao carregar os profissionais.').show();
                    $('#dadosServico').hide();
                }
            });

            // Carrega serviços dinamicamente ao selecionar o profissional
            $('#profissional').change(function () {
                const profissionalId = $(this).val();
                const servicoSelect = $('#servico');

                if (profissionalId) {
                    $.ajax({
                        url: '/barberHub1/servico',
                        type: 'GET',
                        data: { profissionalId: profissionalId },
                        dataType: 'json',
                        success: function (response) {
                            servicoSelect.empty();
                            if (response.length > 0) {
                                servicoSelect.append('<option value="">Selecione um serviço...</option>');
                                response.forEach(servico => {
                                    servicoSelect.append('<option value=' + servico.servicoId + ' data-preco=' + servico.preco + '>' + servico.nome +  '- R$ ' + servico.preco.toFixed(2) + '</option>');
                                });
                            } else {
                                servicoSelect.append('<option value="">Nenhum serviço disponível para este profissional.</option>');
                            }
                        },
                        error: function (xhr, status, error) {
                            console.error('Erro ao buscar serviços:', error);
                            servicoSelect.empty().append('<option value="">Erro ao carregar serviços.</option>');
                        }
                    });
                } else {
                    servicoSelect.empty().append('<option value="">Selecione um profissional primeiro...</option>');
                }
            });

            // Atualiza preço total ao selecionar um serviço
            $('#servico').change(function () {
                const preco = $(this).find(':selected').data('preco') || 0;
                $('#preco').val(preco.toFixed(2));
            });

            
            $('#btnConfirmar').click(function () {
                const profissionalId = $('#profissional').val();
                const servicoId = $('#servico').val();
                const data = $('#data').val();
                const hora = $('#hora').val();
                const preco = $('#preco').val();

                if (profissionalId && servicoId && data && hora && preco) {
                    const url = 'confirmarAgendamento.jsp?estabelecimentoId=' + estabelecimentoId +
                        '&profissionalId=' + profissionalId + '&servicoId=' + servicoId +
                        '&data=' + data + '&hora=' + hora + '&preco=' + preco;
                    window.location.href = url;
                } else {
                    $('#mensagemStatus').text('Preencha todos os campos para continuar.').show();
                }
            });
        } else {
            $('#mensagemStatus').text('ID do estabelecimento não fornecido na URL.').show();
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
