package com.sages.project2.adapters.clients;

import com.spotify.docker.client.*;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.sages.project2.domain.ports.out.DockerApiClient;

import java.util.*;

@Component
public class SpotifyDockerApiClient implements DockerApiClient {

    public static final String PATH_TO_M2_FOLDER = "/home/bartekj/.m2/";
    public static final String DOCKER_IMAGE = "codequest";
    public static final String GITHUB_ADMIN_USERNAME = "codequest504";
//    @Value("${github.app.oauth}")
    public static String GH_TOKEN = "TOKEN_TOKEN_TOKEN";

    public String checkSolution(String repoName, String branchName) throws DockerCertificateException, DockerException, InterruptedException {
        final DockerClient docker = DefaultDockerClient.fromEnv().build();

// Bind container ports to host ports
        final String[] ports = {"10002"};
        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            List<PortBinding> hostPorts = new ArrayList<>();
            hostPorts.add(PortBinding.of("0.0.0.0", port));
            portBindings.put(port, hostPorts);
        }

// Bind container port 443 to an automatically allocated available host port.
        List<PortBinding> randomPort = new ArrayList<>();
        randomPort.add(PortBinding.randomPort("0.0.0.0"));
        portBindings.put("443", randomPort);

        final HostConfig hostConfig = HostConfig.builder()
                .binds(PATH_TO_M2_FOLDER + ":/root/.m2/")
                .portBindings(portBindings).build();


// Create container with exposed ports
        final ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(DOCKER_IMAGE).exposedPorts(ports)
                .cmd("sh", "-c", "while :; do sleep 1; done;")
                .build();

        final ContainerCreation creation = docker.createContainer(containerConfig);
        final String id = creation.id();

// Inspect container
        final ContainerInfo info = docker.inspectContainer(id);

// Start container
        docker.startContainer(id);

// Exec command inside running container with attached STDOUT and STDERR
        ArrayList<String[]> listOfCommands = new ArrayList<>();

        listOfCommands.add(new String[]{"git", "clone", "--branch", branchName,
                "--single-branch", "https://" + GH_TOKEN + "@github.com/" + GITHUB_ADMIN_USERNAME + "/" + repoName + ".git"});

        listOfCommands.add(new String[]{"sh", "-c", "cd " + repoName + " && mvn test"});
//        listOfCommands.add(new String[]{"sh", "-c", "ls"});
        List<String> output = new LinkedList<>();

        listOfCommands.stream().map(s -> sendToContainer(s, id, docker)).forEach(output::add);

        // Stop container
        System.out.println("closing...");
        docker.killContainer(id);
        docker.removeContainer(id);
        docker.close();

        return output.get(output.size() - 1);
    }

    @SneakyThrows
    private static String sendToContainer(String[] commands, String id, DockerClient docker) {
        final ExecCreation execCreation = docker.execCreate(
                id, commands, DockerClient.ExecCreateParam.attachStdout(),
                DockerClient.ExecCreateParam.attachStderr());
        final LogStream output = docker.execStart(execCreation.id());
        final String execOutput = output.readFully();
        return execOutput;
    }

}