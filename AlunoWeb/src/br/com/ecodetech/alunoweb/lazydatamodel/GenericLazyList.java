package br.com.ecodetech.alunoweb.lazydatamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.ecodetech.alunoweb.model.bean.AbstractEntityBean;
import br.com.ecodetech.alunoweb.model.dao.GenericDAO;
import br.com.ecodetech.alunoweb.model.enums.TipoOrdemEnum;

@SuppressWarnings("serial")
public class GenericLazyList<T extends AbstractEntityBean> extends
		LazyDataModel<T> {
	private List<T> datasource;
	private GenericDAO<T> dao;
	private String valorFiltro = "";
	private String campoFiltro = "nome";
	private boolean atualizaTotal;

	public GenericLazyList(GenericDAO<T> dao, List<T> datasource) {
		super();
		this.datasource = datasource;
		this.dao = dao;
	}

	@Override
	public T getRowData(String id) {
		if (id == null) {
			return null;
		}
		Long longId = new Long(id);
		for (T entidade : datasource) {
			if (longId == entidade.getId().longValue()) {
				return entidade;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(T entidade) {
		return entidade.getId();
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {

		TipoOrdemEnum tipo = TipoOrdemEnum.CRESCENTE;
		if (!sortOrder.name().equals("ASCENDING")) {
			tipo = TipoOrdemEnum.DECRESCENTE;
		}

		datasource = dao.listar(first, pageSize, sortField, tipo, campoFiltro, valorFiltro+"%");

		if (datasource == null) {
			datasource = new ArrayList<T>();
		}

		if (getRowCount() <= 0 || atualizaTotal) {
			setRowCount(dao.getTotalRegistros(campoFiltro, valorFiltro+"%").intValue());
			atualizaTotal = false;
		}

		setPageSize(pageSize);

		return datasource;
	}

	public List<T> getDatasorce() {
		return datasource;
	}

	public boolean isAtualizaTotal() {
		return atualizaTotal;
	}

	public void setAtualizaTotal(boolean atualizaTotal) {
		this.atualizaTotal = atualizaTotal;
	}

	public String getValorFiltro() {
		return valorFiltro;
	}

	public void setValorFiltro(String valorFiltro) {
		this.valorFiltro = valorFiltro;
	}

	public String getCampoFiltro() {
		return campoFiltro;
	}

	public void setCampoFiltro(String campoFiltro) {
		this.campoFiltro = campoFiltro;
	}

}
