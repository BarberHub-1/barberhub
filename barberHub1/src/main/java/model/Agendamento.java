package model;

import java.util.Arrays;
import java.sql.Time;

public class Agendamento {
	private int agendamentoId;
	private int estabelecimentoId;
	private int profissionalId;
	private int servicoId;
	private int clienteId;
	private String data;
	private String hora;
	private double preco;
	private double desconto;
	private String status;

	public Agendamento( int agendamentoId, int estabelecimentoId, int profissionalId, int servicoId, int clienteId, String data, String hora, double preco, double desconto, String status ) {
		this.setAgendamentoid( agendamentoId );
		this.setEstabelecimentoid( estabelecimentoId );
		this.setProfissionalid( profissionalId );
		this.setServicoid( servicoId );
		this.setClienteid( clienteId );
		this.setData( data );
		this.setHora( hora );
		this.setPreco( preco );
		this.setDesconto( desconto );
		this.setStatus( status );
	}

	public Agendamento() {
		
	}

	public Agendamento(int estabelecimentoId, int profissionalId, int servicoId, String data, String hora2, double preco, String status) {
        this.estabelecimentoId = estabelecimentoId;
        this.profissionalId = profissionalId;
        this.servicoId = servicoId;
        this.data = data;
        this.hora = hora2;
        this.preco = preco;
        this.status = status;
    }

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getAgendamentoid()),			
			String.valueOf(this.getEstabelecimentoid()),			
			String.valueOf(this.getProfissionalid()),			
			String.valueOf(this.getServicoid()),			
			String.valueOf(this.getClienteid()),			
			String.valueOf(this.getData()),			
			String.valueOf(this.getHora()),			
			String.valueOf(this.getPreco()),			
			String.valueOf(this.getDesconto()),			
			String.valueOf(this.getStatus())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setAgendamentoid(int agendamentoId) {
		this.agendamentoId = agendamentoId;
	}

	public int getAgendamentoid() {
		return this.agendamentoId;
	}

	public void setEstabelecimentoid(int estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}

	public int getEstabelecimentoid() {
		return this.estabelecimentoId;
	}

	public void setProfissionalid(int profissionalId) {
		this.profissionalId = profissionalId;
	}

	public int getProfissionalid() {
		return this.profissionalId;
	}

	public void setServicoid(int servicoId) {
		this.servicoId = servicoId;
	}

	public int getServicoid() {
		return this.servicoId;
	}

	public void setClienteid(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getClienteid() {
		return this.clienteId;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getHora() {
		return this.hora;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getPreco() {
		return this.preco;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getDesconto() {
		return this.desconto;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
