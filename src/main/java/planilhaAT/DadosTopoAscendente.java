 package planilhaAT;
 
 import java.awt.Color;
 
 public class DadosTopoAscendente
 {
   public Color m_color;
   public Object m_data;
   public static Color BLUE = Color.blue;
   public static Color RED = Color.RED;
   
 
   public DadosTopoAscendente(Color color, Object data)
   {
     this.m_color = color;
     this.m_data = data;
   }
   
   public DadosTopoAscendente(Double data) {
     this.m_color = (data.doubleValue() >= 0.0D ? BLUE : RED);
     this.m_data = data;
   }
   
   public String toString() {
     return this.m_data.toString();
   }
 }

