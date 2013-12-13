package br.com.cursoandroid.cadastroaluno;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import br.com.cursoandroid.cadastroaluno.adapter.ListaAlunoAdapter;
import br.com.cursoandroid.cadastroaluno.converter.AlunoConverter;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;
import br.com.cursoandroid.cadastroaluno.modelo.dao.AlunoDAO;
import br.com.cursoandroid.cadastroaluno.suport.WebClient;
import br.com.cursoandroid.cadastroaluno.task.EnviaAlunosTask;

public class ListaAlunosActivity extends Activity {

	// Definicao de constantes
	private final String TAG = "CADASTRO_ALUNO";
	// private final String ALUNOS_KEY = "LISTA";

	// Atributos de tela
	// private EditText edNome;
	// private Button botao;
	private ListView lvListagem;

	// Colecao de Alunos a ser exibida na tela
	private List<Aluno> listaAlunos;

	// Adapter customizado
	private ListaAlunoAdapter adapter;

	// Aluno selecionado no click longo da ListView
	private Aluno alunoSelecionado = null;

	/*
	 * @Override protected void onSaveInstanceState(Bundle outState) { //
	 * Inclusao da lista de alunos no objeto Bundle.Map
	 * outState.putStringArrayList(ALUNOS_KEY, (ArrayList<String>) listaAlunos);
	 * // Persistencia do do objeto Bundle super.onSaveInstanceState(outState);
	 * // Lancamento de mensagem de log Log.i(TAG, "onSaveInstanceState(): " +
	 * listaAlunos); }
	 * 
	 * @Override protected void onRestoreInstanceState(Bundle
	 * savedInstanceState) { // Recuperao o estado do objeto Bundle
	 * super.onRestoreInstanceState(savedInstanceState); // Carrega lista de
	 * alunos do Bundle.Map listaAlunos =
	 * savedInstanceState.getStringArrayList(ALUNOS_KEY); // Lancamento de
	 * mensagem de log Log.i(TAG, "onSaveRestoreState(): " + listaAlunos); }
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Ligacao da Tela ao seu Controlador
		setContentView(R.layout.listaalunoslayout);
		// Ligacao dos componentes de TELA aos atributos da Activity
		lvListagem = (ListView) findViewById(R.id.lvListagem);
		// informa que a ListView tem Menu de Contexto
		registerForContextMenu(lvListagem);
		// Metodo que "escuta" o evento de Click LONGO
		lvListagem.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				// Marca o aluno selecionado na ListView
				alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao);
				Log.i(TAG, "Aluno selecionado ListView.longClick()"
						+ alunoSelecionado.getNome());
				return false;
			}
		}); // Outras linhas do metodo, aqui...

		// Metodo que "escuta" o evento de Click SIMPLES
		lvListagem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {

				Intent form = new Intent(ListaAlunosActivity.this,
						FormularioActivity.class);

				alunoSelecionado = (Aluno) lvListagem
						.getItemAtPosition(posicao);

				form.putExtra("ALUNO_SELECIONADO", alunoSelecionado);

				startActivity(form);
			}
		});

		Log.i(TAG, "Execucao do metodo onCreate()");
	}

	/**
	 * Metodo que carrega a lista de alunos com dados vindos do BD
	 */
	private void carregarLista() {
		// Criacao do objeto DAO - inicio da conexao com BD
		AlunoDAO dao = new AlunoDAO(this);
		// chamada ao metodo listar
		this.listaAlunos = dao.listar();
		// Fim da conexao com BD
		dao.close();

		// O objeto ListaAlunoAdapter sabe converter listas de alunos em View
		this.adapter = new ListaAlunoAdapter(this, listaAlunos);

		// Associacao do Adapter aa ListView
		this.lvListagem.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Carga da coleção de Alunos
		this.carregarLista();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Definicao do objeto Inflater
		MenuInflater inflater = this.getMenuInflater();

		// Inflar um XML em um Menu vazio
		inflater.inflate(R.menu.menu_principal, menu);

		// Exibir o menu
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		// Verifica o item do menu selecionado
		switch (item.getItemId()) {
		// Verifica se foi selecionado o item NOVO
		case R.id.menu_novo:
			intent = new Intent(ListaAlunosActivity.this,
					FormularioActivity.class);
			startActivity(intent);
			return false;
		// Verifica se foi selecionado o item ENVIAR ALUNOS
		case R.id.menu_enviar_alunos:
			
			//Execucao de tarefa assincrona
			new EnviaAlunosTask(this).execute();
			
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);

		getMenuInflater().inflate(R.menu.menu_contexto, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.menuDeletar:
			excluirAluno();
			break;
		case R.id.menuLigar:
			intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
			startActivity(intent);
			break;
		case R.id.menuEnviarSMS:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
			intent.putExtra("sms_body", "Mensagem de boas vindas :-)");
			startActivity(intent);
			break;
		// Outras opcoes aqui...
		case R.id.menuAcharNoMapa:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("geo:0,0?z=14&q="
					+ alunoSelecionado.getEndereco()));
			intent.putExtra("sms_body", "Mensagem de boas vindas :-)");
			startActivity(intent);
			break;
		case R.id.menuNavegar:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("http:" + alunoSelecionado.getSite()));
			startActivity(intent);
			break;// continua...
		case R.id.menuEnviarEmail:
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_EMAIL,
					new String[] { alunoSelecionado.getEmail() });
			intent.putExtra(Intent.EXTRA_SUBJECT, "Falando sobre o curso");
			intent.putExtra(Intent.EXTRA_TEXT, "O curso foi muito legal");
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void excluirAluno() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Confirma a exclusão de: "
				+ alunoSelecionado.getNome());

		builder.setPositiveButton("Sim", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int witch) {
				AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
				dao.deletar(alunoSelecionado);
				dao.close();
				carregarLista();
				alunoSelecionado = null;
			}
		});

		builder.setNegativeButton("Não", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle("Confirmação de operação");
		dialog.show();
	}
}
