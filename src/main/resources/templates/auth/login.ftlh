<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout "Вход">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="text-center mb-4">
                    <h1 class="logo mb-2" style="font-size: 3rem;">mathbilim</h1>
                    <p class="text-muted"><@spring.message "login.sign-in"/></p>
                </div>

                <div class="bg-white rounded shadow-lg p-4 p-md-5">
                    <#if error??>
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <i class="fas fa-exclamation-circle me-2"></i>
                            <div><@spring.message "login.wrong-email"/></div>
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
                                            <@spring.message "login.again"/>
                                        </a>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#if>

                    <form action="/auth/login" method="post">
                        <@main.csrf/>

                        <div class="mb-4">
                            <label for="username" class="form-label small text-muted mb-2">Email <@spring.message "address"/></label>
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
                                <label for="password" class="form-label small text-muted mb-0"><@spring.message "Password"/></label>
                                <a href="/auth/forgot-password" class="text-primary small text-decoration-none"><@spring.message "login.forgot-password"/></a>
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
                                    <@spring.message "login.rememberMe"/>
                                </label>
                            </div>
                        </div>

                        <div class="d-grid mb-4">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-sign-in-alt me-2"></i>
                                <@spring.message "sign-in"/>
                            </button>
                        </div>
                    </form>

                    <div class="position-relative text-center my-4">
                        <hr class="bg-light">
                        <span class="position-absolute top-50 start-50 translate-middle bg-white px-3 text-muted small"><@spring.message "or"/></span>
                    </div>

                    <div class="d-grid mb-4">
                        <a href="/oauth2/authorization/google" class="btn btn-lg btn-light border">
                            <img src="https://www.google.com/favicon.ico" width="20" height="20" class="me-2">
                            <@spring.message "login.google"/>
                        </a>
                    </div>

                    <div class="d-grid mb-4">
                        <a href="/oauth2/authorization/facebook" class="btn btn-lg btn-light border">
                            <img src="data:image/png;base64,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" width="20" height="20" class="me-2">
                            <@spring.message "login.facebook"/>
                        </a>
                    </div>

                    <div class="d-grid mb-4">
                        <div class="btn btn-lg btn-light border">
                            <script async src="https://telegram.org/js/telegram-widget.js?22"
                                    data-telegram-login="Mathbilimbot"
                                    data-size="medium"
                                    data-auth-url="https://usable-selected-bird.ngrok-free.app/auth/telegram/callback"
                                    data-request-access="write">
                            </script>
                        </div>
                    </div>

                    <p class="text-center mb-0 text-muted">
                        <@spring.message "login.haventAcc"/>
                        <a href="/auth/register" class="text-primary fw-bold text-decoration-none"><@spring.message "Register"/></a>
                    </p>
                </div>

                <div class="text-center mt-4">
                    <small class="text-muted">
                        <i class="fas fa-shield-alt me-1"></i>
                        <@spring.message "login.secureNetwork"/>
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