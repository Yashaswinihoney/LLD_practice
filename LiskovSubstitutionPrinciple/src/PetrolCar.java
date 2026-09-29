public class PetrolCar implements Startable, Refuelable{
    @Override
    public void start(){
        System.out.println("Car engine started");
    }

    @Override
    public void refuel(){
        System.out.println("Car tank filled");
    }
}
