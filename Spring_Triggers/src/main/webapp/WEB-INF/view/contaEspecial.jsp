<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href='<c:url value = "./resources/style1.css"/>'>
<title>Conta Especial</title>
</head>
<body>
	<nav id = menu>
	<ul>
		<li><a href="index">Home</a>
		<li><a href="contaPoupanca">Conta Poupança</a>
		<li><a href="contaEspecial">Conta Especial</a>
	</ul>
	</nav>
	
	<div align="center" class="container">
		<form action="contaEspecial" method="post">
			<p class="title">
				<b>Crud - Conta Especial</b>
			</p>
			<table>
				<tr>
					<td colspan="3">
						<input class="id_input_data" type="text"  id="num_conta" name="num_conta" placeholder="Numero da Conta"
						pattern="[0-9]*" title="Por favor, digite apenas números."  
						value='<c:out value="${contaEspecial.num_conta }"></c:out>'>
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text"  id="nome_cliente" name="nome_cliente" placeholder="Nome do Cliente"
						value='<c:out value="${contaEspecial.nome_cliente }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text"  id="limite" name="limite" placeholder="Limite"
						pattern="[0-9,.]*" title="Por favor, digite apenas números."  
						value='<c:out value="${contaEspecial.limite }"></c:out>'>
					</td>
				</tr>	
					
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Cadastrar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Atualizar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Excluir">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Listar">
					</td>	
				</tr>
			</table>
		</form>
	</div>
	<br/>
	<br/>
	<br/>
	
	<div align="center" class="container">
		<form action="contaEspecial" method="post">
			<p class="title">
				<b>Sacar e Depositar</b>
			</p>
			<table>
				<tr>
					<td colspan="2">
						<input class="id_input_data" type="text"  id="num_conta" name="num_conta" placeholder="Numero da Conta"
						pattern="[0-9]*" title="Por favor, digite apenas números."  
						value='<c:out value="${contaEspecial.num_conta }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input class="input_data" type="text"  id="valor" name="valor" placeholder="Valor do Deposito/Saque"
						pattern="[0-9,.]*" title="Por favor, digite apenas números."  >
					</td>
				</tr>	
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Sacar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Depositar">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div align="center">
		<c:if test="${not empty erro }">
			<h2><b> <c:out value="${erro }" /> </b></h2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida }">
			<h3><b> <c:out value="${saida }" /> </b></h3>	
		</c:if>
	</div>
	<br />
	<br />
	<br />
	<div align="center" >
		<c:if test="${not empty contas }">
			<table class="table_round">
				<thead>
					<tr>
						<th class="lista">Numero Conta</th>
						<th class="lista">Nome Cliente</th>
						<th class="lista">Saldo</th>
						<th class="lista_ultimoelemento">Limite</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${contas }">
						<tr>
							<td class="lista"><c:out value="${c.num_conta } " /></td>
							<td class="lista"><c:out value="${c.nome_cliente } " /></td>
							<td class="lista"><c:out value="${c.saldo } " /></td>
							<td class="lista_ultimoelemento"><c:out value="${c.limite } " /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>	

</body>
</html>