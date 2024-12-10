package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnection;

public class LoginDAO {
    private DBConnection dbConnection = new DBConnection();

    public Object autenticar(String email, String senha) {
        // Verificar na tabela Cliente
        String queryCliente = "SELECT * FROM cliente WHERE email = ? AND senha = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryCliente)) {

            statement.setString(1, email);
            statement.setString(2, senha);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setClienteId(rs.getInt("clienteId"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    return cliente;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Verificar na tabela Estabelecimento
        String queryEstabelecimento = "SELECT * FROM estabelecimento WHERE email = ? AND senha = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryEstabelecimento)) {

            statement.setString(1, email);
            statement.setString(2, senha);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Estabelecimento estabelecimento = new Estabelecimento();
                    estabelecimento.setEstabelecimentoId(rs.getInt("estabelecimentoId"));
                    estabelecimento.setNome(rs.getString("nome"));
                    estabelecimento.setEmail(rs.getString("email"));
                    return estabelecimento;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna null se não encontrar em nenhuma tabela
        return null;
    }
}
