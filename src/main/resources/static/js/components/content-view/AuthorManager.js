class AuthorManager {
    constructor(options = {}) {
        this.options = {
            authorContainerSelector: '.author-compact, .blog-author-compact, .news-author-compact, .event-author-compact',
            authorNameSelector: '.author-name',
            apiEndpoint: '/api/users',
            loadingText: 'Загрузка...',
            errorText: 'Ошибка загрузки',
            unknownAuthorText: 'Неизвестный автор',
            cacheEnabled: true,
            ...options
        };

        this.authorCache = new Map();
        this.init();
    }

    init() {
        this.loadAllAuthors();
    }

    async loadAllAuthors() {
        const authorContainers = document.querySelectorAll(this.options.authorContainerSelector);
        
        const loadPromises = Array.from(authorContainers).map(container => {
            const authorId = container.getAttribute('data-author-id');
            if (authorId) {
                return this.loadAuthorData(container, authorId);
            }
            return Promise.resolve();
        });

        await Promise.allSettled(loadPromises);
    }

    async loadAuthorData(container, authorId) {
        if (!authorId || !container) {
            console.log('AuthorManager: Автор не найден или ID отсутствует');
            return;
        }

        try {
            this.showAuthorLoading(container);
            const authorData = await this.fetchAuthorData(authorId);
            this.renderAuthorData(container, authorData);

        } catch (error) {
            console.error('AuthorManager: Ошибка загрузки данных автора:', error);
            this.showAuthorError(container);
        }
    }

    async fetchAuthorData(authorId) {
        if (this.options.cacheEnabled && this.authorCache.has(authorId)) {
            console.log('AuthorManager: Данные автора взяты из кэша:', authorId);
            return this.authorCache.get(authorId);
        }

        console.log('AuthorManager: Загружаем данные автора с сервера:', authorId);

        const response = await fetch(`${this.options.apiEndpoint}/${authorId}`, {
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
        
        if (this.options.cacheEnabled) {
            this.authorCache.set(authorId, authorData);
        }
        
        return authorData;
    }

    showAuthorLoading(container) {
        const authorNameElement = container.querySelector(this.options.authorNameSelector);
        if (authorNameElement) {
            authorNameElement.textContent = this.options.loadingText;
            authorNameElement.classList.add('author-loading');
            authorNameElement.classList.remove('author-error');
        }
    }

    renderAuthorData(container, authorData) {
        const authorNameElement = container.querySelector(this.options.authorNameSelector);
        if (authorNameElement) {
            const fullName = this.buildFullName(authorData);

            authorNameElement.textContent = fullName;
            authorNameElement.classList.remove('author-loading', 'author-error');

            if (authorData.id) {
                this.createAuthorLink(authorNameElement, authorData.id, fullName);
            }

            container.classList.add('author-loaded');
        }
    }

    buildFullName(authorData) {
        const parts = [];
        
        if (authorData.name && authorData.name.trim()) {
            parts.push(authorData.name.trim());
        }
        
        if (authorData.surname && authorData.surname.trim()) {
            parts.push(authorData.surname.trim());
        }

        return parts.length > 0 ? parts.join(' ') : this.options.unknownAuthorText;
    }

    createAuthorLink(authorNameElement, authId, fullName) {
        const link = document.createElement('a');
        link.href = `/users/${authId}`;
        link.textContent = fullName;
        link.className = 'author-name';
        link.style.textDecoration = 'none';

        link.classList.add(...authorNameElement.classList);
        link.classList.remove('author-loading', 'author-error');

        authorNameElement.parentNode.replaceChild(link, authorNameElement);
    }

    showAuthorError(container) {
        const authorNameElement = container.querySelector(this.options.authorNameSelector);
        if (authorNameElement) {
            authorNameElement.textContent = this.options.errorText;
            authorNameElement.classList.remove('author-loading');
            authorNameElement.classList.add('author-error');
        }
    }

    getCSRFHeaders() {
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

        if (csrfToken && csrfHeader) {
            return { [csrfHeader]: csrfToken };
        }

        return {};
    }

    async loadAuthor(authorId, containerSelector) {
        const container = document.querySelector(containerSelector);
        if (container) {
            await this.loadAuthorData(container, authorId);
        }
    }

    getAuthorFromCache(authorId) {
        return this.authorCache.get(authorId);
    }

    clearCache() {
        this.authorCache.clear();
    }

    setCacheEnabled(enabled) {
        this.options.cacheEnabled = enabled;
        if (!enabled) {
            this.clearCache();
        }
    }
}