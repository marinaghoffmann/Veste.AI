**MAPA DE HISTÓRIAS → PLANO DE EXECUÇÃO**

**Guarda-roupa Digital**

*Todo o backbone do mapa de histórias transformado em tarefas de desenvolvimento, ordenadas para que nenhuma feature comece antes da base de que ela depende --- do cadastro até o backlog desejável. Cada história traz o integrante responsável, e cada release incorpora os requisitos técnicos do projeto (DDD, Arquitetura Limpa, padrões de projeto e testes BDD/Cucumber).*

*Convenção do projeto: toda história com dado de usuário é implementada de ponta a ponta (persistência real + API + interface + testes) --- nada de tela só de leitura ou mockada. As únicas exceções são histórias que são, por natureza, apenas de consulta (histórico, dashboard).*

**Fase 0 --- Fundação técnica & arquitetura PRÉ-REQUISITO**

*Nada do backbone pode ser construído sem isso primeiro: sem repositório, modelo de domínio e arquitetura definidos, nenhuma história do release 1 tem onde rodar.*

**0.0 Setup do projeto**

> [x] Definir stack (frontend, backend, banco de dados relacional, storage de imagens)
>
> [x] Criar repositório e estrutura de pastas (front/back)
>
> [x] Configurar ambiente de desenvolvimento (Docker/.env/scripts)
>
> [x] Configurar CI básico (lint + testes automatizados)
>
> [x] Definir design system / UI kit (paleta, tipografia, componentes base)
>
> [x] Configurar storage de imagens (upload de fotos das peças)

**0.1 Modelagem DDD (Domain-Driven Design)**

> [x] Nível preliminar: levantar visão de negócio e requisitos com especialistas do domínio
>
> [x] Nível estratégico: identificar bounded contexts e classificar subdomínios (core, support, generic)
>
> [x] Nível estratégico: modelar o(s) subdomínio(s) no Context Mapper (arquivo .cml)
>
> [x] Nível tático: definir agregados, entidades, objetos de valor e serviços de domínio
>
> [x] Nível operacional: implementar a camada de domínio em código a partir do modelo tático

**0.2 Arquitetura limpa & persistência**

> [x] Definir as camadas da Arquitetura Limpa (domínio, aplicação, infraestrutura, apresentação)
>
> [x] Garantir que as dependências apontem sempre para dentro (domínio isolado de infra e web)
>
> [x] Escolher e configurar o ORM da camada de persistência
>
> [x] Mapear as entidades de domínio para o banco relacional (mapeamento objeto-relacional)
>
> [x] Implementar a camada de apresentação web (rotas/controllers + views, ou API + frontend)
>
> [x] Modelar o banco de dados inicial (usuários, peças, looks) a partir do modelo tático de domínio

**0.3 Padrões de projeto --- adotar 4 ou mais**

> [x] Strategy --- algoritmos de sugestão de look intercambiáveis (por peças / por ocasião / substituição)
>
> [x] Template Method --- fluxo fixo de geração de sugestão de look, com passos variáveis por subclasse
>
> [x] Observer --- notificar interessados quando um look é agendado, uma peça fica indisponível ou é favoritada
>
> [x] Decorator --- anexar atributos a peças/looks sem alterar a classe base (favorito, indisponível, coleção)
>
> ☐ Iterator --- percorrer coleções de peças/looks de forma uniforme (guarda-roupa completo, filtrado, por coleção)
>
> ☐ Proxy --- cache/lazy-loading de imagens de peças, ou controle de acesso ao serviço externo de IA

*Escolher pelo menos 4 dos 6 padrões acima e documentar, no código e na entrega, onde e por que cada um foi aplicado.*

**0.4 BDD & Cucumber --- configuração inicial**

> [x] Configurar o Cucumber na stack escolhida
>
> [x] Definir convenção de arquivos .feature em Gherkin e organização de pastas
>
> [x] Escrever um cenário BDD de exemplo + step definitions de referência para o time seguir

**Release 1 --- Essencial (MVP) 6 HISTÓRIAS**

*Segue exatamente a ordem do backbone: cada atividade depende dos dados que a anterior cria --- não dá para buscar peças sem cadastrar peças, nem sugerir looks sem peças e looks existindo.*

**1 Cadastro & perfil --- Criar conta e login** *· Integrante 03*

