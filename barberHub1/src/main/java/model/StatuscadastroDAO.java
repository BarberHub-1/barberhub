package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import database.DBQuery;

public class StatuscadastroDAO extends DBQuery {
	
	private DBConnection dbConnection;

    public StatuscadastroDAO() {
        this.dbConnection = new DBConnection();
    }
    
    public int save(StatusCadastro statusCadastro) {
        if (statusCadastro.getStatusCadastroId() >= 0) {
            return this.update(statusCadastro);
        } else {
            return this.insert(statusCadastro);
        }
    }
    
    public int update(StatusCadastro statusCadastro) {
        String query = "UPDATE statuscadastro SET descricao = ? WHERE statusCadastroId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
        	
        	System.out.println("Atualizando statusCadastro: " + statusCadastro);

            statement.setString(1, statusCadastro.getDescricao());
            statement.setInt(2, statusCadastro.getStatusCadastroId());
            
           
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    private int insert(StatusCadastro statusCadastro) {
        String query = "INSERT INTO statuscadastro (descricao) VALUES (?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, statusCadastro.getDescricao());
           

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
    

    public int delete(StatusCadastro statusCadastro) {
        return deleteById(statusCadastro.getStatusCadastroId());
    }
    
    public List<StatusCadastro> findAll() {
        String query = "SELECT * FROM statuscadastro";
        List<StatusCadastro> list = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
            	StatusCadastro statusCadastro = new StatusCadastro();
            	statusCadastro.setStatusCadastroId(rs.getInt("statusCadastroId"));
            	statusCadastro.setDescricao(rs.getString("descricao"));
                list.add(statusCadastro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public StatusCadastro findById(int id) {
        String query = "SELECT * FROM statuscadastro WHERE statusCadastroId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                	StatusCadastro statusCadastro = new StatusCadastro();
                	statusCadastro.setStatusCadastroId(rs.getInt("statusCadastroId"));
                	statusCadastro.setDescricao(rs.getString("descricao")); 
                    
                    return statusCadastro;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int deleteById(int id) {
        String query = "DELETE FROM statuscadastro WHERE statusCadastroId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar profissional: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }



	
}
