package br.com.ecodetech.alunoweb.integracao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.ecodetech.alunoweb.interceptor.Transactional;
import br.com.ecodetech.alunoweb.model.bean.Cliente;
import br.com.ecodetech.alunoweb.model.dao.ClienteDAO;

/**
 * Servlet implementation class Entrada
 */
@WebServlet(name = "receber-json", urlPatterns = { "/receber-json" })
public class Entrada extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private ClienteDAO dao;

	@Resource
	private UserTransaction transaction;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Transactional
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Callback for JSONP responses.
		String callback = request.getParameter("callback");

		// MIME type
		String mime = request.getParameter("mime");

		// Access-Control-Allow-Origin
		String allow_origin = request.getParameter("allow_origin");

		// 1. get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String jsonRecebido = "";
		if (br != null) {
			jsonRecebido = br.readLine();
		}

		logger.info(jsonRecebido);

		try {
			// pega o objeto "lista"
			JSONObject jsonObject = new JSONObject(jsonRecebido);
			logger.info(jsonObject);

			JSONArray list = jsonObject.getJSONArray("list");
			logger.info("list: " + list);
			logger.info("list.length(): " + list.length());

			transaction.begin();
			logger.info("Abertura de transação " + transaction);

			for (int a = 0; a < list.length(); a++) {
				JSONObject alunoJson = list.getJSONObject(a);
				logger.info("aluno: " + alunoJson);
				JSONArray listaDeAlunos = alunoJson.getJSONArray("aluno");
				for (int i = 0; i < listaDeAlunos.length(); i++) {
					JSONObject aluno = listaDeAlunos.getJSONObject(i);
					Cliente cliente = new Cliente();
					cliente.setIdMobile(aluno.getLong("id"));
					cliente.setNome(aluno.getString("nome"));
					cliente.setTelefone(aluno.getString("telefone"));
					cliente.setEmail(aluno.getString("email"));
					
					dao.cadastrar(cliente);
					logger.info("cliente cadastrado: " + cliente);
				}
			}

			logger.info("Encerramento de transação " + transaction);
			transaction.commit();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Resposta...
		JSONObject json = new JSONObject();
		String json_text = null;

		try {
			json.put("ip", request.getRemoteAddr());

			if (json_text == null) {
				json_text = json.toString(3);
			}
			if (callback != null) {
				json_text = callback + "(" + json_text + ");";
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		if ("false".equals(allow_origin)) {
			// Do nothing, the user does not want this header.
			// Make a note in logging, since this is a very unusual request.
			System.out
					.println("Access-Control-Allow-Origin header turned off. User's web application may not work.");
		} else {
			// By default, we set access-control-allow-origin to *, which
			// means that all web apps regardless of domain may access
			// and use our services.
			response.setHeader("Access-Control-Allow-Origin", "*");
		}

		String content_type = getContentType(mime, callback);
		response.setContentType(content_type);
		// System.out.println("Content Type: " + content_type);

		// Print out the JSON.
		response.getWriter().println(json_text);
		// System.out.println(json_text);
	}

	private String getContentType(String mime, String callback) {

		String content_type = "text/plain";

		if ("1".equals(mime)) {
			content_type = "application/json";
		} else if ("2".equals(mime)) {
			content_type = "application/javascript";
		} else if ("3".equals(mime)) {
			content_type = "text/javascript";
		} else if ("4".equals(mime)) {
			content_type = "text/html";
		} else if ("5".equals(mime)) {
			content_type = "text/plain";
		} else {
			// The user didn't set up a requested MIME type. We'll set it for
			// them.
			if (callback == null) {
				// If no callback is set
				content_type = "application/json";
			} else {
				// If a callback is set
				content_type = "application/javascript";
			}
		}// end else if content_type is null or does not match an accepted
			// value.

		return content_type;
	}

}
