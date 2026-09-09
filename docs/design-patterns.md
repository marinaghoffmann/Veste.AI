# Padrões de projeto adotados

Convenção: os padrões vivem em `backend/src/main/java/com/vesteai/backend/domain/{strategy,template,event,decorator}`, pois encapsulam regras de negócio e não devem depender de Spring/JPA (a integração com o framework fica na camada de infraestrutura).

## 1. Strategy — algoritmos de sugestão de look intercambiáveis
- `domain/strategy/SugestaoLookStrategy.java` — contrato comum.
- `domain/strategy/SugestaoPorPecasStrategy.java` — sugestão a partir das peças disponíveis.
- `domain/strategy/SugestaoPorOcasiaoStrategy.java` — sugestão considerando a ocasião informada.
- `domain/service/SugestaoLookService.java` recebe a estratégia por injeção de construtor e delega a decisão, permitindo trocar o algoritmo sem alterar o serviço (base da história "IA sugere look" e "sugestão por ocasião").

## 2. Template Method — fluxo fixo de geração de look
- `domain/template/GeracaoLookTemplate.java` define o esqueleto fixo: filtrar peças disponíveis → selecionar peças (passo variável) → montar o look → hook pós-montagem.
- `domain/template/GeracaoLookPorOcasiaoTemplate.java` implementa o passo variável (`selecionarPecas`) e usa o hook para publicar o evento de look criado.

## 3. Observer — notificação de eventos do guarda-roupa
- `domain/event/LookEventListener.java` — contrato do observador (look criado, favoritado, agendado; peça indisponível).
- `domain/event/LookEventPublisher.java` — sujeito que mantém a lista de observadores e dispara as notificações.
- `infrastructure/notification/LogLookEventListener.java` — observador concreto (loga o evento); outros observadores (e-mail, push) podem ser adicionados sem alterar quem publica o evento.
- `infrastructure/config/DomainEventsConfig.java` liga os observadores ao publisher via Spring.

## 4. Decorator — atributos anexados a peças sem alterar a classe base
- `domain/decorator/PecaVisual.java` — contrato comum entre a peça "crua" e suas versões decoradas.
- `domain/decorator/PecaBase.java` — implementação base, sem atributos extras.
- `domain/decorator/PecaFavoritaDecorator.java`, `PecaIndisponivelDecorator.java`, `PecaEmColecaoDecorator.java` — anexam os atributos "favorito", "indisponível" e "coleção: X" combinando decoradores livremente, sem tocar em `Peca`.

## Padrões não adotados nesta fase (ficam como próximo incremento)
- **Iterator**: será avaliado ao implementar a navegação por coleções filtradas do guarda-roupa (história "Buscar/filtrar peças" e "Criar coleções").
- **Proxy**: candidato para cache/lazy-loading de imagens de peças ou para controlar acesso a um serviço externo de IA, quando a integração real com IA for implementada (história "IA reconhece categoria/cor automaticamente").
