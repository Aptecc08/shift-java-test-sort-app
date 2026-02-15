import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ResultWriter implements AutoCloseable {
    private Observer stisticObserver;
    private final Path filePath;
    private BufferedWriter writer; 
    private final boolean appendMode;

    public ResultWriter(Path filePath, Observer observer, boolean appendMode) {
    this.filePath = filePath;
    this.stisticObserver = observer;
    this.appendMode = appendMode;
    }

    public void printStatistic(){
        stisticObserver.onPrintResult();
    }

    public void writeLine(String line) throws IOException {
    ensureWriterCreated();
    writer.write(line == null ? "null" : line);
    writer.newLine();
    writer.flush();
    stisticObserver.onAddElement(line);
    }

    private void ensureWriterCreated() throws IOException {
        if (writer != null) return;

        Path parent = filePath.getParent();
        if (parent != null) Files.createDirectories(parent);

        if (appendMode) {
            writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } else {
            writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
