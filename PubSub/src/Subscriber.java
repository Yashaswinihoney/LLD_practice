public class Subscriber implements ISubscriber{
    private final String id;

    public Subscriber(String id){
        this.id=id;
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public void onMessage(Message message){
        System.out.println("Subscriber ["+id+"] received: "+ message.getPayload());
    }
}
