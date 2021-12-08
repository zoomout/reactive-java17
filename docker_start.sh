#!/bin/bash

set -e
envVars="-e TNT_NEXUS_USERNAME=$TNT_NEXUS_USERNAME -e TNT_NEXUS_PASSWORD=$TNT_NEXUS_PASSWORD"

DOCKER_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

function service_health_check() {
    method=${1}
    url=${2}
    expected_status=${3}
    echo "     * Checking health by calling url: $url, expecting response status: $expected_status"
    done=0
    retries=0
    while [[ "${done}" -eq "0" && "${retries}" -lt "15" ]]; do
        status=$(curl -X "$method" -fso /dev/null -w "%{http_code}" "$url" || echo " Connection error")
        if [ "$status" == "$expected_status" ]; then
            done=1
        else
            echo "     * Service is not healthy yet, retrying. Status: $status. Expected: $expected_status. Attempt: ${retries}..."
            sleep 2
            ((retries+=1))
        fi
    done
    if [[ "${done}" -eq "0" ]]; then
        echo "ERROR: Service is not healthy."
        curl -v -X PUT "$url" -k -s -f -o /dev/null
        exit 1
    else
        echo "     * Service is healthy. Status is: $status"
    fi
}

./gradlew bootJar

docker_files="-f ${DOCKER_DIR}/docker-compose.yml"

docker compose $docker_files pull
docker compose $docker_files build --pull
docker compose ${docker_files} up --force-recreate --detach
