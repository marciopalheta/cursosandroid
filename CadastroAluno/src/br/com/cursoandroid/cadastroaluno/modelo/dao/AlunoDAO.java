package br.com.cursoandroid.cadastroaluno.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.cursoandroid.cadastroaluno.modelo.bean.Aluno;

/**
 * Classe para persistência de Dados do Aluno
 * 
 * @author marciopalheta
 */
public class AlunoDAO extends SQLiteOpenHelper {

	// Constantes para auxilio no controle de versoes
	private static final int VERSAO = 1;
	private static final String TABELA = "Aluno";
	private static final String DATABASE = "MPAlunos";

	// Constante para log no Logcat
	private static final String TAG = "CADASTRO_ALUNO";

	public AlunoDAO(Context context) {

		// Chamada o construtor que sabe acessar o BD
		super(context, DATABASE, null, VERSAO);
	}

	/**
	 * Metodo responsavel pela criacao da estrutura do BD
	 * */
	public void onCreate(SQLiteDatabase database) {

		// Definicao do comando DDL a ser executado
		String ddl = "CREATE TABLE " + TABELA + "( "
				+ "id INTEGER PRIMARY KEY, "
				+ "nome TEXT, telefone TEXT, endereco TEXT, "
				+ "site TEXT, email TEXT, foto TEXT, " + "nota REAL)";

		// Execucao do comando no SQLite
		database.execSQL(ddl);
		Log.i(TAG, "Tabela criada: " + TABELA);
	}

	/**
	 * Metodo responsavel pela atualizacao das estrutura das tableas
	 * */
	public void onUpgrade(SQLiteDatabase database, int versaoAntiga,
			int versaoNova) {

		// Definicao do comando para destruir a tabela Aluno
		String sql = "DROP TABLE IF EXISTS " + TABELA;

		// Execucao do comando de destruicao
		database.execSQL(sql);
		Log.i(TAG, "Tabela excluida: " + TABELA);

		// Chamada ao metodo de contrucao da base de dados
		onCreate(database);
	}

	@Override
	public void onDowngrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		database.execSQL(sql);
		onCreate(database);
	}

	public void cadastrar(Aluno aluno) {

		// Objeto para armazenar os valores dos campos
		ContentValues values = new ContentValues();

		// Definicao de valores dos campos da tabela
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("email", aluno.getEmail());
		values.put("foto", aluno.getFoto());
		values.put("nota", aluno.getNota());

		// Inserir dados do Aluno no BD
		getWritableDatabase().insert(TABELA, null, values);
		Log.i(TAG, "Aluno cadastrado: " + aluno.getNome());
	}

	public void alterar(Aluno aluno) {
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("email", aluno.getEmail());
		values.put("foto", aluno.getFoto());
		values.put("nota", aluno.getNota());

		// Colecao de valores de parametros do SQL
		String[] args = { aluno.getId().toString() };

		// Altera dados do Aluno no BD
		getWritableDatabase().update(TABELA, values, "id=?", args);
		Log.i(TAG, "Aluno alterado: " + aluno.getNome());
	}

	public List<Aluno> listar() {
		// Definicao da colecao de alunos
		List<Aluno> lista = new ArrayList<Aluno>();

		// Definicao da instrucao SQL
		String sql = "Select * from Aluno order by nome";

		// Objeto que recebe os registros do banco de dados
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try {
			while (cursor.moveToNext()) {
				// Criacao de nova referencia para Aluno
				Aluno aluno = new Aluno();
				// Carregar os atributos de Aluno com dados do BD
				aluno.setId(cursor.getLong(0));
				aluno.setNome(cursor.getString(1));
				aluno.setTelefone(cursor.getString(2));
				aluno.setEndereco(cursor.getString(3));
				aluno.setSite(cursor.getString(4));
				aluno.setEmail(cursor.getString(5));
				aluno.setFoto(cursor.getString(6));
				aluno.setNota(cursor.getDouble(7));
				// Adicionar novo Aluno aa lista
				lista.add(aluno);
			}
		} catch (SQLException e) {
			Log.e(TAG, e.getMessage());
		} finally {
			cursor.close();
		}
		return lista;
	}

	/**
	 * Metodo responsavel pela exclusao de um Aluno do BD
	 * 
	 * @param aluno
	 *            a ser excluido
	 */
	public void deletar(Aluno aluno) {
		// Definicao de array de parametros
		String[] args = { aluno.getId().toString() };

		// Exclusao do Aluno
		getWritableDatabase().delete(TABELA, "id=?", args);

		Log.i(TAG, "Aluno deletado: " + aluno.getNome());
	}

	/**
	 * Metodo que verifica se um numero de telefone pertence a um aluno
	 * @param telefone
	 * @return true, se o telefone pertence a um aluno
	 */
	public boolean isAluno(String telefone) {
		String sql = "select * from " + TABELA + " where telefone = ?";
		String[] valores = { telefone };
		Cursor cursor = null;
		try {
			//Abertura da conexao com BD e execucao da consulta
			cursor = getReadableDatabase().rawQuery(sql, valores);
			//Retorna true, se for devolvida alguma linha
			return cursor.getCount() > 0;
		} catch (SQLException e) {
			Log.e(TAG, e.getMessage());
			return false;
		} finally {
			cursor.close();
		}
	}

}
