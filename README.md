# reactive-java17
Demo reactive-java17

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
curl --location --request GET 'localhost:8091/server/string'
# Client
curl --location --request GET 'localhost:8092/client/string'
```