public class ElectricCar implements Startable, Rechargeable{
    @Override
    public void start(){
        System.out.println("Electric Car engine started");
    }

    @Override
    public void charge(){
        System.out.println("Electric car battery charged");
    }
}
