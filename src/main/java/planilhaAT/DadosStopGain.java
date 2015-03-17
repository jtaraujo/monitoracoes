 package planilhaAT;
 
 import java.awt.Color;
 
 public class DadosStopGain
 {
   public Color m_color;
   public Object m_data;
   public static Color BLUE = Color.blue;
   public static Color GREEN = new Color(0, 128, 0);
   
 
   public DadosStopGain(Color color, Object data)
   {
     this.m_color = color;
     this.m_data = data;
   }
   
   public DadosStopGain(Double data) {
     this.m_color = (data.doubleValue() >= 0.0D ? GREEN : BLUE);
     this.m_data = data;
   }
   
   public String toString() {
     return this.m_data.toString();
   }
 }

