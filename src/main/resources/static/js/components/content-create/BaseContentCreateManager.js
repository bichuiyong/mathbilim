class BaseContentCreateManager {
    constructor(options = {}) {
        this.options = {
            formId: 'contentForm',
            contentType: 'content',

            imageCropOptions: {},
            tinyMCEOptions: {},
            languageTabsOptions: {},
            validationOptions: {},
            ...options
        };

        // Компоненты
        this.imageCropManager = null;
        this.tinyMCEManager = null;
        this.languageTabsManager = null;
        this.validationManager = null;

        this.init();
    }

    init() {
        this.initComponents();
        this.bindFormSubmit();
        this.setupComponentIntegration();
    }

    resizeImage(file, maxSize = 2000) {
        return new Promise((resolve) => {
            const img = new Image();
            const reader = new FileReader();
            reader.onload = (e) => {
                img.src = e.target.result;
            };
            img.onload = () => {
                let canvas = document.createElement("canvas");
                let ctx = canvas.getContext("2d");

                let ratio = Math.min(maxSize / img.width, maxSize / img.height, 1);
                canvas.width = img.width * ratio;
                canvas.height = img.height * ratio;
                ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

                canvas.toBlob((blob) => {
                    resolve(new File([blob], file.name, { type: blob.type }));
                }, "image/jpeg", 0.9);
            };
            reader.readAsDataURL(file);
        });
    }


    initComponents() {
        this.imageCropManager = new ImageCropManager({
            onCropComplete: (blob, dataURL) => this.onImageCropped(blob, dataURL),
            onError: (message) => this.showAlert(message, 'danger'),
            beforeImageLoad: async (file) => {
                // новый хук – обработка файла перед загрузкой в cropper
                if (file.size > 3 * 1024 * 1024) {
                    console.warn("Файл больше 3MB, уменьшаем перед кропом...");
                    file = await this.resizeImage(file, 2000);
                }
                return file;
            },
            ...this.options.imageCropOptions
        });

        this.tinyMCEManager = new TinyMCEManager({
            onChange: (lang, editor) => this.onEditorChange(lang, editor),
            onKeyUp: (lang, editor) => this.onEditorKeyUp(lang, editor),
            onInit: (lang, editor) => this.onEditorInit(lang, editor),
            onError: (message) => this.showAlert(message, 'danger'),
            ...this.options.tinyMCEOptions
        });

        this.languageTabsManager = new LanguageTabsManager({
            onLanguageSwitch: (newLang, prevLang) => this.onLanguageSwitch(newLang, prevLang),
            ...this.options.languageTabsOptions
        });

        this.validationManager = new TranslationValidationManager({
            contentProvider: (lang) => this.tinyMCEManager.getTextContent(lang),
            onValidationChange: (isValid) => this.onValidationChange(isValid),
            onValidationError: (message) => this.showAlert(message, 'warning'),
            ...this.options.validationOptions
        });
    }

    setupComponentIntegration() {
        this.validationManager.setContentProvider((lang) => this.tinyMCEManager.getTextContent(lang));

        const currentLang = this.languageTabsManager.getCurrentLanguage();
        setTimeout(() => {
            if (this.tinyMCEManager.isEditorReady(currentLang)) {
                this.tinyMCEManager.focusEditor(currentLang);
            }
        }, 500);
    }

    bindFormSubmit() {
        const form = document.getElementById(this.options.formId);
        if (form) {
            form.addEventListener('submit', (e) => {
                if (!this.onFormSubmit(e)) {
                    e.preventDefault();
                }
            });
        }
    }

    onImageCropped(blob, dataURL) {
        console.log(`${this.options.contentType}: Image cropped successfully`);
        this.showAlert('Изображение успешно обрезано', 'success');
    }

    onEditorChange(lang, editor) {
        this.validationManager.forceUpdate();
    }

    onEditorKeyUp(lang, editor) {
        this.validationManager.forceUpdate();
    }

    onEditorInit(lang, editor) {
        const currentLang = this.languageTabsManager.getCurrentLanguage();
        if (lang === currentLang) {
            setTimeout(() => editor.focus(), 100);
        }
    }

    onLanguageSwitch(newLang, prevLang) {
        console.log(`Switched from ${prevLang} to ${newLang}`);
        this.tinyMCEManager.focusEditor(newLang);
    }

    onValidationChange(isValid) {
        console.log(`Validation state changed: ${isValid}`);
    }

    onFormSubmit(event) {
        if (!this.validationManager.validateForm()) {
            return false;
        }

        if (!this.validateAdditionalFields()) {
            return false;
        }

        console.log(`Submitting ${this.options.contentType} form`);
        return true;
    }

    validateAdditionalFields() {
        return true;
    }

    showAlert(message, type = 'info') {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        const form = document.getElementById(this.options.formId);
        if (form) {
            form.insertBefore(alertDiv, form.firstChild);

            setTimeout(() => {
                if (alertDiv.parentNode) {
                    alertDiv.remove();
                }
            }, 5000);
        }
    }

    getImageCropManager() {
        return this.imageCropManager;
    }

    getTinyMCEManager() {
        return this.tinyMCEManager;
    }

    getLanguageTabsManager() {
        return this.languageTabsManager;
    }

    getValidationManager() {
        return this.validationManager;
    }

    getCurrentLanguage() {
        return this.languageTabsManager.getCurrentLanguage();
    }

    getValidationState() {
        return this.validationManager.getValidationState();
    }

    destroy() {
        if (this.imageCropManager) {
            this.imageCropManager.destroy();
        }
        if (this.tinyMCEManager) {
            this.tinyMCEManager.destroy();
        }
    }
}