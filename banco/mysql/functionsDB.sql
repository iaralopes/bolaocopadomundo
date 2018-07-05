--FUNCTION para trazer o horario que os palpites podem ser lançados

delimiter //

CREATE FUNCTION horarioPalpites () RETURNS VARCHAR(19) DETERMINISTIC
BEGIN
	RETURN DATE_ADD(NOW(), INTERVAL (SELECT valor FROM tbl_configuracao WHERE descricao = 'horas-palpites') HOUR);
END;//

delimiter ;