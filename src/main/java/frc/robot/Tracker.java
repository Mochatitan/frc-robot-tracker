package frc.robot;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.Filesystem;

import java.util.HashMap;
import java.util.ArrayList;

public class Tracker {

    public static final String ROOTPATH = Filesystem.getDeployDirectory().getAbsolutePath();
    public static final String FILE = "/robotData.txt";
    public static final String FILEPATH = ROOTPATH + FILE;

    public static boolean testPrint = false;
    public static ArrayList dataTracked = new ArrayList<String>();
    

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m"; 
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Declaring ANSI_RESET so that we can reset the color 
    public static final String ANSI_RESET = "\u001B[0m"; 
  
    public static void initialize(String...data){
        Tracker.load();
        //dataTracked.addAll(data);
        for(String str : data){
            dataTracked.add(str);
        }
        //System.out.println(dataTracked.toString()); prints out the data tracked
    }
    //money, level, exp
    static HashMap<String, String> saveData = new HashMap<String, String>();
    static ArrayList<String> lineList = new ArrayList<String>();

    //static variables
    public static String get(String keyWord){
         return Tracker.saveData.get(keyWord);
    }
    public static int getInt(String keyWord){
         return Integer.valueOf(Tracker.get(keyWord));
    }
    public static void set(String keyWord, String value){
         Tracker.saveData.put(keyWord, value);
    }
    public static void set(String keyWord, int value){
         Tracker.saveData.put(keyWord, Integer.toString(value));
    }
    public static String readEntireFile(){
        String bar = "";
        for(String str : Tracker.lineList){
            bar += str;
            if((Tracker.lineList.size()-1) != Tracker.lineList.indexOf(str)){ 
                // If the current string is equal to the last text of the list, that means its the last one, so dont add an extra line.
                bar += "\n";
            }

            // String[] arrOfStr = str.split(":", 2);
            // saveData.put(arrOfStr[0],arrOfStr[1]);
        }
        // System.out.println(bar);
        return bar;

    }
    
    // private static File file = new File("../data/save/Save.txt");
    private static File file = new File(FILEPATH);
    // saveData.put("this","isthis");
    static String newSave = "";
    public static void save(){
        Tracker.newSave = "";
        for(String str : Tracker.lineList){

            //Tracker.newSave += str;

            Tracker.newSave += Tracker.dataTracked.get(Tracker.lineList.indexOf(str));
            Tracker.newSave += ":";
            Tracker.newSave += Tracker.saveData.get(Tracker.dataTracked.get(Tracker.lineList.indexOf(str)));

            if((Tracker.lineList.size()-1) != Tracker.lineList.indexOf(str)){ 
                // If the current string is equal to the last text of the list, that means its the last one, so dont add an extra line.
                Tracker.newSave += "\n";
            }
        }
        try{
            writeFile(FILEPATH, Tracker.newSave); //writes to the file
        } catch (IOException i) {}

    }
    public static void load(){
        Tracker.saveData.clear();
        Tracker.lineList.clear();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Tracker.lineList.add(line);
            }
            if(testPrint){
                //Test printing
                System.out.println("");
                colorPrint(ANSI_YELLOW,"System reads:");
                for(String s : Tracker.lineList){
                    System.out.println(s);
                }
                colorPrint(ANSI_YELLOW, "--------------END----------");
                System.out.println("");
            }

                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for(String str : Tracker.lineList){
                String[] arrOfStr = str.split(":", 2);
                saveData.put(arrOfStr[0],arrOfStr[1]);
        }
        if(testPrint){
            colorPrint(ANSI_RED, "Values recieved:");
            System.out.println(Tracker.saveData.get("times-shot"));
            System.out.println(Tracker.saveData.get("level"));
            System.out.println(Tracker.saveData.get("exp"));
            colorPrint(ANSI_RED, "--------------END----------");
        }
        
    }
    
    public static void colorPrint(String color, String msg){
        System.out.println(color + msg + ANSI_RESET); 
    }
    

    public static void writeFile(String filename, String text) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            fos.write(text.getBytes("UTF-8"));
        } catch (IOException e) {
            close(fos);
            throw e;
        }
    }

    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch(IOException ignored) {
        }
    }

}