class AllContentManager {
    constructor() {
        this.currentPage = 0;
        this.currentType = 'all';
        this.currentStatus = '';
        this.currentQuery = '';
        this.allContent = []; // Для хранения всего контента при типе "all"

        this.initStyles();
        this.initEventListeners();
        this.loadAllContent();
    }

    initStyles() {
        // Добавляем стили для пагинации
        const style = document.createElement('style');
        style.textContent = `
            .pagination .page-link {
                border: none;
                color: #0066cc;
                padding: 0.5rem 0.75rem;
                margin: 0 0.125rem;
                border-radius: 8px;
                transition: all 0.2s ease;
            }

            .pagination .page-link:hover {
                background-color: #e3f2fd;
                color: #0066cc;
            }

            .pagination .page-item.active .page-link {
                background-color: #0066cc;
                color: white;
                box-shadow: 0 2px 4px rgba(0,102,204,0.3);
            }

            .pagination .page-item.disabled .page-link {
                color: #6c757d;
                background-color: transparent;
            }

            .content-card {
                transition: all 0.3s ease;
            }
            
            .content-card:hover {
                transform: translateY(-2px);
                box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            }

            .fade-in {
                animation: fadeIn 0.3s ease-in;
            }
            
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(10px); }
                to { opacity: 1; transform: translateY(0); }
            }
        `;
        document.head.appendChild(style);
    }

