class EventCreateManager {
    constructor() {
        this.formId = 'eventForm';
        this.startDatePicker = null;
        this.endDatePicker = null;
        this.froalaInstances = {};
        this.cropper = null;
        this.validationTimeout = null;

        console.log('EventCreateManager: Starting initialization...');
        this.init();
    }

    init() {
        // Ждем полной загрузки DOM
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', () => {
                this.initializeComponents();
            });
        } else {
            this.initializeComponents();
        }
    }

    initializeComponents() {
        console.log('EventCreateManager: Initializing all components...');

        setTimeout(() => {
            try {
                this.initLocationToggle();
                this.initDatePickers();
                this.initImageCrop();
                this.initFroalaEditors();
                this.initOrganizationSelection();
                this.initFormSubmission();
                this.initLanguageTabs();

                console.log('EventCreateManager: All components initialized successfully');
            } catch (error) {
                console.error('EventCreateManager: Initialization error:', error);
            }
        }, 200); // Даем время для загрузки всех библиотек
    }

    // ==========================================
    // ИНИЦИАЛИЗАЦИЯ ВЫБОРА ДАТ (исправленная версия)
    // ==========================================

    initDatePickers() {
        console.log('Initializing date pickers...');

        // Проверяем наличие Flatpickr
        if (typeof flatpickr === 'undefined') {
            console.error('Flatpickr library not found!');
            return;
        }

        const startInput = document.getElementById('startDatePicker');
        const endInput = document.getElementById('endDatePicker');

        if (!startInput || !endInput) {
            console.error('Date input elements not found:', { startInput, endInput });
            return;
        }

        console.log('Found date inputs, initializing Flatpickr...');

        // Конфигурация для Flatpickr
        const config = {
            enableTime: true,
            dateFormat: "Y-m-d H:i:S",
            altInput: true,
            altFormat: "d.m.Y в H:i",
            time_24hr: true,
            locale: "ru",
            allowInput: true,
            clickOpens: true,
            minuteIncrement: 15,
            static: false,
            wrap: false,
            minDate: "today" // Не позволяем выбирать прошедшие даты
        };

        try {
            // Инициализация выбора даты начала
            this.startDatePicker = flatpickr(startInput, {
                ...config,
                placeholder: "Выберите дату и время начала",
                onChange: (selectedDates, dateStr) => {
                    console.log('Start date selected:', dateStr);
                    this.handleStartDateChange(selectedDates[0]);
                },
                onReady: () => {
                    console.log('Start date picker ready');
                },
                onClose: () => {
                    // Валидируем при закрытии календаря
                    setTimeout(() => this.validateDates(), 100);
                }
            });

            // Инициализация выбора даты окончания
            this.endDatePicker = flatpickr(endInput, {
                ...config,
                placeholder: "Выберите дату и время окончания",
                onChange: (selectedDates, dateStr) => {
                    console.log('End date selected:', dateStr);
                    this.handleEndDateChange(selectedDates[0]);
                },
                onReady: () => {
                    console.log('End date picker ready');
                },
                onClose: () => {
                    // Валидируем при закрытии календаря
                    setTimeout(() => this.validateDates(), 100);
                }
            });

            // Добавляем обработчики кликов на иконки календаря
            this.setupDateIconHandlers();

            // Добавляем валидацию на изменение полей ввода
            this.setupDateInputValidation();

            console.log('Date pickers initialized successfully');

        } catch (error) {
            console.error('Failed to initialize date pickers:', error);
        }
    }

    setupDateIconHandlers() {
        const startIcon = document.querySelector('#startDatePicker').closest('.datetime-input-wrapper')?.querySelector('.datetime-icon');
        const endIcon = document.querySelector('#endDatePicker').closest('.datetime-input-wrapper')?.querySelector('.datetime-icon');

        if (startIcon) {
            startIcon.addEventListener('click', () => {
                console.log('Start date icon clicked');
                this.startDatePicker?.open();
            });
            startIcon.style.cursor = 'pointer';
        }

        if (endIcon) {
            endIcon.addEventListener('click', () => {
                console.log('End date icon clicked');
                this.endDatePicker?.open();
            });
            endIcon.style.cursor = 'pointer';
        }
    }

    // Добавляем валидацию на изменение полей ввода
    setupDateInputValidation() {
        const startInput = document.getElementById('startDatePicker');
        const endInput = document.getElementById('endDatePicker');

        if (startInput) {
            startInput.addEventListener('blur', () => {
                setTimeout(() => this.validateDates(), 100);
            });

            startInput.addEventListener('input', () => {
                // Debounce validation
                clearTimeout(this.validationTimeout);
                this.validationTimeout = setTimeout(() => this.validateDates(), 500);
            });
        }

        if (endInput) {
            endInput.addEventListener('blur', () => {
                setTimeout(() => this.validateDates(), 100);
            });

            endInput.addEventListener('input', () => {
                // Debounce validation
                clearTimeout(this.validationTimeout);
                this.validationTimeout = setTimeout(() => this.validateDates(), 500);
            });
        }
    }

    handleStartDateChange(selectedDate) {
        if (!selectedDate) {
            this.validateDates();
            return;
        }

        console.log('Processing start date change:', selectedDate);

        // Устанавливаем минимальную дату для окончания (минимум 30 минут после начала)
        if (this.endDatePicker) {
            const minEndDate = new Date(selectedDate.getTime() + 30 * 60 * 1000);
            this.endDatePicker.set('minDate', minEndDate);

            // Сбрасываем дату окончания если она стала невалидной
            const currentEndDate = this.endDatePicker.selectedDates[0];
            if (currentEndDate && currentEndDate <= selectedDate) {
                this.endDatePicker.clear();
                this.showNotification('Дата окончания была сброшена, так как она должна быть позже даты начала', 'info');
            }
        }

        this.syncDateToForm('startDate', selectedDate);
        // Валидируем с небольшой задержкой
        setTimeout(() => this.validateDates(), 100);
    }

    handleEndDateChange(selectedDate) {
        if (!selectedDate) {
            this.validateDates();
            return;
        }

        console.log('Processing end date change:', selectedDate);
        this.syncDateToForm('endDate', selectedDate);
        // Валидируем с небольшой задержкой
        setTimeout(() => this.validateDates(), 100);
    }

    syncDateToForm(fieldName, date) {
        // Ищем соответствующее скрытое поле
        const hiddenInput = document.querySelector(`input[name*="${fieldName}"]`);
        if (hiddenInput && date) {
            const formattedDate = this.formatDateForServer(date);
            hiddenInput.value = formattedDate;
            console.log(`Synced ${fieldName}:`, formattedDate);
        }
    }

    formatDateForServer(date) {
        if (!date) return '';

        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');

        return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    }

    // ==========================================
    // ИСПРАВЛЕННАЯ ВАЛИДАЦИЯ ДАТ
    // ==========================================

    validateDates() {
        console.log('Validating dates...');

        const startDate = this.getSelectedStartDate();
        const endDate = this.getSelectedEndDate();

        console.log('Current dates:', { startDate, endDate });

        let isValid = true;
        const now = new Date();

        // Очищаем предыдущие ошибки
        this.clearAllDateErrors();

        // Проверка даты начала
        if (!startDate) {
            this.showFieldError(this.startDatePicker?.input, 'Дата начала обязательна');
            isValid = false;
        } else {
            // Проверяем что дата не в прошлом (с учетом текущего времени)
            if (startDate < now) {
                this.showFieldError(this.startDatePicker?.input, 'Дата начала не может быть в прошлом');
                isValid = false;
            }
        }

        // Проверка даты окончания
        if (endDate) {
            if (!startDate) {
                this.showFieldError(this.endDatePicker?.input, 'Сначала выберите дату начала');
                isValid = false;
            } else if (endDate <= startDate) {
                this.showFieldError(this.endDatePicker?.input, 'Дата окончания должна быть позже даты начала');
                isValid = false;
            } else {
                // Проверяем минимальную разницу (30 минут)
                const timeDiff = endDate.getTime() - startDate.getTime();
                const minDiff = 30 * 60 * 1000; // 30 минут в миллисекундах

                if (timeDiff < minDiff) {
                    this.showFieldError(this.endDatePicker?.input, 'Мероприятие должно длиться минимум 30 минут');
                    isValid = false;
                }
            }
        }

        console.log('Date validation result:', isValid);
        this.updateSubmitButton(isValid);

        return isValid;
    }

    // Вспомогательные методы для получения выбранных дат
    getSelectedStartDate() {
        if (this.startDatePicker && this.startDatePicker.selectedDates.length > 0) {
            return this.startDatePicker.selectedDates[0];
        }

        // Fallback: пытаемся парсить значение из поля
        const startInput = document.getElementById('startDatePicker');
        if (startInput && startInput.value) {
            const parsed = new Date(startInput.value);
            if (!isNaN(parsed.getTime())) {
                return parsed;
            }
        }

        return null;
    }

    getSelectedEndDate() {
        if (this.endDatePicker && this.endDatePicker.selectedDates.length > 0) {
            return this.endDatePicker.selectedDates[0];
        }

        // Fallback: пытаемся парсить значение из поля
        const endInput = document.getElementById('endDatePicker');
        if (endInput && endInput.value) {
            const parsed = new Date(endInput.value);
            if (!isNaN(parsed.getTime())) {
                return parsed;
            }
        }

        return null;
    }

    // Очищаем все ошибки дат
    clearAllDateErrors() {
        if (this.startDatePicker?.input) {
            this.clearFieldError(this.startDatePicker.input);
        }
        if (this.endDatePicker?.input) {
            this.clearFieldError(this.endDatePicker.input);
        }
    }

    // ==========================================
    // УПРАВЛЕНИЕ ТИПОМ МЕРОПРИЯТИЯ
    // ==========================================

    initLocationToggle() {
        console.log('Initializing location toggle...');

        const offlineRadio = document.getElementById('offlineEvent');
        const onlineRadio = document.getElementById('onlineEvent');

        if (!offlineRadio || !onlineRadio) {
            console.warn('Location radio buttons not found');
            return;
        }

        offlineRadio.addEventListener('change', () => {
            if (offlineRadio.checked) {
                this.showAddressField();
                this.hideUrlField();
            }
        });

        onlineRadio.addEventListener('change', () => {
            if (onlineRadio.checked) {
                this.showUrlField();
                this.hideAddressField();
            }
        });

        // Инициальное состояние
        this.updateLocationFields();
        console.log('Location toggle initialized');
    }

    showAddressField() {
        const field = document.getElementById('addressField');
        if (field) {
            field.style.display = 'block';
            const input = field.querySelector('input');
            if (input) input.required = true;
        }
    }

    hideAddressField() {
        const field = document.getElementById('addressField');
        if (field) {
            field.style.display = 'none';
            const input = field.querySelector('input');
            if (input) {
                input.required = false;
                input.value = '';
            }
        }
    }

    showUrlField() {
        const field = document.getElementById('urlField');
        if (field) {
            field.style.display = 'block';
            const input = field.querySelector('input');
            if (input) input.required = true;
        }
    }

    hideUrlField() {
        const field = document.getElementById('urlField');
        if (field) {
            field.style.display = 'none';
            const input = field.querySelector('input');
            if (input) {
                input.required = false;
                input.value = '';
            }
        }
    }

    updateLocationFields() {
        const offlineRadio = document.getElementById('offlineEvent');
        const onlineRadio = document.getElementById('onlineEvent');

        if (offlineRadio?.checked) {
            this.showAddressField();
            this.hideUrlField();
        } else if (onlineRadio?.checked) {
            this.showUrlField();
            this.hideAddressField();
        } else {
            this.hideAddressField();
            this.hideUrlField();
        }
    }

    // ==========================================
    // ОБРЕЗКА ИЗОБРАЖЕНИЙ
    // ==========================================

    initImageCrop() {
        console.log('Initializing image crop with ImageCropManager...');

        // Используем ImageCropManager вместо собственной логики
        this.imageCropManager = new ImageCropManager({
            inputId: 'mainImageInput',
            previewId: 'imagePreview',
            previewContainerSelector: '.image-preview-container',
            croppedPreviewSelector: '.cropped-preview',
            cropButtonId: 'cropButton',
            cancelCropId: 'cancelCrop',
            changeCropId: 'changeCrop',
            croppedImageId: 'croppedImage',
            aspectRatio: 3, // 1200x400
            outputWidth: 1200,
            outputHeight: 400,
            onCropComplete: (blob, dataUrl) => {
                console.log('Image crop completed');
                // Дополнительная логика после обрезки, если нужна
            },
            onError: (message) => {
                this.showNotification(message, 'danger');
            }
        });

        // Сохраняем в window для отладки
        window.imageCropManager = this.imageCropManager;

        console.log('ImageCropManager initialized successfully');
    }

    // handleImageSelection(file) {
    //     if (!file.type.startsWith('image/')) {
    //         this.showNotification('Пожалуйста, выберите изображение', 'warning');
    //         return;
    //     }
    //
    //     const reader = new FileReader();
    //     reader.onload = (e) => {
    //         this.showImageCropInterface(e.target.result);
    //     };
    //     reader.readAsDataURL(file);
    // }

    // showImageCropInterface(imageSrc) {
    //     const previewContainer = document.querySelector('.image-preview-container');
    //     const previewImage = document.getElementById('imagePreview');
    //
    //     if (!previewContainer || !previewImage) {
    //         console.warn('Image preview elements not found');
    //         return;
    //     }
    //
    //     previewImage.src = imageSrc;
    //     previewContainer.style.display = 'block';
    //
    //     // Инициализация Cropper.js если доступен
    //     if (typeof Cropper !== 'undefined') {
    //         if (this.cropper) {
    //             this.cropper.destroy();
    //         }
    //
    //         this.cropper = new Cropper(previewImage, {
    //             aspectRatio: 3 / 1, // 1200x400
    //             viewMode: 2,
    //             autoCropArea: 1,
    //             responsive: true,
    //             restore: false,
    //             guides: true,
    //             center: true,
    //             highlight: false,
    //             cropBoxMovable: true,
    //             cropBoxResizable: true,
    //             toggleDragModeOnDblclick: false
    //         });
    //
    //         this.setupCropButtons();
    //     }
    // }

    // setupCropButtons() {
    //     const cropButton = document.getElementById('cropButton');
    //     const cancelButton = document.getElementById('cancelCrop');
    //
    //     if (cropButton) {
    //         cropButton.onclick = () => this.applyCrop();
    //     }
    //
    //     if (cancelButton) {
    //         cancelButton.onclick = () => this.cancelCrop();
    //     }
    // }

    // applyCrop() {
    //     if (!this.cropper) return;
    //
    //     const canvas = this.cropper.getCroppedCanvas({
    //         width: 1200,
    //         height: 400,
    //         fillColor: '#fff'
    //     });
    //
    //     const croppedImage = document.getElementById('croppedImage');
    //     const croppedPreview = document.querySelector('.cropped-preview');
    //
    //     if (croppedImage && croppedPreview) {
    //         croppedImage.src = canvas.toDataURL('image/jpeg', 0.8);
    //         croppedPreview.style.display = 'block';
    //     }
    //
    //     this.hideCropInterface();
    // }
    //
    // cancelCrop() {
    //     this.hideCropInterface();
    //     const imageInput = document.getElementById('mainImageInput');
    //     if (imageInput) {
    //         imageInput.value = '';
    //     }
    // }
    //
    // hideCropInterface() {
    //     const previewContainer = document.querySelector('.image-preview-container');
    //     if (previewContainer) {
    //         previewContainer.style.display = 'none';
    //     }
    //
    //     if (this.cropper) {
    //         this.cropper.destroy();
    //         this.cropper = null;
    //     }
    // }

    // ==========================================
    // FROALA РЕДАКТОРЫ
    // ==========================================

    initFroalaEditors() {
        console.log('Initializing Froala editors...');

        if (typeof FroalaEditor === 'undefined') {
            console.warn('FroalaEditor not available');
            return;
        }

        const textareas = document.querySelectorAll('textarea.rich-text-editor');
        console.log('Found textareas:', textareas.length);

        textareas.forEach((textarea) => {
            const lang = textarea.dataset.lang;
            if (lang) {
                try {
                    this.froalaInstances[lang] = new FroalaEditor(textarea, {
                        toolbarButtons: [
                            'undo', 'redo', '|',
                            'bold', 'italic', 'underline', '|',
                            'paragraphFormat', 'formatUL', 'formatOL', '|',
                            'insertLink', '|',
                            'html'
                        ],
                        height: 300,
                        language: 'ru',
                        placeholderText: 'Введите описание мероприятия...',
                        quickInsertEnabled: false,
                        imageUpload: false
                    });
                    console.log(`Froala initialized for ${lang}`);
                } catch (error) {
                    console.error(`Failed to initialize Froala for ${lang}:`, error);
                }
            }
        });
    }

    // ==========================================
    // ЯЗЫКОВЫЕ ТАБЫ
    // ==========================================

    initLanguageTabs() {
        console.log('Initializing language tabs...');

        const tabs = document.querySelectorAll('[data-bs-toggle="tab"]');
        const translationFields = document.querySelectorAll('.translation-field');

        // Отслеживаем изменения в полях переводов
        translationFields.forEach(field => {
            field.addEventListener('input', () => {
                this.updateTranslationIndicators();
            });
        });

        // Инициальное обновление индикаторов
        setTimeout(() => {
            this.updateTranslationIndicators();
        }, 500);

        console.log('Language tabs initialized');
    }

    updateTranslationIndicators() {
        const languages = ['ru', 'en', 'ky']; // Добавьте нужные языки

        languages.forEach(lang => {
            const indicator = document.getElementById(`indicator-${lang}`);
            if (!indicator) return;

            const titleField = document.querySelector(`input[data-lang="${lang}"][name*="title"]`);
            const contentField = document.querySelector(`textarea[data-lang="${lang}"]`);

            let hasTitle = titleField && titleField.value.trim().length > 0;
            let hasContent = false;

            // Проверяем контент в Froala или обычном textarea
            if (this.froalaInstances[lang]) {
                const froalaContent = this.froalaInstances[lang].html.get();
                hasContent = froalaContent && froalaContent.replace(/<[^>]*>/g, '').trim().length > 0;
            } else if (contentField) {
                hasContent = contentField.value.trim().length > 0;
            }

            // Обновляем индикатор
            const icon = indicator.querySelector('i');
            if (icon) {
                if (hasTitle && hasContent) {
                    icon.className = 'fas fa-circle text-success';
                    icon.title = 'Заполнено';
                } else if (hasTitle || hasContent) {
                    icon.className = 'fas fa-circle text-warning';
                    icon.title = 'Частично заполнено';
                } else {
                    icon.className = 'fas fa-circle text-danger';
                    icon.title = 'Не заполнено';
                }
            }
        });
    }

    // ==========================================
    // ВЫБОР ОРГАНИЗАЦИЙ
    // ==========================================

    initOrganizationSelection() {
        console.log('Initializing organization selection...');

        const select = document.querySelector('select[name="organizationIds"]');
        if (select) {
            select.addEventListener('change', () => {
                const count = Array.from(select.selectedOptions).length;
                console.log(`Selected organizations: ${count}`);
            });
        }

        console.log('Organization selection initialized');
    }

    // ==========================================
    // ОТПРАВКА ФОРМЫ
    // ==========================================

    initFormSubmission() {
        console.log('Initializing form submission...');

        const form = document.getElementById(this.formId);
        if (!form) {
            console.error('Form not found:', this.formId);
            return;
        }

        form.addEventListener('submit', (e) => {
            console.log('Form submission started...');

            if (!this.validateForm()) {
                e.preventDefault();
                console.log('Form validation failed');
                return false;
            }

            this.prepareFormData();
            console.log('Form data prepared, submitting...');
        });

        console.log('Form submission initialized');
    }

    // ==========================================
    // ОБНОВЛЕННАЯ ВАЛИДАЦИЯ ФОРМЫ
    // ==========================================

    validateForm() {
        console.log('=== FORM VALIDATION START ===');

        let isValid = true;

        // Валидация дат (основная проверка)
        const datesValid = this.validateDates();
        if (!datesValid) {
            console.log('Date validation failed');
            isValid = false;
        }

        // Валидация типа мероприятия
        if (!this.validateEventType()) {
            console.log('Event type validation failed');
            isValid = false;
        }

        // Валидация локации
        if (!this.validateLocation()) {
            console.log('Location validation failed');
            isValid = false;
        }

        // Валидация переводов
        if (!this.validateTranslations()) {
            console.log('Translations validation failed');
            isValid = false;
        }

        console.log('=== FORM VALIDATION RESULT:', isValid, '===');

        if (!isValid) {
            // Прокручиваем к первой ошибке
            this.scrollToFirstError();
        }

        return isValid;
    }

    validateEventType() {
        const typeSelect = document.querySelector('select[name*="typeId"]');
        if (!typeSelect || !typeSelect.value) {
            this.showNotification('Выберите тип мероприятия', 'warning');
            return false;
        }
        return true;
    }

    validateLocation() {
        const offlineRadio = document.getElementById('offlineEvent');
        const onlineRadio = document.getElementById('onlineEvent');


        if (offlineRadio?.checked) {
            const addressInput = document.querySelector('input[name*="address"]');
            if (!addressInput?.value?.trim()) {
                this.showNotification('Укажите адрес для офлайн мероприятия', 'warning');
                return false;
            }
        }

        if (onlineRadio?.checked) {
            const urlInput = document.querySelector('input[name*="url"]');
            if (!urlInput?.value?.trim()) {
                this.showNotification('Укажите ссылку для онлайн мероприятия', 'warning');
                return false;
            }
        }

        return true;
    }

    validateTranslations() {
        const titleFields = document.querySelectorAll('input[name*="title"]');
        let hasAtLeastOneTranslation = false;

        titleFields.forEach(field => {
            if (field.value.trim()) {
                hasAtLeastOneTranslation = true;
            }
        });

        return true;
    }

    prepareFormData() {
        Object.keys(this.froalaInstances).forEach(lang => {
            const editor = this.froalaInstances[lang];
            const textarea = document.querySelector(`textarea[data-lang="${lang}"]`);

            if (editor && textarea) {
                textarea.value = editor.html.get();
            }
        });

        // Синхронизируем даты
        const startDate = this.getSelectedStartDate();
        const endDate = this.getSelectedEndDate();

        if (startDate) {
            this.syncDateToForm('startDate', startDate);
        }
        if (endDate) {
            this.syncDateToForm('endDate', endDate);
        }
    }

    // Прокручиваем к первой ошибке
    scrollToFirstError() {
        const firstError = document.querySelector('.is-invalid');
        if (firstError) {
            firstError.scrollIntoView({
                behavior: 'smooth',
                block: 'center'
            });

            // Фокусируемся на поле с ошибкой
            setTimeout(() => {
                firstError.focus();
            }, 500);
        }
    }

    // ==========================================
    // УЛУЧШЕННЫЕ МЕТОДЫ РАБОТЫ С ОШИБКАМИ
    // ==========================================

    showFieldError(input, message) {
        if (!input) {
            console.warn('Cannot show error: input element not found');
            return;
        }

        console.log('Showing field error:', message);

        input.classList.add('is-invalid');

        // Удаляем существующую ошибку
        const wrapper = input.closest('.datetime-input-wrapper') || input.parentNode;
        const existingError = wrapper.querySelector('.invalid-feedback.custom-error');
        if (existingError) {
            existingError.remove();
        }

        // Добавляем новую ошибку
        const errorDiv = document.createElement('div');
        errorDiv.className = 'invalid-feedback custom-error d-block';
        errorDiv.textContent = message;
        errorDiv.style.fontSize = '0.875rem';
        errorDiv.style.marginTop = '0.25rem';

        wrapper.appendChild(errorDiv);
    }

    clearFieldError(input) {
        if (!input) return;

        input.classList.remove('is-invalid');

        // Ищем ошибку в разных возможных местах
        const wrapper = input.closest('.datetime-input-wrapper') || input.parentNode;
        const errorDiv = wrapper.querySelector('.invalid-feedback.custom-error');

        if (errorDiv) {
            errorDiv.remove();
        }
    }

    // ==========================================
    // УТИЛИТЫ
    // ==========================================

    updateSubmitButton(isValid) {
        const button = document.getElementById('submitButton');
        if (!button) return;

        if (isValid) {
            button.disabled = false;
            button.innerHTML = '<i class="fas fa-save me-2"></i>Создать мероприятие';
            button.className = 'btn btn-primary';
        } else {
            button.disabled = true;
            button.innerHTML = '<i class="fas fa-exclamation-triangle me-2"></i>Исправьте ошибки в форме';
            button.className = 'btn btn-warning';
        }
    }

    showNotification(message, type = 'info') {
        console.log(`[${type.toUpperCase()}] ${message}`);

        // Простое уведомление через alert для начала
        // Можно заменить на более красивые уведомления
        if (type === 'warning' || type === 'danger') {
            alert(message);
        }
    }

    // ==========================================
    // ДЕБАГ МЕТОДЫ
    // ==========================================

    debugDatePickers() {
        console.log('=== DATE PICKERS DEBUG ===');
        console.log('Start picker exists:', !!this.startDatePicker);
        console.log('End picker exists:', !!this.endDatePicker);

        if (this.startDatePicker) {
            console.log('Start picker selected dates:', this.startDatePicker.selectedDates);
            console.log('Start picker input value:', this.startDatePicker.input.value);
        }

        if (this.endDatePicker) {
            console.log('End picker selected dates:', this.endDatePicker.selectedDates);
            console.log('End picker input value:', this.endDatePicker.input.value);
        }

        console.log('Current validation result:', this.validateDates());
        console.log('=== END DEBUG ===');
    }

    // ==========================================
    // ТЕСТОВЫЕ МЕТОДЫ
    // ==========================================

    testDatePickers() {
        console.log('=== TESTING DATE PICKERS ===');
        console.log('Start picker:', this.startDatePicker);
        console.log('End picker:', this.endDatePicker);

        if (this.startDatePicker) {
            console.log('Start picker input:', this.startDatePicker.input);
            console.log('Start picker config:', this.startDatePicker.config);
        }

        // Тест установки даты
        const testDate = new Date();
        testDate.setHours(testDate.getHours() + 2);

        console.log('Setting test date:', testDate);
        if (this.startDatePicker) {
            this.startDatePicker.setDate(testDate);
        }
    }

    // ==========================================
    // ОЧИСТКА
    // ==========================================

    destroy() {
        console.log('Destroying EventCreateManager...');

        // Очищаем таймауты
        if (this.validationTimeout) {
            clearTimeout(this.validationTimeout);
        }

        // Уничтожаем date pickers
        if (this.startDatePicker) {
            this.startDatePicker.destroy();
        }
        if (this.endDatePicker) {
            this.endDatePicker.destroy();
        }

        // Уничтожаем cropper
        if (this.cropper) {
            this.cropper.destroy();
        }

        // Уничтожаем Froala редакторы
        Object.values(this.froalaInstances).forEach(editor => {
            if (editor && typeof editor.destroy === 'function') {
                editor.destroy();
            }
        });

        console.log('EventCreateManager destroyed');
    }
}

