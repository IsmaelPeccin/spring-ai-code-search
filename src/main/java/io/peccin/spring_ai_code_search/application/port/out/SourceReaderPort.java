package io.peccin.spring_ai_code_search.application.port.out;


import io.peccin.spring_ai_code_search.domain.model.SourceFile;

import java.nio.file.Path;
import java.util.List;

public interface SourceReaderPort {
    List<SourceFile> readAll(Path repositoryPath);
}
