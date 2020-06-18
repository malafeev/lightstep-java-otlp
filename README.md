# Lightstep Java OTLP Example

## Usage
1. Set "service.name"  
    Currently, "service.name" can be specified only via environment variable `OTEL_RESOURCE_ATTRIBUTES`.  
    E.g.:
    ```shell script
    export OTEL_RESOURCE_ATTRIBUTES=service.name=test
    ```
1. Set "lightstep-access-token".  
    In the `App.java` file update variable `lsToken` to your lightstep access token:
    ```java
    // TODO: set LS access token
    final String lsToken = "your-token";
    ```
1. Build:
    ```shell script
    make build
    ```
1. Run:
    ```shell script
    make run
    ```   