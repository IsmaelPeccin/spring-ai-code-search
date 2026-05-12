package io.peccin.spring_ai_code_search.application.port.out;

import java.util.List;

public interface VectorStorePort {
    void save(CodeChunk chunk, List<Float> embedding);

    List<CodeChunk> findSimilar(List<Float> queryEmbedding);
}
