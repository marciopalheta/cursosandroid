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
		//Definicoes de comunicacao
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			//Coloca a String JSON no conteudo a ser enviado
			post.setEntity(new StringEntity(json));
			//Informa que o conteudo da requisicao eh JSON e 
			post.setHeader("Content-type", "application/json");
			//Solicita que a resposta tambem seja em JSON
			post.setHeader("Accept", "application/json");
			//Envio do JSON para o server
			HttpResponse response = httpClient.execute(post);
			//Verificacao da reposta
			String jsonResposta = 
					EntityUtils.toString(response.getEntity());
			return jsonResposta;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
