package br.com.ecodetech.alunoweb.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ecodetech.alunoweb.model.bean.AbstractEntityBean;
import br.com.ecodetech.alunoweb.model.enums.TipoOrdemEnum;

@SuppressWarnings({ "unchecked", "serial" })
public class GenericDAO<T extends AbstractEntityBean> implements Serializable {

	@Inject
	private EntityManager em;

	private Class<T> classe;

	public GenericDAO() {
		this.classe = getClassType();
	}

	private Class<T> getClassType() {

		/*
		 * System.out.println("getClass(): " + getClass());
		 * System.out.println("getClass().getGenericSuperclass(): " +
		 * getClass().getGenericSuperclass());
		 * System.out.println("getClass().getSuperclass(): " +
		 * getClass().getSuperclass()); System.out
		 * .println("getClass().getSuperclass().getGenericSuperclass(): " +
		 * getClass().getSuperclass().getGenericSuperclass());
		 */

		// Class<?> superClass = getClass().getSuperclass();
		// ParameterizedType parameterizedType = (ParameterizedType)
		// superClass.getGenericSuperclass();

		ParameterizedType parameterizedType = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public void cadastrar(T t) {
		em.persist(t);
	}

	public void alterar(T t) {
		em.merge(t);
	}

	public void excluir(T t) {
		em.remove(em.merge(t));
	}

	public T consultar(Long id) {
		return (T) em.getReference(classe, id);
	}

	public List<T> listar() {
		return em.createQuery(("From " + classe.getName())).getResultList();
	}
	
	public void excluirTodos(){
		for(T t: listar()){
			excluir(t);
		}
	}

	/**
	 * Metodo que retorna uma lista paginada
	 * 
	 * @param inicio
	 *            - primeiro elemento da lista
	 * @param maxPorPagina
	 *            - total de registros da lista
	 * @param campoOrdenar
	 *            - campo base da ordenacao
	 * @param tipoOrdem
	 *            - crescente (ASC) ou decrescente (DESC)
	 * @param filtros
	 *            - criterios para filtro de registros
	 * @return lista paginada
	 */

	public List<T> listar(int inicio, int maxPorPagina, String campoOrdenar,
			TipoOrdemEnum tipoOrdem, String campoFiltro, String valorFiltro) {
		String sql = "select c from " + classe.getName() + " c ";

		if (campoOrdenar == null || campoOrdenar.equals("")) {
			campoOrdenar = campoFiltro;
		}

		sql += " where UPPER(c." + campoFiltro + ") like UPPER(:" + campoFiltro
				+ ")" + " order by c." + campoOrdenar + " "
				+ tipoOrdem.getNome();

		Query query = em.createQuery(sql);
		query.setParameter(campoFiltro, valorFiltro);
		query.setFirstResult(inicio);
		query.setMaxResults(maxPorPagina);

		return query.getResultList();
	}

	public EntityManager getEm() {
		return em;
	}

	public Long getTotalRegistros(String campoFiltro, String valorFiltro) {
		String sql = "select COUNT(c) from " + classe.getName() + " c ";

		sql += " where UPPER(c." + campoFiltro + ") like UPPER(:" + campoFiltro
				+ ")";
		Query query = em.createQuery(sql);
		query.setParameter(campoFiltro, valorFiltro);
		Number result = (Number) query.getSingleResult();
		return result.longValue();
	}

}
