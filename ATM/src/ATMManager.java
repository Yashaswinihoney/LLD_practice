import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//BILL PUGH SINGLETON Manager for ATMS
public class ATMManager {
    //concurrenthashmap for atomic multithreaded machine registration
    private final Map<String,ATMMachine> activeATMs=new ConcurrentHashMap<>();

    //private constructor
    private ATMManager(){}

    //Lock free instance holder
    private static class InstanceHolder{
        private static final ATMManager INSTANCE=new ATMManager();
    }

    //Global access point
    public static ATMManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public void registerATM(String atmId, long initialCash){
        activeATMs.putIfAbsent(atmId,new ATMMachine(atmId,initialCash));
    }

    public ATMMachine getATM(String atmID){
        return activeATMs.get(atmID);
    }
}
