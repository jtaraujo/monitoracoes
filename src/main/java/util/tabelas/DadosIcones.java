 package util.tabelas;
 
 import javax.swing.ImageIcon;
 
 
 
 
 public class DadosIcones
 {
   public ImageIcon m_icon;
   public Object m_data;
   
   public DadosIcones(ImageIcon icon, Object data)
   {
     this.m_icon = icon;
     
     this.m_data = data;
   }
   
 
   public String toString()
   {
     return this.m_data.toString();
   }
 }

