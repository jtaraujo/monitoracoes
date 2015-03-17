 package util.vo;
 
 import java.util.Date;
 
 public class Acao {
   private String code;
   private Double volume;
   private long negocios;
   private Double preco;
   private Double abertura;
   private Double fechamento;
   private Double minimo;
   private Double maximo;
   private Double variacao;
   private Date date;
   
   public Acao() {}
   
   public Double getPreco() { return this.preco; }
   
   public void setPreco(Double preco) {
     this.preco = preco;
   }
   
   public Double getVariacao() { return this.variacao; }
   
   public void setVariacao(Double variacao) {
     this.variacao = variacao;
   }
   
   public Date getDate() { return this.date; }
   
   public void setDate(Date date) {
     this.date = date;
   }
   
   public String getCode() { return this.code; }
   
   public void setCode(String code) {
     this.code = code;
   }
   
   public Double getVolume() { return this.volume; }
   
   public void setVolume(Double volume) {
     this.volume = volume;
   }
   
   public Long getNegocios() { return Long.valueOf(this.negocios); }
   
   public void setNegocios(Long negocios) {
     this.negocios = negocios.longValue();
   }
   
   public String toString() { return this.code + ":" + this.preco + ":" + this.negocios + ":" + this.volume + ":" + this.variacao + ":" + this.date; }
   
   public Double getAbertura() {
     return this.abertura;
   }
   
   public void setAbertura(Double abertura) { this.abertura = abertura; }
   
   public Double getFechamento() {
     return this.fechamento;
   }
   
   public void setFechamento(Double fechamento) { this.fechamento = fechamento; }
   
   public Double getMinimo() {
     return this.minimo;
   }
   
   public void setMinimo(Double minimo) { this.minimo = minimo; }
   
   public Double getMaximo() {
     return this.maximo;
   }
   
   public void setMaximo(Double maximo) { this.maximo = maximo; }
   
   public void setNegocios(long negocios) {
     this.negocios = negocios;
   }
 }

