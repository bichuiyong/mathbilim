<#import "../layout.ftlh" as main>
    <#import "/spring.ftl" as spring>
<@main.layout>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <#if successMessage??>
        <div id="infoAlert" class="info-alert">
            <span class="info-icon">ℹ️</span>
            <span class="info-text">${successMessage}</span>
        </div>
    </#if>


    <style>

        .info-alert {
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #e7f3fe;
            color: #31708f;
            border: 1px solid #bce8f1;
            padding: 12px 16px;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.2);
            display: flex;
            align-items: center;
            gap: 8px;
            font-family: Arial, sans-serif;
            font-size: 14px;
            z-index: 1050;
            opacity: 0;
            transform: translateY(-20px);
            animation: slideIn 0.5s forwards, fadeOut 0.5s 5s forwards;
        }

        .info-icon {
            font-weight: bold;
        }

        @keyframes slideIn {
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes fadeOut {
            to {
                opacity: 0;
                transform: translateY(-20px);
            }
        }
        .page-header {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            border-bottom: 1px solid #dee2e6;
        }

        .page-title {
            color: #495057;
            font-weight: 700;
            margin: 0;
        }

        .page-subtitle {
            color: #6c757d;
            margin: 0;
        }

        .event-card {
            position: relative;
            background-size: cover;
            background-position: center;
            border-radius: 15px;
            overflow: hidden;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            min-height: 280px;
            color: white;
        }

        .event-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
        }

        .event-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(135deg, rgba(0, 0, 0, 0.7) 0%, rgba(0, 0, 0, 0.3) 100%);
            z-index: 1;
        }

        .event-content {
            position: relative;
            z-index: 2;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
        }

        .event-date {
            position: absolute;
            top: 15px;
            left: 15px;
            background: rgba(255, 255, 255, 0.9);
            color: #333;
            font-weight: 600;
            z-index: 3;
        }

        .event-type-badge {
            position: absolute;
            top: 15px;
            right: 15px;
            z-index: 3;
            font-weight: 500;
        }

        .badge-олимпиада {
            background: #dc3545;
        }

        .badge-семинар {
            background: #28a745;
        }

        .badge-лекция {
            background: #007bff;
        }

        .badge-онлайн {
            background: #17a2b8;
        }

        .badge-офлайн {
            background: #6f42c1;
        }

        .featured-event {
            min-height: 400px;
            margin-bottom: 2rem;
        }

        .event-title {
            color: white;
            font-weight: 600;
        }

        .btn-add-event {
            background: linear-gradient(135deg, #007bff, #0056b3);
            border: none;
            transition: all 0.3s ease;
        }

        .btn-add-event:hover {
            background: linear-gradient(135deg, #0056b3, #004085);
            transform: translateY(-2px);
        }

        .main-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 15px;
        }

        .filters-card {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            padding: 24px;
            margin-bottom: 30px;
        }
    </style>

    <!-- Компактный заголовок -->
    <div class="page-header py-4">
        <div class="main-content">
            <div class="row align-items-center">
                <div class="col-md-8">
                    <h1 class="page-title h3"><@spring.message "events.list.title"/></h1>
                    <p class="page-subtitle"><@spring.message "events.list.subtitle"/></p>
                </div>
                <div class="col-md-4 text-md-end">
                    <a href="/events/create" class="btn btn-add-event text-white rounded-pill px-4 py-2">
                        <i class="fas fa-plus me-2"></i><@spring.message "events.list.addEvent"/>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="main-content py-4">
        <!-- Фильтры в карточке -->
        <div class="filters-card">
            <div class="row align-items-end">
                <div class="col-md-3 mb-3">
                    <label for="typeFilter" class="form-label fw-medium">
                        <@spring.message "events.filters.type"/>:
                    </label>
                    <select id="typeFilter" class="form-select">
                        <option value=""><@spring.message "events.filters.allTypes"/></option>
                        <option value="online"><@spring.message "events.format.online"/></option>
                        <option value="offline"><@spring.message "events.format.offline"/></option>
                    </select>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="sortFilter" class="form-label fw-medium">
                        <@spring.message "events.filters.sort"/>:
                    </label>
                    <select id="sortFilter" class="form-select">
                        <option value="startDate,desc"><@spring.message "events.filters.newFirst"/></option>
                        <option value="startDate,asc"><@spring.message "events.filters.oldFirst"/></option>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="searchInput" class="form-label fw-medium">
                        <@spring.message "events.filters.search"/>:
                    </label>
                    <input type="text" id="searchInput" class="form-control"
                           placeholder="${springMacroRequestContext.getMessage('events.filters.searchPlaceholder')}">
                </div>
                <div class="col-md-2 mb-3">
                    <button id="applyFilters" class="btn btn-primary w-100">
                        <i class="fas fa-filter me-1"></i><@spring.message "events.filters.apply"/>
                    </button>
                </div>
            </div>
        </div>

        <!-- Загрузка -->
        <div id="loadingSpinner" class="text-center py-4" style="display: none;">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden"><@spring.message "common.loading"/></span>
            </div>
            <p class="mt-2"><@spring.message "events.loading"/></p>
        </div>

        <!-- Рекомендуемое мероприятие -->
        <div id="featuredEventContainer" class="row mb-5" style="display: none;">
            <div class="col-12">
                <div id="featuredEventCard" class="card event-card featured-event shadow-lg">
                    <!-- Контент будет загружен через JavaScript -->
                </div>
            </div>
        </div>

        <h3 id="eventsTitle" class="h4 fw-semibold mb-4 text-dark">
            <@spring.message "events.upcoming"/>
        </h3>

        <!-- Контейнер для мероприятий -->
        <div id="eventsContainer" class="row g-4">
            <!-- События будут загружены здесь -->
        </div>

        <!-- Сообщение об отсутствии данных -->
        <div id="noEventsMessage" class="text-center py-5" style="display: none;">
            <i class="fas fa-calendar-times fa-3x text-muted mb-3"></i>
            <h4 class="text-muted"><@spring.message "events.notFound.title"/></h4>
            <p class="text-muted"><@spring.message "events.notFound.message"/></p>
        </div>

        <!-- Пагинация -->
        <div id="paginationContainer" class="d-flex justify-content-center mt-4">
            <!-- Пагинация будет загружена здесь -->
        </div>

        <!-- Форма подписки -->
        <div class="mt-5">
            <form id="subscriptionForm"
                  action="/notifications/${(isBlogSubscribed!false)?then('unsubscribe', 'subscribe')}"
                  method="post">
                <@main.csrf/>
                <input type="hidden" name="type" value="EVENT">
                <#if (isAuthenticated!false) && (isBlogSubscribed!false)>
                    <button class="btn btn-outline-danger">
                        🔕 <@spring.message "events.subscription.unsubscribe"/>
                    </button>
                <#else>
                    <button class="btn btn-outline-primary">
                        📩 <@spring.message "events.subscription.subscribe"/>
                    </button>
                </#if>
            </form>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
    <script>
        class EventsManager {
            constructor() {
                this.currentPage = 0;
                this.pageSize = 9;
                this.currentType = '';
                this.currentSort = 'startDate,desc';
                this.isOffline = null; // null, true, false
                this.totalPages = 0;
                this.searchQuery = '';
                this.init();
            }

            init() {
                this.setupEventListeners();
                this.loadEvents();
                this.setupSubscriptionForm();
            }

            setupEventListeners() {
                document.getElementById('applyFilters').addEventListener('click', () => {
                    this.performSearch();
                });

                // Поиск по Enter
                document.getElementById('searchInput').addEventListener('keypress', (e) => {
                    if (e.key === 'Enter') {
                        this.performSearch();
                    }
                });

                // Очистка поиска при изменении фильтров
                document.getElementById('typeFilter').addEventListener('change', () => {
                    this.applyFilters();
                });

                document.getElementById('sortFilter').addEventListener('change', () => {
                    this.applyFilters();
                });
            }

            applyFilters() {
                this.currentPage = 0;
                const filterValue = document.getElementById('typeFilter').value;

                // Преобразуем значения фильтра в boolean или null
                if (filterValue === 'online') {
                    this.isOffline = false;
                } else if (filterValue === 'offline') {
                    this.isOffline = true;
                } else {
                    this.isOffline = null;
                }

                this.currentSort = document.getElementById('sortFilter').value;
                this.loadEvents();
            }

            performSearch() {
                this.searchQuery = document.getElementById('searchInput').value.trim();
                this.applyFilters(); // Применяем все фильтры включая поиск
            }

            async loadEvents() {
                this.showLoading(true);

                try {
                    const urlParams = new URLSearchParams({
                        page: this.currentPage,
                        size: this.pageSize
                    });

                    // Добавляем параметр type только если isOffline не null
                    if (this.isOffline !== null) {
                        urlParams.append('type', this.isOffline ? 'offline' : 'online');
                    }

                    if (this.currentSort) {
                        urlParams.append('sort', this.currentSort);
                    }

                    if (this.searchQuery) {
                        urlParams.append('search', this.searchQuery);
                    }

                    console.log('Loading events with params:', urlParams.toString());

                    const response = await fetch(`/api/event?` + urlParams.toString() + ``);

                    if (!response.ok) {
                        throw new Error('Ошибка загрузки данных');
                    }

                    const data = await response.json();
                    console.log('Loaded events data:', data);

                    this.renderEvents(data);
                    this.renderPagination(data);

                } catch (error) {
                    console.error('Error loading events:', error);
                    this.showError('Ошибка загрузки мероприятий. Попробуйте позже.');
                } finally {
                    this.showLoading(false);
                }
            }

            renderEvents(data) {
                const container = document.getElementById('eventsContainer');
                const featuredContainer = document.getElementById('featuredEventContainer');
                const noEventsMessage = document.getElementById('noEventsMessage');

                if (!data.content || data.content.length === 0) {
                    container.innerHTML = '';
                    featuredContainer.style.display = 'none';
                    noEventsMessage.style.display = 'block';
                    return;
                }

                noEventsMessage.style.display = 'none';

                // Показываем первое мероприятие как рекомендуемое только на первой странице и без поиска
                if (this.currentPage === 0 && data.content.length > 0 && !this.searchQuery) {
                    this.renderFeaturedEvent(data.content[0]);
                    featuredContainer.style.display = 'block';

                    // Остальные события
                    const remainingEvents = data.content.slice(1);
                    container.innerHTML = remainingEvents.map(event => this.createEventCard(event)).join('');
                } else {
                    featuredContainer.style.display = 'none';
                    container.innerHTML = data.content.map(event => this.createEventCard(event)).join('');
                }
            }

            renderFeaturedEvent(event) {
                console.log('Rendering featured event:', event);

                const featuredCard = document.getElementById('featuredEventCard');
                const backgroundImage = this.getEventBackgroundFromDB(event);

                console.log('Background image for featured event:', backgroundImage);

                featuredCard.style.backgroundImage = "url('" + backgroundImage + "')";
                featuredCard.onclick = function () {
                    window.location.href = "/events/" + event.id;
                };

                const dateSpan = this.formatDate(event.startDate);
                const eventTypeName = event.type?.name || 'Мероприятие';
                const typeClass = this.getTypeClass(eventTypeName);

                // Получаем данные из переводов
                const translation = event.eventTranslations && event.eventTranslations.length > 0 ? event.eventTranslations[0] : {};
                const title = translation.title || 'Мероприятие';

                const organizationName = event.organizations && event.organizations.length > 0 ?
                    event.organizations[0].name : 'Организатор не указан';

                const location = event.isOffline ? (event.address || 'Место не указано') : 'Онлайн';

                featuredCard.innerHTML =
                    '<div class="badge event-date px-3 py-2">' + dateSpan + '</div>' +
                    '<div class="badge event-type-badge ' + typeClass + ' px-3 py-1">' + eventTypeName + '</div>' +
                    '<div class="event-content p-4">' +
                    '<h2 class="event-title mb-3">' + title + '</h2>' +
                    '<div class="mb-2 opacity-75">' +
                    '<i class="fas fa-user me-2"></i>' + organizationName +
                    '</div>' +
                    '<div class="opacity-75">' +
                    '<i class="fas fa-' + this.getLocationIcon(location, event.isOffline) + ' me-2"></i>' + location +
                    '</div>' +
                    '</div>';
            }

            createEventCard(event) {
                console.log('Creating event card for:', event);

                const backgroundImage = this.getEventBackgroundFromDB(event);
                console.log('Background image for event card:', backgroundImage);

                const dateSpan = this.formatDate(event.startDate);
                const eventTypeName = event.type?.name || 'Мероприятие';
                const typeClass = this.getTypeClass(eventTypeName);

                // Получаем данные из переводов
                const translation = event.eventTranslations && event.eventTranslations.length > 0 ? event.eventTranslations[0] : {};
                const title = translation.title || 'Мероприятие';

                // Получаем первую организацию из списка
                const organizationName = event.organizations && event.organizations.length > 0 ?
                    event.organizations[0].name : 'Организатор не указан';

                // Определяем локацию
                const location = event.isOffline ? (event.address || 'Место не указано') : 'Онлайн';
                const locationIcon = this.getLocationIcon(location, event.isOffline);

                return (
                    '<div class="col-12 col-md-6 col-xl-4">' +
                    '<div class="card event-card shadow h-100" ' +
                    'style="background-image: url(' + backgroundImage + ');" ' +
                    'onclick="window.location.href=\'/events/' + event.id + '\'">' +
                    '<div class="badge event-date px-2 py-1">' + dateSpan + '</div>' +
                    '<div class="badge event-type-badge ' + typeClass + ' px-2 py-1">' + eventTypeName + '</div>' +
                    '<div class="event-content p-3">' +
                    '<h4 class="event-title h5 mb-2">' + title + '</h4>' +
                    '<div class="small mb-1 opacity-75">' +
                    '<i class="fas fa-user me-1"></i>' + organizationName +
                    '</div>' +
                    '<div class="small opacity-75">' +
                    '<i class="fas fa-' + locationIcon + ' me-1"></i>' + location +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>'
                );
            }

            renderPagination(data) {
                const container = document.getElementById('paginationContainer');
                this.totalPages = data.totalPages;

                if (data.totalPages <= 1) {
                    container.innerHTML = '';
                    return;
                }

                let paginationHtml = '<nav><ul class="pagination">';

                // Предыдущая страница
                if (data.number > 0) {
                    paginationHtml += `
                        <li class="page-item">
                            <a class="page-link" href="#" data-page="` + (data.number - 1) + `">
                                <i class="fas fa-chevron-left"></i>
                            </a>
                        </li>
                    `;
                }

                // Номера страниц
                const startPage = Math.max(0, data.number - 2);
                const endPage = Math.min(data.totalPages - 1, data.number + 2);

                for (let i = startPage; i <= endPage; i++) {
                    const isActive = i === data.number ? 'active' : '';
                    paginationHtml += `
                        <li class="page-item ` + isActive + `">
                            <a class="page-link" href="#" data-page="` + i + `">` + (i + 1) + `</a>
                        </li>
                    `;
                }

                // Следующая страница
                if (data.number < data.totalPages - 1) {
                    paginationHtml += `
                        <li class="page-item">
                            <a class="page-link" href="#" data-page="` + (data.number + 1) + `">
                                <i class="fas fa-chevron-right"></i>
                            </a>
                        </li>
                    `;
                }

                paginationHtml += '</ul></nav>';
                container.innerHTML = paginationHtml;

                container.querySelectorAll('a[data-page]').forEach(link => {
                    link.addEventListener('click', (e) => {
                        e.preventDefault();
                        this.currentPage = parseInt(e.target.closest('a').getAttribute('data-page'));
                        this.loadEvents();
                        window.scrollTo({top: 0, behavior: 'smooth'});
                    });
                });
            }

            getEventBackgroundFromDB(event) {
                console.log('Getting background for event:', event.id, 'mainImageId:', event.mainImageId);

                if (event.mainImageId) {
                    const imageUrl = `/api/files/` + event.mainImageId + `/view`;
                    console.log('Using database image:', imageUrl);
                    return imageUrl;
                }

                const defaultUrl = this.getEventBackground(event.type?.name || 'default');
                console.log('Using default image:', defaultUrl);
                return defaultUrl;
            }

            getEventBackground(category) {
                const backgrounds = {
                    'олимпиада': 'https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&w=1200&q=80',
                    'семинар': 'https://images.unsplash.com/photo-1517077304055-6e89abbf09b0?auto=format&fit=crop&w=800&q=80',
                    'лекция': 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=800&q=80',
                    'online': 'https://images.unsplash.com/photo-1517077304055-6e89abbf09b0?auto=format&fit=crop&w=800&q=80',
                    'offline': 'https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&w=1200&q=80',
                    'default': 'https://images.unsplash.com/photo-1509062522246-3755977927d7?auto=format&fit=crop&w=800&q=80'
                };

                return backgrounds[category?.toLowerCase()] || backgrounds.default;
            }

            getTypeClass(type) {
                const typeClasses = {
                    'олимпиада': 'badge-олимпиада',
                    'семинар': 'badge-семинар',
                    'лекция': 'badge-лекция',
                    'online': 'badge-online',
                    'offline': 'badge-offline'
                };

                return typeClasses[type?.toLowerCase()] || 'badge-семинар';
            }

            getLocationIcon(location, isOffline) {
                if (isOffline === false) {
                    return 'laptop';
                }
                if (location && location.toLowerCase().includes('гибрид')) {
                    return 'globe';
                }
                return 'map-marker-alt';
            }

            formatDate(dateString) {
                if (!dateString) return 'Дата не указана';

                try {
                    const date = new Date(dateString);
                    const options = {day: 'numeric', month: 'long'};
                    return date.toLocaleDateString('ru-RU', options);
                } catch (error) {
                    return dateString;
                }
            }

            showLoading(show) {
                const spinner = document.getElementById('loadingSpinner');
                const container = document.getElementById('eventsContainer');
                const featuredContainer = document.getElementById('featuredEventContainer');

                if (show) {
                    spinner.style.display = 'block';
                    container.style.display = 'none';
                    featuredContainer.style.display = 'none';
                } else {
                    spinner.style.display = 'none';
                    container.style.display = 'flex';
                }
            }

            showError(message) {
                const container = document.getElementById('eventsContainer');
                const noEventsMessage = document.getElementById('noEventsMessage');

                container.innerHTML = '';
                noEventsMessage.innerHTML = `
                    <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
                    <h4 class="text-muted">` + message + `</h4>
                    <button class="btn btn-primary mt-3" onclick="location.reload()">
                        <i class="fas fa-redo me-2"></i>Попробовать снова
                    </button>
                `;
                noEventsMessage.style.display = 'block';
            }

            setupSubscriptionForm() {
                const subscriptionForm = document.getElementById('subscriptionForm');
                const subscriptionButton = subscriptionForm.querySelector('button');

                subscriptionForm.addEventListener('submit', async function (e) {
                    e.preventDefault();

                    const formData = new FormData(subscriptionForm);
                    const isSubscribed = subscriptionButton.textContent.includes('Подписаться');

                    try {
                        if (isSubscribed) {
                            subscriptionForm.action = '/notifications/subscribe';
                            formData.set('type', 'EVENT');
                        } else {
                            subscriptionForm.action = '/notifications/unsubscribe';
                            formData.set('type', 'EVENT');
                        }

                        subscriptionButton.disabled = true;
                        subscriptionButton.textContent = '⏳ Обработка...';

                        const response = await fetch(subscriptionForm.action, {
                            method: 'POST',
                            body: formData
                        });

                        if (response.status === 401 || response.status === 403) {
                            window.location.href = '/auth/login';
                            return;
                        }

                        if (response.ok) {
                            if (isSubscribed) {
                                showNotification('✅ Вы успешно подписались на уведомления мероприятий!', 'success');
                                updateButtonToUnsubscribe();
                            } else {
                                showNotification('❌ Вы отписались от уведомлений мероприятий', 'info');
                                updateButtonToSubscribe();
                            }
                        } else {
                            throw new Error('Network response was not ok');
                        }

                    } catch (error) {
                        console.error('Error:', error);
                        showNotification('❌ Произошла ошибка. Попробуйте позже.', 'error');
                        if (isSubscribed) {
                            updateButtonToSubscribe();
                        } else {
                            updateButtonToUnsubscribe();
                        }
                    }
                });

                function updateButtonToUnsubscribe() {
                    subscriptionButton.textContent = '🔕 Отписаться от уведомлений';
                    subscriptionButton.className = 'btn btn-outline-danger';
                    subscriptionButton.disabled = false;
                    subscriptionForm.action = '/notifications/unsubscribe';
                }

                function updateButtonToSubscribe() {
                    subscriptionButton.textContent = '📩 Подписаться на уведомления';
                    subscriptionButton.className = 'btn btn-outline-primary';
                    subscriptionButton.disabled = false;
                    subscriptionForm.action = '/notifications/subscribe';
                }

                function showNotification(message, type = 'info') {
                    const existingNotifications = document.querySelectorAll('.notification-toast');
                    existingNotifications.forEach(notification => notification.remove());

                    const notification = document.createElement('div');
                    let alertType = 'info';
                    if (type === 'success') {
                        alertType = 'success';
                    } else if (type === 'error') {
                        alertType = 'danger';
                    }
                    notification.className = `notification-toast alert alert-` + alertType + ` alert-dismissible fade show`;
                    notification.style.cssText = `
                        position: fixed;
                        top: 20px;
                        right: 20px;
                        z-index: 1050;
                        min-width: 300px;
                        max-width: 500px;
                        animation: slideInRight 0.3s ease-out;
                    `;

                    notification.innerHTML = `
                        ` + message + `
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    `;

                    if (!document.querySelector('#notification-styles')) {
                        const style = document.createElement('style');
                        style.id = 'notification-styles';
                        style.textContent = `
                            @keyframes slideInRight {
                                from {
                                    transform: translateX(100%);
                                    opacity: 0;
                                }
                                to {
                                    transform: translateX(0);
                                    opacity: 1;
                                }
                            }

                            @keyframes slideOutRight {
                                from {
                                    transform: translateX(0);
                                    opacity: 1;
                                }
                                to {
                                    transform: translateX(100%);
                                    opacity: 0;
                                }
                            }
                        `;
                        document.head.appendChild(style);
                    }

                    document.body.appendChild(notification);

                    setTimeout(() => {
                        if (notification.parentNode) {
                            notification.style.animation = 'slideOutRight 0.3s ease-in';
                            setTimeout(() => {
                                if (notification.parentNode) {
                                    notification.remove();
                                }
                            }, 300);
                        }
                    }, 5000);

                    const closeButton = notification.querySelector('.btn-close');
                    if (closeButton) {
                        closeButton.addEventListener('click', () => {
                            notification.remove();
                        });
                    }
                }
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            new EventsManager();
        });
    </script>
</@main.layout>