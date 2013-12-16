package br.com.cursoandroid.cadastroaluno.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cursoandroid.cadastroaluno.ProvasActivity;
import br.com.cursoandroid.cadastroaluno.R;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Prova;

public class ListaProvasFragment extends Fragment {

	private ListView lvProvas;
	private Prova provaSelecionada;

	//metodo que cria e devolve uma lista de provas
	private List<Prova> carregarProvas() {
		List<Prova> listaProvas = new ArrayList<Prova>();
		Prova prova = new Prova("13/12/2013", "Estrutura de dados");
		prova.setTopicos(Arrays.asList("Vetores", "Matrizes", "Listas"));
		listaProvas.add(prova);

		prova = new Prova("12/12/2013", "Banco de dados");
		prova.setTopicos(Arrays.asList("DDL", "DML", "SGBD"));
		listaProvas.add(prova);

		return listaProvas;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//Inflar o XML com o layout da lista de provas
		View layoutProvas = inflater.inflate(R.layout.listaprovaslayout,
				container, false);

		//Associar o atributo listView ao componente da tela
		lvProvas = (ListView) layoutProvas.findViewById(R.id.listaprovas);

		//Carregar o adapter e a lista de provas
		lvProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(),
				android.R.layout.simple_list_item_1, carregarProvas()));

		//Mostrar a prova selecionada, no click de um item da ListView
		lvProvas.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				//Marca a prova que foi selecionada
				provaSelecionada = (Prova) adapter.getItemAtPosition(posicao);
				/*Toast.makeText(getActivity(), "Prova: " + provaSelecionada,
						Toast.LENGTH_LONG).show();*/
				ProvasActivity calendarioProvas = (ProvasActivity)getActivity();
				calendarioProvas.selecionarProva(provaSelecionada);
			}
		});

		return layoutProvas;
	}

}
