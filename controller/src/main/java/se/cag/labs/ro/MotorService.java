package se.cag.labs.ro;

import com.pi4j.io.i2c.I2CFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MotorService {


    @Value("${motor.controller.address}")
    private int address = 0x60;    // The I2C address of the motor HAT, default is 0x60.

    private int trim = 0;  // Amount to offset the speed of the left motor, can be positive or negative and use useful for matching the speed of both motors.  Default is 0.

    private AdafruitMotorHAT mh;

    private AdafruitMotorHAT.AdafruitDCMotor leftMotor;
    private AdafruitMotorHAT.AdafruitDCMotor rightMotor;


    public MotorService() {
        try {
            this.mh = new AdafruitMotorHAT();
            this.leftMotor = mh.getMotor(AdafruitMotorHAT.Motor.M1);
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);
            this.rightMotor = mh.getMotor(AdafruitMotorHAT.Motor.M2);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);
        } catch (UnsatisfiedLinkError | IOException | I2CFactory.UnsupportedBusNumberException ioe) {
            ioe.printStackTrace();
        }
    }

    public void stop() {
        try {
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.RELEASE);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void setLeftSpeed(int speed) throws IOException {
        if (speed > 0) {
            this.leftMotor.setSpeed(speed);
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.leftMotor.setSpeed(-speed);
            this.leftMotor.run(AdafruitMotorHAT.ServoCommand.BACKWARD);
        }
    }

    public void setRightSpeed(int speed) throws IOException {
        if (speed > 0) {
            this.rightMotor.setSpeed(speed);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.FORWARD);
        } else {
            this.rightMotor.setSpeed(-speed);
            this.rightMotor.run(AdafruitMotorHAT.ServoCommand.BACKWARD);
        }
    }
}
