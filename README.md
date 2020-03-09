# mAgent
A necessidade de realizar operações financeiras em mobilidade tem-se manifestado nos últimos anos com o desenvolvimento da tecnologia e dos modos de vida em movimento. Este fenômeno deu origem a várias aplicações que permitissem realizar este tipo de tarefas, com segurança, e em qualquer lugar.
Com isto em mente, o projecto visa desenvolver uma aplicação para tablets Android, que permita executar de forma segura as tarefas relevantes de uma entidade financeira de forma autónoma e em mobilidade, com georreferenciação das operações executadas.

A app tem semelhanças com as aplicações de “homebanking” como é exemplo da app da Caixa APPs Oficiais da Caixa - Acesso imediato à Caixa. Nesta app é possível realizar tarefas comuns à app mAgent, como por exemplo: transferências, levantamentos, pagamentos ou visualização de movimentos. Outro serviço que se enquadra com a app mAgent poderá ser o Balcão Móvel BPI, que permite realizar tarefas semelhantes às da aplicação a desenvolver.

# Cenários
- Cenário Principal: (Assumido que o utilizador realiza o login com sucesso, através da autenticação biométrica) O utilizador carrega no botão “Consultar”, disponível no menu principal, e visualiza a informação relativa aos seus últimos movimentos. De seguida, retorna ao menu principal e carrega no botão “Transferir”. O utilizador preenche a informação necessária à realização da transferência e prime o botão “Realizar Transferência”. Feita a transferência com sucesso, o utilizador carrega no botão “Consultar” e visualiza a mudança no seu saldo. 

Cenários Secundários: 
- Cenário relativo à informação de clientes (ficha de cliente, conta de cliente). O utilizador carrega em Minha Conta e consegue visualizar e alterar a respetiva informação.  
- Cenário relativo à abertura/fecho de caixa, contexto da agência móvel(?), e fluxos de caixa. O utilizador carrega em Abrir Caixa, dando início ao tracking das transições que vão ocorrer até ao momento que o utilizador feche a caixa. O utilizador realiza algumas tarefas, e de seguida fecha a caixa, ao carregar no botão Fechar Caixa. Ao fechar a Caixa aparece no ecrã a possibilidade de visualizar uma tabela com a informação sobre o fluxo de caixa. O utilizador carrega em visualizar fluxo de caixa.

# Plano de trabalho
- Fase 1 (cerca de 1 semana)
  - Esboço dos ecrãs
- Fase 2 (cerca de 2 semanas)
  - Elaboração de mockups dos ecrãs
- Fase 3 (cerca de 4 semanas)
  - Implementação das funcionalidades de suporte da aplicação
    - Login/Logout
    - Estabelecimento de sessão
    - Bloqueio automático
    - Desbloqueio por PIN e/ou Biometria
    - Configurações
- Fase 4 (cerca de 3 semanas)
  - Implementação de funcionalidades básicas
    - Página inicial com o Estado/Contexto da Agência móvel
    - Abertura de Caixa
    - Fecho de Caixa
    - Relatório de fluxos de caixa
- Fase 5 (cerca de 3 semanas)
  - Implementação de funcionalidades mínimas de negócio
    - Depósitos
    - Levantamentos
    - Transferências entre contas
    - Informação de conta
- Fase 6 (cerca de 3 semanas)
  - Implementação de funcionalidades mínimas de negócio
    - Criação de ficha/conta de cliente.
    - Informação de cliente

