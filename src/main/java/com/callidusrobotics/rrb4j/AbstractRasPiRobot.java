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
import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * Base class for implementations of <code>RasPiRobotBoard</code>.
 *
 * @author Rusty Gerard
 * @since 1.0.0
 */
abstract class AbstractRasPiRobot implements RasPiRobotBoard {

  private static final String NOT_IMPLEMENTED = "This has not yet been implemented";

  protected GpioController gpio;
  protected GpioPinDigitalOutput led1Pin, led2Pin;

  @Override
  public void setLed1(final boolean enabled) {
    led1Pin.setState(enabled);
  }

  @Override
  public void setLed2(final boolean enabled) {
    led2Pin.setState(enabled);
  }

  @Override
  public boolean switch1Closed() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public boolean switch2Closed() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public void setOc1(final boolean enabled) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public void setOc2(final boolean enabled) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public void setMotors(final float m1Speed, final MotorDirection m1Direction, final float m2Speed, final MotorDirection m2Direction) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public void setStepper(final MotorDirection direction, final int delayMillis) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public float getRangeCm() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public void shutdown() {
    gpio.shutdown();
  }
}
