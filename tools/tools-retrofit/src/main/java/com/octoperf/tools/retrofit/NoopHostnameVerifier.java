package com.octoperf.tools.retrofit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public final class NoopHostnameVerifier implements HostnameVerifier {
  public static final NoopHostnameVerifier INSTANCE = new NoopHostnameVerifier();

  @Override
  public boolean verify(final String s, final SSLSession sslSession) {
    return true;
  }
}