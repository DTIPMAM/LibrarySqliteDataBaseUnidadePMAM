package br.com.msm.librarydatabaseunidadepmam.classes_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacoesVO;
import br.com.msm.librarydatabaseunidadepmam.database.DBUnidadePMAMHelper;


public class lotacoesDAO {
    //MÉTODOS PAARA TRABALHAR COM A TABELA lotacoes
    private Context ctx;
    private String table_name = "lotacoes";
    private String[] colunas = new String[]{
            "_id", "id_categoria", "cod_parent", "nomeLotacaoSuperior", "nome", "endereco",
            "descr", "email_institucional", "fone1", "fone2", "latitude", "longitude", "nro_radio", "sigla"};

    public lotacoesDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean insert(lotacoesVO geo) {
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", geo.getID());
        values.put("id_categoria", geo.getId_categoria());
        values.put("cod_parent", geo.getCod_parent());
        values.put("nomeLotacaoSuperior", geo.getNomeLotacaoSuperior());
        values.put("nome", geo.getNome());
        values.put("sigla", geo.getSigla());
        values.put("endereco", geo.getEndereco());
        values.put("descr", geo.getDetalhes());
        values.put("email_institucional", geo.getEmail());
        values.put("fone1", geo.getTel());
        values.put("fone2", geo.getTelSA());
        values.put("latitude", geo.getLat());
        values.put("longitude", geo.getLng());
        values.put("nro_radio", geo.getNro_radio());
        if (db.insert(table_name, null, values) > 0) {
            db.close();
            return true;
        } else {
            return false;
        }
    }

    public boolean Verificalotacao(String codbg) {

        boolean tiporetorn = false;
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{codbg};

            Cursor c = db.query(table_name, colunas, "_id = ?", busca, null, null, null, null);
            if (c.getCount() > 0) {
                tiporetorn = true;
            } else {
                tiporetorn = false;
            }
            c.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiporetorn;
    }

    public Cursor buscarTudoporValor(String codigo) {

        String[] busca = new String[]{codigo};
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        Cursor c = db.query(table_name, colunas, "_id LIKE ? or nomeLotacaoSuperior LIKE ? or nome LIKE ? or endereco LIKE ?", busca, null, null, "_id ASC", null);

        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }

    public Cursor buscarTudo() {
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        Cursor c = db.query(table_name, colunas, null, null, null, null, "_id ASC", null);

        if (c == null) {
            db.close();
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            db.close();
            return null;
        }
        return c;
    }

