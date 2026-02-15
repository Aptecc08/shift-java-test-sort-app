import java.io.IOException;
import java.nio.file.Path;

public class Sorter implements AutoCloseable{
    private final ResultWriter integerResultWriter;
    private final ResultWriter floatResultWriter;
    private final ResultWriter stringResultWriter;

    public Sorter(Path outputDir, String prefix, boolean appendMode, boolean fullStatistic) {
        if (outputDir == null) {
            throw new IllegalArgumentException("Output directory must not be null");
        }
        if (prefix == null) {
            prefix = "";
        }

        Observer integerObserver = fullStatistic ? new NumericStatistic() : new Statistic();
        Observer floatObserver = fullStatistic ? new NumericStatistic() : new Statistic();
        Observer stringObserver = fullStatistic ? new StringStatistic() : new Statistic();

        integerResultWriter = new ResultWriter(outputDir.resolve(prefix + "integer"), integerObserver, appendMode);
        floatResultWriter = new ResultWriter(outputDir.resolve(prefix + "float"), floatObserver, appendMode);
        stringResultWriter = new ResultWriter(outputDir.resolve(prefix + "string"), stringObserver, appendMode);
    }

    public void sort(String value) {
        if (value == null) {
            return;
        }

        switch (TypeDefiner.defineType(value)) {
            case INTEGER -> writeSafe(integerResultWriter, value);
            case FLOAT -> writeSafe(floatResultWriter, value);
            case STRING -> writeSafe(stringResultWriter, value);
        }
    }

    public void printStatistics() {
        System.out.println("String statistic:");
        stringResultWriter.printStatistic();

        System.out.println("Integer statistic:");
        integerResultWriter.printStatistic();

        System.out.println("Float statistic:");
        floatResultWriter.printStatistic();
    }

    private void writeSafe(ResultWriter writer, String value) {
        try {
            writer.writeLine(value);
        } catch (IOException e) {
            System.err.println("Cannot write value: " + value);
        }
    }

    @Override
    public void close() throws IOException {
        integerResultWriter.close();
        floatResultWriter.close();
        stringResultWriter.close();
    }
}
