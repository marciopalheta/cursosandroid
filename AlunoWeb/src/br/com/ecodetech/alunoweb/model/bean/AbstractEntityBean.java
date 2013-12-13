package br.com.ecodetech.alunoweb.model.bean;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntityBean implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	public AbstractEntityBean() {
	}
	
	public AbstractEntityBean(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object outro) {
		if ((outro == null) || !(outro instanceof AbstractEntityBean)) {
			return false;
		}
		return this.id.longValue() == ((AbstractEntityBean) outro).id
				.longValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
