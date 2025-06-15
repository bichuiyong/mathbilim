<#import "../layout.ftlh" as main>

<@main.layout "Повторная отправка подтверждения">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="text-center mb-4">
                    <div class="position-relative d-inline-block">
                        <div class="rounded-circle bg-warning bg-opacity-10 d-flex align-items-center justify-content-center mb-3"
                             style="width: 80px; height: 80px;">
                            <i class="fas fa-envelope text-warning" style="font-size: 2rem;"></i>
                        </div>
                        <div class="position-absolute bottom-0 end-0">
                            <div class="rounded-circle bg-white shadow-sm d-flex align-items-center justify-content-center"
                                 style="width: 30px; height: 30px;">
                                <i class="fas fa-redo text-primary" style="font-size: 0.875rem;"></i>
                            </div>
                        </div>
                    </div>
                    <h3 class="fw-bold">Не получили письмо?</h3>
                    <p class="text-muted">Мы можем отправить его повторно</p>
                </div>

                <div class="bg-white rounded shadow-lg p-4 p-md-5">
                    <#if error??>
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <i class="fas fa-exclamation-circle me-2"></i>
                            <div>${error}</div>
                        </div>
                    </#if>

                    <#if message??>
                        <div class="alert alert-success" role="alert">
                            <div class="text-center">
                                <i class="fas fa-check-circle mb-2" style="font-size: 2rem;"></i>
                                <h5 class="mb-2">Письмо отправлено!</h5>
                                <p class="mb-0">${message}</p>
                            </div>
                        </div>
                    </#if>

                    <#if !message??>
                        <form action="/auth/resend-verification" method="post">
                            <@main.csrf/>

                            <div class="mb-4">
                                <label for="email" class="form-label small text-muted">Email адрес</label>
                                <div class="input-group input-group-lg">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-at text-muted"></i>
                                    </span>
                                    <input type="email"
                                           class="form-control border-start-0"
                                           id="email"
                                           name="email"
                                           placeholder="example@email.com"
                                           value="${RequestParameters.email!''}"
                                           required
                                           autofocus>
                                </div>
                                <small class="text-muted">Введите email, использованный при регистрации</small>
                            </div>

                            <div class="d-grid mb-4">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-paper-plane me-2"></i>
                                    Отправить письмо повторно
                                </button>
                            </div>
                        </form>
                    </#if>

                    <!-- Советы -->
                    <div class="bg-light rounded p-3 mb-4">
                        <h6 class="mb-2">
                            <i class="fas fa-lightbulb text-warning me-2"></i>
                            Полезные советы:
                        </h6>
                        <ul class="mb-0 small text-muted" style="padding-left: 1.5rem;">
                            <li>Проверьте папку "Спам" или "Промоакции"</li>
                            <li>Добавьте noreply@mathbilim.kg в контакты</li>
                            <li>Письмо обычно приходит в течение 1-2 минут</li>
                            <li>Если письмо не пришло, попробуйте через 5 минут</li>
                        </ul>
                    </div>

                    <div class="text-center">
                        <a href="/auth/login" class="text-muted text-decoration-none">
                            <i class="fas fa-arrow-left me-1"></i>
                            Вернуться к входу
                        </a>
                    </div>
                </div>

                <!-- Дополнительная помощь -->
                <div class="text-center mt-4">
                    <small class="text-muted">
                        Все еще проблемы?
                        <a href="/help" class="text-primary">Свяжитесь с поддержкой</a>
                    </small>
                </div>
            </div>
        </div>
    </div>

    <style>
        .input-group-lg .form-control,
        .input-group-lg .input-group-text {
            font-size: 1rem;
            padding: 0.75rem 1rem;
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>
</@main.layout>