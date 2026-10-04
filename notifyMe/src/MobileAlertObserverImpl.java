public class MobileAlertObserverImpl implements NotificationAlertObserver{
    private final  String userName;
    public MobileAlertObserverImpl(String user){
        this.userName=user;
    }

    public void sendMessage(String userName, String msg){
        System.out.println("Mobile notif sent to "+ userName+" :"+msg);
    }

    @Override
    public void update(int currentStock) {
        sendMessage(userName, "Mobile alert !! Product is back int stock! Count "+currentStock);
    }
}
