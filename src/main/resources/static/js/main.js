document.addEventListener("DOMContentLoaded", () => {
    fetchAndRender('/api/news/main', 'news-container', 'новостей', '📰');
    fetchAndRender('/api/posts/main', 'post-container', 'публикаций', '📝');
    fetchAndRenderOlympiads('/api/olymp/main', 'eventsScroll');

    fetch('/api/blog/main')
        .then(res => res.json())
        .then(renderBlogs)
        .catch(err => {
            console.error('Ошибка загрузки блогов:', err);
            hideContainer(document.querySelector('.blog-masonry-grid'));
        });
});

function fetchAndRender(endpoint, containerId, contentType, icon) {
    const container = document.getElementById(containerId);
    if (!container) return;

    function truncateText(text, maxLength = 20) {
        if (!text) return '';
        if (text.length <= maxLength) return text;

        const truncated = text.substring(0, maxLength);
        const lastSpaceIndex = truncated.lastIndexOf(' ');

        if (lastSpaceIndex > 0 && lastSpaceIndex > maxLength * 0.8) {
            return truncated.substring(0, lastSpaceIndex) + '...';
        }

        return truncated + '...';
    }

    const parentSection = container.closest('.col-lg-4');
    if (parentSection) {
        parentSection.style.display = 'block';
    }
    container.style.display = 'block';
    container.innerHTML = `
        <div class="loading-pulse">
            <div class="pulse-dot"></div>
            <div class="pulse-dot"></div>
            <div class="pulse-dot"></div>
        </div>
    `;

    fetch(endpoint)
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка загрузки с ${endpoint}`);
            return res.json();
        })
        .then(data => {
            container.innerHTML = '';

            if (!data || !Array.isArray(data) || data.length === 0) {
                hideContainer(container);
                return;
            }

            const parentSection = container.closest('.col-lg-4');
            if (parentSection) {
                parentSection.style.display = 'block';
            }
            container.style.display = 'block';

            const contentWrapper = document.createElement('div');
            contentWrapper.className = 'modern-content-wrapper';
            contentWrapper.innerHTML = `
                <style>
                    .modern-content-wrapper {
                        border-radius: 16px;
                        padding: 20px;
                        border: 1px solid #bfdbfe;
                        box-shadow: 0 2px 8px rgba(37, 99, 235, 0.08);
                    }
                    
                    .content-item {
                        display: block;
                        text-decoration: none;
                        color: inherit;
                        padding: 16px 0;
                        border-bottom: 1px solid #f1f4f8;
                        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
                        position: relative;
                        overflow: hidden;
                    }
                    
                    .content-item:last-child {
                        border-bottom: none;
                    }
                    
                    .content-item:hover {
                        color: #2563eb;
                        transform: translateX(8px);
                        background: linear-gradient(90deg, rgba(37, 99, 235, 0.04) 0%, transparent 100%);
                    }
                    
                    .content-item::before {
                        content: '';
                        position: absolute;
                        left: -100%;
                        top: 0;
                        width: 4px;
                        height: 100%;
                        background: linear-gradient(180deg, #2563eb, #1d4ed8);
                        transition: left 0.3s ease;
                    }
                    
                    .content-item:hover::before {
                        left: 0;
                    }
                    
                    .item-title {
                        font-weight: 600;
                        font-size: 15px;
                        line-height: 1.4;
                        margin: 0 0 4px 0;
                        color: #1f2937;
                        transition: color 0.3s ease;
                    }
                    
                    .item-description {
                        font-size: 13px;
                        color: #6b7280;
                        line-height: 1.4;
                        margin: 0 0 8px 0;
                    }
                    
                    .item-meta {
                        display: flex;
                        align-items: center;
                        gap: 12px;
                        font-size: 12px;
                        color: #9ca3af;
                    }
                    
                    .meta-badge {
                        background: #e0f2fe;
                        color: #0369a1;
                        padding: 2px 8px;
                        border-radius: 12px;
                        font-size: 11px;
                        font-weight: 500;
                    }
                    
                    .loading-pulse {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        gap: 8px;
                        padding: 30px;
                    }
                    
                    .pulse-dot {
                        width: 8px;
                        height: 8px;
                        background: #2563eb;
                        border-radius: 50%;
                        animation: pulse 1.4s infinite ease-in-out;
                    }
                    
                    .pulse-dot:nth-child(2) { animation-delay: -0.2s; }
                    .pulse-dot:nth-child(3) { animation-delay: -0.4s; }
                    
                    @keyframes pulse {
                        0%, 80%, 100% { transform: scale(0.8); opacity: 0.5; }
                        40% { transform: scale(1.2); opacity: 1; }
                    }
                    
                    .view-all-section {
                        margin-top: 20px;
                        text-align: center;
                    }
                    
                    .view-all-btn {
                        display: inline-flex;
                        align-items: center;
                        gap: 8px;
                        padding: 10px 20px;
                        background: linear-gradient(135deg, #2563eb 0%, #1d4ed8 100%);
                        color: white;
                        text-decoration: none;
                        border-radius: 25px;
                        font-size: 14px;
                        font-weight: 500;
                        transition: all 0.3s ease;
                        box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
                    }
                    
                    .view-all-btn:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 8px 20px rgba(37, 99, 235, 0.4);
                        color: white;
                    }
                    
                    .arrow-icon {
                        transition: transform 0.3s ease;
                    }
                    
                    .view-all-btn:hover .arrow-icon {
                        transform: translateX(4px);
                    }
                </style>
            `;

            data.forEach((item, index) => {
                const fullTitle =
                    item.newsTranslations?.[0]?.title ||
                    item.postTranslations?.[0]?.title ||
                    item.title || 'Без заголовка';

                const fullContent =
                    item.newsTranslations?.[0]?.content ||
                    item.postTranslations?.[0]?.content ||
                    item.content ||
                    item.newsTranslations?.[0]?.description ||
                    item.postTranslations?.[0]?.description ||
                    item.description || '';

                const title = truncateText(fullTitle, 20);
                const description = truncateText(fullContent.replace(/<[^>]*>/g, ''), 30); // Убираем HTML теги

                const dateStr = item.formattedDate || new Date(item.createdAt).toLocaleDateString('ru-RU');
                const views = item.viewCount ?? 0;
                const linkUrl = contentType === 'новостей' ? `/news/${item.id}` : `/posts/${item.id}`;

                const contentItem = document.createElement('a');
                contentItem.href = linkUrl;
                contentItem.className = 'content-item';
                contentItem.innerHTML = `
                    <h4 class="item-title">${title}</h4>
                    ${description ? `<p class="item-description">${description}</p>` : ''}
                    <div class="item-meta">
                        <span>${dateStr}</span>
                        <span class="meta-badge">${views} ${views === 1 ? 'просмотр' : 'просмотров'}</span>
                    </div>
                `;

                contentItem.style.opacity = '0';
                contentItem.style.transform = 'translateY(20px)';

                contentWrapper.appendChild(contentItem);

                setTimeout(() => {
                    contentItem.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
                    contentItem.style.opacity = '1';
                    contentItem.style.transform = 'translateY(0)';
                }, index * 100);
            });

            container.appendChild(contentWrapper);

            const viewAllSection = document.createElement('div');
            viewAllSection.className = 'view-all-section';
            viewAllSection.innerHTML = `
                <a href="/${contentType === 'новостей' ? 'news' : 'posts'}" class="view-all-btn">
                    <span>Посмотреть все</span>
                    <svg class="arrow-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M5 12h14M12 5l7 7-7 7"/>
                    </svg>
                </a>
            `;
            container.appendChild(viewAllSection);
        })
        .catch(err => {
            console.error(err);
            hideContainer(container);
        });
}

function hideContainer(container) {
    if (container) {
        const parentSection = container.closest('.col-lg-4');
        if (parentSection) {
            parentSection.style.display = 'none';
        } else {
            container.style.display = 'none';
        }
        container.innerHTML = '';
    }
}


function renderBlogs(data) {
    const blogGrid = document.querySelector('.blog-masonry-grid');
    if (!blogGrid) return;

    // Проверяем, есть ли данные
    if (!data || !Array.isArray(data) || data.length === 0) {
        showEmptyState(blogGrid, 'блогов', '📖');
        return;
    }

    blogGrid.innerHTML = '';

    data.forEach((b, index) => {
        const t = b.blogTranslations?.[0] || {};
        const title = t.title || 'Без заголовка';
        const excerpt = t.description || '';
        const date = b.formattedDate || new Date(b.createdAt).toLocaleDateString('ru-RU');
        const views = b.viewCount ?? 0;
        const imageUrl = b.mainImage?.id ? `/api/files/${b.mainImage.id}/view` : 'https://via.placeholder.com/400x300';

        let sizeClass = 'blog-item-small';
        if (index === 0) sizeClass = 'blog-item-large';
        else if (index === 3) sizeClass = 'blog-item-tall';

        const card = document.createElement('a');
        card.href = `/blog/${b.id}`;
        card.className = `blog-item ${sizeClass}`;
        card.innerHTML = `
            <img src="${imageUrl}" alt="Blog Image">
            <div class="blog-overlay">
                <h4 class="blog-title">${title}</h4>
                <p class="blog-excerpt">${excerpt}</p>
                <span class="blog-date">${date} <span class="blog-views">👁 ${views}</span></span>
            </div>
        `;

        blogGrid.appendChild(card);
    });
}

function fetchAndRenderOlympiads(endpoint, containerId) {
    const container = document.getElementById(containerId);
    if (!container) return;

    // Показываем индикатор загрузки
    container.innerHTML = `
        <div class="modern-loading-state">
            <div class="modern-loading-spinner"></div>
            <p>Загружаем олимпиады...</p>
        </div>
    `;

    fetch(endpoint)
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка загрузки с ${endpoint}`);
            return res.json();
        })
        .then(data => {
            container.innerHTML = '';
            container.className = 'events-scroll-wrapper';

            // Проверяем, есть ли данные
            if (!data || !Array.isArray(data) || data.length === 0) {
                showEmptyState(container, 'олимпиад', '🏆');
                return;
            }

            data.forEach((olympiad, index) => {
                console.log('Данные олимпиады:', olympiad);
                console.log('createdAt:', olympiad.createdAt, 'тип:', typeof olympiad.createdAt);

                const title = olympiad.title || 'Без названия';
                const imageUrl = olympiad.fileId
                    ? `/api/files/${olympiad.fileId}/view`
                    : 'https://via.placeholder.com/400x300';

                const dateObj = new Date(olympiad.createdAt);
                const day = dateObj.getDate();
                const month = dateObj.toLocaleString('ru-RU', { month: 'short' }).toUpperCase();

                const cardWrapper = document.createElement('div');
                cardWrapper.className = 'event-card-wrapper hover-card';
                cardWrapper.style.animationDelay = `${index * 0.1}s`;
                cardWrapper.setAttribute('data-olympiad-id', olympiad.id);

                cardWrapper.innerHTML = `
                    <div class="card event-card clickable-card">
                        <div class="event-image">
                            <img src="${imageUrl}" alt="${title}">
                            <div class="event-date-overlay">
                                <span class="event-day">${day}</span>
                                <span class="event-month">${month}</span>
                            </div>
                            <div class="hover-overlay">
                                <button class="details-button">
                                    <span>Подробнее</span>
                                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                        <path d="M5 12h14M12 5l7 7-7 7"/>
                                    </svg>
                                </button>
                            </div>
                        </div>
                        <div class="event-content">
                            <h5 class="event-title">${title}</h5>
                        </div>
                    </div>
                `;

                // Добавляем обработчик клика
                cardWrapper.addEventListener('click', () => {
                    viewOlympiadDetails(olympiad.id);
                });

                container.appendChild(cardWrapper);
            });
        })
        .catch(err => {
            console.error(err);
            showErrorState(container, 'олимпиад');
        });
}

