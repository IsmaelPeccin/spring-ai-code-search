package io.peccin.spring_ai_code_search.infrastructure.adapter.out.vectorstore;

import io.peccin.spring_ai_code_search.application.port.out.VectorStorePort;
import io.peccin.spring_ai_code_search.domain.model.CodeChunk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PgVectorStoreAdapter implements VectorStorePort {
    private final VectorStore vectorStore;

    @Override
    public void save(CodeChunk chunk, List<Float> embedding) {
        log.debug("[PgVectorStoreAdapter] [save] Saving chunk: {}", chunk.id());

        Document document = new Document(
                chunk.id(),
                chunk.content(),
                Map.of(
                        "className", chunk.className(),
                        "methodName", chunk.methodName(),
                        "filePath", chunk.filePath(),
                        "repositoryName", chunk.repositoryName()
                )
        );
        vectorStore.add(List.of(document));
    }

    @Override
    public List<CodeChunk> findSimilar(List<Float> queryEmbedding, int topK) {
        log.debug("Searching for {} similar chunks", topK);

        return vectorStore.similaritySearch(
                        SearchRequest.builder()
                                .topK(topK)
                                .build()
                )
                .stream()
                .map(this::toCodeChunk)
                .toList();
    }

    private CodeChunk toCodeChunk(Document document) {
        Map<String, Object> meta = document.getMetadata();
        return new CodeChunk(
                document.getId(),
                document.getText(),
                (String) meta.getOrDefault("className", "Unknown"),
                (String) meta.getOrDefault("methodName", "Unknown"),
                (String) meta.getOrDefault("filePath", "Unknown"),
                (String) meta.getOrDefault("repositoryName", "Unknown")
        );
    }


}
