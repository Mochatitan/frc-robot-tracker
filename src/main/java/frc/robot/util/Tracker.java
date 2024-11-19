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

    public static boolean testPrint = false;
    public static final String FILE = "/robotData.txt"; //format is '/filename.txt'

    public static int ERROR_RETURN_INT = 9999;
    public static String ERROR_RETURN_STRING = "THIS VARIABLE HAD AN ERROR GETTING THE VALUE";
    //public static final String ROOTPATH = Filesystem.getDeployDirectory().getAbsolutePath();
    public static final String ROOTPATH = Filesystem.getLaunchDirectory().getAbsolutePath();
    public static final String FILEPATH = ROOTPATH + FILE;
    private static File file = new File(FILEPATH);
    private static String newSave = "";

    //Colors to use for printing
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
  
    //The hashmap and arraylist used for storing data
    private static ArrayList<String> lineList = new ArrayList<String>();
    private static ArrayList<String> dataTracked = new ArrayList<String>();
    private static HashMap<String, String> saveData = new HashMap<String, String>();


    /**
     * This goes in the Robot.java's robotInit() method, and it loads all the data.
     * Exists mostly to ensure robotData.txt exists, and to add prints for testing purposes without having to edit the convuluted load method.
     */
    public static void initialize(){
        //If the file is found, load the file onto the arrays.
        if(Tracker.robotDataExists()){Tracker.load();}
    }


    
    // a bunch of methods for getting and setting values, duplicates exist so you can feed the parameters strings or integers.
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
            return;
        }
        warn("keyWord '" + keyWord + "' does not exist! error in set method");
    }
    public static void set(String keyWord, int value){
        if(variableExists(keyWord)){
            Tracker.saveData.put(keyWord, Integer.toString(value));
            return;
        }
        warn("keyWord '" + keyWord + "' does not exist! error in set method");
    }
    
    /**
     * This is the method that you put in the RobotDisabled() method of the Robot.java class. 
     * It is the method that actually takes all of the new data that has been logged onto arrays, and it rewrites them into the format for the txt file.
     * Once reformatted, it rewrites the txt file with the new contents.
     */
    public static void save(){
        Tracker.newSave = "";
        for(String str : Tracker.lineList){

            //the new save gets reconstructed using what we broke down earlier.
            Tracker.newSave += Tracker.dataTracked.get(Tracker.lineList.indexOf(str)); //the part before ':'
            Tracker.newSave += ":";
            Tracker.newSave += Tracker.saveData.get(Tracker.dataTracked.get(Tracker.lineList.indexOf(str))); //the part after ':'

            if((Tracker.lineList.size()-1) != Tracker.lineList.indexOf(str)){ 
                // If the current string is equal to the last text of the list, that means its the last one, so dont add an extra line.
                Tracker.newSave += "\n";
            }
        }
        try{
            //writes to the file
            writeFile(FILEPATH, Tracker.newSave); 
        } catch (IOException i) {}

    }

    /**
     * the function that actually gets the values from the txt file and loads them into the neccessary HashMaps and ArrayLists
     */
    public static void load(){
        Tracker.saveData.clear();
        Tracker.lineList.clear();
        try {
            //makes a scanner which scans the entire file line by line, filling a lineList with all the lines
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
                //for each line, split it into two parts, the part before ':', and the part after.
                String[] arrOfStr = str.split(":", 2);
                //connect them as a hash map, so you call the part before ':' to get the part after.
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
        if(robotDataExists()){
            if(testPrint){
                printLinesRecieved();
                printVariablesRecieved();
                printValuesRecieved();
            }
        }
    }
    
    /**
     * 
     * @param color the ANSI color that you want to make the text
     * @param msg the message that you want to have a color
     */
    private static void colorPrint(String color, String msg){
        System.out.println(color + msg + ANSI_RESET); 
    }

    /**
     * 
     * @return returns the string of the entire file from top to bottom
     */
    public static String readEntireFile(){
        String bar = "";
        for(String str : Tracker.lineList){
            bar += str;
            if((Tracker.lineList.size()-1) != Tracker.lineList.indexOf(str)){ 
                // If the current string is equal to the last text of the list, that means its the last one, so dont add an extra line.
                bar += "\n";
            }
        }
        return bar;
    }
    
    /**
     * 
     * @param filename the path/file of the file you want to write to.
     * @param text the text that you want to replace the contents of the file with
     * @throws IOException
     */
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
     * a method for checking whether or not a variable exists in the robot data file.
     * @param varname the name of the variable you want to check
     * @return true if the variable is found in your file, false if it isnt.
     */
    private static boolean variableExists(String varname){
        if(saveData.containsKey(varname)){
            return true;
        }
        return false;
    }
    /**
     * If the file cannot be found, warns the client repeatedly and angrily to fix it.
     * @return returns true if the file robotData.txt is able to be found successfully
     */
    private static boolean robotDataExists(){
        if(!Tracker.file.exists()){
            for(int b = 0; b < 20; b++){
                warn("robotData.txt WAS NOT FOUND!!! EITHER IT WAS NAMED WRONG OR ITS IN THE WRONG DIRECTORY/FOLDER");
            }
            return false;
        }
        return true;
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

    /**
     * a method for easily warning clients of errors, prints to the terminal and the driver station.
     * @param warning The message that you want to warn with
     */
    private static void warn(String warning){
        colorPrint(ANSI_RED, "WARNING!!! TRACKER ERROR: " + warning); //prints to the terminal
        DriverStation.reportWarning(ANSI_RED + warning + ANSI_RESET, false); //prints to the driver station
    }

}