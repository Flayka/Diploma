## Дипломный проект профессии «Тестировщик» ##

### Процедура запуска авто-тестов ###

#### Для запуска авто-тестов необходимо настроить и запустить системы: ####
1. Запустить Docker Toolbox
1. Запустить в Docker контейнеры СУБД MySQl, PostgerSQL и контейнер на Node.js
1. Настроить application.properties в зависимости от тестируемой СУБД
1. Запустить тестируемое приложение

#### Для тестирования приложения на СУБД MySQl: ####
1. Запустить контейнеры MySQl, PostgerSQL и Node.js командой в терминале `docker-compose up`
1. Убедиться что в [application.properties](https://github.com/Flayka/Diploma/blob/master/application.properties) активна строка: 
*spring.datasource.url=jdbc:mysql://192.168.99.100:3306/app*
1. В новом терминале подключиться к СУБД MySQl `docker-compose exec mysql mysql -u app app -p`
1. В новом терминале запустить SUT командой `java -jar artifacts/aqa-shop.jar` 
1. Запустить авто-тесты командой `gradlew clean test`
1. Для получения отчета Allure запустить команду`gredlew allureReport`
1. Посмотреть отчет о тестировании Allure командой `gradlew allureServe`
1. Закрыть все контейнеры командой `docker-compose down`

#### Для тестирования приложения на СУБД PostgerSQL: ####
1. Запустить контейнеры MySQl, PostgerSQL и Node.js командой в терминале `docker-compose up`
1. Убедиться что в [application.properties](https://github.com/Flayka/Diploma/blob/master/application.properties) активна строка:
*spring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app*
1. В новом терминале подключиться к СУБД PostgerSQL `docker-compose exec postgres psql -U app -d app -W`
1. В новом терминале запустить SUT командой `java -jar artifacts/aqa-shop.jar`
1. В новом терминале запустить авто-тесты командой `gradlew clean test allureReport`
1. Посмотреть отчет о тестировании Allure командой `gradlew allureServe`
1. Закрыть все контейнеры командой `docker-compose down`
