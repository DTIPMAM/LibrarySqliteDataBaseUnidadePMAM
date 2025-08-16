package br.com.msm.librarydatabaseunidadepmam;

import static br.com.msm.librarydatabaseunidadepmam.util.Util.Progress;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacao_superiorDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacoesDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.pessoas_lotacaoDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacao_superiorVO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.pessoas_lotacaoVO;
import br.com.msm.librarydatabaseunidadepmam.interfaces.iLotacao;
import br.com.msm.librarydatabaseunidadepmam.interfaces.resultUpdate;
import br.com.msm.librarydatabaseunidadepmam.util.Util;

public class updateUnidadesPMAM {
    private static HashMap<String, updateUnidadesPMAM> instances = new HashMap<String, updateUnidadesPMAM>();
    private lotacao_superiorDAO LSDAO;
    private lotacoesDAO LDAO;
    private pessoas_lotacaoDAO PLDAO;
    private lotacao_superiorVO LSVO = new lotacao_superiorVO();
    private pessoas_lotacaoVO PLVO = new pessoas_lotacaoVO();
    private String name;
    private Context context;
    private resultUpdate iOpms;
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

    private WebServiceApp ws;

    private MaterialDialog pg;

    private  iLotacao ilotacao;

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
        this.iOpms = callback;
        ws = new WebServiceApp(context);
        ws.updateOPms(iOpms);
    }

    public void startList(iLotacao callback) {
        this.ilotacao = callback;
        ws = new WebServiceApp(context);
        ws.getAndUpdate(ilotacao);
    }

}
