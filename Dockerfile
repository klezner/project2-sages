FROM ubuntu:18.04
RUN apt-get install -y openjdk-17-jre-headless
RUN apt-get -y update
RUN apt-get -y install git
RUN apt-get install maven
#RUN git clone https://github.com/bartmj/hello-world.git
#RUN git clone -n https://${GIT_USER}:${GIT_TOKEN}@github.com/bartmj/hello-world.git
RUN git clone https://bartmj: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@github.com/bartmj/hello-world.git
# --depth 1 && cd <your_git> && git checkout ${GIT_COMMIT} <dir_you_want_to_checkout>
RUN cd ./hello-world


// sprawdzic czy jest image open jdk alpine