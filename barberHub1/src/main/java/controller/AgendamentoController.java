package controller;

import model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/agendamento")
public class AgendamentoController extends HttpServlet {

    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO();
    private ProfissionalDAO profissionalDAO = new ProfissionalDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Recuperar a sessão existente
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("clienteId") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Usuário não autenticado.\"}");
                return;
            }

            // Obter o clienteId da sessão
            int clienteId = (int) session.getAttribute("clienteId");

            // Capturar os dados do payload
            JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);

            // Validação dos campos obrigatórios
            if (!jsonObject.has("estabelecimentoId") || !jsonObject.has("profissionalId") || 
                !jsonObject.has("servicoId") || !jsonObject.has("data") || 
                !jsonObject.has("hora") || !jsonObject.has("preco")) {
                throw new IllegalArgumentException("Campos obrigatórios estão faltando ou nulos.");
            }

            // Obter e validar os dados
            int estabelecimentoId = jsonObject.get("estabelecimentoId").getAsInt();
            int profissionalId = jsonObject.get("profissionalId").getAsInt();
            int servicoId = jsonObject.get("servicoId").getAsInt();
            String data = jsonObject.get("data").getAsString();
            String hora = jsonObject.get("hora").getAsString();
            double preco = jsonObject.get("preco").getAsDouble();

            // Validar formato de data e hora
            if (!data.matches("\\d{4}-\\d{2}-\\d{2}")) {  // Formato YYYY-MM-DD
                throw new IllegalArgumentException("Formato de data inválido.");
            }
            if (!hora.matches("\\d{2}:\\d{2}")) {  // Formato HH:MM
                throw new IllegalArgumentException("Formato de hora inválido.");
            }

            // Obter objetos relacionados do banco de dados
            Estabelecimento estabelecimento = estabelecimentoDAO.findById(estabelecimentoId);
            if (estabelecimento == null) throw new IllegalArgumentException("Estabelecimento inválido.");

            Profissional profissional = profissionalDAO.findById(profissionalId);
            if (profissional == null) throw new IllegalArgumentException("Profissional inválido.");

            Servico servico = servicoDAO.findById(servicoId);
            if (servico == null) throw new IllegalArgumentException("Serviço inválido.");

            Cliente cliente = clienteDAO.findById(clienteId);
            if (cliente == null) throw new IllegalArgumentException("Cliente inválido.");

            // Criar o objeto Agendamento
            Agendamento agendamento = new Agendamento(estabelecimento, profissional, servico, cliente, data, hora, preco, "Agendada");

            // Salvar no banco de dados
            int savedId = agendamentoDAO.save(agendamento);

            // Retornar o ID gerado no JSON de resposta
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("success", true);
            responseJson.addProperty("id", savedId);
            response.getWriter().write(gson.toJson(responseJson));

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(errorJson));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao salvar o agendamento.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String agendamentoId = request.getParameter("agendamentoId");

        try {
            if (agendamentoId != null) {
                Agendamento agendamento = agendamentoDAO.findById(Integer.parseInt(agendamentoId));
                if (agendamento != null) {
                    response.getWriter().write(gson.toJson(agendamento));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Agendamento não encontrado.\"}");
                }
            } else {
                response.getWriter().write(gson.toJson(agendamentoDAO.findAll()));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("error", "Erro ao buscar agendamentos.");
            response.getWriter().write(gson.toJson(errorJson));
        }
    }
}
