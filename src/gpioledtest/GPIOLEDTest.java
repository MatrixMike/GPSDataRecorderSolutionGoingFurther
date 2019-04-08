/* Copyright © 2014, Oracle and/or its affiliates. All rights reserved. */
package gpioledtest;
// MJH 9/4/2019
import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import jdk.dio.DeviceNotFoundException;

/**
 *
 * @author Angela Caicedo
 */
public class GPIOLEDTest extends MIDlet {

  GPIOLED pinTest;

  /**
   * Imlet lifecycle start method
   *
   * This method creates a GPIOLED, and invokes the blink method, to blink the
   * LED X number of times, in our case 8 times.
   */
  @Override
  public void startApp() {
    System.out.println("Starting GPIOLEDTest....mike");
    pinTest = new GPIOLED(23);     // was 23 
 //   pinTest2 = new GPIOLED(24);
      System.err.println("debug 1");
    try {
      pinTest.start();
//      pinTest2.start();
    } catch (DeviceNotFoundException ex) {
      System.out.println("DeviceException: mike" + ex.getMessage());
      notifyDestroyed();
    } catch (IOException ex) {
      System.out.println("startApp: IOException: " + ex);
      notifyDestroyed();
    }
      System.out.println("debug 2");
    pinTest.blink(8);
 //   pinTest2.blink(4);
  }

  /**
   * Imlet lifecycle termination method
   *
   * @param unconditional If the imlet should be terminated whatever
   */
  @Override
  public void destroyApp(boolean unconditional) {
      System.out.println("in destroy");
    try {
      if (pinTest != null) {
        pinTest.stop();
 //       pinTest2.stop();
      }
    } catch (IOException ex) {
      System.out.println("IOException: " + ex);
    }
  }

}
