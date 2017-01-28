package se.cag.labs.rover.service;

import com.pi4j.io.i2c.I2CFactory;
import org.springframework.stereotype.Service;
import se.cag.labs.rover.motors.AdafruitMotorHAT;

import java.io.IOException;

@Service
public class MotorService {

    public static final int MAX_SPEED = 255;
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
        } catch (UnsatisfiedLinkError | IOException | I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLeftSpeed(final float speed) throws IOException {
        int realSpeed = Math.round(speed * (float) MotorService.MAX_SPEED);

        if (leftMotor == null) {
            return;
        }

        if (realSpeed > 0) {
            this.leftMotor.setSpeed(realSpeed);
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.leftMotor.setSpeed(-realSpeed);
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.BACKWARD);
        }
    }

    public void setRightSpeed(final float speed) throws IOException {
        int realSpeed = Math.round(speed * (float) MotorService.MAX_SPEED);

        if (rightMotor == null) {
            return;
        }

        if (realSpeed > 0) {
            this.rightMotor.setSpeed(realSpeed);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.rightMotor.setSpeed(-realSpeed);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.BACKWARD);
        }
    }

    public void fireLaser(boolean fire) throws IOException {
        if (laser == null) {
            return;
        }

        if (fire) {
            this.laser.setSpeed(100);
            this.laser.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.laser.run(AdafruitMotorHAT.ServoCommand.RELEASE);
        }
    }
}
