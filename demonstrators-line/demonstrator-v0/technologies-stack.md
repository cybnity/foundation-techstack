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
### Environments and locations
Should support implementation of applications capable to run on several infrastructure environment types (environment profiles):
  - **local**: developer's workstation during unit development activities;
  - **server**: common development server shared between developers collaborative activities and integration tests during unit development of integrated features;
  - **test**: server environment (no mirror of production environment) dedicated to quick tests when lot of infrastructure or environment changes are developed;
  - **staging**: pre-production server environment (mirror of production environment) that contain next version of applications, used for final integration testing and for test/demonstration of deployment repetition or performance demonstration;
  - **production**: production (live) server environment.

### Processing and distribution of load
Should start in a moderate time duration, but should have an acceptable reactivity SLA during the demonstration period allowing to check a realistic behavior of the UI Cockpits principles.

None requirement about support of data volume operated by the demonstrator using the stack (first phase of the CYBNITY project without challenges about the data load performance).

### Access and usage via Internet
Should be usable via Internet for remote demonstration through a web browser hosted on a laptop or tablet, connected to Internet over a 4G bandwidth.

### Hosting, hardware and network specifications
Should be virtualized allowing an installation on a public cloud host, or an independent laptop without Internet connection.

# STACK DEFINITION CURRENT STATUS

| Architecture Layer | Component Name | IDEA STAGE - Implementation Technology Opportunity | ANALYSIS STAGE - Specification Validated | DESIGN STAGE - Design Tested | IMPL STAGE - Implementation Tested | Comments, rejection cause on observations made |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| APPLICATION | | | | | | |
| INFRASTRUCTURE | | | | | | |
| PHYSICAL INFRASTRUCTURE | | | | | | |

# IMPLEMENTATION ARCHITECTURE
Presentation of the retained implementation stack resulting of the analysis steps results.


# ANALYSIS STEPS PERFORMED

## STEP 1 - Eligibility Check
During this step, the candidate technologies are identified and their study is mainly based on their documentations according to the promised announced by their editors/providers.

The eligibility criteria acceptance level are evaluated as:

- Incompatible `KO`
- Acceptable `OK`
- Good `COOL`

The criteria checked about advantages (ADV) are:

- 

| CANDIDATE TECHNOLOGY |
| |

## STEP 2 - Acceptance check regarding impact on other components required
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
| | | | | | | | |

#
[Back To Home](README.md)