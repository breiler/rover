package se.cag.labs.ro;

import com.pi4j.io.i2c.I2CFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MotorService {

    private AdafruitMotorHAT.AdafruitDCMotor leftMotor;
    private AdafruitMotorHAT.AdafruitDCMotor rightMotor;
    private AdafruitMotorHAT.AdafruitDCMotor laser;

    public MotorService() {
        try {
            AdafruitMotorHAT motorHAT = new AdafruitMotorHAT();
            leftMotor = motorHAT.getMotor(AdafruitMotorHAT.Motor.M2);
            leftMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);

            rightMotor = motorHAT.getMotor(AdafruitMotorHAT.Motor.M1);
            rightMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);

            laser = motorHAT.getMotor(AdafruitMotorHAT.Motor.M3);
            laser.run(AdafruitMotorHAT.ServoCommand.RELEASE);
        } catch (UnsatisfiedLinkError | IOException | I2CFactory.UnsupportedBusNumberException ioe) {
            ioe.printStackTrace();
            laser = null;
            leftMotor = null;
            rightMotor = null;
        }
    }

    public void stopMotors() {
        try {
            if (leftMotor != null) {
                leftMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);
            }

            if (rightMotor != null) {
                rightMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);
            }

            if (laser != null) {
                laser.run(AdafruitMotorHAT.ServoCommand.RELEASE);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void setLeftSpeed(int speed) throws IOException {
        if (leftMotor == null) {
            return;
        }

        if (speed > 0) {
            this.leftMotor.setSpeed(speed);
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.leftMotor.setSpeed(-speed);
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.BACKWARD);
        }
    }

    public void setRightSpeed(int speed) throws IOException {
        if (rightMotor == null) {
            return;
        }

        if (speed > 0) {
            this.rightMotor.setSpeed(speed);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.rightMotor.setSpeed(-speed);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.BACKWARD);
        }
    }

    public void setLaser(boolean on) throws IOException {
        if (laser == null) {
            return;
        }

        if (on) {
            this.laser.setSpeed(100);
            this.laser.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.laser.run(AdafruitMotorHAT.ServoCommand.RELEASE);
        }
    }
}
