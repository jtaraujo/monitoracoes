 package monitoramento;
 
 import java.awt.Color;
 
 public class DadosVariacao
 {
   public Color m_color;
   public Object m_data;
   public static Color GREEN = new Color(0, 128, 0);
   public static Color RED = Color.red;
   
 
   public DadosVariacao(Color color, Object data)
   {
     this.m_color = color;
     this.m_data = data;
   }
   
   public DadosVariacao(Double data) {
     this.m_color = (data.doubleValue() >= 0.0D ? GREEN : RED);
     this.m_data = data;
   }
   
   public String toString() {
     return this.m_data.toString();
   }
 }

