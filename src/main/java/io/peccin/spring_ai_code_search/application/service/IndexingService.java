package io.peccin.spring_ai_code_search.application.service;

import io.peccin.spring_ai_code_search.application.port.in.IndexRepositoryUseCase;
import io.peccin.spring_ai_code_search.application.port.out.EmbeddingPort;
import io.peccin.spring_ai_code_search.application.port.out.SourceReaderPort;
import io.peccin.spring_ai_code_search.application.port.out.VectorStorePort;
import io.peccin.spring_ai_code_search.domain.model.CodeChunk;
import io.peccin.spring_ai_code_search.domain.model.SourceFile;
import io.peccin.spring_ai_code_search.domain.strategy.ChunkingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexingService implements IndexRepositoryUseCase {

    private final SourceReaderPort sourceReader;
    private final EmbeddingPort embeddingPort;
    private final VectorStorePort vectorStore;
    private final ChunkingStrategy chunkingStrategy;

    String logTag = "[IndexingService]";

    @Override
    public void index(Path repositoryPath) {
        log.info("{} [index] Starting index for repository: {}", logTag, repositoryPath);

        List<SourceFile> files = sourceReader.readAll(repositoryPath);
        log.info("{} [files] Found {} files to index", logTag, files.size());

        files.stream()
                .flatMap(file -> chunkingStrategy.chunk(file).stream())
                .forEach(this::embedAndStore);
        log.info("{} [index] Indexing complete for repository {}", logTag, repositoryPath);
    }

    private void embedAndStore(CodeChunk chunk) {
        log.debug("{} [embedAndStore] Embedding chunk: {}. {}", logTag, chunk.className(), chunk.methodName());

        List<Float> embedding = embeddingPort.embed(chunk.content());
        vectorStore.save(chunk, embedding);
    }

}
