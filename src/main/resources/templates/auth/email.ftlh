<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.layout "Регистрация">
    <div class="container my-4">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <div class="text-center mb-4">
                    <h1 class="logo mb-1" style="font-size: 2.5rem;">mathbilim</h1>
                    <p class="text-muted">Завершите регистрацию</p>
                </div>

                <form method="POST" action="/auth/add-email">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <div class="bg-white rounded shadow-sm p-4">
                        <div class="mb-4">
                            <h5 class="mb-3">1. Ваш email</h5>
                            <div class="position-relative mb-3">
                                <@spring.formInput "userEmailDto.email"
                                "class='form-control form-control-lg ps-5'
                             placeholder='example@gmail.com'",
                                'email'/>
                                <i class="fas fa-envelope position-absolute top-50 start-0 translate-middle-y ms-3 text-muted"></i>
                            </div>
                            <@spring.showErrors '<br>', 'text-danger small mt-1'/>
                        </div>

                        <div>
                            <h5 class="mb-3">2. Тип аккаунта</h5>
                            <div class="row g-3 mb-4">
                                <#list userTypes as type>
                                    <div class="col-md-6 d-flex">
                                        <label class="account-type-option w-100" for="type${type.id}">
                                            <input class="form-check-input d-none"
                                                   type="radio"
                                                   name="type"
                                                   id="type${type.id}"
                                                   value="${type.id}"
                                                   required>
                                            <div class="account-type-card h-100 p-3 rounded border d-flex flex-column">
                                                <div class="d-flex align-items-center flex-grow-1">
                                                    <div class="me-3">
                                                        <#if type.userTypeTranslations[0].translation?contains("Ученик")>
                                                            <i class="fas fa-user-graduate text-primary fs-4"></i>
                                                        <#elseif type.userTypeTranslations[0].translation?contains("Учитель")>
                                                            <i class="fas fa-chalkboard-teacher text-primary fs-4"></i>
                                                        <#else>
                                                            <i class="fas fa-user text-primary fs-4"></i>
                                                        </#if>
                                                    </div>
                                                    <div>
                                                        <h6 class="mb-1">${type.userTypeTranslations[0].translation}</h6>
                                                        <p class="text-muted small mb-0">
                                                            <#if type.userTypeTranslations[0].translation?contains("Ученик")>
                                                                Доступ к учебным материалам
                                                            <#elseif type.userTypeTranslations[0].translation?contains("Учитель")>
                                                                Создание тестов и отслеживание прогресса
                                                            <#else>
                                                                Полный доступ к платформе
                                                            </#if>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </label>
                                    </div>
                                </#list>
                            </div>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-check me-2"></i>
                                Завершить регистрацию
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <style>
        .form-control-lg {
            font-size: 1rem;
            padding: 0.75rem 1rem;
            height: 3rem;
        }

        .account-type-option {
            cursor: pointer;
        }

        .account-type-card {
            transition: all 0.2s ease;
            border-color: #dee2e6 !important;
        }

        .account-type-card:hover {
            border-color: #0d6efd !important;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }

        input[type="radio"]:checked + .account-type-card {
            border-color: #0d6efd !important;
            background-color: #f8f9fa;
        }
    </style>
</@main.layout>