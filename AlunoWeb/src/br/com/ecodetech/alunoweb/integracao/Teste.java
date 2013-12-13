package br.com.ecodetech.alunoweb.integracao;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

public class Teste {
	public static void main(String[] args) throws JSONException { // entrada:
																	// string
																	// json com
																	// dados dos
																	// filmes
		String jsonStr = "{"
				+ "\"devmovies\":"
				+ "{"
				+ "\"filme\":"
				+ "["
				+ "{"
				+ "\"id\":1,"
				+ "\"titulo\":\"Os Arquivos JSON\","
				+ "\"ano\":1998,"
				+ "\"resumo\":\"A história dos arquivos muito leves\","
				+ "\"generos\":[\"Ação\",\"Sci-fi\",\"Drama\"],"
				+ "\"elenco\":[\"Gillian Triggerson\",\"David Markupovny\"]"
				+ "},"
				+ "{"
				+ "\"id\":2,"
				+ "\"titulo\":\"Sexta-feira 13: JSON Vive\","
				+ "\"ano\":1986,"
				+ "\"generos\":[\"Ação\",\"Horror\"],"
				+ "\"elenco\":[\"Ann Labelvalue Pair\", \"Jennifer Json\", \"John Java\"]"
				+ "}" + "]" + "}" + "}";

		// (1) importa a string para um JSONObject
		JSONObject devMovies = new JSONObject(jsonStr);
		System.out.println(jsonStr);

		// (2) trabalha o conteúdo do JSONObject // imprimindo os dados dos
		// filmes, a partir do objeto devMovies

		// obtém o objeto "devmovies"
		JSONObject filmes = devMovies.getJSONObject("devmovies");
		// obtém o array contendo todos os filmes de "devmovies"
		JSONArray arrFilmes = filmes.getJSONArray("filme");
		// monta laço que percorre o array e imprime os dados de cada filme
		for (int i = 0; i < arrFilmes.length(); i++) { // recupera filme de
														// índice "i" no array
			JSONObject f = arrFilmes.getJSONObject(i);
			System.out.println("id: " + f.getInt("id"));
			System.out.println("titulo: " + f.getString("titulo"));
			System.out.println("ano: " + f.getInt("ano"));

			// como o campo "resumo" é opcional, é preciso usar o método "opt()"
			// se o valor "-" for omitido, a palavra "null" é impressa no
			// segundo filme
			System.out.println("resumo: " + f.optString("resumo", "-"));

			// gêneros
			JSONArray arrGeneros = f.getJSONArray("generos");
			for (int k = 0; k < arrGeneros.length(); k++) {
				System.out.println("genero " + (k + 1) + ": "
						+ arrGeneros.getString(k));
			}
			// elenco
			System.out.println("elenco: ");
			JSONArray arrAtores = f.getJSONArray("elenco");
			for (int j = 0; j < arrAtores.length(); j++) {
				System.out.println("\t" + arrAtores.getString(j));
			}
			System.out.println();
		}
	}
}
