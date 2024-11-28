## ICED JAVA ROBOTICS TRACKER
This is a data tracker for the FRC team 6894 Iced java, made by Luke/mochatitan. It tracks things like how many miles the robot moves, or how many times it shoots, and stores those numbers in a txt file.
(This is a public repository, and pull requests are very welcomed!)

## HOW TO SETUP
Simply make a copy of the Tracker.java file in your code folder that has RobotContainer.java, or a util folder.
Then, run Tracker.initialize() at the RobotInit() method in the Robot.java file, leaving the parameters empty.

Also, to make sure that the new data is pushed to the txt file, add Tracker.save() to the disabledInit() inside the Robot.java class.

## SETTING UP robotData.txt
Fill it with whatever you want, in a format of 
data-name:0
data-name2:0
and place it in the active directory (the one with all your src, and gradle folder. project folder.(view example)

## HOW TO USE
Tracker.getInt(nameOfData) will give you the value of what the txt file says, and Tracker.set() will set the value to something else.

## HOW IT WORKS
it uses the FileSystem.getActiveDirectory when you deploy to the robot, which lets the robot read the robotData.txt file on the computer.
it then converts the txt file into a HashMap which gets used in all the methods.
when you change a value with Tracker.set(), it changes the HashMap value.
when you disable the robot and it runs Tracker.save(), the robot translates the HashMap into the robotData.txt format and rewrites the file.