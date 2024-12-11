package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Profissional {
    private int profissionalId;
    private Estabelecimento estabelecimento;
    private String nome;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String foto;
    private List<Servico> servicos;

    public Profissional() {
        this.servicos = new ArrayList<>(); 
    }

    public Profissional(int profissionalId, Estabelecimento estabelecimento, String nome, String cep, String rua, String numero, 
            String complemento, String bairro, String cidade, String estado, String foto) {
        this.profissionalId = profissionalId;
        this.estabelecimento = estabelecimento;
        this.nome = nome;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.foto = foto;
        this.servicos = new ArrayList<>(); 
    }

    public String[] toArray() {
        return new String[] {
            String.valueOf(this.profissionalId),
            this.estabelecimento != null ? String.valueOf(this.estabelecimento.getEstabelecimentoId()) : "N/A",
            this.nome,
            this.cep,
            this.rua,
            this.numero,
            this.complemento,
            this.bairro,
            this.cidade,
            this.estado,
            this.foto
        };
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    // Getters e Setters
    public int getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(int profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Estabelecimento getEstabelecimento() {
        if (this.estabelecimento == null) {
            this.estabelecimento = new Estabelecimento();
        }
        return this.estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public void addServico(Servico servico) {
        if (this.servicos == null) {
            this.servicos = new ArrayList<>();
        }
        this.servicos.add(servico);
    }
}
