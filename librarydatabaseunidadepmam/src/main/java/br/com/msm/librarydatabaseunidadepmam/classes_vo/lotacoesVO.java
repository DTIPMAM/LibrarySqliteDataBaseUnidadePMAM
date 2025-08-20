package br.com.msm.librarydatabaseunidadepmam.classes_vo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

public class lotacoesVO implements Parcelable {
    @Override
    public String toString() {
        return "lotacoesVO{" +
                "ID=" + _id +
                ", id_categoria=" + id_categoria +
                ", cod_parent=" + cod_parent +
                ", nomeLotacaoSuperior='" + nomeLotacaoSuperior + '\'' +
                ", nome='" + nome + '\'' +
                ", sigla='" + sigla + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", telSA='" + telSA + '\'' +
                ", detalhes='" + detalhes + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", nro_radio=" + nro_radio +
                ", distancia='" + distancia + '\'' +
                '}';
    }

    public static final Creator<lotacoesVO>
            CREATOR = new Creator<lotacoesVO>() {

        public lotacoesVO createFromParcel(Parcel in) {
            return new lotacoesVO(in);
        }

        public lotacoesVO[] newArray(int size) {
            return new lotacoesVO[size];
        }
    };
    private int _id;
    private int id_categoria;

    public lotacoesVO(int ID, int id_categoria, int cod_parent, String nomeLotacaoSuperior, String nome, String sigla, String endereco, String email, String tel, String telSA, String detalhes, double lng, double lat, float nro_radio, String distancia) {
        this._id = ID;
        this.id_categoria = id_categoria;
        this.cod_parent = cod_parent;
        this.nomeLotacaoSuperior = nomeLotacaoSuperior;
        this.nome = nome;
        this.sigla = sigla;
        this.endereco = endereco;
        this.email = email;
        this.tel = tel;
        this.telSA = telSA;
        this.detalhes = detalhes;
        this.lng = lng;
        this.lat = lat;
        this.nro_radio = nro_radio;
        this.distancia = distancia;
    }

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

    public lotacoesVO(lotacoesVO vo) {
        this._id = vo.get_id();
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


    public lotacoesVO(dados vo) {
        this._id = vo.getID() != null && !vo.getID().isEmpty() ? Integer.parseInt(vo.getID()) : 0;
        this.id_categoria = vo.getId_categoria() != null && !vo.getId_categoria().isEmpty() ? Integer.parseInt(vo.getId_categoria()) : 0;
        this.cod_parent = vo.getID_PARENT() != null && !vo.getID_PARENT().isEmpty() ? Integer.parseInt(vo.getID_PARENT()) : 0;
        this.nomeLotacaoSuperior = vo.getNomeLotacaoSuperior();
        this.nome = vo.getNOME();
        this.sigla = vo.getSigla();
        this.endereco = vo.getEndereco();
        this.email = vo.getEmail_institucional();
        this.tel = vo.getFone1();
        this.telSA = vo.getFone2();
        this.detalhes = vo.getDescricao();
        this.lng = vo.getLongitude() != null && !vo.getLongitude().isEmpty() ? Double.parseDouble(vo.getLongitude()) : 0.0;
        this.lat = vo.getLatitude() != null && !vo.getLatitude().isEmpty() ? Double.parseDouble(vo.getLatitude()) : 0.0;
        this.nro_radio = vo.getNro_radio() != null && !vo.getNro_radio().isEmpty() ? Float.parseFloat(vo.getNro_radio()) : 0.0f;
    }


    public lotacoesVO(JsonObject js) {
        //  "_id", "id_categoria", "cod_parent","nomeLotacaoSuperior", "nome","endereco",
        //            "descr","email_institucional","fone1","fone2", "latitude", "longitude","nro_radio", "sigla"};
        this._id = js.has("_id") && !js.get("_id").isJsonNull() && !js.get("_id").getAsString().isEmpty() ? Integer.parseInt(js.get("_id").getAsString()) : 0;

        this.id_categoria = js.has("id_categoria") && !js.get("id_categoria").isJsonNull() && !js.get("id_categoria").getAsString().isEmpty() ? Integer.parseInt(js.get("id_categoria").getAsString()) : 0;

        this.cod_parent = js.has("cod_parent") && !js.get("cod_parent").isJsonNull() && !js.get("cod_parent").getAsString().isEmpty() ? Integer.parseInt(js.get("cod_parent").getAsString()) : 0;

        this.nomeLotacaoSuperior = js.has("nomeLotacaoSuperior") && !js.get("nomeLotacaoSuperior").isJsonNull() ? js.get("nomeLotacaoSuperior").getAsString() : "";

        this.nome = js.has("nome") && !js.get("nome").isJsonNull() ? js.get("nome").getAsString() : "";
        this.sigla = js.has("sigla") && !js.get("sigla").isJsonNull() ? js.get("sigla").getAsString() : "";

        this.endereco = js.has("endereco") && !js.get("endereco").isJsonNull() ? js.get("endereco").getAsString() : "";

        this.email = js.has("email") && !js.get("email").isJsonNull() ? js.get("email").getAsString() : "";

        this.tel = js.has("fone1") && !js.get("fone1").isJsonNull() ? js.get("fone1").getAsString() : "";

        this.telSA = js.has("fone2") && !js.get("fone2").isJsonNull() ? js.get("fone2").getAsString() : "";

        this.detalhes = js.has("descr") && !js.get("descr").isJsonNull() ? js.get("descr").getAsString() : "";

        this.lng = js.has("longitude") && !js.get("longitude").isJsonNull() && !js.get("longitude").getAsString().isEmpty() ? Double.parseDouble(js.get("longitude").getAsString()) : 0;

        this.lat = js.has("latitude") && !js.get("latitude").isJsonNull() && !js.get("latitude").getAsString().isEmpty() ? Double.parseDouble(js.get("latitude").getAsString()) : 0;

        this.nro_radio = js.has("nro_radio") && !js.get("nro_radio").isJsonNull() && !js.get("nro_radio").getAsString().isEmpty() ? Float.parseFloat(js.get("nro_radio").getAsString()) : 0;


        //   this.distancia =  js.get("distancia").getAsString();
    }

    public lotacoesVO(Parcel in) {
        this._id = in.readInt();
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

    public lotacoesVO() {
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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
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
