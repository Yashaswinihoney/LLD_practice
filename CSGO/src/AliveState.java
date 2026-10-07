public class AliveState implements PlayerState{
    @Override
    public void takeDamage(Player player, int amount, Player attacker) {
        player.setHealth(player.getHealth()-amount);
        if (player.getHealth()<=0){
            player.setState(new DeadState());
            player.notifyKillFeed(attacker);
        }
    }

    @Override
    public void move(Player player, double x, double y) {
        player.setLocation(x,y);
    }
}
