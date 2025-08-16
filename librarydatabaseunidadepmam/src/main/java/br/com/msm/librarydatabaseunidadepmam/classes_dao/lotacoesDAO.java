package br.com.msm.librarydatabaseunidadepmam.classes_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import br.com.msm.librarydatabaseunidadepmam.classes_vo.dados;
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

    public boolean insert(lotacoesVO lotacao) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_id", lotacao.getID());
            values.put("id_categoria", lotacao.getId_categoria());
            values.put("cod_parent", lotacao.getCod_parent());
            values.put("nomeLotacaoSuperior", lotacao.getNomeLotacaoSuperior());
            values.put("nome", lotacao.getNome());
            values.put("sigla", lotacao.getSigla());
            values.put("endereco", lotacao.getEndereco());
            values.put("descr", lotacao.getDetalhes());
            values.put("email_institucional", lotacao.getEmail());
            values.put("fone1", lotacao.getTel());
            values.put("fone2", lotacao.getTelSA());
            values.put("latitude", lotacao.getLat());
            values.put("longitude", lotacao.getLng());
            values.put("nro_radio", lotacao.getNro_radio());
            return (db.insert(table_name, null, values) > 0);
        } finally {
            if (db != null) db.close();
        }
    }


    public boolean insert(dados geo) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_id", geo.getID());
            values.put("id_categoria", geo.getId_categoria());
            values.put("cod_parent", geo.getID_PARENT());
            values.put("nomeLotacaoSuperior", geo.getNomeLotacaoSuperior());
            values.put("nome", geo.getNOME());
            values.put("sigla", geo.getSigla());
            values.put("endereco", geo.getEndereco());
            values.put("descr", geo.getDescricao());
            values.put("email_institucional", geo.getEmail_institucional());
            values.put("fone1", geo.getFone1());
            values.put("fone2", geo.getFone2());
            values.put("latitude", geo.getLatitude());
            values.put("longitude", geo.getLongitude());
            values.put("nro_radio", geo.getNro_radio());
            return (db.insert(table_name, null, values) > 0);
        } finally {
            if (db != null) db.close();
        }
    }

    public boolean Verificalotacao(String codbg) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{codbg};
            c = db.query(table_name, colunas, "_id = ?", busca, null, null, null, null);
            return c.getCount() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (c != null) c.close();
            if (db != null) db.close();
        }
    }

    public Cursor buscarTudo() {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            c = db.query(table_name, colunas, null, null, null, null, "_id ASC", null);
            if (!c.moveToFirst()) {
                return null;
            }
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Não feche o cursor aqui, pois ele é retornado e usado externamente
            if (db != null) db.close();
        }
    }


    public Cursor buscarString(String codigo, String txtbusca) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{codigo, txtbusca};

            c = db.query(table_name, colunas, "id_categoria = ? and cod_parent = ?", busca, null, null, "_id ASC", null);

            if (!c.moveToFirst()) {
                 c.close();
                return null;
            }
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Não feche o cursor aqui, pois ele é retornado e usado externamente
            if (db != null) db.close();
        }
    }

    public  List<lotacoesVO> buscarListOpm(String codigo, String txtbusca) {
        SQLiteDatabase db = null;
        Cursor c = null;
        List<lotacoesVO>  list = new ArrayList<>();
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{codigo, txtbusca};

            c = db.query(table_name, colunas, "id_categoria = ? and cod_parent = ?", busca, null, null, "nome ASC", null);
            if (c.moveToFirst()) {
                do {
                    lotacoesVO geo = new lotacoesVO();
                    geo.setID(c.getInt(c.getColumnIndexOrThrow("_id")));
                    geo.setNro_radio(c.getFloat(c.getColumnIndexOrThrow("nro_radio")));
                    geo.setId_categoria(c.getInt(c.getColumnIndexOrThrow("id_categoria")));
                    geo.setCod_parent(c.getInt(c.getColumnIndexOrThrow("cod_parent")));
                    geo.setNomeLotacaoSuperior(c.getString(c.getColumnIndexOrThrow("nomeLotacaoSuperior")));
                    geo.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
                    geo.setSigla(c.getString(c.getColumnIndexOrThrow("sigla")));
                    geo.setEndereco(c.getString(c.getColumnIndexOrThrow("endereco")));
                    geo.setDetalhes(c.getString(c.getColumnIndexOrThrow("descr")));
                    geo.setEmail(c.getString(c.getColumnIndexOrThrow("email_institucional")));
                    geo.setTel(c.getString(c.getColumnIndexOrThrow("fone1")));
                    geo.setTelSA(c.getString(c.getColumnIndexOrThrow("fone2")));
                    geo.setLat(c.getDouble(c.getColumnIndexOrThrow("latitude")));
                    geo.setLng(c.getDouble(c.getColumnIndexOrThrow("longitude")));
                    list.add(geo);
                } while (c.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //  Considerar logar o erro ou lançar uma exceção personalizada
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) db.close();
        }
        return list;
    }


    public boolean update(lotacoesVO geo, String ID) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
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
            String[] updateArgs = new String[]{ID};
            return (db.update(table_name, values, "_id = ?", updateArgs) > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public boolean update(dados geo, String ID) {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_categoria", geo.getId_categoria());
            values.put("cod_parent", geo.getID_PARENT());
            values.put("nomeLotacaoSuperior", geo.getNomeLotacaoSuperior());
            values.put("nome", geo.getNOME());
            values.put("sigla", geo.getSigla());
            values.put("endereco", geo.getEndereco());
            values.put("descr", geo.getDescricao());
            values.put("email_institucional", geo.getEmail_institucional());
            values.put("fone1", geo.getFone1());
            values.put("fone2", geo.getFone2());
            values.put("latitude", geo.getLatitude());
            values.put("longitude", geo.getLongitude());
            values.put("nro_radio", geo.getNro_radio());
            String[] updateArgs = new String[]{ID};
            return (db.update(table_name, values, "_id = ?", updateArgs) > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) db.close();
        }
    }

    public boolean deletaTudo() {
        SQLiteDatabase db = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            return (db.delete(table_name, null, null) > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) db.close();
        }
    }

    public int tamDb() {
        int tam = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getReadableDatabase(); // Use getReadableDatabase para operações de leitura
            cursor = db.query(table_name, new String[]{"COUNT(*)"}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                tam = cursor.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return tam;
    }

    public List<lotacoesVO> lista() {
        List<lotacoesVO> lista = new ArrayList<lotacoesVO>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getReadableDatabase(); // Use getReadableDatabase para leitura
            c = db.query(table_name, colunas, null, null, null, null, "_id ASC"); // Adicionado ordenação

            if (c.moveToFirst()) {
                do {
                    lotacoesVO geo = new lotacoesVO();
                    geo.setID(c.getInt(c.getColumnIndexOrThrow("_id")));
                    geo.setNro_radio(c.getFloat(c.getColumnIndexOrThrow("nro_radio")));
                    geo.setId_categoria(c.getInt(c.getColumnIndexOrThrow("id_categoria")));
                    // Corrigido: Estava setando id_categoria duas vezes. Assumindo que o segundo era para cod_parent.
                    geo.setCod_parent(c.getInt(c.getColumnIndexOrThrow("cod_parent")));
                    geo.setNomeLotacaoSuperior(c.getString(c.getColumnIndexOrThrow("nomeLotacaoSuperior")));
                    geo.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
                    geo.setSigla(c.getString(c.getColumnIndexOrThrow("sigla"))); // Adicionado sigla
                    geo.setEndereco(c.getString(c.getColumnIndexOrThrow("endereco")));
                    geo.setDetalhes(c.getString(c.getColumnIndexOrThrow("descr")));
                    geo.setEmail(c.getString(c.getColumnIndexOrThrow("email_institucional")));
                    geo.setTel(c.getString(c.getColumnIndexOrThrow("fone1")));
                    geo.setTelSA(c.getString(c.getColumnIndexOrThrow("fone2")));
                    geo.setLat(c.getDouble(c.getColumnIndexOrThrow("latitude"))); // Alterado para getDouble
                    geo.setLng(c.getDouble(c.getColumnIndexOrThrow("longitude"))); // Alterado para getDouble
                    lista.add(geo);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Considere logar o erro ou lançar uma exceção personalizada
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

    public lotacoesVO getLotacao(int ID) {
        SQLiteDatabase db = null;
        Cursor c = null;
        lotacoesVO geo = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getReadableDatabase(); // Use getReadableDatabase para leitura
            c = db.query(table_name, colunas, "_id = ?", new String[]{String.valueOf(ID)}, null, null, null, null);

            if (c.moveToFirst()) {
                geo = new lotacoesVO();
                geo.setID(c.getInt(c.getColumnIndexOrThrow("_id")));
                geo.setNro_radio(c.getFloat(c.getColumnIndexOrThrow("nro_radio")));
                geo.setId_categoria(c.getInt(c.getColumnIndexOrThrow("id_categoria")));
                // Corrigido: Estava setando id_categoria duas vezes. Assumindo que o segundo era para cod_parent.
                geo.setCod_parent(c.getInt(c.getColumnIndexOrThrow("cod_parent")));
                geo.setNomeLotacaoSuperior(c.getString(c.getColumnIndexOrThrow("nomeLotacaoSuperior")));
                geo.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
                geo.setSigla(c.getString(c.getColumnIndexOrThrow("sigla"))); // Adicionado sigla
                geo.setEndereco(c.getString(c.getColumnIndexOrThrow("endereco")));
                geo.setDetalhes(c.getString(c.getColumnIndexOrThrow("descr")));
                geo.setEmail(c.getString(c.getColumnIndexOrThrow("email_institucional")));
                geo.setTel(c.getString(c.getColumnIndexOrThrow("fone1")));
                geo.setTelSA(c.getString(c.getColumnIndexOrThrow("fone2")));
                geo.setLat(c.getDouble(c.getColumnIndexOrThrow("latitude")));
                geo.setLng(c.getDouble(c.getColumnIndexOrThrow("longitude")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Considere logar o erro ou lançar uma exceção personalizada
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


	public ArrayList<lotacoesVO> buscarTudocod(int cod) {
        ArrayList<lotacoesVO> lista = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new DBUnidadePMAMHelper(ctx).getWritableDatabase();
            String[] busca = new String[]{String.valueOf(cod)};
            c = db.query(table_name, colunas, "id_categoria = ?", busca, null, null, "nro_radio ASC", null);

            if (c.moveToFirst()) {
                do {
                    lotacoesVO geo = new lotacoesVO();
                    geo.setID(c.getInt(c.getColumnIndexOrThrow("_id")));
                    geo.setNro_radio(c.getFloat(c.getColumnIndexOrThrow("nro_radio")));
                    geo.setId_categoria(c.getInt(c.getColumnIndexOrThrow("id_categoria")));
                    geo.setCod_parent(c.getInt(c.getColumnIndexOrThrow("cod_parent")));
                    geo.setNomeLotacaoSuperior(c.getString(c.getColumnIndexOrThrow("nomeLotacaoSuperior")));
                    geo.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
                    geo.setSigla(c.getString(c.getColumnIndexOrThrow("sigla")));
                    geo.setEndereco(c.getString(c.getColumnIndexOrThrow("endereco")));
                    geo.setDetalhes(c.getString(c.getColumnIndexOrThrow("descr")));
                    geo.setEmail(c.getString(c.getColumnIndexOrThrow("email_institucional")));
                    geo.setTel(c.getString(c.getColumnIndexOrThrow("fone1")));
                    geo.setTelSA(c.getString(c.getColumnIndexOrThrow("fone2")));
                    geo.setLat(c.getDouble(c.getColumnIndexOrThrow("latitude")));
                    geo.setLng(c.getDouble(c.getColumnIndexOrThrow("longitude")));
                    lista.add(geo);
                } while (c.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Considere logar o erro ou lançar uma exceção personalizada
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
