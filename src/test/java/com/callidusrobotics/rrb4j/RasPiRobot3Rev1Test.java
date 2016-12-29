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

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.callidusrobotics.rrb4j.RasPiRobot3Rev1;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RunWith(MockitoJUnitRunner.class)
public class RasPiRobot3Rev1Test {
  RasPiRobot3 board;

  @Mock GpioController mockGpio;
  @Mock GpioPinDigitalInput mockInputPin;
  @Mock GpioPinDigitalOutput mockOutputPin;
  @Mock GpioPinDigitalOutput mockOc2Pin;

  @Before
  public void before() {
    when(mockGpio.provisionDigitalInputPin(isA(Pin.class), isA(String.class))).thenReturn(mockInputPin);

    when(mockGpio.provisionDigitalOutputPin(not(eq(RaspiPin.GPIO_21)), not(eq("OC2")), isA(PinState.class))).thenReturn(mockOutputPin);
    when(mockGpio.provisionDigitalOutputPin(       RaspiPin.GPIO_21,          "OC2",       PinState.LOW)).thenReturn(mockOc2Pin);

    // Initialize our test object
    board = spy(new RasPiRobot3Rev1(mockGpio));

    // Verify constructor calls
    verify(mockGpio).provisionDigitalOutputPin(RaspiPin.GPIO_21, "OC2", PinState.LOW);
  }

  @Test
  public void setCollector2LowSuccess() {
    // Unit under test
    board.setOc2(false);

    // Verify results
    verify(mockOc2Pin).setState(false);
  }

  @Test
  public void setCollector2HighSuccess() {
    // Unit under test
    board.setOc2(true);

    // Verify results
    verify(mockOc2Pin).setState(true);
  }
}
