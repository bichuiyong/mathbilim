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

    const styles = `
        <style>
        .fade-in {
            animation: fadeIn 0.3s ease-in;
        }
        
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
        
        .content-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            transition: all 0.3s ease;
            margin-bottom: 1.5rem;
            overflow: hidden;
        }
        
        .content-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.12);
        }
        
        .content-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            transition: transform 0.3s ease;
        }
        
        .content-card:hover .content-image {
            transform: scale(1.05);
        }
        
        .content-type-badge {
            font-size: 0.75rem;
            padding: 0.25rem 0.5rem;
            border-radius: 12px;
        }
        
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
        
        .no-content {
            padding: 3rem 1rem;
            color: #6c757d;
        }
        
        .loading-spinner {
            color: #0066cc;
        }
        
        .card {
            transition: all 0.3s ease;
        }
        
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }
        
        .badge {
            font-weight: 500;
        }
        
        .content-grid {
            display: grid;
            gap: 1.5rem;
        }
        
        .content-card-header {
            display: flex;
            align-items: center;
            padding: 1rem;
            gap: 1rem;
        }
        
        .content-avatar {
            width: 60px;
            height: 60px;
            border-radius: 8px;
            overflow: hidden;
            flex-shrink: 0;
        }
        
        .content-info {
            flex: 1;
            min-width: 0;
        }
        
        .content-title {
            margin: 0 0 0.25rem 0;
            font-weight: 600;
            color: #333;
        }
        
        .content-meta {
            font-size: 0.875rem;
            color: #6c757d;
        }
        
        .content-author {
            font-weight: 500;
            margin-right: 0.5rem;
        }
        
        .content-badges {
            padding: 0 1rem 1rem 1rem;
            display: flex;
            gap: 0.5rem;
            flex-wrap: wrap;
        }
        </style>
    `;
    document.head.insertAdjacentHTML('beforeend', styles);

    const contentList = document.getElementById("userContentList");
    const paginationNav = document.getElementById("contentPagination");
    const paginationUl = paginationNav ? paginationNav.querySelector("ul.pagination") : null;

    if (contentList && paginationNav && paginationUl) {
        console.log("Инициализация контента пользователя");
        initUserContent(userId, contentList, paginationNav, paginationUl);
    }

    const historyContentList = document.getElementById("historyContentList");
    if (historyContentList) {
        initHistoryContent(userId, historyContentList);
    }

    function initUserContent(userId, contentList, paginationNav, paginationUl) {
        const types = ['post', 'blog', 'event', 'book'];
        const pageSize = 5;

        let currentPage = 0;
        let currentQuery = '';
        let currentFilter = 'all';

        function renderPagination(totalPages, page) {
            console.log(`Отрисовка пагинации: всего страниц ${totalPages}, текущая страница ${page + 1}`);

            paginationUl.innerHTML = '';

            if (totalPages <= 1) {
                paginationNav.style.display = 'none';
                return;
            }

            paginationNav.style.display = 'block';

            // Кнопка "Назад"
            const prevLi = document.createElement('li');
            prevLi.className = `page-item ${page === 0 ? 'disabled' : ''}`;
            const prevLink = document.createElement('a');
            prevLink.className = 'page-link';
            prevLink.href = '#';
            prevLink.innerHTML = '&laquo;';
            prevLink.setAttribute('aria-label', 'Previous');

            prevLink.addEventListener('click', (e) => {
                e.preventDefault();
                if (page > 0) {
                    console.log(`Нажата кнопка "Назад", загрузка страницы ${page}`);
                    loadContentPage(page - 1);
                }
            });

            prevLi.appendChild(prevLink);
            paginationUl.appendChild(prevLi);

            const maxPagesToShow = 7;
            let startPage = Math.max(0, page - Math.floor(maxPagesToShow / 2));
            let endPage = startPage + maxPagesToShow - 1;

            if (endPage >= totalPages) {
                endPage = totalPages - 1;
                startPage = Math.max(0, endPage - maxPagesToShow + 1);
            }

            if (startPage > 0) {
                const firstLi = document.createElement('li');
                firstLi.className = 'page-item';
                const firstLink = document.createElement('a');
                firstLink.className = 'page-link';
                firstLink.href = '#';
                firstLink.textContent = '1';

                firstLink.addEventListener('click', (e) => {
                    e.preventDefault();
                    console.log(`Нажата кнопка страницы 1`);
                    loadContentPage(0);
                });

                firstLi.appendChild(firstLink);
                paginationUl.appendChild(firstLi);

                if (startPage > 1) {
                    const ellipsisLi = document.createElement('li');
                    ellipsisLi.className = 'page-item disabled';
                    const ellipsisSpan = document.createElement('span');
                    ellipsisSpan.className = 'page-link';
                    ellipsisSpan.textContent = '...';
                    ellipsisLi.appendChild(ellipsisSpan);
                    paginationUl.appendChild(ellipsisLi);
                }
            }

            for (let i = startPage; i <= endPage; i++) {
                const li = document.createElement('li');
                li.className = `page-item ${i === page ? 'active' : ''}`;
                const link = document.createElement('a');
                link.className = 'page-link';
                link.href = '#';
                link.textContent = i + 1;

                if (i !== page) {
                    link.addEventListener('click', (e) => {
                        e.preventDefault();
                        console.log(`Нажата кнопка страницы ${i + 1}`);
                        loadContentPage(i);
                    });
                }

                li.appendChild(link);
                paginationUl.appendChild(li);
            }

            // Многоточие и последняя страница
            if (endPage < totalPages - 1) {
                if (endPage < totalPages - 2) {
                    const ellipsisLi = document.createElement('li');
                    ellipsisLi.className = 'page-item disabled';
                    const ellipsisSpan = document.createElement('span');
                    ellipsisSpan.className = 'page-link';
                    ellipsisSpan.textContent = '...';
                    ellipsisLi.appendChild(ellipsisSpan);
                    paginationUl.appendChild(ellipsisLi);
                }

                const lastLi = document.createElement('li');
                lastLi.className = 'page-item';
                const lastLink = document.createElement('a');
                lastLink.className = 'page-link';
                lastLink.href = '#';
                lastLink.textContent = totalPages;

                lastLink.addEventListener('click', (e) => {
                    e.preventDefault();
                    loadContentPage(totalPages - 1);
                });

                lastLi.appendChild(lastLink);
                paginationUl.appendChild(lastLi);
            }

            const nextLi = document.createElement('li');
            nextLi.className = `page-item ${page === totalPages - 1 ? 'disabled' : ''}`;
            const nextLink = document.createElement('a');
            nextLink.className = 'page-link';
            nextLink.href = '#';
            nextLink.innerHTML = '&raquo;';
            nextLink.setAttribute('aria-label', 'Next');

            nextLink.addEventListener('click', (e) => {
                e.preventDefault();
                if (page < totalPages - 1) {
                    console.log(`Нажата кнопка "Вперёд", загрузка страницы ${page + 2}`);
                    loadContentPage(page + 1);
                }
            });

            nextLi.appendChild(nextLink);
            paginationUl.appendChild(nextLi);
        }

        function getContentUrl(type, id) {
            console.log(`🔗 Формирование URL для типа "${type}" с ID ${id}`);

            const urlMap = {
                post: `/posts/${id}`,
                blog: `/blog/${id}`,
                event: `/events/${id}`,
                book: `/books/${id}`
            };

            const url = urlMap[type] || `/${type}s/${id}`;
            console.log(`✅ Сформирован URL: ${url}`);

            return url;
        }

        function loadContentPage(page) {
            currentPage = page;

            contentList.innerHTML = `
                <div class="text-center text-muted py-5">
                    <i class="fas fa-spinner fa-spin fa-2x mb-3 loading-spinner"></i>
                    <p>Загрузка контента...</p>
                </div>`;



            if (currentFilter === 'all') {
                loadAllContentTypes(page);
            } else {
                loadSpecificContentType(page);
            }
        }

        function getContentTitle(item, type) {

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
                case 'book':
                    title = item.name || item.title || 'Без заголовка';
                    break;
                default:
                    title = item.title || 'Без заголовка';
            }

            console.log(`📝 Итоговый title для ${type}:`, title);
            return title;
        }

        function loadAllContentTypes(page) {
            const urls = types.map(type =>
                `/api/users/content?type=${type}&creatorId=${userId}&page=0&size=1000${currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : ''}`
            );

        console.log('📡 Отправка запросов для всех типов контента:', urls);

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
                    console.log('📦 Получены результаты для всех типов:', results);

                    let allItems = [];

                    results.forEach((result, index) => {
                        const type = types[index];
                        console.log(`📋 Обработка результатов для типа "${type}":`, result);

                        if (result.content && Array.isArray(result.content) && result.content.length > 0) {

                            result.content.forEach((item, itemIndex) => {
                                console.log(`🔍 Элемент ${itemIndex + 1} типа "${type}":`, item);

                                allItems.push({
                                    ...item,
                                    contentType: type,
                                    createdAt: new Date(item.createdAt || Date.now())
                                });
                            });
                        } else {
                            console.log(`❌ Нет контента для типа "${type}"`);
                        }
                    });

                console.log('🗂️ Все собранные элементы:', allItems);

                    allItems.sort((a, b) => b.createdAt - a.createdAt);

                    const totalItems = allItems.length;
                    const totalPages = Math.ceil(totalItems / pageSize);
                    const startIndex = page * pageSize;
                    const endIndex = startIndex + pageSize;
                    const pageItems = allItems.slice(startIndex, endIndex);

                console.log(`📊 Пагинация: всего элементов ${totalItems}, страниц ${totalPages}, показываем элементы ${startIndex}-${endIndex}`);

                    if (pageItems.length > 0) {
                        let html = '<div class="row g-3 fade-in">';

                        pageItems.forEach((item, index) => {
                            const imageId = item.mainImageId || 'default-image-id';
                            const type = item.contentType;

                            console.log(`🎨 Рендер элемента ${index + 1}:`, {
                                type: type,
                                item: item,
                                imageId: imageId
                            });

                            // Используем правильную функцию получения title
                            const title = getContentTitle(item, type);

                        // Формируем URL с помощью новой функции
                        const contentUrl = getContentUrl(type, item.id);

                        html += `
                            <div class="col-md-4 col-6 mb-3">
                                <div class="card h-100 border-0 shadow-sm">
                                    <a href="${contentUrl}" class="text-decoration-none">
                                        <div class="position-relative overflow-hidden rounded-top">
                                            <img src="/api/files/${imageId}/view" alt="${type} Image" class="card-img-top" style="height: 200px; object-fit: cover;" loading="lazy">
                                            <div class="position-absolute top-0 end-0 m-2">
                                                <span class="badge bg-primary">${getTypeLabel(type)}</span>
                                            </div>
                                        </div>
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-truncate mb-1">${title}</h6>
                                            <small class="text-muted">${formatDate(item.createdAt)}</small>
                                        </div>
                                    </a>
                                </div>
                            </div>`;
                    });

                        html += '</div>';
                        contentList.innerHTML = html;

                        // Анимация появления с задержкой для лучшего эффекта
                        setTimeout(() => {
                            const contentDiv = contentList.querySelector('div.row');
                            if (contentDiv) {
                                contentDiv.style.opacity = '1';
                            }
                        }, 50);

                        renderPagination(totalPages, page);
                    } else {
                        console.log('❌ Нет элементов для отображения');
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

        console.log(`📡 Запрос для конкретного типа "${apiType}":`, url);

            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        console.error(`Ошибка загрузки: ${response.status} при запросе ${url}`);
                        throw new Error(`Ошибка загрузки: ${response.status}`);
                    }
                    return response.json();
                })
                .then(result => {
                    console.log(`📦 Результат для типа "${apiType}":`, result);

                    if (result.content && Array.isArray(result.content) && result.content.length > 0) {
                        console.log(`✅ Загружено ${result.content.length} элементов типа ${apiType}.`);

                        let html = '<div class="row g-3 fade-in">';

                        result.content.forEach((item, index) => {
                            console.log(`🔍 Элемент ${index + 1} типа "${apiType}":`, item);

                            const imageId = item.mainImageId || 'default-image-id';

                            // Используем правильную функцию получения title
                            const title = getContentTitle(item, apiType);

                        // Формируем URL с помощью новой функции
                        const contentUrl = getContentUrl(apiType, item.id);

                        html += `
                            <div class="col-md-4 col-6 mb-3">
                                <div class="card h-100 border-0 shadow-sm">
                                    <a href="${contentUrl}" class="text-decoration-none">
                                        <div class="position-relative overflow-hidden rounded-top">
                                            <img src="/api/files/${imageId}/view" alt="${apiType} Image" class="card-img-top" style="height: 200px; object-fit: cover;" loading="lazy">
                                        </div>
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-truncate mb-1">${title}</h6>
                                            <small class="text-muted">${formatDate(item.createdAt)}</small>
                                        </div>
                                    </a>
                                </div>
                            </div>`;
                    });

                        html += '</div>';
                        contentList.innerHTML = html;

                        // Анимация появления с задержкой для лучшего эффекта
                        setTimeout(() => {
                            const contentDiv = contentList.querySelector('div.row');
                            if (contentDiv) {
                                contentDiv.style.opacity = '1';
                            }
                        }, 50);

                        renderPagination(result.totalPages || 1, page);
                    } else {
                        showNoContentMessage("Нет контента по выбранному фильтру и запросу.");
                    }
                })
                .catch(error => {
                    showErrorMessage();
                });
        }


        function showNoContentMessage(message = "Пока нет ни одного поста, блога, события или книги.") {
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
    }

    // Инициализация истории контента
    function initHistoryContent(userId, contentList) {
        const typeFilter = document.getElementById("historyTypeFilter");
        const statusFilter = document.getElementById("historyStatusFilter");
        const searchInput = document.getElementById("historySearch");
        const searchBtn = document.getElementById("historySearchBtn");

        let currentPage = 0;
        const pageSize = 5;
        let totalPages = 0;
        let allContent = [];
        let totalElements = 0;

        let currentType = "all";
        let currentStatus = "";
        let currentQuery = "";

        function createPagination(totalItems) {
            totalPages = Math.ceil(Math.max(totalItems || 0, 1) / pageSize);

            if (totalPages <= 1) {
                return ''; // Не показываем пагинацию если только одна страница
            }

            let paginationHtml = '<nav aria-label="Page navigation" class="mt-4"><ul class="pagination justify-content-center">';

            // Кнопка "Назад"
            paginationHtml += `<li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
        <a class="page-link" href="#" data-page="${currentPage - 1}" ${currentPage === 0 ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Previous">
            &laquo;
        </a>
    </li>`;

            const maxPagesToShow = 7;
            let startPage = Math.max(0, currentPage - Math.floor(maxPagesToShow / 2));
            let endPage = startPage + maxPagesToShow - 1;

            if (endPage >= totalPages) {
                endPage = totalPages - 1;
                startPage = Math.max(0, endPage - maxPagesToShow + 1);
            }

            // Первая страница и многоточие (если нужно)
            if (startPage > 0) {
                paginationHtml += `<li class="page-item">
            <a class="page-link" href="#" data-page="0">1</a>
        </li>`;

                if (startPage > 1) {
                    paginationHtml += `<li class="page-item disabled">
                <span class="page-link">...</span>
            </li>`;
                }
            }

            for (let i = startPage; i <= endPage; i++) {
                if (i === currentPage) {
                    paginationHtml += `<li class="page-item active" aria-current="page">
                <a class="page-link" href="#">${i + 1}</a>
            </li>`;
                } else {
                    paginationHtml += `<li class="page-item">
                <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
            </li>`;
                }
            }

            if (endPage < totalPages - 1) {
                if (endPage < totalPages - 2) {
                    paginationHtml += `<li class="page-item disabled">
                <span class="page-link">...</span>
            </li>`;
                }

                paginationHtml += `<li class="page-item">
            <a class="page-link" href="#" data-page="${totalPages - 1}">${totalPages}</a>
        </li>`;
            }

            paginationHtml += `<li class="page-item ${currentPage >= totalPages - 1 ? 'disabled' : ''}">
        <a class="page-link" href="#" data-page="${currentPage + 1}" ${currentPage >= totalPages - 1 ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Next">
            &raquo;
        </a>
    </li>`;

            paginationHtml += '</ul></nav>';
            return paginationHtml;
        }

        function displayContent(content, totalElementsCount = null) {
            const totalForPagination = totalElementsCount !== null ? totalElementsCount : totalElements;

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

            console.log(`📋 История - элемент типа "${item.type}":`, item);

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
                            ${getStatusBadge(item.status, item.deleted)}
                        </div>
                    </div>`;
            });
            html += '</div>';

            const paginationHtml = createPagination(totalForPagination);

            contentList.innerHTML = html + paginationHtml;

            addPaginationHandlers();
        }

        function addPaginationHandlers() {
            const paginationLinks = contentList.querySelectorAll('.page-link[data-page]');
            if (paginationLinks.length > 0) {
                paginationLinks.forEach(link => {
                    link.addEventListener('click', function (e) {
                        e.preventDefault();
                        const page = parseInt(this.dataset.page);

                        // Проверяем валидность страницы
                        if (isNaN(page) || page < 0 || page >= totalPages) {
                            return;
                        }

                        currentPage = page;

                        // Для режима "all" показываем правильный срез данных
                        if (currentType === "all") {
                            const startIndex = currentPage * pageSize;
                            const endIndex = startIndex + pageSize;
                            const pageContent = allContent.slice(startIndex, endIndex);
                            displayContent(pageContent, totalElements);
                        } else {
                            loadContent();
                        }
                    });
                });
            }
        }

        function getStatusBadge(status, deleted = false) {
            if (deleted) {
                return '<span class="badge bg-dark">Удалён</span>';
            }
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
            console.log(`🎯 История - получение title для типа "${type}":`, item);

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
                case 'book':
                    title = item.name || item.title || 'Без заголовка';
                    break;
                default:
                    title = item.title || 'Без заголовка';
            }

            console.log(`📝 История - итоговый title для ${type}:`, title);
            return title;
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

            console.log(`🔄 История - загрузка контента: тип="${currentType}", статус="${currentStatus}", запрос="${currentQuery}"`);

            if (currentType === "all") {
                // Для режима "all" загружаем все данные только один раз
                const types = ['post', 'blog', 'event', 'book'];
                const fetches = types.map(type => {
                    // Загружаем максимальное количество данных для правильной пагинации
                    const url = `/api/users/history?id=${userId}&type=${type}&page=0&size=1000` +
                        (currentStatus !== "all" ? `&status=${currentStatus}` : '') +
                        (currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : '');

                    console.log(`📡 История - запрос для типа "${type}":`, url);

                    return fetch(url)
                        .then(res => {
                            if (!res.ok) throw new Error(`Ошибка загрузки: ${res.status}`);
                            return res.json();
                        })
                        .then(data => ({ type, data }));
                });

                Promise.all(fetches)
                    .then(results => {
                        console.log(`📦 История - результаты для всех типов:`, results);

                        let combinedContent = [];
                        totalElements = 0;

                        results.forEach(result => {
                            const { type, data } = result;
                            console.log(`📋 История - результат для типа "${type}":`, data);

                            if (data.content && Array.isArray(data.content)) {
                                console.log(`✅ История - найдено ${data.content.length} элементов типа "${type}"`);

                                const contentWithType = data.content.map(item => ({
                                    ...item,
                                    type: type
                                }));

                                combinedContent = combinedContent.concat(contentWithType);
                                totalElements += data.content.length;
                            }
                        });

                        console.log(`🗂️ История - объединенный контент (${combinedContent.length} элементов):`, combinedContent);

                        // Сортируем по дате
                        combinedContent.sort((a, b) => {
                            const dateA = new Date(a.createdAt || a.publishedAt || 0);
                            const dateB = new Date(b.createdAt || b.publishedAt || 0);
                            return dateB - dateA;
                        });

                        allContent = combinedContent;

                        // Рассчитываем общее количество страниц
                        totalPages = Math.ceil(totalElements / pageSize);
                        if (totalPages < 1) totalPages = 1;

                        // Показываем первую страницу данных
                        const startIndex = currentPage * pageSize;
                        const endIndex = startIndex + pageSize;
                        const pageContent = allContent.slice(startIndex, endIndex);

                        console.log(`📄 Показ страницы ${currentPage + 1} из ${totalPages} (элементы ${startIndex}-${endIndex-1})`);

                        displayContent(pageContent, totalElements);
                    })
                    .catch(err => {
                        console.error("История - ошибка при загрузке контента:", err);
                        contentList.innerHTML = `
                            <div class="text-center py-5">
                                <i class="fas fa-exclamation-triangle fa-3x mb-3 text-danger"></i>
                                <p class="text-danger">Ошибка загрузки контента.</p>
                                <p class="text-muted">Попробуйте обновить страницу</p>
                            </div>`;
                    });

            } else {
                // Для конкретных типов используем серверную пагинацию
                const url = `/api/users/history?id=${userId}&type=${currentType}&page=${currentPage}&size=${pageSize}` +
                    (currentStatus !== "all" ? `&status=${currentStatus}` : '') +
                    (currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : '');


                fetch(url)
                    .then(res => {
                        if (!res.ok) throw new Error(`Ошибка загрузки: ${res.status}`);
                        return res.json();
                    })
                    .then(data => {
                        console.log(`📦 История - результат для типа "${currentType}":`, data);

                        totalElements = data.totalElements || 0;
                        totalPages = Math.max(data.totalPages || 1, 1);

                        if (!data.content || !Array.isArray(data.content) || data.content.length === 0) {
                            allContent = [];
                            displayContent([]);
                            return;
                        }

                    console.log(`✅ История - найдено ${data.content.length} элементов`);
                    data.content.forEach((item, index) => {
                        console.log(`🔍 История - элемент ${index + 1}:`, item);
                    });

                        allContent = data.content.map(item => ({ ...item, type: currentType }));
                        displayContent(allContent, totalElements);
                    })
                    .catch(err => {
                        console.error("История - ошибка при загрузке контента:", err);
                        contentList.innerHTML = `
                            <div class="text-center py-5">
                                <i class="fas fa-exclamation-triangle fa-3x mb-3 text-danger"></i>
                                <p class="text-danger">Ошибка загрузки контента.</p>
                                <p class="text-muted">Попробуйте обновить страницу</p>
                            </div>`;
                    });
            }
        }

        if (typeFilter) {
            typeFilter.addEventListener("change", () => {
                currentPage = 0;
                loadContent();
            });
        }

        if (statusFilter) {
            statusFilter.addEventListener("change", () => {
                currentPage = 0;
                loadContent();
            });
        }

        if (searchBtn) {
            searchBtn.addEventListener("click", () => {
                currentPage = 0;
                loadContent();
            });
        }

        if (searchInput) {
            searchInput.addEventListener("keypress", (e) => {
                if (e.key === "Enter") {
                    currentPage = 0;
                    loadContent();
                }
            });
        }

        loadContent();
    }
});