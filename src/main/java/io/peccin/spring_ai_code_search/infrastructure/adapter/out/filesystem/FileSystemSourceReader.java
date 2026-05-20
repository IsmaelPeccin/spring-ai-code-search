package io.peccin.spring_ai_code_search.infrastructure.adapter.out.filesystem;

import io.peccin.spring_ai_code_search.application.port.out.SourceReaderPort;
import io.peccin.spring_ai_code_search.domain.model.SourceFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class FileSystemSourceReader implements SourceReaderPort {

    private static final String JAVA_EXTENSION = "java";

    @Override
    public List<SourceFile> readAll(Path repositoryPath) {
        log.info("[FileSystemSourceReader] [readAll] Reading repository at: {}", repositoryPath);

        if (!Files.exists(repositoryPath) || !Files.isDirectory(repositoryPath)) {
            log.warn("[FileSystemSourceReader] [readAll] Path does not exist or is not a directory: {}", repositoryPath);
            return Collections.emptyList();
        }

        try (var paths = Files.walk(repositoryPath)) {
            return paths.filter(Files::isRegularFile)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(JAVA_EXTENSION))
                    .filter(p -> !p.toString().contains("/target/"))
                    .filter(p -> !p.toString().contains("/.git/"))
                    .map(p -> toSourceFile(p, repositoryPath))
                    .filter(Objects::nonNull).toList();
        } catch (IOException e) {
            log.error("[FileSystemSourceReader] [readAll]Failed to read repository: {} ", repositoryPath, e);
            return Collections.emptyList();
        }
    }

    private SourceFile toSourceFile(Path filePath, Path repositoryPath) {
        try {
            String content = Files.readString(filePath);
            String repositoryName = repositoryPath.getFileName().toString();
            return new SourceFile(filePath, content, repositoryName);
        } catch (IOException e) {
            log.warn("Failed to read file: {}", filePath, e);
            return null;
        }
    }
}
