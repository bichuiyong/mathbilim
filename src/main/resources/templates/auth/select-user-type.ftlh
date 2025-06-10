<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Выбор типа пользователя - Math Bilim</title>
    <link rel="stylesheet" href="/static/css/layout.css">
    <link rel="stylesheet" href="/static/scss/main.css">
    <style>
        .user-type-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .user-type-title {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
            font-size: 24px;
        }

        .user-type-subtitle {
            text-align: center;
            color: #7f8c8d;
            margin-bottom: 40px;
            font-size: 16px;
        }

        .user-type-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .user-type-card {
            border: 2px solid #ecf0f1;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
        }

        .user-type-card:hover {
            border-color: #3498db;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(52, 152, 219, 0.2);
        }

        .user-type-card.selected {
            border-color: #2980b9;
            background: #ebf3fd;
        }

        .user-type-icon {
            font-size: 48px;
            margin-bottom: 15px;
            display: block;
        }

        .user-type-name {
            font-size: 18px;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 8px;
        }

        .user-type-description {
            font-size: 14px;
            color: #7f8c8d;
            line-height: 1.4;
        }

        .user-type-radio {
            position: absolute;
            opacity: 0;
        }

        .submit-button {
            width: 100%;
            padding: 15px;
            background: #3498db;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .submit-button:hover:not(:disabled) {
            background: #2980b9;
        }

        .submit-button:disabled {
            background: #bdc3c7;
            cursor: not-allowed;
        }

        .welcome-text {
            text-align: center;
            margin-bottom: 20px;
            color: #34495e;
        }

        .alert {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            text-align: center;
        }

        .alert-error {
            background: #fee;
            border: 1px solid #fcc;
            color: #c33;
        }

        .alert-success {
            background: #efe;
            border: 1px solid #cfc;
            color: #3c3;
        }
    </style>
</head>
<body>
<div class="user-type-container">
    <h1 class="user-type-title">Добро пожаловать в Math Bilim!</h1>

    <#if user??>
    <p class="welcome-text">
        Привет, <strong>${user.name!"пользователь"}</strong>!
        Для персонализации опыта выберите, кто вы:
    </p>
</#if>

<p class="user-type-subtitle">
    Выбор поможет нам настроить подходящий для вас контент и функции
</p>

<#if error??>
<div class="alert alert-error">
    <strong>⚠️ Ошибка:</strong> ${error}
</div>
</#if>

<#if message??>
<div class="alert alert-success">
    <strong>✅ Успех:</strong> ${message}
</div>
</#if>

<form action="/auth/select-user-type" method="post" id="userTypeForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="user-type-grid">
        <#if userTypes?? && userTypes?has_content>
        <#list userTypes as userType>
        <label class="user-type-card" for="type_${userType.id}">
            <input type="radio"
                   name="userTypeId"
                   value="${userType.id}"
                   id="type_${userType.id}"
                   class="user-type-radio"
                   required>

            <span class="user-type-icon">
                                <#switch userType.name>
                                    <#case "SCHOOLBOY">🎓<#break>
                                    <#case "STUDENT">📚<#break>
                                    <#case "TEACHER">👨‍🏫<#break>
                                    <#case "EXPLORER">🔍<#break>
                                    <#default>👤<#break>
                                </#switch>
            </span>

            <div class="user-type-name">
                <#switch userType.name>
                <#case "SCHOOLBOY">Школьник<#break>
                <#case "STUDENT">Студент<#break>
                <#case "TEACHER">Преподаватель<#break>
                <#case "EXPLORER">Исследователь<#break>
                <#default>${userType.name}<#break>
            </#switch>
    </div>

    <div class="user-type-description">
        <#switch userType.name>
        <#case "SCHOOLBOY">Изучаю математику в школе<#break>
        <#case "STUDENT">Изучаю математику в университете<#break>
        <#case "TEACHER">Преподаю математику<#break>
        <#case "EXPLORER">Интересуюсь математикой<#break>
        <#default>Пользователь платформы<#break>
    </#switch>
    </div>
    </label>
</#list>
<#else>
<div style="grid-column: 1 / -1; text-align: center; color: #7f8c8d;">
    <p>Типы пользователей не найдены.</p>
    <p><a href="/auth/login">Вернуться к авторизации</a></p>
</div>
</#if>
</div>

<#if userTypes?? && userTypes?has_content>
<button type="submit" class="submit-button" id="submitBtn" disabled>
    Продолжить
</button>
</#if>
</form>
</div>

<script>
    const radioButtons = document.querySelectorAll('input[name="userTypeId"]');
    const submitBtn = document.getElementById('submitBtn');
    const cards = document.querySelectorAll('.user-type-card');

    radioButtons.forEach(radio => {
        radio.addEventListener('change', function() {
            cards.forEach(card => card.classList.remove('selected'));

            if (this.checked) {
                this.closest('.user-type-card').classList.add('selected');
                if (submitBtn) {
                    submitBtn.disabled = false;
                }
            }
        });
    });

    cards.forEach(card => {
        card.addEventListener('click', function() {
            const radio = this.querySelector('input[type="radio"]');
            if (radio) {
                radio.checked = true;
                radio.dispatchEvent(new Event('change'));
            }
        });
    });
</script>
</body>
</html>