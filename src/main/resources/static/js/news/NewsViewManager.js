class NewsViewManager extends BaseContentViewManager {
    constructor() {
        super({
            contentType: 'news',
            socialShareOptions: {
                contentType: 'news',
                apiEndpoint: '/api'
            },
            authorManagerOptions: {
                authorContainerSelector: '.news-author-compact'
            }
        });
    }

    setupSpecificFeatures() {
        this.initNewsTimestamp();
        this.initNewsCategoryColors();
        this.setupNewsSharing();
    }

    initNewsTimestamp() {
        const timestampElements = document.querySelectorAll('.news-date, .news-timestamp');
        timestampElements.forEach(element => {
            const dateString = element.getAttribute('datetime') || element.textContent;
            const date = new Date(dateString);
            const now = new Date();
            const diffTime = Math.abs(now - date);
            const diffHours = Math.ceil(diffTime / (1000 * 60 * 60));

            if (diffHours < 24) {
                element.innerHTML = `<i class="fas fa-clock me-1"></i>${diffHours} часов назад`;
            } else if (diffHours < 48) {
                element.innerHTML = `<i class="fas fa-clock me-1"></i>Вчера`;
            }
        });
    }

    initNewsCategoryColors() {
        const categoryElements = document.querySelectorAll('.news-category');
        const categoryColors = {
            'education': '#2563eb',
            'technology': '#10b981',
            'science': '#8b5cf6',
            'events': '#f59e0b',
            'announcements': '#ef4444'
        };

        categoryElements.forEach(element => {
            const category = element.textContent.toLowerCase().trim();
            const color = categoryColors[category];
            if (color) {
                element.style.color = color;
                element.style.borderLeft = `3px solid ${color}`;
                element.style.paddingLeft = '8px';
            }
        });
    }

    setupNewsSharing() {
        if (this.socialShareManager) {
            this.socialShareManager.options.twitterHashtags = ['MathBilim', 'Образование', 'Новости'];
        }
    }
}