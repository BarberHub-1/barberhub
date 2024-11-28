package model;

import java.util.Arrays;

public class Pagamento {
	private int pagamentoId;
	private int agendamentoId;
	private double valor;
	private String metodo;
	private String status;

	public Pagamento( int pagamentoId, int agendamentoId, double valor, String metodo, String status ) {
		this.setPagamentoid( pagamentoId );
		this.setAgendamentoid( agendamentoId );
		this.setValor( valor );
		this.setMetodo( metodo );
		this.setStatus( status );
	}

	public Pagamento() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getPagamentoid()),			
			String.valueOf(this.getAgendamentoid()),			
			String.valueOf(this.getValor()),			
			String.valueOf(this.getMetodo()),			
			String.valueOf(this.getStatus())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setPagamentoid(int pagamentoId) {
		this.pagamentoId = pagamentoId;
	}

	public int getPagamentoid() {
		return this.pagamentoId;
	}

	public void setAgendamentoid(int agendamentoId) {
		this.agendamentoId = agendamentoId;
	}

	public int getAgendamentoid() {
		return this.agendamentoId;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return this.valor;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getMetodo() {
		return this.metodo;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
