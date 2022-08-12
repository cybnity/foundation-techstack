## PURPOSE
Presentation of the technical services used by the prototyped systems as infrastructure layer.

# CYBNITY LOCAL INFRASTRUCTURE DEPLOYMENT & RUN
Several projects are managed regarding each technical service that make sens for the prototyping activities.

Each infrastructure system implementation source codes is accessible per sub-directory:
- [proto-containers](proto-containers) (usable generic container type as runtime sub-layer with OS)
  - [javaruntime-container](javaruntime-container/README.md)
- [proto-domains-interactions-broker](proto-domains-interactions-broker) (Kafka broker type and additional tools allowing management)
- [proto-event-logging-server](proto-event-logging-server)
- [proto-iam-server](proto-iam-server)
- [proto-secret-mgt-server](proto-secret-mgt-server)
- [proto-sso-server](proto-sso-server)
- [proto-users-interactions-broker](proto-users-interactions-broker) (Redis broker type)

## BUILD OF INFRASTRUCTURE SYSTEMS



## START OF INFRASTRUCTURE LAYER

## MONITORING & MANAGEMENT

### Users Interactions Space (Redis)
#### Tool for view of stored data
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
