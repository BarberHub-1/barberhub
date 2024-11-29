package controller;

import java.io.BufferedReader;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
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

        Enumeration<String> parameterNames = request.getParameterNames();
        System.out.println("Dados recebidos no POST:");

        // Loop para imprimir os parâmetros recebidos
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            
            // Decodifica o valor do parâmetro, se necessário
            if (paramValue != null) {
                paramValue = URLDecoder.decode(paramValue, StandardCharsets.UTF_8);
            }
            
            System.out.println(paramName + ": " + paramValue);  
        }

       
        String servico = request.getParameter("servico");
        if (servico != null) {
            servico = URLDecoder.decode(servico, StandardCharsets.UTF_8);
        }

        System.out.println("Serviço: " + servico);

        
        String estabelecimentoIdParam = request.getParameter("estabelecimentoId");
        if (estabelecimentoIdParam == null || estabelecimentoIdParam.isEmpty()) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "EstabelecimentoID é obrigatório.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(jsonResponse));
            return;  
        }

        int estabelecimentoId;
        try {
            estabelecimentoId = Integer.parseInt(estabelecimentoIdParam);
            if (estabelecimentoId <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "EstabelecimentoID deve ser maior que 0.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(jsonResponse));
            return;  
        }

        
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
        profissional.setServico(servico);
        profissional.setCep(cep);
        profissional.setRua(rua);
        profissional.setNumero(numero);
        profissional.setComplemento(complemento);
        profissional.setBairro(bairro);
        profissional.setCidade(cidade);
        profissional.setEstado(estado);
        profissional.setFoto(foto);
        profissional.setEstabelecimentoid(estabelecimentoId);

       
        try {
            dao.save(profissional);
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(jsonResponse));
        }
    }

   






    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Captura os parâmetros da requisição PUT
        Enumeration<String> parameterNames = request.getParameterNames();
        System.out.println("Dados recebidos no PUT:");

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            
            // Decodifica o valor do parâmetro, se necessário
            if (paramValue != null) {
                paramValue = URLDecoder.decode(paramValue, StandardCharsets.UTF_8);
            }
            
            System.out.println(paramName + ": " + paramValue);  
        }

        
        String estabelecimentoIdParam = request.getParameter("estabelecimentoId");
        if (estabelecimentoIdParam == null || estabelecimentoIdParam.isEmpty()) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "EstabelecimentoID é obrigatório.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(jsonResponse));
            return;
        }

        int estabelecimentoId;
        try {
            estabelecimentoId = Integer.parseInt(estabelecimentoIdParam);
            if (estabelecimentoId <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("error", "EstabelecimentoID deve ser maior que 0.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(jsonResponse));
            return;
        }

        
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
        profissional.setEstabelecimentoid(estabelecimentoId);

        
        try {
            int updated = dao.update(profissional);
            
            if (updated == 1) {
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
            jsonResponse.addProperty("error", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(jsonResponse));
        }
    }


    
    private void updateProfissional(String profissionalId, Profissional profissional, HttpServletResponse response) throws IOException {
        
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
