import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final String accountId;
    private final int correctPin;

    private long balance;
    private final ReentrantLock accountLock=new ReentrantLock();

    public Account(String id, int pin, long initialBalance){
        this.accountId=id;
        this.correctPin=pin;
        this.balance=initialBalance;
    }

    public boolean authenticate(int pin){
        return this.correctPin==pin;
    }

    public boolean withdraw(long amount){
        accountLock.lock();

        try{
            if (balance>=amount){
                balance-=amount;
                System.out.println("Withdrawn: "+amount+" New Balance: "+balance);
                return true;
            }
            System.out.println("Insufficient funds");
            return false;
        }
        finally {
            accountLock.unlock();
        }
    }
}
