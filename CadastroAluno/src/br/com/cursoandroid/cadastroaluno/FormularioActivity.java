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

	private Aluno alunoParaSerAlterado = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		// Criacao do objeto Helper
		helper = new FormularioHelper(this);
		botao = (Button) findViewById(R.id.sbSalvar);

		// Busca o aluno a ser alterado
		alunoParaSerAlterado = (Aluno) getIntent().getSerializableExtra(
				"ALUNO_SELECIONADO");
		
		if(alunoParaSerAlterado!=null){
			//Atualiza a tela com dados do Aluno
			helper.setAluno(alunoParaSerAlterado);
		} 

		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// Utilizacao do Helper para recuperar dados do Aluno
				Aluno aluno = helper.getAluno();
				// Criacao do objeto DAO - inicio da conexao com o BD
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);

				//Verificacao para salvar ou cadastrar o aluno
				if(aluno.getId()==null){
					dao.cadastrar(aluno);
				}else{
					dao.alterar(aluno);
				}
				
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