function viewOlympiadDetails(olympiadId) {
    const card = document.querySelector(`[data-olympiad-id="${olympiadId}"]`);
    if (card) {
        card.style.transform = 'scale(0.95)';
        card.style.transition = 'transform 0.1s ease';

        setTimeout(() => {
            window.location.href = `/olympiad/details?id=${olympiadId}`;
        }, 100);
    } else {
        window.location.href = `/olympiad/details?id=${olympiadId}`;
    }
}

function showEmptyState(container, contentType, icon) {
    const isCentered = contentType === 'блогов' || contentType === 'олимпиад';
    const stateClass = isCentered ? 'modern-empty-state-centered' : 'modern-empty-state-normal';
    const hasDescription = contentType === 'блогов' || contentType === 'олимпиад';

    container.innerHTML = `
        <div class="${stateClass}">
            <div class="modern-empty-icon">${icon}</div>
            <h3 class="modern-empty-title">Пока нет ${contentType}</h3>
            ${hasDescription ? `<p class="modern-empty-description">
                ${contentType === 'блогов' ? 'Блоги появятся совсем скоро. Не пропустите интересные материалы!' :
        contentType === 'олимпиад' ? 'Скоро здесь появятся новые мероприятия.' :
            'Скоро здесь появится интересный контент.'}
            </p>` : ''}
        </div>
    `;
}

