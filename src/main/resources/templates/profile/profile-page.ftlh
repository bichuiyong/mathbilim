<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout title="Профиль">
    <head>
        <meta name="_csrf" content="${_csrf.token}">
        <title></title>
    </head>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        >
    </#if>

    <div class="container mt-4" data-user-id="${user.id!}">
        <!-- Профиль пользователя -->
        <div class="row">
            <div class="col-lg-4 mb-4">
                <div class="card profile-card">
                    <div class="card-body text-center">
                        <div class="profile-avatar-container mb-3">
                            <#if user.avatar??>
                                <img src="/api/files/${user.avatar.id}/download" alt="Аватар" class="profile-avatar">
                            <#else>
                                <div class="profile-avatar-placeholder">
                                    <i class="fas fa-user"></i>
                                </div>
                            </#if>
                        </div>

                        <h4 class="mb-1">${user.name!} ${user.surname!}</h4>
                        <p class="text-muted mb-2">${user.email}</p>

                        <#if user.type??>
                            <span class="badge bg-light-blue text-primary mb-3">
                            ${user.type.translations[0].translation}
                        </span>
                        </#if>

                        <div class="d-flex justify-content-center gap-2 mb-3">
                            <#if isAdmin>
                                <span class="badge bg-danger">Администратор</span>
                            <#elseif isModer>
                                <span class="badge bg-warning">Модератор</span>
                            <#else>
                                <span class="badge bg-info">Пользователь</span>
                            </#if>
                        </div>

                        <div class="profile-stats">
                            <div class="row text-center">
                                <div class="col-4">
                                    <div class="stat-value" id="totalContent">0</div>
                                    <div class="stat-label">Контента</div>
                                </div>
                                <div class="col-4">
                                    <div class="stat-value" id="approvedContent">0</div>
                                    <div class="stat-label">Одобрено</div>
                                </div>
                                <div class="col-4">
                                    <div class="stat-value" id="pendingContent">0</div>
                                    <div class="stat-label">На проверке</div>
                                </div>
                            </div>
                        </div>

                        <hr>

                        <a href="/users/${user.id}/edit" class="btn btn-outline-primary btn-sm w-100">
                            <i class="fas fa-edit me-2"></i>Редактировать профиль
                        </a>
                    </div>
                </div>

                <!-- Быстрые действия -->
                <div class="card mt-3">
                    <div class="card-body">
                        <h6 class="card-title mb-3">Создать контент</h6>
                        <div class="d-grid gap-2">
                            <a href="/posts/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-newspaper me-2"></i>Публикация
                            </a>
                            <a href="/blog/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-blog me-2"></i>Блог
                            </a>
                            <a href="/events/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-calendar-alt me-2"></i>Мероприятие
                            </a>
                            <a href="/books/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-book me-2"></i>Книга
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-8">
                <!-- Табы для разных разделов -->
                <div class="card">
                    <div class="card-header">
                        <ul class="nav nav-tabs card-header-tabs" id="profileTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="content-tab" data-bs-toggle="tab" data-bs-target="#content" type="button" role="tab">
                                    <i class="fas fa-file-alt me-2"></i>Мой контент
                                </button>
                            </li>
                            <#if isModer>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="moderation-tab" data-bs-toggle="tab" data-bs-target="#moderation" type="button" role="tab">
                                        <i class="fas fa-tasks me-2"></i>Модерация
                                        <span class="badge bg-danger ms-1" id="moderationCount">0</span>
                                    </button>
                                </li>
                            </#if>
                            <#if isAdmin>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="users-tab" data-bs-toggle="tab" data-bs-target="#users" type="button" role="tab">
                                        <i class="fas fa-users me-2"></i>Пользователи
                                    </button>
                                </li>
                            </#if>
                        </ul>
                    </div>

                    <div class="card-body">
                        <div class="tab-content" id="profileTabsContent">
                            <!-- Вкладка "Мой контент" -->
                            <div class="tab-pane fade show active" id="content" role="tabpanel">
                                <!-- Фильтры -->
                                <div class="content-filters mb-4">
                                    <div class="row g-2">
                                        <div class="col-md-3">
                                            <select class="form-select form-select-sm" id="contentTypeFilter">
                                                <option value="all">Все типы</option>
                                                <option value="posts">Публикации</option>
                                                <option value="blog">Блог</option>
                                                <option value="events">Мероприятия</option>
                                                <option value="books">Книги</option>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <select class="form-select form-select-sm" id="contentStatusFilter">
                                                <option value="all">Все статусы</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="input-group input-group-sm">
                                                <input type="text" class="form-control" id="contentSearch" placeholder="Поиск по контенту...">
                                                <button class="btn btn-outline-secondary" type="button" id="contentSearchBtn">
                                                    <i class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Список контента -->
                                <div id="userContentList" class="content-list">
                                    <div class="text-center text-muted py-5">
                                        <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                        <p>Загрузка контента...</p>
                                    </div>
                                </div>

                                <!-- Пагинация -->
                                <nav id="contentPagination" class="mt-3" style="display: none;">
                                    <ul class="pagination justify-content-center"></ul>
                                </nav>
                            </div>

                            <!-- Вкладка "Модерация" (для модераторов) -->
                            <#if isModer>
                                <div class="tab-pane fade" id="moderation" role="tabpanel">
                                    <!-- Фильтры для модерации -->
                                    <div class="moderation-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-4">
                                                <select class="form-select form-select-sm" id="moderationTypeFilter">
                                                    <option value="all">Все типы</option>
                                                    <option value="posts">Публикации</option>
                                                    <option value="blog">Блог</option>
                                                    <option value="events">Мероприятия</option>
                                                    <option value="books">Книги</option>
                                                </select>
                                            </div>
                                            <div class="col-md-8">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="moderationSearch" placeholder="Поиск...">
                                                    <button class="btn btn-outline-secondary" type="button" id="moderationSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Список контента на модерации -->
                                    <div id="moderationContentList" class="content-list">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                            <p>Загрузка контента для модерации...</p>
                                        </div>
                                    </div>

                                    <!-- Пагинация -->
                                    <nav id="moderationPagination" class="mt-3" style="display: none;">
                                        <ul class="pagination justify-content-center"></ul>
                                    </nav>
                                </div>
                            </#if>

                            <!-- Вкладка "Пользователи" (для администраторов) -->
                            <#if isAdmin>
                                <div class="tab-pane fade" id="users" role="tabpanel">
                                    <!-- Действия с пользователями -->
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h5 class="mb-0">Управление пользователями</h5>
                                        <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#createUserModal">
                                            <i class="fas fa-user-plus me-2"></i>Создать пользователя
                                        </button>
                                    </div>

                                    <!-- Фильтры пользователей -->
                                    <div class="users-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-12">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="usersSearch" placeholder="Поиск по имени или email...">
                                                    <button class="btn btn-outline-secondary" type="button" id="usersSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
