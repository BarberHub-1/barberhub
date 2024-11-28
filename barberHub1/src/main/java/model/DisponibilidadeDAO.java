package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DBQuery;

public class DisponibilidadeDAO extends DBQuery {

	public DisponibilidadeDAO() {
		this.setTableName("disponibilidade");
		this.setFieldsName("disponibilidadeId, profissionalId, data, horaInicio, horaFim, em_agendamento");
		this.setFieldKey("disponibilidadeId");
	}

	public int save(Disponibilidade disponibilidade) {
		if(disponibilidade.getDisponibilidadeid() >= 0) {
			return this.update(disponibilidade.toArray());
		} else {
			return this.insert(disponibilidade.toArray());
		}
	}

	public int delete(Disponibilidade disponibilidade) {
		if(disponibilidade.getDisponibilidadeid() != 0) {
			return this.delete(disponibilidade.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
	    return super.select(where); // Chama o método select da classe base (DBQuery)
	}

	public ArrayList<Disponibilidade> findAll() {
		ResultSet rs = this.select("");
		ArrayList<Disponibilidade> list = new ArrayList<>();
		try {
			while (rs.next()) {
				Disponibilidade disponibilidade = new Disponibilidade();
				disponibilidade.setDisponibilidadeid(rs.getInt("disponibilidadeId"));
				disponibilidade.getProfissional().setProfissionalid(rs.getInt("profissionalId"));
				disponibilidade.setData(rs.getString("data"));
				disponibilidade.setHorainicio(rs.getTime("horaInicio"));
				disponibilidade.setHorafim(rs.getTime("horaFim"));
				disponibilidade.setEm_agendamento(rs.getBoolean("em_agendamento"));
				list.add(disponibilidade);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Disponibilidade findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				Disponibilidade disponibilidade = new Disponibilidade();
				disponibilidade.setDisponibilidadeid(rs.getInt("disponibilidadeId"));
				disponibilidade.getProfissional().setProfissionalid(rs.getInt("profissionalId"));
				disponibilidade.setData(rs.getString("data"));
				disponibilidade.setHorainicio(rs.getTime("horaInicio"));
				disponibilidade.setHorafim(rs.getTime("horaFim"));
				disponibilidade.setEm_agendamento(rs.getBoolean("em_agendamento"));
				return disponibilidade;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
