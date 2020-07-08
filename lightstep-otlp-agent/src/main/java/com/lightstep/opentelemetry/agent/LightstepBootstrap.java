package com.lightstep.opentelemetry.agent;

import io.opentelemetry.auto.bootstrap.AgentBootstrap;
import java.lang.instrument.Instrumentation;

public class LightstepBootstrap {

  public static void premain(final String agentArgs, final Instrumentation inst) {
    System.out.println("LightstepBootstrap");
    System.setProperty("ota.exporter", "otlp");
    System.setProperty("otel.otlp.endpoint", "ingest.lightstep.com");
    AgentBootstrap.premain(agentArgs, inst);
  }

}