<#--                                            <div class="col-md-4">-->
<#--                                                <select class="form-select form-select-sm" id="usersSortBy">-->
<#--                                                    <option value="name">По имени</option>-->
<#--                                                    <option value="email">По email</option>-->
<#--                                                    <option value="createdAt">По дате регистрации</option>-->
<#--                                                </select>-->
<#--                                            </div>-->
                                        </div>
                                    </div>

                                    <!-- Список пользователей -->
                                    <div id="usersContentList" class="users-list">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                            <p>Загрузка пользователей...</p>
                                        </div>
                                    </div>

                                    <!-- Пагинация -->
                                    <nav id="usersPagination" class="mt-3">
                                        <ul class="pagination justify-content-center"></ul>
                                    </nav>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="deleteUserModal" tabindex="-1" aria-labelledby="deleteUserModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteUserModalLabel">Подтвердить удаление</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="deleteUserModalBody">
                    <input type="hidden" value="" id="deleteUserInput">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                    <button type="button" class="btn btn-danger" id="deleteUserBtn">Удалить</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editUserModalLabel">Изменить пользователя</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editUserForm">
                        <input type="hidden" id="editUserId" name="id" value="">
                        <div class="mb-3">
                            <label for="editUserName" class="form-label">First name</label>
                            <input type="text" class="form-control" id="editUserName" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="editUserSurname" class="form-label">Last name</label>
                            <input type="text" class="form-control" id="editUserSurname" name="surname" required>
                        </div>
                        <div class="mb-3">
                            <label for="editUserRole" class="form-label">Роль</label>
                            <select class="form-select" id="editUserRole" name="role">
                                <#list roles as role>
                                    <option value="${role.name}">${role.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="editUserType" class="form-label">Тип аккаунта</label>
                            <div class="account-type-select">
                                <select class="form-select" id="editUserType" name="type">
                                    <option selected disabled value="">Выберите тип аккаунта</option>
                                    <#list types as type>
                                        <option value="${type.id}" data-name="${type.userTypeTranslations[0].translation}">${type.userTypeTranslations[0].translation}</option>
                                    </#list>
                                </select>
                                <i class="bi bi-chevron-down dropdown-icon"></i>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                    <button type="button" class="btn btn-primary" id="editUserBtn">Сохранить изменения</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="createUserModal" tabindex="-1" aria-labelledby="createUserModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createUserModalLabel">Создать нового пользователя</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="createNewUser">
<#--                        <#if _csrf??>-->
<#--                            <input type="hidden" name="${_csrf.parameterName!}" value="${_csrf.token!}"/>-->
<#--                        </#if>-->
                        <div class="mb-3">
                            <label for="name" class="form-label">First name</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="surname" class="form-label">Last name</label>
                            <input type="text" class="form-control" id="surname" name="surname" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="mb-3">
                            <label for="newUserRole" class="form-label">Роль</label>
                            <select class="form-select" id="newUserRole" name="role">
                                <#list roles as role>
                                    <option value="${role.name}">${role.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="type" class="form-label">Тип аккаунта</label>
                            <div class="account-type-select">
                                <select class="form-select" id="type" name="type">
                                    <option selected disabled value="">Выберите тип аккаунта</option>
                                    <#list types as type>
                                        <option value="${type.id}" data-name="${type.userTypeTranslations[0].translation}">${type.userTypeTranslations[0].translation}</option>
                                    </#list>
                                </select>
                                <i class="bi bi-chevron-down dropdown-icon"></i>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                    <button type="button" class="btn btn-primary" id="createUserBtn">Создать</button>
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/admin.js"></script>
</@main.layout>