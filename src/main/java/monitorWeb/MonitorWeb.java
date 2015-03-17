package monitorWeb;

import util.config.Constantes;
import coletorWeb.HttpThread;

public class MonitorWeb
 {
   public MonitorWeb() {}
   
   public static void main(String[] args)
   {
     new MonitorFrame();
     
     HttpThread web = new HttpThread(Constantes.getInstance().intervaloColetor);
     web.start();
   }
 }