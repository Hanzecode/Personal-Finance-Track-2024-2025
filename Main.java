import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            List<Transaction> transactions =
                    FinanceAnalyzer.loadTransactions("PersonalFinanceHist.csv");

            FinanceAnalyzer.printSummary(transactions);
            FinanceAnalyzer.topFiveExpenseCategories(transactions);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
