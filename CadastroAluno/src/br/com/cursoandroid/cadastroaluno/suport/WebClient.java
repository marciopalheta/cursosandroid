package br.com.cursoandroid.cadastroaluno.suport;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient {

	private final String url;

	public WebClient(String url) {
		this.url = url;
	}
	
	public String post(String json){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new StringEntity(json));
			//Informa que o conteudo da requisicao eh JSON e 
			//Solicita que a resposta tambem seja em JSON
			post.setHeader("Content-type", "application/json");
			post.setHeader("Accept", "application/json");
			//Execucao da requisicao
			HttpResponse response = httpClient.execute(post);
			//Verificacao da reposta
			String jsonResposta = EntityUtils.toString(response.getEntity());
			
			return jsonResposta;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
