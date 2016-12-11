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

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.callidusrobotics.rrb4j.RasPiRobot3;
import com.callidusrobotics.rrb4j.RasPiRobotBoard;
import com.callidusrobotics.rrb4j.RasPiRobotBoard.MotorDirection;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RunWith(MockitoJUnitRunner.class)
public class RasPiRobot3Test {
  RasPiRobotBoard board;

  @Mock GpioController mockGpio;
  @Mock GpioPinDigitalOutput mockLed1Pin;
  @Mock GpioPinDigitalOutput mockLed2Pin;

  @Before
  public void before() {
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "LED1", PinState.LOW)).thenReturn(mockLed1Pin);
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "LED2", PinState.LOW)).thenReturn(mockLed2Pin);

    // Initialize our test object
    board = new RasPiRobot3(mockGpio);

    // Verify constructor calls
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_08, "LED1", PinState.LOW);
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_07, "LED2", PinState.LOW);

    verify(mockLed1Pin).setShutdownOptions(true, PinState.LOW);
    verify(mockLed2Pin).setShutdownOptions(true, PinState.LOW);
  }

  @After
  public void after() {
    verifyNoMoreInteractions(mockGpio);
  }

  @Test
  public void setLedsLowSuccess() {
    // Unit under test
    board.setLed1(false);
    board.setLed2(false);

    // Verify results
    verify(mockLed1Pin).setState(false);
    verify(mockLed2Pin).setState(false);
  }

  @Test
  public void setLedsHighSuccess() {
    // Unit under test
    board.setLed1(true);
    board.setLed2(true);

    // Verify results
    verify(mockLed1Pin).setState(true);
    verify(mockLed2Pin).setState(true);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void switchesClosedSuccess() {
    // Unit under test
    board.switch1Closed();
    board.switch2Closed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void switchesOpenSuccess() {
    // Unit under test
    board.switch1Closed();
    board.switch2Closed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void setCollectorsLowSuccess() {
    // Unit under test
    board.setOc1(false);
    board.setOc2(false);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void setCollectorsHighSuccess() {
    // Unit under test
    board.setOc1(true);
    board.setOc2(true);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void setMotorsForwardFull() {
    // Unit under test
    board.setMotors(1.0f, MotorDirection.FORWARD, 1.0f, MotorDirection.FORWARD);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void setMotorsReverseFull() {
    // Unit under test
    board.setMotors(1.0f, MotorDirection.REVERSE, 1.0f, MotorDirection.REVERSE);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void setStepperForward() {
    // Unit under test
    board.setStepper(MotorDirection.FORWARD, 20);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void setStepperReverse() {
    // Unit under test
    board.setStepper(MotorDirection.REVERSE, 20);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getRangeCm() throws Exception {
    // Unit under test
    board.getRangeCm();
  }

  @Test
  public void shutdownSuccess() {
    // Unit under test
    board.shutdown();

    // Verify results
    verify(mockGpio).shutdown();
  }
}
