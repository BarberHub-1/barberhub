package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.Servico;
import model.ServicoDAO;

@WebServlet("/servico")
public class ServicoController extends HttpServlet {

    private ServicoDAO dao = new ServicoDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String profissionalId = request.getParameter("profissionalId");
        String servicoId = request.getParameter("servicoId");

        try {
            if (profissionalId != null) {
                // Buscar serviços pelo profissional
                int id = Integer.parseInt(profissionalId);
                List<Servico> servicos = dao.findByProfissionalId(id);

                if (!servicos.isEmpty()) {
                    JsonArray jsonArray = gson.toJsonTree(servicos).getAsJsonArray();
                    response.getWriter().write(gson.toJson(jsonArray));
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Nenhum serviço encontrado para este profissional.");
                    response.getWriter().write(gson.toJson(json));
                }
            } else if (servicoId != null) {
                Servico servico = dao.findById(Integer.parseInt(servicoId));
                if (servico != null) {
                    response.getWriter().write(gson.toJson(servico));
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Serviço não encontrado.");
                    response.getWriter().write(gson.toJson(json));
                }
            } else {
                List<Servico> servicos = dao.findAll();
                JsonArray jsonArray = gson.toJsonTree(servicos).getAsJsonArray();
                response.getWriter().write(gson.toJson(jsonArray));
            }
        } catch (NumberFormatException e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", "ID inválido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", "Erro ao processar a solicitação: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(json));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Servico servico = gson.fromJson(request.getReader(), Servico.class);
            dao.save(servico);
            JsonObject json = new JsonObject();
            json.addProperty("success", true);
            response.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", "Erro ao salvar o serviço: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(json));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String servicoId = request.getParameter("servicoId");

        try {
            if (servicoId != null) {
                Servico servico = gson.fromJson(request.getReader(), Servico.class);
                Servico servicoExistente = dao.findById(Integer.parseInt(servicoId));

                if (servicoExistente != null) {
                    servicoExistente.setNome(servico.getNome());
                    servicoExistente.setDescricao(servico.getDescricao());
                    servicoExistente.setTipoServicoId(servico.getTipoServicoId());
                    servicoExistente.setPreco(servico.getPreco());
                    dao.update(servicoExistente);

                    JsonObject json = new JsonObject();
                    json.addProperty("success", true);
                    response.getWriter().write(gson.toJson(json));
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Serviço não encontrado.");
                    response.getWriter().write(gson.toJson(json));
                }
            } else {
                JsonObject json = new JsonObject();
                json.addProperty("error", "ID do serviço é necessário.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(json));
            }
        } catch (NumberFormatException e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", "ID inválido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", "Erro ao atualizar o serviço: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(json));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Servico servico = gson.fromJson(request.getReader(), Servico.class);
            dao.delete(servico);
            JsonObject json = new JsonObject();
            json.addProperty("success", true);
            response.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", "Erro ao deletar o serviço: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(json));
        }
    }
}
