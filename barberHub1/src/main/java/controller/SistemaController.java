package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.Sistema;
import model.SistemaDAO;

@WebServlet("/sistema")
public class SistemaController extends HttpServlet {

	private SistemaDAO dao = new SistemaDAO();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        // Valida se o parâmetro "sistemaId" existe e não está vazio
	        String sistemaIdParam = request.getParameter("sistemaId");
	        if (sistemaIdParam != null && !sistemaIdParam.isEmpty()) {
	            try {
	                int sistemaId = Integer.parseInt(sistemaIdParam);
	                Sistema sistema = dao.findById(sistemaId);

	                if (sistema != null) {
	                    request.setAttribute("sistema", sistema);
	                    // request.getRequestDispatcher("sistema.jsp").forward(request, response);
	                } else {
	                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sistema não encontrado");
	                }
	            } catch (NumberFormatException e) {
	                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do Sistema inválido");
	            }
	        } else {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro 'sistemaId' é obrigatório");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no banco de dados");
	    }
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		try {
			Sistema sistema = gson.fromJson(request.getReader(), Sistema.class);
			dao.save(sistema);
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
		String sistemaId = request.getParameter("sistemaId");
		try {
			if (sistemaId != null) {
				Sistema sistema = gson.fromJson(request.getReader(), Sistema.class);
				dao.save(sistema);
				JsonObject json = new JsonObject();
				json.addProperty("success", true);
				response.getWriter().write(gson.toJson(json));
			} else {
				JsonObject json = new JsonObject();
				json.addProperty("error", "sistemaId is required");
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
			Sistema sistema = gson.fromJson(request.getReader(), Sistema.class);
			dao.delete(sistema);
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
