package br.com.msm.librarydatabaseunidadepmam;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacao_superiorDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacoesDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.pessoas_lotacaoDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacao_superiorVO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacoesVO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.pessoas_lotacaoVO;
import br.com.msm.librarydatabaseunidadepmam.interfaces.resultUpdate;
import br.com.msm.librarydatabaseunidadepmam.util.Util;

import static br.com.msm.librarydatabaseunidadepmam.WebServiceApp.LotacoesEnderecos;

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
                progressor.setProgress(progressor.getCurrentProgress() + 50);
            } else if (msg.what == 1) {
                progressor.setProgress(progressor.getCurrentProgress() + 50);
            } else if (msg.what == 2) {
                progressor.dismiss();
                if (ERRO_OR_SUCESS != null) {
                    result.setResult("Ocorreu erro durante o processo. Alguns endereços não foram atualizados.");
                } else {
                    result.setResult("Dados atualizados com sucesso!");
                }
            }
        }
    };

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
        if (LDAO.tamDb() < 50) {
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
        loadListPessoasAdm();
        startVerificarQuantidadeLotacoes(result);
    }

    private void startVerificarQuantidadeLotacoes(final resultUpdate r) {
        if (Util.isOnline()) {
            progressor = new MaterialDialog.Builder(context)
                    .content("Aguarde...")
                    .progress(false, 100, true)
                    .progressNumberFormat("%1d de %2d")
                    .progressPercentFormat(NumberFormat.getPercentInstance())
                    .show();

            Ion.with(context).load(LotacoesEnderecos)
                    .setBodyParameter("currentPage", String.valueOf(0))
                    .setBodyParameter("listPerPage", String.valueOf(50))
                    .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    String erro = eDesconhecido;
                    if (e == null) {
                        LSDAO.deletaTudo();
                        LDAO.deletaTudo();
                        PLDAO.deletaTudo();
                        if (result.has("totalPager") && result.has("item_count") && result.has("retorno") && result.has("dados")
                                && result.get("retorno").getAsString().equals("YES")) {

                            int totalPager = result.get("totalPager").getAsInt();
                            int item_count = result.get("item_count").getAsInt();
                            popularDadosDatabase(result);
                            startSyncLotacoes(item_count, totalPager);

                        } else if (result.has("message")) {
                            progressor.dismiss();
                            erro = result.get("message").getAsString();
                            r.setResult(erro);
                        } else {
                            progressor.dismiss();
                            r.setResult(erro);
                        }

                    } else {
                        progressor.dismiss();
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

    private void getEnderecoLotacoes(final int currentPage) {


        Ion.with(context).load(LotacoesEnderecos)
                .setBodyParameter("currentPage", String.valueOf(currentPage))
                .setBodyParameter("listPerPage", String.valueOf(50))
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject r) {


                if (e == null) {
                    hl.sendEmptyMessage(1);
                    if (r.has("totalPager") && r.has("item_count") && r.has("retorno") && r.has("dados")
                            && r.get("retorno").getAsString().equals("YES")) {
                        popularDadosDatabase(r);

                    } else if (r.has("message")) {
                        ERRO_OR_SUCESS = r.get("message").getAsString();
                    } else {
                        ERRO_OR_SUCESS = eDesconhecido;
                    }

                } else {
                    hl.sendEmptyMessage(0);

                    if (e.toString().contains("JsonParseException")) {
                        ERRO_OR_SUCESS = eJsonParseException;

                    } else if (e.toString().contains("TimeoutException")) {
                        ERRO_OR_SUCESS = eTimeOutException;

                    } else {
                        if (e.toString().length() > 2) {
                            ERRO_OR_SUCESS = eDesconhecido;
                        }
                    }

                    Log.d("startSyncLotacoes ", " ERRO_OR_SUCESS " + ERRO_OR_SUCESS);
                }

            }
        });

    }

    private void startSyncLotacoes(int item_count, final int totalPager) {
        progressor.setMaxProgress(item_count);
        progressor.setProgress(50);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int pag = 1;
                do {
                    getEnderecoLotacoes(pag);
                    try {
                        Thread.sleep(1000 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pag++;
                    if (pag == totalPager) {
                        hl.sendEmptyMessage(2);
                    }
                } while (totalPager > pag);
            }
        }).start();
    }


    private void popularDadosDatabase(JsonObject r) {

        JsonArray result = r.get("dados").getAsJsonArray();

        for (int y = 0; y < result.size(); y++) {
            JsonObject Obj = result.get(y).getAsJsonObject();
            ID = Obj.get("ID").getAsString();
            id_categoria = Obj.get("id_categoria").getAsString();
            nro_radio = Obj.get("nro_radio").getAsString().isEmpty() ? 0 : Float.parseFloat(Obj.get("nro_radio").getAsString());
            cod_parent = Obj.get("ID_PARENT").getAsString();
            nomeLotacaoSuperior = Obj.get("nomeLotacaoSuperior").getAsString();
            NOME = Obj.get("NOME").getAsString();
            sigla = Obj.get("sigla").getAsString();
            detalhes = Obj.get("descricao").getAsString();
            fone1 = "";
            fone2 = "";
            endereco = "";
            latitude = 0;
            longitude = 0;
            email_institucional = "";

            Log.d("startSyncLotacoes ", "  lotação " + Obj.toString());


            if (!Obj.get("auxLotacoesContatosMany").getAsJsonArray().isJsonNull()) {
                for (int j = 0; j < Obj.get("auxLotacoesContatosMany").getAsJsonArray().size(); j++) {
                    JsonObject ObjCont = Obj.get("auxLotacoesContatosMany").getAsJsonArray().get(j).getAsJsonObject();
                    fone1 = ObjCont.get("fone1").getAsString();
                    fone2 = ObjCont.get("fone2").getAsString();
                    email_institucional = ObjCont.get("email_institucional").getAsString();
                    endereco = ObjCont.get("endereco").getAsString();
                    latitude = Double.parseDouble(ObjCont.get("latitude").getAsString());
                    longitude = Double.parseDouble(ObjCont.get("longitude").getAsString());

                }
            }

            if (!LSDAO.VerificaLSuperior(cod_parent)) {
                LSVO.setId_categoria(Integer.parseInt(id_categoria));
                LSVO.setCod_parent(Integer.parseInt(cod_parent));
                LSVO.setnomeLotacaoSuperior(nomeLotacaoSuperior);
                LSDAO.insert(LSVO);
            } else {
                LSVO.setId_categoria(Integer.parseInt(id_categoria));
                LSVO.setnomeLotacaoSuperior(nomeLotacaoSuperior);
                LSDAO.update(LSVO, cod_parent);
            }

            lotacoesVO LVO = new lotacoesVO();
            if (!LDAO.Verificalotacao(ID)) {
                LVO.setID(Integer.parseInt(ID));
                LVO.setCod_parent(Integer.parseInt(cod_parent));
                LVO.setId_categoria(Integer.parseInt(id_categoria));
                LVO.setNomeLotacaoSuperior(nomeLotacaoSuperior);
                LVO.setNome(NOME);
                LVO.setSigla(sigla);
                LVO.setTel(fone1);
                LVO.setEmail(email_institucional);
                LVO.setTelSA(fone2);
                LVO.setEndereco(endereco);
                LVO.setLat(latitude);
                LVO.setLng(longitude);
                LVO.setDetalhes(detalhes);
                LVO.setNro_radio(nro_radio);
                LDAO.insert(LVO);
            } else {
                LVO.setCod_parent(Integer.parseInt(cod_parent));
                LVO.setId_categoria(Integer.parseInt(id_categoria));
                LVO.setNomeLotacaoSuperior(nomeLotacaoSuperior);
                LVO.setNome(NOME);
                LVO.setTel(fone1);
                LVO.setSigla(sigla);
                LVO.setEmail(email_institucional);
                LVO.setTelSA(fone2);
                LVO.setEndereco(endereco);
                LVO.setLat(latitude);
                LVO.setLng(longitude);
                LVO.setNro_radio(nro_radio);
                LVO.setDetalhes(detalhes);
                LDAO.update(LVO, ID);

            }

            for (int l = 0; l < listPS.size(); l++) {
                if (!Obj.get(listPS.get(l).getListPessoaFunc()).getAsJsonArray().isJsonNull()) {
                    for (int j = 0; j < Obj.get(listPS.get(l).getListPessoaFunc()).getAsJsonArray().size(); j++) {
                        JsonObject Objcontato = null;
                        String nomepessoa, telpessoa = "";


                        Objcontato = Obj.get(listPS.get(l).getListPessoaFunc()).getAsJsonArray().get(j).getAsJsonObject();
                        nomepessoa = Objcontato.get("militar").getAsJsonObject().get("postoNomeGuerra").getAsString();


                        Log.d("startSyncLotacoes ", "   pessoa " + nomepessoa + "  Objcontato " + Objcontato.toString());

                        if (!Objcontato.get(listPS.get(l).getListcontato()).getAsJsonArray().isJsonNull()) {

                            JsonArray arrObj = Objcontato.get(listPS.get(l).getListcontato()).getAsJsonArray();
                            for (int c = 0; c < arrObj.size(); c++) {
                                JsonObject ObjCont = arrObj.get(c).getAsJsonObject();
                                telpessoa = (ObjCont.get("telefone_celular_corporativo").isJsonNull()) ? "" : ObjCont.get("telefone_celular_corporativo").getAsString();
                            }
                        }

                        if (!PLDAO.VerificaPessoasLotacao(nomepessoa)) {
                            PLVO.setId_unidade(Integer.parseInt(ID));
                            PLVO.setFuncao(listPS.get(l).getListFunc());
                            PLVO.setPessoa_nome(nomepessoa);
                            PLVO.setTelefone_corporativo(telpessoa);
                            PLDAO.insert(PLVO);
                        } else {
                            PLVO.setFuncao(listPS.get(l).getListFunc());
                            PLVO.setId_unidade(Integer.parseInt(ID));
                            PLVO.setTelefone_corporativo(telpessoa);
                            PLDAO.update(PLVO, nomepessoa);

                        }
                    }
                }
            }


        }


    }

    private void loadListPessoasAdm() {

        listPS = new ArrayList<>();

        listPS.add(new ObjJsonPessoaFuncao("arrDiretores", "diretorescontato", "DIRETOR"));
        listPS.add(new ObjJsonPessoaFuncao("arrSubDir", "subdircontato", "SUBDIRETOR"));
        listPS.add(new ObjJsonPessoaFuncao("arrComandantes", "cmtcontato", "CMT"));
        listPS.add(new ObjJsonPessoaFuncao("arrSubCmt", "subcmtcontato", "SUBCMT"));
        listPS.add(new ObjJsonPessoaFuncao("arrP1", "p1contato", "P1"));
        listPS.add(new ObjJsonPessoaFuncao("arrP2", "p2contato", "P2"));
        listPS.add(new ObjJsonPessoaFuncao("arrP3", "p3contato", "P3"));
        listPS.add(new ObjJsonPessoaFuncao("arrP4", "p4contato", "P4"));
        listPS.add(new ObjJsonPessoaFuncao("arrCmtGeral", "cmtgeralcontato", "CMT GERAL"));
        listPS.add(new ObjJsonPessoaFuncao("arrSubCmtGeral", "subcmtgeralcontato", "SUBCMT GERAL"));


    }

}
