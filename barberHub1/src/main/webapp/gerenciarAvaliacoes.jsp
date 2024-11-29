<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gerenciamento - Avaliação</title>
	<link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>




<div class="container my-4">
	<h1>Gerenciamento - Avaliação</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Add New Avaliacao</button>
	</div>
	<table id="dataTable" class="table table-striped">
		<thead>
			<tr>
				<th>Avaliacaoid</th>
				<th>Agendamentoid</th>
				<th>Avaliado</th>
				<th>Nota</th>
				<th>Comentario</th>
				<th>Dataavaliacao</th>
				<th>Actions</th>
			</tr>
		</thead>
	</table>
</div>
<div id="formModal" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Avaliacao Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="avaliacaoForm">
					<div class="mb-3">
						<label for="avaliacaoId" class="form-label">Avaliacaoid</label>
						<input type="text" id="avaliacaoId" name="avaliacaoId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="agendamentoId" class="form-label">Agendamentoid</label>
						<input type="text" id="agendamentoId" name="agendamentoId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="avaliado" class="form-label">Avaliado</label>
						<input type="text" id="avaliado" name="avaliado" class="form-control">
					</div>
					<div class="mb-3">
						<label for="nota" class="form-label">Nota</label>
						<input type="text" id="nota" name="nota" class="form-control">
					</div>
					<div class="mb-3">
						<label for="comentario" class="form-label">Comentario</label>
						<input type="text" id="comentario" name="comentario" class="form-control">
					</div>
					<div class="mb-3">
						<label for="dataAvaliacao" class="form-label">Dataavaliacao</label>
						<input type="text" id="dataAvaliacao" name="dataAvaliacao" class="form-control">
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
				url: '/barberHub1/avaliacao',
				dataSrc: ''
			},
			"columnDefs": [
	            {
	                "targets": "_all", 
	                "defaultContent": "null" 
	            }
	        ],
			columns: [
				{ data: 'avaliacaoId' },
				{ data: 'agendamentoId' },
				{ data: 'avaliado' },
				{ data: 'nota' },
				{ data: 'comentario' },
				{ data: 'dataAvaliacao' },
				{
					data: null,
					render: function(data, type, row) {
						return '<button class="btn btn-warning btn-sm editRow" data-id=' + row.avaliacaoId + '>Edit</button>' +
						'<button class="btn btn-danger btn-sm deleteRow" data-id=' + row.avaliacaoId + '>Delete</button>';
					}
				}
			]
		});
		$('#addNew').on('click', function() {
			$('#formModal').modal('show');
		});
		$('#saveData').on('click', function() {
			var formData = $('#avaliacaoForm').serialize();
			$.ajax({
				url: '/avaliacao',
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