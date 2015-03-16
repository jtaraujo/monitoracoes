# Sistema de Monitoramento de Ações #

## Como instalar o Banco de Dados para a Aplicação ##

### Instruções passo a passo: ###

  1. Em http://www.postgresql.org/download você pode fazer o download do arquivo compactado contendo o instalador, ele possui aproximadamente 25 MB de tamanho.
  1. Descompacte o pacote e execute o arquivo postgresql-8.2.msi.Você vai perceber que há seis idiomas para sua escolha (Inglês, Alemão, Francês, Sueco, Turco e Português (Brasil)).
    * http://monitoracoes.googlecode.com/svn/trunk/docs/images/BancoDeDados/Passo_2_Selecao_Idioma.JPG
  1. Na janela seguinte, você terá as opções de instalação. Aconselho que os módulos de _Suporte para idioma nacional_ , _PL/Java_ e _Interfaces de usuário_ fiquem habilitados.
    * http://monitoracoes.googlecode.com/svn/trunk/docs/images/BancoDeDados/Passo_3_Opcoes_Instalacao.JPG
  1. Instale o PostgreSQL como um serviço no Windows habilitando o checkbox "Install as service". Nesta tela, preencha **somente** a senha e a confirmação da senha. **A informação de DOMÍNIO é específica de seu computador**. A senha padrão do programa de Monitoramento de Ações é **login**:_postgres_ e **senha**:_admin_.
    * http://monitoracoes.googlecode.com/svn/trunk/docs/images/BancoDeDados/Passo_4_Configuracao_Servico.JPG
  1. Na janela seguinte é pedida as opções relacionadas a que porta o servidor irá rodar, codificação e também qual será o super usuário. Este usuário é um usuário interno do Banco de Dados. Selecione a opção **Locale** como “Portugues, Brazil”.  A senha padrão do acesso ao Banco de Dados do programa de Monitoramento de Ações é **login**:_postgres_ e **senha**:_admin_.
    * http://monitoracoes.googlecode.com/svn/trunk/docs/images/BancoDeDados/Passo_5_Agrupamento_Banco_de_Dados.JPG
  1. Selecione "Próximo" na tela de seleção das Linguagens Procedurais, selecione conforme a figura abaixo. Lembrando que é importante estar selecionado a opção "PL/pgsql".
    * http://monitoracoes.googlecode.com/svn/trunk/docs/images/BancoDeDados/Passo_6_Habilitar_PL.JPG
  1. **Não é necessário instalar nenhum módulo adicional** para o programa funcionar. Na figura abaixo todas estão selecionadas, porém fica a seu critério o que você realmente vai utilizar.
    * http://monitoracoes.googlecode.com/svn/trunk/docs/images/BancoDeDados/Passo_7_Habilitar_Modulos.JPG
  1. Pronto, continue clicando em "_Próximo_" até concluir a Instalação.

[Voltar](PaginaInicial.md)