<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Agendamentos - BarberHub</title>
    <link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>

<!-- Barra de Navegação -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Barbearia Marketplace</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Alternar navegação">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
            <li class="nav-item"><a class="nav-link" href="homeBarbearia.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="gerenciarAgendamentos.jsp">Agendamentos</a></li>
            <li class="nav-item"><a class="nav-link" href="gerenciarClientes.jsp">Clientes</a></li>
            <li class="nav-item"><a class="nav-link" href="gerenciarServicos.jsp">Serviços</a></li>
            <li class="nav-item"><a class="nav-link" href="gerenciarProfissionais.jsp">Profissionais</a></li>
            <li class="nav-item"><a class="nav-link" href="gerenciarPromocoes.jsp">Promoções</a></li>
            <li class="nav-item"><a class="nav-link" href="login.jsp">Logout</a></li>
        </ul>
    </div>
</nav>


<div class="container my-5">
    <h2 class="text-center mb-4">Gerenciamento de Agendamentos</h2>

    <div class="table-responsive">
        <table id="agendamento" class="table table-bordered">
            <thead>
                <tr>
                    <th scope="col">ID Agendamento</th>
                    <th scope="col">ID Estabelecimento</th>
                    <th scope="col">ID Profissional</th>
                    <th scope="col">ID Cliente</th>
                    <th scope="col">ID Serviço</th>
                    <th scope="col">Serviço</th>
                    <th scope="col">Data</th>
                    <th scope="col">Hora</th>
                    <th scope="col">Preço</th>
                    <th scope="col">Desconto</th>
                    <th scope="col">Status</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
               
            </tbody>
        </table>
    </div>
</div>

<!-- Rodapé -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>© 2024 BarberHub - Todos os direitos reservados.</p>
</footer>

<script>
    $(document).ready(function() {
        var table = $('#agendamento').DataTable({
            ajax: {
                url: '/barberHub1/agendamento', 
                dataSrc: ''  
            },
            columns: [
                { data: 'agendamentoId' },  
                { data: 'estabelecimentoId' },
                { data: 'profissionalId' },
                { data: 'clienteId' },
                { data: 'servicoId' },
                { data: 'servico' },  
                { data: 'data' },  
                { data: 'hora' },  
                { data: 'preco' },  
                { data: 'desconto' },  
                { data: 'status' },  
                {
                    data: null,
                    render: function(data, type, row) {
                        return '<button class="btn btn-warning btn-sm editRow" data-id=' + row.agendamentoId + '>Editar</button>' +
                               '<button class="btn btn-danger btn-sm deleteRow" data-id=' + row.agendamentoId + '>Excluir</button>';
                    }
                }
            ]
        });

        
        $('#agendamento').on('click', '.editRow', function() {
            var id = $(this).data('id');
            $.ajax({
                url: '/barberHub1/agendamento/' + id, 
                method: 'GET',
                success: function(data) {
                   
                    $('#editIdAgendamento').val(data.agendamentoId); 
                    $('#editCliente').val(data.cliente); 
                    $('#editServico').val(data.servico); 
                    $('#editDataHora').val(data.dataHora);  
                    $('#editStatus').val(data.status);  
                    $('#editModal').modal('show');
                },
                error: function() {
                    alert('Erro ao buscar dados do agendamento.');
                }
            });
        });

        
        $('#saveEdit').click(function() {
            var formData = $('#editAgendamentoForm').serialize();
            $.ajax({
                url: '/barberHub1/agendamento', 
                method: 'PUT',
                data: formData,
                success: function() {
                    $('#editModal').modal('hide');
                    table.ajax.reload();
                }
            });
        });

        
        $('#agendamento').on('click', '.deleteRow', function() {
            var id = $(this).data('id');
            if (confirm('Você tem certeza que deseja excluir este agendamento?')) {
                $.ajax({
                    url: '/barberHub1/agendamento/' + id,
                    method: 'DELETE',
                    success: function() {
                        table.ajax.reload();
                    }
                });
            }
        });
    });
</script>

</body>
</html>
