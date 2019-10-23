package br.com.msm.libraryslitedatabaseunidadepmam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import br.com.msm.librarydatabaseunidadepmam.classes_dao.lotacoesDAO;
import br.com.msm.librarydatabaseunidadepmam.interfaces.resultUpdate;
import br.com.msm.librarydatabaseunidadepmam.updateUnidadesPMAM;

import static br.com.msm.librarydatabaseunidadepmam.updateUnidadesPMAM.isAtualizarDados;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);




		if(isAtualizarDados(this)){
			starAlertInforAtualizar();
		}


		Button btn = findViewById(R.id.btn_start);

		lotacoesDAO dao = new lotacoesDAO(getApplication());
		Cursor cr = dao.buscarTudo();
		btn.setText("Total de lotações " + cr.getCount());
		cr.close();


		findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				starAlertInforAtualizar();
			}
		});
	}




	private void starAlertInforAtualizar() {
		new MaterialDialog.Builder(this)
				.title(R.string.syncTitle)
				.positiveText(R.string.btn_sim)
				.negativeText(R.string.btn_nao)
				.content(R.string.aguarde_sync)
				.onPositive(new MaterialDialog.SingleButtonCallback() {
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

						updateUnidadesPMAM.with(MainActivity.this).start(new resultUpdate() {
							@Override
							public void setResult(String result) {


								Log.d("MainActivity ", result);
								Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
							}
						});

					}
				}).show();
	}
}
