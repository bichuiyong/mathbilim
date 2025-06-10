<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../static/scss/main.css">
    <div class="container-fluid px-3 px-md-4 py-4">
        <div class="profile-banner p-4 p-md-5 mb-4">
            <div class="row align-items-center position-relative" style="z-index: 2;">
                <div class="col-12 col-md-auto text-center text-md-start mb-3 mb-md-0">
                    <div class="avatar mx-auto mx-md-0">
                        ${user.name?substring(0, 1)}${user.surname?substring(0, 1)}
                        <div class="online-indicator"></div>
                    </div>
                </div>
                <div class="col-12 col-md text-center text-md-start">
                    <h1 class="profile-name mb-2">${user.name} ${user.surname}</h1>
                    <div class="profile-role mb-3">
                        🎯
                        <#if user.type?? && user.type.name()?has_content>
                            ${user.type.name()}
                        <#else>
                            Администратор / Модератор
                        </#if>
                    </div>
                    <div class="row justify-content-center justify-content-md-start">
                        <div class="col-4 col-md-auto text-center px-2 px-md-3">
                            <span class="metric-value d-block">24</span>
                            <div class="metric-label">Публикации</div>
                        </div>
                        <div class="col-4 col-md-auto text-center px-2 px-md-3">
                            <span class="metric-value d-block">12</span>
                            <div class="metric-label">Мероприятия</div>
                        </div>
                        <div class="col-4 col-md-auto text-center px-2 px-md-3">
                            <span class="metric-value d-block">8</span>
                            <div class="metric-label">Книги</div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-auto text-center mt-3 mt-md-0">
                    <a href="/profile/edit" class="btn edit-profile-btn px-4 py-2">
                        <i class="fas fa-edit me-2"></i>
                        Редактировать
                    </a>
                </div>
            </div>
        </div>

        <div class="row g-4">
            <div class="col-12 col-lg-3 order-2 order-lg-1">
                <div class="sidebar-card p-4">
                    <h3 class="sidebar-title mb-3">
                        <i class="fas fa-chart-bar me-2"></i>
                        Статистика
                    </h3>
                    <div class="d-grid gap-3">
                        <div class="stat-item p-3 d-flex justify-content-between align-items-center">
                            <span class="stat-name">На модерации</span>
                            <span class="stat-value">3</span>
                        </div>
                        <div class="stat-item p-3 d-flex justify-content-between align-items-center">
                            <span class="stat-name">Одобрено</span>
                            <span class="stat-value">18</span>
                        </div>
                        <div class="stat-item p-3 d-flex justify-content-between align-items-center">
                            <span class="stat-name">Всего просмотров</span>
                            <span class="stat-value">2.4k</span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 col-lg-9 order-1 order-lg-2">
                <div class="bg-white rounded-4 p-4 shadow">
                    <div class="text-center mb-4">
                        <h2 class="section-title">
                            <i class="fas fa-book me-3"></i>
                            Мои публикации
                        </h2>
                        <div class="dropdown d-inline-block">
                            <button class="btn add-button px-4 py-2 dropdown-toggle" type="button" id="addDropdown"
                                    data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-plus me-2"></i>
                                Добавить
                            </button>
                            <ul class="dropdown-menu shadow-lg border-0" aria-labelledby="addDropdown">
                                <li><a class="dropdown-item py-3" href="#">
                                        <i class="fas fa-calendar me-3"></i>
                                        Создать мероприятие
                                    </a></li>
                                <li><a class="dropdown-item py-3" href="#">
                                        <i class="fas fa-book me-3"></i>
                                        Добавить книгу
                                    </a></li>
                                <li><a class="dropdown-item py-3" href="#">
                                        <i class="fas fa-pen me-3"></i>
                                        Написать блог
                                    </a></li>
                            </ul>
                        </div>
                    </div>

                    <div class="bg-light p-4 rounded-3 mb-4">
                        <div class="row g-3">
                            <div class="col-12 col-md-4">
                                <label class="form-label fw-semibold text-muted">
                                    <i class="fas fa-folder me-2"></i>
                                    Тип публикации
                                </label>
                                <select class="form-select" id="typeFilter">
                                    <option value="all">Все типы</option>
                                    <option value="event">Мероприятия</option>
                                    <option value="book">Книги</option>
                                    <option value="blog">Блоги</option>
                                </select>
                            </div>
                            <div class="col-12 col-md-4">
                                <label class="form-label fw-semibold text-muted">
                                    <i class="fas fa-chart-line me-2"></i>
                                    Статус
                                </label>
                                <select class="form-select" id="statusFilter">
                                    <option value="all">Все статусы</option>
                                    <option value="pending">В проверке</option>
                                    <option value="approved">Одобрено</option>
                                    <option value="rejected">Отклонено</option>
                                </select>
                            </div>
                            <div class="col-12 col-md-4">
                                <label class="form-label fw-semibold text-muted">
                                    <i class="fas fa-sort me-2"></i>
                                    Сортировка
                                </label>
                                <select class="form-select" id="sortFilter">
                                    <option value="date-desc">Дата (новые)</option>
                                    <option value="date-asc">Дата (старые)</option>
                                    <option value="title">По названию</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row g-4" id="publicationsGrid">
                        <div class="col-12 col-lg-6">
                            <div class="publication-card" data-type="event" data-status="approved"
                                 data-date="2024-06-01">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div class="flex-grow-1">
                                        <div class="publication-title mb-2">🚀 Конференция по цифровым технологиям</div>
                                        <div class="publication-meta d-flex flex-wrap gap-3 mb-0">
                                            <span><i class="fas fa-calendar me-1"></i> 15 июня 2024</span>
                                            <span><i class="fas fa-users me-1"></i> 120 участников</span>
                                            <span><i class="fas fa-clock me-1"></i> 09:00-18:00</span>
                                        </div>
                                    </div>
                                    <div class="publication-type ms-3">Мероприятие</div>
                                </div>
                                <div class="publication-description mb-3">
                                    Международная конференция о современных тенденциях в области цифровых технологий и
                                    их
                                    влиянии на бизнес. Выступления экспертов, воркшопы и нетворкинг.
                                </div>
                                <div class="d-flex justify-content-between align-items-center pt-3 border-top">
                                    <div class="status-badge status-approved">
                                        <i class="fas fa-check"></i>
                                        Одобрено
                                    </div>
                                    <div class="btn-group">
                                        <button class="btn action-btn btn-edit btn-sm">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn action-btn btn-delete btn-sm">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 col-lg-6">
                            <div class="publication-card" data-type="book" data-status="pending" data-date="2024-05-15">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div class="flex-grow-1">
                                        <div class="publication-title mb-2">📘 Основы веб-разработки</div>
                                        <div class="publication-meta d-flex flex-wrap gap-3 mb-0">
                                            <span><i class="fas fa-file-alt me-1"></i> 320 страниц</span>
                                            <span><i class="fas fa-star me-1"></i> 4.5/5</span>
                                            <span><i class="fas fa-comments me-1"></i> 45 отзывов</span>
                                        </div>
                                    </div>
                                    <div class="publication-type ms-3">Книга</div>
                                </div>
                                <div class="publication-description mb-3">
                                    Практическое руководство по современной веб-разработке с примерами и упражнениями
                                    для
                                    начинающих. Включает HTML, CSS, JavaScript и основы фреймворков.
                                </div>
                                <div class="d-flex justify-content-between align-items-center pt-3 border-top">
                                    <div class="status-badge status-pending">
                                        <i class="fas fa-clock"></i>
                                        В проверке
                                    </div>
                                    <div class="btn-group">
                                        <button class="btn action-btn btn-edit btn-sm">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn action-btn btn-delete btn-sm">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 col-lg-6">
                            <div class="publication-card" data-type="blog" data-status="approved"
                                 data-date="2024-06-05">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div class="flex-grow-1">
                                        <div class="publication-title mb-2">✨ Тренды UX дизайна в 2024</div>
                                        <div class="publication-meta d-flex flex-wrap gap-3 mb-0">
                                            <span><i class="fas fa-book-open me-1"></i> 5 мин чтения</span>
                                            <span><i class="fas fa-eye me-1"></i> 1,234 просмотра</span>
                                            <span><i class="fas fa-comment me-1"></i> 28 комментариев</span>
                                        </div>
                                    </div>
                                    <div class="publication-type ms-3">Блог</div>
                                </div>
                                <div class="publication-description mb-3">
                                    Подробный обзор актуальных трендов в области пользовательского опыта и интерфейсного
                                    дизайна. Анализ лучших практик и будущих направлений развития.
                                </div>
                                <div class="d-flex justify-content-between align-items-center pt-3 border-top">
                                    <div class="status-badge status-approved">
                                        <i class="fas fa-check"></i>
                                        Одобрено
                                    </div>
                                    <div class="btn-group">
                                        <button class="btn action-btn btn-edit btn-sm">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn action-btn btn-delete btn-sm">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 col-lg-6">
                            <div class="publication-card" data-type="event" data-status="rejected"
                                 data-date="2024-04-20">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div class="flex-grow-1">
                                        <div class="publication-title mb-2">📸 Мастер-класс по фотографии</div>
                                        <div class="publication-meta d-flex flex-wrap gap-3 mb-0">
                                            <span><i class="fas fa-calendar me-1"></i> 25 мая 2024</span>
                                            <span><i class="fas fa-users me-1"></i> 50 участников</span>
                                            <span><i class="fas fa-clock me-1"></i> 14:00-17:00</span>
                                        </div>
                                    </div>
                                    <div class="publication-type ms-3">Мероприятие</div>
                                </div>
                                <div class="publication-description mb-3">
                                    Практический мастер-класс по основам художественной фотографии для начинающих
                                    фотографов. Работа с композицией, светом и постобработкой.
                                </div>
                                <div class="d-flex justify-content-between align-items-center pt-3 border-top">
                                    <div class="status-badge status-rejected">
                                        <i class="fas fa-times"></i>
                                        Отклонено
                                    </div>
                                    <div class="btn-group">
                                        <button class="btn action-btn btn-edit btn-sm">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn action-btn btn-delete btn-sm">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</@main.layout>
