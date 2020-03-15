import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Transaction {

    private List<String> amount;
    private List<String> expense;
    private Map<String, Double> counterpart;

    private static List<String> lines;

    static {
        try {
            lines = Files.readAllLines(Paths.get("09_FilesAndNetwork/files/movementList.csv"));
            lines.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> parseLineAmount() {

        amount = new ArrayList<>();

        for (String line : lines) {
            String[] fragments = line.split(",");
            amount.add(fragments[6]);
        }
        return amount;
    }

    public List<String> parseLineExpense() {

        expense = new ArrayList<>();

        for (String line : lines) {
            line = line.replace("\"", "");
            String[] fragments = line.split(",", 8);
            expense.add(fragments[7].replace(",", "."));
        }
        return expense;
    }

    public Map<String, Double> parseLineByCategory() {

        counterpart = new TreeMap<>();

        for (String line : lines) {
            line = line.replace("\"", "");
            String[] fragments = line.split(",", 8);
            String key = fragments[5].substring(20, 60);
            if (key.startsWith("\\")) {
                key = key.substring(1).replaceFirst("GBR\\\\", "").trim();
            } else {
                key = key.substring(12).replaceFirst("\\\\", "").trim();
            }
            Double value = Double.parseDouble(fragments[7].replace(",", "."));

            if (counterpart.containsKey(key)) {
                value = value + counterpart.get(key);
                counterpart.put(key, value);
            }
            if (!counterpart.containsKey(key)) {
                counterpart.put(key, value);
            }
        }
        return counterpart;
    }
}
