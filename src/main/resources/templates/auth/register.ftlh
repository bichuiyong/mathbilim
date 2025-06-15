<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <link rel="stylesheet" href="../static/scss/main.css">
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-12">
                <div class="login-card">
                    <div class="login-header">
                        <div class="icon">
                            <i class="bi bi-person-plus"></i>
                        </div>
                        <h2>Registration</h2>
                        <p>Create your account</p>
                    </div>

                    <#if error?? && errorMessage??>
                        <div class="alert alert-danger alert-custom mb-4" role="alert">
                            <strong>${errorMessage}</strong>
                        </div>
                    </#if>

                    <form action="/auth/register" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName!}" value="${_csrf.token!}"/>
                        </#if>

                        <div class="mb-3">
                            <label for="firstName" class="form-label">First Name</label>
                            <div class="input-wrapper">
                                <@spring.formInput "userDto.name" 'type="text" id="firstName" name="firstName" class="form-control" placeholder="Enter your first name"'/>
                                <i class="bi bi-person input-icon"></i>
                            </div>
                            <@spring.showErrors '<div class="error">' "</div>"/>
                        </div>

                        <div class="mb-3">
                            <label for="lastName" class="form-label">Last Name</label>
                            <div class="input-wrapper">
                                <@spring.formInput "userDto.surname" 'type="text" id="lastName" name="lastName" class="form-control" placeholder="Enter your last name"'/>
                                <i class="bi bi-person input-icon"></i>
                            </div>
                            <@spring.showErrors '<div class="error">' "</div>"/>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <div class="input-wrapper">
                                <@spring.formInput "userDto.email" 'type="email" id="email" name="email" class="form-control" placeholder="example@email.com"'/>
                                <i class="bi bi-envelope input-icon"></i>
                            </div>
                            <@spring.showErrors "<br>" "error text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <div class="input-wrapper">
                                <@spring.formPasswordInput "userDto.password" 'type="password" id="password" name="password" class="form-control" placeholder="Create a strong password"'/>
                                <i class="bi bi-lock input-icon"></i>
                            </div>
                            <@spring.showErrors "<br>" "error text-danger"/>
                        </div>

                        <div class="mb-4">
                            <label for="type" class="form-label">Account Type</label>
                            <div class="account-type-select">
                                <select class="account-type-dropdown" id="type" name="type.id">
                                    <#list types as type>
                                    <option value="${type.id}">
                                        ${type.userTypeTranslations[0].translation}
                                    </option>
                                </#list>
                                </select>
                                <i class="bi bi-chevron-down dropdown-icon"></i>
                            </div>
                            <#if errors?? && errors.hasFieldErrors("type.id")>
                                <div class="error">${errors.getFieldError("type.id").defaultMessage}</div>
                            </#if>
                        </div>
                        <div>
                            <div class="g-recaptcha" data-sitekey="6LcuuF8rAAAAACaF1yxHhWoVxHHgjUF7BuOogWpO"></div>
                        </div>
                        <button class="login-btn" type="submit">
                            <i class="bi bi-person-plus me-2"></i>
                            Register
                        </button>
                    </form>

                    <div class="auth-footer">
                        <p>Already have an account? <a href="/auth/login">Sign In</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#if error?? && errorMessage??>
        <script>
            window.onload = function () {
                const alert = document.getElementById('errorAlert');
                alert.classList.add('visible');
                setTimeout(() => alert.classList.remove('visible'), 5000);
            };
        </script>
    </#if>
</@main.layout>
