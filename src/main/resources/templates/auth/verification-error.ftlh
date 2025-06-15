<#import "../layout.ftlh" as main>

<@main.layout "Ошибка верификации">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6">
                <div class="text-center">
                    <!-- Анимированная иконка ошибки -->
                    <div class="mb-4 position-relative">
                        <div class="rounded-circle bg-danger bg-opacity-10 d-flex align-items-center justify-content-center mx-auto"
                             style="width: 120px; height: 120px;">
                            <i class="fas fa-link-slash text-danger" style="font-size: 3rem;"></i>
                        </div>
                        <div class="position-absolute" style="bottom: 0; right: 50%; transform: translateX(50%);">
                            <div class="bg-white rounded-circle shadow p-2">
                                <i class="fas fa-exclamation text-warning"></i>
                            </div>
                        </div>
                    </div>

                    <h2 class="mb-3 fw-bold">Ошибка верификации</h2>

                    <#if error??>
                        <div class="alert alert-danger mb-4">
                            <i class="fas fa-exclamation-triangle me-2"></i>
                            ${error}
                        </div>
                    <#else>
                        <p class="text-muted fs-5 mb-4">
                            Не удалось подтвердить ваш email адрес
                        </p>
                    </#if>

                    <!-- Возможные причины -->
                    <div class="bg-light rounded-3 p-4 mb-4 text-start">
                        <h5 class="mb-3">
                            <i class="fas fa-question-circle text-primary me-2"></i>
                            Возможные причины:
                        </h5>
                        <div class="d-flex align-items-start mb-2">
                            <i class="fas fa-clock text-muted me-3 mt-1"></i>
                            <div>
                                <strong>Ссылка устарела</strong><br>
                                <small class="text-muted">Ссылки действительны только 24 часа</small>
                            </div>
                        </div>
                        <div class="d-flex align-items-start mb-2">
                            <i class="fas fa-sync text-muted me-3 mt-1"></i>
                            <div>
                                <strong>Ссылка уже использована</strong><br>
                                <small class="text-muted">Каждую ссылку можно использовать только один раз</small>
                            </div>
                        </div>
                        <div class="d-flex align-items-start">
                            <i class="fas fa-link text-muted me-3 mt-1"></i>
                            <div>
                                <strong>Поврежденная ссылка</strong><br>
                                <small class="text-muted">Ссылка могла быть неправильно скопирована</small>
                            </div>
                        </div>
                    </div>

                    <!-- Что делать дальше -->
                    <div class="alert alert-info mb-4">
                        <i class="fas fa-lightbulb me-2"></i>
                        <strong>Что делать?</strong> Запросите новое письмо с подтверждением
                    </div>

                    <div class="d-flex flex-column flex-sm-row gap-3 justify-content-center mb-4">
                        <a href="/auth/resend-verification" class="btn btn-primary btn-lg">
                            <i class="fas fa-envelope me-2"></i>
                            Отправить новое письмо
                        </a>
                        <a href="/auth/login" class="btn btn-outline-primary btn-lg">
                            <i class="fas fa-sign-in-alt me-2"></i>
                            Перейти к входу
                        </a>
                    </div>

                    <p class="text-muted">
                        Все еще есть проблемы?
                        <a href="/help" class="text-primary fw-bold">Свяжитесь с поддержкой</a>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <style>
        .min-vh-50 {
            min-height: 50vh;
        }

        .alert {
            border: none;
        }

        .alert-info {
            background-color: rgba(37, 99, 235, 0.1);
            color: #1e40af;
        }
    </style>
</@main.layout>