> ☐ Modelar tabela de usuários (nome, e-mail, senha com hash)
>
> ☐ Endpoint de cadastro (POST /auth/register)
>
> ☐ Endpoint de login com token (POST /auth/login)
>
> ☐ Tela de cadastro com validação de e-mail/senha
>
> ☐ Tela de login
>
> ☐ Testes automatizados do fluxo de autenticação
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**2 Cadastrar peças --- Cadastro manual (foto, categoria, cor)** *· Integrante 03*

> ☐ Modelar tabela de peças (dono, foto, categoria, cor, estação)
>
> ☐ Endpoint de upload de foto
>
> ☐ Endpoint POST /pecas
>
> ☐ Formulário de cadastro (upload, categoria, seletor de cor)
>
> ☐ Validações de campos obrigatórios
>
> ☐ Testes de integração do fluxo de cadastro
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**3 Organizar guarda-roupa --- Buscar/filtrar peças** *· Integrante 05*

> ☐ Endpoint GET /pecas com filtros (categoria, cor, estação)
>
> ☐ Índices no banco para busca eficiente
>
> ☐ Componente de filtros na UI (chips/dropdowns)
>
> ☐ Grid de exibição das peças filtradas
>
> ☐ Testes combinando múltiplos filtros
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**4 Montar looks --- Montagem manual** *· Integrante 03*

> ☐ Modelar tabela de looks (dono, nome, peças associadas)
>
> ☐ Endpoint POST /looks
>
> ☐ Interface de seleção/combinação de peças
>
> ☐ Preview visual do look montado
>
> ☐ Testes do fluxo de montagem
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**5 IA & sugestões --- Sugestão de look com base nas peças** *· Integrante 01*

> ☐ Definir critério/algoritmo de sugestão inicial (candidato a Strategy/Template Method)
>
> ☐ Endpoint GET /looks/sugestao
>
> ☐ Integração com serviço de IA (API externa ou regras próprias)
>
> ☐ Tela de exibição da sugestão
>
> ☐ Testes com diferentes conjuntos de peças cadastradas
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**6 Planejar & compartilhar --- Agendar look para um dia** *· Integrante 05*

> ☐ Modelar tabela de agenda (dono, look, data)
>
> ☐ Endpoint POST /agenda
>
> ☐ Componente de calendário na UI
>
> ☐ Testes de agendamento e conflito de datas
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**Release 2 --- Importante 6 HISTÓRIAS**

*Refinamentos sobre a base do release 1 --- cada item aqui edita ou enriquece algo que já existe (perfil, peça, look), por isso só começa depois do essencial estar de pé.*

**1 Cadastro & perfil --- Editar perfil** *· Integrante 04*

> ☐ Endpoint PUT /perfil
>
> ☐ Tela de edição (nome, foto, dados pessoais)
>
> ☐ Validações de alteração de e-mail/senha
>
> ☐ Testes de atualização de perfil
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**2 Cadastrar peças --- Editar/excluir peça** *· Integrante 04*

> ☐ Endpoints PUT/DELETE /pecas/:id
>
> ☐ Tela de edição de peça existente
>
> ☐ Confirmação de exclusão
>
> ☐ Testes de edição e exclusão
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**3 Organizar guarda-roupa --- Criar coleções** *· Integrante 04*

> ☐ Modelar tabela de coleções e relação peça↔coleção
>
> ☐ CRUD de coleções (endpoints)
>
> ☐ Tela de criação/gestão de coleções (ex: verão, trabalho)
>
> ☐ Associar peças a uma ou mais coleções
>
> ☐ Testes de CRUD de coleções
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**4 Montar looks --- Salvar como favorito** *· Integrante 06*

> ☐ Flag/tabela de favoritos para looks
>
> ☐ Endpoints de favoritar/desfavoritar
>
> ☐ Botão de favoritar na UI
>
> ☐ Tela/filtro de looks favoritos
>
> ☐ Testes de favoritar/desfavoritar
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**5 IA & sugestões --- Sugestão por ocasião específica** *· Integrante 01*

> ☐ Definir lista de ocasiões (trabalho, festa, casual\...)
>
> ☐ Endpoint GET /looks/sugestao?ocasiao=
>
> ☐ Ajustar algoritmo/estratégia de IA para considerar ocasião (Strategy)
>
> ☐ Seletor de ocasião na UI
>
> ☐ Testes por ocasião
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**6 Planejar & compartilhar --- Compartilhar look (link/imagem)** *· Integrante 06*

