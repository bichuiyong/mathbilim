# MathBilim - образовательная платформа

## ⚙ Требования для запуска

- [Java 21](https://docs.fabricmc.net/ru_ru/players/installing-java/windows)
- [PostgresQL](https://selectel.ru/blog/tutorials/ustanovka-postgresql-15-windows/)
- [Git](https://docs.github.com/ru/desktop/installing-and-authenticating-to-github-desktop/installing-github-desktop)

---

## <h1>🛠 Установка и запуск</h1>

<h3>1. Склонируйте репоситорий в папку и выполните сборку jar в mathbilim:</h3>

✅ В Windows:
```
.\mvnw clean package -DskipTests
```
✅ В Mac:
```
./mvnw clean package -DskipTests
```
<h3>2. Далее перейди в папку target:</h3>

```
cd target
```
<h3>3. В папке target нужно создать файл c названием .env без расширения, с содержимым:</h3>

```
AWS_ACCESS_KEY_ID=<ваш AWS_ACCESS_KEY_ID>
AWS_SECRET_ACCESS_KEY=<ваш AWS_SECRET_ACCESS_KEY>
AWS_REGION=<ваш AWS_REGION>
AWS_S3_BUCKET_NAME=<ваш AWS_S3_BUCKET_NAME>

DB_HOST=<хост бд, к примеру (localhost)>
DB_PORT=<к примеру 5432>
DB_NAME=<название базы>
DB_USERNAME=<ваш логин к базе>
DB_PASSWORD=<ваш пароль к базе>

GOOGLE_CLIENT_ID=<ваш Google Client ID>
GOOGLE_CLIENT_SECRET=<ваш Google Client Secret>

RESET_EMAIL=<ваш email>
RESET_PASSWORD=<ваш password>

RECAPTCHA_SECRET_KEY=<ключ капчи>
TGBOTKEY=<ключ бота телеграм>

FACEBOOK_CLIENT_ID=<id клиент приложения facebook>
FACEBOOK_CLIENT_SECRET=<ключ приложения facebook>

```

<h3>4. Запусти приложение командой:</h3>

```
java -jar mathbilim-0.0.1-SNAPSHOT.jar
```

🔹 Главная страница: http://localhost:9999/

🔹 API документация (Swagger): http://localhost:9999/swagger-ui/index.html

---
## Дополнительные материалы

- [Создание ключей доступа для IAM-пользователя (AWS Docs)](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html)
- [Настройка переменных окружения AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-envvars.html)
- [Amazon S3 Руководство пользователя](https://docs.aws.amazon.com/AmazonS3/latest/userguide/Welcome.html)
- [Установка Postgres](https://selectel.ru/blog/tutorials/ustanovka-postgresql-15-windows/)
- [Настройка SMTP Gmail для отправки почты](https://www.digitalocean.com/community/tutorials/how-to-use-google-s-smtp-server-ru)
- [Создание ключей reCAPTCHA v2/v3](https://my.hostiman.ru/knowledge/108/510-kak-poluchit-privatnyy-i-publichnyy-klyuchi-dlya-recaptcha)
- [Создание Telegram-бота через BotFather и получение его api](https://docs.radist.online/radist.online-docs/nashi-produkty/radist-web/podklyucheniya/telegram-bot/instrukciya-po-sozdaniyu-i-nastroiki-bota-v-botfather)
- [Создание OAuth 2.0 клиента в Google Cloud Console](https://support.google.com/workspacemigrate/answer/9222992?hl=ru)
- [Создание Facebook App (Meta for Developers)](https://developers.facebook.com/docs/development/create-an-app?locale=ru_RU)