public interface PlayerState {
    void takeDamage(Player player, int amount, Player attacker);
    void move(Player player, double x, double y);
}
