## PURPOSE
This section develop, document and elaborate detailed designs progressively using agreed-on and rapid agile development techniques, addressing all components (business processes and related automated and manual controls, supporting IT applications, infrastructure services and technology products, and partners/suppliers). Ensure that the detailed design includes internal and external SLAs and OLAs.

This section presents the design specifications about the solutions and technology approved for build.

# LONG TERM GOALS AND REQUIREMENTS
Presents the standard goals that should be reached and benefits that are targeted to be collected from the official technology stack by CYBNITY projects.
A set of common requirements is defined as frame of principles need to be maintained by any version of the CYBNITY official technology stack.
It's possible that new goals entry enhance the common goals, regarding specific technology and/or implementation layers during a new version development of the standards catalog.

### OBJECTIVES
Respond to the set of common requirements defined as frame of principles need to be maintained by any version of the CYBNITY official technology stack.
Definition of a minimal set of components allowing to:
- build and deliver a first version of CYBNITY demonstrator regarding UI cockpits principles;
- build and deliver a first version of a CYBNITY prototype of basic features for demonstration to potential partners' ecosystem.

### REQUIREMENTS
#### ARCHITECTURE
Should allow build of a first implementation architecture respecting the target architecture defined by Foundation project ready for deployment as an Internet platform usable for demonstration and motivation of partnerships.

#### DESIGN & DEMONSTRATOR
Should provide basic technologies set allowing the development of a first prototype aligned with the visual demonstrator of Cockpits concept.

#### USER INTERFACE LAYER
**Dynamics**
Should be real-time: frontend and backend components should maintain up-to-date states of domain events (e.g domain data state changes) and collaboration data (e.g shared data, contextualized capabilities during a security crisis situation) when collaboration data are managed in real-time between several CYBNITY users connected via their CYBNITY cockpits).

**Context and local persistence**
Should proposed personal management of real-time data dedicated to the connected user in dedicated space (e.g session during cockpit access, when a user prepare a draft of a security information before to commit it in a transaction mode to a CYBNITY domain).

**Integration with domains**
Should be integrated in real-time with domain over bridge to event bus and data spaces shared with security domains (e.g centralized), in a standard mode allowing scalable backend/frontend services, but with a standard integration to the domain applications (e.g application components) via middleware platform (e.g Redis).

#### APPLICATION LAYER
**Processing and distribution of load**
Should start in a moderate time duration, but should have an acceptable reactivity SLA during the demonstration period allowing to check a realistic behavior of the UI Cockpits principles.
None requirement about support of data volume operated by the demonstrator using the stack (first phase of the CYBNITY project without challenges about the data load performance).

**Access and usage via Internet**
Should be usable via Internet for remote demonstration through a web browser hosted on a laptop or tablet, connected to Internet over a 4G bandwidth.

#### INFRASTRUCTURE LAYER
**Environments and locations**
Should support implementation of applications capable to run on several infrastructure environment types (environment profiles):
  - **local**: developer's workstation during unit development activities;
  - **dev**: common development server shared between developers collaborative activities and integration tests during unit development of integrated features;
  - **test**: server environment (no mirror of production environment) dedicated to quick tests when lot of infrastructure or environment changes are developed;
  - **staging**: pre-production server environment (mirror of production environment) that contain next version of applications, used for final integration testing and for test/demonstration of deployment repetition or performance demonstration;
  - **production**: production (live) server environment.

**Hosting and execution platform**
Should be virtualizable (e.g Docker containerized) allowing an installation/execution on a public cloud host (e.g IAS, PAAS), or an independent laptop (e.g OS on x64, ARM64 processing unit) without Internet connection.

#### TOOLING & SUPERVISION
Should allow definition and test of basic software factory implementation allowing to deliver Dockerized systems.
None supervision requirements required regarding the step of the CYBNITY Foundation project.

# CURRENT MPP OFFICIAL VERSION
- Version:
- Released at:
- Status:
- Documentation

## LAYER ARCHITECTURE

### Event-Based Principles
![image](mvf-event-based-architecture.png)

## STANDARD TECHNOLOGIES STACK
Presentation of the technologies and frameworks used for implementation of the CYBNITY software projects.

### Application Layer

| Name | Usage | Solution Type | Implementation ID | Technology | Runtime Infrastructure Target |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | | | | |


### Infrastructure Layer

| Name | Usage | Solution Type | Implementation ID | Technology | Runtime Infrastructure Target |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | | | | |


### Physical Infrastructure Layer

| Name | Usage | Solution Type | Implementation ID | Technology | Runtime Infrastructure Target |
| :--- | :--- | :--- | :--- | :--- | :--- |
| | | | | | |

#
[Back To Home](../README.md)
