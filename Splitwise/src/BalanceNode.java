//Balance node for each user, basically the total net balance is user
//owes others or is owed, from all the splits
public class BalanceNode {
    String userId;
    double netAmount;
    public BalanceNode(String userId, double netAmount){
        this.userId=userId;
        this.netAmount=netAmount;
    }
}
