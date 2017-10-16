package com.javaeeeee.dwstart;

import com.javaeeeee.dwstart.auth.GreetingAuthenticator;
import com.javaeeeee.dwstart.core.Employee;
import com.javaeeeee.dwstart.core.User;
import com.javaeeeee.dwstart.db.EmployeeDAO;
import com.javaeeeee.dwstart.resources.ConverterResource;
import com.javaeeeee.dwstart.resources.EmployeesResource;
import com.javaeeeee.dwstart.resources.HelloResource;
import com.javaeeeee.dwstart.resources.SecuredHelloResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.ws.rs.client.Client;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * The application class.
 *
 * @author Dmitry Noranovich
 */
public class DWGettingStartedApplication
        extends Application<DWGettingStartedConfiguration> {

    /**
     * Hibernate bundle.
     */
    private final HibernateBundle<DWGettingStartedConfiguration> hibernateBundle
            = new HibernateBundle<DWGettingStartedConfiguration>(
                    Employee.class
            ) {

        @Override
        public DataSourceFactory getDataSourceFactory(
                DWGettingStartedConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }

    };

    /**
     * The main method of the application.
     *
     * @param args command-line arguments
     * @throws Exception any exception while executing the main() method.
     */
    public static void main(final String[] args) throws Exception {
        new DWGettingStartedApplication().run(args);
    }

    @Override
    public String getName() {
        return "DWGettingStarted";
    }

    @Override
    public void initialize(
            final Bootstrap<DWGettingStartedConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final DWGettingStartedConfiguration configuration,
            final Environment environment) {
        //Create Employee DAO.
        final EmployeeDAO employeeDAO
                = new EmployeeDAO(hibernateBundle.getSessionFactory());
        //Create Jersey client.
        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());
        //Register authenticator.

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new GreetingAuthenticator(configuration.getLogin(),
                                configuration.getPassword()))
                        .setRealm("SECURITY REALM")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        //Register a simple resource.
        environment.jersey().register(new HelloResource());
        //Register a secured resource.
        environment.jersey().register(new SecuredHelloResource());
        //Register a database-backed resource.
        environment.jersey().register(new EmployeesResource(employeeDAO));
        //Register a resource using Jersey client.
        environment.jersey().register(
                new ConverterResource(
                        client,
                        configuration.getApiURL(),
                        configuration.getApiKey())
        );
    }

}
