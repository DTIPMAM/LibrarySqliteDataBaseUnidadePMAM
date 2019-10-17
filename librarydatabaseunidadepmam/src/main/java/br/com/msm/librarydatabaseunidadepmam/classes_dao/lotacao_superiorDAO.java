package br.com.msm.librarydatabaseunidadepmam.classes_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacao_superiorVO;
import br.com.msm.librarydatabaseunidadepmam.database.DBUnidadePMAMHelper;


public class lotacao_superiorDAO {
    //MÉTODOS PAARA TRABALHAR COM A TABELA lotacoes
    private Context ctx;
    private String table_name = "lotacao_superior";
    private String[] colunas = new String[]{ "_id","cod_parent","id_categoria","nomeLotacaoSuperior"};

    public lotacao_superiorDAO(Context ctx) {
        this.ctx = ctx;
    }
    public boolean insert(lotacao_superiorVO geo) {
        SQLiteDatabase db     = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_categoria", geo.getId_categoria());
        values.put("cod_parent", geo.getCod_parent());
        values.put("nomeLotacaoSuperior", geo.getnomeLotacaoSuperior());
        if(db.insert(table_name, null, values) > 0){
            db.close();
            return true;
        }else{
            return false;
        } }
    public Cursor buscarTudocod(int cod) {

            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{String.valueOf(cod)};
            Cursor c = db.query(table_name, colunas, "id_categoria = ?", busca, null, null, "cod_parent ASC", null);
        if (c == null) {
            c.close();
            return null;
        } else if (!c.moveToFirst()) {
             c.close();
            return null;
        }
        return c;

    }
    public boolean update(lotacao_superiorVO geo , String cod_parent) {


        String[] update = new String[]{String.valueOf(cod_parent)};

        SQLiteDatabase db     = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_categoria", geo.getId_categoria());
        values.put("nomeLotacaoSuperior", geo.getnomeLotacaoSuperior());
        if(db.update(table_name, values, "cod_parent = ?", update) > 0){
            db.close();
            return true;
        }else{
            return false;
        } }
    public boolean deletaTudo() {

        SQLiteDatabase db    = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        return (db.delete(table_name, null, null) > 0);
    }
    public int tamDb() {
        SQLiteDatabase db     = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        Cursor cursor = db.query(table_name, colunas, null, null, null, null, null);
        return cursor.getCount();
    }
    public List<lotacao_superiorVO> lista() {

        List<lotacao_superiorVO> lista = new ArrayList<lotacao_superiorVO>();
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            Cursor c  = db.query(table_name, colunas, null, null, null, null, null);

            while (c.moveToNext()) {
                lotacao_superiorVO geo= new lotacao_superiorVO();
                geo.setId_categoria(c.getInt(c.getColumnIndex("id_categoria")));
                geo.setCod_parent(c.getInt(c.getColumnIndex("cod_parent")));
                geo.setnomeLotacaoSuperior(c.getString(c.getColumnIndex("nomeLotacaoSuperior")));
                lista.add(geo);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    public boolean VerificaLSuperior (String cod_parent) {

        boolean tiporetorn = false;
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{cod_parent};

            Cursor c = db.query(table_name, colunas, "cod_parent = ?", busca, null, null, null, null);
            if(c.getCount() > 0) {
                tiporetorn = true;
            } else {
                tiporetorn = false;
            }
            c.close();
            db.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tiporetorn;
    }
}
