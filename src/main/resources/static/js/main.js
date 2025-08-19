document.addEventListener("DOMContentLoaded", () => {
    fetchAndRender('/api/news/main', 'news-container', 'новостей', '📰');
    fetchAndRender('/api/posts/main', 'post-container', 'публикаций', '📝');
    fetchAndRenderOlympiads('/api/olymp/main', 'eventsScroll');

    fetch('/api/blog/main')
        .then(res => res.json())
        .then(renderBlogs)
        .catch(err => {
            console.error('Ошибка загрузки блогов:', err);
            showEmptyState(document.querySelector('.blog-masonry-grid'), 'блогов', '📖');
        });
});

function fetchAndRender(endpoint, containerId, contentType, icon) {
    const container = document.getElementById(containerId);
    if (!container) return;

    // Показываем индикатор загрузки
    container.innerHTML = `
        <div class="modern-loading-state">
            <div class="modern-loading-spinner"></div>
            <p>Загружаем ${contentType}...</p>
        </div>
    `;

    fetch(endpoint)
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка загрузки с ${endpoint}`);
            return res.json();
        })
        .then(data => {
            container.innerHTML = '';

            // Проверяем, есть ли данные
            if (!data || !Array.isArray(data) || data.length === 0) {
                showEmptyState(container, contentType, icon);
                return;
            }

            // Создаем сетку простых карточек
            const grid = document.createElement('div');
            grid.className = 'simple-content-grid';

            data.forEach((item, index) => {
                const title =
                    item.newsTranslations?.[0]?.title ||
                    item.postTranslations?.[0]?.title ||
                    item.title || 'Без заголовка';

                const dateStr = item.formattedDate || new Date(item.createdAt).toLocaleString('ru-RU');
                const views = item.viewCount ?? 0;
                const shares = item.shareCount ?? 0;

                const linkUrl = contentType === 'новостей' ? `/news/${item.id}` : `/posts/${item.id}`;

                const card = document.createElement('article');
                card.className = 'simple-content-card';
                card.innerHTML = `
                    <div class="card-header">
                        <div class="card-category">
                            ${icon} ${contentType === 'новостей' ? 'Новость' : 'Публикация'}
                        </div>
                        <div class="card-stats">
                            <span class="stat-item">👁 ${views}</span>
                            ${shares > 0 ? `<span class="stat-item">📤 ${shares}</span>` : ''}
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="card-meta">
                            <time class="card-date">${dateStr}</time>
                        </div>
                        <h3 class="card-title">
                            <a href="${linkUrl}">${title}</a>
                        </h3>
                        <div class="card-footer">
                            <a href="${linkUrl}" class="read-more-btn">
                                Читать далее
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <path d="M5 12h14M12 5l7 7-7 7"/>
                                </svg>
                            </a>
                        </div>
                    </div>
                `;

                // Добавляем анимацию появления
                card.style.opacity = '0';
                card.style.transform = 'translateY(15px);'

                grid.appendChild(card);

                // Анимация с задержкой
                setTimeout(() => {
                    card.style.transition = 'all 0.6s ease-out';
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                }, index * 100);
            });

            container.appendChild(grid);

            // Добавляем кнопку "Показать все"
            const showAllBtn = document.createElement('div');
            showAllBtn.className = 'show-all-wrapper';
            showAllBtn.innerHTML = `
                <a href="/${contentType === 'новостей' ? 'news' : 'posts'}" class="show-all-btn">
                    Все ${contentType}
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M5 12h14M12 5l7 7-7 7"/>
                    </svg>
                </a>
            `;
            container.appendChild(showAllBtn);
        })
        .catch(err => {
            console.error(err);
            showErrorState(container, contentType);
        });
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

            // Проверяем, есть ли данные
            if (!data || !Array.isArray(data) || data.length === 0) {
                showEmptyState(container, 'олимпиад', '🏆');
                return;
            }

            data.forEach(olympiad => {
                console.log('Данные олимпиады:', olympiad);
                console.log('createdAt:', olympiad.createdAt, 'тип:', typeof olympiad.createdAt);

                const title = olympiad.title || 'Без названия';
                const imageUrl = olympiad.fileId
                    ? `/api/files/${olympiad.fileId}/view`
                    : 'https://via.placeholder.com/400x300';

                const dateObj = new Date(olympiad.createdAt);
                console.log('dateObj:', dateObj);

                const day = dateObj.getDate();
                const month = dateObj.toLocaleString('ru-RU', { month: 'short' }).toUpperCase();

                const cardWrapper = document.createElement('div');
                cardWrapper.className = 'event-card-wrapper';
                cardWrapper.innerHTML = `
                    <div class="card event-card">
                        <div class="event-image">
                            <img src="${imageUrl}" alt="${title}">
                            <div class="event-date-overlay">
                                <span class="event-day">${day}</span>
                                <span class="event-month">${month}</span>
                            </div>
                        </div>
                        <div class="event-content">
                            <h5>${title}</h5>
                        </div>
                    </div>
                `;
                container.appendChild(cardWrapper);
            });
        })
        .catch(err => {
            console.error(err);
            showErrorState(container, 'олимпиад');
        });
}

// Функция для показа пустого состояния
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

// Функция для показа состояния ошибки
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
/* Простые стили для контента */
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
        : 'https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=600&h=400&fit=crop';

    const dateObj = olympiad.createdAt ? new Date(olympiad.createdAt) : new Date();
    const formattedDate = dateObj.toLocaleDateString('ru-RU', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    heroContainer.innerHTML = `
                <div class="row align-items-center">
                    <div class="col-lg-6">
                        <h1 class="hero-title">${title}</h1>
                        <p class="hero-description">${description}</p>
                        <div class="mb-3">
                            <small class="text-muted">📅 Дата: ${formattedDate}</small>
                        </div>
                          <div class="hero-buttons">
                        <a href="/tests" class="btn btn-primary me-3">Начать тестирование</a>
                        <a href="/olympiads" class="btn btn-outline-primary">Олимпиады</a>
                    </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="hero-image">
                            <img src="${imageUrl}" alt="${title}" class="img-fluid">
                        </div>
                    </div>
                </div>
            `;
}

function showDefaultHero() {
    const heroContainer = document.getElementById('hero-container');

    heroContainer.innerHTML = `
                <div class="row align-items-center">
                    <div class="col-lg-6">
                        <h1 class="hero-title">Развиваем математическое мышление в <span class="text-primary">Кыргызстане</span></h1>
                        <p class="hero-description">Платформа для подготовки к олимпиадам, тестам и развития математических навыков. Присоединяйтесь к сообществу математиков!</p>
                        <div class="hero-buttons">
                            <a href="/tests" class="btn btn-primary me-3">Начать тестирование</a>
                            <a href="/olympiads" class="btn btn-outline-primary">Олимпиады</a>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="hero-image">
                            <img src="https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=600&h=400&fit=crop" 
                                 alt="Математика" class="img-fluid">
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