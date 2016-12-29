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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.callidusrobotics.rrb4j.RasPiRobot3;
import com.callidusrobotics.rrb4j.RasPiRobotBoard;
import com.callidusrobotics.rrb4j.RasPiRobotBoard.MotorDirection;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RunWith(MockitoJUnitRunner.class)
public class RasPiRobot3Test {
  RasPiRobot3 board;

  @Mock GpioController mockGpio;
  @Mock GpioPinDigitalOutput mockLed1Pin;
  @Mock GpioPinDigitalOutput mockLed2Pin;
  @Mock GpioPinDigitalInput mockSwitch1Pin;
  @Mock GpioPinDigitalInput mockSwitch2Pin;
  @Mock GpioPinDigitalOutput mockOc1Pin;
  @Mock GpioPinDigitalOutput mockOc2Pin;
  @Mock GpioPinDigitalOutput mockM1PhasePin1;
  @Mock GpioPinDigitalOutput mockM1PhasePin2;
  @Mock GpioPinDigitalOutput mockM2PhasePin1;
  @Mock GpioPinDigitalOutput mockM2PhasePin2;
  @Mock GpioPinDigitalOutput mockTriggerPin;
  @Mock GpioPinDigitalInput mockEchoPin;

  @Rule public Timeout globalTimeout = Timeout.seconds(1);

