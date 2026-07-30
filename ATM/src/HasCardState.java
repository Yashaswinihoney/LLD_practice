public class HasCardState implements ATMState{
    private final ATMMachine atm;

    public HasCardState(ATMMachine atm){
        this.atm=atm;
    }
    @Override
    public void insertCard(Account account) {
        System.out.println("Card already inserted");
    }

    @Override
    public void autheticatePin(int pin) {
        Account acc= atm.getCurrentAccount();
        if(acc!=null&&acc.authenticate(pin)){
            System.out.println("PIN auntehnticated succesfully");
            atm.setState(new AutheticatedState(atm));
        }
        else{
            System.out.println("Invalid PIN");
            ejectCard();
        }
    }

    @Override
    public void withdrawCash(long amount) {
        System.out.println("Enter PIN first");
    }

    @Override
    public void ejectCard() {
        System.out.println("Card ejected");
        atm.setCurrentAccount(null);
        atm.setState(new IdleState(atm));
    }
}
