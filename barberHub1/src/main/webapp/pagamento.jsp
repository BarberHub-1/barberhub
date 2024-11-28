<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gerenciamento - Pagamento</title>
	<link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<div class="container my-4">
	<h1>Pagamento Management</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Add New Pagamento</button>
	</div>
	<table id="dataTable" class="table table-striped">
		<thead>
			<tr>
				<th>Pagamentoid</th>
				<th>Agendamentoid</th>
				<th>Valor</th>
				<th>Metodo</th>
				<th>Status</th>
				<th>Actions</th>
			</tr>
		</thead>
	</table>
</div>
<div id="formModal" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Pagamento Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="pagamentoForm">
					<div class="mb-3">
						<label for="pagamentoId" class="form-label">Pagamentoid</label>
						<input type="text" id="pagamentoId" name="pagamentoId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="agendamentoId" class="form-label">Agendamentoid</label>
						<input type="text" id="agendamentoId" name="agendamentoId" class="form-control">
					</div>
					<div class="mb-3">
						<label for="valor" class="form-label">Valor</label>
						<input type="text" id="valor" name="valor" class="form-control">
					</div>
					<div class="mb-3">
						<label for="metodo" class="form-label">Metodo</label>
						<input type="text" id="metodo" name="metodo" class="form-control">
					</div>
					<div class="mb-3">
						<label for="status" class="form-label">Status</label>
						<input type="text" id="status" name="status" class="form-control">
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
				url: '/pagamento',
				dataSrc: ''
			},
			columns: [
				{ data: 'pagamentoId' },
				{ data: 'agendamentoId' },
				{ data: 'valor' },
				{ data: 'metodo' },
				{ data: 'status' },
				{
					data: null,
					render: function(data, type, row) {
						return '<button class="btn btn-warning btn-sm editRow" data-id=' + row.pagamentoId + '>Edit</button>' +
						'<button class="btn btn-danger btn-sm deleteRow" data-id=' + row.pagamentoId + '>Delete</button>';
					}
				}
			]
		});
		$('#addNew').on('click', function() {
			$('#formModal').modal('show');
		});
		$('#saveData').on('click', function() {
			var formData = $('#pagamentoForm').serialize();
			$.ajax({
				url: '/pagamento',
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