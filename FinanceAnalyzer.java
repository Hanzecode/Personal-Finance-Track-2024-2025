import java.io.*;
import java.util.*;

public class FinanceAnalyzer {
    
    /**
     * Step 1: Loading Data from File
     * This method converts a text file (CSV) into a list of Java Objects.
     */
    public static List<Transaction> loadTransactions(String path) throws IOException {

        // Create an empty list to store our Transaction objects
        List<Transaction> transactions = new ArrayList<>();

        // Open the file using a Scanner
        Scanner scanner = new Scanner(new File(path));

        // Skip the very first line of the CSV (the header row with column titles)
        if (scanner.hasNextLine()) scanner.nextLine();

        // Loop through the file as long as there is another line to read
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Split the line into an array of strings wherever a comma appears
            String[] parts = line.split(",");

            // Create a new Transaction object using the data from the array
            // We convert Strings to numbers using Double.parseDouble()
            Transaction t = new Transaction(
                parts[0], 
                parts[1], 
                parts[2],
                Double.parseDouble(parts[3]), 
                Double.parseDouble(parts[4]), 
                Double.parseDouble(parts[5])
            );

            // Add the newly created object to our list
            transactions.add(t);
        }

        // Close the scanner to free up system resources
        scanner.close();

        // Return the full list of objects to the caller
        return transactions;
    }

    /**
     * Step 2: Basic Summary Calculation
     * Calculates total income, expenses, and net savings using a simple loop.
     */

    public static void printSummary(List<Transaction> transactions) {
        double totalIncome = 0;
        double totalExpense = 0;

        // Use a standard for-each loop to look at every transaction one by one
        for (Transaction t : transactions) {
            
            // Add moneyIn and moneyOut to our running totals
            totalIncome += t.moneyIn;
            totalExpense += t.moneyOut;
        }

        // Display the results to the console
        System.out.println("Total Income: £" + totalIncome);
        System.out.println("Total Expenses: £" + totalExpense);
        System.out.println("Net Savings: £" + (totalIncome - totalExpense));
    }

    /**
     * Step 3: Compounding and Sorting Expenses
     * This is the logic to sum spending by category and find the top 10.
     */
    public static void topFiveExpenseCategories(List<Transaction> transactions) {
        System.out.println("\nTop 10 Expense Categories (Totaled):");

        // PART A: Grouping and Summing
        // We use a Map where the Key is the 'Description' and Value is the 'Total Spent'

        Map<String, Double> totalsMap = new HashMap<>();
        for (Transaction t : transactions) {

            // Only process the transaction if it is an expense
            if (t.isExpense()) {

                // Get the current total for this description. If it's new, start at 0.0
                double currentTotal = totalsMap.getOrDefault(t.description, 0.0);

                // Add the current transaction's cost to that description's total
                totalsMap.put(t.description, currentTotal + t.moneyOut);
            }
        }

        // PART B: Sorting
        // Maps cannot be sorted directly. We must turn the Map entries into a List.
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(totalsMap.entrySet());

        // Sort the list using a Comparator
        sortedList.sort(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
                // Compare the values (totals) in reverse order (Descending)
                // e2 compared to e1 makes the largest numbers come first
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        // PART C: Printing the Limit
        // Loop 5 times, or less if the list doesn't have 5 items
        int limit = Math.min(10, sortedList.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Double> entry = sortedList.get(i);
            System.out.printf("%s - £%.2f%n", entry.getKey(), entry.getValue());
        }
    }
}