<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        .test-header {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 2rem;
            margin-bottom: 2rem;
        }
        .subject-badge {
            font-size: 1rem;
            padding: 0.5rem 1rem;
        }
        .test-info-card {
            border-left: 3px solid #388dff;
            border-radius: 0 8px 8px 0;
        }
        .btn-start-test {
            padding: 0.75rem 2rem;
            font-size: 1.1rem;
            font-weight: 500;
        }
        .rules-list {
            list-style-type: none;
            padding-left: 1.5rem;
        }
        .rules-list li {
            position: relative;
            margin-bottom: 0.5rem;
        }
        .rules-list li:before {
            content: "•";
            color: #0d6efd;
            font-weight: bold;
            position: absolute;
            left: -1rem;
        }
        .additional-info {
            background-color: #f1f8ff;
            border-radius: 8px;
            padding: 1.5rem;
            margin-top: 2rem;
        }
        .custom {
            background-color: #388dff;
        }
    </style>
    <body>
    <div class="container py-5">
        <div class="test-header text-center mb-5">
            <h1 class="display-5 fw-bold mb-3">Тест: ${test.name}</h1>
            <p class="lead mb-4">Проверьте свои знания перед основным тестированием</p>
        </div>

        <div class="row">
            <div class="col-lg-8">
                <div class="card mb-4 border-0 shadow-sm">
                    <div class="card-body">
                        <h2 class="h4 fw-bold mb-3">Добро пожаловать на тест: ${test.name}</h2>
                        <p class="mb-4">Тест состоит из ${test.questionCount} заданий,<#if test.hasLimit> на выполнение которых отводится ${test.timeLimit} минут.<#else>время на выполнение не ограничено</#if> Во время выполнения заданий Вы можете изменить выбранный вариант ответа, но сделать это можно только один раз для каждого задания. При исправлении новый (правильный) ответ будет представлен квадратиком.</p>

                        <p class="mb-4">После прохождения теста Вы получите результат, в котором будет указано количество данных Вами правильных ответов и общий процент правильно и неправильно выполненных заданий. Желаем удачи!</p>

                        <h3 class="h5 fw-bold mt-4 mb-3">Правила прохождения теста:</h3>
                        <ul class="rules-list">
                            <#if test.hasLimit><li>На прохождение теста дается ограниченное время</li></#if>
                            <li>Можно вернуться к предыдущим вопросам</li>
                            <li>Изменение ответа возможно только один раз для каждого вопроса</li>
                            <li>После окончания времени тест завершается автоматически</li>
                            <li>Результаты доступны сразу после завершения теста</li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-lg-4">
                <div class="card mb-4 test-info-card shadow-sm">
                    <div class="card-body">
                        <h3 class="h5 fw-bold mb-4">Информация о тесте</h3>
                        <div class="d-flex align-items-center mb-3">
                            <i class="bi bi-clock me-2 text-primary"></i>
                            <div>
                                <p class="mb-0 fw-bold">Время:</p>
                                <#if test.hasLimit>
                                    <p class="mb-0">${test.timeLimit} минут</p>
                                <#else>
                                    Неограниченно
                                </#if>
                            </div>
                        </div>
                        <div class="d-flex align-items-center mb-3">
                            <i class="bi bi-list-task me-2 text-primary"></i>
                            <div>
                                <p class="mb-0 fw-bold">Количество заданий:</p>
                                <p class="mb-0">${test.questionCount}</p>
                            </div>
                        </div>
                        <button class="btn btn-primary custom btn-start-test w-100 py-2">
                            <a style="all:unset" href="/tests/${test.id}/pass">Начать тест</a>
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="additional-info">
            <h3 class="h5 fw-bold mb-3">Для более подробных результатов</h3>
            <p>Показывающих уровень подготовленности тестируемого в конкретных разделах теста ОРТ Вам следует пройти Пробное бланочное тестирование ЦООМО.</p>
            <p class="mb-0">Тесты, используемые в этом случае, отличаются по содержанию от тестов бесплатного онлайн-тестирования.</p>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@main.layout>