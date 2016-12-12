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

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.wiringpi.Gpio;

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
  protected GpioPinDigitalInput switch1Pin, switch2Pin;
  protected GpioPinDigitalOutput rangeTriggerPin;
  protected GpioPinDigitalInput rangeEchoPin;

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
    return switch1Pin.isLow();
  }

  @Override
  public boolean switch2Closed() {
    return switch2Pin.isLow();
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

  @SuppressWarnings("PMD.PrematureDeclaration")
  @Override
  public float getRangeCm() throws IOException {
    // Pulse the trigger pin for 10 microseconds
    rangeTriggerPin.setState(PinState.HIGH);
    delayMicroseconds(TRIGGER_MICROS);
    rangeTriggerPin.setState(PinState.LOW);

    // Wait for start of echo pulse from the rangefinder (rising edge of echo pin)
    if (!waitForEvent(rangeEchoPin, PinState.HIGH, ECHO_DELAY_MICROS)) {
      throw new IOException("Rangefinder is not connected");
    }
    final long sendTime = currentTimeNanos();

    // Measure pulse width (time until falling edge of echo pin)
    if (!waitForEvent(rangeEchoPin, PinState.LOW, MAX_PULSE_MICROS)) {
      // Echo went beyond maximum measurable distance
      return Float.POSITIVE_INFINITY;
    }
    final long receiveTime = currentTimeNanos();

    // Compute distance traveled (halved to account for round-trip duration)
    final long durationMicros = (receiveTime - sendTime) / (1000L * 2);
    final float distMm = SOS_MM_MICROS * durationMicros;

    return distMm / 10.0f;
  }

  @Override
  public void shutdown() {
    gpio.shutdown();
  }

  // Wrapper around Gpio.delayMicroseconds to hide static methods
  protected void delayMicroseconds(final long microseconds) {
    Gpio.delayMicroseconds(microseconds);
  }

  // Wrapper around System.nanoTime to hide static methods
  protected long currentTimeNanos() {
    return System.nanoTime();
  }

  // Wait up to a specified number of microseconds for the input pin to indicate a particular value
  protected boolean waitForEvent(final GpioPinDigitalInput pin, final PinState value, final long timeoutMicros) {
    // TODO: Re-write this to use interrupts instead of software polling? This seems to be *good enough* for centimeter resolution.
    final long startTime = currentTimeNanos();
    long endTime = startTime;
    while ((endTime - startTime) < (1000L * timeoutMicros)) {
      if (pin.getState() == value) {
        return true;
      }

      endTime = currentTimeNanos();
    }

    return false;
  }
}
