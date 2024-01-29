##0) node
###сборка образа node
docker build -t hw36-node:v1 .

###запуск node
docker container run -p 18189:8189 -d hw36-node:v1

##1) loadbalancer
###сборка образа loadbalancer
docker build -t hw36-loadbalancer:v1 .

###запуск node
docker container run -p 18191:8191 -d hw36-loadbalancer:v1

##2) front
###билд толстого jar
./gradlew clean build -Pvaadin.productionMode
###сборка образа front
docker build -t hw36-front:v1 .

###запуск node
docker container run -p 18080:8080 -d hw36-front:v1


##4) compose
###запуск docker compose
docker-compose up

##5) config
- Управлять уровнем ошибок на нодах через переменную окружения SUCCESS_RATE