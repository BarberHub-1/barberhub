package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;
import database.DBQuery;

public class SistemaDAO extends DBQuery {

    public SistemaDAO() {
        this.setTableName("sistema");
        this.setFieldsName("sistemaId, nome, logo, email, telefone");
        this.setFieldKey("sistemaId");
    }

    public int save(Sistema sistema) {
        if (sistema.getSistemaid() > 0) {
            
            return this.update(sistema.toArray());
        } else {
           
            return this.insert(sistema.toArray());
        }
    }

    public int delete(Sistema sistema) {
        if (sistema.getSistemaid() > 0) {
            return this.delete(sistema.toArray());
        } else {
            return 0;
        }
    }

    public ResultSet select(String where) {
        return super.select(where); // Chama o método select da classe base (DBQuery)
    }

    public ArrayList<Sistema> findAll() {
        ResultSet rs = this.select("");
        ArrayList<Sistema> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Sistema sistema = new Sistema();
                sistema.setSistemaid(rs.getInt("sistemaId"));
                sistema.setNome(rs.getString("nome"));
                sistema.setLogo(rs.getString("logo"));
                sistema.setEmail(rs.getString("email"));
                sistema.setTelefone(rs.getString("telefone"));
                list.add(sistema);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public Sistema findById(int sistemaId) throws SQLException {
        Sistema sistema = null;
        String sql = "SELECT * FROM sistema WHERE sistemaId = ?";
        
        // Abrindo a conexão com DBConnection
        try (DBConnection dbConn = new DBConnection(); 
             Connection conn = dbConn.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sistemaId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                sistema = new Sistema();
                sistema.setSistemaid(rs.getInt("sistemaId"));
                sistema.setNome(rs.getString("nome"));
                sistema.setLogo(rs.getString("logo"));
                sistema.setEmail(rs.getString("email"));
                sistema.setTelefone(rs.getString("telefone"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ajuste para tratar erro adequadamente
            throw e; // Re-throw para que o erro seja tratado em nível superior
        }
    
        return sistema;
    }

    public int insert(Object[] values) {
        String query = "INSERT INTO " + this.getTableName() + " (" + this.getFieldsName() + ") VALUES (?, ?, ?, ?, ?)";
        try (DBConnection dbConn = new DBConnection();
        	Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
           
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }

            int affectedRows = stmt.executeUpdate(); // Executa a inserção
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Obtém o ID gerado
                        int generatedId = generatedKeys.getInt(1);
                        // Atualiza o ID no objeto Sistema
                        ((Sistema) values[0]).setSistemaid(generatedId);  // Atribui o ID gerado
                    }
                }
            }
            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
