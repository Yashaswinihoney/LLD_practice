public class GarageService {
    public void service(Startable vehicle){
        System.out.println("Starting engine");
        vehicle.start();

        if (vehicle instanceof Refuelable r){
            r.refuel();
        }
        else if (vehicle instanceof Rechargeable c){
            c.charge();
        }

        System.out.println("Service completed");
    }
}
