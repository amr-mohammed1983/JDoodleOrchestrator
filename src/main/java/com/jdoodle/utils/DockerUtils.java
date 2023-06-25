package com.jdoodle.utils;


import org.mandas.docker.client.DockerClient;
import org.mandas.docker.client.ProgressHandler;
import org.mandas.docker.client.builder.jersey.JerseyDockerClientBuilder;
import org.mandas.docker.client.exceptions.DockerCertificateException;
import org.mandas.docker.client.exceptions.DockerException;
import org.mandas.docker.client.messages.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class DockerUtils {
    DockerClient dockerClient = null;

    public void config() throws DockerCertificateException {
        dockerClient = new JerseyDockerClientBuilder().fromEnv().build();
    }

    public String createAndStartDockerContainer(String imageName, String tag) {
        String message = "";
        try {
            // Pull an image
            dockerClient.pull(imageName + ":" + tag);

            // Bind container ports to host ports
            final String[] ports = {"80", "9081"};
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

            final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

            // Create container with exposed ports
            final ContainerConfig containerConfig = ContainerConfig.builder()
                    .hostConfig(hostConfig)
                    .image(imageName + ":" + tag).exposedPorts(ports)
                    .cmd("sh", "-c", "while :; do sleep 1; done")
                    .build();

            final ContainerCreation creation = dockerClient.createContainer(containerConfig);
            final String id = creation.id();
            // Inspect container
            final ContainerInfo info = dockerClient.inspectContainer(id);

            // Start container
            dockerClient.startContainer(id);
            message = "A container of the image " + imageName + ":" + tag + " is started successfully";
        } catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }

    public String stopContainer(String containerId)
    {
        String message ="";
        try
        {
            // wait 5 seconds and kill the container
            dockerClient.stopContainer(containerId,5);
            message = "A container with id : "+containerId+" is stopped successfully";
        }
        catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }
    public String buildDockerImage(String dockerFilePath) throws DockerException, IOException, InterruptedException {
        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
        final String dockerDirectory = ".";
        try {
            final String returnedImageId = dockerClient.build(
                    Paths.get(dockerDirectory), "Dockerfile", new ProgressHandler() {
                        @Override
                        public void progress(ProgressMessage message)  {
                            final String imageId = message.buildImageId();
                            if (imageId != null) {
                                imageIdFromMessage.set(imageId);
                            }
                        }
                    });
        }catch(Exception x)
        {
            x.printStackTrace();
        }
        return "";
    }

    public List listDockerImages() {
        List<Image> dockerImages = new ArrayList<>();
        try {
            dockerImages = dockerClient.listImages();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return dockerImages;
    }

    public List<Container> listDockerContainers() {
            List<Container> dockerContainers = new ArrayList<>();
            try {
                dockerContainers = dockerClient.listContainers();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return dockerContainers;

    }
}
