document.addEventListener("DOMContentLoaded", () => {
    fetchAndRender('/api/news/main', 'news-container');
    fetchAndRender('/api/posts/main', 'post-container');
    fetchAndRenderOlympiads('/api/olymp/main', 'eventsScroll');

    fetch('/api/blog/main')
        .then(res => res.json())
        .then(renderBlogs)
        .catch(err => console.error('Ошибка загрузки блогов:', err));
});


function fetchAndRender(endpoint, containerId) {
    const container = document.getElementById(containerId);
    if (!container) return;

    fetch(endpoint)
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка загрузки с ${endpoint}`);
            return res.json();
        })
        .then(data => {
            container.innerHTML = '';

            data.forEach(item => {
                const title =
                    item.newsTranslations?.[0]?.title ||
                    item.postTranslations?.[0]?.title ||
                    item.title || 'Без заголовка';
                const dateStr = item.formattedDate || new Date(item.createdAt).toLocaleString('ru-RU');
                const views = item.viewCount ?? 0;
                const card = document.createElement('div');
                card.className = 'news-item';
                card.innerHTML = `
             <div class="news-date">${dateStr} <span class="news-stats">👁 ${views}</span></div>
            <div class="news-content">${title}</div>
   `;

                container.appendChild(card);
            });

        })
        .catch(err => {
            container.innerHTML = `<p>Не удалось загрузить данные из ${endpoint}.</p>`;
            console.error(err);
        });
}


function renderBlogs(data) {
    const blogGrid = document.querySelector('.blog-masonry-grid');
    if (!blogGrid || !Array.isArray(data)) return;

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

    fetch(endpoint)
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка загрузки с ${endpoint}`);
            return res.json();
        })
        .then(data => {
            container.innerHTML = '';

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
            container.innerHTML = `<p>Не удалось загрузить олимпиады.</p>`;
            console.error(err);
        });
}

