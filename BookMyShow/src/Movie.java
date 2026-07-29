public class Movie {
    private final String title;
    private final int durationMins;

    public Movie(String title, int durationMins){
        this.durationMins=durationMins;
        this.title=title;
    }

    public String getTitle(){
        return title;
    }
}
