package model;

public class ProfissionalServico {
    private int profissionalId;
    private int servicoId;

    public ProfissionalServico() {}

    public ProfissionalServico(int profissionalId, int servicoId) {
        this.profissionalId = profissionalId;
        this.servicoId = servicoId;
    }

    // Getters e Setters
    public int getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(int profissionalId) {
        this.profissionalId = profissionalId;
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    @Override
    public String toString() {
        return "ProfissionalServico{" +
                "profissionalId=" + profissionalId +
                ", servicoId=" + servicoId +
                '}';
    }

	public String[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
}
