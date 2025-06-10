# MathBilim - образовательная платформа

## Требования для запуска
- Java 21 - https://docs.fabricmc.net/ru_ru/players/installing-java/windows
- PostgreSQL -https://selectel.ru/blog/tutorials/ustanovka-postgresql-15-windows/
- Git - https://docs.github.com/ru/desktop/installing-and-authenticating-to-github-desktop/installing-github-desktop
## Установка и запуск

1.Создайте папку куда вы будете клонировать проект

2.Перейди в папку через консоль
cd ~/projects

3.С клонируйте репоситорий
git clone https://github.com/aishkoy/mathbilim.git

4.Перейдите к папке репозитория
cd mathbilim

5.Перейдите к ветке dev
git checkout dev

6.Запустите сборку jar
./mvnw clean package -DskipTests

7.Дальше в проекте у вас появится папка target, добавьте в нее файл без расширения с названием .env
его содержимое (вручную или через консоль):

cd target

(Через консоль: 
touch .env
nano .env )

AWS_ACCESS_KEY_ID=
AWS_SECRET_ACCESS_KEY=
AWS_REGION=
AWS_S3_BUCKET_NAME=

DB_URL=url баззы (пример: jdbc:postgresql://localhost:5432/postgres)
DB_USERNAME=название вашей баззы данных
DB_PASSWORD=пароль

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=

(ctrl x + y)

8.Дальше выполните команду запуска jar:
java -jar mathbilim-0.0.1-SNAPSHOT.jar

если все сделано правильно файл запустится, перейдите в браузере по ссылке
http://localhost:9999/ в браузере

для просмотра имеющихся апи: http://localhost:9999/swagger-ui/index.html

(не забудьте завершать процесс (незнаю как на маке))
