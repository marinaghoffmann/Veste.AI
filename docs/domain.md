# Descrição do domínio — Linguagem onipresente (Ubiquitous Language)

## 1. Glossário

| Termo | Definição |
|---|---|
| **Usuário** | Pessoa que possui uma conta no sistema e gerencia seu próprio guarda-roupa digital. |
| **Perfil** | Conjunto de informações do usuário: dados pessoais e preferências de estilo (cores, ocasiões mais frequentes). |
| **Peça** | Item individual de vestuário ou acessório cadastrado pelo usuário (ex: camisa, calça, sapato, bolsa). Possui categoria, cor e, opcionalmente, tags. |
| **Categoria** | Classificação de uma peça (ex: parte de cima, parte de baixo, calçado, acessório). |
| **Tag** | Rótulo livre atribuído pelo usuário a uma peça para facilitar organização e busca (ex: "trabalho", "verão"). |
| **Coleção** | Agrupamento de peças definido pelo usuário em torno de um tema ou finalidade (ex: "praia", "inverno"). |
| **Guarda-roupa** | Conjunto total de peças cadastradas por um usuário. |
| **Look** | Combinação de duas ou mais peças, montada manualmente pelo usuário ou sugerida pelo sistema, destinada a um uso específico. |
| **Look favorito** | Look marcado pelo usuário para acesso e reutilização rápida. |
| **Sugestão de look** | Combinação de peças gerada automaticamente pelo sistema, com base no guarda-roupa do usuário e, opcionalmente, em uma ocasião informada. |
| **Ocasião** | Contexto ou evento para o qual um look é destinado (ex: trabalho, festa, entrevista). |
| **Agendamento de look** | Associação entre um look e uma data específica no calendário do usuário. |
| **Disponibilidade da peça** | Estado que indica se uma peça está pronta para uso ou temporariamente indisponível (ex: na lavanderia). |
| **Compartilhamento** | Ação de tornar um look visível ou acessível fora da conta do usuário (via link ou exportação de imagem). |

## 2. Descrição narrativa do domínio

Um **usuário** cria uma conta no sistema e configura seu **perfil**, informando preferências de estilo que ajudam a personalizar sugestões futuras.

O núcleo do sistema é o **guarda-roupa** do usuário: um conjunto de **peças** cadastradas individualmente. Cada peça pertence a uma **categoria** e pode receber **tags** livres para facilitar a organização. O usuário pode agrupar peças relacionadas em **coleções**, e pode alterar a **disponibilidade da peça** quando ela não estiver pronta para uso.

A partir das peças do guarda-roupa, o usuário monta um **look**, combinando duas ou mais peças para uma finalidade específica. Um look pode ser marcado como **look favorito** para reutilização rápida, ou pode ser **agendado** para uma data específica através do **agendamento de look**.

Além da montagem manual, o sistema oferece **sugestão de look**: a partir do guarda-roupa cadastrado — e, opcionalmente, de uma **ocasião** informada pelo usuário — o sistema propõe combinações de peças automaticamente.

Por fim, o usuário pode realizar o **compartilhamento** de um look, tornando-o acessível fora de sua conta.

## 3. Próximos passos

Este documento é a base para os artefatos seguintes:
- Modelagem estratégica de subdomínios (Context Mapper / CML)
- Modelagem tática (agregados, entidades, objetos de valor)
- Cenários BDD, que devem reutilizar exatamente estes termos
