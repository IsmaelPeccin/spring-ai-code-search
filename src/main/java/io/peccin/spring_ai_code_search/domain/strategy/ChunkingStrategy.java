package io.peccin.spring_ai_code_search.domain.strategy;

import io.peccin.spring_ai_code_search.domain.model.CodeChunk;
import io.peccin.spring_ai_code_search.domain.model.SourceFile;

import java.util.List;

public interface ChunkingStrategy {
    List<CodeChunk> chunk(SourceFile file);
}
