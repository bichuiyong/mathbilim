<#import "../layout.ftlh" as main>

<@main.layout "Вход">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="text-center mb-4">
                    <h1 class="logo mb-2" style="font-size: 3rem;">mathbilim</h1>
                    <p class="text-muted">Войдите в свой аккаунт</p>
                </div>

                <div class="bg-white rounded shadow-lg p-4 p-md-5">
                    <#if error??>
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <i class="fas fa-exclamation-circle me-2"></i>
                            <div>Неверный email или пароль</div>
                        </div>
                    </#if>

                    <#if success??>
                        <div class="alert alert-success d-flex align-items-center" role="alert">
                            <i class="fas fa-check-circle me-2"></i>
                            <div>${success}</div>
                        </div>
                    </#if>

                    <#if emailNotVerified??>
                        <div class="alert alert-warning" role="alert">
                            <div class="d-flex align-items-start">
                                <i class="fas fa-exclamation-triangle me-2 mt-1"></i>
                                <div>
                                    <div>${errorMessage}</div>
                                    <#if userEmail??>
                                        <a href="/auth/resend-verification?email=${userEmail}" class="btn btn-sm btn-warning mt-2">
                                            <i class="fas fa-paper-plane me-1"></i>
                                            Отправить письмо повторно
                                        </a>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#if>

                    <form action="/auth/login" method="post">
                        <@main.csrf/>

                        <div class="mb-4">
                            <label for="username" class="form-label small text-muted mb-2">Email адрес</label>
                            <div class="position-relative">
                                <input type="email"
                                       class="form-control form-control-lg ps-5"
                                       id="username"
                                       name="username"
                                       placeholder="example@email.com"
                                       
                                       autofocus>
                                <i class="fas fa-envelope position-absolute top-50 start-0 translate-middle-y ms-3 text-muted"></i>
                            </div>
                        </div>

                        <div class="mb-4">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <label for="password" class="form-label small text-muted mb-0">Пароль</label>
                                <a href="/auth/forgot-password" class="text-primary small text-decoration-none">Забыли пароль?</a>
                            </div>
                            <div class="position-relative">
                                <input type="password"
                                       class="form-control form-control-lg ps-5 pe-5"
                                       id="password"
                                       name="password"
                                       placeholder="••••••••"
                                       >
                                <i class="fas fa-lock position-absolute top-50 start-0 translate-middle-y ms-3 text-muted"></i>
                                <button class="btn btn-link position-absolute top-50 end-0 translate-middle-y me-2 text-muted p-0"
                                        type="button"
                                        id="togglePassword"
                                        style="border: none; background: none;">
                                    <i class="fas fa-eye"></i>
                                </button>
                            </div>
                        </div>

                        <div class="mb-4">
                            <div class="form-check">
                                <input type="checkbox"
                                       class="form-check-input"
                                       id="remember-me"
                                       name="remember-me">
                                <label class="form-check-label text-muted" for="remember-me">
                                    Запомнить меня
                                </label>
                            </div>
                        </div>

                        <div class="d-grid mb-4">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-sign-in-alt me-2"></i>
                                Войти
                            </button>
                        </div>
                    </form>

                    <div class="position-relative text-center my-4">
                        <hr class="bg-light">
                        <span class="position-absolute top-50 start-50 translate-middle bg-white px-3 text-muted small">или</span>
                    </div>

                    <div class="d-grid mb-4">
                        <a href="/oauth2/authorization/google" class="btn btn-lg btn-light border">
                            <img src="https://www.google.com/favicon.ico" width="20" height="20" class="me-2">
                            Войти через Google
                        </a>
                    </div>

                    <p class="text-center mb-0 text-muted">
                        Нет аккаунта?
                        <a href="/auth/register" class="text-primary fw-bold text-decoration-none">Зарегистрироваться</a>
                    </p>
                </div>

                <div class="text-center mt-4">
                    <small class="text-muted">
                        <i class="fas fa-shield-alt me-1"></i>
                        Защищенное соединение
                    </small>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('togglePassword').addEventListener('click', function() {
            const passwordInput = document.getElementById('password');
            const icon = this.querySelector('i');

            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    </script>

    <style>
        .form-control-lg {
            font-size: 1rem;
            padding: 0.75rem 1rem;
            height: 3rem;
        }

        .form-control:focus {
            box-shadow: 0 0 0 0.2rem rgba(37, 99, 235, 0.25);
        }

        .btn-lg {
            padding: 0.75rem 1.5rem;
            font-size: 1rem;
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>
</@main.layout>