public class App {
    public static void main(String[] args){
        Tracker tracker = new Tracker();
        Tracker.load();
        
        Tracker.set("exp", 7);
        //System.out.println(Tracker.get("exp"));

        if(Tracker.getInt("exp") == 2){
            System.out.println("test passed!");
        }

        System.out.println(Tracker.readEntireFile());
        Tracker.save();

    }
        
}
