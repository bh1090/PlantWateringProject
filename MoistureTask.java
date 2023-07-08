package eecs1021;
// minor project Moisture class

import org.firmata4j.IODevice;
import	org.firmata4j.Pin;
import	org.firmata4j.ssd1306.SSD1306;
import	java.util.TimerTask;

// This class interacts with the soil moisture sensor and the pump.
public	class	MoistureTask extends	TimerTask	{
    private	int	duration;
    private	final SSD1306	myOled;
    private Pin	pinInput;
    private	final	int	myTimer;
    private Long	sensorVal;
    private int lastRectLength = 0;

    private Pin pumpInput; // pin for pump

    private Pin  buttonInput;
    private final int soilDryVoltage = 700;

    private IODevice theArduinoObj;
    //	class	constructor.
    public MoistureTask(SSD1306	display, Pin	pin, int	timer, IODevice theArduinoObj, Pin PumpPin, Pin ButtonPin	){
        this.myOled =	display;
        this.pinInput =	pin; // pin for moisture sensor
        this.pumpInput = PumpPin; // pin for pump
        this.buttonInput = ButtonPin;
        this.myTimer	=	timer;
        this.theArduinoObj = theArduinoObj;
    }
    @Override
    public	void	run()	{ // continuously runs the code inside this method

        sensorVal = (Long)pinInput.getValue();
        // System.out.println( (Long)sensorVal ); // prints out the current moisture sensor value


        if(sensorVal >= soilDryVoltage){ // runs this code if the soil is dry.

            if (PumpTask.getPumpValue() == 1) {
                myOled.getCanvas().drawString(0, 0, String.valueOf("PUMP ON"));
                myOled.getCanvas().drawString(0, 20, String.valueOf("SOIL IS DRY"));
                myOled.display(); // Update the OLED display (move data from memory onto the screen itself)
                myOled.getCanvas().clear();

            } else if(PumpTask.getPumpValue() == 0){
                myOled.getCanvas().drawString(0, 0, String.valueOf("PUMP OFF"));
                myOled.getCanvas().drawString(0, 20, String.valueOf("SOIL IS DRY"));
                myOled.display(); // Update the OLED display (move data from memory onto the screen itself)
                myOled.getCanvas().clear();

            }

        } else if(sensorVal < soilDryVoltage) { // runs this code if the soil is wet

            if (PumpTask.getPumpValue() == 1) {
                myOled.getCanvas().drawString(0, 0, String.valueOf("PUMP ON"));
                myOled.getCanvas().drawString(0, 20, String.valueOf("SOIL IS WET"));
                myOled.display(); // Update the OLED display (move data from memory onto the screen itself)
                myOled.getCanvas().clear();

            } else if(PumpTask.getPumpValue() == 0){
                myOled.getCanvas().drawString(0, 0, String.valueOf("PUMP OFF"));
                myOled.getCanvas().drawString(0, 20, String.valueOf("SOIL IS WET"));
                myOled.display(); // Update the OLED display (move data from memory onto the screen itself)
                myOled.getCanvas().clear();

            }

        }


        PumpTask PumpTask = new PumpTask(pumpInput,buttonInput); // declares and initializes a new pumpTask.
        theArduinoObj.addEventListener(PumpTask); // adds an eventlistener to the pumpTask class.

        if(sensorVal <= soilDryVoltage){ // SOIL IS WET, SO TURN PUMP OFF

            System.out.println("pump turned off");
            PumpTask.pumpChange(0);

        }else if(sensorVal > soilDryVoltage ){ // SOIL IS DRY, SO TURN PUMP ON

            PumpTask.pumpChange(1);
            System.out.println("pump turned off");



        } // end of run method

        MinorProject.MoistureValueArray.add(sensorVal); // adds the moisture sensor value to the arraylist.
    }


} // end of class

// END OF FILE.....................