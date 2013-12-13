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

	// URL que serah chamada - http://ip.jsontest.com/
	private final String url = "http://192.168.1.105:8080/AlunoWeb/receber-json";

	// Contexto (tela) para exibicao de mensagens
	private Context context;

	// Barra de progresso
	private ProgressDialog progress;
	
	public EnviaAlunosTask(Context context) {
		this.context = context;
	}

	/*
	 * Metodo executado antes de iniciar o servico
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(context, "Aguarde...",
				"Enviando dados para o servidor web", true, true);
	}

	/*
	 * Metodo executa em outra Thread, diferente da UIThread
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Object... params) {
		AlunoDAO dao = new AlunoDAO(context);
		List<Aluno> lista = dao.listar();
		dao.close();

		String json = new AlunoConverter().toJSON(lista);

		String jsonResposta = new WebClient(url).post(json);

		return jsonResposta;
	}

	/*
	 * Metodo executado pela UIThread
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		progress.dismiss();
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}

}
