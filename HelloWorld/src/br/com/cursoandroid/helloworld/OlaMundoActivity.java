package br.com.cursoandroid.helloworld;

//imports omitidos 
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OlaMundoActivity extends Activity {
	// Atributos que representam componentes de tela
	private EditText edNome;
	private Button btEnviar;
	private Button btModal;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.olamundolayout);
		Log.i("CICLO DE VIDA", "onCreate()");
		// Busca por referencias aos componentes de tela
		edNome = (EditText) findViewById(R.id.edNome);
		btEnviar = (Button) findViewById(R.id.btExibir);
		btModal = (Button) findViewById(R.id.btModal);
		// Definicao do evento de clique do botao
		btEnviar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), edNome.getText(),
						Toast.LENGTH_LONG).show();
			}
		});
		// Definicao do evento de clique do botao - exibir confirmacao
		btModal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						OlaMundoActivity.this);
				builder.setMessage("Operação realizada com sucesso");
				builder.setNeutralButton("OK", null);
				AlertDialog dialog = builder.create();
				dialog.setTitle("Resultado da execução");
				dialog.show();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("CICLO DE VIDA", "onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("CICLO DE VIDA", "onResume()");
	}

	// Outros metodos aqui...

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action
		// bar if it is present.
		getMenuInflater().inflate(R.menu.ola_mundo, menu);
		return true;
	}
}
