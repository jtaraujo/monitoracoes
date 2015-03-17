 package util.dao;
 
 import alertaWeb.Alerta;
 import java.io.PrintStream;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.SimpleDateFormat;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.Set;
 import java.util.TreeMap;
 import java.util.Vector;
 import monitoramento.DadosMonitor;
 import planilhaAT.DadosPlanilha;
 import util.config.Constantes;
 import util.vo.Acao;
 import util.vo.ItemPlanilha;
 
 public class Base
 {
   static
   {
     try
     {
       Class.forName("org.postgresql.Driver");
     } catch (Exception e) {
       e.printStackTrace();
     } }
   
   public Base() {}
   
   private Connection getConn() { Connection con = null;
     try {
       con = java.sql.DriverManager.getConnection("jdbc:postgresql:cotacoes", 
         Constantes.getInstance().loginbd, 
         Constantes.getInstance().senhabd);
       
       con.setAutoCommit(true);
     }
     catch (SQLException e) {
       e.printStackTrace();
       System.out.println("tentando novamente");
       try { Thread.sleep(3000L); } catch (Exception localException) {}
       return getConn();
     }
     
     return con;
   }
   
   public void insereCotacao(TreeMap<String, Acao> acaoMap)
   {
     Connection con = getConn();
     PreparedStatement pstmI = null;
     PreparedStatement pstmU = null;
     PreparedStatement pstmS = null;
     Acao acao = null;
     try {
       pstmS = con.prepareStatement("select acao,data,preco,volume,negocio,variacao from cotacao where acao=? and data = ?");
       pstmI = con.prepareStatement("insert into cotacao (acao,data,preco,volume,negocio,variacao,abertura,minimo,maximo,fechamento) values (?,?,?,?,?,?,?,?,?,?)");
       pstmU = con.prepareStatement("update cotacao set preco=? ,volume=?,negocio=?,variacao=?,abertura=?,minimo=?,maximo=?,fechamento=?  where acao=? and data=?");
       for (Iterator iterator = acaoMap.keySet().iterator(); iterator.hasNext();) {
         String key = (String)iterator.next();
         acao = (Acao)acaoMap.get(key);
         if (acao.getPreco() != null) {
           pstmS.setObject(1, acao.getCode());
           pstmS.setObject(2, new java.sql.Date(acao.getDate().getTime()));
           ResultSet rs = pstmS.executeQuery();
           if (rs.next()) {
             if (!acao.getNegocios().equals(Long.valueOf(rs.getLong(5)))) {
               pstmU.setObject(1, acao.getPreco());
               pstmU.setObject(2, acao.getVolume());
               pstmU.setObject(3, acao.getNegocios());
               pstmU.setObject(4, acao.getVariacao());
               pstmU.setObject(5, acao.getAbertura());
               pstmU.setObject(6, acao.getMinimo());
               pstmU.setObject(7, acao.getMaximo());
               pstmU.setObject(8, acao.getFechamento());
               pstmU.setObject(9, acao.getCode());
               pstmU.setObject(10, new java.sql.Date(acao.getDate().getTime()));
               pstmU.execute();
             }
           } else {
             pstmI.setObject(1, acao.getCode());
             pstmI.setObject(2, new java.sql.Date(acao.getDate().getTime()));
             pstmI.setObject(3, acao.getPreco());
             pstmI.setObject(4, acao.getVolume());
             pstmI.setObject(5, acao.getNegocios());
             pstmI.setObject(6, acao.getVariacao());
             pstmI.setObject(7, acao.getAbertura());
             pstmI.setObject(8, acao.getMinimo());
             pstmI.setObject(9, acao.getMaximo());
             pstmI.setObject(10, acao.getFechamento());
             pstmI.execute();
           }
           rs.close();
         } }
       pstmS.close();
       pstmU.close();
       pstmI.close();
       con.close();
     } catch (Exception e) {
       System.out.println("Exceção : " + acao);
       e.printStackTrace();
     }
   }
   
 
   public void inserePlanilha(TreeMap<String, ItemPlanilha> acaoMap)
   {
     Connection con = getConn();
     PreparedStatement pstmI = null;
     PreparedStatement pstmU = null;
     PreparedStatement pstmS = null;
     ItemPlanilha item = null;
     try {
       pstmS = con.prepareStatement("select acao,posicao,fechamento,suporte_1,suporte_2,suporte_3,resistencia_1,resistencia_2,resistencia_3,data from planilha where acao=? ");
       pstmI = con.prepareStatement("insert into planilha (acao,posicao,fechamento,suporte_1,suporte_2,suporte_3,resistencia_1,resistencia_2,resistencia_3,data) values (?,?,?,?,?,?,?,?,?,?)");
       pstmU = con.prepareStatement("update planilha set posicao=? ,fechamento=?,suporte_1=?,suporte_2=?,suporte_3=?,resistencia_1=?,resistencia_2=?,resistencia_3=?,data=?   where acao=? ");
       for (Iterator iterator = acaoMap.keySet().iterator(); iterator.hasNext();) {
         String key = (String)iterator.next();
         item = (ItemPlanilha)acaoMap.get(key);
         pstmS.setObject(1, item.getAcao());
         ResultSet rs = pstmS.executeQuery();
         if (rs.next()) {
           if (!item.getDate().equals(rs.getDate(10))) {
             pstmU.setObject(1, item.getPosicao());
             pstmU.setObject(2, item.getFechamento());
             pstmU.setObject(3, item.getSuporte_1Formatado());
             pstmU.setObject(4, item.getSuporte_2Formatado());
             pstmU.setObject(5, item.getSuporte_3Formatado());
             pstmU.setObject(6, item.getResistencia_1Formatado());
             pstmU.setObject(7, item.getResistencia_2Formatado());
             pstmU.setObject(8, item.getResistencia_3Formatado());
             pstmU.setObject(9, new java.sql.Date(item.getDate().getTime()));
             pstmU.setObject(10, item.getAcao());
             pstmU.execute();
           }
         } else {
           pstmI.setObject(1, item.getAcao());
           pstmI.setObject(2, item.getPosicao());
           pstmI.setObject(3, item.getFechamento());
           pstmI.setObject(4, item.getSuporte_1Formatado());
           pstmI.setObject(5, item.getSuporte_2Formatado());
           pstmI.setObject(6, item.getSuporte_3Formatado());
           pstmI.setObject(7, item.getResistencia_1Formatado());
           pstmI.setObject(8, item.getResistencia_2Formatado());
           pstmI.setObject(9, item.getResistencia_3Formatado());
           pstmI.setObject(10, new java.sql.Date(item.getDate().getTime()));
           pstmI.execute();
         }
         rs.close();
       }
       pstmS.close();
       pstmU.close();
       pstmI.close();
       con.close();
     } catch (Exception e) {
       System.out.println("Exceção : " + item);
       e.printStackTrace();
     }
   }
   
 
   public void limparCotacoes()
   {
     Connection con = getConn();
     PreparedStatement pstm = null;
     try
     {
       String sql = "select count(*) from cotacao ";
       pstm = con.prepareStatement(sql);
       ResultSet rs = pstm.executeQuery();
       if ((rs.next()) && 
         (rs.getInt(1) > 0)) {
         rs.close();
         pstm.close();
         
         sql = "truncate table cotacao ";
         pstm = con.prepareStatement(sql);
         rs = pstm.executeQuery();
       }
       
 
       rs.close();
       pstm.close();
       con.close();
     }
     catch (Exception localException) {}
   }
   
 
 
   public LinkedList<Alerta> getAlerta()
   {
     LinkedList<Alerta> result = new LinkedList();
     Connection con = getConn();
     PreparedStatement pstm = null;
     try
     {
       String sql = "select\t ID,SQL,EMAIL,INTERVALO,INICIOHORA,FIMHORA,ULTIMA,TEMPOEXECUCAO from BUSCA where iniciohora>trunc(sysdate,'HH')  and fimhora<trunc(sysdate,'HH')   and nvl(ultima,sysdate+1) > sysdate+intervalo/(24*60)";
       
 
 
 
       pstm = con.prepareStatement(sql);
       
       ResultSet rs = pstm.executeQuery();
       while (rs.next()) {
         Alerta alerta = new Alerta();
         alerta.setId(rs.getInt(1));
         alerta.setEmail(rs.getString(3).split(";"));
         alerta.setSql(rs.getString(2));
         result.add(alerta);
       }
       rs.close();
       pstm.close();
       con.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
     return result;
   }
   
 
 
   public Vector<DadosMonitor> getMonitoramentoParametrizado()
   {
     Vector<DadosMonitor> result = new Vector();
     Connection con = getConn();
     PreparedStatement pstm = null;
     try {
       String dataAtual = new SimpleDateFormat("dd/MM/yyyy", 
         new java.util.Locale("pt", "br")).format(Constantes.getInstance().data_atual);
       String gapabertura = String.valueOf(Constantes.getInstance().variacao_gap_abertura);
       String multiplicador_volume = String.valueOf(Constantes.getInstance().multiplicador_volume);
       String sql = 
         " select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,a.minimo as minimo,a.maximo as maximo,trunc(a.volume) as volume ,  trunc(media.volume) as media_volume ,a.negocio ,trunc(media.negocio) as media_negocio, 'alerta " + 
         Constantes.getInstance().media_menor + " dias' as alerta " + 
         " from cotacao a,(select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio " + 
         " \t\t\t\t  from cotacao b " + 
         " \t\t\t\t  where  b.data > to_date('" + dataAtual + "','dd/MM/yyyy') - " + Constantes.getInstance().media_menor + " and b.data < to_date('" + dataAtual + "','dd/MM/yyyy') " + 
         " \t\t\t\t  group by acao   )  media " + 
         " where a.data = to_date('" + dataAtual + "','dd/MM/yyyy') " + 
         " and  a.acao = media.acao " + 
         " and  a.negocio > media.negocio*(select case to_char(current_timestamp, 'HH24') " + 
         "\t\t\t\t\t\t\t\t\t\t when '09' then 1    when '10' then 1    when '11' then 1.3 " + 
         " \t\t\t\t\t\t\t\t\t\t when '12' then 1.3  when '13' then 1.6   else 2   " + 
         " \t\t\t\t\t\t\t\t  END) " + 
         " and  a.volume  > media.volume *(select case to_char(current_timestamp, 'HH24') " + 
         "\t\t\t\t\t\t\t\t\t     when '09' then 1    when '10' then 1 when '11' then 1.3  " + 
         "\t\t\t\t\t\t\t\t\t\t when '12' then 1.3  when '13' then 1.6    else 2  " + 
         "\t\t\tEND)  " + 
         " and a.negocio> " + Constantes.getInstance().filtro_min_negocios + "  " + 
         " and a.variacao >=0 " + 
         " and a.fechamento > a.abertura " + 
         " union " + 
         " select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,a.minimo as minimo,a.maximo as maximo,trunc(a.volume) as volume , " + 
         "       trunc(media.volume) as media_volume, a.negocio , trunc(media.negocio) as media_negocio, 'alerta " + Constantes.getInstance().media_maior + " dias' as alerta " + 
         " from cotacao a, (select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio " + 
         "\t\t\t\t   from cotacao b " + 
         "\t\t \t\t   where  b.data > to_date('" + dataAtual + "','dd/MM/yyyy') - " + Constantes.getInstance().media_maior + " and b.data < to_date('" + dataAtual + "','dd/MM/yyyy') " + 
         "\t\t\t\t   group by acao " + 
         "\t\t\t\t  ) media " + 
         " where a.data = to_date('" + dataAtual + "','dd/MM/yyyy') " + 
         " and  a.acao = media.acao " + 
         " and  a.negocio > media.negocio*(select case to_char(current_timestamp, 'HH24') " + 
         "\t\t\t\t\t\t\t\t\t\t when '09' then 1    when '10' then 1    when '11' then 1.3 " + 
         " \t\t\t\t\t\t\t\t\t\t when '12' then 1.3  when '13' then 1.6   else 2   " + 
         " \t\t\t\t\t\t\t\t  END) " + 
         " and  a.volume  > media.volume *(select case to_char(current_timestamp, 'HH24') " + 
         "\t\t\t\t\t\t\t\t\t     when '09' then 1    when '10' then 1 when '11' then 1.3  " + 
         "\t\t\t\t\t\t\t\t\t\t when '12' then 1.3  when '13' then 1.6    else 2  " + 
         "\t\t\tEND)  " + 
         " and a.variacao >= 0 " + 
         " and a.negocio > " + Constantes.getInstance().filtro_min_negocios + "  " + 
         " and a.preco > a.abertura " + 
         " union " + 
         " select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,a.minimo as minimo,a.maximo as maximo,trunc(a.volume) as volume , " + 
         " \ttrunc(media.volume) as media_volume ,a.negocio , trunc(media.negocio) as media_negocio, " + 
         "   'GAP '||trunc(100*(a.abertura-media.preco)/media.preco,2)||' %' as alerta  " + 
         "\t from cotacao a, (select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio " + 
         "\t   \t\t\t      from cotacao b " + 
         "\t\t\t\t\t  where  b.data = (select max(data) from cotacao where data < to_date('" + dataAtual + "','dd/MM/yyyy')   )  " + 
         "\t\t\t \t\t  group by acao  " + 
         " \t\t\t\t\t)  media  " + 
         " where a.data = to_date('" + dataAtual + "','dd/MM/yyyy')  " + 
         " and  a.acao = media.acao  " + 
         " and  a.abertura > (media.preco*(1.00+(" + gapabertura + "/100)))   " + 
         " and  to_number(to_char(current_timestamp,'HH24'),'99') <10  " + 
         " union  " + 
         " select a.acao,trunc(a.variacao,2) as variacao,trunc(a.preco,2) as preco,round(media.preco,2) as preco_medio,a.minimo as minimo,a.maximo as maximo,trunc(a.volume) ,  " + 
         "   trunc(media.volume) as media_volume ,a.negocio , trunc(media.negocio) as media_negocio,  " + 
         "   'VOLUME " + Constantes.getInstance().media_maior + " dias '||trunc(100*(a.volume-media.volume)/media.volume,2)||' %' as alerta  " + 
         " from cotacao a,\t(select b.acao as acao, avg(b.preco) as  preco,  avg(b.volume) as volume,  avg(b.negocio) as negocio   " + 
         "\t\t\t\t\t from cotacao b  " + 
         "\t\t\t\t\t where  b.data > to_date('" + dataAtual + "','dd/MM/yyyy') - " + Constantes.getInstance().media_maior + " and b.data < to_date('" + dataAtual + "','dd/MM/yyyy')  " + 
         "\t\t\t\t     group by acao   " + 
         "\t\t\t\t\t)  media  " + 
         " where a.data = to_date('" + dataAtual + "','dd/MM/yyyy')  " + 
         " and  a.acao = media.acao  " + 
         " and  a.volume > (media.volume*" + multiplicador_volume + ")  " + 
         " and  media.volume > 0  " + 
         " and a.preco > media.preco   " + 
         " order by alerta,variacao desc  ";
       
       pstm = con.prepareStatement(sql);
       
       ResultSet rs = pstm.executeQuery();
       while (rs.next()) {
         String acao = rs.getString(1);
         double variacao = rs.getDouble(2);
         double preco = rs.getDouble(3);
         double preco_medio = rs.getDouble(4);
         double minimo = rs.getDouble(5);
         double maximo = rs.getDouble(6);
         double volume = rs.getDouble(7);
         double volume_medio = rs.getDouble(8);
         long negocio = rs.getLong(9);
         double negocio_medio = rs.getDouble(10);
         String msg_alerta = rs.getString(11);
         result.addElement(new DadosMonitor(acao, variacao, preco, 
           preco_medio, minimo, maximo, volume, volume_medio, negocio, negocio_medio, msg_alerta));
       }
       rs.close();
       pstm.close();
       con.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
     
     return result;
   }
   
 
   public Vector<DadosPlanilha> getPlanilhaParametrizado()
   {
     Vector<DadosPlanilha> result = new Vector();
     Connection con = getConn();
     PreparedStatement pstm = null;
     try {
       String dataAtual = new SimpleDateFormat("dd/MM/yyyy", 
         new java.util.Locale("pt", "br")).format(Constantes.getInstance().data_atual);
       
       String sql = 
         "\t\tselect c.acao,trunc(c.variacao,2) as variacao,trunc(c.preco,2) as preco,\t          ifr(c.acao,9,'" + 
         dataAtual + "') as ifr,   " + 
         "          stochastic(c.acao,10,3,'" + dataAtual + "') as stochastic,   " + 
         "          topo_ascendente(c.acao,'" + dataAtual + "') as topo_ascendente,   " + 
         "          fundo_descendente(c.acao,'" + dataAtual + "') as fundo_descendente,   " + 
         "\t\t   case p.suporte_1 \t" + 
         "\t\t         when 0.0 then null" + 
         "\t\t\t\t else case p.suporte_2" + 
         "\t      \t\t\t\twhen 0.0 then to_char(trunc(p.suporte_1,2),'999.99') " + 
         "\t      \t\t\t\telse case p.suporte_3 " + 
         "\t           \t\t\t\t \twhen 0.0 then to_char(trunc(p.suporte_1,2),'999.99')||' /'||to_char(trunc(p.suporte_2,2),'999.99') " + 
         "\t           \t\t\t\t \telse to_char(trunc(p.suporte_1,2),'999.99')||' /'||to_char(trunc(p.suporte_2,2),'999.99')||' /'||to_char(trunc(p.suporte_3,2),'999.99') " + 
         "\t           \t\t    \t end" + 
         "   \t   \t\t\t  end" + 
         "          end as suportes," + 
         "\t       case p.resistencia_1 " + 
         "\t\t         when 0.0 then null " + 
         "\t\t         else case p.resistencia_2 " + 
         "\t\t              when 0.0 then to_char(trunc(p.resistencia_1,2),'999.99') " + 
         "\t\t              else case p.resistencia_3 " + 
         "\t\t\t                   when 0.0 then to_char(trunc(p.resistencia_1,2),'999.99')||' /'||to_char(trunc(p.resistencia_2,2),'999.99') " + 
         "\t\t\t                   else to_char(trunc(p.resistencia_1,2),'999.99')||' /'||to_char(trunc(p.resistencia_2,2),'999.99')||' /'||to_char(trunc(p.resistencia_3,2),'999.99') " + 
         "\t\t                   end " + 
         "\t\t\t          end " + 
         "\t\t   end as resistencias,\t" + 
         "\t       trunc(100*(p.suporte_1-c.preco)/c.preco,2) as stop_loss1,\t" + 
         "\t       trunc(100*(p.resistencia_1-c.preco)/c.preco,2) as stop_gain1, " + 
         "\t       case p.suporte_2 " + 
         "\t              when 0.0 then null " + 
         "\t\t          else trunc(100*(p.suporte_2-c.preco)/c.preco,2) " + 
         "\t       end  as stop_loss2, " + 
         "\t       case p.resistencia_2 " + 
         "\t              when 0.0 then null " + 
         "\t              else trunc(100*(p.resistencia_2-c.preco)/c.preco,2) " + 
         "\t       end  as stop_gain2 , " + 
         "\t       case p.suporte_3 " + 
         "\t              when 0.0 then null " + 
         "\t              else trunc(100*(p.suporte_3-c.preco)/c.preco,2) " + 
         "\t       end  as stop_loss3, " + 
         "\t       case p.resistencia_3 " + 
         " \t              when 0.0 then null " + 
         " \t              else trunc(100*(p.resistencia_3-c.preco)/c.preco,2) " + 
         " \t       end  as stop_gain3 " + 
         "  from planilha p, cotacao c " + 
         "  where c.acao = p.acao " + 
         "  and c.data = to_date('" + dataAtual + "','dd/MM/yyyy') " + 
         "  and p.data = (select max(data) from planilha ) " + 
         "  order by stop_loss1 desc ";
       
       pstm = con.prepareStatement(sql);
       
       ResultSet rs = pstm.executeQuery();
       while (rs.next()) {
         String acao = rs.getString(1);
         double variacao = rs.getDouble(2);
         double preco = rs.getDouble(3);
         double ifr = rs.getDouble(4);
         double stochastic = rs.getDouble(5);
         double topo_ascendente = rs.getDouble(6);
         double fundo_descendente = rs.getDouble(7);
         String suportes = rs.getString(8);
         String resistencias = rs.getString(9);
         double stop_loss1 = rs.getDouble(10);
         double stop_gain1 = rs.getDouble(11);
         double stop_loss2 = rs.getDouble(12);
         double stop_gain2 = rs.getLong(13);
         double stop_loss3 = rs.getDouble(14);
         double stop_gain3 = rs.getDouble(15);
         result.addElement(new DadosPlanilha(acao, variacao, preco, ifr, stochastic, topo_ascendente, fundo_descendente, 
           suportes, resistencias, stop_loss1, stop_gain1, stop_loss2, stop_gain2, stop_loss3, stop_gain3));
       }
       rs.close();
       pstm.close();
       con.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
     
     return result;
   }
   
   public void AtualizarVariacoes()
   {
     Connection con = getConn();
     PreparedStatement pstm = null;
     ResultSet rs = null;
     try {
       String sql = "update cotacao a set variacao = (select 100*(a.preco-b.preco)/b.preco ";
       sql = sql + " \t\t\t\t\t\t\t\t from cotacao b ";
       sql = sql + "\t\t\t\t\t\t\t\t where a.acao=b.acao ";
       sql = sql + " \t\t\t\t\t\t\t\t and b.data = (select max(c.data)  ";
       sql = sql + "\t\t\t\t\t\t\t\t\t\t\t   from cotacao c      ";
       sql = sql + "\t\t\t\t\t\t\t\t\t\t\t   where c.data<a.data ";
       sql = sql + "\t\t\t\t\t\t\t\t\t\t\t   and c.acao=a.acao)) ";
       
       pstm = con.prepareStatement(sql);
       rs = pstm.executeQuery();
       
 
       rs.close();
       pstm.close();
       con.close();
     }
     catch (Exception localException) {}
   }
 }

