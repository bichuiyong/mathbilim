<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <link rel="stylesheet" href="../static/scss/main.css">
    <title>Ошибка верификации - MathBilim</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

    <div class="verification-page">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card verification-card">
                        <div class="card-body text-center">
                            <div class="verification-icon danger">
                                <i class="fas fa-exclamation-triangle"></i>
                            </div>

                            <h2 class="verification-title danger">Ошибка верификации</h2>

                            <#if error??>
                                <div class="alert alert-danger">
                                    <i class="fas fa-times-circle me-2"></i>
                                    ${error}
                                </div>
                            </#if>

                            <p class="verification-text">
                                К сожалению, произошла ошибка при подтверждении вашего email адреса.
                            </p>

                            <div class="reasons-list">
                                <h6 class="mb-3" style="color: #007bff; font-weight: 600;">
                                    <i class="fas fa-list-ul me-2"></i>
                                    Возможные причины:
                                </h6>
                                <ul class="text-start">
                                    <li>Ссылка устарела или недействительна</li>
                                    <li>Email уже был подтвержден ранее</li>
                                    <li>Технические неполадки на сервере</li>
                                    <li>Поврежденная ссылка из письма</li>
                                </ul>
                            </div>

                            <div class="d-grid gap-3 mt-4">
                                <a href="/auth/resend-verification" class="btn btn-primary">
                                    <i class="fas fa-redo me-2"></i>
                                    Запросить новое письмо
                                </a>
                                <a href="/auth/login" class="btn btn-outline-secondary">
                                    <i class="fas fa-sign-in-alt me-2"></i>
                                    На страницу входа
                                </a>
                            </div>

                            <div class="mt-4 pt-3" style="border-top: 1px solid #dee2e6;">
                                <small class="text-muted">
                                    <i class="fas fa-question-circle me-1"></i>
                                    Нужна помощь? <a href="/support">Обратитесь в поддержку</a>
                                </small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@main.layout>