package br.com.msm.librarydatabaseunidadepmam;

import android.content.Context;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacao_superiorDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacoesDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.pessoas_lotacaoDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.dados;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacao_superiorVO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacoesVO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.retorno;
import br.com.msm.librarydatabaseunidadepmam.interfaces.resultUpdate;

public class WebServiceApp {
    private lotacao_superiorDAO LSDAO;
    private lotacoesDAO LDAO;
    private pessoas_lotacaoDAO PLDAO;
    private lotacao_superiorVO LSVO;

    private Context ctx;

    private String LotacoesEnderecos = "https://pmam.online/comandopmam/index.php/dpa/AuxLotacoes/json_v2getlotacaoperpage";


    public WebServiceApp(Context ctx) {
        this.ctx = ctx;
        LSDAO = new lotacao_superiorDAO(this.ctx);
        LDAO = new lotacoesDAO(this.ctx);
        PLDAO = new pessoas_lotacaoDAO(this.ctx);
        LSVO = new lotacao_superiorVO();
    }

    //lotacoes


    public void LogD(Throwable line, String s) {
        Log.d(getClass().getSimpleName() + " " + line.getStackTrace()[0].getLineNumber(), s);

    }

    public void getAllOpms(resultUpdate iDocs) {
        Ion.with(ctx).load(LotacoesEnderecos)
                .setLogging("API_OPM", Log.DEBUG)
                .asJsonObject().setCallback((e, result) -> {
                    if (result != null) {
                        LogD(new Throwable(), result.toString());
                        retorno r = new Gson().fromJson(result, new TypeToken<retorno>() {
                        }.getType());
                        if (r.getRetorno().equalsIgnoreCase("YES")) {
                            List<CompletableFuture<lotacoesVO>> FuturesList = new ArrayList<>();
                            for (int i = 1; i <= r.getTotalPager(); i++) {
                                FuturesList.add(getAllOpms(r.getTotalPager(), i));
                            }
                            // Quando todos os processamentos de escala individual estiverem completos
                            CompletableFuture.allOf(FuturesList.toArray(new CompletableFuture[0]))
                                    .thenApply(v -> FuturesList.stream()
                                            .map(future -> {
                                                try {
                                                    return future.get(); // .get() é seguro aqui
                                                } catch (Exception e2) {
                                                    LogD(new Throwable(), "Erro ao obter dados: " + e2.getMessage());
                                                    return null; // ou tratar de outra forma
                                                }
                                            })
                                            .filter(Objects::nonNull) // Filtra escalas nulas (em caso de erro no processamento individual)
                                            .collect(Collectors.toList()))
                                    .whenComplete((ListOpms, ex) -> {
                                        if (ex == null) {
                                            iDocs.setResult("Dados atualizados com sucesso.");
                                        } else {
                                            iDocs.setResult(ex.getMessage());
                                        }
                                    });

                        } else {
                            iDocs.setResult("Desculpe, algo deu errado. Tente novamente mais tarde.");
                        }
                    } else if (e != null) {
                        iDocs.setResult(e.getMessage());
                    } else {
                        iDocs.setResult("Desculpe, algo deu errado. Tente novamente mais tarde.");
                    }
                });
    }

    private CompletableFuture<lotacoesVO> getAllOpms(int itemCount, int currentPage) {
        CompletableFuture<lotacoesVO> singlePageFuture = new CompletableFuture<>();
        // final int currentPage = i; // Variável final para uso dentro da lambda
        Ion.with(ctx)
                .load(LotacoesEnderecos)
                .setLogging("API_OPM_PAGE_" + currentPage, Log.DEBUG) // Log específico por página
                .setBodyParameter("currentPage", String.valueOf(currentPage))
                .setBodyParameter("listPerPage", String.valueOf(itemCount))
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (result != null) {
                        try {
                            retorno r = new Gson().fromJson(result, new TypeToken<retorno>() {  }.getType());
                            if (r.getRetorno().equalsIgnoreCase("YES")) {
                                lotacoesVO lotacoesPageData = processaDados(result);
                                singlePageFuture.complete(lotacoesPageData);
                            } else {
                                singlePageFuture.completeExceptionally(new RuntimeException("Página de carregamento de erro desconhecido: " + currentPage));
                            }
                        } catch (Exception parseException) {
                            // tg.LogE(new Throwable(), "Error parsing JSON for page " + currentPage + ": " + parseException.getMessage());
                            singlePageFuture.completeExceptionally(parseException);
                        }
                    } else if (e != null) {
                        // tg.LogE(new Throwable(), "Error loading page " + currentPage + ": " + e.getMessage());
                        singlePageFuture.completeExceptionally(e);
                    } else {
                        // tg.LogE(new Throwable(), "Unknown error loading page " + currentPage);
                        singlePageFuture.completeExceptionally(new RuntimeException("Página de carregamento de erro desconhecido: " + currentPage));
                    }
                });

        return singlePageFuture;
    }

    private lotacoesVO processaDados(JsonObject r) {
        lotacoesVO lotacao = new lotacoesVO();
        JsonArray jsa1 = (r.has("dados") && r.get("dados").isJsonArray()) ? r.get("dados").getAsJsonArray() : new JsonArray();
        if (!jsa1.isEmpty()) {
            JsonObject jo1 = (jsa1.get(0).isJsonObject()) ? jsa1.get(0).getAsJsonObject() : new JsonObject();
            JsonArray jsa2 = (jo1.has("auxLotacoesContatosMany") && jo1.get("auxLotacoesContatosMany").isJsonArray()) ? jo1.get("auxLotacoesContatosMany").getAsJsonArray() : new JsonArray();
            if (!jsa2.isEmpty()) {
                JsonObject jo2 = (jsa2.get(0).isJsonObject()) ? jsa2.get(0).getAsJsonObject() : new JsonObject();
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
            dados bd = new Gson().fromJson(jo1, new TypeToken<dados>() {
            }.getType());
            lotacao = new lotacoesVO(bd);
            if (!LDAO.Verificalotacao(bd.getID())) {
                LDAO.insert(lotacao);
            } else {
                LDAO.update(lotacao, bd.getID());
            }
            if (!LSDAO.VerificaLSuperior(bd.getID_PARENT())) {
                LSDAO.insert(bd);
            } else {
                LSDAO.update(bd, bd.getID_PARENT());
            }
        }
        return lotacao;
    }
}
