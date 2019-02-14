package com.octoperf.tools.retrofit;

import okhttp3.OkHttpClient;

/**
 *
 * Rest endpoints wrapper factory
 *
 * @author gerald
 */
public interface RestApiWrapperFactory {

  RestApiWrapper newWrapper(String baseUrl);

  RestApiWrapper newWrapper(OkHttpClient client, String baseUrl);
}
