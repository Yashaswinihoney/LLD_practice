public class DCLSingleton {
    private static volatile DCLSingleton instance;
    private DCLSingleton(){}

    public static DCLSingleton getInstance(){
        if(instance==null){
            synchronized (DCLSingleton.class){
                if (instance==null){
                    instance=new DCLSingleton();
                }
            }
        }
        return instance;
    }

    public void showMessage(){
        System.out.println("DCL Singleton executed");
    }
}
