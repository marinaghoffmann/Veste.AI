package com.vesteai.backend.infrastructure.ia;

/**
 * Espelha exatamente o JSON que a Lambda envia:
 * {"peca_key": "...", "categoria": "...", "cor": "...", "confianca": 0-100}
 *
 * O nome do campo peca_key (snake_case) é mapeado pra pecaKey via
 * @JsonProperty, já que o Java usa camelCase.
 */
public record IaPreviewPayload(
        @com.fasterxml.jackson.annotation.JsonProperty("peca_key") String pecaKey,
        String categoria,
        String cor,
        double confianca
) {
}
