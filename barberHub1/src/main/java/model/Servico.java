package model;

import java.util.Arrays;

public class Servico {
	private int servicoId;
	private String nome;
	private String descricao;
	private int tipoServicoId;
	private String tipoServicoNome;
	private double preco;
	private int duracao;

	public Servico( int servicoId, String nome, String descricao, double preco, int duracao ) {
		this.setServicoId( servicoId );
		this.setNome( nome );
		this.setDescricao( descricao );
		this.setPreco( preco );
		this.setDuracao( duracao );
	}

	public Servico() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getServicoId()),			
			String.valueOf(this.getNome()),			
			String.valueOf(this.getDescricao()),					
			String.valueOf(this.getPreco()),			
			String.valueOf(this.getDuracao())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setServicoId(int servicoId) {
		this.servicoId = servicoId;
	}

	public int getServicoId() {
		return this.servicoId;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getPreco() {
		return this.preco;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public int getDuracao() {
		return this.duracao;
	}

	public int getTipoServicoId() {
		return this.tipoServicoId;
	}

	public void setTipoServicoId(int tipoServicoId) {
		this.tipoServicoId = tipoServicoId;
	}

	public String getTipoServicoNome() {
		return this.tipoServicoNome;
	}

	public void setTipoServicoNome(String tipoServicoNome) {
		this.tipoServicoNome = tipoServicoNome;
	}
}
