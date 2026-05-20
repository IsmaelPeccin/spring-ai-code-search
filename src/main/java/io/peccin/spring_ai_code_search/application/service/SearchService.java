package io.peccin.spring_ai_code_search.application.service;

import io.peccin.spring_ai_code_search.application.port.in.SearchCodeUseCase;
import io.peccin.spring_ai_code_search.application.port.out.EmbeddingPort;
import io.peccin.spring_ai_code_search.application.port.out.VectorStorePort;
import io.peccin.spring_ai_code_search.domain.model.CodeChunk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService implements SearchCodeUseCase {
    private final EmbeddingPort embeddingPort;
    private final VectorStorePort vectorStore;
    String logTag = "[SearchService]"

    @Override
    public List<CodeChunk> search(String query, int topK) {
        log.info("{} [search] Searching for: '{}'", logTag, query);
        List<Float> queryEmbedding = embeddingPort.embed(query);
        List<CodeChunk> results = vectorStore.findSimilar(queryEmbedding, topK);
        log.info("{}, [search] Found {} results", logTag, results.size());
        return results;
    }
}
