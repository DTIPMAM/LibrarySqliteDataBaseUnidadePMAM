package br.com.msm.librarydatabaseunidadepmam.classes_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import br.com.msm.librarydatabaseunidadepmam.classes_vo.pessoas_lotacaoVO;
import br.com.msm.librarydatabaseunidadepmam.database.DBUnidadePMAMHelper;


public class pessoas_lotacaoDAO {
    //MÉTODOS PAARA TRABALHAR COM A TABELA pessoas_lotacoes
    private Context ctx;
    private String table_name = "pessoas_lotacoes";
    private String[] colunas = new String[]{
            "_id", "id_unidade", "pessoa_nome","funcao",
            "telefone_corporativo"};


    public pessoas_lotacaoDAO(Context ctx) {
        this.ctx = ctx;
    }
    public boolean insert(pessoas_lotacaoVO geo) {
        SQLiteDatabase db     = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_unidade", geo.getId_unidade());
        values.put("pessoa_nome", geo.getPessoa_nome());
        values.put("funcao", geo.getFuncao());
        values.put("telefone_corporativo", geo.getTelefone_corporativo());
       if(db.insert(table_name, null, values) > 0){

        db.close();
        return true;
    }else{
        return false;
    } }

    public boolean VerificaPessoasLotacao (String Nome_Pessoa) {

        boolean tiporetorn = false;
        try {
        String[] busca = new String[]{Nome_Pessoa};
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            Cursor c = db.query(table_name, colunas, "pessoa_nome = ?", busca, null, null, null, null);
            if(c.getCount() > 0) {
                tiporetorn = true;
            } else {
                tiporetorn = false;
            }
            c.close();
            db.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return tiporetorn;

    }

    public Cursor buscarTudo() {
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        Cursor c = db.query(table_name, colunas, null, null, null, null, "_id ASC", null);

        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }
    public Cursor buscarTudocod(int cod) {
        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        String[] busca = new String[]{String.valueOf(cod)};

        Cursor c = db.query(table_name, colunas, "id_unidade = ?", busca,  null, null, "_id ASC", null);

        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }
    //-----------------------------------------
    public Cursor VerificarSeTemUnidade_and_funcao(String id_unidade, String funcao) {

        try {
        String[] busca = new String[]{id_unidade, funcao};
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        Cursor c = db.query(table_name, colunas, "id_unidade = ? and funcao = ?", busca, null, null, null);
            if (c == null) {
                return null;
            }else if (!c.moveToFirst()) {
                c.close();
                return null;
            }else {
                return c;
            }
        }catch (Exception e){
            return null;
        }
    }

    public Cursor VerificarSeTemUnidade_pessoa(String Nome_Pessoa) {

        try {
            String[] busca = new String[]{Nome_Pessoa};
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            Cursor c = db.query(table_name, colunas, "pessoa_nome = ?", busca, null, null, null, null);
            if (c == null) {
                return null;
            }else if (!c.moveToFirst()) {
                c.close();
                return null;
            }else {
                return c;
            }
        }catch (Exception e){
            return null;
        }
    }



    public Cursor buscarCodprod(String txtbusca) {

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        String[] busca = new String[]{txtbusca};

        Cursor c = db.query(table_name, colunas, "id_unidade = ?", busca, null, null, null);

        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;


    }

    public boolean hasDadosDatabase(String txtbusca) {

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        String[] busca = new String[]{txtbusca};

        Cursor c = db.query(table_name, colunas, "id_unidade = ?", busca, null, null, null);

        if (c == null) {
            return false;
        } else if (!c.moveToFirst()) {
            c.close();
            return false;
        }
         c.close();
        return true;

    }

    public boolean deletaitem(String num) {

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        return (db.delete(table_name, "_id = ?", new String[]{num}) > 0);
    }


    public boolean buscarPessoa(String pessoa_nome) {

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

        String[] busca = new String[]{pessoa_nome};
        try {
            Cursor c = db.query(table_name, colunas, "pessoa_nome = ?", busca, null, null, null);
            if (c == null) {
                return false;
            }else if (!c.moveToFirst()) {
                c.close();
                return false;
            }else {
                c.close();
              return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    //updateTelNome
    public boolean updateTelNome(pessoas_lotacaoVO geo , String cod) {

        String[] update = new String[]{cod};

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pessoa_nome", geo.getPessoa_nome());
        values.put("telefone_corporativo", geo.getTelefone_corporativo());
        if (db.update(table_name, values, "_id = ?", update) > 0){
            db.close();
            return true;
        }else{
            return false;
        }
    }

    //updateTelNome
    public boolean updateNomeFuncao(pessoas_lotacaoVO geo ,String nomeGuerraPessoa) {

        String[] update = new String[]{nomeGuerraPessoa};

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_unidade", geo.getId_unidade());
        values.put("funcao", geo.getFuncao());
        values.put("telefone_corporativo", geo.getTelefone_corporativo());
        if (db.update(table_name, values, "pessoa_nome = ?", update) > 0){
            db.close();
            return true;
        }else{
            return false;
        }
    }



    public boolean update(pessoas_lotacaoVO geo , String nomepessoa) {


        String[] update = new String[]{nomepessoa};

        SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_unidade", geo.getId_unidade());
        values.put("funcao", geo.getFuncao());
        values.put("telefone_corporativo", geo.getTelefone_corporativo());
        if (db.update(table_name, values, "pessoa_nome = ?", update) > 0){
        db.close();
        return true;
    }else{
        return false;
    }
}
    public boolean deletaTudo() {

        SQLiteDatabase db    = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        return (db.delete(table_name, null, null) > 0);
    }
    public int tamDb() {
        int total =0;
        SQLiteDatabase db     = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
        Cursor c = db.query(table_name, colunas, null, null, null, null, null);
        if (c == null) {
            return total;
        } else if (!c.moveToFirst()) {
            c.close();
            return total;
        }

        total = c.getCount();
        c.close();
        return total;
    }
    public List<pessoas_lotacaoVO> lista() {

        List<pessoas_lotacaoVO> lista = new ArrayList<pessoas_lotacaoVO>();
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            Cursor c  = db.query(table_name, colunas, null, null, null, null, null);

            while (c.moveToNext()) {
                pessoas_lotacaoVO geo= new pessoas_lotacaoVO();
                geo.setId_unidade(c.getInt(c.getColumnIndex("id_unidade")));
                geo.setPessoa_nome(c.getString(c.getColumnIndex("pessoa_nome")));
                geo.setFuncao(c.getString(c.getColumnIndex("funcao")));
                geo.setTelefone_corporativo(c.getString(c.getColumnIndex("telefone_corporativo")));
                lista.add(geo);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public String getPessoaNome (int  codgeo) {


        String codretorn  = Integer.toString(codgeo);
        boolean tiporetorn = false;
        try {
            SQLiteDatabase db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();

            String[] busca = new String[]{codretorn};

            Cursor c = db.query(table_name, colunas, "id_unidade = ?", busca, null, null, null, null);
            pessoas_lotacaoVO geo = new pessoas_lotacaoVO();
            while (c.moveToNext()) {
                geo.setPessoa_nome(c.getString(c.getColumnIndex("pessoa_nome")));
            }
            return geo.getPessoa_nome();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
    }
    }
}
