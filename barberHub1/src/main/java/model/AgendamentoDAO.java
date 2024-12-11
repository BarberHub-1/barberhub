package model;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    private DBConnection dbConnection;

    public AgendamentoDAO() {
        this.dbConnection = new DBConnection();
    }

    
    public int save(Agendamento agendamento) {
        if (agendamento.getAgendamentoId() > 0) {
            return this.update(agendamento);
        } else {
            return this.insert(agendamento);
        }
    }

    // Inserir um novo Agendamento
    private int insert(Agendamento agendamento) {
        String query = "INSERT INTO agendamento (estabelecimentoId, profissionalId, servicoId, clienteId, data, hora, preco, status) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Configurar os parâmetros da query
            System.out.println("Inserindo agendamento com os seguintes dados:");
            System.out.println("Estabelecimento ID: " + agendamento.getEstabelecimento().getEstabelecimentoId());
            System.out.println("Profissional ID: " + agendamento.getProfissional().getProfissionalId());
            System.out.println("Serviço ID: " + agendamento.getServico().getServicoId());
            System.out.println("Cliente ID: " + agendamento.getCliente().getClienteId());
            System.out.println("Data: " + agendamento.getData());
            System.out.println("Hora: " + agendamento.getHora());
            System.out.println("Preço: " + agendamento.getPreco());
            System.out.println("Status: " + agendamento.getStatus());

            statement.setInt(1, agendamento.getEstabelecimento().getEstabelecimentoId());
            statement.setInt(2, agendamento.getProfissional().getProfissionalId());
            statement.setInt(3, agendamento.getServico().getServicoId());
            statement.setInt(4, agendamento.getCliente().getClienteId());
            statement.setString(5, agendamento.getData());
            statement.setString(6, agendamento.getHora());
            statement.setDouble(7, agendamento.getPreco());
            statement.setString(8, agendamento.getStatus());

            // Executar a query
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("Agendamento inserido com sucesso. ID gerado: " + generatedKeys.getInt(1));
                        return generatedKeys.getInt(1); // Retornar o ID gerado
                    }
                }
            }
            return 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir agendamento: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // Atualizar um Agendamento existente
    public int update(Agendamento agendamento) {
        String query = "UPDATE agendamento SET estabelecimentoId = ?, profissionalId = ?, servicoId = ?, clienteId = ?, " +
                       "data = ?, hora = ?, preco = ?, status = ? WHERE agendamentoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Configurar os parâmetros da query
            statement.setInt(1, agendamento.getAgendamentoId());
            statement.setInt(2, agendamento.getProfissional().getProfissionalId());
            statement.setInt(3, agendamento.getServico().getServicoId());
            statement.setInt(4, agendamento.getCliente().getClienteId());
            statement.setString(5, agendamento.getData());
            statement.setString(6, agendamento.getHora());
            statement.setDouble(7, agendamento.getPreco());
            statement.setString(8, agendamento.getStatus());
            statement.setInt(9, agendamento.getAgendamentoId());

            // Executar a query
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar agendamento: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // Deletar um Agendamento
    public int delete(Agendamento agendamento) {
        return deleteById(agendamento.getAgendamentoId());
    }

    // Deletar um Agendamento pelo ID
    public int deleteById(int id) {
        String query = "DELETE FROM agendamento WHERE agendamentoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar agendamento: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    // Buscar todos os Agendamentos
    public List<Agendamento> findAll() {
        String query = "SELECT * FROM agendamento";
        List<Agendamento> agendamentos = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Agendamento agendamento = mapResultSetToAgendamento(rs);
                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os agendamentos: " + e.getMessage());
            e.printStackTrace();
        }
        return agendamentos;
    }

    // Buscar um Agendamento pelo ID
    public Agendamento findById(int id) {
        String query = "SELECT * FROM agendamento WHERE agendamentoId = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAgendamento(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar agendamento pelo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Buscar Agendamentos por Estabelecimento
    public List<Agendamento> findByEstabelecimentoId(int estabelecimentoId) {
        String query = "SELECT * FROM agendamento WHERE estabelecimentoId = ?";
        List<Agendamento> agendamentos = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, estabelecimentoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento agendamento = mapResultSetToAgendamento(rs);
                    agendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar agendamentos por estabelecimento: " + e.getMessage());
            e.printStackTrace();
        }
        return agendamentos;
    }

    // Método auxiliar para mapear um ResultSet em um objeto Agendamento
    private Agendamento mapResultSetToAgendamento(ResultSet rs) throws SQLException {
        Agendamento agendamento = new Agendamento();

        Estabelecimento estabelecimento = new Estabelecimento();
        int estabelecimentoId = rs.getInt("estabelecimentoId");
        if (estabelecimentoId > 0) {
            estabelecimento.setEstabelecimentoId(estabelecimentoId);


            // Não inicialize profissionais como lista vazia
            estabelecimento.setProfissionais(null);
        }

        Profissional profissional = new Profissional();
        int profissionalId = rs.getInt("profissionalId");
        if (profissionalId > 0) {
            profissional.setProfissionalId(profissionalId);
            profissional.setServicos(null); // Não inicialize serviços como lista vazia
        }

        Servico servico = new Servico();
        int servicoId = rs.getInt("servicoId");
        if (servicoId > 0) {
            servico.setServicoId(servicoId);
            servico.setPreco(rs.getDouble("preco"));
//            servico.setDuracao(rs.getInt("duracao"));
        }

        Cliente cliente = new Cliente();
        int clienteId = rs.getInt("clienteId");
        if (clienteId > 0) {
            cliente.setClienteId(clienteId);
        }

        agendamento.setAgendamentoId(rs.getInt("agendamentoId"));
        agendamento.setEstabelecimento(estabelecimento);
        agendamento.setProfissional(profissional);
        agendamento.setServico(servico);
        agendamento.setCliente(cliente);
        agendamento.setData(rs.getString("data"));
        agendamento.setHora(rs.getString("hora"));
        agendamento.setPreco(rs.getDouble("preco"));
        agendamento.setStatus(rs.getString("status"));

        return agendamento;
    }
}
