 package monitoramento;
 
 import util.tabelas.DadosIcones;
 
 public class ComparadorCotacoes implements java.util.Comparator
 {
   protected int m_sortCol;
   protected boolean m_sortAsc;
   
   public ComparadorCotacoes(int sortCol, boolean sortAsc) {
     this.m_sortCol = sortCol;
     this.m_sortAsc = sortAsc;
   }
   
   public int compare(Object o1, Object o2) {
     if ((!(o1 instanceof DadosMonitor)) || (!(o2 instanceof DadosMonitor)))
       return 0;
     DadosMonitor s1 = (DadosMonitor)o1;
     DadosMonitor s2 = (DadosMonitor)o2;
     int result = 0;
     
     switch (this.m_sortCol) {
     case 0: 
       String str1 = (String)s1.getIcone().m_data;
       String str2 = (String)s2.getIcone().m_data;
       result = str1.compareTo(str2);
       break;
     case 1: 
       double d1 = ((Double)s1.getVariacao().m_data).doubleValue();
       double d2 = ((Double)s2.getVariacao().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 2: 
       d1 = s1.getPreco().doubleValue();
       d2 = s2.getPreco().doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 3: 
       d1 = s1.getPreco_medio().doubleValue();
       d2 = s2.getPreco_medio().doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 4: 
       d1 = s1.getMinimo().doubleValue();
       d2 = s2.getMinimo().doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 5: 
       d1 = s1.getMaximo().doubleValue();
       d2 = s2.getMaximo().doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 6: 
       d1 = s1.getVolume().doubleValue();
       d2 = s2.getVolume().doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 7: 
       d1 = s1.getVolume_medio().doubleValue();
       d2 = s2.getVolume_medio().doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 8: 
       d1 = ((Double)s1.getNegocio().m_data).doubleValue();
       d2 = ((Double)s2.getNegocio().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 9: 
       d1 = s1.getNegocio_medio().doubleValue();
       d2 = s2.getNegocio_medio().doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 10: 
       result = s1.getMsg_alerta().compareTo(s2.getMsg_alerta());
     }
     
     
     if (!this.m_sortAsc)
       result = -result;
     return result;
   }
   
   public boolean equals(Object obj) {
     if ((obj instanceof ComparadorCotacoes)) {
       ComparadorCotacoes compObj = (ComparadorCotacoes)obj;
       
       return (compObj.m_sortCol == this.m_sortCol) && (compObj.m_sortAsc == this.m_sortAsc);
     }
     return false;
   }
 }

