package model;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstabelecimentoDAO {

    private DBConnection dbConnection;

    public EstabelecimentoDAO() {
        this.dbConnection = new DBConnection(); 
    }

    public int save(Estabelecimento estabelecimento) {
        if (estabelecimento.getEstabelecimentoid() >= 0) {
            return this.update(estabelecimento);
        } else {
            return this.insert(estabelecimento);
        }
    }

    private int update(Estabelecimento estabelecimento) {
        String query = "UPDATE estabelecimento SET nome = ?, email = ?, senha = ?, telefone = ?, cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, statusCadastroId = ?, dataCadastro = ?, foto = ? WHERE estabelecimentoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, estabelecimento.getNome());
            statement.setString(2, estabelecimento.getEmail());
            statement.setString(3, estabelecimento.getSenha());
            statement.setString(4, estabelecimento.getTelefone());
            statement.setString(5, estabelecimento.getCep());
            statement.setString(6, estabelecimento.getRua());
            statement.setString(7, estabelecimento.getNumero());
            statement.setString(8, estabelecimento.getComplemento());
            statement.setString(9, estabelecimento.getBairro());
            statement.setString(10, estabelecimento.getCidade());
            statement.setString(11, estabelecimento.getEstado());
            statement.setInt(12, estabelecimento.getStatuscadastroid());
            statement.setString(13, estabelecimento.getDatacadastro());
            statement.setString(14, estabelecimento.getFoto());
            statement.setInt(15, estabelecimento.getEstabelecimentoid());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int insert(Estabelecimento estabelecimento) {
        String query = "INSERT INTO estabelecimento (nome, email, senha, telefone, cep, rua, numero, complemento, bairro, cidade, estado, statusCadastroId, dataCadastro, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
             
            statement.setString(1, estabelecimento.getNome());
            statement.setString(2, estabelecimento.getEmail());
            statement.setString(3, estabelecimento.getSenha());
            statement.setString(4, estabelecimento.getTelefone());
            statement.setString(5, estabelecimento.getCep());
            statement.setString(6, estabelecimento.getRua());
            statement.setString(7, estabelecimento.getNumero());
            statement.setString(8, estabelecimento.getComplemento());
            statement.setString(9, estabelecimento.getBairro());
            statement.setString(10, estabelecimento.getCidade());
            statement.setString(11, estabelecimento.getEstado());
            statement.setInt(12, estabelecimento.getStatuscadastroid());
            statement.setString(13, estabelecimento.getDatacadastro());
            statement.setString(14, estabelecimento.getFoto());
            
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

    public int delete(Estabelecimento estabelecimento) {
        if (estabelecimento.getEstabelecimentoid() != 0) {
            String query = "DELETE FROM estabelecimento WHERE estabelecimentoId = ?";
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, estabelecimento.getEstabelecimentoid());
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    public ArrayList<Estabelecimento> findAll() {
        String query = "SELECT * FROM estabelecimento";
        ArrayList<Estabelecimento> list = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
                estabelecimento.setNome(rs.getString("nome"));
                estabelecimento.setEmail(rs.getString("email"));
                estabelecimento.setSenha(rs.getString("senha"));
                estabelecimento.setTelefone(rs.getString("telefone"));
                estabelecimento.setCep(rs.getString("cep"));
                estabelecimento.setRua(rs.getString("rua"));
                estabelecimento.setNumero(rs.getString("numero"));
                estabelecimento.setComplemento(rs.getString("complemento"));
                estabelecimento.setBairro(rs.getString("bairro"));
                estabelecimento.setCidade(rs.getString("cidade"));
                estabelecimento.setEstado(rs.getString("estado"));
                estabelecimento.setStatuscadastroid(rs.getInt("statusCadastroId"));
                estabelecimento.setDatacadastro(rs.getString("dataCadastro"));
                estabelecimento.setFoto(rs.getString("foto"));
                list.add(estabelecimento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Estabelecimento findById(int id) {
        String query = "SELECT * FROM estabelecimento WHERE estabelecimentoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Estabelecimento estabelecimento = new Estabelecimento();
                    estabelecimento.setEstabelecimentoid(rs.getInt("estabelecimentoId"));
                    estabelecimento.setNome(rs.getString("nome"));
                    estabelecimento.setEmail(rs.getString("email"));
                    estabelecimento.setSenha(rs.getString("senha"));
                    estabelecimento.setTelefone(rs.getString("telefone"));
                    estabelecimento.setCep(rs.getString("cep"));
                    estabelecimento.setRua(rs.getString("rua"));
                    estabelecimento.setNumero(rs.getString("numero"));
                    estabelecimento.setComplemento(rs.getString("complemento"));
                    estabelecimento.setBairro(rs.getString("bairro"));
                    estabelecimento.setCidade(rs.getString("cidade"));
                    estabelecimento.setEstado(rs.getString("estado"));
                    estabelecimento.setStatuscadastroid(rs.getInt("statusCadastroId"));
                    estabelecimento.setDatacadastro(rs.getString("dataCadastro"));
                    estabelecimento.setFoto(rs.getString("foto"));
                    return estabelecimento;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Estabelecimento findById1(int estabelecimentoId) {
        String query = "SELECT " +
                       "e.nome AS nome, " +
                       "e.email AS email, " +
                       "e.telefone AS telefone, " +
                       "e.rua AS EstabelecimentoRua, " +
                       "e.bairro AS EstabelecimentoBairro, " +
                       "e.cidade AS EstabelecimentoCidade, " +
                       "e.estado AS EstabelecimentoEstado, " +
                       "p.nome AS ProfissionalNome, " +
                       "p.servico AS ProfissionalServico, " +
                       "s.nome AS ServicoNome, " +
                       "s.preco AS ServicoPreco, " +
                       "s.duracao AS ServicoDuracao " +
                       "FROM " +
                       "estabelecimento e " +
                       "JOIN profissional p ON e.estabelecimentoId = p.estabelecimentoId " +
                       "JOIN profissionalServico ps ON p.profissionalId = ps.profissionalId " +
                       "JOIN servico s ON ps.servicoId = s.servicoId " +
                       "WHERE e.estabelecimentoId = ?";  // Substituir "?" pelo ID do estabelecimento

        Estabelecimento estabelecimento = null;
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/barberhub", "root", "");
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, estabelecimentoId);  // Define o valor do ID no SQL

            try (ResultSet rs = stmt.executeQuery()) {  // Executa a consulta e processa o resultado
                if (rs.next()) {
                    // Cria o objeto Estabelecimento com os dados obtidos
                    estabelecimento = new Estabelecimento(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("EstabelecimentoRua"),
                            rs.getString("EstabelecimentoBairro"),
                            rs.getString("EstabelecimentoCidade"),
                            rs.getString("EstabelecimentoEstado"),
                            rs.getString("ProfissionalNome"),
                            rs.getString("ProfissionalServico"),
                            rs.getString("ServicoNome"),
                            rs.getDouble("ServicoPreco"),
                            rs.getInt("ServicoDuracao")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return estabelecimento;  // Retorna o objeto Estabelecimento preenchido
    }
    
    
    public List<Profissional> findProfissionaisByEstabelecimentoId(int estabelecimentoId) {
        String query = "SELECT " +
                       "e.nome AS EstabelecimentoNome, " +
                       "e.email AS EstabelecimentoEmail, " +
                       "e.telefone AS EstabelecimentoTelefone, " +
                       "e.rua AS EstabelecimentoRua, " +
                       "e.bairro AS EstabelecimentoBairro, " +
                       "e.cidade AS EstabelecimentoCidade, " +
                       "e.estado AS EstabelecimentoEstado, " +
                       "p.nome AS ProfissionalNome, " +
                       "p.servico AS ProfissionalServico, " +
                       "s.nome AS ServicoNome, " +
                       "s.preco AS ServicoPreco, " +
                       "s.duracao AS ServicoDuracao " +
                       "FROM " +
                       "estabelecimento e " +
                       "JOIN profissional p ON e.estabelecimentoId = p.estabelecimentoId " +
                       "JOIN profissionalServico ps ON p.profissionalId = ps.profissionalId " +
                       "JOIN servico s ON ps.servicoId = s.servicoId " +
                       "WHERE e.estabelecimentoId = ?";

        List<Profissional> profissionais = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/seu_banco", "usuario", "senha");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            // Define o ID do estabelecimento como parâmetro da consulta
            stmt.setInt(1, estabelecimentoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Profissional profissional = new Profissional(
                        rs.getString("ProfissionalNome"),
                        rs.getString("ProfissionalServico"),
                        rs.getString("ServicoNome"),
                        rs.getDouble("ServicoPreco"),
                        rs.getInt("ServicoDuracao")
                    );
                    profissionais.add(profissional);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return profissionais;
    }

    
}
