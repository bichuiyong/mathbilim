<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout "Восстановление пароля">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="bg-white rounded shadow-lg overflow-hidden">
                    <!-- Верхняя часть с градиентом -->
                    <div class="bg-primary text-white p-4 text-center"
                         style="background: linear-gradient(135deg, #2563eb 0%, #3b82f6 100%);">
                        <i class="fas fa-key mb-3" style="font-size: 3rem;"></i>
                        <h3 class="mb-0"><@spring.message "password.change.title" /></h3>
                    </div>

                    <!-- Основной контент -->
                    <div class="p-4 p-md-5">
                        <#if error??>
                            <div class="alert alert-danger d-flex align-items-center" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <div>${error}</div>
                            </div>
                        </#if>

                        <#if message??>
                            <div class="alert alert-success" role="alert">
                                <div class="d-flex align-items-start">
                                    <i class="fas fa-check-circle me-2 mt-1"></i>
                                    <div>
                                        <strong><@spring.message "password.change.mailSent" /></strong><br>
                                        ${message}
                                    </div>
                                </div>
                            </div>
                        </#if>

                        <p class="text-muted mb-4">
                            <@spring.message "password.change.enterOld" />
                        </p>

                        <form action="/users/change-password" method="post">
                            <@main.csrf/>
                            <div class="mb-4">
                                <label for="oldPassword" class="form-label small text-muted">
                                    <@spring.message "password.change.oldPassword.label" />
                                </label>
                                <div class="input-group input-group-lg">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-envelope text-muted"></i>
                                    </span>
                                    <@spring.formInput
                                    "changePasswordDto.oldPassword",
                                    "class='form-control border-end-0' id='oldPassword' placeholder='${springMacroRequestContext.getMessage('password.change.oldPassword.placeholder')}'",
                                    "password"
                                    />
                                    <button class="btn btn-outline-secondary border-start-0" type="button"
                                            id="toggleOldPassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <@spring.showErrors "<br>" "error text-danger"/>
                                </div>
                            </div>
                            <div id="oldPasswordError" class="text-danger small mt-1" style="display: none;">
                                <@spring.message "password.change.oldPassword.incorrect" />
                            </div>

                            <div class="mb-4">
                                <label for="newPassword" class="form-label small text-muted">
                                    <@spring.message "password.change.newPassword.label" />
                                </label>
                                <div class="input-group input-group-lg">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-envelope text-muted"></i>
                                    </span>
                                    <@spring.formInput
                                    "changePasswordDto.newPassword",
                                    "class='form-control border-end-0' id='newPassword' placeholder='${springMacroRequestContext.getMessage('password.change.newPassword.placeholder')}'",
                                    "password"
                                    />
                                    <button class="btn btn-outline-secondary border-start-0" type="button" id="toggleNewPassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <@spring.showErrors "<br>" "error text-danger"/>
                                </div>
                            </div>

                            <div class="d-grid mb-4">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-paper-plane me-2"></i>
                                    <@spring.message "password.change.submit" />
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        .input-group-lg .form-control,
        .input-group-lg .input-group-text {
            font-size: 1rem;
            padding: 0.75rem 1rem;
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>
    <script>
        function togglePassword(inputId, toggleBtnId) {
            const input = document.getElementById(inputId);
            const toggleBtn = document.getElementById(toggleBtnId);
            const icon = toggleBtn.querySelector('i');

            toggleBtn.addEventListener('click', () => {
                if (input.type === 'password') {
                    input.type = 'text';
                    icon.classList.replace('fa-eye', 'fa-eye-slash');
                } else {
                    input.type = 'password';
                    icon.classList.replace('fa-eye-slash', 'fa-eye');
                }
            });
        }

        togglePassword('oldPassword', 'toggleOldPassword');
        togglePassword('newPassword', 'toggleNewPassword');
    </script>

</@main.layout>