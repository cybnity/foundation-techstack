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

## MONITORING & MANAGEMENT
Read logs from shell command line:
`docker-compose logs -f`

Kill container from shell command line:
`docker-compose kill`

or
`docker kill agnet/cybnity-redis-distribution`

## IMAGE PUBLISH
Create tag for built Redis container distribution specific to CYBNITY technology from shell command line:
`docker tag cybnity-redis-distribution agnet/cybnity-redis-distribution:latest`

Push docker image to online CYBNITY private Docker repository from shell command line:
`docker push agnet/cybnity-redis-distribution:latest`
