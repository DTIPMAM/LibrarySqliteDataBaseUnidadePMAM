package br.com.msm.librarydatabaseunidadepmam.classes_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import br.com.msm.librarydatabaseunidadepmam.classes_vo.dados;
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
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_categoria", geo.getId_categoria());
            values.put("cod_parent", geo.getCod_parent());
            values.put("nomeLotacaoSuperior", geo.getnomeLotacaoSuperior());
            return (db.insert(table_name, null, values) > 0);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean insert(dados geo) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_categoria", geo.getId_categoria());
            values.put("cod_parent", geo.getID_PARENT());
            values.put("nomeLotacaoSuperior", geo.getNomeLotacaoSuperior());
            return (db.insert(table_name, null, values) > 0);
        } finally {
            if (db != null) db.close();
        }
    }

    public Cursor buscarTudocod(int cod) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{String.valueOf(cod)};
            c = db.query(table_name, colunas, "id_categoria = ?", busca, null, null, "cod_parent ASC", null);
            if (!c.moveToFirst()) {
                return null;
            }
            return c;
        } finally {
            // Não feche o cursor aqui se você pretende usá-lo fora deste método.
            // O chamador será responsável por fechar o cursor.
            // Se o cursor não for retornado (ou seja, em caso de erro ou nenhum dado), feche o banco de dados.
            if (c == null && db != null) {
                db.close();
            }
        }
    }


    public  List<lotacao_superiorVO> buscarAllcod(int cod) {
        SQLiteDatabase db = null;
        Cursor c = null;
        List<lotacao_superiorVO> lista = new ArrayList<lotacao_superiorVO>();
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{String.valueOf(cod)};
            c = db.query(table_name, colunas, "id_categoria = ?", busca, null, null, "cod_parent ASC", null);
            while (c.moveToNext()) {
                lotacao_superiorVO geo = new lotacao_superiorVO();
                geo.setId_categoria(c.getInt(c.getColumnIndexOrThrow("id_categoria")));
                geo.setCod_parent(c.getInt(c.getColumnIndexOrThrow("cod_parent")));
                geo.setnomeLotacaoSuperior(c.getString(c.getColumnIndexOrThrow("nomeLotacaoSuperior")));
                lista.add(geo);
            }
        } catch (Exception e) {
            //  e.printStackTrace();
        } finally {
            if (c != null) c.close();
            if (db != null) db.close();
        }
        return lista;
    }

    public boolean update(lotacao_superiorVO geo , String cod_parent) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_categoria", geo.getId_categoria());
            values.put("nomeLotacaoSuperior", geo.getnomeLotacaoSuperior());
            return (db.update(table_name, values, "cod_parent = ?", new String[]{cod_parent}) > 0);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    public boolean update(dados geo , String cod_parent) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_categoria", geo.getId_categoria());
            values.put("nomeLotacaoSuperior", geo.getNomeLotacaoSuperior());
            return (db.update(table_name, values, "cod_parent = ?", new String[]{cod_parent}) > 0);
        } finally {
            if (db != null) db.close();
        }
    }
    public boolean deletaTudo() {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            return (db.delete(table_name, null, null) > 0);
        } finally {
            if (db != null) db.close();
        }
    }
    public int tamDb() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getReadableDatabase();
            cursor = db.query(table_name, colunas, null, null, null, null, null);
            return cursor.getCount();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }
    public List<lotacao_superiorVO> lista() {
        List<lotacao_superiorVO> lista = new ArrayList<lotacao_superiorVO>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getReadableDatabase();
            c = db.query(table_name, colunas, null, null, null, null, null);

            while (c.moveToNext()) {
                lotacao_superiorVO geo = new lotacao_superiorVO();
                geo.setId_categoria(c.getInt(c.getColumnIndexOrThrow("id_categoria")));
                geo.setCod_parent(c.getInt(c.getColumnIndexOrThrow("cod_parent")));
                geo.setnomeLotacaoSuperior(c.getString(c.getColumnIndexOrThrow("nomeLotacaoSuperior")));
                lista.add(geo);
            }
        } catch (Exception e) {
            //  e.printStackTrace();
        } finally {
            if (c != null) c.close();
            if (db != null) db.close();
        }
        return lista;
    }

    public boolean VerificaLSuperior (String cod_parent) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getReadableDatabase();
            String[] busca = new String[]{cod_parent};
            c = db.query(table_name, colunas, "cod_parent = ?", busca, null, null, null, null);
            return c.getCount() > 0;
        }
        catch (Exception e) {
           // e.printStackTrace();
            return false;
        } finally {
            if (c != null) c.close();
            if (db != null) db.close();
        }
    }
}