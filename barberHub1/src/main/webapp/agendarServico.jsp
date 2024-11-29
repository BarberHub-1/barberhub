<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.sql.*, java.util.*" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agendar - Barbearias Marketplace</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>

<!-- Barra de Navegação -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="home.jsp">BarberHub</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Alternar navegação">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="home.jsp">Início</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="barbearias.jsp">Barbearias</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="promocoes.jsp">Promoções</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Meus Agendamentos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="login.jsp">Login</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container my-5">
    <h2 class="text-center mb-4">Agendar Serviço</h2>

    <form action="confirmarAgendamento.jsp" method="post">
        <%
            // Obter o ID do estabelecimento da URL
            String estabelecimentoId = request.getParameter("id");

            // Conectar ao banco de dados
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                // Carregar o driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seu_banco_de_dados", "usuario", "senha");

                // Consultar os serviços disponíveis para os profissionais do estabelecimento
                String query = "SELECT s.servicoId, s.nome " +
                               "FROM servico s " +
                               "JOIN profissionalservico ps ON s.servicoId = ps.servicoId " +
                               "JOIN profissional p ON ps.profissionalId = p.profissionalId " +
                               "WHERE p.estabelecimentoId = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(estabelecimentoId));

                rs = stmt.executeQuery();

                // Armazenar os serviços para exibição no formulário
                List<String> servicos = new ArrayList<>();
                while (rs.next()) {
                    servicos.add(rs.getString("nome"));
                }
        %>

        <div class="mb-3">
            <label for="servico" class="form-label">Escolha o Serviço</label>
            <select class="form-select" id="servico" name="servico" required>
                <% for (String servico : servicos) { %>
                    <option value="<%= servico %>"><%= servico %></option>
                <% } %>
            </select>
        </div>

        <% 
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Fechar recursos
                if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        %>

        <div class="mb-3">
            <label for="dataHora" class="form-label">Escolha a Data e Hora</label>
            <input type="datetime-local" class="form-control" id="dataHora" name="dataHora" required />
        </div>

        <div class="mb-3">
            <label for="nome" class="form-label">Seu Nome</label>
            <input type="text" class="form-control" id="nome" name="nome" required />
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Seu E-mail</label>
            <input type="email" class="form-control" id="email" name="email" required />
        </div>

        <div class="mb-3">
            <label for="telefone" class="form-label">Seu Telefone</label>
            <input type="tel" class="form-control" id="telefone" name="telefone" required />
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-primary">Confirmar Agendamento</button>
        </div>
    </form>
</div>

<!-- Rodapé -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p>© 2024 Marketplace de Barbearias - Todos os direitos reservados.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

</body>
</html>
