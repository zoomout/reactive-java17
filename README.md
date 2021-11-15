# reactive-java17
Demo reactive-java17.
This project demonstrates how many parallel requests reactive stack can handle.
Client is calling server.
Client accepts 2 arguments: number of parallel requests and delay from the server.
Delay argument is passed through to the server, server delays each item accordingly.

# WebFlux configuration
By default, WebFlux allows 255 parallel client requests.
```
GET http://localhost:8092/client/string?n=255&d=1000 => response in ~1000ms
GET http://localhost:8092/client/string?n=256&d=1000 => response in ~2000ms
```

Server can handle more.
For example if running 2 clients in parallel calling 1 server, the response time doesn't degrade
```
# terminal 1
time curl --location --request GET 'localhost:8093/client/string?n=255&d=2000'
real    0m2.074s
# terminal 2
time curl --location --request GET 'localhost:8093/client/string?n=255&d=2000'
real    0m2.096s
```

# Start
```
./docker_start.sh
```

# Stop
```
./docker_stop.sh
```

# Run manual test
```
# Server
curl --location --request GET 'localhost:8091/server/string?d=500'
# Client
curl --location --request GET 'localhost:<port>/client/string?n=<number_of_parallel_requests>&d=<server_delay_millis>
# Client 1
curl --location --request GET 'localhost:8092/client/string?n=255&d=1000'
# Client 2
curl --location --request GET 'localhost:8093/client/string?n=255&d=1000'
```
