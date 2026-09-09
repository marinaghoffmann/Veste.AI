# Veste.AI - Guarda-Roupa Digital

Sistema web para gerenciamento de guarda-roupa virtual, criação de looks e recomendações inteligentes de vestuário.

## Stack Técnica
- **Backend:** Java 17/21, Spring Boot 3.3.3, Spring Data JPA, Lombok
- **Frontend:** React 19 + TypeScript + Vite, testes com Vitest/Testing Library
- **Banco de Dados:** PostgreSQL (Produção) / H2 (Desenvolvimento local)
- **Storage de imagens:** disco local em `uploads/pecas` (via `ImageStorageService`), plugável para storage em nuvem no futuro
- **Testes:** BDD com Cucumber + JUnit 5 (backend), Vitest (frontend)
- **Modelagem:** Domain-Driven Design (DDD) com Context Mapper (.cml) — ver `docs/domain.md` e `docs/model.cml`
- **Padrões de projeto:** ver `docs/design-patterns.md`
- **Design system:** ver `docs/design-system.md`

## Como rodar o ambiente de desenvolvimento

```bash
# Backend (usa H2 em memória por padrão, perfil "dev")
cd backend
./mvnw spring-boot:run

# Frontend
cd frontend
npm install
npm run dev

# Banco Postgres local (opcional, para testar o perfil "prod")
docker compose up -d postgres
```

Copie `.env.example` para `.env` e ajuste as variáveis se for usar o Postgres via Docker.

## Declaração sobre Uso de IA Generativa
- [ ] O grupo declara que **NÃO** utilizou assistentes de IA generativa no desenvolvimento deste projeto.
- [X] O grupo utilizou ferramentas de IA generativa (ChatGPT/Gemini/Copilot) como suporte no desenvolvimento.
  - **Objetivo:** Auxiliar no planejamento da arquitetura limpa, estrutura tática do DDD e scripts do Context Mapper.
  - **Registro de interações:** Os prompts e históricos de auxílio estão arquivados em `docs/ai-prompts/`.
