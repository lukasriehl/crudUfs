CREATE SCHEMA avaliacao_celk;
    
USE avaliacao_celk;
    
CREATE TABLE unidade_federativa (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    sigla varchar(2) NOT NULL,
    descricao varchar(500) NOT NULL,
    data_criacao datetime NOT NULL,
    data_modificacao datetime NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
