package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DBQuery;

public class StatuscadastroDAO extends DBQuery {

	public StatuscadastroDAO() {
		this.setTableName("statusCadastro");
		this.setFieldsName("statusCadastroId, descricao");
		this.setFieldKey("statusCadastroId");
	}

	public int save(StatusCadastro statusCadastro) {
		if(statusCadastro.getStatuscadastroId() >= 0) {
			return this.update(statusCadastro.toArray());
		} else {
			return this.insert(statusCadastro.toArray());
		}
	}

	public int delete(StatusCadastro statusCadastro) {
		if(statusCadastro.getStatuscadastroId() != 0) {
			return this.delete(statusCadastro.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
	    return super.select(where); 
	}

	public ArrayList<StatusCadastro> findAll() {
		ResultSet rs = this.select("");
		ArrayList<StatusCadastro> list = new ArrayList<>();
		try {
			while (rs.next()) {
				StatusCadastro statusCadastro = new StatusCadastro();
				statusCadastro.setStatuscadastroId(rs.getInt("statusCadastroId"));
				statusCadastro.setDescricao(rs.getString("descricao"));
				list.add(statusCadastro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public StatusCadastro findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				StatusCadastro statusCadastro = new StatusCadastro();
				statusCadastro.setStatuscadastroId(rs.getInt("statusCadastroId"));
				statusCadastro.setDescricao(rs.getString("descricao"));
				return statusCadastro;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
