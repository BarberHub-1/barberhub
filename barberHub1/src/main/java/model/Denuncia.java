package model;

import java.util.Arrays;

public class Denuncia {
	private int denunciaId;
	private Estabelecimento estabelecimento;
	private Cliente cliente;
	private String descricao;
	private String dataDenuncia;

	public Denuncia( int denunciaId, String descricao, String dataDenuncia ) {
		this.setDenunciaid( denunciaId );
//		this.setEstabelecimentoid( estabelecimentoId );
//		this.setClienteid( clienteId );
		this.setDescricao( descricao );
		this.setDatadenuncia( dataDenuncia );
	}

	public Denuncia() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getDenunciaid()),			
			String.valueOf(this.getEstabelecimento()),			
			String.valueOf(this.getCliente()),			
			String.valueOf(this.getDescricao()),			
			String.valueOf(this.getDatadenuncia())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setDenunciaid(int denunciaId) {
		this.denunciaId = denunciaId;
	}

	public int getDenunciaid() {
		return this.denunciaId;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return this.estabelecimento;
	}

	public void setClienteid(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDatadenuncia(String dataDenuncia) {
		this.dataDenuncia = dataDenuncia;
	}

	public String getDatadenuncia() {
		return this.dataDenuncia;
	}
}
