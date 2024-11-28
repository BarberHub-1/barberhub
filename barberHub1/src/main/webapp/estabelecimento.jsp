<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gerenciamento - Estabelecimento</title>
	<link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<div class="container my-4">
	<h1>Gerenciamento - Estabelecimento</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Add New Estabelecimento</button>
	</div>
	<table id="estabelecimento" class="table table-striped">
		<thead>
			<tr>
				<th>Estabelecimentoid</th>
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
				<th>Statuscadastroid</th>
				<th>Datacadastro</th>
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
				<h5 class="modal-title">Estabelecimento Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="estabelecimentoForm">
					<div class="mb-3">
						<label for="estabelecimentoId" class="form-label">Estabelecimentoid</label>
						<input type="text" id="estabelecimentoId" name="estabelecimentoId" class="form-control">
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
						<label for="statusCadastroId" class="form-label">Statuscadastroid</label>
						<input type="text" id="statusCadastroId" name="statusCadastroId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="dataCadastro" class="form-label">Datacadastro</label>
						<input type="text" id="dataCadastro" name="dataCadastro" class="form-control">
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
		var table = $('#estabelecimento').DataTable({
			ajax: {
				url: '/barberHub1/estabelecimento',
				dataSrc: ''
			},
			columns: [
				{ data: 'estabelecimentoId' },
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
				{ data: 'statusCadastroId' },
				{ data: 'dataCadastro' },
				{ data: 'foto' },
				{
					data: null,
					render: function(data, type, row) {
						return `<button class='btn btn-warning btn-sm editRow' data-id='${row.estabelecimentoId}'>Edit</button> ` +
						`<button class='btn btn-danger btn-sm deleteRow' data-id='${row.estabelecimentoId}'>Delete</button>`;
					}
				}
			]
		});
		$('#addNew').on('click', function() {
			$('#formModal').modal('show');
		});
		$('#saveData').on('click', function() {
			var formData = $('#estabelecimentoForm').serialize();
			$.ajax({
				url: '/barberHub1/estabelecimento',
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