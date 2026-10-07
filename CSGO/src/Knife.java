public class Knife implements WeaponStrategy{
    @Override
    public int getDamage() {
        return 55;
    }

    @Override
    public double getRange() {
        return 2.0;
    }

    @Override
    public String getName() {
        return "Combat Knife";
    }
}
