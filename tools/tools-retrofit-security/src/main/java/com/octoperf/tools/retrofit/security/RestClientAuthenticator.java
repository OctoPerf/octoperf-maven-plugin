package com.octoperf.tools.retrofit.security;

import okhttp3.Interceptor;

/**
 * Manages the Rest client authentication refreshToken.
 *
 * <p>It allows the REST client to manage access to secured area automatically.</p>
 *
 * @author jerome
 * @author kojiro
 */
public interface RestClientAuthenticator extends Interceptor { // NOSONAR

}
