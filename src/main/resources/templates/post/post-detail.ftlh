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
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background-color: #fafafa;
            color: #262626;
            line-height: 1.4;
        }

        .content-wrapper {
            max-width: 470px;
            margin: 0 auto;
            padding: 20px 0;
        }

        .publication-item {
            background: white;
            border: 1px solid #dbdbdb;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            margin-bottom: 24px;
        }

        .publication-header {
            display: flex;
            align-items: center;
            padding: 16px;
            border-bottom: 1px solid #efefef;
        }

        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: linear-gradient(45deg, #fd5949, #d6249f, #285aeb);
            padding: 2px;
            margin-right: 12px;
        }

        .avatar img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid white;
        }

        .publication-author {
            font-weight: 600;
            font-size: 15px;
            color: #262626;
            text-decoration: none;
        }

        .publication-carousel {
            height: 470px;
            background: #f8f8f8;
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
        }

        .publication-carousel .carousel-control-prev,
        .publication-carousel .carousel-control-next {
            width: 40px;
            height: 40px;
            background: rgba(0, 0, 0, 0.5);
            border-radius: 50%;
            top: 50%;
            transform: translateY(-50%);
            opacity: 1;
        }

        .publication-carousel .carousel-control-prev {
            left: 12px;
        }

        .publication-carousel .carousel-control-next {
            right: 12px;
        }

        .publication-carousel .carousel-control-prev:hover,
        .publication-carousel .carousel-control-next:hover {
            background: rgba(0, 0, 0, 0.7);
        }

        .publication-carousel .carousel-control-prev-icon,
        .publication-carousel .carousel-control-next-icon {
            width: 16px;
            height: 16px;
        }

        .publication-carousel .carousel-indicators {
            bottom: 12px;
            margin-bottom: 0;
        }

        .publication-carousel .carousel-indicators [data-bs-target] {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.4);
            border: none;
            margin: 0 3px;
        }

        .publication-carousel .carousel-indicators .active {
            background-color: white;
            transform: scale(1.2);
        }

        .image-counter {
            position: absolute;
            top: 12px;
            right: 12px;
            background: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
            z-index: 10;
        }

        .single-image .carousel-control-prev,
        .single-image .carousel-control-next,
        .single-image .carousel-indicators,
        .single-image .image-counter {
            display: none !important;
        }

        .publication-content {
            padding: 12px 16px;
        }

        .publication-title {
            font-weight: 600;
            font-size: 18px;
            margin-bottom: 8px;
            line-height: 1.3;
        }

        .publication-text {
            font-size: 15px;
            color: #262626;
            margin-bottom: 12px;
            line-height: 1.5;
            white-space: pre-wrap;
            word-break: break-word;
            overflow-wrap: break-word;
            overflow: hidden;
            transition: max-height 0.3s ease;
        }

        .publication-text.collapsed {
            max-height: 120px;
            position: relative;
        }

        .show-more-btn {
            display: inline-block;
            margin-top: 4px;
            color: #0095f6;
            font-weight: 600;
            cursor: pointer;
            user-select: none;
        }

        .publication-meta {
            font-size: 13px;
            color: #8e8e8e;
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            gap: 12px;
            flex-wrap: wrap;
        }

        .meta-item {
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .back-btn {
            display: block;
            text-align: center;
            margin: 16px 0;
            padding: 12px 24px;
            background: linear-gradient(135deg, #0095f6, #0074cc);
            color: white;
            font-weight: 600;
            border-radius: 8px;
            text-decoration: none;
            transition: all 0.2s ease;
            box-shadow: 0 2px 4px rgba(0, 149, 246, 0.3);
        }

        .back-btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 149, 246, 0.4);
            text-decoration: none;
            color: white;
        }

        /* Comments Modal */
        .comments-modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.65);
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
            border-radius: 12px;
            width: 90%;
            max-width: 500px;
            max-height: 80vh;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            transform: scale(0.9);
            transition: transform 0.3s ease;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
        }

        .comments-modal-overlay.show .comments-modal {
            transform: scale(1);
        }

        .comments-modal-header {
            padding: 16px 20px;
            border-bottom: 1px solid #efefef;
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: white;
            position: sticky;
            top: 0;
            z-index: 10;
        }

        .comments-modal-title {
            font-weight: 600;
            font-size: 16px;
            color: #262626;
            margin: 0;
        }

        .comments-modal-close {
            background: none;
            border: none;
            font-size: 24px;
            color: #8e8e8e;
            cursor: pointer;
            padding: 0;
            width: 32px;
            height: 32px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            transition: background-color 0.2s ease;
        }

        .comments-modal-close:hover {
            background: #f0f0f0;
        }

        .comments-modal-body {
            flex: 1;
            overflow-y: auto;
            padding: 0;
            min-height: 200px;
            max-height: 400px;
        }

        .comments-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .comment-item {
            padding: 12px 20px;
            border-bottom: 1px solid #f0f0f0;
            display: flex;
            align-items: flex-start;
            gap: 12px;
            transition: background-color 0.2s ease;
            position: relative;
        }

        .comment-item:hover {
            background: #fafafa;
        }

        .comment-item:last-child {
            border-bottom: none;
        }

        .comment-avatar {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background: linear-gradient(45deg, #fd5949, #d6249f, #285aeb);
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
            font-size: 14px;
            color: #262626;
            margin-bottom: 2px;
        }

        .comment-text {
            font-size: 14px;
            color: #262626;
            line-height: 1.4;
            word-break: break-word;
            margin-bottom: 4px;
        }

        .comment-meta {
            display: flex;
            align-items: center;
            gap: 16px;
            margin-top: 8px;
        }

        .comment-time {
            font-size: 12px;
            color: #8e8e8e;
        }

        .comment-like {
            background: none;
            border: none;
            color: #8e8e8e;
            font-size: 12px;
            cursor: pointer;
            padding: 0;
            font-weight: 600;
            transition: color 0.2s ease;
        }

        .comment-like:hover {
            color: #262626;
        }

        .comment-like.liked {
            color: #ed4956;
        }

        .comment-delete {
            background: none;
            border: none;
            color: #ed4956;
            font-size: 12px;
            cursor: pointer;
            padding: 0;
            font-weight: 600;
            opacity: 0;
            transition: opacity 0.2s ease;
        }

        .comment-item:hover .comment-delete {
            opacity: 1;
        }

        .comments-modal-footer {
            padding: 16px 20px;
            border-top: 1px solid #efefef;
            background: white;
            position: sticky;
            bottom: 0;
        }

        .comment-input-wrapper {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .comment-input-wrapper textarea {
            flex: 1;
            border: none;
            outline: none;
            font-size: 14px;
            color: #262626;
            background: transparent;
            resize: none;
            max-height: 80px;
            min-height: 18px;
            line-height: 18px;
            padding: 0;
            font-family: inherit;
        }

        .comment-input-wrapper textarea::placeholder {
            color: #8e8e8e;
        }

        .comment-input-wrapper button {
            background: none;
            border: none;
            color: #0095f6;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            padding: 0;
            opacity: 0.3;
            transition: opacity 0.2s ease;
        }

        .comment-input-wrapper button.active {
            opacity: 1;
        }

        .comment-input-wrapper button:hover.active {
            opacity: 0.7;
        }

        .comments-empty {
            text-align: center;
            padding: 40px 20px;
            color: #8e8e8e;
        }

        .comments-empty-icon {
            font-size: 48px;
            margin-bottom: 16px;
            opacity: 0.5;
        }

        .comments-empty-text {
            font-size: 16px;
            font-weight: 400;
            margin-bottom: 8px;
        }

        .comments-empty-subtext {
            font-size: 14px;
            color: #c7c7c7;
        }

        /* Comment Menu */
        .comment-menu {
            position: relative;
            margin-left: auto;
            flex-shrink: 0;
        }

        .comment-menu-btn {
            background: none;
            border: none;
            padding: 4px;
            cursor: pointer;
            color: #666;
            border-radius: 4px;
            transition: all 0.2s;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .comment-menu-btn:hover {
            background: #f0f0f0;
            color: #333;
        }

        .comment-menu-dropdown {
            position: absolute;
            top: 100%;
            right: 0;
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
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
            color: #666;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: background 0.2s;
        }

        .comment-menu-item:hover {
            background: #f5f5f5;
            color: #d73a49;
        }

        .comment-menu-item svg {
            width: 14px;
            height: 14px;
        }

        /* View Comments Button */
        #viewCommentsBtn {
            background: none;
            border: none;
            color: #8e8e8e;
            font-size: 14px;
            cursor: pointer;
            padding: 0;
            font-weight: 400;
            display: flex;
            align-items: center;
            gap: 4px;
            transition: color 0.2s ease;
        }

        #viewCommentsBtn:hover {
            color: #262626;
        }

        /* Mobile Responsive */
        @media (max-width: 768px) {
            .comment-menu-btn {
                padding: 8px;
            }

            .comment-menu-dropdown {
                min-width: 100px;
            }

            .comment-menu-item {
                padding: 10px 12px;
                font-size: 16px;
            }

            .comments-modal {
                width: 95%;
                max-height: 90vh;
                border-radius: 8px;
            }

            .comments-modal-header {
                padding: 12px 16px;
            }

            .comments-modal-footer {
                padding: 12px 16px;
            }

            .comment-item {
                padding: 12px 16px;
            }
        }

        @media (max-width: 480px) {
            .content-wrapper {
                padding: 0;
            }

            .publication-item {
                border-radius: 0;
                border-left: none;
                border-right: none;
                margin-bottom: 0;
            }

            .publication-carousel {
                height: 400px;
            }

            .publication-carousel .carousel-control-prev,
            .publication-carousel .carousel-control-next {
                width: 32px;
                height: 32px;
            }

            .publication-carousel .carousel-control-prev {
                left: 8px;
            }

            .publication-carousel .carousel-control-next {
                right: 8px;
            }

            .back-btn {
                margin: 12px;
                border-radius: 8px;
            }

            .comments-modal {
                width: 100%;
                height: 100vh;
                max-height: 100vh;
                border-radius: 0;
            }

            .comments-modal-body {
                max-height: calc(100vh - 140px);
            }
        }
    </style>

    <div class="content-wrapper">
        <article class="publication-item">
            <header class="publication-header">
                <div class="avatar">
                </div>
                <a href="/profile/info/${post.creator.id}" class="publication-author">${post.creator.name}</a>
            </header>

            <#assign allImages = []>
            <#if post.mainImage??>
                <#assign allImages = allImages + [{"url": "/api/files/${post.mainImageId}/view", "alt": "Main Image"}]>
            </#if>
            <#if post.postFiles?? && post.postFiles?size gt 0>
                <#list post.postFiles as file>
                    <#assign allImages = allImages + [{"url": "/api/files/${file.id}/view", "alt": "Post Image " + (file_index + 1)}]>
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
                                <img src="${image.url}" alt="${image.alt}" class="d-block">
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
                <#assign translation = post.postTranslations[0]>
                <#if translation.title?? && translation.title?has_content>
                    <div class="publication-title">${translation.title}</div>
                </#if>
                <#if translation.content?? && translation.content?has_content>
                    <div class="publication-text collapsed" id="postContent">${translation.content?no_esc}</div>
                    <div class="show-more-btn" id="toggleButton">Показать ещё</div>
                </#if>
                <div class="publication-meta">
                    <div class="meta-item">
                        📅 ${post.formattedDate}
                    </div>
                    <div class="meta-item">
                        👁 ${post.viewCount!0}
                    </div>
                    <#if post.shareCount?? && post.shareCount gt 0>
                        <div class="meta-item">
                            📤 ${post.shareCount}
                        </div>
                    </#if>
                    <#if allImages?size gt 1>
                        <div class="meta-item">
                            📷 ${allImages?size} фото
                        </div>
                    </#if>
                    <div class="meta-item">
                        <button id="viewCommentsBtn" data-post-id="${post.id}"
                                <#if currentUser??>data-current-user-id="${currentUser.id}"</#if>>💬 Посмотреть
                            комментарии
                        </button>
                    </div>
                </div>
            </div>
        </article>

        <a href="/posts" class="back-btn">← Вернуться к публикациям</a>
    </div>

    <div class="comments-modal-overlay" id="commentsModalOverlay">
        <div class="comments-modal">
            <div class="comments-modal-header">
                <h3 class="comments-modal-title">Комментарии</h3>
                <button class="comments-modal-close" id="closeCommentsModal">×</button>
            </div>
            <div class="comments-modal-body">
                <ul class="comments-list" id="comments-list">
                </ul>
                <div class="comments-empty" id="comments-empty" style="display: none;">
                    <div class="comments-empty-icon">💬</div>
                    <div class="comments-empty-text">Пока нет комментариев</div>
                    <div class="comments-empty-subtext">Начните обсуждение.</div>
                </div>
            </div>
            <div class="comments-modal-footer">
                <#if currentUser??>
                    <div class="comment-input-wrapper">
                        <textarea id="newCommentInput" placeholder="Напишите комментарий..."></textarea>
                        <button id="submitComment">Опубликовать</button>
                    </div>
                <#else>
                    <a href="/auth/login" class="btn btn-primary">Войдите в аккаунт, чтобы прокомментировать</a>
                </#if>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
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

                document.addEventListener('keydown', function (e) {
                    if (e.key === 'ArrowLeft') {
                        carousel.prev();
                        e.preventDefault();
                    } else if (e.key === 'ArrowRight') {
                        carousel.next();
                        e.preventDefault();
                    }
                });
            }
        });

        document.addEventListener('DOMContentLoaded', function () {
            const contentElement = document.getElementById('postContent');
            const toggleButton = document.getElementById('toggleButton');

            if (contentElement && toggleButton) {
                if (contentElement.scrollHeight <= 120) {
                    toggleButton.style.display = 'none';
                }
                toggleButton.addEventListener('click', function () {
                    contentElement.classList.toggle('collapsed');
                    if (contentElement.classList.contains('collapsed')) {
                        toggleButton.textContent = 'Показать ещё';
                    } else {
                        toggleButton.textContent = 'Скрыть';
                    }
                });
            }
        });

        document.addEventListener('DOMContentLoaded', function () {
            const viewCommentsBtn = document.getElementById('viewCommentsBtn');
            const commentsModalOverlay = document.getElementById('commentsModalOverlay');
            const closeCommentsModal = document.getElementById('closeCommentsModal');
            const commentsList = document.getElementById('comments-list');
            const commentsEmpty = document.getElementById('comments-empty');
            const newCommentInput = document.getElementById('newCommentInput');
            const submitComment = document.getElementById('submitComment');

            const postId = document.getElementById('viewCommentsBtn').dataset.postId;

            // Получаем ID текущего пользователя из data-атрибута или мета-тега
            const currentUserId = getCurrentUserId();

            viewCommentsBtn.addEventListener('click', function () {
                loadComments();
                showModal();
            });

            // Close modal
            closeCommentsModal.addEventListener('click', hideModal);
            commentsModalOverlay.addEventListener('click', function (e) {
                if (e.target === commentsModalOverlay) {
                    hideModal();
                }
            });

            // ESC key to close modal
            document.addEventListener('keydown', function (e) {
                if (e.key === 'Escape' && commentsModalOverlay.classList.contains('show')) {
                    hideModal();
                }
            });

            function showModal() {
                commentsModalOverlay.classList.add('show');
                document.body.style.overflow = 'hidden';
                newCommentInput.focus();
            }

            function hideModal() {
                commentsModalOverlay.classList.remove('show');
                document.body.style.overflow = '';
                newCommentInput.value = '';
                updateSubmitButton();
            }

            function getCurrentUserId() {
                // Вариант 1: Получить из мета-тега
                const meta = document.querySelector('meta[name="current-user-id"]');
                if (meta) {
                    return meta.getAttribute('content');
                }

                // Вариант 2: Получить из data-атрибута элемента
                const element = document.querySelector('[data-current-user-id]');
                if (element) {
                    return element.dataset.currentUserId;
                }

                // Вариант 3: Получить из глобальной переменной
                if (typeof window.currentUserId !== 'undefined') {
                    return window.currentUserId;
                }

                return null;
            }

            function loadComments() {
                fetch('/api/comments/post/' + postId)
                    .then(res => res.json())
                    .then(data => {
                        console.log(data);
                        renderComments(data);
                    })
                    .catch(error => {
                        console.error('Error loading comments:', error);
                        commentsEmpty.style.display = 'block';
                    });
            }

            function renderComments(commentsData) {
                commentsList.innerHTML = '';
                if (commentsData.length === 0) {
                    commentsEmpty.style.display = 'block';
                    return;
                }
                commentsEmpty.style.display = 'none';
                commentsData.forEach(comment => {
                    const commentItem = document.createElement('li');
                    commentItem.classList.add('comment-item');
                    const authorInitial = comment.author && comment.author.name
                        ? comment.author.name.charAt(0).toUpperCase()
                        : 'U';
                    const timeAgo = formatTimeAgo(comment.createdAt);

                    // Проверяем, является ли текущий пользователь автором комментария
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
                                    <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
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
                            <div class="comment-text">` + comment.content + `</div>
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

            function updateSubmitButton() {
                const hasContent = newCommentInput.value.trim().length > 0;
                submitComment.classList.toggle('active', hasContent);
            }

            function autoResize(textarea) {
                textarea.style.height = 'auto';
                textarea.style.height = Math.min(textarea.scrollHeight, 80) + 'px';
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
                        submitComment.textContent = 'Опубликовать';
                    });
            }

            window.toggleCommentMenu = function (commentId) {
                const menu = document.getElementById('menu-' + commentId);
                const allMenus = document.querySelectorAll('.comment-menu-dropdown');

                allMenus.forEach(m => {
                    if (m.id !== 'menu-' + commentId) {
                        m.style.display = 'none';
                    }
                });

                menu.style.display = menu.style.display === 'none' ? 'block' : 'none';
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
