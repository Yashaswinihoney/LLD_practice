import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Topic {
    private final String name;
    private final List<ISubscriber> subscribers=new CopyOnWriteArrayList<>();
    public Topic(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void addSubscriber(ISubscriber subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(ISubscriber subscriber){
        subscribers.remove(subscriber);
    }

    public List<ISubscriber> getSubscribers(){
        return subscribers;
    }
}
