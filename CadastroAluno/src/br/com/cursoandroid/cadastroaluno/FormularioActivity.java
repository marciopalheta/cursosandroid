package br.com.cursoandroid.cadastroaluno;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FormularioActivity extends Activity {
	
	//Atributos para manipulacao de componentes de tela
	private String foto;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}

}
