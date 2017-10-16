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

import com.javaeeeee.dwstart.core.Greeting;
import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author Dmitry Noranovich
 */
public class HelloResourceTest {

    /**
     * Do some setup in order to provide tests with javax.ws.rs.client.Client in
     * order to access resources from Java.
     */
    @Rule
    public ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new HelloResource()).build();

    /**
     * Greeting object to test resources returning JSON response.
     */
    public static final Greeting GREETING = new Greeting("Hello world!");

    /**
     * Test of getGreeting method, of class HelloResource.
     */
    @Test
    public void testGetGreeting() {
        String expected = "Hello world!";
        //Obtain client from @Rule.
        Client client = resource.client();
        //Get WebTarget from client using URI of root resource.
        WebTarget helloTarget = client.target("http://localhost:8080/hello");
        //To invoke response we use Invocation.Builder
        //and specify the media type of representation asked from resource.
        Invocation.Builder builder = helloTarget.request(MediaType.TEXT_PLAIN);
        //Obtain response.
        Response response = builder.get();

        //Do assertions.
        assertEquals(Response.Status.OK, response.getStatusInfo());
        String actual = response.readEntity(String.class);
        assertEquals(expected, actual);

        //Same as above. Everything can be chained.
        actual = resource.client()
                .target("http://localhost:8080/hello")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals(expected, actual);
    }

    /**
     * Test of getTailoredGreetingPathParam method, of class HelloResource.
     */
    @Test
    public void testGetTailoredGreetingPathParam() {
        String name = "Melinda";
        String expected = "Hello " + name;
        WebTarget target = resource.client()
                .target("http://localhost:8080/hello")
                .path("path_param")
                .path(name);
        String actual = target
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals(expected, actual);
    }

    /**
     * Test of the case when path parameter is missing.
     */
    @Test
    public void testGetTailoredGreetingPathParamDefault() {
        String expected = "Hello world";
        WebTarget target = resource.client()
                .target("http://localhost:8080/hello")
                .path("path_param");
        String actual = target
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals(expected, actual);
    }

    /**
     * Test of getTailoredGreetingWithQueryParam method, of class HelloResource.
     */
    @Test
    public void testGetTailoredGreetingWithQueryParam() {
        String name = "Melinda";
        String expected = "Hello " + name;
        WebTarget target = resource.client()
                .target("http://localhost:8080/hello")
                .path("query_param")
                .queryParam("name", name);
        String actual = target
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals(expected, actual);
    }

    /**
     * Method is used to test a case when query parameter is missing.
     */
    @Test
    public void testGetTailoredGreetingWithQueryParamDefault() {
        String expected = "Hello world";
        WebTarget target = resource.client()
                .target("http://localhost:8080/hello")
                .path("query_param");
        String actual = target
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals(expected, actual);
    }

    /**
     * Test of getJSONGreeting method, of class HelloResource.
     */
    @Test
    public void testGetJSONGreeting() {
        Greeting actual = resource.client()
                .target("http://localhost:8080/hello")
                .path("/hello_json")
                .request(MediaType.APPLICATION_JSON)
                .get(Greeting.class);
        assertEquals(GREETING, actual);
    }

    /**
     * Test of getJSONGreetingContentNegotiation method, of class HelloResource.
     */
    @Test
    public void testGetJSONGreetingContentNegotiation() {
        Greeting actual = resource.client()
                .target("http://localhost:8080/hello")
                .request(MediaType.APPLICATION_JSON)
                .get(Greeting.class);
        assertEquals(GREETING, actual);
    }
}
