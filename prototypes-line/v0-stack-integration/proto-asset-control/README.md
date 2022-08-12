# CYBNITY PROTOTYPE SYSTEMS DEPLOYMENT & RUN
## BUILD OF COMPONENTS & SYSTEMS
### Java components build
* From shell command line executed from the [root directory](../):

  ```shell
  mvn clean install
  ```

All the prototype components are built and available into the local Maven artifacts repository.

### Proto backend UI server
During coding activity of this component, the Vert.x modified contents can be considered by the runtime with the shell command lines:
* Package the server service:

  ```shell
  mvn package
  ```

* Start the server Vert.x server-side backend system:

  ```shell
  mvn exec:java
  ```

## START OF SYSTEMS
The start of [infrastructure services](../proto-infrastructures/README.md) is __required before to execute the start of prototyped systems__.

The CYBNITY prototyped systems are managed into the Maven sub-project named __proto-asset-control__.

### RTS Computation Unit server
Start the RTS Computation Unit server __from the proto-rts-computation-unit-server__ Maven sub-project directory:
- From Eclipse IDE (since src/test/java sub-directory), execute the `org.cybnity.application.asset_control.domain.system.AssetControlRTComputationUnitLauncher` class as Java application.

- or from shell command line (since the project's target sub-directory), execute the java main class with command line instruction  `java org.cybnity.application.asset_control.domain.system.AssetControlComputationUnit`.

When start process is executed:
  - The RTS unit server instance is started and automatically connected to the Domains Interactions broker (Kafka);
  - The java console output show the processed events received from the UI layer (e.g domain gateways) via all the integration chain.

### Domains Gateway servers
__From the proto-domain-gateway-server__ Maven sub-project directory, start the multi domains gateway (simulating in one project a gateway about UI capabilities, and a gateway about the asset control features domain) which manage the start of 2 Vert.x Verticles dedicated to each gateway instance:
- From Eclipse IDE (since src/test/java sub-directory), execute the `org.cybnity.application.MultipleDomainGatewayLauncher` class as Java application.

- or from shell command line (since the project's target sub-directory), execute the java main class with command line instruction  `java org.cybnity.application.MultipleDomainGateway`.

When start process is executed:
  - One UI capabilities gateway relative to Assets & Areas Protection is started (ready for process UI capabilities received from backend ui server over the Users Interactions Space);
    - Its console output show the collected events from UIS broker, treated by UI capability (via channels of UIS) or delegated to the dynamically identified applicative domain (e.g to Asset Control domain via event forward to its API channel entry point).
  - One applicative domain IO gateway relative to Asset Control domain is started (ready for process the security features supported by the Asset Control domain, coming from UI layer via the User Interactions Space and requiring dispatching on Domains Interactions space);
    - Its console output show the collected and dispatched to application layer.

### Backend UI server
__From the proto-backend-ui-server__ Maven sub-project directory, start the backend service ensuring the web exposure of web UI, and CYBNITY prototype UI layer (e.g html/javascript, ui services API via Vert.x event bus over HTTP):
- From Eclipse IDE (since src/test/java sub-directory), execute the `org.cybnity.application.asset_control.ui.system.backend.SockJSReactiveBackendServerLauncher` class as Java application.

- of from shell command line (since the project's target sub-directory), execute the java main class with command line instruction  `java org.cybnity.application.asset_control.ui.system.backend.SockJSReactiveBackendServer`.

When start process is executed:
  - The UI backend server instance is started and automatically expose an http service on port 8080;
  - The java console output show the http requests received from the front end layer (e.g web client or web browser) and treatment logs (e.g return of html static contents, delegation to UI capabilities layer).

# INTEGRATION TEST PROTOCOL
## TEST OF UI BACKEND ENDPOINTS (static contents)
### From shell command line
1. Test that ui backend give answer about sent parameter (over HTTP/GET protocol) without specific route to defined UI capability:

    ```shell
    curl -v http://localhost:8080/assetcontrol/
    ```

2. Test that UI backend give answer about sent parameter (over HTTP/GET protocol) from specific route defined about UI capability handled:

    ```shell
    curl -v http://localhost:8080/assetcontrol/\?name\="olivier"
    ```

### From web browser
1. Test that UI backend give static resources with default index.html reactive page display

    ```shell
    http://localhost:8080/static/
    ```

2. Test that UI backend give answer about sent parameter (over HTTP/GET protocol)

    ```shell
    http://localhost:8080/assetcontrol/?name=olivier
    ```

## TEST OF WEB UI (static contents and dynamic interactions)
The scope of test implementing the usage of static contents and event-bus interactions over Vert.x event bus is accessible with a web browser calling the url `http://localhost:8080/static/`.

When page is shown, some automatic requests are executed by the page in asynchronous process (e.g implemented via javascript instruction using the Vert.x web client connected to the ui backend server).
The send and received events are displayed into the web console of the web browser allowing to follow the JSON commands and responses exchanged between the frontend layer (browser) and the backend layer.

Each command automatically generated by this page, produce automatic and reactive treatment into the backend, domain gateways and RTS unit server components (e.g traces generated in each of their consoles).

## REDIS Users Interactions Space BROKER
### Tool for view of stored data
After success start of Redis docker image (project proto-infrastructures/proto-users-interactions-broker/redis-container), install a Redis desktop manager/browser allowing to navigate into the Redis database
- [Redisinsight](https://developer.redis.com/explore/redisinsightv2/getting-started/#step-1-download-redisinsight)

- or use redis-cli from started Redis image's console

    ```shell
    // Connect on Redis instance from client
    redis-cli
    // authentify user allowing to have data access (protected mode actived in redis.conf)
    auth username password
    // connect on the database number used by the backend (1 by default)
    select 1
    // List the registered event
    keys *
    // Read an event payload from its id (key identifier)
    get "<key number>"
    // Clear the database from any record
    flushdb
    // Read in real-time the events published into all channels
    psubscribe *
    //
    ```
