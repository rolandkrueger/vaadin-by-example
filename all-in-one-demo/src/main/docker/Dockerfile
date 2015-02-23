FROM jeanblanchard/busybox-java:8
MAINTAINER rolandkrueger
EXPOSE 8080
CMD java -Xmx500m -Xms250m -jar /data/${project.build.finalName}.war
ADD build/${project.build.finalName}.war /data/${project.build.finalName}.war