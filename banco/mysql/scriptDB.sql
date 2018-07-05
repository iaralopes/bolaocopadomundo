CREATE DATABASE bolao_copa_mundo;
USE bolao_copa_mundo;

CREATE TABLE tbl_selecoes(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
nome VARCHAR(100) NOT NULL,
grupo CHAR(1) NOT NULL,
eliminado INT NOT NULL,
PRIMARY KEY(id),
UNIQUE INDEX id_UNIQUE(id ASC));

CREATE TABLE tbl_estadios(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
nome VARCHAR(150) NOT NULL,
local VARCHAR(150) NULL,
PRIMARY KEY(id),
UNIQUE INDEX id_UNIQUE(id ASC));

CREATE TABLE tbl_jogos(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
horario DATETIME NOT NULL,
id_selecao_casa INT UNSIGNED NOT NULL,
gols_casa INT UNSIGNED NULL,
id_selecao_visitante INT UNSIGNED NOT NULL,
gols_visitante INT UNSIGNED NULL,
num_rodada INT UNSIGNED NOT NULL,
id_estadios INT UNSIGNED NOT NULL,
PRIMARY KEY(id),
UNIQUE INDEX id_UNIQUE(id ASC),
INDEX fk_tbl_jogos_tbl_selecoes_casa_id(id_selecao_casa ASC),
INDEX fk_tbl_jogos_tbl_selecoes_visitante_id(id_selecao_visitante ASC),
INDEX fk_tbl_jogos_tbl_estadios_id(id_estadios ASC),
CONSTRAINT fk_tbl_jogos_tbl_selecoes_casa FOREIGN KEY(id_selecao_casa) REFERENCES tbl_selecoes(id),
CONSTRAINT fk_tbl_jogos_tbl_selecoes_visitante FOREIGN KEY(id_selecao_visitante) REFERENCES tbl_selecoes(id),
CONSTRAINT fk_tbl_jogos_tbl_estadios FOREIGN KEY(id_estadios) REFERENCES tbl_estadios(id));

CREATE TABLE tbl_pessoas(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
nome VARCHAR(150) NOT NULL,
email VARCHAR(150) NOT NULL,
senha VARCHAR(150) NULL,
facebook INT NOT NULL,
PRIMARY KEY(id),
UNIQUE INDEX email_UNIQUE(email ASC));

CREATE TABLE tbl_bolao(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
nome VARCHAR(150) NOT NULL,
num_ganhadores INT UNSIGNED NOT NULL,
id_criador INT UNSIGNED NOT NULL,
PRIMARY KEY(id),
UNIQUE INDEX id_UNIQUE(id ASC),
CONSTRAINT fk_tbl_bolao_tbl_pessoas FOREIGN KEY(id_criador) REFERENCES tbl_pessoas(id));

CREATE TABLE tbl_membros_bolao(
id_membro INT UNSIGNED AUTO_INCREMENT NOT NULL,
id_bolao INT UNSIGNED NOT NULL,
id_pessoa INT UNSIGNED NOT NULL,
PRIMARY KEY(id_membro),
INDEX fk_tbl_pessoas1_id(id_pessoa ASC),
INDEX fk_tbl_bolao_id(id_bolao ASC),
UNIQUE INDEX id_membro_bolao_UNIQUE(id_membro ASC, id_bolao ASC, id_pessoa ASC),
CONSTRAINT fk_tbl_pessoas_tbl_bolao FOREIGN KEY(id_bolao) REFERENCES tbl_bolao(id),
CONSTRAINT fk_tbl_pessoas_tbl_pessoas FOREIGN KEY(id_pessoa) REFERENCES tbl_pessoas(id));

CREATE TABLE tbl_apostas(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
id_jogo INT UNSIGNED NOT NULL,
id_membro INT UNSIGNED NOT NULL,
gols_casa INT UNSIGNED NULL,
gols_visitante INT UNSIGNED NULL,
PRIMARY KEY(id, id_jogo, id_membro),
UNIQUE INDEX id_UNIQUE(id ASC),
UNIQUE INDEX jogo_membro_UNIQUE(id_jogo, id_membro),
INDEX fk_tbl_jogos_id(id_jogo ASC),
INDEX fk_tbl_membros_bolao_id(id_membro ASC),
CONSTRAINT fk_tbl_apostas_tbl_jogos FOREIGN KEY(id_jogo) REFERENCES tbl_jogos(id),
CONSTRAINT fk_tbl_apostas_tbl_membros_bolao FOREIGN KEY(id_membro) REFERENCES tbl_membros_bolao(id_membro));

CREATE TABLE tbl_configuracao(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
descricao VARCHAR(150) NOT NULL,
valor INT NOT NULL,
PRIMARY KEY(id),
UNIQUE INDEX id_UNIQUE(id ASC));

CREATE TABLE tbl_administradores(
id INT UNSIGNED AUTO_INCREMENT NOT NULL,
login VARCHAR(150) NOT NULL,
nome VARCHAR(150) NOT NULL,
senha VARCHAR(150) NOT NULL,
PRIMARY KEY(id),
UNIQUE INDEX id_UNIQUE(id ASC));




