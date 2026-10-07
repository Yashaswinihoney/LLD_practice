public class MobileAppClient implements TripObserver{
    private final String clientId;
    public MobileAppClient(String clientId){
        this.clientId=clientId;
    }
    @Override
    public void onTripStatusChanged(Ride ride) {
        System.out.println(clientId+" App, Trip "+ride
                .getId()+" is now "+ ride.getStatus());
    }
}
