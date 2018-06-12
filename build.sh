#!/bin/sh

CONTAINER_NAME="jaxrsclientsample"
NET=jaxrsnw
CPUSETCPUS=0-1

#--------------------------------------------------
# Check for container network, if does not exist
# create new
#--------------------------------------------------
NW=`docker network ls | grep $NET`
if [ -z "$NW" ]
then
    docker network create $NET
fi

#--------------------------------------------------
# Stop and remove current container
#--------------------------------------------------
docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME
docker rmi $CONTAINER_NAME

#--------------------------------------------------
# Build container using Dockerfile
#--------------------------------------------------
docker build -t $CONTAINER_NAME .


#--------------------------------------------------
# Run the container
#--------------------------------------------------
docker run -dti -h $CONTAINER_NAME --name $CONTAINER_NAME --net=$NET --cpuset-cpus=$CPUSETCPUS $CONTAINER_NAME /bin/bash


