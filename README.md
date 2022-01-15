# Spring Native KTX Serialization

Start a DB:
```
docker run --rm -ePOSTGRES_PASSWORD=password -p5432:5432 --name my-postgres postgres:13.1
```

Init the DB:
```
cat init.sql | docker exec -i my-postgres psql -U postgres
```

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