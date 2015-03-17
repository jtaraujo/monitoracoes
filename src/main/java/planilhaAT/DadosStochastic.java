 package planilhaAT;
 
 import java.awt.Color;
 
 public class DadosStochastic
 {
   public Color m_color;
   public Object m_data;
   public static Color BLUE = Color.blue;
   public static Color GREEN = new Color(0, 128, 0);
   public static Color RED = Color.RED;
   
   public DadosStochastic(Color color, Object data)
   {
     this.m_color = color;
     this.m_data = data;
   }
   
   public DadosStochastic(Double data) {
     if (data.doubleValue() < 20.0D) {
       this.m_color = RED;
     }
     else if ((data.doubleValue() >= 20.0D) && (data.doubleValue() <= 80.0D)) {
       this.m_color = BLUE;
     } else {
       this.m_color = GREEN;
     }
     
     this.m_data = data;
   }
   
   public String toString() {
     return this.m_data.toString();
   }
 }
