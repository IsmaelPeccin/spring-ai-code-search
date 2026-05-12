package io.peccin.spring_ai_code_search.application.port.in;

import io.peccin.spring_ai_code_search.domain.model.CodeChunk;

import java.util.List;

public interface SearchCodeUseCase {
    List<CodeChunk> search(String query, int topK);
}
