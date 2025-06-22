class LanguageTabsManager {
    constructor(options = {}) {
        this.options = {
            tabsContainerSelector: '#languageTabs',
            tabLinkSelector: '.nav-link[data-lang]',
            onLanguageSwitch: null,
            ...options
        };

        this.currentLang = null;
        this.availableLanguages = [];

        this.init();
    }

    init() {
        this.detectAvailableLanguages();
        this.setInitialActiveLang();
        this.bindEvents();
    }

    detectAvailableLanguages() {
        const tabs = document.querySelectorAll(this.options.tabLinkSelector);
        this.availableLanguages = Array.from(tabs).map(tab => tab.getAttribute('data-lang'));
    }

    setInitialActiveLang() {
        const activeTab = document.querySelector(`${this.options.tabsContainerSelector} .nav-link.active`);
        this.currentLang = activeTab ? activeTab.getAttribute('data-lang') :
            (this.availableLanguages.length > 0 ? this.availableLanguages[0] : 'ru');
    }

    bindEvents() {
        const languageTabs = document.querySelectorAll(this.options.tabLinkSelector);

        languageTabs.forEach(tab => {
            tab.addEventListener('shown.bs.tab', (e) => {
                const lang = e.target.getAttribute('data-lang');
                if (lang && lang !== this.currentLang) {
                    const previousLang = this.currentLang;
                    this.currentLang = lang;

                    if (this.options.onLanguageSwitch) {
                        this.options.onLanguageSwitch(lang, previousLang);
                    }
                }
            });
        });
    }

    getCurrentLanguage() {
        return this.currentLang;
    }

    getAvailableLanguages() {
        return [...this.availableLanguages];
    }

    switchToLanguage(lang) {
        if (!this.availableLanguages.includes(lang)) {
            console.warn(`Language '${lang}' is not available`);
            return false;
        }

        const tab = document.querySelector(`${this.options.tabLinkSelector}[data-lang="${lang}"]`);
        if (tab) {
            // Используем Bootstrap API для переключения вкладки
            const tabInstance = new bootstrap.Tab(tab);
            tabInstance.show();
            return true;
        }

        return false;
    }

    setLanguageSwitchCallback(callback) {
        this.options.onLanguageSwitch = callback;
    }
}