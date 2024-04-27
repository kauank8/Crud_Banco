package farias.paulino.kauan.Spring_Triggers.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import farias.paulino.kauan.Spring_Triggers.model.ContaEspecial;
import farias.paulino.kauan.Spring_Triggers.model.ContaPoupanca;
import farias.paulino.kauan.Spring_Triggers.persistence.ContaEspecialDao;

@Controller
public class ContaEspecialController {
	@Autowired
	private ContaEspecialDao eDao;

	@RequestMapping(name = "contaEspecial", value = "/contaEspecial", method = RequestMethod.GET)
	public ModelAndView contaEspecialGet(ModelMap model) {
		return new ModelAndView("contaEspecial");
	}

	@RequestMapping(name = "contaEspecial", value = "/contaEspecial", method = RequestMethod.POST)
	public ModelAndView contaEspecialPost(@RequestParam Map<String, String> param, ModelMap model) {
		String cmd = param.get("botao");
		String num_conta = param.get("num_conta");
		String nome_cliente = param.get("nome_cliente");
		String valor = param.get("valor");
		String limite = param.get("limite");

		// Retorno
		List<ContaEspecial> contas = new ArrayList<>();
		ContaEspecial conta = new ContaEspecial();
		String erro = "";
		String saida = "";

		if (!cmd.contains("Listar") && !num_conta.trim().isEmpty()) {
			conta.setNum_conta(Integer.parseInt(num_conta));
		}
		if (cmd.equalsIgnoreCase("Cadastrar") || cmd.equalsIgnoreCase("Atualizar") && !nome_cliente.trim().isEmpty()
				&& !limite.trim().isEmpty()) {
			conta.setNome_cliente(nome_cliente);
			conta.setLimite(Float.parseFloat(limite));
		}
		try {
			if (cmd.equalsIgnoreCase("Cadastrar")) {
				eDao.inserir(conta);
				saida = "Conta inserido com sucesso";
				conta = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				eDao.atualizar(conta);
				saida = "Conta atualizado com sucesso";
				conta = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("Excluir")) {
				eDao.excluir(conta);
				saida = "Conta excluido com sucesso";
				conta = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("BUSCAR")) {
				conta = eDao.buscar(conta);
			}
			if (cmd.equalsIgnoreCase("LISTAR")) {
				contas = eDao.listar();
				conta = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("Sacar") && !valor.trim().isEmpty()) {
				eDao.sacar(conta, Float.parseFloat(valor));
				conta = eDao.buscar(conta);
				saida = "O saldo após o saque é: R$" + conta.getSaldo();
				conta = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("Depositar") && !valor.trim().isEmpty()) {
				Float valor1 = Float.parseFloat(valor);
				eDao.depositar(conta,valor1 );
				conta = eDao.buscar(conta);
				saida = "O saldo após o deposito é: R$" + conta.getSaldo();
				conta = new ContaEspecial();
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("contaEspecial", conta);
			model.addAttribute("contas", contas);
		}


		return new ModelAndView("contaEspecial");
	}

}
