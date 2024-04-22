package farias.paulino.kauan.Spring_Triggers.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farias.paulino.kauan.Spring_Triggers.model.ContaPoupanca;

@Repository
public class ContaPoupancaDao implements IConta<ContaPoupanca> {

	@Autowired
	private GenericDao gDao;

	@Override
	public void inserir(ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO ContaBancaria VALUES (?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cp.getNome_cliente());
		ps.setInt(2, cp.getNum_conta());
		ps.setFloat(3, 0);
		ps.execute();

		sql = "INSERT INTO ContaPoupanca VALUES (?,?)";
		ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNum_conta());
		ps.setInt(2, cp.getRendimento());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void atualizar(ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "Update  ContaBancaria set nome_cliente = ? where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cp.getNome_cliente());
		ps.setInt(2, cp.getNum_conta());
		ps.execute();

		sql = "Update ContaPoupanca set dia_rendimento = ? where num_conta = ?";
		ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getRendimento());
		ps.setInt(2, cp.getNum_conta());
		ps.execute();
		ps.close();
		c.close();

	}

	@Override
	public void excluir(ContaPoupanca cp) throws SQLException, ClassNotFoundException {

		Connection c = gDao.getConnection();
		String sql = "Delete ContaPoupanca where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNum_conta());
		ps.execute();

		sql = "Delete ContaBancaria where num_conta = ?";
		ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNum_conta());
		ps.execute();

		ps.close();
		c.close();

	}

	@Override
	public ContaPoupanca buscar(ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM ContaBancaria cb, ContaPoupanca cp WHERE cb.num_conta = cp.num_conta and cb.num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNum_conta());
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			cp.setNum_conta(rs.getInt("num_conta"));
			cp.setNome_cliente(rs.getString("nome_cliente"));
			cp.setSaldo(rs.getFloat("saldo"));
			cp.setRendimento(rs.getInt("dia_rendimento"));
		}
		rs.close();
		ps.close();
		c.close();

		return cp;
	}

	@Override
	public List<ContaPoupanca> listar() throws SQLException, ClassNotFoundException {

		List<ContaPoupanca> contas = new ArrayList<>();

		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM ContaBancaria cb, ContaPoupanca cp WHERE cb.num_conta = cp.num_conta";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ContaPoupanca cp = new ContaPoupanca();
			cp.setNum_conta(rs.getInt("num_conta"));
			cp.setNome_cliente(rs.getString("nome_cliente"));
			cp.setSaldo(rs.getFloat("saldo"));
			cp.setRendimento(rs.getInt("dia_rendimento"));
			contas.add(cp);
		}
		rs.close();
		ps.close();
		c.close();

		return contas;
	}

	@Override
	public void sacar(ContaPoupanca cp, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "Update  ContaBancaria set saldo = saldo - ? where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, cp.getNum_conta());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void depositar(ContaPoupanca cp, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();

		String sql = "Update ContaBancaria set saldo = saldo + ? where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, cp.getNum_conta());
		ps.execute();
		ps.close();
		c.close();
	}

	public void calcularNovoSaldo(ContaPoupanca cp, float taxa) throws ClassNotFoundException, SQLException {
		Connection c = gDao.getConnection();

		String sql = "Update ContaBancaria set saldo = saldo + (saldo*?) where num_conta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, taxa);
		ps.setInt(2, cp.getNum_conta());
		ps.execute();
		ps.close();
		c.close();
	}

}
