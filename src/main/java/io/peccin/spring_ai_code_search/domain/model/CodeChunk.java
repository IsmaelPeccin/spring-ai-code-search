package io.peccin.spring_ai_code_search.domain.model;

public record CodeChunk(
        String id,
        String content,
        String className,
        String methodName,
        String filePath,
        String repositoryName
) {
}
