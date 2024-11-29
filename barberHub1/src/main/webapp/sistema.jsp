<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gerenciamento - Sistema</title>
	<link href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<div class="container my-4">
	<h1>Gerenciamento - Sistema</h1>
	<div class="mb-3">
		<button class="btn btn-primary" id="addNew">Add New Sistema</button>
	</div>
	<table id="dataTable" class="table table-striped">
		<thead>
			<tr>
				<th>Sistema ID</th>
				<th>Nome</th>
				<th>Logo</th>
				<th>Email</th>
				<th>Telefone</th>
				<th>Actions</th>
			</tr>
		</thead>
	</table>
</div>
<div id="formModal" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Sistema Form</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="sistemaForm">
					<input type="hidden" id="sistemaId" name="sistemaId">
					<div class="mb-3">
						<label for="nome" class="form-label">Nome</label>
						<input type="text" id="nome" name="nome" class="form-control">
					</div>
					<div class="mb-3">
						<label for="logo" class="form-label">Logo</label>
						<input type="text" id="logo" name="logo" class="form-control">
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email</label>
						<input type="text" id="email" name="email" class="form-control">
					</div>
					<div class="mb-3">
						<label for="telefone" class="form-label">Telefone</label>
						<input type="text" id="telefone" name="telefone" class="form-control">
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
				url: '/barberHub1/sistema',
				dataSrc: ''
			},
			columns: [
				{ data: 'sistemaId' },
				{ data: 'nome' },
				{ data: 'logo' },
				{ data: 'email' },
				{ data: 'telefone' },
				{
					data: null,
					render: function(data, type, row) {
						return '<button class="btn btn-warning btn-sm editRow" data-id='+ row.sistemaId +'>Edit</button>' +
						'<button class="btn btn-danger btn-sm deleteRow" data-id=' + row.sistemaId +'>Delete</button>';
					}
				}
			]
		});

		$('#addNew').on('click', function() {
			$('#sistemaForm')[0].reset();
			$('#sistemaId').val('');
			$('#formModal').modal('show');
		});

		$('#saveData').on('click', function() {
		    var formData = {
		        nome: $('#nome').val(),
		        logo: $('#logo').val(),
		        email: $('#email').val(),
		        telefone: $('#telefone').val()
		    };

		    if ($('#sistemaId').val()) {
		        formData.sistemaId = $('#sistemaId').val();
		    }

		    $.ajax({
		        url: '/barberHub1/sistema',
		        method: formData.sistemaId ? 'PUT' : 'POST',
		        contentType: 'application/json',
		        data: JSON.stringify(formData),
		        success: function(response) {
		            table.ajax.reload();
		            $('#formModal').modal('hide');
		        },
		        error: function(xhr) {
		            alert('Error: ' + xhr.responseText);
		        }
		    });
		});

		
		$('#dataTable').on('click', '.editRow', function() {
		    var sistemaId = $(this).data('id');

		    $.ajax({
		        url: '/barberHub1/sistema?sistemaId=' + sistemaId,
		        method: 'GET',
		        success: function(data) {
		            $('#sistemaId').val(data.sistemaId);
		            $('#nome').val(data.nome);
		            $('#logo').val(data.logo);
		            $('#email').val(data.email);
		            $('#telefone').val(data.telefone);
		            $('#formModal').modal('show');
		        },
		        error: function(xhr) {
		            alert('Error: ' + xhr.responseText);
		        }
		    });
		});

		
		$('#dataTable').on('click', '.deleteRow', function() {
		    var sistemaId = $(this).data('id');

		    if (confirm('Tem certeza que deseja excluir?')) {
		        $.ajax({
		            url: '/barberHub1/sistema?sistemaId=' + sistemaId,
		            method: 'DELETE',
		            success: function(response) {
		                table.ajax.reload();
		            },
		            error: function(xhr) {
		                alert('Error: ' + xhr.responseText);
		            }
		        });
		    }
		});
	});
</script>
</body>
</html>
