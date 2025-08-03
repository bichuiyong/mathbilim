document.addEventListener('DOMContentLoaded', function () {
    let formChanged = false;

    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content') ||
        document.querySelector('input[name="_csrf"]')?.value ||
        document.querySelector('input[name="csrf"]')?.value;

    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || 'X-CSRF-TOKEN';

    initializeTinyMCE();

    setupSlugGeneration();

    setupFileHandling();

    setupActionButtons();

    setupFormSubmit();

    setupUnsavedChangesWarning();

    function initializeTinyMCE() {
        tinymce.init({
            selector: '#content',
            height: 500,
            language: 'ru',
            plugins: [
                'advlist', 'autolink', 'lists', 'link', 'charmap',
                'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                'insertdatetime', 'table', 'preview', 'help', 'wordcount',
                'emoticons', 'template', 'codesample'
            ],
            toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link | codesample | fullscreen preview | help',
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
            setup: function (editor) {
                editor.on('change', function () {
                    formChanged = true;
                });

                editor.on('Paste', function (e) {
                    const items = (e.clipboardData || e.originalEvent?.clipboardData)?.items;
                    if (items && Array.from(items).some(item => item.type.indexOf('image') !== -1)) {
                        e.preventDefault();
                        alert('Вставка изображений отключена.');
                    }
                });

                editor.on('Drop', function (e) {
                    const files = e.dataTransfer?.files;
                    if (files && Array.from(files).some(file => file.type.startsWith('image'))) {
                        e.preventDefault();
                        alert('Загрузка изображений перетаскиванием отключена.');
                    }
                });
            }
        });
    }

    function setupSlugGeneration() {
        const titleInput = document.getElementById('title');
        const slugInput = document.getElementById('slug');

        if (titleInput && slugInput) {
            titleInput.addEventListener('input', function () {
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
        const maxSize = 50 * 1024 * 1024;
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
            'pdf': 'fa-file-pdf text-danger',
            'doc': 'fa-file-word text-primary',
            'docx': 'fa-file-word text-primary',
            'xls': 'fa-file-excel text-success',
            'xlsx': 'fa-file-excel text-success',
            'ppt': 'fa-file-powerpoint text-warning',
            'pptx': 'fa-file-powerpoint text-warning',
            'zip': 'fa-file-archive text-secondary',
            'rar': 'fa-file-archive text-secondary',
            '7z': 'fa-file-archive text-secondary',
            'txt': 'fa-file-text text-info',
            'jpg': 'fa-file-image text-success',
            'jpeg': 'fa-file-image text-success',
            'png': 'fa-file-image text-success',
            'gif': 'fa-file-image text-success',
            'bmp': 'fa-file-image text-success',
            'svg': 'fa-file-image text-success',
            'webp': 'fa-file-image text-success',
            'mp3': 'fa-file-audio text-purple',
            'wav': 'fa-file-audio text-purple',
            'flac': 'fa-file-audio text-purple',
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

    function setupFormSubmit() {
        const form = document.getElementById('postForm');

        if (form) {
            form.addEventListener('input', function () {
                formChanged = true;
            });

            form.addEventListener('submit', function (e) {
                formChanged = false;
            });
        }
    }

    function setupUnsavedChangesWarning() {
        window.addEventListener('beforeunload', function (e) {
            if (formChanged) {
                e.preventDefault();
                e.returnValue = 'У вас есть несохраненные изменения. Вы уверены, что хотите покинуть страницу?';
            }
        });
    }
});
