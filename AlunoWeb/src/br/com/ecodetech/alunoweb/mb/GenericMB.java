package br.com.ecodetech.alunoweb.mb;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import br.com.ecodetech.alunoweb.interceptor.Transactional;
import br.com.ecodetech.alunoweb.lazydatamodel.GenericLazyList;
import br.com.ecodetech.alunoweb.model.bean.AbstractEntityBean;
import br.com.ecodetech.alunoweb.model.dao.GenericDAO;

@SuppressWarnings("serial")
public abstract class GenericMB<T extends AbstractEntityBean, E extends GenericDAO<T>>
		implements Serializable {

	@Inject
	private E dao;
	@Inject
	private Conversation conversa;
	@Inject
	private transient Logger logger;

	private T entidade;
	private String valorFiltro = "";
	private List<T> listaEntidades = new ArrayList<T>();
	private String paginaListagem;
	private String paginaDados;
	private LazyDataModel<T> listaEntidadesPaginada = null;

	/**
	 * Metodo abstrado usado para inicializar a lista de entidades
	 */
	public abstract void carregarLista();

	/**
	 * Inicia a lista paginada
	 */
	public void carregarListaPaginada() {
		listaEntidadesPaginada = new GenericLazyList<T>(getDao(),
				listaEntidades);
	}

	/**
	 * @return lista paginada
	 */
	public LazyDataModel<T> getListaEntidadesPaginada() {
		if (listaEntidadesPaginada == null) {
			carregarListaPaginada();
		}
		return listaEntidadesPaginada;
	}

	/**
	 * Metodo que inicia a conversacao
	 */
	public void iniciarConversa() {

		if (!FacesContext.getCurrentInstance().isPostback()
				&& conversa.isTransient()) {
			conversa.begin();
			logger.debug("iniciarConversa()INICIANDO:" + conversa.getId());
		}
		logger.debug("iniciarConversa():" + conversa.getId());
	}

	/**
	 * Metodo que encerra a conversa
	 * 
	 * @return String com a url da pagina de listagem
	 */
	public String encerrarConversa() {
		logger.debug("encerrarConversa().ID:" + conversa.getId());
		logger.debug("encerrarConversa().isTransient:" + conversa.isTransient());
		if (!conversa.isTransient()) {
			conversa.end();
		}
		return paginaListagem;
	}

	/**
	 * Metodo que exibe a tela de cadastro
	 * 
	 * @return String com a url da pagina de cadastro
	 */
	public String exibirCadastrar() {
		criarEntidade();
		return paginaDados;
	}

	/**
	 * Metodo que exibe a tela de edicao de dados
	 * 
	 * @return String com a url da tela de edicao
	 */
	public String exibirAlterar() {
		// carregarEntidade();
		return paginaDados;
	}

	/**
	 * Metodo usado para o cancelamento da conversacao
	 * 
	 * @return String com a url da tela de listagem
	 */
	public String cancelar() {
		return encerrarConversa();
	}

	/**
	 * Metodo usado para cadastrar ou alterar dados de uma entidade
	 * 
	 * @return String com a url da pagina de listagem
	 */
	@Transactional
	public String salvar() {
		try {
			if (entidade.getId() == null) {
				dao.cadastrar(entidade);
			} else {
				dao.alterar(entidade);
			}
			/*
			 * ECodeTechUtil.setMessageView(FacesContext.getCurrentInstance(),
			 * ChaveMensagemEnum.ALERTA_OPERACAO_RESULTADO,
			 * ChaveMensagemEnum.ALERTA_OPERACAO_SUCESSO,
			 * FacesMessage.SEVERITY_INFO);
			 */
		} catch (Exception e) {
			/*
			 * ECodeTechUtil.setMessageView(FacesContext.getCurrentInstance(),
			 * ChaveMensagemEnum.ALERTA_OPERACAO_RESULTADO,
			 * ChaveMensagemEnum.ALERTA_OPERACAO_FALHA,
			 * FacesMessage.SEVERITY_ERROR);
			 */
			e.printStackTrace();
		} finally {
			encerrarConversa();
		}
		return paginaListagem;
	}

	@Transactional
	public void excluir() {
		try {
			getDao().excluir(entidade);

			// carregarLista();
			listaEntidades.remove(entidade);
			((GenericLazyList<T>) listaEntidadesPaginada)
					.setAtualizaTotal(true);

			/*
			 * ECodeTechUtil.setMessageView(FacesContext.getCurrentInstance(),
			 * ChaveMensagemEnum.ALERTA_OPERACAO_RESULTADO,
			 * ChaveMensagemEnum.ALERTA_OPERACAO_SUCESSO,
			 * FacesMessage.SEVERITY_INFO);
			 */
			logger.info("excluir(): sucesso");
		} catch (Exception e) {
			/*
			 * ECodeTechUtil.setMessageView(FacesContext.getCurrentInstance(),
			 * ChaveMensagemEnum.ALERTA_OPERACAO_RESULTADO,
			 * ChaveMensagemEnum.ALERTA_OPERACAO_FALHA,
			 * FacesMessage.SEVERITY_ERROR);
			 */
			logger.error("excluir(): falha" + e.getMessage());
		}
	}

	public String getValorFiltro() {
		return valorFiltro;
	}

	public void setValorFiltro(String valorFiltro) {
		this.valorFiltro = valorFiltro;
	}

	public void filtrar() {
		GenericLazyList<T> list = (GenericLazyList<T>) getListaEntidadesPaginada();
		list.setValorFiltro(valorFiltro);
		list.setCampoFiltro("nome");
		list.setAtualizaTotal(true);
	}

	/**
	 * @return a conversa atual
	 */
	public Conversation getConversa() {
		return conversa;
	}

	/**
	 * Metodo que cria novas entidades
	 */
	public void criarEntidade() {
		try {
			entidade = getClassType().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que carrega entidades do BD, baseado no id
	 */
	public void carregarEntidade() {
		entidade = dao.consultar(entidade.getId());
	}

	/**
	 * Devolve o tipo da classe informada como parametro T
	 **/
	@SuppressWarnings("unchecked")
	private Class<T> getClassType() {

		/*System.out.println("getClass(): " + getClass());
		System.out.println("getClass().getGenericSuperclass(): "
				+ getClass().getGenericSuperclass());
		System.out.println("getClass().getSuperclass(): "
				+ getClass().getSuperclass());
		System.out
				.println("getClass().getSuperclass().getGenericSuperclass(): "
						+ getClass().getSuperclass().getGenericSuperclass());*/

		// Class<?> superClass = getClass().getSuperclass();
		// ParameterizedType parameterizedType = (ParameterizedType)
		// superClass.getGenericSuperclass();

		ParameterizedType parameterizedType = (ParameterizedType) getClass().getSuperclass()
				.getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	/**
	 * @return entidade atual
	 */
	public T getEntidade() {

		if (entidade == null) {
			criarEntidade();
		}

		return entidade;
	}

	/**
	 * Metodo usado para alterar a entidade atual
	 * 
	 * @param entidade
	 */
	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	/**
	 * Metodo que retorna a instancia atual do DAO
	 * 
	 * @return GenericDAO<E>
	 */
	public E getDao() {
		return dao;
	}

	/**
	 * @return Lista de entidades <T>
	 */
	public List<T> getListaEntidades() {
		return listaEntidades;
	}

	/**
	 * Atualiza a lista de entidades atual
	 * 
	 * @param listaEntidades
	 */
	public void setListaEntidades(List<T> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	/**
	 * @return pagina de listagem de entidades
	 */
	public String getPaginaListagem() {
		return paginaListagem;
	}

	/**
	 * Atualiza a pagina de listagem das entidades
	 * 
	 * @param paginaListagem
	 */
	public void setPaginaListagem(String paginaListagem) {
		this.paginaListagem = paginaListagem + "?faces-redirect=true";
	}

	/**
	 * @return pagina para edicao ou cadastro de entidades
	 */
	public String getPaginaDados() {
		return paginaDados;
	}

	/**
	 * Metodo que atualiza a pagina de dados
	 * 
	 * @param paginaDados
	 */
	public void setPaginaDados(String paginaDados) {
		this.paginaDados = paginaDados + "?faces-redirect=true";
	}

}
