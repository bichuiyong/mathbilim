<#import "../layout.ftlh" as main>
<@main.layout>
    <link rel="stylesheet" href="/static/scss/main.css">
    <title>Пароль изменен - MathBilim</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

    <div class="verification-page">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card verification-card">
                        <div class="card-body text-center">
                            <div class="verification-icon success">
                                <i class="fas fa-check-circle"></i>
                            </div>

                            <h2 class="verification-title success">Пароль успешно изменен!</h2>

                            <#if message??>
                                <div class="alert alert-success">
                                    <i class="fas fa-check-circle me-2"></i>
                                    ${message}
                                </div>
                            </#if>

                            <p class="verification-text">
                                Ваш пароль был успешно изменен. Теперь вы можете войти в систему, используя новый пароль.
                            </p>

                            <div class="alert alert-info">
                                <i class="fas fa-shield-alt me-2"></i>
                                <strong>Безопасность:</strong> Рекомендуем запомнить новый пароль и не передавать его третьим лицам.
                            </div>

                            <div class="d-grid gap-3 mt-4">
                                <a href="/auth/login" class="btn btn-primary">
                                    <i class="fas fa-sign-in-alt me-2"></i>
                                    Войти в систему
                                </a>
                                <a href="/" class="btn btn-outline-secondary">
                                    <i class="fas fa-home me-2"></i>
                                    На главную страницу
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@main.layout>