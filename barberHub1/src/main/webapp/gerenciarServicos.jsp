<%@ page import="java.util.List" %>
<%@ page import="model.Estabelecimento" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Serviços - BarberHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
   
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
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
    <h2>Gerenciamento de Serviços</h2>
    <a href="cadastrarServico.jsp" class="btn btn-primary mb-3">Adicionar Novo Serviço</a>

   
    <table id="servico" class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome do Serviço</th>
                <th>Descrição</th>
                <th>Tipo de Serviço</th>
                <th>Preço</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            
        </tbody>
    </table>
</div>


<!-- Modal de Edição de Serviço -->
<div class="modal fade" id="formModal" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="formModalLabel">Editar Serviço</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editarServicoForm">
                    <div class="mb-3">
                        <label for="nomeServico" class="form-label">Nome do Serviço</label>
                        <input type="text" class="form-control" id="nomeServico" required>
                    </div>
                    <div class="mb-3">
                        <label for="descricaoServico" class="form-label">Descrição</label>
                        <input type="text" class="form-control" id="descricaoServico" required>
                    </div>
                    <div class="mb-3">
                        <label for="tipoServico" class="form-label">Tipo de Serviço</label>
                        <input type="text" class="form-control" id="tipoServico" required>
                    </div>
                    <div class="mb-3">
                        <label for="precoServico" class="form-label">Preço</label>
                        <input type="number" class="form-control" id="precoServico" required>
                    </div>
                    <input type="hidden" id="servicoId">
                    <button type="submit" class="btn btn-primary">Salvar alterações</button>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- Rodapé -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>© 2024 BarberHub - Todos os direitos reservados.</p>
</footer>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>


<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<script>
$(document).ready(function() {
    $('#tabelaServicos').DataTable({
        "ajax": {
            "url": "/barberHub1/servico", 
            "type": "GET",
            "dataSrc": "" 
        },
        "columnDefs": [
            {
                "targets": "_all", 
                "defaultContent": "null" 
            }
        ],
        "columns": [
            { "data": "servicoId" },
            { "data": "nome" },
            { "data": "descricao" },
            { "data": "tipoServicoNome" },
            { "data": "preco" },
            { 
                "data": null, 
                "render": function(data, type, row) {
                    var id = row.servicoId;
                    return '<a href="#" class="btn btn-warning btn-sm editRow" data-id="' + id + '">Editar</a>' +
                           ' <button class="btn btn-danger btn-sm deleteRow" data-id="' + id + '">Excluir</button>';
                }
            }
        ],
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/pt-BR.json"
        }
    });

    
    $('#tabelaServicos').on('click', '.editRow', function() {
        var id = $(this).data('id'); 

       
        $.ajax({
            url: "/barberHub1/servico?servicoId=" + id, 
            method: 'GET',
            success: function(response) {
               
                $('#servicoId').val(response.servicoId);
                $('#nomeServico').val(response.nome);
                $('#descricaoServico').val(response.descricao);
                $('#tipoServico').val(response.tipoServicoNome);
                $('#precoServico').val(response.preco);

                
                $('#formModal').modal('show');
            },
            error: function(xhr) {
                alert('Erro ao buscar dados: ' + xhr.responseText);
            }
        });
    });

   
    $('#editarServicoForm').submit(function(e) {
        e.preventDefault(); 

        var servicoId = $('#servicoId').val();
        var nome = $('#nomeServico').val();
        var descricao = $('#descricaoServico').val();
        var tipoServico = $('#tipoServico').val();
        var preco = $('#precoServico').val();

       
        var formData = {
            servicoId: servicoId,
            nome: nome,
            descricao: descricao,
            tipoServicoNome: tipoServico,
            preco: preco
        };

        $.ajax({
            url: "/barberHub1/servico?servicoId=" + servicoId,
            method: 'PUT',  
            data: JSON.stringify(formData),  
            contentType: 'application/json',  
            success: function(response) {
                alert('Serviço atualizado com sucesso!');
               
                $('#formModal').modal('hide');
              
                $('#tabelaServicos').DataTable().ajax.reload();
            },
            error: function(xhr) {
                alert('Erro ao atualizar serviço: ' + xhr.responseText);
            }
        });
    });

   
    $('#tabelaServicos').on('click', '.deleteRow', function() {
        var id = $(this).data('id'); 

        if (confirm("Você tem certeza que deseja excluir este serviço?")) {
           
            $.ajax({
                url: "/barberHub1/servico?servicoId=" + id,  
                method: 'DELETE',  
                success: function(response) {
                    alert('Serviço excluído com sucesso!');
                    
                    $('#tabelaServicos').DataTable().ajax.reload();
                },
                error: function(xhr) {
                    alert('Erro ao excluir serviço: ' + xhr.responseText);
                }
            });
        }
    });
});
</script>


</body>
</html>
