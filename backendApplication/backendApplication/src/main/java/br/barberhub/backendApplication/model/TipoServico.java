package br.barberhub.backendApplication.model;

public enum TipoServico {
	CORTE_DE_CABELO("Corte de Cabelo"), 
	BARBA("Barba"), 
	SOBRANCELHA("Sobrancelha"), 
	HIDRATACAO("Hidratação"), 
	LUZES("Luzes");

	private final String displayName;

	TipoServico(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
