class ContentCreateManagerFactory {
    static create(contentType) {
        switch (contentType.toLowerCase()) {
            case 'blog':
                return new BlogCreateManager();
            case 'news':
                return new NewsCreateManager();
            case 'event':
                return new EventCreateManager();
            default:
                throw new Error(`Unknown content type: ${contentType}`);
        }
    }

    static createFromPage() {
        const path = window.location.pathname;
        
        if (path.includes('/blog/create')) {
            return ContentCreateManagerFactory.create('blog');
        } else if (path.includes('/news/create')) {
            return ContentCreateManagerFactory.create('news');
        } else if (path.includes('/events/create')) {
            return ContentCreateManagerFactory.create('event');
        }
        
        if (document.getElementById('blogForm')) {
            return ContentCreateManagerFactory.create('blog');
        } else if (document.getElementById('newsForm')) {
            return ContentCreateManagerFactory.create('news');
        } else if (document.getElementById('eventForm')) {
            return ContentCreateManagerFactory.create('event');
        }

        throw new Error('Unable to determine content type from page');
    }
}