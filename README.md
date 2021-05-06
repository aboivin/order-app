# Order APP

Example event sourcing and websocket application using Kotlin, Axon 4 with an hexagonal architecture

### Requirements
Requires java >= 11, npm >= 7

### How to build

Backend, using maven:
```shell
./mvnw clean install
```

Frontend, using npm
```shell
cd ./front-app
npm install
```

### How to run it locally

Backend
```shell
java -jar ./target/order-app-1.0.0-SNAPSHOT.jar
```

Frontend
```shell
cd ./front-app
npm start
```
