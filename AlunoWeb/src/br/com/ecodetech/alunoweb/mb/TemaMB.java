package br.com.ecodetech.alunoweb.mb;

import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TemaMB {

	private String tema;
	private Map<String, String> listaTemas;

	public TemaMB() {

		listaTemas = new TreeMap<String, String>();
		listaTemas.put("Afterdark", "afterdark");
		listaTemas.put("Afternoon", "afternoon");
		listaTemas.put("Aristo", "aristo");
		listaTemas.put("Cupertino", "cupertino");
		listaTemas.put("Dark Hive", "dark-hive");
		listaTemas.put("Delta", "delta");
		listaTemas.put("Eggplant", "eggplant");
		listaTemas.put("Home", "home");
		listaTemas.put("Redmond", "redmond");
		listaTemas.put("Rocket", "rocket");
		listaTemas.put("South Street", "south-street");
		listaTemas.put("Start", "start");
		listaTemas.put("Sunny", "sunny");
		listaTemas.put("Trontastic", "trontastic");
		listaTemas.put("UI Darkness", "ui-darkness");
		listaTemas.put("UI Lightness", "ui-lightness");
		listaTemas.put("Vader", "vader");
		
		/*
		listaTemas.put("Afterwork", "afterwork");
		listaTemas.put("Black Tie", "black-tie");
		listaTemas.put("Blitzer", "blitzer");
		listaTemas.put("Bluesky", "bluesky");
		listaTemas.put("Bootstrap", "bootstrap");
		listaTemas.put("Casablanca", "casablanca");
		listaTemas.put("Cruze", "cruze");
		listaTemas.put("Swanky Purse", "swanky-purse");
		listaTemas.put("Overcast", "overcast");
		listaTemas.put("Flick", "flick");
		listaTemas.put("Hot Sneaks", "hot-sneaks");
		listaTemas.put("Smoothness", "smoothness");
		listaTemas.put("Glass X", "glass-x");
		listaTemas.put("Excite Bike", "excite-bike");*/

		// aqui voce pode retornar seu tema atual do banco
	}

	public void salvarTema() {
		// aqui voce pode salvar seu tema no banco
	}

	public String getTema() {
		if (tema == null) {
			tema = "redmond";
		}
		return tema;
	}

	public void setTema(String meuTema) {
		this.tema = meuTema;
	}

	public Map<String, String> getListaTemas() {
		return listaTemas;
	}

	public void setListaTemas(Map<String, String> themes) {
		this.listaTemas = themes;
	}
}
