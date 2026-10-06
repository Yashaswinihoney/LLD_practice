import java.util.List;

public class EqualSplitStrategy implements SplitStrategy{

    @Override
    public void calculate(double totalAmount, List<Split> splits) {
        int n=splits.size();
        // Truncate to 2 decimal places to prevent infinite fractions
        double baseAmount = Math.floor((totalAmount / n) * 100) / 100.0;

        // Precision Rounding Protection: Allocate the remaining cents to the first user
        double remainder = Math.round((totalAmount - (baseAmount * n)) * 100) / 100.0;
        for(int i=0;i<n;i++){
            splits.get(i).setAmount(baseAmount+((i==0?remainder:0.0)));
        }
    }
}
