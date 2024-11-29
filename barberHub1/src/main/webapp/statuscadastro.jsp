<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gerenciamento - Statuscadastro</title>
	<link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<div class="container my-4">
	<h1>Gerenciamento - Statuscadastro</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Add New Statuscadastro</button>
	</div>
	<table id="dataTable" class="table table-striped">
		<thead>
			<tr>
				<th>Statuscadastroid</th>
				<th>Descricao</th>
				<th>Actions</th>
			</tr>
		</thead>
	</table>
</div>
<div id="formModal" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Statuscadastro Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="statuscadastroForm">
					<div class="mb-3">
						<label for="statusCadastroId" class="form-label">Statuscadastroid</label>
						<input type="text" id="statusCadastroId" name="statusCadastroId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="descricao" class="form-label">Descricao</label>
						<input type="text" id="descricao" name="descricao" class="form-control">
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
				url: '/barberHub1/statuscadastro',
				dataSrc: ''
			},
			"columnDefs": [
	            {
	                "targets": "_all", 
	                "defaultContent": "null" 
	            }
	        ],
			columns: [
				{ data: 'statusCadastroId' },
				{ data: 'descricao' },
				{
					data: null,
					render: function(data, type, row) {
						return '<button class="btn btn-warning btn-sm editRow" data-id="' + row.statusCadastroId + '">Edit</button>' +
						'<button class="btn btn-danger btn-sm deleteRow" data-id=' + row.statusCadastroId +'>Delete</button>';
					}
				}
			]
		});
		$('#addNew').on('click', function() {
			$('#formModal').modal('show');
		});
		$('#saveData').on('click', function() {
			var formData = $('#statuscadastroForm').serialize();
			$.ajax({
				url: '/barberHub1/statuscadastro',
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