    initEventListeners() {
        // Проверяем существование элементов перед добавлением обработчиков
        const typeFilter = document.getElementById('allContentTypeFilter');
        const statusFilter = document.getElementById('allContentStatusFilter');
        const searchBtn = document.getElementById('allContentSearchBtn');
        const searchInput = document.getElementById('allContentSearch');

        // Фильтры
        if (typeFilter) {
            typeFilter.addEventListener('change', (e) => {
                this.currentType = e.target.value;
                this.currentPage = 0;
                this.loadAllContent();
            });
        }

        if (statusFilter) {
            statusFilter.addEventListener('change', (e) => {
                this.currentStatus = e.target.value;
                this.currentPage = 0;
                this.loadAllContent();
            });
        }

        // Поиск
        if (searchBtn) {
            searchBtn.addEventListener('click', () => {
                this.performSearch();
            });
        }

        if (searchInput) {
            searchInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    this.performSearch();
                }
            });
        }
    }

    performSearch() {
        const searchInput = document.getElementById('allContentSearch');
        this.currentQuery = searchInput ? searchInput.value.trim() : '';
        this.currentPage = 0;
        this.loadAllContent();
    }

    async loadAllContent() {
        const contentList = document.getElementById('allContentList');
        const pagination = document.getElementById('allContentPagination');

        if (!contentList) {
            console.error('Элемент allContentList не найден');
            return;
        }

        // Показать загрузку
        contentList.innerHTML = `
            <div class="text-center text-muted py-5">
                <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                <p>Загрузка контента...</p>
            </div>
        `;

        try {
            if (this.currentType === 'all') {
                await this.loadAllTypes();
            } else {
                await this.loadSingleType();
            }

        } catch (error) {
            console.error('Ошибка:', error);
            contentList.innerHTML = `
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    Ошибка загрузки контента. Попробуйте еще раз.
                </div>
            `;
        }
    }

    async loadAllTypes() {
        const types = ['post', 'blog', 'event', 'book', 'news', 'olympiad'];
        let allContent = [];

        // Загружаем данные по всем типам параллельно
        const promises = types.map(async (type) => {
            try {
                const params = new URLSearchParams({
                    page: 0,
                    size: 100, // Загружаем больше для объединения
                    type: type,
                    status: this.currentStatus
                });

                if (this.currentQuery) {
                    params.append('query', this.currentQuery);
                }

                const response = await fetch(`/api/admin/content?${params}`);
                if (response.ok) {
                    const data = await response.json();
                    // Добавляем contentType к каждому элементу и проверяем структуру
                    return data.content ? data.content.map(item => ({
                        ...item,
                        contentType: type,
                        // Обеспечиваем наличие creator объекта
                        creator: item.creator || { name: 'ADMIN', id: null }
                    })) : [];
                }
                return [];
            } catch (error) {
                console.error(`Ошибка загрузки ${type}:`, error);
                return [];
            }
        });

        const results = await Promise.all(promises);
        allContent = results.flat();

        // Сортируем по дате создания (новые сначала)
        allContent.sort((a, b) => {
            const dateA = new Date(a.createdAt || 0);
            const dateB = new Date(b.createdAt || 0);
            return dateB - dateA;
        });

        const pageSize = 10;
        const startIndex = this.currentPage * pageSize;
        const endIndex = startIndex + pageSize;
        const pageContent = allContent.slice(startIndex, endIndex);

        this.renderAllContent(pageContent);
        this.renderClientPagination(allContent.length, pageSize);
    }

    async loadSingleType() {
        const params = new URLSearchParams({
            page: this.currentPage,
            size: 10,
            type: this.currentType,
            status: this.currentStatus
        });

        if (this.currentQuery) {
            params.append('query', this.currentQuery);
        }

        const response = await fetch(`/api/admin/content?${params}`);

        if (!response.ok) {
            throw new Error(`Ошибка загрузки контента: ${response.status}`);
        }

        const data = await response.json();
        // Добавляем проверку и обеспечиваем наличие creator
        const contentWithType = (data.content || []).map(item => ({
            ...item,
            contentType: this.currentType,
            creator: item.creator || { name: 'ADMIN', id: null }
        }));

        this.renderAllContent(contentWithType);
        this.renderServerPagination(data);
    }

    getContentTitle(item, type) {
        if (!item) return 'Без заголовка';

        let title = '';

        try {
            switch (type) {
                case 'event':
                    if (item.eventTranslations && Array.isArray(item.eventTranslations) && item.eventTranslations.length > 0) {
                        title = item.eventTranslations[0].title;
                    } else {
                        title = item.title || 'Без заголовка';
                    }
                    break;
                case 'blog':
                    if (item.blogTranslations && Array.isArray(item.blogTranslations) && item.blogTranslations.length > 0) {
                        title = item.blogTranslations[0].title;
                    } else {
                        title = item.title || 'Без заголовка';
                    }
                    break;
                case 'post':
                    if (item.postTranslations && Array.isArray(item.postTranslations) && item.postTranslations.length > 0) {
                        title = item.postTranslations[0].title;
                    } else {
                        title = item.title || 'Без заголовка';
                    }
                    break;
                case 'news':
                    if (item.newsTranslations && Array.isArray(item.newsTranslations) && item.newsTranslations.length > 0) {
                        title = item.newsTranslations[0].title;
                    } else {
                        title = item.title || 'Без заголовка';
                    }
                    break;
                case 'book':
                    title = item.name || item.title || 'Без заголовка';
                    break;
                case 'olympiad':
                    title = item.title || 'Без заголовка';
                    break;
                default:
                    title = item.title || 'Без заголовка';
            }
        } catch (error) {
            console.error(`Ошибка получения заголовка для ${type}:`, error);
            title = 'Без заголовка';
        }

        return title || 'Без заголовка';
    }

    renderServerPagination(data) {
        const pagination = document.getElementById('allContentPagination');
        if (!pagination) return;

        const paginationList = pagination.querySelector('.pagination');
        if (!paginationList) return;

        if (!data || data.totalPages <= 1) {
            pagination.style.display = 'none';
            return;
        }

        pagination.style.display = 'block';
        let paginationHtml = '';

        // Кнопка "Назад"
        paginationHtml += `
            <li class="page-item ${data.first ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${data.number - 1})" 
                   ${data.first ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Previous">
                    &laquo;
                </a>
            </li>
        `;

        const maxPagesToShow = 7;
        let startPage = Math.max(0, data.number - Math.floor(maxPagesToShow / 2));
        let endPage = startPage + maxPagesToShow - 1;

        if (endPage >= data.totalPages) {
            endPage = data.totalPages - 1;
            startPage = Math.max(0, endPage - maxPagesToShow + 1);
        }

        // Первая страница и многоточие (если нужно)
        if (startPage > 0) {
            paginationHtml += `
                <li class="page-item">
                    <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(0)">1</a>
                </li>
            `;

            if (startPage > 1) {
                paginationHtml += `
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                `;
            }
        }

        // Номера страниц
        for (let i = startPage; i <= endPage; i++) {
            if (i === data.number) {
                paginationHtml += `
                    <li class="page-item active" aria-current="page">
                        <a class="page-link" href="#">${i + 1}</a>
                    </li>
                `;
            } else {
                paginationHtml += `
                    <li class="page-item">
                        <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${i})">${i + 1}</a>
                    </li>
                `;
            }
        }

        // Многоточие и последняя страница
        if (endPage < data.totalPages - 1) {
            if (endPage < data.totalPages - 2) {
                paginationHtml += `
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                `;
            }

            paginationHtml += `
                <li class="page-item">
                    <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${data.totalPages - 1})">${data.totalPages}</a>
                </li>
            `;
        }

        paginationHtml += `
            <li class="page-item ${data.last ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${data.number + 1})" 
                   ${data.last ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Next">
                    &raquo;
                </a>
            </li>
        `;

        paginationList.innerHTML = paginationHtml;
    }

    renderAllContent(content) {
        const contentList = document.getElementById('allContentList');
        if (!contentList) {
            console.error('Элемент allContentList не найден');
            return;
        }

        if (!content || content.length === 0) {
            contentList.innerHTML = `
                <div class="text-center text-muted py-5">
                    <i class="fas fa-inbox fa-3x mb-3"></i>
                    <p>Нету контента</p>
                </div>
            `;
            return;
        }

        try {
            const contentHtml = content.map(item => this.renderContentCard(item)).join('');
            contentList.innerHTML = contentHtml;
        } catch (error) {
            console.error('Ошибка при рендеринге контента:', error);
            contentList.innerHTML = `
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    Ошибка отображения контента
                </div>
            `;
        }
    }

    renderContentCard(item) {
        if (!item) {
            console.warn('Получен пустой item');
            return '';
        }

        const contentType = item.contentType || 'unknown';
        const typeLabel = this.getTypeLabel(contentType);
        const title = this.getContentTitle(item, contentType);

        const creatorName = this.getCreatorName(item);
        const createdAt = this.formatDate(item.createdAt);
        const viewCount = item.viewCount || 0;
        const description = item.description || '';
        const itemId = item.id || 0;

        const status = item.status || '';
        const statusBadge = status ? `<span class="badge ${this.getStatusBadgeClass(status)} ms-2">${this.getStatusLabel(status)}</span>` : '';

        return `
            <div class="card mb-3">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start">
                        <div class="flex-grow-1">
                            <div class="d-flex align-items-center mb-2">
                                <span class="badge bg-primary me-2">${typeLabel}</span>
                            </div>
                            
                                        <img src="/api/files/${item.mainImageId}/view" alt="${item.type} Image" class="content-image">

                            
                            <h6 class="card-title mb-2">${title}</h6>
                            
                            <div class="text-muted small mb-2">
                                <span><i class="fas fa-user me-1"></i>${item.creator.name || 'ADMIN'}</span>
                                <span class="ms-3"><i class="fas fa-calendar me-1"></i>${this.formatDate(item.createdAt)}</span>
                                ${item.viewCount ? `<span class="ms-3"><i class="fas fa-eye me-1"></i>${item.viewCount}</span>` : ''}
                            </div>
                            
                            ${item.description ? `<p class="card-text text-muted">${this.truncateText(item.description, 150)}</p>` : ''}
                        </div>
                        
                        <div class="ms-3">
                            <button class="btn btn-danger btn-sm" onclick="allContentManager.deleteContent('${item.contentType}', ${item.id}, '${this.escapeHtml(title)}')">
                                <i class="fas fa-trash me-1"></i>Удалить
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    getCreatorName(item) {
        try {
            if (item.creator && item.creator.name) {
                return item.creator.name;
            }

            if (item.creatorName) {
                return item.creatorName;
            }

            if (item.author && item.author.name) {
                return item.author.name;
            }

            if (item.authorName) {
                return item.authorName;
            }

            return 'ADMIN';
        } catch (error) {
            console.warn('Ошибка получения имени создателя:', error);
            return 'ADMIN';
        }
    }

    async deleteContent(type, id, title) {

        if (!confirm(`Вы уверены, что хотите удалить "${title}"?`)) {
            console.log('❌ Удаление отменено пользователем');
            return;
        }

        const deleteUrls = {
            'post': `/posts/delete/${id}`,
            'blog': `/api/blog/delete/${id}`,
            'event': `/events/delete/${id}`,
            'book': `/books/delete/${id}`,
            'news': `/news/delete/${id}`,
            'olympiad': `/olympiads/delete/${id}`
        };

        const deleteUrl = deleteUrls[type];
        console.log('🔗 URL для удаления:', deleteUrl);

        if (!deleteUrl) {
            console.error('❌ Неизвестный тип контента:', type);
            this.showNotification('Неизвестный тип контента', 'error');
            return;
        }

        let csrfToken = null;
        const csrfMeta = document.querySelector('meta[name="_csrf"]');
        if (csrfMeta) {
            csrfToken = csrfMeta.getAttribute('content');
        }

        console.log('🔐 CSRF токен:', csrfToken ? 'найден' : 'НЕ НАЙДЕН');

        if (!csrfToken) {
            console.warn('⚠️ CSRF токен не найден');
            this.showNotification('Ошибка безопасности: CSRF токен не найден', 'error');
            return;
        }

        try {
            const formData = new FormData();
            formData.append('_csrf', csrfToken);
            formData.append('_method', 'delete');

            console.log('📤 Отправка запроса на удаление...');
            console.log('📋 FormData содержимое:', {
                '_csrf': csrfToken,
                '_method': 'delete'
            });

            const response = await fetch(deleteUrl, {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            });

            console.log('📥 Ответ сервера:', {
                status: response.status,
                statusText: response.statusText,
                ok: response.ok,
                url: response.url
            });

            const responseText = await response.text();


            if (response.ok) {
                console.log('✅ Сервер сообщил об успешном удалении');

                if (responseText.includes('success') || responseText.includes('удален') || response.status === 200) {
                    this.showNotification('Контент успешно удален', 'success');
                    console.log('🔄 Перезагружаем список контента...');
                    await this.loadAllContent();
                } else {
                    console.warn('⚠️ Сервер вернул OK, но в ответе нет подтверждения удаления');
                    this.showNotification('Возможно, контент не был удален. Проверьте вручную.', 'warning');
                }
            } else {
                console.error('❌ Ошибка сервера при удалении:', {
                    status: response.status,
                    statusText: response.statusText,
                    responseText: responseText
                });

                // Пробуем распарсить JSON ошибку
                try {
                    const errorData = JSON.parse(responseText);
                    console.error('📋 Данные ошибки:', errorData);
                    this.showNotification(`Ошибка удаления: ${errorData.message || errorData.error || 'Неизвестная ошибка'}`, 'error');
                } catch (parseError) {
                    this.showNotification(`Ошибка сервера: ${response.status} - ${response.statusText}`, 'error');
                }
            }

        } catch (error) {
            console.error('💥 Критическая ошибка при удалении:', error);
            console.error('📊 Детали ошибки:', {
                name: error.name,
                message: error.message,
                stack: error.stack
            });
            this.showNotification('Критическая ошибка при удалении контента', 'error');
        }
    }

    renderClientPagination(totalItems, pageSize) {
        const pagination = document.getElementById('allContentPagination');
        if (!pagination) return;

        const paginationList = pagination.querySelector('.pagination');
        if (!paginationList) return;

        const totalPages = Math.ceil(totalItems / pageSize);

        if (totalPages <= 1) {
            pagination.style.display = 'none';
            return;
        }

        pagination.style.display = 'block';
        let paginationHtml = '';

        paginationHtml += `
            <li class="page-item ${this.currentPage === 0 ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${this.currentPage - 1})" 
                   ${this.currentPage === 0 ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Previous">
                    &laquo;
                </a>
            </li>
        `;

        const maxPagesToShow = 7;
        let startPage = Math.max(0, this.currentPage - Math.floor(maxPagesToShow / 2));
        let endPage = startPage + maxPagesToShow - 1;

        if (endPage >= totalPages) {
            endPage = totalPages - 1;
            startPage = Math.max(0, endPage - maxPagesToShow + 1);
        }

        if (startPage > 0) {
            paginationHtml += `
                <li class="page-item">
                    <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(0)">1</a>
                </li>
            `;

            if (startPage > 1) {
                paginationHtml += `
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                `;
            }
        }

        // Номера страниц
        for (let i = startPage; i <= endPage; i++) {
            if (i === this.currentPage) {
                paginationHtml += `
                    <li class="page-item active" aria-current="page">
                        <a class="page-link" href="#">${i + 1}</a>
                    </li>
                `;
            } else {
                paginationHtml += `
                    <li class="page-item">
                        <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${i})">${i + 1}</a>
                    </li>
                `;
            }
        }

        // Многоточие и последняя страница
        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                paginationHtml += `
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                `;
            }

            paginationHtml += `
                <li class="page-item">
                    <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${totalPages - 1})">${totalPages}</a>
                </li>
            `;
        }

        paginationHtml += `
            <li class="page-item ${this.currentPage >= totalPages - 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="event.preventDefault(); allContentManager.goToPage(${this.currentPage + 1})" 
                   ${this.currentPage >= totalPages - 1 ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Next">
                    &raquo;
                </a>
            </li>
        `;

        paginationList.innerHTML = paginationHtml;
    }

    goToPage(page) {
        if (page < 0) page = 0;
        this.currentPage = page;
        this.loadAllContent();
    }

    getStatusBadgeClass(status) {
        switch (status) {
            case 'APPROVED': return 'bg-success';
            case 'REJECTED': return 'bg-danger';
            case 'PENDING_REVIEW': return 'bg-warning';
            default: return 'bg-secondary';
        }
    }

    getStatusLabel(status) {
        switch (status) {
            case 'APPROVED': return 'Одобрено';
            case 'REJECTED': return 'Отклонено';
            case 'PENDING_REVIEW': return 'На рассмотрении';
            default: return status || 'Неизвестно';
        }
    }

    getTypeLabel(type) {
        const labels = {
            'post': 'Пост',
            'blog': 'Блог',
            'event': 'Событие',
            'book': 'Книга',
            'news': 'Новость',
            'olympiad': 'Олимпиада'
        };
        return labels[type] || type || 'Неизвестно';
    }

    formatDate(dateString) {
        if (!dateString) return 'Неизвестно';

        try {
            const date = new Date(dateString);
            if (isNaN(date.getTime())) {
                return 'Неверная дата';
            }

            return date.toLocaleDateString('ru-RU', {
                year: 'numeric',
                month: 'short',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            });
        } catch (error) {
            console.error('Ошибка форматирования даты:', error);
            return 'Ошибка даты';
        }
    }

    truncateText(text, maxLength) {
        if (!text || typeof text !== 'string') return '';
        if (text.length <= maxLength) return text;
        return text.substring(0, maxLength) + '...';
    }

    escapeHtml(text) {
        if (!text || typeof text !== 'string') return '';

        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    showNotification(message, type = 'info') {
        console.log(`🔔 Уведомление [${type}]:`, message);

        const alertClass = type === 'success' ? 'alert-success' :
            type === 'error' ? 'alert-danger' :
                type === 'warning' ? 'alert-warning' : 'alert-info';

        const notification = document.createElement('div');
        notification.className = `alert ${alertClass} alert-dismissible fade show position-fixed`;
        notification.style.cssText = 'top: 20px; right: 20px; z-index: 1060; min-width: 300px;';
        notification.innerHTML = `
            <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : type === 'warning' ? 'exclamation-triangle' : 'info-circle'} me-2"></i>
            ${this.escapeHtml(message)}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        document.body.appendChild(notification);

        setTimeout(() => {
            if (notification.parentNode) {
                notification.remove();
            }
        }, 5000);
    }
}

let allContentManager;
document.addEventListener('DOMContentLoaded', () => {
    try {
        allContentManager = new AllContentManager();
    } catch (error) {
        console.error('Ошибка инициализации AllContentManager:', error);
    }
});