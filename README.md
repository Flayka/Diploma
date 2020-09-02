## Дипломный проект профессии «Тестировщик» ##

#### Для запуска авто-тестов необходимо провести предварительную подготовку: ####
1. Запустить в Docker контейнеры СУБД MySQl, PostgerSQL и контейнер на Node.js
1. Запустить тестируемое приложение

#### Для тестирования приложения на СУБД MySQl: ####
1. Запустить контейнеры СУБД MySQl, PostgerSQL и Node.js командой в терминале:  
`docker-compose up`
1. В новом терминале запустить SUT, работающей на СУБД MySQl командой:  
`java -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app -jar artifacts/aqa-shop.jar` 
1. В новом терминале запустить авто-тесты командой:  
`gradlew clean test -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app`
1. Посмотреть отчет о тестировании Allure командой:  
`gradlew allureReport`
1. Закрыть все контейнеры командой:  
`docker-compose down`

#### Для тестирования приложения на СУБД PostgerSQL: ####
1. Запустить контейнеры MySQl, PostgerSQL и Node.js командой в терминале:  
 `docker-compose up`
1. В новом терминале запустить SUT, работающей на СУБД PostgerSQL командой:  
`java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app -jar artifacts/aqa-shop.jar `
1. В новом терминале запустить авто-тесты командой:  
`gradlew clean test -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app`
1. Посмотреть отчет о тестировании Allure командой:  
`gradlew allureReport`
1. Закрыть все контейнеры командой:  
`docker-compose down`

##### Дополнительные комментарии по настройкам #####
1. В [build.gradle](https://github.com/Flayka/Diploma/blob/master/build.gradle) по умолчанию стоит настройка подключения к СУБД MySQl:  
*systemProperty 'datasource.url', System.getProperty('datasource.url', 'jdbc:mysql://192.168.99.100:3306/app')*  
Для работы с СУБД PostgerSQL не через командную строку, следует использовать выражение:  
*systemProperty 'datasource.url', System.getProperty('datasource.url', 'jdbc:postgresql://192.168.99.100:5432/app')*

#### Документация по проекту: ####
1. [План автоматизации](https://github.com/Flayka/Diploma/blob/master/Documentation/Plan.md)
1. [Отчёт о проведённом тестировании](https://github.com/Flayka/Diploma/blob/master/Documentation/Report.md)
1. [Отчёт о проведённой автоматизации](https://github.com/Flayka/Diploma/blob/master/Documentation/Summary.md)