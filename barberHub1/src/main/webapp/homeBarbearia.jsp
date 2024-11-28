<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento - BarberHub</title>
    
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


<div class="container my-5">
    <div class="row">
        <div class="col-lg-8">
            <h2>Bem-vindo ao Gerenciamento da Barbearia</h2>
           
            <h3>Últimos Agendamentos</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Cliente</th>
                        <th scope="col">Serviço</th>
                        <th scope="col">Data</th>
                        <th scope="col">Status</th>
                        <th scope="col">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <tr>
                        <td>1</td>
                        <td>João Silva</td>
                        <td>Corte de Cabelo</td>
                        <td>15/11/2024 14:00</td>
                        <td><span class="badge bg-success">Confirmado</span></td>
                        <td>
                            <a href="verAgendamento.jsp?id=1" class="btn btn-info btn-sm">Ver</a>
                            <a href="cancelarAgendamento.jsp?id=1" class="btn btn-danger btn-sm">Cancelar</a>
                        </td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Maria Oliveira</td>
                        <td>Barba e Bigode</td>
                        <td>16/11/2024 10:00</td>
                        <td><span class="badge bg-warning">Pendente</span></td>
                        <td>
                            <a href="verAgendamento.jsp?id=2" class="btn btn-info btn-sm">Ver</a>
                            <a href="cancelarAgendamento.jsp?id=2" class="btn btn-danger btn-sm">Cancelar</a>
                        </td>
                    </tr>
                </tbody>
            </table>

            <a href="gerenciarAgendamentos.jsp" class="btn btn-primary">Ver Todos os Agendamentos</a>
        </div>

        <div class="col-lg-4">
            <h3>Resumo Rápido</h3>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Agendamentos Pendentes</h5>
                    <p class="card-text">Você tem <strong>3</strong> agendamentos pendentes para os próximos dias.</p>
                    <a href="gerenciarAgendamentos.jsp" class="btn btn-warning">Ver Agendamentos</a>
                </div>
            </div>

            <div class="card mt-4">
                <div class="card-body">
                    <h5 class="card-title">Promoções Ativas</h5>
                    <p class="card-text">Confira as promoções que estão disponíveis para seus clientes.</p>
                    <a href="promoções.jsp" class="btn btn-success">Ver Promoções</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Rodapé -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>© 2024 BarberHub - Todos os direitos reservados.</p>
</footer>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

</body>
</html>
