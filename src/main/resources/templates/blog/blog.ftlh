<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.layout title="${pageTitle!'Блог'} - MathBilim.kg">
    <#if pageDescription??>
        <meta name="description" content="${pageDescription}">
    </#if>
    <#if pageImage??>
        <meta property="og:image" content="${pageImage}">
        <meta name="twitter:image" content="${pageImage}">
    </#if>
    <meta property="og:title" content="${pageTitle!'Блог'} - MathBilim.kg">
    <meta property="og:description" content="${pageDescription!''}">
    <meta property="og:url" content="${shareUrl!''}">
    <meta property="og:type" content="article">
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="${pageTitle!'Блог'} - MathBilim.kg">
    <meta name="twitter:description" content="${pageDescription!''}">

    <div class="blog-view-container">
        <!-- Хлебные крошки -->
        <nav aria-label="breadcrumb" class="blog-breadcrumb">
            <div class="container">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/">Главная</a></li>
                    <li class="breadcrumb-item"><a href="/blog">Блог</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${blog.title}</li>
                </ol>
            </div>
        </nav>

        <article class="blog-article">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 col-xl-7">
                        <header class="blog-header">
                            <div class="blog-meta">
                                <time class="blog-date" datetime="${blog.createdAt}">
                                    <i class="fas fa-calendar-alt me-2"></i>
                                    ${blog.createdAt}
                                </time>
                                <div class="blog-stats">
                                    <span class="stat-item">
                                        <i class="fas fa-eye me-1"></i>
                                        ${blog.viewCount}
                                    </span>
                                    <span class="stat-item">
                                        <i class="fas fa-share me-1"></i>
                                        ${blog.shareCount}
                                    </span>
                                </div>
                            </div>

                            <h1 class="blog-title">${blog.title}</h1>

                            <#if blog.creator?? && blog.creator.id??>
                                <div class="blog-author" data-author-id="${blog.creator.id}">
                                    <div class="author-info">
                                        <div class="author-avatar">
                                            <div class="skeleton-avatar"></div>
                                        </div>
                                        <div class="author-details">
                                            <div class="skeleton-text skeleton-name"></div>
                                            <div class="skeleton-text skeleton-role"></div>
                                        </div>
                                    </div>
                                </div>
                            </#if>

                            <div class="blog-language-switcher">
                                <a href="/blog/${blog.id}?lang=ru"
                                   class="lang-switch <#if currentLang == 'ru'>'active'</#if>">
                                    Русский
                                </a>
                                <a href="/blog/${blog.id}?lang=ky"
                                   class="lang-switch <#if currentLang == 'ky'>'active'</#if>">
                                    Кыргызча
                                </a>
                                <a href="/blog/${blog.id}?lang=en"
                                   class="lang-switch <#if currentLang == 'en'>'active'</#if>">
                                    English
                                </a>
                            </div>
                        </header>

                        <#if blog.mainImage??>
                            <div class="blog-main-image">
                                <img src="/api/files/${blog.mainImage.id}/view"
                                     alt="${blog.title}"
                                     class="img-fluid rounded shadow-sm">
                            </div>
                        </#if>

                        <div class="blog-content">
                            ${blog.content?no_esc}
                        </div>

                        <div class="blog-actions">
                            <div class="action-group">
                                <button class="btn btn-outline-primary btn-action" onclick="shareBlog()">
                                    <i class="fas fa-share-alt me-2"></i>Поделиться
                                </button>
                                <button class="btn btn-outline-secondary btn-action" onclick="copyLink()">
                                    <i class="fas fa-link me-2"></i>Копировать ссылку
                                </button>
                            </div>

                            <div class="social-share">
                                <span class="share-label">Поделиться в:</span>
                                <div class="social-buttons">
                                    <a href="#" onclick="shareToFacebook(); return false;" class="social-btn facebook">
                                        <i class="fab fa-facebook-f"></i>
                                    </a>
                                    <a href="#" onclick="shareToTwitter(); return false;" class="social-btn twitter">
                                        <i class="fab fa-twitter"></i>
                                    </a>
                                    <a href="#" onclick="shareToTelegram(); return false;" class="social-btn telegram">
                                        <i class="fab fa-telegram-plane"></i>
                                    </a>
                                    <a href="#" onclick="shareToWhatsApp(); return false;" class="social-btn whatsapp">
                                        <i class="fab fa-whatsapp"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </article>

        <#if relatedBlogs?? && (relatedBlogs?size > 0)>
            <section class="related-blogs">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8 col-xl-7">
                            <h3 class="related-title">Похожие статьи</h3>
                            <div class="related-grid">
                                <#list relatedBlogs as relatedBlog>
                                    <article class="related-card"
                                             <#if relatedBlog.creator?? && relatedBlog.creator.id??>data-author-id="${relatedBlog.creator.id}"</#if>>
                                        <a href="/blog/${relatedBlog.id}?lang=${currentLang}" class="related-link">
                                            <#if relatedBlog.mainImage??>
                                                <div class="related-image">
                                                    <img src="/api/files/${relatedBlog.mainImage.id}/view"
                                                         alt="${relatedBlog.title}"
                                                         class="img-fluid"
                                                         loading="lazy">
                                                </div>
                                            <#else>
                                                <div class="related-image-placeholder">
                                                    <i class="fas fa-image"></i>
                                                </div>
                                            </#if>
                                            <div class="related-content">
                                                <h4 class="related-card-title">${relatedBlog.title}</h4>
                                                <time class="related-date">
                                                    ${relatedBlog.createdAt}
                                                </time>
                                                <#if relatedBlog.creator?? && relatedBlog.creator.id??>
                                                    <div class="related-author">
                                                        <div class="skeleton-author-small"></div>
                                                    </div>
                                                </#if>
                                            </div>
                                        </a>
                                    </article>
                                </#list>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </#if>

        <div class="blog-navigation">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 col-xl-7">
                        <div class="nav-actions">
                            <a href="/blog" class="btn btn-outline-primary">
                                <i class="fas fa-arrow-left me-2"></i>К списку блогов
                            </a>
                            <a href="/blog/create" class="btn btn-primary">
                                <i class="fas fa-plus me-2"></i>Создать блог
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="shareModal" tabindex="-1" aria-labelledby="shareModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="shareModalLabel">Поделиться статьей</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="share-options">
                        <a href="#" onclick="shareToFacebook(); return false;" class="share-option">
                            <i class="fab fa-facebook-f"></i>
                            <span>Facebook</span>
                        </a>
                        <a href="#" onclick="shareToTwitter(); return false;" class="share-option">
                            <i class="fab fa-twitter"></i>
                            <span>Twitter</span>
                        </a>
                        <a href="#" onclick="shareToTelegram(); return false;" class="share-option">
                            <i class="fab fa-telegram-plane"></i>
                            <span>Telegram</span>
                        </a>
                        <a href="#" onclick="shareToWhatsApp(); return false;" class="share-option">
                            <i class="fab fa-whatsapp"></i>
                            <span>WhatsApp</span>
                        </a>
                        <a href="#" onclick="copyLink(); return false;" class="share-option">
                            <i class="fas fa-link"></i>
                            <span>Копировать ссылку</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/blog-view.js"></script>

    <script>
        window.blogData = {
            id: ${blog.id},
            title: '${blog.title?js_string}',
            shareUrl: '${shareUrl?js_string}',
            description: '${blog.description?js_string}'
        };
    </script>

    <!-- CSS стили для skeleton loading и асинхронной загрузки -->
    <style>
        /* Skeleton loading стили */
        .skeleton-avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
            background-size: 200% 100%;
            animation: loading 1.5s infinite;
        }

        .skeleton-text {
            height: 16px;
            background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
            background-size: 200% 100%;
            animation: loading 1.5s infinite;
            border-radius: 4px;
            margin-bottom: 8px;
        }

        .skeleton-name {
            width: 120px;
        }

        .skeleton-role {
            width: 60px;
            height: 12px;
        }

        .skeleton-author-small {
            height: 20px;
            width: 100px;
            background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
            background-size: 200% 100%;
            animation: loading 1.5s infinite;
            border-radius: 4px;
            margin-top: 8px;
        }

        @keyframes loading {
            0% {
                background-position: 200% 0;
            }
            100% {
                background-position: -200% 0;
            }
        }

        /* Анимация появления загруженного автора */
        .blog-author {
            transition: opacity 0.3s ease;
        }

        .blog-author.author-loaded {
            animation: fadeInUp 0.5s ease;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Стили для заглушек */
        .related-image-placeholder {
            height: 160px;
            background: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6c757d;
            font-size: 2rem;
        }

        .avatar-placeholder.error {
            background: #ef4444;
            color: white;
        }

        /* Adaptive улучшения для мобильных */
        @media (max-width: 768px) {
            .skeleton-avatar {
                width: 40px;
                height: 40px;
            }

            .skeleton-name {
                width: 100px;
            }

            .skeleton-role {
                width: 50px;
            }
        }
    </style>

</@main.layout>