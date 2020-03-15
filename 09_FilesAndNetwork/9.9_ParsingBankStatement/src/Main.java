public class Main {

    public static void main(String[] args) {

        Transaction transaction = new Transaction();

        //460800,00
        System.out.printf("%n%s%,.2f%s%n", "Поступление денежных средств на счет : ", transaction.parseLineAmount()
                .stream()
                .mapToDouble(Double::parseDouble)
                .sum(), " руб.");

        System.out.println("-----------------------------------------------------------");
        System.out.println("Расходы по контрагентам : " + "\n");

        transaction.parseLineByCategory()
                .forEach((k, v) -> System.out.printf("%-32s - %,.2f руб.%n", k, v));

        System.out.println("------------------------------------------------------------");
        //466393,07
        System.out.printf("%n%-32s - %,.2f%s%n", "Денежные расходы по счету ", transaction.parseLineExpense()
                .stream()
                .mapToDouble(Double::parseDouble)
                .sum(), " руб.");
    }
}