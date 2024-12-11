package controller;

import model.Cliente;
import model.Estabelecimento;
import model.LoginDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginDAO loginDAO = new LoginDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obter parâmetros do corpo da requisição
        String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        Gson gson = new Gson();
        JsonObject loginData = gson.fromJson(body, JsonObject.class);

        String email = loginData.get("email").getAsString();
        String senha = loginData.get("senha").getAsString();

        try {
            Object usuario = loginDAO.autenticar(email, senha);

            if (usuario != null) {
                HttpSession session = request.getSession();

                JsonObject jsonResponse = new JsonObject();

                if (usuario instanceof Cliente) {
                    Cliente cliente = (Cliente) usuario;
                    session.setAttribute("usuario", cliente);
                    session.setAttribute("tipoUsuario", "cliente");
                    session.setAttribute("clienteId", cliente.getClienteId()); // Adiciona o clienteId na sessão
                    jsonResponse.addProperty("tipoUsuario", "cliente");
                    jsonResponse.addProperty("clienteId", cliente.getClienteId()); // Inclui clienteId no JSON de resposta
                } else if (usuario instanceof Estabelecimento) {
                    Estabelecimento estabelecimento = (Estabelecimento) usuario;
                    session.setAttribute("usuario", estabelecimento);
                    session.setAttribute("tipoUsuario", "estabelecimento");
                    session.setAttribute("estabelecimentoId", estabelecimento.getEstabelecimentoId()); // Adicionando estabelecimentoId na sessão
                    jsonResponse.addProperty("tipoUsuario", "estabelecimento");
                    jsonResponse.addProperty("estabelecimentoId", estabelecimento.getEstabelecimentoId()); // Adicionando no JSON de resposta
                }

                response.getWriter().write(jsonResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401
                response.getWriter().write("{\"erro\": \"Email ou senha inválidos.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Código 500
            response.getWriter().write("{\"erro\": \"Erro ao processar o login.\"}");
        }
    }
}
