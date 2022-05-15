## PURPOSE
This section develop, document and elaborate detailed designs progressively using agreed-on and rapid agile development techniques, addressing all components (business processes and related automated and manual controls, supporting IT applications, infrastructure services and technology products, and partners/suppliers). Ensure that the detailed design includes internal and external SLAs and OLAs.

This section presents the design specifications about the solutions and technology approved for build.

# WHY & WHAT TARGETED OBJECTIVES
## GOALS
Respond to the set of common requirements defined as frame of principles need to be maintained by any version of the CYBNITY official technology stack.

Definition of a minimal set of components allowing to:

- build and deliver a first version of CYBNITY demonstrator regarding UI cockpits principles;
- build and deliver a first version of a CYBNITY prototype of basic features for demonstration to potential partners' ecosystem.

## REQUIREMENTS
### Architecture

### Design & Demonstrator

### User Interface Layer
#### Dynamics
Should be real-time: frontend and backend components should maintain up-to-date states of domain events (e.g domain data state changes) and collaboration data (e.g shared data, contextualized capabilities during a security crisis situation) when collaboration data are managed in real-tim between several CYBNITY users connected via their CYBNITY cockpits).

#### Context and local persistence
Should proposed personal management of real-time data dedicated to the connected user in dedicated space (e.g session during cockpit access, when a user prepare a draft of a security information before to commit it in a transaction mode to a CYBNITY domain).

#### Integration with domains
Should be integrated in real-time with domain over bridge to event bus and data spaces shared with security domains (e.g centralized), in a standard mode allowing scalable backend/frontend services, but with a standard integration to the domain applications (e.g application components) via middleware platform (e.g Redis).

### Application Layer
#### Processing and distribution of load
Should start in a moderate time duration, but should have an acceptable reactivity SLA during the demonstration period allowing to check a realistic behavior of the UI Cockpits principles.

None requirement about support of data volume operated by the demonstrator using the stack (first phase of the CYBNITY project without challenges about the data load performance).

#### Access and usage via Internet
Should be usable via Internet for remote demonstration through a web browser hosted on a laptop or tablet, connected to Internet over a 4G bandwidth.

### Infrastructure Layer
#### Environments and locations
Should support implementation of applications capable to run on several infrastructure environment types (environment profiles):
  - **local**: developer's workstation during unit development activities;
  - **dev**: common development server shared between developers collaborative activities and integration tests during unit development of integrated features;
  - **test**: server environment (no mirror of production environment) dedicated to quick tests when lot of infrastructure or environment changes are developed;
  - **staging**: pre-production server environment (mirror of production environment) that contain next version of applications, used for final integration testing and for test/demonstration of deployment repetition or performance demonstration;
  - **production**: production (live) server environment.

#### Hosting and execution platform
Should be virtualizable (e.g Docker containerized) allowing an installation/execution on a public cloud host (e.g IAS, PAAS), or an independent laptop (e.g OS on x64, ARM64 processing unit) without Internet connection.

### Tooling & Supervision

# SELECTED IMPLEMENTATION ARCHITECTURE
Presentation of the retained implementation stack resulting of the analysis steps result and stack definition.

| TECHNOLOGY | USABLE ON PLATFORM TYPES | AUTHORIZED ENVIRONMENTS | AUTHORIZED ROLES |
| :--- | :--- | :--- | :--- |
| |Web, Monitoring, Centralized Storage, Middlewares|Local, Dev, Test, Staging, Production|UI Layer, Application/Domain Layer, Application/Integration/Persistence/Indexing/Monitoring/Operating/Physical Infrastructure, Delivery Tool|

![image](mpp-ui-technology-stack.png)

# STACK DEFINITION PROCESS CURRENT STATUS
Find here the current selected technologies set which are finally retained as valid stack after their analysis steps passed with success. This is the conclusions that are coming as outputs from the technology analysis stream executed.
The current status of technology already in the analysis stream give a general view which one is always in the race:

- Always in study `X`
- Opportunity as alternate challenger `ALT`
- Retained `WIN`

