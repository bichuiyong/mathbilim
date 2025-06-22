/**
 * Blog View Manager с асинхронной загрузкой данных автора
 */
class BlogViewManager {
    constructor() {
        this.blogData = window.blogData || {};
        this.authorCache = new Map(); // Кэш для авторов
        this.init();
    }

    init() {
        this.initSmoothScrolling();
        this.initImageModal();
        this.initProgressBar();
        this.initBackToTop();
        this.setupKeyboardNavigation();

        this.loadAuthorData();
        this.loadRelatedAuthorsData();
    }

    // ==========================================
    // АСИНХРОННАЯ ЗАГРУЗКА АВТОРА
    // ==========================================

    async loadAuthorData() {
        const authorContainer = document.querySelector('.blog-author');
        const authorId = authorContainer?.getAttribute('data-author-id');

        if (!authorId || !authorContainer) {
            console.log('Автор не найден или ID отсутствует');
            return;
        }

        try {
            this.showAuthorSkeleton(authorContainer);
            const authorData = await this.fetchAuthorData(authorId);

            this.renderAuthorData(authorContainer, authorData);

        } catch (error) {
            console.error('Ошибка загрузки данных автора:', error);
            this.showAuthorError(authorContainer);
        }
    }

    async fetchAuthorData(authorId) {
        if (this.authorCache.has(authorId)) {
            console.log('Данные автора взяты из кэша:', authorId);
            return this.authorCache.get(authorId);
        }

        console.log('Загружаем данные автора с сервера:', authorId);

        const response = await fetch(`/api/users/${authorId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                ...this.getCSRFHeaders()
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        const authorData = await response.json();
        this.authorCache.set(authorId, authorData);
        return authorData;
    }

    showAuthorSkeleton(container) {
        container.innerHTML = `
            <div class="author-info">
                <div class="author-avatar">
                    <div class="skeleton-avatar"></div>
                </div>
                <div class="author-details">
                    <div class="skeleton-text skeleton-name"></div>
                    <div class="skeleton-text skeleton-role"></div>
                </div>
            </div>
        `;
    }

    renderAuthorData(container, authorData) {
        const profileImageSrc = authorData.avatar
            ? `/api/files/${authorData.avatar.id}/view`
            : null;

        const fullName = authorData.name + ' ' + authorData.surname? authorData.surname : '';

        container.innerHTML = `
            <div class="author-info">
                <div class="author-avatar">
                    ${profileImageSrc
            ? `<img src="${profileImageSrc}" 
                               alt="${fullName}" 
                               class="avatar-img"
                               loading="lazy">`
            : `<div class="avatar-placeholder">${fullName}</div>`
        }
                </div>
                <div class="author-details">
                    <a href="/users/${authorData.authId}" class="author-name">
                        ${fullName || 'Неизвестный автор'}
                    </a>
                    <span class="author-role">Автор</span>
                </div>
            </div>
        `;

        container.classList.add('author-loaded');
    }

    showAuthorError(container) {
        container.innerHTML = `
            <div class="author-info">
                <div class="author-avatar">
                    <div class="avatar-placeholder error">
                        <i class="fas fa-exclamation-triangle"></i>
                    </div>
                </div>
                <div class="author-details">
                    <span class="author-name text-muted">Ошибка загрузки автора</span>
                    <span class="author-role">Автор</span>
                </div>
            </div>
        `;
    }

    async loadRelatedAuthorsData() {
        const relatedCards = document.querySelectorAll('.related-card');

        for (const card of relatedCards) {
            const authorId = card.getAttribute('data-author-id');
            if (authorId) {
                try {
                    const authorData = await this.fetchAuthorData(authorId);
                    this.renderRelatedAuthorData(card, authorData);
                } catch (error) {
                    console.error('Ошибка загрузки автора похожего блога:', error);
                }
            }
        }
    }

    renderRelatedAuthorData(card, authorData) {
        const authorContainer = card.querySelector('.related-author');
        if (!authorContainer) return;

        const authorInitial = authorData.fullName
            ? authorData.fullName.charAt(0).toUpperCase()
            : '?';

        authorContainer.innerHTML = `
            <div class="author-avatar-small">
                ${authorData.profileImage
            ? `<img src="/api/files/${authorData.profileImage.id}/view" 
                           alt="${authorData.fullName}">`
            : `<div class="avatar-placeholder-small">${authorInitial}</div>`
        }
            </div>
            <span class="author-name">${authorData.fullName || 'Неизвестный автор'}</span>
        `;
    }

    getCSRFHeaders() {
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

        if (csrfToken && csrfHeader) {
            return { [csrfHeader]: csrfToken };
        }

        return {};
    }

    // ==========================================
    // СОЦИАЛЬНЫЙ ШАРИНГ (оставляем как было)
    // ==========================================

    shareToFacebook() {
        const url = encodeURIComponent(this.blogData.shareUrl);
        const shareUrl = `https://www.facebook.com/sharer/sharer.php?u=${url}`;
        this.openShareWindow(shareUrl, 'Facebook');
        this.incrementShareCount();
    }

    shareToTwitter() {
        const url = encodeURIComponent(this.blogData.shareUrl);
        const text = encodeURIComponent(this.blogData.title);
        const shareUrl = `https://twitter.com/intent/tweet?url=${url}&text=${text}&via=MathBilimKG`;
        this.openShareWindow(shareUrl, 'Twitter');
        this.incrementShareCount();
    }

    shareToTelegram() {
        const url = encodeURIComponent(this.blogData.shareUrl);
        const text = encodeURIComponent(`${this.blogData.title}\n\n`);
        const shareUrl = `https://t.me/share/url?url=${url}&text=${text}`;
        this.openShareWindow(shareUrl, 'Telegram');
        this.incrementShareCount();
    }

    shareToWhatsApp() {
        const url = encodeURIComponent(this.blogData.shareUrl);
        const text = encodeURIComponent(`${this.blogData.title}\n\n${this.blogData.shareUrl}`);
        const shareUrl = `https://wa.me/?text=${text}`;
        this.openShareWindow(shareUrl, 'WhatsApp');
        this.incrementShareCount();
    }

    openShareWindow(url, platform) {
        const width = 600;
        const height = 400;
        const left = (window.innerWidth - width) / 2;
        const top = (window.innerHeight - height) / 2;

        window.open(
            url,
            `share${platform}`,
            `width=${width},height=${height},left=${left},top=${top},resizable=yes,scrollbars=yes`
        );
    }

    async copyLink() {
        try {
            await navigator.clipboard.writeText(this.blogData.shareUrl);
            this.showNotification('Ссылка скопирована в буфер обмена!', 'success');
            this.incrementShareCount();
        } catch (err) {
            this.fallbackCopyLink();
        }
    }

    fallbackCopyLink() {
        const textArea = document.createElement('textarea');
        textArea.value = this.blogData.shareUrl;
        textArea.style.position = 'fixed';
        textArea.style.opacity = '0';
        document.body.appendChild(textArea);
        textArea.select();

        try {
            document.execCommand('copy');
            this.showNotification('Ссылка скопирована в буфер обмена!', 'success');
            this.incrementShareCount();
        } catch (err) {
            this.showNotification('Не удалось скопировать ссылку', 'error');
        }

        document.body.removeChild(textArea);
    }

    // ==========================================
    // МОДАЛЬНОЕ ОКНО ШАРИНГА
    // ==========================================

    shareBlog() {
        if (navigator.share) {
            navigator.share({
                title: this.blogData.title,
                text: this.blogData.description,
                url: this.blogData.shareUrl
            }).then(() => {
                this.incrementShareCount();
            }).catch(() => {
                this.showShareModal();
            });
        } else {
            this.showShareModal();
        }
    }

    showShareModal() {
        const shareModal = new bootstrap.Modal(document.getElementById('shareModal'));
        shareModal.show();
    }

    // ==========================================
    // УВЕЛИЧЕНИЕ СЧЕТЧИКА ШАРИНГА
    // ==========================================

    async incrementShareCount() {
        try {
            const headers = {
                'Content-Type': 'application/json',
                ...this.getCSRFHeaders()
            };

            await fetch(`/api/blog/${this.blogData.id}/share`, {
                method: 'POST',
                headers: headers
            });

            this.updateShareCountDisplay();
        } catch (error) {
            console.log('Не удалось обновить счетчик репостов');
        }
    }

    updateShareCountDisplay() {
        const shareCountElement = document.querySelector('.blog-stats .stat-item:last-child');
        if (shareCountElement) {
            const currentCount = parseInt(shareCountElement.textContent.trim()) || 0;
            shareCountElement.innerHTML = `<i class="fas fa-share me-1"></i>${currentCount + 1}`;
        }
    }

    // ==========================================
    // УВЕДОМЛЕНИЯ
    // ==========================================

    showNotification(message, type = 'info') {
        const existingNotifications = document.querySelectorAll('.blog-notification');
        existingNotifications.forEach(notification => notification.remove());

        const notification = document.createElement('div');
        notification.className = `blog-notification alert alert-${type} alert-dismissible fade show`;
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1060;
            min-width: 300px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        `;

        notification.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        document.body.appendChild(notification);

        setTimeout(() => {
            if (notification.parentNode) {
                notification.remove();
            }
        }, 4000);
    }

    // ==========================================
    // ПЛАВНАЯ ПРОКРУТКА
    // ==========================================

    initSmoothScrolling() {
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', (e) => {
                const targetId = anchor.getAttribute('href').substring(1);
                const targetElement = document.getElementById(targetId);

                if (targetElement) {
                    e.preventDefault();
                    targetElement.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });
    }

    // ==========================================
    // МОДАЛЬНОЕ ОКНО ИЗОБРАЖЕНИЙ
    // ==========================================

    initImageModal() {
        const contentImages = document.querySelectorAll('.blog-content img');

        contentImages.forEach(img => {
            img.style.cursor = 'pointer';
            img.addEventListener('click', () => {
                this.showImageModal(img);
            });
        });
    }

    showImageModal(img) {
        const modalHtml = `
            <div class="modal fade" id="imageModal" tabindex="-1">
                <div class="modal-dialog modal-xl modal-dialog-centered">
                    <div class="modal-content bg-transparent border-0">
                        <div class="modal-body p-0 text-center">
                            <img src="${img.src}" class="img-fluid" alt="${img.alt}" style="max-height: 90vh;">
                            <button type="button" class="btn-close btn-close-white position-absolute top-0 end-0 m-3" 
                                    data-bs-dismiss="modal"></button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        const existingModal = document.getElementById('imageModal');
        if (existingModal) {
            existingModal.remove();
        }

        document.body.insertAdjacentHTML('beforeend', modalHtml);

        const imageModal = new bootstrap.Modal(document.getElementById('imageModal'));
        imageModal.show();

        document.getElementById('imageModal').addEventListener('hidden.bs.modal', () => {
            document.getElementById('imageModal').remove();
        });
    }

    // ==========================================
    // ПРОГРЕСС БАР ЧТЕНИЯ
    // ==========================================

    initProgressBar() {
        const progressBar = document.createElement('div');
        progressBar.className = 'reading-progress';
        progressBar.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 0%;
            height: 3px;
            background: linear-gradient(90deg, #2563eb, #3b82f6);
            z-index: 1050;
            transition: width 0.2s ease;
        `;
        document.body.appendChild(progressBar);

        window.addEventListener('scroll', () => {
            const scrolled = (window.scrollY / (document.documentElement.scrollHeight - window.innerHeight)) * 100;
            progressBar.style.width = Math.min(scrolled, 100) + '%';
        });
    }

    // ==========================================
    // КНОПКА "НАВЕРХ"
    // ==========================================

    initBackToTop() {
        const backToTopBtn = document.createElement('button');
        backToTopBtn.className = 'btn btn-primary back-to-top';
        backToTopBtn.innerHTML = '<i class="fas fa-arrow-up"></i>';
        backToTopBtn.style.cssText = `
            position: fixed;
            bottom: 30px;
            right: 30px;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: none;
            z-index: 1040;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            border: none;
        `;

        document.body.appendChild(backToTopBtn);

        window.addEventListener('scroll', () => {
            if (window.scrollY > 300) {
                backToTopBtn.style.display = 'flex';
                backToTopBtn.style.alignItems = 'center';
                backToTopBtn.style.justifyContent = 'center';
            } else {
                backToTopBtn.style.display = 'none';
            }
        });

        backToTopBtn.addEventListener('click', () => {
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        });
    }

    // ==========================================
    // КЛАВИАТУРНАЯ НАВИГАЦИЯ
    // ==========================================

    setupKeyboardNavigation() {
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape') {
                const openModals = document.querySelectorAll('.modal.show');
                openModals.forEach(modal => {
                    const modalInstance = bootstrap.Modal.getInstance(modal);
                    if (modalInstance) {
                        modalInstance.hide();
                    }
                });
            }

            if ((e.ctrlKey || e.metaKey) && e.key === 'c' && !window.getSelection().toString()) {
                const activeElement = document.activeElement;
                if (!activeElement || !['INPUT', 'TEXTAREA'].includes(activeElement.tagName)) {
                    e.preventDefault();
                    this.copyLink();
                }
            }
        });
    }
}

// ==========================================
// ГЛОБАЛЬНЫЕ ФУНКЦИИ
// ==========================================

let blogViewManager;

function shareBlog() {
    blogViewManager?.shareBlog();
}

function shareToFacebook() {
    blogViewManager?.shareToFacebook();
}

function shareToTwitter() {
    blogViewManager?.shareToTwitter();
}

function shareToTelegram() {
    blogViewManager?.shareToTelegram();
}

function shareToWhatsApp() {
    blogViewManager?.shareToWhatsApp();
}

function copyLink() {
    blogViewManager?.copyLink();
}

// ==========================================
// ИНИЦИАЛИЗАЦИЯ
// ==========================================

document.addEventListener('DOMContentLoaded', () => {
    blogViewManager = new BlogViewManager();

    const videos = document.querySelectorAll('.blog-content video');
    videos.forEach(video => {
        video.addEventListener('loadstart', () => {
            video.style.cursor = 'pointer';
        });
    });

    const relatedImages = document.querySelectorAll('.related-card img');
    relatedImages.forEach(img => {
        img.loading = 'lazy';
    });

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Tab') {
            const openModal = document.querySelector('.modal.show');
            if (openModal) {
                const focusableElements = openModal.querySelectorAll(
                    'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
                );
                const firstElement = focusableElements[0];
                const lastElement = focusableElements[focusableElements.length - 1];

                if (e.shiftKey && document.activeElement === firstElement) {
                    e.preventDefault();
                    lastElement.focus();
                } else if (!e.shiftKey && document.activeElement === lastElement) {
                    e.preventDefault();
                    firstElement.focus();
                }
            }
        }
    });
});