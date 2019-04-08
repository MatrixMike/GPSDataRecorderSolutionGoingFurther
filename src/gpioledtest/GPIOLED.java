/* Copyright © 2014, Oracle and/or its affiliates. All rights reserved. */
package gpioledtest;
/* revived 9/04/2019 */
import java.io.IOException;
import jdk.dio.DeviceManager;
import jdk.dio.DeviceNotFoundException;
import jdk.dio.gpio.GPIOPin;

/**
 * @author Angela
 */
public class GPIOLED {

    //Pin where the LED is connected. By default we are using Pin 23.
    private int ledID = 23;
 // private int ledID2 = 24;

    private GPIOPin greenLED;   //Green LED
    public boolean stopBlink = false;

    /**
     * GPIOLED Constructor
     *
     * @param pinNumber Pin where the LED is connected
     */
    public GPIOLED(int pinNumber) {
        ledID = pinNumber;
    }

    /**
     * This method start the connection to the LED, and turns it on.
     *
     * @throws IOException
     * @throws jdk.dio.DeviceNotFoundException
     */
    public void start() throws IOException, DeviceNotFoundException {

        // Open the LED pin (Output)
        greenLED = DeviceManager.open(ledID);

        //Set LED initial value
        greenLED.setValue(true);

        //Small pause is introduce
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * This method set the value for the LED
     *
     * @param value new value for the LED
     * @throws IOException
     */
    public void setValue(boolean value) throws IOException {
        greenLED.setValue(value);
    }

    /**
     * This method set the value for the LED
     *
     * @return It returns the value of the LED
     * @throws IOException
     */
    public boolean getValue() throws IOException {
        return greenLED.getValue();
    }

    /**
     * This method allows to stop the LED blinking process
     *
     * @param status Status for the stopBlinking variable. If true, this will
     * cause an active blinking to stop.
     */
    public void setStopBlink(boolean status) {
        stopBlink = status;
    }

    /**
     * This method will blink the LED. We used a separate thread so the program
     * won't get paused for other actions.
     *
     * @param times Number of LED's blinks.
     */
    // view->IDElog will show where the source is
    public void blink(final int times) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; (i < (times * 2)) && !stopBlink; i++) {
                    System.out.println("in blink " + i);
                    try {
                        setValue(!getValue());
                        Thread.sleep(1000);
                    } catch (IOException | InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }).start();
    }

    /**
     * Method to turn off the LED before ending the application nd closing the
     * connection with the LED.
     *
     * @throws IOException
     */
    public void stop() throws IOException {
        if (greenLED != null) {
            greenLED.setValue(false);
            greenLED.close();
        }
    }
}
