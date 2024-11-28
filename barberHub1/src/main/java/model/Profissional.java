package model;

import java.util.Arrays;

public class Profissional {
	private int profissionalId;
	private int estabelecimentoId;
	private String nome;
	private String servico;
	private String cep;
	private String rua;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String foto;

	public Profissional( int profissionalId, int estabelecimentoId, String nome, String servicoId, String cep, String rua, String numero, String complemento, String bairro, String cidade, String estado, String foto ) {
		this.setProfissionalid( profissionalId );
		this.setEstabelecimentoid( estabelecimentoId );
		this.setNome( nome );
		this.setServico( servico );
		this.setCep( cep );
		this.setRua( rua );
		this.setNumero( numero );
		this.setComplemento( complemento );
		this.setBairro( bairro );
		this.setCidade( cidade );
		this.setEstado( estado );
		this.setFoto( foto );
	}

	public Profissional(String string, String string2, String string3, double d, int i) {
		
	}

	public Profissional() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getProfissionalid()),			
			String.valueOf(this.getEstabelecimentoid()),			
			String.valueOf(this.getNome()),			
			String.valueOf(this.getServico()),			
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

	public void setProfissionalid(int profissionalId) {
		this.profissionalId = profissionalId;
	}

	public int getProfissionalid() {
		return this.profissionalId;
	}

	public void setEstabelecimentoid(int estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}

	public int getEstabelecimentoid() {
		return this.estabelecimentoId;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getServico() {
		return this.servico;
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
