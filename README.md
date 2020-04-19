![University](https://www.iade.europeia.pt/hs-fs/hubfs/IADE-SITE/static/ue-iade-h75.png?width=444&name=ue-iade-h75.png)

### Degree in Computer Engineering | School Year 2019/2020 


Student Name | Email | Student Number
------------ | ------------- | -------------
Manuel Beijinho | manuel.beijinho@gmail.com | 50037192

Professors | Email 
------------ | ------------- 
Jacinto Estima | jacinto.estima@universidadeeuropeia.pt 
José Vasconcelos | jose.vasconcelos@universidadeeuropeia.pt

Empresa |
--------|
AgileFactor

# mAgent
A necessidade de realizar operações financeiras em mobilidade tem-se manifestado nos últimos anos com o desenvolvimento da tecnologia e dos modos de vida em movimento, bem como pela falta de infraestruturas para este efeito em determinadas regiões do mundo. Estes fenômenos deram origem a várias aplicações que permitissem realizar este tipo de tarefas, com segurança, e em qualquer lugar.
Com isto em mente, o projecto visa desenvolver uma aplicação para tablets Android, que permita executar de forma segura as tarefas relevantes de uma entidade financeira de forma autónoma e em mobilidade, com georreferenciação das operações executadas.

Alguns exemplos de aplicações concorrentes são: CB Bank, digipay, Emerico e BPC.

# Cenários
- Cenário Principal: (Assumido que o utilizador realiza o login com sucesso, através da autenticação PIN ou biométrica)
  1. O agente preenche a informação sobre a caixa.
  2. O agente prime "Open Shift".
  3. A aplicação mostra a informação sobre a caixa do agente.
  4. De seguida, navega para "Client" e identifica a conta do cliente.
  5. O agente escolhe a operação "Deposits".
  6. O agente preenche o montante.
  7. O agente prime "Deposit".
  8. O agente introduz o PIN de segurança.

- Cenários Secundários: (Assumido que o utilizador realiza o login com sucesso, através da autenticação PIN ou biométrica)
  - 1
     1. O agente prime "Transfers"
     2. O agente preenche os campos com a informação de ambas as contas dos terceiros.
     3. O agente insere o montante.
     4. O agente prime "Transfer".
     5. O agente introduz o PIN de segurança.
 
  - 2
      1. O agente prime "Client"
      2. O agente preenche os campos para identificar a conta do cliente.
      3. O agente escolhe a conta pretendida.
      4. Pressiona "Check balance".
      5. Introduz o PIN de segurança
      6. A aplicação mostra o saldo e os movimentos da conta requisitada.

# Personas
- Agente: Individuo que representa a entidade financeira.

# Functional Requirements
![Functional Requirements](https://github.com/manuelbpc/mAgent/blob/master/3rd%20Delivery/FR_NFR_Updated/FR.png)

# Non Functional Requirements
![Non Functional Requirements](https://github.com/manuelbpc/mAgent/blob/master/3rd%20Delivery/FR_NFR_Updated/NFR.png)

# Mockups e interfaces
![Mockups](https://github.com/manuelbpc/mAgent/blob/master/2nd%20Delivery/Interfaces/mixed_interfacev2.jpg)


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
- Fase 4 (cerca de 3 semanas)
  - Implementação de funcionalidades básicas
    - Página inicial com o Estado/Contexto da Agência móvel
    - Abertura de Caixa
    - Fecho de Caixa
    - Relatório de fluxos de caixa (opcional)
- Fase 5 (cerca de 3 semanas)
  - Implementação de funcionalidades mínimas de negócio
    - Depósitos
    - Levantamentos
    - Transferências entre contas
    - Informação de conta
- Fase 6 (cerca de 3 semanas)
   - Debugging
   - Melhoria de código
    