    //-----------------------------------------
    public Cursor buscarString(String codigo, String txtbusca) {
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        String[] busca = new String[]{codigo, txtbusca};

        Cursor c = db.query(table_name, colunas, "id_categoria = ? and cod_parent = ?", busca, null, null, "_id ASC", null);

        if (c == null) {
            c.close();
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }

    public Cursor buscarStrings(String txt) {
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        String[] busca = new String[]{"%" + txt + "%", "%" + txt + "%"};

        Cursor c = db.query(table_name, colunas, "nro_radio LIKE ? or nome LIKE ? ", busca, null, null, "_id ASC", null);

        if (c == null) {
            c.close();
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }

    public Cursor buscarPosicaoLotacao(double latitude, double longitude) {
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        String[] busca = new String[]{String.valueOf(latitude), String.valueOf(longitude)};

        Cursor c = db.query(table_name, colunas, "latitude = ? and longitude = ?", busca, null, null, "_id ASC", null);

        if (c == null) {
            c.close();
            db.close();
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            db.close();
            return null;
        }
        return c;
    }

    public Cursor buscarLotcao(String _id) {

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        String[] busca = new String[]{_id};

        Cursor c = db.query(table_name, colunas, "_id = ?", busca, null, null, null);

        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;


    }

    public String buscarIdUnidade(String ID_unidade) {

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        String[] busca = new String[]{ID_unidade};
        String unidade;
        Cursor c = db.query(table_name, colunas, "_id = ?", busca, null, null, null);

        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        unidade = c.getString(c.getColumnIndex("nome"));
        c.close();
        return unidade;


    }


    public Cursor buscarTel(String txtbusca) {
        String retono = "";
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        String[] busca = new String[]{txtbusca};
        try {
            Cursor c = db.query(table_name, colunas, "unidade = ?", busca, null, null, null);
            if (c == null) {
                return null;
            } else if (!c.moveToFirst()) {
                c.close();
                return null;
            } else {
                return c;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean update(lotacoesVO geo, String ID) {
        String[] update = new String[]{ID};
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_categoria", geo.getId_categoria());
        values.put("cod_parent", geo.getCod_parent());
        values.put("nomeLotacaoSuperior", geo.getNomeLotacaoSuperior());
        values.put("nome", geo.getNome());
        values.put("sigla", geo.getSigla());
        values.put("endereco", geo.getEndereco());
        values.put("descr", geo.getDetalhes());
        values.put("email_institucional", geo.getEmail());
        values.put("fone1", geo.getTel());
        values.put("fone2", geo.getTelSA());
        values.put("latitude", geo.getLat());
        values.put("longitude", geo.getLng());
        values.put("nro_radio", geo.getNro_radio());
        if (db.update(table_name, values, "_id = ?", update) > 0) {
            db.close();
            return true;
        } else {
            return false;
        }
    }

    public boolean deletaTudo() {

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        return (db.delete(table_name, null, null) > 0);
    }

    public int tamDb() {
        int tam;
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        Cursor cursor = db.query(table_name, colunas, null, null, null, null, null);
        tam = cursor.getCount();
        db.close();
        cursor.close();
        return tam;
    }

    public List<lotacoesVO> lista() {

        List<lotacoesVO> lista = new ArrayList<lotacoesVO>();
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            Cursor c = db.query(table_name, colunas, null, null, null, null, null);

            while (c.moveToNext()) {
                lotacoesVO geo = new lotacoesVO();
                geo.setID(c.getInt(c.getColumnIndex("_id")));
                geo.setId_categoria(c.getInt(c.getColumnIndex("id_categoria")));
                geo.setId_categoria(c.getInt(c.getColumnIndex("cod_parent")));
                geo.setNomeLotacaoSuperior(c.getString(c.getColumnIndex("nomeLotacaoSuperior")));
                geo.setNome(c.getString(c.getColumnIndex("nome")));
                geo.setEndereco(c.getString(c.getColumnIndex("endereco")));
                geo.setDetalhes(c.getString(c.getColumnIndex("descr")));
                geo.setEmail(c.getString(c.getColumnIndex("email_institucional")));
                geo.setTel(c.getString(c.getColumnIndex("fone1")));
                geo.setTelSA(c.getString(c.getColumnIndex("fone2")));
                geo.setLat(c.getInt(c.getColumnIndex("latitude")));
                geo.setLng(c.getInt(c.getColumnIndex("longitude")));
                lista.add(geo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public lotacoesVO getLotacao(int ID) {


        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        Cursor c = db.query(table_name, colunas, "_id = ?", new String[]{String.valueOf(ID)}, null, null, null, null);
        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        lotacoesVO geo = new lotacoesVO();
        geo.setID(c.getInt(c.getColumnIndex("_id")));
        geo.setId_categoria(c.getInt(c.getColumnIndex("id_categoria")));
        geo.setId_categoria(c.getInt(c.getColumnIndex("cod_parent")));
        geo.setNomeLotacaoSuperior(c.getString(c.getColumnIndex("nomeLotacaoSuperior")));
        geo.setNome(c.getString(c.getColumnIndex("nome")));
        geo.setEndereco(c.getString(c.getColumnIndex("endereco")));
        geo.setDetalhes(c.getString(c.getColumnIndex("descr")));
        geo.setEmail(c.getString(c.getColumnIndex("email_institucional")));
        geo.setTel(c.getString(c.getColumnIndex("fone1")));
        geo.setTelSA(c.getString(c.getColumnIndex("fone2")));
        geo.setLat(c.getDouble(c.getColumnIndex("latitude")));
        geo.setLng(c.getDouble(c.getColumnIndex("longitude")));
        c.close();
        return geo;


    }

    public String getUnidadeNome(int codgeo) {


        String codretorn = Integer.toString(codgeo);
        boolean tiporetorn = false;
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

            String[] busca = new String[]{codretorn};

            Cursor c = db.query(table_name, colunas, "_id = ?", busca, null, null, null, null);
            lotacoesVO geo = new lotacoesVO();
            while (c.moveToNext()) {
                geo.setNome(c.getString(c.getColumnIndex("nome")));
            }
            db.close();
            c.close();
            return geo.getNome();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getSigla(int codgeo) {


        String codretorn = Integer.toString(codgeo);
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

            String[] busca = new String[]{codretorn};

            Cursor c = db.query(table_name, colunas, "_id = ?", busca, null, null, null, null);
            if (c == null) {
                return "";
            } else if (!c.moveToFirst()) {
                c.close();
                return "";
            } else {
                String sigle = c.getString(c.getColumnIndex("sigla"));
                c.close();
                return sigle;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getCodigo(String name) {

        String[] busca = new String[]{name};
        String nome;
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

            Cursor c = db.query(table_name, colunas, "nome = ?", busca, null, null, null, null);
            if (c == null) {
                return null;
            } else if (!c.moveToFirst()) {
                c.close();
                return null;
            } else {
                nome = c.getString(c.getColumnIndex("_id"));
                c.close();
                return nome;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getCursorForName(String name) {

        String[] busca = new String[]{name};
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

            Cursor c = db.query(table_name, colunas, "nome = ?", busca, null, null, null, null);
            if (c == null) {
                return null;
            } else if (!c.moveToFirst()) {
                c.close();
                return null;
            } else {
                return c;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
