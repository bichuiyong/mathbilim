<#import "../layout.ftlh" as main>
<@main.layout>
    <link rel="stylesheet" href="/static/scss/main.css">
    <link rel="stylesheet" href="/static/scss/verification.css">
    <title>Сброс пароля - MathBilim</title>
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
                                    <i class="fas fa-lock"></i>
                                </div>
                                <h2 class="verification-title primary">Создайте новый пароль</h2>
                            </div>

                            <#if error??>
                                <div class="alert alert-danger">
                                    <i class="fas fa-exclamation-triangle me-2"></i>
                                    ${error}
                                </div>
                            </#if>

                            <p class="verification-text text-center">
                                Введите новый надежный пароль для вашего аккаунта.
                            </p>

                            <form action="/auth/reset_password" method="post">
                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                           value="${(_csrf.token)!'csrf-token'}"/>
                                </#if>

                                <input type="hidden" name="token" value="${token!}"/>

                                <div class="mb-4">
                                    <label for="password" class="form-label">
                                        <i class="fas fa-lock me-2"></i>
                                        Новый пароль
                                    </label>
                                    <div class="position-relative">
                                        <input type="password" name="password" id="password" class="form-control"
                                               placeholder="Введите новый пароль" required autofocus/>
                                        <button type="button" class="btn btn-link position-absolute end-0 top-50 translate-middle-y pe-3"
                                                onclick="togglePassword()">
                                            <i class="fas fa-eye" id="toggleIcon"></i>
                                        </button>
                                    </div>
                                    <div class="form-text">
                                        <i class="fas fa-info-circle me-1"></i>
                                        Пароль должен содержать минимум 8 символов
                                    </div>
                                </div>

                                <div class="d-grid mb-3">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-check me-2"></i>
                                        Изменить пароль
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

    <script>
        function togglePassword() {
            const passwordInput = document.getElementById('password');
            const toggleIcon = document.getElementById('toggleIcon');

            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleIcon.classList.remove('fa-eye');
                toggleIcon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                toggleIcon.classList.remove('fa-eye-slash');
                toggleIcon.classList.add('fa-eye');
            }
        }
    </script>

</@main.layout>