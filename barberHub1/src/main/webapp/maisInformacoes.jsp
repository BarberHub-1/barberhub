<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ver Mais - BarberHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .navbar {
            background-color: #d0d0d0 !important;
        }
        .navbar-brand {
            font-size: 1.5rem;
        }
        .container {
            max-width: 600px;
            margin-top: 40px;
        }
        .card {
            border: none;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #d3d3d3 !important; 
            color: #333;
            font-size: 1.5rem;
            font-weight: bold;
            text-align: center;
            padding: 20px;
        }
        .card-body {
            background-color: #ffffff;
            padding: 20px; 
            font-size: 1rem;
            color: #333;
        }
        .card-body img {
            max-width: 100%;
            border-radius: 8px;
        }
        .btn-agendar {
		    margin-top: 20px;
		    background-color: #000000 !important;
		    color: white;
		    border: none;
		    padding: 12px 30px;
		    border-radius: 25px; 
		    font-size: 1.2rem;
		    text-align: center; 
		    display: flex; 
		    justify-content: center; 
		    align-items: center; 
		    margin: 20px auto 0;
		    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15); 
		    transition: background-color 0.3s ease, transform 0.3s ease;
		}
        .btn-agendar:hover {
            background-color: #292929 !important;
            cursor: pointer;
            transform: translateY(-2px); 
        }
        footer {
            background-color: #343a40;
            color: white;
            padding: 20px 0;
            text-align: center;
            margin-top: 40px; 
        }
        .error-message {
            color: red;
        }
        a {
            text-decoration: none;
            color: white !important;
        }
    </style>
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

<div class="container">
    <h2 class="text-center mb-4">Detalhes da Barbearia</h2>

    <div id="mensagemStatus" style="display: none;">Carregando dados...</div>

    <div id="dadosEstabelecimento" style="display: none;">
        <div class="card">
            <div class="card-header">
                Informações do Estabelecimento
            </div>
            <div class="card-body">
                <img src="http://localhost:8080/barberHub1/uploads/barbearia.png?text=Imagem+da+Barbearia" alt="Foto da Barbearia">
                <p><strong>Nome:</strong> <span id="nomeEstabelecimento"></span></p>
                <p><strong>Email:</strong> <span id="emailEstabelecimento"></span></p>
                <p><strong>Telefone:</strong> <span id="telefoneEstabelecimento"></span></p>
                <p><strong>Cidade:</strong> <span id="cidadeEstabelecimento"></span></p>

                <h4>Serviços:</h4>
                <div id="profissionaisEstabelecimento"></div>
            </div>
        </div>

        <a id="btnAgendar" href="#" class="btn-agendar bt">Agendar Agora</a>
    </div>
</div>

<!-- Rodapé -->
<footer class="bg-dark text-white py-3">
    <p>© 2024 BarberHub - Todos os direitos reservados.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function() {
        var urlParams = new URLSearchParams(window.location.search);
        var id = urlParams.get('id');

        if (id) {
            $('#mensagemStatus').show();
            $('#dadosEstabelecimento').hide();

            $.ajax({
                url: '/barberHub1/estabelecimento', 
                type: 'GET',
                data: { id: id },
                dataType: 'json',
                success: function(response) {
                    $('#nomeEstabelecimento').text(response.nome);
                    $('#emailEstabelecimento').text(response.email);
                    $('#telefoneEstabelecimento').text(response.telefone);
                    $('#cidadeEstabelecimento').text(response.cidade);

                    // Exibir os profissionais e serviços
                    var profissionaisHTML = '';
                    response.profissionais.forEach(function(profissional) {
                        profissionaisHTML += '<div><strong>' + profissional.nome + '</strong><ul>';
                        profissional.servicos.forEach(function(servico) {
                            profissionaisHTML += '<li>' + servico.nome + ' - R$ ' + servico.preco + ' (' + servico.duracao + ' min)</li>';
                        });
                        profissionaisHTML += '</ul></div>';
                    });
                    $('#profissionaisEstabelecimento').html(profissionaisHTML);

                    $('#mensagemStatus').hide();
                    $('#dadosEstabelecimento').show();

                    $('#btnAgendar').attr('href', 'agendarServico.jsp?id=' + id);
                },
                error: function(xhr, status, error) {
                    console.error('Erro ao buscar estabelecimento: ', error);
                    $('#mensagemStatus').html('<span class="error-message">Erro ao buscar dados.</span>');
                }
            });
        } else {
            $('#mensagemStatus').html('<span class="error-message">ID não encontrado na URL.</span>');
        }
    });
</script>

</body>
</html>
