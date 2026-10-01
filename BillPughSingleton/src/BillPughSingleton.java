public class BillPughSingleton {
    private BillPughSingleton(){}
    private static class InstanceHolder{
        private static final BillPughSingleton instance=new BillPughSingleton();
    }

//    The getInstance() method is declared static because you must be able to call it without having an object of the class first.
//    If the method were not static, you would need an existing BillPughSingleton object to invoke it. However, because the Singleton pattern strictly makes the constructor private, you cannot create an object from the outside using new.
    public static BillPughSingleton getInstance(){
        return InstanceHolder.instance;
    }
    public void showMessage(){
        System.out.println("Bill pugh singleton instance accesesed");
    }
}
