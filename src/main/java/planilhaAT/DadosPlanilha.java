 package planilhaAT;
 
 import javax.swing.ImageIcon;
 import util.tabelas.DadosIcones;
 
 
 public class DadosPlanilha
 {
   public static ImageIcon ICON_UP = new ImageIcon("img\\ArrUp.gif");
   public static ImageIcon ICON_DOWN = new ImageIcon("img\\ArrDown.gif");
   public static ImageIcon ICON_BLANK = new ImageIcon("img\\equals.gif");
   
   public DadosIcones icone;
   
   private String acao;
   
   private DadosIFR ifr;
   private DadosStochastic stochastic;
   private DadosTopoAscendente topoAscen;
   private DadosFundoDescendente fundoDescen;
   private DadosVariacao variacao;
   private Double preco;
   private String suportes;
   private String resistencias;
   private DadosStopLoss stop_loss_1;
   private DadosStopGain stop_gain_1;
   private DadosStopLoss stop_loss_2;
   private DadosStopGain stop_gain_2;
   private DadosStopLoss stop_loss_3;
   private DadosStopGain stop_gain_3;
   
   public DadosPlanilha(String acao, double variacao, double preco, double ifr, double stochastic, double topo_ascendente, double fundo_descendente, String suportes, String resistencias, double stop_loss_1, double stop_gain_1, double stop_loss_2, double stop_gain_2, double stop_loss_3, double stop_gain_3)
   {
     this.icone = new DadosIcones(getIcon(variacao), acao);
     this.acao = acao;
     this.ifr = new DadosIFR(new Double(ifr));
     this.stochastic = new DadosStochastic(new Double(stochastic));
     this.topoAscen = new DadosTopoAscendente(new Double(topo_ascendente));
     this.fundoDescen = new DadosFundoDescendente(new Double(fundo_descendente));
     this.variacao = new DadosVariacao(new Double(variacao));
     this.preco = new Double(preco);
     this.suportes = suportes;
     this.resistencias = resistencias;
     this.stop_loss_1 = new DadosStopLoss(new Double(stop_loss_1));
     this.stop_gain_1 = new DadosStopGain(new Double(stop_gain_1));
     this.stop_loss_2 = new DadosStopLoss(new Double(stop_loss_2));
     this.stop_gain_2 = new DadosStopGain(new Double(stop_gain_2));
     this.stop_loss_3 = new DadosStopLoss(new Double(stop_loss_3));
     this.stop_gain_3 = new DadosStopGain(new Double(stop_gain_3));
   }
   
   public static ImageIcon getIcon(double change) {
     return change < 0.0D ? ICON_DOWN : change > 0.0D ? ICON_UP : ICON_BLANK;
   }
   
   public String getAcao() {
     return this.acao;
   }
   
   public void setAcao(String acao) {
     this.acao = acao;
   }
   
   public DadosIcones getIcone() {
     return this.icone;
   }
   
   public void setIcone(DadosIcones icone) {
     this.icone = icone;
   }
   
   public Double getPreco() {
     return this.preco;
   }
   
   public void setPreco(Double preco) {
     this.preco = preco;
   }
   
   public String getResistencias() {
     return this.resistencias;
   }
   
   public void setResistencias(String resistencias) {
     this.resistencias = resistencias;
   }
   
   public DadosStopGain getStop_gain_1() {
     return this.stop_gain_1;
   }
   
   public void setStop_gain_1(DadosStopGain stop_gain_1) {
     this.stop_gain_1 = stop_gain_1;
   }
   
   public DadosStopGain getStop_gain_2() {
     return this.stop_gain_2;
   }
   
   public void setStop_gain_2(DadosStopGain stop_gain_2) {
     this.stop_gain_2 = stop_gain_2;
   }
   
   public DadosStopGain getStop_gain_3() {
     return this.stop_gain_3;
   }
   
   public void setStop_gain_3(DadosStopGain stop_gain_3) {
     this.stop_gain_3 = stop_gain_3;
   }
   
   public DadosStopLoss getStop_loss_1() {
     return this.stop_loss_1;
   }
   
   public void setStop_loss_1(DadosStopLoss stop_loss_1) {
     this.stop_loss_1 = stop_loss_1;
   }
   
   public DadosStopLoss getStop_loss_2() {
     return this.stop_loss_2;
   }
   
   public void setStop_loss_2(DadosStopLoss stop_loss_2) {
     this.stop_loss_2 = stop_loss_2;
   }
   
   public DadosStopLoss getStop_loss_3() {
     return this.stop_loss_3;
   }
   
   public void setStop_loss_3(DadosStopLoss stop_loss_3) {
     this.stop_loss_3 = stop_loss_3;
   }
   
   public String getSuportes() {
     return this.suportes;
   }
   
   public void setSuportes(String suportes) {
     this.suportes = suportes;
   }
   
   public DadosVariacao getVariacao() {
     return this.variacao;
   }
   
   public void setVariacao(DadosVariacao variacao) {
     this.variacao = variacao;
   }
   
   public DadosIFR getIfr() {
     return this.ifr;
   }
   
   public void setIfr(DadosIFR ifr) {
     this.ifr = ifr;
   }
   
   public DadosStochastic getStochastic() {
     return this.stochastic;
   }
   
   public void setStochastic(DadosStochastic stochastic) {
     this.stochastic = stochastic;
   }
   
   public DadosTopoAscendente getTopoAscen() {
     return this.topoAscen;
   }
   
   public void setTopoAscen(DadosTopoAscendente topoAscen) {
     this.topoAscen = topoAscen;
   }
   
   public DadosFundoDescendente getFundoDescen() {
     return this.fundoDescen;
   }
   
   public void setFundoDescen(DadosFundoDescendente fundoDescen) {
     this.fundoDescen = fundoDescen;
   }
 }

