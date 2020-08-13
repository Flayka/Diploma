FROM node:10-alpine

# Папка приложения
# ARG APP_DIR=app
# RUN mkdir -p ${APP_DIR}
# WORKDIR ${APP_DIR}    or
# создание директории приложения
WORKDIR /usr/src/app

# Установка зависимостей
# символ астериск ("*") используется для того чтобы по возможности
# скопировать оба файла: package.json и package-lock.json
COPY ./gate-simulator/package*.json ./
RUN npm install
# Для использования в продакшне
# RUN npm install --production

# Устанавливаем зависимости, собираем проект и удаляем зависимости
# RUN npm install --production && npm run build:production && rm -rf node_module

# Копирование файлов проекта
COPY .. .

# Уведомление о порте, который будет прослушивать работающее приложение
EXPOSE 9999

# Запуск проекта
CMD ["npm", "start"]