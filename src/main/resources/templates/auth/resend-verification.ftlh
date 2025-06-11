<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <link rel="stylesheet" href="../static/scss/main.css">
    <title>Повторная отправка подтверждения - MathBilim</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

    <div class="verification-page">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card verification-card">
                        <div class="card-body">
                            <div class="text-center mb-4">
                                <div class="verification-icon primary">
                                    <i class="fas fa-paper-plane"></i>
                                </div>
                                <h2 class="verification-title primary">Повторная отправка подтверждения</h2>
                            </div>

                            <#if error??>
                                <div class="alert alert-danger">
                                    <i class="fas fa-exclamation-triangle me-2"></i>
                                    ${error}
                                </div>
                            </#if>

                            <#if message??>
                                <div class="alert alert-success">
                                    <i class="fas fa-check-circle me-2"></i>
                                    ${message}
                                </div>
                            </#if>

                            <form method="post" action="/auth/resend-verification">
                                <div class="mb-3">
                                    <label for="email" class="form-label">
                                        <i class="fas fa-envelope me-2"></i>
                                        Email адрес
                                    </label>
                                    <input type="email" class="form-control" id="email" name="email"
                                           placeholder="example@email.com" required>
                                    <div class="form-text">
                                        <i class="fas fa-info-circle me-1"></i>
                                        Введите email, который вы указывали при регистрации
                                    </div>
                                </div>

                                <div class="d-grid mb-3">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-paper-plane me-2"></i>
                                        Отправить письмо повторно
                                    </button>
                                </div>
                            </form>

                            <div class="text-center mt-4">
                                <a href="/auth/login" class="text-decoration-none">
                                    <i class="fas fa-arrow-left me-2"></i>
                                    Вернуться на страницу входа
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@main.layout>