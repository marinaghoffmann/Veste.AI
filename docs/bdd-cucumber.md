# Convenção de BDD/Cucumber

## Onde ficam os arquivos
- Arquivos `.feature`: `backend/src/test/resources/features/` (recurso de teste, não vai para o artefato de produção).
- Step definitions: `backend/src/test/java/com/vesteai/backend/bdd/` (glue path configurado no `RunCucumberTest`).
- Runner: `backend/src/test/java/com/vesteai/backend/bdd/RunCucumberTest.java` — precisa terminar em `Test` para ser descoberto pelo Surefire (não há plugin Failsafe configurado neste projeto).
- Contexto Spring para os steps: `backend/src/test/java/com/vesteai/backend/bdd/CucumberSpringConfiguration.java`.

## Convenção de nomenclatura
- Um arquivo `.feature` por história (ex.: `cadastro-de-conta.feature`, `filtrar-pecas.feature`), em `kebab-case`.
- Gherkin em português (`# language: pt`), reaproveitando exatamente os termos do glossário em `docs/domain.md`.
- Uma classe de steps por feature (ou por conjunto coeso de features), nomeada `<Feature>Steps.java`.

## Exemplo de referência
- `exemplo.feature` + `ExemploSteps.java` validam que o ambiente Cucumber está funcionando (Dado/Quando/Então básicos) e servem de modelo de estrutura para as próximas features.
- Cada história do backbone (`Release 1`, `Release 2`, `Backlog`) deve trazer seu próprio `.feature` + steps ao ser implementada.
