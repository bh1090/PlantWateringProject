// Name: Bhavya Trivedi
// Program Title: MinorProject
// Program Description: This program is the code/logic for the plant watering system which uses components such as moisture sensors, pumps, etc to water the plants when its dry.
// This is the main file for the Minor Project.
// This file calls pumpTask and MoistureTask and GraphTask
// Date: 4 April, 2023

package eecs1021;

// importing all the required classes.

import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;


public class MinorProject {

    //	Pin	definitions	(assuming	Nano	or	UNO)
    static	final	int	A1	=	15;	//	moisture sensor
    static	final	int	D6	=		6;	//	Button
    static	final	byte	I2C0	=	0x3C;	//	OLED	Display
    public static ArrayList<Long> MoistureValueArray = new ArrayList<Long>();
    static Timer time = new Timer();

    public static void main(String[] args) throws
            IOException, InterruptedException {
        /* Initialize the Board */

        String myUSB = "COM6";

        // Create a FirmataDevice object with a USB connection.
        IODevice theArduinoObject = new FirmataDevice(myUSB);
        // Start up the FirmataDevice object.
        theArduinoObject.start();
        theArduinoObject.ensureInitializationIsDone();
        // Created an I2C communication object b/w the Arduino chip and the OLED over I2C wires
        // Then created an SSD1306 object using the I2C object with the right pixel size for the OLED
        I2CDevice i2cObject = theArduinoObject.getI2CDevice((byte) I2C0); // Use 0x3C for the Grove OLED
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // 128x64 OLED SSD1515
        // Initialized the OLED (SSD1306) object
        theOledObject.init();
        var pumpObject = theArduinoObject.getPin(7);
        // 2. Filled the pump object.
        pumpObject.setMode(Pin.Mode.OUTPUT);
        // 1. Assign memory location to the button object
        var buttonObject = theArduinoObject.getPin(D6);
        // 2. Filled the object.
        buttonObject.setMode(Pin.Mode.INPUT);
        var myMoistureSensor = theArduinoObject.getPin(A1); // the pin for the moisture sensor
        final	int	myTimer = 0;
        // Set up the display (type, size ...)
        var moisturetask = new MoistureTask(theOledObject,myMoistureSensor, myTimer, theArduinoObject, pumpObject, buttonObject );

        // Timer timerr; // runs the timer task every 1000ms.
        time.schedule(moisturetask, 0, 1000);

    } // end of main method

    // getArrayValue() method returns the moisture arraylist when called.
    public static ArrayList<Long> getArrayValue() {
        return MoistureValueArray;
    }

    // Method turnTimerClassOff cancels the timer when called.
    public static void turnTimerClassOff() {
         time.cancel(); // terminates the timer.schedule that was running in the background.

    }

} // end of class

// END OF FILE..............

