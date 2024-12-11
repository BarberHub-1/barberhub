<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Tipo de Serviço</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
</head>
<body>

<!-- Barra de Navegação -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">BarberHub</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Alternar navegação">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
            <li class="nav-item">
                <a class="nav-link" href="homeBarbearia.jsp">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="gerenciarAgendamentos.jsp">Agendamentos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="gerenciarClientes.jsp">Clientes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="gerenciarServicos.jsp">Serviços</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="gerenciarProfissionais.jsp">Profissionais</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="gerenciarPromocoes.jsp">Promoções</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="login.jsp">Logout</a>
            </li>
        </ul>
    </div>
</nav>


<div class="container mt-5">
    <h2 class="text-center mb-4">Cadastro de Tipo de Serviço</h2>
    <form id="cadastroTipoServicoForm" method="POST" action="/tipoServico">
        <div class="row">
            <div class="col-md-6">

                <div class="form-group mb-3">
                    <label for="servico" class="form-label">Serviço</label>
                    <input type="text" id="servico" name="servico" class="form-control">
                </div>
               
            </div>
        </div>

        <div class="d-flex justify-content-center">
            <button type="submit" class="btn btn-primary mt-4">Cadastrar Tipo de Serviço</button>
        </div>
    </form>
</div>

<!-- Rodapé -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>© 2024 BarberHub - Todos os direitos reservados.</p>
</footer>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function() {
        
        
    	$('#cadastroTipoServicoForm').submit(function(e) {
    	    e.preventDefault();  

    	    let formData = {
    	        servico: $('#servico').val()  // O campo "serviço" do formulário
    	    };

    	    $.ajax({
    	        url: '/barberHub1/tipoServico',
    	        type: 'POST',
    	        data: formData,  // Envia como dados do formulário
    	        success: function(response) {
    	            alert('Tipo de serviço cadastrado com sucesso!');
    	            $('#cadastroTipoServicoForm')[0].reset();
    	        },
    	        error: function(error) {
    	            alert('Erro ao cadastrar o tipo de serviço. Tente novamente.');
    	        }
    	    });
    	});
    });
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>

</body>
</html>
