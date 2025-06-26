class SocialShareManager {
    constructor(options = {}) {
        this.options = {
            contentData: null,
            shareCountSelector: '.stat-item:last-child',
            copyLinkButtonId: 'copyLinkBtn',
            apiEndpoint: '/api',
            contentType: 'blog',
            platforms: {
                facebook: true,
                twitter: true,
                telegram: true,
                whatsapp: true,
                copyLink: true
            },
            twitterHandle: 'MathBilimKG',
            ...options
        };

        this.contentData = this.options.contentData || this.extractContentDataFromWindow();
        this.init();
    }

    init() {
        if (!this.contentData) {
            console.warn('SocialShareManager: Content data not provided');
            return;
        }

        this.bindGlobalFunctions();
    }

    extractContentDataFromWindow() {
        return window.blogData || window.newsData || window.eventData || null;
    }

    bindGlobalFunctions() {
        window.shareToFacebook = () => this.shareToFacebook();
        window.shareToTwitter = () => this.shareToTwitter();
        window.shareToTelegram = () => this.shareToTelegram();
        window.shareToWhatsApp = () => this.shareToWhatsApp();
        window.copyLink = () => this.copyLink();
    }

    shareToFacebook() {
        if (!this.options.platforms.facebook) return;

        const url = encodeURIComponent(this.contentData.shareUrl);
        const shareUrl = `https://www.facebook.com/sharer/sharer.php?u=${url}`;
        this.openShareWindow(shareUrl, 'Facebook');
        this.incrementShareCount();
    }

    shareToTwitter() {
        if (!this.options.platforms.twitter) return;

        const url = encodeURIComponent(this.contentData.shareUrl);
        const text = encodeURIComponent(this.contentData.title);
        const via = this.options.twitterHandle ? `&via=${this.options.twitterHandle}` : '';
        const shareUrl = `https://twitter.com/intent/tweet?url=${url}&text=${text}${via}`;
        this.openShareWindow(shareUrl, 'Twitter');
        this.incrementShareCount();
    }

    shareToTelegram() {
        if (!this.options.platforms.telegram) return;

        const url = encodeURIComponent(this.contentData.shareUrl);
        const text = encodeURIComponent(`${this.contentData.title}\n\n`);
        const shareUrl = `https://t.me/share/url?url=${url}&text=${text}`;
        this.openShareWindow(shareUrl, 'Telegram');
        this.incrementShareCount();
    }

    shareToWhatsApp() {
        if (!this.options.platforms.whatsapp) return;

        const text = encodeURIComponent(`${this.contentData.title}\n\n${this.contentData.shareUrl}`);
        const shareUrl = `https://wa.me/?text=${text}`;
        this.openShareWindow(shareUrl, 'WhatsApp');
        this.incrementShareCount();
    }

    async copyLink() {
        if (!this.options.platforms.copyLink) return;

        const btn = document.getElementById(this.options.copyLinkButtonId);

        try {
            if (navigator.clipboard && window.isSecureContext) {
                await navigator.clipboard.writeText(this.contentData.shareUrl);
            } else {
                this.fallbackCopyLink();
                return;
            }

            if (btn) {
                btn.classList.add('copied');
                setTimeout(() => {
                    btn.classList.remove('copied');
                }, 2000);
            }

            this.showNotification('Ссылка скопирована в буфер обмена!', 'success');
            this.incrementShareCount();

        } catch (err) {
            console.error('Ошибка при копировании:', err);
            this.fallbackCopyLink();
        }
    }

    fallbackCopyLink() {
        const btn = document.getElementById(this.options.copyLinkButtonId);
        const textArea = document.createElement('textarea');
        textArea.value = this.contentData.shareUrl;
        textArea.style.position = 'fixed';
        textArea.style.left = '-999999px';
        textArea.style.top = '-999999px';
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();

        try {
            const successful = document.execCommand('copy');
            if (successful) {
                if (btn) {
                    btn.classList.add('copied');
                    setTimeout(() => {
                        btn.classList.remove('copied');
                    }, 2000);
                }

                this.showNotification('Ссылка скопирована в буфер обмена!', 'success');
                this.incrementShareCount();
            } else {
                throw new Error('Команда копирования не поддерживается');
            }
        } catch (err) {
            console.error('Ошибка при копировании:', err);
            this.showNotification('Не удалось скопировать ссылку', 'error');
        }

        document.body.removeChild(textArea);
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

    async incrementShareCount() {
        try {
            const endpoint = `${this.options.apiEndpoint}/${this.options.contentType}/${this.contentData.id}/share`;
            
            const headers = {
                'Content-Type': 'application/json',
                ...this.getCSRFHeaders()
            };

            await fetch(endpoint, {
                method: 'POST',
                headers: headers
            });

            this.updateShareCountDisplay();
        } catch (error) {
            console.log('SocialShareManager: Не удалось обновить счетчик репостов');
        }
    }

    updateShareCountDisplay() {
        const shareCountElement = document.querySelector(this.options.shareCountSelector);
        if (shareCountElement) {
            const currentCount = parseInt(shareCountElement.textContent.trim()) || 0;
            shareCountElement.innerHTML = `<i class="fas fa-share me-1"></i>${currentCount + 1}`;
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

    showNotification(message, type = 'info') {
        const existingNotifications = document.querySelectorAll('.share-notification');
        existingNotifications.forEach(notification => notification.remove());

        const notification = document.createElement('div');
        notification.className = `share-notification alert alert-${type} alert-dismissible fade show`;
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

    setContentData(contentData) {
        this.contentData = contentData;
    }

    updateContentData(updates) {
        this.contentData = { ...this.contentData, ...updates };
    }

    enablePlatform(platform) {
        this.options.platforms[platform] = true;
    }

    disablePlatform(platform) {
        this.options.platforms[platform] = false;
    }

    getShareUrl(platform) {
        const encodedUrl = encodeURIComponent(this.contentData.shareUrl);
        const encodedTitle = encodeURIComponent(this.contentData.title);

        switch (platform) {
            case 'facebook':
                return `https://www.facebook.com/sharer/sharer.php?u=${encodedUrl}`;
            case 'twitter':
                const via = this.options.twitterHandle ? `&via=${this.options.twitterHandle}` : '';
                return `https://twitter.com/intent/tweet?url=${encodedUrl}&text=${encodedTitle}${via}`;
            case 'telegram':
                return `https://t.me/share/url?url=${encodedUrl}&text=${encodedTitle}%0A%0A`;
            case 'whatsapp':
                return `https://wa.me/?text=${encodedTitle}%0A%0A${encodedUrl}`;
            default:
                return null;
        }
    }
}