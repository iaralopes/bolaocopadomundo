--TRIGGER para cadastrar apostas após inserir um membro a um bolão

DELIMITER //

CREATE TRIGGER inserir_apostas_membro
AFTER INSERT
   ON tbl_membros_bolao FOR EACH ROW
BEGIN

	INSERT INTO tbl_apostas (id, id_jogo, id_membro, gols_casa, gols_visitante)
	SELECT null, id, (SELECT MAX(id_membro) FROM tbl_membros_bolao), null, null FROM tbl_jogos;
END; //

DELIMITER ;

--TRIGGER para excluir apostas após deletar um membro;

DELIMITER //

CREATE TRIGGER deletar_apostas_membro
BEFORE DELETE
   ON tbl_membros_bolao FOR EACH ROW
BEGIN
	DELETE FROM tbl_apostas WHERE id_membro = OLD.id_membro;
END; //

DELIMITER ;

--TRIGGER para cadastrar apostas após inserir um jogo

DELIMITER //

CREATE TRIGGER inserir_apostas_jogo
AFTER INSERT
   ON tbl_jogos FOR EACH ROW
BEGIN

	INSERT INTO tbl_apostas (id, id_jogo, id_membro, gols_casa, gols_visitante)
	SELECT null, (SELECT MAX(id) FROM tbl_jogos), id_membro, null, null FROM tbl_membros_bolao;
END; //

DELIMITER ;

--TRIGGER para excluir apostas após deletar um jogo;

DELIMITER //

CREATE TRIGGER deletar_apostas_jogo
AFTER DELETE
   ON tbl_jogos FOR EACH ROW
BEGIN

	DELETE FROM tbl_apostas WHERE id_jogo = OLD.id_jogo;
END; //

DELIMITER ;



