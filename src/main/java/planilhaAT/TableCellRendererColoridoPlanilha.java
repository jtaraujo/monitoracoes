 package planilhaAT;
 
 import java.awt.Font;
 import javax.swing.table.DefaultTableCellRenderer;
 import util.tabelas.DadosIcones;
 
 public class TableCellRendererColoridoPlanilha extends DefaultTableCellRenderer
 {
   public TableCellRendererColoridoPlanilha() {}
   
   public void setValue(Object value)
   {
     if ((value instanceof DadosVariacao)) {
       DadosVariacao cvalue = (DadosVariacao)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof DadosStopLoss)) {
       DadosStopLoss cvalue = (DadosStopLoss)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof DadosStopGain)) {
       DadosStopGain cvalue = (DadosStopGain)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof DadosIcones)) {
       DadosIcones ivalue = (DadosIcones)value;
       setIcon(ivalue.m_icon);
       setText(ivalue.m_data.toString());
       setFont(new Font("TimeRoman", 1, 12));
     }
     else if ((value instanceof DadosIFR)) {
       DadosIFR cvalue = (DadosIFR)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof DadosStochastic)) {
       DadosStochastic cvalue = (DadosStochastic)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof DadosTopoAscendente)) {
       DadosTopoAscendente cvalue = (DadosTopoAscendente)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof DadosFundoDescendente)) {
       DadosFundoDescendente cvalue = (DadosFundoDescendente)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof String)) {
       setFont(new Font("TimeRoman", 1, 12));
       super.setValue((String)value);
     }
     else {
       super.setValue(value);
     }
   }
 }

