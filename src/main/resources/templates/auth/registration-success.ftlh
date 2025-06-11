<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <link rel="stylesheet" href="../static/scss/main.css">
    <title>Регистрация успешна - MathBilim</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

    <div class="verification-page">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card verification-card">
                        <div class="card-body text-center">
                            <div class="verification-icon success">
                                <i class="fas fa-envelope"></i>
                            </div>

                            <h2 class="verification-title success">Регистрация успешна!</h2>

                            <p class="verification-text">
                                Мы отправили письмо с подтверждением на вашу электронную почту.
                                Пожалуйста, проверьте почту и перейдите по ссылке для активации аккаунта.
                            </p>

                            <div class="alert alert-info">
                                <i class="fas fa-info-circle me-2"></i>
                                <strong>Важно:</strong> Проверьте папку "Спам" если письмо не пришло в основную папку.
                            </div>

                            <div class="d-grid gap-3 mt-4">
                                <a href="/auth/login" class="btn btn-primary">
                                    <i class="fas fa-sign-in-alt me-2"></i>
                                    На страницу входа
                                </a>
                                <a href="/auth/resend-verification" class="btn btn-outline-secondary">
                                    <i class="fas fa-redo me-2"></i>
                                    Отправить письмо повторно
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@main.layout>