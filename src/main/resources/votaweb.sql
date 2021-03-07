# ************************************************************
# Base de Dados: votaweb_db
# Tempo de Geração: 2021-03-06
# ************************************************************


# ************************************************************
# *************************DROPS******************************
# ************************************************************
drop table `votaweb_db`.`votacao`;
drop table `votaweb_db`.`sessao`;
drop table `votaweb_db`.`pauta`;
drop table `votaweb_db`.`associado`;

# ************************************************************
# *************************PAUTA******************************
# ************************************************************
CREATE TABLE `votaweb_db`.`pauta`
(
    `id`         INT NOT NULL,
    `nome_pauta` VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `votaweb_db`.`pauta`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('1', 'Lava Jato');
INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('2', 'Operação Zelotes');
INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('3', 'Operação Acrônimo');
INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('4', 'Operação Castelo de Areia');
INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('5', 'Operação Furacão (Hurricane)');
INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('6', 'Operação Shogun');
INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('7', 'Operação Cevada');
INSERT INTO `votaweb_db`.`pauta` (`id`, `nome_pauta`) VALUES ('8', 'Operação Chacal');

# ************************************************************
# *************************ASSOCIADO**************************
# ************************************************************
CREATE TABLE `votaweb_db`.`associado`
(
    `id`             int(11) NOT NULL,
    `nome_associado` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `votaweb_db`.`associado`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('1', 'Luciano Huck');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('2', 'Fausto Silva');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('3', 'Xuxa');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('4', 'Lula');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('5', 'Bolsonaro');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('6', 'FHC');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('7', 'Collor de Mello');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('8', 'Sarney');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('9', 'Temer');
INSERT INTO `votaweb_db`.`associado` (`id`, `nome_associado`) VALUES ('10', 'Dilminha');

# ************************************************************
# *************************VOTACAO****************************
# ************************************************************
CREATE TABLE `votaweb_db`.`votacao`
(
    `id`           int(11)  NOT NULL ,
    `id_associado` int(11)  DEFAULT NULL,
    `id_pauta`     int(11)  DEFAULT NULL,
    `voto`         varchar(8) DEFAULT 'sim' COMMENT 'sim, nao',
    PRIMARY KEY (`id`),
    KEY            `associado` (`id_associado`),
    KEY            `pauta` (`id_pauta`),
    CONSTRAINT `id_associado_votacao` FOREIGN KEY (`id_associado`) REFERENCES `associado` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `id_associado_pauta` FOREIGN KEY (`id_pauta`) REFERENCES `pauta` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `votaweb_db`.`votacao`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('1', '1', '1', 'sim');
INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('2', '1', '2', 'nao');
INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('3', '2', '1', 'nao');
INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('4', '2', '2', 'sim');
INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('5', '3', '1', 'sim');
INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('6', '3', '2', 'sim');
INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('7', '4', '1', 'nao');
INSERT INTO `votaweb_db`.`votacao` (`id`, `id_associado`, `id_pauta`, `voto`) VALUES ('8', '4', '2', 'nao');

# ************************************************************
# *************************SESSAO*****************************
# ************************************************************
CREATE TABLE `votaweb_db`.`sessao`
(
    `id`             int(11) NOT NULL,
    `id_pauta`     int(11)  DEFAULT NULL,
    `id_associado` int(11)  DEFAULT NULL,
    `inicio_sessao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `termino_sessao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `mensagem_termino` varchar(8) DEFAULT 'true' COMMENT 'true, false',
    PRIMARY KEY (`id`),
    KEY            `associado` (`id_associado`),
    KEY            `pauta` (`id_pauta`),
    CONSTRAINT `id_sessao_votacao` FOREIGN KEY (`id_associado`) REFERENCES `associado` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `id_sessao_pauta` FOREIGN KEY (`id_pauta`) REFERENCES `pauta` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `votaweb_db`.`sessao`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);
