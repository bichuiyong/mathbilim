class ContentViewManagerFactory {
    static create(contentType) {
        switch (contentType.toLowerCase()) {
            case 'blog':
                return new BlogViewManager();
            case 'news':
                return new NewsViewManager();
            case 'event':
                return new EventViewManager();
            default:
                return new BaseContentViewManager({ contentType });
        }
    }

    static createFromPage() {
        const path = window.location.pathname;

        if (path.includes('/blog/')) {
            return ContentViewManagerFactory.create('blog');
        } else if (path.includes('/news/')) {
            return ContentViewManagerFactory.create('news');
        } else if (path.includes('/events/')) {
            return ContentViewManagerFactory.create('event');
        }

        if (document.querySelector('.blog-content, .blog-title')) {
            return ContentViewManagerFactory.create('blog');
        } else if (document.querySelector('.news-content, .news-title')) {
            return ContentViewManagerFactory.create('news');
        } else if (document.querySelector('.event-content, .event-title')) {
            return ContentViewManagerFactory.create('event');
        }

        return new BaseContentViewManager();
    }
}
