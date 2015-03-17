 package monitorWeb;
 
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Collections;
 import java.util.Date;
 import java.util.Locale;
 import java.util.Vector;
 import javax.swing.JTable;
 import javax.swing.event.ChangeEvent;
 import javax.swing.event.ListSelectionEvent;
 import javax.swing.event.TableColumnModelEvent;
 import javax.swing.event.TableColumnModelListener;
 import javax.swing.event.TableModelEvent;
 import javax.swing.table.AbstractTableModel;
 import javax.swing.table.TableColumn;
 import javax.swing.table.TableColumnModel;
 import monitoramento.ComparadorCotacoes;
 import monitoramento.DadosMonitor;
 import util.config.Constantes;
 import util.dao.Base;
 import util.tabelas.DadosColuna;
 
 public class DadosTabelaCotacoes
   extends AbstractTableModel
   implements Runnable
 {
   public static final DadosColuna[] m_columns = {
     new DadosColuna("Ação", 90, 2), 
     new DadosColuna("Variação %", 90, 4), 
     new DadosColuna("Preço", 90, 4), 
     new DadosColuna("Preço Médio", 100, 4), 
     new DadosColuna("Mínimo", 90, 4), 
     new DadosColuna("Máximo", 90, 4), 
     new DadosColuna("Volume", 100, 4), 
     new DadosColuna("Volume Médio", 100, 4), 
     new DadosColuna("Negócios", 90, 4), 
     new DadosColuna("Média Negócios", 110, 4), 
     new DadosColuna("Alerta", 200, 0) };
   
   protected SimpleDateFormat m_frm;
   
   protected Vector m_vector;
   protected Date m_date;
   protected int m_columnsCount = m_columns.length;
   
   private int delay;
   
   Thread runner;
   protected int m_sortCol = 0;
   protected boolean m_sortAsc = true;
   
   protected int m_result = 0;
   
   public void setDelay(float seconds) {
     this.delay = Math.round(seconds * 1000.0F);
   }
   
   public DadosTabelaCotacoes(float initialDelay) {
     this.m_frm = new SimpleDateFormat("dd/MM/yyyy");
     this.m_vector = new Vector();
     setDefaultData();
     setDelay(initialDelay);
     
     Thread runner = new Thread(this);
     runner.start();
   }
   
   public void setDefaultData() {
     try {
       String data = new SimpleDateFormat("dd/MM/yyyy", 
         new Locale("pt", "br")).format(Constantes.getInstance().data_atual);
       this.m_date = this.m_frm.parse(data);
     } catch (ParseException ex) {
       this.m_date = null;
     }
     
     this.m_vector.removeAllElements();
     AtualizarCotacoes();
   }
   
   public int getRowCount() {
     return this.m_vector == null ? 0 : this.m_vector.size();
   }
   
   public int getColumnCount() {
     return this.m_columnsCount;
   }
   
   public String getColumnName(int column) {
     String str = m_columns[column].m_title;
     if (column == this.m_sortCol)
       str = str + (this.m_sortAsc ? ">>" : "<<");
     return str;
   }
   
   public boolean isCellEditable(int nRow, int nCol) {
     return false;
   }
   
   public Object getValueAt(int nRow, int nCol) {
     if ((nRow < 0) || (nRow >= getRowCount()))
       return "";
     DadosMonitor row = (DadosMonitor)this.m_vector.elementAt(nRow);
     switch (nCol) {
     case 0:  return row.getIcone();
     case 1:  return row.getVariacao();
     case 2:  return row.getPreco();
     case 3:  return row.getPreco_medio();
     case 4:  return row.getMinimo();
     case 5:  return row.getMaximo();
     case 6:  return row.getVolume();
     case 7:  return row.getVolume_medio();
     case 8:  return row.getNegocio();
     case 9:  return row.getNegocio_medio();
     case 10:  return row.getMsg_alerta();
     }
     return "";
   }
   
   public String getTitle() {
     if (this.m_date == null)
       return "Cotações";
     return "Cotações em " + this.m_frm.format(this.m_date);
   }
   
   public void AtualizarCotacoes()
   {
     Base base = new Base();
     this.m_vector = base.getMonitoramentoParametrizado();
     
     Collections.sort(this.m_vector, new ComparadorCotacoes(this.m_sortCol, this.m_sortAsc));
     
     this.m_date = Constantes.getInstance().data_atual;
   }
   
 
   public void run()
   {
     for (;;)
     {
       AtualizarCotacoes();
       
       fireTableDataChanged();
       try
       {
         Thread.sleep(this.delay);
       }
       catch (InterruptedException localInterruptedException) {}
     }
   }
   
 
 
   public int retrieveData(Date date)
   {
     return this.m_result;
   }
   
   public class ColumnListenerMonitoramento extends MouseAdapter
   {
     protected ColumnListenerMonitoramentoData data = new ColumnListenerMonitoramentoData();

	public ColumnListenerMonitoramento(JTable table) {
       this.data.m_table = table;
     }
     
     public void mouseClicked(MouseEvent e)
     {
       TableColumnModel colModel = this.data.m_table.getColumnModel();
       int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
       int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
       
       if (modelIndex < 0)
         return;
       if (DadosTabelaCotacoes.this.m_sortCol == modelIndex) {
         DadosTabelaCotacoes.this.m_sortAsc = (!DadosTabelaCotacoes.this.m_sortAsc);
       } else {
         DadosTabelaCotacoes.this.m_sortCol = modelIndex;
       }
       for (int i = 0; i < DadosTabelaCotacoes.this.m_columnsCount; i++) {
         TableColumn column = colModel.getColumn(i);
         column.setHeaderValue(DadosTabelaCotacoes.this.getColumnName(column.getModelIndex()));
       }
       this.data.m_table.getTableHeader().repaint();
       
       Collections.sort(DadosTabelaCotacoes.this.m_vector, 
         new ComparadorCotacoes(modelIndex, DadosTabelaCotacoes.this.m_sortAsc));
       this.data.m_table.tableChanged(
         new TableModelEvent(DadosTabelaCotacoes.this));
       this.data.m_table.repaint();
     }
   }
   
   public class ColumnMovementMonitoramentoListener implements TableColumnModelListener {
     ColumnMovementMonitoramentoListener() {}
     
     public void columnAdded(TableColumnModelEvent e) { DadosTabelaCotacoes.this.m_columnsCount += 1; }
     
     public void columnRemoved(TableColumnModelEvent e)
     {
       DadosTabelaCotacoes.this.m_columnsCount -= 1;
       if (DadosTabelaCotacoes.this.m_sortCol >= e.getFromIndex()) {
         DadosTabelaCotacoes.this.m_sortCol = 0;
       }
     }
     
     public void columnMarginChanged(ChangeEvent e) {}
     
     public void columnMoved(TableColumnModelEvent e) {}
     
     public void columnSelectionChanged(ListSelectionEvent e) {}
   }
 }

