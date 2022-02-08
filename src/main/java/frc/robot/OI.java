package frc.robot;

import com.spikes2212.util.XboxControllerWrapper;

public class OI /*Gevald*/ {
    private final XboxControllerWrapper controller = new XboxControllerWrapper(0);

    public double getRightX() {
        return controller.getRightX() * 0.7;
    }

    public double getLeftY() {
        return controller.getLeftY() * -0.7;
    }
}
