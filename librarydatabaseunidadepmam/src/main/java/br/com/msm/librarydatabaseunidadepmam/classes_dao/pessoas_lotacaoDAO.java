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
    public boolean insert(pessoas_lotacaoVO pessoaLotacao) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_unidade", pessoaLotacao.getId_unidade());
            values.put("pessoa_nome", pessoaLotacao.getPessoa_nome());
            values.put("funcao", pessoaLotacao.getFuncao());
            values.put("telefone_corporativo", pessoaLotacao.getTelefone_corporativo());
            return db.insert(table_name, null, values) > 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }    }




    public Cursor buscarCodprod(String txtbusca) {
        SQLiteDatabase db = null;
        Cursor c = null;
        String[] busca = new String[]{txtbusca};
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, "id_unidade = ?", busca, null, null, null);
            if (c == null) {
                return null;
            } else if (!c.moveToFirst()) {
                c.close();
                return null;
            }
            return c;
        } catch (SQLException e) {
            // Log the error or handle it as needed
            return null; // ou lançar uma exceção personalizada
        } finally {
            // Não feche o cursor aqui se ele for retornado e usado externamente.
            // O chamador será responsável por fechar o cursor.
            // No entanto, o banco de dados pode ser fechado se não for mais necessário.
            // Se o cursor estiver ligado a este db instance, não feche o db aqui também.
            // A melhor prática é que o chamador do método gerencie o ciclo de vida do cursor e do db.
        }
    }
    public boolean hasDadosDatabase(String txtbusca) {

        SQLiteDatabase db = null;
        Cursor c = null;
        String[] busca = new String[]{txtbusca};
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, "id_unidade = ?", busca, null, null, null);
            return c != null && c.moveToFirst();
        } catch (SQLException e) {
            // Log the error or handle it as needed
            return false;
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean deletaitem(String num) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            return (db.delete(table_name, "_id = ?", new String[]{num}) > 0);
        } finally {
            if (db != null) {
                db.close();
            }
        }


    }


    public boolean buscarPessoa(String pessoa_nome) {

        SQLiteDatabase db = null;
        Cursor c = null;
        String[] busca = new String[]{pessoa_nome};
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, "pessoa_nome = ?", busca, null, null, null);
            return c != null && c.moveToFirst();
        } catch (SQLException e) {
            // Log the error or handle it as needed
            return false;
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    //updateTelNome
    public boolean updateTelNome(pessoas_lotacaoVO pessoaLotacaoVO, String cod) {
        SQLiteDatabase db = null;
        String[] update = new String[]{cod};
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("pessoa_nome", pessoaLotacaoVO.getPessoa_nome());
            values.put("telefone_corporativo", pessoaLotacaoVO.getTelefone_corporativo());
            return db.update(table_name, values, "_id = ?", update) > 0;
        } catch (SQLException e) {
            // Log the error or handle it as needed
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //updateTelNome
    public boolean updateNomeFuncao(pessoas_lotacaoVO pessoaLotacaoVO, String nomeGuerraPessoa) {
        SQLiteDatabase db = null;
        String[] update = new String[]{nomeGuerraPessoa};
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_unidade", pessoaLotacaoVO.getId_unidade());
            values.put("funcao", pessoaLotacaoVO.getFuncao());
            values.put("telefone_corporativo", pessoaLotacaoVO.getTelefone_corporativo());
            return db.update(table_name, values, "pessoa_nome = ?", update) > 0;
        } catch (SQLException e) {
            // Log the error or handle it as needed
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }




    public boolean update(pessoas_lotacaoVO geo, String nomepessoa) {
        SQLiteDatabase db = null;
        String[] update = new String[]{nomepessoa};
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_unidade", geo.getId_unidade());
            values.put("funcao", geo.getFuncao());
            values.put("telefone_corporativo", geo.getTelefone_corporativo());
            return db.update(table_name, values, "pessoa_nome = ?", update) > 0;
        } catch (SQLException e) {
            // Log the error or handle it as needed
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    public boolean deletaTudo() {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            return (db.delete(table_name, null, null) > 0);
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }
    public int tamDb() {
        int total = 0;
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, null, null, null, null, null);
            if (c != null) {
                total = c.getCount();
            }
        } catch (SQLException e) {
            // Log the error or handle it as needed
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
            return total;
    }

    public pessoas_lotacaoVO lista(String id_unidade) {
        SQLiteDatabase db = null;
        Cursor c = null;
        String[] busca = new String[]{id_unidade};
        pessoas_lotacaoVO geo = null;

        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, "id_unidade = ?", busca, null, null, null);

             if (!c.moveToFirst()) {
                return null;
            }
            geo = new pessoas_lotacaoVO();
            geo.setId_unidade(c.getInt(c.getColumnIndexOrThrow("id_unidade")));
            geo.setPessoa_nome(c.getString(c.getColumnIndexOrThrow("pessoa_nome")));
            geo.setFuncao(c.getString(c.getColumnIndexOrThrow("funcao")));
            geo.setTelefone_corporativo(c.getString(c.getColumnIndexOrThrow("telefone_corporativo")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return geo;
    }


    public  List<pessoas_lotacaoVO> buscarPessoaIdOPM(String txtbusca) {
        SQLiteDatabase db = null;
        Cursor c = null;
        List<pessoas_lotacaoVO> lista = new ArrayList<pessoas_lotacaoVO>();

        String[] busca = new String[]{txtbusca};
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, "id_unidade = ?", busca, null, null, null);

            while (c.moveToNext()) {
                pessoas_lotacaoVO geo = new pessoas_lotacaoVO();
                geo.setId_unidade(c.getInt(c.getColumnIndexOrThrow("id_unidade")));
                geo.setPessoa_nome(c.getString(c.getColumnIndexOrThrow("pessoa_nome")));
                geo.setFuncao(c.getString(c.getColumnIndexOrThrow("funcao")));
                geo.setTelefone_corporativo(c.getString(c.getColumnIndexOrThrow("telefone_corporativo")));
                lista.add(geo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return lista;
    }



    public List<pessoas_lotacaoVO> lista() {

        List<pessoas_lotacaoVO> lista = new ArrayList<pessoas_lotacaoVO>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, null, null, null, null, null);

            while (c.moveToNext()) {
                pessoas_lotacaoVO geo = new pessoas_lotacaoVO();
                geo.setId_unidade(c.getInt(c.getColumnIndexOrThrow("id_unidade")));
                geo.setPessoa_nome(c.getString(c.getColumnIndexOrThrow("pessoa_nome")));
                geo.setFuncao(c.getString(c.getColumnIndexOrThrow("funcao")));
                geo.setTelefone_corporativo(c.getString(c.getColumnIndexOrThrow("telefone_corporativo")));
                lista.add(geo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return lista;
    }

}
