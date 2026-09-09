# language: pt
Funcionalidade: Criar conta e login
  Como visitante do Veste.AI
  Quero criar uma conta e depois entrar com ela
  Para acessar meu guarda-roupa digital

  Cenário: Cadastro de conta com sucesso
    Dado que não existe conta cadastrada com o e-mail "julia.mendes@example.com"
    Quando eu me cadastro com nome "Julia Mendes", e-mail "julia.mendes@example.com" e senha "senhaForte123"
    Então o cadastro deve ser aceito

  Cenário: Login com credenciais válidas
    Dado que existe uma conta cadastrada com e-mail "pedro.alves@example.com" e senha "senhaForte123"
    Quando eu faço login com e-mail "pedro.alves@example.com" e senha "senhaForte123"
    Então devo receber um token de acesso

  Cenário: Login com senha incorreta
    Dado que existe uma conta cadastrada com e-mail "renata.costa@example.com" e senha "senhaForte123"
    Quando eu faço login com e-mail "renata.costa@example.com" e senha "senhaTotalmenteErrada"
    Então o login deve ser recusado
