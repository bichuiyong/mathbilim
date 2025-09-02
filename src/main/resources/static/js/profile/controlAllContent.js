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
        // Фильтры
        document.getElementById('allContentTypeFilter').addEventListener('change', (e) => {
            this.currentType = e.target.value;
            this.currentPage = 0;
            this.loadAllContent();
        });

        document.getElementById('allContentStatusFilter').addEventListener('change', (e) => {
            this.currentStatus = e.target.value;
            this.currentPage = 0;
            this.loadAllContent();
        });

        // Поиск
        document.getElementById('allContentSearchBtn').addEventListener('click', () => {
            this.performSearch();
        });

        document.getElementById('allContentSearch').addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.performSearch();
            }
        });
    }

    performSearch() {
        this.currentQuery = document.getElementById('allContentSearch').value.trim();
        this.currentPage = 0;
        this.loadAllContent();
    }

    async loadAllContent() {
        const contentList = document.getElementById('allContentList');
        const pagination = document.getElementById('allContentPagination');

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
                    return data.content.map(item => ({...item, contentType: type}));
                }
                return [];
            } catch (error) {
                console.error(`Ошибка загрузки ${type}:`, error);
                return [];
            }
        });

        const results = await Promise.all(promises);
        allContent = results.flat();

        allContent.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

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
            throw new Error('Ошибка загрузки контента');
        }

        const data = await response.json();
        const contentWithType = data.content.map(item => ({...item, contentType: this.currentType}));
        this.renderAllContent(contentWithType);
        this.renderServerPagination(data);
    }

    getContentTitle(item, type) {
        let title = '';

        switch (type) {
            case 'event':
                if (item.eventTranslations && item.eventTranslations[0]?.title) {
                    title = item.eventTranslations[0].title;
                } else {
                    title = item.title || 'Без заголовка';
                }
                break;
            case 'blog':
                if (item.blogTranslations && item.blogTranslations[0]?.title) {
                    title = item.blogTranslations[0].title;
                } else {
                    title = item.title || 'Без заголовка';
                }
                break;
            case 'post':
                if (item.postTranslations && item.postTranslations[0]?.title) {
                    title = item.postTranslations[0].title;
                } else {
                    title = item.title || 'Без заголовка';
                }
                break;
            case 'news':
                if (item.newsTranslations && item.newsTranslations[0]?.title) {
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

        console.log(`📝 Итоговый title для ${type}:`, title);
        return title;
    }

    renderServerPagination(data) {
        const pagination = document.getElementById('allContentPagination');
        const paginationList = pagination.querySelector('.pagination');

        if (data.totalPages <= 1) {
            pagination.style.display = 'none';
            return;
        }

        pagination.style.display = 'block';
        let paginationHtml = '';

        // Кнопка "Назад"
        paginationHtml += `
            <li class="page-item ${data.first ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="allContentManager.goToPage(${data.number - 1})" 
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
                    <a class="page-link" href="#" onclick="allContentManager.goToPage(0)">1</a>
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
                        <a class="page-link" href="#" onclick="allContentManager.goToPage(${i})">${i + 1}</a>
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
                    <a class="page-link" href="#" onclick="allContentManager.goToPage(${data.totalPages - 1})">${data.totalPages}</a>
                </li>
            `;
        }

        paginationHtml += `
            <li class="page-item ${data.last ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="allContentManager.goToPage(${data.number + 1})" 
                   ${data.last ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Next">
                    &raquo;
                </a>
            </li>
        `;

        paginationList.innerHTML = paginationHtml;
    }

    renderAllContent(content) {
        const contentList = document.getElementById('allContentList');

        if (!content || content.length === 0) {
            contentList.innerHTML = `
                <div class="text-center text-muted py-5">
                    <i class="fas fa-inbox fa-3x mb-3"></i>
                    <p>Контент не найден</p>
                </div>
            `;
            return;
        }

        const contentHtml = content.map(item => this.renderContentCard(item)).join('');
        contentList.innerHTML = contentHtml;
    }

    renderContentCard(item) {
        const typeLabel = this.getTypeLabel(item.contentType);
        const title = this.getContentTitle(item, item.contentType);


        return `
            <div class="card mb-3">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start">
                        <div class="flex-grow-1">
                            <div class="d-flex align-items-center mb-2">
                                <span class="badge bg-primary me-2">${typeLabel}</span>
                            </div>
                            
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

    async deleteContent(type, id, title) {
        if (!confirm(`Вы уверены, что хотите удалить "${title}"?`)) {
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
        if (!deleteUrl) {
            this.showNotification('Неизвестный тип контента', 'error');
            return;
        }

        let csrfToken = null;
        const csrfMeta = document.querySelector('meta[name="_csrf"]');
        if (csrfMeta) {
            csrfToken = csrfMeta.getAttribute('content');
        }

        if (!csrfToken) {
            console.warn('CSRF токен не найден');
            this.showNotification('Ошибка безопасности: CSRF токен не найден', 'error');
            return;
        }

        try {
            const formData = new FormData();
            formData.append('_csrf', csrfToken);
            formData.append('_method', 'delete');

            const response = await fetch(deleteUrl, {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            });

            if (response.ok) {
                this.showNotification('Контент успешно удален', 'success');
                this.loadAllContent();
            } else {
                throw new Error(`Ошибка сервера: ${response.status}`);
            }

        } catch (error) {
            console.error('Ошибка при удалении:', error);
            this.showNotification('Ошибка при удалении контента', 'error');
        }
    }


    renderClientPagination(totalItems, pageSize) {
        const pagination = document.getElementById('allContentPagination');
        const paginationList = pagination.querySelector('.pagination');

        const totalPages = Math.ceil(totalItems / pageSize);

        if (totalPages <= 1) {
            pagination.style.display = 'none';
            return;
        }

        pagination.style.display = 'block';
        let paginationHtml = '';

        paginationHtml += `
            <li class="page-item ${this.currentPage === 0 ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="allContentManager.goToPage(${this.currentPage - 1})" 
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
                    <a class="page-link" href="#" onclick="allContentManager.goToPage(0)">1</a>
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
                        <a class="page-link" href="#" onclick="allContentManager.goToPage(${i})">${i + 1}</a>
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
                    <a class="page-link" href="#" onclick="allContentManager.goToPage(${totalPages - 1})">${totalPages}</a>
                </li>
            `;
        }

        paginationHtml += `
            <li class="page-item ${this.currentPage >= totalPages - 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" onclick="allContentManager.goToPage(${this.currentPage + 1})" 
                   ${this.currentPage >= totalPages - 1 ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Next">
                    &raquo;
                </a>
            </li>
        `;

        paginationList.innerHTML = paginationHtml;
    }

    goToPage(page) {
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
            default: return status;
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
        return labels[type] || type;
    }

    formatDate(dateString) {
        if (!dateString) return 'Неизвестно';

        const date = new Date(dateString);
        return date.toLocaleDateString('ru-RU', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    truncateText(text, maxLength) {
        if (!text || text.length <= maxLength) return text;
        return text.substring(0, maxLength) + '...';
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    showNotification(message, type = 'info') {
        const alertClass = type === 'success' ? 'alert-success' :
            type === 'error' ? 'alert-danger' : 'alert-info';

        const notification = document.createElement('div');
        notification.className = `alert ${alertClass} alert-dismissible fade show position-fixed`;
        notification.style.cssText = 'top: 20px; right: 20px; z-index: 1060; min-width: 300px;';
        notification.innerHTML = `
            <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : 'info-circle'} me-2"></i>
            ${message}
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
    allContentManager = new AllContentManager();
});