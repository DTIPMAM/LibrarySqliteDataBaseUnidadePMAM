package br.com.msm.librarydatabaseunidadepmam;

/**
 * Created by MSM on 12/05/2017.
 */

public class ObjJsonPessoaFuncao {

    private  String listPessoaFunc;
    private  String listFunc;
    private  String listcontato;


    public ObjJsonPessoaFuncao(String listPessoaFunc, String listcontato, String listFunc){
        this.listPessoaFunc = listPessoaFunc;
        this.listFunc = listFunc;
        this.listcontato = listcontato;
    }

    public String getListPessoaFunc() {
        return listPessoaFunc;
    }

    public void setListPessoaFunc(String listPessoaFunc) {
        this.listPessoaFunc = listPessoaFunc;
    }

    public String getListFunc() {
        return listFunc;
    }

    public void setListFunc(String listFunc) {
        this.listFunc = listFunc;
    }

    public String getListcontato() {
        return listcontato;
    }

    public void setListcontato(String listcontato) {
        this.listcontato = listcontato;
    }
}
