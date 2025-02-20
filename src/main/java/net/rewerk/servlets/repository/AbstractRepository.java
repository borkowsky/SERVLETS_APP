package net.rewerk.servlets.repository;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.rewerk.servlets.exception.SourceCreateException;
import net.rewerk.servlets.exception.SourceNotFoundException;
import net.rewerk.servlets.exception.SourceSaveException;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class AbstractRepository<T> {
    private final Gson GSON = new Gson();

    protected List<T> getEntities(String filename, Class<T> cls) {
        try {
            this.checkSourceFileExists(filename);
        } catch (Exception e) {
            throw new RuntimeException("Error checking if source file exists", e);
        }
        try (InputStream is = this.getClass().getResourceAsStream("/" + filename)) {
            if (is != null) {
                JsonReader reader = new JsonReader(new InputStreamReader(is));
                Type listType = TypeToken.getParameterized(List.class, cls).getType();
                return GSON.fromJson(reader, listType);
            } else {
                throw new SourceNotFoundException(String.format("UserRepository: %s not found",
                        filename));
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error reading entities from source file: %s, error: %s",
                    filename,
                    e.getMessage()));
        }
    }

    protected void save(List<T> entities, String filename) throws SourceSaveException {
        URL saveFilePath = this.getClass().getResource("/" + filename);
        if (saveFilePath != null) {
            Path path = Paths.get(saveFilePath.getPath());
            try (FileWriter fileWriter = new FileWriter(path.toAbsolutePath().toString())) {
                GSON.toJson(entities, fileWriter);
            } catch (Exception e) {
                throw new SourceSaveException(String.format("UserRepository: unable to save %s",
                        filename));
            }
        }
    }

    private void checkSourceFileExists(String filename) throws SourceCreateException {
        URL resourceRootPath = this.getClass().getResource("/");
        if (resourceRootPath == null) {
            throw new SourceCreateException("Source root path is null");
        }
        Path path = Paths.get(resourceRootPath.getPath());
        try {
            File file = new File(Paths.get(path.toString(), filename).toString());
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new SourceCreateException(String.format("AbstractRepository: unable to create %s file",
                            filename));
                } else {
                    try {
                        checkFileLength(file);
                    } catch (Exception e) {
                        throw new SourceCreateException(
                                String.format("AbstractRepository: unable to check file %s length",
                                filename)
                        );
                    }
                }
            } else {
                try {
                    checkFileLength(file);
                } catch (Exception e) {
                    throw new SourceCreateException(
                            String.format("AbstractRepository: unable to check file %s length",
                                    filename)
                    );
                }
            }
        } catch (Exception e) {
            throw new SourceCreateException(e.getMessage());
        }
    }

    private void checkFileLength(File file) throws SourceCreateException {
        if (file.length() == 0 && file.canWrite()) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write("[]");
                fileWriter.flush();
            } catch (Exception e) {
                throw new SourceCreateException(String.format("AbstractRepository: unable to write initial bytes to %s file",
                        file.getName()));
            }
        }
    }
}
