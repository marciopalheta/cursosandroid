package br.com.ecodetech.alunoweb.model.bean;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Cliente extends AbstractEntityBean{

	private String nome;
	private String telefone;
	private String email;
	private Long idMobile;

	@Override
	public String toString() {
		return nome+" - "+email+" ["+telefone+"] ("+idMobile+")";
	}
	
	public Long getIdMobile() {
		return idMobile;
	}

	public void setIdMobile(Long idMobile) {
		this.idMobile = idMobile;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
