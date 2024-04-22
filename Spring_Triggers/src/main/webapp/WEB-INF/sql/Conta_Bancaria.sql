create database Conta_Bancaria
go
use Conta_Bancaria
go
create table ContaBancaria(
nome_cliente varchar(100) not null,
num_conta int not null primary key,
saldo decimal(7,2) not null
)
go
create table ContaPoupanca(
num_conta int primary key references ContaBancaria(num_conta),
dia_rendimento int not null
)
go
create table ContaEspecial(
num_conta int primary key references ContaBancaria(num_conta),
limite decimal(7,2) not null
)
Go
Create trigger t_controleSaldo on ContaBancaria
after update
as
begin
	declare @num_conta int,
			@limite decimal(7,2),
			@saque decimal(7,2),
			@novo_saldo decimal(7,2)

	set @num_conta = (select num_conta from inserted)
	set @saque = (select saldo from deleted)-(select saldo from inserted)

	if((Select num_conta from ContaEspecial where num_conta = @num_conta) is not null) begin
		set @limite = (select limite from ContaEspecial where num_conta = @num_conta)
		
		if(@saque > @limite) begin
			RollBack Transaction
			RaisError('Esse valor ultrapassa seu limite',16,1)
		end
	end
	else begin
		if((select saldo from inserted)<0) begin
			RollBack Transaction
			RaisError('Com esse valor de saque, você ficaria com saldo negativo, operação cancelada',16,1)
		end
	end
end
SELECT * FROM ContaBancaria cb, ContaPoupanca cp