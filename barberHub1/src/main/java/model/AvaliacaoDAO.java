package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DBQuery;

public class AvaliacaoDAO extends DBQuery {

	public AvaliacaoDAO() {
		this.setTableName("avaliacao");
		this.setFieldsName("avaliacaoId, agendamentoId, avaliado, nota, comentario, dataAvaliacao");
		this.setFieldKey("avaliacaoId");
	}

	public int save(Avaliacao avaliacao) {
		if(avaliacao.getAvaliacaoid() >= 0) {
			return this.update(avaliacao.toArray());
		} else {
			return this.insert(avaliacao.toArray());
		}
	}

	public int delete(Avaliacao avaliacao) {
		if(avaliacao.getAvaliacaoid() != 0) {
			return this.delete(avaliacao.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
	    return super.select(where); // Chama o método select da classe base (DBQuery)
	}

	public ArrayList<Avaliacao> findAll() {
		ResultSet rs = this.select("");
		ArrayList<Avaliacao> list = new ArrayList<>();
		try {
			while (rs.next()) {
				Avaliacao avaliacao = new Avaliacao();
				avaliacao.setAvaliacaoid(rs.getInt("avaliacaoId"));
				avaliacao.setAgendamentoid(rs.getInt("agendamentoId"));
				avaliacao.setAvaliado(rs.getInt("avaliado"));
				avaliacao.setNota(rs.getInt("nota"));
				avaliacao.setComentario(rs.getString("comentario"));
				avaliacao.setDataAvaliacao(rs.getString("dataAvaliacao"));
				list.add(avaliacao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Avaliacao findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				Avaliacao avaliacao = new Avaliacao();
				avaliacao.setAvaliacaoid(rs.getInt("avaliacaoId"));
				avaliacao.setAgendamentoid(rs.getInt("agendamentoId"));
				avaliacao.setAvaliado(rs.getInt("avaliado"));
				avaliacao.setNota(rs.getInt("nota"));
				avaliacao.setComentario(rs.getString("comentario"));
				avaliacao.setDataAvaliacao(rs.getString("dataAvaliacao"));
				return avaliacao;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
