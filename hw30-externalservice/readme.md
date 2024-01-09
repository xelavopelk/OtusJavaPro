сборка образа inner:
docker build -t simplerest:v1 .

запуск образа inner:
docker container run -p 18189:8189 -d simplerest:v1