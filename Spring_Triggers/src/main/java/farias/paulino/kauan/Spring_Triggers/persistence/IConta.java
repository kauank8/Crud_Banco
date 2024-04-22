package farias.paulino.kauan.Spring_Triggers.persistence;

import java.sql.SQLException;
import java.util.List;

public interface IConta<T> {

	public void inserir(T t) throws SQLException, ClassNotFoundException;
	public void atualizar(T t) throws SQLException, ClassNotFoundException;
	public void excluir(T t) throws SQLException, ClassNotFoundException;
	public T buscar(T t) throws SQLException, ClassNotFoundException;
	public List<T> listar() throws SQLException, ClassNotFoundException;
	public void sacar(T t, float valor) throws SQLException, ClassNotFoundException;
	public void depositar(T t, float valor) throws SQLException, ClassNotFoundException;
	
}
