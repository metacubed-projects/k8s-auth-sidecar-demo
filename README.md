# AuthN/AuthZ using sidecar proxies on Kubernetes

This demo contains the following components:

- A resource server running on port 80
- A sidecar proxy running on port 8000, in the same pod as the resource-server

![Kubernetes sidecar networking](docs/k8s-sidecar-networking.png)

The sidecar proxy intercepts all calls to the resource server and runs authN/authZ checks against them. If incoming calls pass these checks, they are forwarded to the resource-server. Otherwise, a 403 Forbidden reply is sent back.

## Running the demo

### Pre-requisites

- Install and configure [Minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)
- Install [Kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/) 

### Testing

- Switch to the Kubernetes namespace where the demo application should be deployed.
- Navigate to the [/deploy-config](deploy-config) directory.
- Run the [deploy-minikube.sh](deploy-config/deploy-minikube.sh) script to deploy the demo.
- Run `minikube service list` to get the external base url of the deployed service.
- Try the API methods described below, with authorized and unauthorized users.

### API

The resource server allows reads and writes using the `/data` API. The URLs are:

#### Read Data

```sh
curl -X GET "${BASE_URL}/data?key=${KEY}" -H "Authorization: ${USERNAME}"
```
Response:
```json
{
  "<KEY>": "<VALUE>"
}
```

#### Write Data

```sh
curl -X POST "${BASE_URL}/data?key=${KEY}&value=${VALUE}" -H "Authorization: ${USERNAME}"
```
Response:
```json
{
  "<KEY>": "<VALUE>"
}
```

## References

- [Kubernetes - Pods - Resource sharing and communication](https://kubernetes.io/docs/concepts/workloads/pods/#resource-sharing-and-communication)
- [Kubernetes - Cluster Networking](https://kubernetes.io/docs/concepts/cluster-administration/networking/)
- [Implementing a reverse proxy server in Kubernetes using the sidecar](https://www.magalix.com/blog/implemeting-a-reverse-proxy-server-in-kubernetes-using-the-sidecar-pattern)
- [Hand-crafting a sidecar proxy and demystifying Istio](https://venilnoronha.io/hand-crafting-a-sidecar-proxy-and-demystifying-istio)
