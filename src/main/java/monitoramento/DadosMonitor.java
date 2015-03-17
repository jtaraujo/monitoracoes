 package monitoramento;
 
 import javax.swing.ImageIcon;
 import util.tabelas.DadosIcones;
 
 
 public class DadosMonitor
 {
   public ImageIcon ICON_UP = new ImageIcon(this.getClass().getResource("/img/ArrUp.gif"));
   public ImageIcon ICON_DOWN = new ImageIcon(this.getClass().getResource("/img/ArrDown.gif"));
   public ImageIcon ICON_BLANK = new ImageIcon(this.getClass().getResource("/img/equals.gif"));
   
   public DadosIcones icone;
   
   private String acao;
   
   private DadosVariacao variacao;
   private Double minimo;
   private Double maximo;
   private Double preco;
   private Double preco_medio;
   private Double volume;
   private Double volume_medio;
   private DadosNegocio negocio;
   private Double negocio_medio;
   private String msg_alerta;
   
   public DadosMonitor(String acao, double variacao, double preco, double preco_medio, double minimo, double maximo, double volume, double volume_medio, long negocio, double negocio_medio, String msg_alerta)
   {
     this.icone = new DadosIcones(getIcon(variacao), acao);
     this.acao = acao;
     this.variacao = new DadosVariacao(new Double(variacao));
     this.preco = new Double(preco);
     this.minimo = new Double(minimo);
     this.maximo = new Double(maximo);
     this.preco_medio = new Double(preco_medio);
     this.volume = new Double(volume);
     this.volume_medio = new Double(volume_medio);
     this.negocio = new DadosNegocio(new Double(negocio));
     this.negocio_medio = new Double(negocio_medio);
     this.msg_alerta = msg_alerta;
   }
   
   public ImageIcon getIcon(double change) {
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
   
   public String getMsg_alerta() {
     return this.msg_alerta;
   }
   
   public void setMsg_alerta(String msg_alerta) {
     this.msg_alerta = msg_alerta;
   }
   
   public DadosNegocio getNegocio() {
     return this.negocio;
   }
   
   public void setNegocio(DadosNegocio negocio) {
     this.negocio = negocio;
   }
   
   public Double getNegocio_medio() {
     return this.negocio_medio;
   }
   
   public void setNegocio_medio(Double negocio_medio) {
     this.negocio_medio = negocio_medio;
   }
   
   public Double getPreco() {
     return this.preco;
   }
   
   public void setPreco(Double preco) {
     this.preco = preco;
   }
   
   public Double getPreco_medio() {
     return this.preco_medio;
   }
   
   public void setPreco_medio(Double preco_medio) {
     this.preco_medio = preco_medio;
   }
   
   public DadosVariacao getVariacao() {
     return this.variacao;
   }
   
   public void setVariacao(DadosVariacao variacao) {
     this.variacao = variacao;
   }
   
   public Double getVolume() {
     return this.volume;
   }
   
   public void setVolume(Double volume) {
     this.volume = volume;
   }
   
   public Double getVolume_medio() {
     return this.volume_medio;
   }
   
   public void setVolume_medio(Double volume_medio) {
     this.volume_medio = volume_medio;
   }
   
   public Double getMaximo() {
     return this.maximo;
   }
   
   public void setMaximo(Double maximo) {
     this.maximo = maximo;
   }
   
   public Double getMinimo() {
     return this.minimo;
   }
   
   public void setMinimo(Double minimo) {
     this.minimo = minimo;
   }
 }

