/**
 * Модуль для управления детальной страницей новости
 */
class NewsDetailManager {
    constructor() {
        this.data = window.newsDetailData || {};
        this.currentImageIndex = 0;
        this.images = this.data.images || [];
        this.isTransitioning = false;

        this.init();
    }

    init() {
        document.addEventListener('DOMContentLoaded', () => {
            this.initializeGallery();
            this.initializeContent();
            this.initializeComments();
        });
    }

    /**
     * Инициализация галереи изображений
     */
    initializeGallery() {
        if (this.images.length <= 1) return;

        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');
        const indicators = document.querySelectorAll('.gallery-indicator');

        if (prevBtn && nextBtn) {
            prevBtn.addEventListener('click', () => this.changeImage(-1));
            nextBtn.addEventListener('click', () => this.changeImage(1));
        }

        indicators.forEach((indicator, index) => {
            indicator.addEventListener('click', () => this.goToImage(index));
        });

        // Навигация клавиатурой
        document.addEventListener('keydown', (e) => {
            if (e.target.tagName === 'TEXTAREA' || e.target.tagName === 'INPUT') {
                return; // Не обрабатываем, если фокус в поле ввода
            }

            if (e.key === 'ArrowLeft') {
                this.changeImage(-1);
                e.preventDefault();
            } else if (e.key === 'ArrowRight') {
                this.changeImage(1);
                e.preventDefault();
            }
        });

        // Свайп для мобильных устройств
        this.initializeSwipe();

        // Автозапуск (опционально)
        // this.startAutoSlide();
    }

    /**
     * Инициализация свайпов
     */
    initializeSwipe() {
        let startX = 0;
        let endX = 0;
        let startY = 0;
        let endY = 0;

        const galleryContainer = document.getElementById('newsGallery');
        if (!galleryContainer) return;

        galleryContainer.addEventListener('touchstart', (e) => {
            startX = e.touches[0].clientX;
            startY = e.touches[0].clientY;
        }, { passive: true });

        galleryContainer.addEventListener('touchmove', (e) => {
            // Предотвращаем скролл при горизонтальном свайпе
            const currentX = e.touches[0].clientX;
            const currentY = e.touches[0].clientY;
            const diffX = Math.abs(currentX - startX);
            const diffY = Math.abs(currentY - startY);

            if (diffX > diffY) {
                e.preventDefault();
            }
        }, { passive: false });

        galleryContainer.addEventListener('touchend', (e) => {
            endX = e.changedTouches[0].clientX;
            endY = e.changedTouches[0].clientY;
            this.handleSwipe();
        }, { passive: true });
    }

    /**
     * Обработка свайпа
     */
    handleSwipe() {
        const threshold = 50;
        const diffX = Math.abs(this.startX - this.endX);
        const diffY = Math.abs(this.startY - this.endY);

        // Убеждаемся, что это горизонтальный свайп
        if (diffX < threshold || diffY > diffX) return;

        const direction = this.startX - this.endX;

        if (direction > 0) {
            this.changeImage(1); // Свайп влево - следующее изображение
        } else {
            this.changeImage(-1); // Свайп вправо - предыдущее изображение
        }
    }

    /**
     * Смена изображения
     */
    changeImage(direction) {
        if (this.images.length <= 1 || this.isTransitioning) return;

        this.isTransitioning = true;

        const newIndex = (this.currentImageIndex + direction + this.images.length) % this.images.length;
        this.goToImage(newIndex);

        setTimeout(() => {
            this.isTransitioning = false;
        }, 400);
    }

    /**
     * Переход к конкретному изображению
     */
    goToImage(index) {
        if (index === this.currentImageIndex || this.isTransitioning) return;

        this.currentImageIndex = index;
        this.updateGalleryView();
    }

