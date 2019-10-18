package br.com.msm.librarydatabaseunidadepmam;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

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
import static br.com.msm.librarydatabaseunidadepmam.WebServiceApp.json_SyncLotacao;

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
	private int qtdErros = 0;

	private ProgressDialog pgd;
	private String retorno = "Unidades: ";
	public Handler hl = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				pgd.setProgress(pgd.getProgress() + 5);
			} else if (msg.what == 1) {
				pgd.dismiss();
				result.setResult(retorno);
			} else if (msg.what == 2) {
				pgd.setProgress(pgd.getProgress() + 5);
			}
		}
	};

	private String eIternet = "Erro, Verifique sua conexão com a internet";
	private String eTimeOutException = "Tempo limite excedido. Verifique sua conexão com a internet";
	private String eJsonParseException = "Erro Json. dados inválidos";
	private String eDesconhecido = "Desculpe, algo deu errado. Tente novamente mais tarde";

	private ArrayList<ObjJsonPessoaFuncao> listPS;

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
			final MaterialDialog pg = Util.Progress(context);
			pg.show();
			Ion.with(context).load(json_SyncLotacao)
					.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
				@Override
				public void onCompleted(Exception e, JsonObject result) {
					pg.dismiss();
					if (e == null) {
						int QTDOPM = result.get("count").getAsInt();
						pessoas_lotacaoDAO PLDAO = new pessoas_lotacaoDAO(context);
						//se tiver dados isUpadate vai ser falso, se não positivo
						if (PLDAO.tamDb() > 0) {
							PLDAO.deletaTudo();
						}
						startSyncLotacoes(QTDOPM);
					} else if (e.toString().contains("JsonParseException")) {
						r.setResult(eJsonParseException);

					} else if (e.toString().contains("TimeoutException")) {
						r.setResult(eTimeOutException);
					} else {
						if (e.toString().length() > 2) {
							r.setResult(e.toString());
						} else {
							r.setResult(eDesconhecido);
						}

					}

				}
			});
		} else {
			r.setResult(eIternet);
		}
	}


	private void startSyncLotacoes(final int QTDOPM) {
		pgd = new ProgressDialog(context);
		pgd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pgd.setCancelable(false);
		pgd.setTitle("Atualizando dados...");
		pgd.setMax(QTDOPM * 5);
		pgd.setProgressNumberFormat("%1d/%2d");
		pgd.setProgressPercentFormat(NumberFormat.getPercentInstance());
		pgd.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <= QTDOPM; i++) {
					Ion.with(context).load(LotacoesEnderecos)
							.setBodyParameter("currentPage", String.valueOf(i))
							.asJsonArray().setCallback(new FutureCallback<JsonArray>() {
						@Override
						public void onCompleted(Exception e, JsonArray result) {

							if (e == null) {
								qtdErros++;

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
								hl.sendEmptyMessage(0);
							} else if (e.toString().contains("JsonParseException")) {
								retorno += "\n " + eJsonParseException;
								hl.sendEmptyMessage(2);
							} else if (e.toString().contains("TimeoutException")) {

								retorno += "\n " + eTimeOutException;
								hl.sendEmptyMessage(2);
							} else {
								if (e.toString().length() > 2) {
									retorno += "\n " + e.toString();
								} else {
									retorno += "\n " + eDesconhecido;

								}

								hl.sendEmptyMessage(2);
							}
						}
					});
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (i == QTDOPM) {
						if (qtdErros > 0) {
							retorno = String.format("Atualizado com sucesso: %d Unidades", (QTDOPM * 5) - (qtdErros * 5))
									+ String.format("\nErro durante atualização: %d Unidades", qtdErros * 5);
						} else {
							retorno = "Dados atualizados com sucesso!";

						}

						hl.sendEmptyMessage(1);
					}
				}
			}
		}).start();
	}

	public static boolean isAtualizarDados(Context ctx) {
		lotacoesDAO LDAO = new lotacoesDAO(ctx);
		if (LDAO.tamDb() < 50) {
			return true;
		}
		return false;
	}


	private void loadListPessoasAdm() {

		listPS = new ArrayList<ObjJsonPessoaFuncao>();

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
