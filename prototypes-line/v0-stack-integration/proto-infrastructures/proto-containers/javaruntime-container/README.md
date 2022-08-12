# FROM PROJECT DIRECTORY

## BUILD OF IMAGE
### Build a Docker image of Linux/Java runtime environment container from shell command (including force remove of intermediary container)
`docker-compose build --force-rm`

### Build dockerfile (without docker-compose help) from directory
`docker build -t agnet/cybnity-javaruntime-distribution .`

## CHECK IMAGE
### Scan image vulnerabilities
`docker scan agnet/cybnity-javaruntime-distribution`

## START IMAGE
### Run it
`docker run -it --rm agnet/cybnity-javaruntime-distribution`

### Run a docker container without shell console (as a daemon without interaction) or without -d argument to maintain shell console opened
`docker-compose up -d`

### Check that the container is running
`docker ps`

## MONITOR
### Read logs
`docker-compose logs -f`

## STOP IMAGE
### Kill container
`docker-compose kill`

or

`docker kill agnet/cybnity-javaruntime-distribution`

## PUBLISH TO DOCKER REPOSITORY
### Create tag for built java container distribution specific to CYBNITY technology
`docker tag cybnity-javaruntime-distribution agnet/cybnity-javaruntime-distribution:latest`

### Push docker image to online CYBNITY private Docker repository
`docker push agnet/cybnity-javaruntime-distribution:latest`
