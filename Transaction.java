public class Transaction {
    public String date;
    public String description;
    public String type;
    public double moneyIn;
    public double moneyOut;
    public double balance;

    public Transaction(String date, String description, String type,
                       double moneyIn, double moneyOut, double balance) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.moneyIn = moneyIn;
        this.moneyOut = moneyOut;
        this.balance = balance;
    }

    public boolean isIncome() {
        return moneyIn > 0;
    }

    public boolean isExpense() { 
        return moneyOut > 0;
    }
}
