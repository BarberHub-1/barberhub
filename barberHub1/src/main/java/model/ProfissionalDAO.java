package model;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class ProfissionalDAO {

    private DBConnection dbConnection;

    public ProfissionalDAO() {
        this.dbConnection = new DBConnection(); 
    }

    public int save(Profissional profissional) {
        if (profissional.getProfissionalid() >= 0) {
            return this.update(profissional);
        } else {
            return this.insert(profissional);
        }
    }

    private int insert(Profissional profissional) {
        String query = "INSERT INTO profissional (estabelecimentoId, nome, servico, cep, rua, numero, complemento, bairro, cidade, estado, foto) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Verifique se os campos obrigatórios estão preenchidos
            if (profissional.getNome() == null || profissional.getNome().isEmpty()) {
                return 0;
            }

            statement.setInt(1, profissional.getEstabelecimentoid());
            statement.setString(2, profissional.getNome());
            statement.setString(3, profissional.getServico());
            statement.setString(4, profissional.getCep());
            statement.setString(5, profissional.getRua());
            statement.setString(6, profissional.getNumero());
            statement.setString(7, profissional.getComplemento());
            statement.setString(8, profissional.getBairro());
            statement.setString(9, profissional.getCidade());
            statement.setString(10, profissional.getEstado());
            statement.setString(11, profissional.getFoto());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Retorna o ID gerado
                    }
                }
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(Profissional profissional) {
        String query = "UPDATE profissional SET estabelecimentoId = ?, nome = ?, servico = ?, cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, foto = ? WHERE profissionalId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Verifique se os campos obrigatórios estão preenchidos
            if (profissional.getNome() == null || profissional.getNome().isEmpty()) {
                return 0;  // Retorna 0 para indicar que a atualização não foi realizada devido a dados inválidos
            }

            statement.setInt(1, profissional.getEstabelecimentoid());
            statement.setString(2, profissional.getNome());
            statement.setString(3, profissional.getServico());
            statement.setString(4, profissional.getCep());
            statement.setString(5, profissional.getRua());
            statement.setString(6, profissional.getNumero());
            statement.setString(7, profissional.getComplemento());
            statement.setString(8, profissional.getBairro());
            statement.setString(9, profissional.getCidade());
            statement.setString(10, profissional.getEstado());
            statement.setString(11, profissional.getFoto());
            statement.setInt(12, profissional.getProfissionalid());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return 1;  // Retorna 1 para indicar que a atualização foi bem-sucedida
            } else {
                return 0;  // Retorna 0 se nenhuma linha foi afetada
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  // Retorna -1 para indicar que ocorreu um erro
        }
    }


    public int delete(Profissional profissional) {
        if (profissional.getProfissionalid() != 0) {
            String query = "DELETE FROM profissional WHERE profissionalId = ?";
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, profissional.getProfissionalid());
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    public int deleteById(int id) {
        String query = "DELETE FROM profissional WHERE profissionalId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Profissional> findAll() {
        String query = "SELECT * FROM profissional";
        ArrayList<Profissional> list = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Profissional profissional = new Profissional();
                profissional.setProfissionalid(rs.getInt("profissionalId"));
                profissional.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
                profissional.setNome(rs.getString("nome"));
                profissional.setServico(rs.getString("servico"));
                profissional.setCep(rs.getString("cep"));
                profissional.setRua(rs.getString("rua"));
                profissional.setNumero(rs.getString("numero"));
                profissional.setComplemento(rs.getString("complemento"));
                profissional.setBairro(rs.getString("bairro"));
                profissional.setCidade(rs.getString("cidade"));
                profissional.setEstado(rs.getString("estado"));
                profissional.setFoto(rs.getString("foto"));
                list.add(profissional);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Profissional findById(int id) {
        String query = "SELECT * FROM profissional WHERE profissionalId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Profissional profissional = new Profissional();
                    profissional.setProfissionalid(rs.getInt("profissionalId"));
                    profissional.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
                    profissional.setNome(rs.getString("nome"));
                    profissional.setServico(rs.getString("servico"));
                    profissional.setCep(rs.getString("cep"));
                    profissional.setRua(rs.getString("rua"));
                    profissional.setNumero(rs.getString("numero"));
                    profissional.setComplemento(rs.getString("complemento"));
                    profissional.setBairro(rs.getString("bairro"));
                    profissional.setCidade(rs.getString("cidade"));
                    profissional.setEstado(rs.getString("estado"));
                    profissional.setFoto(rs.getString("foto"));
                    return profissional;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
