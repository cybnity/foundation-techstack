# FROM PROJECT DIRECTORY
See commands help regarding reused docker image at https://github.com/hleb-albau/kafka-manager-docker .

## START IMAGE
Start a cluster manager from docker from shell command line:

```shell
docker run -d \
     -p 9000:9000  \
     -e ZK_HOSTS="192.168.60.13:2181,192.168.60.13:2182,192.168.60.13:2183" \
     agnet/kafka-cluster-manager-distribution:latest
```

Start a cluster manager with docker-compose from shell command line:
`docker-compose -f kafka-cluster-manager.yml up -d`

## RUN
Connect from web browser to the UI console at `http://localhost:9000` and use the admin account:

- login = manager
- password = manager
