<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmar Agendamento</title>
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
    <h2 class="text-center mb-4">Confirmar Agendamento</h2>

   
    <div class="card">
        <div class="card-body">
            <h5 class="card-title text-center">Detalhes do Agendamento</h5>
            <ul class="list-group">
                <li class="list-group-item"><strong>Estabelecimento:</strong> <span id="estabelecimento"></span></li>
                <li class="list-group-item"><strong>Profissional:</strong> <span id="profissional"></span></li>
                <li class="list-group-item"><strong>Serviço:</strong> <span id="servico"></span></li>
                <li class="list-group-item"><strong>Data:</strong> <span id="data"></span></li>
                <li class="list-group-item"><strong>Hora:</strong> <span id="hora"></span></li>
                <li class="list-group-item"><strong>Preço Total:</strong> R$ <span id="preco"></span></li>
            </ul>
        </div>
    </div>

    <!-- Botões de ação -->
    <div class="text-center mt-4">
        <button id="btnConfirmar" class="btn btn-success">Confirmar</button>
        <a href="agendarServico.jsp?id=<%= request.getParameter("estabelecimentoId") %>" class="btn btn-secondary">Voltar</a>
    </div>

    <!-- Mensagem de status -->
    <div id="mensagemStatus" class="alert mt-4" style="display: none;"></div>
</div>

	<!-- Rodapé -->
    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p>© 2024 BarberHub - Todos os direitos reservados.</p>
    </footer>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const urlParams = new URLSearchParams(window.location.search);

        
        const estabelecimentoId = urlParams.get('estabelecimentoId');
        const estabelecimentoNome = urlParams.get('estabelecimentoNome'); 
        const profissionalId = urlParams.get('profissionalId');
        const servicoId = urlParams.get('servicoId');
        const data = urlParams.get('data');
        const hora = urlParams.get('hora');
        const preco = urlParams.get('preco');

       
        document.getElementById('estabelecimento').innerText = estabelecimentoNome || estabelecimentoId || 'N/A';
        document.getElementById('profissional').innerText = profissionalId || 'N/A';
        document.getElementById('servico').innerText = servicoId || 'N/A';
        document.getElementById('data').innerText = data || 'N/A';
        document.getElementById('hora').innerText = hora || 'N/A';
        document.getElementById('preco').innerText = preco || 'N/A';

       
        document.getElementById('btnConfirmar').addEventListener('click', () => {
            
            const payload = {
                estabelecimentoId,
                profissionalId,
                servicoId,
                data,
                hora,
                preco
            };

            
            $.ajax({
                url: '/barberHub1/agendamento', 
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(payload),
                success: function(response) {
                    $('#mensagemStatus')
                        .removeClass('alert-danger')
                        .addClass('alert-success')
                        .text('Agendamento realizado com sucesso!')
                        .show();

                    setTimeout(() => {
                        window.location.href = "home.jsp";
                    }, 2000);
                },
                error: function(xhr) {
                    const errorMessage = xhr.responseJSON?.error || 'Erro ao realizar o agendamento.';
                    $('#mensagemStatus')
                        .removeClass('alert-success')
                        .addClass('alert-danger')
                        .text(errorMessage)
                        .show();
                }
            });
        });
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
