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
            background: #ffffff;
            color: #2c3e50;
            line-height: 1.5;
            min-height: 100vh;
        }

        .content-wrapper {
            max-width: 800px;
            margin: 0 auto;
            padding: 32px 20px;
        }

        /* Кнопка возврата */
        .back-btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 32px;
            padding: 12px 20px;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            font-weight: 600;
            border-radius: 12px;
            text-decoration: none;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(37, 99, 235, 0.2);
            font-size: 14px;
            border: none;
            cursor: pointer;
        }

        .back-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(37, 99, 235, 0.3);
            text-decoration: none;
            color: white;
        }

        /* Основная карточка поста */
        .publication-item {
            background: #ffffff;
            border-radius: 24px;
            overflow: hidden;
            box-shadow:
                    0 8px 32px rgba(0, 0, 0, 0.08),
                    0 2px 8px rgba(0, 0, 0, 0.04);
            border: 1px solid #e5e7eb;
            margin-bottom: 32px;
            animation: fadeInUp 0.6s ease-out;
        }

        /* Заголовок с автором */
        .publication-header {
            padding: 24px 28px 20px;
            border-bottom: 1px solid #f1f3f4;
            display: flex;
            align-items: center;
            gap: 16px;
        }

        .avatar {
            width: 48px;
            height: 48px;
            border-radius: 50%;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 700;
            font-size: 18px;
            flex-shrink: 0;
            box-shadow: 0 4px 12px rgba(37, 99, 235, 0.2);
        }

        .author-info {
            flex: 1;
        }

        .publication-author {
            font-weight: 700;
            font-size: 16px;
            color: #2c3e50;
            text-decoration: none;
            display: block;
            margin-bottom: 4px;
            transition: color 0.2s ease;
        }

        .publication-author:hover {
            color: #2563eb;
            text-decoration: none;
        }

        .publication-date {
            font-size: 14px;
            color: #6b7280;
            font-weight: 500;
        }

        /* Заголовок поста */
        .publication-title {
            padding: 0 28px 16px;
            font-size: 28px;
            font-weight: 800;
            color: #1f2937;
            line-height: 1.2;
            letter-spacing: -0.3px;
        }

        /* ИСПРАВЛЕННАЯ галерея изображений в стиле Bootstrap carousel */
        .publication-carousel {
            position: relative;
            margin-bottom: 24px;
            background: #f8fafc;
            height: 500px;
        }

        .publication-carousel.single-image .carousel-control-prev,
        .publication-carousel.single-image .carousel-control-next,
        .publication-carousel.single-image .carousel-indicators,
        .publication-carousel.single-image .image-counter {
            display: none !important;
        }

        .publication-carousel .carousel-inner {
            height: 100%;
            border-radius: 0;
        }

        .publication-carousel .carousel-item {
            height: 100%;
        }

        .publication-carousel .carousel-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            user-select: none;
            -webkit-user-drag: none;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            transition: transform 0.3s ease;
        }

        .publication-carousel:hover .carousel-item.active img {
            transform: scale(1.02);
        }

        .publication-carousel .carousel-control-prev,
        .publication-carousel .carousel-control-next {
            width: 48px;
            height: 48px;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border: none;
            border-radius: 50%;
            top: 50%;
            transform: translateY(-50%);
            opacity: 0;
            visibility: hidden;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .publication-carousel:hover .carousel-control-prev,
        .publication-carousel:hover .carousel-control-next {
            opacity: 1;
            visibility: visible;
        }

        .publication-carousel .carousel-control-prev {
            left: 16px;
        }

        .publication-carousel .carousel-control-next {
            right: 16px;
        }

        .publication-carousel .carousel-control-prev:hover,
        .publication-carousel .carousel-control-next:hover {
            background: white;
            transform: translateY(-50%) scale(1.1);
        }

        .publication-carousel .carousel-control-prev:active,
        .publication-carousel .carousel-control-next:active {
            transform: translateY(-50%) scale(0.95);
        }

        .publication-carousel .carousel-control-prev-icon,
        .publication-carousel .carousel-control-next-icon {
            width: 20px;
            height: 20px;
            filter: invert(0.3);
        }

        .publication-carousel .carousel-indicators {
            bottom: 16px;
            margin-bottom: 0;
            opacity: 0;
            visibility: hidden;
            transition: all 0.3s ease;
        }

        .publication-carousel:hover .carousel-indicators {
            opacity: 1;
            visibility: visible;
        }

        .publication-carousel .carousel-indicators [data-bs-target] {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.4);
            border: none;
            margin: 0 4px;
            transition: all 0.3s ease;
        }

        .publication-carousel .carousel-indicators .active {
            background-color: white;
            transform: scale(1.25);
        }

        .publication-carousel .carousel-indicators [data-bs-target]:hover:not(.active) {
            background-color: rgba(255, 255, 255, 0.7);
            transform: scale(1.1);
        }

        .image-counter {
            position: absolute;
            top: 16px;
            right: 16px;
            background: rgba(0, 0, 0, 0.75);
            backdrop-filter: blur(10px);
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
            z-index: 10;
        }

        /* Контент поста */
        .publication-content {
            padding: 0 28px 24px;
        }

        .publication-text {
            font-size: 16px;
            line-height: 1.7;
            color: #374151;
            margin-bottom: 24px;
            white-space: pre-wrap;
            word-break: break-word;
        }

        .publication-text.collapsed {
            max-height: 200px;
            overflow: hidden;
            position: relative;
        }

        .publication-text.collapsed::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 40px;
            background: linear-gradient(transparent, white);
        }

        .show-more-btn {
            color: #2563eb;
            font-weight: 600;
            cursor: pointer;
            font-size: 15px;
            display: inline-block;
            margin-bottom: 20px;
            transition: color 0.2s ease;
            background: none;
            border: none;
            padding: 0;
        }

        .show-more-btn:hover {
            color: #1d4ed8;
        }

        /* Метаинформация */
        .publication-meta {
            display: flex;
            align-items: center;
            gap: 24px;
            flex-wrap: wrap;
            padding: 20px 28px;
            background: #f8fafc;
            border-top: 1px solid #f1f3f4;
        }

        .meta-item {
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
            color: #6b7280;
            font-weight: 500;
        }

        .meta-item .icon {
            font-size: 16px;
        }

        #viewCommentsBtn {
            background: none;
            border: none;
            color: #6b7280;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 8px 12px;
            border-radius: 8px;
            transition: all 0.2s ease;
        }

        #viewCommentsBtn:hover {
            background: #f1f5f9;
            color: #2563eb;
        }

        /* Модальное окно комментариев */
        .comments-modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.6);
            backdrop-filter: blur(4px);
            z-index: 1050;
            display: none;
            align-items: center;
            justify-content: center;
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .comments-modal-overlay.show {
            display: flex;
            opacity: 1;
        }

        .comments-modal {
            background: white;
            border-radius: 20px;
            width: 90%;
            max-width: 600px;
            max-height: 85vh;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            transform: scale(0.9);
            transition: transform 0.3s ease;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        }

        .comments-modal-overlay.show .comments-modal {
            transform: scale(1);
        }

        .comments-modal-header {
            padding: 24px 28px 20px;
            border-bottom: 1px solid #f1f3f4;
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: white;
            position: sticky;
            top: 0;
            z-index: 10;
        }

        .comments-modal-title {
            font-weight: 700;
            font-size: 18px;
            color: #1f2937;
            margin: 0;
        }

        .comments-modal-close {
            background: #f3f4f6;
            border: none;
            width: 36px;
            height: 36px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: all 0.2s ease;
            color: #6b7280;
        }

        .comments-modal-close:hover {
            background: #e5e7eb;
            color: #374151;
        }

        .comments-modal-body {
            flex: 1;
            overflow-y: auto;
            min-height: 200px;
            max-height: 400px;
        }

        .comments-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .comment-item {
            padding: 20px 28px;
            border-bottom: 1px solid #f8fafc;
            display: flex;
            align-items: flex-start;
            gap: 16px;
            transition: background-color 0.2s ease;
        }

        .comment-item:hover {
            background: #f8fafc;
        }

        .comment-avatar {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 600;
            font-size: 14px;
            flex-shrink: 0;
        }

        .comment-content {
            flex: 1;
            min-width: 0;
        }

        .comment-author {
            font-weight: 600;
            font-size: 15px;
            color: #1f2937;
            margin-bottom: 4px;
        }

        .comment-text {
            font-size: 15px;
            color: #374151;
            line-height: 1.5;
            word-break: break-word;
            margin-bottom: 8px;
        }

        .comment-meta {
            display: flex;
            align-items: center;
            gap: 16px;
        }

        .comment-time {
            font-size: 13px;
            color: #9ca3af;
            font-weight: 500;
        }

        .comment-menu {
            position: relative;
            margin-left: auto;
            flex-shrink: 0;
        }

        .comment-menu-btn {
            background: none;
            border: none;
            padding: 8px;
            cursor: pointer;
            color: #9ca3af;
            border-radius: 6px;
            transition: all 0.2s;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .comment-menu-btn:hover {
            background: #f3f4f6;
            color: #6b7280;
        }

        .comment-menu-dropdown {
            position: absolute;
            top: 100%;
            right: 0;
            background: white;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
            min-width: 140px;
            z-index: 1000;
            overflow: hidden;
        }

        .comment-menu-item {
            width: 100%;
            padding: 12px 16px;
            background: none;
            border: none;
            text-align: left;
            cursor: pointer;
            color: #6b7280;
            font-size: 14px;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 10px;
            transition: all 0.2s;
        }

        .comment-menu-item:hover {
            background: #fef2f2;
            color: #dc2626;
        }

        /* Футер модального окна */
        .comments-modal-footer {
            padding: 20px 28px 24px;
            border-top: 1px solid #f1f3f4;
            background: white;
            position: sticky;
            bottom: 0;
        }

        .comment-input-wrapper {
            display: flex;
            align-items: flex-end;
            gap: 16px;
            background: #f8fafc;
            border-radius: 16px;
            padding: 12px 16px;
            border: 1px solid #e5e7eb;
            transition: border-color 0.2s ease;
        }

        .comment-input-wrapper:focus-within {
            border-color: #2563eb;
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
        }

        .comment-input-wrapper textarea {
            flex: 1;
            border: none;
            outline: none;
            font-size: 15px;
            color: #374151;
            background: transparent;
            resize: none;
            max-height: 100px;
            min-height: 20px;
            line-height: 20px;
            padding: 0;
            font-family: inherit;
        }

        .comment-input-wrapper textarea::placeholder {
            color: #9ca3af;
        }

        .comment-submit-btn {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            border: none;
            color: white;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            padding: 8px 16px;
            border-radius: 8px;
            opacity: 0.5;
            transition: all 0.2s ease;
            flex-shrink: 0;
        }

        .comment-submit-btn.active {
            opacity: 1;
        }

        .comment-submit-btn:hover.active {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
        }

        /* Пустое состояние комментариев */
        .comments-empty {
            text-align: center;
            padding: 60px 20px;
            color: #9ca3af;
        }

        .comments-empty-icon {
            font-size: 48px;
            margin-bottom: 16px;
            opacity: 0.7;
        }

        .comments-empty-text {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 8px;
            color: #6b7280;
        }

        .comments-empty-subtext {
            font-size: 15px;
            color: #9ca3af;
        }

        /* Анимации */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Адаптивность */
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 20px 16px;
            }

            .publication-item {
                border-radius: 16px;
                margin: 0 -16px 24px;
            }

            .publication-header {
                padding: 20px 24px 16px;
            }

            .avatar {
                width: 40px;
                height: 40px;
                font-size: 16px;
            }

            .publication-title {
                padding: 0 24px 16px;
                font-size: 22px;
            }

            .publication-content {
                padding: 0 24px 20px;
            }

            .publication-meta {
                padding: 16px 24px;
                gap: 16px;
            }

            .publication-carousel {
                height: 300px;
            }

            .publication-carousel .carousel-control-prev,
            .publication-carousel .carousel-control-next {
                width: 40px;
                height: 40px;
                opacity: 1 !important;
                visibility: visible !important;
            }

            .publication-carousel .carousel-control-prev {
                left: 12px;
            }

            .publication-carousel .carousel-control-next {
                right: 12px;
            }

            .publication-carousel .carousel-indicators {
                opacity: 1 !important;
                visibility: visible !important;
                bottom: 12px;
            }

            .image-counter {
                top: 12px;
                right: 12px;
            }

            .comments-modal {
                width: 95%;
                max-height: 90vh;
                border-radius: 16px;
            }

            .comments-modal-header,
            .comments-modal-footer {
                padding: 16px 20px;
            }

            .comment-item {
                padding: 16px 20px;
            }
        }

        @media (max-width: 480px) {
            .back-btn {
                margin-bottom: 20px;
                padding: 10px 16px;
                font-size: 13px;
            }

            .publication-item {
                border-radius: 0;
                margin: -20px -16px 20px;
            }

            .publication-carousel {
                height: 250px;
            }

            .publication-title {
                font-size: 20px;
                padding: 0 20px 14px;
            }

            .publication-header {
                padding: 16px 20px 14px;
            }

            .publication-content {
                padding: 0 20px 16px;
            }

            .publication-meta {
                flex-direction: column;
                align-items: flex-start;
                gap: 12px;
                padding: 14px 20px;
            }

            .comments-modal {
                width: 100%;
                height: 100vh;
                max-height: 100vh;
                border-radius: 0;
            }

            .publication-carousel .carousel-control-prev,
            .publication-carousel .carousel-control-next {
                width: 36px;
                height: 36px;
            }

            .publication-carousel .carousel-control-prev {
                left: 8px;
            }

            .publication-carousel .carousel-control-next {
                right: 8px;
            }

            .publication-carousel .carousel-control-prev-icon,
            .publication-carousel .carousel-control-next-icon {
                width: 16px;
                height: 16px;
            }

            .image-counter {
                font-size: 12px;
                padding: 6px 12px;
            }

            .publication-carousel .carousel-indicators {
                bottom: 8px;
            }

            .publication-carousel .carousel-indicators [data-bs-target] {
                width: 6px;
                height: 6px;
            }
        }

        @media (max-width: 360px) {
            .publication-carousel {
                height: 200px;
            }

            .publication-title {
                font-size: 18px;
            }

            .avatar {
                width: 36px;
                height: 36px;
                font-size: 14px;
            }
        }
    </style>

    <div class="content-wrapper">
        <a href="/posts" class="back-btn">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zM5.354 4.646L4.646 5.354a.5.5 0 0 0 0 .708L7.293 8.5H1.5a.5.5 0 0 0 0 1h5.793l-2.647 2.146a.5.5 0 0 0 .708.708l3.5-3a.5.5 0 0 0 0-.708l-3.5-3z"/>
            </svg>
            Назад к публикациям
        </a>

        <article class="publication-item">
            <header class="publication-header">
                <div class="avatar">
                    ${post.creator.name?substring(0, 1)?upper_case}
                </div>
                <div class="author-info">
                    <a href="/profile/info/${post.creator.id}" class="publication-author">
                        ${post.creator.name}
                    </a>
                    <div class="publication-date">
                        ${post.formattedDate}
                    </div>
                </div>
            </header>

            <#assign translation = post.postTranslations[0]>
            <#if translation.title?? && translation.title?has_content>
                <h1 class="publication-title">${translation.title}</h1>
            </#if>

            <#assign allImages = []>
            <#if post.mainImage??>
                <#assign allImages = allImages + [{"url": "/api/files/${post.mainImageId}/view", "alt": "Главное изображение"}]>
            </#if>
            <#if post.postFiles?? && post.postFiles?size gt 0>
                <#list post.postFiles as file>
                    <#assign allImages = allImages + [{"url": "/api/files/${file.id}/view", "alt": "Изображение ${file_index + 1}"}]>
                </#list>
            </#if>

            <#if allImages?size gt 0>
                <div id="postCarousel"
                     class="carousel slide publication-carousel <#if allImages?size == 1>single-image</#if>"
                     data-bs-ride="false" data-bs-touch="true">
                    <#if allImages?size gt 1>
                        <div class="image-counter">
                            <span id="currentSlide">1</span>/${allImages?size}
                        </div>

                        <div class="carousel-indicators">
                            <#list 0..(allImages?size-1) as i>
                                <button type="button" data-bs-target="#postCarousel" data-bs-slide-to="${i}"
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
                        <button class="carousel-control-prev" type="button" data-bs-target="#postCarousel"
                                data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#postCarousel"
                                data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </#if>
                </div>
            </#if>

            <div class="publication-content">
                <#if translation.content?? && translation.content?has_content>
                    <div class="publication-text collapsed" id="postContent">${translation.content?no_esc}</div>
                    <button class="show-more-btn" id="toggleButton">Читать полностью</button>
                </#if>
            </div>
            <#if post.status == "APPROVED">
                <div class="publication-meta">
                    <div class="meta-item">
                        <span class="icon">👁️</span>
                        <span>${post.viewCount!0} просмотров</span>
                    </div>
                    <#if post.shareCount?? && post.shareCount gt 0>
                        <div class="meta-item">
                            <span class="icon">📤</span>
                            <span>${post.shareCount} репостов</span>
                        </div>
                    </#if>
                    <#if allImages?size gt 1>
                        <div class="meta-item">
                            <span class="icon">📷</span>
                            <span>${allImages?size} фотографий</span>
                        </div>
                    </#if>
                    <button id="viewCommentsBtn" data-post-id="${post.id}"
                            <#if currentUser??>data-current-user-id="${currentUser.id}"</#if>>
                        <span class="icon">💬</span>
                        Комментарии
                    </button>
                </div>
            </#if>
        </article>
    </div>

    <div class="comments-modal-overlay" id="commentsModalOverlay">
        <div class="comments-modal">
            <div class="comments-modal-header">
                <h3 class="comments-modal-title">Комментарии</h3>
                <button class="comments-modal-close" id="closeCommentsModal" aria-label="Закрыть комментарии">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/>
                    </svg>
                </button>
            </div>
            <div class="comments-modal-body">
                <ul class="comments-list" id="comments-list"></ul>
                <div class="comments-empty" id="comments-empty" style="display: none;">
                    <div class="comments-empty-icon">💬</div>
                    <div class="comments-empty-text">Пока нет комментариев</div>
                    <div class="comments-empty-subtext">Станьте первым, кто оставит комментарий</div>
                </div>
            </div>
            <div class="comments-modal-footer">
                <#if currentUser??>
                    <div class="comment-input-wrapper">
                        <textarea id="newCommentInput" placeholder="Оставьте комментарий..."></textarea>
                        <button class="comment-submit-btn" id="submitComment">Отправить</button>
                    </div>
                <#else>
                    <div style="text-align: center; padding: 16px;">
                        <a href="/auth/login" class="back-btn" style="margin: 0; display: inline-flex;">
                            Войти для комментирования
                        </a>
                    </div>
                </#if>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Инициализация карусели
            const carouselElement = document.getElementById('postCarousel');
            const currentSlideSpan = document.getElementById('currentSlide');

            if (carouselElement && currentSlideSpan) {
                const carousel = new bootstrap.Carousel(carouselElement, {
                    interval: false,
                    wrap: false,
                    touch: true
                });

                carouselElement.addEventListener('slid.bs.carousel', function (event) {
                    const activeIndex = event.to;
                    if (currentSlideSpan) {
                        currentSlideSpan.textContent = activeIndex + 1;
                    }
                });

                // Навигация клавиатурой
                document.addEventListener('keydown', function (e) {
                    if (e.target.tagName === 'TEXTAREA' || e.target.tagName === 'INPUT') {
                        return; // Не обрабатываем, если фокус в поле ввода
                    }

                    if (e.key === 'ArrowLeft') {
                        carousel.prev();
                        e.preventDefault();
                    } else if (e.key === 'ArrowRight') {
                        carousel.next();
                        e.preventDefault();
                    }
                });
            }

            // Инициализация контента (сворачивание/разворачивание)
            const contentElement = document.getElementById('postContent');
            const toggleButton = document.getElementById('toggleButton');

            if (contentElement && toggleButton) {
                if (contentElement.scrollHeight <= 200) {
                    toggleButton.style.display = 'none';
                    contentElement.classList.remove('collapsed');
                }
                toggleButton.addEventListener('click', function () {
                    const isCollapsed = contentElement.classList.contains('collapsed');
                    contentElement.classList.toggle('collapsed');
                    toggleButton.textContent = isCollapsed ? 'Свернуть' : 'Читать полностью';
                });
            }

            // Инициализация комментариев
            const viewCommentsBtn = document.getElementById('viewCommentsBtn');
            const commentsModalOverlay = document.getElementById('commentsModalOverlay');
            const closeCommentsModal = document.getElementById('closeCommentsModal');
            const commentsList = document.getElementById('comments-list');
            const commentsEmpty = document.getElementById('comments-empty');
            const newCommentInput = document.getElementById('newCommentInput');
            const submitComment = document.getElementById('submitComment');

            const postId = document.getElementById('viewCommentsBtn').dataset.postId;
            const currentUserId = getCurrentUserId();

            if (viewCommentsBtn) {
                viewCommentsBtn.addEventListener('click', function () {
                    loadComments();
                    showModal();
                });
            }

            // Закрытие модального окна
            if (closeCommentsModal) {
                closeCommentsModal.addEventListener('click', hideModal);
            }

            if (commentsModalOverlay) {
                commentsModalOverlay.addEventListener('click', function (e) {
                    if (e.target === commentsModalOverlay) {
                        hideModal();
                    }
                });
            }

            // ESC для закрытия модального окна
            document.addEventListener('keydown', function (e) {
                if (e.key === 'Escape' && commentsModalOverlay && commentsModalOverlay.classList.contains('show')) {
                    hideModal();
                }
            });

            function showModal() {
                if (commentsModalOverlay) {
                    commentsModalOverlay.classList.add('show');
                    document.body.style.overflow = 'hidden';
                    if (newCommentInput) {
                        setTimeout(() => newCommentInput.focus(), 100);
                    }
                }
            }

            function hideModal() {
                if (commentsModalOverlay) {
                    commentsModalOverlay.classList.remove('show');
                    document.body.style.overflow = '';
                    if (newCommentInput) {
                        newCommentInput.value = '';
                        updateSubmitButton();
                        autoResize(newCommentInput);
                    }
                }
            }

            function getCurrentUserId() {
                // Получаем ID текущего пользователя
                const meta = document.querySelector('meta[name="current-user-id"]');
                if (meta) {
                    return meta.getAttribute('content');
                }

                const element = document.querySelector('[data-current-user-id]');
                if (element) {
                    return element.dataset.currentUserId;
                }

                if (typeof window.currentUserId !== 'undefined') {
                    return window.currentUserId;
                }

                return null;
            }

            function loadComments() {
                fetch('/api/comments/post/' + postId)
                    .then(res => res.json())
                    .then(data => {
                        renderComments(data);
                    })
                    .catch(error => {
                        console.error('Error loading comments:', error);
                        if (commentsEmpty) {
                            commentsEmpty.style.display = 'block';
                        }
                    });
            }

            function renderComments(commentsData) {
                if (!commentsList) return;

                commentsList.innerHTML = '';
                if (commentsData.length === 0) {
                    if (commentsEmpty) {
                        commentsEmpty.style.display = 'block';
                    }
                    return;
                }

                if (commentsEmpty) {
                    commentsEmpty.style.display = 'none';
                }

                commentsData.forEach(comment => {
                    const commentItem = document.createElement('li');
                    commentItem.classList.add('comment-item');
                    const authorInitial = comment.author && comment.author.name
                        ? comment.author.name.charAt(0).toUpperCase()
                        : 'U';
                    const timeAgo = formatTimeAgo(comment.createdAt);

                    const isCurrentUserAuthor = currentUserId && comment.author &&
                        (comment.author.id == currentUserId || comment.author.id === currentUserId);

                    const menuHtml = isCurrentUserAuthor ? `
                        <div class="comment-menu">
                            <button class="comment-menu-btn" onclick="toggleCommentMenu(` + comment.id + `)">
                                <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                                    <circle cx="8" cy="2" r="1.5"/>
                                    <circle cx="8" cy="8" r="1.5"/>
                                    <circle cx="8" cy="14" r="1.5"/>
                                </svg>
                            </button>
                            <div class="comment-menu-dropdown" id="menu-` + comment.id + `" style="display: none;">
                                <button class="comment-menu-item" onclick="deleteComment(` + comment.id + `)">
                                    <svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
                                        <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5zM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 5.883 16h4.234a2 2 0 0 0 1.992-1.84L12.962 3.5H13.5a.5.5 0 0 0 0-1H11zm1.958 1-.846 10.58a1 1 0 0 1-.997.92H5.883a1 1 0 0 1-.997-.92L3.042 3.5h9.916z"/>
                                    </svg>
                                    Удалить
                                </button>
                            </div>
                        </div>
                    ` : '';

                    commentItem.innerHTML = `
                        <div class="comment-avatar">` + authorInitial + `</div>
                        <div class="comment-content">
                            <div class="comment-author">` + comment.author.name + `</div>
                            <div class="comment-text">` + escapeHtml(comment.content) + `</div>
                            <div class="comment-meta">
                                <span class="comment-time">` + timeAgo + `</span>
                            </div>
                        </div>
                        ` + menuHtml + `
                    `;
                    commentsList.appendChild(commentItem);
                });
            }

            function formatTimeAgo(dateString) {
                try {
                    let cleanDateString = dateString.replace(/(\.\d{3})\d*/, '$1');
                    const parts = cleanDateString.split('T');
                    const datePart = parts[0];
                    const timePart = parts[1] || '00:00:00';
                    const [year, month, day] = datePart.split('-').map(Number);
                    const [hour, minute] = timePart.split(':').map(Number);
                    const date = new Date(year, month - 1, day, hour, minute);
                    const today = new Date();
                    const yesterday = new Date(today);
                    yesterday.setDate(yesterday.getDate() - 1);

                    if (date.toDateString() === today.toDateString()) {
                        return formatTime(hour, minute);
                    }
                    if (date.toDateString() === yesterday.toDateString()) {
                        return 'вчера в ' + formatTime(hour, minute);
                    }
                    const diffDays = Math.floor((today - date) / (1000 * 60 * 60 * 24));
                    if (diffDays > 7) {
                        return formatDate(day, month);
                    }
                    const weekDays = ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'];
                    return weekDays[date.getDay()] + ' в ' + formatTime(hour, minute);

                } catch (error) {
                    return 'недавно';
                }
            }

            function formatTime(hour, minute) {
                return hour.toString().padStart(2, '0') + ':' + minute.toString().padStart(2, '0');
            }

            function formatDate(day, month) {
                const months = ['янв', 'фев', 'мар', 'апр', 'май', 'июн', 'июл', 'авг', 'сен', 'окт', 'ноя', 'дек'];
                return day + ' ' + months[month - 1];
            }

            function escapeHtml(text) {
                const map = {
                    '&': '&amp;',
                    '<': '&lt;',
                    '>': '&gt;',
                    '"': '&quot;',
                    "'": '&#039;'
                };
                return text.replace(/[&<>"']/g, m => map[m]);
            }

            // Обработка ввода комментария
            if (newCommentInput && submitComment) {
                newCommentInput.addEventListener('input', function () {
                    updateSubmitButton();
                    autoResize(this);
                });

                newCommentInput.addEventListener('keydown', function (e) {
                    if (e.key === 'Enter' && !e.shiftKey) {
                        e.preventDefault();
                        if (this.value.trim()) {
                            addComment();
                        }
                    }
                });

                submitComment.addEventListener('click', function () {
                    if (newCommentInput.value.trim()) {
                        addComment();
                    }
                });
            }

            function updateSubmitButton() {
                if (!submitComment || !newCommentInput) return;
                const hasContent = newCommentInput.value.trim().length > 0;
                submitComment.classList.toggle('active', hasContent);
            }

            function autoResize(textarea) {
                textarea.style.height = 'auto';
                textarea.style.height = Math.min(textarea.scrollHeight, 100) + 'px';
            }

            function getCsrfToken() {
                const meta = document.querySelector('meta[name="_csrf"]');
                return meta ? meta.getAttribute('content') : '';
            }

            function getCsrfHeader() {
                const meta = document.querySelector('meta[name="_csrf_header"]');
                return meta ? meta.getAttribute('content') : 'X-CSRF-TOKEN';
            }

            function addComment() {
                const content = newCommentInput.value.trim();
                if (!content) return;

                submitComment.disabled = true;
                submitComment.textContent = 'Отправка...';

                fetch('/api/comments/post/new/' + postId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        [getCsrfHeader()]: getCsrfToken()
                    },
                    body: JSON.stringify({
                        postId: postId,
                        content: content
                    })
                })
                    .then(response => {
                        if (response.ok) {
                            newCommentInput.value = '';
                            updateSubmitButton();
                            autoResize(newCommentInput);
                            loadComments();
                        } else {
                            response.text().then(text => {
                                alert('Ошибка: ' + response.status + ' ' + text);
                            });
                        }
                    })
                    .catch(error => {
                        console.error('Error adding comment:', error);
                        alert('Ошибка при добавлении комментария');
                    })
                    .finally(() => {
                        submitComment.disabled = false;
                        submitComment.textContent = 'Отправить';
                    });
            }

            // Глобальные функции для меню комментариев
            window.toggleCommentMenu = function (commentId) {
                const menu = document.getElementById('menu-' + commentId);
                const allMenus = document.querySelectorAll('.comment-menu-dropdown');

                allMenus.forEach(m => {
                    if (m.id !== 'menu-' + commentId) {
                        m.style.display = 'none';
                    }
                });

                if (menu) {
                    menu.style.display = menu.style.display === 'none' ? 'block' : 'none';
                }
            };

            window.deleteComment = function (commentId) {
                if (confirm('Вы уверены, что хотите удалить этот комментарий?')) {
                    fetch('/api/comments/delete/' + commentId, {
                        method: 'POST',
                        headers: {
                            [getCsrfHeader()]: getCsrfToken()
                        }
                    })
                        .then(response => {
                            if (response.ok) {
                                loadComments();
                            } else {
                                alert('Ошибка при удалении комментария');
                            }
                        })
                        .catch(error => {
                            console.error('Error deleting comment:', error);
                            alert('Ошибка при удалении комментария');
                        });
                }
            };

            document.addEventListener('click', function (event) {
                if (!event.target.closest('.comment-menu')) {
                    const allMenus = document.querySelectorAll('.comment-menu-dropdown');
                    allMenus.forEach(menu => {
                        menu.style.display = 'none';
                    });
                }
            });
        });
    </script>

</@main.layout>