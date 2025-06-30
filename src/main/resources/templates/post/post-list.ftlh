<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        .content-wrapper {
            max-width: 975px;
            margin: 0 auto;
            padding: 20px;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            color: #262626;
            line-height: 1.4;
        }

        /* Улучшенные стили для формы поиска */
        .search-container {
            background: white;
            border: 1px solid #dbdbdb;
            border-radius: 12px;
            margin-bottom: 28px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .search-header {
            padding: 16px;
            border-bottom: 1px solid #efefef;
            background: #fafafa;
        }

        .search-title {
            font-weight: 600;
            font-size: 16px;
            color: #262626;
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .search-title::before {
            content: "🔍";
            font-size: 18px;
        }

        .content-wrapper form {
            padding: 16px;
        }

        .search-row {
            display: flex;
            gap: 12px;
            margin-bottom: 16px;
            flex-wrap: wrap;
            align-items: center;
        }

        .search-input-group {
            flex: 1;
            min-width: 200px;
            position: relative;
        }

        .search-input-group input {
            width: 100%;
            padding: 12px 16px;
            border: 1px solid #dbdbdb;
            border-radius: 8px;
            font-size: 14px;
            outline: none;
            transition: all 0.2s ease;
            background: white;
        }

        .search-input-group input:focus {
            border-color: #0095f6;
            box-shadow: 0 0 0 2px rgba(0, 149, 246, 0.2);
            background: white;
        }

        .select-group {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }

        .select-wrapper {
            position: relative;
            min-width: 120px;
        }

        .select-wrapper select {
            width: 100%;
            padding: 12px 16px;
            padding-right: 36px;
            border: 1px solid #dbdbdb;
            border-radius: 8px;
            font-size: 14px;
            background: white;
            cursor: pointer;
            outline: none;
            transition: all 0.2s ease;
            appearance: none;
        }

        .select-wrapper::after {
            content: "▼";
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            pointer-events: none;
            color: #8e8e8e;
            font-size: 12px;
        }

        .select-wrapper select:focus {
            border-color: #0095f6;
            box-shadow: 0 0 0 2px rgba(0, 149, 246, 0.2);
        }

        .search-button {
            padding: 12px 24px;
            background: linear-gradient(135deg, #0095f6, #0074cc);
            border: none;
            border-radius: 8px;
            color: white;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.2s ease;
            box-shadow: 0 2px 4px rgba(0, 149, 246, 0.3);
            min-width: 100px;
        }

        .search-button:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 149, 246, 0.4);
        }

        .search-button:active {
            transform: translateY(0);
        }

        /* Instagram-style Grid */
        .posts-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 4px;
            margin-bottom: 32px;
        }

        .post-item {
            position: relative;
            aspect-ratio: 1;
            overflow: hidden;
            cursor: pointer;
            background: #f8f8f8;
            border-radius: 4px;
            transition: all 0.2s ease;
        }

        .post-item:hover {
            transform: scale(1.02);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
            z-index: 1;
        }

        .post-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: all 0.2s ease;
        }

        .post-item:hover img {
            filter: brightness(0.8);
        }

        .post-overlay {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.3);
            display: flex;
            align-items: center;
            justify-content: center;
            opacity: 0;
            transition: opacity 0.2s ease;
            flex-direction: column;
            gap: 8px;
        }

        .post-item:hover .post-overlay {
            opacity: 1;
        }

        .post-stats {
            display: flex;
            align-items: center;
            gap: 16px;
            color: white;
            font-weight: 600;
            font-size: 16px;
        }

        .stat-item {
            display: flex;
            align-items: center;
            gap: 6px;
        }

        .stat-item::before {
            font-size: 18px;
        }

        .stat-views::before {
            content: "👁";
        }

        .stat-shares::before {
            content: "📤";
        }

        .post-title-overlay {
            color: white;
            font-weight: 600;
            font-size: 14px;
            text-align: center;
            padding: 0 12px;
            max-width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .multiple-images-indicator {
            position: absolute;
            top: 8px;
            right: 8px;
            background: rgba(0, 0, 0, 0.7);
            color: white;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-weight: bold;
        }

        .multiple-images-indicator::before {
            content: "📷";
            font-size: 10px;
        }

        .loader {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 200px;
            color: #8e8e8e;
            font-size: 16px;
            grid-column: 1 / -1;
        }

        .spinner {
            border: 4px solid #f3f3f3;
            border-top: 4px solid #0095f6;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin-bottom: 16px;
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        .no-posts {
            grid-column: 1 / -1;
            text-align: center;
            padding: 60px 20px;
            color: #8e8e8e;
            font-size: 18px;
        }

        .no-posts::before {
            content: "📷";
            display: block;
            font-size: 48px;
            margin-bottom: 16px;
            opacity: 0.5;
        }

        /* Улучшенная пагинация */
        .pagination-container {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 12px;
            margin-top: 32px;
            padding: 20px 0;
        }

        .pagination-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 12px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.2s ease;
            min-width: 80px;
            gap: 8px;
        }

        .pagination-btn.prev {
            background: #f0f0f0;
            color: #262626;
            border: 1px solid #dbdbdb;
        }

        .pagination-btn.prev:hover {
            background: #e0e0e0;
            transform: translateY(-1px);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-decoration: none;
        }

        .pagination-btn.next {
            background: linear-gradient(135deg, #0095f6, #0074cc);
            color: white;
            border: none;
            box-shadow: 0 2px 4px rgba(0, 149, 246, 0.3);
        }

        .pagination-btn.next:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 149, 246, 0.4);
            text-decoration: none;
        }

        .pagination-btn::before {
            font-size: 12px;
        }

        .pagination-btn.prev::before {
            content: "←";
        }

        .pagination-btn.next::after {
            content: "→";
        }

        .pagination-info {
            background: white;
            border: 1px solid #dbdbdb;
            border-radius: 8px;
            padding: 8px 16px;
            font-size: 14px;
            color: #8e8e8e;
            font-weight: 500;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 12px;
            }

            .search-container {
                border-radius: 8px;
                margin-bottom: 20px;
            }

            .search-row {
                flex-direction: column;
                gap: 12px;
            }

            .search-input-group {
                min-width: unset;
            }

            .select-group {
                width: 100%;
                justify-content: space-between;
            }

            .select-wrapper {
                flex: 1;
                min-width: unset;
            }

            .posts-grid {
                gap: 2px;
            }

            .pagination-container {
                padding: 16px 0;
                gap: 8px;
            }

            .pagination-btn {
                padding: 10px 16px;
                font-size: 13px;
                min-width: 70px;
            }

            .pagination-info {
                padding: 6px 12px;
                font-size: 13px;
            }
        }

        @media (max-width: 480px) {
            .posts-grid {
                grid-template-columns: repeat(2, 1fr);
                gap: 2px;
            }

            .post-stats {
                font-size: 14px;
                gap: 12px;
            }

            .post-title-overlay {
                font-size: 12px;
            }
        }
    </style>

    <div class="content-wrapper">
        <div class="search-container">
            <div class="search-header">
                <div class="search-title">Поиск и фильтры</div>
            </div>
            <form method="get" action="/posts">
                <div class="search-row">
                    <div class="search-input-group">
                        <input type="text" name="query" value="${query!}" placeholder="Найти посты..."/>
                    </div>
                </div>

                <div class="search-row">
                    <div class="select-group">
                        <div class="select-wrapper">
                            <select name="sortBy">
                                <option value="createdAt" <#if sortBy=="createdAt">selected</#if>>По дате</option>
                                <option value="viewCount" <#if sortBy=="viewCount">selected</#if>>По просмотрам</option>
                                <option value="shareCount" <#if sortBy=="shareCount">selected</#if>>По репостам</option>
                            </select>
                        </div>

                        <div class="select-wrapper">
                            <select name="sortDirection">
                                <option value="DESC" <#if sortDirection=="DESC">selected</#if>>Сначала новые</option>
                                <option value="ASC" <#if sortDirection=="ASC">selected</#if>>Сначала старые</option>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="search-button">Применить</button>
                </div>

                <input type="hidden" name="lang" value="${currentLang!'ru'}"/>
            </form>
        </div>

        <#if posts?? && posts.content?size gt 0>
            <div class="posts-grid">
                <#list posts.content as p>
                    <div class="post-item" onclick="window.location.href='/posts/${p.id}'">
                        <#if p.mainImage??>
                            <img src="/api/files/${p.mainImageId}/view" alt="Post Image">
                        <#elseif p.postFiles?? && p.postFiles?size gt 0>
                            <img src="/api/files/${p.postFiles[0].id}/view" alt="Post Image">
                        <#else>
                            <div style="width: 100%; height: 100%; background: linear-gradient(45deg, #f0f0f0, #e0e0e0); display: flex; align-items: center; justify-content: center; color: #999; font-size: 24px;">📷</div>
                        </#if>

                        <#assign totalImages = (p.postFiles?size) + (p.mainImage?has_content?then(1, 0))>
                        <#if totalImages gt 1>
                            <div class="multiple-images-indicator"></div>
                        </#if>

                        <div class="post-overlay">
                            <div class="post-stats">
                                <div class="stat-item stat-views">${p.viewCount!0}</div>
                                <div class="stat-item stat-shares">${p.shareCount!0}</div>
                            </div>
                            <#assign translation = p.postTranslations?first>
                            <#if translation.title?? && translation.title?has_content>
                                <div class="post-title-overlay">${translation.title}</div>
                            </#if>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="posts-grid">
                <#if posts?? && posts.content?size == 0>
                    <div class="no-posts">
                        Посты не найдены
                        <div style="margin-top: 8px; font-size: 14px;">Попробуйте изменить параметры поиска</div>
                    </div>
                <#else>
                    <div class="loader">
                        <div class="spinner"></div>
                        <span>Посты загружаются, пожалуйста подождите...</span>
                    </div>
                </#if>
            </div>
        </#if>

        <#if posts?? && posts.content?size gt 0 && (posts.hasPrevious() || posts.hasNext())>
            <div class="pagination-container">
                <#if posts.hasPrevious()>
                    <a href="?page=${page - 1}&size=${size}&query=${query!}&sortBy=${sortBy}&sortDirection=${sortDirection}&lang=${currentLang!'ru'}"
                       class="pagination-btn prev">
                        Назад
                    </a>
                </#if>

                <div class="pagination-info">
                    Страница ${page + 1}
                </div>

                <#if posts.hasNext()>
                    <a href="?page=${page + 1}&size=${size}&query=${query!}&sortBy=${sortBy}&sortDirection=${sortDirection}&lang=${currentLang!'ru'}"
                       class="pagination-btn next">
                        Вперёд
                    </a>
                </#if>
            </div>
        </#if>
    </div>

    <script>
        function switchLanguage(langCode) {
            const url = new URL(window.location);
            url.searchParams.set('lang', langCode);
            window.location.href = url.toString();
        }

        document.addEventListener('DOMContentLoaded', function() {
            const langButtons = document.querySelectorAll('[data-lang]');
            langButtons.forEach(button => {
                button.addEventListener('click', function(e) {
                    e.preventDefault();
                    const langCode = this.getAttribute('data-lang');
                    switchLanguage(langCode);
                });
            });

            const currentLang = new URLSearchParams(window.location.search).get('lang') || 'ru';
            const activeLangButton = document.querySelector(`[data-lang="${currentLang}"]`);
            if (activeLangButton) {
                activeLangButton.classList.add('active');
            }

            document.addEventListener('keydown', function(e) {
                const posts = document.querySelectorAll('.post-item');
                const currentPost = document.querySelector('.post-item:focus');

                if (!currentPost && posts.length > 0) {
                    if (e.key === 'ArrowDown' || e.key === 'ArrowUp' || e.key === 'ArrowLeft' || e.key === 'ArrowRight') {
                        posts[0].focus();
                        e.preventDefault();
                    }
                    return;
                }

                const currentIndex = Array.from(posts).indexOf(currentPost);
                let newIndex = currentIndex;

                switch(e.key) {
                    case 'ArrowLeft':
                        newIndex = currentIndex > 0 ? currentIndex - 1 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'ArrowRight':
                        newIndex = currentIndex < posts.length - 1 ? currentIndex + 1 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'ArrowUp':
                        newIndex = currentIndex >= 3 ? currentIndex - 3 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'ArrowDown':
                        newIndex = currentIndex + 3 < posts.length ? currentIndex + 3 : currentIndex;
                        e.preventDefault();
                        break;
                    case 'Enter':
                        if (currentPost) {
                            currentPost.click();
                        }
                        e.preventDefault();
                        break;
                }

                if (newIndex !== currentIndex && posts[newIndex]) {
                    posts[newIndex].focus();
                }
            });

            document.querySelectorAll('.post-item').forEach(item => {
                item.setAttribute('tabindex', '0');
            });
        });
    </script>
</@main.layout>