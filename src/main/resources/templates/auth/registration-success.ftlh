<#import "../layout.ftlh" as main>

<@main.layout "Регистрация успешна">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6">
                <div class="text-center">
                    <!-- Анимированная иконка успеха -->
                    <div class="position-relative d-inline-block mb-4">
                        <div class="rounded-circle bg-light-blue d-flex align-items-center justify-content-center"
                             style="width: 120px; height: 120px; animation: pulse 2s infinite;">
                            <i class="fas fa-envelope-open-text text-primary" style="font-size: 3.5rem;"></i>
                        </div>
                        <div class="position-absolute bottom-0 end-0">
                            <div class="rounded-circle bg-success text-white d-flex align-items-center justify-content-center"
                                 style="width: 40px; height: 40px; animation: scaleIn 0.5s ease-out;">
                                <i class="fas fa-check"></i>
                            </div>
                        </div>
                    </div>

                    <h2 class="mb-3 fw-bold">Почти готово!</h2>

                    <p class="text-muted mb-4 fs-5">
                        Мы отправили письмо с подтверждением на ваш email
                    </p>

                    <div class="bg-light rounded-3 p-4 mb-4 text-start">
                        <h5 class="mb-3">
                            <i class="fas fa-tasks text-primary me-2"></i>
                            Что делать дальше:
                        </h5>
                        <ol class="mb-0">
                            <li class="mb-2">Откройте вашу почту</li>
                            <li class="mb-2">Найдите письмо от MathBilim.kg</li>
                            <li class="mb-2">Нажмите на кнопку подтверждения</li>
                            <li>Войдите в систему с вашими данными</li>
                        </ol>
                    </div>

                    <div class="alert alert-warning d-flex align-items-start mb-4">
                        <i class="fas fa-lightbulb me-3 mt-1"></i>
                        <div class="text-start">
                            <strong>Не видите письмо?</strong><br>
                            Проверьте папку "Спам" или "Промоакции". Письмо может попасть туда автоматически.
                        </div>
                    </div>

                    <div class="d-flex flex-column flex-sm-row gap-3 justify-content-center">
                        <a href="/auth/login" class="btn btn-primary">
                            <i class="fas fa-sign-in-alt me-2"></i>
                            Перейти к входу
                        </a>
                        <a href="/auth/resend-verification" class="btn btn-outline-primary">
                            <i class="fas fa-redo me-2"></i>
                            Отправить повторно
                        </a>
                    </div>

                    <div class="mt-4">
                        <small class="text-muted">
                            Письмо обычно приходит в течение 1-2 минут
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        @keyframes pulse {
            0% {
                box-shadow: 0 0 0 0 rgba(37, 99, 235, 0.4);
            }
            70% {
                box-shadow: 0 0 0 20px rgba(37, 99, 235, 0);
            }
            100% {
                box-shadow: 0 0 0 0 rgba(37, 99, 235, 0);
            }
        }

        @keyframes scaleIn {
            0% {
                transform: scale(0);
                opacity: 0;
            }
            50% {
                transform: scale(1.2);
            }
            100% {
                transform: scale(1);
                opacity: 1;
            }
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>
</@main.layout>