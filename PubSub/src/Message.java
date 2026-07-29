import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Message {
    private final String id;
    private final String payload;

    public Message(String payload){
        this.id= UUID.randomUUID().toString();
        this.payload=payload;
    }

    public String getId() {
        return id;
    }

    public String getPayload(){
        return payload;
    }
}
