/*
 * The MIT License
 *
 * Copyright 2015 javaeeeee.
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
import java.math.BigDecimal;
import java.math.MathContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

/**
 * A resource class to convert currencies.
 *
 * @author Dmitry Noranovich
 */
@Path("/converter")
public class ConverterResource {

    /**
     * The name of the path parameter for the API key.
     */
    public static final String APP_ID = "app_id";
    /**
     * A part of an error message.
     */
    public static final String UNKNOWN_CURRENCY = "Unknown currency ";
    /**
     * Error message when no amount to convert was presented.
     */
    public static final String AMOUNT_IS_NECESSARY = "Amount is necessary.";

    /**
     * A Jersey client to connect to external API to get exchange rate.
     */
    private final Client client;
    /**
     * The URL to access exchange rate API.
     */
    private final String apiURL;
    /**
     * The key to access exchange rate API.
     */
    private final String apiKey;

    /**
     * A constructor.
     *
     * @param client Jersey client.
     * @param apiURL the URL to access exchange rate API.
     * @param apiKey he key to access exchange rate API.
     */
    public ConverterResource(Client client, String apiURL, String apiKey) {
        this.client = client;
        this.apiURL = apiURL;
        this.apiKey = apiKey;
    }

    /**
     * A subresource method used to convert currencies.
     *
     * The URI to access it looks like
     * <em>localhost:8080/converter/1.5?from=EUR&amp;to=CAD</em> where a double
     * number is provided as a path parameter and initial and final currencies
     * are provided as query parameters.
     *
     * @param amount a whole number, an amount to convert.
     * @param from the currency to be converted.
     * @param to the currency to which to convert
     * @return the result of conversion rounded to Cents.
     */
    @GET
    @Path("/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    //localhost:8080/converter/1.5?from=EUR&to=CAD
    public double convert(
            @PathParam("amount") Optional<Double> amount,
            @DefaultValue("USD") @QueryParam("from") String from,
            @DefaultValue("EUR") @QueryParam("to") String to
    ) {

        //A variable to store convertion rate of initial currency to US Dollar.
        Double fromRate;
        //A variable to store convertion rate of the desired currency
        //to US Dollar.
        Double toRate;
        //A string to store an error message if an incorrect currency
        //was provided.
        String errorMessage;

        //Check that amount to convert is present.
        if (!amount.isPresent()) {
            throw new BadRequestException(AMOUNT_IS_NECESSARY);
        }

        //Obtain data from external API.
        CurrencyData currencyData = client
                // returns WebTarget
                .target(apiURL)
                // returns WebTarget
                .queryParam(APP_ID, apiKey)
                //Can be done only in Enterprise and unlimited versions.
                //.queryParam("base", to)
                //returns Invocation.Builder
                .request(MediaType.APPLICATION_JSON)
                //returns CurrencyData
                .get(CurrencyData.class);

        //Obtain rates.
        fromRate = currencyData.getRates().get(from);
        toRate = currencyData.getRates().get(to);

        //Check that valid currency symbols were provided.
        if (fromRate == null || toRate == null) {
            errorMessage = UNKNOWN_CURRENCY
                    + (fromRate == null ? from : to);
            throw new BadRequestException(errorMessage);
        }

        //Convert and return the result.
        return convert(fromRate, toRate, amount.get());
    }

    /**
     * A method to convert from one currency to another. As we cannot change the
     * base currency in the free version, we specify rates relative to US Dollar
     * and than calculate the exchange rate for two currencies.
     *
     * @param fromRate Exchange rate of the currency to be converted to US
     * Dollar.
     * @param toRate Exchange rate of the currency to which to convert to US
     * Dollar
     * @param amount amount to convert.
     * @return the result of conversion rounded to Cents.
     */
    protected double convert(Double fromRate, Double toRate, Double amount) {
        //Calculate exchange rate.
        BigDecimal rate = (new BigDecimal(toRate.toString()))
                .divide(new BigDecimal(fromRate.toString()),
                        MathContext.DECIMAL64);

        BigDecimal value = new BigDecimal(amount);
        //Convert
        BigDecimal result = rate.multiply(value);
        //Return rounded to Cents.
        return result.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
}
