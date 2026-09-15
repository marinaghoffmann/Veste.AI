package com.vesteai.backend.presentation.dto;

/**
 * @param fotoUrl URL local da foto (como já era antes) — usado pra exibir
 *                a pré-visualização e salvar a peça.
 * @param iaKey   key do objeto no S3 (ex: "pecas/1.png"), usada pelo
 *                frontend pra consultar o resultado da IA via polling em
 *                GET /api/pecas/ia-preview/{key}. Pode vir null se o
 *                upload pro S3 falhou — nesse caso o frontend simplesmente
 *                não tenta o polling e o usuário preenche categoria/cor
 *                manualmente.
 */
public record FotoResponse(String fotoUrl, String iaKey) {
}
