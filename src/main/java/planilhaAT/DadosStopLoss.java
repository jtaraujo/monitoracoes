 package planilhaAT;
 
 import java.awt.Color;
 
 public class DadosStopLoss
 {
   public Color m_color;
   public Object m_data;
   public static Color ORANGE = new Color(200, 100, 10);
   public static Color RED = Color.RED;
   
 
   public DadosStopLoss(Color color, Object data)
   {
     this.m_color = color;
     this.m_data = data;
   }
   
   public DadosStopLoss(Double data) {
     this.m_color = (data.doubleValue() >= 0.0D ? ORANGE : RED);
     this.m_data = data;
   }
   
   public String toString() {
     return this.m_data.toString();
   }
 }
