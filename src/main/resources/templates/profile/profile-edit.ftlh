<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout title="Редактирование профиля">

<#-- Определяем, это администратор редактирует другого пользователя или пользователь редактирует себя -->
    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ROLE_ADMIN")
        currentUserEmail = SPRING_SECURITY_CONTEXT.authentication.principal.username
        isEditingSelf = !editingUser?? || (editingUser.email == currentUserEmail)
        >
    </#if>

    <div class="container mt-4"
         data-profile-edit
         data-is-editing-self="${isEditingSelf?c}"
         data-is-admin="${isAdmin?c}"
         data-user-id="${(editingUser.id)!user.id}"
         data-current-type-id="${(userEditDto.typeId)!(editingUser.type.id)!0}">
        <div class="row">
            <div class="col-12">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/profile">Профиль</a></li>
                        <li class="breadcrumb-item active">
                            ${isEditingSelf?string('Редактирование профиля', 'Редактирование пользователя')}
                        </li>
                    </ol>
                </nav>

                <h3 class="mb-4">
                    ${isEditingSelf?string('Редактирование профиля', 'Редактирование пользователя')}
                </h3>

                <div class="admin-form">
                    <#-- Информация о пользователе (только для админов, редактирующих других) -->
                    <#if !isEditingSelf && editingUser??>
                        <div class="alert alert-info mb-4">
                            <div class="d-flex align-items-center">
                                <#if editingUser.avatar??>
                                    <img src="/api/files/${editingUser.avatar.id}" alt="Аватар"
                                         class="rounded-circle me-3" width="50" height="50">
                                <#else>
                                    <div class="rounded-circle bg-light d-flex align-items-center justify-content-center me-3"
                                         style="width: 50px; height: 50px;">
                                        <i class="fas fa-user text-muted"></i>
                                    </div>
                                </#if>
                                <div>
                                    <h6 class="mb-0">${editingUser.name!} ${editingUser.surname!}</h6>
                                    <small class="text-muted">${editingUser.email}</small>
                                </div>
                            </div>
                        </div>
                    </#if>

                    <form action="${isEditingSelf?string('/profile/edit', '/admin/users/edit/' + editingUser.id)}"
                          method="post" id="editProfileForm">
                        <@main.csrf/>

                        <div class="form-section">
                            <h5>Основная информация</h5>

                            <#-- Аватар (только для редактирования себя) -->
                            <#if isEditingSelf>
                                <div class="text-center mb-4">
                                    <div class="profile-avatar-container d-inline-block">
                                        <#if userEditDto.avatar??>
                                            <img src="/api/files/${userEditDto.avatar.id}" alt="Аватар"
                                                 class="profile-avatar">
                                        <#else>
                                            <div class="profile-avatar-placeholder">
                                                <i class="fas fa-user"></i>
                                            </div>
                                        </#if>
                                        <button type="button" class="btn btn-sm btn-primary profile-avatar-edit"
                                                data-bs-toggle="modal" data-bs-target="#avatarModal">
                                            <i class="fas fa-camera"></i>
                                        </button>
                                    </div>
                                </div>
                            </#if>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="name" class="form-label">
                                        <i class="fas fa-user text-muted me-1"></i>
                                        Имя *
                                    </label>
                                    <#if isEditingSelf>
                                        <@spring.formInput "userEditDto.name", "class='form-control' id='name' placeholder='Введите имя'", "text"/>
                                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                    <#else>
                                        <@spring.formInput "userEditByAdminDto.name", "class='form-control' id='name' placeholder='Введите имя'", "text"/>
                                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                    </#if>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="surname" class="form-label">
                                        <i class="fas fa-user text-muted me-1"></i>
                                        Фамилия
                                    </label>
                                    <#if isEditingSelf>
                                        <@spring.formInput "userEditDto.surname", "class='form-control' id='surname' placeholder='Введите фамилию'", "text"/>
                                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                    <#else>
                                        <@spring.formInput "userEditByAdminDto.surname", "class='form-control' id='surname' placeholder='Введите фамилию'", "text"/>
                                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                    </#if>
                                </div>
                            </div>
                        </div>

                        <div class="form-section">
                            <h5><#if isAdmin && !isEditingSelf>
                                    Роль и тип аккаунта
                                <#else>
                                    Тип аккаунта
                                </#if>
                            </h5>

                            <#-- Роль (только для админов, редактирующих других) -->
                            <#if isAdmin && !isEditingSelf>
                                <div class="mb-3">
                                    <label for="roleId" class="form-label">
                                        <i class="fas fa-shield-alt text-muted me-1"></i>
                                        Роль *
                                    </label>
                                    <@spring.formSingleSelect
                                    "userEditByAdminDto.role.id",
                                    {"1": "Пользователь", "2": "Модератор", "3": "Администратор"},
                                    'class="form-select" id="roleId"'
                                    />
                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                </div>
                            </#if>

                            <#-- Тип аккаунта -->
                            <div class="mb-3" id="userTypeSection"
                                 <#if isAdmin && !isEditingSelf && editingUser.role?? && (editingUser.role.id == 2 || editingUser.role.id == 3)>style="display: none;"</#if>>
                                <label for="typeId" class="form-label">
                                    <i class="fas fa-user-tag text-muted me-1"></i>
                                    Тип аккаунта *
                                </label>

                                <#if types??>
                                    <#assign typeMap = {}>
                                    <#list types as type>
                                        <#assign typeMap = typeMap + {type.id?string: type.userTypeTranslations[0].translation}>
                                    </#list>

                                    <@spring.formSingleSelect
                                    "${isEditingSelf?string('userEditDto.typeId', 'userEditByAdminDto.typeId')}",
                                    typeMap,
                                    'class="form-select" id="typeId"'
                                    />
                                <#else>
                                    <select class="form-select" id="typeId" name="typeId">
                                        <option value="">Загрузка типов...</option>
                                    </select>
                                </#if>
                                <@spring.showErrors '<br>', 'text-danger small mt-1'/>
                            </div>
                        </div>

                        <#-- Статус аккаунта (только для админов, редактирующих других) -->
                        <#if isAdmin && !isEditingSelf && editingUser??>
                            <div class="form-section">
                                <h5>Статус аккаунта</h5>

                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <div>
                                        <h6 class="mb-1">Состояние аккаунта</h6>
                                        <p class="text-muted mb-0">
                                            Текущий статус:
                                            <#if editingUser.enabled>
                                                <span class="badge bg-success">Активен</span>
                                            <#else>
                                                <span class="badge bg-danger">Заблокирован</span>
                                            </#if>
                                        </p>
                                    </div>
                                    <button type="button"
                                            class="btn btn-outline-${editingUser.enabled?string('warning', 'success')} btn-sm"
                                            id="toggleStatusBtn">
                                        <#if editingUser.enabled>
                                            <i class="fas fa-ban me-2"></i>Заблокировать
                                        <#else>
                                            <i class="fas fa-check me-2"></i>Разблокировать
                                        </#if>
                                    </button>
                                </div>

                                <div class="mb-3">
                                    <h6 class="mb-1">Email верификация</h6>
                                    <p class="text-muted mb-0">
                                        Email ${editingUser.isEmailVerified?string('подтвержден', 'не подтвержден')}
                                        <#if editingUser.isEmailVerified>
                                            <i class="fas fa-check-circle text-success ms-1"></i>
                                        <#else>
                                            <i class="fas fa-times-circle text-danger ms-1"></i>
                                        </#if>
                                    </p>
                                </div>

                                <div>
                                    <h6 class="mb-1">Дата регистрации</h6>
                                    <p class="text-muted mb-0">
                                        <#if editingUser.createdAt??>
                                            ${editingUser.createdAt}
                                        <#else>
                                            Не указана
                                        </#if>
                                    </p>
                                </div>
                            </div>
                        </#if>

                        <div class="d-flex justify-content-between">
                            <a href="${isEditingSelf?string('/profile', '/profile#users')}" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-2"></i>Назад
                            </a>
                            <div>
                                <#if isAdmin && !isEditingSelf>
                                    <button type="button" class="btn btn-outline-danger me-2" id="deleteUserBtn">
                                        <i class="fas fa-trash me-2"></i>Удалить
                                    </button>
                                </#if>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-2"></i>Сохранить изменения
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

<#-- Модальное окно для загрузки аватара (только для редактирования себя) -->
    <#if isEditingSelf>
        <div class="modal fade" id="avatarModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Загрузить аватар</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <form id="avatarForm" enctype="multipart/form-data">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="avatarFile" class="form-label">Выберите изображение</label>
                                <input type="file" class="form-control" id="avatarFile" accept="image/*" required>
                                <div class="form-text">Поддерживаются форматы: JPG, PNG, GIF. Максимальный размер: 5MB
                                </div>
                            </div>
                            <div id="avatarPreview" class="text-center" style="display: none;">
                                <img src="" alt="Preview" class="img-fluid rounded" style="max-height: 200px;">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-upload me-2"></i>Загрузить
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </#if>

    <script src="/static/js/profile-edit.js"></script>

</@main.layout>