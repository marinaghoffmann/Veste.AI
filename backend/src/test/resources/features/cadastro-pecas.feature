# language: pt
Funcionalidade: Cadastrar peças
  Como usuário do Veste.AI
  Quero cadastrar peças do meu guarda-roupa
  Para depois montar looks com elas

  Cenário: Cadastro de peça com sucesso
    Dado que estou autenticado como "maria.pecas@example.com"
    E que enviei a foto "camisa.png" da peça
    Quando eu cadastro uma peça com nome "Camisa social", categoria "PARTE_DE_CIMA", cor "branco" e estação "TODAS"
    Então a peça deve ser cadastrada com sucesso

  Cenário: Cadastro de peça sem foto é recusado
    Dado que estou autenticado como "joao.pecas@example.com"
    Quando eu tento cadastrar uma peça sem foto com nome "Tênis", categoria "CALCADO", cor "preto" e estação "TODAS"
    Então o cadastro deve ser recusado
