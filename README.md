# MathBilim

Образовательная платформа для изучения математики с интерактивными уроками и персонализированным подходом к обучению.

##  Содержание

- [Системные требования](#системные-требования)
- [Подготовка окружения](#подготовка-окружения)
- [Настройка хранилища файлов](#настройка-хранилища-файлов)
- [Настройка базы данных](#настройка-базы-данных)
- [Настройка внешних сервисов](#настройка-внешних-сервисов)
- [Установка проекта](#установка-проекта)
- [Конфигурация](#конфигурация)
- [Запуск](#запуск)
- [API документация](#api-документация)
- [Решение проблем](#решение-проблем)
- [Вклад в проект](#вклад-в-проект)

##  Системные требования

- **Java 21** или выше
- **PostgreSQL 15+**
- **Git**
- **Хранилище файлов**: AWS S3 или MinIO (локальная альтернатива)

##  Подготовка окружения

### Ubuntu/Debian

```bash
# Обновление системы
sudo apt update && sudo apt upgrade -y

# Установка Java 21
sudo apt install openjdk-21-jdk -y

# Проверка установки Java
java -version

# Установка PostgreSQL
sudo apt install postgresql postgresql-contrib -y

# Установка Git
sudo apt install git -y

# Установка curl и wget (если не установлены)
sudo apt install curl wget -y
```

### CentOS/RHEL/Fedora

```bash
# Обновление системы
sudo dnf update -y  # для Fedora
# sudo yum update -y  # для CentOS/RHEL

# Установка Java 21
sudo dnf install java-21-openjdk java-21-openjdk-devel -y

# Проверка установки Java
java -version

# Установка PostgreSQL
sudo dnf install postgresql postgresql-server postgresql-contrib -y

# Инициализация базы данных PostgreSQL
sudo postgresql-setup --initdb
sudo systemctl enable postgresql
sudo systemctl start postgresql

# Установка Git
sudo dnf install git -y

# Установка curl и wget
sudo dnf install curl wget -y
```

### Arch Linux

```bash
# Обновление системы
sudo pacman -Syu

# Установка Java 21
sudo pacman -S jdk21-openjdk

# Проверка установки Java
java -version

# Установка PostgreSQL
sudo pacman -S postgresql

# Инициализация базы данных
sudo -u postgres initdb -D /var/lib/postgres/data
sudo systemctl enable postgresql
sudo systemctl start postgresql

# Установка Git
sudo pacman -S git

# Установка curl и wget
sudo pacman -S curl wget
```

### Windows

1. **Java 21:** [Скачать с официального сайта Oracle](https://www.oracle.com/java/technologies/downloads/#java21) или [OpenJDK](https://adoptium.net/)
2. **PostgreSQL:** [Скачать установщик](https://www.postgresql.org/download/windows/)
3. **Git:** [Скачать Git for Windows](https://git-scm.com/download/win)

### macOS

```bash
# Установка Homebrew (если не установлен)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Установка Java 21
brew install openjdk@21

# Установка PostgreSQL
brew install postgresql@15
brew services start postgresql@15

# Установка Git
brew install git
```

##  Настройка хранилища файлов

Выберите один из вариантов:

### Вариант 1: AWS S3 (Облачное решение)

**Настройка AWS S3:**
1. [Создайте аккаунт AWS](https://aws.amazon.com/free/)
2. [Настройте S3 bucket](https://docs.aws.amazon.com/AmazonS3/latest/userguide/create-bucket-overview.html)
3. [Создайте IAM пользователя с правами доступа к S3](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html)
4. [Получите Access Key и Secret Key](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html)

**Полезные ссылки:**
- [AWS S3 Getting Started Guide](https://docs.aws.amazon.com/AmazonS3/latest/userguide/GetStartedWithS3.html)
- [AWS CLI Configuration](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-quickstart.html)

### Вариант 2: MinIO (Локальное решение, бесплатно)

**Установка MinIO Server:**

#### Ubuntu/Debian:
```bash
# Скачивание MinIO
wget https://dl.min.io/server/minio/release/linux-amd64/minio
chmod +x minio
sudo mv minio /usr/local/bin/

# Создание директории для данных
sudo mkdir -p /opt/minio/data
sudo chown -R $USER:$USER /opt/minio

# Запуск MinIO (в отдельном терминале)
MINIO_ROOT_USER=minioadmin MINIO_ROOT_PASSWORD=minioadmin123 minio server /opt/minio/data --console-address ":9001"
```

#### Windows:
```cmd
# Скачайте minio.exe с официального сайта
curl -O https://dl.min.io/server/minio/release/windows-amd64/minio.exe

# Создайте директорию для данных
mkdir C:\minio\data

# Запуск MinIO
set MINIO_ROOT_USER=minioadmin
set MINIO_ROOT_PASSWORD=minioadmin123
minio.exe server C:\minio\data --console-address ":9001"
```

#### macOS:
```bash
# Установка через Homebrew
brew install minio/stable/minio

# Создание директории для данных
mkdir -p ~/minio/data

# Запуск MinIO
MINIO_ROOT_USER=minioadmin MINIO_ROOT_PASSWORD=minioadmin123 minio server ~/minio/data --console-address ":9001"
```

**Настройка MinIO:**
1. Откройте веб-интерфейс: http://localhost:9001
2. Войдите с учетными данными: `minioadmin` / `minioadmin`
3. Создайте новый bucket для проекта (например, `mathbilim`)

**Полезные ссылки:**
- [MinIO Quickstart Guide](https://min.io/docs/minio/linux/index.html)
- [MinIO Console Guide](https://min.io/docs/minio/linux/administration/minio-console.html)
- [MinIO Client (mc) Setup](https://min.io/docs/minio/linux/reference/minio-mc.html)

##  Настройка базы данных

### Создание пользователя и базы данных PostgreSQL

```bash
# Переключение на пользователя postgres
sudo -u postgres psql

# В PostgreSQL консоли выполните:
CREATE USER mathbilim_user WITH PASSWORD 'your_secure_password';
CREATE DATABASE mathbilim OWNER mathbilim_user;
GRANT ALL PRIVILEGES ON DATABASE mathbilim TO mathbilim_user;

# Выход из PostgreSQL
\q
```

### Настройка доступа к PostgreSQL

Отредактируйте файл конфигурации PostgreSQL:

#### Ubuntu/Debian:
```bash
# Найдите файл pg_hba.conf
sudo find /etc/postgresql -name "pg_hba.conf"

# Отредактируйте файл (замените версию на вашу)
sudo nano /etc/postgresql/15/main/pg_hba.conf
```

#### CentOS/RHEL:
```bash
sudo nano /var/lib/pgsql/data/pg_hba.conf
```

Добавьте или измените строку для локального доступа:
```
# TYPE  DATABASE        USER            ADDRESS                 METHOD
local   all             all                                     md5
host    all             all             127.0.0.1/32            md5
```

Перезапустите PostgreSQL:
```bash
sudo systemctl restart postgresql
```

##  Настройка внешних сервисов

### Настройка Google reCAPTCHA

1. Перейдите в [Google reCAPTCHA Admin Console](https://www.google.com/recaptcha/admin)
2. Нажмите "+" для создания нового сайта
3. Выберите тип reCAPTCHA (рекомендуется v2 "I'm not a robot")
4. Добавьте домен: `localhost` (для разработки)
5. Скопируйте **Secret Key** и вставьте в `RECAPTCHA_SECRET_KEY`

### Настройка Telegram Bot

1. Найдите [@BotFather](https://t.me/botfather) в Telegram
2. Отправьте команду `/newbot`
3. Следуйте инструкциям для создания бота:
    - Введите имя бота (например, "MathBilim Helper")
    - Введите username бота (должен заканчиваться на 'bot', например, "mathbilim_helper_bot")
4. BotFather отправит вам токен в формате: `1234567890:ABCdefGHIjklMNOpqrsTUVwxyz`
5. Скопируйте этот токен и вставьте в `TGBOTKEY`

**Дополнительные настройки Telegram Bot:**
```bash
# Установите описание бота
curl -X POST https://api.telegram.org/bot<YOUR_BOT_TOKEN>/setMyDescription \
  -H "Content-Type: application/json" \
  -d '{"description": "Образовательный бот для платформы MathBilim"}'

# Установите команды бота
curl -X POST https://api.telegram.org/bot<YOUR_BOT_TOKEN>/setMyCommands \
  -H "Content-Type: application/json" \
  -d '{"commands": [{"command": "start", "description": "Начать работу с ботом"}, {"command": "help", "description": "Получить помощь"}]}'
```

##  Установка проекта

### 1. Клонирование репозитория

```bash
# Создайте директорию для проекта
mkdir ~/mathbilim-project
cd ~/mathbilim-project

# Клонируйте репозиторий
git clone https://github.com/aishkoy/mathbilim.git
cd mathbilim

# Переключитесь на ветку разработки
git checkout dev
```

### 2. Сборка проекта

**Linux/macOS:**
```bash
./mvnw clean package -DskipTests
```

**Windows:**
```cmd
.\mvnw.cmd clean package -DskipTests
```

Если возникает ошибка с правами доступа на Linux/macOS:
```bash
chmod +x mvnw
```

##  Конфигурация

### Создание файла конфигурации

Перейдите в директорию `target` и создайте файл `.env`:

```bash
cd target
nano .env  # или используйте любой текстовый редактор
```

### Для AWS S3:
```env
# AWS S3 конфигурация
AWS_ACCESS_KEY_ID=your_aws_access_key_id
AWS_SECRET_ACCESS_KEY=your_aws_secret_access_key
AWS_REGION=us-east-1
AWS_S3_BUCKET_NAME=your_s3_bucket_name

# База данных PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/mathbilim
DB_USERNAME=mathbilim_user
DB_PASSWORD=your_secure_password

# Email сервис для сброса паролей
RESET_EMAIL=your_email@gmail.com
RESET_PASSWORD=your_app_password

# Google reCAPTCHA
RECAPTCHA_SECRET_KEY=your_recaptcha_secret_key

# Telegram Bot
TGBOTKEY=your_telegram_bot_token
```
### Для MinIO:
```env
# MinIO конфигурация (совместимо с AWS S3 API)
AWS_ACCESS_KEY_ID=your_minio_access_key
AWS_SECRET_ACCESS_KEY=your_minio_secret_key
AWS_REGION=us-east-1
AWS_S3_BUCKET_NAME=mathbilim-files
AWS_ENDPOINT_URL=http://localhost:9000

# База данных PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/mathbilim
DB_USERNAME=mathbilim_user
DB_PASSWORD=your_secure_password

# Email сервис для сброса паролей
RESET_EMAIL=your_email@gmail.com
RESET_PASSWORD=your_app_password

# Google reCAPTCHA
RECAPTCHA_SECRET_KEY=your_recaptcha_secret_key

# Telegram Bot
TGBOTKEY=your_telegram_bot_token
```
### Настройка Google OAuth 2.0

1. Перейдите в [Google Cloud Console](https://console.cloud.google.com/)
2. Создайте новый проект или выберите существующий
3. Включите Google+ API
4. Создайте OAuth 2.0 credentials
5. Добавьте redirect URI: `http://localhost:9999/login/oauth2/code/google`

### Настройка Google reCAPTCHA

1. Перейдите в [Google reCAPTCHA Admin Console](https://www.google.com/recaptcha/admin)
2. Нажмите "+" для создания нового сайта
3. Выберите тип reCAPTCHA (рекомендуется v2 "I'm not a robot")
4. Добавьте домен: `localhost` (для разработки)
5. Скопируйте **Secret Key** и вставьте в `RECAPTCHA_SECRET_KEY`

<h3>4. Запусти приложение командой:</h3>
=======
### Настройка Telegram Bot

1. Найдите [@BotFather](https://t.me/botfather) в Telegram
2. Отправьте команду `/newbot`
3. Следуйте инструкциям для создания бота:
    - Введите имя бота (например, "MathBilim Helper")
    - Введите username бота (должен заканчиваться на 'bot', например, "mathbilim_helper_bot")
4. BotFather отправит вам токен в формате: `1234567890:ABCdefGHIjklMNOpqrsTUVwxyz`
5. Скопируйте этот токен и вставьте в `TGBOTKEY`

**Дополнительные настройки Telegram Bot:**
```bash
# Установите описание бота
curl -X POST https://api.telegram.org/bot<YOUR_BOT_TOKEN>/setMyDescription \
  -H "Content-Type: application/json" \
  -d '{"description": "Образовательный бот для платформы MathBilim"}'

# Установите команды бота
curl -X POST https://api.telegram.org/bot<YOUR_BOT_TOKEN>/setMyCommands \
  -H "Content-Type: application/json" \
  -d '{"commands": [{"command": "start", "description": "Начать работу с ботом"}, {"command": "help", "description": "Получить помощь"}]}'
```

### Настройка Email для сброса паролей

**Для Gmail:**
1. Включите двухфакторную аутентификацию
2. Создайте [App Password](https://support.google.com/accounts/answer/185833)
3. Используйте App Password в поле `RESET_PASSWORD`

## 🎯 Запуск

### Последовательность запуска

1. **Запустите PostgreSQL** (если не запущен):
```bash
sudo systemctl start postgresql
```

2. **Запустите MinIO** (если используете MinIO):
```bash
MINIO_ROOT_USER=minioadmin MINIO_ROOT_PASSWORD=minioadmin123 minio server /opt/minio/data --console-address ":9001"
```

3. **Запустите приложение**:
```bash
cd target
java -jar mathbilim-0.0.1-SNAPSHOT.jar
```

### Проверка запуска

Приложение будет доступно по адресам:
- **Главная страница:** http://localhost:9999/
- **API документация (Swagger):** http://localhost:9999/swagger-ui/index.html
- **MinIO Console** (если используется): http://localhost:9001/

##  API документация

Полная документация API доступна через Swagger UI после запуска приложения:
http://localhost:9999/swagger-ui/index.html

##  Решение проблем

### Частые ошибки

**Ошибка подключения к базе данных:**
```bash
# Проверьте статус PostgreSQL
sudo systemctl status postgresql

# Проверьте подключение к базе
psql -U mathbilim_user -d mathbilim -h localhost
```

**Ошибка с правами доступа Maven Wrapper:**
```bash
chmod +x mvnw
```

**Ошибка "Port already in use":**
```bash
# Найдите процесс, использующий порт 9999
sudo lsof -i :9999
# Или для систем без lsof
sudo netstat -tlnp | grep :9999

# Остановите процесс
sudo kill -9 <PID>
```

**Проблемы с MinIO:**
```bash
# Проверьте, запущен ли MinIO
curl http://localhost:9000/minio/health/live

# Проверьте логи MinIO
journalctl -u minio
```

### Системные службы (опционально)

Создание systemd сервиса для автозапуска:

```bash
# Создайте файл сервиса
sudo nano /etc/systemd/system/mathbilim.service
```

Содержимое файла:
```ini
[Unit]
Description=MathBilim Educational Platform
After=network.target postgresql.service

[Service]
Type=simple
User=your_username
WorkingDirectory=/home/your_username/mathbilim-project/mathbilim/target
ExecStart=/usr/bin/java -jar mathbilim-0.0.1-SNAPSHOT.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Активация сервиса:
```bash
sudo systemctl daemon-reload
sudo systemctl enable mathbilim
sudo systemctl start mathbilim
```

##  Вклад в проект

1. Сделайте форк репозитория
2. Создайте ветку для новой функции (`git checkout -b feature/amazing-feature`)
3. Зафиксируйте изменения (`git commit -m 'Add amazing feature'`)
4. Отправьте изменения в ветку (`git push origin feature/amazing-feature`)
5. Создайте Pull Request

##  Лицензия

Информация о лицензии будет добавлена позже.

---

**️ Важные замечания по безопасности:**
- Никогда не публикуйте файл `.env` в репозитории
- Используйте сложные пароли для базы данных
- Регулярно обновляйте зависимости проекта
- В продакшене используйте HTTPS и настройте firewall