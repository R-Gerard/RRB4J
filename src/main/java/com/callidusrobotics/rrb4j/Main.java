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

  public static void main(final String[] args) throws InterruptedException {
    final RasPiRobotBoard rrb3 = new RasPiRobot3();

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

    rrb3.shutdown();
  }
}
