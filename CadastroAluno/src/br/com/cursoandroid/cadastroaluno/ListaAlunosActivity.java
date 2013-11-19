package br.com.cursoandroid.cadastroaluno;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunosActivity extends Activity {
	
	//Definicao de constantes
	private final String TAG = "CADASTRO_ALUNO";
	private final String ALUNOS_KEY = "LISTA";
	
	// Atributos de tela
	private EditText edNome;
	private Button botao;
	private ListView lvListagem;
	
	// Colecao de Alunos a ser exibida na tela
	private List<String> listaAlunos;

	// O ArrayAdapter sabe converter listas ou vetores em View
	private ArrayAdapter<String> adapter;
	// Definicao do layout de exibicao da listagem
	private int adapterLayout = android.R.layout.simple_list_item_1;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//Inclusao da lista de alunos no objeto Bundle.Map
		outState.putStringArrayList(ALUNOS_KEY, (ArrayList<String>) listaAlunos);
		//Persistencia do do objeto Bundle
		super.onSaveInstanceState(outState);
		//Lancamento de mensagem de log
		Log.i(TAG, "onSaveInstanceState(): " + listaAlunos);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//Recuperao o estado do objeto Bundle
		super.onRestoreInstanceState(savedInstanceState);
		//Carrega lista de alunos do Bundle.Map
		listaAlunos = savedInstanceState.getStringArrayList(ALUNOS_KEY);
		//Lancamento de mensagem de log
		Log.i(TAG, "onSaveRestoreState(): " + listaAlunos);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Ligacao da Tela ao seu Controlador
		setContentView(R.layout.listaalunoslayout);
		// Ligacao dos componentes de TELA aos atributos da Activity
		edNome = (EditText) findViewById(R.id.edNomeListagem);
		botao = (Button) findViewById(R.id.btAddListagem);
		lvListagem = (ListView) findViewById(R.id.lvListagem);
		
		//Lancando mensagens de LOG no Logcat
		Log.i(TAG, "Execucao do metodo onCreate()");
		
		//Verificando se ha uma lista de alunos salva no Bundle
		if (savedInstanceState != null) {
			//Carregar lista de allunos do Bundle
			listaAlunos = savedInstanceState.getStringArrayList(ALUNOS_KEY);
		}
		if(listaAlunos == null){
			// Inicializacao da Colecao de Alunos
			listaAlunos = new ArrayList<String>();
		}
		
		// O objeto ArrayAdapter sabe converter listas ou vetores em View
		adapter = new ArrayAdapter<String>(this, adapterLayout, listaAlunos);
		// Associacao do Adapter aa ListView
		lvListagem.setAdapter(adapter);
		// Programacao do evento de clique do Botao
		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				listaAlunos.add(edNome.getText().toString());
				edNome.setText("");
				adapter.notifyDataSetChanged();
			}
		});

		// Metodo que "escuta" o evento de Click SIMPLES
		lvListagem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {

				Toast.makeText(ListaAlunosActivity.this,
						"Aluno: " + listaAlunos.get(posicao), Toast.LENGTH_LONG)
						.show();
			}
		});

		// Metodo que "escuta" o evento de Click LONGO
		lvListagem.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {

				Toast.makeText(
						ListaAlunosActivity.this,
						"Aluno: " + listaAlunos.get(posicao) + " [click longo]",
						Toast.LENGTH_LONG).show();

				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_alunos, menu);
		return true;
	}
	
}
