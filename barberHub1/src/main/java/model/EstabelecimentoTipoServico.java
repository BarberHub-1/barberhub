package model;

public class EstabelecimentoTipoServico {
    private int estabelecimentoId;
    private int tipoServicoId;

   
    private Estabelecimento estabelecimento;
    private TipoServico tipoServico;

    
    public EstabelecimentoTipoServico(int estabelecimentoId, int tipoServicoId) {
        this.estabelecimentoId = estabelecimentoId;
        this.tipoServicoId = tipoServicoId;
    }

    
    public int getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(int estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    public int getTipoServicoId() {
        return tipoServicoId;
    }

    public void setTipoServicoId(int tipoServicoId) {
        this.tipoServicoId = tipoServicoId;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }
}
