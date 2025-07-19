class ContentViewManager {
    constructor(options = {}) {
        this.options = {
            contentSelector: '.content, .blog-content, .news-content, .event-content',
            enableImageModal: true,
            enableSmoothScrolling: true,
            enableProgressBar: true,
            enableBackToTop: true,
            enableKeyboardNavigation: true,
            progressBarHeight: '3px',
            progressBarColor: 'linear-gradient(90deg, #2563eb, #3b82f6)',
            backToTopPosition: { bottom: '30px', right: '30px' },
            backToTopShowOffset: 300,
            ...options
        };

        this.init();
    }

    init() {
        if (this.options.enableSmoothScrolling) {
            this.initSmoothScrolling();
        }

        if (this.options.enableImageModal) {
            this.initImageModal();
        }

        if (this.options.enableProgressBar) {
            this.initProgressBar();
        }

        if (this.options.enableBackToTop) {
            this.initBackToTop();
        }

        if (this.options.enableKeyboardNavigation) {
            this.setupKeyboardNavigation();
        }

        this.initLazyLoading();
        this.initVideoControls();
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
        const contentImages = document.querySelectorAll(`${this.options.contentSelector} img`);

        contentImages.forEach(img => {
            if (!img.closest('.modal') && !img.classList.contains('no-modal')) {
                img.style.cursor = 'pointer';
                img.addEventListener('click', () => {
                    this.showImageModal(img);
                });
            }
        });
    }

    showImageModal(img) {
        const modalId = 'contentImageModal';
        
        const existingModal = document.getElementById(modalId);
        if (existingModal) {
            existingModal.remove();
        }

        const modalHtml = `
            <div class="modal fade" id="${modalId}" tabindex="-1">
                <div class="modal-dialog modal-xl modal-dialog-centered">
                    <div class="modal-content bg-transparent border-0">
                        <div class="modal-body p-0 text-center">
                            <img src="${img.src}" class="img-fluid" alt="${img.alt || ''}" style="max-height: 90vh;">
                            <button type="button" class="btn-close btn-close-white position-absolute top-0 end-0 m-3" 
                                    data-bs-dismiss="modal" aria-label="Закрыть"></button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.body.insertAdjacentHTML('beforeend', modalHtml);

        const imageModal = new bootstrap.Modal(document.getElementById(modalId));
        imageModal.show();

        document.getElementById(modalId).addEventListener('hidden.bs.modal', () => {
            document.getElementById(modalId).remove();
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
            height: ${this.options.progressBarHeight};
            background: ${this.options.progressBarColor};
            z-index: 1050;
            transition: width 0.2s ease;
        `;
        document.body.appendChild(progressBar);

        window.addEventListener('scroll', () => {
            const scrolled = (window.scrollY / (document.documentElement.scrollHeight - window.innerHeight)) * 100;
            progressBar.style.width = Math.min(Math.max(scrolled, 0), 100) + '%';
        });
    }

    // ==========================================
    // КНОПКА "НАВЕРХ"
    // ==========================================

    initBackToTop() {
        const backToTopBtn = document.createElement('button');
        backToTopBtn.className = 'btn btn-primary back-to-top';
        backToTopBtn.innerHTML = '<i class="fas fa-arrow-up"></i>';
        backToTopBtn.setAttribute('aria-label', 'Наверх');
        backToTopBtn.style.cssText = `
            position: fixed;
            bottom: ${this.options.backToTopPosition.bottom};
            right: ${this.options.backToTopPosition.right};
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: none;
            align-items: center;
            justify-content: center;
            z-index: 1040;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            border: none;
            transition: opacity 0.3s ease, transform 0.3s ease;
        `;

        document.body.appendChild(backToTopBtn);

        window.addEventListener('scroll', () => {
            if (window.scrollY > this.options.backToTopShowOffset) {
                backToTopBtn.style.display = 'flex';
                backToTopBtn.style.opacity = '1';
                backToTopBtn.style.transform = 'scale(1)';
            } else {
                backToTopBtn.style.opacity = '0';
                backToTopBtn.style.transform = 'scale(0.8)';
                setTimeout(() => {
                    if (backToTopBtn.style.opacity === '0') {
                        backToTopBtn.style.display = 'none';
                    }
                }, 300);
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

            if (e.key === 'Home' && !this.isInputFocused()) {
                e.preventDefault();
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }

            if (e.key === 'End' && !this.isInputFocused()) {
                e.preventDefault();
                window.scrollTo({ top: document.documentElement.scrollHeight, behavior: 'smooth' });
            }
        });

        document.addEventListener('keydown', (e) => {
            if (e.key === 'Tab') {
                const openModal = document.querySelector('.modal.show');
                if (openModal) {
                    this.handleModalTabNavigation(e, openModal);
                }
            }
        });
    }

    handleModalTabNavigation(e, modal) {
        const focusableElements = modal.querySelectorAll(
            'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
        );
        
        if (focusableElements.length === 0) return;

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

    isInputFocused() {
        const activeElement = document.activeElement;
        return activeElement && ['INPUT', 'TEXTAREA', 'SELECT'].includes(activeElement.tagName);
    }

    // ==========================================
    // ЛЕНИВАЯ ЗАГРУЗКА ИЗОБРАЖЕНИЙ
    // ==========================================

    initLazyLoading() {
        const images = document.querySelectorAll('img[data-src]');
        
        if ('IntersectionObserver' in window) {
            const imageObserver = new IntersectionObserver((entries, observer) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        const img = entry.target;
                        img.src = img.dataset.src;
                        img.removeAttribute('data-src');
                        img.classList.remove('lazy');
                        observer.unobserve(img);
                    }
                });
            });

            images.forEach(img => imageObserver.observe(img));
        } else {
            images.forEach(img => {
                img.src = img.dataset.src;
                img.removeAttribute('data-src');
            });
        }
    }

    // ==========================================
    // ВИДЕО КОНТРОЛЫ
    // ==========================================

    initVideoControls() {
        const videos = document.querySelectorAll(`${this.options.contentSelector} video`);
        
        videos.forEach(video => {
            video.addEventListener('loadstart', () => {
                video.style.cursor = 'pointer';
            });

            if (!video.hasAttribute('controls')) {
                video.setAttribute('controls', '');
            }
        });
    }

    // ==========================================
    // ПУБЛИЧНЫЕ МЕТОДЫ
    // ==========================================

    showImageInModal(imageSrc, imageAlt = '') {
        const tempImg = { src: imageSrc, alt: imageAlt };
        this.showImageModal(tempImg);
    }

    scrollToTop() {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    scrollToElement(elementId) {
        const element = document.getElementById(elementId);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    }

    updateProgressBar() {
        const scrolled = (window.scrollY / (document.documentElement.scrollHeight - window.innerHeight)) * 100;
        const progressBar = document.querySelector('.reading-progress');
        if (progressBar) {
            progressBar.style.width = Math.min(Math.max(scrolled, 0), 100) + '%';
        }
    }

    destroy() {
        const progressBar = document.querySelector('.reading-progress');
        const backToTopBtn = document.querySelector('.back-to-top');
        
        if (progressBar) progressBar.remove();
        if (backToTopBtn) backToTopBtn.remove();
    }
}