function showErrorState(container, contentType) {
    container.innerHTML = `
        <div class="modern-error-state">
            <div class="modern-error-icon">⚠️</div>
            <h3 class="modern-error-title">Не удалось загрузить ${contentType}</h3>
            <p class="modern-error-description">
                Произошла ошибка при загрузке данных. Пожалуйста, попробуйте обновить страницу.
            </p>
            <button class="modern-retry-button" onclick="location.reload()">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M1 4v6h6M23 20v-6h-6"/>
                    <path d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10m22 4l-4.64 4.36A9 9 0 0 1 3.51 15"/>
                </svg>
                Обновить страницу
            </button>
        </div>
    `;
}

// Добавляем простые CSS стили
const simpleStyles = `
.events-scroll-wrapper {
    display: flex;
    gap: 1.5rem;
    overflow-x: auto;
    padding: 1rem;
    scrollbar-width: none;
    -ms-overflow-style: none;
}

.events-scroll-wrapper::-webkit-scrollbar {
    display: none;
}

/* Анимация появления */
.hover-card {
    opacity: 0;
    transform: translateY(20px);
    animation: slideUp 0.6s ease-out forwards;
}

@keyframes slideUp {
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Стили карточки */
.event-card-wrapper {
    flex-shrink: 0;
    width: 300px;
}

.clickable-card {
    cursor: pointer;
    transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94);
    border-radius: 16px;
    overflow: hidden;
    background: white;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    border: 1px solid rgba(0, 0, 0, 0.05);
    position: relative;
}

.clickable-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
    border-color: rgba(59, 130, 246, 0.2);
}

/* Стили изображения */
.event-image {
    position: relative;
    height: 200px;
    overflow: hidden;
}

.event-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.4s ease;
}

.clickable-card:hover .event-image img {
    transform: scale(1.1);
}

/* Затемнение при наведении */
.hover-overlay {
    position: absolute;
    inset: 0;
    background: rgba(0, 0, 0, 0.4);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: all 0.3s ease;
    backdrop-filter: blur(2px);
}

.clickable-card:hover .hover-overlay {
    opacity: 1;
}

/* Кнопка "Подробнее" */
.details-button {
    background: white;
    color: #3b82f6;
    border: none;
    padding: 12px 24px;
    border-radius: 30px;
    font-weight: 600;
    font-size: 14px;
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    transform: translateY(10px);
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
}

.clickable-card:hover .details-button {
    transform: translateY(0);
}

.details-button:hover {
    background: #f8fafc;
    transform: translateY(-2px) !important;
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.25);
}

.details-button svg {
    width: 16px;
    height: 16px;
    transition: transform 0.2s ease;
}

.details-button:hover svg {
    transform: translateX(3px);
}

/* Бейдж даты */
.event-date-overlay {
    position: absolute;
    top: 12px;
    right: 12px;
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(10px);
    border-radius: 12px;
    padding: 8px 10px;
    text-align: center;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: all 0.3s ease;
}

.clickable-card:hover .event-date-overlay {
    background: white;
    transform: scale(1.05);
}

.event-day {
    display: block;
    font-size: 18px;
    font-weight: 700;
    color: #3b82f6;
    line-height: 1;
}

.event-month {
    display: block;
    font-size: 11px;
    font-weight: 600;
    color: #6b7280;
    margin-top: 2px;
}

/* Контент карточки */
.event-content {
    padding: 20px;
    background: white;
}

.event-title {
    font-size: 16px;
    font-weight: 700;
    color: #1f2937;
    margin: 0;
    line-height: 1.4;
    transition: color 0.3s ease;
}

.clickable-card:hover .event-title {
    color: #3b82f6;
}

/* Состояния загрузки */
.modern-loading-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 60px 20px;
    color: #6b7280;
}

.modern-loading-spinner {
    width: 40px;
    height: 40px;
    border: 3px solid #f3f4f6;
    border-top: 3px solid #3b82f6;
    border-radius: 50%;
    animation: spin 1s linear infinite;
    margin-bottom: 16px;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

/* Пустое состояние */
.modern-empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 60px 20px;
    text-align: center;
    background: white;
    border-radius: 16px;
    border: 2px dashed rgba(59, 130, 246, 0.2);
}

.modern-empty-icon {
    font-size: 48px;
    margin-bottom: 16px;
    opacity: 0.6;
}

.modern-empty-title {
    font-size: 20px;
    font-weight: 700;
    color: #374151;
    margin: 0 0 8px 0;
}

.modern-empty-description {
    font-size: 14px;
    color: #6b7280;
    margin: 0;
}

/* Состояние ошибки */
.modern-error-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 60px 20px;
    text-align: center;
    background: white;
    border-radius: 16px;
    border: 2px solid rgba(239, 68, 68, 0.1);
}

.modern-error-icon {
    font-size: 48px;
    margin-bottom: 16px;
}

.modern-error-title {
    font-size: 20px;
    font-weight: 700;
    color: #dc2626;
    margin: 0 0 8px 0;
}

.modern-error-description {
    font-size: 14px;
    color: #6b7280;
    margin: 0 0 24px 0;
}

.modern-retry-button {
    background: #3b82f6;
    color: white;
    border: none;
    padding: 12px 24px;
    font-size: 14px;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    align-items: center;
    gap: 8px;
}

.modern-retry-button:hover {
    background: #1d4ed8;
    transform: translateY(-2px);
}

.modern-retry-button svg {
    width: 16px;
    height: 16px;
}

/* Адаптивность */
@media (max-width: 768px) {
    .events-scroll-wrapper {
        gap: 1rem;
        padding: 0.5rem;
    }
    
    .event-card-wrapper {
        width: 280px;
    }
    
    .event-image {
        height: 180px;
    }
    
    .clickable-card:hover {
        transform: translateY(-4px);
    }
    
    .event-content {
        padding: 16px;
    }
    
    .event-title {
        font-size: 15px;
    }
}

@media (max-width: 480px) {
    .event-card-wrapper {
        width: 260px;
    }
    
    .event-image {
        height: 160px;
    }
    
    .details-button {
        padding: 10px 20px;
        font-size: 13px;
    }
}

@keyframes pulse {
    0% { box-shadow: 0 0 0 0 rgba(59, 130, 246, 0.4); }
    70% { box-shadow: 0 0 0 10px rgba(59, 130, 246, 0); }
    100% { box-shadow: 0 0 0 0 rgba(59, 130, 246, 0); }
}

.clickable-card:active {
    animation: pulse 0.6s;
}

.simple-content-grid {
    display: grid;
    grid-template-columns: 1fr;
    gap: 16px;
    margin-bottom: 32px;
}

.simple-content-card {
    background: white;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    border: 1px solid rgba(0, 0, 0, 0.05);
}

.simple-content-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
}

.card-category {
    background: linear-gradient(135deg, #3b82f6, #1d4ed8);
    color: white;
    padding: 4px 12px;
    border-radius: 16px;
    font-size: 12px;
    font-weight: 600;
}

.card-stats {
    display: flex;
    gap: 8px;
}

.stat-item {
    background: #f3f4f6;
    color: #6b7280;
    padding: 4px 8px;
    border-radius: 12px;
    font-size: 11px;
    font-weight: 600;
}

.card-content {
    position: relative;
}

.card-meta {
    margin-bottom: 8px;
}

.card-date {
    color: #6b7280;
    font-size: 13px;
    font-weight: 500;
}

.card-title {
    margin: 0 0 16px 0;
    font-size: 18px;
    font-weight: 700;
    line-height: 1.4;
    color: #1f2937;
}

.card-title a {
    color: inherit;
    text-decoration: none;
    transition: color 0.2s ease;
}

.card-title a:hover {
    color: #3b82f6;
}

.card-footer {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding-top: 12px;
    border-top: 1px solid #f3f4f6;
}

.read-more-btn {
    color: #3b82f6;
    font-size: 14px;
    font-weight: 600;
    text-decoration: none;
    display: flex;
    align-items: center;
    gap: 6px;
    transition: all 0.2s ease;
}

.read-more-btn:hover {
    color: #1d4ed8;
    transform: translateX(2px);
}

.read-more-btn svg {
    transition: transform 0.2s ease;
}

.read-more-btn:hover svg {
    transform: translateX(2px);
}

.show-all-wrapper {
    text-align: center;
    margin-top: 24px;
}

.show-all-btn {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    background: linear-gradient(135deg, #3b82f6, #1d4ed8);
    color: white;
    padding: 12px 24px;
    border-radius: 12px;
    text-decoration: none;
    font-weight: 600;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(59, 130, 246, 0.3);
}

.show-all-btn:hover {
    color: white;
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(59, 130, 246, 0.4);
}

/* Современные состояния загрузки и ошибок */
.modern-loading-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 60px 20px;
    text-align: center;
    color: #6b7280;
}

.modern-loading-spinner {
    width: 40px;
    height: 40px;
    border: 3px solid #f3f4f6;
    border-top: 3px solid #3b82f6;
    border-radius: 50%;
    animation: modernSpin 1s linear infinite;
    margin-bottom: 16px;
}

@keyframes modernSpin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

.modern-empty-state-normal {
    padding: 40px 20px;
    text-align: center;
    color: #6b7280;
    background: white;
    border-radius: 12px;
    border: 1px solid #f3f4f6;
}

.modern-empty-state-centered {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 60px 20px;
    text-align: center;
    color: #6b7280;
    background: white;
    border-radius: 12px;
    border: 1px solid #f3f4f6;
    min-height: 200px;
}

.modern-empty-icon {
    font-size: 40px;
    margin-bottom: 16px;
    opacity: 0.7;
}

.modern-empty-title {
    font-size: 18px;
    font-weight: 600;
    margin: 0 0 8px 0;
    color: #374151;
}

.modern-empty-description {
    font-size: 14px;
    line-height: 1.5;
    margin: 0;
    max-width: 300px;
    color: #6b7280;
}

.modern-error-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 60px 20px;
    text-align: center;
    background: white;
    border-radius: 12px;
    border: 1px solid #f3f4f6;
    min-height: 200px;
}

.modern-error-icon {
    font-size: 40px;
    margin-bottom: 16px;
}

.modern-error-title {
    font-size: 18px;
    font-weight: 600;
    margin: 0 0 8px 0;
    color: #dc2626;
}

.modern-error-description {
    font-size: 14px;
    line-height: 1.5;
    margin: 0 0 20px 0;
    max-width: 300px;
    color: #6b7280;
}

.modern-retry-button {
    background: #3b82f6;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 14px;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.2s ease;
    display: flex;
    align-items: center;
    gap: 6px;
}

.modern-retry-button:hover {
    background: #1d4ed8;
    transform: translateY(-1px);
}

/* Адаптивность */
@media (max-width: 768px) {
    .simple-content-card {
        padding: 16px;
    }
    
    .card-title {
        font-size: 16px;
    }
    
    .card-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 8px;
    }
    
    .card-stats {
        align-self: flex-end;
    }
}

@media (max-width: 480px) {
    .simple-content-card {
        padding: 14px;
    }
    
    .card-title {
        font-size: 15px;
    }
    
    .show-all-btn {
        padding: 10px 20px;
        font-size: 14px;
    }
}
`;

