class BlogViewManager extends BaseContentViewManager {
    constructor() {
        super({
            contentType: 'blog',
            socialShareOptions: {
                contentType: 'blog',
                apiEndpoint: '/api',
                twitterHandle: 'MathBilimKG'
            },
            authorManagerOptions: {
                authorContainerSelector: '.blog-author-compact',
                apiEndpoint: '/api/users'
            },
            contentViewOptions: {
                contentSelector: '.blog-content',
                enableImageModal: true,
                enableSmoothScrolling: true,
                enableProgressBar: true,
                enableBackToTop: true
            }
        });
    }

    setupSpecificFeatures() {
        this.initRelatedPosts();
        this.initReadingTimeEstimate();
        this.setupBlogNavigation();
        this.initBlogAnalytics();
        this.setupBlogComments();
    }

    initRelatedPosts() {
        const relatedSection = document.querySelector('.related-posts, .related-blogs');
        if (relatedSection) {
            const relatedImages = relatedSection.querySelectorAll('img');
            relatedImages.forEach(img => {
                img.loading = 'lazy';
                img.addEventListener('error', () => {
                    img.src = '/static/images/placeholder-blog.jpg';
                });
            });

            const relatedLinks = relatedSection.querySelectorAll('a[href*="/blog/"]');
            relatedLinks.forEach(link => {
                link.addEventListener('mouseenter', () => {
                    this.preloadBlogPost(link.href);
                }, { once: true });
            });
        }
    }

    preloadBlogPost(url) {
        const link = document.createElement('link');
        link.rel = 'prefetch';
        link.href = url;
        document.head.appendChild(link);
    }

    initReadingTimeEstimate() {
        const content = document.querySelector('.blog-content');
        if (content) {
            const text = content.textContent || content.innerText;
            const words = text.trim().split(/\s+/).length;
            const readingTime = Math.ceil(words / 200);

            const readingTimeElement = document.createElement('span');
            readingTimeElement.className = 'reading-time text-muted';
            readingTimeElement.innerHTML = `<i class="fas fa-clock me-1"></i>${readingTime} мин чтения`;

            const metaSection = document.querySelector('.blog-meta');
            if (metaSection) {
                metaSection.appendChild(readingTimeElement);
            }

            this.trackReadingProgress(readingTime);
        }
    }

