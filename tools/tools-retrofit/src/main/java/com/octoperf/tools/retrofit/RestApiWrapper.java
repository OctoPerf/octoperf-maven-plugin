package com.octoperf.tools.retrofit;

/**
 *
 * Rest endpoints factory
 *
 * @author gerald
 */
@FunctionalInterface
public interface RestApiWrapper {

  <T> T create(final Class<T> service);
}
