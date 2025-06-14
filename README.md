# MathBilim - образовательная платформа

## ⚙ Требования для запуска

- [Java 21](https://docs.fabricmc.net/ru_ru/players/installing-java/windows)
- [PostgresQL](https://selectel.ru/blog/tutorials/ustanovka-postgresql-15-windows/)
- [Git](https://docs.github.com/ru/desktop/installing-and-authenticating-to-github-desktop/installing-github-desktop)

---

## <h1>🛠 Установка и запуск</h1>

<h3>1. Создай папку, куда будешь клонировать проект.</h3>

<h3>2. Далее перейди в эту папку с помощью командной строки <br> (если папка на рабочем столе не забудьте выполнить cd ~/Desktop):</h3>

```
cd <ваша папка> 
```

<h3>3. Клонируй репозиторий:</h3>

```
git clone https://github.com/aishkoy/mathbilim.git
```
<h3>4. Зайди в папку с проектом:</h3>

```
cd mathbilim
```
<h3>5. Перейди на ветку dev:</h3>

```
git checkout dev
```
<h3>6. Запусти сборку jar:</h3>


✅ В Windows:
```
.\mvnw clean package -DskipTests
```
✅ В Mac:
```
./mvnw clean package -DskipTests
```
<h3>7. Далее перейди в папку target:</h3>

```
cd target
```
<h3>8. В папке target нужно создать файл без расширения, с содержимым:</h3>

```
AWS_ACCESS_KEY_ID=<ваш AWS_ACCESS_KEY_ID>
AWS_SECRET_ACCESS_KEY=<ваш AWS_SECRET_ACCESS_KEY>
AWS_REGION=<ваш AWS_REGION>
AWS_S3_BUCKET_NAME=<ваш AWS_S3_BUCKET_NAME>

DB_URL=<ваш url базы>
DB_USERNAME=<ваш логин к базе>
DB_PASSWORD=<ваш пароль к базе>

GOOGLE_CLIENT_ID=<ваш Google Client ID>
GOOGLE_CLIENT_SECRET=<ваш Google Client Secret>

RESET_EMAIL=<ваш email>
RESET_PASSWORD=<ваш password>

```

<h3>9. Запусти приложение командой:</h3>

```
java -jar mathbilim-0.0.1-SNAPSHOT.jar
```

🔹 Главная страница: http://localhost:9999/

🔹 API документация (Swagger): http://localhost:9999/swagger-ui/index.html

