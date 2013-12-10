package br.com.cursoandroid.cadastroaluno.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.cursoandroid.cadastroaluno.R;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;

public class ListaAlunoAdapter extends BaseAdapter {

	private final List<Aluno> listaAlunos;
	private final Activity activity;

	public ListaAlunoAdapter(Activity activity, List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return listaAlunos.size();
	}

	@Override
	public long getItemId(int posicao) {
		return listaAlunos.get(posicao).getId();
	}

	@Override
	public Object getItem(int posicao) {
		return listaAlunos.get(posicao);
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
	
		// Infla o layout na view
		View view = activity.getLayoutInflater().inflate(R.layout.item, null);

		Aluno aluno = listaAlunos.get(posicao);

		// Definicao de cor de fundo de linhas pares ou impares
		if (posicao % 2 == 0) {
			view.setBackgroundColor(activity.getResources().getColor(
					R.color.linha_par));
		} else {
			view.setBackgroundColor(activity.getResources().getColor(
					R.color.linha_impar));
		} // continua...

		// Configuracao do nome
		TextView nome = (TextView) view.findViewById(R.id.itemNome);
		nome.setText(aluno.getNome());

		// Configuracao da foto
		Bitmap bmp;
		if (aluno.getFoto() != null) {
			bmp = BitmapFactory.decodeFile(aluno.getFoto());
		} else {
			bmp = BitmapFactory.decodeResource(activity.getResources(),
					R.drawable.ic_no_image);
		}
		bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
		ImageView foto = (ImageView) view.findViewById(R.id.itemFoto);
		foto.setImageBitmap(bmp);

		return view;
	}

}
