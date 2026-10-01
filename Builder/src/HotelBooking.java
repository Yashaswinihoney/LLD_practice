public class HotelBooking {
    private final String city;
    private final String checkIn;
    private final String checkOut;
    private final int guests;
    private final boolean breakfast;
    private final boolean parking;
    private final boolean poolAccess;
    private final String roomType;

    private HotelBooking(String city, String checkIn, String checkOut, int guests, boolean breakfast, boolean parking, boolean poolAccess, String roomType) {
        this.city = city;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.breakfast = breakfast;
        this.parking = parking;
        this.poolAccess = poolAccess;
        this.roomType = roomType;
    }

    //the builder class is static to be able to instantiate the HotelBooking object,
    public static class Builder{
        //required params
        private final String city;
        private final String checkIn;
        private final String checkOut;

        //optional params, with default values
        private int guests=1;
        private boolean breakfast=false;
        private boolean parking=false;
        private boolean poolAccess=false;
        private String roomType="STANDARD";
        public Builder(String city, String checkIn, String checkOut) {
            this.city = city;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }

        public Builder guests(int guests){ this.guests=guests; return this;}
        public Builder breakfast(boolean v) { this.breakfast = v; return this; }
        public Builder parking(boolean v) { this.parking = v; return this; }
        public Builder poolAccess(boolean v) { this.poolAccess = v; return this; }
        public Builder roomType(String type) { this.roomType = type; return this; }

        public HotelBooking build() {
            if (checkIn.compareTo(checkOut) >= 0) throw new IllegalArgumentException("check-out must be after check-in");
            return new HotelBooking(city, checkIn, checkOut, guests, breakfast, parking, poolAccess, roomType);
        }

        @Override
        public String toString(){
            return "HotelBooking{city=" + city + ", dates=" + checkIn + " to " + checkOut +
                    ", guests=" + guests + ", room=" + roomType +
                    ", breakfast=" + breakfast + ", parking=" + parking + "}";

        }
    }
}
