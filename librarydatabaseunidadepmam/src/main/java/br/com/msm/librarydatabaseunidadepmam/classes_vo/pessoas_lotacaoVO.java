package br.com.msm.librarydatabaseunidadepmam.classes_vo;


public class pessoas_lotacaoVO {

    private int id_unidade;
    private String nomeLotacaoSuperior;
    private String pessoa_nome;
    private String funcao;
    private String telefone_corporativo;

    @Override
    public String toString() {
        return "pessoas_lotacaoVO{" +
                "id_unidade=" + id_unidade +
                ", nomeLotacaoSuperior='" + nomeLotacaoSuperior + '\'' +
                ", pessoa_nome='" + pessoa_nome + '\'' +
                ", funcao='" + funcao + '\'' +
                ", telefone_corporativo='" + telefone_corporativo + '\'' +
                '}';
    }

    public String getNomeLotacaoSuperior() {
        return nomeLotacaoSuperior;
    }

    public void setNomeLotacaoSuperior(String nomeLotacaoSuperior) {
        this.nomeLotacaoSuperior = nomeLotacaoSuperior;
    }

    public int getId_unidade() {
        return id_unidade;
    }

    public void setId_unidade(int id_unidade) {
        this.id_unidade = id_unidade;
    }

    public String getTelefone_corporativo() {
        return telefone_corporativo;
    }

    public void setTelefone_corporativo(String telefone_corporativo) {
        this.telefone_corporativo = telefone_corporativo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getPessoa_nome() {
        return pessoa_nome;
    }

    public void setPessoa_nome(String pessoa_nome) {
        this.pessoa_nome = pessoa_nome;
    }
}
