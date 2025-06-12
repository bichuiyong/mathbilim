// Инициализация TinyMCE для всех текстовых редакторов
document.addEventListener('DOMContentLoaded', function() {
    // Инициализация TinyMCE
    tinymce.init({
        selector: '.content-editor',
        height: 400,
        menubar: false,
        plugins: [
            'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
            'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
            'insertdatetime', 'media', 'table', 'help', 'wordcount'
        ],
        toolbar: 'undo redo | blocks | bold italic forecolor | alignleft aligncenter ' +
            'alignright alignjustify | bullist numlist outdent indent | ' +
            'removeformat | help',
        content_style: 'body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; font-size: 14px }',
        language: 'ru',
        branding: false,
        setup: function (editor) {
            editor.on('change keyup', function () {
                editor.save();
                updateLanguageIndicator(editor.id);
            });
        }
    });

    // Инициализация Flatpickr для дат
    let startDatePicker, endDatePicker;

    const flatpickrConfig = {
        enableTime: true,
        dateFormat: "Y-m-d H:i",
        time_24hr: true,
        locale: "ru",
        minDate: "today",
        allowInput: false,
        clickOpens: true,
        altInput: true,
        altFormat: "d.m.Y H:i"
    };

    startDatePicker = flatpickr("#startDate", {
        ...flatpickrConfig,
        onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length > 0) {
                endDatePicker.set('minDate', selectedDates[0]);

                const endDate = endDatePicker.selectedDates[0];
                if (endDate && endDate < selectedDates[0]) {
                    endDatePicker.clear();
                    showAlert('Дата окончания была сброшена, так как она была раньше новой даты начала', 'warning');
                }
            }
        }
    });

    endDatePicker = flatpickr("#endDate", {
        ...flatpickrConfig,
        onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length > 0 && startDatePicker.selectedDates.length > 0) {
                const startDate = startDatePicker.selectedDates[0];
                const endDate = selectedDates[0];

                if (endDate < startDate) {
                    instance.clear();
                    showAlert('Дата окончания не может быть раньше даты начала', 'error');
                }
            }
        }
    });

    // Обработчики для языковых индикаторов
    setupLanguageIndicators();
});

// Функция для обновления индикатора языка
function updateLanguageIndicator(editorId) {
    const languageCode = editorId.replace('content-', '');
    const titleInput = document.getElementById(`title-${languageCode}`);
    const contentEditor = tinymce.get(editorId);
    const indicator = document.getElementById(`indicator-${languageCode}`);

    if (titleInput && contentEditor && indicator) {
        const hasTitle = titleInput.value.trim().length > 0;
        const hasContent = contentEditor.getContent().trim().length > 0;

        if (hasTitle && hasContent) {
            indicator.classList.add('filled');
        } else {
            indicator.classList.remove('filled');
        }
    }
}

// Настройка индикаторов языков
function setupLanguageIndicators() {
    // Обработчики для полей названия
    document.querySelectorAll('.title-input').forEach(input => {
        input.addEventListener('input', function() {
            const languageCode = this.id.replace('title-', '');
            updateLanguageIndicatorByCode(languageCode);
        });
    });

    // Первоначальная проверка всех полей
    setTimeout(() => {
        document.querySelectorAll('.title-input').forEach(input => {
            const languageCode = input.id.replace('title-', '');
            updateLanguageIndicatorByCode(languageCode);
        });
    }, 1000); // Даем время для инициализации TinyMCE
}

// Обновление индикатора по коду языка
function updateLanguageIndicatorByCode(languageCode) {
    const titleInput = document.getElementById(`title-${languageCode}`);
    const contentEditor = tinymce.get(`content-${languageCode}`);
    const indicator = document.getElementById(`indicator-${languageCode}`);

    if (titleInput && indicator) {
        const hasTitle = titleInput.value.trim().length > 0;
        let hasContent = false;

        if (contentEditor) {
            hasContent = contentEditor.getContent().trim().length > 0;
        }

        if (hasTitle && hasContent) {
            indicator.classList.add('filled');
        } else {
            indicator.classList.remove('filled');
        }
    }
}

