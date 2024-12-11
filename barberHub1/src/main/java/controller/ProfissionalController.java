package controller;


import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.Profissional;
import model.ProfissionalDAO;

@WebServlet("/profissional")
public class ProfissionalController extends HttpServlet {

    private ProfissionalDAO dao = new ProfissionalDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String estabelecimentoIdParam = request.getParameter("estabelecimentoId");
        String profissionalIdParam = request.getParameter("profissionalId");

        try {
            // Obtém a sessão do usuário
            HttpSession session = request.getSession(false);
            Integer sessionEstabelecimentoId = (session != null) ? (Integer) session.getAttribute("estabelecimentoId") : null;

            if (profissionalIdParam != null) {
                // Buscar dados de um profissional específico pelo ID
                int profissionalId = Integer.parseInt(profissionalIdParam);
                Profissional profissional = dao.findById(profissionalId);

                if (profissional != null) {
                    response.getWriter().write(gson.toJson(profissional));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonObject errorJson = new JsonObject();
                    errorJson.addProperty("error", "Profissional não encontrado.");
                    response.getWriter().write(gson.toJson(errorJson));
                }
            } else if (estabelecimentoIdParam != null) {
                // Buscar profissionais por estabelecimentoId enviado na URL
                int estabelecimentoId = Integer.parseInt(estabelecimentoIdParam);
                List<Profissional> profissionais = dao.findProfissionaisByEstabelecimentoId(estabelecimentoId);

                response.getWriter().write(gson.toJson(profissionais));
            } else if (sessionEstabelecimentoId != null) {
                // Buscar profissionais pelo estabelecimentoId da sessão
                List<Profissional> profissionais = dao.findProfissionaisByEstabelecimentoId(sessionEstabelecimentoId);

                if (profissionais.isEmpty()) {
                    JsonObject errorJson = new JsonObject();
                    errorJson.addProperty("error", "Nenhum profissional encontrado para este estabelecimento.");
                    response.getWriter().write(gson.toJson(errorJson));
                } else {
                    response.getWriter().write(gson.toJson(profissionais));
                }
            } else {
                // Caso nenhuma condição seja atendida, retornar todos os profissionais
                List<Profissional> todosProfissionais = dao.findAll();
                response.getWriter().write(gson.toJson(todosProfissionais));
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("error", "Parâmetro inválido.");
            response.getWriter().write(gson.toJson(errorJson));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(errorJson));
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            Profissional profissional = parseFormParameters(request);

            
            String estabelecimentoIdParam = request.getParameter("estabelecimentoId");
            if (estabelecimentoIdParam == null || estabelecimentoIdParam.isEmpty()) {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("error", "EstabelecimentoID é obrigatório.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            int estabelecimentoId = Integer.parseInt(estabelecimentoIdParam);
            profissional.getEstabelecimento().setEstabelecimentoId(estabelecimentoId);

            
            dao.save(profissional);
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "Erro ao salvar profissional: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            Profissional profissional = gson.fromJson(request.getReader(), Profissional.class);

            if (profissional.getEstabelecimento() == null || profissional.getEstabelecimento().getEstabelecimentoId() <= 0) {
                throw new IllegalArgumentException("EstabelecimentoId é obrigatório.");
            }

            int updated = dao.update(profissional);
            if (updated > 0) {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("success", true);
                response.getWriter().write(gson.toJson(jsonResponse));
            } else {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("error", "Erro ao atualizar o profissional.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(gson.toJson(jsonResponse));
            }
        } catch (Exception e) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "Erro ao atualizar profissional: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(jsonResponse));
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            String profissionalId = request.getParameter("profissionalId");
            if (profissionalId == null || profissionalId.isEmpty()) {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("error", "ProfissionalID é obrigatório.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            
            Profissional profissionalExistente = dao.findById(Integer.parseInt(profissionalId));
            if (profissionalExistente != null) {
                dao.delete(profissionalExistente);
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("success", true);
                response.getWriter().write(gson.toJson(jsonResponse));
            } else {
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("error", "Profissional não encontrado.");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(gson.toJson(jsonResponse));
            }
        } catch (Exception e) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "Erro ao excluir profissional: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    
    private Profissional parseFormParameters(HttpServletRequest request) {
        String nome = request.getParameter("nome");
        String cep = request.getParameter("cep");
        String rua = request.getParameter("rua");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String foto = request.getParameter("foto");

        Profissional profissional = new Profissional();
        profissional.setNome(nome);
        profissional.setCep(cep);
        profissional.setRua(rua);
        profissional.setNumero(numero);
        profissional.setComplemento(complemento);
        profissional.setBairro(bairro);
        profissional.setCidade(cidade);
        profissional.setEstado(estado);
        profissional.setFoto(foto);

        return profissional;
    }
}
