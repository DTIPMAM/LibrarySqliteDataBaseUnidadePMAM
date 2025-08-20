package br.com.msm.librarydatabaseunidadepmam.classes_vo;


public class pessoas_lotacaoVO {

    private int _id;
    private int id_unidade;
    private String nomeLotacaoSuperior;
    private String pessoa_nome;
    private String funcao;
    private String telefone_corporativo;

    public pessoas_lotacaoVO(int id_unidade, String nomeLotacaoSuperior, String pessoa_nome, String funcao, String telefone_corporativo) {
        this.id_unidade = id_unidade;
        this.nomeLotacaoSuperior = nomeLotacaoSuperior;
        this.pessoa_nome = pessoa_nome;
        this.funcao = funcao;
        this.telefone_corporativo = telefone_corporativo;
    }

    public pessoas_lotacaoVO() {
    }

    @Override
    public String toString() {
        return "pessoas_lotacaoVO{" +
                "_id=" + _id +
                ", id_unidade=" + id_unidade +
                ", id_unidade=" + id_unidade +
                ", nomeLotacaoSuperior='" + nomeLotacaoSuperior + '\'' +
                ", pessoa_nome='" + pessoa_nome + '\'' +
                ", funcao='" + funcao + '\'' +
                ", telefone_corporativo='" + telefone_corporativo + '\'' +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getId_unidade() {
        return id_unidade;
    }

    public void setId_unidade(int id_unidade) {
        this.id_unidade = id_unidade;
    }

    public String getNomeLotacaoSuperior() {
        return nomeLotacaoSuperior;
    }

    public void setNomeLotacaoSuperior(String nomeLotacaoSuperior) {
        this.nomeLotacaoSuperior = nomeLotacaoSuperior;
    }

    public String getPessoa_nome() {
        return pessoa_nome;
    }

    public void setPessoa_nome(String pessoa_nome) {
        this.pessoa_nome = pessoa_nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getTelefone_corporativo() {
        return telefone_corporativo;
    }

    public void setTelefone_corporativo(String telefone_corporativo) {
        this.telefone_corporativo = telefone_corporativo;
    }
}
