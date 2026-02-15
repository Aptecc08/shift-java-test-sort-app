public class TypeDefiner {
    public static DataType defineType(String input) {
        if (input == null || input.isEmpty() || input.matches(".*\\s+.*")) {
            return DataType.STRING;
        }

        String value = input.trim();

        // integer: 123, -45, +7
        if (value.matches("[+-]?\\d+")) {
            return DataType.INTEGER;
        }

        // float: 12.3, -0.5, +10.0 (также поддерживает научную запись: 1e3, -2.5E-4)
        if (value.matches("[+-]?(\\d+\\.\\d+|\\d+\\.|\\.\\d+|\\d+[eE][+-]?\\d+|\\d*\\.\\d+[eE][+-]?\\d+)")) {
            return DataType.FLOAT;
        }

        return DataType.STRING;
    }

    public static void printType(String input) {
        System.out.println("Input: \"" + input + "\" -> " + defineType(input));

    }
}