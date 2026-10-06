import java.util.List;

public class ExactSplitStrategy implements SplitStrategy{
    @Override
    public void calculate(double totalAmount, List<Split> splits) {
        double currSum=splits.stream().mapToDouble(Split::getAmount).sum();
        if(Math.abs(currSum-totalAmount)>0.01){
            throw new IllegalArgumentException("Exact splits do not sum to total amount");
        }
    }
}
