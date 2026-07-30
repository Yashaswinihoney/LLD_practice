public interface ATMState {
    void insertCard(Account account);
    void autheticatePin(int pin);
    void withdrawCash(long amount);
    void ejectCard();
}
