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
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;

/**
 * RasPiRobot Board v3 (latest hardware revision) implementation.
 * <p>
 * This implementation is thread-safe but not reentrant.<br>
 * Multi-threaded applications will need to implement their own synchronization
 * for each underlying hardware resource to prevent contention problems.
 *
 * @author Rusty Gerard
 * @since 1.0.0
 */
public class RasPiRobot3 extends AbstractRasPiRobot {
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
  public RasPiRobot3() {
    super();

    GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
    gpio = GpioFactory.getInstance();

    init();
  }

  /**
   * @param batteryVoltage
   *          The nominal voltage of the power source
   * @param motorVoltage
   *          The maximum voltage of the motors
   */
  public RasPiRobot3(final float batteryVoltage, final float motorVoltage) {
    super(batteryVoltage, motorVoltage);

    GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
    gpio = GpioFactory.getInstance();

    init();
  }

  // Constructor for unit tests
  RasPiRobot3(final GpioController gpio) {
    super();

    this.gpio = gpio;

    init();
  }

  // Constructor for unit tests
  RasPiRobot3(final GpioController gpio, final float batteryVoltage, final float motorVoltage) {
    super(batteryVoltage, motorVoltage);

    this.gpio = gpio;

    init();
  }

  private void init() {
    led1Pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "LED1", PinState.LOW);
    led1Pin.setShutdownOptions(true, PinState.LOW);

    led2Pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "LED2", PinState.LOW);
    led2Pin.setShutdownOptions(true, PinState.LOW);

    // TODO: Add support for debounce and event listeners
    switch1Pin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_11, "Switch1");
    switch2Pin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_09, "Switch2");

    oc1Pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "OC1", PinState.LOW);
    oc2Pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "OC2", PinState.LOW);

    m1PwmPin = RaspiPin.GPIO_24;
    m2PwmPin = RaspiPin.GPIO_14;
    m1PhasePin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "M1Phase1", PinState.LOW);
    m1PhasePin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "M1Phase2", PinState.LOW);
    m2PhasePin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "M2Phase1", PinState.LOW);
    m2PhasePin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "M2Phase2", PinState.LOW);

    rangeTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "Trigger", PinState.LOW);
    rangeTriggerPin.setShutdownOptions(true, PinState.LOW);

    rangeEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_23, "Echo", PinPullResistance.PULL_DOWN);
  }
}
