FROM centos:latest
MAINTAINER xsgkgo@gmail.com
RUN yum -y install java-1.8.0-openjdk-devel.x86_64 && \
	mkdir -p /opt/jaxrsclientsample && \
	cd /opt/jaxrsclientsample && \
	mkdir bin lib upload download sh && \
	cd lib && \
	curl -L http://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.27/jaxrs-ri-2.27.zip -o jaxrs-ri-2.27.zip && \
	jar -xvf jaxrs-ri-2.27.zip && \
        export CLASSPATH=$CLASSPATH
ADD ./jaxrsclientsample.sh /opt/jaxrsclientsample/sh
ADD ./jaxrs.jar /opt/jaxrsclientsample/bin
ADD ./upload.png /opt/jaxrsclientsample/upload

