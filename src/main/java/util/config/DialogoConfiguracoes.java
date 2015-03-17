 package util.config;
 
 import java.awt.BorderLayout;
 import java.awt.GridLayout;
 import java.awt.Point;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.beans.PropertyChangeEvent;
 import java.beans.PropertyChangeListener;
 import java.text.DateFormat;
 import java.text.NumberFormat;
 import java.util.Date;
 import java.util.Locale;
 import javax.swing.BorderFactory;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JFormattedTextField;
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 
 
 
 public class DialogoConfiguracoes
   extends JDialog
   implements PropertyChangeListener
 {
   private JLabel dataAtualLabel;
   private JLabel mediaMenorLabel;
   private JLabel mediaMaiorLabel;
   private JLabel filtroMinNegociosLabel;
   private JLabel variacaoGapAberturaLabel;
   private JLabel multiplicadorVolumeLabel;
   private static String dataAtualString = "Data Monitor: ";
   private static String mediaMenorString = "Dias Media Curta: ";
   private static String mediaMaiorString = "Dias Media Longa: ";
   private static String filtroMinNegociosString = "Filtro Min. Negocios: ";
   private static String variacaoGapAberturaString = "Variação Gap Abertura (%): ";
   private static String multiplicadorVolumeString = "Multiplicador Volume: ";
   
   private JFormattedTextField dataAtualField;
   
   private JFormattedTextField mediaMenorField;
   
   private JFormattedTextField mediaMaiorField;
   
   private JFormattedTextField filtroMinNegociosField;
   private JFormattedTextField variacaoGapAberturaField;
   private JFormattedTextField multiplicadorVolumeField;
   private DateFormat dataAtualFormat;
   private NumberFormat mediaMenorFormat;
   private NumberFormat mediaMaiorFormat;
   private NumberFormat filtroMinNegociosFormat;
   private NumberFormat variacaoGapAberturaFormat;
   private NumberFormat multiplicadorVolumeFormat;
   
   public DialogoConfiguracoes(JFrame parent)
   {
     super(parent, true);
     setSize(320, 210);
     
     Point p = parent.getLocation();
     setLocation(p.x + Math.round(parent.getWidth() / 2 - 160), 
       p.y + Math.round(parent.getHeight() / 2 - 105));
     
     setTitle("Configurar Propriedades");
     
     JPanel newContentPane = new JPanel(new BorderLayout());
     newContentPane.setOpaque(true);
     
     configurarFormatos();
     
 
     this.dataAtualLabel = new JLabel(dataAtualString);
     this.mediaMenorLabel = new JLabel(mediaMenorString);
     this.mediaMaiorLabel = new JLabel(mediaMaiorString);
     this.filtroMinNegociosLabel = new JLabel(filtroMinNegociosString);
     this.variacaoGapAberturaLabel = new JLabel(variacaoGapAberturaString);
     this.multiplicadorVolumeLabel = new JLabel(multiplicadorVolumeString);
     
 
     this.dataAtualField = new JFormattedTextField(this.dataAtualFormat);
     this.dataAtualField.setValue(Constantes.getInstance().data_atual);
     this.dataAtualField.setColumns(10);
     this.dataAtualField.addPropertyChangeListener("value", this);
     
     this.mediaMenorField = new JFormattedTextField(this.mediaMenorFormat);
     this.mediaMenorField.setValue(Integer.valueOf(Constantes.getInstance().media_menor));
     this.mediaMenorField.setColumns(10);
     this.mediaMenorField.addPropertyChangeListener("value", this);
     
     this.mediaMaiorField = new JFormattedTextField(this.mediaMaiorFormat);
     this.mediaMaiorField.setValue(Integer.valueOf(Constantes.getInstance().media_maior));
     this.mediaMaiorField.setColumns(10);
     this.mediaMaiorField.addPropertyChangeListener("value", this);
     
     this.filtroMinNegociosField = new JFormattedTextField(this.filtroMinNegociosFormat);
     this.filtroMinNegociosField.setValue(Integer.valueOf(Constantes.getInstance().filtro_min_negocios));
     this.filtroMinNegociosField.setColumns(10);
     this.filtroMinNegociosField.addPropertyChangeListener("value", this);
     
     this.variacaoGapAberturaField = new JFormattedTextField(this.variacaoGapAberturaFormat);
     this.variacaoGapAberturaField.setValue(Float.valueOf(Constantes.getInstance().variacao_gap_abertura));
     this.variacaoGapAberturaField.setColumns(10);
     this.variacaoGapAberturaField.addPropertyChangeListener("value", this);
     
     this.multiplicadorVolumeField = new JFormattedTextField(this.multiplicadorVolumeFormat);
     this.multiplicadorVolumeField.setValue(Float.valueOf(Constantes.getInstance().multiplicador_volume));
     this.multiplicadorVolumeField.setColumns(10);
     this.multiplicadorVolumeField.addPropertyChangeListener("value", this);
     
     this.dataAtualLabel.setLabelFor(this.dataAtualField);
     this.mediaMenorLabel.setLabelFor(this.mediaMenorField);
     this.mediaMaiorLabel.setLabelFor(this.mediaMaiorField);
     this.filtroMinNegociosLabel.setLabelFor(this.filtroMinNegociosField);
     this.variacaoGapAberturaLabel.setLabelFor(this.variacaoGapAberturaField);
     this.multiplicadorVolumeLabel.setLabelFor(this.multiplicadorVolumeField);
     
 
 
 
     JPanel labelPane = new JPanel(new GridLayout(0, 1));
     labelPane.add(this.dataAtualLabel);
     labelPane.add(this.mediaMenorLabel);
     labelPane.add(this.mediaMaiorLabel);
     labelPane.add(this.filtroMinNegociosLabel);
     labelPane.add(this.variacaoGapAberturaLabel);
     labelPane.add(this.multiplicadorVolumeLabel);
     
 
 
     JPanel fieldPane = new JPanel(new GridLayout(0, 1));
     fieldPane.add(this.dataAtualField);
     fieldPane.add(this.mediaMenorField);
     fieldPane.add(this.mediaMaiorField);
     fieldPane.add(this.filtroMinNegociosField);
     fieldPane.add(this.variacaoGapAberturaField);
     fieldPane.add(this.multiplicadorVolumeField);
     
 
 
 
     newContentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
     newContentPane.add(labelPane, "Center");
     newContentPane.add(fieldPane, "After");
     
     JButton b = new JButton("Ok");
     b.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent ev) {
         DialogoConfiguracoes.this.atualizarValores();
         DialogoConfiguracoes.this.dispose();
       }
     });
     newContentPane.add(b, "South");
     
     setContentPane(newContentPane);
     
     setDefaultCloseOperation(2);
     pack();
     setVisible(true);
   }
   
   public void atualizarValores() {
     Constantes.getInstance().data_atual = ((Date)this.dataAtualField.getValue());
     Constantes.getInstance().media_menor = ((Number)this.mediaMenorField.getValue()).intValue();
     Constantes.getInstance().media_maior = ((Number)this.mediaMaiorField.getValue()).intValue();
     Constantes.getInstance().filtro_min_negocios = ((Number)this.filtroMinNegociosField.getValue()).intValue();
     Constantes.getInstance().variacao_gap_abertura = ((Number)this.variacaoGapAberturaField.getValue()).floatValue();
     Constantes.getInstance().multiplicador_volume = ((Number)this.multiplicadorVolumeField.getValue()).floatValue();
   }
   
 
   private void configurarFormatos()
   {
     this.dataAtualFormat = DateFormat.getDateInstance(2, new Locale("pt", "br"));
     this.mediaMenorFormat = NumberFormat.getNumberInstance();
     this.mediaMenorFormat.setMinimumFractionDigits(0);
     
     this.mediaMaiorFormat = NumberFormat.getNumberInstance();
     this.mediaMaiorFormat.setMinimumFractionDigits(0);
     
     this.filtroMinNegociosFormat = NumberFormat.getNumberInstance();
     this.filtroMinNegociosFormat.setMinimumFractionDigits(0);
     
     this.variacaoGapAberturaFormat = NumberFormat.getNumberInstance();
     this.variacaoGapAberturaFormat.setMinimumFractionDigits(2);
     
     this.multiplicadorVolumeFormat = NumberFormat.getNumberInstance();
     this.multiplicadorVolumeFormat.setMinimumFractionDigits(2);
   }
   
   public void propertyChange(PropertyChangeEvent e) {}
 }

