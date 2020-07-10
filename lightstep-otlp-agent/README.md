# Lightstep OTLP Auto Exporter Agent

The Lightstep OTLP Agent is a configuration layer over OpenTelemetry Instrumentation Agent.


## Usage

### Service Name

It's required to define service name.  
Currently, it's possible only via environment variable `OTEL_RESOURCE_ATTRIBUTES`

```shell script
export OTEL_RESOURCE_ATTRIBUTES=service.name=test
```


### Configuration from system properties and environmental variables
Supported system properties and environmental variables:

* `LS_SATELLITE_URL` - satellite URL, default is _ingest.lightstep.com_.
* `LS_DEADLINE_MILLIS` - maximum amount of time the tracer should wait for a response from the collector when sending a report, default is _30000_.
* `LS_ACCESS_TOKEN` - token for Lightstep access
* `LS_USE_TLS` - use TLS or not, default is _true_.

### Run

java -javaagent:path/to/target/lightstep-opentelemetry-auto-<version>.jar \
     -jar myapp.jar


## License

[Apache 2.0 License](../LICENSE).
