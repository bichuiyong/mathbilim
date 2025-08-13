<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout "Регистрация">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-10 col-lg-8">
                <div class="row g-0 shadow-lg rounded overflow-hidden">

                    <div class="col-lg-5 bg-primary text-white p-5 d-flex flex-column justify-content-center">
                        <div class="text-center mb-4">
                            <i class="fas fa-graduation-cap mb-3" style="font-size: 4rem;"></i>
                            <h2 class="fw-bold"><@spring.message "register.welcome"/></h2>
                        </div>
                        <p class="mb-4 text-center opacity-90">
                            <@spring.message "register.desc"/>
                        </p>
                        <div class="mt-auto">
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle me-3"></i>
                                <span><@spring.message "register.olymp"/></span>
                            </div>
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle me-3"></i>
                                <span><@spring.message "register.tests"/></span>
                            </div>
                            <div class="d-flex align-items-center">
                                <i class="fas fa-check-circle me-3"></i>
                                <span><@spring.message "register.materials"/></span>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-7 bg-white p-5">
                        <h3 class="mb-4 fw-bold text-dark"><@spring.message "register.create"/></h3>

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
                                    <label for="name" class="form-label small text-muted">
                                        <i class="fas fa-user text-muted me-1"></i>
                                        <@spring.message "Name"/> *
                                    </label>
                                    <@spring.formInput "userDto.name", "class='form-control' id='name' placeholder='Введите имя'", "text"/>
                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="surname" class="form-label small text-muted">
                                        <i class="fas fa-user text-muted me-1"></i>
                                        <@spring.message "Surname"/> *
                                    </label>
                                    <@spring.formInput "userDto.surname", "class='form-control' id='surname' placeholder='Введите фамилию'", "text"/>
                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label small text-muted">
                                    <i class="fas fa-envelope text-muted me-1"></i>
                                    Email <@spring.message "address"/> *
                                </label>
                                <@spring.formInput "userDto.email", "class='form-control' id='email' placeholder='example@email.com'", "email"/>
                                <@spring.showErrors "<br>", "text-danger small mt-1"/>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label small text-muted">
                                    <i class="fas fa-lock text-muted me-1"></i>
                                    <@spring.message "Password"/> *
                                </label>
                                <div class="input-group">
                                    <@spring.formInput "userDto.password", "class='form-control border-end-0' id='password' placeholder='Минимум 6 символов'", "password"/>
                                    <button class="btn btn-outline-secondary border-start-0" type="button" id="togglePassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <@spring.showErrors "<br>", "text-danger small mt-1"/>
                            </div>

                            <div class="mb-4">
                                <label for="typeId" class="form-label small text-muted">
                                    <i class="fas fa-user-tag text-muted me-1"></i>
                                    <@spring.message "account.type"/> *
                                </label>

                                <#assign typeMap = {} />
                                <#list types as type>
                                    <#assign typeMap = typeMap + {type.translations[0].typeId: type.translations[0].translation}>
                                </#list>


                                <@spring.formSingleSelect
                                "userDto.type.id",
                                typeMap,
                                'class="form-control" id="typeId"'
                                />
                                <@spring.showErrors '<br>', 'text-danger small mt-1'/>
                            </div>

                            <div class="d-grid mb-3">
                                <button type="submit" class="btn btn-primary py-3">
                                    <i class="fas fa-user-plus me-2"></i>
                                    <@spring.message "Register"/>
                                </button>
                            </div>
                        </form>

                        <div class="text-center">
                            <div class="d-flex align-items-center my-4">
                                <hr class="flex-grow-1">
                                <span class="mx-3 text-muted small"><@spring.message "or"/></span>
                                <hr class="flex-grow-1">
                            </div>

                            <a href="/oauth2/authorization/google" class="btn btn-outline-secondary w-100 mb-3">
                                <img src="https://www.google.com/favicon.ico" width="20" height="20" class="me-2">
                                <@spring.message "register.google"/>
                            </a>

                            <p class="mb-0 small text-muted">
                                <@spring.message "register.acc-exist"/>
                                <a href="/auth/login" class="text-primary fw-bold"><@spring.message "sign-in"/></a>
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