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

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * RasPiRobot Board v3 (hardware revision 1) implementation.
 * <p>
 * This implementation is thread-safe but not reentrant.<br>
 * Multi-threaded applications will need to implement their own synchronization
 * for each underlying hardware resource to prevent contention problems.
 *
 * @author Rusty Gerard
 * @since 1.0.0
 */
public class RasPiRobot3Rev1 extends RasPiRobot3 {
  /**
   * Uses default voltage settings:
   * <ul>
   *   <li>Battery:
   *   {@value com.callidusrobotics.rrb4j.RasPiRobotBoard#BATTERY_DEFAULT_V} volts
   *   </li>
   *   <li>Motors:
   *   {@value com.callidusrobotics.rrb4j.RasPiRobotBoard#MOTOR_DEFAULT_V} volts
   *   </li>
   * </ul>
   */
  public RasPiRobot3Rev1() {
    super();

    init();
  }

  /**
   * @param batteryVoltage
   *          The nominal voltage of the power source
   * @param motorVoltage
   *          The maximum voltage of the motors
   */
  public RasPiRobot3Rev1(final float batteryVoltage, final float motorVoltage) {
    super(batteryVoltage, motorVoltage);

    init();
  }

  // Constructor for unit tests
  RasPiRobot3Rev1(final GpioController gpio) {
    super(gpio);

    init();
  }

  // Constructor for unit tests
  RasPiRobot3Rev1(final GpioController gpio, final float batteryVoltage, final float motorVoltage) {
    super(gpio, batteryVoltage, motorVoltage);

    init();
  }

  // Variations in the original hardware revision compared to the latest revision
  private void init() {
    oc2Pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "OC2", PinState.LOW);
  }
}
