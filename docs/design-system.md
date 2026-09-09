# Design System — Veste.AI

## Paleta de cores

| Token | Valor | Uso |
|---|---|---|
| `--color-bg` | `#FAF7F2` | Fundo geral (tom "linho") |
| `--color-surface` | `#FFFFFF` | Cards, painéis |
| `--color-primary` | `#2D2A26` | Texto principal, botões primários |
| `--color-accent` | `#C97B5B` | Terracota — ações de destaque, favoritos |
| `--color-accent-soft` | `#F1DCCB` | Fundos de destaque suave, tags |
| `--color-success` | `#4C8B6B` | Disponibilidade, confirmações |
| `--color-danger` | `#B5474A` | Indisponibilidade, exclusão |
| `--color-border` | `#E4DCD2` | Bordas e divisores |
| `--color-text-muted` | `#7A7368` | Texto secundário |

## Tipografia

- Família: `"Inter", system-ui, sans-serif` para texto de interface; `"Fraunces", serif` para títulos de destaque.
- Escala: `12 / 14 / 16 / 20 / 28 / 36` px (`--font-size-xs` … `--font-size-2xl`).
- Peso: 400 (texto), 600 (ênfase/labels), 700 (títulos).

## Componentes base

- **Button**: primário (`--color-primary`), secundário (outline), destrutivo (`--color-danger`).
- **Card**: peça/look — imagem 1:1, título, chips de categoria/cor.
- **Chip/Tag**: usado em categoria, cor, estação, coleção.
- **Input/Select**: formulários de cadastro (peça, perfil).
- **Badge de status**: disponível / indisponível / favorito.

Os tokens de cor e tipografia vivem em `frontend/src/styles/tokens.css` e devem ser a única fonte de valores de estilo (evitar cores/tamanhos "mágicos" espalhados nos componentes).
