###сборка образа node
docker build -t hw36-node:v1 .

###запуск node
docker container run -p 18189:8189 -d hw36-node:v1

###сборка образа loadbalancer
docker build -t hw36-loadbalancer:v1 .

###запуск node
docker container run -p 18191:8191 -d hw36-loadbalancer:v1

###запуск docker compose
docker-compose up

##
Управлять уровнем ошибок на нодах через переменную окружения SUCCESS_RATE