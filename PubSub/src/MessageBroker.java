import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageBroker {
    private static volatile MessageBroker instance;
    private final Map<String,Topic> topics;
    private final ExecutorService executorService;

    private MessageBroker(){
        this.topics=new ConcurrentHashMap<>();
        this.executorService= Executors.newFixedThreadPool(10);
    }

    public static MessageBroker getInstance(){
        if(instance==null){
            synchronized (MessageBroker.class){
                if (instance==null){
                    instance=new MessageBroker();
                }
            }
        }
        return instance;
    }

    public void createTopic(String topicName){
        topics.putIfAbsent(topicName, new Topic(topicName));
    }

    public void subscribe(String topicName, ISubscriber subscriber){
        Topic topic=topics.get(topicName);
        if(topic!=null){
            topic.addSubscriber(subscriber);
            System.out.println(subscriber.getId()+" subscribed to "+ topicName);
        }
        else{
            System.out.println("topic dosent exist");
        }
    }

    public void publish(String topicName, Message message){
        Topic topic=topics.get(topicName);

        if(topic!=null){
            for(ISubscriber subscriber: topic.getSubscribers()){
                executorService.submit(()->subscriber.onMessage(message));
            }
        }
        else{
            System.out.println("topic dosent exist");
        }
    }

    public void shutdown(){
        executorService.shutdown();
    }
}
