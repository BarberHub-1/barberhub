package model;

import java.util.Arrays;

public class StatusCadastro {
	private int statusCadastroId;
	private String descricao;

	public StatusCadastro( int statusCadastroId, String descricao ) {
		this.setStatusCadastroId( statusCadastroId );
		this.setDescricao( descricao );
	}

	public StatusCadastro() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getStatusCadastroId()),			
			String.valueOf(this.getDescricao())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setStatusCadastroId(int statusCadastroId) {
		this.statusCadastroId = statusCadastroId;
	}

	public int getStatusCadastroId() {
		return this.statusCadastroId;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