// Обработка главного изображения
document.getElementById('mainImage').addEventListener('change', function(e) {
    const file = e.target.files[0];
    const previewContainer = document.getElementById('imagePreviewContainer');
    const previewImage = document.getElementById('imagePreview');

    if (file) {
        if (file.size > 5 * 1024 * 1024) {
            showAlert('Размер файла не должен превышать 5 МБ', 'error');
            this.value = '';
            previewContainer.style.display = 'none';
            return;
        }

        const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
        if (!allowedTypes.includes(file.type)) {
            showAlert('Поддерживаются только изображения (JPG, PNG, GIF, WebP)', 'error');
            this.value = '';
            previewContainer.style.display = 'none';
            return;
        }

        // Создание превью
        const reader = new FileReader();
        reader.onload = function(e) {
            previewImage.src = e.target.result;
            previewContainer.style.display = 'block';
        };
        reader.readAsDataURL(file);
    } else {
        previewContainer.style.display = 'none';
    }
});

// Удаление изображения
document.getElementById('removeImage').addEventListener('click', function() {
    document.getElementById('mainImage').value = '';
    document.getElementById('imagePreviewContainer').style.display = 'none';
});

// Обработка прикрепленных файлов
document.getElementById('attachments').addEventListener('change', function() {
    const files = Array.from(this.files);
    const maxSize = 10 * 1024 * 1024; // 10 МБ
    const allowedTypes = [
        'application/pdf',
        'application/msword',
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        'application/vnd.ms-excel',
        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        'image/jpeg',
        'image/jpg',
        'image/png'
    ];

    for (let file of files) {
        if (file.size > maxSize) {
            showAlert(`Файл "${file.name}" превышает максимальный размер 10 МБ`, 'error');
            this.value = '';
            return;
        }

        if (!allowedTypes.includes(file.type)) {
            showAlert(`Файл "${file.name}" имеет неподдерживаемый формат`, 'error');
            this.value = '';
            return;
        }
    }
});

// Отслеживание выбранных организаций
document.querySelectorAll('.organization-checkbox').forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        const selectedCount = document.querySelectorAll('.organization-checkbox:checked').length;
        const organizationsContainer = document.querySelector('.organizations-container');
        organizationsContainer.setAttribute('data-selected', selectedCount);
    });
});

// Функция для показа уведомлений
function showAlert(message, type = 'info') {
    // Создаем временный alert div
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type === 'error' ? 'danger' : type === 'warning' ? 'warning' : 'info'} alert-dismissible fade show position-fixed`;
    alertDiv.style.cssText = 'top: 20px; right: 20px; z-index: 9999; max-width: 400px;';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;

    document.body.appendChild(alertDiv);

    // Автоматически удаляем через 5 секунд
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.remove();
        }
    }, 5000);
}

// Валидация формы перед отправкой
document.querySelector('form').addEventListener('submit', function(e) {
    let hasError = false;
    const errorMessages = [];

    // Проверяем, что заполнен хотя бы один язык полностью
    const languages = ['ru', 'ky', 'en'];
    let hasCompleteLanguage = false;

    languages.forEach(lang => {
        const titleInput = document.getElementById(`title-${lang}`);
        const contentEditor = tinymce.get(`content-${lang}`);

        if (titleInput && contentEditor) {
            const hasTitle = titleInput.value.trim().length > 0;
            const hasContent = contentEditor.getContent().trim().length > 0;

            if (hasTitle && hasContent) {
                hasCompleteLanguage = true;
            }
        }
    });

    if (!hasCompleteLanguage) {
        hasError = true;
        errorMessages.push('Необходимо заполнить название и описание хотя бы для одного языка');
    }

    // Проверяем дату начала
    const startDate = document.getElementById('startDate').value;
    if (!startDate) {
        hasError = true;
        errorMessages.push('Необходимо указать дату начала мероприятия');
    }

    // Проверяем тип мероприятия
    const eventType = document.getElementById('type').value;
    if (!eventType) {
        hasError = true;
        errorMessages.push('Необходимо выбрать тип мероприятия');
    }

    if (hasError) {
        e.preventDefault();
        errorMessages.forEach(message => {
            showAlert(message, 'error');
        });
        return false;
    }

    // Синхронизируем содержимое TinyMCE перед отправкой
    tinymce.triggerSave();
});