package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import database.DBQuery;

public class AgendamentoDAO extends DBQuery {

	public AgendamentoDAO() {
		this.setTableName("agendamento");
		this.setFieldsName("agendamentoId, estabelecimentoId, profissionalId, servicoId, clienteId, data, hora, preco, desconto, status");
		this.setFieldKey("agendamentoId");
	}

	public int save(Agendamento agendamento) {
		if(agendamento.getAgendamentoid() >= 0) {
			return this.update(agendamento.toArray());
		} else {
			return this.insert(agendamento.toArray());
		}
	}

	public int delete(Agendamento agendamento) {
		if(agendamento.getAgendamentoid() != 0) {
			return this.delete(agendamento.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
		return super.select(where);
	}

	public ArrayList<Agendamento> findAll() {
		ResultSet rs = this.select("");
		ArrayList<Agendamento> list = new ArrayList<>();
		try {
			while (rs.next()) {
				Agendamento agendamento = new Agendamento();
				agendamento.setAgendamentoid(rs.getInt("agendamentoId"));
				agendamento.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
				agendamento.setProfissionalid(rs.getInt("profissionalId"));
				agendamento.setServicoid(rs.getInt("servicoId"));
				agendamento.setClienteid(rs.getInt("clienteId"));
				agendamento.setData(rs.getString("data"));
				agendamento.setHora(Time.valueOf(rs.getTime("hora").toLocalTime()));
				agendamento.setPreco(rs.getDouble("preco"));
				agendamento.setDesconto(rs.getDouble("desconto"));
				agendamento.setStatus(rs.getString("status"));
				list.add(agendamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Agendamento findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				Agendamento agendamento = new Agendamento();
				agendamento.setAgendamentoid(rs.getInt("agendamentoId"));
				agendamento.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
				agendamento.setProfissionalid(rs.getInt("profissionalId"));
				agendamento.setServicoid(rs.getInt("servicoId"));
				agendamento.setClienteid(rs.getInt("clienteId"));
				agendamento.setData(rs.getString("data"));
				agendamento.setHora(Time.valueOf(rs.getTime("hora").toLocalTime()));
				agendamento.setPreco(rs.getDouble("preco"));
				agendamento.setDesconto(rs.getDouble("desconto"));
				agendamento.setStatus(rs.getString("status"));
				return agendamento;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
