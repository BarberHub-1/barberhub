<!DOCTYPE html>
<html>
<head>
    <title>Gerenciamento - Profissional</title>
    <link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<div class="container my-4">
    <h1>Profissional Management</h1>
    <div class="mb-3">
        
        <button class="btn btn-primary" id="addNew">Add New Profissional</button>
    </div>
    <table id="profissional" class="table table-striped">
        <thead>
            <tr>
                <th>Profissionalid</th>
                <th>Estabelecimentoid</th>
                <th>Nome</th>
                <th>Servico</th>
                <th>Cep</th>
                <th>Rua</th>
                <th>Numero</th>
                <th>Complemento</th>
                <th>Bairro</th>
                <th>Cidade</th>
                <th>Estado</th>
                <th>Foto</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            
        </tbody>
    </table>
</div>


<div class="modal fade" id="formModal" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="formModalLabel">Editar Profissional</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form id="formProfissional">
            <!-- Campo   oculto -->
            <input type="hidden" id="profissionalId">
            
            <div class="mb-3">
                <label for="estabelecimentoId" class="form-label">Estabelecimento ID</label>
                <input type="text" class="form-control" id="estabelecimentoId">
            </div>
            <div class="mb-3">
                <label for="nome" class="form-label">Nome</label>
                <input type="text" class="form-control" id="nome">
            </div>
            <div class="mb-3">
                <label for="servico" class="form-label">Servico</label>
                <input type="text" class="form-control" id="servico">
            </div>
            <div class="mb-3">
                <label for="cep" class="form-label">CEP</label>
                <input type="text" class="form-control" id="cep">
            </div>
            <div class="mb-3">
                <label for="rua" class="form-label">Rua</label>
                <input type="text" class="form-control" id="rua">
            </div>
            <div class="mb-3">
                <label for="numero" class="form-label">Numero</label>
                <input type="text" class="form-control" id="numero">
            </div>
            <div class="mb-3">
                <label for="complemento" class="form-label">Complemento</label>
                <input type="text" class="form-control" id="complemento">
            </div>
            <div class="mb-3">
                <label for="bairro" class="form-label">Bairro</label>
                <input type="text" class="form-control" id="bairro">
            </div>
            <div class="mb-3">
                <label for="cidade" class="form-label">Cidade</label>
                <input type="text" class="form-control" id="cidade">
            </div>
            <div class="mb-3">
                <label for="estado" class="form-label">Estado</label>
                <input type="text" class="form-control" id="estado">
            </div>
            <div class="mb-3">
                <label for="foto" class="form-label">Foto</label>
                <input type="text" class="form-control" id="foto">
            </div>
            <button type="submit" class="btn btn-primary">Salvar</button>
        </form>
      </div>
    </div>
  </div>
</div>

<!-- Scripts do jQuery e DataTables -->
<script>
$(document).ready(function() {
    
    var table = $('#profissional').DataTable({
        "ajax": {
            "url": "/barberHub1/profissional", 
            "type": "GET",
            "dataSrc": function (json) {
                return json.map(function(item) {
                   
                    return {
                        profissionalId: item.profissionalId,
                        estabelecimentoId: item.estabelecimentoId,
                        nome: item.nome,
                        servico: item.servico,
                        cep: item.cep,
                        rua: item.rua,
                        numero: item.numero,
                        complemento: item.complemento,
                        bairro: item.bairro,
                        cidade: item.cidade,
                        estado: item.estado,
                        foto: item.foto
                    };
                });
            }
        },
        "columns": [
            { "data": "profissionalId" },
            { "data": "estabelecimentoId" },
            { "data": "nome" },
            { "data": "servico" },
            { "data": "cep" },
            { "data": "rua" },
            { "data": "numero" },
            { "data": "complemento" },
            { "data": "bairro" },
            { "data": "cidade" },
            { "data": "estado" },
            { "data": "foto" },
            {
                "data": null,
                "render": function(data, type, row) {
                    var id = row.profissionalId;
                    return '<button class="btn btn-warning editRow" data-id="' + id + '">Editar</button>' +
                    '<button class="btn btn-danger deleteRow" data-id="' + id + '">Excluir</button>';
                }
            }
        ]
    });

    
    $('#addNew').click(function() {
        window.location.href = "/barberHub1/cadastroProfissional.jsp";
    });

   
    $('#profissional').on('click', '.editRow', function() {
        var id = $(this).data('id');
        console.log(id);
        $.ajax({
            url: "/barberHub1/profissional?profissionalId=" + id, 
            method: 'GET',
            success: function(data) {
                
                $('#profissionalId').val(data.profissionalId); 
                $('#estabelecimentoId').val(data.estabelecimentoId);
                $('#nome').val(data.nome);
                $('#servico').val(data.servico);
                $('#cep').val(data.cep);
                $('#rua').val(data.rua);
                $('#numero').val(data.numero);
                $('#complemento').val(data.complemento);
                $('#bairro').val(data.bairro);
                $('#cidade').val(data.cidade);
                $('#estado').val(data.estado);
                $('#foto').val(data.foto);
                $('#formModal').modal('show');
            },
            error: function(err) {
                alert('Erro ao carregar dados do profissional');
            }
        });
    });

    
    $('#profissional').on('click', '.deleteRow', function() {
        var id = $(this).data('id'); 

        if (confirm("Voc� tem certeza que deseja excluir este profissional?")) {
            
            $.ajax({
                url: "/barberHub1/profissional?profissionalId=" + id,  
                method: 'DELETE',  
                success: function(response) {
                    alert("Profissional exclu�do com sucesso");
                    table.ajax.reload();  
                },
                error: function(error) {
                    alert("Erro ao excluir profissional");
                }
            });
        }
    });
    
 
    $('#formProfissional').submit(function(e) {
        e.preventDefault();

        var id = $('#profissionalId').val();
        var profissionalData = {
            profissionalId: id,
            estabelecimentoId: $('#estabelecimentoId').val(),
            nome: $('#nome').val(),
            servico: $('#servico').val(),
            cep: $('#cep').val(),
            rua: $('#rua').val(),
            numero: $('#numero').val(),
            complemento: $('#complemento').val(),
            bairro: $('#bairro').val(),
            cidade: $('#cidade').val(),
            estado: $('#estado').val(),
            foto: $('#foto').val()
        };

        $.ajax({
            url: "/barberHub1/profissional?profissionalId=" + id, 
            method: 'PUT',
            data: profissionalData,
            success: function(response) {
                alert("Profissional editado com sucesso!");
                $('#formModal').modal('hide');
                table.ajax.reload();  
            },
            error: function(error) {
                alert("Erro ao salvar dados do profissional.");
            }
        });
    });

    // Fun��o de mask para CEP
    $('#cep').mask('00000-000');
});
</script>
</body>
</html>