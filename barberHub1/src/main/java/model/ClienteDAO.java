package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import database.DBQuery;

public class ClienteDAO extends DBQuery {

	public ClienteDAO() {
		this.setTableName("cliente");
		this.setFieldsName("clienteId, nome, email, senha, telefone, cep, rua, numero, complemento, bairro, cidade, estado, foto");
		this.setFieldKey("clienteId");
	}

	public int save(Cliente cliente) {
		if(cliente.getClienteid() >= 0) {
			return this.update(cliente.toArray());
		} else {
			return this.insert(cliente.toArray());
		}
	}

	public int delete(Cliente cliente) {
		if(cliente.getClienteid() != 0) {
			return this.delete(cliente.toArray());
		} else {
			return 0;
		}
	}

	public ResultSet select(String where) {
	    return super.select(where); // Chama o método select da classe base (DBQuery)
	}

	public ArrayList<Cliente> findAll() {
		ResultSet rs = this.select("");
		ArrayList<Cliente> list = new ArrayList<>();
		try {
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setClienteid(rs.getInt("clienteId"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setCep(rs.getString("cep"));
				cliente.setRua(rs.getString("rua"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setComplemento(rs.getString("complemento"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEstado(rs.getString("estado"));
				cliente.setFoto(rs.getString("foto"));
				list.add(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Cliente findById(int id) {
		ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id);
		try {
			if (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setClienteid(rs.getInt("clienteId"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setCep(rs.getString("cep"));
				cliente.setRua(rs.getString("rua"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setComplemento(rs.getString("complemento"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEstado(rs.getString("estado"));
				cliente.setFoto(rs.getString("foto"));
				return cliente;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