| Layer / Role | Component Name | IDEA STAGE - Implementation Technology Opportunity | ANALYSIS STAGE - Specification Validated | DESIGN STAGE - Design Tested | IMPL STAGE - Implementation Tested | Comments |
| :--- | :--- | :--- | :---: | :---: | :---: | :--- |
| UI | Web GUI Graphic Components Library | ReactBootstrap | X | | | Re-written bootstrap components in React[Doc](https://react-bootstrap.github.io/) |
| UI | Web GUI Reactive Frontend Library | [Vert.x SockJS Client](https://github.com/sockjs/sockjs-client) | `WIN` | | | JavaScript event bus bridge to Vert.x server-side reactive services |
| UI | Web GUI Reactive Frontend Library | ReactJS | `WIN` | | | HTML/CSS view from rendering components based with data changes auto-refresh, according to a View / Presenter pattern approach |
| UI | Client-Side Data Model Format | JSON | `WIN` | | | Data and command events (e.g Data Transform Object, domain events) |
| UI | Server-Side Reactive Backend Server | Eclipse Vert.x | `WIN` | | | UI reactive services (e.g UI capabilities) and processors (e.g UI behavior logic) managing access control, controllers collaboration (e.g event bus) and command/data events integration (via Redis client) with application(s) services layer via Application Domains Access Hub |
| APPLICATION INFRASTRUCTURE | Application Domains Access Hub | Redis | `WIN` | | | Async ports and protocol for application exchanges (e.g domain events and requests) |
| APPLICATION INFRASTRUCTURE | Application Domains Access Hub | Infinispan | X | | In-memory distributed data store storing, managing, and processing data; [Doc](https://infinispan.org/) |
| APPLICATION / DOMAIN | Application Services Layer | Eclipse Vert.x | `WIN` | | | Anti-Corruption Layer, remote proxy, input/output ports |
| APPLICATION / DOMAIN | Real-Time Data Stream Computation | Apache Flink | `WIN` | | | Stateful data processing, CEP, data pipeline |
| APPLICATION INFRASTRUCTURE | Application Services / Domain Model Integration Middleware | Kafka (features / domain / data events) in embedded mode | X | | | |
| APPLICATION INFRASTRUCTURE | Application Execution Container | Spring Boot | `WIN` | | | |
| APPLICATION INFRASTRUCTURE | Java Runtime Environment | Eclipse OpenJ9 8/11 | `WIN` | | | [Documentation](https://www.eclipse.org/openj9/docs/index.html)|
| APPLICATION INFRASTRUCTURE | Java Runtime Environment | Liberica JDK | `ALT` | | | |
| APPLICATION INFRASTRUCTURE | Single-Sign-On | Keycloack (SAML2.0 protocol for user authentication with SSO) | X | | | |
| APPLICATION INFRASTRUCTURE | UIAM | Identity/Access Mgt (federation, RBAC or ABAC based on attributes); SPML ? (to create users/permissions as access provisioning) | X | | | |
| APLLICATION INFRASTRUCTURE | Secrets In Dynamic Infrastructure | Vault (pki, credentials, passwords, api keys via secrets engines and authentication methods like JWT/LDAP/pki certif/Token) for partners API integration/connectors | X | | | |
| INTEGRATION INFRASTRUCTURE | Service Registry/Discovery & Configuration Management | Consul (service registration and discovery, health checking, distributed key-value store, TLS, ACL to data & api, key generation, dynamic load balancing, multi data centers support, data encryption, REST api, CLI, administration web-ui) | X | | | |
| INTEGRATION INFRASTRUCTURE | Remote Services Integration | Netflix Eureka (integration with spring, circuit breaker pattern from web ui, health checking) | X | | | |
| INTEGRATION INFRASTRUCTURE | Service Registry/Discovery & Configuration Management | Apache Zookeeper | X | | | |
| INTEGRATION INFRASTRUCTURE | Service Registry/Discovery & Configuration Management | Istio (settings mesh of container urls to maintain service information like security X509, to track/monitor services, load balancing, services discovery and registration) | X | | | |
| INTEGRATION INFRASTRUCTURE | External Systems Integration & APIs Management | API Umbrella | X | | | |
| INTEGRATION INFRASTRUCTURE | External Systems Integration & APIs Management | Gravitee.io | X | | | |
| INTEGRATION INFRASTRUCTURE | External Systems Integration & APIs Management | Apiman.io (async) | X | | | |
| INTEGRATION INFRASTRUCTURE | External Systems Integration & APIs Management | Akana | X | | | |
| PERSISTENCE INFRASTRUCTURE | Embedded Memory Database | Redis | X | | | |
| PERSISTENCE INFRASTRUCTURE | Distributed Memory Database | Kafka | X | | | |
| PERSISTENCE INFRASTRUCTURE | Centralized Data Persistence | MongoDB (autonomous dynamic backup/recovery of distributed data, collection-oriented repository equals to a set of objects) | X | | | |
| INDEXING INFRASTRUCTURE | Distributed Database Indexing | Elastic Search | X | | | |
| INDEXING INFRASTRUCTURE | Distributed Database Indexing | Apache Solr | X | | | |
| MONITORING INFRASTRUCTURE | APIs Monitoring | Kibana | X | | | |
| MONITORING INFRASTRUCTURE | APIs Monitoring | Grafana | X | | | |
| MONITORING INFRASTRUCTURE | APIs Monitoring | Jaeger | X | | | |
| OPERATING INFRASTRUCTURE | Operating System | Linux Distribution (Alpine, Debian, Ubuntu), 64bits kernel | X | | | |
| OPERATING INFRASTRUCTURE | Executable System | Docker Image | `WIN` | | | |
| OPERATING INFRASTRUCTURE | Infra-As-Code Execution | Microk8s (laptop, local dev deployment) compatible with Quarkus (server side) | X | | | |
| OPERATING INFRASTRUCTURE | Infra-As-Code Execution | Minikube (laptop, local dev deployment) | X | | | |
| OPERATING INFRASTRUCTURE | Infra-As-Code Execution | Kubernetes orchestrator (server deployment); Kubernetes (server deployment) or Microk8s (laptop, local dev deployment) compatible with Quarkus (server side) | X | | | |
| DELIVERY TOOL | System Images Build | Waypoint (command CLI for publish application docker images with build>deploy>release steps on Kubernetes) | X | | | |
| PHYSICAL INFRASTRUCTURE | Processing Unit | x86 | `WIN` | | | |
| PHYSICAL INFRASTRUCTURE | Processing Unit | ARM | `WIN` | | | |

# TECHNOLOGY ANALYSIS STREAM

## STEP 1 - Eligibility Check
During this step, the candidate technologies are identified and their study is mainly based on their documentations according to the promised announced by their editors/providers.
The first set of technologies identified like potential interesting candidate is coming from market proposal and/or team members experiences.

The eligibility criteria acceptance level are evaluated as:

- Incompatible or Discarded `KO`
- Acceptable `OK`
- Good `COOL`

The criteria checked about advantages (ADV) are:

- ADV01: Intellectual Property (IP) dependency and risks coming from supplier/license model or conflict with Apache 2.0;
- ADV02: virtual infrastructure compatibility, flexibility and/or optimization (e.g ARM, vector calculation, GPU);
- ADV03: simplicity of build, deployment, maintenance and tooling for developers;
- ADV04: sustainability and dynamics usage/developers community/project roadmap.

| CANDIDATE TECHNOLOGY | Description | Comments | ANALYSIS RESULT |
| :--- | :--- | :--- | :--- |
| [Apache Solr](https://solr.apache.org/) |Scalable, fault tolerant, providing distributed indexing, replication and load-balanced querying, automated failover and recovery, centralized configuration<br>**ADV02:** Docker and Kubernetes compatible| | |
| Jaeger | | | |
| [ReactJS](https://reactjs.org/) |Focused on View creation based on JS; update and render just the right components when the data changes (direct link between data state to UI); Declarative views; Build encapsulated components that manage their own state, then compose them; component logic is written in JavaScript<br>**ADV02:** usage of virtual DOM object (better performance than direct read of DOM); rendering on client or server side<br>**ADV03:** [ReactNative](https://reactnative.dev/) for native Android/iOS applications frontend; XML-like syntax called JSX; maintain internal state data; customizable rendering by components; maven plugin<br>**ADV04:** Facebook project/sponsor; in 2020, React had 1,390 contributions with Angular possessing 1,129 contributions|**Advantage:** data state stored in client side's memory; 10x faster than Angular for view refresh (thanks virtual DOM); data binding capabilities is better for large interactions and rich gui; large support community<br>**Disadvantage:** focused on view rendering, requiring integration with a MVC framework|`COOL`|
| Angular |MVC with JavaScript, view templates, components, services to server-side REST services; link of data in double ways<br>**ADV01:** MIT license<br>Front end framework<br>**ADV02:** usage of direct DOM object for rendering (less performance than ReactJS); rendering on client or server side<br>**ADV03:** [documentation](https://angular.io/guide/understanding-angular-overview); Single Page Application(SPA) including HTML template for page rendering, TypeScript classes for behavior as directives, CSS styling<br>**ADV04:** managed by Google team; open source community and popular from web developers|**Advantage:** integration with Vert.x, support for frontend in web/mobile/native desktop UI<br>**Disadvantage:** low performance with page contain interactive elements|`OK`|
| Spring WebFlux |Reactive-stack streams API to changes (i/o events, UI controller mouse events with concurrent connections); Spring Security Reactive; Spring Data Reactive Repositories (e.g Redis, Mongo) used over Reactive Stream Adapters and Netty, Servlet 3.1+ containers|**Advantage:** alternative to Vert.x for server-side reactive services into SpringBoot context|`OK`|
| Spring MVC |Servlet API and Servlet containers, synchronous blocking I/O architecture with a one-request-per threat model|**Disadvantage:** Synchronous blocking i/o less performance than react architecture|`KO`|
| Apache Flink |Stateful computations over data streams (e.g stream/batch processing, state mgt, event-time processing, live stream processing of data) for event-driven applications, data analytics or pipeline applications, stateful functions<br>**ADV02:** stand-alone cluster on bare-metal hardware, or deployment on YARN/Apache Mesos/Kubernetes; optimized for streaming than for batch processing (e.g better than Apache Spark for real-time processing)<br>**ADV03:** docker image; Flink web UI; detailed documentation; FlinkCEP; several connectors (e.g Kafka, ElasticSearch, RabbitMQ); Machine Learning (ML) library for ML pipelines; Maven support|**Advantage:** better than Apache Storm for real-time, and good complement to Apache Spark for large data volume processing<br>**Disadvantage:** Eclipse IDe not supported; IntelliJ recommended|`OK`|
| Eclipse Vert.x |**ADV01:** Eclipse Public License 2.0, Apache 2.0<br>Toolkit (not framework) for reactive applications on the JVM<br>**ADV03**: SockJS event bus (JavaScript client library) bridge to Vert.x; event bus bridge Java client library over TCP/WebSocket transport with Jackson/Gson codecs; Web Client asynchronous HTTP client for interactions with a web server; support of OAuth2, auth, JWT (JSON web tokens), OTP (multi-factor authentication), FIDO2 WebAuth (password less), SQL client auth, MongoDB auth, LDAP; Infinispan/Redis/MongoDB/Cassandra NoSQL, Kafka, MQTT, RabbitMQ, SMTP mail, Consul clients; monitoring/service discovery/config/circuit breaker clients|**Advantage:** event-driven microservice architecture optimized; application execution container (e.g web server) is not need; lot of plugings for reactive implementation with other systems; can be executed on any sub-container or direct JVM|`COOL`|
| Micronaut | Natively designed for cloud microservices<br>**ADV03:** run on Netty-based http server with possible swicth to Tomcat, Jetty or Undertow; messaging support for Apache Kafka, RabbitMQ, Nats.io; authorization strategies (basic, form login, JWT, LDAP); OAuth2 support; handle for distributed configuration, service discovery, client-side load balancing, distributed tracing, serverless functions<br>|**Disadvantage:** No support of SAML|`KO`|
| Spring Boot |**ADV01:** open source<br>**ADV02:** faster I/O operations compared to Quarkus; robust Dependency Injection Container; mature templates (e.g Spring MVC, ThymeLeaf)<br>**ADV03:** excellent documentation; auto-configuration; YAML support; template design (Thymeleaf, Freemarker, Mustache) pattern in pom.xml; more secure than Quarkus; embed Tomcat/Jetty without WAR file; metrics, health checks, externalized configuration; messaging support for ActiveMQ, RabbitMQ, Apache Kafka; authorization strategies (basic, form login, JWT, SAML, LDAP); OAuth2 support; actuators (expose operational information about running application like health, metric, info, dump, env...)<br>**ADV04:** community support|**Advantage:** SAML supported; several template languages<br>**Disadvantage:** higher startup times compared to Quarkus; lot of dependencies; memory usage|`OK`|
| Quarkus |Kubernetes-native java framework tailored for GraalVM and HotSpot<br>**ADV01:** Apache 2.0<br>**ADV02:** Boot time<br>**ADV03:** a lot of documentations; Graal or Substrate VM; hot reload (detect Java/resource files changes, recompile and deploy changes); JAX-RS enterprise standards built-on; extensions (e.g Vert.x, Redis Client); Eclipse plugin|**Disadvantage:** optimized for cloud deployment which is not an only CYBNITY targeted deployment model<br>**Advantage:** low memory footprint than Spring Boot, plugins for IoC with Redis/Kafka/Vault...|`KO`|
| VMWare Tanzu Gemfire |In-memory data management platform, real-time access to data-intensive applications through cloud architectures, memory/CPU/network resources pooling, dynamic replication, data partitioning, asynchronous event notifications, message delivery guarantee|**Disadvantage:** ex-pivotal project taken by VMWare; focus on compatibility with VMWare server-platform|`KO`|
| Riak KV |Autonomous dynamic backup/recovery of distributed data, collection-oriented repository equals to a set of objects.<br>Clusterised NoSQL database, key-value data model, real-time big data.<br>**ADV01:** Apache 2.0; commercial licenses for multi-cluster replication<br>**ADV02:** integration with Redis caching, Solr; Debian, Ubuntu compatible; TLS|**Disadvantage:** low documentation, tools, integration tools|`KO`|
| JavaFX |Native desktop or web application with CSS<br>**ADV01:** GPL licensed<br>**ADV02:** compatible with OpenJDK11+, snapcraft package; ARM compatible via [JFX embedded SDK](https://gluonhq.com/products/mobile/javafxports/get/)<br>**ADV03:** Scene Builder for UI design (FXML based interfaces, 2D/3D scenes); WebKit (browser engine supporting HTML5, CSS, JS, DOM, SVG) based component for JFX UI control (WebEngine and WebView); dynamic deployment of JRE from JFX app start over web page (Java webstart, or JS API); [TestFX](https://github.com/TestFX/TestFX/#features) for simulate user interactions and verify expected states of scene-graph nodes; [visual components and libraries](https://openjfx.io/); [Snapcraft project](https://github.com/jgneff/openjfx) about OpenJFX; [JafaFX framework](https://github.com/topics/javafx-frameworks)|**Advantage:** Webkit engine for web contents interpretation; reused OS native graphic stack via Glass WT|`OK`|
| Eclipse OpenJ9 8/11 |**ADV01:** Apache license 2.0, [Eclipse Public License 2.0](https://github.com/eclipse-openj9/openj9-docs/blob/master/LICENSE.md)<br>**ADV02:** compatible with AArch64 (v8-A) OpenJDK11/16 for ARM 64bits (Ubuntu 18.04/20.04); comparing to HotSpot, OpenJ9 features quicker start-up times and lower memory consumption; several OpenJDKs (11, 16, 17) supported by OpenJ8 JVM<br>**ADV03:** [Developer tooling](https://www.eclipse.org/openj9/docs/tool_migration/)<br>**ADV04:** runtime engine for many IBM’s enterprise products; new release delivered every quarter|**Advantage:** OpenJDK 11 [performance transparency](https://github.com/eclipse-openj9/openj9-website/blob/master/benchmark/openjdk11-daytrader7.md); Eclipse OMR supporting other languages (e.g JavaScript);Performance optimizations for footprint and startup time|`COOL`|
| AdoptOpenJDK (Eclipse Adoptium)|**ADV01:** GNU GPL license|**Advantage:** quality program and tools for build and maintain JDK platform supported by a open source community; Tool that make sense for Linux binary usage regarding an OpenJDK compiler used by T21 project; OpenJDK for specification build during development phase; OpenJ9 JVM for runtime to avoid risk linked to OpenJDK in case of conflict between AdoptOpenJDK project and Oracle about sub-license<br>**Disadvantage:** AdoptOpenJDK team need to negotiate with Oracle to maintain compatibility with Java SE for license contract OpenJDK Community TCK. In July 2021, none success discussion with Oracle. Migration to [Eclipse Foundation Adoptium](https://adoptium.net/); what competition with OpenJ9 project could arrive into Eclipse context?|`KO`|
| Liberica JDK |**ADV01:** JDK 100% open source license, no filed of use restrictions; liberica [NIK end user license agreement](https://bell-sw.com/liberica_nik_eula/); long-term support and guarantee of 8 years Liberica JDK lifetime<br>**ADV02:** ARM 64/32 bit (V8 AArch64); Linux, Debian and Ubuntu (from 12.04 LTS to 20.04 LTS) compatible; OpenJFX supported via LibericalFX (part of JDK and JRE bundles) for Windows, macOS, Linux (libavcodec and libavformat packages (available in Ubuntu 16.04+) are required for Media functionality); smallest Alpine images (lightweight JDK Alpine musl-based images of 41.5 MB) for deploying Docker containers and microservices<br>**ADV03:** tools for monitoring (Java Flight Recorder, Mission Control) by [Bellsoft for free](https://bell-sw.com/announcements/2020/06/24/Java-Flight-Recorder-a-gem-hidden-in-OpenJDK/)(Liberica Mission Control is GPL)<br>**ADV04:** [Java 8 (LTS) to Java 16 supported roadmap](https://bell-sw.com/pages/roadmap/); Unified Java Runtime approach for cloud, server, desktop; Supported HotSpot [JVMs features](https://bell-sw.com/pages/supported-configurations/); Binaries verified with TCK; 6 releases per yer concurrently with Oracle JDK; 30+ team members in San Jose, Boston, St-Petersburg; 7 members are OpenJDK contributors and 3 are OpenJDK reviewers|**Advantage:** diversity of supported Java versions runtime by JDK; Control and care of license topics (e.g sub-GPL contamination risks) by Bellsoft skills|`COOL`|
| Oracle GraalVM |Compilation of a OpenJFX application and a JVM into one native image ready for deployment like an integrated app/vm |**Disadvantage:** unknown Oracle licensing politic evolution; Oracle effort focused on server-side|`KO`|

## STEP 2 - Acceptance check regarding impact on other components required
Only the technologies set identified as acceptable or good are studied during this phase.
The goals of the acceptance test are:

- Check real compatibility with other architecture components that could be impacted by the selected candidates, and identify the requirements that will be generated to this components if the technology is selected for implementation;
- Measure the reality of the technical promises relative to the advantages announced by the technology provider and the eligibility check step;
- Measure of complementary differentiation criteria.

The specific observations executed and measures collected during an acceptance test of an eligible technology are:

- Complexity, specificity (e.g sub-platform version/type) and success installation (Install);
- Storage system (e.g quantity of space) impact regarding minimum size required (Storage);
- Duration for become operational since power off (Start);
- Computing Processing Unit consumption (CPU) without over-layer application execution;
- (RAM) impact when framework is at rest and only basic minimum processes and mandatory are started;
- Software tool types (e.g compatibility) and run with minimum requirements satisfied (Tools);

The acceptance level per differentiation criteria is evaluated as:

- Incompatible or failure `KO`
- Risk or unknown identified `RISK`
- Usable for target `OK`

| ELIGIBLE TECHNOLOGY | INSTALL | STORAGE | START | CPU | RAM | TOOLS | COMMENTS |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| Eclipse Vert.x SockJS Client | | | | | | | |
| ReactJS | | | | | | | |
| Eclipse Vert.x Core | | | | | | | |
| Eclipse Vert.x Web | | | | | | | |
| Redis | | | | | | | |
| Eclipse OpenJ9 8/11 | | | | | | | |
| Docker | | | | | | | |

## STEP 3 - Arbitration of the retained technologies set
During this step, the final selection of the solutions set is performed between the accepted ideal or alternative eligible technologies.

The deliverable is the output official new version of the technologies stack proposed for validation by demonstrator(s).
See the [technologies stack](v0-technologies-stack.md) page as eligible official new kit.

#
[Back To Home](README.md)