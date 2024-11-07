## ICED JAVA ROBOTICS TRACKER
This is a data tracker for the FRC team 6894 Iced java, made by Luke R. It tracks things like how many miles the robot moves, or how many times it shoots, and stores those numbers in a txt file.

## HOW TO SETUP
Simply make a copy of the Tracker.java file in your code folder that has RobotContainer.java
Then, run Tracker.initialize at the RobotInit() method in the Robot.java file.
fill Tracker.initialize() with all of the things being tracked, in a fashion like this example would be
Tracker.initialize(nameOfData, nameOfData2, nameOfData3);

Also, to make sure that the new data is pushed to the txt file, add Tracker.save() to the disabledInit() inside the Robot.java class.

## SETTING UP robotData.txt
TBD

## HOW TO USE
Tracker.getInt(nameOfData) will give you the value of what the txt file says, and Tracker.set() will set the value to something else.
