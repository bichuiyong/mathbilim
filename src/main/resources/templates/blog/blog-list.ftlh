<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.layout title="Блог - MathBilim.kg">

    <div class="blog-list-container">
        <!-- Заголовок и навигация -->
        <section class="blog-header-section">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-8">
                        <h1 class="page-title">Блог MathBilim.kg</h1>
                        <p class="page-subtitle">
                            Последние статьи о математическом образовании, олимпиадах и научных достижениях
                        </p>
                    </div>
                    <div class="col-lg-4 text-lg-end">
                        <a href="/blog/create" class="btn btn-primary btn-lg">
                            <i class="fas fa-plus me-2"></i>Написать статью
                        </a>
                    </div>
                </div>
            </div>
        </section>

        <!-- Фильтры и сортировка -->
        <section class="blog-filters">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <div class="language-filter">
                            <label class="filter-label">Язык статей:</label>
                            <div class="btn-group" role="group">
                                <a href="/blog?lang=ru" class="btn btn-outline-primary ${currentLang == 'ru' ? 'active' : ''}">Русский</a>
                                <a href="/blog?lang=ky" class="btn btn-outline-primary ${currentLang == 'ky' ? 'active' : ''}">Кыргызча</a>
                                <a href="/blog?lang=en" class="btn btn-outline-primary ${currentLang == 'en' ? 'active' : ''}">English</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 text-md-end">
                        <div class="blog-stats">
                            <span class="stats-text">
                                <i class="fas fa-newspaper me-1"></i>
                                Всего статей: <strong>${totalElements}</strong>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Список блогов -->
        <section class="blog-content">
            <div class="container">
                <#if blogs?? && blogs?size > 0>
                    <div class="blog-masonry-grid">
                        <#list blogs as blog>
                            <#if blog.title?? && blog.title?trim != "">
                                <article class="blog-item ${blog?index % 4 == 0 ? 'blog-item-large' : (blog?index % 3 == 0 ? 'blog-item-tall' : 'blog-item-small')}">
                                    <a href="/blog/${blog.id}?lang=${currentLang}" class="blog-link">
                                        <#if blog.mainImage??>
                                            <img src="/api/files/${blog.mainImage.id}/view"
                                                 alt="${blog.title}"
                                                 loading="lazy">
                                        <#else>
                                            <div class="blog-placeholder">
                                                <i class="fas fa-file-alt"></i>
                                            </div>
                                        </#if>

                                        <div class="blog-overlay">
                                            <div class="blog-category">
                                                <i class="fas fa-tag me-1"></i>Блог
                                            </div>

                                            <h2 class="blog-title">${blog.title}</h2>

                                            <#if blog.content??>
                                                <p class="blog-excerpt">
                                                    ${blog.getExcerpt(120)}
                                                </p>
                                            </#if>

                                            <div class="blog-meta">
                                                <time class="blog-date">${blog.createdAt?string("dd MMM yyyy")}</time>
                                                <div class="blog-stats">
                                                    <span><i class="fas fa-eye me-1"></i>${blog.viewCount}</span>
                                                    <span><i class="fas fa-share me-1"></i>${blog.shareCount}</span>
                                                </div>
                                            </div>

                                            <#if blog.creator?? && blog.creator.fullName??>
                                                <div class="blog-author">
                                                    <div class="author-avatar-small">
                                                        <#if blog.creator.profileImage??>
                                                            <img src="/api/files/${blog.creator.profileImage.id}/view"
                                                                 alt="${blog.creator.fullName}">
                                                        <#else>
                                                            <div class="avatar-placeholder-small">
                                                                ${blog.creator.fullName?substring(0, 1)?upper_case}
                                                            </div>
                                                        </#if>
                                                    </div>
                                                    <span class="author-name">${blog.creator.fullName}</span>
                                                </div>
                                            </#if>
                                        </div>
                                    </a>
                                </article>
                            </#if>
                        </#list>
                    </div>

                    <form action="/notifications/subscribe" method="post">
                        <@main.csrf/>
                        <input type="hidden" name="type" value="BLOG">
                        <button class="btn btn-outline-primary">📩 Подписаться на уведомления</button>
                    </form>


                    <!-- Пагинация -->
                    <#if totalPages > 1>
                        <nav class="blog-pagination" aria-label="Навигация по страницам блога">
                            <ul class="pagination justify-content-center">
                                <!-- Предыдущая страница -->
                                <#if currentPage > 0>
                                    <li class="page-item">
                                        <a class="page-link" href="/blog?page=${currentPage - 1}&lang=${currentLang}">
                                            <i class="fas fa-chevron-left me-1"></i>Назад
                                        </a>
                                    </li>
                                <#else>
                                    <li class="page-item disabled">
                                        <span class="page-link">
                                            <i class="fas fa-chevron-left me-1"></i>Назад
                                        </span>
                                    </li>
                                </#if>

                                <!-- Номера страниц -->
                                <#assign startPage = (currentPage - 2 < 0) ? 0 : (currentPage - 2)>
