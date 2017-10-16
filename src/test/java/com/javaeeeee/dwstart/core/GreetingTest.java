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
package com.javaeeeee.dwstart.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import static io.dropwizard.testing.FixtureHelpers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Class tests serialization and deserialization of Greeting object.
 *
 * @author Dmitry Noranovich
 */
public class GreetingTest {

    /**
     * Object user to test marshalling and unmarshalling.
     */
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    /**
     * Test of serialization of class Greeting.
     *
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    //fails
    //https://github.com/dropwizard/dropwizard/issues/603
    //@Test
    public void testSerializeGreetingToJSON() throws JsonProcessingException {
        //Instantiate a greeting object
        final Greeting greeting = new Greeting("Hello world!");
        //Read json from
        String expected = fixture("fixtures/greeting.json");
        //Serialize object to JSON
        String actual = MAPPER.writeValueAsString(greeting);
        //assertEquals(expected, actual);
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Test of deserialization class Greeting.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testDeerializeGreetingFromJSON() throws IOException {
        //Instantiate a greeting object
        final Greeting expected = new Greeting("Hello world!");
        //Create an object from JSON
        Greeting actual = MAPPER.readValue(fixture("fixtures/greeting.json"),
                Greeting.class);
        assertEquals(expected, actual);
    }
}
