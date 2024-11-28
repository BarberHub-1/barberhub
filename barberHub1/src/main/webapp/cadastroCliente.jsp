<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - BarberHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<!-- Barra de Navegação -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">BarberHub</a>
</nav>


<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card" style="width: 100%; max-width: 400px;">
        <div class="card-header text-center">
            <h3>Cadastro</h3>
        </div>
        <div class="card-body">
            <form action="/cadastro" method="post">
              
                <div class="form-group mb-3">
                    <label for="nome">Nome Completo</label>
                    <input type="text" class="form-control" id="nome" name="nome" placeholder="Digite seu nome completo" required>
                </div>

              
                <div class="form-group mb-3">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Digite seu email" required>
                </div>

            
                <div class="form-group mb-3">
                    <label for="senha">Senha</label>
                    <input type="password" class="form-control" id="senha" name="senha" placeholder="Digite sua senha" required>
                </div>

             
                <div class="form-group mb-3">
                    <label for="confirmarSenha">Confirmar Senha</label>
                    <input type="password" class="form-control" id="confirmarSenha" name="confirmarSenha" placeholder="Confirme sua senha" required>
                </div>

                
                <div class="form-group mb-3">
                    <label for="telefone">Telefone</label>
                    <input type="text" class="form-control" id="telefone" name="telefone" placeholder="Digite seu telefone" required>
                </div>

               
                <button type="submit" class="btn btn-primary w-100">Cadastrar</button>
            </form>

           
            <div class="text-center mt-3">
                <p>Já tem uma conta? <a href="login.jsp">Faça login</a></p>
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

</body>
</html>
