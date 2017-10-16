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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * A simple representation class which is used to produce greetings in JSON
 * format.
 *
 * @author Dmitry Noranovich
 */
public class Greeting {

    /**
     * A greeting string to be produced in JSON format.
     */
    @JsonProperty
    private String greeting;

    /**
     * Default constructor.
     */
    public Greeting() {
    }

    /**
     * Constructor to create a Greeting resource.
     *
     * @param greeting greeting to show in JSON format.
     */
    public Greeting(String greeting) {
        this.greeting = greeting;
    }

    /**
     * A getter for greeting string.
     *
     * @return greeting string.
     */
    public String getGreeting() {
        return greeting;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.greeting);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Greeting other = (Greeting) obj;
        if (!Objects.equals(this.greeting, other.greeting)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Greeting{" + "greeting=" + greeting + '}';
    }

}
