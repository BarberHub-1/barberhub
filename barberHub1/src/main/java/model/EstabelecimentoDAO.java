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
        if (estabelecimento.getEstabelecimentoId() >= 0) {
            return this.update(estabelecimento);
        } else {
            return this.insert(estabelecimento);
        }
    }


    private int update(Estabelecimento estabelecimento) {
        String query = "UPDATE estabelecimento SET nome = ?, email = ?, senha = ?, telefone = ?, " +
                       "cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, " +
                       "estado = ?, statusCadastroId = ?, dataCadastro = ?, foto = ? WHERE estabelecimentoId = ?";
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
            statement.setInt(12, estabelecimento.getStatusCadastro().getStatuscadastroId());
            statement.setString(13, estabelecimento.getDataCadastro());
            statement.setString(14, estabelecimento.getFoto());
            statement.setInt(15, estabelecimento.getEstabelecimentoId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private int insert(Estabelecimento estabelecimento) {
        String query = "INSERT INTO estabelecimento (nome, email, senha, telefone, cep, rua, numero, complemento, " +
                       "bairro, cidade, estado, statusCadastroId, dataCadastro, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setInt(12, estabelecimento.getStatusCadastro().getStatuscadastroId());
            statement.setString(13, estabelecimento.getDataCadastro());
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
        if (estabelecimento.getEstabelecimentoId() != 0) {
            String query = "DELETE FROM estabelecimento WHERE estabelecimentoId = ?";
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, estabelecimento.getEstabelecimentoId());
                return statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }


    public List<Estabelecimento> findAll() {
        String query = "SELECT * FROM estabelecimento";
        List<Estabelecimento> list = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setEstabelecimentoId(rs.getInt("estabelecimentoId"));
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
                estabelecimento.getStatusCadastro().setStatuscadastroId(rs.getInt("statusCadastroId"));
                estabelecimento.setDataCadastro(rs.getString("dataCadastro"));
                estabelecimento.setFoto(rs.getString("foto"));
                list.add(estabelecimento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Buscar um estabelecimento por ID
    public Estabelecimento findById(int id) {
        String query = "SELECT " +
                       "e.estabelecimentoId, e.nome AS estabelecimentoNome, e.email AS estabelecimentoEmail, " +
                       "e.telefone AS estabelecimentoTelefone, e.rua AS estabelecimentoRua, e.bairro AS estabelecimentoBairro, " +
                       "e.cidade AS estabelecimentoCidade, e.estado AS estabelecimentoEstado, " +
                       "p.profissionalId, p.nome AS profissionalNome, " +
                       "s.servicoId, s.nome AS servicoNome, s.descricao AS servicoDescricao, s.preco AS servicoPreco, s.duracao AS servicoDuracao " +
                       "FROM estabelecimento e " +
                       "LEFT JOIN profissional p ON e.estabelecimentoId = p.estabelecimentoId " +
                       "LEFT JOIN profissionalservico ps ON p.profissionalId = ps.profissionalId " +
                       "LEFT JOIN servico s ON ps.servicoId = s.servicoId " +
                       "WHERE e.estabelecimentoId = ?";

        Estabelecimento estabelecimento = null;
        List<Profissional> profissionais = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    
                    if (estabelecimento == null) {
                        estabelecimento = new Estabelecimento();
                        estabelecimento.setEstabelecimentoId(rs.getInt("estabelecimentoId"));
                        estabelecimento.setNome(rs.getString("estabelecimentoNome"));
                        estabelecimento.setEmail(rs.getString("estabelecimentoEmail"));
                        estabelecimento.setTelefone(rs.getString("estabelecimentoTelefone"));
                        estabelecimento.setRua(rs.getString("estabelecimentoRua"));
                        estabelecimento.setBairro(rs.getString("estabelecimentoBairro"));
                        estabelecimento.setCidade(rs.getString("estabelecimentoCidade"));
                        estabelecimento.setEstado(rs.getString("estabelecimentoEstado"));
                    }

                   
                    int profissionalId = rs.getInt("profissionalId");
                    if (profissionalId > 0) { 
                        Profissional profissional = profissionais.stream()
                                .filter(p -> p.getProfissionalId() == profissionalId)
                                .findFirst()
                                .orElseGet(() -> {
                                    Profissional newProfissional = new Profissional();
                                    newProfissional.setProfissionalId(profissionalId);
                                    try {
										newProfissional.setNome(rs.getString("profissionalNome"));
									} catch (SQLException e) {
									
										e.printStackTrace();
									}
                                    profissionais.add(newProfissional);
                                    return newProfissional;
                                });

                       
                        int servicoId = rs.getInt("servicoId");
                        if (servicoId > 0) { 
                            Servico servico = new Servico();
                            servico.setServicoId(servicoId);
                            servico.setNome(rs.getString("servicoNome"));
                            servico.setDescricao(rs.getString("servicoDescricao"));
                            servico.setPreco(rs.getDouble("servicoPreco"));
                            servico.setDuracao(rs.getInt("servicoDuracao"));
                            profissional.addServico(servico);
                        }
                    }
                }
            }

            if (estabelecimento != null) {
                estabelecimento.setProfissionais(profissionais);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estabelecimento;
    }


    // Buscar serviços por ID do estabelecimento
    public List<Servico> findServicosByEstabelecimentoId(int estabelecimentoId) {
        String query = "SELECT DISTINCT s.servicoId, s.nome AS servico, s.descricao, s.preco, s.duracao " +
                       "FROM servico s " +
                       "JOIN profissionalservico ps ON s.servicoId = ps.servicoId " +
                       "JOIN profissional p ON ps.profissionalId = p.profissionalId " +
                       "WHERE p.estabelecimentoId = ?";

        List<Servico> servicos = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, estabelecimentoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Servico servico = new Servico();
                    servico.setServicoId(rs.getInt("servicoId"));
                    servico.setNome(rs.getString("servico"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.setPreco(rs.getDouble("preco"));
                    servico.setDuracao(rs.getInt("duracao"));
                    servicos.add(servico);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }
}
