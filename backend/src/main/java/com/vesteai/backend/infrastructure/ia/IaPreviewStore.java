package com.vesteai.backend.infrastructure.ia;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Guarda os resultados da IA em memória, só até o frontend consultar (ou
 * até expirar). Não precisa de banco/tabela pra isso — é um resultado
 * transitório, só serve pra "ponte" entre a Lambda (assíncrona) e o
 * polling do frontend.
 *
 * Se a aplicação rodar em mais de uma instância (múltiplos pods/servidores),
 * isso pararia de funcionar (cada instância teria seu próprio mapa) — nesse
 * caso trocar por Redis. Pra esse projeto acadêmico, memória local resolve.
 */
@Component
public class IaPreviewStore {

    private static final long EXPIRACAO_MINUTOS = 10;

    private record Entrada(IaPreviewPayload payload, Instant salvoEm) {
    }

    private final Map<String, Entrada> resultados = new ConcurrentHashMap<>();

    public void salvar(String pecaKey, IaPreviewPayload payload) {
        resultados.put(pecaKey, new Entrada(payload, Instant.now()));
    }

    public Optional<IaPreviewPayload> buscar(String pecaKey) {
        Entrada entrada = resultados.get(pecaKey);
        if (entrada == null) {
            return Optional.empty();
        }
        if (Instant.now().isAfter(entrada.salvoEm().plusSeconds(EXPIRACAO_MINUTOS * 60))) {
            resultados.remove(pecaKey);
            return Optional.empty();
        }
        return Optional.of(entrada.payload());
    }
}
