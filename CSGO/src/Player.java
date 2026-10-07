import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    private final String id;
    private int health=100;
    private double x,y;
    private WeaponStrategy currentWeapon;
    private PlayerState currentState;

    private final ReentrantLock playerLock=new ReentrantLock();
    private final List<MatchObserver> observers=new CopyOnWriteArrayList<>();

    public Player(String id, double x, double y){
        this.id=id;
        this.x=x;
        this.y=y;
        this.currentState=new AliveState();
        this.currentWeapon=new AK47();
    }

    public void addObserver(MatchObserver obs) { observers.add(obs); }

    public void notifyKillFeed(Player attacker) {
        for (MatchObserver obs : observers) {
            obs.onPlayerKilled(this.id, attacker.getId(), attacker.currentWeapon.getName());
        }
    }

    // Encapsulated state mutations
    public String getId() { return id; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public void setState(PlayerState state) { this.currentState = state; }
    public void setLocation(double x, double y) { this.x = x; this.y = y; }

    public void equipWeapon(WeaponStrategy weapon){
        playerLock.lock();
        try{
            this.currentWeapon=weapon;
        }
        finally {
            playerLock.unlock();
        }
    }

    // Incoming network action: Taking fire
    public void receiveHit(int damage, Player attacker) {
        playerLock.lock();
        try {
            currentState.takeDamage(this, damage, attacker);
        } finally {
            playerLock.unlock();
        }
    }

    // Incoming network action: Firing weapon
    public void fireAt(Player target) {
        // Calculate Euclidean distance
        double distance = Math.sqrt(Math.pow(this.x - target.x, 2) + Math.pow(this.y - target.y, 2));

        if (distance <= currentWeapon.getRange()) {
            System.out.println(this.id + " hit " + target.getId() + " for " + currentWeapon.getDamage());
            target.receiveHit(currentWeapon.getDamage(), this);
        } else {
            System.out.println(this.id + " missed (Out of Range).");
        }
    }
}
