 package planilhaAT;
 
  
 public class ComparadorPlanilha implements java.util.Comparator
 {
   protected int m_sortCol;
   protected boolean m_sortAsc;
   
   public ComparadorPlanilha(int sortCol, boolean sortAsc) {
     this.m_sortCol = sortCol;
     this.m_sortAsc = sortAsc;
   }
   
   public int compare(Object o1, Object o2) {
     if ((!(o1 instanceof DadosPlanilha)) || (!(o2 instanceof DadosPlanilha)))
       return 0;
     DadosPlanilha s1 = (DadosPlanilha)o1;
     DadosPlanilha s2 = (DadosPlanilha)o2;
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
       d1 = ((Double)s1.getIfr().m_data).doubleValue();
       d2 = ((Double)s2.getIfr().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 4: 
       d1 = ((Double)s1.getStochastic().m_data).doubleValue();
       d2 = ((Double)s2.getStochastic().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 5: 
       d1 = ((Double)s1.getTopoAscen().m_data).doubleValue();
       d2 = ((Double)s2.getTopoAscen().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 6: 
       d1 = ((Double)s1.getFundoDescen().m_data).doubleValue();
       d2 = ((Double)s2.getFundoDescen().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 7: 
       result = s1.getSuportes().compareTo(s2.getSuportes());
       break;
     case 8: 
       result = s1.getResistencias().compareTo(s2.getResistencias());
       break;
     case 9: 
       d1 = ((Double)s1.getStop_loss_1().m_data).doubleValue();
       d2 = ((Double)s2.getStop_loss_1().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 10: 
       d1 = ((Double)s1.getStop_gain_1().m_data).doubleValue();
       d2 = ((Double)s2.getStop_gain_1().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 11: 
       d1 = ((Double)s1.getStop_loss_2().m_data).doubleValue();
       d2 = ((Double)s2.getStop_loss_2().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 12: 
       d1 = ((Double)s1.getStop_gain_2().m_data).doubleValue();
       d2 = ((Double)s2.getStop_gain_2().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 13: 
       d1 = ((Double)s1.getStop_loss_3().m_data).doubleValue();
       d2 = ((Double)s2.getStop_loss_3().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
       break;
     case 14: 
       d1 = ((Double)s1.getStop_gain_3().m_data).doubleValue();
       d2 = ((Double)s2.getStop_gain_3().m_data).doubleValue();
       result = d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
     }
     
     
     if (!this.m_sortAsc)
       result = -result;
     return result;
   }
   
   public boolean equals(Object obj) {
     if ((obj instanceof ComparadorPlanilha)) {
       ComparadorPlanilha compObj = (ComparadorPlanilha)obj;
       
       return (compObj.m_sortCol == this.m_sortCol) && (compObj.m_sortAsc == this.m_sortAsc);
     }
     return false;
   }
 }

