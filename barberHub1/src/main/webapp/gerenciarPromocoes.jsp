<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Promoções</title>
   
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
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



    <h2>Gerenciar Promoções</h2>
    

    
    <a href="novaPromocao.jsp" class="btn btn-success mb-3">Criar Nova Promoção</a>

    <!-- Tabela de Promoções Ativas -->
    <table class="table">
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Título</th>
                <th scope="col">Descrição</th>
                <th scope="col">Data de Início</th>
                <th scope="col">Data de Fim</th>
                <th scope="col">Desconto (%)</th>
                <th scope="col">Ações</th>
            </tr>
        </thead>
        <tbody>
            
            <tr>
                <td>1</td>
                <td>Promoção de Corte</td>
                <td>Corte de cabelo com 20% de desconto</td>
                <td>01/12/2024</td>
                <td>31/12/2024</td>
                <td>20</td>
                <td>
                    <a href="editarPromocao.jsp?id=1" class="btn btn-warning">Editar</a>
                    <a href="removerPromocao.jsp?id=1" class="btn btn-danger">Remover</a>
                </td>
            </tr>
            <tr>
                <td>2</td>
                <td>Desconto em Serviços</td>
                <td>10% de desconto em qualquer serviço</td>
                <td>15/11/2024</td>
                <td>15/12/2024</td>
                <td>10</td>
                <td>
                    <a href="editarPromocao.jsp?id=2" class="btn btn-warning">Editar</a>
                    <a href="removerPromocao.jsp?id=2" class="btn btn-danger">Remover</a>
                </td>
            </tr>
            
        </tbody>
    </table>
</div>

<!-- Rodapé -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>© 2024 BarberHub - Todos os direitos reservados.</p>
</footer>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

</body>
</html>
