 package util.config;
 
 import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
 
 public class Constantes
 {
   public float intervaloColetor;
   public float intervaloMonitor;
   public int largura;
   public int comprimento;
   public String host;
   public String hostibov;
   public String hostPlanilha;
   public String arqImportacao;
   public String arqPlanilha;
   public String email;
   public String senha;
   public String destino;
   public String loginbd;
   public String senhabd;
   public int limiarnegocios;
   public Date data_atual;
   public int media_menor;
   public int media_maior;
   public int filtro_min_negocios;
   public float variacao_gap_abertura;
   public float multiplicador_volume;
   public boolean estaLendoArquivoCotacoes;
   private static final Constantes SINGLETON = new Constantes();
   
   public static Constantes getInstance() { return SINGLETON; }
   
   private Constantes()
   {
     System.out.println("Carregando configuracao...");
     String ano = new SimpleDateFormat("yyyy", 
       new java.util.Locale("pt", "br")).format(new Date());
     
     String configFile = new String("InvestSync.conf");
     Properties config = new Properties();
     
     this.estaLendoArquivoCotacoes = false;
     try {
       ClassLoader loader = Thread.currentThread().getContextClassLoader();
       InputStream resourceStream = loader.getResourceAsStream(configFile);	 
       config.load(resourceStream);
       
       this.intervaloColetor = Float.parseFloat(config.getProperty("intervaloColetor", "300"));
       this.intervaloMonitor = Float.parseFloat(config.getProperty("intervaloMonitor", "15"));
       this.largura = Integer.parseInt(config.getProperty("largura", "900"));
       this.comprimento = Integer.parseInt(config.getProperty("comprimento", "600"));
       this.host = config.getProperty("host", "http://custom.infomoney.com.br/investimentos/ferramentas/carteiras/acompanhamento/carteira.asp");
       this.hostibov = config.getProperty("hostibov", "http://web.infomoney.com.br//investimentos/acoes/cotacoes/ibrx100/");
       this.hostPlanilha = config.getProperty("hostPlanilha", "https://www.agorainvest.com.br/centro/analise_plan_spinnex.asp");
       this.arqImportacao = config.getProperty("arquivoImportacao", "COTAHIST_A" + ano + ".TXT");
       this.arqPlanilha = config.getProperty("arqPlanilha", "analise_plan_spinnex.asp");
       this.email = config.getProperty("email", "chimpamicose@gmail.com");
       this.senha = config.getProperty("senha", "meu1milhao");
       this.destino = config.getProperty("destino", "http://custom.infomoney.com.br/investimentos/ferramentas/carteiras/acompanhamento/carteira.asp");
       this.loginbd = config.getProperty("loginbd", "cotacoes");
       this.senhabd = config.getProperty("senhabd", "cotacoes");
       this.limiarnegocios = Integer.parseInt(config.getProperty("limiarnegocios", "50"));
       
       this.data_atual = new Date();
       this.media_menor = Integer.parseInt(config.getProperty("media_menor", "3"));
       this.media_maior = Integer.parseInt(config.getProperty("media_maior", "20"));
       this.filtro_min_negocios = Integer.parseInt(config.getProperty("filtro_min_negocios", "10"));
       this.variacao_gap_abertura = Float.parseFloat(config.getProperty("variacao_gap_abertura", "4"));
       this.multiplicador_volume = Float.parseFloat(config.getProperty("multiplicador_volume", "4"));
     }
     catch (FileNotFoundException e1) {
       System.out.println("ATENCAO: Arquivo de configuracao: InvestSync.conf nao encontrado. Assumindo valores default.");
       
       this.intervaloColetor = 300.0F;
       this.intervaloMonitor = 15.0F;
       this.largura = 800;
       this.comprimento = 600;
       this.host = "http://custom.infomoney.com.br/investimentos/ferramentas/carteiras/acompanhamento/carteira.asp";
       this.hostibov = "http://web.infomoney.com.br//investimentos/acoes/cotacoes/ibrx100/";
       this.hostPlanilha = "https://www.agorainvest.com.br/centro/analise_plan_spinnex.asp";
       this.arqImportacao = ("COTAHIST_A" + ano + ".TXT");
       this.arqPlanilha = "analise_plan_spinnex.asp";
       this.email = "chimpamicose@gmail.com";
       this.senha = "meu1milhao";
       this.destino = "http://custom.infomoney.com.br/investimentos/ferramentas/carteiras/acompanhamento/carteira.asp";
       this.loginbd = "postgres";
       this.senhabd = "admin";
       this.limiarnegocios = 50;
       
       this.data_atual = new Date();
       this.media_menor = 3;
       this.media_maior = 20;
       this.filtro_min_negocios = 10;
       this.variacao_gap_abertura = 4.0F;
       this.multiplicador_volume = 4.0F;
     } catch (IOException e1) {
       System.out.println("ERRO ao ler arquivo de configuracao");
       e1.printStackTrace();
     }
     System.out.println("Configuracoes carregadas com Sucesso!");
   }
   
   public boolean EhDiaSemana() {
     Calendar cal = new java.util.GregorianCalendar();
     cal.setTime(new Date());
     switch (cal.get(Calendar.DAY_OF_WEEK)) {
     case Calendar.SUNDAY: 
     case Calendar.SATURDAY: 
       return false;
     }
     
    return true;
   }
 }

