 package planilhaAT;
 
 import java.awt.Color;
 
 public class DadosFundoDescendente
 {
   public Color m_color;
   public Object m_data;
   public static Color ORANGE = new Color(200, 100, 10);
   public static Color GREEN = new Color(0, 128, 0);
   
 
   public DadosFundoDescendente(Color color, Object data)
   {
     this.m_color = color;
     this.m_data = data;
   }
   
   public DadosFundoDescendente(Double data) {
     this.m_color = (data.doubleValue() >= 0.0D ? GREEN : ORANGE);
     this.m_data = data;
   }
   
   public String toString() {
     return this.m_data.toString();
   }
 }

