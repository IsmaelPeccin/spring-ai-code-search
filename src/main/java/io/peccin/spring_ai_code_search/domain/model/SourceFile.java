package io.peccin.spring_ai_code_search.domain.model;

import java.nio.file.Path;

public record SourceFile(
        Path path,
        String content,
        String repositoryName
) {
}
