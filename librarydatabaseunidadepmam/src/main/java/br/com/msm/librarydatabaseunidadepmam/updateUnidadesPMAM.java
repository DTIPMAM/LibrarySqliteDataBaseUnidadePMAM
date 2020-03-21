package br.com.msm.librarydatabaseunidadepmam;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacao_superiorDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacoesDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.pessoas_lotacaoDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.dados;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacao_superiorVO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacoesVO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.pessoas_lotacaoVO;
import br.com.msm.librarydatabaseunidadepmam.interfaces.resultUpdate;
import br.com.msm.librarydatabaseunidadepmam.util.Util;

import static br.com.msm.librarydatabaseunidadepmam.WebServiceApp.LotacoesEnderecos;
import static br.com.msm.librarydatabaseunidadepmam.util.Util.Progress;

public class updateUnidadesPMAM {
    private static HashMap<String, updateUnidadesPMAM> instances = new HashMap<String, updateUnidadesPMAM>();
    private lotacao_superiorDAO LSDAO;
    private lotacoesDAO LDAO;
    private pessoas_lotacaoDAO PLDAO;
    private lotacao_superiorVO LSVO = new lotacao_superiorVO();
    private pessoas_lotacaoVO PLVO = new pessoas_lotacaoVO();
    private String name;
    private Context context;
    private resultUpdate result;
    private String ID, id_categoria, cod_parent, nomeLotacaoSuperior, NOME, sigla, email_institucional, detalhes, endereco, fone1, fone2;
    private float nro_radio;
    private double latitude, longitude;

