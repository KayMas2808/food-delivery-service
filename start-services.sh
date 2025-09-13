#!/bin/bash
# will differ based on OS.
PROJECT_DIR=$(pwd)

echo "--- Starting all services in separate Konsole tabs ---"

konsole --new-tab -p tabtitle="Customer Service" --workdir "${PROJECT_DIR}/customer-service" -e bash -c 'mvn spring-boot:run; read' &
konsole --new-tab -p tabtitle="Restaurant Service" --workdir "${PROJECT_DIR}/restaurant-service" -e bash -c 'mvn spring-boot:run; read' &
konsole --new-tab -p tabtitle="Delivery Service" --workdir "${PROJECT_DIR}/delivery-service" -e bash -c 'mvn spring-boot:run; read' &
konsole --new-tab -p tabtitle="Order Service" --workdir "${PROJECT_DIR}/order-service" -e bash -c 'mvn spring-boot:run; read' &
konsole --new-tab -p tabtitle="API Gateway" --workdir "${PROJECT_DIR}/api-gateway" -e bash -c 'mvn spring-boot:run; read' &

echo "--- New Konsole tabs have been opened for each service. ---"