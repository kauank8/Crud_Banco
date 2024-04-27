package farias.paulino.kauan.Spring_Triggers.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farias.paulino.kauan.Spring_Triggers.model.ContaEspecial;

@Repository
public class ContaEspecialDao implements IConta<ContaEspecial> {
	
	@Autowired
	private GenericDao gDao;

	@Override
	public void inserir(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO ContaBancaria VALUES (?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, ce.getNome_cliente());
		ps.setInt(2, ce.getNum_conta());
		ps.setFloat(3, 0);
		ps.execute();

		sql = "INSERT INTO ContaEspecial VALUES (?,?)";
		ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNum_conta());
		ps.setFloat(2, ce.getLimite());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void atualizar(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "Update  ContaBancaria set nome_cliente = ? where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, ce.getNome_cliente());
		ps.setInt(2, ce.getNum_conta());
		ps.execute();

		sql = "Update ContaEspecial set limite = ? where num_conta = ?";
		ps = c.prepareStatement(sql);
		ps.setFloat(1, ce.getLimite());
		ps.setInt(2, ce.getNum_conta());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void excluir(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "Delete ContaEspecial where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNum_conta());
		ps.execute();

		sql = "Delete ContaBancaria where num_conta = ?";
		ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNum_conta());
		ps.execute();

		ps.close();
		c.close();
	}

	@Override
	public ContaEspecial buscar(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM ContaBancaria cb, ContaEspecial ce WHERE cb.num_conta = ce.num_conta and cb.num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNum_conta());
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			ce.setNum_conta(rs.getInt("num_conta"));
			ce.setNome_cliente(rs.getString("nome_cliente"));
			ce.setSaldo(rs.getFloat("saldo"));
			ce.setLimite(rs.getFloat("limite"));
		}
		rs.close();
		ps.close();
		c.close();

		return ce;
	}

	@Override
	public List<ContaEspecial> listar() throws SQLException, ClassNotFoundException {
		List<ContaEspecial> contas = new ArrayList<>();

		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM ContaBancaria cb, ContaEspecial ce WHERE cb.num_conta = ce.num_conta";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ContaEspecial ce = new ContaEspecial();
			ce.setNum_conta(rs.getInt("num_conta"));
			ce.setNome_cliente(rs.getString("nome_cliente"));
			ce.setSaldo(rs.getFloat("saldo"));
			ce.setLimite(rs.getFloat("limite"));
			contas.add(ce);
		}
		rs.close();
		ps.close();
		c.close();

		return contas;
	}

	@Override
	public void sacar(ContaEspecial ce, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "Update  ContaBancaria set saldo = saldo - ? where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, ce.getNum_conta());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void depositar(ContaEspecial ce, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "Update  ContaBancaria set saldo = saldo + ? where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, ce.getNum_conta());
		ps.execute();
		ps.close();
		c.close();
	}



}
