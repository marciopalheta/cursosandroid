package br.com.cursoandroid.cadastroaluno;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.cursoandroid.cadastroaluno.helper.FormularioHelper;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;
import br.com.cursoandroid.cadastroaluno.modelo.dao.AlunoDAO;

public class FormularioActivity extends Activity {
	private Button botao;
	private FormularioHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		// Criacao do objeto Helper
		helper = new FormularioHelper(this);
		botao = (Button) findViewById(R.id.sbSalvar);
		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// Utilizacao do Helper para recuperar dados do Aluno
				Aluno aluno = helper.getAluno();

				// Criacao do objeto DAO - inicio da conexao com o BD
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
				// Chamada do metodo de cadastro do Aluno
				dao.cadastrar(aluno);
				// Encerramento da conexao com o Banco de Dados
				dao.close();

				// Encerrando a Activity
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}

}
