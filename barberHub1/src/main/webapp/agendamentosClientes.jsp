<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meus Agendamentos - Marketplace de Barbearias</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<!-- Barra de Navegação -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="home.jsp">BarberHub</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Alternar navegação">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="home.jsp">Início</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="barbearias.jsp">Barbearias</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="promocoes.jsp">Promoções</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="agendamentosClientes.jsp">Meus Agendamentos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="login.jsp">Login</a>
            </li>
        </ul>
    </div>
</nav>


<div class="container my-5">
    <h2 class="text-center mb-4">Meus Agendamentos</h2>

    
    <div id="mensagemSemAgendamentos" class="alert alert-info" role="alert" style="display: none;">
        Você não tem agendamentos.
    </div>

    
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Data e Hora</th>
                <th>Barbearia</th>
                <th>Serviço</th>
                <th>Estado</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody id="agendamentosTabela">
       
            <tr>
                <td>10/12/2024 15:00</td>
                <td>Barbearia X</td>
                <td>Corte de Cabelo</td>
                <td>Agendado</td>
                <td>
                    <a href="#" class="btn btn-danger btn-sm" onclick="cancelarAgendamento(1)">Cancelar</a>
                </td>
            </tr>
            <tr>
                <td>12/12/2024 09:00</td>
                <td>Barbearia Y</td>
                <td>Barba e Corte</td>
                <td>Agendado</td>
                <td>
                    <a href="#" class="btn btn-danger btn-sm" onclick="cancelarAgendamento(2)">Cancelar</a>
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


<script>
    function cancelarAgendamento(id) {
      
        if (confirm("Você tem certeza que deseja cancelar este agendamento?")) {
           
            alert("Agendamento " + id + " cancelado com sucesso.");
            
            document.querySelector("#agendamento-" + id).remove();
            verificarAgendamentos();
        }
    }

   
    function verificarAgendamentos() {
        const agendamentos = document.querySelectorAll("#agendamentosTabela tr");
        if (agendamentos.length === 0) {
            document.getElementById("mensagemSemAgendamentos").style.display = "block";
        }
    }

  
    window.onload = function() {
        verificarAgendamentos();
    };
</script>

</body>
</html>
