package com.octoperf.tools.retrofit;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * Simple implementation of X509TrustManager that trusts all certificates.
 * This class is only intended to be used for testing purposes.
 */
final class TrustingX509TrustManager implements X509TrustManager {
  private static final X509Certificate[] X509_CERTIFICATES = new X509Certificate[0];

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return X509_CERTIFICATES;
  }

  @Override
  public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
    // No-op, to trust all certs
  }

  @Override
  public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
    // No-op, to trust all certs
  }
}