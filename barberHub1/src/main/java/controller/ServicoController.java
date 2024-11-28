package controller;

import java.io.IOException;
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
		String servicoId = request.getParameter("servicoId");
		try {
			if (servicoId != null) {
				Servico servico = dao.findByIdWithJoin(Integer.parseInt(servicoId));
				if (servico != null) {
					String json = gson.toJson(servico);
					response.getWriter().write(json);
				} else {
					JsonObject json = new JsonObject();
					json.addProperty("error", "Record not found");
					response.getWriter().write(gson.toJson(json));
				}
			} else {
				JsonArray jsonArray = gson.toJsonTree(dao.findAllWithJoin()).getAsJsonArray();
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
			Servico servico = gson.fromJson(request.getReader(), Servico.class);
			dao.save(servico);
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
	    String servicoId = request.getParameter("servicoId");
	    try {
	        if (servicoId != null) {
	            // Converte o corpo da requisição para um objeto Servico
	            Servico servico = gson.fromJson(request.getReader(), Servico.class);
	            
	            // Encontre o serviço pelo ID
	            Servico servicoExistente = dao.findById(Integer.parseInt(servicoId));
	            if (servicoExistente != null) {
	                // Atualize os dados do serviço existente
	                servicoExistente.setNome(servico.getNome());
	                servicoExistente.setDescricao(servico.getDescricao());
	                servicoExistente.setTipoServicoId(servico.getTipoServicoId());
	                servicoExistente.setPreco(servico.getPreco());
	                
	                // Salve as alterações no banco de dados
	                dao.update(servicoExistente);

	                JsonObject json = new JsonObject();
	                json.addProperty("success", true);
	                response.getWriter().write(gson.toJson(json));
	            } else {
	                // Se o serviço não for encontrado, retorna um erro
	                JsonObject json = new JsonObject();
	                json.addProperty("error", "Serviço não encontrado");
	                response.getWriter().write(gson.toJson(json));
	            }
	        } else {
	            JsonObject json = new JsonObject();
	            json.addProperty("error", "servicoId é necessário");
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
			Servico servico = gson.fromJson(request.getReader(), Servico.class);
			dao.delete(servico);
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
