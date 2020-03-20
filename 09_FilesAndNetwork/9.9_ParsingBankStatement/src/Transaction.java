public class Transaction {

    private long income;
    private long expense;
    private String contractor;

    public Transaction(long income, long expense, String contractor) {
        this.income = income;
        this.expense = expense;
        this.contractor = contractor;
    }

    public long getIncome() {
        return income;
    }

    public long getExpense() {
        return expense;
    }

    public String getContractor() {
        return contractor;
    }
}
