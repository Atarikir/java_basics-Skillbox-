import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static String transactionFile = "09_FilesAndNetwork/files/movementList.csv";

    public static void main(String[] args) {

        List<Transaction> listOfTransaction = parseLine();

        //460800,00 доходы
        System.out.printf("%n%s%d%n", "Поступление денежных средств на счет : ", listOfTransaction
                .stream()
                .mapToLong(Transaction::getIncome)
                .sum());

        System.out.println("-----------------------------------------------------------");
        System.out.println("Расходы по контрагентам : " + "\n");

        listOfTransaction.stream()
                .collect(Collectors.groupingBy(Transaction::getContractor,
                        Collectors.summingLong(Transaction::getExpense)))
                .forEach((k, v) -> System.out.printf("%-32s - %d%n", k, v));

        System.out.println("------------------------------------------------------------");

        //466393,07 расходы
        System.out.printf("%n%-32s - %d%n", "Денежные расходы по счету ", listOfTransaction
                .stream()
                .mapToLong(Transaction::getExpense)
                .sum());
    }

    private static List<Transaction> parseLine() {

        List<Transaction> listOfTransaction = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(transactionFile));
            lines.remove(0);

            for (String line : lines) {

                String[] fragments = line.replace("\"", "").split(",", 8);

                if (fragments.length != 8) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }

                if (fragments[7].matches(".+,.{2}")) {
                    fragments[7] = fragments[7].replace(",", "");
                } else if (fragments[7].matches(".+,.")) {
                    fragments[7] = fragments[7].replace(",", "").concat("0");
                } else fragments[7] = fragments[7].concat("00");

                fragments[5] = fragments[5].substring(20, 60);
                if (fragments[5].startsWith("\\")) {
                    fragments[5] = fragments[5].substring(1).replaceFirst("GBR\\\\", "").trim();
                } else {
                    fragments[5] = fragments[5].substring(12).replaceFirst("\\\\", "").trim();
                }

                listOfTransaction.add(new Transaction(
                        Long.parseLong(fragments[6].concat("00")),
                        Long.parseLong(fragments[7]),
                        fragments[5]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfTransaction;
    }
}