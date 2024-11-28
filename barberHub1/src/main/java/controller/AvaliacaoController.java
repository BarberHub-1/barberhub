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

import model.Avaliacao;
import model.AvaliacaoDAO;

@WebServlet("/avaliacao")
public class AvaliacaoController extends HttpServlet {

	private AvaliacaoDAO dao = new AvaliacaoDAO();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String avaliacaoId = request.getParameter("avaliacaoId");
		try {
			if (avaliacaoId != null) {
				Avaliacao avaliacao = dao.findById(Integer.parseInt(avaliacaoId));
				if (avaliacao != null) {
					String json = gson.toJson(avaliacao);
					response.getWriter().write(json);
				} else {
					JsonObject json = new JsonObject();
					json.addProperty("error", "Record not found");
					response.getWriter().write(gson.toJson(json));
				}
			} else {
				JsonArray jsonArray = gson.toJsonTree(dao.findAll()).getAsJsonArray();
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
			Avaliacao avaliacao = gson.fromJson(request.getReader(), Avaliacao.class);
			dao.save(avaliacao);
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
		String avaliacaoId = request.getParameter("avaliacaoId");
		try {
			if (avaliacaoId != null) {
				Avaliacao avaliacao = gson.fromJson(request.getReader(), Avaliacao.class);
				dao.save(avaliacao);
				JsonObject json = new JsonObject();
				json.addProperty("success", true);
				response.getWriter().write(gson.toJson(json));
			} else {
				JsonObject json = new JsonObject();
				json.addProperty("error", "avaliacaoId is required");
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
			Avaliacao avaliacao = gson.fromJson(request.getReader(), Avaliacao.class);
			dao.delete(avaliacao);
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
