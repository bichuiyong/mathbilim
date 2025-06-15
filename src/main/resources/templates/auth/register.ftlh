<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout "Регистрация">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-10 col-lg-8">
                <div class="row g-0 shadow-lg rounded overflow-hidden">
                    <!-- Левая панель с информацией -->
                    <div class="col-lg-5 bg-primary text-white p-5 d-flex flex-column justify-content-center">
                        <div class="text-center mb-4">
                            <i class="fas fa-graduation-cap mb-3" style="font-size: 4rem;"></i>
                            <h2 class="fw-bold">Добро пожаловать!</h2>
                        </div>
                        <p class="mb-4 text-center opacity-90">
                            Присоединяйтесь к сообществу MathBilim и откройте для себя мир математического образования
                        </p>
                        <div class="mt-auto">
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle me-3"></i>
                                <span>Доступ к олимпиадным задачам</span>
                            </div>
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle me-3"></i>
                                <span>Интерактивные тесты</span>
                            </div>
                            <div class="d-flex align-items-center">
                                <i class="fas fa-check-circle me-3"></i>
                                <span>Образовательные материалы</span>
                            </div>
                        </div>
                    </div>

                    <!-- Правая панель с формой -->
                    <div class="col-lg-7 bg-white p-5">
                        <h3 class="mb-4 fw-bold text-dark">Создать аккаунт</h3>

                        <#if error??>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </#if>

                        <form action="/auth/register" method="post">
                            <@main.csrf/>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="name" class="form-label small text-muted">Имя</label>
                                    <@spring.bind "userDto.name"/>
                                    <div class="input-group">
                                        <span class="input-group-text border-end-0 bg-white">
                                            <i class="fas fa-user text-muted"></i>
                                        </span>
                                        <input type="text"
                                               class="form-control border-start-0 ${(spring.status.error)?then('is-invalid', '')}"
                                               id="name"
                                               name="${spring.status.expression}"
                                               value="${spring.stringStatusValue}"
                                               placeholder="Введите имя"
                                               >
                                        <#if spring.status.errorMessages?has_content>
                                            <div class="invalid-feedback">
                                                <#list spring.status.errorMessages as error>
                                                    ${error}
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="surname" class="form-label small text-muted">Фамилия</label>
                                    <@spring.bind "userDto.surname"/>
                                    <div class="input-group">
                                        <span class="input-group-text border-end-0 bg-white">
                                            <i class="fas fa-user text-muted"></i>
                                        </span>
                                        <input type="text"
                                               class="form-control border-start-0 ${(spring.status.error)?then('is-invalid', '')}"
                                               id="surname"
                                               name="${spring.status.expression}"
                                               value="${spring.stringStatusValue}"
                                               placeholder="Введите фамилию"
                                               >
                                        <#if spring.status.errorMessages?has_content>
                                            <div class="invalid-feedback">
                                                <#list spring.status.errorMessages as error>
                                                    ${error}
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label small text-muted">Email адрес</label>
                                <@spring.bind "userDto.email"/>
                                <div class="input-group">
                                    <span class="input-group-text border-end-0 bg-white">
                                        <i class="fas fa-envelope text-muted"></i>
                                    </span>
                                    <input type="email"
                                           class="form-control border-start-0 ${(spring.status.error)?then('is-invalid', '')}"
                                           id="email"
                                           name="${spring.status.expression}"
                                           value="${spring.stringStatusValue}"
                                           placeholder="example@email.com"
                                           >
                                    <#if spring.status.errorMessages?has_content>
                                        <div class="invalid-feedback">
                                            <#list spring.status.errorMessages as error>
                                                ${error}
                                            </#list>
                                        </div>
                                    </#if>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label small text-muted">Пароль</label>
                                <@spring.bind "userDto.password"/>
                                <div class="input-group">
                                    <span class="input-group-text border-end-0 bg-white">
                                        <i class="fas fa-lock text-muted"></i>
                                    </span>
                                    <input type="password"
                                           class="form-control border-start-0 border-end-0 ${(spring.status.error)?then('is-invalid', '')}"
                                           id="password"
                                           name="${spring.status.expression}"
                                           placeholder="Минимум 6 символов"
                                           >
                                    <button class="btn btn-outline-secondary border-start-0" type="button" id="togglePassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <#if spring.status.errorMessages?has_content>
                                        <div class="invalid-feedback">
                                            <#list spring.status.errorMessages as error>
                                                ${error}
                                            </#list>
                                        </div>
                                    </#if>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="typeId" class="form-label small text-muted">Тип аккаунта</label>
                                <div class="input-group">
                                    <span class="input-group-text border-end-0 bg-white">
                                        <i class="fas fa-user-tag text-muted"></i>
                                    </span>
                                    <select class="form-control border-start-0"
                                            id="typeId"
                                            name="type.id"
                                            >
                                        <option value="">Выберите тип аккаунта</option>
                                        <#list types as type>
                                            <option value="${type.id}">
                                                ${type.userTypeTranslations[0].translation}
                                            </option>
                                        </#list>
                                    </select>
                                </div>
                                <#if errors?? && errors.hasFieldErrors("type.id")>
                                    <div class="text-danger small mt-1">
                                        ${errors.getFieldError("type.id").defaultMessage}
                                    </div>
                                </#if>
                            </div>

                            <div class="d-grid mb-3">
                                <button type="submit" class="btn btn-primary py-3">
                                    <i class="fas fa-user-plus me-2"></i>
                                    Зарегистрироваться
                                </button>
                            </div>
                        </form>

                        <div class="text-center">
                            <div class="d-flex align-items-center my-4">
                                <hr class="flex-grow-1">
                                <span class="mx-3 text-muted small">или</span>
                                <hr class="flex-grow-1">
                            </div>

                            <a href="/oauth2/authorization/google" class="btn btn-outline-secondary w-100 mb-3">
                                <img src="https://www.google.com/favicon.ico" width="20" height="20" class="me-2">
                                Продолжить с Google
                            </a>

                            <p class="mb-0 small text-muted">
                                Уже есть аккаунт?
                                <a href="/auth/login" class="text-primary fw-bold">Войти</a>
                            </p>
                        </div>
                    </div>
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
</@main.layout>