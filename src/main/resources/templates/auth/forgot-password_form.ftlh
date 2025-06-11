<#import "../layout.ftlh" as main>
<@main.layout>
    <link rel="stylesheet" href="/static/scss/main.css">
    <title>Забыли пароль - MathBilim</title>
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
                                    <i class="fas fa-key"></i>
                                </div>
                                <h2 class="verification-title primary">Забыли пароль?</h2>
                            </div>

                            <#if error??>
                                <div class="alert alert-danger">
                                    <i class="fas fa-exclamation-triangle me-2"></i>
                                    ${error}
                                </div>
                            </#if>

                            <#if message??>
                                <div class="alert alert-info">
                                    <i class="fas fa-info-circle me-2"></i>
                                    ${message}
                                </div>
                            </#if>

                            <p class="verification-text text-center">
                                Введите ваш email адрес и мы отправим вам ссылку для восстановления пароля.
                            </p>

                            <form action="/auth/forgot-password" method="post">
                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                           value="${(_csrf.token)!'csrf-token'}"/>
                                </#if>

                                <div class="mb-4">
                                    <label for="email" class="form-label">
                                        <i class="fas fa-envelope me-2"></i>
                                        Email адрес
                                    </label>
                                    <input type="email" name="email" id="email" class="form-control"
                                           placeholder="Введите ваш email" required autofocus/>
                                </div>

                                <div class="d-grid mb-3">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-paper-plane me-2"></i>
                                        Отправить ссылку для восстановления
                                    </button>
                                </div>
                            </form>

                            <div class="text-center mt-4">
                                <a href="/auth/login" class="text-decoration-none">
                                    <i class="fas fa-arrow-left me-2"></i>
                                    Вернуться к входу
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@main.layout>