package com.vesteai.backend.infrastructure.ia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Dois papéis nesse controller:
 *
 * 1. POST /pecas/ia-preview — "webhook" chamado pela Lambda de IA na AWS,
 * assim que ela termina de analisar a foto (categoria/cor).
 * 2. GET /pecas/ia-preview/{key} — consultado pelo frontend em polling,
 * logo depois do upload da foto, até o resultado da IA chegar.
 */
@RestController
@RequestMapping("/api/pecas/ia-preview")
public class IaPreviewController {

    private final IaPreviewStore store;

    public IaPreviewController(IaPreviewStore store) {
        this.store = store;
    }

    @PostMapping
    public ResponseEntity<Void> receberResultadoDaIa(@RequestBody IaPreviewPayload payload) {
        store.salvar(payload.pecaKey(), payload);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<IaPreviewPayload> consultarResultado(@RequestParam String key) {
        return store.buscar(key)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
