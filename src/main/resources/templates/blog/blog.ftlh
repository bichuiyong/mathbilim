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
            margin-bottom: 24px;
            padding: 10px 16px;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            font-weight: 600;
            border-radius: 10px;
            text-decoration: none;
            transition: all 0.3s ease;
            box-shadow: 0 3px 10px rgba(37, 99, 235, 0.2);
            font-size: 14px;
            border: none;
            cursor: pointer;
        }

        .back-btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 6px 20px rgba(37, 99, 235, 0.3);
            text-decoration: none;
            color: white;
        }

        /* Основная карточка блога */
        .blog-card {
            background: #ffffff;
            border-radius: 20px;
            overflow: hidden;
            box-shadow:
                    0 6px 25px rgba(0, 0, 0, 0.06),
                    0 2px 8px rgba(0, 0, 0, 0.04);
            border: 1px solid #e5e7eb;
            margin-bottom: 24px;
            animation: fadeInUp 0.6s ease-out;
        }

        /* Заголовок с автором */
        .blog-header {
            padding: 20px 24px 16px;
            border-bottom: 1px solid #f1f3f4;
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .author-avatar {
            width: 42px;
            height: 42px;
            border-radius: 50%;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 700;
            font-size: 16px;
            flex-shrink: 0;
            box-shadow: 0 3px 10px rgba(37, 99, 235, 0.2);
        }

        .author-info {
            flex: 1;
        }

        .author-name {
            font-weight: 700;
            font-size: 15px;
            color: #2c3e50;
            text-decoration: none;
            display: block;
            margin-bottom: 3px;
            transition: color 0.2s ease;
        }

        .author-name:hover {
            color: #2563eb;
            text-decoration: none;
        }

        .publication-date {
            font-size: 13px;
            color: #6b7280;
            font-weight: 500;
        }

        /* Заголовок блога */
        .blog-title {
            padding: 0 24px 14px;
            font-size: 24px;
            font-weight: 800;
            color: #1f2937;
            line-height: 1.2;
            letter-spacing: -0.2px;
        }

        /* Главное изображение блога */
        .blog-main-image {
            position: relative;
            margin-bottom: 20px;
            background: #f8fafc;
            height: 350px;
        }

        .blog-main-image img {
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

        .blog-main-image:hover img {
            transform: scale(1.015);
        }

        /* Контент блога */
        .blog-content {
            padding: 0 24px 20px;
        }

        .blog-text {
            font-size: 15px;
            line-height: 1.65;
            color: #374151;
            margin-bottom: 20px;
            white-space: pre-wrap;
            word-break: break-word;
        }

        .blog-text.collapsed {
            max-height: 180px;
            overflow: hidden;
            position: relative;
        }

        .blog-text.collapsed::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 35px;
            background: linear-gradient(transparent, white);
        }

        .show-more-btn {
            color: #2563eb;
            font-weight: 600;
            cursor: pointer;
            font-size: 14px;
            display: inline-block;
            margin-bottom: 16px;
            transition: color 0.2s ease;
            background: none;
            border: none;
            padding: 0;
        }

        .show-more-btn:hover {
            color: #1d4ed8;
        }

        /* Метаинформация */
        .blog-meta {
            display: flex;
            align-items: center;
            gap: 20px;
            flex-wrap: wrap;
            padding: 16px 24px;
            background: #f8fafc;
            border-top: 1px solid #f1f3f4;
        }

        .meta-item {
            display: flex;
            align-items: center;
            gap: 6px;
            font-size: 13px;
            color: #6b7280;
            font-weight: 500;
        }

        .meta-item .icon {
            font-size: 14px;
        }

        /* Кнопки поделиться */
        .share-buttons {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-left: auto;
        }

        .share-label {
            font-size: 13px;
            color: #6b7280;
            font-weight: 500;
        }

        .social-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 32px;
            height: 32px;
            border-radius: 50%;
            color: white;
            text-decoration: none;
            transition: all 0.2s ease;
            font-size: 14px;
            border: none;
            cursor: pointer;
            position: relative;
        }

        .social-btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
        }

        .social-btn.facebook {
            background: #1877f2;
        }

        .social-btn.twitter {
            background: #1da1f2;
        }

        .social-btn.telegram {
            background: #0088cc;
        }

        .social-btn.whatsapp {
            background: #25d366;
        }

        .social-btn.copy-link {
            background: #6b7280;
        }

        .social-btn.copy-link.copied {
            background: #16a34a;
        }

        .copy-feedback {
            position: absolute;
            top: -30px;
            left: 50%;
            transform: translateX(-50%);
            background: #1f2937;
            color: white;
            padding: 3px 6px;
            border-radius: 4px;
            font-size: 11px;
            white-space: nowrap;
            opacity: 0;
            visibility: hidden;
            transition: all 0.2s ease;
            pointer-events: none;
        }

        .copy-feedback::after {
            content: '';
            position: absolute;
            top: 100%;
            left: 50%;
            transform: translateX(-50%);
            border: 3px solid transparent;
            border-top-color: #1f2937;
        }

        .social-btn.copied .copy-feedback {
            opacity: 1;
            visibility: visible;
            transform: translateX(-50%) translateY(-3px);
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
                padding: 16px 12px;
            }

            .back-btn {
                margin-bottom: 16px;
                padding: 8px 14px;
                font-size: 13px;
            }

            .blog-card {
                border-radius: 14px;
                margin: 0 -12px 20px;
            }

            .blog-header {
                padding: 16px 20px 12px;
            }

            .author-avatar {
                width: 36px;
                height: 36px;
                font-size: 14px;
            }

            .blog-title {
                padding: 0 20px 12px;
                font-size: 20px;
            }

            .blog-content {
                padding: 0 20px 16px;
            }

            .blog-meta {
                padding: 12px 20px;
                gap: 14px;
                flex-direction: column;
                align-items: flex-start;
            }

            .share-buttons {
                margin-left: 0;
                margin-top: 6px;
            }

            .blog-main-image {
                height: 250px;
            }
        }

        @media (max-width: 480px) {
            .blog-card {
                border-radius: 0;
                margin: -16px -12px 16px;
            }

            .blog-main-image {
                height: 220px;
            }

            .blog-title {
                font-size: 18px;
                padding: 0 16px 10px;
            }

            .blog-header {
                padding: 12px 16px 10px;
            }

            .blog-content {
                padding: 0 16px 12px;
            }

            .blog-meta {
                padding: 10px 16px;
            }

            .share-buttons {
                gap: 8px;
            }

            .social-btn {
                width: 28px;
                height: 28px;
                font-size: 12px;
            }
        }

        @media (max-width: 360px) {
            .blog-main-image {
                height: 200px;
            }

            .blog-title {
                font-size: 17px;
            }

            .author-avatar {
                width: 32px;
                height: 32px;
                font-size: 13px;
            }
        }
        .comments-btn {
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

        .comments-btn:hover {
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
    </style>
    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ADMIN") || authorities?seq_contains("SUPER_ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        >
    </#if>

  <div class="content-wrapper">
    <a href="/blog" class="back-btn">
        <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
            <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zM5.354 4.646L4.646 5.354a.5.5 0 0 0 0 .708L7.293 8.5H1.5a.5.5 0 0 0 0 1h5.793l-2.647 2.146a.5.5 0 0 0 .708.708l3.5-3a.5.5 0 0 0 0-.708l-3.5-3z"/>
        </svg>
        <@spring.message "blog.back-to-list"/>
    </a>

    <article class="blog-card">
        <header class="blog-header">
            <div class="author-avatar">
                <#if blog.creator?? && blog.creator.name??>
                    ${blog.creator.name?substring(0, 1)?upper_case}
                <#else>
                    <@spring.message "blog.author.initial"/>
                </#if>
            </div>
            <div class="author-info">
                <#if blog.creator?? && blog.creator.id??>
                    <a href="/profile/info/${blog.creator.id}" class="author-name">
                        ${blog.creator.name!springMacroRequestContext.getMessage('blog.author.default')}
                    </a>
                <#else>
                    <span class="author-name"><@spring.message "blog.author.default"/></span>
                </#if>
                <div class="publication-date">
                    <#if blog.createdAt??>
                        ${blog.formattedDate}
                    <#else>
                        <@spring.message "blog.date.today"/>
                    </#if>
                </div>
            </div>
        </header>
        <#assign translation = blog.blogTranslations[0]>

        <#if translation.title?? && translation.title?has_content>
            <h1 class="blog-title">${translation.title}</h1>
        </#if>

        <#if blog.mainImage??>
            <div class="blog-main-image">
                <img src="/api/files/${blog.mainImage.id}/view"
                     alt="${translation.title!springMacroRequestContext.getMessage('blog.image.alt')}"
                     loading="eager">
            </div>
        </#if>

        <div class="blog-content">
            <#if translation.content?? && translation.content?has_content>
                <div class="blog-text collapsed" id="blogContent">${translation.content?no_esc}</div>
                <button class="show-more-btn" id="toggleContentBtn">
                    <@spring.message "blog.read-more"/>
                </button>
            </#if>
        </div>

        <#if blog.status == "APPROVED">
            <div class="blog-meta">
                <div class="meta-item">
                    <span class="icon">👁️</span>
                    <span>${blog.viewCount!0} <@spring.message "blog.views"/></span>
                </div>
                <#if blog.shareCount?? && blog.shareCount gt 0>
                    <div class="meta-item">
                        <span class="icon">📤</span>
                        <span>${blog.shareCount} <@spring.message "blog.shares"/></span>
                    </div>
                </#if>
                <div class="meta-item">
                    <span>
                        <button class="comments-btn" id="openCommentsBtn"
                                data-post-id="${blog.id}"
                                <#if currentUser??>data-current-user-id="${currentUser.id}"</#if>>
                            <span class="icon">💬</span>
                            <@spring.message "blog.comments"/>
                        </button>
                    </span>
                </div>

                <div class="share-buttons">
                    <span class="share-label"><@spring.message "blog.share"/></span>
                    <button class="social-btn facebook" onclick="shareToFacebook()" title="Facebook">
                        <i class="fab fa-facebook-f"></i>
                    </button>
                    <button class="social-btn twitter" onclick="shareToTwitter()" title="Twitter">
                        <i class="fab fa-twitter"></i>
                    </button>
                    <button class="social-btn telegram" onclick="shareToTelegram()" title="Telegram">
                        <i class="fab fa-telegram-plane"></i>
                    </button>
                    <button class="social-btn whatsapp" onclick="shareToWhatsApp()" title="WhatsApp">
                        <i class="fab fa-whatsapp"></i>
                    </button>
                    <button class="social-btn copy-link" onclick="copyLink()" id="copyLinkBtn"
                            title="<@spring.message 'blog.copy-link'/>">
                        <i class="fas fa-link"></i>
                        <span class="copy-feedback"><@spring.message "blog.copied"/></span>
                    </button>
                </div>
            </div>
        </#if>
    </article>
</div>

<div class="comments-modal-overlay" id="commentsModalOverlay">
    <div class="comments-modal">
        <div class="comments-modal-header">
            <h3 class="comments-modal-title"><@spring.message "blog.comments.title"/></h3>
            <button class="comments-modal-close" id="closeCommentsModal" aria-label="<@spring.message 'blog.comments.close'/>">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/>
                </svg>
            </button>
        </div>
        <div class="comments-modal-body">
            <ul class="comments-list" id="commentsList"></ul>
            <div class="comments-empty" id="commentsEmpty" style="display: none;">
                <div class="comments-empty-icon">💬</div>
                <div class="comments-empty-text"><@spring.message "blog.comments.empty"/></div>
                <div class="comments-empty-subtext"><@spring.message "blog.comments.first"/></div>
            </div>
        </div>
        <div class="comments-modal-footer">
            <#if currentUser??>
                <div class="comment-input-wrapper">
                    <textarea id="commentInput" placeholder="<@spring.message 'blog.comments.placeholder'/>"></textarea>
                    <button class="comment-submit-btn" id="submitCommentBtn">
                        <@spring.message "blog.comments.submit"/>
                    </button>
                </div>
            <#else>
                <div style="text-align: center; padding: 16px;">
                    <a href="/auth/login" class="back-btn" style="margin: 0; display: inline-flex;">
                        <@spring.message "blog.comments.login"/>
                    </a>
                </div>
            </#if>
        </div>
    </div>
</div>

    <script>
        // Данные для шаринга
        window.blogData = {
            <#if blog.id??>id: ${blog.id},</#if>
            <#if blog.title??>title: '${blog.title?js_string}',</#if>
            shareUrl: window.location.href,
        };

        document.addEventListener('DOMContentLoaded', function () {
            // Инициализация контента (сворачивание/разворачивание)
            const contentElement = document.getElementById('blogContent');
            const toggleButton = document.getElementById('toggleContentBtn');

            if (contentElement && toggleButton) {
                // Проверяем, нужна ли кнопка "Читать полностью"
                if (contentElement.scrollHeight <= 180) {
                    toggleButton.style.display = 'none';
                    contentElement.classList.remove('collapsed');
                }

                toggleButton.addEventListener('click', function () {
                    const isCollapsed = contentElement.classList.contains('collapsed');
                    contentElement.classList.toggle('collapsed');
                    toggleButton.textContent = isCollapsed ? 'Свернуть' : 'Читать полностью';
                });
            }
        });

        // Функции для поделиться
        function shareToFacebook() {
            const url = encodeURIComponent(window.blogData.shareUrl);
            window.open('https://www.facebook.com/sharer/sharer.php?u=' + url, '_blank', 'width=600,height=400');
        }

        function shareToTwitter() {
            const text = encodeURIComponent(window.blogData.title || 'Интересная статья');
            const url = encodeURIComponent(window.blogData.shareUrl);
            const hashtags = 'программирование,обучение,IT';
            window.open('https://twitter.com/intent/tweet?text=' + text + '&url=' + url + '&hashtags=' + hashtags, '_blank', 'width=600,height=400');
        }

        function shareToTelegram() {
            const text = encodeURIComponent((window.blogData.title || 'Интересная статья') + '\n\n' + window.blogData.shareUrl);
            window.open('https://t.me/share/url?url=' + encodeURIComponent(window.blogData.shareUrl) + '&text=' + encodeURIComponent(window.blogData.title || 'Интересная статья'), '_blank');
        }

        function shareToWhatsApp() {
            const text = encodeURIComponent((window.blogData.title || 'Интересная статья') + '\n\n' + window.blogData.shareUrl);
            window.open('https://wa.me/?text=' + text, '_blank');
        }

        function copyLink() {
            const url = window.blogData.shareUrl;

            if (navigator.clipboard && window.isSecureContext) {
                navigator.clipboard.writeText(url).then(function() {
                    showCopySuccess();
                }).catch(function() {
                    fallbackCopyTextToClipboard(url);
                });
            } else {
                fallbackCopyTextToClipboard(url);
            }
        }

        function fallbackCopyTextToClipboard(text) {
            const textArea = document.createElement("textarea");
            textArea.value = text;
            textArea.style.position = "fixed";
            textArea.style.left = "-999999px";
            textArea.style.top = "-999999px";
            document.body.appendChild(textArea);
            textArea.focus();
            textArea.select();

            try {
                document.execCommand('copy');
                showCopySuccess();
            } catch (err) {
                console.error('Не удалось скопировать текст: ', err);
                alert('Не удалось скопировать ссылку');
            }

            document.body.removeChild(textArea);
        }

        function showCopySuccess() {
            const btn = document.getElementById('copyLinkBtn');
            btn.classList.add('copied');
            setTimeout(() => {
                btn.classList.remove('copied');
            }, 2000);
        }

        const openCommentsBtn = document.getElementById('openCommentsBtn');
        const commentsModalOverlay = document.getElementById('commentsModalOverlay');
        const closeCommentsModal = document.getElementById('closeCommentsModal');
        const commentsList = document.getElementById('commentsList');
        const commentsEmpty = document.getElementById('commentsEmpty');
        const commentInput = document.getElementById('commentInput');
        const submitCommentBtn = document.getElementById('submitCommentBtn');

        const newsId = document.getElementById('openCommentsBtn').dataset.postId;
        const currentUserId = getCurrentUserId();

        if (openCommentsBtn) {
            openCommentsBtn.addEventListener('click', function () {
                loadComments();
                showModal();
            });
        }

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
                if (commentInput) {
                    setTimeout(() => commentInput.focus(), 100);
                }
            }
        }

        function hideModal() {
            if (commentsModalOverlay) {
                commentsModalOverlay.classList.remove('show');
                document.body.style.overflow = '';
                if (commentInput) {
                    commentInput.value = '';
                    updateSubmitButton();
                    autoResize(commentInput);
                }
            }
        }

        function getCurrentUserId() {
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
            fetch('/api/comments/blog/' + newsId)
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

                const moderOrAdmin = <#if (isAdmin?? && isAdmin) || (isModer?? && isModer)>true<#else>false</#if>
                // const removeComment = moderOrAdmin ? moderOrAdmin : false;

                const isCurrentUserAuthor = currentUserId && comment.author &&
                    (comment.author.id == currentUserId || comment.author.id === currentUserId) || moderOrAdmin;



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
        if (commentInput && submitCommentBtn) {
            commentInput.addEventListener('input', function () {
                updateSubmitButton();
                autoResize(this);
            });

            commentInput.addEventListener('keydown', function (e) {
                if (e.key === 'Enter' && !e.shiftKey) {
                    e.preventDefault();
                    if (this.value.trim()) {
                        addComment();
                    }
                }
            });

            submitCommentBtn.addEventListener('click', function () {
                if (commentInput.value.trim()) {
                    addComment();
                }
            });
        }

        function updateSubmitButton() {
            if (!submitCommentBtn || !commentInput) return;
            const hasContent = commentInput.value.trim().length > 0;
            submitCommentBtn.classList.toggle('active', hasContent);
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
            const content = commentInput.value.trim();
            if (!content) return;

            submitCommentBtn.disabled = true;
            submitCommentBtn.textContent = 'Отправка...';

            fetch('/api/comments/blog/new/' + newsId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [getCsrfHeader()]: getCsrfToken()
                },
                body: JSON.stringify({
                    postId: newsId,
                    content: content
                })
            })
                .then(response => {
                    if (response.ok) {
                        commentInput.value = '';
                        updateSubmitButton();
                        autoResize(commentInput);
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
                    submitCommentBtn.disabled = false;
                    submitCommentBtn.textContent = 'Отправить';
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
    </script>

</@main.layout>