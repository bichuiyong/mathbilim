document.addEventListener("DOMContentLoaded", function () {
    const container = document.querySelector(".container");
    if (!container) {
        console.error("Контейнер с классом .container не найден");
        return;
    }

    const userId = container.getAttribute("data-user-id");
    if (!userId) {
        console.error("Атрибут data-user-id отсутствует");
        return;
    }

    const contentList = document.getElementById("userContentList");
    if (!contentList) {
        console.error("Элемент #userContentList не найден");
        return;
    }

    contentList.innerHTML = `
        <div class="text-center text-muted py-5">
            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
            <p>Загрузка контента...</p>
        </div>`;

    function loadAllContent() {
        const types = ['post', 'blog', 'event', 'book'];
        const urls = types.map(type => `/api/users/content?type=${type}&creatorId=${userId}&page=0&size=10`);

        Promise.all(urls.map(url =>
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Ошибка загрузки: ${response.status}`);
                    }
                    return response.json();
                })
        ))
            .then(results => {
                let html = '<div class="row g-3">';
                let hasContent = false;

                results.forEach((result, index) => {
                    const type = types[index];
                    if (result.content && Array.isArray(result.content)) {
                        result.content.forEach(item => {
                            hasContent = true;
                            html += `
                                    <div class="col-md-4 col-6">
                                        <a href="/${type === 'post' ? 'posts' : type}/${item.id}" class="d-block border rounded overflow-hidden">
                                            <img src="/api/files/${item.mainImageId}/view" alt="${type} Image" class="img-fluid">
                                        </a>
                                    </div>`;
                        });
                    }
                });

                html += '</div>';

                if (!hasContent) {
                    contentList.innerHTML = `
                        <div class="no-content text-center py-5">
                            <svg width="120" height="120" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <rect x="12" y="14" width="40" height="36" rx="3" ry="3" fill="#eaeaea" stroke="#888" stroke-width="2"/>
                                <line x1="20" y1="20" x2="44" y2="20" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="26" x2="44" y2="26" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="32" x2="44" y2="32" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="38" x2="44" y2="38" stroke="#bbb" stroke-width="1.5"/>
                            </svg>
                            <p class="text-muted fs-5 mt-3">
                                У вас пока нет ни одного поста, блога, события или книги.
                            </p>
                        </div>`;
                } else {
                    contentList.innerHTML = html;
                    const contentDiv = contentList.querySelector('div.row');
                    if (contentDiv) {
                        contentDiv.classList.add('fade-in');
                    }
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
            });
    }

    loadAllContent();
});

document.addEventListener("DOMContentLoaded", function () {
    const container = document.querySelector(".container");
    if (!container) {
        console.error("Контейнер с классом .container не найден");
        return;
    }

    const userId = container.getAttribute("data-user-id");
    if (!userId) {
        console.error("Атрибут data-user-id отсутствует");
        return;
    }

    const contentList = document.getElementById("historyContentList");
    if (!contentList) {
        console.error("Элемент #historyContentList не найден");
        return;
    }

    contentList.innerHTML = `
        <div class="text-center text-muted py-5">
            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
            <p>Загрузка контента...</p>
        </div>`;

    let currentPage = 0;
    const pageSize = 10;
    let totalPages = 0;
    let allContent = [];

    function createPagination(totalItems) {
        totalPages = Math.ceil(totalItems / pageSize);
        if (totalPages <= 1) return '';

        let paginationHtml = '<nav aria-label="Page navigation" class="mt-4"><ul class="pagination justify-content-center">';

        // Предыдущая страница
        paginationHtml += `<li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="${currentPage - 1}">Назад</a>
        </li>`;

        // Страницы
        for (let i = 0; i < totalPages; i++) {
            if (i === currentPage) {
                paginationHtml += `<li class="page-item active"><a class="page-link" href="#">${i + 1}</a></li>`;
            } else if (i < 3 || i >= totalPages - 3 || Math.abs(i - currentPage) <= 2) {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" data-page="${i}">${i + 1}</a></li>`;
            } else if (i === 3 || i === totalPages - 4) {
                paginationHtml += `<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`;
            }
        }

        // Следующая страница
        paginationHtml += `<li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="${currentPage + 1}">Вперед</a>
        </li>`;

        paginationHtml += '</ul></nav>';
        return paginationHtml;
    }

    function displayContent(content) {
        let html = '<div class="content-grid">';
        content.forEach(item => {
            const createdDate = new Date(item.createdAt || item.publishedAt || Date.now()).toLocaleDateString('ru-RU');

            html += `
                <div class="content-card">
                    <div class="content-card-header">
                        <div class="content-avatar">
                            <img src="/api/files/${item.mainImageId}/view" alt="${item.type} Image" class="content-image">
                        </div>
                        <div class="content-info">
<h6 class="content-title">${getContentTitle(item, item.type)}</h6>
                            <div class="content-meta">
                                <span class="content-author">ADMIN</span>
                                <span class="content-date">${createdDate}</span>
                            </div>
                        </div>
                    </div>
                    <div class="content-badges">
                        <span class="badge content-type-badge">${getTypeLabel(item.type)}</span>
                        ${getStatusBadge(item.status)}
                    </div>
                </div>`;
        });
        html += '</div>';

        const paginationHtml = createPagination(allContent.length);

        // Добавляем стили
        const styles = `
            <style>
                .content-grid {
                    display: flex;
                    flex-direction: column;
                    gap: 16px;
                }
                
                .content-card {
                    background: white;
                    border: 1px solid #e9ecef;
                    border-radius: 12px;
                    padding: 20px;
                    box-shadow: 0 2px 4px rgba(0,0,0,0.04);
                    transition: all 0.2s ease;
                }
                
                .content-card:hover {
                    box-shadow: 0 4px 12px rgba(0,0,0,0.08);
                    transform: translateY(-1px);
                }
                
                .content-card-header {
                    display: flex;
                    align-items: center;
                    gap: 16px;
                    margin-bottom: 16px;
                }
                
                .content-avatar {
                    flex-shrink: 0;
                    width: 60px;
                    height: 60px;
                    border-radius: 8px;
                    overflow: hidden;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }
                
                .content-image {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
                
                .content-info {
                    flex: 1;
                    min-width: 0;
                }
                
                .content-title {
                    margin: 0 0 8px 0;
                    font-size: 16px;
                    font-weight: 600;
                    color: #212529;
                    line-height: 1.4;
                }
                
                .content-meta {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    font-size: 14px;
                    color: #6c757d;
                }
                
                .content-author {
                    font-weight: 500;
                    color: #495057;
                }
                
                .content-date::before {
                    content: "•";
                    margin-right: 8px;
                }
                
                .content-badges {
                    display: flex;
                    gap: 8px;
                    flex-wrap: wrap;
                }
                
                .content-type-badge {
                    background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
                    color: white;
                    font-weight: 500;
                    padding: 6px 12px;
                    border-radius: 20px;
                    font-size: 12px;
                }
                
                .badge.bg-success {
                    background: linear-gradient(135deg, #56ab2f 0%, #a8e6cf 100%) !important;
                }
                
                .badge.bg-warning {
                    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%) !important;
                    color: white !important;
                }
                
                .badge.bg-danger {
                    background: linear-gradient(135deg, #ff416c 0%, #ff4b2b 100%) !important;
                }
                
                .badge.bg-secondary {
                    background: linear-gradient(135deg, #a8a8a8 0%, #d3d3d3 100%) !important;
                }
                
                .badge.bg-info {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
                }
                
                .pagination .page-link {
                    color: #667eea;
                    border: 1px solid #dee2e6;
                    border-radius: 8px;
                    margin: 0 4px;
                    padding: 8px 16px;
                    transition: all 0.2s ease;
                }
                
                .pagination .page-link:hover {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    border-color: #667eea;
                }
                
                .pagination .page-item.active .page-link {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    border-color: #667eea;
                    color: white;
                }
                
                .pagination .page-item.disabled .page-link {
                    opacity: 0.5;
                }
            </style>
        `;

        contentList.innerHTML = styles + html + paginationHtml;

        // Добавляем обработчики для пагинации
        contentList.querySelectorAll('.page-link[data-page]').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                const page = parseInt(this.dataset.page);
                if (page >= 0 && page < totalPages) {
                    currentPage = page;
                    showCurrentPage();
                }
            });
        });
    }

    function showCurrentPage() {
        const startIndex = currentPage * pageSize;
        const endIndex = startIndex + pageSize;
        const currentContent = allContent.slice(startIndex, endIndex);
        displayContent(currentContent);
    }

    function getStatusBadge(status) {
        // Получаем имя статуса
        const statusName = status?.name || status;

        switch(statusName) {
            case 'APPROVED':
                return '<span class="badge bg-success">Одобрен</span>';
            case 'PENDING_REVIEW':
                return '<span class="badge bg-warning text-dark">На рассмотрении</span>';
            case 'REJECTED':
                return '<span class="badge bg-danger">Отклонён</span>';
            case 'DRAFT':
                return '<span class="badge bg-secondary">Черновик</span>';
            default:
                return `<span class="badge bg-info">${statusName || 'Не указан'}</span>`;
        }
    }

    function getTypeLabel(type) {
        switch(type) {
            case 'post': return 'Пост';
            case 'blog': return 'Блог';
            case 'event': return 'Событие';
            case 'book': return 'Книга';
            default: return 'Контент';
        }
    }

    function loadAllContent() {
        const types = ['post', 'blog', 'event', 'book'];
        const urls = types.map(type => `/api/users/history?id=${userId}&type=${type}&page=0&size=100`); // Увеличиваем размер для получения всех элементов

        Promise.all(urls.map(url =>
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Ошибка загрузки: ${response.status}`);
                    }
                    return response.json();
                })
        ))
            .then(results => {
                allContent = [];

                results.forEach((result, index) => {
                    const type = types[index];
                    if (result.content && Array.isArray(result.content)) {
                        result.content.forEach(item => {
                            allContent.push({
                                ...item,
                                type: type
                            });
                        });
                    }
                });

                if (allContent.length === 0) {
                    contentList.innerHTML = `
                        <div class="no-content text-center py-5">
                            <svg width="120" height="120" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <rect x="12" y="14" width="40" height="36" rx="3" ry="3" fill="#eaeaea" stroke="#888" stroke-width="2"/>
                                <line x1="20" y1="20" x2="44" y2="20" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="26" x2="44" y2="26" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="32" x2="44" y2="32" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="38" x2="44" y2="38" stroke="#bbb" stroke-width="1.5"/>
                            </svg>
                            <p class="text-muted fs-5 mt-3">
                                У вас пока нет ни одного поста, блога, события или книги.
                            </p>
                        </div>`;
                } else {
                    allContent.sort((a, b) => new Date(b.createdAt || b.publishedAt || 0) - new Date(a.createdAt || a.publishedAt || 0));

                    currentPage = 0;
                    showCurrentPage();
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
            });
    }

    loadAllContent();
});


function getContentTitle(item, type) {
    switch (type) {
        case 'event':
            return item.eventTranslations && item.eventTranslations[0]?.title
                ? item.eventTranslations[0].title
                : 'Без заголовка';
        case 'blog':
            return item.blogTranslations && item.blogTranslations[0]?.title
                ? item.blogTranslations[0].title
                : item.title || 'Без заголовка';
        case 'post':
            return item.postTranslations && item.postTranslations[0]?.title
                ? item.postTranslations[0].title
                : item.title || 'Без заголовка';
        case 'book':
            return item.name || 'Без заголовка';
        default:
            return item.title || 'Без заголовка';
    }
}

