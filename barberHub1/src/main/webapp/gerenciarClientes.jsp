<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gerenciamento - Cliente</title>
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


<div class="container my-4">
	<h1>Gerenciamento - Cliente</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Adicionar Cliente</button>
	</div>
	<table id="dataTable" class="table table-striped">
		<thead>
			<tr>
				<th>Clienteid</th>
				<th>Nome</th>
				<th>Email</th>
				<th>Senha</th>
				<th>Telefone</th>
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
	</table>
</div>
<div id="formModal" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Cliente Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="clienteForm">
					<div class="mb-3">
						<label for="clienteId" class="form-label">Clienteid</label>
						<input type="text" id="clienteId" name="clienteId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="nome" class="form-label">Nome</label>
						<input type="text" id="nome" name="nome" class="form-control">
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email</label>
						<input type="text" id="email" name="email" class="form-control">
					</div>
					<div class="mb-3">
						<label for="senha" class="form-label">Senha</label>
						<input type="text" id="senha" name="senha" class="form-control">
					</div>
					<div class="mb-3">
						<label for="telefone" class="form-label">Telefone</label>
						<input type="text" id="telefone" name="telefone" class="form-control">
					</div>
					<div class="mb-3">
						<label for="cep" class="form-label">Cep</label>
						<input type="text" id="cep" name="cep" class="form-control">
					</div>
					<div class="mb-3">
						<label for="rua" class="form-label">Rua</label>
						<input type="text" id="rua" name="rua" class="form-control">
					</div>
					<div class="mb-3">
						<label for="numero" class="form-label">Numero</label>
						<input type="text" id="numero" name="numero" class="form-control">
					</div>
					<div class="mb-3">
						<label for="complemento" class="form-label">Complemento</label>
						<input type="text" id="complemento" name="complemento" class="form-control">
					</div>
					<div class="mb-3">
						<label for="bairro" class="form-label">Bairro</label>
						<input type="text" id="bairro" name="bairro" class="form-control">
					</div>
					<div class="mb-3">
						<label for="cidade" class="form-label">Cidade</label>
						<input type="text" id="cidade" name="cidade" class="form-control">
					</div>
					<div class="mb-3">
						<label for="estado" class="form-label">Estado</label>
						<input type="text" id="estado" name="estado" class="form-control">
					</div>
					<div class="mb-3">
						<label for="foto" class="form-label">Foto</label>
						<input type="text" id="foto" name="foto" class="form-control">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" id="saveData">Save</button>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		var table = $('#dataTable').DataTable({
			ajax: {
				url: '/barberHub1/cliente',
				dataSrc: ''
			},
			"columnDefs": [
	            {
	                "targets": "_all", 
	                "defaultContent": "null" 
	            }
	        ],
			columns: [
				{ data: 'clienteId' },
				{ data: 'nome' },
				{ data: 'email' },
				{ data: 'senha' },
				{ data: 'telefone' },
				{ data: 'cep' },
				{ data: 'rua' },
				{ data: 'numero' },
				{ data: 'complemento' },
				{ data: 'bairro' },
				{ data: 'cidade' },
				{ data: 'estado' },
				{ data: 'foto' },
				{
					data: null,
					render: function(data, type, row) {
						return '<button class="btn btn-warning btn-sm editRow" data-id=' + row.clienteId + '>Edit</button>' +
						'<button class="btn btn-danger btn-sm deleteRow" data-id=' + row.clienteId + '>Delete</button>';
					}
				}
			]
		});
		$('#addNew').on('click', function() {
			$('#formModal').modal('show');
		});
		$('#saveData').on('click', function() {
			var formData = $('#clienteForm').serialize();
			$.ajax({
				url: '/cliente',
				method: 'POST',
				data: formData,
				success: function(response) {
					table.ajax.reload();
					$('#formModal').modal('hide');
				},
				error: function(xhr) {
					alert('Error: ' + xhr.responseText);
				}
			});
		});
	});
</script>
</body>
</html>