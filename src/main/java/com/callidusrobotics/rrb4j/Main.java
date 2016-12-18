/**
 * Copyright (C) 2016 Rusty Gerard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package com.callidusrobotics.rrb4j;

import java.io.IOException;

import com.callidusrobotics.rrb4j.RasPiRobotBoard.MotorDirection;

/**
 * Sample driver application to demonstrate the <code>RasPiRobotBoard</code>
 * interface.
 *
 * @author Rusty Gerard
 * @since 1.0.0
 * @see RasPiRobotBoard
 */
@SuppressWarnings("PMD.ShortClassName")
public final class Main {
  private Main() {
    // No need to initialize this class
  }

  /**
   * Driver main method.
   *
   * @param args Optional positional CLI arguments to pass to the <code>RasPiRobot3</code> constructor:
   * <ol>
   *   <li>batteryVoltage</li>
   *   <li>motorVoltage</li>
   * </ol>
   * @throws InterruptedException If the thread is interrupted
   * @see RasPiRobot3#RasPiRobot3(float, float)
   */
  @SuppressWarnings({"PMD.NPathComplexity", "PMD.LawOfDemeter"})
  public static void main(final String[] args) throws InterruptedException {
    final RasPiRobotBoard rrb3;

    if (args.length > 1) {
      final float batteryVoltage = Float.parseFloat(args[0]);
      final float motorVoltage = Float.parseFloat(args[1]);

      rrb3 = new RasPiRobot3(batteryVoltage, motorVoltage);
    } else {
      rrb3 = new RasPiRobot3();
    }

    System.out.print("Blinking LEDs");
    for (int i = 0; i < 10; i++) {
      rrb3.setLed1(i % 2 == 0);
      rrb3.setLed2(i % 2 == 0);

      System.out.print(".");

      Thread.sleep(1000);
    }
    System.out.println("");

    System.out.println("Switch1: " + (rrb3.switch1Closed() ? "closed" : "open"));
    System.out.println("Switch2: " + (rrb3.switch2Closed() ? "closed" : "open"));

    try {
      System.out.println("Rangefinder: " + rrb3.getRangeCm() + " cm");
    } catch (final IOException e) {
      System.out.println("Rangefinder: not connected");
    }

    System.out.println("Motors: forward...");
    rrb3.setMotors(0.25f, MotorDirection.FORWARD, 0.25f, MotorDirection.FORWARD);
    Thread.sleep(5000);

    System.out.println("Motors: stop...");
    rrb3.setMotors(0.0f, MotorDirection.FORWARD, 0.0f, MotorDirection.FORWARD);
    Thread.sleep(1000);

    System.out.println("Motors: reverse...");
    rrb3.setMotors(0.25f, MotorDirection.REVERSE, 0.25f, MotorDirection.REVERSE);
    Thread.sleep(5000);

    rrb3.shutdown();
  }
}
