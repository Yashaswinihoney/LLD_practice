class DeadState implements PlayerState {
    @Override
    public void takeDamage(Player player, int amount, Player attacker) {
        // Already dead, ignore.
    }
    @Override
    public void move(Player player, double x, double y) {
        System.out.println(player.getId() + " is dead and cannot move.");
    }
}