import java.util.concurrent.locks.ReentrantLock;

public class ATMMachine {
    private final String atmId;
    private ATMState currentState;
    private long atmVaultBalance;
    private Account currentAccount;
    private final ReentrantLock atmLock= new ReentrantLock();

    private final ATMState idleState=new IdleState(this);
    private final ATMState hasCardState=new HasCardState(this);
    private final ATMState authenticatedState=new AutheticatedState(this);
    public ATMMachine(String atmId, long initialCash){
        this.atmId=atmId;
        this.atmVaultBalance=initialCash;
        this.currentState=idleState;
    }

    public void insertCard(Account acc){
        atmLock.lock();
        try{
            currentState.insertCard(acc);
        }
        finally {
            atmLock.unlock();
        }
    }

    public void enterPin(int pin){
        atmLock.lock();
        try{
            currentState.autheticatePin(pin);
        }
        finally {
            atmLock.unlock();
        }
    }

    public void withdraw(long amount){
        atmLock.lock();
        try{
            currentState.withdrawCash(amount);
        }
        finally {
            atmLock.unlock();
        }
    }
    public void setState(ATMState state){
        this.currentState=state;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }


    public Account getCurrentAccount() {
        return currentAccount;
    }

    public boolean hasSufficientPhyiscalCash(long amount){
        atmLock.lock();
        try {
            return atmVaultBalance>=amount;
        }
        finally {
            atmLock.unlock();
        }
    }

    public void deductVaultCash(long amount){
        atmLock.lock();
        try{
            atmVaultBalance-=amount;
        }
        finally {
            atmLock.unlock();
        }
    }

    ATMState getIdleState(){
        return idleState;
    }
    ATMState getHasCardState(){
        return hasCardState;
    }
    ATMState getAuthenticatedState(){
        return authenticatedState;
    }
}
