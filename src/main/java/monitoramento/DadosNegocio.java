 package monitoramento;
 
 import java.awt.Color;
 import util.config.Constantes;
 
 
 public class DadosNegocio
 {
   public Color m_color;
   public Object m_data;
   public static Color BLUE = Color.blue;
   public static Color RED = Color.red;
   
 
   public DadosNegocio(Color color, Object data)
   {
     this.m_color = color;
     this.m_data = data;
   }
   
   public DadosNegocio(Double data) {
     this.m_color = (data.doubleValue() >= Constantes.getInstance().limiarnegocios ? BLUE : RED);
     this.m_data = data;
   }
   
   public String toString() {
     return this.m_data.toString();
   }
 }

