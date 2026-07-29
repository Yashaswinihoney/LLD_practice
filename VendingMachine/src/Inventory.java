import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private final Map<String, Product> products= new ConcurrentHashMap<>();
    private final Map<String, Integer> stock=new ConcurrentHashMap<>();

    public void addProduct(String code, Product p, int count){
        products.put(code,p);
        stock.put(code,count);
    }

    public Product getProduct(String code){
        return products.get(code);
    }

    public boolean isAvailable(String code){
        return stock.containsKey(code)&&stock.get(code)>0;
    }

    public void reduceStock(String code){
        stock.computeIfPresent(code,(k,v)->v-1);
    }
}
