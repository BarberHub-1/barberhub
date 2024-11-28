<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Denuncia Management</title>
	<link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<div class="container my-4">
	<h1>Denuncia Management</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Add New Denuncia</button>
	</div>
	<table id="dataTable" class="table table-striped">
		<thead>
			<tr>
				<th>Denunciaid</th>
				<th>Estabelecimentoid</th>
				<th>Clienteid</th>
				<th>Descricao</th>
				<th>Datadenuncia</th>
				<th>Actions</th>
			</tr>
		</thead>
	</table>
</div>
<div id="formModal" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Denuncia Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="denunciaForm">
					<div class="mb-3">
						<label for="denunciaId" class="form-label">Denunciaid</label>
						<input type="text" id="denunciaId" name="denunciaId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="estabelecimentoId" class="form-label">Estabelecimentoid</label>
						<input type="text" id="estabelecimentoId" name="estabelecimentoId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="clienteId" class="form-label">Clienteid</label>
						<input type="text" id="clienteId" name="clienteId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="descricao" class="form-label">Descricao</label>
						<input type="text" id="descricao" name="descricao" class="form-control">
					</div>
					<div class="mb-3">
						<label for="dataDenuncia" class="form-label">Datadenuncia</label>
						<input type="text" id="dataDenuncia" name="dataDenuncia" class="form-control">
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
				url: '/barberHub1/denuncia',
				dataSrc: ''
			},
			columns: [
				{ data: 'denunciaId' },
				{ data: 'estabelecimentoId' },
				{ data: 'clienteId' },
				{ data: 'descricao' },
				{ data: 'dataDenuncia' },
				{
					data: null,
					render: function(data, type, row) {
						return `<button class='btn btn-warning btn-sm editRow' data-id='${row.denunciaId}'>Edit</button> ` +
						`<button class='btn btn-danger btn-sm deleteRow' data-id='${row.denunciaId}'>Delete</button>`;
					}
				}
			]
		});
		$('#addNew').on('click', function() {
			$('#formModal').modal('show');
		});
		$('#saveData').on('click', function() {
			var formData = $('#denunciaForm').serialize();
			$.ajax({
				url: '/denuncia',
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