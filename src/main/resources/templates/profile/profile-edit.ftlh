<#import "../layout.ftlh" as main>
<@main.layout ; spring>


    <title>Редактирование профиля</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/main.css">
    <div class="container-fluid px-3 px-lg-4 py-4">
        <div class="profile-banner p-4 p-lg-5">
            <div class="row align-items-center position-relative" style="z-index: 2;">
                <div class="col-12 col-lg text-center text-lg-start">
                    <h1 class="profile-name mb-2">Редактирование профиля</h1>
                    <div class="profile-role">Обновите свою информацию</div>
                </div>
                <div class="col-12 col-lg-auto text-center mt-3 mt-lg-0">
                    <a href="/profile" class="back-button" style="color: white">
                        <i class="fas fa-arrow-left"></i>
                        Назад к профилю
                    </a>
                </div>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-12 col-xl-8">
                <div class="edit-form-card p-4 p-lg-5">
                    <form action="/profile/edit" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <div class="form-section">
                            <h3 class="form-section-title">
                                <i class="fas fa-user"></i>
                                Основная информация
                            </h3>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="name" class="form-label">Имя</label>
                                    <@spring.formInput "userEditDto.name", 'class="form-control form-control-lg"' />
                                    <div class="text-danger">
                                        <@spring.showErrors "userEditDto.name" "<br>" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="surname" class="form-label">Фамилия</label>
                                    <@spring.formInput "userEditDto.surname", 'class="form-control form-control-lg"' />
                                    <div class="text-danger">
                                        <@spring.showErrors "userEditDto.surname" "<br>" />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="d-flex flex-column flex-md-row gap-3 justify-content-end">
                            <button type="button" class="btn btn-outline-secondary  mt-4"
                                    onclick="window.location.href='/profile'">
                                <i class="fas fa-times me-2"></i>
                                Отмена
                            </button>
                            <button type="submit" class="btn btn-primary mt-4">
                                <i class="fas fa-save me-2"></i>
                                Сохранить изменения
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</@main.layout>