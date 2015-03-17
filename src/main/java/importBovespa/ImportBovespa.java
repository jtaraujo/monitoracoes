 package importBovespa;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import util.config.Constantes;
import util.dao.Base;
import util.vo.Acao;
 
 public class ImportBovespa
   implements Runnable
 {
   private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
   
   Thread runner;
   private Scanner scan;
   
   public ImportBovespa(String arquivoImportacao)
   {
     this.scan = getArquivoPlainText(arquivoImportacao);
     Thread runner = new Thread(this);
     runner.start();
   }
   
   public void run() {
     if (this.scan == null) {
       Constantes.getInstance().estaLendoArquivoCotacoes = false;
       System.out.println("Erro: Arquivo de Cotacoes Hiistóricas deve ser \".txt\" ou \".zip\"!");
       return;
     }
     System.out.println("Iniciando Atualizacao das Cotacoes Historicas");
     Constantes.getInstance().estaLendoArquivoCotacoes = true;
     try {
       Base base = new Base();
       String data = "";
       
       long count = 0L;
       TreeMap<String, Acao> acaoMap = new TreeMap<String, Acao>();
       while (this.scan.hasNextLine()) {
         String x = this.scan.nextLine();
         if (x.startsWith("01")) {
           data = x.substring(2, 10);
           String tp = x.substring(10, 12);
           if (tp.equals("02")) {
             String acao = x.substring(12, 23).trim();
             String abertura = x.substring(56, 69);
             String maximo = x.substring(69, 82);
             String minimo = x.substring(82, 95);
             String fechamento = x.substring(108, 121);
             String preco = x.substring(108, 121);
             String negocios = x.substring(147, 152);
             String volume = x.substring(170, 188);
             
             Acao a = new Acao();
             a.setCode(acao);
             a.setDate(this.sdf.parse(data));
             a.setNegocios(Long.parseLong(negocios));
             a.setPreco(Double.valueOf(Double.parseDouble(preco) / 100.0D));
             a.setVolume(Double.valueOf(Double.parseDouble(volume) / 100.0D));
             a.setAbertura(Double.valueOf(Double.parseDouble(abertura) / 100.0D));
             a.setMaximo(Double.valueOf(Double.parseDouble(maximo) / 100.0D));
             a.setMinimo(Double.valueOf(Double.parseDouble(minimo) / 100.0D));
             a.setFechamento(Double.valueOf(Double.parseDouble(fechamento) / 100.0D));
             a.setVariacao(Double.valueOf(0.0D));
             count++;
             acaoMap.put(Long.valueOf(count).toString(), a);
             if (count > 10000L) {
               count = 0L;
               base.insereCotacao(acaoMap);
               acaoMap.clear();
               Thread.sleep(2000L);
               System.out.println("Importando Cotacoes Historicas ate " + data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4));
             }
           }
         } }
       if (count > 0L) {
         base.insereCotacao(acaoMap);
         System.out.println("Importando Cotacoes Historicas ate " + data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4));
         
         base.AtualizarVariacoes();
         count = 0L;
       }
     }
     catch (ParseException e) {
       e.printStackTrace();
     }
     catch (InterruptedException e) {
       e.printStackTrace();
     }
     Constantes.getInstance().estaLendoArquivoCotacoes = false;
     System.out.println("Atualizacao das Cotacoes Historicas com Sucesso!");
   }
   
   private Scanner getArquivoPlainText(String arquivoImportacao)
   {
     try {
       if (arquivoImportacao.toLowerCase().endsWith(".zip"))
       {
         ZipFile zipFile = new ZipFile(arquivoImportacao);
         Enumeration enumeration = zipFile.entries();
         
         ZipEntry zipEntry = (ZipEntry)enumeration.nextElement();
         
 
         return new Scanner(zipFile.getInputStream(zipEntry));
       }
       
       if (!arquivoImportacao.toLowerCase().endsWith(".txt")) 
       return new Scanner(new FileInputStream(arquivoImportacao));
 
     }
     catch (FileNotFoundException e)
     {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace(); 
       }
     return null;
   }
 }

