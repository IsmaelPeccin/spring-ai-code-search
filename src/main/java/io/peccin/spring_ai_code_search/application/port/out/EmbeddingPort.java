package io.peccin.spring_ai_code_search.application.port.out;

import java.util.List;

public interface EmbeddingPort {
    List<Float> embed(String content);
}
