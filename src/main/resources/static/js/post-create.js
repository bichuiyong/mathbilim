document.addEventListener('DOMContentLoaded', function() {
    let formChanged = false;

    const csrfToken = document.querySelector('input[name="_csrf"]')?.value ||
        document.querySelector('input[name="csrf"]')?.value ||
        document.querySelector('meta[name="_csrf"]')?.getAttribute('content');

    initializeTinyMCE();

    setupSlugGeneration();

    setupFileHandling();

    setupActionButtons();

    setupFormValidation();

    setupUnsavedChangesWarning();

    function initializeTinyMCE() {
        tinymce.init({
            selector: '#content',
            height: 500,
            language: 'ru',
            plugins: [
                'advlist', 'autolink', 'lists', 'link', 'image', 'charmap',
                'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                'insertdatetime', 'media', 'table', 'preview', 'help', 'wordcount',
                'emoticons', 'template', 'codesample'
            ],
            toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image media | codesample | fullscreen preview | help',
            content_style: `
                body { 
                    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif; 
                    font-size: 16px; 
                    line-height: 1.6; 
                    color: #333;
                    max-width: 800px;
                    margin: 0 auto;
                    padding: 20px;
                }
                h1, h2, h3, h4, h5, h6 { 
                    color: #2c3e50; 
                    margin-top: 1.5em; 
                    margin-bottom: 0.5em; 
                }
                p { margin-bottom: 1em; }
                img { max-width: 100%; height: auto; }
                blockquote { 
                    border-left: 4px solid #4a90e2; 
                    padding-left: 1rem; 
                    margin: 1.5rem 0; 
                    font-style: italic; 
                    color: #6c757d; 
                }
                code { 
                    background-color: #f8f9fa; 
                    padding: 0.2rem 0.4rem; 
                    border-radius: 3px; 
                    font-size: 0.9em; 
                }
                pre { 
                    background-color: #f8f9fa; 
                    padding: 1rem; 
                    border-radius: 8px; 
                    overflow-x: auto; 
                }
            `,

            images_upload_handler: function (blobInfo, success, failure) {
                uploadFileToAPI(blobInfo.blob(), blobInfo.filename(), 'editor/images')
                    .then(result => {
                        if (result.s3Link) {
                            success(result.s3Link);
                        } else {
                            failure('Ошибка загрузки изображения');
                        }
                    })
                    .catch(error => {
                        failure('Ошибка загрузки изображения: ' + error.message);
                    });
            },

            file_picker_callback: function(callback, value, meta) {
                if (meta.filetype === 'image') {
                    selectAndUploadFile('image/*', 'editor/images', callback);
                } else if (meta.filetype === 'file') {
                    selectAndUploadFile('.pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx', 'editor/documents', callback);
                }
            },

            setup: function(editor) {
                editor.on('change', function() {
                    formChanged = true;
                });
            }
        });
    }

    function setupSlugGeneration() {
        const titleInput = document.getElementById('title');
        const slugInput = document.getElementById('slug');

        if (titleInput && slugInput) {
            titleInput.addEventListener('input', function() {
                const title = this.value;
                const slug = generateSlugFromTitle(title);
                slugInput.value = slug;
                formChanged = true;
            });
        }
    }

    function generateSlugFromTitle(title) {
        if (!title || title.trim() === '') {
            return '';
        }

        return title
            .toLowerCase()
            .replace(/[^a-z0-9а-яё\s\-]/g, '')
            .replace(/\s+/g, '-')
            .replace(/-+/g, '-')
            .replace(/^-|-$/g, '');
    }

    function setupFileHandling() {
        const filesInput = document.getElementById('attachments');
        const filePreview = document.getElementById('filePreview');

        if (filesInput && filePreview) {
            filesInput.addEventListener('change', handleFileSelection);
        }
    }

    function handleFileSelection(e) {
        const files = e.target.files;
        const filePreview = document.getElementById('filePreview');

        if (!validateFiles(files)) {
            e.target.value = '';
            return;
        }

        displayFilePreview(files, filePreview);
        formChanged = true;
    }

    function validateFiles(files) {
        const maxSize = 50 * 1024 * 1024; // 50MB
        const maxFiles = 10;

        if (files.length > maxFiles) {
            alert(`Можно прикрепить максимум ${maxFiles} файлов`);
            return false;
        }

        for (let file of files) {
            if (file.size > maxSize) {
                alert(`Файл "${file.name}" слишком большой. Максимальный размер: 50MB`);
                return false;
            }
        }

        return true;
    }

    function displayFilePreview(files, container) {
        container.innerHTML = '';

        if (files.length === 0) return;

        const header = document.createElement('div');
        header.className = 'mb-2';
        header.innerHTML = `<strong>📎 Прикрепленных файлов: ${files.length}</strong>`;
        container.appendChild(header);

        Array.from(files).forEach((file, index) => {
            const fileItem = createFileItem(file, index);
            container.appendChild(fileItem);
        });
    }

    function createFileItem(file, index) {
        const fileItem = document.createElement('div');
        fileItem.className = 'file-item';

        const fileInfo = document.createElement('div');
        fileInfo.className = 'file-info';

        const icon = document.createElement('i');
        icon.className = `fas ${getFileIcon(file.name)} file-icon`;

        const fileName = document.createElement('span');
        fileName.className = 'file-name';
        fileName.textContent = file.name;

        const fileSize = document.createElement('span');
        fileSize.className = 'file-size';
        fileSize.textContent = formatFileSize(file.size);

        fileInfo.appendChild(icon);
        fileInfo.appendChild(fileName);
        fileInfo.appendChild(fileSize);

        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'file-remove';
        removeBtn.innerHTML = '×';
        removeBtn.onclick = () => removeFile(index);

        fileItem.appendChild(fileInfo);
        fileItem.appendChild(removeBtn);

        return fileItem;
    }

    function getFileIcon(fileName) {
        const ext = fileName.split('.').pop().toLowerCase();
        const iconMap = {
            // Документы
            'pdf': 'fa-file-pdf text-danger',
            'doc': 'fa-file-word text-primary',
            'docx': 'fa-file-word text-primary',
            'xls': 'fa-file-excel text-success',
            'xlsx': 'fa-file-excel text-success',
            'ppt': 'fa-file-powerpoint text-warning',
            'pptx': 'fa-file-powerpoint text-warning',

            // Архивы
            'zip': 'fa-file-archive text-secondary',
            'rar': 'fa-file-archive text-secondary',
            '7z': 'fa-file-archive text-secondary',

            // Текст
            'txt': 'fa-file-text text-info',

            // Изображения
            'jpg': 'fa-file-image text-success',
            'jpeg': 'fa-file-image text-success',
            'png': 'fa-file-image text-success',
            'gif': 'fa-file-image text-success',
            'bmp': 'fa-file-image text-success',
            'svg': 'fa-file-image text-success',
            'webp': 'fa-file-image text-success',

            // Аудио
            'mp3': 'fa-file-audio text-purple',
            'wav': 'fa-file-audio text-purple',
            'flac': 'fa-file-audio text-purple',

            // Видео
            'mp4': 'fa-file-video text-dark',
            'avi': 'fa-file-video text-dark',
            'mkv': 'fa-file-video text-dark',
            'mov': 'fa-file-video text-dark'
        };

        return iconMap[ext] || 'fa-file text-muted';
    }

    function formatFileSize(bytes) {
        if (bytes === 0) return '0 Bytes';
        const k = 1024;
        const sizes = ['Bytes', 'KB', 'MB', 'GB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    }

    function removeFile(index) {
        const fileInput = document.getElementById('attachments');
        const dt = new DataTransfer();
        const files = fileInput.files;

        for (let i = 0; i < files.length; i++) {
            if (i !== index) {
                dt.items.add(files[i]);
            }
        }

        fileInput.files = dt.files;
        fileInput.dispatchEvent(new Event('change'));
    }

    function setupActionButtons() {
        const saveAsDraftBtn = document.getElementById('saveAsDraft');
        if (saveAsDraftBtn) {
            saveAsDraftBtn.addEventListener('click', function() {
                const statusInput = document.createElement('input');
                statusInput.type = 'hidden';
                statusInput.name = 'status';
                statusInput.value = 'DRAFT';
                document.getElementById('postForm').appendChild(statusInput);

                formChanged = false;
                document.getElementById('postForm').submit();
            });
        }

        const previewBtn = document.getElementById('previewBtn');
        if (previewBtn) {
            previewBtn.addEventListener('click', showPreview);
        }
    }

    function showPreview() {
        const title = document.getElementById('title').value;
        const content = tinymce.get('content').getContent();

        document.getElementById('previewTitle').textContent = title || 'Без названия';
        document.getElementById('previewBody').innerHTML = content;

        const modal = new bootstrap.Modal(document.getElementById('previewModal'));
        modal.show();
    }

    function setupFormValidation() {
        const form = document.getElementById('postForm');

        if (form) {
            form.addEventListener('input', function() {
                formChanged = true;
            });

            form.addEventListener('submit', function(e) {
                if (!validateForm()) {
                    e.preventDefault();
                    return;
                }
                formChanged = false;
            });
        }
    }

    function validateForm() {
        const title = document.getElementById('title').value.trim();
        const slug = document.getElementById('slug').value.trim();
        const content = tinymce.get('content').getContent({format: 'text'}).trim();

        if (!title) {
            alert('Пожалуйста, введите название поста');
            document.getElementById('title').focus();
            return false;
        }

        if (!slug) {
            alert('Пожалуйста, введите URL адрес (slug)');
            document.getElementById('slug').focus();
            return false;
        }

        if (!content || content.length < 10) {
            alert('Пожалуйста, добавьте содержание поста (минимум 10 символов)');
            tinymce.get('content').focus();
            return false;
        }

        return true;
    }

    function setupUnsavedChangesWarning() {
        window.addEventListener('beforeunload', function(e) {
            if (formChanged) {
                e.preventDefault();
                e.returnValue = 'У вас есть несохраненные изменения. Вы уверены, что хотите покинуть страницу?';
            }
        });
    }

    async function uploadFileToAPI(file, filename, context) {
        const formData = new FormData();
        formData.append('file', file);

        const response = await fetch(`/api/files?context=${encodeURIComponent(context)}`, {
            method: 'POST',
            body: formData,
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        });

        if (!response.ok) {
            throw new Error('Ошибка сервера: ' + response.status);
        }

        return await response.json();
    }

    function selectAndUploadFile(accept, context, callback) {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept', accept);

        input.onchange = function() {
            const file = this.files[0];
            if (file) {
                uploadFileToAPI(file, file.name, context)
                    .then(result => {
                        if (context.includes('images')) {
                            callback(result.s3Link, { alt: file.name });
                        } else {
                            callback(result.s3Link, { text: result.filename });
                        }
                    })
                    .catch(error => {
                        alert('Ошибка загрузки файла: ' + error.message);
                    });
            }
        };

        input.click();
    }
});