// ==========================================
// ИНИЦИАЛИЗАЦИЯ
// ==========================================

let eventCreateManager;

function initEventCreate() {
    console.log('=== STARTING EVENT CREATE INITIALIZATION ===');

    try {
        eventCreateManager = new EventCreateManager();
        window.eventCreateManager = eventCreateManager;

        // Добавляем тестовую функцию
        window.testEventManager = () => {
            console.log('=== EVENT MANAGER TEST ===');
            eventCreateManager.testDatePickers();

            // Дополнительные тесты
            console.log('Form element:', document.getElementById('eventForm'));
            console.log('Date inputs:', {
                start: document.getElementById('startDatePicker'),
                end: document.getElementById('endDatePicker')
            });
        };

        // Добавляем функцию отладки
        window.debugEventManager = () => {
            eventCreateManager.debugDatePickers();
        };

        // Добавляем функцию принудительной валидации
        window.validateEventForm = () => {
            const result = eventCreateManager.validateForm();
            console.log('Manual validation result:', result);
            return result;
        };

        console.log('EventCreateManager initialized successfully!');
        console.log('Available test functions:');
        console.log('- window.testEventManager() - basic tests');
        console.log('- window.debugEventManager() - debug info');
        console.log('- window.validateEventForm() - manual validation');

        // Автотест через 3 секунды
        setTimeout(() => {
            console.log('Running auto-test...');
            if (window.testEventManager) {
                window.testEventManager();
            }
        }, 3000);

    } catch (error) {
        console.error('Failed to initialize EventCreateManager:', error);
    }
}

// Инициализация при загрузке
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
        setTimeout(initEventCreate, 300);
    });
} else {
    setTimeout(initEventCreate, 300);
}

// Очистка при выгрузке
window.addEventListener('beforeunload', () => {
    if (eventCreateManager) {
        eventCreateManager.destroy();
    }
});