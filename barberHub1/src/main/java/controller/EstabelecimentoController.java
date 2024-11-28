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

import model.Estabelecimento;
import model.EstabelecimentoDAO;

@WebServlet("/estabelecimento")
public class EstabelecimentoController extends HttpServlet {

    private EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO(); // Acesso ao DAO
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Método GET para retornar todos os estabelecimentos ou um específico por ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        
        try {
            String estabelecimentoId = request.getParameter("id"); // Captura o ID da URL (parâmetro de consulta)
            
            if (estabelecimentoId != null) {
                // Se o ID for fornecido, busca o estabelecimento específico
                int id = Integer.parseInt(estabelecimentoId);
                Estabelecimento estabelecimento = estabelecimentoDAO.findById1(id);
                
                if (estabelecimento != null) {
                    JsonObject estabelecimentoJson = new JsonObject();
                    estabelecimentoJson.addProperty("id", estabelecimento.getEstabelecimentoid());
                    estabelecimentoJson.addProperty("nome", estabelecimento.getNome());
                    estabelecimentoJson.addProperty("email", estabelecimento.getEmail());
                    estabelecimentoJson.addProperty("cidade", estabelecimento.getCidade());
                    estabelecimentoJson.addProperty("telefone", estabelecimento.getTelefone());
                    estabelecimentoJson.addProperty("profissionais", estabelecimento.getProfissionalNome());
                    estabelecimentoJson.addProperty("servicos", estabelecimento.getProfissionalServico());
                    
                    // Retorna o estabelecimento encontrado
                    response.getWriter().write(gson.toJson(estabelecimentoJson));
                } else {
                    // Caso não encontre o estabelecimento
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Estabelecimento not found");
                    response.getWriter().write(gson.toJson(json));
                }
            } else {
                // Se não for passado o ID, retorna todos os estabelecimentos
                List<Estabelecimento> estabelecimentos = estabelecimentoDAO.findAll();
                JsonArray jsonArray = new JsonArray();

                for (Estabelecimento estabelecimento : estabelecimentos) {
                    JsonObject estabelecimentoJson = new JsonObject();
                    estabelecimentoJson.addProperty("id", estabelecimento.getEstabelecimentoid());
                    estabelecimentoJson.addProperty("nome", estabelecimento.getNome());
                    estabelecimentoJson.addProperty("email", estabelecimento.getEmail());
                    estabelecimentoJson.addProperty("cidade", estabelecimento.getCidade());
                    estabelecimentoJson.addProperty("telefone", estabelecimento.getTelefone());
                    
                    jsonArray.add(estabelecimentoJson);
                }

                // Retorna todos os estabelecimentos em formato JSON
                response.getWriter().write(gson.toJson(jsonArray));
            }
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(json));
        }
    }

    // Método POST para criar um novo estabelecimento
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Estabelecimento estabelecimento = gson.fromJson(request.getReader(), Estabelecimento.class);
            estabelecimentoDAO.save(estabelecimento);
            JsonObject json = new JsonObject();
            json.addProperty("success", true);
            response.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(json));
        }
    }

    // Método PUT para atualizar um estabelecimento
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String estabelecimentoId = request.getParameter("id"); // Captura o parâmetro ID da URL
        
        try {
            if (estabelecimentoId != null) {
                Estabelecimento estabelecimento = gson.fromJson(request.getReader(), Estabelecimento.class);
                estabelecimento.setEstabelecimentoid(Integer.parseInt(estabelecimentoId)); // Atualiza o ID
                estabelecimentoDAO.save(estabelecimento);
                JsonObject json = new JsonObject();
                json.addProperty("success", true);
                response.getWriter().write(gson.toJson(json));
            } else {
                JsonObject json = new JsonObject();
                json.addProperty("error", "ID is required");
                response.getWriter().write(gson.toJson(json));
            }
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(json));
        }
    }

    // Método DELETE para excluir um estabelecimento
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Estabelecimento estabelecimento = gson.fromJson(request.getReader(), Estabelecimento.class);
            estabelecimentoDAO.delete(estabelecimento);
            JsonObject json = new JsonObject();
            json.addProperty("success", true);
            response.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(json));
        }
    }
}
