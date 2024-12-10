package model;

import java.util.Arrays;

public class TipoServico {
	
	private int tipoServicoId;
	private String tipoServicoNome;

	public TipoServico( int tipoServicoId, String tipoServicoNome ) {
		this.setTipoServicoId( tipoServicoId );
		this.setServicoNome( tipoServicoNome );
		
	}

	public TipoServico() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getTipoServicoId()),			
			String.valueOf(this.getServicoNome()),			
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setTipoServicoId(int tipoServicoId) {
		this.tipoServicoId = tipoServicoId;
	}

	public int getTipoServicoId() {
		return this.tipoServicoId;
	}

	public void setServicoNome(String tipoServicoNome) {
		this.tipoServicoNome = tipoServicoNome;
	}

	public String getServicoNome() {
		return this.tipoServicoNome;
	}

}
