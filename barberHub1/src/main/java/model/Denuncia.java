package model;

import java.util.Arrays;

public class Denuncia {
	private int denunciaId;
	private int estabelecimentoId;
	private int clienteId;
	private String descricao;
	private String dataDenuncia;

	public Denuncia( int denunciaId, int estabelecimentoId, int clienteId, String descricao, String dataDenuncia ) {
		this.setDenunciaid( denunciaId );
		this.setEstabelecimentoid( estabelecimentoId );
		this.setClienteid( clienteId );
		this.setDescricao( descricao );
		this.setDatadenuncia( dataDenuncia );
	}

	public Denuncia() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getDenunciaid()),			
			String.valueOf(this.getEstabelecimentoid()),			
			String.valueOf(this.getClienteid()),			
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

	public void setEstabelecimentoid(int estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}

	public int getEstabelecimentoid() {
		return this.estabelecimentoId;
	}

	public void setClienteid(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getClienteid() {
		return this.clienteId;
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
