package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;


public class TiposervicoDAO {
	
	private DBConnection dbConnection;

    public TiposervicoDAO() {
        this.dbConnection = new DBConnection();
    }
    
    public int save(TipoServico tipoServico) {
        if (tipoServico.getTipoServicoId() > 0) {
            return this.update(tipoServico);
        } else {
            return this.insert(tipoServico);
        }
    }

	
    public int update(TipoServico tipoServico) {
        String query = "UPDATE tiposervico SET servico = ? WHERE tipoServicoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
        	
        	System.out.println("Atualizando tipoServico: " + tipoServico);

            statement.setString(1, tipoServico.getServicoNome());
            statement.setInt(2, tipoServico.getTipoServicoId());
            
           
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    private int insert(TipoServico tipoServico) {
        String query = "INSERT INTO tiposervico (servico) VALUES (?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, tipoServico.getServicoNome());
           

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

    public int delete(TipoServico tipoServico) {
        return deleteById(tipoServico.getTipoServicoId());
    }

    public List<TipoServico> findAll() {
        String query = "SELECT * FROM tiposervico";
        List<TipoServico> list = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
            	TipoServico tipoServico = new TipoServico();
                tipoServico.setTipoServicoId(rs.getInt("tipoServicoId"));
                tipoServico.setServicoNome(rs.getString("servico"));
                list.add(tipoServico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public TipoServico findById(int id) {
        String query = "SELECT * FROM tiposervico WHERE tipoServicoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                	TipoServico tipoServico = new TipoServico();
                    tipoServico.setTipoServicoId(rs.getInt("tipoServicoId"));
                    tipoServico.setServicoNome(rs.getString("servico"));
                    
                    return tipoServico;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int deleteById(int id) {
        String query = "DELETE FROM tiposervico WHERE tipoServicoId = ?";
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
