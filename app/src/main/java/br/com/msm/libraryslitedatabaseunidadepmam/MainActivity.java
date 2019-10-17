package br.com.msm.libraryslitedatabaseunidadepmam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

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
								Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
							}
						});

					}
				}).show();
	}
}
