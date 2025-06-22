<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.layout title="${blog.title!'Блог'} - MathBilim.kg">
    <#if pageImage??>
        <meta property="og:image" content="/api/files/${blog.mainImage.id}/view">
        <meta name="twitter:image" content="/api/files/${blog.mainImage.id}/view">
    </#if>
    <meta property="og:title" content="${blog.title!'Блог'} - MathBilim.kg">
    <meta property="og:url" content="${shareUrl!''}">
    <meta property="og:type" content="article">
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="${blog.title!'Блог'} - MathBilim.kg">

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

                                <#if blog.creator?? && blog.creator.id??>
                                    <span class="blog-author-compact" data-author-id="${blog.creator.id}">
                                        <i class="fas fa-user me-2"></i>
                                        <span class="author-name">Загрузка...</span>
                                    </span>
                                </#if>

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
                                    <a href="#" onclick="copyLink(); return false;" class="social-btn copy-link" id="copyLinkBtn">
                                        <i class="fas fa-link"></i>
                                        <span class="copy-feedback">Скопировано!</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </article>

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


    <script src="/static/js/components/content-view/AuthorManager.js"></script>
    <script src="/static/js/components/content-view/SocialShareManager.js"></script>
    <script src="/static/js/components/content-view/ContentViewManager.js"></script>
    <script src="/static/js/components/content-view/BaseContentViewManager.js"></script>
    <script src="/static/js/blog/BlogViewManager.js"></script>

    <script>
        window.blogData = {
            id: ${blog.id},
            title: '${blog.title?js_string}',
            shareUrl: '${shareUrl?js_string}',
        };
    </script>

    <style>
        .blog-meta {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 2rem;
            margin-bottom: 1.5rem;
            flex-wrap: wrap;
        }

        .blog-date {
            color: #64748b;
            font-size: 0.9rem;
            font-weight: 500;
            display: flex;
            align-items: center;
        }

        .blog-date i {
            color: #2563eb;
        }

        .blog-stats {
            display: flex;
            gap: 1rem;
        }

        .stat-item {
            color: #64748b;
            font-size: 0.875rem;
            display: flex;
            align-items: center;
        }

        .stat-item i {
            color: #9ca3af;
            font-size: 0.8rem;
        }

        .blog-author-compact {
            color: #64748b;
            font-size: 0.9rem;
            font-weight: 500;
            display: flex;
            align-items: center;
        }

        .blog-author-compact i {
            color: #2563eb;
        }

        .blog-author-compact .author-name {
            color: #1e293b;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .blog-author-compact .author-name:hover {
            color: #2563eb;
        }

        .author-loading {
            color: #9ca3af !important;
            font-style: italic;
            animation: pulse 1.5s ease-in-out infinite;
        }

        .author-error {
            color: #ef4444 !important;
        }

        .blog-author-compact.author-loaded .author-name {
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

        @keyframes pulse {
            0%, 100% {
                opacity: 1;
            }
            50% {
                opacity: 0.5;
            }
        }

        .blog-header {
            margin-bottom: 2.5rem;
            text-align: center;
        }

        .blog-title {
            font-size: 2.5rem;
            font-weight: 800;
            color: #1e293b;
            line-height: 1.2;
            margin-bottom: 0;
            max-width: 800px;
            margin-left: auto;
            margin-right: auto;
        }

        @media (max-width: 768px) {
            .blog-meta {
                flex-direction: column;
                gap: 1rem;
            }

            .blog-title {
                font-size: 2rem;
            }
        }

        @media (max-width: 576px) {
            .blog-title {
                font-size: 1.75rem;
            }

            .blog-meta {
                gap: 0.75rem;
            }

            .blog-date,
            .blog-author-compact {
                font-size: 0.85rem;
            }

            .stat-item {
                font-size: 0.8rem;
            }
        }
    </style>

</@main.layout>