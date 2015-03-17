 package alertaWeb;
 
 public class Alerta { private int id;
   private String sql;
   private String[] email;
   private String acao;
   
   public Alerta() {}
   
   public int getId() { return this.id; }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getSql() { return this.sql; }
   
   public void setSql(String sql) {
     this.sql = sql;
   }
   
   public String[] getEmail() { return this.email; }
   
   public void setEmail(String[] email) {
     this.email = email;
   }
   
   public String getAcao() { return this.acao; }
   
   public void setAcao(String acao) {
     this.acao = acao;
   }
 }

