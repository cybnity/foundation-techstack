## PURPOSE
Presentation of the technical services used by the prototyped systems as infrastructure layer.

# CYBNITY LOCAL INFRASTRUCTURE DEPLOYMENT & RUN
Several projects are managed regarding each technical service that make sens for the prototyping activities.

Each infrastructure system implementation source codes is accessible per sub-directory:
- [proto-containers](proto-containers) (usable generic container type as runtime sub-layer with OS)
  - [javaruntime-container](proto-containers/javaruntime-container/README.md)
- [proto-domains-interactions-broker](proto-domains-interactions-broker) (Kafka broker type and additional tools allowing management)
  - [kafka-broker](proto-domains-interactions-broker/kafka-broker/README.md)
  - [kafka-manager](proto-domains-interactions-broker/kafka-manager/README.md)
- [proto-event-logging-server](proto-event-logging-server)
- [proto-iam-server](proto-iam-server)
- [proto-secret-mgt-server](proto-secret-mgt-server)
- [proto-sso-server](proto-sso-server)
- [proto-users-interactions-broker](proto-users-interactions-broker) (Redis broker type)
  - [redis-container](proto-users-interactions-broker/redis-container/README.md)