> ☐ Geração de link público ou imagem exportável do look
>
> ☐ Renderização da imagem do look
>
> ☐ Botão de compartilhar (redes sociais/clipboard)
>
> ☐ Testes de geração e acesso ao link
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**Backlog --- Desejável 6 HISTÓRIAS**

*Ganhos incrementais sobre uma base já funcionando --- nenhum item aqui é pré-requisito de outro, então a ordem entre eles é livre.*

**1 Cadastro & perfil --- Definir preferências de estilo** *· Integrante 05*

> ☐ Modelar preferências (estilos, cores favoritas, tamanhos)
>
> ☐ Tela de onboarding/configuração de preferências
>
> ☐ Endpoint de salvar preferências
>
> ☐ Usar preferências como input futuro da IA
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**2 Cadastrar peças --- IA reconhece categoria/cor automaticamente** *· Integrante 02*

> ☐ Avaliar/integrar serviço de visão computacional
>
> ☐ Pipeline de processamento da foto no upload
>
> ☐ Pré-preencher categoria/cor no formulário
>
> ☐ Permitir correção manual pelo usuário
>
> ☐ Testes com fotos variadas
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**3 Organizar guarda-roupa --- Marcar peça como indisponível** *· Integrante 06*

> ☐ Campo de status na peça (disponível/na lavanderia)
>
> ☐ Endpoint PATCH /pecas/:id/status
>
> ☐ Toggle de status na UI da peça
>
> ☐ Excluir peças indisponíveis das sugestões automáticas
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**4 Montar looks --- Ver histórico de looks usados** *· não atribuído · extra/backlog*

> ☐ Registrar data de uso ao usar um look (candidato a Observer)
>
> ☐ Endpoint GET /looks/historico
>
> ☐ Tela de linha do tempo/histórico
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**5 IA & sugestões --- Sugestão de substituição de peça** *· Integrante 02*

> ☐ Endpoint de substituição por similaridade
>
> ☐ Lógica de similaridade entre peças (categoria/cor)
>
> ☐ UI de sugestão de substituição dentro do look
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

**6 Planejar & compartilhar --- Dashboard de peças mais/menos usadas** *· não atribuído · extra/backlog*

> ☐ Query agregada de uso de peças
>
> ☐ Endpoint GET /dashboard/uso
>
> ☐ Componente de gráfico (mais/menos usadas)
>
> ☐ Escrever cenário BDD (Gherkin) da história
>
> ☐ Automatizar o cenário com Cucumber

*\"Ver histórico de looks usados\" e \"Dashboard de peças mais/menos usadas\" ficaram de fora da distribuição obrigatória entre os integrantes --- servem como reforço de portfólio individual ou ficam só no backlog do projeto.*

**Divisão da equipe 6 INTEGRANTES**

*Cota por integrante: 2 histórias altas, OU 1 média + 2 baixas, OU 4 baixas. Com o mapa atual, os 6 integrantes cobrem as 4 altas, as 4 médias e 8 das 10 baixas do catálogo.*

*Atenção: \"alta/média/baixa\" é o critério de esforço que a equipe usou para distribuir a carga de trabalho entre as 6 pessoas --- é um eixo diferente de \"essencial/importante/desejável\" (a prioridade de release do backbone) e os dois não coincidem. Por exemplo, uma história \"alta\" pode estar no backlog (desejável), e uma \"baixa\" pode ser essencial (release 1). Por isso cada item abaixo mostra as duas informações lado a lado, e cada história nas seções anteriores traz o nome do integrante responsável.*

