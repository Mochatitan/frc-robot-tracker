public class App {
    public static void main(String[] args){
        Tracker.initialize("times-shot", "level", "exp");

        Tracker.set("exp", 5);

        Tracker.save();

    }
        
}
