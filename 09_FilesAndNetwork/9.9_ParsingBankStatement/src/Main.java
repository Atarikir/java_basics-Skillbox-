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

        final int INDEX_CONTRACTOR = 5;
        final int INDEX_INCOME = 6;
        final int INDEX_EXPENSE = 7;

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

                if (fragments[INDEX_EXPENSE].matches(".+,.{2}")) {
                    fragments[INDEX_EXPENSE] = fragments[INDEX_EXPENSE].replace(",", "");
                } else if (fragments[INDEX_EXPENSE].matches(".+,.")) {
                    fragments[INDEX_EXPENSE] = fragments[INDEX_EXPENSE].replace(",", "").concat("0");
                } else fragments[INDEX_EXPENSE] = fragments[INDEX_EXPENSE].concat("00");

                fragments[INDEX_CONTRACTOR] = fragments[INDEX_CONTRACTOR].substring(20, 60);
                if (fragments[INDEX_CONTRACTOR].startsWith("\\")) {
                    fragments[INDEX_CONTRACTOR] = fragments[INDEX_CONTRACTOR].substring(1).replaceFirst("GBR\\\\", "").trim();
                } else {
                    fragments[INDEX_CONTRACTOR] = fragments[INDEX_CONTRACTOR].substring(12).replaceFirst("\\\\", "").trim();
                }

                listOfTransaction.add(new Transaction(
                        Long.parseLong(fragments[INDEX_INCOME].concat("00")),
                        Long.parseLong(fragments[INDEX_EXPENSE]),
                        fragments[5]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfTransaction;
    }
}