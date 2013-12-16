package br.com.cursoandroid.cadastroaluno;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import br.com.cursoandroid.cadastroaluno.fragment.DetalhesProvaFragment;
import br.com.cursoandroid.cadastroaluno.fragment.ListaProvasFragment;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Prova;

public class ProvasActivity extends FragmentActivity {

	private boolean isTablet() {
		return getResources().getBoolean(R.bool.isTablet);
	}
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.provaslayout);

		// Vincular o fragment da listagem ao framelayout
		if (bundle == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();

			if (isTablet()) {
				transaction
					.replace(R.id.provas_lista,
						new ListaProvasFragment(),
						ListaProvasFragment.class.getCanonicalName())
					.replace(
						R.id.provas_view, new DetalhesProvaFragment(), 
						DetalhesProvaFragment.class.getCanonicalName());
			} else {
				transaction
					.replace(R.id.provas_view,
						new ListaProvasFragment(),
						ListaProvasFragment.class.getCanonicalName());
			}

			transaction.commit();
		}
	}
	
	public void selecionarProva(Prova prova){
		Bundle argumentos = new Bundle();
		argumentos.putSerializable("prova", prova);
		
		DetalhesProvaFragment detalhes = new DetalhesProvaFragment();
		detalhes.setArguments(argumentos);
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(
				R.id.provas_view, detalhes, 
				DetalhesProvaFragment.class.getCanonicalName());
		if(!isTablet()){
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

}
