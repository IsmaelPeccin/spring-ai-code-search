package io.peccin.spring_ai_code_search.infrastructure.adapter.in.rest;

import io.peccin.spring_ai_code_search.application.port.in.IndexRepositoryUseCase;
import io.peccin.spring_ai_code_search.application.port.in.SearchCodeUseCase;
import io.peccin.spring_ai_code_search.domain.model.CodeChunk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CodeSearchController {

    private final IndexRepositoryUseCase indexRepositoryUseCase;
    private final SearchCodeUseCase  searchCodeUseCase;

    @PostMapping("/index")
    public ResponseEntity<IndexResponse> index(@RequestBody IndexRequest request) {
        log.info("Received index request for: {}", request.repositoryPath());
        indexRepositoryUseCase.index(Path.of(request.repositoryPath()));
        return ResponseEntity.accepted().body(new IndexResponse(
                "Indexing started for: " + request.repositoryPath()
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CodeChunkResponse>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int topK) {
        log.info("Received search request: '{}'", query);
        List<CodeChunk> results = searchCodeUseCase.search(query, topK);
        return ResponseEntity.ok(results.stream()
                .map(CodeChunkResponse::from)
                .toList());
    }

    record IndexRequest(String repositoryPath) {}

    record IndexResponse(String message) {}

    record CodeChunkResponse(
            String id,
            String className,
            String methodName,
            String filePath,
            String repositoryName,
            String content
    ) {
        static CodeChunkResponse from(CodeChunk chunk) {
            return new CodeChunkResponse(
                    chunk.id(),
                    chunk.className(),
                    chunk.methodName(),
                    chunk.filePath(),
                    chunk.repositoryName(),
                    chunk.content()
            );
        }
    }
}

