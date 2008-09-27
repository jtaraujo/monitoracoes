-- Database: cotacoes

-- DROP DATABASE cotacoes;

CREATE DATABASE cotacoes
  WITH OWNER = postgres
       ENCODING = 'SQL_ASCII';

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

-- Parametros :
--              * data_atual
--      * media_menor 
--      * media_maior
--      * filtro_min_negocios
--      * variacao_gap_abertura
--      * multiplicador_volume

CREATE VIEW monitoramento AS
( 
select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,trunc(a.volume) as volume ,
      trunc(media.volume) as media_volume ,a.negocio ,trunc(media.negocio) as media_negocio, 'alerta_ontem' as alerta
from cotacao a,(select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio
                                from cotacao b
                                where  b.data > current_date - 3 and b.data < (current_date)
                                group by acao
                           )  media
where a.data = (current_date)
 and  a.acao = media.acao
 and  a.negocio > media.negocio*(select case to_char(current_timestamp, 'HH24')
                                                                                         when '09' then 1   when '10' then 1 when '11' then 2   
                                                                                         when '12' then 2   when '13' then 2      else 3
                                                                 END)-- varia conforme a hora do dia
 and  a.volume  > media.volume *(select case to_char(current_timestamp, 'HH24')
                                                                                         when '09' then 1   when '10' then 1 when '11' then 2   
                                                                                         when '12' then 2   when '13' then 2      else 3
                                END)-- varia conforme a hora do dia
 and a.negocio>10
 and a.variacao >=0
 and a.fechamento > a.abertura
union
select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,trunc(a.volume) as volume ,
       trunc(media.volume) as media_volume, a.negocio , trunc(media.negocio) as media_negocio, 'alerta_20dias' as alerta
from cotacao a, (select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio
                                 from cotacao b 
                                 where  b.data > current_date - 20 and b.data < (current_date)
                                 group by acao
                                )  media
where a.data = (current_date)
 and  a.acao = media.acao
 and  a.negocio > media.negocio*(select case to_char(current_timestamp, 'HH24')
                                                                 when '09' then 1   when '10' then 1 when '11' then 2   when '12' then 2
                                                                 when '13' then 2      else 3
                                                                END) -- varia conforme a hora do dia
 and  a.volume  > media.volume *(select case to_char(current_timestamp, 'HH24')
                                                                 when '09' then 1   when '10' then 1 when '11' then 2   when '12' then 2
                                                                 when '13' then 2      else 3
                                                                END) -- varia conforme a hora do dia
 and a.variacao >= 0
 and a.negocio > 10
 and a.preco > a.abertura
union --GAP de ABERTURA
select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,trunc(a.volume) as volume , 
       trunc(media.volume) as media_volume ,a.negocio , trunc(media.negocio) as media_negocio, 
       'GAP '||trunc(100*(a.abertura-media.preco)/media.preco,2)||' %' as alerta
from cotacao a, (select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio
                                 from cotacao b
                                 where  b.data = (select max(data) from cotacao where data < (current_date)   )
                                 group by acao
                                )  media
where a.data = (current_date)
 and  a.acao = media.acao
 and  a.abertura > (media.preco*1.04)
 and  to_number(to_char(current_timestamp,'HH24'),'99') <10
union --SUPER VOLUME
select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,trunc(a.volume) , 
       trunc(media.volume) as media_volume ,a.negocio , trunc(media.negocio) as media_negocio, 
       'VOLUME20dias '||trunc(100*(a.volume-media.volume)/media.volume,2)||' %' as alerta
from cotacao a, (select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio
                                 from cotacao b
                                 where  b.data > current_date - 20 and b.data < (current_date)
                                 group by acao
                            )  media
where a.data = (current_date)
 and  a.acao = media.acao
 and  a.volume > (media.volume*4)
 and  media.volume > 0
 and a.preco > media.preco
order by alerta,variacao desc
)
