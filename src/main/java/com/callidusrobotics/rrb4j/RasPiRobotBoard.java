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

/**
 * API definitions for MonkMakes RasPiRobot microcontrollers.
 *
 * @author Rusty Gerard
 * @since 1.0.0
 */
public interface RasPiRobotBoard {

  enum MotorDirection {
    FORWARD, REVERSE
  }

  /**
   * LED-1 mutator.
   *
   * @param enabled
   *          Turns the LED on if true, otherwise off
   */
  void setLed1(boolean enabled);

  /**
   * LED-2 mutator.
   *
   * @param enabled
   *          Turns the LED on if true, otherwise off
   */
  void setLed2(boolean enabled);

  /**
   * Switch-1 accessor.
   *
   * @return True if the switch is closed, otherwise false
   */
  boolean switch1Closed();

  /**
   * Switch-2 accessor.
   *
   * @return True if the switch is closed, otherwise false
   */
  boolean switch2Closed();

  /**
   * High-power open collector 1 mutator.
   *
   * @param enabled
   *          Turns the OC on if true, otherwise off
   */
  void setOc1(boolean enabled);

  /**
   * High-power open collector 2 mutator.
   *
   * @param enabled
   *          Turns the OC on if true, otherwise off
   */
  void setOc2(boolean enabled);

  /**
   * DC motors mutator.<br>
   * Sets the motors to run continuously with the specified speeds and
   * directions.
   *
   * @param m1Speed
   *          Proportional speed of motor-1, valid values in the range [0, 1.0]
   * @param m1Direction
   *          Direction of rotation of motor-1, not null
   * @param m2Speed
   *          Proportional speed of motor-2, valid values in the range [0, 1.0]
   * @param m2Direction
   *          Direction of rotation of motor-2, not null
   */
  void setMotors(float m1Speed, MotorDirection m1Direction, float m2Speed, MotorDirection m2Direction);

  /**
   * Bipolar stepper motor mutator.<br>
   * Rotates shaft one full step in the specified direction.
   *
   * @param direction
   *          Direction of rotation of the motor, not null
   * @param delayMillis
   *          Number of milliseconds between each phase change, must be positive
   *          and nonzero
   */
  void setStepper(MotorDirection direction, int delayMillis);

  /**
   * SR-04 ultrasonic rangefinder accessor.
   *
   * @return The estimated distance from the sensor to the target in centimeters
   */
  float getRangeCm();

  /**
   * Prepare the system for final shutdown.
   */
  void shutdown();
}
