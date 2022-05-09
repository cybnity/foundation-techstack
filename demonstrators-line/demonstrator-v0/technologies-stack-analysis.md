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
  - **server**: common development server shared between developers collaborative activities and integration tests during unit development of integrated features;
  - **test**: server environment (no mirror of production environment) dedicated to quick tests when lot of infrastructure or environment changes are developed;
  - **staging**: pre-production server environment (mirror of production environment) that contain next version of applications, used for final integration testing and for test/demonstration of deployment repetition or performance demonstration;
  - **production**: production (live) server environment.

#### Hosting and execution platform
Should be virtualizable (e.g Docker containerized) allowing an installation/execution on a public cloud host (e.g IAS, PAAS), or an independent laptop (e.g OS on x64, ARM64 processing unit) without Internet connection.

### Tooling & Supervision

# STACK DEFINITION CURRENT STATUS
Find here the current selected technologies set which are finally retained as valid stack after their analysis steps passed with success. This is the conclusions that are coming as outputs from the technology analysis stream executed.
The current status of technology already in the analysis stream give a general view which one is always in the race:

- Always in study `X`
- Opportunity as alternate challenger `ALT`
- Retained `WIN`

| Architecture Layer | Component Name | IDEA STAGE - Implementation Technology Opportunity | ANALYSIS STAGE - Specification Validated | DESIGN STAGE - Design Tested | IMPL STAGE - Implementation Tested | Comments, rejection cause on observations made |
| :--- | :--- | :--- | :---: | :---: | :---: | :--- |
| UI | Web GUI Framework | HTML/Javascript/JQuery | X | | | |
| UI | Web GUI Framework | Struts | X | | | |
| UI | Web GUI Framework | Spring MVC | X | | | |
| UI | Web GUI Framework | React (javascript) | X | | | |
| UI | Web GUI Framework | Angular (javascript) | X | | | |
| UI | Native Graphic Components Library | BootstrapFX (bootstrap components, CSS) | X | X | | |
| UI | Native Graphic Components Library | TileFX (control, dashboard) | X | X | | |
| UI | Native Graphic Components Library | MaterialFX (widget, panels, combo-box, dialogs, fields, pickers) | X | X | | |
| UI | Native GUI Framework | JavaFX | `WIN` | X | | [APIs, docs](https://docs.oracle.com/javase/8/javase-clienttechnologies.htm) |
| APPLICATION | Distributed Stateful Application | Apache Flink | X | | | |
| APPLICATION | Distributed Stateful Application | Vert.x | X | | | |
| APPLICATION | Single-Sign-On | Keycloack (SAML2.0 protocol for user authentication with SSO) | X | | | |
| APPLICATION | UIAM | Identity/Access Mgt (federation, RBAC or ABAC based on attributes); SPML ? (to create users/permissions as access provisioning) | X | | | |
| APPLICATION | Application Execution Container | Quarkus (Kubernetes native java stack for GraalVM and Hotspot JVM, low memory footprint than Spring Boot, plugins for IoC with Redis/Kafka/Vault...) | X | | | |
| APPLICATION | Java Runtime Environment | Eclipse OpenJ9 8/11 | `WIN` | X | | [Documentation](https://www.eclipse.org/openj9/docs/index.html)|
| APPLICATION | Java Runtime Environment | Liberica JDK | `ALT` | X | | |
| INFRASTRUCTURE | Remote Services Integration | Netflix Eureka (integration with spring, circuit breaker pattern from web ui, health checking) | X | | | |
| INFRASTRUCTURE | Secrets In Dynamic Infrastructure | Vault (pki, credentials, passwords, api keys via secrets engines and authentication methods like JWT/LDAP/pki certif/Token) for partners API integration/connectors | X | | | |
| INFRASTRUCTURE | Service Registry/Discovery & Configuration Management | Consul (service registration and discovery, health checking, distributed key-value store, TLS, ACL to data & api, key generation, dynamic load balancing, multi data centers support, data encryption, REST api, CLI, administration web-ui) | X | | | |
| INFRASTRUCTURE | Service Registry/Discovery & Configuration Management | Apache Zookeeper | X | | | |
| INFRASTRUCTURE | Service Registry/Discovery & Configuration Management | Istio (settings mesh of container urls to maintain service information like security X509, to track/monitor services, load balancing, services discovery and registration) | X | | | |
| INFRASTRUCTURE | External Systems Integration & APIs Management | API Umbrella | X | | | |
| INFRASTRUCTURE | External Systems Integration & APIs Management | Gravitee.io | X | | | |
| INFRASTRUCTURE | External Systems Integration & APIs Management | Apiman.io (async) | X | | | |
| INFRASTRUCTURE | External Systems Integration & APIs Management | Akana | X | | | |
| INFRASTRUCTURE | UI / Microservices Integration Middleware | Redis (capabilities / processing events) | X | | | |
| INFRASTRUCTURE | Microservice / Domain Model Integration Middleware | Kafka (features / domain / data events) in embedded mode | X | | | |
| INFRASTRUCTURE | Executable System | Docker Image | X | | | |
| INFRASTRUCTURE | Embedded Memory Database | Redis | X | | | |
| INFRASTRUCTURE | Distributed Memory Database | Redis | X | | | |
| INFRASTRUCTURE | Distributed Memory Database | Kafka | X | | | |
| INFRASTRUCTURE | Distributed Memory Database | Elastic Search | X | | | |
| INFRASTRUCTURE | Centralized Data Persistence | MongoDB (autonomous dynamic backup/recovery of distributed datas, collection-oriented repository equals to a set of objects) | X | | | |
| INFRASTRUCTURE | Centralized Data Persistence | Riak (autonomous dynamic backup/recovery of distributed datas, collection-oriented repository equals to a set of objects) | X | | | |
| DELIVERY TOOL | System Images Build | Waypoint (command CLI for publish application docker images with build>deploy>release steps on Kubernetes) | X | | | |
| OPERATING TOOL | APIs Monitoring | Kibana | X | | | |
| OPERATING TOOL | APIs Monitoring | Grafana | X | | | |
| OPERATING TOOL | APIs Monitoring | Jaeger | X | | | |
| INFRASTRUCTURE | Infra-As-Code Execution | Kubernetes orchestrator (server deployment); Kubernetes (server deployment) or Microk8s (laptop, local dev deployment) compatible with Quarkus (server side) | X | | | |
| INFRASTRUCTURE | Operating System | Linux Distribution (Alpine, Debian, Ubuntu), 64bits kernel | X | | | |
| PHYSICAL INFRASTRUCTURE | Processing Unit | X | x86 | `WIN` | | |
| PHYSICAL INFRASTRUCTURE | Processing Unit | X | ARM | `WIN` | | |

# IMPLEMENTATION ARCHITECTURE
Presentation of the retained implementation stack resulting of the analysis steps results.

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
| VMWare Tanzu Gemfire |In-memory data management platform, real-time access to data-intensive applications through cloud architectures, memory/CPU/network resources pooling, dynamic replication, data partitioning, asynchronous event notifications, message delivery guarantee|**Disadvantage:** Ex-pivotal project taken by VMWare; focus on compatibility with VMWare server-platform|`KO`|
| Riak | | | |
| Jaeger | | | |
| JavaFX |Native desktop or web application with CSS<br>**ADV01:** GPL licensed<br>ADV02: compatible with OpenJDK11+, snapcraft package; ARM compatible via [JFX embedded SDK](https://gluonhq.com/products/mobile/javafxports/get/)<br>**ADV03:** Scene Builder for UI design (FXML based interfaces, 2D/3D scenes); WebKit (browser engine supporting HTML5, CSS, JS, DOM, SVG) based component for JFX UI control (WebEngine and WebView); dynamic deployment of JRE from JFX app start over web page (Java webstart, or JS API); [TestFX](https://github.com/TestFX/TestFX/#features) for simulate user interactions and verify expected states of scene-graph nodes; [visual components and libraries](https://openjfx.io/); [Snapcraft project](https://github.com/jgneff/openjfx) about OpenJFX |**Advantage:** Get advantages of Webkit engine for web contents interpretation; reused OS native graphic stack via Glass WT|`OK`|
| Eclipse OpenJ9 8/11 |**ADV01:** Apache license 2.0, [Eclipse Public License 2.0](https://github.com/eclipse-openj9/openj9-docs/blob/master/LICENSE.md)<br>**ADV02:** compatible with AArch64 (v8-A) OpenJDK11/16 for ARM 64bits (Ubuntu 18.04/20.04); comparing to HotSpot, OpenJ9 features quicker start-up times and lower memory consumption; several OpenJDKs (11, 16, 17) supported by OpenJ8 JVM<br>**ADV03:** [Developer tooling](https://www.eclipse.org/openj9/docs/tool_migration/)<br>**ADV04:** runtime engine for many IBM’s enterprise products; new release delivered every quarter|**Advantage:** OpenJDK 11 [performance transparency](https://github.com/eclipse-openj9/openj9-website/blob/master/benchmark/openjdk11-daytrader7.md); Eclipse OMR supporting other languages (e.g JavaScript);Performance optimizations for footprint and startup time|`COOL`|
| AdoptOpenJDK (Eclipse Adoptium)| GNU GPL license|**Advantage:** Quality program and tools for build and maintain JDK platform supported by a open source community; Tool that make sense for Linux binary usage regarding an OpenJDK compiler used by T21 project; OpenJDK for specification build during development phase; OpenJ9 JVM for runtime to avoid risk linked to OpenJDK in case of conflict between AdoptOpenJDK project and Oracle about sub-license<br>**Disadvantage:** AdoptOpenJDK team need to negotiate with Oracle to maintain compatibility with Java SE for license contract OpenJDK Community TCK. In July 2021, none success discussion with Oracle. Migration to [Eclipse Foundation Adoptium](https://adoptium.net/); what competition with OpenJ9 project could arrive into Eclipse context?|`KO`|
| Liberica JDK |**ADV01:** JDK 100% open source license, no filed of use restrictions; liberica [NIK end user license agreement](https://bell-sw.com/liberica_nik_eula/); long-term support and guarantee of 8 years Liberica JDK lifetime<br>**ADV02:** ARM 64/32 bit (V8 AArch64); Linux, Debian and Ubuntu (from 12.04 LTS to 20.04 LTS) compatible; OpenJFX supported via LibericalFX (part of JDK and JRE bundles) for Windows, macOS, Linux (libavcodec and libavformat packages (available in Ubuntu 16.04+) are required for Media functionality); smallest Alpine images (lightweight JDK Alpine musl-based images of 41.5 MB) for deploying Docker containers and microservices<br>**ADV03:** tools for monitoring (Java Flight Recorder, Mission Control) by [Bellsoft for free](https://bell-sw.com/announcements/2020/06/24/Java-Flight-Recorder-a-gem-hidden-in-OpenJDK/)(Liberica Mission Control is GPL)<br>**ADV04:** [Java 8 (LTS) to Java 16 supported roadmap](https://bell-sw.com/pages/roadmap/); Unified Java Runtime approach for cloud, server, desktop; Supported HotSpot [JVMs features](https://bell-sw.com/pages/supported-configurations/); Binaries verified with TCK; 6 releases per yer concurrently with Oracle JDK; 30+ team members in San Jose, Boston, St-Petersburg; 7 members are OpenJDK contributors and 3 are OpenJDK reviewers|**Advantage:** Diversity of supported Java versions runtime by JDK; Control and care of license topics (e.g sub-GPL contamination risks) by Bellsoft skills|`COOL`|
| Oracle GraalVM | Compilation of a OpenJFX application and a JVM into one native image ready for deployment like an integrated app/vm |**Disadvantage:** Unknown Oracle licensing politic evolution;<br>Oracle effort focused on server-side|`KO`|

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
| | | | | | | | |

## STEP 3 - Arbitration of the retained technologies set
During this step, the final selection of the solutions set is performed between the accepted ideal or alternative eligible technologies.

The deliverable is the output official new version of the technologies stack proposed for validation by demonstrator(s).
See the [technologies stack](v0-technologies-stack.md) page as eligible official new kit.

#
[Back To Home](README.md)