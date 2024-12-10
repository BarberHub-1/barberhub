package model;

import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    private DBConnection dbConnection;

    public ServicoDAO() {
        this.dbConnection = new DBConnection();
    }

    public int save(Servico servico) {
        if (servico.getServicoId() >= 0) {
            return this.update(servico);
        } else {
            return this.insert(servico);
        }
    }

    public int update(Servico servico) {
        String query = "UPDATE servico SET nome = ?, descricao = ?, tipoServicoId = ?, preco = ?, duracao = ? WHERE servicoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, servico.getNome());
            statement.setString(2, servico.getDescricao());
            statement.setInt(3, servico.getTipoServico().getTipoServicoId());
            statement.setDouble(4, servico.getPreco());
            statement.setInt(5, servico.getDuracao());
            statement.setInt(6, servico.getServicoId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int insert(Servico servico) {
        String query = "INSERT INTO servico (nome, descricao, tipoServicoId, preco, duracao) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, servico.getNome());
            statement.setString(2, servico.getDescricao());
            statement.setInt(3, servico.getTipoServico().getTipoServicoId());
            statement.setDouble(4, servico.getPreco());
            statement.setInt(5, servico.getDuracao());

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

    public int delete(Servico servico) {
        String query = "DELETE FROM servico WHERE servicoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, servico.getServicoId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Servico> findAll() {
        String query = "SELECT * FROM servico";
        List<Servico> list = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Servico servico = new Servico();
                servico.setServicoId(rs.getInt("servicoId"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.getTipoServico().setTipoServicoId(rs.getInt("tipoServicoId"));
                servico.setPreco(rs.getDouble("preco"));
                servico.setDuracao(rs.getInt("duracao"));
                list.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Servico findById(int id) {
        String query = "SELECT * FROM servico WHERE servicoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Servico servico = new Servico();
                    servico.setServicoId(rs.getInt("servicoId"));
                    servico.setNome(rs.getString("nome"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.getTipoServico().setTipoServicoId(rs.getInt("tipoServicoId"));
                    servico.setPreco(rs.getDouble("preco"));
                    servico.setDuracao(rs.getInt("duracao"));
                    return servico;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Servico> findAllWithJoin() {
        String query = "SELECT servico.*, tiposervico.servico AS tipoServicoNome FROM servico INNER JOIN tiposervico ON servico.tipoServicoId = tiposervico.tipoServicoId";
        List<Servico> list = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Servico servico = new Servico();
                servico.setServicoId(rs.getInt("servicoId"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setPreco(rs.getDouble("preco"));
                servico.setDuracao(rs.getInt("duracao"));
                servico.getTipoServico().setServicoNome(rs.getString("tipoServicoNome"));
                list.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Servico findByIdWithJoin(int id) {
        String query = "SELECT servico.*, tiposervico.servico AS tipoServicoNome FROM servico INNER JOIN tiposervico ON servico.tipoServicoId = tiposervico.tipoServicoId WHERE servico.servicoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Servico servico = new Servico();
                    servico.setServicoId(rs.getInt("servicoId"));
                    servico.setNome(rs.getString("nome"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.setPreco(rs.getDouble("preco"));
                    servico.setDuracao(rs.getInt("duracao"));
                    servico.getTipoServico().setServicoNome(rs.getString("tipoServicoNome"));
                    return servico;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Servico> findByProfissionalId(int profissionalId) {
        String query = "SELECT s.* FROM servico s INNER JOIN profissionalservico ps ON s.servicoId = ps.servicoId WHERE ps.profissionalId = ?";
        List<Servico> list = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, profissionalId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Servico servico = new Servico();
                    servico.setServicoId(rs.getInt("servicoId"));
                    servico.setNome(rs.getString("nome"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.setPreco(rs.getDouble("preco"));
                    servico.setDuracao(rs.getInt("duracao"));
                    list.add(servico);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
