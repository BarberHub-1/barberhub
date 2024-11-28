package model;

import java.util.Arrays;

public class Cliente {
	private int clienteId;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	private String cep;
	private String rua;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String foto;

	public Cliente( int clienteId, String nome, String email, String senha, String telefone, String cep, String rua, String numero, String complemento, String bairro, String cidade, String estado, String foto ) {
		this.setClienteid( clienteId );
		this.setNome( nome );
		this.setEmail( email );
		this.setSenha( senha );
		this.setTelefone( telefone );
		this.setCep( cep );
		this.setRua( rua );
		this.setNumero( numero );
		this.setComplemento( complemento );
		this.setBairro( bairro );
		this.setCidade( cidade );
		this.setEstado( estado );
		this.setFoto( foto );
	}

	public Cliente() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getClienteid()),			
			String.valueOf(this.getNome()),			
			String.valueOf(this.getEmail()),			
			String.valueOf(this.getSenha()),			
			String.valueOf(this.getTelefone()),			
			String.valueOf(this.getCep()),			
			String.valueOf(this.getRua()),			
			String.valueOf(this.getNumero()),			
			String.valueOf(this.getComplemento()),			
			String.valueOf(this.getBairro()),			
			String.valueOf(this.getCidade()),			
			String.valueOf(this.getEstado()),			
			String.valueOf(this.getFoto())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setClienteid(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getClienteid() {
		return this.clienteId;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCep() {
		return this.cep;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getRua() {
		return this.rua;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFoto() {
		return this.foto;
	}
}
