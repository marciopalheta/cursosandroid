package br.com.cursoandroid.cadastroaluno.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.cursoandroid.cadastroaluno.converter.AlunoConverter;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;
import br.com.cursoandroid.cadastroaluno.modelo.dao.AlunoDAO;
import br.com.cursoandroid.cadastroaluno.suport.WebClient;

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

	//Servidor para teste JSON: http://www.jsontest.com/
	//private final String url = "http://ip.jsontest.com/";
	private final String url = "http://192.168.43.215:8080/AlunoWeb/receber-json";

	// Contexto (tela) para exibicao de mensagens
	private Context context;

	// Barra de progresso
	private ProgressDialog progress;
	
	//Construtor que recebe o contexto da App
	public EnviaAlunosTask(Context context) {
		this.context = context;
	}

	protected void onPreExecute() {
		//Executando a barra de progresso
		progress = ProgressDialog.show(context, "Aguarde...",
				"Enviando dados para o servidor web", true, true);
	}
	protected String doInBackground(Object... params) {
		//Lista de alunos
		AlunoDAO dao = new AlunoDAO(context);
		List<Aluno> lista = dao.listar();
		dao.close();
		//Conversao da lista para JSON
		String json = new AlunoConverter().toJSON(lista);
		//Envio de dados para o servidor remoto
		String jsonResposta = new WebClient(url).post(json);
		//Devolvendo a resposta do servidor
		return jsonResposta;
	}
	protected void onPostExecute(String result) {
		//Encerra a exibicao da barra de progresso
		progress.dismiss();
		//Exibindo a resposta do servidor
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}

}
