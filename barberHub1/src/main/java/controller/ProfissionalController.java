package controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import model.Profissional;
import model.ProfissionalDAO;

@WebServlet("/profissional")
public class ProfissionalController extends HttpServlet {

    private ProfissionalDAO dao = new ProfissionalDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String profissionalId = request.getParameter("profissionalId");

        try {
            if (profissionalId != null) {
                Profissional profissional = dao.findById(Integer.parseInt(profissionalId));
                if (profissional != null) {
                    String json = gson.toJson(profissional);
                    response.getWriter().write(json);
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Profissional não encontrado");
                    response.getWriter().write(gson.toJson(json));
                }
            } else {
                response.getWriter().write(gson.toJson(dao.findAll()));
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
            // Lê o corpo da requisição JSON e converte para um objeto Profissional
            Profissional profissional = gson.fromJson(request.getReader(), Profissional.class);

            // Validação simples do nome
            if (profissional.getNome() == null || profissional.getNome().isEmpty()) {
                JsonObject json = new JsonObject();
                json.addProperty("error", "Nome do profissional é obrigatório");
                response.getWriter().write(gson.toJson(json));
                return;
            }

            // Salva o profissional no banco de dados
            dao.save(profissional);

            // Retorna sucesso
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

        // Obtém o profissionalId da requisição
        String profissionalId = request.getParameter("profissionalId");

        try {
            // Verifica se o ID do profissional foi fornecido
            if (profissionalId != null) {

                // Verifica o tipo de conteúdo da requisição
                String contentType = request.getContentType();

                // Verifica se o conteúdo é JSON
                if (contentType != null && contentType.contains("application/json")) {

                    // Lê o corpo da requisição como JSON
                    BufferedReader reader = request.getReader();
                    StringBuilder jsonString = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonString.append(line);
                    }

                    // Converte o JSON para objeto Profissional
                    Profissional profissional = gson.fromJson(jsonString.toString(), Profissional.class);
                    
                    // Atualiza os dados do profissional no banco
                    updateProfissional(profissionalId, profissional, response);

                } else if (contentType != null && contentType.contains("application/x-www-form-urlencoded")) {

                    // Caso o conteúdo seja x-www-form-urlencoded
                    Profissional profissional = parseFormParameters(request);
                    
                    // Atualiza os dados do profissional no banco
                    updateProfissional(profissionalId, profissional, response);

                } else {
                    // Caso o tipo de conteúdo não seja suportado
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("error", "Formato de requisição não suportado");
                    response.getWriter().write(gson.toJson(jsonResponse));
                }
            } else {
                // Caso profissionalId não tenha sido enviado
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("error", "profissionalId é necessário");
                response.getWriter().write(gson.toJson(jsonResponse));
            }
        } catch (Exception e) {
            // Exceção geral
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    // Método para atualizar os dados do profissional no banco de dados
    private void updateProfissional(String profissionalId, Profissional profissional, HttpServletResponse response) throws IOException {
        // Busca o profissional no banco
        Profissional profissionalExistente = dao.findById(Integer.parseInt(profissionalId));
        if (profissionalExistente != null) {
            // Atualiza os dados do profissional
            profissionalExistente.setNome(profissional.getNome());
            profissionalExistente.setServico(profissional.getServico());
            profissionalExistente.setCep(profissional.getCep());
            profissionalExistente.setRua(profissional.getRua());
            profissionalExistente.setNumero(profissional.getNumero());
            profissionalExistente.setComplemento(profissional.getComplemento());
            profissionalExistente.setBairro(profissional.getBairro());
            profissionalExistente.setCidade(profissional.getCidade());
            profissionalExistente.setEstado(profissional.getEstado());
            profissionalExistente.setFoto(profissional.getFoto());

            // Salva no banco de dados
            dao.save(profissionalExistente);

            // Resposta de sucesso
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            response.getWriter().write(gson.toJson(jsonResponse));
        } else {
            // Profissional não encontrado
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "Profissional não encontrado");
            response.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    // Método para tratar os parâmetros de formulário x-www-form-urlencoded
    private Profissional parseFormParameters(HttpServletRequest request) {
        // Extrai os parâmetros do formulário
        String nome = request.getParameter("nome");
        String servico = request.getParameter("servico");
        String cep = request.getParameter("cep");
        String rua = request.getParameter("rua");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String foto = request.getParameter("foto");

        // Cria o objeto Profissional com os parâmetros do formulário
        Profissional profissional = new Profissional();
        profissional.setNome(nome);
        profissional.setServico(servico);
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


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            String profissionalId = request.getParameter("profissionalId");

            if (profissionalId != null) {
                Profissional profissionalExistente = dao.findById(Integer.parseInt(profissionalId));
                
                if (profissionalExistente != null) {
                    dao.delete(profissionalExistente);
                    
                    JsonObject json = new JsonObject();
                    json.addProperty("success", true);
                    response.getWriter().write(gson.toJson(json));
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Profissional não encontrado");
                    response.getWriter().write(gson.toJson(json));
                }
            } else {
                JsonObject json = new JsonObject();
                json.addProperty("error", "profissionalId é necessário");
                response.getWriter().write(gson.toJson(json));
            }
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(json));
        }
    }

}
