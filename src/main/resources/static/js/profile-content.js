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
    const paginationNav = document.getElementById("contentPagination");
    const paginationUl = paginationNav.querySelector("ul.pagination");

    if (!contentList || !paginationNav || !paginationUl) {
        console.error("Не найден элемент для контента или пагинации");
        return;
    }

    const types = ['post', 'blog', 'event', 'book'];
    const pageSize = 10; // Размер страницы

    let currentPage = 0;
    let currentQuery = '';
    let currentFilter = 'all';

    console.log(`Страница загружена. userId=${userId}`);

    function renderPagination(totalPages, currentPage) {
        console.log(`Отрисовка пагинации: всего страниц ${totalPages}, текущая страница ${currentPage + 1}`);

        paginationUl.innerHTML = '';

        if (totalPages <= 1) {
            paginationNav.style.display = 'none';
            return;
        }

        paginationNav.style.display = 'block';

        // Кнопка "Назад"
        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPage === 0 ? 'disabled' : ''}`;
        prevLi.innerHTML = `<a class="page-link" href="#" aria-label="Previous">&laquo;</a>`;
        prevLi.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage > 0) {
                console.log(`Нажата кнопка "Назад", загрузка страницы ${currentPage}`);
                loadContentPage(currentPage - 1);
            }
        });
        paginationUl.appendChild(prevLi);

        // Логика отображения номеров страниц
        const maxPagesToShow = 7;
        let startPage = Math.max(0, currentPage - Math.floor(maxPagesToShow / 2));
        let endPage = startPage + maxPagesToShow - 1;

        if (endPage >= totalPages) {
            endPage = totalPages - 1;
            startPage = Math.max(0, endPage - maxPagesToShow + 1);
        }

        // Первая страница и многоточие
        if (startPage > 0) {
            const firstLi = document.createElement('li');
            firstLi.className = 'page-item';
            firstLi.innerHTML = `<a class="page-link" href="#">1</a>`;
            firstLi.addEventListener('click', (e) => {
                e.preventDefault();
                console.log(`Нажата кнопка страницы 1`);
                loadContentPage(0);
            });
            paginationUl.appendChild(firstLi);

            if (startPage > 1) {
                const ellipsisLi = document.createElement('li');
                ellipsisLi.className = 'page-item disabled';
                ellipsisLi.innerHTML = `<span class="page-link">...</span>`;
                paginationUl.appendChild(ellipsisLi);
            }
        }

        // Основные номера страниц
        for (let i = startPage; i <= endPage; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${i === currentPage ? 'active' : ''}`;
            li.innerHTML = `<a class="page-link" href="#">${i + 1}</a>`;
            li.addEventListener('click', (e) => {
                e.preventDefault();
                if (i !== currentPage) {
                    console.log(`Нажата кнопка страницы ${i + 1}`);
                    loadContentPage(i);
                }
            });
            paginationUl.appendChild(li);
        }

        // Многоточие и последняя страница
        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                const ellipsisLi = document.createElement('li');
                ellipsisLi.className = 'page-item disabled';
                ellipsisLi.innerHTML = `<span class="page-link">...</span>`;
                paginationUl.appendChild(ellipsisLi);
            }

            const lastLi = document.createElement('li');
            lastLi.className = 'page-item';
            lastLi.innerHTML = `<a class="page-link" href="#">${totalPages}</a>`;
            lastLi.addEventListener('click', (e) => {
                e.preventDefault();
                console.log(`Нажата кнопка страницы ${totalPages}`);
                loadContentPage(totalPages - 1);
            });
            paginationUl.appendChild(lastLi);
        }

        // Кнопка "Вперед"
        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}`;
        nextLi.innerHTML = `<a class="page-link" href="#" aria-label="Next">&raquo;</a>`;
        nextLi.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage < totalPages - 1) {
                console.log(`Нажата кнопка "Вперёд", загрузка страницы ${currentPage + 2}`);
                loadContentPage(currentPage + 1);
            }
        });
        paginationUl.appendChild(nextLi);
    }

    function loadContentPage(page) {
        currentPage = page;
        console.log(`Загрузка контента: страница=${currentPage + 1}, фильтр='${currentFilter}', запрос='${currentQuery}'`);

        // Показать индикатор загрузки
        contentList.innerHTML = `
            <div class="text-center text-muted py-5">
                <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                <p>Загрузка контента...</p>
            </div>`;

        contentList.scrollIntoView({ behavior: 'smooth', block: 'start' });

        if (currentFilter === 'all') {
            // Загружаем все типы контента с объединенной пагинацией
            loadAllContentTypes(page);
        } else {
            // Загрузка определенного типа контента
            loadSpecificContentType(page);
        }
    }

    function loadAllContentTypes(page) {
        // Для режима "все типы" делаем единый запрос с объединенным контентом
        const url = `/api/users/content/all?creatorId=${userId}&page=${page}&size=${pageSize}${currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : ''}`;

        // Если нет единого API endpoint для всех типов, делаем параллельные запросы
        const urls = types.map(type =>
            `/api/users/content?type=${type}&creatorId=${userId}&page=0&size=1000${currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : ''}`
        );

        Promise.all(urls.map(url =>
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        console.error(`Ошибка загрузки: ${response.status} при запросе ${url}`);
                        throw new Error(`Ошибка загрузки: ${response.status}`);
                    }
                    return response.json();
                })
        ))
            .then(results => {
                let allItems = [];

                // Собираем все элементы из всех типов
                results.forEach((result, index) => {
                    if (result.content && Array.isArray(result.content) && result.content.length > 0) {
                        const type = types[index];
                        result.content.forEach(item => {
                            allItems.push({
                                ...item,
                                contentType: type,
                                createdAt: new Date(item.createdAt || Date.now())
                            });
                        });
                    }
                });

                // Сортируем по дате создания (новые первыми)
                allItems.sort((a, b) => b.createdAt - a.createdAt);

                // Вычисляем пагинацию
                const totalItems = allItems.length;
                const totalPages = Math.ceil(totalItems / pageSize);
                const startIndex = page * pageSize;
                const endIndex = startIndex + pageSize;
                const pageItems = allItems.slice(startIndex, endIndex);

                if (pageItems.length > 0) {
                    let html = '<div class="row g-3">';

                    pageItems.forEach(item => {
                        const imageId = item.mainImageId || 'default-image-id';
                        const type = item.contentType;
                        html += `
                            <div class="col-md-4 col-6 mb-3">
                                <div class="card h-100 border-0 shadow-sm">
                                    <a href="/${type === 'post' ? 'posts' : type + 's'}/${item.id}" class="text-decoration-none">
                                        <div class="position-relative overflow-hidden rounded-top">
                                            <img src="/api/files/${imageId}/view" alt="${type} Image" class="card-img-top" style="height: 200px; object-fit: cover;" loading="lazy">
                                            <div class="position-absolute top-0 end-0 m-2">
                                                <span class="badge bg-primary">${getTypeLabel(type)}</span>
                                            </div>
                                        </div>
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-truncate mb-1">${item.title || 'Без названия'}</h6>
                                            <small class="text-muted">${formatDate(item.createdAt)}</small>
                                        </div>
                                    </a>
                                </div>
                            </div>`;
                    });

                    html += '</div>';
                    contentList.innerHTML = html;

                    // Анимация появления
                    const contentDiv = contentList.querySelector('div.row');
                    if (contentDiv) {
                        contentDiv.style.opacity = '0';
                        contentDiv.classList.add('fade-in');
                        setTimeout(() => {
                            contentDiv.style.opacity = '1';
                        }, 100);
                    }

                    renderPagination(totalPages, page);
                } else {
                    showNoContentMessage();
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                showErrorMessage();
            });
    }

    function loadSpecificContentType(page) {
        const typeMap = {
            posts: "post",
            blog: "blog",
            events: "event",
            books: "book"
        };
        const apiType = typeMap[currentFilter] || 'post';
        const url = `/api/users/content?type=${apiType}&creatorId=${userId}&page=${page}&size=${pageSize}${currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : ''}`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    console.error(`Ошибка загрузки: ${response.status} при запросе ${url}`);
                    throw new Error(`Ошибка загрузки: ${response.status}`);
                }
                return response.json();
            })
            .then(result => {
                if (result.content && Array.isArray(result.content) && result.content.length > 0) {
                    console.log(`Загружено ${result.content.length} элементов типа ${apiType}.`);
                    let html = '<div class="row g-3">';

                    result.content.forEach(item => {
                        const imageId = item.mainImageId || 'default-image-id';
                        html += `
                            <div class="col-md-4 col-6 mb-3">
                                <div class="card h-100 border-0 shadow-sm">
                                    <a href="/${apiType === 'post' ? 'posts' : apiType + 's'}/${item.id}" class="text-decoration-none">
                                        <div class="position-relative overflow-hidden rounded-top">
                                            <img src="/api/files/${imageId}/view" alt="${apiType} Image" class="card-img-top" style="height: 200px; object-fit: cover;" loading="lazy">
                                        </div>
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-truncate mb-1">${item.title || 'Без названия'}</h6>
                                            <small class="text-muted">${formatDate(item.createdAt)}</small>
                                        </div>
                                    </a>
                                </div>
                            </div>`;
                    });

                    html += '</div>';
                    contentList.innerHTML = html;

                    // Анимация появления
                    const contentDiv = contentList.querySelector('div.row');
                    if (contentDiv) {
                        contentDiv.style.opacity = '0';
                        contentDiv.classList.add('fade-in');
                        setTimeout(() => {
                            contentDiv.style.opacity = '1';
                        }, 100);
                    }

                    renderPagination(result.totalPages || 1, page);
                } else {
                    showNoContentMessage("Нет контента по выбранному фильтру и запросу.");
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                showErrorMessage();
            });
    }

    function showNoContentMessage(message = "У вас пока нет ни одного поста, блога, события или книги.") {
        console.log("Нет контента для отображения.");
        contentList.innerHTML = `
            <div class="no-content text-center py-5">
                <div class="mb-4">
                    <i class="fas fa-folder-open fa-3x text-muted mb-3"></i>
                </div>
                <h5 class="text-muted">Пока нет контента</h5>
                <p class="text-muted">${message}</p>
            </div>`;
        paginationNav.style.display = 'none';
    }

    function showErrorMessage() {
        contentList.innerHTML = `
            <div class="text-center py-5">
                <i class="fas fa-exclamation-triangle fa-2x text-danger mb-3"></i>
                <p class="text-danger">Ошибка загрузки контента. Попробуйте обновить страницу.</p>
            </div>`;
        paginationNav.style.display = 'none';
    }

    function getTypeLabel(type) {
        const labels = {
            post: 'Пост',
            blog: 'Блог',
            event: 'Событие',
            book: 'Книга'
        };
        return labels[type] || type;
    }

    function formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString('ru-RU', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        });
    }

        const searchInput = document.getElementById("contentSearch");
    const searchBtn = document.getElementById("moderationSearchBtn");
    const filterSelect = document.getElementById("contentFilter");

    if (searchBtn && searchInput && filterSelect) {
        searchBtn.addEventListener("click", () => {
            currentQuery = searchInput.value.trim();
            currentFilter = filterSelect.value;
            currentPage = 0;
            console.log(`Запущен поиск. Фильтр: '${currentFilter}', Запрос: '${currentQuery}'`);
            loadContentPage(currentPage);
        });

        searchInput.addEventListener("keypress", (e) => {
            if (e.key === 'Enter') {
                searchBtn.click();
            }
        });

        filterSelect.addEventListener("change", () => {
            currentFilter = filterSelect.value;
            currentPage = 0;
            console.log(`Сменен фильтр на '${currentFilter}'.`);
            loadContentPage(currentPage);
        });
    }

    loadContentPage(0);
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

    const typeFilter = document.getElementById("historyTypeFilter");
    const statusFilter = document.getElementById("historyStatusFilter");
    const searchInput = document.getElementById("historySearch");
    const searchBtn = document.getElementById("historySearchBtn");

    let currentPage = 0;
    const pageSize = 5;
    let totalPages = 0;
    let allContent = [];

    let currentType = "all";
    let currentStatus = "";
    let currentQuery = "";

    function createPagination(totalItems) {
        totalPages = Math.ceil(Math.max(totalItems || 0, 0) / pageSize);

        if (totalPages < 1) {
            totalPages = 1;
        }

        let paginationHtml = '<nav aria-label="Page navigation" class="mt-4"><ul class="pagination justify-content-center">';

        paginationHtml += `<li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="${currentPage - 1}">Назад</a>
        </li>`;

        for (let i = 0; i < totalPages; i++) {
            if (i === currentPage) {
                paginationHtml += `<li class="page-item active"><a class="page-link" href="#">${i + 1}</a></li>`;
            } else if (i < 3 || i >= totalPages - 3 || Math.abs(i - currentPage) <= 2) {
                paginationHtml += `<li class="page-item"><a class="page-link" href="#" data-page="${i}">${i + 1}</a></li>`;
            } else if (i === 3 || i === totalPages - 4) {
                paginationHtml += `<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`;
            }
        }

        paginationHtml += `<li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" data-page="${currentPage + 1}">Вперед</a>
        </li>`;

        paginationHtml += '</ul></nav>';
        return paginationHtml;
    }

    function displayContent(content, totalElements = null) {
        const totalForPagination = totalElements !== null ? totalElements : allContent.length;

        if (!content || content.length === 0) {
            const paginationHtml = createPagination(totalForPagination);
            contentList.innerHTML = `
                <div class="no-content text-center py-5">
                    <i class="fas fa-inbox fa-3x mb-3 text-muted"></i>
                    <p class="mb-0">Нет данных для отображения.</p>
                </div>
                ${paginationHtml}`;

            addPaginationHandlers();
            return;
        }

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

        const paginationHtml = createPagination(totalForPagination);

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
                    width: 100px;
                    height: 100px;
                    border-radius: 12px;
                    overflow: hidden;
                    background: #f0f0f0;
                    flex-shrink: 0;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    border: 1px solid #e9ecef;
                }
                
                .content-image {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                    object-position: center;
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

                /* Улучшенная сине-белая пагинация */
                .pagination {
                    padding-left: 0;
                    margin: 2rem 0;
                    list-style: none;
                    display: flex;
                    justify-content: center;
                    gap: 4px;
                    flex-wrap: wrap;
                }
                
                .pagination .page-item {
                    display: inline-block;
                }
                
                .pagination .page-link {
                    position: relative;
                    display: block;
                    padding: 10px 16px;
                    margin: 0;
                    line-height: 1.25;
                    color: #0066cc;
                    background-color: #ffffff;
                    border: 2px solid #e3f2fd;
                    border-radius: 8px;
                    text-decoration: none;
                    cursor: pointer;
                    font-weight: 500;
                    font-size: 14px;
                    transition: all 0.2s ease-in-out;
                    min-width: 44px;
                    text-align: center;
                    box-shadow: 0 1px 3px rgba(0, 102, 204, 0.1);
                }
                
                .pagination .page-link:hover {
                    color: #004499;
                    background-color: #f0f8ff;
                    border-color: #bbdefb;
                    transform: translateY(-1px);
                    box-shadow: 0 4px 8px rgba(0, 102, 204, 0.15);
                }
                
                .pagination .page-item.active .page-link {
                    z-index: 3;
                    color: #ffffff;
                    background: linear-gradient(135deg, #0066cc 0%, #004499 100%);
                    border-color: #0066cc;
                    cursor: default;
                    box-shadow: 0 4px 12px rgba(0, 102, 204, 0.3);
                    transform: none;
                }
                
                .pagination .page-item.active .page-link:hover {
                    transform: none;
                    box-shadow: 0 4px 12px rgba(0, 102, 204, 0.4);
                }
                
                .pagination .page-item.disabled .page-link {
                    color: #b0bec5;
                    pointer-events: none;
                    background-color: #f8f9fa;
                    border-color: #e9ecef;
                    cursor: not-allowed;
                    box-shadow: none;
                    opacity: 0.7;
                }

                .no-content {
                    font-size: 1.25rem;
                    color: #6c757d;
                    padding: 60px 20px;
                    background: #f8f9fa;
                    border-radius: 12px;
                    border: 1px solid #e9ecef;
                }
                
                .no-content i {
                    opacity: 0.5;
                }

                /* Responsive пагинация */
                @media (max-width: 576px) {
                    .pagination {
                        gap: 2px;
                        margin: 1.5rem 0;
                    }
                    
                    .pagination .page-link {
                        padding: 8px 12px;
                        font-size: 13px;
                        min-width: 36px;
                    }
                }
            </style>
        `;

        contentList.innerHTML = styles + html + paginationHtml;

        addPaginationHandlers();
    }

    function addPaginationHandlers() {
        const paginationLinks = contentList.querySelectorAll('.page-link[data-page]');
        if (paginationLinks.length > 0) {
            paginationLinks.forEach(link => {
                link.addEventListener('click', function (e) {
                    e.preventDefault();
                    const page = parseInt(this.dataset.page);
                    if (page >= 0 && page < totalPages) {
                        currentPage = page;

                        // Для режима "all" показываем правильный срез данных
                        if (currentType === "all") {
                            const startIndex = currentPage * pageSize;
                            const endIndex = startIndex + pageSize;
                            const pageContent = allContent.slice(startIndex, endIndex);
                            displayContent(pageContent, allContent.length);
                        } else {
                            loadContent();
                        }
                    }
                });
            });
        }
    }

    function getStatusBadge(status) {
        const statusName = status?.name || status;

        switch (statusName) {
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
        switch (type) {
            case 'post': return 'Пост';
            case 'blog': return 'Блог';
            case 'event': return 'Событие';
            case 'book': return 'Книга';
            default: return 'Контент';
        }
    }

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

    function loadContent() {
        contentList.innerHTML = `
            <div class="text-center text-muted py-5">
                <i class="fas fa-spinner fa-spin fa-2x mb-3" style="color: #0066cc;"></i>
                <p>Загрузка контента...</p>
            </div>`;

        currentType = typeFilter.value === "all" ? "all" : typeFilter.value.replace(/s$/, '');
        currentStatus = statusFilter.value;
        currentQuery = searchInput.value.trim();

        if (currentType === "all") {
            const types = ['post', 'blog', 'event', 'book'];
            const fetches = types.map(type =>
                fetch(`/api/users/history?id=${userId}&type=${type}&page=${currentPage}&size=${pageSize}` +
                    (currentStatus !== "all" ? `&status=${currentStatus}` : '') +
                    (currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : '')
                )
                    .then(res => {
                        if (!res.ok) throw new Error(`Ошибка загрузки: ${res.status}`);
                        return res.json();
                    })
            );

            Promise.all(fetches)
                .then(results => {
                    let combinedContent = [];
                    let totalElements = 0;

                    results.forEach((pageData, idx) => {
                        if (pageData.content && Array.isArray(pageData.content)) {
                            combinedContent = combinedContent.concat(pageData.content.map(item => ({
                                ...item,
                                type: types[idx]
                            })));
                        }
                        totalElements += pageData.totalElements || 0;
                    });

                    // Сохраняем результат даже если он пустой
                    allContent = combinedContent;
                    if (combinedContent.length === 0) {
                        totalPages = 1; // Минимум одна страница
                        displayContent([]);
                        return;
                    }

                    combinedContent.sort((a, b) => new Date(b.createdAt || b.publishedAt || 0) - new Date(a.createdAt || a.publishedAt || 0));

                    allContent = combinedContent;
                    totalPages = Math.ceil(totalElements / pageSize);
                    displayContent(allContent.slice(0, pageSize));
                })
                .catch(err => {
                    console.error("Ошибка при загрузке контента:", err);
                    contentList.innerHTML = `
                        <div class="text-center py-5">
                            <i class="fas fa-exclamation-triangle fa-3x mb-3 text-danger"></i>
                            <p class="text-danger">Ошибка загрузки контента.</p>
                            <p class="text-muted">Попробуйте обновить страницу</p>
                        </div>`;
                });

        } else {
            fetch(`/api/users/history?id=${userId}&type=${currentType}&page=${currentPage}&size=${pageSize}` +
                (currentStatus !== "all" ? `&status=${currentStatus}` : '') +
                (currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : '')
            )
                .then(res => {
                    if (!res.ok) throw new Error(`Ошибка загрузки: ${res.status}`);
                    return res.json();
                })
                .then(data => {
                    if (!data.content || !Array.isArray(data.content) || data.content.length === 0) {
                        allContent = [];
                        totalPages = 1; // Минимум одна страница
                        displayContent([]);
                        return;
                    }

                    allContent = data.content.map(item => ({ ...item, type: currentType }));
                    totalPages = Math.max(data.totalPages || 1, 1); // Минимум одна страница
                    displayContent(allContent);
                })
                .catch(err => {
                    console.error("Ошибка при загрузке контента:", err);
                    contentList.innerHTML = `
                        <div class="text-center py-5">
                            <i class="fas fa-exclamation-triangle fa-3x mb-3 text-danger"></i>
                            <p class="text-danger">Ошибка загрузки контента.</p>
                            <p class="text-muted">Попробуйте обновить страницу</p>
                        </div>`;
                });
        }
    }

    typeFilter.addEventListener("change", () => {
        currentPage = 0;
        loadContent();
    });

    statusFilter.addEventListener("change", () => {
        currentPage = 0;
        loadContent();
    });

    searchBtn.addEventListener("click", () => {
        currentPage = 0;
        loadContent();
    });

    searchInput.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            currentPage = 0;
            loadContent();
        }
    });

    loadContent();
});