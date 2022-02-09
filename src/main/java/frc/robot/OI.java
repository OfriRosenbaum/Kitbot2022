package frc.robot;

import com.spikes2212.util.XboxControllerWrapper;
import edu.wpi.first.wpilibj.Joystick;

public class OI /*Gevald*/ {
    private final XboxControllerWrapper controller = new XboxControllerWrapper(0);
    private final Joystick joystick1 = new Joystick(0);
    private final Joystick joystick2 = new Joystick(1);

    public double getRightX() {
        return joystick1.getX();
    }

    public double getLeftY() {
        return -joystick2.getY();
    }
}
