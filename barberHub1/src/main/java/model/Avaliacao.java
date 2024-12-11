package model;

import java.util.Arrays;

public class Avaliacao {
	private int avaliacaoId;
	private Agendamento agendamento;
	private int avaliado;
	private int nota;
	private String comentario;
	private String dataAvaliacao;

	public Avaliacao( int avaliacaoId, int avaliado, int nota, String comentario, String dataAvaliacao ) {
		this.setAvaliacaoid( avaliacaoId );
		this.setAvaliado( avaliado );
		this.setNota( nota );
		this.setComentario( comentario );
		this.setDataAvaliacao( dataAvaliacao );
	}

	public Avaliacao() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getAvaliacaoid()),		
//			String.valueOf(this.getAgendamentoid()),			
			String.valueOf(this.getAvaliado()),			
			String.valueOf(this.getNota()),			
			String.valueOf(this.getComentario()),			
			String.valueOf(this.getDataAvaliacao())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setAvaliacaoid(int avaliacaoId) {
		this.avaliacaoId = avaliacaoId;
	}

	public int getAvaliacaoid() {
		return this.avaliacaoId;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public Agendamento getAgendamento() {
		return this.agendamento;
	}

	public void setAvaliado(int avaliado) {
		this.avaliado = avaliado;
	}

	public int getAvaliado() {
		return this.avaliado;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public int getNota() {
		return this.nota;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setDataAvaliacao(String dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public String getDataAvaliacao() {
		return this.dataAvaliacao;
	}
}
