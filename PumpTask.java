package eecs1021;

// Interface defined: (https://bit.ly/3IhVg1J)

import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.firmata4j.Pin;
import java.io.IOException;
// This class controls the pump of the plant watering system.
public class PumpTask implements
        IODeviceEventListener {
    private static int pumpState = 0; // 0 = OFF; 1 = ON
    private static Pin pumpPin;
    private final Pin buttonPin;


    // constructor
    PumpTask(Pin pumpPin, Pin buttonPin) {
        this.pumpPin = pumpPin; // assigning pumpPin pin to this.pumpPin.
        this.buttonPin = buttonPin; // assigning buttonPin pun to this.buttonPin

    }
// Method pumpChange changes the pump value when called.
    public static void pumpChange(int value) {
        // Return right away if the even isn't from the Button.
            try {
                pumpPin.setValue(value); // sets the pump value to the value parameter provided

                 pumpState = value; // records the value parameter into the pumpState variable.

            } catch (IOException e) { // catches the exception.

                e.printStackTrace();
                System.out.println("There was an error while turning the pump on/off.");
            }
        }

        // Method getPumpValue() returns the pumpState variable when called.
    public static int getPumpValue() {
        return pumpState;

}

    @Override
    public void onStart(IOEvent ioEvent) {

    }

    @Override
    public void onStop(IOEvent ioEvent) {

    }

    @Override
    public void onPinChange(IOEvent event) {
        // Return right away if the even isn't from the Button.
        if (event.getPin().getIndex() != buttonPin.getIndex()) {
            // to do: return;
            return;
        }

        MinorProject.turnTimerClassOff(); // calls the method from the minorProject class.
        this.pumpChange(0); // turns the pump off.

       try {
           System.out.println(MinorProject.getArrayValue().toString()); // to print off moisture value array
             GraphTask.graphData(MinorProject.getArrayValue(), "Soil Moisture Sensor"); // calls the graphdata() method to graph the data
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Program was terminated");

        System.exit(0); // exits the program.


    }
    // These are empty methods (nothing in the curly   braces)
    @Override
    public void onMessageReceive(IOEvent ioEvent, String s) {

    }




    } // end of class


// END OF FILE................
