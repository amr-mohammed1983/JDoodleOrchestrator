package com.jdoodle.utils;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;


import org.mandas.docker.client.DockerClient;
import org.mandas.docker.client.ProgressHandler;
import org.mandas.docker.client.builder.jersey.JerseyDockerClientBuilder;
import org.mandas.docker.client.exceptions.DockerCertificateException;
import org.mandas.docker.client.exceptions.DockerException;
import org.mandas.docker.client.messages.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class K8SUtils {

    public void config() throws DockerCertificateException {

    }

    public String createAndStartDockerK8s(String imageName, String tag) {
        String message = "";
        try {
            // Load the Kubernetes configuration from the default kubeconfig file
            String kubeConfigPath = Paths.get(System.getProperty("user.home"), ".kube", "config").toString();
            ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
            Configuration.setDefaultApiClient(client);

            // Create an instance of the CoreV1Api client
            CoreV1Api api = new CoreV1Api();

            // Create the Nginx container
            V1Container container = new V1Container();
            container.setName("nginx-container");
            container.setImage(imageName+":"+tag);

            // Create the Nginx pod
            V1Pod pod = new V1Pod();

            pod.setMetadata(new V1ObjectMeta());  // Initialize the metadata field
            pod.setSpec(new V1PodSpec());  // Initialize the spec field

            pod.getMetadata().setName("nginx-pod");
            pod.getSpec().setContainers(Collections.singletonList(container));

            // Create the pod in Kubernetes
            V1Pod createdPod = api.createNamespacedPod("default", pod, null, null, null,"");
            System.out.println("Pod created: " + createdPod.getMetadata().getName());

    } catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }

    public String stopDockerK8s(String podName)
    {
        String message ="";
        try
        {
            // Retrieve the Kubernetes configuration
            String kubeConfigPath = System.getProperty("user.home") + "/.kube/config";
            ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
            Configuration.setDefaultApiClient(client);

            // Create an instance of the CoreV1Api client
            CoreV1Api api = new CoreV1Api();

            // Delete the pod
            api.deleteNamespacedPod(podName, "default", null, null, null, null, null, null);
            System.out.println("Pod deleted: " + podName);
        }
        catch(Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }

    public String getPageFromK8s(String containerId)
    {
        String response = "";
        try
        {
        }
        catch(Exception e)
        {
            response = e.getMessage();
        }
        return response;
    }
}
