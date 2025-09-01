document.addEventListener("DOMContentLoaded", function () {
    const contentList = document.getElementById("moderationContentList");
    const modal = new bootstrap.Modal(document.getElementById('contentModal'));
    const modalContent = document.getElementById('modalContent');
    const modalApproveBtn = document.getElementById('modalApproveBtn');
    const modalRejectBtn = document.getElementById('modalRejectBtn');
    const typeFilter = document.getElementById("moderationTypeFilter");
    const searchInput = document.getElementById("moderationSearch");
    const searchBtn = document.getElementById("moderationSearchBtn");
    const paginationContainer = document.getElementById("moderationPagination");
    let currentItem = null;
    let currentPage = 0;
    let currentSize = 5;
    let currentType = 'all';
    let currentQuery = '';
    let totalPages = 0;
    let totalElements = 0;

    function getCsrfToken() {
        return document.querySelector('meta[name="_csrf"]')?.getAttribute('content') || '';
    }

    function getCsrfHeader() {
        return document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || 'X-CSRF-TOKEN';
    }


    const noContent = {
        en: {
            actions: "No content for moderation",
            statusPending: "Pending",
            approve: "Approve",
            reject: "Reject",
            types: {
                post: "Post",
                blog: "Blog",
                event: "Event",
                book: "Book"
            }
        },
        ru: {
            actions: "Нет контента на модерации",
            statusPending: "Ожидает",
            approve: "Принять",
            reject: "Отклонить",
            types: {
                post: "Пост",
                blog: "Блог",
                event: "Событие",
                book: "Книга"
            }
        },
        ky: {
            actions: "Модерация үчүн контент жок",
            statusPending: "Күтүлүүдө",
            approve: "Кабыл алуу",
            reject: "Кабыл кылбоо",
            types: {
                post: "Пост",
                blog: "Блог",
                event: "Иш-чара",
                book: "Китеп"
            }
        }
    };

    function t(key) {
        return noContent[currentLocale]?.[key] || key;
    }

    function showLoading() {
        contentList.innerHTML = `
            <div class="text-center text-muted py-5">
                <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                <p>Загрузка контента...</p>
            </div>`;
    }

    function getTypeName(type) {
        return noContent[currentLocale]?.types?.[type] || type;
    }

    function getContentTitle(item, type) {
        switch (type) {
            case 'event':
                return item.eventTranslations && item.eventTranslations[0] && item.eventTranslations[0].title
                    ? item.eventTranslations[0].title
                    : 'Без заголовка';
            case 'blog':
                return item.blogTranslations && item.blogTranslations[0] && item.blogTranslations[0].title
                    ? item.blogTranslations[0].title
                    : item.title || 'Без заголовка';
            case 'post':
                return item.postTranslations && item.postTranslations[0] && item.postTranslations[0].title
                    ? item.postTranslations[0].title
                    : item.title || 'Без заголовка';
            case 'book':
                return item.name || 'Без заголовка';
            default:
                return item.title || 'Без заголовка';
        }
    }

    function getContentDescription(item, type) {
        switch (type) {
            case 'event':
                return item.eventTranslations && item.eventTranslations[0] && item.eventTranslations[0].description
                    ? item.eventTranslations[0].description
                    : 'Нет описания';
            case 'blog':
                return item.blogTranslations && item.blogTranslations[0] && item.blogTranslations[0].content
                    ? item.blogTranslations[0].content
                    : item.content || 'Нет контента';
            case 'post':
                return item.postTranslations && item.postTranslations[0] && item.postTranslations[0].content
                    ? item.postTranslations[0].content
                    : item.content || 'Нет контента';
            case 'book':
                return item.description || 'Нет описания';
            default:
                return item.content || 'Нет контента';
        }
    }

    function formatEventDateTime(startDate, endDate) {
        if (!startDate) return '';

        const start = new Date(startDate);
        const startFormatted = start.toLocaleDateString('ru-RU', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });

        if (endDate) {
            const end = new Date(endDate);
            const endFormatted = end.toLocaleDateString('ru-RU', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            });
            return `${startFormatted} - ${endFormatted}`;
        }

        return startFormatted;
    }

    function getEventLocation(item) {
        if (item.isOffline) {
            return item.address || 'Адрес не указан';
        } else {
            return item.url || 'Ссылка не указана';
        }
    }

    function showContentDetails(item, type) {
        currentItem = { ...item, type };

        const title = getContentTitle(item, type);
        const content = getContentDescription(item, type);
        const author = item.creator && item.creator.name || 'Неизвестен';
        const date = new Date(item.createdAt || Date.now()).toLocaleDateString('ru-RU');

        let additionalInfo = '';

        if (type === 'event') {
            const eventDateTime = formatEventDateTime(item.startDate, item.endDate);
            const location = getEventLocation(item);
            const locationType = item.isOffline ? 'Офлайн' : 'Онлайн';

            additionalInfo = `
                <div class="additional-info">
                    <div class="info-item">
                        <div class="info-label">Дата и время:</div>
                        <div class="info-value">${eventDateTime}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Тип:</div>
                        <div class="info-value">${locationType}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">${item.isOffline ? 'Адрес' : 'Ссылка'}:</div>
                        <div class="info-value">${location}</div>
                    </div>
                </div>
            `;
        } else if (type === 'book') {
            const infoItems = [];
            if (item.authors) infoItems.push({ label: 'Авторы', value: item.authors });
            if (item.isbn) infoItems.push({ label: 'ISBN', value: item.isbn });
            if (item.category && item.category.name) infoItems.push({ label: 'Категория', value: item.category.name });

            if (infoItems.length > 0) {
                additionalInfo = `
                    <div class="additional-info">
                        ${infoItems.map(info => `
                            <div class="info-item">
                                <div class="info-label">${info.label}:</div>
                                <div class="info-value">${info.value}</div>
                            </div>
                        `).join('')}
                    </div>
                `;
            }
        } else if (type === 'post' || type === 'blog') {
            if (item.postFiles && item.postFiles.length > 0) {
                additionalInfo = `
                    <div class="additional-info">
                        <div class="info-item">
                            <div class="info-label">Файлы:</div>
                            <div class="info-value">${item.postFiles.length} файл(ов)</div>
                        </div>
                    </div>
                `;
            }
        }

        modalContent.innerHTML = `
            <div class="mb-3">
                <img src="/api/files/${item.mainImageId || (item.mainImage && item.mainImage.id)}/view"
                     alt="Изображение" class="modal-image"
                     onerror="this.src='/static/images/img.png'">
            </div>
            
            <div class="content-detail-item">
                <div class="content-detail-label">Заголовок</div>
                <div class="content-detail-value">${title}</div>
            </div>

            <div class="content-detail-item">
                <div class="content-detail-label">Тип</div>
                <div class="content-detail-value">
                    <span class="badge bg-secondary">${getTypeName(type)}</span>
                </div>
            </div>

            <div class="content-detail-item">
                <div class="content-detail-label">Автор</div>
                <div class="content-detail-value">${author}</div>
            </div>

            <div class="content-detail-item">
                <div class="content-detail-label">Дата создания</div>
                <div class="content-detail-value">${date}</div>
            </div>

            ${additionalInfo}

            <div class="content-detail-item">
                <div class="content-detail-label">${type === 'book' ? 'Описание' : (type === 'event' ? 'Описание' : 'Контент')}</div>
                <div class="content-detail-value">
                    <div class="publication-text" id="modalPostContent">${content}</div>
                    <div class="show-more-btn" id="modalToggleButton" style="display: none;">
                        Показать полностью <i class="fas fa-chevron-down"></i>
                    </div>
                </div>
            </div>
        `;

        // Инициализируем кнопку "Показать еще"
        initializeToggleButton();

        modal.show();
    }

    function initializeToggleButton() {
        const postContent = document.getElementById('modalPostContent');
        const toggleBtn = document.getElementById('modalToggleButton');

        if (!postContent || !toggleBtn) return;

        // Сбрасываем предыдущие обработчики событий
        const newToggleBtn = toggleBtn.cloneNode(true);
        toggleBtn.parentNode.replaceChild(newToggleBtn, toggleBtn);

        // Проверяем, нужна ли кнопка "Показать еще"
        const maxHeight = 150;

        // Временно убираем ограничения для измерения реальной высоты
        postContent.style.maxHeight = 'none';
        postContent.style.overflow = 'visible';
        postContent.classList.remove('collapsed', 'expanded');

        const actualHeight = postContent.scrollHeight;

        if (actualHeight > maxHeight) {
            // Показываем кнопку и применяем ограничения
            postContent.classList.add('collapsed');
            postContent.style.maxHeight = '';
            postContent.style.overflow = '';
            newToggleBtn.style.display = 'inline-block';

            newToggleBtn.addEventListener('click', function() {
                const isCollapsed = postContent.classList.contains('collapsed');

                if (isCollapsed) {
                    postContent.classList.remove('collapsed');
                    postContent.classList.add('expanded');
                    newToggleBtn.innerHTML = 'Скрыть <i class="fas fa-chevron-up"></i>';
                    newToggleBtn.classList.add('expanded');
                } else {
                    postContent.classList.remove('expanded');
                    postContent.classList.add('collapsed');
                    newToggleBtn.innerHTML = 'Показать полностью <i class="fas fa-chevron-down"></i>';
                    newToggleBtn.classList.remove('expanded');

                    // Прокручиваем модальное окно к началу контента
                    const modalBody = postContent.closest('.modal-body');
                    if (modalBody) {
                        modalBody.scrollTop = postContent.offsetTop - modalBody.offsetTop - 20;
                    }
                }
            });
        } else {
            // Скрываем кнопку, если контент помещается
            newToggleBtn.style.display = 'none';
            postContent.classList.remove('collapsed', 'expanded');
            postContent.style.maxHeight = 'none';
            postContent.style.overflow = 'visible';
        }
    }

    function showContentDetailsById(id, type) {
        const endpoint = getApiEndpoint(type);
        const headers = {
            'Content-Type': 'application/json'
        };

        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        fetch(`${endpoint}/${id}`, {
            method: 'GET',
            headers: headers
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка: ${response.status}`);
                }
                return response.json();
            })
            .then(item => {
                showContentDetails(item, type);
            })
            .catch(error => {
                console.error('Ошибка при загрузке деталей:', error);
                showNotification('Ошибка при загрузке деталей контента', 'error');
            });
    }

    function getApiEndpoint(type) {
        const endpoints = {
            'post': '/api/users/posts',
            'blog': '/api/users/blogs',
            'event': '/api/users/events',
            'book': '/api/users/books'
        };
        return endpoints[type] || '/api/users/content';
    }

    function performModerationActionById(action, id, type) {
        currentItem = { id, type };
        performModerationAction(action);
    }

    function performModerationAction(action) {
        if (!currentItem) return;

        const url = `/api/admin/${action}/${currentItem.type}/${currentItem.id}`;

        const headers = {
            'Content-Type': 'application/json'
        };

        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        fetch(url, {
            method: 'POST',
            headers: headers
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка: ${response.status}`);
                }
                return response.text().then(text => {
                    try {
                        return text ? JSON.parse(text) : {};
                    } catch (e) {
                        return { message: 'Действие выполнено успешно' };
                    }
                });
            })
            .then(result => {
                modal.hide();

                const cardElement = document.querySelector(`[data-item-id="${currentItem.id}"][data-item-type="${currentItem.type}"]`);
                if (cardElement) {
                    cardElement.classList.add('removing');
                    setTimeout(() => {
                        cardElement.remove();
                        const remainingCards = contentList.querySelectorAll('.content-card');
                        if (remainingCards.length === 0 && totalElements > currentSize) {
                            if (currentPage > 0) {
                                currentPage--;
                            }
                            loadContent();
                        } else if (remainingCards.length === 0) {
                            showNoContent();
                        }
                    }, 300);
                }

                const actionMessage = action === 'approve' ? 'одобрен' : 'отклонён';
                showNotification(
                    result.message || `Контент ${actionMessage}`,
                    'success'
                );

                updateModerationCount();
            })
            .catch(error => {
                console.error('Ошибка при выполнении действия:', error);
                showNotification('Ошибка при выполнении действия', 'error');
            });
    }

    function showNotification(message, type) {
        const toastContainerId = 'toastContainer';

        let toastContainer = document.getElementById(toastContainerId);
        if (!toastContainer) {
            toastContainer = document.createElement('div');
            toastContainer.id = toastContainerId;
            toastContainer.className = 'position-fixed top-0 end-0 p-4';
            toastContainer.style.zIndex = '1080';
            document.body.appendChild(toastContainer);
        }

        const toastId = `toast-${Date.now()}`;
        const bgColor = type === 'success' ? 'rgba(40, 167, 69, 0.85)' : 'rgba(220, 53, 69, 0.85)';

        const toastHtml = `
        <div id="${toastId}" class="toast fade show shadow-lg" role="alert" aria-live="assertive" aria-atomic="true"
             style="
                 min-width: 320px;
                 max-width: 420px;
                 margin-bottom: 1rem;
                 background-color: ${bgColor};
                 color: #fff;
                 border: none;
                 border-radius: 12px;
                 backdrop-filter: blur(8px);
                 font-size: 1rem;
                 padding: 1rem 1.5rem;
                 transition: opacity 0.5s ease-in-out;
             ">
            <div class="d-flex justify-content-between align-items-center">
                <div class="toast-body">${message}</div>
                <button type="button" class="btn-close btn-close-white ms-3" data-bs-dismiss="toast" aria-label="Закрыть"></button>
            </div>
        </div>
        `;

        toastContainer.insertAdjacentHTML('beforeend', toastHtml);

        const toastElement = document.getElementById(toastId);
        const bsToast = new bootstrap.Toast(toastElement, { delay: 5000 });
        bsToast.show();

        toastElement.addEventListener('hidden.bs.toast', () => {
            toastElement.remove();
        });
    }

    function showNoContent() {
        contentList.innerHTML = `
            <div class="no-content">
                <svg width="120" height="120" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <rect x="12" y="14" width="40" height="36" rx="3" ry="3" fill="#eaeaea" stroke="#888" stroke-width="2"/>
                    <line x1="20" y1="20" x2="44" y2="20" stroke="#bbb" stroke-width="1.5"/>
                    <line x1="20" y1="26" x2="44" y2="26" stroke="#bbb" stroke-width="1.5"/>
                    <line x1="20" y1="32" x2="44" y2="32" stroke="#bbb" stroke-width="1.5"/>
                    <line x1="20" y1="38" x2="44" y2="38" stroke="#bbb" stroke-width="1.5"/>
                </svg>
                <p class="fs-5">${t('actions')}</p>
            </div>
        `;
        paginationContainer.style.display = 'none';
    }


    function createContentCard(item, type) {
        const title = getContentTitle(item, type);
        const author = item.creator && item.creator.name || 'Неизвестен';
        const date = new Date(item.createdAt || Date.now()).toLocaleDateString('ru-RU');
        const imageId = item.mainImageId || (item.mainImage && item.mainImage.id) || '';

        return `
            <div class="content-card" data-item-id="${item.id}" data-item-type="${type}">
                <div class="moderation-image-wrapper">
                    <img src="/api/files/${imageId}/view"
                         alt="${getTypeName(type)} Image"
                         class="moderation-image"
                         onclick="showContentDetailsById(${item.id}, '${type}')"
                         onerror="this.src='/static/images/img.png'">
                </div>
                
                
                <div class="content-info">
                    <div class="content-title" onclick="showContentDetailsById(${item.id}, '${type}')">${title}</div>
                    <div class="content-meta">${author} • ${date}</div>
                    <span class="badge bg-info me-2">${getTypeName(type)}</span>
                    <span class="status-badge status-pending">${t('statusPending')}</span>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-approve" onclick="performModerationActionById('approve', ${item.id}, '${type}')">
                        <i class="fas fa-check"></i> ${t('approve')}
                    </button>
                    <button class="btn btn-reject" onclick="performModerationActionById('reject', ${item.id}, '${type}')">
                        <i class="fas fa-times"></i> ${t('reject')}
                    </button>
                </div>
            </div>
        `;
    }

    function buildPagination(currentPageNum, totalPagesNum) {
        if (totalPagesNum <= 1) {
            paginationContainer.style.display = 'none';
            return;
        }

        paginationContainer.style.display = 'block';

        let pagination = paginationContainer.querySelector('.pagination');
        if (!pagination) {
            pagination = document.createElement('nav');
            pagination.innerHTML = '<ul class="pagination justify-content-center"></ul>';
            paginationContainer.appendChild(pagination);
            pagination = pagination.querySelector('.pagination');
        }

        pagination.innerHTML = '';

        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPageNum === 0 ? 'disabled' : ''}`;
        prevLi.innerHTML = `
        <a class="page-link" href="#" data-page="${currentPageNum - 1}" ${currentPageNum === 0 ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Previous">
            &laquo;
        </a>
    `;
        pagination.appendChild(prevLi);

        const maxVisiblePages = 7;
        let startPage = Math.max(0, currentPageNum - Math.floor(maxVisiblePages / 2));
        let endPage = Math.min(totalPagesNum - 1, startPage + maxVisiblePages - 1);

        if (endPage - startPage < maxVisiblePages - 1) {
            startPage = Math.max(0, endPage - maxVisiblePages + 1);
        }

        if (startPage > 0) {
            const firstLi = document.createElement('li');
            firstLi.className = 'page-item';
            firstLi.innerHTML = `<a class="page-link" href="#" data-page="0">1</a>`;
            pagination.appendChild(firstLi);

            if (startPage > 1) {
                const dotsLi = document.createElement('li');
                dotsLi.className = 'page-item disabled';
                dotsLi.innerHTML = '<span class="page-link">...</span>';
                pagination.appendChild(dotsLi);
            }
        }

        for (let i = startPage; i <= endPage; i++) {
            const li = document.createElement('li');
            if (i === currentPageNum) {
                li.className = 'page-item active';
                li.setAttribute('aria-current', 'page');
                li.innerHTML = `<a class="page-link" href="#">${i + 1}</a>`;
            } else {
                li.className = 'page-item';
                li.innerHTML = `<a class="page-link" href="#" data-page="${i}">${i + 1}</a>`;
            }
            pagination.appendChild(li);
        }

        if (endPage < totalPagesNum - 1) {
            if (endPage < totalPagesNum - 2) {
                const dotsLi = document.createElement('li');
                dotsLi.className = 'page-item disabled';
                dotsLi.innerHTML = '<span class="page-link">...</span>';
                pagination.appendChild(dotsLi);
            }

            const lastLi = document.createElement('li');
            lastLi.className = 'page-item';
            lastLi.innerHTML = `<a class="page-link" href="#" data-page="${totalPagesNum - 1}">${totalPagesNum}</a>`;
            pagination.appendChild(lastLi);
        }

        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPageNum === totalPagesNum - 1 ? 'disabled' : ''}`;
        nextLi.innerHTML = `
        <a class="page-link" href="#" data-page="${currentPageNum + 1}" ${currentPageNum === totalPagesNum - 1 ? 'tabindex="-1" aria-disabled="true"' : ''} aria-label="Next">
            &raquo;
        </a>
    `;
        pagination.appendChild(nextLi);

        pagination.addEventListener('click', function(e) {
            if (e.target.matches('a[data-page], a[data-page] *')) {
                e.preventDefault();
                const link = e.target.closest('a[data-page]');
                const page = parseInt(link.dataset.page);

                if (!link.closest('.page-item').classList.contains('disabled') &&
                    page >= 0 && page < totalPagesNum && page !== currentPageNum) {
                    currentPage = page;
                    loadContent();
                }
            }
        });
    }

    function loadContent() {
        showLoading();
        updateModerationCount();

        if (currentType === 'all') {
            loadAllContent();
        } else {
            const type = currentType === 'posts' ? 'post' : currentType;
            loadSpecificContent(type);
        }
    }

    function loadAllContent() {
        const types = ['post', 'blog', 'event', 'book'];

        const promises = types.map(type => loadTypeContent(type, 0, 1000));

        Promise.all(promises)
            .then(results => {
                let allItems = [];

                results.forEach((result, index) => {
                    if (result && result.content && Array.isArray(result.content)) {
                        result.content.forEach(item => {
                            allItems.push({ item, type: types[index] });
                        });
                    }
                });

                allItems.sort((a, b) => {
                    const dateA = new Date(a.item.createdAt || 0);
                    const dateB = new Date(b.item.createdAt || 0);
                    return dateB - dateA;
                });

                totalElements = allItems.length;
                totalPages = Math.ceil(totalElements / currentSize);

                if (currentPage >= totalPages && totalPages > 0) {
                    currentPage = totalPages - 1;
                }

                const startIndex = currentPage * currentSize;
                const endIndex = startIndex + currentSize;
                const pageItems = allItems.slice(startIndex, endIndex);

                if (pageItems.length === 0) {
                    showNoContent();
                } else {
                    let html = '';
                    pageItems.forEach(({ item, type }) => {
                        html += createContentCard(item, type);
                    });
                    contentList.innerHTML = html;
                    buildPagination(currentPage, totalPages);
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
                paginationContainer.style.display = 'none';
            });
    }

    function loadSpecificContent(type) {
        loadTypeContent(type, currentPage, currentSize)
            .then(result => {
                if (!result || !result.content || !Array.isArray(result.content)) {
                    showNoContent();
                    return;
                }

                totalPages = result.totalPages || 0;
                totalElements = result.totalElements || result.content.length;

                if (currentPage >= totalPages && totalPages > 0) {
                    currentPage = totalPages - 1;
                    loadSpecificContent(type);
                    return;
                }

                if (result.content.length === 0) {
                    showNoContent();
                } else {
                    let html = '';
                    result.content.forEach(item => {
                        html += createContentCard(item, type);
                    });
                    contentList.innerHTML = html;
                    buildPagination(currentPage, totalPages);
                }
            })
            .catch(error => {
                console.error(`Ошибка при загрузке ${type} контента:`, error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
                paginationContainer.style.display = 'none';
            });
    }

    function loadTypeContent(type, page, size) {
        const url = `/api/users/moder?type=${type}&page=${page}&size=${size}${currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : ''}`;

        const headers = {
            'Content-Type': 'application/json'
        };

        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        return fetch(url, {
            method: 'GET',
            headers: headers
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка загрузки: ${response.status}`);
                }
                return response.json();
            })
            .catch(error => {
                console.warn(`Ошибка загрузки для ${type}:`, error);
                return { content: [], totalPages: 0, totalElements: 0 };
            });
    }

    function performSearch() {
        currentQuery = searchInput.value.trim();
        currentPage = 0;
        loadContent();
    }

    // Обработчики событий
    typeFilter.addEventListener('change', (e) => {
        currentType = e.target.value;
        currentPage = 0;
        loadContent();
    });

    searchBtn.addEventListener('click', performSearch);

    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            performSearch();
        }
    });

    let searchTimeout;
    searchInput.addEventListener('input', () => {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(() => {
            if (searchInput.value.trim() !== currentQuery) {
                performSearch();
            }
        }, 500);
    });

    window.showContentDetailsById = showContentDetailsById;
    window.performModerationActionById = performModerationActionById;

    modalApproveBtn.addEventListener('click', () => performModerationAction('approve'));
    modalRejectBtn.addEventListener('click', () => performModerationAction('reject'));

    loadContent();
});

function updateModerationCount() {
    fetch('/api/users/count')
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при получении данных');
            }
            return response.text();
        })
        .then(countStr => {
            const badge = document.getElementById('moderationCount');
            if (badge) {
                badge.textContent = countStr;
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}