FROM ubuntu:18.04
ARG GIT_USER=bartmj
ARG GIT_TOKEN=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
ARG REPO_NAME=hello-world
RUN apt-get update && \
    apt-get install -y openjdk-11-jre-headless && \
    apt-get clean
RUN apt-get -y install git
RUN apt-get -y install maven
RUN git clone https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${REPO_NAME}.git
RUN cd ${REPO_NAME} && mvn dependency:go-offline
