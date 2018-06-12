#!/bin/sh

BASEDIR="/opt/jaxrsclientsample"
APPJAR="jaxrs.jar"
MAINCLASS="sample.jaxrs.client.jersey.JaxRsUploadSample"

JAXRSJAR=`find $BASEDIR/lib/jaxrs-ri -name '*.jar'`

for JAR in $JAXRSJAR
do
    export CLASSPATH=$CLASSPATH:$JAR
done

export CLASSPATH=$CLASSPATH:$BASEDIR/bin/$APPJAR


java $MAINCLASS


