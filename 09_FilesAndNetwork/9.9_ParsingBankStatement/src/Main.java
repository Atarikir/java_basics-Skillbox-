import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    static List<String> lines;

    static {
        try {
            lines = Files.readAllLines(Paths.get("09_FilesAndNetwork/files/movementList.csv"));
            lines.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        List<String> amount = parseLineAmount();
        List<String> expense = parseLineExpense();
        Map<String, Double> categories = parseLineByCategory();

        //460800,00
        System.out.printf("%n%s%,.2f%s%n", "Поступление денежных средств на счет : ", amount.stream()
                //.skip(1)
                .mapToDouble(Double::parseDouble).sum(), " руб.");

        System.out.println("-----------------------------------------------------------");
        System.out.println("Расходы по категориям : " + "\n");

        for (Map.Entry<String, Double> entry : categories.entrySet()) {
            System.out.printf("%-32s - %,.2f руб.%n", entry.getKey(), entry.getValue());
        }

        System.out.println("------------------------------------------------------------");
        //466393,07
        System.out.printf("%n%-32s - %,.2f%s%n", "Денежные расходы по счету ", expense.stream()
                //.skip(1)
                .mapToDouble(Double::parseDouble).sum()," руб.");
    }

    private static Map<String, Double> parseLineByCategory() {

        Map<String, Double> categories = new TreeMap<>();

        for (String line : lines) {
            line = line.replace("\"", "");
            String[] fragments = line.split(",", 8);
            //String fragment = fragments[5].substring(20, 60);
//            if (fragments[5].startsWith("\\")) {
            String key = fragments[5].substring(20, 60);
            if (key.startsWith("\\")) {
                key = key.substring(1).trim();
            }else key = key.substring(9).trim();
            Double value = Double.parseDouble(fragments[7].replace(",", "."));

            if (categories.containsKey(key)) {
                value = value + categories.get(key);
                categories.put(key, value);
            }
            if (!categories.containsKey(key)) {
                categories.put(key, value);
            }


        }
        return categories;
    }

    private static List<String> parseLineExpense() {

        List<String> expense = new ArrayList<>();

        for (String line : lines) {
            line = line.replace("\"", "");
            String[] fragments = line.split(",", 8);
            expense.add(fragments[7].replace(",", "."));
        }
        return expense;
    }

    private static List<String> parseLineAmount() {

        List<String> amount = new ArrayList<>();

        for (String line : lines) {
            String[] fragments = line.split(",");
            amount.add(fragments[6]);
        }
        return amount;
    }
}

//        double sum = 0.00;
//        for (int i = 0; i < columns.size() ; i++) {
//
//            Double.parseDouble(columns.get(i).toString().replaceAll("\"", "").trim());
//            System.out.println(sum);
//        }
//        ColumnValue[] array = columns.toArray(new ColumnValue[0]);
//System.out.println(Double.parseDouble(columns.get(1).toString()));// +
//Double.parseDouble(columns.get(63).toString()));

//        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i].toString().replaceAll("\"", ""));
//        }

//        for (int i = 0; i < columns.size(); i++) {
//            String parse = columns.get(i).toString().replaceAll("\"", "");
//            double parseDouble = Double.parseDouble(parse);
//            double sum = columns.stream()
//                    .map(s -> parseDouble)
//                    .reduce(Double::sum).orElse((double) 0);
//            System.out.println(sum);
//            System.out.println(columns.get(i).toString().replaceAll("\"", ""));
//}
//System.out.println(columns.toString().replaceAll("\"",""));


//        double sum = columns.stream()
//                .map(s -> Double.parseDouble())
//                .reduce((s1, s2) -> s1 + s2);
//        System.out.println(sum);
//System.out.println(columns.toString().replaceAll("\"", ""));
//        double sum = 0.00;
//        for (int i = 0; i < columns.size(); i++) {
//            sum += Double.parseDouble(columns.get(i).toString().replaceAll("\"", ""));
//            System.out.println(sum);
//        }


//        double sum = 0.00;
//        for (int i = 0; i < columns.size(); i++) {
//            sum += Double.parseDouble(columns.get(i).toString().replaceAll("\"", ""));
//            System.out.println(sum);
//            //System.out.println(columns.get(i).toString().replaceAll("\"", ""));
//            //}
//        }

//        double sum = 0.0;
//        for (ColumnValue c : columns) {
//           //if (c.getExpense().matches("\\d+")) {
//                double v = Double.parseDouble(String.valueOf(c));
//                sum += v;
//            //}
//        }
//        System.out.println(sum);


//List<String> expense = parseLineExpense();

//        System.out.println("Поступление денежных средств на счет : " + amount.stream()
//                .filter(s -> s.matches("\\d+"))
//                .mapToDouble(Double::parseDouble).sum() + " руб.");
//
//        System.out.println(expense);


//    private static List<String> parseLineAmount() {
//
//        List<String> amount = new ArrayList<>();
//
//        try {
//            List<String> lines = Files.readAllLines(Paths.get(csvFile));
//            for (String line : lines) {
//                String[] fragments = line.split(",");
//                amount.add(fragments[6]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return amount;
//    }
//
//    private static List<ColumnValue> parseLine(String csvFile) throws IOException {
//
//        List<ColumnValue> columns = new ArrayList<>();
//        List<String> fileLines = Files.readAllLines(Paths.get(csvFile));
//        for (String fileLine : fileLines) {
//            String[] splitedText = fileLine.split(",");
//            ArrayList<String> columnList = new ArrayList<>();
//            for (int i = 0; i < splitedText.length; i++) {
//                if (IsColumnPart(splitedText[i])) {
//                    String lastText = columnList.get(columnList.size() - 1);
//                    columnList.set(columnList.size() - 1, lastText + "," + splitedText[i]);
//                } else {
//                    columnList.add(splitedText[i]);
//                }
//            }
//        }
//
//        columns.subList(0, 1).clear();
//        return columns;
//    }
//
//    private static boolean IsColumnPart(String text) {
//        String trimText = text.trim();
//        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
//    }


