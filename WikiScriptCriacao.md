# Sistema de Monitoramento de Ações #

## Script de Criação do Banco de Dados para a Aplicação ##

```

CREATE TABLE COTACAO
(
  acao character varying(10),
  data date,
  preco numeric(20,3),
  volume numeric(20,3),
  negocio numeric(20,0),
  variacao numeric(20,3),
  abertura numeric(20,3),
  minimo numeric(20,3),
  maximo numeric(20,3),
  fechamento numeric(20,3)  
);
ALTER TABLE COTACAO ADD CONSTRAINT COTACAO_PK PRIMARY KEY (acao,data); 


CREATE TABLE PLANILHA
(
  acao character varying(10),
  posicao character varying(10),
  fechamento numeric(20,3),
  suporte_1 numeric(20,3),
  suporte_2 numeric(20,3),
  suporte_3 numeric(20,3),
  resistencia_1 numeric(20,3),
  resistencia_2 numeric(20,3),
  resistencia_3 numeric(20,3),
  data date  
);

CREATE FUNCTION max_periodo(VARCHAR,INTEGER,VARCHAR)
RETURNS numeric 
AS '
select max(a.maximo) from 
        (select c1.maximo as maximo
         from cotacao c1
         where c1.acao= $1      
         and c1.data <= to_date($3,''dd/MM/yyyy'') 
         order by c1.data desc
         limit $2) a  '
LANGUAGE SQL
RETURNS NULL ON NULL INPUT; 

CREATE FUNCTION min_periodo(VARCHAR,INTEGER,VARCHAR)
RETURNS numeric 
AS '
select min(a.minimo) from 
        (select c1.minimo as minimo
         from cotacao c1
         where c1.acao= $1
          and c1.data <= to_date($3,''dd/MM/yyyy'')     
         order by c1.data desc
         limit $2) a  '
LANGUAGE SQL
RETURNS NULL ON NULL INPUT; 

CREATE FUNCTION media_periodo(VARCHAR,INTEGER,VARCHAR)
RETURNS numeric 
AS '
select round(avg(a.preco),2) from 
        (select c1.preco as preco
         from cotacao c1
         where c1.acao= $1
         and c1.data <= to_date($3,''dd/MM/yyyy'')              
         order by c1.data desc
         limit $2) a '
LANGUAGE SQL
RETURNS NULL ON NULL INPUT; 

CREATE FUNCTION ifr(VARCHAR,INTEGER,VARCHAR)
RETURNS numeric 
AS '
select round(100.0-(100.0/(1.0+sum(alta)/sum(baixa))),2) 
from (select case when c1.variacao>0 then c1.preco-c2.preco end as alta,
             case when c1.variacao<0 then c2.preco-c1.preco end as baixa
      from cotacao c1, cotacao c2 -- c2 = dia anterior
      where c1.acao= $1
        and c1.data <= to_date($3,''dd/MM/yyyy'') 
        and c1.acao=c2.acao
        and c2.data=(select max(data) from cotacao where data<c1.data)
      order by c1.data desc
      limit $2
      ) a '
LANGUAGE SQL
RETURNS NULL ON NULL INPUT;  

CREATE FUNCTION stochastic(VARCHAR,INTEGER,INTEGER,VARCHAR)
RETURNS numeric 
AS '
select round(avg(a.k),2) 
from (
select 100.0*(c1.preco - min_periodo(c1.acao,$2,to_char(c1.data,''dd/MM/yyyy'')) )/ 
             (max_periodo(c1.acao,$2,to_char(c1.data,''dd/MM/yyyy'')) - min_periodo(c1.acao,$2,to_char(c1.data,''dd/MM/yyyy''))) as k
      from cotacao c1, cotacao c2 -- c2 = dia anterior
      where c1.acao= $1
        and c1.data <= to_date($4,''dd/MM/yyyy'') 
        and c1.acao=c2.acao
        and c2.data=(select max(data) from cotacao where data<c1.data)
      order by c1.data desc
      limit $3) a '
LANGUAGE SQL
RETURNS NULL ON NULL INPUT; 



CREATE FUNCTION topo_ascendente(VARCHAR,VARCHAR)
RETURNS numeric 
AS '
   select trunc(100.0*(c1.preco-c2.maximo)/c2.maximo,2)
   from cotacao c1, cotacao c2 -- c2 = dia anterior
   where c1.acao= $1
   and c1.data <= to_date($2,''dd/MM/yyyy'') 
   and c1.acao=c2.acao
   and c2.data=(select max(data) from cotacao where data<c1.data)
   order by c1.data desc
   limit 1 '
LANGUAGE SQL
RETURNS NULL ON NULL INPUT;

CREATE FUNCTION fundo_descendente(VARCHAR,VARCHAR)
RETURNS numeric 
AS '
   select trunc(100.0*(c1.preco-c2.minimo)/c2.minimo,2)
   from cotacao c1, cotacao c2 -- c2 = dia anterior
   where c1.acao= $1
   and c1.data <= to_date($2,''dd/MM/yyyy'') 
   and c1.acao=c2.acao
   and c2.data=(select max(data) from cotacao where data<c1.data)
   order by c1.data desc
   limit 1 '
LANGUAGE SQL
RETURNS NULL ON NULL INPUT;


ALTER TABLE PLANILHA ADD CONSTRAINT PLANILHA_PK PRIMARY KEY (acao,data); 


```

[Voltar](WikiConfigurarBancoDados.md)