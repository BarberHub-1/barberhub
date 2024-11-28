package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DBQuery;

public class TiposervicoDAO extends DBQuery {

	public TiposervicoDAO() {
		this.setTableName("tipoServico");
		this.setFieldsName("tipoServicoId, servico");
		this.setFieldKey("tipoServicoId");
	}

	public int save(TipoServico tipoServico) {
		if(tipoServico.getTiposervicoid() >= 0) {
			return this.update(tipoServico.toArray());
		} else {
			return this.insert(tipoServico.toArray());
		}
	}

	public int delete(TipoServico tipoServico) {
		if(tipoServico.getTiposervicoid() != 0) {
			return this.delete(tipoServico.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
	    return super.select(where); 
	}

	public ArrayList<TipoServico> findAll() {
		ResultSet rs = this.select("");
		ArrayList<TipoServico> list = new ArrayList<>();
		try {
			while (rs.next()) {
				TipoServico tipoServico = new TipoServico();
				tipoServico.setTiposervicoid(rs.getInt("tipoServicoId"));
				tipoServico.setServico(rs.getString("servico"));
				list.add(tipoServico);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public TipoServico findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				TipoServico tipoServico = new TipoServico();
				tipoServico.setTiposervicoid(rs.getInt("tipoServicoId"));
				tipoServico.setServico(rs.getString("servico"));
				return tipoServico;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
