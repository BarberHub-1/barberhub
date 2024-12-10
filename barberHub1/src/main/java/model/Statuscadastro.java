package model;

import java.util.Arrays;

public class StatusCadastro {
	private int statusCadastroId;
	private String descricao;

	public StatusCadastro( int statusCadastroId, String descricao ) {
		this.setStatuscadastroId( statusCadastroId );
		this.setDescricao( descricao );
	}

	public StatusCadastro() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getStatuscadastroId()),			
			String.valueOf(this.getDescricao())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setStatuscadastroId(int statusCadastroId) {
		this.statusCadastroId = statusCadastroId;
	}

	public int getStatuscadastroId() {
		return this.statusCadastroId;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
