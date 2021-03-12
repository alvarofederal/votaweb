# ************************************************************
# Base de Dados: heroku_96f23168d824f01
# Tempo de Geração: 2021-03-06
# ************************************************************

# ************************************************************
# Base de Dados: heroku_96f23168d824f01
# Tempo de Geração: 2021-03-06
# ************************************************************

# ************************************************************
# *************************DROPS******************************
# ************************************************************
drop table `heroku_96f23168d824f01`.`votacao`;
drop table `heroku_96f23168d824f01`.`sessao`;
drop table `heroku_96f23168d824f01`.`pauta`;
drop table `heroku_96f23168d824f01`.`associado`;

# ************************************************************
# *************************PAUTA******************************
# ************************************************************
CREATE TABLE `heroku_96f23168d824f01`.`pauta`
(
    `id`         INT NOT NULL,
    `nome_pauta` VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `heroku_96f23168d824f01`.`pauta`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('1', 'Lava Jato');
INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('2', 'Operação Zelotes');
INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('3', 'Operação Acrônimo');
INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('4', 'Operação Castelo de Areia');
INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('5', 'Operação Furacão (Hurricane)');
INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('6', 'Operação Shogun');
INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('7', 'Operação Cevada');
INSERT INTO `heroku_96f23168d824f01`.`pauta` (`id`, `nome_pauta`) VALUES ('8', 'Operação Chacal');

# ************************************************************
# *************************ASSOCIADO**************************
# ************************************************************
CREATE TABLE `heroku_96f23168d824f01`.`associado`
(
    `id`             int(11) NOT NULL,
    `nome_associado` varchar(255) NOT NULL,
	`cpf` varchar(20) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `heroku_96f23168d824f01`.`associado`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('1', 'Luciano Huck', '12345678912');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('2', 'Fausto Silva', '52994814030');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('3', 'Xuxa', '12345678912');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('4', 'Lula', '77535051065');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('5', 'Bolsonaro', '12345678912');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('6', 'FHC', '12661323090');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('7', 'Collor de Mello', '06207177002');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('8', 'Sarney', '45168803008');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('9', 'Temer', '61115038044');
INSERT INTO `heroku_96f23168d824f01`.`associado` (`id`, `nome_associado`, `cpf`) VALUES ('10', 'Dilminha', '12345678912');

# ************************************************************
# *************************SESSAO*****************************
# ************************************************************
CREATE TABLE `heroku_96f23168d824f01`.`sessao`
(
    `id`             int(11) NOT NULL,
    `inicio_sessao` datetime NOT NULL,
    `termino_sessao` datetime NOT NULL,
    `mensagem_termino` varchar(8) DEFAULT 'true' COMMENT 'true, false',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `heroku_96f23168d824f01`.`sessao`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

# ************************************************************
# *************************VOTACAO****************************
# ************************************************************
CREATE TABLE `heroku_96f23168d824f01`.`votacao`
(
    `id`           int(11) NOT NULL ,
    `id_associado` int(11) DEFAULT NULL,
    `id_pauta`     int(11) DEFAULT NULL,
    `id_sessao`    int(11) DEFAULT NULL,
    `voto`         int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY            `associado` (`id_associado`),
    KEY            `pauta` (`id_pauta`),
    KEY            `sessao` (`id_sessao`),
    CONSTRAINT `id_associado_votacao` FOREIGN KEY (`id_associado`) REFERENCES `associado` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `id_associado_pauta` FOREIGN KEY (`id_pauta`) REFERENCES `pauta` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `id_associado_sessao` FOREIGN KEY (`id_sessao`) REFERENCES `sessao` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `heroku_96f23168d824f01`.`votacao`
    CHANGE COLUMN `id` `id` INT (11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

