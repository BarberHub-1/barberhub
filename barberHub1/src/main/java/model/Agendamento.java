package model;

import java.util.Arrays;

public class Agendamento {
    private int agendamentoId;
    private Estabelecimento estabelecimento;
    private Profissional profissional;
    private Servico servico;
    private Cliente cliente;
    private String data;
    private String hora;
    private double preco;
    private double desconto;
    private String status;

    // Construtor com inicialização direta
    public Agendamento(Estabelecimento estabelecimento, Profissional profissional, Servico servico, Cliente cliente, 
            String data, String hora, double preco, String status) {
			this.estabelecimento = estabelecimento;
			this.profissional = profissional;
			this.servico = servico;
			this.cliente = cliente;
			this.data = data;
			this.hora = hora;
			this.preco = preco;
			this.status = status;
			}


    // Construtor vazio
    public Agendamento() {}

    
    

	public String[] toArray() {
        return new String[] {
            String.valueOf(this.agendamentoId),
            this.estabelecimento != null ? this.estabelecimento.toString() : "null",
            this.profissional != null ? this.profissional.toString() : "null",
            this.servico != null ? this.servico.toString() : "null",
            this.cliente != null ? this.cliente.toString() : "null",
            this.data,
            this.hora,
            String.valueOf(this.preco),
            String.valueOf(this.desconto),
            this.status
        };
    }

    // Método toString
    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

	public int getAgendamentoId() {
		return agendamentoId;
	}

	public void setAgendamentoId(int agendamentoId) {
		this.agendamentoId = agendamentoId;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
}
