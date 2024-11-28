package model;

import java.util.Arrays;

public class Estabelecimento {
	private int estabelecimentoId;
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
	private int statusCadastroId;
	private String dataCadastro;
	private String foto;
	private String profissionalNome;
	private String profissionalServico;
	private String servicoNome;
	private double servicoPreco;
	private int servicoDuracao;

	public Estabelecimento( int estabelecimentoId, String nome, String email, String senha, String telefone, String cep, String rua, String numero, String complemento, String bairro, String cidade, String estado, int statusCadastroId, String dataCadastro, String foto ) {
		this.setEstabelecimentoid( estabelecimentoId );
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
		this.setStatuscadastroid( statusCadastroId );
		this.setDatacadastro( dataCadastro );
		this.setFoto( foto );
	}
	
	

	public Estabelecimento(int estabelecimentoId, String nome, String email, String senha, String telefone, String cep,
			String rua, String numero, String complemento, String bairro, String cidade, String estado,
			int statusCadastroId, String dataCadastro, String foto, String profissionalNome, String profissionalServico,
			String servicoNome, double servicoPreco, int servicoDuracao) {
		super();
		this.estabelecimentoId = estabelecimentoId;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.cep = cep;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.statusCadastroId = statusCadastroId;
		this.dataCadastro = dataCadastro;
		this.foto = foto;
		this.profissionalNome = profissionalNome;
		this.profissionalServico = profissionalServico;
		this.servicoNome = servicoNome;
		this.servicoPreco = servicoPreco;
		this.servicoDuracao = servicoDuracao;
	}



	public Estabelecimento(String nome, String email, String telefone, String rua, String bairro,
            String cidade, String estado, String profissionalNome, String profissionalServico,
            String servicoNome, double servicoPreco, int servicoDuracao) {
			this.nome = nome;
			this.email = email;
			this.telefone = telefone;
			this.rua = rua;
			this.bairro = bairro;
			this.cidade = cidade;
			this.estado = estado;
			this.profissionalNome = profissionalNome;
			this.profissionalServico = profissionalServico;
			this.servicoNome = servicoNome;
			this.servicoPreco = servicoPreco;
			this.servicoDuracao = servicoDuracao;
		}


	public Estabelecimento() {
	
	}



	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getEstabelecimentoid()),			
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
			String.valueOf(this.getStatuscadastroid()),			
			String.valueOf(this.getDatacadastro()),			
			String.valueOf(this.getFoto())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
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

	public void setStatuscadastroid(int statusCadastroId) {
		this.statusCadastroId = statusCadastroId;
	}

	public int getStatuscadastroid() {
		return this.statusCadastroId;
	}

	public void setDatacadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDatacadastro() {
		return this.dataCadastro;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFoto() {
		return this.foto;
	}

	public String getProfissionalNome() {
		return profissionalNome;
	}

	public void setProfissionalNome(String profissionalNome) {
		this.profissionalNome = profissionalNome;
	}

	public String getProfissionalServico() {
		return profissionalServico;
	}

	public void setProfissionalServico(String profissionalServico) {
		this.profissionalServico = profissionalServico;
	}

	public String getServicoNome() {
		return servicoNome;
	}

	public void setServicoNome(String servicoNome) {
		this.servicoNome = servicoNome;
	}

	public double getServicoPreco() {
		return servicoPreco;
	}

	public void setServicoPreco(double servicoPreco) {
		this.servicoPreco = servicoPreco;
	}

	public int getServicoDuracao() {
		return servicoDuracao;
	}

	public void setServicoDuracao(int servicoDuracao) {
		this.servicoDuracao = servicoDuracao;
	}
	
	
}