    trackReadingProgress(estimatedTime) {
        let readingStartTime = null;
        let hasStartedReading = false;

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting && !hasStartedReading) {
                    readingStartTime = Date.now();
                    hasStartedReading = true;
                    this.logAnalyticsEvent('blog_reading_started', {
                        blog_id: this.options.contentData?.id,
                        estimated_time: estimatedTime
                    });
                }
            });
        }, { threshold: 0.5 });

        const content = document.querySelector('.blog-content');
        if (content) {
            observer.observe(content);
        }

        window.addEventListener('beforeunload', () => {
            if (readingStartTime) {
                const actualTime = Math.floor((Date.now() - readingStartTime) / 1000);
                this.logAnalyticsEvent('blog_reading_completed', {
                    blog_id: this.options.contentData?.id,
                    actual_time: actualTime,
                    estimated_time: estimatedTime * 60
                });
            }
        });
    }

    setupBlogNavigation() {
        const prevNextLinks = document.querySelectorAll('.blog-nav a, .navigation-links a');

        prevNextLinks.forEach(link => {
            link.addEventListener('mouseenter', (e) => {
                const href = link.getAttribute('href');
                if (href && href.includes('/blog/')) {
                    this.preloadBlogPost(href);
                }
            }, { once: true });

            link.addEventListener('click', (e) => {
                if (window.ENABLE_SMOOTH_NAVIGATION) {
                    e.preventDefault();
                    this.smoothNavigateTo(link.href);
                }
            });
        });

        document.addEventListener('keydown', (e) => {
            if (!this.isInputFocused()) {
                if (e.key === 'ArrowLeft') {
                    const prevLink = document.querySelector('.blog-nav .prev, .navigation-links .prev');
                    if (prevLink) prevLink.click();
                } else if (e.key === 'ArrowRight') {
                    const nextLink = document.querySelector('.blog-nav .next, .navigation-links .next');
                    if (nextLink) nextLink.click();
                }
            }
        });
    }

    smoothNavigateTo(url) {
        document.body.style.opacity = '0.7';
        document.body.style.transition = 'opacity 0.3s ease';

        setTimeout(() => {
            window.location.href = url;
        }, 300);
    }

    initBlogAnalytics() {
        if (this.options.contentData?.id) {
            this.logAnalyticsEvent('blog_view', {
                blog_id: this.options.contentData.id,
                blog_title: this.options.contentData.title
            });

            let maxScroll = 0;
            let scrollMilestones = [25, 50, 75, 90];
            let reachedMilestones = [];

            window.addEventListener('scroll', this.throttle(() => {
                const scrollPercent = Math.round(
                    (window.scrollY / (document.documentElement.scrollHeight - window.innerHeight)) * 100
                );

                maxScroll = Math.max(maxScroll, scrollPercent);

                scrollMilestones.forEach(milestone => {
                    if (scrollPercent >= milestone && !reachedMilestones.includes(milestone)) {
                        reachedMilestones.push(milestone);
                        this.logAnalyticsEvent('blog_scroll_milestone', {
                            blog_id: this.options.contentData.id,
                            milestone: milestone
                        });
                    }
                });
            }, 1000));

            this.startTimeTracking();
        }
    }

    startTimeTracking() {
        const startTime = Date.now();
        let isActive = true;

        const activityEvents = ['click', 'scroll', 'keydown', 'mousemove'];

        activityEvents.forEach(event => {
            document.addEventListener(event, () => {
                isActive = true;
            });
        });

        setInterval(() => {
            if (isActive) {
                isActive = false;
            }
        }, 30000);

        window.addEventListener('beforeunload', () => {
            const timeSpent = Math.floor((Date.now() - startTime) / 1000);
            this.logAnalyticsEvent('blog_time_spent', {
                blog_id: this.options.contentData?.id,
                time_spent: timeSpent
            });
        });
    }

    setupBlogComments() {
        const commentsSection = document.querySelector('.comments-section, #comments');

        if (commentsSection) {
            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        this.loadComments();
                        observer.unobserve(entry.target);
                    }
                });
            });

            observer.observe(commentsSection);

            const commentForm = commentsSection.querySelector('form');
            if (commentForm) {
                commentForm.addEventListener('submit', (e) => {
                    this.handleCommentSubmit(e);
                });
            }
        }
    }

    loadComments() {
        console.log('Loading comments for blog:', this.options.contentData?.id);

        this.logAnalyticsEvent('blog_comments_viewed', {
            blog_id: this.options.contentData?.id
        });
    }

    handleCommentSubmit(event) {
        event.preventDefault();

        const form = event.target;
        const formData = new FormData(form);

        formData.append('blogId', this.options.contentData?.id);
        console.log('Submitting comment for blog:', this.options.contentData?.id);

        this.logAnalyticsEvent('blog_comment_submitted', {
            blog_id: this.options.contentData?.id
        });
    }

    throttle(func, limit) {
        let inThrottle;
        return function() {
            const args = arguments;
            const context = this;
            if (!inThrottle) {
                func.apply(context, args);
                inThrottle = true;
                setTimeout(() => inThrottle = false, limit);
            }
        };
    }

    isInputFocused() {
        const activeElement = document.activeElement;
        return activeElement && ['INPUT', 'TEXTAREA', 'SELECT'].includes(activeElement.tagName);
    }

    logAnalyticsEvent(eventName, eventData = {}) {
        if (typeof gtag !== 'undefined') {
            gtag('event', eventName, {
                'custom_parameter': eventData,
                'event_category': 'blog',
                'event_label': this.options.contentData?.title || 'unknown'
            });
        }

        if (window.analytics && typeof window.analytics.track === 'function') {
            window.analytics.track(eventName, eventData);
        }

        console.log('Analytics event:', eventName, eventData);
    }

    shareBlog() {
        if (this.socialShareManager) {
            this.socialShareManager.shareToFacebook();
        }
    }

    getBlogData() {
        return this.options.contentData;
    }

    updateBlogViews() {
        const viewCountElement = document.querySelector('.stat-item .fa-eye + text(), .view-count');
        if (viewCountElement) {
            const currentViews = parseInt(viewCountElement.textContent) || 0;
            viewCountElement.textContent = currentViews + 1;
        }
    }

    extractContentDataFromDOM() {
        const baseData = super.extractContentDataFromDOM();

        const categoryElement = document.querySelector('.blog-category, .category');
        const tagsElements = document.querySelectorAll('.blog-tags .tag, .tags .tag');

        return {
            ...baseData,
            category: categoryElement ? categoryElement.textContent.trim() : null,
            tags: Array.from(tagsElements).map(tag => tag.textContent.trim()),
            type: 'blog'
        };
    }
}

let blogViewManager;

function shareToFacebook() {
    blogViewManager?.getSocialShareManager()?.shareToFacebook();
}

function shareToTwitter() {
    blogViewManager?.getSocialShareManager()?.shareToTwitter();
}

function shareToTelegram() {
    blogViewManager?.getSocialShareManager()?.shareToTelegram();
}

function shareToWhatsApp() {
    blogViewManager?.getSocialShareManager()?.shareToWhatsApp();
}

function copyLink() {
    blogViewManager?.getSocialShareManager()?.copyLink();
}

function shareBlog() {
    blogViewManager?.shareBlog();
}

function initBlogView() {
    blogViewManager = new BlogViewManager();
    window.blogViewManager = blogViewManager;

    console.log('BlogViewManager initialized successfully');
}

document.addEventListener('DOMContentLoaded', () => {
    initBlogView();
});

window.addEventListener('beforeunload', () => {
    if (blogViewManager) {
        blogViewManager.destroy();
    }
});