    /**
     * Обновление отображения галереи
     */
    updateGalleryView() {
        const slides = document.querySelectorAll('.gallery-slide');
        const currentIndexSpan = document.getElementById('currentImageIndex');
        const indicators = document.querySelectorAll('.gallery-indicator');
        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');

        // Обновляем активный слайд
        slides.forEach((slide, index) => {
            slide.classList.toggle('active', index === this.currentImageIndex);
        });

        // Обновляем счетчик
        if (currentIndexSpan) {
            currentIndexSpan.textContent = this.currentImageIndex + 1;
        }

        // Обновляем индикаторы
        indicators.forEach((indicator, index) => {
            indicator.classList.toggle('active', index === this.currentImageIndex);
        });

        // Обновляем состояние кнопок (если нужна цикличность, можно убрать)
        if (prevBtn && nextBtn) {
            prevBtn.disabled = this.currentImageIndex === 0;
            nextBtn.disabled = this.currentImageIndex === this.images.length - 1;
        }

        // Предзагрузка соседних изображений
        this.preloadAdjacentImages();
    }

    /**
     * Предзагрузка соседних изображений
     */
    preloadAdjacentImages() {
        const preloadIndexes = [
            (this.currentImageIndex + 1) % this.images.length,
            (this.currentImageIndex - 1 + this.images.length) % this.images.length
        ];

        preloadIndexes.forEach(index => {
            if (this.images[index] && !this.images[index].preloaded) {
                const img = new Image();
                img.src = this.images[index].url;
                this.images[index].preloaded = true;
            }
        });
    }

    /**
     * Автоматическое переключение слайдов (опционально)
     */
    startAutoSlide(interval = 5000) {
        this.autoSlideInterval = setInterval(() => {
            if (!this.isTransitioning && this.images.length > 1) {
                this.changeImage(1);
            }
        }, interval);
    }

    stopAutoSlide() {
        if (this.autoSlideInterval) {
            clearInterval(this.autoSlideInterval);
            this.autoSlideInterval = null;
        }
    }

    /**
     * Инициализация контента новости
     */
    initializeContent() {
        const contentElement = document.getElementById('newsContent');
        const toggleButton = document.getElementById('toggleContentBtn');

        if (!contentElement || !toggleButton) return;

        // Проверяем, нужна ли кнопка "Читать полностью"
        if (contentElement.scrollHeight <= 200) {
            toggleButton.style.display = 'none';
            contentElement.classList.remove('collapsed');
            return;
        }

        toggleButton.addEventListener('click', () => {
            const isCollapsed = contentElement.classList.contains('collapsed');

            contentElement.classList.toggle('collapsed');
            toggleButton.textContent = isCollapsed ? 'Свернуть' : 'Читать полностью';

            if (isCollapsed) {
                // Плавно скроллим к началу контента при разворачивании
                setTimeout(() => {
                    contentElement.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }, 100);
            }
        });
    }

    /**
     * Инициализация системы комментариев
     */
    initializeComments() {
        const openCommentsBtn = document.getElementById('openCommentsBtn');
        if (!openCommentsBtn) return;

        this.commentsManager = new CommentsManager(this.data.newsId, this.data.currentUserId);
        this.commentsManager.init();
    }
}

/**
 * Менеджер комментариев
 */
class CommentsManager {
    constructor(newsId, currentUserId) {
        this.newsId = newsId;
        this.currentUserId = currentUserId;
        this.isModalOpen = false;
    }

    init() {
        this.bindEvents();
    }

