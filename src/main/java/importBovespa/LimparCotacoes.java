 package importBovespa;
 
 import util.dao.Base;
 
 public class LimparCotacoes
 {
   public LimparCotacoes() {}
   
   public void Limpar()
   {
     if (!util.config.Constantes.getInstance().estaLendoArquivoCotacoes) {
       System.out.println("Iniciando Exclusao das Cotacoes Historicas...");
       Base base = new Base();
       base.limparCotacoes();
       System.out.println("Cotacoes Historicas excluidas com Sucesso");
     } else {
       System.out.println("Nao e permitido Excluir as Cotacoes Historicas durante sua Atualizacao!");
     }
   }
 }

