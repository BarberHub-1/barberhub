package database;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

    public static void main(String[] args) {
        // Crie uma instância de DBConnection com os parâmetros de conexão padrão
        DBConnection dbConnection = new DBConnection();

        // Obtenha a conexão
        Connection connection = dbConnection.getConnection();

        // Verifique se a conexão é válida
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao conectar com o banco de dados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
