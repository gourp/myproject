/*
 * The MIT License
 *
 * Copyright 2015 Dmitry Noranovich.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.javaeeeee.dwstart.resources;

import java.util.Optional;
import com.javaeeeee.dwstart.core.CurrencyData;
import java.util.Date;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * A test for the ConverterResource class.
 *
 * @author Dmitry Noranovich
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverterResourceTest {

    /**
     * A dummy string to pass where a string is necessary, but its content is
     * not important.
     */
    public static final String DUMMY = "dummy";
    /**
     * A symbol for US Dollar.
     */
    private static final String USD = "USD";
    /**
     * A symbol for Canadian Dollar.
     */
    private static final String CAD = "CAD";
    /**
     * A symbol for Euro.
     */
    private static final String EUR = "EUR";
    /**
     * A wrong currency symbol.
     */
    private static final String WRONG_CURRENCY = "ZZ02";
    /**
     * API URL.
     */
    private final static String API_URL = "anyURL";
    /**
     * API key.
     */
    private final static String API_KEY = "anyKey";

    /**
     * Jersey client.
     */
    private Client client;
    /**
     * A builder to mock.
     */
    private Invocation.Builder builder;
    /**
     * A WebTarget to mock.
     */
    private WebTarget target;
    /**
     * System Under Test.
     */
    private ConverterResource sut = null;
    /**
     * A representation class.
     */
    private CurrencyData currencyData;
    /**
     * Argument captor for string arguments passed to internal methods.
     */
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    /**
     * A rule to check whether an exception was thrown.
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Before class initialization.
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * After class cleanup.
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Before method initialization.
     */
    @Before
    public void setUp() {
        client = mock(Client.class);
        builder = mock(Invocation.Builder.class);
        target = mock(WebTarget.class);
        sut = new ConverterResource(client, API_URL, API_KEY);
        currencyData
                = new CurrencyData(DUMMY, DUMMY, new Date().getTime(), USD);
        currencyData.getRates().put(CAD, 1.333576);
        currencyData.getRates().put(EUR, 0.929969);
    }

    /**
     * After method cleanup.
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of convert method, of class ConverterResource.
     */
    @Test
    public void testConvertHappyPath() {
        System.out.println("convert");
        Optional<Double> amount = Optional.of(1.5);

        //given
        when(builder.get(CurrencyData.class)).thenReturn(currencyData);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(target.queryParam(
                Matchers.eq(ConverterResource.APP_ID), Matchers.anyString()
        )).thenReturn(target);
        when(client.target(Matchers.anyString())).thenReturn(target);

        //when
        double result = sut.convert(amount, EUR, CAD);

        //then
        verify(client).target(Matchers.anyString());
        verify(client).target(stringCaptor.capture());
        assertEquals(API_URL, stringCaptor.getValue());
        verify(client.target(Matchers.anyString()))
                .queryParam(Matchers.anyString(), stringCaptor.capture());
        assertEquals(API_KEY, stringCaptor.getValue());
        double expResult = 2.15;
        assertEquals(expResult, result, 0.);
    }

    /**
     * A test to check a sad path when no amount to convert was provided.
     */
    public void testConvertSadPathNoAmount() {
        System.out.println("convert");
        Optional<Double> amount = Optional.empty();
        exception.expect(BadRequestException.class);
        exception.expectMessage(ConverterResource.AMOUNT_IS_NECESSARY);
        sut.convert(amount, EUR, CAD);
    }

    /**
     * A test to check a sad path when a wrong currency symbol was provided.
     */
    public void testConvertSadPathWrongCurrency() {
        System.out.println("convert");
        Optional<Double> amount = Optional.of(1.5);

        //given
        when(builder.get(CurrencyData.class)).thenReturn(currencyData);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(target.queryParam(
                Matchers.eq(ConverterResource.APP_ID), Matchers.anyString()
        )).thenReturn(target);
        when(client.target(Matchers.anyString())).thenReturn(target);
        exception.expect(BadRequestException.class);
        exception.expectMessage(
                ConverterResource.UNKNOWN_CURRENCY + WRONG_CURRENCY
        );
        sut.convert(amount, EUR, CAD);
    }

    /**
     * Test of convert method, of class ConverterResource.
     */
    @Test
    public void testConvertDoubles() {
        System.out.println("convert");
        Double fromRate = 1.;
        Double toRate = 1.333576;
        Double amount = 1.;
        double expResult = 1.33;
        double result = sut.convert(fromRate, toRate, amount);
        assertEquals(expResult, result, 0.);
    }
    
    /**
     * Test of convert method, of class ConverterResource.
     */
    @Test
    public void testConvertDoublesReverse() {
        System.out.println("convert");
        Double fromRate = 1.333576;
        Double toRate = 1.;
        Double amount = 1.;
        double expResult = 0.75;
        double result = sut.convert(fromRate, toRate, amount);
        assertEquals(expResult, result, 0.);
    }

    /**
     * Test of convert method, of class ConverterResource.
     */
    @Test
    public void testConvertDoublesWithSensibleData() {
        System.out.println("convert");
        Double fromRate = 1.32;
        Double toRate = .75;
        Double amount = 15.3;
        double expResult = amount * toRate / fromRate;
        double result = sut.convert(fromRate, toRate, amount);
        assertEquals(expResult, result, 0.01);
    }

}
