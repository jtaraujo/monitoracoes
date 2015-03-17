 package planilhaAT;
 
 import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.TreeMap;

import util.config.Constantes;
import util.dao.Base;
import util.vo.ItemPlanilha;
 
 public class CarregarPlanilha implements Runnable
 {
   private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   
   Thread runner;
   
   public CarregarPlanilha()
   {
     Thread runner = new Thread(this);
     runner.start();
   }
   
   public void run() {
     System.out.println("Iniciando Atualizacao da Planilha");
     Base base = new Base();
     TreeMap<String, ItemPlanilha> planilhaMap = new TreeMap();
     try {
       Scanner scan = new Scanner(getPage(Constantes.getInstance().hostPlanilha));
       Thread.sleep(2000L);
       StringBuffer page = new StringBuffer();
       boolean dentroTabela = false;
       String data = "";
       while (scan.hasNextLine()) {
         String linhaAtual = scan.nextLine();
         if (linhaAtual.startsWith("<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\" class=\"tb_padrao\">")) {
           dentroTabela = true;
         }
         if ((linhaAtual.startsWith("</table>")) && (dentroTabela)) {
           dentroTabela = false;
         }
         if (linhaAtual.startsWith("<tr bgcolor='#F7f7f7' width='100px' height='20'><td class='Trebuchet_cinza_10' colspan=11 align='right'><span class='Trebuchet_cinza_10_bold'>Última Atualização")) {
           data = extractValue("Última Atualização:</span> ", " às ", linhaAtual);
         }
         if (dentroTabela) {
           page.append(linhaAtual);
           page.append("\n");
         }
       }
       scan.close();
       
       String dadosPlanilha = page.toString();
       int pos = dadosPlanilha.lastIndexOf("id=\"linha");
       int qtdeLinhas = Integer.parseInt(extractValue("\"linha", "\" onMouseOver=\"mudaCor(", dadosPlanilha.substring(pos)));
       for (int i = 1; i <= qtdeLinhas; i++) {
         String linhaPlanilha = extractValue("id=\"linha" + i, "</tr>", dadosPlanilha);
         String acao = extractValue("<td  class=\"Trebuchet_cinza_10\" width=\"6%\"  height=\"20\"><b>", "</b></td>", linhaPlanilha);
         
         String fechamento = extractValue("<td  class=\"Trebuchet_cinza_10\" width=\"13%\" height=\"20\">", "</td>", linhaPlanilha);
         
         String posicao = extractValue("<td  class=\"Trebuchet_cinza_10\" width=\"9%\" height=\"20\"><font color=", "</font></td>", linhaPlanilha);
         posicao = posicao.substring(posicao.lastIndexOf(">") + 1);
         
         int inicio = linhaPlanilha.indexOf("<td  class=\"Trebuchet_cinza_10\" width=\"8%\" height=\"20\">");
         linhaPlanilha = linhaPlanilha.substring(inicio);
 
 
         String suporte_1 = extractValue("  <td  class=\"Trebuchet_cinza_10\" width=\"9%\" height=\"20\">", "</td>", linhaPlanilha);
         inicio = linhaPlanilha.indexOf("  <td  class=\"Trebuchet_cinza_10\" width=\"9%\" height=\"20\">");
         linhaPlanilha = linhaPlanilha.substring(inicio);
         
         String suporte_2 = extractValue("  <td  class=\"Trebuchet_cinza_10\" width=\"6%\" height=\"20\">", "</td>", linhaPlanilha);
         inicio = linhaPlanilha.indexOf("  <td  class=\"Trebuchet_cinza_10\" width=\"6%\" height=\"20\">");
         linhaPlanilha = linhaPlanilha.substring(inicio);
         
         String suporte_3 = extractValue("  <td class=\"Trebuchet_cinza_10\" width=\"8%\" height=\"20\">", "</td>", linhaPlanilha);
         inicio = linhaPlanilha.indexOf("  <td class=\"Trebuchet_cinza_10\" width=\"8%\" height=\"20\">");
         linhaPlanilha = linhaPlanilha.substring(inicio);
         
         String resistencia_1 = extractValue("  <td  class=\"Trebuchet_cinza_10\" width=\"8%\" height=\"20\">", "</td>", linhaPlanilha);
         inicio = linhaPlanilha.indexOf("  <td  class=\"Trebuchet_cinza_10\" width=\"8%\" height=\"20\">");
         linhaPlanilha = linhaPlanilha.substring(inicio);
         
         String resistencia_2 = extractValue("    <td  class=\"Trebuchet_cinza_10\" width=\"8%\" height=\"20\">", "</td>", linhaPlanilha);
         inicio = linhaPlanilha.indexOf("    <td  class=\"Trebuchet_cinza_10\" width=\"8%\" height=\"20\">");
         linhaPlanilha = linhaPlanilha.substring(inicio);
         
         String resistencia_3 = extractValue("    <td  class=\"Trebuchet_cinza_10\" width=\"7%\" height=\"20\">", "</td>", linhaPlanilha);
         inicio = linhaPlanilha.indexOf("    <td  class=\"Trebuchet_cinza_10\" width=\"7%\" height=\"20\">");
         linhaPlanilha = linhaPlanilha.substring(inicio);
         
 
         ItemPlanilha p = new ItemPlanilha();
         
         p.setAcao(acao);
         p.setPosicao(posicao);
         fechamento = fechamento.replace(",", ".");
         p.setFechamento(Double.valueOf(Double.parseDouble(fechamento)));
         
         if (suporte_1.equals("-")) {
           p.setSuporte_1(null);
         } else {
           suporte_1 = suporte_1.replace(",", ".");
           p.setSuporte_1(Double.valueOf(Double.parseDouble(suporte_1)));
         }
         if (suporte_2.equals("-")) {
           p.setSuporte_2(null);
         } else {
           suporte_2 = suporte_2.replace(",", ".");
           p.setSuporte_2(Double.valueOf(Double.parseDouble(suporte_2)));
         }
         if (suporte_3.equals("-")) {
           p.setSuporte_3(null);
         } else {
           suporte_3 = suporte_3.replace(",", ".");
           p.setSuporte_3(Double.valueOf(Double.parseDouble(suporte_3)));
         }
         
         if (resistencia_1.equals("-")) {
           p.setResistencia_1(null);
         } else {
           resistencia_1 = resistencia_1.replace(",", ".");
           p.setResistencia_1(Double.valueOf(Double.parseDouble(resistencia_1)));
         }
         if (resistencia_2.equals("-")) {
           p.setResistencia_2(null);
         } else {
           resistencia_2 = resistencia_2.replace(",", ".");
           p.setResistencia_2(Double.valueOf(Double.parseDouble(resistencia_2)));
         }
         if (resistencia_3.equals("-")) {
           p.setResistencia_3(null);
         } else {
           resistencia_3 = resistencia_3.replace(",", ".");
           p.setResistencia_3(Double.valueOf(Double.parseDouble(resistencia_3)));
         }
         p.setDate(this.sdf.parse(data));
         
         planilhaMap.put(Integer.valueOf(i).toString(), p);
       }
       
 
 
 
 
       base.inserePlanilha(planilhaMap);
       Thread.sleep(2000L);
     }
     catch (ParseException e) {
       e.printStackTrace();
     } catch (InterruptedException e) {
       e.printStackTrace();
     }
     System.out.println("Planilha Atualizada com Sucesso!");
   }
   
   private String extractValue(String startAt, String finishAt, String responseBody) {
     String vl = "";
     int p = responseBody.indexOf(startAt);
     
     if (p > 0) {
       vl = responseBody.substring(p + startAt.length());
       int p2 = vl.indexOf(finishAt);
       return vl.substring(0, p2);
     }
     
     return "";
   }
   
   public String getPage(String x)
   {
     StringBuffer page = new StringBuffer();
     try
     {
       URL url = new URL(x);
       URLConnection conn = url.openConnection();
       conn.setDoOutput(false);
       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.8) Gecko/20061201 Firefox/2.0.0.8 (Ubuntu-feisty)");
       conn.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
       
 
       BufferedReader rd = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
       String line;
       while ((line = rd.readLine()) != null) { 
         page.append(line).append("\n");
       }
       rd.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
     
     return page.toString();
   }
 }

