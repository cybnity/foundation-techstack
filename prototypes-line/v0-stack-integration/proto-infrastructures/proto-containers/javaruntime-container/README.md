# FROM PROJECT DIRECTORY

## BUILD OF IMAGE
Build a Docker image of Linux/Java runtime environment container from shell command (including force remove of intermediary container) from shell command line:
`docker-compose build --force-rm`

Build dockerfile (without docker-compose help) from directory from shell command line:
`docker build -t agnet/cybnity-javaruntime-distribution .`

## CHECK IMAGE
Scan image vulnerabilities from shell command line:
`docker scan agnet/cybnity-javaruntime-distribution`

## START IMAGE
Run it from shell command line:
`docker run -it --rm agnet/cybnity-javaruntime-distribution`

Run a docker container without shell console (as a daemon without interaction) or without -d argument to maintain shell console opened from shell command line:
`docker-compose up -d`

Check that the container is running from shell command line:
`docker ps`

## MONITOR
Read logs from shell command line:
`docker-compose logs -f`

## STOP IMAGE
Kill container from shell command line:
`docker-compose kill`

or

`docker kill agnet/cybnity-javaruntime-distribution`

## PUBLISH TO DOCKER REPOSITORY
Create tag for built java container distribution specific to CYBNITY technology from shell command line:
`docker tag cybnity-javaruntime-distribution agnet/cybnity-javaruntime-distribution:latest`

Push docker image to online CYBNITY private Docker repository from shell command line:
`docker push agnet/cybnity-javaruntime-distribution:latest`
