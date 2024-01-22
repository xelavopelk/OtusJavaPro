#readme по hw36 см. в hw36-loadbalancer

На node_modules надо много места!!!!!!!
Запуск в IDE:

```bash
./gradlew clean bootRun
```
Настройка ideaJ: https://www.programmersought.com/article/77894451359/


## Сборка в продакшн(только так!!!!!)

Сборка (под это отдельная градле в модуле):

```bash
./gradlew clean build -Pvaadin.productionMode
```

Запуск в командной строке

```bash
cd build/libs/
java -jar hw36-front.jar
```

Запуск в браузере [http://localhost:8080](http://localhost:8080) 

