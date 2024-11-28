package model;

import java.util.Arrays;

public class TipoServico {
	private int tipoServicoId;
	private String servico;

	public TipoServico( int tipoServicoId, String servico ) {
		this.setTiposervicoid( tipoServicoId );
		this.setServico( servico );
		
	}

	public TipoServico() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getTiposervicoid()),			
			String.valueOf(this.getServico()),			
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setTiposervicoid(int tipoServicoId) {
		this.tipoServicoId = tipoServicoId;
	}

	public int getTiposervicoid() {
		return this.tipoServicoId;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getServico() {
		return this.servico;
	}

}
