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
Place it in the src/main/**deploy** folder to ensure it gets sent to the roborio

## HOW TO USE
Tracker.getInt(nameOfData) will give you the value of what the txt file says, and Tracker.set() will set the value to something else.
