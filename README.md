JDoodle PoC.

I created a VM using Virutal Box and installed Docker engine in it.

I built the docker file in the resources folder, and built the imag using the following command:

docker build -t amrmoha/poc-amr:jdoodle-orchestrator-v1 .

I created Spring boot endpoints to:
- Start container from the above image
- Stop container given the container ID
- list all docker images
- list all docker containers


For K8s cluster:

I installed minikube and created k8s cluster with one master and one worker:

minikube start --nodes=2minikube start --nodes=2


I changed context so that kubectl is pointing to docker-desktop:
- kubectl config get-contexts
- kubectl config use-context docker-desktop