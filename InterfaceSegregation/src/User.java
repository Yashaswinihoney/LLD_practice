public class User {
    String id;
    String name;

    public User(String id, String name){
        this.id=id;
        this.name=name;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