    private String ERRO_OR_SUCESS = null;
    private String retorno = "Unidades: ";
    private String eIternet = "Erro, Verifique sua conexão com a internet";
    // retorno = ;
    private String eTimeOutException = "Tempo limite excedido. Verifique sua conexão com a internet";
    private String eJsonParseException = "Erro Json. dados inválidos";
    private String eDesconhecido = "Desculpe, algo deu errado. Tente novamente mais tarde";
    private ArrayList<ObjJsonPessoaFuncao> listPS;
    private MaterialDialog progressor;
    public Handler hl = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                progressor.setProgress(progressor.getCurrentProgress() + 1);
            } else if (msg.what == 1) {
                progressor.setProgress(progressor.getCurrentProgress() + 1);
            } else if (msg.what == 2) {
                progressor.dismiss();

                result.setResult("Dados atualizados com sucesso!");

            }
        }
    };
    private MaterialDialog pg;

    private updateUnidadesPMAM(Context context, String name) {
        this.context = context;
        this.name = name;

    }

    public static updateUnidadesPMAM with(Context context) {
        return getDefault(context);
    }

    private static updateUnidadesPMAM getDefault(Context context) {
        return getInstance(context, "updateUnidadesPMAM");
    }

    private static updateUnidadesPMAM getInstance(Context context, String name) {
        if (context == null) {
            throw new NullPointerException("O Context não pode ser null");
        } else {
            updateUnidadesPMAM instance = instances.get(name);
            if (instance == null)
                instances.put(name, instance = new updateUnidadesPMAM(context, name));
            return instance;
        }
    }

    public static boolean isAtualizarDados(Context ctx) {
        lotacoesDAO LDAO = new lotacoesDAO(ctx);
        if (LDAO.tamDb() < 100) {
            return true;
        }
        return false;
    }

    public void start(resultUpdate callback) {
        this.result = callback;
        LSDAO = new lotacao_superiorDAO(context);
        LDAO = new lotacoesDAO(context);
        PLDAO = new pessoas_lotacaoDAO(context);
        LSVO = new lotacao_superiorVO();
        startVerificarQuantidadeLotacoes(result);
    }

    private void startVerificarQuantidadeLotacoes(final resultUpdate r) {
        if (Util.isOnline()) {

            pg = Progress(context);
            pg.show();


            Ion.with(context).load(LotacoesEnderecos)
                    .setBodyParameter("currentPage", String.valueOf(0))
                    .setBodyParameter("listPerPage", String.valueOf(200))
                    .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    String erro = eDesconhecido;
                    pg.dismiss();
                    if (e == null) {
                        LSDAO.deletaTudo();
                        LDAO.deletaTudo();
                        PLDAO.deletaTudo();
                        if (result.has("dados")  && result.get("retorno").getAsString().equals("YES")) {
                            //int totalPager = result.get("totalPager").getAsInt();
                          //  int item_count = result.get("item_count").getAsInt();
                            popularDadosDatabase(result);
                        } else if (result.has("message")) {
                            erro = result.get("message").getAsString();
                            r.setResult(erro);
                        } else {
                            r.setResult(erro);
                        }

                    } else {
                        if (e.toString().contains("JsonParseException")) {
                            erro = eJsonParseException;
                        } else if (e.toString().contains("TimeoutException")) {
                            erro = eTimeOutException;

                        }
                        r.setResult(erro);

                    }
                }
            });

        } else {
            r.setResult(eIternet);
        }
    }

    private void popularDadosDatabase(JsonObject r) {

        final JsonArray jsa1 = (r.has("dados") && r.get("dados").isJsonArray()) ? r.get("dados").getAsJsonArray() : new JsonArray();

        if (jsa1.size() > 0) {
            progressor = new MaterialDialog.Builder(context)
                    .content("Atualizando dados...")
                    .progress(false, jsa1.size(), true)
                    .progressNumberFormat("%1d de %2d")
                    .progressPercentFormat(NumberFormat.getPercentInstance())
                    .show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int pag = 0;
                    do {
                        hl.sendEmptyMessage(1);
                        progressor.setProgress(pag);

                        JsonObject jo1 = (jsa1.get(pag).isJsonObject()) ? jsa1.get(pag).getAsJsonObject() : new JsonObject();
                        JsonArray jsaAuxLC = (jo1.has("auxLotacoesContatosMany") && jo1.get("auxLotacoesContatosMany").isJsonArray()) ? jo1.getAsJsonArray("auxLotacoesContatosMany") : new JsonArray();
                        if (jsaAuxLC.size() > 0) {
                            JsonObject jo2 = (jsaAuxLC.get(0).isJsonObject()) ? jsaAuxLC.get(0).getAsJsonObject() : new JsonObject();
                            jo1.addProperty("fone1", (jo2.has("fone1")) ? jo2.get("fone1").getAsString() : "");
                            jo1.addProperty("fone2", (jo2.has("fone2")) ? jo2.get("fone2").getAsString() : "");
                            jo1.addProperty("email_institucional", (jo2.has("email_institucional")) ? jo2.get("email_institucional").getAsString() : "");
                            jo1.addProperty("endereco", (jo2.has("endereco")) ? jo2.get("endereco").getAsString() : "");
                            jo1.addProperty("latitude", (jo2.has("latitude")) ? jo2.get("latitude").getAsString() : "");
                            jo1.addProperty("longitude", (jo2.has("longitude")) ? jo2.get("longitude").getAsString() : "");
                        } else {
                            jo1.addProperty("fone1", "");
                            jo1.addProperty("fone2", "");
                            jo1.addProperty("email_institucional", "");
                            jo1.addProperty("endereco", "");
                            jo1.addProperty("latitude", "");
                            jo1.addProperty("longitude", "");
                        }
                        dados lotacao = new Gson().fromJson(jo1, new TypeToken<dados>() {
                        }.getType());
                        if (!LDAO.Verificalotacao(lotacao.getID())) {
                            LDAO.insert(lotacao);
                        } else {
                            LDAO.update(lotacao, ID);
                        }
                        if (!LSDAO.VerificaLSuperior(lotacao.getID_PARENT())) {
                            LSDAO.insert(lotacao);
                        } else {
                            LSDAO.update(lotacao, lotacao.getID_PARENT());
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pag++;
                        if (pag == jsa1.size()) {
                            hl.sendEmptyMessage(2);
                        }
                    } while (jsa1.size() > pag);
                }
            }).start();


        }else{
            result.setResult(eDesconhecido);
        }



    }



}
