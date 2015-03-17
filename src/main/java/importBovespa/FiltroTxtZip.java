 package importBovespa;
 
 import java.io.File;
 
 public class FiltroTxtZip extends javax.swing.filechooser.FileFilter
 {
   public FiltroTxtZip() {}
   
   public boolean accept(File f) {
     return (f.isDirectory()) || (f.getName().toLowerCase().endsWith(".txt")) || (f.getName().toLowerCase().endsWith(".zip"));
   }
   
   public String getDescription() {
     return "Arquivos .txt ou .zip";
   }
 }
