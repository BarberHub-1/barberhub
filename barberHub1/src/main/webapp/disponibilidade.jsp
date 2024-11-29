<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gerenciamento - Disponibilidade</title>
	<link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<div class="container my-4">
	<h1>Gerenciamento - Disponibilidade</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Add New Disponibilidade</button>
	</div>
	<table id="dataTable" class="table table-striped">
		<thead>
			<tr>
				<th>Disponibilidadeid</th>
				<th>Profissionalid</th>
				<th>Data</th>
				<th>Horainicio</th>
				<th>Horafim</th>
				<th>Em_agendamento</th>
				<th>Actions</th>
			</tr>
		</thead>
	</table>
</div>
<div id="formModal" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Disponibilidade Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="disponibilidadeForm">
					<div class="mb-3">
						<label for="disponibilidadeId" class="form-label">Disponibilidadeid</label>
						<input type="text" id="disponibilidadeId" name="disponibilidadeId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="profissionalId" class="form-label">Profissionalid</label>
						<input type="text" id="profissionalId" name="profissionalId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="data" class="form-label">Data</label>
						<input type="text" id="data" name="data" class="form-control">
					</div>
					<div class="mb-3">
						<label for="horaInicio" class="form-label">Horainicio</label>
						<input type="text" id="horaInicio" name="horaInicio" class="form-control">
					</div>
					<div class="mb-3">
						<label for="horaFim" class="form-label">Horafim</label>
						<input type="text" id="horaFim" name="horaFim" class="form-control">
					</div>
					<div class="mb-3">
						<label for="em_agendamento" class="form-label">Em_agendamento</label>
						<input type="text" id="em_agendamento" name="em_agendamento" class="form-control">
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
				url: 'barberHub1/disponibilidade',
				dataSrc: ''
			},
			"columnDefs": [
	            {
	                "targets": "_all", 
	                "defaultContent": "null" 
	            }
	        ],
			columns: [
				{ data: 'disponibilidadeId' },
				{ data: 'profissionalId' },
				{ data: 'data' },
				{ data: 'horaInicio' },
				{ data: 'horaFim' },
				{ data: 'em_agendamento' },
				{
					data: null,
					render: function(data, type, row) {
						return '<button class="btn btn-warning btn-sm editRow" data-id=' + row.disponibilidadeId + '>Edit</button>' +
						'<button class="btn btn-danger btn-sm deleteRow" data-id=' + row.disponibilidadeId + '>Delete</button>`;
					}
				}
			]
		});
		$('#addNew').on('click', function() {
			$('#formModal').modal('show');
		});
		$('#saveData').on('click', function() {
			var formData = $('#disponibilidadeForm').serialize();
			$.ajax({
				url: '/disponibilidade',
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