package io.peccin.spring_ai_code_search.infrastructure.adapter.out.chunking;

import io.peccin.spring_ai_code_search.domain.model.CodeChunk;
import io.peccin.spring_ai_code_search.domain.model.SourceFile;
import io.peccin.spring_ai_code_search.domain.strategy.ChunkingStrategy;

import java.util.List;
import java.util.UUID;

public class MethodChunkingStrategy implements ChunkingStrategy {
    @Override
    public List<CodeChunk> chunk(SourceFile file) {
        return List.of(
                new CodeChunk(
                        UUID.randomUUID().toString(),
                        file.content(),
                        "Unknown",
                        "Unknown",
                        file.path().toString(),
                        file.repositoryName()
                )
        );
    }
}
