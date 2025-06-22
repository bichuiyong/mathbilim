class TranslationValidationManager {
    constructor(options = {}) {
        this.options = {
            titleFieldSelector: 'input[data-lang]',
            indicatorSelector: '[id^="indicator-"]',
            submitButtonId: 'submitButton',
            contentProvider: null,
            onValidationChange: null,
            ...options
        };

        this.availableLanguages = [];
        this.lastValidationState = false;

        this.init();
    }

    init() {
        this.detectAvailableLanguages();
        this.bindEvents();
        this.updateTranslationIndicators();
    }

    detectAvailableLanguages() {
        const titleFields = document.querySelectorAll(this.options.titleFieldSelector);
        this.availableLanguages = Array.from(titleFields).map(field => field.getAttribute('data-lang'));
    }

    bindEvents() {
        const titleFields = document.querySelectorAll(this.options.titleFieldSelector);
        titleFields.forEach(field => {
            field.addEventListener('input', () => {
                this.updateTranslationIndicators();
            });
        });
    }

    updateTranslationIndicators() {
        let hasValidTranslation = false;

        this.availableLanguages.forEach(lang => {
            const titleField = document.querySelector(`${this.options.titleFieldSelector}[data-lang="${lang}"]`);
            const indicator = document.getElementById(`indicator-${lang}`);

            if (!titleField || !indicator) return;

            const title = titleField.value.trim();
            let content = '';

            if (this.options.contentProvider) {
                content = this.options.contentProvider(lang);
            }

            const icon = indicator.querySelector('i');
            if (!icon) return;

            if (title && content) {
                icon.className = 'fas fa-circle text-success';
                icon.title = 'Заполнено';
                hasValidTranslation = true;
            } else if (title || content) {
                icon.className = 'fas fa-circle text-warning';
                icon.title = 'Частично заполнено';
            } else {
                icon.className = 'fas fa-circle text-danger';
                icon.title = 'Не заполнено';
            }
        });

        this.updateSubmitButton(hasValidTranslation);

        if (this.lastValidationState !== hasValidTranslation) {
            this.lastValidationState = hasValidTranslation;
            if (this.options.onValidationChange) {
                this.options.onValidationChange(hasValidTranslation);
            }
        }
    }

    updateSubmitButton(isValid) {
        const submitButton = document.getElementById(this.options.submitButtonId);
        if (submitButton) {
            submitButton.disabled = !isValid;
        }
    }

    validateForm(showAlert = true) {
        let hasValidTranslation = false;

        for (const lang of this.availableLanguages) {
            const titleField = document.querySelector(`${this.options.titleFieldSelector}[data-lang="${lang}"]`);
            const title = titleField ? titleField.value.trim() : '';

            let content = '';
            if (this.options.contentProvider) {
                content = this.options.contentProvider(lang);
            }

            if (title && content) {
                hasValidTranslation = true;
                break;
            }
        }

        if (!hasValidTranslation && showAlert) {
            this.showValidationAlert('Заполните хотя бы один полный перевод (заголовок и содержание)');
        }

        return hasValidTranslation;
    }

    showValidationAlert(message) {
        if (this.options.onValidationError) {
            this.options.onValidationError(message);
        } else {
            console.warn('Validation error:', message);
        }
    }

    setContentProvider(provider) {
        this.options.contentProvider = provider;
        this.updateTranslationIndicators();
    }

    forceUpdate() {
        this.updateTranslationIndicators();
    }

    getValidationState() {
        return this.lastValidationState;
    }

    getLanguageStatus(lang) {
        const titleField = document.querySelector(`${this.options.titleFieldSelector}[data-lang="${lang}"]`);
        const title = titleField ? titleField.value.trim() : '';

        let content = '';
        if (this.options.contentProvider) {
            content = this.options.contentProvider(lang);
        }

        if (title && content) return 'complete';
        if (title || content) return 'partial';
        return 'empty';
    }

    getAllLanguageStatuses() {
        const statuses = {};
        this.availableLanguages.forEach(lang => {
            statuses[lang] = this.getLanguageStatus(lang);
        });
        return statuses;
    }
}