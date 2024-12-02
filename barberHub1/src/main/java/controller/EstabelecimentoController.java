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
import model.Profissional;
import model.Servico;

@WebServlet("/estabelecimento")
public class EstabelecimentoController extends HttpServlet {

    private EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO(); 
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        
        try {
            String estabelecimentoId = request.getParameter("id"); 
            
            if (estabelecimentoId != null) {
                int id = Integer.parseInt(estabelecimentoId);
                Estabelecimento estabelecimento = estabelecimentoDAO.findById(id);
                
                if (estabelecimento != null) {
                    JsonObject estabelecimentoJson = new JsonObject();
                    estabelecimentoJson.addProperty("id", estabelecimento.getEstabelecimentoid());
                    estabelecimentoJson.addProperty("nome", estabelecimento.getNome());
                    estabelecimentoJson.addProperty("email", estabelecimento.getEmail());
                    estabelecimentoJson.addProperty("cidade", estabelecimento.getCidade());
                    estabelecimentoJson.addProperty("telefone", estabelecimento.getTelefone());

                    
                    JsonArray profissionaisArray = new JsonArray();
                    for (Profissional profissional : estabelecimento.getProfissionais()) {
                        JsonObject profissionalJson = new JsonObject();
                        profissionalJson.addProperty("id", profissional.getProfissionalId());
                        profissionalJson.addProperty("nome", profissional.getNome());

                       
                        JsonArray servicosArray = new JsonArray();
                        for (Servico servico : profissional.getServicos()) {
                            JsonObject servicoJson = new JsonObject();
                            servicoJson.addProperty("id", servico.getServicoId());
                            servicoJson.addProperty("nome", servico.getNome());
                            servicoJson.addProperty("descricao", servico.getDescricao());
                            servicoJson.addProperty("preco", servico.getPreco());
                            servicoJson.addProperty("duracao", servico.getDuracao());
                            servicosArray.add(servicoJson);
                        }
                        profissionalJson.add("servicos", servicosArray);
                        profissionaisArray.add(profissionalJson);
                    }

                    estabelecimentoJson.add("profissionais", profissionaisArray);

                    response.getWriter().write(gson.toJson(estabelecimentoJson));
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Estabelecimento not found");
                    response.getWriter().write(gson.toJson(json));
                }
            } else {
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

                response.getWriter().write(gson.toJson(jsonArray));
            }
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(json));
        }
    }

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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String estabelecimentoId = request.getParameter("id"); 
        
        try {
            if (estabelecimentoId != null) {
                Estabelecimento estabelecimento = gson.fromJson(request.getReader(), Estabelecimento.class);
                estabelecimento.setEstabelecimentoid(Integer.parseInt(estabelecimentoId)); 
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
