<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout "Выбор типа аккаунта">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-10 col-lg-8">
                <div class="text-center mb-5">
                    <div class="d-inline-block position-relative mb-3">
                        <img src="https://www.google.com/favicon.ico"
                             width="60"
                             height="60"
                             class="rounded-circle shadow"
                             alt="Google">
                        <div class="position-absolute bottom-0 end-0">
                            <div class="rounded-circle bg-success text-white d-flex align-items-center justify-content-center"
                                 style="width: 24px; height: 24px;">
                                <i class="fas fa-check" style="font-size: 0.75rem;"></i>
                            </div>
                        </div>
                    </div>
                    <h2 class="fw-bold mb-2"><@spring.message "userType.welcome"/></h2>
                    <p class="text-muted fs-5"><@spring.message "userType.chose"/></p>
                </div>

                <form action="/auth/select-user-type" method="post" id="registrationForm">
                    <@main.csrf/>

                    <div class="row g-4 mb-4">
                        <#list userTypes as type>
                            <div class="col-md-6">
                                <label class="account-type-option h-100" for="type${type.id}">
                                    <input class="form-check-input d-none"
                                           type="radio"
                                           name="userTypeId"
                                           id="type${type.id}"
                                           value="${type.id}"
                                           required>

                                    <div class="account-type-card h-100 p-4 rounded-3 border position-relative overflow-hidden">
                                        <div class="selected-gradient position-absolute top-0 start-0 w-100 h-100"></div>

                                        <div class="position-relative">
                                            <div class="mb-3">
                                                <#if type.translations[0].translation?contains("Ученик") || type.translations[0].translation?contains("Student")>
                                                    <i class="fas fa-user-graduate text-primary" style="font-size: 2.5rem;"></i>
                                                <#elseif type.translations[0].translation?contains("Учитель") || type.translations[0].translation?contains("Teacher")>
                                                    <i class="fas fa-chalkboard-teacher text-primary" style="font-size: 2.5rem;"></i>
                                                <#elseif type.translations[0].translation?contains("Родитель") || type.translations[0].translation?contains("Parent")>
                                                    <i class="fas fa-user-friends text-primary" style="font-size: 2.5rem;"></i>
                                                <#else>
                                                    <i class="fas fa-user text-primary" style="font-size: 2.5rem;"></i>
                                                </#if>
                                            </div>

                                            <h4 class="mb-2">${type.translations[0].translation}</h4>

                                            <p class="text-muted mb-3">
                                                <#if type.translations[0].translation?contains("Ученик") || type.translations[0].translation?contains("Student")>
                                                    <@spring.message "userType.test"/>
                                                <#elseif type.translations[0].translation?contains("Учитель") || type.translations[0].translation?contains("Teacher")>
                                                    <@spring.message "userType.createTest"/>
                                                <#elseif type.translations[0].translation?contains("Родитель") || type.translations[0].translation?contains("Parent")>
                                                    <@spring.message "userType.perfomance"/>
                                                <#else>
                                                    <@spring.message "userType.function"/>
                                                </#if>
                                            </p>

                                            <div class="selection-indicator position-absolute top-0 end-0 p-3">
                                                <div class="rounded-circle border-2 border-secondary d-flex align-items-center justify-content-center"
                                                     style="width: 24px; height: 24px;">
                                                    <i class="fas fa-check text-white" style="font-size: 0.75rem; display: none;"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </label>
                            </div>
                        </#list>
                    </div>

                    <div class="password-section mb-5">
                        <div class="row g-4">
                            <div class="col-md-6">
                                <div class="password-field-wrapper">
                                    <label for="password" class="form-label fw-semibold text-dark mb-2">
                                        <i class="fas fa-lock me-2 text-primary"></i>Пароль
                                    </label>
                                    <div class="input-group password-input-group">
                                        <span class="input-group-text border-end-0 bg-light">
                                            <i class="fas fa-key text-muted"></i>
                                        </span>
                                        <input type="password"
                                               class="form-control border-start-0 ps-2"
                                               id="password"
                                               name="password"
                                               placeholder="Введите пароль"
                                               required
                                               minlength="6">
                                        <button class="btn btn-outline-secondary border-start-0"
                                                type="button"
                                                id="togglePassword"
                                                tabindex="-1">
                                            <i class="fas fa-eye" id="passwordEye"></i>
                                        </button>
                                    </div>
                                    <div class="password-strength mt-2">
                                        <div class="password-strength-bar">
                                            <div class="strength-bar-fill" id="strengthBarFill"></div>
                                        </div>
                                        <small class="text-muted mt-1 d-block" id="strengthText">Пароль должен содержать 6 символов одну заглавную букву и одну маленькую</small>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="password-field-wrapper">
                                    <label for="confirmPassword" class="form-label fw-semibold text-dark mb-2">
                                        <i class="fas fa-lock me-2 text-primary"></i>Повторите пароль
                                    </label>
                                    <div class="input-group password-input-group">
                                        <span class="input-group-text border-end-0 bg-light">
                                            <i class="fas fa-shield-alt text-muted"></i>
                                        </span>
                                        <input type="password"
                                               class="form-control border-start-0 ps-2"
                                               id="confirmPassword"
                                               name="confirmPassword"
                                               placeholder="Повторите пароль"
                                               required>
                                        <button class="btn btn-outline-secondary border-start-0"
                                                type="button"
                                                id="toggleConfirmPassword"
                                                tabindex="-1">
                                            <i class="fas fa-eye" id="confirmPasswordEye"></i>
                                        </button>
                                    </div>
                                    <div class="password-match mt-2" id="passwordMatch" style="display: none;">
                                        <small class="text-success">
                                            <i class="fas fa-check-circle me-1"></i>Пароли совпадают
                                        </small>
                                    </div>
                                    <div class="password-mismatch mt-2" id="passwordMismatch" style="display: none;">
                                        <small class="text-danger">
                                            <i class="fas fa-times-circle me-1"></i>Пароли не совпадают
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-primary btn-lg px-5" disabled id="continueBtn">
                            <i class="fas fa-arrow-right me-2"></i>
                            <@spring.message "Continue"/>
                        </button>

                        <p class="text-muted mt-3 mb-0">
                            <i class="fas fa-info-circle me-1"></i>
                            <@spring.message "userType.canChange"/>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <style>
        .account-type-option {
            cursor: pointer;
            display: block;
        }

        .account-type-card {
            transition: all 0.3s ease;
            background: white;
            border-color: #e2e8f0 !important;
        }

        .account-type-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
            border-color: #2563eb !important;
        }

        .selected-gradient {
            background: linear-gradient(135deg, rgba(37, 99, 235, 0.05) 0%, rgba(59, 130, 246, 0.05) 100%);
            opacity: 0;
            transition: opacity 0.3s ease;
            z-index: 0;
        }

        input[type="radio"]:checked ~ .account-type-card {
            border-color: #2563eb !important;
            border-width: 2px;
        }

        input[type="radio"]:checked ~ .account-type-card .selected-gradient {
            opacity: 1;
        }

        input[type="radio"]:checked ~ .account-type-card .selection-indicator > div {
            background-color: #2563eb;
            border-color: #2563eb !important;
        }

        input[type="radio"]:checked ~ .account-type-card .selection-indicator i {
            display: block !important;
        }

        .min-vh-50 {
            min-height: 50vh;
        }

        .password-section {
            background: linear-gradient(135deg, rgba(37, 99, 235, 0.03) 0%, rgba(59, 130, 246, 0.03) 100%);
            border-radius: 1rem;
            padding: 2rem;
            border: 1px solid rgba(37, 99, 235, 0.1);
        }

        .password-field-wrapper {
            position: relative;
        }

        .password-input-group {
            border-radius: 0.5rem;
            overflow: hidden;
            transition: all 0.3s ease;
        }

        .password-input-group:focus-within {
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
        }

        .password-input-group .input-group-text {
            border-color: #e2e8f0;
            background: #f8fafc;
            padding: 0.75rem 1rem;
        }

        .password-input-group .form-control {
            border-color: #e2e8f0;
            padding: 0.75rem 1rem;
            font-size: 1rem;
        }

        .password-input-group .form-control:focus {
            box-shadow: none;
            border-color: #2563eb;
        }

        .password-input-group .btn {
            border-color: #e2e8f0;
            background: white;
            color: #6b7280;
            padding: 0.75rem 1rem;
        }

        .password-input-group .btn:hover {
            background: #f8fafc;
            color: #2563eb;
        }

        .password-strength-bar {
            height: 4px;
            background: #e2e8f0;
            border-radius: 2px;
            overflow: hidden;
        }

        .strength-bar-fill {
            height: 100%;
            width: 0%;
            transition: all 0.3s ease;
            border-radius: 2px;
        }

        .strength-weak { background: #ef4444; }
        .strength-fair { background: #f59e0b; }
        .strength-good { background: #3b82f6; }
        .strength-strong { background: #10b981; }

        .form-label {
            color: #374151;
            font-weight: 600;
        }

        .form-label i {
            color: #2563eb;
        }

        .password-match, .password-mismatch {
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-5px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>

    <script>
        const radioButtons = document.querySelectorAll('input[name="userTypeId"]');
        const continueBtn = document.getElementById('continueBtn');
        const passwordField = document.getElementById('password');
        const confirmPasswordField = document.getElementById('confirmPassword');
        const strengthBarFill = document.getElementById('strengthBarFill');
        const strengthText = document.getElementById('strengthText');
        const passwordMatch = document.getElementById('passwordMatch');
        const passwordMismatch = document.getElementById('passwordMismatch');

        // Toggle password visibility
        document.getElementById('togglePassword').addEventListener('click', function() {
            const passwordField = document.getElementById('password');
            const eyeIcon = document.getElementById('passwordEye');

            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                eyeIcon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                passwordField.type = 'password';
                eyeIcon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        });

        document.getElementById('toggleConfirmPassword').addEventListener('click', function() {
            const confirmPasswordField = document.getElementById('confirmPassword');
            const eyeIcon = document.getElementById('confirmPasswordEye');

            if (confirmPasswordField.type === 'password') {
                confirmPasswordField.type = 'text';
                eyeIcon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                confirmPasswordField.type = 'password';
                eyeIcon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        });

        passwordField.addEventListener('input', function() {
            const password = this.value;
            let strength = 0;
            let text = '';

            if (password.length >= 8) strength += 1;
            if (/[A-Z]/.test(password)) strength += 1;
            if (/[a-z]/.test(password)) strength += 1;
            if (/[0-9]/.test(password)) strength += 1;
            if (/[^A-Za-z0-9]/.test(password)) strength += 1;

            switch (strength) {
                case 0:
                case 1:
                    strengthBarFill.className = 'strength-bar-fill strength-weak';
                    strengthBarFill.style.width = '20%';
                    text = 'Слабый пароль';
                    break;
                case 2:
                    strengthBarFill.className = 'strength-bar-fill strength-fair';
                    strengthBarFill.style.width = '40%';
                    text = 'Средний пароль';
                    break;
                case 3:
                case 4:
                    strengthBarFill.className = 'strength-bar-fill strength-good';
                    strengthBarFill.style.width = '70%';
                    text = 'Хороший пароль';
                    break;
                case 5:
                    strengthBarFill.className = 'strength-bar-fill strength-strong';
                    strengthBarFill.style.width = '100%';
                    text = 'Очень сильный пароль';
                    break;
            }

            strengthText.textContent = text;
            checkFormValidity();
        });

        confirmPasswordField.addEventListener('input', function() {
            const password = passwordField.value;
            const confirmPassword = this.value;

            if (confirmPassword === '') {
                passwordMatch.style.display = 'none';
                passwordMismatch.style.display = 'none';
            } else if (password === confirmPassword) {
                passwordMatch.style.display = 'block';
                passwordMismatch.style.display = 'none';
            } else {
                passwordMatch.style.display = 'none';
                passwordMismatch.style.display = 'block';
            }

            checkFormValidity();
        });

        function checkFormValidity() {
            const userTypeSelected = document.querySelector('input[name="userTypeId"]:checked');
            const passwordValid = passwordField.value.length >= 6;
            const passwordsMatch = passwordField.value === confirmPasswordField.value && confirmPasswordField.value !== '';

            continueBtn.disabled = !(userTypeSelected && passwordValid && passwordsMatch);
        }

        radioButtons.forEach(radio => {
            radio.addEventListener('change', function() {
                checkFormValidity();
            });
        });
    </script>
</@main.layout>