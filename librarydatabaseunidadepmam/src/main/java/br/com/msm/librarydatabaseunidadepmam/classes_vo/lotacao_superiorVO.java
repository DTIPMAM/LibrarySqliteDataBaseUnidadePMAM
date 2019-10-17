package br.com.msm.librarydatabaseunidadepmam.classes_vo;


public class lotacao_superiorVO {

    private int id_categoria;
    private int cod_parent;
    private String nomeLotacaoSuperior;


    public lotacao_superiorVO() {
    }

    public String getnomeLotacaoSuperior() {
        return nomeLotacaoSuperior;
    }

    public void setnomeLotacaoSuperior(String nomeLotacaoSuperior) {
        this.nomeLotacaoSuperior = nomeLotacaoSuperior;
    }

    public lotacao_superiorVO(int id_categoria, int cod_parent, String nomeLotacaoSuperior) {
        this.id_categoria = id_categoria;
        this.cod_parent = cod_parent;
        this.nomeLotacaoSuperior = nomeLotacaoSuperior;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getCod_parent() {
        return cod_parent;
    }

    public void setCod_parent(int cod_parent) {
        this.cod_parent = cod_parent;
    }
}
