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

import model.ProfissionalServico;
import model.ProfissionalServicoDAO;

@WebServlet("/profissionalservico")
public class ProfissionalServicoController extends HttpServlet {

	private ProfissionalServicoDAO dao = new ProfissionalServicoDAO();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String profissionalId = request.getParameter("profissionalId");
		try {
			if (profissionalId != null) {
				ProfissionalServico profissionalServico = dao.findById(Integer.parseInt(profissionalId));
				if (profissionalServico != null) {
					String json = gson.toJson(profissionalServico);
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
			ProfissionalServico profissionalServico = gson.fromJson(request.getReader(), ProfissionalServico.class);
			dao.save(profissionalServico);
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
		String profissionalId = request.getParameter("profissionalId");
		try {
			if (profissionalId != null) {
				ProfissionalServico profissionalServico = gson.fromJson(request.getReader(), ProfissionalServico.class);
				dao.save(profissionalServico);
				JsonObject json = new JsonObject();
				json.addProperty("success", true);
				response.getWriter().write(gson.toJson(json));
			} else {
				JsonObject json = new JsonObject();
				json.addProperty("error", "profissionalId is required");
				response.getWriter().write(gson.toJson(json));
			}
		} catch (Exception e) {
			JsonObject json = new JsonObject();
			json.addProperty("error", e.getMessage());
			response.getWriter().write(gson.toJson(json));
		}
	}

	/*
	 * @Override protected void doDelete(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * response.setContentType("application/json"); try { ProfissionalServico
	 * profissionalServico = gson.fromJson(request.getReader(),
	 * ProfissionalServico.class); dao.delete(profissionalServico); JsonObject json
	 * = new JsonObject(); json.addProperty("success", true);
	 * response.getWriter().write(gson.toJson(json)); } catch (Exception e) {
	 * JsonObject json = new JsonObject(); json.addProperty("error",
	 * e.getMessage()); response.getWriter().write(gson.toJson(json)); } }
	 */

}
