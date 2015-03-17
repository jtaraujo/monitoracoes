 package monitoramento;
 
 import java.awt.Font;
 import javax.swing.table.DefaultTableCellRenderer;
 import util.tabelas.DadosIcones;
 
 public class TableCellRendererColoridoMonitoramento extends DefaultTableCellRenderer
 {
   public TableCellRendererColoridoMonitoramento() {}
   
   public void setValue(Object value)
   {
     if ((value instanceof DadosVariacao)) {
       DadosVariacao cvalue = (DadosVariacao)value;
       setForeground(cvalue.m_color);
       setFont(new Font("TimeRoman", 1, 12));
       setText(cvalue.m_data.toString());
     }
     else if ((value instanceof DadosNegocio)) {
       DadosNegocio cvalue = (DadosNegocio)value;
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
     else {
       super.setValue(value);
     }
   }
 }
