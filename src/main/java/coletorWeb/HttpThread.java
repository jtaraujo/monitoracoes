 package coletorWeb;
 
 import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import util.config.Constantes;
import util.dao.Base;
import util.vo.Acao;
 
 
 
 public class HttpThread
   extends Thread
 {
   private String cookie;
   private TreeMap<String, String> cookieMap = new TreeMap<String, String>();
   private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   private int delay;
   
   public HttpThread(float intervalo) {
     setDelay(intervalo);
   }
   
   public void setDelay(float seconds) { this.delay = Math.round(seconds * 1000.0F); }
   
   public String getCarteira()
   {
     String teste = Constantes.getInstance().host;
     return getPage(teste);
   }
   
   public void cookie(URLConnection conn, boolean post, String data) {
     this.cookie = "";
     if (!this.cookieMap.isEmpty()) {
       for (Iterator<Map.Entry<String, String>> iterator = this.cookieMap.entrySet().iterator(); iterator.hasNext();) {
         Map.Entry<String, String> entry = (Map.Entry<String, String>)iterator.next();
         this.cookie = (this.cookie + (String)entry.getKey() + "=" + (String)entry.getValue() + "; ");
       }
       this.cookie = this.cookie.substring(0, this.cookie.length() - 2);
     }
     
 
     if (!this.cookie.equals("")) {
       conn.setRequestProperty("Cookie", this.cookie);
     }
     
     conn.setDoOutput(post);
     
     if (data != null) {
       try {
         OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
         wr.write(data);
         wr.flush();
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
     
     this.cookie = conn.getHeaderField("Set-Cookie");
     if (this.cookie != null) {
       String[] c1 = this.cookie.split("; ");
       for (int i = 0; i < c1.length; i++) {
         String[] c2 = c1[i].split("=");
         this.cookieMap.put(c2[0], c2[1]);
       }
     }
   }
   
 
   public String getPage(String x)
   {
     StringBuffer page = new StringBuffer();
     boolean telaLogin = false;
     try {
       URL url = new URL(x);
       URLConnection conn = url.openConnection();
       conn.setDoOutput(false);
       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.8) Gecko/20061201 Firefox/2.0.0.8 (Ubuntu-feisty)");
       conn.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
       
 
       cookie(conn, false, null);
       
 
       BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       
       String line;
       while ((line = rd.readLine()) != null) { 
         page.append(line).append("\n");
         
         if (line.equalsIgnoreCase("<title>InfoMoney: Login</title>")) {
           telaLogin = true;
         }
       }
       rd.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
     if (telaLogin) {
       return login();
     }
     return page.toString();
   }
   
 
   public String login()
   {
     StringBuffer page = new StringBuffer();
     boolean fase1 = true;
     String passo2 = null;
     String passo3 = null;
     try
     {
       String data = URLEncoder.encode("posteddata", "UTF-8") + "=" + URLEncoder.encode("1139BD05E", "UTF-8");
       data = data + "&" + URLEncoder.encode("help_status552979123", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
       data = data + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Constantes.getInstance().email, "UTF-8");
       data = data + "&" + URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(Constantes.getInstance().senha, "UTF-8");
       data = data + "&" + URLEncoder.encode("memEmail", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
       data = data + "&" + URLEncoder.encode("SendForm", "UTF-8") + "=" + URLEncoder.encode("Acessar", "UTF-8");
       data = data + "&" + URLEncoder.encode("destino", "UTF-8") + "=" + URLEncoder.encode(Constantes.getInstance().destino, "UTF-8");
       
       URL url = new URL("http://custom.infomoney.com.br/comunidade/login/login.asp");
       URLConnection conn = url.openConnection();
       cookie(conn, true, data);
       
 
       BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       String line;
       while ((line = rd.readLine()) != null)
       {
         if (line.startsWith("Aguarde, personalizando ambiente")) {
           fase1 = true;
         }
         if (line.startsWith("<iframe src=\"http://web.infomoney.com.br/comunidade/login/login_UserEnv.asp?usuario=")) {
           String[] aux = line.split("\"");
           passo2 = aux[1];
           passo3 = aux[11];
           aux = passo3.split("'");
           passo3 = aux[1];
         }
       }
       try
       {
         Thread.sleep(10000L);
       }
       catch (Exception localException1) {}
       rd.close();
       if (fase1)
       {
         getPage(passo2);
         
         page.append(getPage(passo3));
       }
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return page.toString();
   }
   
   private Double trata(String x) {
     try {
       if (x.indexOf("n/d") >= 0) return null;
       if (x.equals("")) return null;
       x = x.replaceAll("\\.", "");
       x = x.replace(',', '.');
       if (x.indexOf('M') > 0) {
         x = x.replaceAll("M", "");
         return Double.valueOf(Double.parseDouble(x) * 1000000.0D);
       }
       if (x.indexOf('K') > 0) {
         x = x.replaceAll("K", "");
         return Double.valueOf(Double.parseDouble(x) * 1000.0D);
       }
       if (x.indexOf('G') > 0) {
         x = x.replaceAll("G", "");
         return Double.valueOf(Double.parseDouble(x) * 1.0E9D);
       }
       if (x.indexOf('B') > 0) {
         x = x.replaceAll("B", "");
         return Double.valueOf(Double.parseDouble(x) * 1.0E12D);
       }
       return Double.valueOf(Double.parseDouble(x));
     } catch (Exception e) {
       e.printStackTrace();
     }
     return null;
   }
   
   private TreeMap<String, Acao> extraicotacao(String paginaHtml) {
     TreeMap<String, Acao> acaoMap = new TreeMap<String, Acao>();
     Scanner scan = new Scanner(paginaHtml);
     while (scan.hasNextLine()) {
       String line = scan.nextLine();
       
       if (line.length() > 50000) {
         String[] aux = line.split("</tr>");
         for (int i = 1; i < aux.length - 1; i++) {
           Acao acao = new Acao();
           String[] aux2 = aux[i].split("</td>");
           acao.setCode(aux2[0].replaceAll(".*cotPopup\\(\\'", "").replaceAll("'.*", ""));
           acao.setPreco(trata(aux2[1].replaceAll(".*\\>", "")));
           acao.setMinimo(trata(aux2[8].replaceAll(".*\\>", "")));
           acao.setMaximo(trata(aux2[9].replaceAll(".*\\>", "")));
           acao.setAbertura(trata(aux2[7].replaceAll(".*\\>", "")));
           acao.setFechamento(trata(aux2[5].replaceAll(".*\\>", "")));
           acao.setVolume(trata(aux2[3].replaceAll(".*\\>", "")));
           acao.setNegocios(Long.parseLong(aux2[4].replaceAll(".*\\>", "").replaceAll("\\.", "")));
           acao.setVariacao(trata(aux2[2].replaceAll(".*\\\"\\>", "").replaceAll("\\<.*", "")));
           try { acao.setDate(this.sdf.parse(aux2[6].replaceAll(".*\\>", "") + "/2008"));
           }
           catch (ParseException e) {
             acao.setDate(new Date());
           }
           acaoMap.put(acao.getCode(), acao);
         }
       }
     }
     scan.close();
     return acaoMap;
   }
   
 
 
   public void run()
   {
    /* Base base = new Base();
     for (;;) {
       if (Constantes.getInstance().EhDiaSemana()) {
         String paginaHtml = getCarteira();
         TreeMap<String, Acao> acaoMap = extraicotacao(paginaHtml);
         System.gc();
         base.insereCotacao(acaoMap);
       }
       try {
         Thread.sleep(this.delay);
       } catch (Exception e) { e.printStackTrace();
       }
     }*/
   }
 }

