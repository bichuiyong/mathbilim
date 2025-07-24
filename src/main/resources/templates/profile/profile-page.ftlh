<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout title="Профиль">
    <head>
        <meta name="_csrf" content="${_csrf.token}">
        <title></title>
    </head>

    <style>




        .collapsed {
            max-height: 150px;
            overflow: hidden;
            position: relative;
        }

        .moderation-image-wrapper {
            margin-right: 16px;
        }

        .moderation-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
            cursor: pointer;
        }


        .moderation-card {
            display: flex;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            overflow: hidden;
            background: white;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }

        .expanded {
            max-height: none;
        }

        .content-card {
            border: 1px solid #dee2e6;
            border-radius: 8px;
            overflow: hidden;
            background: white;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }

        .content-card:hover {
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .content-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
            cursor: pointer;
        }

        .content-info {
            flex: 1;
            padding: 15px;
        }

        .content-title {
            font-size: 16px;
            font-weight: 600;
            color: #212529;
            margin-bottom: 8px;
            cursor: pointer;
        }

        .content-title:hover {
            color: #0d6efd;
        }

        .content-meta {
            color: #6c757d;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .status-badge {
            font-size: 12px;
            padding: 6px 12px;
            border-radius: 20px;
            font-weight: 500;
            display: inline-block;
            margin-right: 10px;
        }

        .status-pending {
            background-color: #fff3cd;
            color: #856404;
        }

        .status-approved {
            background-color: #d1edff;
            color: #0c63e4;
        }

        .status-rejected {
            background-color: #f8d7da;
            color: #721c24;
        }

        .action-buttons {
            display: flex;
            gap: 8px;
            align-items: center;
            padding: 15px;
            border-left: 1px solid #dee2e6;
        }

        .btn-approve {
            background-color: #198754;
            border-color: #198754;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
        }

        .btn-reject {
            background-color: #dc3545;
            border-color: #dc3545;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
        }

        .btn-approve:hover {
            background-color: #157347;
            border-color: #146c43;
        }

        .btn-reject:hover {
            background-color: #bb2d3b;
            border-color: #b02a37;
        }

        .modal-content {
            border-radius: 12px;
        }

        .modal-image {
            width: 100%;
            max-height: 300px;
            object-fit: cover;
            border-radius: 8px;
            margin-bottom: 15px;
        }

        .publication-text {
            max-height: 200px;
            overflow-y: auto;
            line-height: 1.6;
            color: #495057;
        }

        .publication-text.collapsed {
            max-height: 150px;
            overflow: hidden;
            position: relative;
        }

        .publication-text.expanded {
            max-height: none;
        }

        .show-more-btn {
            color: #0d6efd;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
        }

        .show-more-btn:hover {
            text-decoration: underline;
        }

        .content-card.removing {
            animation: fadeOut 0.3s ease-out forwards;
        }

        @keyframes fadeOut {
            from {
                opacity: 1;
                transform: scale(1);
            }
            to {
                opacity: 0;
                transform: scale(0.8);
            }
        }

        .no-content {
            text-align: center;
            padding: 60px 20px;
            color: #6c757d;
        }

        .navbar-nav .nav-link.active {
            color: #0d6efd !important;
            font-weight: 500;
        }

    </style>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        >
    </#if>

    <div class="container mt-4" data-user-id="${user.id!}">
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

                        <a href="/users/${user.id}/edit" class="btn btn-outline-primary btn-sm w-100 mb-2">
                            <i class="fas fa-edit me-2"></i>Редактировать профиль
                        </a>

                        <a href="https://t.me/Math_Bilim_bot?start=${user.id}"
                           target="_blank" class="btn btn-outline-primary btn-sm w-100">
                            📲 Подключить уведомления в Telegram
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
                            <#if isModer || isAdmin>
                                <a href="/olympiad/create" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-book me-2"></i>Олимпиада
                                </a>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <ul class="nav nav-tabs card-header-tabs" id="profileTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="content-tab" data-bs-toggle="tab"
                                        data-bs-target="#content" type="button" role="tab">
                                    <i class="fas fa-file-alt me-2"></i>Мой контент
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="history-tab" data-bs-toggle="tab"
                                        data-bs-target="#history" type="button" role="tab">
                                    <i class="fas fa-history me-2"></i>Моя история
                                </button>
                            </li>
                            <#if isModer>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="moderation-tab" data-bs-toggle="tab"
                                            data-bs-target="#moderation" type="button" role="tab">
                                        <i class="fas fa-tasks me-2"></i>Модерация
                                        <span class="badge bg-danger ms-1" id="moderationCount">0</span>
                                    </button>
                                </li>
                            </#if>
                            <#if isAdmin>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="users-tab" data-bs-toggle="tab" data-bs-target="#users"
                                            type="button" role="tab">
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
                                <div class="moderation-filters mb-4">
                                    <div class="row g-2">
                                        <div class="col-md-4">
                                            <select class="form-select form-select-sm" id="contentFilter">
                                                <option value="all">Все типы</option>
                                                <option value="posts">Публикации</option>
                                                <option value="blog">Блог</option>
                                                <option value="events">Мероприятия</option>
                                                <option value="books">Книги</option>
                                            </select>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="input-group input-group-sm">
                                                <input type="text" class="form-control" id="contentSearch"
                                                       placeholder="Поиск...">
                                                <button class="btn btn-outline-secondary" type="button"
                                                        id="moderationSearchBtn">
                                                    <i class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

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

                            <!-- Вкладка "Моя история" -->
                            <div class="tab-pane fade" id="history" role="tabpanel">
                                <!-- Фильтры для истории -->
                                <div class="history-filters mb-4">
                                    <div class="row g-2">
                                        <div class="col-md-3">
                                            <select class="form-select form-select-sm" id="historyTypeFilter">
                                                <option value="all">Все типы</option>
                                                <option value="post">Публикации</option>
                                                <option value="blog">Блог</option>
                                                <option value="event">Мероприятия</option>
                                                <option value="book">Книги</option>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <select class="form-select form-select-sm" id="historyStatusFilter">
                                                <option value=" ">Все статусы</option>
                                                <option value="APPROVED">Одобрено</option>
                                                <option value="REJECTED">Отклонено</option>
                                                <option value="PENDING_REVIEW">На рассмотрении</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="input-group input-group-sm">
                                                <input type="text" class="form-control" id="historySearch"
                                                       placeholder="Поиск по истории...">
                                                <button class="btn btn-outline-secondary" type="button"
                                                        id="historySearchBtn">
                                                    <i class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="historyContentList" class="content-list">
                                    <div class="text-center text-muted py-5">
                                        <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                        <p>Загрузка истории...</p>
                                    </div>
                                </div>

                                <!-- Пагинация -->
                                <nav id="historyPagination" class="mt-3" style="display: none;">
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
                                                    <option value="post">Публикации</option>
                                                    <option value="blog">Блог</option>
                                                    <option value="event">Мероприятия</option>
                                                    <option value="book">Книги</option>
                                                </select>
                                            </div>
                                            <div class="col-md-8">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="moderationSearch"
                                                           placeholder="Поиск...">
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            id="moderationSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="moderationContentList" class="content-list">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                            <p>Загрузка контента для модерации...</p>
                                        </div>
                                    </div>

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
                                        <button class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                                data-bs-target="#createUserModal">
                                            <i class="fas fa-user-plus me-2"></i>Создать пользователя
                                        </button>
                                    </div>

                                    <!-- Фильтры пользователей -->
                                    <div class="users-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-12">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="usersSearch"
                                                           placeholder="Поиск по имени или email...">
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            id="usersSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
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

                                <!-- Вкладка "Весь контент" (для администраторов) -->
                                <div class="tab-pane fade" id="allcontent" role="tabpanel">
                                    <!-- Фильтры для всего контента -->
                                    <div class="allcontent-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-3">
                                                <select class="form-select form-select-sm" id="allcontentTypeFilter">
                                                    <option value="all">Все типы</option>
                                                    <option value="post">Публикации</option>
                                                    <option value="blog">Блог</option>
                                                    <option value="event">Мероприятия</option>
                                                    <option value="book">Книги</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <select class="form-select form-select-sm" id="allcontentStatusFilter">
                                                    <option value="all">Все статусы</option>
                                                    <option value="APPROVED">Одобрено</option>
                                                    <option value="REJECTED">Отклонено</option>
                                                    <option value="PENDING_REVIEW">На рассмотрении</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="allcontentSearch"
                                                           placeholder="Поиск по контенту...">
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            id="allcontentSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Пагинация -->
                                    <nav id="allcontentPagination" class="mt-3" style="display: none;">
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
                                        <option value="${type.translations[0].typeId}"
                                                data-name="${type.translations[0].translation}">${type.translations[0].translation}</option>
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
                                        <option value="${type.translations[0].typeId}"
                                                data-name="${type.translations[0].translation}">${type.translations[0].translation}</option>
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


    <div class="modal fade" id="contentModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalTitle">Детали контента</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div id="modalContent">
                        <!-- Контент будет загружен здесь -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-reject" id="modalRejectBtn">
                        <i class="fas fa-times"></i> Отклонить
                    </button>
                    <button type="button" class="btn btn-approve" id="modalApproveBtn">
                        <i class="fas fa-check"></i> Одобрить
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/admin.js"></script>
    <script src="/static/js/profile-content.js"></script>
</@main.layout>