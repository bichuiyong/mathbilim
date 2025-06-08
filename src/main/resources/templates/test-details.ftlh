<#import "layout.ftlh" as main>
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
            border-left: 4px solid #0d6efd;
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
    </style>
</head>
<body>
<div class="container py-5">
    <div class="test-header text-center mb-5">
        <span class="badge bg-primary subject-badge mb-3">Математика</span>
        <h1 class="display-5 fw-bold mb-3">Предметный тест по Математике</h1>
        <p class="lead mb-4">Проверьте свои знания перед основным тестированием</p>
    </div>

    <div class="row">
        <div class="col-lg-8">
            <div class="card mb-4 border-0 shadow-sm">
                <div class="card-body">
                    <h2 class="h4 fw-bold mb-3">Добро пожаловать на предметный тест по Математике</h2>
                    <p class="mb-4">Тест состоит из 30 заданий, на выполнение которых отводится 30 минут. Во время выполнения заданий Вы можете изменить выбранный вариант ответа, но сделать это можно только один раз для каждого задания. При исправлении новый (правильный) ответ будет представлен квадратиком.</p>

                    <p class="mb-4">После прохождения теста Вы получите результат, в котором будет указано количество данных Вами правильных ответов и общий процент правильно и неправильно выполненных заданий. Желаем удачи!</p>

                    <h3 class="h5 fw-bold mt-4 mb-3">Правила прохождения теста:</h3>
                    <ul class="rules-list">
                        <li>На прохождение теста дается ограниченное время</li>
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
                            <p class="mb-0">30 минут</p>
                        </div>
                    </div>
                    <div class="d-flex align-items-center mb-3">
                        <i class="bi bi-list-task me-2 text-primary"></i>
                        <div>
                            <p class="mb-0 fw-bold">Количество заданий:</p>
                            <p class="mb-0">30</p>
                        </div>
                    </div>
                    <div class="d-flex align-items-center mb-4">
                        <i class="bi bi-bar-chart me-2 text-primary"></i>
                        <div>
                            <p class="mb-0 fw-bold">Сложность:</p>
                            <p class="mb-0">Средняя</p>
                        </div>
                    </div>
                    <button class="btn btn-primary btn-start-test w-100 py-2">
                        Начать тест
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