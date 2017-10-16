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

import java.util.HashMap;
import java.util.Map;

/**
 * A representation class to store data from the currency API.
 *
 * @author Dmitry Noranovich
 */
public class CurrencyData {

    /**
     * A string with a disclaimer.
     */
    private String disclaimer;
    /**
     * A string with a license.
     */
    private String license;
    /**
     * A timestamp.
     */
    private long timestamp;
    /**
     * The currency relative to which exchange rates are returned. In a free
     * version of the API one cannot change the base and it is US Dollar by
     * default.
     */
    private String base;
    /**
     * A map with exchange rate with keys like USD for US Dollar, EUR for Euro,
     * CAD for Canadian Dollar etc.
     */
    private Map<String, Double> rates = new HashMap<>();

    /**
     * A no-argument constructor.
     */
    public CurrencyData() {
    }

    /**
     * A constructor needed for testing.
     *
     * @param disclaimer A string with a disclaimer.
     * @param license A string with a license.
     * @param timestamp A timestamp.
     * @param base The currency relative to which exchange rates are returned.
     */
    public CurrencyData(String disclaimer, String license, long timestamp, String base) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
    }

    //Getters and setters.
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

}
