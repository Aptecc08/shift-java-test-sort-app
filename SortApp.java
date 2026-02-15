import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SortApp {
    private static final class Config {
        Path outputDir = Path.of(".");
        String prefix = "";
        boolean appendMode = false;
        boolean fullStatistic = false; 
        List<Path> inputFiles = new ArrayList<>();
    }

    public static void main(String[] args) {
        Config config;
        try {
            config = parseArgs(args);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            printUsage();
            return;
        }

        if (config.inputFiles.isEmpty()) {
            System.err.println("No input files were provided.");
            printUsage();
            return;
        }

        InputParser reader = new InputParser();

        try (Sorter sorter = new Sorter(
                config.outputDir,
                config.prefix,
                config.appendMode,
                config.fullStatistic
        )) {
            List<String> lines = reader.readLinesSequentially(config.inputFiles);

            for (String line : lines) {
                sorter.sort(line);
            }

            sorter.printStatistics();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid arguments: " + e.getMessage());
            printUsage();
        } catch (Exception e) {
            System.err.println("Program finished with error: " + e.getMessage());
        }
    }

    private static Config parseArgs(String[] args) {
        Config config = new Config();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "-o":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("Option -o requires a path");
                    }
                    config.outputDir = Path.of(args[++i]);
                    break;

                case "-p":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("Option -p requires a prefix");
                    }
                    config.prefix = args[++i];
                    break;

                case "-a":
                    config.appendMode = true;
                    break;

                case "-f":
                    config.fullStatistic = true;
                    break;

                case "-s":
                    config.fullStatistic = false; 
                    break;

                default:
                    if (arg.startsWith("-")) {
                        throw new IllegalArgumentException("Unknown option: " + arg);
                    }
                    config.inputFiles.add(Path.of(arg));
                    break;
            }
        }

        return config;
    }

    private static void printUsage() {
        System.out.println("Usage: java sort [options] <input files>");
        System.out.println("Options:");
        System.out.println("  -o <path>  output directory");
        System.out.println("  -p <pref>  prefix for output files");
        System.out.println("  -a         append mode");
        System.out.println("  -s         short statistics (default)");
        System.out.println("  -f         full statistics");
    }
}
