public class AK47 implements WeaponStrategy{
    @Override
    public int getDamage() {
        return 35;
    }

    @Override
    public double getRange() {
        return 50.0;
    }

    @Override
    public String getName() {
        return "AK-47";
    }
}
