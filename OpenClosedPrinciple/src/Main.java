import java.lang.reflect.Type;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        PaymentRouter router=new PaymentRouter();

        for(String type: new String[]{"CARD","UPI","WALLET"}){
            System.out.println("Payment via "+ type +":");
            router.getHandler(type).process(250.0);
        }
    }
}