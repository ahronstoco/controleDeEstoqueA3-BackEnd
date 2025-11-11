create database controleestoquea3; 
use controleestoquea3;

create table if not exists categoria(
idCategoria int not null auto_increment primary key,
nome varchar(100) not null,
tamanho ENUM('Pequeno', 'Medio', 'Grande') NOT NULL,
embalagem varchar(50) not null
);

create table if not exists produto(
idProduto int not null auto_increment primary key,
nome varchar(200) not null,
precoUnitario decimal(10,2) not null,
unidade varchar(20) not null,
quantidadeEstoque int not null,
quantidadeMinima int not null,
quantidadeMaxima int not null,
idCategoria INT NOT NULL,
FOREIGN KEY (idCategoria) REFERENCES categoria(idCategoria)
);

CREATE TABLE IF NOT EXISTS movimentacao (
    idMovimentacao INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idProduto INT NOT NULL,
    tipo ENUM('entrada', 'saida') NOT NULL,
    quantidade INT NOT NULL,
    data DATE NOT NULL,
    observacao VARCHAR(255),
    FOREIGN KEY (idProduto) REFERENCES produto(idProduto)
);

select * from controleestoquea3.produto;
select * from controleestoquea3.categoria;
select * from controleestoquea3.movimentacao;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE produto;
TRUNCATE TABLE categoria;
TRUNCATE TABLE movimentacao;
SET FOREIGN_KEY_CHECKS = 1;