<#assign endPage = (currentPage + 2 > totalPages - 1) ? (totalPages - 1) : (currentPage + 2)>


                                <#if startPage > 0>
                                    <li class="page-item">
                                        <a class="page-link" href="/blog?page=0&lang=${currentLang}">1</a>
                                    </li>
                                    <#if startPage > 1>
                                        <li class="page-item disabled">
                                            <span class="page-link">...</span>
                                        </li>
                                    </#if>
                                </#if>

                                <#list startPage..endPage as pageNum>
                                    <li class="page-item  <#if currentPage == pageNum>'active'</#if>">
                                        <a class="page-link" href="/blog?page=${pageNum}&lang=${currentLang}">
                                            ${pageNum + 1}
                                        </a>
                                    </li>
                                </#list>

                                <#if endPage < totalPages - 1>
                                    <#if endPage < totalPages - 2>
                                        <li class="page-item disabled">
                                            <span class="page-link">...</span>
                                        </li>
                                    </#if>
                                    <li class="page-item">
                                        <a class="page-link" href="/blog?page=${totalPages - 1}&lang=${currentLang}">
                                            ${totalPages}
                                        </a>
                                    </li>
                                </#if>

                                <!-- Следующая страница -->
                                <#if currentPage < totalPages - 1>
                                    <li class="page-item">
                                        <a class="page-link" href="/blog?page=${currentPage + 1}&lang=${currentLang}">
                                            Вперед<i class="fas fa-chevron-right ms-1"></i>
                                        </a>
                                    </li>
                                <#else>
                                    <li class="page-item disabled">
                                        <span class="page-link">
                                            Вперед<i class="fas fa-chevron-right ms-1"></i>
                                        </span>
                                    </li>
                                </#if>
                            </ul>
                        </nav>
                    </#if>

                <#else>
                    <!-- Пустое состояние -->
                    <div class="empty-state">
                        <div class="empty-icon">
                            <i class="fas fa-file-alt"></i>
                        </div>
                        <h3 class="empty-title">Пока нет статей</h3>
                        <p class="empty-text">
                            Статьи на выбранном языке еще не опубликованы.
                            Попробуйте выбрать другой язык или станьте первым автором!
                        </p>
                        <a href="/blog/create" class="btn btn-primary btn-lg">
                            <i class="fas fa-plus me-2"></i>Написать первую статью
                        </a>
                    </div>
                </#if>
            </div>
        </section>
    </div>

    <style>
        /* Дополнительные стили для списка блогов */
        .blog-list-container {
            background: #f8fafc;
            min-height: 100vh;
        }

        .blog-header-section {
            background: linear-gradient(135deg, #2563eb 0%, #3b82f6 100%);
            color: white;
            padding: 4rem 0 3rem;
        }

        .page-title {
            font-size: 3rem;
            font-weight: 800;
            margin-bottom: 1rem;
        }

        .page-subtitle {
            font-size: 1.25rem;
            opacity: 0.9;
            margin-bottom: 0;
        }

        .blog-filters {
            background: white;
            padding: 2rem 0;
            border-bottom: 1px solid #e2e8f0;
        }

        .filter-label {
            font-weight: 600;
            color: #374151;
            margin-right: 1rem;
        }

        .blog-stats .stats-text {
            color: #6b7280;
            font-size: 0.95rem;
        }

        .blog-content {
            padding: 3rem 0;
        }

        .blog-placeholder {
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, #f1f5f9 0%, #e2e8f0 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: #9ca3af;
            font-size: 3rem;
        }

        .empty-state {
            text-align: center;
            padding: 4rem 2rem;
            background: white;
            border-radius: 12px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .empty-icon {
            font-size: 4rem;
            color: #d1d5db;
            margin-bottom: 2rem;
        }

        .empty-title {
            font-size: 1.5rem;
            font-weight: 700;
            color: #374151;
            margin-bottom: 1rem;
        }

        .empty-text {
            color: #6b7280;
            font-size: 1.1rem;
            margin-bottom: 2rem;
            max-width: 500px;
            margin-left: auto;
            margin-right: auto;
        }

        .blog-pagination {
            margin-top: 3rem;
        }

        .author-avatar-small {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            overflow: hidden;
            margin-right: 0.5rem;
        }

        .author-avatar-small img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .avatar-placeholder-small {
            width: 100%;
            height: 100%;
            background: #2563eb;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.75rem;
            font-weight: 600;
        }

        .blog-author {
            display: flex;
            align-items: center;
            margin-top: 1rem;
            font-size: 0.875rem;
        }

        .author-name {
            color: rgba(255, 255, 255, 0.9);
            font-weight: 500;
        }

        @media (max-width: 768px) {
            .page-title {
                font-size: 2rem;
            }

            .page-subtitle {
                font-size: 1rem;
            }

            .blog-header-section {
                padding: 2rem 0;
                text-align: center;
            }

            .blog-filters .row {
                text-align: center;
            }

            .blog-filters .col-md-6:first-child {
                margin-bottom: 1rem;
            }

            .language-filter {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 1rem;
            }
        }
    </style>

</@main.layout>