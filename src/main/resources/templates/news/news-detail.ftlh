<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', sans-serif;
            background: #f8f9fa;
            color: #333333;
            line-height: 1.6;
            min-height: 100vh;
        }

        .content-wrapper {
            max-width: 1200px;
            margin: 0 auto;
            padding: 24px 16px;
        }

        /* Кнопка возврата */
        .back-btn {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            margin-bottom: 24px;
            padding: 10px 16px;
            background: #ffffff;
            color: #007bff;
            font-weight: 500;
            border-radius: 8px;
            text-decoration: none;
            transition: all 0.2s ease;
            border: 1px solid #e0e0e0;
            cursor: pointer;
        }

        .back-btn:hover {
            background: #f1f5f9;
            border-color: #007bff;
            color: #0056b3;
        }

        /* Основная карточка новости */
        .news-card {
            background: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
            margin-bottom: 24px;
            transition: transform 0.2s ease;
        }

        .news-card:hover {
            transform: translateY(-4px);
        }

        /* Заголовок с автором */
        .news-header {
            padding: 16px 20px;
            border-bottom: 1px solid #e9ecef;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .author-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: #007bff;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 600;
            font-size: 16px;
            flex-shrink: 0;
        }

        .author-info {
            flex: 1;
        }

        .author-name {
            font-weight: 600;
            font-size: 15px;
            color: #333333;
            text-decoration: none;
            display: block;
            margin-bottom: 2px;
            transition: color 0.2s ease;
        }

        .author-name:hover {
            color: #007bff;
            text-decoration: none;
        }

        .publication-date {
            font-size: 13px;
            color: #6c757d;
            font-weight: 400;
        }

        /* Заголовок новости */
        .news-title {
            padding: 16px 20px;
            font-size: 24px;
            font-weight: 700;
            color: #212529;
            line-height: 1.3;
        }

        /* Галерея изображений */
        .news-gallery {
            position: relative;
            margin-bottom: 16px;
            background: #f1f3f5;
            height: 400px;
        }

        .news-gallery.single-image .carousel-control-prev,
        .news-gallery.single-image .carousel-control-next,
        .news-gallery.single-image .carousel-indicators,
        .news-gallery.single-image .image-counter {
            display: none !important;
        }

        .news-gallery .carousel-inner {
            height: 100%;
        }

        .news-gallery .carousel-item {
            height: 100%;
        }

        .news-gallery .carousel-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            user-select: none;
        }

        .news-gallery .carousel-control-prev,
        .news-gallery .carousel-control-next {
            width: 40px;
            height: 40px;
            background: rgba(0, 0, 0, 0.5);
            border-radius: 50%;
            top: 50%;
            transform: translateY(-50%);
            transition: background 0.2s ease;
        }

        .news-gallery .carousel-control-prev {
            left: 12px;
        }

        .news-gallery .carousel-control-next {
            right: 12px;
        }

        .news-gallery .carousel-control-prev:hover,
        .news-gallery .carousel-control-next:hover {
            background: rgba(0, 0, 0, 0.7);
        }

        .news-gallery .carousel-control-prev-icon,
        .news-gallery .carousel-control-next-icon {
            width: 16px;
            height: 16px;
        }

        .news-gallery .carousel-indicators {
            bottom: 12px;
        }

        .news-gallery .carousel-indicators [data-bs-target] {
            width: 10px;
            height: 10px;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.5);
            margin: 0 5px;
        }

        .news-gallery .carousel-indicators .active {
            background-color: white;
        }

        .image-counter {
            position: absolute;
            top: 12px;
            right: 12px;
            background: rgba(0, 0, 0, 0.6);
            color: white;
            padding: 6px 12px;
            border-radius: 12px;
            font-size: 13px;
            font-weight: 500;
        }

        /* Контент новости */
        .news-content {
            padding: 16px 20px;
            position: relative;
        }

        .news-text {
            font-size: 24px;
            line-height: 1.8;
            color: #343a40;
            margin-bottom: 16px;
            white-space: pre-wrap;
            word-break: break-word;
            max-height: 300px;
            overflow: hidden;
            transition: max-height 0.3s ease;
        }

        .news-text.expanded {
            max-height: none;
        }

        .read-more-btn {
            display: none;
            background: #007bff;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            margin: 8px 0;
            transition: background 0.2s ease;
        }

        .read-more-btn:hover {
            background: #0056b3;
        }

        .news-text.show-read-more ~ .read-more-btn {
            display: inline-block;
        }

        /* Стили для содержимого из редактора */
        .news-text p {
            margin-bottom: 16px;
            font-size: 15px;
            line-height: 1.6;
            color: #343a40;
        }

        .news-text h1,
        .news-text h2,
        .news-text h3,
        .news-text h4,
        .news-text h5,
        .news-text h6 {
            margin: 16px 0 8px;
            font-weight: 600;
            color: #212529;
        }

        .news-text h1 { font-size: 22px; }
        .news-text h2 { font-size: 20px; }
        .news-text h3 { font-size: 18px; }
        .news-text h4 { font-size: 16px; }
        .news-text h5 { font-size: 15px; }
        .news-text h6 { font-size: 14px; }

        .news-text ul,
        .news-text ol {
            margin: 12px 0;
            padding-left: 24px;
        }

        .news-text li {
            margin-bottom: 8px;
            font-size: 15px;
            color: #343a40;
        }

        .news-text blockquote {
            border-left: 4px solid #007bff;
            padding: 8px 16px;
            margin: 16px 0;
            background: #f8f9fa;
            font-style: italic;
            color: #343a40;
        }

        .news-text a {
            color: #007bff;
            text-decoration: none;
            transition: color 0.2s ease;
        }

        .news-text a:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        .news-text img {
            max-width: 100%;
            height: auto;
            margin: 12px 0;
            border-radius: 8px;
        }

        /* Метаинформация */
        .news-meta {
            display: flex;
            align-items: center;
            gap: 16px;
            flex-wrap: wrap;
            padding: 12px 20px;
            background: #f8f9fa;
            border-top: 1px solid #e9ecef;
        }

        .meta-item {
            display: flex;
            align-items: center;
            gap: 6px;
            font-size: 13px;
            color: #6c757d;
            font-weight: 400;
        }

        .meta-item .icon {
            font-size: 14px;
        }

        /* Модальное окно комментариев */
        .comments-modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 1000;
            display: none;
            align-items: center;
            justify-content: center;
            opacity: 0;
            transition: opacity 0.2s ease;
        }

        .comments-modal-overlay.show {
            display: flex;
            opacity: 1;
        }

        .comments-modal {
            background: #ffffff;
            border-radius: 12px;
            width: 90%;
            max-width: 500px;
            max-height: 80vh;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            transform: scale(0.95);
            transition: transform 0.2s ease;
        }

        .comments-modal-overlay.show .comments-modal {
            transform: scale(1);
        }

        .comments-modal-header {
            padding: 16px 20px;
            border-bottom: 1px solid #e9ecef;
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: #ffffff;
            position: sticky;
            top: 0;
            z-index: 10;
        }

        .comments-modal-title {
            font-weight: 600;
            font-size: 16px;
            color: #212529;
            margin: 0;
        }

        .comments-modal-close {
            background: #f1f3f5;
            border: none;
            width: 32px;
            height: 32px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: all 0.2s ease;
            color: #6c757d;
        }

        .comments-modal-close:hover {
            background: #e9ecef;
            color: #343a40;
        }

        .comments-modal-body {
            flex: 1;
            overflow-y: auto;
            min-height: 150px;
            max-height: 350px;
        }

        .comments-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .comment-item {
            padding: 12px 20px;
            border-bottom: 1px solid #f1f3f5;
            display: flex;
            align-items: flex-start;
            gap: 12px;
        }

        .comment-item:hover {
            background: #f8f9fa;
        }

        .comment-avatar {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background: #007bff;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 500;
            font-size: 13px;
            flex-shrink: 0;
        }

        .comment-content {
            flex: 1;
            min-width: 0;
        }

        .comment-author {
            font-weight: 500;
            font-size: 14px;
            color: #212529;
            margin-bottom: 2px;
        }

        .comment-text {
            font-size: 14px;
            color: #343a40;
            line-height: 1.5;
            word-break: break-word;
            margin-bottom: 6px;
        }

        .comment-meta {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .comment-time {
            font-size: 12px;
            color: #6c757d;
            font-weight: 400;
        }

        .comment-menu {
            position: relative;
            margin-left: auto;
            flex-shrink: 0;
        }

        .comment-menu-btn {
            background: none;
            border: none;
            padding: 6px;
            cursor: pointer;
            color: #6c757d;
            border-radius: 4px;
            transition: all 0.2s;
        }

        .comment-menu-btn:hover {
            background: #f1f3f5;
            color: #343a40;
        }

        .comment-menu-dropdown {
            position: absolute;
            top: 100%;
            right: 0;
            background: #ffffff;
            border: 1px solid #e9ecef;
            border-radius: 8px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
            min-width: 120px;
            z-index: 1000;
            overflow: hidden;
        }

        .comment-menu-item {
            width: 100%;
            padding: 8px 12px;
            background: none;
            border: none;
            text-align: left;
            cursor: pointer;
            color: #6c757d;
            font-size: 13px;
            font-weight: 400;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: all 0.2s;
        }

        .comment-menu-item:hover {
            background: #f8f9fa;
            color: #dc3545;
        }

        /* Футер модального окна */
        .comments-modal-footer {
            padding: 12px 20px;
            border-top: 1px solid #e9ecef;
            background: #ffffff;
            position: sticky;
            bottom: 0;
        }

        .comment-input-wrapper {
            display: flex;
            align-items: flex-end;
            gap: 12px;
            background: #f1f3f5;
            border-radius: 12px;
            padding: 8px 12px;
            border: 1px solid #e9ecef;
            transition: border-color 0.2s ease;
        }

        .comment-input-wrapper:focus-within {
            border-color: #007bff;
        }

        .comment-input-wrapper textarea {
            flex: 1;
            border: none;
            outline: none;
            font-size: 14px;
            color: #343a40;
            background: transparent;
            resize: none;
            max-height: 80px;
            min-height: 20px;
            line-height: 20px;
            padding: 0;
            font-family: inherit;
        }

        .comment-input-wrapper textarea::placeholder {
            color: #6c757d;
        }

        .comment-submit-btn {
            background: #007bff;
            border: none;
            color: white;
            font-weight: 500;
            font-size: 13px;
            cursor: pointer;
            padding: 6px 12px;
            border-radius: 6px;
            opacity: 0.6;
            transition: all 0.2s ease;
            flex-shrink: 0;
        }

        .comment-submit-btn.active {
            opacity: 1;
        }

        .comment-submit-btn:hover.active {
            background: #0056b3;
        }

        /* Пустое состояние комментариев */
        .comments-empty {
            text-align: center;
            padding: 40px 16px;
            color: #6c757d;
        }

        .comments-empty-icon {
            font-size: 36px;
            margin-bottom: 12px;
            opacity: 0.8;
        }

        .comments-empty-text {
            font-size: 16px;
            font-weight: 500;
            margin-bottom: 6px;
            color: #6c757d;
        }

        .comments-empty-subtext {
            font-size: 14px;
            color: #6c757d;
        }

        /* Анимации */
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

        @media (max-width: 768px) {
            .content-wrapper {
                padding: 16px 12px;
            }

            .news-card {
                border-radius: 8px;
                margin: 0 -12px 16px;
            }

            .news-header {
                padding: 12px 16px;
            }

            .author-avatar {
                width: 36px;
                height: 36px;
                font-size: 14px;
            }

            .news-title {
                padding: 12px 16px;
                font-size: 20px;
            }

            .news-content {
                padding: 12px 16px;
            }

            .news-meta {
                padding: 12px 16px;
                gap: 12px;
            }

            .news-gallery {
                height: 250px;
            }

            .comments-modal {
                width: 95%;
                max-height: 85vh;
                border-radius: 8px;
            }
        }

        @media (max-width: 480px) {
            .back-btn {
                margin-bottom: 16px;
                padding: 8px 12px;
                font-size: 12px;
            }

            .news-card {
                margin: 0 -12px 16px;
            }

            .news-gallery {
                height: 200px;
            }

            .news-title {
                font-size: 18px;
                padding: 10px 14px;
            }

            .news-header {
                padding: 10px 14px;
            }

            .news-content {
                padding: 10px 14px;
            }

            .news-meta {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
                padding: 10px 14px;
            }

            .comments-modal {
                width: 100%;
                max-height: 100vh;
                border-radius: 0;
            }
        }

        @media (max-width: 360px) {
            .news-gallery {
                height: 180px;
            }

            .news-title {
                font-size: 16px;
            }

            .author-avatar {
                width: 32px;
                height: 32px;
                font-size: 12px;
            }
        }
    </style>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const newsText = document.querySelector('.news-text');
            const readMoreBtn = document.createElement('button');
            readMoreBtn.className = 'read-more-btn';
            readMoreBtn.textContent = '<@spring.message "news.read_more"/>';

            if (newsText && newsText.scrollHeight > 300) {
                newsText.classList.add('show-read-more');
                newsText.parentNode.insertBefore(readMoreBtn, newsText.nextSibling);

                readMoreBtn.addEventListener('click', function() {
                    if (newsText.classList.contains('expanded')) {
                        newsText.classList.remove('expanded');
                        readMoreBtn.textContent = '<@spring.message "news.read_more"/>';
                    } else {
                        newsText.classList.add('expanded');
                        readMoreBtn.textContent = '<@spring.message "news.read_less"/>';
                    }
                });
            }
        });
    </script>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ADMIN") || authorities?seq_contains("SUPER_ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        >
    </#if>

    <div class="content-wrapper">
        <a href="/news" class="back-btn">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zM5.354 4.646L4.646 5.354a.5.5 0 0 0 0 .708L7.293 8.5H1.5a.5.5 0 0 0 0 1h5.793l-2.647 2.146a.5.5 0 0 0 .708.708l3.5-3a.5.5 0 0 0 0-.708l-3.5-3z"/>
            </svg>
            <@spring.message "news.back.button"/>
        </a>
        <#if currentUser?? && (isAdmin || (newsDto.creator?? && newsDto.creator.id == currentUser.id))>
            <form action="/news/delete/${newsDto.id}" method="post" style="">
                <@main.csrf/>
                <input type="hidden" name="_method" value="delete"/>
                <button type="submit" class="back-btn" style="background: #ffffff; color: #dc3545; border: 1px solid #e0e0e0;">
                    <@spring.message "news.delete.button"/>
                </button>
            </form>
        </#if>

        <article class="news-card">
            <header class="news-header">
                <div class="author-avatar">
                    ${newsDto.creator.name?substring(0, 1)?upper_case}
                </div>
                <div class="author-info">
                    <a href="/users/${newsDto.creator.id}/public" class="author-name">
                        ${newsDto.creator.name}
                    </a>
                    <div class="publication-date">
                        ${newsDto.formattedDate}
                    </div>
                </div>
            </header>

            <#assign translation = newsDto.newsTranslations[0]>
            <#if translation.title?? && translation.title?has_content>
                <h1 class="news-title">${translation.title}</h1>
            </#if>

            <#assign allImages = []>
            <#if newsDto.mainImage??>
                <#assign allImages = allImages + [{"url": "/api/files/${newsDto.mainImageId}/view", "alt": "Главное изображение"}]>
            </#if>
            <#if newsDto.newsFiles?? && newsDto.newsFiles?size gt 0>
                <#list newsDto.newsFiles as file>
                    <#assign allImages = allImages + [{"url": "/api/files/${file.id}/view", "alt": "Изображение ${file_index + 1}"}]>
                </#list>
            </#if>

            <#if allImages?size gt 0>
                <div id="newsCarousel"
                     class="carousel slide news-gallery <#if allImages?size == 1>single-image</#if>"
                     data-bs-ride="false" data-bs-touch="true">
                    <#if allImages?size gt 1>
                        <div class="image-counter">
                            <span id="currentSlide">1</span>/${allImages?size}
                        </div>

                        <div class="carousel-indicators">
                            <#list 0..(allImages?size-1) as i>
                                <button type="button" data-bs-target="#newsCarousel" data-bs-slide-to="${i}"
                                        <#if i == 0>class="active" aria-current="true"</#if>
                                        aria-label="Slide ${i + 1}"></button>
                            </#list>
                        </div>
                    </#if>

                    <div class="carousel-inner">
                        <#list allImages as image>
                            <div class="carousel-item <#if image_index == 0>active</#if>">
                                <img src="${image.url}" alt="${image.alt}" class="d-block" loading="<#if image_index == 0>eager<#else>lazy</#if>">
                            </div>
                        </#list>
                    </div>

                    <#if allImages?size gt 1>
                        <button class="carousel-control-prev" type="button" data-bs-target="#newsCarousel"
                                data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#newsCarousel"
                                data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </#if>
                </div>
            </#if>

            <div class="news-content">
                <#if translation.content?? && translation.content?has_content>
                    <div class="news-text" id="newsContent">${translation.content?no_esc}</div>
                </#if>
            </div>

            <div class="news-meta">
                <div class="meta-item">
                    <span class="icon">👁️</span>
                    <span><@spring.message "news.views"/> ${newsDto.viewCount}</span>
                </div>
                <#if newsDto.shareCount?? && newsDto.shareCount gt 0>
                    <div class="meta-item">
                        <span class="icon">📤</span>
                        <span><@spring.message "news.shares"/> ${newsDto.shareCount}</span>
                    </div>
                </#if>
                <#if allImages?size gt 1>
                    <div class="meta-item">
                        <span class="icon">📷</span>
                        <span>${allImages?size} <@spring.message "news.photos"/></span>
                    </div>
                </#if>
            </div>
        </article>
    </div>

</@main.layout>