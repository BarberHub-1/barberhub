package model;

import java.util.Arrays;

public class ProfissionalServico {
	private int profissionalId;
	private int servicoId;

	public ProfissionalServico( int profissionalId, int servicoI ) {
		this.setProfissionalId( profissionalId );
		this.setServicoId( servicoId );
	}

	public ProfissionalServico() {
		
	}

	public String[] toArray() {
		return new String[] {
			String.valueOf(this.getProfissionaIdl()),			
			String.valueOf(this.getServicoId())
		};
	}

	public String toString() {
		return Arrays.toString(this.toArray());
	}

	public void setProfissionalId(int profissionalId) {
		this.profissionalId = profissionalId;
	}

	public int getProfissionaIdl() {
		return this.profissionalId;
	}

	public void setServicoId(int servicoId) {
		this.servicoId = servicoId;
	}

	public int getServicoId() {
		return this.servicoId;
	}
}
