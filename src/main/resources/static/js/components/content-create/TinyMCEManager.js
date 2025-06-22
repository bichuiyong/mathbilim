class TinyMCEManager {
    constructor(options = {}) {
        this.options = {
            editorSelector: '.tinymce-editor',
            height: 500,
            imageUploadUrl: '/api/files/tinymce/image',
            documentUploadUrl: '/api/files/tinymce/document',
            videoUploadUrl: '/api/files/tinymce/video',
            onChange: null,
            onKeyUp: null,
            onInit: null,
            ...options
        };

        this.instances = {};
        this.availableLanguages = [];

        this.init();
    }

    init() {
        this.initInstances();
    }

    initInstances() {
        const editors = document.querySelectorAll(this.options.editorSelector);

        editors.forEach(editor => {
            const lang = editor.getAttribute('data-lang');
            if (lang) {
                this.availableLanguages.push(lang);
                this.initSingleEditor(editor, lang);
            }
        });
    }

    initSingleEditor(editor, lang) {
        tinymce.init({
            target: editor,
            height: this.options.height,
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

            images_upload_url: this.options.imageUploadUrl,
            images_upload_base_path: '',
            images_upload_credentials: true,
            images_upload_handler: this.handleImageUpload.bind(this),

            file_picker_callback: this.handleFilePicker.bind(this),
            file_picker_types: 'file image media',

            setup: (editorInstance) => {
                this.instances[lang] = editorInstance;

                editorInstance.on('change', () => {
                    if (this.options.onChange) {
                        this.options.onChange(lang, editorInstance);
                    }
                });

                editorInstance.on('keyup', () => {
                    if (this.options.onKeyUp) {
                        this.options.onKeyUp(lang, editorInstance);
                    }
                });

                editorInstance.on('init', () => {
                    if (this.options.onInit) {
                        this.options.onInit(lang, editorInstance);
                    }
                });
            }
        });
    }

    handleImageUpload(blobInfo, progress) {
        return new Promise((resolve, reject) => {
            const formData = new FormData();
            formData.append('file', blobInfo.blob(), blobInfo.filename());

            const xhr = new XMLHttpRequest();
            xhr.open('POST', this.options.imageUploadUrl);

            const csrfHeaders = this.getCSRFHeaders();
            Object.keys(csrfHeaders).forEach(header => {
                xhr.setRequestHeader(header, csrfHeaders[header]);
            });

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

            let uploadUrl = this.options.documentUploadUrl;
            if (meta.filetype === 'image') {
                uploadUrl = this.options.imageUploadUrl;
            } else if (meta.filetype === 'media') {
                uploadUrl = this.options.videoUploadUrl;
            }

            const csrfHeaders = this.getCSRFHeaders();

            fetch(uploadUrl, {
                method: 'POST',
                headers: csrfHeaders,
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
                    console.error('Ошибка загрузки файла:', error);
                    if (this.options.onError) {
                        this.options.onError(error.message);
                    }
                });
        });

        input.click();
    }

    getCSRFHeaders() {
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

        if (csrfToken && csrfHeader) {
            return { [csrfHeader]: csrfToken };
        }

        return {};
    }

    // Публичные методы для управления
    focusEditor(lang) {
        if (this.instances[lang]) {
            setTimeout(() => {
                this.instances[lang].focus();
            }, 150);
        }
    }

    getContent(lang, format = 'html') {
        if (this.instances[lang]) {
            return this.instances[lang].getContent({ format });
        }
        return '';
    }

    setContent(lang, content) {
        if (this.instances[lang]) {
            this.instances[lang].setContent(content);
        }
    }

    getTextContent(lang) {
        return this.getContent(lang, 'text').trim();
    }

    getAllInstances() {
        return this.instances;
    }

    getAvailableLanguages() {
        return [...this.availableLanguages];
    }

    isEditorReady(lang) {
        return !!(this.instances[lang] && this.instances[lang].initialized);
    }

    destroy() {
        Object.values(this.instances).forEach(instance => {
            if (instance) {
                instance.destroy();
            }
        });
        this.instances = {};
        this.availableLanguages = [];
    }
}