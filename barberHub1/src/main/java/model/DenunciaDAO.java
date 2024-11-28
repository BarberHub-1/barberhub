package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DBQuery;

public class DenunciaDAO extends DBQuery {

	public DenunciaDAO() {
		this.setTableName("denuncia");
		this.setFieldsName("denunciaId, estabelecimentoId, clienteId, descricao, dataDenuncia");
		this.setFieldKey("denunciaId");
	}

	public int save(Denuncia denuncia) {
		if(denuncia.getDenunciaid() >= 0) {
			return this.update(denuncia.toArray());
		} else {
			return this.insert(denuncia.toArray());
		}
	}

	public int delete(Denuncia denuncia) {
		if(denuncia.getDenunciaid() != 0) {
			return this.delete(denuncia.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
	    return super.select(where); // Chama o método select da classe base (DBQuery)
	}

	public ArrayList<Denuncia> findAll() {
		ResultSet rs = this.select("");
		ArrayList<Denuncia> list = new ArrayList<>();
		try {
			while (rs.next()) {
				Denuncia denuncia = new Denuncia();
				denuncia.setDenunciaid(rs.getInt("denunciaId"));
				denuncia.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
				denuncia.setClienteid(rs.getInt("clienteId"));
				denuncia.setDescricao(rs.getString("descricao"));
				denuncia.setDatadenuncia(rs.getString("dataDenuncia"));
				list.add(denuncia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Denuncia findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				Denuncia denuncia = new Denuncia();
				denuncia.setDenunciaid(rs.getInt("denunciaId"));
				denuncia.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
				denuncia.setClienteid(rs.getInt("clienteId"));
				denuncia.setDescricao(rs.getString("descricao"));
				denuncia.setDatadenuncia(rs.getString("dataDenuncia"));
				return denuncia;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
