package model;

import java.util.Arrays;

public class Statuscadastro {
	private int statusCadastroId;
	private String descricao;

	public Statuscadastro( int statusCadastroId, String descricao ) {
		this.setStatuscadastroid( statusCadastroId );
		this.setDescricao( descricao );
	}

	public Statuscadastro() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getStatuscadastroid()),			
			String.valueOf(this.getDescricao())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setStatuscadastroid(int statusCadastroId) {
		this.statusCadastroId = statusCadastroId;
	}

	public int getStatuscadastroid() {
		return this.statusCadastroId;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
