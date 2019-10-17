package br.com.msm.librarydatabaseunidadepmam.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static br.com.msm.librarydatabaseunidadepmam.database.Config.Version;
import static br.com.msm.librarydatabaseunidadepmam.database.Config.oldVersion;


/********* --- PMAM ---***********
 ********* --- DTI ---***********
 ***** CB - MAYCON MEDEIROS*******
 *****--MANAUS - AM -19/09/2016**/

public class DBUnidadePMAMHelper extends SQLiteOpenHelper {
    //atualizado de 11 para 12: 12/06/2018, para adicionar o BD da identidade digital
    // //atualizado de 12 para 13: 11/07/2018, para adicionar o BD da identidade digital
    // //atualizado de 13 para 14: 06/09/2018, para adicionar o BD da identidade digital
    // //atualizado de 14 para 15: 20/09/2018, para adicionar o BD da identidade digital
    // //atualizado de 15 para 16: 23/09/2018, para adicionar o BD da identidade digital
    // //atualizado de 16 para 17: 24/09/2018, para adicionar o BD da identidade digital
    public DBUnidadePMAMHelper(Context context) {
        super(context, "DBUnidadesPMAM", null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // para usuario que estão instalando o app pela primeira vez
        db.execSQL("CREATE TABLE lotacao_superior( _id INTEGER PRIMARY KEY AUTOINCREMENT, id_categoria INT, cod_parent INT, nomeLotacaoSuperior VARCHAR(100));"); //a ordem tem que está correta, pq na hora de fazer o loop com o cursor dar erro na hora de pegar os dados
        //decimal(10,5)indica que o campo deve ser usado para armazenar um valor de até dez dígitos, com até cinco dígitos antes do ponto decimal e até cinco dígitos após o ponto decimal.
        db.execSQL("CREATE TABLE lotacoes( _id INTEGER PRIMARY KEY, id_categoria INT,cod_parent INT, nomeLotacaoSuperior VARCHAR(100), " +
                "nome VARCHAR(100), endereco VARCHAR(250), descr VARCHAR(300)," +
                "email_institucional VARCHAR(100), fone1 VARCHAR(16), fone2 VARCHAR(16), " +
                "latitude DOUBLE, longitude DOUBLE, nro_radio DECIMAL(4,3),sigla VARCHAR(100));");

        db.execSQL("CREATE TABLE pessoas_lotacoes( _id INTEGER PRIMARY KEY AUTOINCREMENT, pessoa_nome VARCHAR(100), id_unidade INT,funcao VARCHAR(100), telefone_corporativo VARCHAR(16));");

        //FICHA MILITAR
        //inicio version 6
        db.execSQL("CREATE TABLE pessoas_dados_pessoais( _id INTEGER PRIMARY KEY, cpf VARCHAR(15), rg VARCHAR(10), matricula VARCHAR(10)," +
                "data_nasc  VARCHAR(10), nome VARCHAR(30), nome_pai VARCHAR(30), nome_mae VARCHAR(30),naturaldo VARCHAR(20),cidadenatal VARCHAR(20)," +
                "alistmilitar VARCHAR(10),dsexo VARCHAR(10),estado_civil VARCHAR(10),religiao VARCHAR(30),pis_pasep VARCHAR(10),ctps_numero VARCHAR(10)," +
                "ctps_serie VARCHAR(15),ctps_uf VARCHAR(15),nr_titulo VARCHAR(15),zona VARCHAR(10),secao VARCHAR(10),uf VARCHAR(20),barba VARCHAR(20)," +
                "bigode VARCHAR(20),cabCor VARCHAR(20),cabTipo VARCHAR(20),cabTom VARCHAR(20),cutis VARCHAR(20),olhosCor VARCHAR(20),olhostom VARCHAR(20)," +
                "Categoria VARCHAR(20),nro_registro VARCHAR(20),data_validade  VARCHAR(10)," +
                "cep VARCHAR(20),bairro VARCHAR(30),numero VARCHAR(5),cidade VARCHAR(30),estado VARCHAR(30),logradouro VARCHAR(50),complemento VARCHAR(30)," +
                "telefone_fixo_pessoal VARCHAR(12),telefone_celular_pessoal1 VARCHAR(12),telefone_celular_pessoal2 VARCHAR(12),telefone_celular_pessoal3 VARCHAR(12)," +
                "telefone_celular_corporativo VARCHAR(12),email_institucional VARCHAR(30),email_pessoal VARCHAR(30)," +
                "data_atualiz VARCHAR(20),whoupdate VARCHAR(30));");



        db.execSQL("CREATE TABLE sessoes_ativas( _id INTEGER PRIMARY KEY AUTOINCREMENT, token_firebase VARCHAR(300), token_grupo VARCHAR(250), token_user VARCHAR(250), last_data_time_on DATETIME, id_user INT, " +
                " version VARCHAR(10), aplicacao VARCHAR(100), modelo_cel VARCHAR(50), sdk VARCHAR(50), ip VARCHAR(20)," +
                "status BOOLEAN);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV == oldVersion) {
            if (newV == Version) {
                /*  verifica se a tablea boletim existe, caso não criar a tabela*/

                // ,sigla VARCHAR(100) decimal(10,5)indica que o campo deve ser usado para armazenar um valor de até dez dígitos, com até cinco dígitos antes do ponto decimal e até cinco dígitos após o ponto decimal.
                if (!isFieldExist(db, "lotacoes", "nro_radio")) {
                    db.execSQL("ALTER TABLE lotacoes ADD COLUMN nro_radio DECIMAL(4,3)");
                } else {
                    Log.d("database", "a Coluna já existe");
                }

                if (!isFieldExist(db, "lotacoes", "sigla")) {
                    db.execSQL("ALTER TABLE lotacoes ADD COLUMN sigla VARCHAR(100)");
                } else {
                    Log.d("database", "a Coluna já existe");
                }

            }
        }

    }

    //usado para verificar se uma coluna existe em uma tabela, pois a mesma pode ter sido colocada em versões antigas e removidas
    private boolean isFieldExist(SQLiteDatabase db, String tableName, String fieldName) {
        boolean isExist = false;

        Cursor res = null;

        try {

            res = db.rawQuery("Select * from " + tableName + " limit 1", null);

            int colIndex = res.getColumnIndex(fieldName);
            if (colIndex != -1) {
                isExist = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (Exception e1) {
            }
        }

        return isExist;
    }

}