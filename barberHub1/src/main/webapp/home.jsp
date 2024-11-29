<%@ page import="java.util.List" %>
<%@ page import="model.Estabelecimento" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
   
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>

        body {
            background-color: #f8f9fa;
            color: #333;
        }
  
        .navbar {
            background-color: #d0d0d0 !important;
        }

        .navbar-brand,
        .nav-link {
            color: white;
        }

        .banner {
            background-color: #333;
            color: white;
        }

        .btn-primary {
            background-color: #555;
            border-color: #555;
        }

        .btn-primary:hover {
            background-color: #444;
            border-color: #444;
        }

        .cards-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: space-around;
            padding: 20px;
        }
       
        .card {
            width: 22%;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            background-color: white;
            transition: transform 0.3s ease;
        }

        .card:hover {
            transform: translateY(-10px);
        }

        .card-img-top {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .card-body {
            padding: 15px;
        }

        .card-title {
            font-size: 1.2em;
            margin-bottom: 10px;
        }

        .card-text {
            margin-bottom: 10px;
        }

        @media (max-width: 768px) {
            .card {
                width: 45%;
            }
        }

        @media (max-width: 480px) {
            .card {
                width: 90%;
            }
        }
    </style>
    <script>
        $(document).ready(function() {
            $.ajax({
                url: '/barberHub1/estabelecimento',
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                 
                    if (data.error) {
                        $('#estabelecimentos-list').html('<p>' + data.error + '</p>');
                    } else {
                       
                        var listHtml = '';
                        data.forEach(function(estabelecimento) {
                            listHtml += '<div class="card">' +
                                            '<img src="http://localhost:8080/barberHub1/uploads/barbearia.png" class="card-img-top" alt="Imagem do estabelecimento">' +
                                            '<div class="card-body">' +
                                                '<h5 class="card-title">' + estabelecimento.nome + '</h5>' +
                                                '<p class="card-text"><strong>Email:</strong>' + estabelecimento.email + '</p>' +
                                                '<p class="card-text"><strong>Telefone:</strong>' + estabelecimento.telefone + '</p>' +
                                                '<p class="card-text"><strong>Cidade:</strong>' + estabelecimento.cidade + '</p>' +
                                                '<a href="maisInformacoes.jsp?id=' + estabelecimento.id + '" class="btn btn-primary">Ver Mais</a>' +
                                            '</div>' +
                                          '</div>';
                        });
                        $('#estabelecimentos-list').html(listHtml);
                    }
                },
                error: function(xhr, status, error) {
                    $('#estabelecimentos-list').html('<p>Erro ao carregar dados dos estabelecimentos.</p>');
                }
            });

            
            $.ajax({
                url: '/barberHub1/tipoServico',
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                    var selectHtml = '<option value="">Selecione um serviço</option>';
                    data.forEach(function(servico) {
                        
                        selectHtml += '<option value="' + servico.tipoServicoId + '">' + servico.servico + '</option>';
                    });
                    $('#servico').html(selectHtml);
                },
                error: function(xhr, status, error) {
                    console.error('Erro ao carregar tipos de serviço:', error);
                }
            });

           
            $(document).on('click', '.editar-btn', function() {
                var estabelecimentoId = $(this).data('id');
                console.log("ID do estabelecimento para editar: " + estabelecimentoId);

               
                window.location.href = "maisInformacoes.jsp?id=" + estabelecimentoId;
            });
        });
    </script>
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

    <!-- Banner Principal -->
    <div class="container-fluid p-5 banner text-center">
        <h1>Bem-vindo ao BarberHub!</h1>
        <p>Encontre as melhores barbearias da sua cidade com facilidade</p>
        <a href="barbearias.jsp" class="btn btn-primary btn-lg">Buscar Barbearias</a>
    </div>

    <!-- Filtros de Busca -->
    <div class="container my-5" id="buscar">
        <h2 class="text-center mb-4">Encontre a Barbearia Perfeita</h2>
        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="form-group">
                    <label for="servico">Serviço</label>
                    <select class="form-control" id="servico">
                       
                    </select>
                </div>
            </div>
            <div class="col-md-4 d-flex align-items-end">
                <button class="btn btn-primary w-100">Buscar</button>
            </div>
        </div>
    </div>

  
    <h1 class="text-center my-4">Lista de Estabelecimentos</h1>
    <div class="cards-container" id="estabelecimentos-list">
       
    </div>

    <!-- Rodapé -->
    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p>© 2024 BarberHub - Todos os direitos reservados.</p>
    </footer>
</body>
</html>