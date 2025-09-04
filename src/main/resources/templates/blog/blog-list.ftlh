<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <#if successMessage??>
        <div id="infoAlert" class="info-alert">
            <span class="info-icon">ℹ️</span>
            <span class="info-text">${successMessage}</span>
        </div>
    </#if>
    <style>

        .info-alert {
            position: fixed;
            top: 50px;
            right: 20px;
            background-color: #0085ff;
            color: #ffffff;
            border: 1px solid #bce8f1;
            padding: 20px 16px;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.2);
            display: flex;
            align-items: center;
            gap: 8px;
            font-family: Arial, sans-serif;
            font-size: 20px;
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
        .content-wrapper {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Inter', Roboto, sans-serif;
            color: #2d3748;
            line-height: 1.6;
        }

        .post-date {
            color: #a0aec0;
            font-size: 0.85rem;
            font-weight: 500;
        }

        .post-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 16px;
            padding-top: 16px;
            border-top: 1px solid #e2e8f0;
        }


        .post-description {
            color: #718096;
            font-size: 0.95rem;
            line-height: 1.6;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .page-header {
            text-align: center;
            margin-bottom: 40px;
            padding: 40px 20px;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            border-radius: 24px;
            color: white;
            box-shadow: 0 20px 40px rgba(102, 126, 234, 0.3);
        }

        .page-title {
            font-size: 2.5rem;
            font-weight: 800;
            margin-bottom: 12px;
            background: linear-gradient(135deg, #ffffff, #f0f4ff);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .page-subtitle {
            font-size: 1.1rem;
            opacity: 0.9;
            font-weight: 400;
        }

        /* Search Container */
        .search-container {
            background: white;
            border-radius: 20px;
            margin-bottom: 40px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .search-header {
            padding: 24px;
            background: linear-gradient(135deg, #f8fafc, #e2e8f0);
            border-bottom: 1px solid #e2e8f0;
        }

        .search-title {
            font-weight: 700;
            font-size: 18px;
            color: #2d3748;
            margin: 0;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .search-title::before {
            content: "🔍";
            font-size: 20px;
        }

        .content-wrapper form {
            padding: 24px;
        }

        .search-row {
            display: flex;
            gap: 16px;
            margin-bottom: 20px;
            flex-wrap: wrap;
            align-items: stretch;
        }

        .search-input-group {
            flex: 2;
            min-width: 250px;
        }

        .search-input-group input {
            width: 100%;
            padding: 16px 20px;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            font-size: 16px;
            outline: none;
            transition: all 0.3s ease;
            background: white;
            font-weight: 500;
        }

        .search-input-group input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
            transform: translateY(-2px);
        }

        .select-group {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
            flex: 3;
        }

        .select-wrapper {
            position: relative;
            min-width: 140px;
            flex: 1;
        }

        .select-wrapper select {
            width: 100%;
            padding: 16px 20px;
            padding-right: 45px;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            font-size: 14px;
            background: white;
            cursor: pointer;
            outline: none;
            transition: all 0.3s ease;
            appearance: none;
            font-weight: 500;
        }

        .select-wrapper::after {
            content: "▼";
            position: absolute;
            right: 16px;
            top: 50%;
            transform: translateY(-50%);
            pointer-events: none;
            color: #667eea;
            font-size: 12px;
            font-weight: bold;
        }

        .select-wrapper select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
            transform: translateY(-2px);
        }

        .search-button {
            padding: 16px 32px;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            border: none;
            border-radius: 12px;
            color: white;
            font-weight: 700;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
            min-width: 120px;
        }

        .search-button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.5);
        }

        .search-button:active {
            transform: translateY(-1px);
        }

        .language-selector {
            background: linear-gradient(135deg, #f0f4ff, #e0e7ff);
            border-color: #c7d2fe;
        }

        .language-selector:focus {
            border-color: #6366f1;
            box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
        }

        .blogs-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 30px;
            margin-bottom: 50px;
        }

        .blog-item {
            background: white;
            border-radius: 20px;
            overflow: hidden;
            cursor: pointer;
            transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            position: relative;
        }

        .blog-item:hover {
            transform: translateY(-10px) scale(1.02);
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
        }

        .blog-image-container {
            position: relative;
            height: 220px;
            overflow: hidden;
        }

        .blog-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: all 0.4s ease;
        }

        .blog-item:hover img {
            transform: scale(1.1);
            filter: brightness(0.8);
        }

        .blog-placeholder {
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 48px;
        }

        .blog-overlay {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0.8) 100%);
            display: flex;
            align-items: flex-end;
            justify-content: space-between;
            opacity: 0;
            transition: all 0.3s ease;
            padding: 20px;
        }

        .blog-item:hover .blog-overlay {
            opacity: 1;
        }

        .blog-stats {
            display: flex;
            align-items: center;
            gap: 20px;
            color: white;
            font-weight: 600;
            font-size: 14px;
        }

        .stat-item {
            display: flex;
            align-items: center;
            gap: 6px;
            padding: 8px 12px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 20px;
            backdrop-filter: blur(10px);
        }

        .stat-views::before {
            content: "👁";
            font-size: 16px;
        }

        .stat-shares::before {
            content: "📤";
            font-size: 16px;
        }


        .blog-content {
            padding: 24px;
        }

        .blog-title {
            font-size: 1.25rem;
            font-weight: 700;
            color: #2d3748;
            margin-bottom: 12px;
            line-height: 1.4;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .blog-description {
            color: #718096;
            font-size: 0.95rem;
            line-height: 1.6;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .blog-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 16px;
            padding-top: 16px;
            border-top: 1px solid #e2e8f0;
        }

        .blog-date {
            color: #a0aec0;
            font-size: 0.85rem;
            font-weight: 500;
        }

        .blog-stats-bottom {
            display: flex;
            gap: 16px;
        }

        .stat-item-bottom {
            display: flex;
            align-items: center;
            gap: 4px;
            color: #718096;
            font-size: 0.85rem;
            font-weight: 500;
        }

        /* Subscription Section */
        .subscription-section {
            background: white;
            padding: 30px;
            border-radius: 20px;
            margin: 40px 0;
            text-align: center;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        .subscription-button {
            padding: 16px 32px;
            border: none;
            border-radius: 12px;
            font-weight: 700;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s ease;
            min-width: 250px;
        }

        .btn-outline-primary {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }

        .btn-outline-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.5);
        }

        .btn-outline-danger {
            background: linear-gradient(135deg, #e53e3e, #c53030);
            color: white;
            box-shadow: 0 4px 15px rgba(229, 62, 62, 0.4);
        }

        .btn-outline-danger:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(229, 62, 62, 0.5);
        }

        /* Pagination */
        .pagination-container {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 16px;
            margin-top: 50px;
            padding: 30px 0;
        }

        .pagination-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 16px 24px;
            border-radius: 12px;
            text-decoration: none;
            font-weight: 700;
            font-size: 16px;
            transition: all 0.3s ease;
            min-width: 120px;
            gap: 8px;
        }

        .pagination-btn.prev {
            background: white;
            color: #667eea;
            border: 2px solid #667eea;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.2);
        }

        .pagination-btn.prev:hover {
            background: #667eea;
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
            text-decoration: none;
        }

        .pagination-btn.next {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            border: none;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }

        .pagination-btn.next:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.5);
            text-decoration: none;
        }

        .pagination-info {
            background: white;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            padding: 12px 20px;
            font-size: 16px;
            color: #2d3748;
            font-weight: 600;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        /* Loading and No Blogs */
        .loader {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 300px;
            color: #718096;
            font-size: 18px;
            grid-column: 1 / -1;
        }

        .spinner {
            border: 4px solid #e2e8f0;
            border-top: 4px solid #667eea;
            border-radius: 50%;
            width: 50px;
            height: 50px;
            animation: spin 1s linear infinite;
            margin-bottom: 20px;
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        .no-blogs {
            grid-column: 1 / -1;
            text-align: center;
            padding: 80px 20px;
            color: #718096;
            font-size: 20px;
            background: white;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        .no-blogs::before {
            content: "📝";
            display: block;
            font-size: 64px;
            margin-bottom: 20px;
            opacity: 0.5;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 15px;
            }

            .page-title {
                font-size: 2rem;
            }

            .search-row {
                flex-direction: column;
            }

            .search-input-group,
            .select-group {
                width: 100%;
            }

            .blogs-grid {
                grid-template-columns: 1fr;
                gap: 20px;
            }

            .pagination-container {
                flex-wrap: wrap;
                gap: 12px;
            }

            .pagination-btn {
                min-width: 100px;
                padding: 12px 20px;
                font-size: 14px;
            }
        }

        @media (max-width: 480px) {
            .select-group {
                flex-direction: column;
            }

            .select-wrapper {
                min-width: unset;
            }

            .blog-stats {
                font-size: 12px;
                gap: 12px;
            }
        }
    </style>

    <div class="content-wrapper">
        <!-- Page Header -->
        <div class="page-header">
            <h1 class="page-title">📝 <@spring.message "blogs.blogs"/></h1>
            <p class="page-subtitle"><@spring.message "blogs.info1"/></p>
        </div>

        <div class="search-container">
            <div class="search-header">
                <h2 class="search-title"><@spring.message "blogs.search"/></h2>
            </div>
            <form method="get" action="/blog">
                <div class="search-row">
                    <div class="search-input-group">
                        <input type="text" name="query" value="${query!}"
                               placeholder="🔍 <@spring.message "blogs.byName"/>..."/>
                    </div>
                </div>

                <div class="search-row">
                    <div class="select-group">
                        <div class="select-wrapper">
                            <select name="sortBy">
                                <option value="createdAt" <#if sortBy=="createdAt">selected</#if>>📅 <@spring.message "blogs.byDate"/>
                                </option>
                                <option value="viewCount" <#if sortBy=="viewCount">selected</#if>>👁 <@spring.message "blogs.byView"/>
                                </option>
                                <option value="shareCount" <#if sortBy=="shareCount">selected</#if>>📤 <@spring.message "blogs.byRepost"/>
                                </option>
                            </select>
                        </div>

                        <div class="select-wrapper">
                            <select name="sortDirection">
                                <option value="DESC" <#if sortDirection=="DESC">selected</#if>>⬇️ <@spring.message "blogs.byNew"/></option>
                                <option value="ASC" <#if sortDirection=="ASC">selected</#if>>⬆️ <@spring.message "blogs.byOld"/></option>
                            </select>
                        </div>

                        <div class="select-wrapper">
                            <select name="language" class="language-selector">
                                <option value="ru" <#if currentLang=="ru">selected</#if>>🇷🇺 Русский</option>
                                <option value="en" <#if currentLang=="en">selected</#if>>🇺🇸 English</option>
                                <option value="ky" <#if currentLang=="ky">selected</#if>>🇰🇬 Кыргызча</option>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="search-button">✨ <@spring.message "blogs.take"/></button>
                </div>
            </form>
        </div>

        <#if blog?? && blog.content?size gt 0>
            <div class="blogs-grid">
                <#list blog.content as b>
                    <article class="blog-item" onclick="window.location.href='/blog/${b.id}'" tabindex="0">
                        <div class="blog-image-container">
                            <#if b.mainImage??>
                                <img src="/api/files/${b.mainImage.id}/view" alt="Blog Image">
                            <#else>
                                <div class="blog-placeholder">📝</div>
                            </#if>

                            <div class="blog-overlay">
                                <div class="blog-stats">
                                    <div class="stat-item stat-views">${b.viewCount!0}</div>
                                    <div class="stat-item stat-shares">${b.shareCount!0}</div>
                                </div>
                            </div>
                        </div>

                        <div class="blog-content">
                            <#assign translation = (b.blogTranslations?first)!>

                            <#if translation??>
                                <#if translation.title?? && translation.title?has_content>
                                    <h3 class="post-title">${translation.title}</h3>
                                </#if>

                                <#if translation.description?? && translation.description?has_content>
                                    <p class="post-description">${translation.description}</p>
                                </#if>

                                <div class="post-meta">
            <span class="post-date">
               📅 ${b.formattedDate}
            </span>

                                    <span class="post-date">
            </span>
                                </div>
                            </#if>
                        </div>
                    </article>
                </#list>
            </div>
        <#else>
            <div class="blogs-grid">
                <#if blog?? && blog.content?size == 0>
                    <div class="no-blogs">
                        <@spring.message "blogs.blogNotFind"/>
                        <div style="margin-top: 12px; font-size: 16px; opacity: 0.8;"><@spring.message "blogs.changeSearch"/>
                        </div>
                    </div>
                <#else>
                    <div class="loader">
                        <div class="spinner"></div>
                        <span><@spring.message "blogs.loadBlogs"/>...</span>
                    </div>
                </#if>
            </div>
        </#if>

        <div class="subscription-section">
            <form action="/notifications/${(isBlogSubscribed!false)?then('unsubscribe', 'subscribe')}" method="post">
                <@main.csrf/>
                <input type="hidden" name="type" value="BLOG">
                <#if (isAuthenticated!false) && (isBlogSubscribed!false)>
                    <button class="subscription-button btn-outline-danger">🔕 <@spring.message "blogs.unscribe"/></button>
                <#else>
                    <button class="subscription-button btn-outline-primary">📩 <@spring.message "blogs.subcribe"/></button>
                </#if>
            </form>
        </div>

    </div>

    <#if blog?? && blog.content?size gt 0 && (blog.hasPrevious() || blog.hasNext())>>
        <div class="pagination-container">
            <#if blog.hasPrevious()>
                <a href="?page=${page - 1}&size=${size}&query=${query!}&sortBy=${sortBy}&sortDirection=${sortDirection}&language=${currentLang!'ru'}"
                   class="pagination-btn prev">
                    ← <@spring.message "blogs.back"/>
                </a>
            </#if>

            <div class="pagination-info">
                <@spring.message "blogs.page"/> ${page}
            </div>

            <#if blog.hasNext()>
                <a href="?page=${page + 1}&size=${size}&query=${query!}&sortBy=${sortBy}&sortDirection=${sortDirection}&language=${currentLang!'ru'}"
                   class="pagination-btn next">
                    <@spring.message "blogs.next"/> →
                </a>
            </#if>
        </div>
    </#if>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Language selector change handler
            const langSelect = document.querySelector('select[name="language"]');
            if (langSelect) {
                langSelect.addEventListener('change', function () {
                    const form = this.closest('form');
                    if (form) {
                        form.submit();
                    }
                });
            }

            // Keyboard navigation for blogs
            document.addEventListener('keydown', function (e) {
                const blogs = document.querySelectorAll('.blog-item');
                const currentBlog = document.querySelector('.blog-item:focus');

                if (!currentBlog && blogs.length > 0) {
                    if (['ArrowDown', 'ArrowUp', 'ArrowLeft', 'ArrowRight'].includes(e.key)) {
                        blogs[0].focus();
                        e.preventDefault();
                    }
                    return;
                }

                const currentIndex = Array.from(blogs).indexOf(currentBlog);
                let newIndex = currentIndex;

                switch (e.key) {
                    case 'ArrowLeft':
                        newIndex = currentIndex > 0 ? currentIndex - 1 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'ArrowRight':
                        newIndex = currentIndex < blogs.length - 1 ? currentIndex + 1 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'ArrowUp':
                        const cols = Math.floor(blogs[0].parentElement.offsetWidth / blogs[0].offsetWidth);
                        newIndex = currentIndex >= cols ? currentIndex - cols : currentIndex;
                        e.preventDefault();
                        break;
                    case 'ArrowDown':
                        const colsDown = Math.floor(blogs[0].parentElement.offsetWidth / blogs[0].offsetWidth);
                        newIndex = currentIndex + colsDown < blogs.length ? currentIndex + colsDown : currentIndex;
                        e.preventDefault();
                        break;
                    case 'Enter':
                        if (currentBlog) {
                            currentBlog.click();
                        }
                        e.preventDefault();
                        break;
                }

                if (newIndex !== currentIndex && blogs[newIndex]) {
                    blogs[newIndex].focus();
                }
            });

            // Make blogs focusable
            document.querySelectorAll('.blog-item').forEach(item => {
                item.setAttribute('tabindex', '0');
            });

            // Subscription form handler
            const subscriptionForm = document.querySelector('form[action*="/notifications/"]');
            if (subscriptionForm) {
                const subscriptionButton = subscriptionForm.querySelector('button');

                subscriptionForm.addEventListener('submit', async function (e) {
                    e.preventDefault();

                    const formData = new FormData(subscriptionForm);
                    const isSubscribed = subscriptionButton.textContent.includes('Подписаться');

                    try {
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
                                showNotification('✅ Вы успешно подписались на уведомления!', 'success');
                                updateButtonToUnsubscribe();
                            } else {
                                showNotification('❌ Вы отписались от уведомлений', 'info');
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
                    subscriptionButton.className = 'subscription-button btn-outline-danger';
                    subscriptionButton.disabled = false;
                    subscriptionForm.action = subscriptionForm.action.replace('/subscribe', '/unsubscribe');
                }

                function updateButtonToSubscribe() {
                    subscriptionButton.textContent = '📩 Подписаться на уведомления';
                    subscriptionButton.className = 'subscription-button btn-outline-primary';
                    subscriptionButton.disabled = false;
                    subscriptionForm.action = subscriptionForm.action.replace('/unsubscribe', '/subscribe');
                }
            }

            function showNotification(message, type = 'info') {
                const existingNotifications = document.querySelectorAll('.notification-toast');
                existingNotifications.forEach(notification => notification.remove());

                const notification = document.createElement('div');
                let alertClass = 'info';
                if (type === 'success') {
                    alertClass = 'success';
                } else if (type === 'error') {
                    alertClass = 'error';
                }

                notification.className = `notification-toast alert-`+ alertClass +``;
                notification.style.cssText = `
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    z-index: 1050;
                    min-width: 300px;
                    max-width: 500px;
                    padding: 16px 20px;
                    border-radius: 12px;
                    color: white;
                    font-weight: 600;
                    animation: slideInRight 0.3s ease-out;
                    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
                    backdrop-filter: blur(10px);
                `;

                if (type === 'success') {
                    notification.style.background = 'linear-gradient(135deg, #48bb78, #38a169)';
                } else if (type === 'error') {
                    notification.style.background = 'linear-gradient(135deg, #e53e3e, #c53030)';
                } else {
                    notification.style.background = 'linear-gradient(135deg, #667eea, #764ba2)';
                }

                notification.innerHTML = `
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <span>`+ message +`</span>
                        <button onclick="this.parentElement.parentElement.remove()"
                                style="background: none; border: none; color: white; font-size: 18px; cursor: pointer; padding: 0; margin-left: 12px;">×</button>
                    </div>
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

                        .notification-toast:hover {
                            transform: scale(1.02);
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
            }

            // Smooth scroll for pagination
            const paginationLinks = document.querySelectorAll('.pagination-btn');
            paginationLinks.forEach(link => {
                link.addEventListener('click', function (e) {
                    // Add loading state
                    this.style.opacity = '0.7';
                    this.style.pointerEvents = 'none';
                });
            });

            // Enhanced hover effects for blogs
            const blogItems = document.querySelectorAll('.blog-item');
            blogItems.forEach(item => {
                item.addEventListener('mouseenter', function () {
                    this.style.zIndex = '10';
                });

                item.addEventListener('mouseleave', function () {
                    this.style.zIndex = '1';
                });

                // Add ripple effect on click
                item.addEventListener('click', function (e) {
                    const ripple = document.createElement('div');
                    ripple.style.cssText = `
                        position: absolute;
                        border-radius: 50%;
                        background: rgba(102, 126, 234, 0.3);
                        transform: scale(0);
                        animation: ripple 0.6s linear;
                        pointer-events: none;
                    `;

                    const rect = this.getBoundingClientRect();
                    const size = Math.max(rect.width, rect.height);
                    const x = e.clientX - rect.left - size / 2;
                    const y = e.clientY - rect.top - size / 2;

                    ripple.style.width = ripple.style.height = size + 'px';
                    ripple.style.left = x + 'px';
                    ripple.style.top = y + 'px';

                    this.appendChild(ripple);

                    setTimeout(() => {
                        ripple.remove();
                    }, 600);
                });
            });

            if (!document.querySelector('#ripple-styles')) {
                const style = document.createElement('style');
                style.id = 'ripple-styles';
                style.textContent = `
                    @keyframes ripple {
                        to {
                            transform: scale(2);
                            opacity: 0;
                        }
                    }
                `;
                document.head.appendChild(style);
            }

            // Page loading effect
            const blogGrid = document.querySelector('.blogs-grid');
            if (blogGrid) {
                const observer = new IntersectionObserver((entries) => {
                    entries.forEach(entry => {
                        if (entry.isIntersecting) {
                            entry.target.style.opacity = '1';
                            entry.target.style.transform = 'translateY(0)';
                        }
                    });
                });

                document.querySelectorAll('.blog-item').forEach((item, index) => {
                    item.style.opacity = '0';
                    item.style.transform = 'translateY(20px)';
                    item.style.transition = `opacity 0.6s ease `+index * 0.1+`s, transform 0.6s ease `+index * 0.1+`s`;
                    observer.observe(item);
                });
            }

            // Auto-refresh functionality
            let refreshInterval;
            const startAutoRefresh = () => {
                refreshInterval = setInterval(() => {
                    const currentUrl = new URL(window.location);
                    fetch(currentUrl.toString(), {
                        headers: {
                            'X-Requested-With': 'XMLHttpRequest'
                        }
                    }).catch(error => {
                        console.log('Auto-refresh failed:', error);
                    });
                }, 300000);
            };

            const stopAutoRefresh = () => {
                if (refreshInterval) {
                    clearInterval(refreshInterval);
                }
            };

            document.addEventListener('visibilitychange', () => {
                if (document.hidden) {
                    stopAutoRefresh();
                } else {
                    startAutoRefresh();
                }
            });

            startAutoRefresh();

            const searchInput = document.querySelector('input[name="query"]');
            if (searchInput) {
                let searchTimeout;
                searchInput.addEventListener('input', function () {
                    clearTimeout(searchTimeout);
                    searchTimeout = setTimeout(() => {
                        // Auto-submit search after 1 second of no typing
                        if (this.value.length > 2 || this.value.length === 0) {
                            const form = this.closest('form');
                            if (form) {
                                const submitBtn = form.querySelector('.search-button');
                                submitBtn.style.background = 'linear-gradient(135deg, #48bb78, #38a169)';
                                submitBtn.textContent = '🔍 Поиск...';
                                setTimeout(() => {
                                    form.submit();
                                }, 500);
                            }
                        }
                    }, 1000);
                });
            }
        });
    </script>

</@main.layout>