package io.peccin.spring_ai_code_search.application.port.in;

import java.nio.file.Path;

public interface IndexRepositoryUseCase {
    void index(Path repositoryPath);
}
