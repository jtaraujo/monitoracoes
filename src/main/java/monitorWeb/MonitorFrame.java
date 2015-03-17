package monitorWeb;

import importBovespa.FiltroTxtZip;
import importBovespa.ImportBovespa;
import importBovespa.LimparCotacoes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import monitoramento.TableCellRendererColoridoMonitoramento;
import planilhaAT.CarregarPlanilha;
import planilhaAT.TableCellRendererColoridoPlanilha;
import util.config.Constantes;
import util.config.DialogoConfiguracoes;
import util.tabelas.DadosColuna;

public class MonitorFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8843587918732006727L;
	protected JTable m_table_monitoramento;
	protected DadosTabelaCotacoes m_data_monitoramento;
	protected JTable m_table_planilha;
	protected DadosPlanilhaCotacoes m_data_planilha;
	protected JLabel m_title;

	public MonitorFrame() {
		super("Monitoramento de Ações");
		setSize(Constantes.getInstance().largura,
				Constantes.getInstance().comprimento);

		JPanel conteudoMonitoramento = new JPanel();
		conteudoMonitoramento.setLayout(new BorderLayout(1, 1));

		this.m_table_monitoramento = new JTable();
		this.m_table_monitoramento.setAutoCreateColumnsFromModel(false);
		this.m_data_monitoramento = new DadosTabelaCotacoes(
				Constantes.getInstance().intervaloMonitor);
		this.m_table_monitoramento.setModel(this.m_data_monitoramento);

		for (int k = 0; k < DadosTabelaCotacoes.m_columns.length; k++) {
			DefaultTableCellRenderer renderer = new TableCellRendererColoridoMonitoramento();
			renderer.setHorizontalAlignment(DadosTabelaCotacoes.m_columns[k].m_alignment);
			TableColumn column = new TableColumn(k,
					DadosTabelaCotacoes.m_columns[k].m_width, renderer, null);
			this.m_table_monitoramento.addColumn(column);
		}

		JTableHeader header_monitoramento = this.m_table_monitoramento
				.getTableHeader();
		header_monitoramento.setUpdateTableInRealTime(true);
		header_monitoramento
				.addMouseListener(m_data_monitoramento.new ColumnListenerMonitoramento(this.m_table_monitoramento));
		header_monitoramento.setReorderingAllowed(true);
		
		this.m_table_monitoramento.getColumnModel().addColumnModelListener(
				m_data_monitoramento.new ColumnMovementMonitoramentoListener());

		JScrollPane ps = new JScrollPane();
		ps.getViewport().add(this.m_table_monitoramento);

		conteudoMonitoramento.add(ps, "Center");

		JPanel conteudoPlanilha = new JPanel();
		conteudoPlanilha.setLayout(new BorderLayout(1, 1));

		this.m_table_planilha = new JTable();
		this.m_table_planilha.setAutoCreateColumnsFromModel(false);
		this.m_data_planilha = new DadosPlanilhaCotacoes(
				Constantes.getInstance().intervaloMonitor);
		this.m_table_planilha.setModel(this.m_data_planilha);

		for (int k = 0; k < DadosPlanilhaCotacoes.m_columns.length; k++) {
			DefaultTableCellRenderer renderer = new TableCellRendererColoridoPlanilha();
			renderer.setHorizontalAlignment(DadosPlanilhaCotacoes.m_columns[k].m_alignment);
			TableColumn column = new TableColumn(k,
					DadosPlanilhaCotacoes.m_columns[k].m_width, renderer, null);
			this.m_table_planilha.addColumn(column);
		}

		JTableHeader header_planilha = this.m_table_planilha.getTableHeader();
		header_planilha.setUpdateTableInRealTime(true);
		header_planilha
				.addMouseListener(m_data_planilha.new ColumnPlanilhaListener(
						 this.m_table_planilha));
		header_planilha.setReorderingAllowed(true);
	

		this.m_table_planilha.getColumnModel().addColumnModelListener(
				m_data_planilha.new ColumnMovementPlanilhaListener());

		JScrollPane ps_planilha = new JScrollPane();
		ps_planilha.getViewport().add(this.m_table_planilha);

		conteudoPlanilha.add(ps_planilha, "Center");

		ImageIcon moneyIcon = new ImageIcon(this.getClass().getResource("/img/money.gif"));
		
		this.m_title = new JLabel(this.m_data_monitoramento.getTitle(),
				moneyIcon, 2);
		this.m_title.setFont(new Font("TimesRoman", 1, 24));
		this.m_title.setForeground(Color.black);

		getContentPane().add(this.m_title, "North");

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add(conteudoMonitoramento, "Monitoramento");
		tabbedPane.add(conteudoPlanilha, "Planilha");

		getContentPane().add(tabbedPane, "Center");
		setLocationRelativeTo(null);

		JMenuBar menuBar = createMenuBar();
		setJMenuBar(menuBar);

		WindowListener wndCloser = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		addWindowListener(wndCloser);

		setVisible(true);
	}

	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu mFile = new JMenu("Arquivo");
		mFile.setMnemonic('a');

		JMenuItem mData = new JMenuItem("Configurar");
		mData.setMnemonic('c');
		ActionListener lstData = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogoConfiguracoes(MonitorFrame.this);

				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(3));

				MonitorFrame.this.m_title
						.setText(MonitorFrame.this.m_data_monitoramento
								.getTitle());
				MonitorFrame.this.m_table_monitoramento
						.tableChanged(new TableModelEvent(
								MonitorFrame.this.m_data_monitoramento));
				MonitorFrame.this.m_table_monitoramento.repaint();
				MonitorFrame.this.getContentPane().repaint();

				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(0));
			}
		};
		mData.addActionListener(lstData);
		mData.setToolTipText("Atualizar as Configurações");
		mFile.add(mData);
		mFile.addSeparator();

		JMenuItem mDataPlanilha = new JMenuItem("Atualizar Planilha");
		mData.setMnemonic('p');
		ActionListener lstDataPlanilha = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(3));

				Runnable atualizarPlanilha = new Runnable() {
					public void run() {
						new CarregarPlanilha();
					}
				};
				SwingUtilities.invokeLater(atualizarPlanilha);

				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(0));
			}
		};
		mDataPlanilha.addActionListener(lstDataPlanilha);
		mDataPlanilha.setToolTipText("Atualizar Planilha Análise Gráfica");
		mFile.add(mDataPlanilha);

		JMenuItem mSerieHistorica = new JMenuItem(
				"Atualizar Cotações Históricas");
		mData.setMnemonic('s');
		ActionListener lstSerieHistorica = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(3));
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(0);
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.addChoosableFileFilter(new FiltroTxtZip());

				int returnVal = fc.showOpenDialog(MonitorFrame.this);

				if (returnVal == 0) {
					File file = fc.getSelectedFile();

					Constantes.getInstance().arqImportacao = file
							.getAbsolutePath();

					Runnable atualizarBaseCotacoes = new Runnable() {
						public void run() {
							new ImportBovespa(
									Constantes.getInstance().arqImportacao);
						}
					};
					SwingUtilities.invokeLater(atualizarBaseCotacoes);
				}

				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(0));
			}
		};
		mSerieHistorica.addActionListener(lstSerieHistorica);
		mSerieHistorica
				.setToolTipText("Selecione o Arquivo com Série Históricas Bovespa");
		mFile.add(mSerieHistorica);

		JMenuItem mLimparCotacoes = new JMenuItem("Excluir Cotações Históricas");
		mData.setMnemonic('l');
		ActionListener lstLimparCotacoes = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(3));

				Runnable LimparBaseCotacoes = new Runnable() {
					public void run() {
						new LimparCotacoes().Limpar();
					}
				};
				SwingUtilities.invokeLater(LimparBaseCotacoes);

				MonitorFrame.this.setCursor(Cursor.getPredefinedCursor(0));
			}
		};
		mLimparCotacoes.addActionListener(lstLimparCotacoes);
		mLimparCotacoes.setToolTipText("Excluir Todas as Cotações");
		mFile.add(mLimparCotacoes);

		mFile.addSeparator();

		JMenuItem mExit = new JMenuItem("Sair");
		mExit.setMnemonic('r');
		ActionListener lstExit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		mExit.addActionListener(lstExit);
		mFile.add(mExit);
		menuBar.add(mFile);

		JMenu mView = new JMenu("Monitoramento");
		mView.setMnemonic('m');
		TableColumnModel model = this.m_table_monitoramento.getColumnModel();
		for (int k = 0; k < DadosTabelaCotacoes.m_columns.length; k++) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(
					DadosTabelaCotacoes.m_columns[k].m_title);
			item.setSelected(true);
			TableColumn column = model.getColumn(k);
			item.addActionListener(new ColumnKeeperMonitoramento(column,
					DadosTabelaCotacoes.m_columns[k]));
			mView.add(item);
		}
		menuBar.add(mView);

		JMenu mViewPlanilha = new JMenu("Planilha");
		mViewPlanilha.setMnemonic('p');
		TableColumnModel modelPlanilha = this.m_table_planilha.getColumnModel();
		for (int k = 0; k < DadosPlanilhaCotacoes.m_columns.length; k++) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(
					DadosPlanilhaCotacoes.m_columns[k].m_title);
			item.setSelected(true);
			TableColumn column = modelPlanilha.getColumn(k);
			item.addActionListener(new ColumnKeeperPlanilha(column,
					DadosPlanilhaCotacoes.m_columns[k]));
			mViewPlanilha.add(item);
		}
		menuBar.add(mViewPlanilha);

		return menuBar;
	}

	class ColumnKeeperMonitoramento implements ActionListener {
		protected TableColumn m_column;
		protected DadosColuna m_colData;

		public ColumnKeeperMonitoramento(TableColumn column, DadosColuna colData) {
			this.m_column = column;
			this.m_colData = colData;
		}

		public void actionPerformed(ActionEvent e) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
			TableColumnModel model = MonitorFrame.this.m_table_monitoramento
					.getColumnModel();
			if (item.isSelected()) {
				model.addColumn(this.m_column);
			} else {
				model.removeColumn(this.m_column);
			}
			MonitorFrame.this.m_table_monitoramento
					.tableChanged(new TableModelEvent(
							MonitorFrame.this.m_data_monitoramento));
			MonitorFrame.this.m_table_monitoramento.repaint();
		}
	}

	class ColumnKeeperPlanilha implements ActionListener {
		protected TableColumn m_column;
		protected DadosColuna m_colData;

		public ColumnKeeperPlanilha(TableColumn column, DadosColuna colData) {
			this.m_column = column;
			this.m_colData = colData;
		}

		public void actionPerformed(ActionEvent e) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
			TableColumnModel model = MonitorFrame.this.m_table_planilha
					.getColumnModel();
			if (item.isSelected()) {
				model.addColumn(this.m_column);
			} else {
				model.removeColumn(this.m_column);
			}
			MonitorFrame.this.m_table_planilha
					.tableChanged(new TableModelEvent(
							MonitorFrame.this.m_data_planilha));
			MonitorFrame.this.m_table_planilha.repaint();
		}
	}
}
