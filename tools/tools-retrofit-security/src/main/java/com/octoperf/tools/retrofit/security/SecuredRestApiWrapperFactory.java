package com.octoperf.tools.retrofit.security;

/**
 *
 * Secured rest endpoints wrapper factory
 *
 * @author gerald
 */
@FunctionalInterface
public interface SecuredRestApiWrapperFactory {

  SecuredRestApiWrapper newWrapper(String baseUrl, String apiKey);

}
