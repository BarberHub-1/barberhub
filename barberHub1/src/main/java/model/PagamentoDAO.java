package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DBQuery;

public class PagamentoDAO extends DBQuery {

	public PagamentoDAO() {
		this.setTableName("pagamento");
		this.setFieldsName("pagamentoId, agendamentoId, valor, metodo, status");
		this.setFieldKey("pagamentoId");
	}

	public int save(Pagamento pagamento) {
		if(pagamento.getPagamentoid() >= 0) {
			return this.update(pagamento.toArray());
		} else {
			return this.insert(pagamento.toArray());
		}
	}

	public int delete(Pagamento pagamento) {
		if(pagamento.getPagamentoid() != 0) {
			return this.delete(pagamento.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
	    return super.select(where); // Chama o método select da classe base (DBQuery)
	}

	public ArrayList<Pagamento> findAll() {
		ResultSet rs = this.select("");
		ArrayList<Pagamento> list = new ArrayList<>();
		try {
			while (rs.next()) {
				Pagamento pagamento = new Pagamento();
				pagamento.setPagamentoid(rs.getInt("pagamentoId"));
				pagamento.setAgendamentoid(rs.getInt("agendamentoId"));
				pagamento.setValor(rs.getDouble("valor"));
				pagamento.setMetodo(rs.getString("metodo"));
				pagamento.setStatus(rs.getString("status"));
				list.add(pagamento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Pagamento findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				Pagamento pagamento = new Pagamento();
				pagamento.setPagamentoid(rs.getInt("pagamentoId"));
				pagamento.setAgendamentoid(rs.getInt("agendamentoId"));
				pagamento.setValor(rs.getDouble("valor"));
				pagamento.setMetodo(rs.getString("metodo"));
				pagamento.setStatus(rs.getString("status"));
				return pagamento;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
