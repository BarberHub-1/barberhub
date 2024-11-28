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

import model.Profissional;
import model.TipoServico;

import model.TiposervicoDAO;

@WebServlet("/tipoServico")
public class TipoServicoController extends HttpServlet {

	private TiposervicoDAO dao = new TiposervicoDAO();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String tipoServicoId = request.getParameter("tipoServicoId");
		try {
			if (tipoServicoId != null) {
				TipoServico tipoServico = dao.findById(Integer.parseInt(tipoServicoId));
				if (tipoServico != null) {
					String json = gson.toJson(tipoServico);
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
			TipoServico tipoServico = gson.fromJson(request.getReader(), TipoServico.class);
			dao.save(tipoServico);
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
		String tipoServicoId = request.getParameter("tipoServicoId");
		try {
			if (tipoServicoId != null) {
				TipoServico tipoServico = gson.fromJson(request.getReader(), TipoServico.class);
				dao.save(tipoServico);
				JsonObject json = new JsonObject();
				json.addProperty("success", true);
				response.getWriter().write(gson.toJson(json));
			} else {
				JsonObject json = new JsonObject();
				json.addProperty("error", "tipoServicoId is required");
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
        	String tipoServicoId = request.getParameter("tipoServicoId");

            if (tipoServicoId != null) {
                TipoServico tipoServicoExistente = dao.findById(Integer.parseInt(tipoServicoId));
                
                if (tipoServicoExistente != null) {
                    dao.delete(tipoServicoExistente);
                    
                    JsonObject json = new JsonObject();
                    json.addProperty("success", true);
                    response.getWriter().write(gson.toJson(json));
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("error", "Tipo de serviço não encontrado");
                    response.getWriter().write(gson.toJson(json));
                }
            } else {
                JsonObject json = new JsonObject();
                json.addProperty("error", "tipoServicoId é necessário");
                response.getWriter().write(gson.toJson(json));
            }
        } catch (Exception e) {
            JsonObject json = new JsonObject();
            json.addProperty("error", e.getMessage());
            response.getWriter().write(gson.toJson(json));
        }
    }

}
