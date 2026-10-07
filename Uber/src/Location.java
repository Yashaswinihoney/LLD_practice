public class Location {
    double latitude;
    double longitude;

    public Location(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getGridId(){
        return (int)latitude+"_"+(int)longitude;
    }
    public double distanceTo(Location other){
        return Math.sqrt(Math.pow(this.latitude- other.latitude,2)+Math.pow(this.longitude- other.longitude,2));
    }
}
