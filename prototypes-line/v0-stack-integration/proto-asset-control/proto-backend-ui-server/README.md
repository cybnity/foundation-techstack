# CYBNITY PROTOTYPE SYSTEMS DEPLOYMENT & START
## Backend UI server run
From shell command line:
* Package the server service:

  ```shell
  mvn package
  ```

* Start the server Vert.x server-side backend system:

  ```shell
  mvn exec:java
  ```

## REDIS Users Interactions Space Broker
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
  ```

# INTEGRATION TEST PROTOCOL

## UI BACKEND SUPPORTED ENDPOINTS (HTTP, API, EVENTBUS)
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
