package com.sages.project2.common;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ExecCreation;
import lombok.SneakyThrows;
import org.apache.catalina.startup.HostConfig;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DockerService {
    @SneakyThrows
    public static String dockerService(String gitURL) /*throws DockerCertificateException, DockerException, InterruptedException*/ {
        DockerClient docker = DefaultDockerClient.fromEnv().build();
        docker.pull("geri2045/ad:aadd");

        //Prepare volume
//        HostConfig.Bind volume = com.spotify.docker.client.messages.HostConfig.Bind.from("/j/").to("/home/gerard3")/*.readOnly(false)*/.build();

        //Prepare host
//        final HostConfig hostConfig = HostConfig.builder().appendBinds(volume).build();

        //Prepare container
        ContainerConfig containerConfig = ContainerConfig.builder()
//                .hostConfig(hostConfig)
                .image("geri2045/ad:aadd")
                .cmd("sh", "-c", "while :; do sleep 1; done")
                .build();
        ContainerCreation creation = docker.createContainer(containerConfig, "GerardContainer_______");
        String id = creation.id();
        ContainerInfo info = docker.inspectContainer(id);

        // Start container
        docker.startContainer(id);
        System.out.println(id);

        String tempFolderName = gitURL.split("//")[gitURL.split("//").length - 1].replace(".git", "");


        // Start block of commands
        ArrayList<String[]> listOfCommands = new ArrayList<>();
        listOfCommands.add(new String[]{"java", "-version"});
//        listOfCommands.add(new String[]{"git", "clone", "https://github.com/Coveros/helloworld.git"});
        listOfCommands.add(new String[]{"git", "clone", gitURL});
        listOfCommands.add(new String[]{"sh", "-c", "ls -la"});
        listOfCommands.add(new String[]{"sh", "-c", String.format("cd /%s/ && ls -la", tempFolderName)});
        listOfCommands.add(new String[]{"sh", "-c", String.format("cd /%s/ && mvn clean package", tempFolderName)});
        listOfCommands.add(new String[]{"sh", "-c", String.format("cd /%s/target/ && java -jar %s-1-1.jar", tempFolderName, tempFolderName.toLowerCase()    )});
//        listOfCommands.add(new String[]{"sh", "-c", "cd /helloworld/target/ && java -cp helloworld-1.1.jar com.coveros.demo.helloworld.HelloWorld"});

        // todo: -jar

        // Execute commands
        List<String> output = new LinkedList<>();

        listOfCommands.stream().map(s -> sendToContainer(s, id, docker)).forEach(output::add);
        output.forEach(System.out::println);

        // Wait!
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Stop container
        System.out.println("closing...");
        docker.killContainer(id);
        docker.removeContainer(id);
        docker.close();

        return output.get(output.size()-1);
    }

    public static String sendToContainer(String[] commands, String id, DockerClient docker) {
        DockerClient.ExecCreateParam ecpOUT = DockerClient.ExecCreateParam.attachStdout();
        DockerClient.ExecCreateParam ecpERR = DockerClient.ExecCreateParam.attachStderr();
        LogStream output = null;
        try {
            ExecCreation execCreation = docker.execCreate(id, commands, ecpOUT, ecpERR);
            output = docker.execStart(execCreation.id());
        } catch (DockerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return output.readFully();
    }
}

