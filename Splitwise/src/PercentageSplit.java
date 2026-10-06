import java.util.List;

public class PercentageSplit implements SplitStrategy{
    @Override
    public void calculate(double totalAmount, List<Split> splits) {
        double currentPercent=splits.stream().mapToDouble(Split::getPercentage).sum();
        if(Math.abs(currentPercent-100.0)>0.01){
            throw new IllegalArgumentException("Percentages must sum to 100");
        }

        double allocated=0;
        for(int i=0;i< splits.size();i++){
            Split s=splits.get(i);
            double amount=Math.floor((totalAmount*s.getPercentage()/100.0)*100)/100.0;
            s.setAmount(amount);
            allocated+=amount;
        }
        splits.get(splits.size()-1).setAmount(Math.round((totalAmount-allocated)*100)/100.0);
    }
}
