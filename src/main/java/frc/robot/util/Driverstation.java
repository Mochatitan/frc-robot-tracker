package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class Driverstation extends Joystick{

    

    public Driverstation(int id){
        super(id);
    }
    public class ButtonBinds{
        public class RowOne{
            public final static int ONE = 7; //put the button IDs here
        }
    }
    
    //at this point, it functions simply as a clone of the Joystick class


}