+------------------------------------------------------+-------------------------------------------------------+-------------------------------------------------------+
| **Integrante 01**                                    | **Integrante 02**                                     | **Integrante 03**                                     |
|                                                      |                                                       |                                                       |
| *2 altas (esforço da equipe)*                        | *2 altas (esforço da equipe)*                         | *1 média + 2 baixas (esforço da equipe)*              |
|                                                      |                                                       |                                                       |
| > ● IA sugere um look com base nas peças cadastradas | > ● IA reconhece categoria/cor automaticamente        | > ● Montar look manualmente                           |
| >                                                    | >                                                     | >                                                     |
| > **esforço: ALTA** *· release: Essencial · R1*      | > **esforço: ALTA** *· release: Desejável · backlog*  | > **esforço: MÉDIA** *· release: Essencial · R1*      |
| >                                                    | >                                                     | >                                                     |
| > ● IA sugere look para ocasião específica           | > ● IA sugere substituição de peça em um look         | > ● Criar conta e login                               |
| >                                                    | >                                                     | >                                                     |
| > **esforço: ALTA** *· release: Importante · R2*     | > **esforço: ALTA** *· release: Desejável · backlog*  | > **esforço: BAIXA** *· release: Essencial · R1*      |
|                                                      |                                                       | >                                                     |
|                                                      |                                                       | > ● Cadastrar peça manualmente                        |
|                                                      |                                                       | >                                                     |
|                                                      |                                                       | > **esforço: BAIXA** *· release: Essencial · R1*      |
+------------------------------------------------------+-------------------------------------------------------+-------------------------------------------------------+
| **Integrante 04**                                    | **Integrante 05**                                     | **Integrante 06**                                     |
|                                                      |                                                       |                                                       |
| *1 média + 2 baixas (esforço da equipe)*             | *1 média + 2 baixas (esforço da equipe)*              | *1 média + 2 baixas (esforço da equipe)*              |
|                                                      |                                                       |                                                       |
| > ● Criar coleções (ex: verão, trabalho)             | > ● Agendar look do dia (calendário)                  | > ● Compartilhar look (link/imagem)                   |
| >                                                    | >                                                     | >                                                     |
| > **esforço: MÉDIA** *· release: Importante · R2*    | > **esforço: MÉDIA** *· release: Essencial · R1*      | > **esforço: MÉDIA** *· release: Importante · R2*     |
| >                                                    | >                                                     | >                                                     |
| > ● Editar perfil                                    | > ● Buscar/filtrar peças                              | > ● Salvar look como favorito                         |
| >                                                    | >                                                     | >                                                     |
| > **esforço: BAIXA** *· release: Importante · R2*    | > **esforço: BAIXA** *· release: Essencial · R1*      | > **esforço: BAIXA** *· release: Importante · R2*     |
| >                                                    | >                                                     | >                                                     |
| > ● Editar/excluir peça                              | > ● Definir preferências de estilo                    | > ● Marcar peça como indisponível                     |
| >                                                    | >                                                     | >                                                     |
| > **esforço: BAIXA** *· release: Importante · R2*    | > **esforço: BAIXA** *· release: Desejável · backlog* | > **esforço: BAIXA** *· release: Desejável · backlog* |
+------------------------------------------------------+-------------------------------------------------------+-------------------------------------------------------+

*As 18 histórias do catálogo = 4 altas + 4 médias + 10 baixas. 8 das 10 baixas foram distribuídas acima; \"Ver histórico de looks usados\" e \"Dashboard de peças mais/menos usadas\" (ambas baixas, ambas desejável/backlog) ficaram de fora da cota obrigatória --- servem como reforço de portfólio individual, ou algum integrante pode absorvê-las como variante \"4 baixas\" no lugar de \"1 média + 2 baixas\".*

**Fase final --- QA, deploy & documentação FECHAMENTO**

*Roda ao final de cada release --- pelo menos uma vez depois do release 1, para validar o MVP antes de avançar para o release 2.*

**F Validação e entrega**

> ☐ Testes de integração ponta a ponta
>
> ☐ Testes de usabilidade com usuários reais
>
> ☐ Revisão de acessibilidade e responsividade
>
> ☐ Revisar cobertura dos cenários BDD/Cucumber de todas as histórias
>
> ☐ Revisar a aplicação dos padrões de projeto adotados (mínimo 4) e documentar onde cada um foi usado
>
> ☐ Validar aderência à Arquitetura Limpa e ao modelo DDD (Context Mapper atualizado)
>
> ☐ Preparar ambiente de produção (hosting, banco relacional, domínio)
>
> ☐ Deploy da versão 1 (release essencial)
>
> ☐ Documentação técnica (README, documentação de API)
>
> ☐ Deploy da versão 2 (release importante)
>
> ☐ Retrospectiva e priorização do backlog

*Baseado no mapa de histórias do usuário --- Guarda-roupa digital.*
