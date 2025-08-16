package br.com.msm.libraryslitedatabaseunidadepmam;

import static com.msm.themes.util.Util.isOnlineV1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.msm.themes.BaseActivity;
import com.msm.themes.ThemeUtil;

import java.util.List;

import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacoesDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_dao.pessoas_lotacaoDAO;
import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacoesVO;
import br.com.msm.librarydatabaseunidadepmam.interfaces.resultUpdate;
import br.com.msm.librarydatabaseunidadepmam.updateUnidadesPMAM;
import br.com.msm.librarydatabaseunidadepmam.util.Util;

import static br.com.msm.librarydatabaseunidadepmam.updateUnidadesPMAM.isAtualizarDados;
import static br.com.msm.librarydatabaseunidadepmam.util.Util.Progress;

public class MainActivity extends BaseActivity {

	private List<lotacoesVO> lt;
	private int totalLotacao = 0;
	private int position = 0;
	private MaterialDialog pg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ThemeUtil.setMyTheme(this, ThemeUtil.THEME_BLUE);
		setContentView(R.layout.activity_main);

		pg = Progress(this);
		if(isAtualizarDados(this)){
			starAlertInforAtualizar();
		}


		Button btn = findViewById(R.id.btn_start);

		final lotacoesDAO dao = new lotacoesDAO(this);

		final pessoas_lotacaoDAO daop = new pessoas_lotacaoDAO(this);

		final Cursor cr = dao.buscarTudo();
		if(cr != null){
			btn.setText("Total de lotações " + cr.getCount());
			lt = 	dao.lista();
			totalLotacao = lt.size();
			cr.close();

		}
		final TextView txtv = findViewById(R.id.textView);

		final TextView txtResult = findViewById(R.id.txtResult);

		position = 0;
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

		 @SuppressLint("SetTextI18n")
         @Override
		 public void onClick(View v) {
			 if(lt != null && position > 1){
				 position --;
				 txtResult.setText(lt.get(position).toString());
				}
			 txtv.setText(String.valueOf(position));
		 }
	 });

		findViewById(R.id.button2).setOnClickListener(v -> {

            if(lt != null && position < totalLotacao){
                position ++;
                txtResult.setText(lt.get(position).toString());
            }
            txtv.setText(String.valueOf(position));
        });



		findViewById(R.id.btn_start).setOnClickListener(v -> starAlertInforAtualizar());
	}




	private void starAlertInforAtualizar() {

		if (isOnlineV1(this)) {
		new MaterialDialog.Builder(this)
				.title(R.string.syncTitle)
				.positiveText(R.string.btn_sim)
				.negativeText(R.string.btn_nao)
				.content(R.string.aguarde_sync)
				.onPositive((dialog, which) -> {
                    pg.show();
                    updateUnidadesPMAM.with(MainActivity.this).start(new resultUpdate() {
                        @Override
                        public void setResult(String result) {
                            pg.dismiss();
                            Log.d("MainActivity ", result);
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });

                }).show();
		}
	}
}
