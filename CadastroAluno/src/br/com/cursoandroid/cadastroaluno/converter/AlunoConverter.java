package br.com.cursoandroid.cadastroaluno.converter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Log;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;

public class AlunoConverter {

	private final String TAG = "CADASTRO_ALUNO";

	public String toJSON(List<Aluno> listaAlunos) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			// Definicao da colecao de alunos
			jsonStringer.object().key("list").array().
				object().key("aluno").array();
			for (Aluno aluno : listaAlunos) {
				//Carregar dados do aluno no JSON
				jsonStringer.object()
						.key("id").value(aluno.getId())
						.key("nome").value(aluno.getNome())
						.key("telefone").value(aluno.getTelefone())
						.key("endereco").value(aluno.getEndereco())
						.key("site").value(aluno.getSite())
						.key("email").value(aluno.getEmail())
						.key("nota").value(aluno.getNota())
				.endObject();
			}
			jsonStringer.endArray().endObject().endArray().endObject();
			System.out.println(jsonStringer.toString());
			return jsonStringer.toString();
		} catch (JSONException e) {
			Log.i(TAG, e.getMessage());
			return null;
		}
	}
}
