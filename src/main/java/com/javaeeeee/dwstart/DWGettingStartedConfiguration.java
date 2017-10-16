package com.javaeeeee.dwstart;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Configuration class.
 *
 * @author Dmitry Noranovich
 */
public class DWGettingStartedConfiguration extends Configuration {

    /**
     * User login.
     */
    @NotEmpty
    private String login;
    /**
     * User password.
     */
    @NotEmpty
    private String password;
    /**
     * The URL to access exchange rate API.
     */
    @NotEmpty
    private String apiURL;
    /**
     * The key to access exchange rate API.
     */
    @NotEmpty
    private String apiKey;
    /**
     * A factory used to connect to a relational database management system.
     * Factories are used by Dropwizard to group together related configuration
     * parameters such as database connection driver, URI, password etc.
     */
    @NotNull
    @Valid
    private final DataSourceFactory dataSourceFactory
            = new DataSourceFactory();

    /**
     * Jersey client default configuration.
     */
    @Valid
    @NotNull
    private final JerseyClientConfiguration jerseyClientConfiguration
            = new JerseyClientConfiguration();

    /**
     *
     * @return Jersey Client
     */
    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClientConfiguration;
    }

    /**
     * Login getter.
     *
     * @return user login
     */
    @JsonProperty
    public String getLogin() {
        return login;
    }

    /**
     * Password getter.
     *
     * @return user password
     */
    @JsonProperty
    public String getPassword() {
        return password;
    }

    /**
     * A getter for the database factory.
     *
     * @return An instance of database factory deserialized from the
     * configuration file passed as a command-line argument to the application.
     */
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    /**
     * A getter for the URL of currency rates the API.
     *
     * @return the URL of currency rates the API.
     */
    @JsonProperty
    public String getApiURL() {
        return apiURL;
    }

    /**
     * A getter for the API key of currency rates the API.
     *
     * @return the API key of currency rates the API.
     */
    @JsonProperty
    public String getApiKey() {
        return apiKey;
    }

}
