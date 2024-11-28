package model;

import java.util.Arrays;

public class Sistema {
	private int sistemaId;
	private String nome;
	private String logo;
	private String email;
	private String telefone;

	public Sistema( int sistemaId, String nome, String logo, String email, String telefone ) {
		this.setSistemaid( sistemaId );
		this.setNome( nome );
		this.setLogo( logo );
		this.setEmail( email );
		this.setTelefone( telefone );
	}

	public Sistema() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getSistemaid()),			
			String.valueOf(this.getNome()),			
			String.valueOf(this.getLogo()),			
			String.valueOf(this.getEmail()),			
			String.valueOf(this.getTelefone())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setSistemaid(int sistemaId) {
		this.sistemaId = sistemaId;
	}

	public int getSistemaid() {
		return this.sistemaId;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone() {
		return this.telefone;
	}
}
