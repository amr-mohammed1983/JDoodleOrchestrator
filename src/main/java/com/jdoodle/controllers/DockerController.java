package com.jdoodle.controllers;

import com.google.gson.Gson;
import jakarta.ws.rs.PathParam;
import org.mandas.docker.client.exceptions.DockerCertificateException;
import org.mandas.docker.client.exceptions.DockerException;
import org.springframework.web.bind.annotation.*;
import com.jdoodle.utils.DockerUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class DockerController {

    @PostMapping("/create-start-docker-nginx")
    public String createAndStartDockerContainer(@PathParam("imageName") String imageName,@PathParam("tag") String tag ) {
        String message = "";
        try
        {
            DockerUtils dockerUtils = new DockerUtils();

            message = dockerUtils.createAndStartDockerContainer(imageName,tag);

        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }

    @PostMapping("/stop-docker-nginx")
    public String stopDockerContainer(@PathParam("containerId") String containerId) {
        String message = "Success";
        try
        {
            DockerUtils dockerUtils = new DockerUtils();
            dockerUtils.config();
            message = dockerUtils.stopContainer(containerId);
        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }

    @GetMapping("/get-page-docker")
    public String getPageFromDocker()
    {
        String message = "Success";
        try
        {

        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }
    @GetMapping("/list-docker-images")
    public String listDockerImages() {
        String message = "";
        try
        {
            DockerUtils dockerUtils = new DockerUtils();
            dockerUtils.config();
            List dockerImages = dockerUtils.listDockerImages();
            message = new Gson().toJson(dockerImages);
        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }
    @GetMapping("/list-docker-containers")
    public String listDockerContainers() {
        String message = "";
        try
        {
            DockerUtils dockerUtils = new DockerUtils();
            dockerUtils.config();
            List dockerContainers = dockerUtils.listDockerContainers();
            message = new Gson().toJson(dockerContainers);
        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }
}

