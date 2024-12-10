<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - BarberHub</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<!-- Barra de Navegação -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">BarberHub</a>
</nav>

<!-- Tela de Login -->
<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card" style="width: 100%; max-width: 400px;">
        <div class="card-header text-center">
            <h3>Login</h3>
        </div>
        <div class="card-body">
            
            <!-- Formulário de Login -->
            <form id="loginForm">
                <div id="erro" class="alert alert-danger d-none"></div> <!-- Mensagem de erro -->
                <div class="form-group mb-3">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Digite seu email" required>
                </div>
                <div class="form-group mb-3">
                    <label for="senha">Senha</label>
                    <input type="password" class="form-control" id="senha" name="senha" placeholder="Digite sua senha" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Entrar</button>
            </form>

            <div class="text-center mt-3">
                <p>Não tem uma conta? <a href="cadastroCliente.jsp">Cadastre-se</a></p>
            </div>
        </div>
    </div>
</div>

<!-- Rodapé -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>© 2024 BarberHub - Todos os direitos reservados.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function () {
        // Envio do formulário via AJAX
        $('#loginForm').submit(function (e) {
            e.preventDefault(); // Previne o comportamento padrão de envio do formulário

            // Dados do formulário
            var loginData = {
                email: $('#email').val(),
                senha: $('#senha').val()
            };

            console.log("Enviando dados de login:", loginData); // Log para debug

            // Enviar os dados via AJAX
            $.ajax({
                url: "/barberHub1/login", // URL do servlet de login
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(loginData), // Converte os dados para JSON
                success: function (response) {
                    console.log("Login bem-sucedido:", response); // Log para sucesso
                    // Redirecionar com base no tipo de usuário
                    if (response.tipoUsuario === "cliente") {
                        window.location.href = "home.jsp";
                    } else if (response.tipoUsuario === "estabelecimento") {
                        window.location.href = "homeBarbearia.jsp";
                    }
                },
                error: function (xhr) {
                    console.error("Erro no login:", xhr.responseText); // Log para erro
                    // Exibe mensagem de erro
                    $('#erro').removeClass('d-none').text("Email ou senha inválidos.");
                }
            });
        });
    });
</script>

</body>
</html>
