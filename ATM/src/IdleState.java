public class IdleState implements ATMState{
    private final ATMMachine atm;
    public IdleState(ATMMachine atm){
        this.atm=atm;
    }
    @Override
    public void insertCard(Account account) {
        System.out.println("Card Inserted Securely ");
        atm.setCurrentAccount(account);
        atm.setState(new HasCardState(atm));
    }

    @Override
    public void autheticatePin(int pin) {
        System.out.println("Insert Card first");
    }

    @Override
    public void withdrawCash(long amount) {
        System.out.println("Insert card first");
    }

    @Override
    public void ejectCard() {
        System.out.println("No card to eject");
    }
}
