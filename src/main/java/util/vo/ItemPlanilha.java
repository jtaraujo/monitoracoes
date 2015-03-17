 package util.vo;
 
 import java.util.Date;
 
 public class ItemPlanilha {
   private String acao;
   private String posicao;
   private Double fechamento;
   private Double suporte_1;
   private Double suporte_2;
   private Double suporte_3;
   private Double resistencia_1;
   private Double resistencia_2;
   private Double resistencia_3;
   private Date date;
   
   public ItemPlanilha() {}
   
   public String getAcao() { return this.acao; }
   
   public void setAcao(String acao) {
     this.acao = acao;
   }
   
   public Date getDate() { return this.date; }
   
   public void setDate(Date date) {
     this.date = date;
   }
   
   public Double getFechamento() { return this.fechamento; }
   
   public void setFechamento(Double fechamento) {
     this.fechamento = fechamento;
   }
   
   public String getPosicao() { return this.posicao; }
   
   public void setPosicao(String posicao) {
     this.posicao = posicao;
   }
   
   public Double getResistencia_1() { return this.resistencia_1; }
   
   public void setResistencia_1(Double resistencia_1) {
     this.resistencia_1 = resistencia_1;
   }
   
   public Double getResistencia_2() { return this.resistencia_2; }
   
   public void setResistencia_2(Double resistencia_2) {
     this.resistencia_2 = resistencia_2;
   }
   
   public Double getResistencia_3() { return this.resistencia_3; }
   
   public void setResistencia_3(Double resistencia_3) {
     this.resistencia_3 = resistencia_3;
   }
   
   public Double getSuporte_1() { return this.suporte_1; }
   
   public void setSuporte_1(Double suporte_1) {
     this.suporte_1 = suporte_1;
   }
   
   public Double getSuporte_2() { return this.suporte_2; }
   
   public void setSuporte_2(Double suporte_2) {
     this.suporte_2 = suporte_2;
   }
   
   public Double getSuporte_3() { return this.suporte_3; }
   
   public void setSuporte_3(Double suporte_3) {
     this.suporte_3 = suporte_3;
   }
   
   public Double getSuporte_1Formatado() {
     return Double.valueOf(getSuporte_1() != null ? getSuporte_1().doubleValue() : 0.0D);
   }
   
   public Double getSuporte_2Formatado() {
     return Double.valueOf(getSuporte_2() != null ? getSuporte_2().doubleValue() : 0.0D);
   }
   
   public Double getSuporte_3Formatado() {
     return Double.valueOf(getSuporte_3() != null ? getSuporte_3().doubleValue() : 0.0D);
   }
   
   public Double getResistencia_1Formatado() {
     return Double.valueOf(getResistencia_1() != null ? getResistencia_1().doubleValue() : 0.0D);
   }
   
   public Double getResistencia_2Formatado() {
     return Double.valueOf(getResistencia_2() != null ? getResistencia_2().doubleValue() : 0.0D);
   }
   
   public Double getResistencia_3Formatado() {
     return Double.valueOf(getResistencia_3() != null ? getResistencia_3().doubleValue() : 0.0D);
   }
 }

