package com.jdoodle.controllers;

import com.google.gson.Gson;
import com.jdoodle.utils.DockerUtils;
import jakarta.ws.rs.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class K8sController {

    @PostMapping("/create-start-docker-k8s")
    public String createAndStartDockerK8s(@PathParam("imageName") String imageName,@PathParam("tag") String tag ) {
        String message = "";
        try
        {

        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }

    @PostMapping("/stop-docker-k8s")
    public String stopDockerK8s(@PathParam("containerId") String containerId) {
        String message = "Success";
        try
        {

        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }

    @GetMapping("/get-page-k8s")
    public String getPageFromK8s()
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
    @GetMapping("/list-docker-k8s-pods")
    public String listK8sPods() {
        String message = "";
        try
        {

        }catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }

}

