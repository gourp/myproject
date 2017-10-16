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
package com.javaeeeee.dwstart.auth;

import java.util.Optional;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import com.javaeeeee.dwstart.core.User;
import io.dropwizard.auth.AuthenticationException;

/**
 * Class used for basic authentication of the users.
 *
 * @author Dmitry Noranovich
 */
public class GreetingAuthenticator
        implements Authenticator<BasicCredentials, User> {

    /**
     * User login.
     */
    private final String login;
    /**
     * User password.
     */
    private final String password;

    /**
     * Constructor.
     *
     * @param login user-ID
     * @param password password
     */
    public GreetingAuthenticator(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * The method authenticate users.
     *
     * @param credentials user credentials, in this case login and password.
     * @return Optional containing User if present and empty if absent.
     * @throws AuthenticationException exception thrown in the case of
     * authentication problems.
     */
    @Override
    public Optional<User> authenticate(BasicCredentials credentials)
            throws AuthenticationException {
        if (password.equals(credentials.getPassword())
                && login.equals(credentials.getUsername())) {
            return Optional.of(new User());
        } else {
            return Optional.empty();
        }
    }
}
