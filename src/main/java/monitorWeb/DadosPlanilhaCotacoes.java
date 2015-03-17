package monitorWeb;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Collections;
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

import planilhaAT.ComparadorPlanilha;
import planilhaAT.DadosPlanilha;
import util.dao.Base;
import util.tabelas.DadosColuna;

public class DadosPlanilhaCotacoes extends AbstractTableModel implements
		Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1446359021257347729L;

	public static final DadosColuna[] m_columns = {
			new DadosColuna("Ação", 90, 2),
			new DadosColuna("Variação %", 90, 4),
			new DadosColuna("Preço", 90, 4), new DadosColuna("IFR", 90, 4),
			new DadosColuna("STK%", 90, 4), new DadosColuna("Topo", 90, 4),
			new DadosColuna("Fundo", 90, 4),
			new DadosColuna("Suportes", 180, 4),
			new DadosColuna("Resistencias", 180, 4),
			new DadosColuna("Stop Loss 1", 90, 4),
			new DadosColuna("Stop Gain 1", 90, 4),
			new DadosColuna("Stop Loss 2", 90, 4),
			new DadosColuna("Stop Gain 2", 90, 4) };

	protected SimpleDateFormat m_frm;

	protected Vector m_vector;

	protected int m_columnsCount = m_columns.length;

	private int delay;

	Thread runner;
	protected int m_sortCol = 0;
	protected boolean m_sortAsc = true;

	protected int m_result = 0;

	public void setDelay(float seconds) {
		this.delay = Math.round(seconds * 1000.0F);
	}

	public DadosPlanilhaCotacoes(float initialDelay) {
		this.m_frm = new SimpleDateFormat("dd/MM/yyyy");
		this.m_vector = new Vector();
		setDefaultData();
		setDelay(initialDelay);

		Thread runner = new Thread(this);
		runner.start();
	}

	public void setDefaultData() {
		this.m_vector.removeAllElements();
		AtualizarPlanilha();
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
		DadosPlanilha row = (DadosPlanilha) this.m_vector.elementAt(nRow);
		switch (nCol) {
		case 0:
			return row.getIcone();
		case 1:
			return row.getVariacao();
		case 2:
			return row.getPreco();
		case 3:
			return row.getIfr();
		case 4:
			return row.getStochastic();
		case 5:
			return row.getTopoAscen();
		case 6:
			return row.getFundoDescen();
		case 7:
			return row.getSuportes();
		case 8:
			return row.getResistencias();
		case 9:
			return row.getStop_loss_1();
		case 10:
			return row.getStop_gain_1();
		case 11:
			return row.getStop_loss_2();
		case 12:
			return row.getStop_gain_2();
		case 13:
			return row.getStop_loss_3();
		case 14:
			return row.getStop_gain_3();
		}
		return "";
	}

	public void AtualizarPlanilha() {
		Base base = new Base();
		this.m_vector = base.getPlanilhaParametrizado();

		Collections.sort(this.m_vector, new ComparadorPlanilha(this.m_sortCol,
				this.m_sortAsc));
	}

	public void run() {
		for (;;) {
			AtualizarPlanilha();
			fireTableDataChanged();
			try {
				Thread.sleep(this.delay);
			} catch (InterruptedException localInterruptedException) {
			}
		}
	}

	class ColumnPlanilhaListener extends MouseAdapter {
		protected JTable m_table;

		public ColumnPlanilhaListener(JTable table) {
			this.m_table = table;
		}

		public void mouseClicked(MouseEvent e) {
			TableColumnModel colModel = this.m_table.getColumnModel();
			int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
			int modelIndex = colModel.getColumn(columnModelIndex)
					.getModelIndex();

			if (modelIndex < 0)
				return;
			if (DadosPlanilhaCotacoes.this.m_sortCol == modelIndex) {
				DadosPlanilhaCotacoes.this.m_sortAsc = (!DadosPlanilhaCotacoes.this.m_sortAsc);
			} else {
				DadosPlanilhaCotacoes.this.m_sortCol = modelIndex;
			}
			for (int i = 0; i < DadosPlanilhaCotacoes.this.m_columnsCount; i++) {
				TableColumn column = colModel.getColumn(i);
				column.setHeaderValue(DadosPlanilhaCotacoes.this
						.getColumnName(column.getModelIndex()));
			}
			this.m_table.getTableHeader().repaint();

			Collections.sort(DadosPlanilhaCotacoes.this.m_vector,
					new ComparadorPlanilha(modelIndex,
							DadosPlanilhaCotacoes.this.m_sortAsc));
			this.m_table.tableChanged(new TableModelEvent(
					DadosPlanilhaCotacoes.this));
			this.m_table.repaint();
		}
	}

	class ColumnMovementPlanilhaListener implements TableColumnModelListener {
		ColumnMovementPlanilhaListener() {
		}

		public void columnAdded(TableColumnModelEvent e) {
			DadosPlanilhaCotacoes.this.m_columnsCount += 1;
		}

		public void columnRemoved(TableColumnModelEvent e) {
			DadosPlanilhaCotacoes.this.m_columnsCount -= 1;
			if (DadosPlanilhaCotacoes.this.m_sortCol >= e.getFromIndex()) {
				DadosPlanilhaCotacoes.this.m_sortCol = 0;
			}
		}

		public void columnMarginChanged(ChangeEvent e) {
		}

		public void columnMoved(TableColumnModelEvent e) {
		}

		public void columnSelectionChanged(ListSelectionEvent e) {
		}
	}
}
