# FROM PROJECT DIRECTORY

## BUILD
Build a Docker image of Redis environment container from shell command (including force remove of intermediary container):
`docker-compose build --force-rm`

Scan image vulnerabilities from shell command line:
`docker scan agnet/cybnity-redis-distribution`

## RUN
Run it from shell command line:
`docker run -it --rm agnet/cybnity-redis-distribution`

Run a docker container without shell console (as a daemon without interaction) or without -d argument to maintain shell console opened:
`docker-compose up -d`

Check that the container is running from shell command line:
`docker ps`

## IMAGE PUBLISH
Create tag for built Redis container distribution specific to CYBNITY technology from shell command line:
`docker tag cybnity-redis-distribution agnet/cybnity-redis-distribution:latest`

Push docker image to online CYBNITY private Docker repository from shell command line:
`docker push agnet/cybnity-redis-distribution:latest`

## MONITORING & MANAGEMENT
Read logs from shell command line:
`docker-compose logs -f`

Kill container from shell command line:
`docker-compose kill`

or
`docker kill agnet/cybnity-redis-distribution`

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
