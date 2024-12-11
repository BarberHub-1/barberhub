<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gerenciamento - Tiposervico</title>
    <link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container my-4">
    <h1>Gerenciamento - Tiposervico</h1>
    <div class="mb-3">
        <button class="btn btn-primary" id="addNew">Adicionar novo Tipo de serviço</button>
    </div>
    <table id="tipoServico" class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Tipo de Serviço</th>
            <th>Ações</th>
        </tr>
        </thead>
    </table>
</div>

<!-- Modal -->
<div id="formModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Editar Tipo de Serviço</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="tiposervicoForm">
                    <input type="hidden" id="tipoServicoId" name="tipoServicoId">
                    <div class="mb-3">
                        <label for="tipoServicoNome" class="form-label">Tipo de Serviço</label>
                        <input type="text" id="tipoServicoNome" name="tipoServicoNome" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
      
        var table = $('#tipoServico').DataTable({
            ajax: {
                url: '/barberHub1/tipoServico',
                dataSrc: ''
            },
            "columnDefs": [
                {
                    "targets": "_all",
                    "defaultContent": "null"
                }
            ],
            columns: [
                {data: 'tipoServicoId'},
                {data: 'tipoServicoNome'},
                {
                    data: null,
                    render: function (data, type, row) {
                        var id = row.tipoServicoId;
                        return '<button class="btn btn-warning editRow" data-id="' + id + '">Editar</button>' +
                            '<button class="btn btn-danger deleteRow" data-id="' + id + '">Excluir</button>';
                    }
                }
            ]
        });
        
        
        $('#addNew').click(function() {
            window.location.href = "/barberHub1/cadastroTipoServico.jsp";
        });

      
        $('#tipoServico').on('click', '.editRow', function () {
            var id = $(this).data('id');
            console.log("Editando registro com ID:", id);
            $.ajax({
                url: "/barberHub1/tipoServico?tipoServicoId=" + id,
                method: 'GET',
                success: function (data) {
                    $('#tipoServicoId').val(data.tipoServicoId);
                    $('#tipoServicoNome').val(data.tipoServicoNome);
                    $('#formModal').modal('show');
                },
                error: function (err) {
                    alert('Erro ao carregar dados do tipo de serviço.');
                }
            });
        });

       
        $('#tipoServico').on('click', '.deleteRow', function () {
            var id = $(this).data('id');
            if (confirm("Você tem certeza que deseja excluir este tipo de serviço?")) {
                $.ajax({
                    url: "/barberHub1/tipoServico?tipoServicoId=" + id,
                    method: 'DELETE',
                    success: function (response) {
                        alert("Tipo de serviço excluído com sucesso.");
                        table.ajax.reload();
                    },
                    error: function (error) {
                        alert("Erro ao excluir tipo de serviço.");
                    }
                });
            }
        });

        
        $('#tiposervicoForm').submit(function (e) {
            e.preventDefault(); 

            var tipoServicoData = {
                tipoServicoId: $('#tipoServicoId').val(),
                tipoServicoNome: $('#tipoServicoNome').val()
            };

            console.log("Enviando dados para o servidor:", tipoServicoData);

            $.ajax({
                url: "/barberHub1/tipoServico",
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(tipoServicoData),
                success: function (response) {
                    alert("Tipo de serviço salvo com sucesso!");
                    $('#formModal').modal('hide'); 
                    table.ajax.reload();
                },
                error: function (xhr) {
                    alert("Erro ao salvar o tipo de serviço. Detalhes: " + xhr.responseText);
                    console.error("Erro:", xhr.responseText);
                }
            });
        });
    });
    
    
</script>
</body>
</html>