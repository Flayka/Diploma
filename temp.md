Необходимо запустить оба сервиса через docker-compose up
Удостоверяемся, что оба сервиса запущены: docker-compose ps
запуск:
docker-compose exec mysql mysql -u app app -p
docker-compose exec postgres psql -U app -d app -W
docker-compose exec nodejs

запускается стандартным способом java -jar artifacts/aqa-shop.jar на порту 8080.
java -jar artifacts/aqa-shop.jar -P:jdbc.url=jdbc:postgres://192.168.99.100:5432/app -P:jdbc.user=app -P:jdbc.password=pass
java -jar artifacts/aqa-shop.jar -P:jdbc.url=jdbc:mysql://192.168.99.100:3306/app -P:jdbc.user=app -P:jdbc.password=pass

Сборка образа
Перейдите в директорию, в которой находится ваш Dockerfile и запустите следующую команду, чтобы собрать Docker-образ. Флаг -t позволяет поставить тэг к вашему образу, чтобы его позже было проще найти при помощи команды docker images:
docker build -t diploma/node-web-app

Запуск образа
Запуск образа с флагом -d позволяет контейнеру работать в фоновом режиме. Флаг -p перенаправляет публичный порт на приватный порт внутри контейнера. Запустите образ, который вы ранее создали:
#docker run -p 49160:9999 -d gate-simulator/node-web-app
docker run -p 8080:9999 -d nodejs

# отобразить все контейнеры, чтобы получить id нужного нам
$ docker ps
# отобразить логи
$ docker logs <container_id>
# пример логов
running on http://localhost:8080
Если вам нужно попасть внутрь контейнера, используйте команду exec:
# войти в контейнер в интерактивном режиме
$ docker exec -it <container id> /bin/bash
$ docker exec -it deb2f1975f05 Dockerfile

Проверка
Чтобы проверить ваше приложение, используйте публичный порт, к которому привязан контейнер:
$ docker ps

В примере выше docker связал порт 8080 внутри контейнера с портом 49160 на вашем компьютере.
Вы можете сделать запрос к вашему приложению с помощью утилиты curl (установите ее, если требуется с помощью команды: sudo apt-get install curl):
$ curl -i localhost:49160



Put all your project in C:\Users\Idea (not anywhere else)
Start from scratch: in the console - docker-machine start (only if it not started)
Recheck ip: docker-machine ip (it may be not 192.168.99.100)
If in step 2, ip is not 192.168.99.100 use docker-machine env (copy and paste output to console)
Start docker-compose: docker-compose up
If it warns about certificates with different ip (x.x.x.101) use docker-machine provision
Wait, it slow on your pc
After all work docker-machine stop
My fault it's use old path