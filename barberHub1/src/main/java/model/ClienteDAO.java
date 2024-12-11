package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;

public class ClienteDAO{
	
	private DBConnection dbConnection;
	
	public ClienteDAO() {
        this.dbConnection = new DBConnection(); 
    }

 
    public int save(Cliente cliente) {
        if (cliente.getClienteId()  > 0) {
            return this.update(cliente);
        } else {
            return this.insert(cliente);
        }
    }
    
    private int update(Cliente cliente) {
        String query = "UPDATE cliente SET nome = ?, email = ?, senha = ?, telefone = ?, " +
                       "cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, " +
                       "estado = ?, foto = ? WHERE clienteId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getSenha());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getCep());
            statement.setString(6, cliente.getRua());
            statement.setString(7, cliente.getNumero());
            statement.setString(8, cliente.getComplemento());
            statement.setString(9, cliente.getBairro());
            statement.setString(10, cliente.getCidade());
            statement.setString(11, cliente.getEstado());
            statement.setString(12, cliente.getFoto());
            statement.setInt(13, cliente.getClienteId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private int insert(Cliente cliente) {
        String query = "INSERT INTO cliente (nome, email, senha, telefone, cep, rua, numero, complemento, " +
                       "bairro, cidade, estado, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
             
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getSenha());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getCep());
            statement.setString(6, cliente.getRua());
            statement.setString(7, cliente.getNumero());
            statement.setString(8, cliente.getComplemento());
            statement.setString(9, cliente.getBairro());
            statement.setString(10, cliente.getCidade());
            statement.setString(11, cliente.getEstado());
            statement.setString(12, cliente.getFoto());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); 
                    }
                }
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

  
    public int delete(Cliente cliente) {
        if (cliente.getClienteId() != 0) {
            String query = "DELETE FROM cliente WHERE clienteId = ?";
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, cliente.getClienteId());
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }


    public List<Cliente> findAll() {
        String query = "SELECT * FROM cliente";
        List<Cliente> list = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
            	Cliente cliente = new Cliente();
            	cliente.setClienteId(rs.getInt("clienteId"));
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

	public Cliente findById(int clienteId) {
	    String query = "SELECT clienteId, nome, email, senha, telefone, cep, rua, numero, complemento, bairro, cidade, estado, foto " +
	                   "FROM cliente WHERE clienteId = ?";
	    try (Connection connection = dbConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setInt(1, clienteId); 
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                Cliente cliente = new Cliente();
	                cliente.setClienteId(rs.getInt("clienteId"));
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
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao buscar cliente pelo ID: " + e.getMessage());
	    }
	    return null;
	}
}
