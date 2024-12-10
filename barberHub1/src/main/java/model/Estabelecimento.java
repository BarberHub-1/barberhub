package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Estabelecimento {

    // Atributos principais
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
    private String dataCadastro;
    private String foto;

    // Relacionamentos e atributos auxiliares
    private StatusCadastro statusCadastro;
    private Servico servico;
    private List<Profissional> profissionais;

    // Construtores
    public Estabelecimento() {
        // Inicializando objetos e listas
        this.statusCadastro = new StatusCadastro();
        this.servico = new Servico();
        this.profissionais = new ArrayList<>();
    }

    public Estabelecimento(int estabelecimentoId, String nome, String email, String senha, String telefone, String cep,
                           String rua, String numero, String complemento, String bairro, String cidade, String estado,
                           int statusCadastro, String dataCadastro, String foto) {
        this(); // Chama o construtor padrão para inicializar objetos relacionados
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
        this.dataCadastro = dataCadastro;
        this.foto = foto;
    }

    public Estabelecimento(String nome, String email, String telefone, String rua, String bairro, String cidade,
                           String estado) {
        this(); // Inicializa objetos relacionados
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Métodos auxiliares
    public String[] toArray() {
        return new String[]{
                String.valueOf(this.estabelecimentoId),
                this.nome,
                this.email,
                this.senha,
                this.telefone,
                this.cep,
                this.rua,
                this.numero,
                this.complemento,
                this.bairro,
                this.cidade,
                this.estado,
                this.statusCadastro != null ? this.statusCadastro.toString() : "N/A",
                this.dataCadastro,
                this.foto
        };
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    // Getters e Setters
    public int getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(int estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public StatusCadastro getStatusCadastro() {
        return statusCadastro;
    }

    public void setStatusCadastro(StatusCadastro statusCadastroId) {
        this.statusCadastro = statusCadastroId;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
    }
}