  @Before
  public void before() {
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "LED1", PinState.LOW)).thenReturn(mockLed1Pin);
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "LED2", PinState.LOW)).thenReturn(mockLed2Pin);

    when(mockGpio.provisionDigitalInputPin(RaspiPin.GPIO_11, "Switch1")).thenReturn(mockSwitch1Pin);
    when(mockGpio.provisionDigitalInputPin(RaspiPin.GPIO_09, "Switch2")).thenReturn(mockSwitch2Pin);

    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "OC1", PinState.LOW)).thenReturn(mockOc1Pin);
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "OC2", PinState.LOW)).thenReturn(mockOc2Pin);

    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "M1Phase1", PinState.LOW)).thenReturn(mockM1PhasePin1);
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "M1Phase2", PinState.LOW)).thenReturn(mockM1PhasePin2);
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "M2Phase1", PinState.LOW)).thenReturn(mockM2PhasePin1);
    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "M2Phase2", PinState.LOW)).thenReturn(mockM2PhasePin2);

    when(mockGpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, "Trigger", PinState.LOW)).thenReturn(mockTriggerPin);
    when(mockGpio.provisionDigitalInputPin(RaspiPin.GPIO_23, "Echo", PinPullResistance.PULL_DOWN)).thenReturn(mockEchoPin);

    // Initialize our test object
    board = spy(new RasPiRobot3(mockGpio));

    doNothing().when(board).delayMicroseconds(Matchers.anyLong());
    doNothing().when(board).softPwmCreate(RaspiPin.GPIO_24);
    doNothing().when(board).softPwmCreate(RaspiPin.GPIO_14);
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_24, 0);
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_14, 0);

    // Verify constructor calls
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_08, "LED1", PinState.LOW);
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_07, "LED2", PinState.LOW);

    verify(mockGpio).provisionDigitalInputPin(RaspiPin.GPIO_11, "Switch1");
    verify(mockGpio).provisionDigitalInputPin(RaspiPin.GPIO_09, "Switch2");

    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_22, "OC1", PinState.LOW);
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_27, "OC2", PinState.LOW);

    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_17, "M1Phase1", PinState.LOW);
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_04, "M1Phase2", PinState.LOW);
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_10, "M2Phase1", PinState.LOW);
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_25, "M2Phase2", PinState.LOW);

    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_18, "Trigger", PinState.LOW);
    verify(mockGpio).provisionDigitalInputPin(RaspiPin.GPIO_23, "Echo", PinPullResistance.PULL_DOWN);

    verify(mockLed1Pin).setShutdownOptions(true, PinState.LOW);
    verify(mockLed2Pin).setShutdownOptions(true, PinState.LOW);
  }

  @After
  public void after() {
    verifyNoMoreInteractions(mockGpio);
  }

  @Test
  public void defaultConstructor() {
    // Verify results
    assertEquals(RasPiRobotBoard.MOTOR_DEFAULT_V / RasPiRobotBoard.BATTERY_DEFAULT_V, board.pwmScale, Float.MIN_NORMAL);
  }

  @Test
  public void constructorCustomVoltage() {
    final float batteryVoltage = 12.0f;
    final float motorVoltage = 3.0f;

    // Initialize mocks
    GpioPinDigitalOutput mockOutputPin = mock(GpioPinDigitalOutput.class);
    GpioController mockMotorGpio = mock(GpioController.class);
    when(mockMotorGpio.provisionDigitalOutputPin(isA(Pin.class), isA(String.class), isA(PinState.class))).thenReturn(mockOutputPin);

    // Unit under test
    board = new RasPiRobot3(mockMotorGpio, batteryVoltage, motorVoltage);

    // Verify results
    assertEquals(motorVoltage / batteryVoltage, board.pwmScale, Float.MIN_NORMAL);
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

  @Test
  public void switchesClosedSuccess() {
    // Initialize mocks
    when(mockSwitch1Pin.isLow()).thenReturn(true);
    when(mockSwitch2Pin.isLow()).thenReturn(true);

    // Unit under test
    final boolean switch1Closed = board.switch1Closed();
    final boolean switch2Closed = board.switch2Closed();

    // Verify results
    verify(mockSwitch1Pin).isLow();
    verify(mockSwitch2Pin).isLow();

    assertTrue(switch1Closed);
    assertTrue(switch2Closed);
  }

  @Test
  public void switchesOpenSuccess() {
    // Initialize mocks
    when(mockSwitch1Pin.isLow()).thenReturn(false);
    when(mockSwitch2Pin.isLow()).thenReturn(false);

    // Unit under test
    final boolean switch1Closed = board.switch1Closed();
    final boolean switch2Closed = board.switch2Closed();

    // Verify results
    verify(mockSwitch1Pin).isLow();
    verify(mockSwitch2Pin).isLow();

    assertFalse(switch1Closed);
    assertFalse(switch2Closed);
  }

  @Test
  public void setCollectorsLowSuccess() {
    // Unit under test
    board.setOc1(false);
    board.setOc2(false);

    // Verify results
    verify(mockOc1Pin).setState(false);
    verify(mockOc2Pin).setState(false);
  }

  @Test
  public void setCollectorsHighSuccess() {
    // Unit under test
    board.setOc1(true);
    board.setOc2(true);

    // Verify results
    verify(mockOc1Pin).setState(true);
    verify(mockOc2Pin).setState(true);
  }

  @Test
  public void setMotorsForwardFull() {
    // Initialize mocks
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_24, (int) (100 * board.pwmScale));
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_14, (int) (100 * board.pwmScale));

    // Unit under test
    board.setMotors(1.0f, MotorDirection.FORWARD, 1.0f, MotorDirection.FORWARD);

    // Verify results
    verify(board).softPwmCreate(RaspiPin.GPIO_24);
    verify(board).softPwmCreate(RaspiPin.GPIO_14);
    verify(board).softPwmWrite(RaspiPin.GPIO_24, (int) (100 * board.pwmScale));
    verify(board).softPwmWrite(RaspiPin.GPIO_14, (int) (100 * board.pwmScale));

    verify(mockM1PhasePin1).setState(false);
    verify(mockM1PhasePin2).setState(true);
    verify(mockM2PhasePin1).setState(false);
    verify(mockM2PhasePin2).setState(true);
  }

  @Test
  public void setMotorsReverseFull() {
    // Initialize mocks
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_24, (int) (100 * board.pwmScale));
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_14, (int) (100 * board.pwmScale));

    // Unit under test
    board.setMotors(1.0f, MotorDirection.REVERSE, 1.0f, MotorDirection.REVERSE);

    // Verify results
    verify(board).softPwmCreate(RaspiPin.GPIO_24);
    verify(board).softPwmCreate(RaspiPin.GPIO_14);
    verify(board).softPwmWrite(RaspiPin.GPIO_24, 0);
    verify(board).softPwmWrite(RaspiPin.GPIO_14, 0);
    verify(board).softPwmWrite(RaspiPin.GPIO_24, (int) (100 * board.pwmScale));
    verify(board).softPwmWrite(RaspiPin.GPIO_14, (int) (100 * board.pwmScale));

    verify(mockM1PhasePin1).setState(true);
    verify(mockM1PhasePin2).setState(false);
    verify(mockM2PhasePin1).setState(true);
    verify(mockM2PhasePin2).setState(false);
  }

  @Test
  public void setMotorsTurnHalf() {
    // Initialize mocks
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_24, (int) (50 * board.pwmScale));
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_14, (int) (50 * board.pwmScale));

    // Unit under test
    board.setMotors(0.5f, MotorDirection.FORWARD, 0.5f, MotorDirection.REVERSE);

    // Verify results
    verify(board).softPwmCreate(RaspiPin.GPIO_24);
    verify(board).softPwmCreate(RaspiPin.GPIO_14);
    verify(board).softPwmWrite(RaspiPin.GPIO_24, (int) (50 * board.pwmScale));
    verify(board).softPwmWrite(RaspiPin.GPIO_14, (int) (50 * board.pwmScale));

    verify(mockM1PhasePin1).setState(false);
    verify(mockM1PhasePin2).setState(true);
    verify(mockM2PhasePin1).setState(true);
    verify(mockM2PhasePin2).setState(false);
  }

  @Test
  public void setMotorsForwardReverseFull() {
    // Initialize mocks
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_24, (int) (100 * board.pwmScale));
    doNothing().when(board).softPwmWrite(RaspiPin.GPIO_14, (int) (100 * board.pwmScale));

    // Unit under test
    board.setMotors(1.0f, MotorDirection.FORWARD, 1.0f, MotorDirection.FORWARD);
    board.setMotors(1.0f, MotorDirection.REVERSE, 1.0f, MotorDirection.REVERSE);

    // Verify results
    verify(board).softPwmCreate(RaspiPin.GPIO_24);
    verify(board).softPwmCreate(RaspiPin.GPIO_14);
    verify(board, times(2)).softPwmWrite(RaspiPin.GPIO_24, 0);
    verify(board, times(2)).softPwmWrite(RaspiPin.GPIO_14, 0);
    verify(board, times(2)).softPwmWrite(RaspiPin.GPIO_24, (int) (100 * board.pwmScale));
    verify(board, times(2)).softPwmWrite(RaspiPin.GPIO_14, (int) (100 * board.pwmScale));

    verify(mockM1PhasePin1).setState(true);
    verify(mockM1PhasePin2).setState(false);
    verify(mockM2PhasePin1).setState(true);
    verify(mockM2PhasePin2).setState(false);

    verify(mockM1PhasePin1).setState(false);
    verify(mockM1PhasePin2).setState(true);
    verify(mockM2PhasePin1).setState(false);
    verify(mockM2PhasePin2).setState(true);
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

  @Test
  public void getRange2Cm() throws Exception {
    // 2 * 2 cm / speed of sound = 117.546798 microseconds
    final float expectedDistCm = RasPiRobotBoard.RANGE_MIN_CM;
    final int durationMicros = 118;

    // Initialize mocks
    when(mockEchoPin.getState()).thenReturn(PinState.LOW).thenReturn(PinState.HIGH).thenReturn(PinState.LOW);
    doReturn(0L).doReturn(0L).doReturn(0L).doReturn(durationMicros * 1000L).when(board).currentTimeNanos();

    // Unit under test
    final float estimatedDistCm = board.getRangeCm();

    // Verify results
    verify(mockTriggerPin).setState(PinState.HIGH);
    verify(mockTriggerPin).setState(PinState.LOW);
    verify(mockEchoPin, atLeastOnce()).getState();
    verify(board).delayMicroseconds(10);

    assertEquals(expectedDistCm, estimatedDistCm, 0.1f);
  }

  @Test
  public void getRange100Cm() throws Exception {
    // 2 * 100 cm / speed of sound = 5877.33992 microseconds
    final float expectedDistCm = 100.0f;
    final int durationMicros = 5877;

    // Initialize mocks
    when(mockEchoPin.getState()).thenReturn(PinState.LOW).thenReturn(PinState.HIGH).thenReturn(PinState.LOW);
    doReturn(0L).doReturn(0L).doReturn(0L).doReturn(durationMicros * 1000L).when(board).currentTimeNanos();

    // Unit under test
    final float estimatedDistCm = board.getRangeCm();

    // Verify results
    verify(mockTriggerPin).setState(PinState.HIGH);
    verify(mockTriggerPin).setState(PinState.LOW);
    verify(mockEchoPin, atLeastOnce()).getState();
    verify(board).delayMicroseconds(10);

    assertEquals(expectedDistCm, estimatedDistCm, 0.1f);
  }

  @Test
  public void getRange200Cm() throws Exception {
    // 2 * 200 cm / speed of sound = 11754.6798 microseconds
    final float expectedDistCm = 200.0f;
    final int durationMicros = 11754;

    // Initialize mocks
    when(mockEchoPin.getState()).thenReturn(PinState.LOW).thenReturn(PinState.HIGH).thenReturn(PinState.LOW);
    doReturn(0L).doReturn(0L).doReturn(0L).doReturn(durationMicros * 1000L).when(board).currentTimeNanos();

    // Unit under test
    final float estimatedDistCm = board.getRangeCm();

    // Verify results
    verify(mockTriggerPin).setState(PinState.HIGH);
    verify(mockTriggerPin).setState(PinState.LOW);
    verify(mockEchoPin, atLeastOnce()).getState();
    verify(board).delayMicroseconds(10);

    assertEquals(expectedDistCm, estimatedDistCm, 0.1f);
  }

  @Test
  public void getRange400Cm() throws IOException {
    // 2 * 400 cm / speed of sound = 23509.3597 microseconds
    final float expectedDistCm = RasPiRobotBoard.RANGE_MAX_CM;
    final int durationMicros = RasPiRobotBoard.MAX_PULSE_MICROS - 1;

    // Initialize mocks
    when(mockEchoPin.getState()).thenReturn(PinState.LOW).thenReturn(PinState.HIGH).thenReturn(PinState.LOW);
    doReturn(0L).doReturn(0L).doReturn(0L).doReturn(durationMicros * 1000L).when(board).currentTimeNanos();

    // Unit under test
    final float estimatedDistCm = board.getRangeCm();

    // Verify results
    verify(mockTriggerPin).setState(PinState.HIGH);
    verify(mockTriggerPin).setState(PinState.LOW);
    verify(mockEchoPin, atLeastOnce()).getState();
    verify(board).delayMicroseconds(10);

    assertEquals(expectedDistCm, estimatedDistCm, 0.1f);
  }

  @Test(expected = IOException.class)
  public void getRangeFailureNotConnected() throws Exception {
    // Initialize mocks
    when(mockEchoPin.getState()).thenReturn(PinState.LOW);
    doReturn(0L).doReturn(RasPiRobotBoard.ECHO_DELAY_MICROS * 1000L).when(board).currentTimeNanos();

    // Unit under test
    try {
      board.getRangeCm();
    } catch (final IOException e) {
      // Verify results
      verify(mockTriggerPin).setState(PinState.HIGH);
      verify(mockTriggerPin).setState(PinState.LOW);
      verify(mockEchoPin, atLeastOnce()).getState();
      verify(board).delayMicroseconds(10);

      throw e;
    }
  }

  @Test
  public void getRangeInfinity() throws Exception {
    // Initialize mocks
    when(mockEchoPin.getState()).thenReturn(PinState.LOW).thenReturn(PinState.HIGH);
    doReturn(0L).doReturn(0L).doReturn(0L).doReturn(0L).doReturn(RasPiRobotBoard.MAX_PULSE_MICROS * 1000L).when(board).currentTimeNanos();

    // Unit under test
    final float estimatedDistCm = board.getRangeCm();

    // Verify results
    verify(mockTriggerPin).setState(PinState.HIGH);
    verify(mockTriggerPin).setState(PinState.LOW);
    verify(mockEchoPin, atLeastOnce()).getState();
    verify(board).delayMicroseconds(10);

    assertTrue(Float.isInfinite(estimatedDistCm));
  }

  @Test
  public void shutdownSuccess() {
    // Unit under test
    board.shutdown();

    // Verify results
    verify(mockGpio).shutdown();
  }
}
