public class MobileAlertObserverImpl implements NotificationAlertObserver{
    private final  String userName;
    private final StockObservable observable;

    public MobileAlertObserverImpl(String user, StockObservable obs){
        this.observable=obs;
        this.userName=user;
    }

    @Override
    public void update(){
        sendMessage(userName,"mobile alert: product back int stock");
    }

    public void sendMessage(String userName, String msg){
        System.out.println("Mobile notif sent to "+ userName+" :"+msg);
    }
}
