package com.octoperf.tools.retrofit.security;

import com.octoperf.tools.retrofit.RestApiWrapper;

/**
 *
 * Rest endpoints factory
 *
 * @author gerald
 */
public interface SecuredRestApiWrapper extends RestApiWrapper {

  RestClientAuthenticator authenticator();
}
