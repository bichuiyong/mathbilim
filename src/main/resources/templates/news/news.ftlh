<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous">
    <style>
        .news-wrapper {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Inter', Roboto, sans-serif;
            color: #2d3748;
            line-height: 1.6;
        }

        .news-header {
            text-align: center;
            margin-bottom: 40px;
            padding: 30px 20px;
            background: linear-gradient(135deg, #1a73e8, #0d47a1);
            border-radius: 16px;
            color: white;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }

        .news-title {
            font-size: 2.25rem;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .news-subtitle {
            font-size: 1.1rem;
            opacity: 0.9;
            font-weight: 400;
        }

        /* Search Container */
        .news-search-container {
            background: white;
            border-radius: 12px;
            margin-bottom: 30px;
            padding: 20px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            border: 1px solid #e2e8f0;
        }

        .news-search-title {
            font-weight: 600;
            font-size: 1.25rem;
            color: #2d3748;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .news-search-title::before {
            content: "🔍";
            font-size: 1.25rem;
        }

        .news-search-row {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            align-items: center;
        }

        .news-input-group {
            flex: 1;
            min-width: 200px;
        }

        .news-input-group input {
            width: 100%;
            padding: 12px 16px;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            font-size: 0.95rem;
            outline: none;
            transition: all 0.3s ease;
        }

        .news-input-group input:focus {
            border-color: #1a73e8;
            box-shadow: 0 0 0 3px rgba(26, 115, 232, 0.1);
        }

        .news-select-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            flex: 1;
        }

        .news-select-wrapper {
            position: relative;
            min-width: 120px;
            flex: 1;
        }

        .news-select-wrapper select {
            width: 100%;
            padding: 12px 16px;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            font-size: 0.9rem;
            background: white;
            cursor: pointer;
            outline: none;
            transition: all 0.3s ease;
            appearance: none;
        }

        .news-select-wrapper::after {
            content: "▼";
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            pointer-events: none;
            color: #1a73e8;
            font-size: 0.8rem;
        }

        .news-select-wrapper select:focus {
            border-color: #1a73e8;
            box-shadow: 0 0 0 3px rgba(26, 115, 232, 0.1);
        }

        .news-search-button {
            padding: 12px 24px;
            background: #1a73e8;
            border: none;
            border-radius: 8px;
            color: white;
            font-weight: 600;
            font-size: 0.95rem;
            cursor: pointer;
            transition: all 0.3s ease;
            min-width: 100px;
        }

        .news-search-button:hover {
            background: #1557b0;
            transform: translateY(-2px);
        }

        /* News List (Vertical list layout) */
        .news-grid {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-bottom: 40px;
        }

        .news-card {
            display: flex;
            align-items: center;
            background: white;
            border-bottom: 1px solid #e2e8f0;
            padding: 15px;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .news-card:hover {
            background: #f7fafc;
            transform: translateY(-2px);
        }

        .news-image-container {
            flex: 0 0 100px;
            height: 60px;
            margin-right: 15px;
            overflow: hidden;
        }

        .news-card img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .news-placeholder {
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, #1a73e8, #0d47a1);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.5rem;
        }

        .news-content {
            flex: 1;
        }

        .news-title {
            font-size: 2.5rem;
            font-weight: 800;
            margin-bottom: 12px;
            background: linear-gradient(135deg, #ffffff, #f0f4ff);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .news-description {
            color: #718096;
            font-size: 0.85rem;
            line-height: 1.4;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .news-meta {
            display: flex;
            gap: 15px;
            margin-top: 8px;
            font-size: 0.8rem;
            color: #718096;
        }

        .news-meta span {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        /* Subscription Section */
        .news-subscription {
            background: white;
            padding: 20px;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            margin: 30px 0;
        }

        .news-subscription-button {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            font-size: 0.95rem;
            cursor: pointer;
            transition: all 0.3s ease;
            min-width: 200px;
        }

        .news-subscription-button.subscribe {
            background: #1a73e8;
            color: white;
        }

        .news-subscription-button.subscribe:hover {
            background: #1557b0;
            transform: translateY(-2px);
        }

        .news-subscription-button.unsubscribe {
            background: #e53e3e;
            color: white;
        }

        .news-subscription-button.unsubscribe:hover {
            background: #c53030;
            transform: translateY(-2px);
        }

        /* Pagination */
        .news-pagination {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 30px;
        }

        .news-pagination-btn {
            padding: 10px 20px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 0.9rem;
            transition: all 0.3s ease;
            text-decoration: none;
        }

        .news-pagination-btn.prev {
            background: white;
            color: #1a73e8;
            border: 1px solid #1a73e8;
        }

        .news-pagination-btn.prev:hover {
            background: #1a73e8;
            color: white;
        }

        .news-pagination-btn.next {
            background: #1a73e8;
            color: white;
            border: none;
        }

        .news-pagination-btn.next:hover {
            background: #1557b0;
        }

        .news-pagination-info {
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 0.9rem;
            color: #2d3748;
            font-weight: 600;
            border: 1px solid #e2e8f0;
        }

        /* Loading and No Posts */
        .news-loader {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 200px;
            color: #718096;
            font-size: 1.1rem;
            width: 100%;
        }

        .news-spinner {
            border: 3px solid #e2e8f0;
            border-top: 3px solid #1a73e8;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin-bottom: 15px;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        .news-no-posts {
            width: 100%;
            text-align: center;
            padding: 40px;
            color: #718096;
            font-size: 1.2rem;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .news-no-posts::before {
            content: "📰";
            display: block;
            font-size: 2.5rem;
            margin-bottom: 15px;
            opacity: 0.5;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .news-wrapper {
                padding: 15px;
            }

            .news-title {
                font-size: 1.75rem;
            }

            .news-search-row {
                flex-direction: column;
            }

            .news-input-group,
            .news-select-group {
                width: 100%;
            }

            .news-card {
                flex-direction: column;
                align-items: flex-start;
                padding: 10px;
            }

            .news-image-container {
                width: 100%;
                height: 120px;
                margin-right: 0;
                margin-bottom: 10px;
            }
        }

        @media (max-width: 480px) {
            .news-select-group {
                flex-direction: column;
            }

            .news-select-wrapper {
                min-width: unset;
            }

            .news-image-container {
                height: 100px;
            }
        }
    </style>

    <div class="news-wrapper">
        <!-- News Header -->
        <div class="news-header">
            <h1 class="news-title">📰 <@spring.message "news.list.title"/></h1>
            <p class="news-subtitle"><@spring.message "news.list.subtitle"/></p>
        </div>

        <!-- Search Container -->
        <div class="news-search-container">
            <h2 class="news-search-title"><@spring.message "news.search.title"/></h2>
            <form method="get" action="/news">
                <div class="news-search-row">
                    <div class="news-input-group">
                        <input type="text" name="query" value="${query!}"
                               placeholder="${springMacroRequestContext.getMessage('news.search.placeholder')}"/>
                    </div>
                    <div class="news-select-group">
                        <div class="news-select-wrapper">
                            <select name="sortBy">
                                <option value="createdAt" <#if sortBy=="createdAt">selected</#if>>
                                    📅 <@spring.message "news.sort.createdAt"/>
                                </option>
                                <option value="viewCount" <#if sortBy=="viewCount">selected</#if>>
                                    👁 <@spring.message "news.sort.viewCount"/>
                                </option>
                                <option value="shareCount" <#if sortBy=="shareCount">selected</#if>>
                                    📤 <@spring.message "news.sort.shareCount"/>
                                </option>
                            </select>
                        </div>
                        <div class="news-select-wrapper">
                            <select name="sortDirection">
                                <option value="DESC" <#if sortDirection=="DESC">selected</#if>>
                                    ⬇️ <@spring.message "news.sort.newFirst"/>
                                </option>
                                <option value="ASC" <#if sortDirection=="ASC">selected</#if>>
                                    ⬆️ <@spring.message "news.sort.oldFirst"/>
                                </option>
                            </select>
                        </div>
                        <div class="news-select-wrapper">
                            <select name="language" class="language-selector">
                                <option value="ru" <#if currentLang=="ru">selected</#if>>🇷🇺 Русский</option>
                                <option value="en" <#if currentLang=="en">selected</#if>>🇺🇸 English</option>
                                <option value="ky" <#if currentLang=="ky">selected</#if>>🇰🇬 Кыргызча</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="news-search-button">
                        🔍 <@spring.message "news.search.apply"/>
                    </button>
                </div>
            </form>
        </div>

        <!-- News List -->
        <#if news?? && news.content?size gt 0>
            <div class="news-grid">
                <#list news.content as p>
                    <article class="news-card" onclick="window.location.href='/news/${p.id}?language=${currentLang}'" tabindex="0">
                        <div class="news-image-container">
                            <#if p.mainImage??>
                                <img src="/api/files/${p.mainImageId}/view" alt="News Image">
                            <#elseif p.newsFiles?? && p.newsFiles?size gt 0>
                                <img src="/api/files/${p.newsFiles[0].id}/view" alt="News Image">
                            <#else>
                                <div class="news-placeholder">📰</div>
                            </#if>
                        </div>
                        <div class="news-content">
                            <#assign translation = (p.newsTranslations?first)!>
                            <#if translation??>
                                <#if translation.title?? && translation.title?has_content>
                                    <h3 class="news-title-card">${translation.title}</h3>
                                </#if>
                                <#if translation.description?? && translation.description?has_content>
                                    <p class="news-description">${translation.description}</p>
                                </#if>
                                <div class="news-meta">
                                    <span><i class="fas fa-calendar-alt"></i> ${p.formattedDate}</span>
                                    <span><i class="fas fa-eye"></i> ${p.viewCount!0}</span>
                                </div>
                            </#if>
                        </div>
                    </article>
                </#list>
            </div>
        <#else>
            <div class="news-grid">
                <#if news?? && news.content?size == 0>
                    <div class="news-no-posts">
                        <@spring.message "news.notFound.title"/>
                        <div style="margin-top: 10px; font-size: 1rem; opacity: 0.8;">
                            <@spring.message "news.notFound.message"/>
                        </div>
                    </div>
                <#else>
                    <div class="news-loader">
                        <div class="news-spinner"></div>
                        <span><@spring.message "news.loading"/></span>
                    </div>
                </#if>
            </div>
        </#if>

        <!-- Subscription Section -->
        <div class="news-subscription">
            <form action="/notifications/${(isBlogSubscribed!false)?then('unsubscribe', 'subscribe')}" method="post">
                <@main.csrf/>
                <input type="hidden" name="type" value="NEWS">
                <#if (isAuthenticated!false) && (isBlogSubscribed!false)>
                    <button class="news-subscription-button unsubscribe">
                        <i class="fas fa-bell-slash"></i> <@spring.message "news.subscription.unsubscribe"/>
                    </button>
                <#else>
                    <button class="news-subscription-button subscribe">
                        <i class="fas fa-bell"></i> <@spring.message "news.subscription.subscribe"/>
                    </button>
                </#if>
            </form>
        </div>

        <!-- Pagination -->
        <#if news?? && news.content?size gt 0 && (news.hasPrevious() || news.hasNext())>
            <div class="news-pagination">
                <#if news.hasPrevious()>
                    <a href="?page=${page - 1}&size=${size}&query=${query!}&sortBy=${sortBy}&sortDirection=${sortDirection}&language=${currentLang!'ru'}"
                       class="news-pagination-btn prev">
                        <i class="fas fa-chevron-left"></i> <@spring.message "pagination.previous"/>
                    </a>
                </#if>
                <div class="news-pagination-info">
                    <@spring.message "pagination.page"/> ${page + 1}
                </div>
                <#if news.hasNext()>
                    <a href="?page=${page + 1}&size=${size}&query=${query!}&sortBy=${sortBy}&sortDirection=${sortDirection}&language=${currentLang!'ru'}"
                       class="news-pagination-btn next">
                        <@spring.message "pagination.next"/> <i class="fas fa-chevron-right"></i>
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

            // Keyboard navigation for news items
            document.querySelectorAll('.news-card').forEach(item => {
                item.setAttribute('tabindex', '0');
            });

            document.addEventListener('keydown', function (e) {
                const cards = document.querySelectorAll('.news-card');
                const currentCard = document.querySelector('.news-card:focus');
                if (!currentCard && cards.length > 0 && ['ArrowDown', 'ArrowUp'].includes(e.key)) {
                    cards[0].focus();
                    e.preventDefault();
                    return;
                }

                const currentIndex = Array.from(cards).indexOf(currentCard);
                let newIndex = currentIndex;

                switch (e.key) {
                    case 'ArrowUp':
                        newIndex = currentIndex > 0 ? currentIndex - 1 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'ArrowDown':
                        newIndex = currentIndex < cards.length - 1 ? currentIndex + 1 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'Enter':
                        if (currentCard) {
                            currentCard.click();
                        }
                        e.preventDefault();
                        break;
                }

                if (newIndex !== currentIndex && cards[newIndex]) {
                    cards[newIndex].focus();
                    cards[newIndex].scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            });

            // Subscription form handler
            const subscriptionForm = document.querySelector('form[action*="/notifications/"]');
            if (subscriptionForm) {
                const subscriptionButton = subscriptionForm.querySelector('button');

                subscriptionForm.addEventListener('submit', async function (e) {
                    e.preventDefault();
                    const isSubscribed = subscriptionButton.classList.contains('subscribe');

                    try {
                        subscriptionButton.disabled = true;
                        subscriptionButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Обработка...';

                        const response = await fetch(subscriptionForm.action, {
                            method: 'POST',
                            body: new FormData(subscriptionForm)
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
                    subscriptionButton.innerHTML = '<i class="fas fa-bell-slash"></i> Отписаться от уведомлений';
                    subscriptionButton.className = 'news-subscription-button unsubscribe';
                    subscriptionButton.disabled = false;
                    subscriptionForm.action = subscriptionForm.action.replace('/subscribe', '/unsubscribe');
                }

                function updateButtonToSubscribe() {
                    subscriptionButton.innerHTML = '<i class="fas fa-bell"></i> Подписаться на уведомления';
                    subscriptionButton.className = 'news-subscription-button subscribe';
                    subscriptionButton.disabled = false;
                    subscriptionForm.action = subscriptionForm.action.replace('/unsubscribe', '/subscribe');
                }
            }
            <#noparse>
            function showNotification(message, type = 'info') {
                const existingNotifications = document.querySelectorAll('.notification-toast');
                existingNotifications.forEach(notification => notification.remove());

                const notification = document.createElement('div');
                notification.className = `notification-toast alert-${type}`;
                notification.style.cssText = `
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    z-index: 1050;
                    min-width: 250px;
                    padding: 12px 16px;
                    border-radius: 8px;
                    color: white;
                    font-weight: 600;
                    font-size: 0.9rem;
                    animation: slideInRight 0.3s ease-out;
                    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                `;

                notification.style.background = type === 'success' ? '#48bb78' : type === 'error' ? '#e53e3e' : '#1a73e8';
                notification.innerHTML = `
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <span>${message}</span>
                        <button onclick="this.parentElement.parentElement.remove()"
                                style="background: none; border: none; color: white; font-size: 1rem; cursor: pointer; padding: 0 8px;">×</button>
                    </div>
                `;

                if (!document.querySelector('#notification-styles')) {
                    const style = document.createElement('style');
                    style.id = 'notification-styles';
                    style.textContent = `
                        @keyframes slideInRight {
                            from { transform: translateX(100%); opacity: 0; }
                            to { transform: translateX(0); opacity: 1; }
                        }
                        @keyframes slideOutRight {
                            from { transform: translateX(0); opacity: 1; }
                            to { transform: translateX(100%); opacity: 0; }
                        }
                    `;
                    document.head.appendChild(style);
                }

                document.body.appendChild(notification);
                setTimeout(() => {
                    if (notification.parentNode) {
                        notification.style.animation = 'slideOutRight 0.3s ease-in';
                        setTimeout(() => notification.remove(), 300);
                    }
                }, 3000);
            }
        });
    </script>
</#noparse>
</@main.layout>