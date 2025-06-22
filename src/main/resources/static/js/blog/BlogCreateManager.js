class BlogCreateManager extends BaseContentCreateManager {
    constructor() {
        super({
            formId: 'blogForm',
            contentType: 'blog',
            imageCropOptions: {
                inputId: 'mainImageInput',
                previewId: 'imagePreview',
                previewContainerSelector: '.image-preview-container',
                croppedPreviewSelector: '.cropped-preview',
                cropButtonId: 'cropButton',
                cancelCropId: 'cancelCrop',
                changeCropId: 'changeCrop',
                croppedImageId: 'croppedImage',
                aspectRatio: 3,
                outputWidth: 1200,
                outputHeight: 400
            },
            tinyMCEOptions: {
                editorSelector: '.tinymce-editor',
                height: 500,
                imageUploadUrl: '/api/files/tinymce/image',
                videoUploadUrl: '/api/files/tinymce/video'
            },
            languageTabsOptions: {
                tabsContainerSelector: '#languageTabs',
                tabLinkSelector: '.nav-link[data-lang]'
            },
            validationOptions: {
                titleFieldSelector: 'input[name*="blogTranslations"][name*="title"]',
                submitButtonId: 'submitButton'
            }
        });
    }

    validateAdditionalFields() {
        return true;
    }

    onFormSubmit(event) {
        console.log('Submitting blog form...');
        this.prepareBlogData();

        return super.onFormSubmit(event);
    }

    prepareBlogData() {
        console.log('Preparing blog data for submission');

        this.syncTinyMCEWithForm();
        this.optimizeContent();
    }

    syncTinyMCEWithForm() {
        const languages = this.languageTabsManager.getAvailableLanguages();

        languages.forEach(lang => {
            const editor = this.tinyMCEManager.getAllInstances()[lang];
            if (editor) {
                const content = editor.getContent();
                const textarea = document.querySelector(`textarea[data-lang="${lang}"]`);
                if (textarea) {
                    textarea.value = content;
                }
            }
        });
    }

    optimizeContent() {
        const languages = this.languageTabsManager.getAvailableLanguages();

        languages.forEach(lang => {
            const editor = this.tinyMCEManager.getAllInstances()[lang];
            if (editor) {
                let content = editor.getContent();
                content = content.replace(/<img /g, '<img loading="lazy" ');
                content = content.replace(/<a href="([^"]*)"(?![^>]*title=)/g, '<a href="$1" title="Перейти по ссылке"');

                editor.setContent(content);
            }
        });
    }

    onImageCropped(blob, dataURL) {
        super.onImageCropped(blob, dataURL);

        console.log('Blog main image cropped successfully');
        this.updateSEOPreview(dataURL);
    }

    updateSEOPreview(imageURL) {
        const seoPreview = document.querySelector('.seo-preview img');
        if (seoPreview) {
            seoPreview.src = imageURL;
        }
    }

    onLanguageSwitch(newLang, prevLang) {
        super.onLanguageSwitch(newLang, prevLang);

        console.log(`Blog: Language switched from ${prevLang} to ${newLang}`);
        this.updateLanguageSpecificUI(newLang);
    }

    updateLanguageSpecificUI(lang) {
        const langSpecificElements = document.querySelectorAll('[data-lang-specific]');
        langSpecificElements.forEach(element => {
            const supportedLangs = element.getAttribute('data-lang-specific').split(',');
            if (supportedLangs.includes(lang)) {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        });
    }

    onValidationChange(isValid) {
        super.onValidationChange(isValid);
        this.updatePublishButton(isValid);
        this.updateProgressIndicator(isValid);
    }

    updatePublishButton(isValid) {
        const publishButton = document.getElementById('submitButton');
        if (publishButton) {
            if (isValid) {
                publishButton.classList.remove('btn-secondary');
                publishButton.classList.add('btn-primary');
                publishButton.innerHTML = '<i class="fas fa-save me-2"></i>Создать блог';
            } else {
                publishButton.classList.remove('btn-primary');
                publishButton.classList.add('btn-secondary');
                publishButton.innerHTML = '<i class="fas fa-exclamation-triangle me-2"></i>Заполните форму';
            }
        }
    }

    updateProgressIndicator(isValid) {
        const progressBar = document.querySelector('.form-progress-bar');
        if (progressBar) {
            const statuses = this.validationManager.getAllLanguageStatuses();
            const totalLanguages = Object.keys(statuses).length;
            const completedLanguages = Object.values(statuses).filter(status => status === 'complete').length;
            const progress = (completedLanguages / totalLanguages) * 100;

            progressBar.style.width = `${progress}%`;
            progressBar.setAttribute('aria-valuenow', progress);
        }
    }

    saveDraft() {
        if (!this.validationManager.getValidationState()) {
            this.showAlert('Нечего сохранять в черновики', 'info');
            return;
        }

        const formData = new FormData(document.getElementById(this.options.formId));
        if (this.imageCropManager.hasCroppedImage()) {
            formData.set('mpMainImage', this.imageCropManager.getCroppedImageData(), 'main-image.jpg');
        }
        formData.set('status', 'DRAFT');

        console.log('Saving blog draft...');
        this.showAlert('Черновик сохранен', 'success');
    }

    getCurrentLanguage() {
        return this.languageTabsManager.getCurrentLanguage();
    }

    switchToLanguage(lang) {
        return this.languageTabsManager.switchToLanguage(lang);
    }

    getContentForLanguage(lang) {
        return {
            title: document.querySelector(`input[data-lang="${lang}"]`)?.value || '',
            content: this.tinyMCEManager.getContent(lang)
        };
    }

    setContentForLanguage(lang, title, content) {
        const titleField = document.querySelector(`input[data-lang="${lang}"]`);
        if (titleField) {
            titleField.value = title;
        }

        this.tinyMCEManager.setContent(lang, content);
        this.validationManager.forceUpdate();
    }

    restoreFromAutoSave() {
        const autoSaveKey = `blog_autosave_${Date.now()}`;
        const savedData = localStorage.getItem(autoSaveKey);

        if (savedData) {
            try {
                const data = JSON.parse(savedData);

                // Восстанавливаем данные по языкам
                Object.keys(data.translations || {}).forEach(lang => {
                    const translation = data.translations[lang];
                    this.setContentForLanguage(lang, translation.title, translation.content);
                });

                this.showAlert('Данные восстановлены из автосохранения', 'info');
            } catch (error) {
                console.error('Failed to restore from autosave:', error);
            }
        }
    }

    destroy() {
        super.destroy();

        console.log('Destroying BlogCreateManager');
    }
}

let blogCreateManager;

function initBlogCreate() {
    blogCreateManager = new BlogCreateManager();
    window.blogCreateManager = blogCreateManager;

    const saveDraftButton = document.getElementById('saveDraftButton');
    if (saveDraftButton) {
        saveDraftButton.addEventListener('click', () => {
            blogCreateManager.saveDraft();
        });
    }

    if (window.ENABLE_AUTOSAVE) {
        setInterval(() => {
            if (blogCreateManager.validationManager.getValidationState()) {
                const currentLang = blogCreateManager.getCurrentLanguage();
                const data = {
                    translations: {},
                    timestamp: Date.now()
                };

                blogCreateManager.languageTabsManager.getAvailableLanguages().forEach(lang => {
                    data.translations[lang] = blogCreateManager.getContentForLanguage(lang);
                });

                localStorage.setItem(`blog_autosave_current`, JSON.stringify(data));
            }
        }, 30000);
    }

    console.log('BlogCreateManager initialized successfully');
}

document.addEventListener('DOMContentLoaded', () => {
    initBlogCreate();
});

window.addEventListener('beforeunload', () => {
    if (blogCreateManager) {
        blogCreateManager.destroy();
    }
});