class BaseContentViewManager {
    constructor(options = {}) {
        this.options = {
            contentType: 'content',
            contentData: null,
            authorManagerOptions: {},
            socialShareOptions: {},
            contentViewOptions: {},
            ...options
        };

        this.authorManager = null;
        this.socialShareManager = null;
        this.contentViewManager = null;

        this.init();
    }

    init() {
        this.extractContentData();
        this.initComponents();
        this.setupAdditionalFeatures();
    }

    extractContentData() {
        if (!this.options.contentData) {
            this.options.contentData = this.extractContentDataFromWindow() || this.extractContentDataFromDOM();
        }
    }

    extractContentDataFromWindow() {
        const dataKeys = ['blogData', 'newsData', 'eventData', 'contentData'];
        for (const key of dataKeys) {
            if (window[key]) {
                return window[key];
            }
        }
        return null;
    }

    extractContentDataFromDOM() {
        const titleElement = document.querySelector('h1, .title, .blog-title, .news-title, .event-title');
        const title = titleElement ? titleElement.textContent.trim() : 'Untitled';
        
        return {
            id: this.extractIdFromURL(),
            title: title,
            shareUrl: window.location.href,
            description: this.extractDescription()
        };
    }

    extractIdFromURL() {
        const pathParts = window.location.pathname.split('/');
        const lastPart = pathParts[pathParts.length - 1];
        return isNaN(lastPart) ? null : parseInt(lastPart);
    }

    extractDescription() {
        const metaDescription = document.querySelector('meta[name="description"]');
        return metaDescription ? metaDescription.getAttribute('content') : '';
    }

    initComponents() {
        this.authorManager = new AuthorManager({
            ...this.options.authorManagerOptions
        });

        this.socialShareManager = new SocialShareManager({
            contentData: this.options.contentData,
            contentType: this.options.contentType,
            ...this.options.socialShareOptions
        });

        this.contentViewManager = new ContentViewManager({
            ...this.options.contentViewOptions
        });
    }

    setupAdditionalFeatures() {
        this.initAccessibility();
        this.initPerformanceOptimizations();
    }

    initAccessibility() {
        this.addSkipLinks();
        this.improveHeadingStructure();
        this.addAriaLabels();
    }

    addSkipLinks() {
        const skipLink = document.createElement('a');
        skipLink.href = '#main-content';
        skipLink.textContent = 'Перейти к основному содержанию';
        skipLink.className = 'skip-link sr-only';
        skipLink.style.cssText = `
            position: absolute;
            top: -40px;
            left: 6px;
            background: #000;
            color: #fff;
            padding: 8px;
            text-decoration: none;
            z-index: 1000;
        `;

        skipLink.addEventListener('focus', () => {
            skipLink.style.top = '6px';
        });

        skipLink.addEventListener('blur', () => {
            skipLink.style.top = '-40px';
        });

        document.body.insertBefore(skipLink, document.body.firstChild);

        const mainContent = document.querySelector('main, .main-content, article, .content');
        if (mainContent && !mainContent.id) {
            mainContent.id = 'main-content';
        }
    }

    improveHeadingStructure() {
        const headings = document.querySelectorAll('h1, h2, h3, h4, h5, h6');
        let previousLevel = 0;

        headings.forEach(heading => {
            const level = parseInt(heading.tagName.charAt(1));
            
            if (!heading.hasAttribute('tabindex')) {
                heading.setAttribute('tabindex', '0');
            }
        });
    }

    addAriaLabels() {
        const socialButtons = document.querySelectorAll('.social-btn');
        socialButtons.forEach(btn => {
            if (!btn.hasAttribute('aria-label')) {
                const icon = btn.querySelector('i');
                if (icon) {
                    const platform = this.detectSocialPlatform(icon.className);
                    if (platform) {
                        btn.setAttribute('aria-label', `Поделиться в ${platform}`);
                    }
                }
            }
        });

        const buttons = document.querySelectorAll('button:not([aria-label]):not([aria-labelledby])');
        buttons.forEach(btn => {
            const text = btn.textContent.trim();
            if (text) {
                btn.setAttribute('aria-label', text);
            }
        });
    }

    detectSocialPlatform(iconClasses) {
        const platforms = {
            'facebook': 'Facebook',
            'twitter': 'Twitter',
            'telegram': 'Telegram',
            'whatsapp': 'WhatsApp',
            'vk': 'VKontakte',
            'linkedin': 'LinkedIn'
        };

        for (const [key, name] of Object.entries(platforms)) {
            if (iconClasses.includes(key)) {
                return name;
            }
        }
        return null;
    }

    initPerformanceOptimizations() {
        this.preloadCriticalResources();
        this.optimizeImages();
        this.setupIntersectionObserver();
    }

    preloadCriticalResources() {
        const criticalImages = document.querySelectorAll('.hero-image, .main-image, .featured-image');
        criticalImages.forEach(img => {
            if (img.src && !img.complete) {
                const link = document.createElement('link');
                link.rel = 'preload';
                link.as = 'image';
                link.href = img.src;
                document.head.appendChild(link);
            }
        });
    }

    optimizeImages() {
        const images = document.querySelectorAll('img:not([loading])');
        const viewportHeight = window.innerHeight;

        images.forEach(img => {
            const rect = img.getBoundingClientRect();
            if (rect.top > viewportHeight) {
                img.loading = 'lazy';
            }
        });
    }

    setupIntersectionObserver() {
        if ('IntersectionObserver' in window && this.options.contentData?.id) {
            const content = document.querySelector('.content, .blog-content, .news-content, .event-content');
            if (content) {
                const observer = new IntersectionObserver((entries) => {
                    entries.forEach(entry => {
                        if (entry.isIntersecting) {
                            this.trackReadingTime();
                        }
                    });
                }, { threshold: 0.5 });

                observer.observe(content);
            }
        }
    }

    trackReadingTime() {
        if (!this.readingStartTime) {
            this.readingStartTime = Date.now();
        }
    }

    getAuthorManager() {
        return this.authorManager;
    }

    getSocialShareManager() {
        return this.socialShareManager;
    }

    getContentViewManager() {
        return this.contentViewManager;
    }

    getContentData() {
        return this.options.contentData;
    }

    updateContentData(updates) {
        this.options.contentData = { ...this.options.contentData, ...updates };
        if (this.socialShareManager) {
            this.socialShareManager.updateContentData(updates);
        }
    }

    // Методы для переопределения в дочерних классах
    setupSpecificFeatures() {
        // Переопределяется в дочерних классах для специфичных функций
    }

    getReadingTime() {
        if (this.readingStartTime) {
            return Math.floor((Date.now() - this.readingStartTime) / 1000);
        }
        return 0;
    }

    destroy() {
        if (this.contentViewManager) {
            this.contentViewManager.destroy();
        }
        
        const skipLink = document.querySelector('.skip-link');
        if (skipLink) {
            skipLink.remove();
        }
    }
}