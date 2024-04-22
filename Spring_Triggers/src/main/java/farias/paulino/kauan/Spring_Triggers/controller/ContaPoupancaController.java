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

import farias.paulino.kauan.Spring_Triggers.model.ContaPoupanca;
import farias.paulino.kauan.Spring_Triggers.persistence.ContaPoupancaDao;

@Controller
public class ContaPoupancaController {
	
	@Autowired
	private ContaPoupancaDao pDao;
	
	@RequestMapping(name = "contapoupanca", value = "/contapoupanca", method = RequestMethod.GET)
	public ModelAndView contaPoupancaGet(ModelMap model) {
		return new ModelAndView("contaPoupanca");
	}
	
	@RequestMapping(name = "contapoupanca", value = "/contapoupanca", method = RequestMethod.POST)
	public ModelAndView contaPoupancaPost(@RequestParam Map<String, String> param, ModelMap model) {
		
		String cmd = param.get("botao");
		String num_conta = param.get("num_conta");
		String nome_cliente = param.get("nome_cliente");
		String valor = param.get("valor");
		String dia_rendimento = param.get("dia_rendimento");
		
		//Retorno
		List<ContaPoupanca> contas = new ArrayList<>() ;
		ContaPoupanca conta = new ContaPoupanca();
		String erro = "";
		String saida = "";
		
		if(!cmd.contains("Listar") &&  !num_conta.trim().isEmpty()) {
			conta.setNum_conta(Integer.parseInt(num_conta));
		}
		if (cmd.equalsIgnoreCase("Cadastrar") || cmd.equalsIgnoreCase("Atualizar") && !nome_cliente.trim().isEmpty() && !dia_rendimento.trim().isEmpty() ) {
			conta.setNome_cliente(nome_cliente);
			conta.setRendimento(Integer.parseInt(dia_rendimento));
		}
		try {
			if (cmd.equalsIgnoreCase("Cadastrar")) {
				pDao.inserir(conta);
				saida = "conta inserido com sucesso";
				conta = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				pDao.atualizar(conta);
				saida = "Conta atualizado com sucesso";
				conta = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("Excluir")) {
				pDao.excluir(conta);
				saida = "Conta excluido com sucesso";
				conta = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("BUSCAR")) {
				conta = pDao.buscar(conta);
			}
			if (cmd.equalsIgnoreCase("LISTAR")) {
				contas = pDao.listar();
				conta = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("Sacar") && !valor.trim().isEmpty()) {
				pDao.sacar(conta, Float.parseFloat(valor));
				conta = pDao.buscar(conta);
				saida = "O saldo após o saque é: R$" + conta.getSaldo() ;
				conta = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("Depositar") && !valor.trim().isEmpty()) {
				pDao.depositar(conta, Float.parseFloat(valor));
				conta = pDao.buscar(conta);
				saida = "O saldo após o deposito é: R$" + conta.getSaldo() ;
				conta = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("Calcular Rendimento")) {
				pDao.calcularNovoSaldo(conta, (float) 0.2);
				conta = pDao.buscar(conta);
				saida = "O saldo após o rendimento é: R$" + conta.getSaldo() ;
				conta = new ContaPoupanca();
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("contaPoupanca", conta);
			model.addAttribute("contas", contas);
		}

		
		return new ModelAndView("contaPoupanca");
	}
}
