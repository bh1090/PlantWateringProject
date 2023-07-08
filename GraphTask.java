// This class graphs the moisture data from the moisture sensor.
// This class is part of the minor project files.

package eecs1021;
import edu.princeton.cs.introcs.StdDraw;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static edu.princeton.cs.introcs.StdDraw.*;


public class GraphTask {
    public static int sample = 0;
    // public static String SensorType = "";
    // set up the graph
    // Axes (scale) first
    public static void graphData(ArrayList<Long> arrayData, String SensorType) throws IOException, InterruptedException{
       StdDraw.setXscale(-15, 100); // up to 100 samples
       StdDraw.setYscale(-40, 1100); // values up to 1023

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);

    // DRAW AXES
        StdDraw.line(0, 0, 0, 1000); // vertical line
        StdDraw.line(0, 0, 100, 0); // horizontal line


    // labels of the graph
        StdDraw.text(50, -69, "Time [min]");
        StdDraw.text(-10, 540, "Voltage [V]");
        StdDraw.text(50, 1100, SensorType + "Sensor Voltage vs Time");
        StdDraw.text(-5, 980, "5");
        StdDraw.text(-5, 487, "2.5");
        StdDraw.text(-5, 5, "0");
        StdDraw.text(50, -30, "1");
        StdDraw.text(95, -30, "2");
    // get a report from the scheduled task

        for(int i = 0; i < arrayData.size();i++ ){

             // value to graph. Graph data  from method in the other class (on Timertask)

            text((double) sample, (double) arrayData.get(sample), "*"); // note casts to double

             // increment the sample and wrap aronud at 100 samples
             if (sample < (arrayData.size() - 1)) {
                 sample++;

             } else {
                 sample = 1; // start again from the left side of the graph

             }

         } // end of for loop

        // Using BufferedReader to wait for user input before closing graph and terminating program.

        System.out.println("Enter any text to terminate program.");

        // Enter data using BufferReader
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();

    } // end of graph data method

} // end of class

// END OF FILE..............