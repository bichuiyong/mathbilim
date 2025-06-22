class EventViewManager extends BaseContentViewManager {
    constructor() {
        super({
            contentType: 'event',
            socialShareOptions: {
                contentType: 'event',
                apiEndpoint: '/api',
                twitterHandle: 'MathBilimKG'
            },
            authorManagerOptions: {
                authorContainerSelector: '.event-author-compact',
                apiEndpoint: '/api/users'
            },
            contentViewOptions: {
                contentSelector: '.event-content',
                enableImageModal: true,
                enableSmoothScrolling: true,
                enableProgressBar: true,
                enableBackToTop: true
            }
        });

        this.eventData = window.eventData || {};
    }

    setupSpecificFeatures() {
    }

    // ==========================================
    // СОЦИАЛЬНЫЙ ШАРИНГ
    // ==========================================

    shareEvent() {
        // Обертка для совместимости
        if (this.socialShareManager) {
            this.socialShareManager.shareToFacebook();
        }
    }

    updateContentData(updates) {
        super.updateContentData(updates);
        this.eventData = { ...this.eventData, ...updates };
    }

    // ==========================================
    // ПУБЛИЧНЫЕ МЕТОДЫ
    // ==========================================

    getEventData() {
        return this.eventData;
    }

    isEventStarted() {
        if (!this.eventData.startDate) return false;
        const startDate = new Date(this.eventData.startDate);
        return new Date() >= startDate;
    }

    getTimeUntilStart() {
        if (!this.eventData.startDate) return null;
        const startDate = new Date(this.eventData.startDate);
        const now = new Date();
        return startDate - now;
    }

    extractContentDataFromDOM() {
        const baseData = super.extractContentDataFromDOM();

        const typeElement = document.querySelector('.event-type-badge');
        const locationElement = document.querySelector('.location-info');

        return {
            ...baseData,
            type: 'event',
            eventType: typeElement ? typeElement.textContent.trim() : null,
            isOffline: locationElement ? locationElement.classList.contains('offline') : null
        };
    }

    destroy() {
        super.destroy();
        this.clearCountdown();
        console.log('EventViewManager destroyed');
    }
}

// ==========================================
// ГЛОБАЛЬНЫЕ ФУНКЦИИ
// ==========================================

let eventViewManager;

function shareToFacebook() {
    eventViewManager?.getSocialShareManager()?.shareToFacebook();
}

function shareToTwitter() {
    eventViewManager?.getSocialShareManager()?.shareToTwitter();
}

function shareToTelegram() {
    eventViewManager?.getSocialShareManager()?.shareToTelegram();
}

function shareToWhatsApp() {
    eventViewManager?.getSocialShareManager()?.shareToWhatsApp();
}

function copyLink() {
    eventViewManager?.getSocialShareManager()?.copyLink();
}

function shareEvent() {
    eventViewManager?.shareEvent();
}

function initEventView() {
    eventViewManager = new EventViewManager();

    window.eventViewManager = eventViewManager;

    console.log('EventViewManager initialized successfully');
}

// ==========================================
// ИНИЦИАЛИЗАЦИЯ
// ==========================================

document.addEventListener('DOMContentLoaded', () => {
    initEventView();
});

window.addEventListener('beforeunload', () => {
    if (eventViewManager) {
        eventViewManager.destroy();
    }
});