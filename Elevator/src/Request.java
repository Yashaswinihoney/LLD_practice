public class Request {
    private final int targetFloor;
    private final Direction direction;

    public Request(int targetFloor, Direction direction){
        this.direction=direction;
        this.targetFloor=targetFloor;
    }

    public int getTargetFloor(){
        return targetFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}
