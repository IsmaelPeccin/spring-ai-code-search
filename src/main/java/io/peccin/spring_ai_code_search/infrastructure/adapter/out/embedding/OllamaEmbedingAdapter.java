package io.peccin.spring_ai_code_search.infrastructure.adapter.out.embedding;

import io.peccin.spring_ai_code_search.application.port.out.EmbeddingPort;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.ArrayList;
import java.util.List;

public class OllamaEmbedingAdapter implements EmbeddingPort {

    private final EmbeddingModel embeddingModel;

    @Override
    public List<Float> embed(String content) {
        log.debug("[OllamaEmbedingAdapter] [embed] Generating embedding for content of length: {}", content);
        float[] result = embedding = new ArrayList<>();
        List<Float> embedding = new ArrayList<>();
        for (float f : result) {
            embedding.add(f);
        }

        return embedding;
    }
}
