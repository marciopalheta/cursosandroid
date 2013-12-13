package br.com.ecodetech.alunoweb.mb;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import br.com.ecodetech.alunoweb.interceptor.Transactional;
import br.com.ecodetech.alunoweb.model.bean.Cliente;
import br.com.ecodetech.alunoweb.model.dao.ClienteDAO;

@SuppressWarnings("serial")
@ConversationScoped
@Named
public class ClienteMB extends GenericMB<Cliente, ClienteDAO> {

	@Override
	@PostConstruct
	public void carregarLista() {
		setPaginaDados("clientedados");
		setPaginaListagem("index");
		carregarListaPaginada();
	}
	
	@Transactional
	public void excluirTodos(){
		getDao().excluirTodos();
	}

}
