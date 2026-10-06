public class Split {
    private final String userId;
    private double amount;
    private final double percentage; //only used for percentage strategy
    public Split(String userId){
        this(userId,0.0,0.0);
    }

    public Split(String userId, double amount, double percentage) {
        this.userId=userId;
        this.amount=amount;
        this.percentage=percentage;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
