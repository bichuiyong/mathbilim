class BlogCreateManager {
    constructor() {
        this.cropper = null;
        this.croppedImageData = null;
        this.currentLang = 'ru';
        this.tinymceInstances = {};

        this.init();
    }

    init() {
        this.initImageCrop();
        this.initTinyMCE();
        this.initLanguageTabs();
        this.initFormValidation();
        this.updateTranslationIndicators();
    }

    // ==========================================
    // КРОП ИЗОБРАЖЕНИЯ
    // ==========================================

    initImageCrop() {
        const imageInput = document.getElementById('mainImageInput');
        const imagePreview = document.getElementById('imagePreview');
        const previewContainer = document.querySelector('.image-preview-container');
        const croppedPreview = document.querySelector('.cropped-preview');
        const cropButton = document.getElementById('cropButton');
        const cancelCrop = document.getElementById('cancelCrop');
        const changeCrop = document.getElementById('changeCrop');

        // Обработка загрузки изображения
        imageInput.addEventListener('change', (e) => {
            const file = e.target.files[0];
            if (file) {
                this.loadImageForCrop(file, imagePreview, previewContainer);
            }
        });

        // Применить кроп
        cropButton.addEventListener('click', () => {
            this.applyCrop(previewContainer, croppedPreview);
        });

        // Отменить кроп
        cancelCrop.addEventListener('click', () => {
            this.cancelCrop(previewContainer, croppedPreview);
        });

        // Изменить кроп
        changeCrop.addEventListener('click', () => {
            this.changeCrop(previewContainer, croppedPreview);
        });
    }

    loadImageForCrop(file, imagePreview, previewContainer) {
        // Валидация размера файла (5MB)
        if (file.size > 5 * 1024 * 1024) {
            this.showAlert('Размер изображения не должен превышать 5MB', 'danger');
            return;
        }

        // Валидация типа файла
        if (!file.type.startsWith('image/')) {
            this.showAlert('Пожалуйста, выберите изображение', 'danger');
            return;
        }

        const reader = new FileReader();
        reader.onload = (e) => {
            imagePreview.src = e.target.result;
            previewContainer.style.display = 'block';

            // Уничтожаем предыдущий cropper
            if (this.cropper) {
                this.cropper.destroy();
            }

            // Инициализируем cropper с соотношением 3:1
            this.cropper = new Cropper(imagePreview, {
                aspectRatio: 3,
                viewMode: 2,
                dragMode: 'move',
                autoCropArea: 1,
                responsive: true,
                background: false,
                guides: true,
                center: true,
                highlight: false,
                cropBoxMovable: true,
                cropBoxResizable: false,
                toggleDragModeOnDblclick: false
            });
        };
        reader.readAsDataURL(file);
    }

    applyCrop(previewContainer, croppedPreview) {
        if (!this.cropper) return;

        // Получаем кропнутое изображение
        const canvas = this.cropper.getCroppedCanvas({
            width: 1200,
            height: 400,
            imageSmoothingQuality: 'high'
        });

        canvas.toBlob((blob) => {
            this.croppedImageData = blob;

            // Показываем превью
            const croppedImage = document.getElementById('croppedImage');
            croppedImage.src = canvas.toDataURL();

            previewContainer.style.display = 'none';
            croppedPreview.style.display = 'block';

            this.showAlert('Изображение успешно обрезано', 'success');
        }, 'image/jpeg', 0.9);
    }

    cancelCrop(previewContainer, croppedPreview) {
        if (this.cropper) {
            this.cropper.destroy();
            this.cropper = null;
        }
        previewContainer.style.display = 'none';
        document.getElementById('mainImageInput').value = '';
    }

    changeCrop(previewContainer, croppedPreview) {
        croppedPreview.style.display = 'none';
        previewContainer.style.display = 'block';
        this.croppedImageData = null;
    }

    // ==========================================
    // TINYMCE РЕДАКТОР
    // ==========================================

    initTinyMCE() {
        const editors = document.querySelectorAll('.tinymce-editor');

        editors.forEach(editor => {
            const lang = editor.getAttribute('data-lang');

            tinymce.init({
                target: editor,
                height: 500,
                menubar: true,
                plugins: [
                    'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
                    'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                    'insertdatetime', 'media', 'table', 'help', 'wordcount', 'emoticons'
                ],
                toolbar: 'undo redo | blocks | bold italic forecolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | image media link table | code preview fullscreen | help',
                content_style: `
                    body { 
                        font-family: Inter, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; 
                        font-size: 16px; 
                        line-height: 1.6; 
                        color: #1e293b;
                    }
                    img { max-width: 100%; height: auto; }
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid #e2e8f0; padding: 8px 12px; }
                `,

                // Настройки загрузки изображений
                images_upload_url: '/api/files/tinymce/image',
                images_upload_base_path: '',
                images_upload_credentials: true,
                images_upload_handler: this.handleImageUpload.bind(this),

                // Настройки загрузки файлов
                file_picker_callback: this.handleFilePicker.bind(this),
                file_picker_types: 'file image media',

                // Автосохранение в localStorage не используем (согласно ограничениям)

                // Валидация контента
                setup: (editor) => {
                    this.tinymceInstances[lang] = editor;

                    editor.on('change', () => {
                        this.updateTranslationIndicators();
                    });

                    editor.on('keyup', () => {
                        this.updateTranslationIndicators();
                    });
                }
            });
        });
    }

    handleImageUpload(blobInfo, progress) {
        return new Promise((resolve, reject) => {
            const formData = new FormData();
            formData.append('file', blobInfo.blob(), blobInfo.filename());

            // Добавляем CSRF токен
            const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

            const xhr = new XMLHttpRequest();
            xhr.open('POST', '/api/files/tinymce/image');

            if (csrfToken && csrfHeader) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }

            xhr.upload.addEventListener('progress', (e) => {
                progress(e.loaded / e.total * 100);
            });

            xhr.addEventListener('load', () => {
                if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    resolve(response.location);
                } else {
                    const error = JSON.parse(xhr.responseText);
                    reject(error.error || 'Ошибка загрузки изображения');
                }
            });

            xhr.addEventListener('error', () => {
                reject('Ошибка сети при загрузке изображения');
            });

            xhr.send(formData);
        });
    }

    handleFilePicker(callback, value, meta) {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');

        if (meta.filetype === 'image') {
            input.setAttribute('accept', 'image/*');
        } else if (meta.filetype === 'media') {
            input.setAttribute('accept', 'video/*,audio/*');
        } else {
            input.setAttribute('accept', '*/*');
        }

        input.addEventListener('change', (e) => {
            const file = e.target.files[0];
            if (!file) return;

            const formData = new FormData();
            formData.append('file', file);

            let uploadUrl = '/api/files/tinymce/document';
            if (meta.filetype === 'image') {
                uploadUrl = '/api/files/tinymce/image';
            } else if (meta.filetype === 'media') {
                uploadUrl = '/api/files/tinymce/video';
            }

            // Добавляем CSRF токен
            const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

            fetch(uploadUrl, {
                method: 'POST',
                headers: csrfToken && csrfHeader ? { [csrfHeader]: csrfToken } : {},
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    if (data.location) {
                        callback(data.location, { title: file.name });
                    } else {
                        throw new Error(data.error || 'Ошибка загрузки файла');
                    }
                })
                .catch(error => {
                    this.showAlert(error.message, 'danger');
                });
        });

        input.click();
    }

    // ==========================================
    // ЯЗЫКОВЫЕ ТАБЫ
    // ==========================================

    initLanguageTabs() {
        const languageTabs = document.querySelectorAll('#languageTabs .nav-link');

        languageTabs.forEach(tab => {
            tab.addEventListener('click', (e) => {
                const lang = e.target.getAttribute('data-lang');
                if (lang) {
                    this.currentLang = lang;

                    // Обновляем TinyMCE при переключении табов
                    setTimeout(() => {
                        if (this.tinymceInstances[lang]) {
                            this.tinymceInstances[lang].focus();
                        }
                    }, 100);
                }
            });
        });
    }

    // ==========================================
    // ВАЛИДАЦИЯ И ИНДИКАТОРЫ
    // ==========================================

    updateTranslationIndicators() {
        const languages = ['ru', 'ky', 'en'];
        let hasValidTranslation = false;

        languages.forEach(lang => {
            const titleField = document.querySelector(`input[data-lang="${lang}"]`);
            const indicator = document.getElementById(`indicator-${lang}`);

            if (!titleField || !indicator) return;

            const title = titleField.value.trim();
            let content = '';

            // Получаем контент из TinyMCE
            if (this.tinymceInstances[lang]) {
                content = this.tinymceInstances[lang].getContent({format: 'text'}).trim();
            }

            const icon = indicator.querySelector('i');

            if (title && content) {
                // Полностью заполнено
                icon.className = 'fas fa-circle text-success';
                icon.title = 'Заполнено';
                hasValidTranslation = true;
            } else if (title || content) {
                // Частично заполнено
                icon.className = 'fas fa-circle text-warning';
                icon.title = 'Частично заполнено';
            } else {
                // Не заполнено
                icon.className = 'fas fa-circle text-danger';
                icon.title = 'Не заполнено';
            }
        });

        // Обновляем состояние кнопки отправки
        const submitButton = document.getElementById('submitButton');
        submitButton.disabled = !hasValidTranslation;
    }

    initFormValidation() {
        // Добавляем обработчики на поля заголовков
        const titleFields = document.querySelectorAll('.translation-field[data-lang]');
        titleFields.forEach(field => {
            field.addEventListener('input', () => {
                this.updateTranslationIndicators();
            });
        });

        // Валидация формы перед отправкой
        const form = document.getElementById('blogForm');
        form.addEventListener('submit', (e) => {
            if (!this.validateForm()) {
                e.preventDefault();
            } else {
                // Если есть кропнутое изображение, заменяем файл
                if (this.croppedImageData) {
                    this.replaceImageFile();
                }
            }
        });
    }

    validateForm() {
        const languages = ['ru', 'ky', 'en'];
        let hasValidTranslation = false;

        for (const lang of languages) {
            const titleField = document.querySelector(`input[data-lang="${lang}"]`);
            const title = titleField ? titleField.value.trim() : '';

            let content = '';
            if (this.tinymceInstances[lang]) {
                content = this.tinymceInstances[lang].getContent({format: 'text'}).trim();
            }

            if (title && content) {
                hasValidTranslation = true;
                break;
            }
        }

        if (!hasValidTranslation) {
            this.showAlert('Заполните хотя бы один полный перевод (заголовок и содержание)', 'warning');
            return false;
        }

        return true;
    }

    replaceImageFile() {
        if (!this.croppedImageData) return;

        const input = document.getElementById('mainImageInput');
        const dataTransfer = new DataTransfer();

        // Создаем новый файл из blob
        const file = new File([this.croppedImageData], 'cropped-image.jpg', {
            type: 'image/jpeg'
        });

        dataTransfer.items.add(file);
        input.files = dataTransfer.files;
    }

    // ==========================================
    // УТИЛИТЫ
    // ==========================================

    showAlert(message, type = 'info') {
        // Создаем alert
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        // Вставляем в начало формы
        const form = document.getElementById('blogForm');
        form.insertBefore(alertDiv, form.firstChild);

        // Автоматически скрываем через 5 секунд
        setTimeout(() => {
            if (alertDiv.parentNode) {
                alertDiv.remove();
            }
        }, 5000);
    }
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    new BlogCreateManager();
});