<#import "../layout.ftlh" as main>

<@main.layout "Новый пароль">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="text-center mb-4">
                    <div class="d-inline-flex align-items-center justify-content-center rounded-circle bg-primary text-white mb-3"
                         style="width: 80px; height: 80px;">
                        <i class="fas fa-lock" style="font-size: 2rem;"></i>
                    </div>
                    <h3 class="fw-bold">Создайте новый пароль</h3>
                </div>

                <div class="bg-white rounded shadow-lg p-4 p-md-5">
                    <#if error??>
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <i class="fas fa-exclamation-circle me-2"></i>
                            <div>${error}</div>
                        </div>
                    <#elseif token??>
                        <p class="text-muted mb-4 text-center">
                            Придумайте надежный пароль для защиты вашего аккаунта
                        </p>

                        <form action="/auth/reset_password" method="post" id="resetPasswordForm">
                            <@main.csrf/>
                            <input type="hidden" name="token" value="${token}">

                            <div class="mb-4">
                                <label for="password" class="form-label small text-muted">Новый пароль</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-lock text-muted"></i>
                                    </span>
                                    <input type="password"
                                           class="form-control border-start-0 border-end-0"
                                           id="password"
                                           name="password"
                                           placeholder="Минимум 6 символов"
                                           required
                                           minlength="6"
                                           autofocus>
                                    <button class="btn btn-outline-secondary border-start-0" type="button" id="togglePassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <div class="password-strength mt-2">
                                    <div class="progress" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" id="passwordStrength"></div>
                                    </div>
                                    <small class="text-muted" id="strengthText">Введите пароль</small>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="confirmPassword" class="form-label small text-muted">Подтвердите пароль</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-lock text-muted"></i>
                                    </span>
                                    <input type="password"
                                           class="form-control border-start-0 border-end-0"
                                           id="confirmPassword"
                                           name="confirmPassword"
                                           placeholder="Повторите пароль"
                                           required
                                           minlength="6">
                                    <button class="btn btn-outline-secondary border-start-0" type="button" id="toggleConfirmPassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <div class="invalid-feedback" id="passwordMatchError">
                                    Пароли не совпадают
                                </div>
                            </div>

                            <!-- Требования к паролю -->
                            <div class="bg-light rounded p-3 mb-4">
                                <h6 class="mb-2">
                                    <i class="fas fa-shield-alt text-primary me-2"></i>
                                    Требования к паролю:
                                </h6>
                                <ul class="mb-0 small text-muted" style="padding-left: 1.5rem;">
                                    <li>Минимум 6 символов</li>
                                    <li>Рекомендуется использовать буквы и цифры</li>
                                    <li>Чувствителен к регистру</li>
                                </ul>
                            </div>

                            <div class="d-grid mb-3">
                                <button type="submit" class="btn btn-primary btn-lg" id="submitBtn">
                                    <i class="fas fa-save me-2"></i>
                                    Сохранить новый пароль
                                </button>
                            </div>
                        </form>
                    </#if>

                    <div class="text-center">
                        <a href="/auth/login" class="text-muted text-decoration-none">
                            <i class="fas fa-arrow-left me-1"></i>
                            Вернуться к входу
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Показать/скрыть пароль
        function togglePasswordVisibility(inputId, buttonId) {
            const input = document.getElementById(inputId);
            const button = document.getElementById(buttonId);
            const icon = button.querySelector('i');

            button.addEventListener('click', function() {
                if (input.type === 'password') {
                    input.type = 'text';
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    input.type = 'password';
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            });
        }

        togglePasswordVisibility('password', 'togglePassword');
        togglePasswordVisibility('confirmPassword', 'toggleConfirmPassword');

        // Проверка силы пароля
        const passwordInput = document.getElementById('password');
        const strengthBar = document.getElementById('passwordStrength');
        const strengthText = document.getElementById('strengthText');

        passwordInput.addEventListener('input', function() {
            const password = this.value;
            let strength = 0;

            if (password.length >= 6) strength += 25;
            if (password.length >= 8) strength += 25;
            if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength += 25;
            if (/[0-9]/.test(password)) strength += 25;

            strengthBar.style.width = strength + '%';
            strengthBar.className = 'progress-bar';

            if (strength <= 25) {
                strengthBar.classList.add('bg-danger');
                strengthText.textContent = 'Слабый пароль';
            } else if (strength <= 50) {
                strengthBar.classList.add('bg-warning');
                strengthText.textContent = 'Средний пароль';
            } else if (strength <= 75) {
                strengthBar.classList.add('bg-info');
                strengthText.textContent = 'Хороший пароль';
            } else {
                strengthBar.classList.add('bg-success');
                strengthText.textContent = 'Надежный пароль';
            }

            checkPasswordMatch();
        });

        // Проверка совпадения паролей
        const confirmPasswordInput = document.getElementById('confirmPassword');
        const passwordMatchError = document.getElementById('passwordMatchError');
        const submitBtn = document.getElementById('submitBtn');

        function checkPasswordMatch() {
            if (confirmPasswordInput.value && passwordInput.value !== confirmPasswordInput.value) {
                confirmPasswordInput.classList.add('is-invalid');
                passwordMatchError.style.display = 'block';
                submitBtn.disabled = true;
            } else {
                confirmPasswordInput.classList.remove('is-invalid');
                passwordMatchError.style.display = 'none';
                submitBtn.disabled = false;
            }
        }

        confirmPasswordInput.addEventListener('input', checkPasswordMatch);

        // Валидация при отправке формы
        document.getElementById('resetPasswordForm').addEventListener('submit', function(e) {
            if (passwordInput.value !== confirmPasswordInput.value) {
                e.preventDefault();
                checkPasswordMatch();
            }
        });
    </script>

    <style>
        .min-vh-50 {
            min-height: 50vh;
        }

        .password-strength {
            display: none;
        }

        #password:focus ~ .password-strength {
            display: block;
        }
    </style>
</@main.layout>