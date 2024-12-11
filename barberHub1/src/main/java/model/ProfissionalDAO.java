package model;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfissionalDAO {

    private DBConnection dbConnection;

    public ProfissionalDAO() {
        this.dbConnection = new DBConnection(); 
    }

    
    public int save(Profissional profissional) {
        if (profissional.getProfissionalId() > 0) {
            return this.update(profissional);
        } else {
            return this.insert(profissional);
        }
    }

   
    private int insert(Profissional profissional) {
        String query = "INSERT INTO profissional (estabelecimentoId, nome, cep, rua, numero, complemento, bairro, cidade, estado, foto) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            
            if (profissional.getEstabelecimento().getEstabelecimentoId() <= 0) {
                throw new IllegalArgumentException("EstabelecimentoID é obrigatório e deve ser maior que 0.");
            }
            if (profissional.getNome() == null || profissional.getNome().isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório.");
            }

           
            statement.setInt(1, profissional.getEstabelecimento().getEstabelecimentoId());
            statement.setString(2, profissional.getNome());
            statement.setString(3, profissional.getCep());
            statement.setString(4, profissional.getRua());
            statement.setString(5, profissional.getNumero());
            statement.setString(6, profissional.getComplemento());
            statement.setString(7, profissional.getBairro());
            statement.setString(8, profissional.getCidade());
            statement.setString(9, profissional.getEstado());
            statement.setString(10, profissional.getFoto());

            
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
            System.err.println("Erro ao inserir profissional: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    
    public int update(Profissional profissional) {
        String query = "UPDATE profissional SET estabelecimentoId = ?, nome = ?, cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, foto = ? WHERE profissionalId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            System.out.println("Atualizando profissional: " + profissional);

            if (profissional.getEstabelecimento() == null) {
                profissional.setEstabelecimento(new Estabelecimento()); // Garante que o objeto Estabelecimento existe
            }

            statement.setInt(1, profissional.getEstabelecimento().getEstabelecimentoId());
            statement.setString(2, profissional.getNome());
            statement.setString(3, profissional.getCep());
            statement.setString(4, profissional.getRua());
            statement.setString(5, profissional.getNumero());
            statement.setString(6, profissional.getComplemento());
            statement.setString(7, profissional.getBairro());
            statement.setString(8, profissional.getCidade());
            statement.setString(9, profissional.getEstado());
            statement.setString(10, profissional.getFoto());
            statement.setInt(11, profissional.getProfissionalId());

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Linhas atualizadas: " + rowsUpdated);
            return rowsUpdated;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar profissional: " + e.getMessage());
            e.printStackTrace();
            return -1; 
        }
    }

    
    public int delete(Profissional profissional) {
        return deleteById(profissional.getProfissionalId());
    }

   
    public int deleteById(int id) {
        String query = "DELETE FROM profissional WHERE profissionalId = ?";
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

    
    public List<Profissional> findAll() {
        String query = "SELECT * FROM profissional";
        List<Profissional> profissionais = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Profissional profissional = mapResultSetToProfissional(rs);
                profissionais.add(profissional);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os profissionais: " + e.getMessage());
            e.printStackTrace();
        }
        return profissionais;
    }

    
    public Profissional findById(int id) {
        String query = "SELECT * FROM profissional WHERE profissionalId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Profissional profissional = mapResultSetToProfissional(rs);
                    return profissional;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar profissional pelo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Método auxiliar para mapear um ResultSet em um objeto Profissional
    private Profissional mapResultSetToProfissional(ResultSet rs) throws SQLException {
        Profissional profissional = new Profissional();
        profissional.setProfissionalId(rs.getInt("profissionalId"));

        int estabelecimentoId = rs.getInt("estabelecimentoId");
        if (estabelecimentoId > 0) {
            Estabelecimento estabelecimento = new Estabelecimento();
            estabelecimento.setEstabelecimentoId(estabelecimentoId);
            profissional.setEstabelecimento(estabelecimento);
        }

        profissional.setNome(rs.getString("nome"));
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
    
    public List<Profissional> findProfissionaisByEstabelecimentoId(int estabelecimentoId) {
        String query = "SELECT * FROM profissional WHERE estabelecimentoId = ?";
        List<Profissional> profissionais = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, estabelecimentoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Profissional profissional = new Profissional();

                    // Inicializar o objeto Estabelecimento
                    Estabelecimento estabelecimento = new Estabelecimento();
                    estabelecimento.setEstabelecimentoId(estabelecimentoId);
                    profissional.setEstabelecimento(estabelecimento);

                    // Mapear outros atributos
                    profissional.setProfissionalId(rs.getInt("profissionalId"));
                    profissional.setNome(rs.getString("nome"));
                    profissional.setCep(rs.getString("cep"));
                    profissional.setRua(rs.getString("rua"));
                    profissional.setNumero(rs.getString("numero"));
                    profissional.setComplemento(rs.getString("complemento"));
                    profissional.setBairro(rs.getString("bairro"));
                    profissional.setCidade(rs.getString("cidade"));
                    profissional.setEstado(rs.getString("estado"));
                    profissional.setFoto(rs.getString("foto"));

                    profissionais.add(profissional);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar profissionais por estabelecimentoId: " + e.getMessage());
            e.printStackTrace();
        }

        return profissionais;
    }

}
