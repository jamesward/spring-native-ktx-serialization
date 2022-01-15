# Spring Native KTX Serialization

Start on JVM:
```
./gradlew :server:bootRun
```

Or Spring Native:
```
./gradlew :server:bootBuildImage --imageName=spring-native-ktx-serialization
docker run -it -p8080:8080 spring-native-ktx-serialization
```

```
curl -X POST http://localhost:8080/api/bars -H 'Content-Type: application/json' -d '{"name": "Test"}'
curl http://localhost:8080/api/bars
```