#!/bin/sh

cd ../auth-proxy
mvn clean package
cd -

eval $(minikube docker-env)

docker build -t auth-sidecar-demo/auth-proxy ../auth-proxy
docker build -t auth-sidecar-demo/auth-proxy-init ../auth-proxy-init
docker build -t auth-sidecar-demo/resource-server ../resource-server

kubectl apply -f deployment.yml
kubectl apply -f service.yml