if (!document.getElementById('simple-content-styles')) {
    const styleSheet = document.createElement('style');
    styleSheet.id = 'simple-content-styles';
    styleSheet.textContent = simpleStyles;
    document.head.appendChild(styleSheet);
}
document.addEventListener("DOMContentLoaded", () => {
    loadHeroOlympiad();
});

function loadHeroOlympiad() {
    const heroContainer = document.getElementById('hero-container');
    if (!heroContainer) return;

    // Показываем состояние загрузки
    heroContainer.innerHTML = `
                <div class="hero-loading">
                    <div class="loading-spinner"></div>
                    <h3>Загружаем актуальную олимпиаду...</h3>
                </div>
            `;

    // Загружаем данные олимпиады (берем первую из списка)
    fetch('/api/olymp/main')
        .then(res => {
            if (!res.ok) throw new Error('Ошибка загрузки олимпиады');
            return res.json();
        })
        .then(data => {
            if (!data || !Array.isArray(data) || data.length === 0) {
                showDefaultHero();
                return;
            }

            // Берем первую олимпиаду из списка
            const olympiad = data[0];
            renderHeroOlympiad(olympiad);
        })
        .catch(err => {
            console.error('Ошибка загрузки олимпиады:', err);
            showErrorHero();
        });
}

function renderHeroOlympiad(olympiad) {
    const heroContainer = document.getElementById('hero-container');

    const title = olympiad.title || 'Математическая олимпиада';
    const description = olympiad.info || 'Присоединяйтесь к олимпиаде и проверьте свои математические навыки. Развивайте логическое мышление и соревнуйтесь с лучшими!';

    const imageUrl = olympiad.fileId
        ? `/api/files/${olympiad.fileId}/view`
        : 'https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=400&h=300&fit=crop';

    const dateObj = olympiad.createdAt ? new Date(olympiad.createdAt) : new Date();
    const formattedDate = dateObj.toLocaleDateString('ru-RU', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    heroContainer.innerHTML = `
                <div class="row align-items-center">
                    <div class="col-lg-7">
                        <h1 class="hero-title">${title}</h1>
                        <p class="hero-description">${description}</p>
                        <div class="mb-3">
                            <small class="text-muted">📅 Дата: ${formattedDate}</small>
                        </div>
                          <div class="hero-buttons">
                        <a href="/tests" class="btn btn-primary me-3">Начать тестирование</a>
                        <a href="/olympiad" class="btn btn-outline-primary">Олимпиады</a>
                    </div>
                    </div>
                    <div class="col-lg-5">
                        <div class="hero-image">
                            <img src="${imageUrl}" alt="${title}" class="img-fluid rounded" style="max-height: 300px; width: 100%; object-fit: cover;">
                        </div>
                    </div>
                </div>
            `;
}
const translations = {
    ru: {
        olympiadTitle: "🏆 Математические олимпиады",
        olympiadDesc: "Следите за объявлениями о предстоящих олимпиадах и соревнованиях!",
        olympiadBtn: "Все олимпиады",

        heroTitle: 'Развиваем математическое мышление в <span class="text-primary">Кыргызстане</span>',
        heroDesc: "Платформа для подготовки к олимпиадам, тестам и развития математических навыков. Присоединяйтесь к сообществу математиков!",
        heroStartBtn: "Начать тестирование",
        heroOlympiadBtn: "Олимпиады",
        heroAlt: "Математика"
    },
    ky: {
        olympiadTitle: "🏆 Математикалык олимпиадалар",
        olympiadDesc: "Алдыда боло турган олимпиадалар жана сынактар жөнүндө кабарларды көзөмөлдөңүз!",
        olympiadBtn: "Бардык олимпиадалар",

        heroTitle: 'Математикалык ой жүгүртүүнү <span class="text-primary">Кыргызстанда</span> өнүктүрөбүз',
        heroDesc: "Олимпиадаларга, тесттерге даярдануу жана математика боюнча жөндөмдөрдү өркүндөтүү үчүн платформа. Математиктердин коомчулугуна кошулуңуз!",
        heroStartBtn: "Тестти баштоо",
        heroOlympiadBtn: "Олимпиадалар",
        heroAlt: "Математика"
    },
    en: {
        olympiadTitle: "🏆 Math Olympiads",
        olympiadDesc: "Stay updated on upcoming olympiads and competitions!",
        olympiadBtn: "All Olympiads",

        heroTitle: 'Developing mathematical thinking in <span class="text-primary">Kyrgyzstan</span>',
        heroDesc: "A platform for preparing for olympiads, tests, and improving math skills. Join the community of mathematicians!",
        heroStartBtn: "Start Testing",
        heroOlympiadBtn: "Olympiads",
        heroAlt: "Mathematics"
    }

};

const locale = document.getElementById('current-locale').textContent;

function showDefaultOlympiadAlert() {
    const t = translations[locale];
    const alertContainer = document.querySelector('.alert.alert-warning');

    alertContainer.innerHTML = `
        <div class="d-flex align-items-center">
            <div class="announcement-icon me-3">
                <i class="fas fa-trophy"></i>
            </div>
            <div class="flex-grow-1">
                <h4 class="mb-1">${t.olympiadTitle}</h4>
                <p class="mb-0">${t.olympiadDesc}</p>
            </div>
            <div class="announcement-action">
                <a href="/olympiads" class="btn btn-primary">${t.olympiadBtn}</a>
            </div>
        </div>
    `;
}

function showDefaultHero() {
    const t = translations[locale];
    const heroContainer = document.getElementById('hero-container');

    heroContainer.innerHTML = `
        <div class="row align-items-center">
            <div class="col-lg-7">
                <h1 class="hero-title">${t.heroTitle}</h1>
                <p class="hero-description">${t.heroDesc}</p>
                <div class="hero-buttons">
                    <a href="/tests" class="btn btn-primary me-3">${t.heroStartBtn}</a>
                    <a href="/olympiads" class="btn btn-outline-primary">${t.heroOlympiadBtn}</a>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="hero-image">
                    <img src="https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=400&h=300&fit=crop" 
                         alt="${t.heroAlt}" 
                         class="img-fluid rounded" style="max-height: 300px; width: 100%; object-fit: cover;">
                </div>
            </div>
        </div>
    `;
}


function showErrorHero() {
    const heroContainer = document.getElementById('hero-container');

    heroContainer.innerHTML = `
                <div class="hero-error">
                    <h2>⚠️ Ошибка загрузки</h2>
                    <p>Не удалось загрузить информацию об олимпиаде</p>
                    <button class="btn btn-primary" onclick="loadHeroOlympiad()">
                        🔄 Попробовать снова
                    </button>
                </div>
            `;
}


function renderOlympiadAlert(olympiad) {
    const alertContainer = document.querySelector('.alert.alert-warning');

    let title = olympiad.title || 'Математическая олимпиада';
    if (title.length > 20) {
        title = title.substring(0, 97) + '...';
    }

    const olympiadUrl = `/olympiad/details?id=${olympiad.id}`;

    let dateInfo = '';
    if (olympiad.createdAt) {
        const dateObj = new Date(olympiad.createdAt);
        const formattedDate = dateObj.toLocaleDateString('ru-RU', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
        dateInfo = ` Опубликовано: ${formattedDate}.`;
    }

    alertContainer.innerHTML = `
        <div class="d-flex align-items-center">
            <div class="announcement-icon me-3">
                <i class="fas fa-trophy"></i>
            </div>
            <div class="flex-grow-1">
                <h4 class="mb-1">${title}</h4>
                <p class="mb-0">${dateInfo}</p>
            </div>
            <div class="announcement-action">
                <a href="${olympiadUrl}" class="btn btn-primary">Подать заявку</a>
            </div>
        </div>
    `;
}



function showOlympiadAlertError() {
    const alertContainer = document.querySelector('.alert');

    alertContainer.className = 'alert alert-danger mb-5';

    alertContainer.innerHTML = `
        <div class="d-flex align-items-center">
            <div class="announcement-icon me-3">
                <i class="fas fa-exclamation-triangle"></i>
            </div>
            <div class="flex-grow-1">
                <h4 class="mb-1">⚠️ Ошибка загрузки</h4>
                <p class="mb-0">Не удалось загрузить информацию об актуальных олимпиадах</p>
            </div>
            <div class="announcement-action">
                <button class="btn btn-outline-danger" onclick="loadOlympiadAlert()">
                    🔄 Попробовать снова
                </button>
            </div>
        </div>
    `;
}

function loadOlympiadAlert() {
    const alertContainer = document.querySelector('.alert');

    if (!alertContainer) return;

    alertContainer.className = 'alert alert-warning mb-5';

    alertContainer.innerHTML = `
        <div class="d-flex align-items-center justify-content-center">
            <div class="spinner-border spinner-border-sm me-2" role="status">
                <span class="visually-hidden">Загрузка...</span>
            </div>
            <span>Загружаем актуальную олимпиаду...</span>
        </div>
    `;

    fetch('/api/olymp/main')
        .then(res => {
            if (!res.ok) throw new Error('Ошибка загрузки олимпиады');
            return res.json();
        })
        .then(data => {
            if (!data || !Array.isArray(data) || data.length === 0) {
                showDefaultOlympiadAlert();
                return;
            }

            const olympiad = data[0];
            renderOlympiadAlert(olympiad);
        })
        .catch(err => {
            console.error('Ошибка загрузки олимпиады:', err);
            showOlympiadAlertError();
        });
}

document.addEventListener('DOMContentLoaded', function() {
    loadOlympiadAlert();
});