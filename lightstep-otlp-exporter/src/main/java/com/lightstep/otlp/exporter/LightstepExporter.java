package com.lightstep.otlp.exporter;

import io.opentelemetry.exporters.otlp.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

public class LightstepExporter {

  public static class Builder {
    private String accessToken = "";
    private String satelliteUrl = "ingest.lightstep.com";
    private long deadlineMillis = 30000;
    private boolean useTransportSecurity = true;

    /**
     * Sets the token for Lightstep access
     *
     * @param accessToken Your specific token for Lightstep access.
     * @return this builder's instance
     */
    public Builder setAccessToken(String accessToken) {
      this.accessToken = accessToken;
      return this;
    }

    /**
     * Sets the satellite url
     *
     * @param satelliteUrl satellite url
     * @return this builder's instance
     */
    public Builder setSatelliteUrl(String satelliteUrl) {
      this.satelliteUrl = satelliteUrl;
      return this;
    }

    /**
     * Overrides the default deadlineMillis with the provided value.
     *
     * @param deadlineMillis The maximum amount of time the tracer should wait for a response from
     * the collector when sending a report.
     * @return this builder's instance
     */
    public Builder setDeadlineMillis(long deadlineMillis) {
      this.deadlineMillis = deadlineMillis;
      return this;
    }

    public Builder useTransportSecurity(boolean useTransportSecurity) {
      this.useTransportSecurity = useTransportSecurity;
      return this;
    }

    /**
     * Constructs a new instance of the exporter based on the builder's values.
     *
     * @return a new exporter's instance
     */
    public OtlpGrpcSpanExporter build() {
      System.setProperty("otel.otlp.endpoint", satelliteUrl);
      System.setProperty("otel.otlp.use.tls", String.valueOf(useTransportSecurity));
      System.setProperty("otel.otlp.span.timeout", String.valueOf(deadlineMillis));
      System.setProperty("otel.otlp.metadata", "lightstep-access-token=" + accessToken);

      return OtlpGrpcSpanExporter.newBuilder()
          .readSystemProperties()
          .readEnvironmentVariables()
          .build();
    }

    /**
     * Installs exporter into tracer SDK default provider with batching span processor.
     */
    public void install() {
      BatchSpanProcessor spansProcessor = BatchSpanProcessor.newBuilder(this.build()).build();
      OpenTelemetrySdk.getTracerProvider().addSpanProcessor(spansProcessor);
    }

    /**
     * Creates builder from system properties and environmental variables.
     *
     * @return this builder's instance
     */
    public static Builder fromEnv() {
      final Builder builder = new Builder();
      builder.accessToken = getProperty("LS_ACCESS_TOKEN", "");
      builder.useTransportSecurity = Boolean.parseBoolean(getProperty("LS_USE_TLS", "true"));
      builder.deadlineMillis = Long.parseLong(
          getProperty("LS_DEADLINE_MILLIS", String.valueOf(30000)));
      builder.satelliteUrl = getProperty("LS_SATELLITE_URL", "ingest.lightstep.com");

      return builder;
    }

    private static String getProperty(String name, String defaultValue) {
      String val = System.getProperty(name, System.getenv(name));
      if (val == null || val.isEmpty()) {
        return defaultValue;
      }
      return val;
    }
  }

  /**
   * Creates a new builder instance.
   *
   * @return a new instance builder for this exporter
   */
  public static Builder newBuilder() {
    return new Builder();
  }

}
