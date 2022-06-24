package com.sages.project2.adapters.clients;

import com.spotify.docker.client.*;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.sages.project2.domain.ports.out.DockerApiClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
public class SpotifyDockerApiClient implements DockerApiClient {

    public static final String PATH_TO_LOCAL_M2_FOLDER = "/home/bartekj/.m2/";
    public static final String DOCKER_IMAGE = "geri2045/ad:aadd";
    public static final String GITHUB_ADMIN_USERNAME = "bartmj";
    public static String GH_TOKEN = "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";

    public String checkSolution(String repoName, String branchName) throws DockerCertificateException, DockerException, InterruptedException {
        DockerClient docker = null;
        String id = null;
        List<String> output;

        try {
            docker = DefaultDockerClient.fromEnv().build();
            docker.pull("geri2045/ad:aadd");

            final String[] ports = {"10002"};
            final Map<String, List<PortBinding>> portBindings = new HashMap<>();
            for (String port : ports) {
                List<PortBinding> hostPorts = new ArrayList<>();
                hostPorts.add(PortBinding.of("0.0.0.0", port));
                portBindings.put(port, hostPorts);
            }

            List<PortBinding> randomPort = new ArrayList<>();
            randomPort.add(PortBinding.randomPort("0.0.0.0"));
            portBindings.put("443", randomPort);

            final HostConfig hostConfig = HostConfig.builder()
                    .binds(PATH_TO_LOCAL_M2_FOLDER + ":/root/.m2/")
                    .portBindings(portBindings).build();


            final ContainerConfig containerConfig = ContainerConfig.builder()
                    .hostConfig(hostConfig)
                    .image(DOCKER_IMAGE).exposedPorts(ports)
                    .cmd("sh", "-c", "while :; do sleep 1; done;")
                    .build();

            final ContainerCreation creation = docker.createContainer(containerConfig);
            id = creation.id();

            final ContainerInfo info = docker.inspectContainer(id);

            docker.startContainer(id);

            ArrayList<String[]> listOfCommands = new ArrayList<>();

            listOfCommands.add(new String[]{"git", "clone", "--branch", branchName,
                    "--single-branch", "https://" + GH_TOKEN + "@github.com/" + GITHUB_ADMIN_USERNAME + "/" + repoName + ".git"});

            listOfCommands.add(new String[]{"sh", "-c", "cd " + repoName + " && mvn test"});

            output = new LinkedList<>();
            String finalId = id;
            DockerClient finalDocker = docker;

            listOfCommands.stream().map(s -> sendToContainer(s, finalId, finalDocker)).forEach(output::add);

            System.out.println("closing...");
        } catch (DockerCertificateException | DockerException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            docker.killContainer(id);
            docker.removeContainer(id);
            docker.close();

        }
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