    bindEvents() {
        const openCommentsBtn = document.getElementById('openCommentsBtn');
        const commentsModalOverlay = document.getElementById('commentsModalOverlay');
        const closeCommentsModal = document.getElementById('closeCommentsModal');
        const commentInput = document.getElementById('commentInput');
        const submitCommentBtn = document.getElementById('submitCommentBtn');

        // Открытие модального окна
        openCommentsBtn?.addEventListener('click', () => {
            this.loadComments();
            this.showModal();
        });

        // Закрытие модального окна
        closeCommentsModal?.addEventListener('click', () => this.hideModal());

        commentsModalOverlay?.addEventListener('click', (e) => {
            if (e.target === commentsModalOverlay) {
                this.hideModal();
            }
        });

        // Закрытие по ESC
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && this.isModalOpen) {
                this.hideModal();
            }
        });

        // Обработка ввода комментария
        if (commentInput && submitCommentBtn) {
            commentInput.addEventListener('input', () => {
                this.updateSubmitButton();
                this.autoResizeTextarea(commentInput);
            });

            commentInput.addEventListener('keydown', (e) => {
                if (e.key === 'Enter' && !e.shiftKey) {
                    e.preventDefault();
                    if (commentInput.value.trim()) {
                        this.submitComment();
                    }
                }
            });

            submitCommentBtn.addEventListener('click', () => {
                if (commentInput.value.trim()) {
                    this.submitComment();
                }
            });
        }

        // Глобальные функции для меню комментариев
        this.setupGlobalCommentFunctions();
    }

    showModal() {
        const overlay = document.getElementById('commentsModalOverlay');
        if (overlay) {
            overlay.classList.add('show');
            document.body.style.overflow = 'hidden';
            this.isModalOpen = true;

            const commentInput = document.getElementById('commentInput');
            if (commentInput) {
                setTimeout(() => commentInput.focus(), 100);
            }
        }
    }

    hideModal() {
        const overlay = document.getElementById('commentsModalOverlay');
        if (overlay) {
            overlay.classList.remove('show');
            document.body.style.overflow = '';
            this.isModalOpen = false;

            const commentInput = document.getElementById('commentInput');
            if (commentInput) {
                commentInput.value = '';
                this.updateSubmitButton();
                this.autoResizeTextarea(commentInput);
            }
        }
    }

    async loadComments() {
        try {
            const response = await fetch(`/api/comments/news/${this.newsId}`);
            const comments = await response.json();
            this.renderComments(comments);
        } catch (error) {
            console.error('Ошибка загрузки комментариев:', error);
            this.showEmptyState();
        }
    }

    renderComments(comments) {
        const commentsList = document.getElementById('commentsList');
        const commentsEmpty = document.getElementById('commentsEmpty');

        if (!commentsList) return;

        commentsList.innerHTML = '';

        if (comments.length === 0) {
            this.showEmptyState();
            return;
        }

        if (commentsEmpty) {
            commentsEmpty.style.display = 'none';
        }

        comments.forEach(comment => {
            const commentElement = this.createCommentElement(comment);
            commentsList.appendChild(commentElement);
        });
    }

    createCommentElement(comment) {
        const li = document.createElement('li');
        li.className = 'comment-item';

        const authorInitial = comment.author?.name?.charAt(0).toUpperCase() || 'У';
        const timeAgo = this.formatTimeAgo(comment.createdAt);
        const isCurrentUserAuthor = this.currentUserId && comment.author &&
            String(comment.author.id) === String(this.currentUserId);

        const menuHtml = isCurrentUserAuthor ? `
            <div class="comment-menu">
                <button class="comment-menu-btn" onclick="commentMenuManager.toggle(${comment.id})">
                    <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                        <circle cx="8" cy="2" r="1.5"/>
                        <circle cx="8" cy="8" r="1.5"/>
                        <circle cx="8" cy="14" r="1.5"/>
                    </svg>
                </button>
                <div class="comment-menu-dropdown" id="menu-${comment.id}" style="display: none;">
                    <button class="comment-menu-item" onclick="commentMenuManager.delete(${comment.id})">
                        <svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                        </svg>
                        Удалить
                    </button>
                </div>
            </div>
        ` : '';

        li.innerHTML = `
            <div class="comment-avatar">${authorInitial}</div>
            <div class="comment-content">
                <div class="comment-author">${comment.author.name}</div>
                <div class="comment-text">${this.escapeHtml(comment.content)}</div>
                <div class="comment-meta">
                    <span class="comment-time">${timeAgo}</span>
                </div>
            </div>
            ${menuHtml}
        `;

        return li;
    }

    showEmptyState() {
        const commentsEmpty = document.getElementById('commentsEmpty');
        if (commentsEmpty) {
            commentsEmpty.style.display = 'block';
        }
    }

    updateSubmitButton() {
        const submitBtn = document.getElementById('submitCommentBtn');
        const commentInput = document.getElementById('commentInput');

        if (!submitBtn || !commentInput) return;

        const hasContent = commentInput.value.trim().length > 0;
        submitBtn.classList.toggle('active', hasContent);
    }

    autoResizeTextarea(textarea) {
        textarea.style.height = 'auto';
        textarea.style.height = Math.min(textarea.scrollHeight, 100) + 'px';
    }

    async submitComment() {
        const commentInput = document.getElementById('commentInput');
        const submitBtn = document.getElementById('submitCommentBtn');

        if (!commentInput || !submitBtn) return;

        const content = commentInput.value.trim();
        if (!content) return;

        submitBtn.disabled = true;
        submitBtn.textContent = 'Отправка...';

        try {
            const response = await fetch(`/api/comments/news/new/${this.newsId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [this.getCsrfHeader()]: this.getCsrfToken()
                },
                body: JSON.stringify({
                    postId: this.newsId,
                    content: content
                })
            });

            if (response.ok) {
                commentInput.value = '';
                this.updateSubmitButton();
                this.autoResizeTextarea(commentInput);
                await this.loadComments();
            } else {
                throw new Error(`HTTP ${response.status}`);
            }
        } catch (error) {
            console.error('Ошибка при добавлении комментария:', error);
            alert('Ошибка при добавлении комментария. Попробуйте еще раз.');
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Отправить';
        }
    }

    setupGlobalCommentFunctions() {
        // Создаем глобальный менеджер меню комментариев
        window.commentMenuManager = {
            toggle: (commentId) => {
                const menu = document.getElementById(`menu-${commentId}`);
                const allMenus = document.querySelectorAll('.comment-menu-dropdown');

                // Закрываем все остальные меню
                allMenus.forEach(m => {
                    if (m.id !== `menu-${commentId}`) {
                        m.style.display = 'none';
                    }
                });

                // Переключаем текущее меню
                if (menu) {
                    menu.style.display = menu.style.display === 'none' ? 'block' : 'none';
                }
            },

            delete: async (commentId) => {
                if (!confirm('Вы уверены, что хотите удалить этот комментарий?')) {
                    return;
                }

                try {
                    const response = await fetch(`/api/comments/delete/${commentId}`, {
                        method: 'POST',
                        headers: {
                            [this.getCsrfHeader()]: this.getCsrfToken()
                        }
                    });

                    if (response.ok) {
                        await this.loadComments();
                    } else {
                        throw new Error(`HTTP ${response.status}`);
                    }
                } catch (error) {
                    console.error('Ошибка при удалении комментария:', error);
                    alert('Ошибка при удалении комментария. Попробуйте еще раз.');
                }
            }
        };

        // Закрытие меню при клике вне его
        document.addEventListener('click', (event) => {
            if (!event.target.closest('.comment-menu')) {
                document.querySelectorAll('.comment-menu-dropdown').forEach(menu => {
                    menu.style.display = 'none';
                });
            }
        });
    }

    /**
     * Вспомогательные методы
     */
    formatTimeAgo(dateString) {
        try {
            const cleanDateString = dateString.replace(/(\.\d{3})\d*/, '$1');
            const parts = cleanDateString.split('T');
            const datePart = parts[0];
            const timePart = parts[1] || '00:00:00';
            const [year, month, day] = datePart.split('-').map(Number);
            const [hour, minute] = timePart.split(':').map(Number);
            const date = new Date(year, month - 1, day, hour, minute);

            const now = new Date();
            const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            const yesterday = new Date(today);
            yesterday.setDate(yesterday.getDate() - 1);

            const commentDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());

            if (commentDate.getTime() === today.getTime()) {
                return `сегодня в ${this.formatTime(hour, minute)}`;
            }

            if (commentDate.getTime() === yesterday.getTime()) {
                return `вчера в ${this.formatTime(hour, minute)}`;
            }

            const diffDays = Math.floor((now - date) / (1000 * 60 * 60 * 24));

            if (diffDays <= 7) {
                const weekDays = ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'];
                return `${weekDays[date.getDay()]} в ${this.formatTime(hour, minute)}`;
            }

            return this.formatDate(day, month);
        } catch (error) {
            console.error('Ошибка форматирования даты:', error);
            return 'недавно';
        }
    }

    formatTime(hour, minute) {
        return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
    }

    formatDate(day, month) {
        const months = ['янв', 'фев', 'мар', 'апр', 'май', 'июн',
            'июл', 'авг', 'сен', 'окт', 'ноя', 'дек'];
        return `${day} ${months[month - 1]}`;
    }

    escapeHtml(text) {
        const map = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        };
        return text.replace(/[&<>"']/g, m => map[m]);
    }

    getCsrfToken() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.getAttribute('content') : '';
    }

    getCsrfHeader() {
        const meta = document.querySelector('meta[name="_csrf_header"]');
        return meta ? meta.getAttribute('content') : 'X-CSRF-TOKEN';
    }
}

const newsDetailManager = new NewsDetailManager();