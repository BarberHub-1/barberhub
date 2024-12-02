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

import model.Agendamento;
import model.AgendamentoDAO;

@WebServlet("/agendamento")
public class AgendamentoController extends HttpServlet {

	private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String agendamentoId = request.getParameter("agendamentoId");
		try {
			if (agendamentoId != null) {
				Agendamento agendamento = agendamentoDAO.findById(Integer.parseInt(agendamentoId));
				if (agendamento != null) {
					String json = gson.toJson(agendamento);
					response.getWriter().write(json);
				} else {
					JsonObject json = new JsonObject();
					json.addProperty("error", "Record not found");
					response.getWriter().write(gson.toJson(json));
				}
			} else {
				JsonArray jsonArray = gson.toJsonTree(agendamentoDAO.findAll()).getAsJsonArray();
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
	        // Obter os dados enviados no corpo da requisição
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = request.getReader().readLine()) != null) {
	            sb.append(line);
	        }

	        String jsonData = sb.toString();
	        if (jsonData.isEmpty()) {
	            throw new IllegalArgumentException("Nenhum dado enviado na solicitação.");
	        }

	        
	        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);

	        
	        if (!jsonObject.has("estabelecimentoId") || jsonObject.get("estabelecimentoId").isJsonNull() ||
	            !jsonObject.has("profissionalId") || jsonObject.get("profissionalId").isJsonNull() ||
	            !jsonObject.has("servicoId") || jsonObject.get("servicoId").isJsonNull() ||
	            !jsonObject.has("data") || jsonObject.get("data").isJsonNull() ||
	            !jsonObject.has("hora") || jsonObject.get("hora").isJsonNull() ||
	            !jsonObject.has("preco") || jsonObject.get("preco").isJsonNull()) {
	            throw new IllegalArgumentException("Campos obrigatórios estão faltando ou nulos.");
	        }

	       
	        int estabelecimentoId = jsonObject.get("estabelecimentoId").getAsInt();
	        int profissionalId = jsonObject.get("profissionalId").getAsInt();
	        int servicoId = jsonObject.get("servicoId").getAsInt();
	        String data = jsonObject.get("data").getAsString();
	        String hora = jsonObject.get("hora").getAsString();
	        double preco = jsonObject.get("preco").getAsDouble();

	       
	        Agendamento agendamento = new Agendamento(estabelecimentoId, profissionalId, servicoId, data, hora, preco, "Agendada");

	        
	        int savedId = agendamentoDAO.save(agendamento);

	       
	        JsonObject responseJson = new JsonObject();
	        responseJson.addProperty("success", true);
	        responseJson.addProperty("id", savedId);
	        response.getWriter().write(gson.toJson(responseJson));

	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        JsonObject errorJson = new JsonObject();
	        errorJson.addProperty("error", e.getMessage());
	        response.getWriter().write(gson.toJson(errorJson));
	        e.printStackTrace();
	    }
	}


	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String agendamentoId = request.getParameter("agendamentoId");
		try {
			if (agendamentoId != null) {
				Agendamento agendamento = gson.fromJson(request.getReader(), Agendamento.class);
				agendamentoDAO.save(agendamento);
				JsonObject json = new JsonObject();
				json.addProperty("success", true);
				response.getWriter().write(gson.toJson(json));
			} else {
				JsonObject json = new JsonObject();
				json.addProperty("error", "agendamentoId is required");
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
			Agendamento agendamento = gson.fromJson(request.getReader(), Agendamento.class);
			agendamentoDAO.delete(agendamento);
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
