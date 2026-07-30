public class AutheticatedState implements ATMState{
    private final ATMMachine atm;
    public AutheticatedState(ATMMachine atm){
        this.atm=atm;
    }
    @Override
    public void insertCard(Account account) {
        System.out.println("Card already inserted");
    }

    @Override
    public void autheticatePin(int pin) {
        System.out.println("Already autheticated");
    }

    @Override
    public void withdrawCash(long amount) {

        //pre check atm vault to prevent needing complex rollbacks
        if(!atm.hasSufficientPhyiscalCash(amount)){
            System.out.println("ATM ERROR: Insufficient funds");
            ejectCard();
            return;
        }

        Account acc=atm.getCurrentAccount();

        //safely withdraw from bank accvount
        if(acc.withdraw(amount)){
            //deduct from vault
            atm.deductVaultCash(amount);
            System.out.println("Success! Dispensing "+ amount);
        }

        ejectCard(); //auto eject after transaction
    }

    @Override
    public void ejectCard() {
        System.out.println("Transaction complete, Card Ejected");
        atm.setCurrentAccount(null);
        atm.setState(new IdleState(atm));
    }
}
