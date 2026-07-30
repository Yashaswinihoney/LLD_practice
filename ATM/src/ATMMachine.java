import java.util.concurrent.locks.ReentrantLock;

public class ATMMachine {
    private ATMState currentState;
    private long atmVaultBalance;
    private Account currentAccount;
    private final ReentrantLock vaultLock= new ReentrantLock();

    public ATMMachine(long initialCash){
        this.atmVaultBalance=initialCash;
        this.currentState=new IdleState(this);
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
        vaultLock.lock();
        try {
            return atmVaultBalance>=amount;
        }
        finally {
            vaultLock.unlock();
        }
    }

    public void deductVaultCash(long amount){
        vaultLock.lock();
        try{
            atmVaultBalance-=amount;
        }
        finally {
            vaultLock.unlock();
        }
    }

    public void insertCard(Account acc){
        currentState.insertCard(acc);
    }

    public void enterPin(int pin){
        currentState.autheticatePin(pin);
    }

    public void withdraw(long amount){
        currentState.withdrawCash(amount);
    }
}
