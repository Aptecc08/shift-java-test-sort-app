import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputParser {
     
    public List<String> readLinesSequentially(List<Path> files) {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();

        for (Path file : files) {
            if (file == null) {
                System.err.println("Input file path is null. Skipped.");
                continue;
            }

            try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            } catch (IOException e) {
                System.err.println("Cannot read file: " + file + ". Skipped.");
            }
        }

        return result;
    }
}
