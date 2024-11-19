package frc.robot.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

import java.util.HashMap;
import java.util.ArrayList;

public class Tracker {

    public static int ERROR_RETURN_INT = 9999;
    public static String ERROR_RETURN_STRING = "THIS VARIABLE HAD AN ERROR GETTING THE VALUE";
    //public static final String ROOTPATH = Filesystem.getDeployDirectory().getAbsolutePath();
    public static final String ROOTPATH = Filesystem.getLaunchDirectory().getAbsolutePath();
    public static final String FILE = "/robotData.txt";
    public static final String FILEPATH = ROOTPATH + FILE;

    public static boolean testPrint = true;
    public static ArrayList<String> dataTracked = new ArrayList<String>();
    

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
  
    public static void initialize(){
        System.out.println(FILEPATH);
        Tracker.load();
        String[] data = Tracker.loadDataNames();
        
    }

    private static String[] loadDataNames(){

        return null;
    }
    //money, level, exp
    static HashMap<String, String> saveData = new HashMap<String, String>();
    static ArrayList<String> lineList = new ArrayList<String>();
    
    //static variables
    public static String get(String keyWord){
         
         if(variableExists(keyWord)){
            return Tracker.saveData.get(keyWord);
        }
        warn("keyWord '" + keyWord + "' does not exist! error in getInt method");
        return ERROR_RETURN_STRING;
    }
    public static int getInt(String keyWord){
         if(variableExists(keyWord)){
            return Integer.valueOf(Tracker.get(keyWord));
        }
        warn("keyWord '" + keyWord + "' does not exist! error in getInt method");
        return ERROR_RETURN_INT;
    }
    public static void set(String keyWord, String value){
        if(variableExists(keyWord)){
            Tracker.saveData.put(keyWord, value);
        }
        warn("keyWord '" + keyWord + "' does not exist! error in set method");
    }
    public static void set(String keyWord, int value){
        if(variableExists(keyWord)){
            Tracker.saveData.put(keyWord, Integer.toString(value));
        }
        warn("keyWord '" + keyWord + "' does not exist! error in set method");
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
    private static boolean variableExists(String varname){
        if(saveData.containsKey(varname)){
            return true;
        }
        return false;
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
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for(String str : Tracker.lineList){
                String[] arrOfStr = str.split(":", 2);
                saveData.put(arrOfStr[0],arrOfStr[1]);
                dataTracked.add(arrOfStr[0]);
        }
        printAll(); // test printing that displays all the things taken from the txt file and how they are interpreted into variables
    }
    
    private static void printValuesRecieved(){
        colorPrint(ANSI_RED, "Values recieved:");
        for(String diddy : Tracker.dataTracked){
            System.out.println(Tracker.saveData.get(diddy));
        }
        colorPrint(ANSI_RED, "--------------END----------");
    }
    private static void printVariablesRecieved(){
        // for(int a = 0; a <= Tracker.dataTracked.size()-1; a++){
            //     System.out.println(Tracker.dataTracked.get(a));
            // }
            colorPrint(ANSI_BLUE, "Variables recieved:");
            for(String egg : Tracker.dataTracked){
                System.out.println(egg);
            }
            colorPrint(ANSI_BLUE,"--------------END----------\n");
    }
    private static void printLinesRecieved(){
        //Test printing
        System.out.println("");
        colorPrint(ANSI_YELLOW,"System reads:");
        for(String s : Tracker.lineList){
            System.out.println(s);
        }
        colorPrint(ANSI_YELLOW, "--------------END----------");
        System.out.println("");
    }
    private static void printAll(){
        if(testPrint){
            printLinesRecieved();
            printVariablesRecieved();
            printValuesRecieved();
        }
    }
    
    private static void colorPrint(String color, String msg){
        System.out.println(color + msg + ANSI_RESET); 
    }
    
    private static void writeFile(String filename, String text) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            fos.write(text.getBytes("UTF-8"));
        } catch (IOException e) {
            close(fos);
            throw e;
        }
    }

    /**
     * Closes the scanner so it doesnt use resources during the actual match
     * @param closeable the scanner
     */
    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch(IOException ignored) {
        }
    }

    private static void warn(String warning){
        colorPrint(ANSI_RED, "WARNING!!! TRACKER ERROR: " + warning);
        DriverStation.reportWarning(ANSI_RED + warning + ANSI_RESET, false);
    }

}