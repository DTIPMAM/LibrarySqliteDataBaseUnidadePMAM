package br.com.msm.librarydatabaseunidadepmam.classes_vo;




public class retorno {

    private String retorno;

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public int getTotalPager() {
        return totalPager;
    }

    public void setTotalPager(int totalPager) {
        this.totalPager = totalPager;
    }

    private String item_count;
    private int totalPager;
}
