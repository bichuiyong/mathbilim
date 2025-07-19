<#import "../layout.ftlh" as main>

<@main.layout "Выбор типа аккаунта">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-10 col-lg-8">
                <div class="text-center mb-5">
                    <div class="d-inline-block position-relative mb-3">
                        <img src="https://www.google.com/favicon.ico"
                             width="60"
                             height="60"
                             class="rounded-circle shadow"
                             alt="Google">
                        <div class="position-absolute bottom-0 end-0">
                            <div class="rounded-circle bg-success text-white d-flex align-items-center justify-content-center"
                                 style="width: 24px; height: 24px;">
                                <i class="fas fa-check" style="font-size: 0.75rem;"></i>
                            </div>
                        </div>
                    </div>
                    <h2 class="fw-bold mb-2">Добро пожаловать в MathBilim!</h2>
                    <p class="text-muted fs-5">Выберите, как вы хотите использовать платформу</p>
                </div>

                <form action="/auth/select-user-type" method="post">
                    <@main.csrf/>

                    <div class="row g-4 mb-5">
                        <#list userTypes as type>
                            <div class="col-md-6">
                                <label class="account-type-option h-100" for="type${type.id}">
                                    <input class="form-check-input d-none"
                                           type="radio"
                                           name="userTypeId"
                                           id="type${type.id}"
                                           value="${type.id}"
                                           required>

                                    <div class="account-type-card h-100 p-4 rounded-3 border position-relative overflow-hidden">
                                        <!-- Фоновый градиент при выборе -->
                                        <div class="selected-gradient position-absolute top-0 start-0 w-100 h-100"></div>

                                        <div class="position-relative">
                                            <!-- Иконка в зависимости от типа -->
                                            <div class="mb-3">
                                                <#if type.translations[0].translation?contains("Ученик") || type.translations[0].translation?contains("Student")>
                                                    <i class="fas fa-user-graduate text-primary" style="font-size: 2.5rem;"></i>
                                                <#elseif type.translations[0].translation?contains("Учитель") || type.translations[0].translation?contains("Teacher")>
                                                    <i class="fas fa-chalkboard-teacher text-primary" style="font-size: 2.5rem;"></i>
                                                <#elseif type.translations[0].translation?contains("Родитель") || type.translations[0].translation?contains("Parent")>
                                                    <i class="fas fa-user-friends text-primary" style="font-size: 2.5rem;"></i>
                                                <#else>
                                                    <i class="fas fa-user text-primary" style="font-size: 2.5rem;"></i>
                                                </#if>
                                            </div>

                                            <h4 class="mb-2">${type.translations[0].translation}</h4>

                                            <!-- Примерное описание для каждого типа -->
                                            <p class="text-muted mb-3">
                                                <#if type.translations[0].translation?contains("Ученик") || type.translations[0].translation?contains("Student")>
                                                    Доступ к тестам, олимпиадным задачам и учебным материалам
                                                <#elseif type.translations[0].translation?contains("Учитель") || type.translations[0].translation?contains("Teacher")>
                                                    Создание тестов, отслеживание прогресса учеников, методические материалы
                                                <#elseif type.translations[0].translation?contains("Родитель") || type.translations[0].translation?contains("Parent")>
                                                    Контроль успеваемости, уведомления о прогрессе ребенка
                                                <#else>
                                                    Полный доступ к функциям платформы
                                                </#if>
                                            </p>

                                            <!-- Чекбокс индикатор -->
                                            <div class="selection-indicator position-absolute top-0 end-0 p-3">
                                                <div class="rounded-circle border-2 border-secondary d-flex align-items-center justify-content-center"
                                                     style="width: 24px; height: 24px;">
                                                    <i class="fas fa-check text-white" style="font-size: 0.75rem; display: none;"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </label>
                            </div>
                        </#list>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-primary btn-lg px-5" disabled id="continueBtn">
                            <i class="fas fa-arrow-right me-2"></i>
                            Продолжить
                        </button>

                        <p class="text-muted mt-3 mb-0">
                            <i class="fas fa-info-circle me-1"></i>
                            Вы сможете изменить тип аккаунта позже в настройках профиля
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <style>
        .account-type-option {
            cursor: pointer;
            display: block;
        }

        .account-type-card {
            transition: all 0.3s ease;
            background: white;
            border-color: #e2e8f0 !important;
        }

        .account-type-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
            border-color: #2563eb !important;
        }

        .selected-gradient {
            background: linear-gradient(135deg, rgba(37, 99, 235, 0.05) 0%, rgba(59, 130, 246, 0.05) 100%);
            opacity: 0;
            transition: opacity 0.3s ease;
            z-index: 0;
        }

        input[type="radio"]:checked ~ .account-type-card {
            border-color: #2563eb !important;
            border-width: 2px;
        }

        input[type="radio"]:checked ~ .account-type-card .selected-gradient {
            opacity: 1;
        }

        input[type="radio"]:checked ~ .account-type-card .selection-indicator > div {
            background-color: #2563eb;
            border-color: #2563eb !important;
        }

        input[type="radio"]:checked ~ .account-type-card .selection-indicator i {
            display: block !important;
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>

    <script>
        // Активация кнопки при выборе
        const radioButtons = document.querySelectorAll('input[name="userTypeId"]');
        const continueBtn = document.getElementById('continueBtn');

        radioButtons.forEach(radio => {
            radio.addEventListener('change', function() {
                if (this.checked) {
                    continueBtn.disabled = false;
                }
            });
        });
    </script>
</@main.layout>