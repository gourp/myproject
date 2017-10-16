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

import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

/**
 * Tests of secured with basic authentication resources.
 *
 * @author Dmitry Noranovich
 */
public class SecuredHelloResourceTest {

    /**
     * Do some setup in order to provide tests with javax.ws.rs.client.Client in
     * order to access resources from Java.
     */
    @Rule
    public ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new SecuredHelloResource()).build();

    /**
     * Test of getGreeting method, of class SecuredHelloResource.
     */
    @Test
    public void testGetGreeting() {
        String expected = "Hello world!";
        //Obtain client
        Client client = resource.client();
        //Get actual resul string
        String actual = client
                .target("http://localhost:8080/secured_hello")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        //Do an assertion
        assertEquals(expected, actual);

    }

}
