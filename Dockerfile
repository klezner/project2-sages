FROM ubuntu:18.04
RUN apt-get update && \
    apt-get install -y openjdk-11-jre-headless && \
    apt-get clean
RUN apt-get -y install git
RUN apt-get -y install maven
RUN cd root/ && mkdir .m2