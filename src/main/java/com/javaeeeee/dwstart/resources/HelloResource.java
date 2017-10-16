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
import com.javaeeeee.dwstart.core.Greeting;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Dmitry Noranovich
 */
@Path("/hello")
public class HelloResource {

    /**
     * Method is used to test the simplest functionality of a resource.
     *
     * @return a string "Hello world"
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting() {
        return "Hello world!";
    }

    /**
     * Method is used to greet using name plucked from the URL.
     *
     * @param name name of a person to greet
     * @return greeting "Hello" + name from URL
     */
    @Path("/path_param/{name}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTailoredGreetingPathParam(
            @PathParam(value = "name") String name) {
        return "Hello " + name;
    }

    /**
     * Default values are not supported by path parameters.
     * https://java.net/projects/jersey/lists/users/archive/2012-03/message/100
     *
     * @return "Hello world" string without quotes
     */
    @Path("/path_param")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTailoredGreetingPathParamDefault() {
        return "Hello world";
    }

    /**
     * A greeting method using query parameter.
     *
     * @param name name of a person to greet
     * @return greeting "Hello" + name from query
     */
    @Path("/query_param")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTailoredGreetingWithQueryParam(
            @QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return "Hello " + name.get();
        } else {
            return "Hello world";
        }
        //The same can be accomplished using or(...) method to provide the default value
        //return "Hello " + name.orElse("world");
    }

    /**
     * Resource method producing greeting in JSON format.
     *
     * @return a Greeting object
     */
    @Path("/hello_json")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting getJSONGreeting() {
        return new Greeting("Hello world!");
    }

    /**
     * Resource method producing greeting in JSON format with the same path as
     * the getGreeting method which produces plain text.
     *
     * @return a Greeting object
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting getJSONGreetingContentNegotiation() {
        return new Greeting("Hello world!");
    }

}
