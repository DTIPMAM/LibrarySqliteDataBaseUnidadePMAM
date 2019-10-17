package br.com.msm.librarydatabaseunidadepmam.classes_vo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

public class lotacoesVO implements Parcelable {

    private int ID;
    private int id_categoria;
    private int cod_parent;
    private String nomeLotacaoSuperior;
    private String nome;
    private String sigla;
    private String endereco;
    private String email;
    private String tel;
    private String telSA;
    private String detalhes;
    private double lng;
    private double lat;
    private float nro_radio;
    private String distancia;


    public static final Creator<lotacoesVO>
            CREATOR = new Creator<lotacoesVO>() {

        public lotacoesVO createFromParcel(Parcel in) {
            return new lotacoesVO(in);
        }

        public lotacoesVO[] newArray(int size) {
            return new lotacoesVO[size];
        }
    };

    public lotacoesVO(lotacoesVO vo) {
        this.ID = vo.getID();
        this.id_categoria = vo.getId_categoria();
        this.cod_parent = vo.getCod_parent();
        this.nomeLotacaoSuperior = vo.getNomeLotacaoSuperior();
        this.nome = vo.getNome();
        this.sigla = vo.getSigla();
        this.endereco = vo.getEndereco();
        this.email = vo.getEmail();
        this.tel = vo.getTel();
        this.telSA = vo.getTelSA();
        this.detalhes = vo.getDetalhes();
        this.lng = vo.getLng();
        this.lat = vo.getLat();
        this.nro_radio = vo.getNro_radio();
        this.distancia = vo.getDistancia();
    }

    public lotacoesVO(JsonObject js) {
        //  "_id", "id_categoria", "cod_parent","nomeLotacaoSuperior", "nome","endereco",
        //            "descr","email_institucional","fone1","fone2", "latitude", "longitude","nro_radio", "sigla"};
        this.ID = Integer.parseInt(js.get("_id").getAsString());
        this.id_categoria = Integer.parseInt(js.get("id_categoria").getAsString());
        this.cod_parent = Integer.parseInt(js.get("cod_parent").getAsString());
        this.nomeLotacaoSuperior =  js.get("nomeLotacaoSuperior").getAsString();
        this.nome =  js.get("nome").getAsString();
        this.sigla =  js.get("sigla").getAsString();
        this.endereco =  js.get("endereco").getAsString();
        this.email =  js.get("email_institucional").getAsString();
        this.tel =  js.get("fone1").getAsString();
        this.telSA =  js.get("fone2").getAsString();
        this.detalhes =  js.get("descr").getAsString();
        this.lng = Double.parseDouble(js.get("longitude").getAsString());
        this.lat = Double.parseDouble(js.get("latitude").getAsString());
        this.nro_radio = Float.parseFloat(js.get("nro_radio").getAsString());
     //   this.distancia =  js.get("distancia").getAsString();
    }
    public lotacoesVO(Parcel in) {
        this.ID = in.readInt();
        this.id_categoria = in.readInt();
        this.cod_parent = in.readInt();
        this.nomeLotacaoSuperior = in.readString();
        this.nome = in.readString();
        this.sigla = in.readString();
        this.endereco = in.readString();
        this.email = in.readString();
        this.tel = in.readString();
        this.telSA = in.readString();
        this.detalhes = in.readString();
        this.lng = in.readDouble();
        this.lat = in.readDouble();
        this.nro_radio = in.readFloat();
        this.distancia = in.readString();
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public float getNro_radio() {
        return nro_radio;
    }

    public void setNro_radio(float nro_radio) {
        this.nro_radio = nro_radio;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelSA() {
        return telSA;
    }

    public void setTelSA(String telSA) {
        this.telSA = telSA;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNomeLotacaoSuperior() {
        return nomeLotacaoSuperior;
    }

    public void setNomeLotacaoSuperior(String nomeLotacaoSuperior) {
        this.nomeLotacaoSuperior = nomeLotacaoSuperior;
    }

    public int getCod_parent() {
        return cod_parent;
    }

    public void setCod_parent(int cod_parent) {
        this.cod_parent = cod_parent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public lotacoesVO() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(id_categoria);
        dest.writeInt(cod_parent);
        dest.writeString(nomeLotacaoSuperior);
        dest.writeString(nome);
        dest.writeString(sigla);
        dest.writeString(endereco);
        dest.writeString(email);
        dest.writeString(tel);
        dest.writeString(telSA);
        dest.writeString(detalhes);
        dest.writeDouble(lng);
        dest.writeDouble(lat);
        dest.writeFloat(nro_radio);
        dest.writeString(distancia);
    }
}
