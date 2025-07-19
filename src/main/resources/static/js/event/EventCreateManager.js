class EventCreateManager extends BaseContentCreateManager {
    constructor() {
        super({
            formId: 'eventForm',
            contentType: 'event',
            imageCropOptions: {
                inputId: 'mainImageInput'
            },
            validationOptions: {
                titleFieldSelector: 'input[name*="eventTranslations"][name*="title"]'
            }
        });

        this.initEventSpecificFeatures();
    }

    initEventSpecificFeatures() {
        this.initLocationToggle();
        this.initFlatpickrDates();
        this.initOrganizationSelection();
    }

    // ==========================================
    // УПРАВЛЕНИЕ ПОЛЯМИ ЛОКАЦИИ
    // ==========================================

    initLocationToggle() {
        const offlineRadio = document.getElementById('offlineEvent');
        const onlineRadio = document.getElementById('onlineEvent');
        const addressField = document.getElementById('addressField');
        const urlField = document.getElementById('urlField');

        if (!offlineRadio || !onlineRadio) {
            console.warn('Radio buttons for event type not found');
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

        this.updateLocationFields();

        this.enhanceRadioButtons();
    }

    enhanceRadioButtons() {
        const radioButtons = document.querySelectorAll('input[name*="isOffline"]');
        const labels = document.querySelectorAll('.form-check-label');

        radioButtons.forEach((radio, index) => {
            radio.addEventListener('change', () => {
                labels.forEach(label => label.classList.remove('selected'));
                if (radio.checked) {
                    labels[index].classList.add('selected');
                }
            });
        });

        const style = document.createElement('style');
        style.textContent = `
            .form-check-label.selected {
                background: linear-gradient(135deg, #2563eb, #3b82f6);
                color: white;
                padding: 0.5rem 1rem;
                border-radius: 6px;
                transition: all 0.3s ease;
                transform: scale(1.02);
            }
            .form-check-label {
                transition: all 0.3s ease;
                padding: 0.5rem 1rem;
                border-radius: 6px;
                cursor: pointer;
            }
            .form-check-label:hover {
                background: #f1f5f9;
            }
        `;
        document.head.appendChild(style);
    }

    showAddressField() {
        const addressField = document.getElementById('addressField');
        const addressInput = addressField.querySelector('input[name*="address"]');

        addressField.style.display = 'block';
        setTimeout(() => {
            addressField.classList.add('show');
        }, 10);

        if (addressInput) {
            addressInput.setAttribute('required', 'required');
        }
    }

    hideAddressField() {
        const addressField = document.getElementById('addressField');
        const addressInput = addressField.querySelector('input[name*="address"]');

        addressField.classList.remove('show');
        setTimeout(() => {
            addressField.style.display = 'none';
        }, 300);

        if (addressInput) {
            addressInput.removeAttribute('required');
            addressInput.value = '';
        }
    }

    showUrlField() {
        const urlField = document.getElementById('urlField');
        const urlInput = urlField.querySelector('input[name*="url"]');

        urlField.style.display = 'block';
        setTimeout(() => {
            urlField.classList.add('show');
        }, 10);

        if (urlInput) {
            urlInput.setAttribute('required', 'required');
        }
    }

    hideUrlField() {
        const urlField = document.getElementById('urlField');
        const urlInput = urlField.querySelector('input[name*="url"]');

        urlField.classList.remove('show');
        setTimeout(() => {
            urlField.style.display = 'none';
        }, 300);

        if (urlInput) {
            urlInput.removeAttribute('required');
            urlInput.value = '';
        }
    }

    updateLocationFields() {
        const offlineRadio = document.getElementById('offlineEvent');
        const onlineRadio = document.getElementById('onlineEvent');

        if (offlineRadio && offlineRadio.checked) {
            this.showAddressField();
            this.hideUrlField();
        } else if (onlineRadio && onlineRadio.checked) {
            this.showUrlField();
            this.hideAddressField();
        } else {
            this.hideAddressField();
            this.hideUrlField();
        }
    }

    // ==========================================
    // ИНИЦИАЛИЗАЦИЯ FLATPICKR ДЛЯ ДАТ
    // ==========================================

    initFlatpickrDates() {
        // Настройки по умолчанию для всех дат
        const defaultConfig = {
            enableTime: true,
            dateFormat: "Y-m-d H:i",
            time_24hr: true,
            locale: "ru",
            theme: "material_blue",
            allowInput: false,
            clickOpens: true,
            disableMobile: true,
            animate: true,
            minuteIncrement: 15,
            minDate: new Date(Date.now() + 60 * 60 * 1000),
        };

        this.startDatePicker = flatpickr("#startDatePicker", {
            ...defaultConfig,
            placeholder: "Выберите дату и время начала",
            onChange: (selectedDates, dateStr) => {
                this.onStartDateChange(selectedDates[0], dateStr);
            },
            onReady: (selectedDates, dateStr, instance) => {
                this.stylePickerIcon(instance);
            }
        });

        this.endDatePicker = flatpickr("#endDatePicker", {
            ...defaultConfig,
            placeholder: "Выберите дату и время окончания",
            onChange: (selectedDates, dateStr) => {
                this.onEndDateChange(selectedDates[0], dateStr);
            },
            onReady: (selectedDates, dateStr, instance) => {
                this.stylePickerIcon(instance);
            }
        });

        console.log('Flatpickr date pickers initialized');
    }

    stylePickerIcon(instance) {
        const input = instance.input;
        const icon = input.parentNode.querySelector('.datetime-icon');

        if (icon) {
            input.addEventListener('focus', () => {
                icon.style.transform = 'translateY(-50%) scale(1.1)';
                icon.style.color = '#2563eb';
            });

            input.addEventListener('blur', () => {
                icon.style.transform = 'translateY(-50%) scale(1)';
                icon.style.color = '#6b7280';
            });
        }
    }

    onStartDateChange(selectedDate, dateStr) {
        if (selectedDate) {
            const minEndDate = new Date(selectedDate.getTime() + 30 * 60 * 1000);
            this.endDatePicker.set('minDate', minEndDate);

            const endDate = this.endDatePicker.selectedDates[0];
            if (endDate && endDate <= selectedDate) {
                this.endDatePicker.clear();
                this.showNotification('Дата окончания была сброшена, так как она была раньше даты начала', 'info');
            }

            this.validateEventDates();
        }
    }

    onEndDateChange(selectedDate, dateStr) {
        if (selectedDate) {
            this.validateEventDates();
        }
    }

    // ==========================================
    // ВАЛИДАЦИЯ ДАТ (ОБНОВЛЕННАЯ)
    // ==========================================

    validateEventDates() {
        const startDate = this.startDatePicker.selectedDates[0];
        const endDate = this.endDatePicker.selectedDates[0];
        const now = new Date();

        let isValid = true;

        if (!startDate) {
            this.showFieldError(this.startDatePicker.input, 'Дата начала мероприятия обязательна');
            isValid = false;
        } else if (startDate <= now) {
            this.showFieldError(this.startDatePicker.input, 'Дата начала должна быть в будущем');
            isValid = false;
        } else {
            this.clearFieldError(this.startDatePicker.input);
        }

        if (endDate) {
            if (!startDate) {
                this.showFieldError(this.endDatePicker.input, 'Сначала выберите дату начала');
                isValid = false;
            } else if (endDate <= startDate) {
                this.showFieldError(this.endDatePicker.input, 'Дата окончания должна быть позже даты начала');
                isValid = false;
            } else {
                this.clearFieldError(this.endDatePicker.input);
            }
        } else {
            this.clearFieldError(this.endDatePicker.input);
        }

        return isValid;
    }

    // ==========================================
    // УТИЛИТЫ ДЛЯ РАБОТЫ С ДАТАМИ
    // ==========================================

    getSelectedStartDate() {
        return this.startDatePicker.selectedDates[0] || null;
    }

    getSelectedEndDate() {
        return this.endDatePicker.selectedDates[0] || null;
    }

    setStartDate(date) {
        if (date) {
            this.startDatePicker.setDate(date);
        }
    }

    setEndDate(date) {
        if (date) {
            this.endDatePicker.setDate(date);
        }
    }

    clearDates() {
        this.startDatePicker.clear();
        this.endDatePicker.clear();
    }

    showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `alert alert-${type} alert-dismissible fade show notification-custom`;
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1070;
            min-width: 350px;
            max-width: 400px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
            border: none;
            border-radius: 8px;
            animation: slideInRight 0.3s ease;
        `;

        const iconMap = {
            'success': 'fas fa-check-circle',
            'warning': 'fas fa-exclamation-triangle',
            'danger': 'fas fa-times-circle',
            'info': 'fas fa-info-circle'
        };

        notification.innerHTML = `
            <div class="d-flex align-items-center">
                <i class="${iconMap[type] || iconMap.info} me-2"></i>
                <span>${message}</span>
            </div>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        document.body.appendChild(notification);

        setTimeout(() => {
            if (notification.parentNode) {
                notification.style.animation = 'slideOutRight 0.3s ease';
                setTimeout(() => notification.remove(), 300);
            }
        }, 4000);
    }

    showFieldError(input, message) {
        input.classList.add('is-invalid');

        const existingError = input.parentNode.querySelector('.invalid-feedback.custom-error');
        if (existingError) {
            existingError.remove();
        }

        const errorDiv = document.createElement('div');
        errorDiv.className = 'invalid-feedback custom-error';
        errorDiv.textContent = message;
        input.parentNode.appendChild(errorDiv);
    }

    clearFieldError(input) {
        input.classList.remove('is-invalid');
        const errorDiv = input.parentNode.querySelector('.invalid-feedback.custom-error');
        if (errorDiv) {
            errorDiv.remove();
        }
    }

    // ==========================================
    // ВАЛИДАЦИЯ ДОПОЛНИТЕЛЬНЫХ ПОЛЕЙ
    // ==========================================

    validateAdditionalFields() {
        return this.validateEventDatesForSubmit() &&
            this.validateEventLocation() &&
            this.validateEventType();
    }

    validateEventDatesForSubmit() {
        const startDate = this.getSelectedStartDate();

        if (!startDate) {
            this.showAlert('Дата начала мероприятия обязательна', 'warning');
            this.startDatePicker.open();
            return false;
        }

        return this.validateEventDates();
    }

    validateEventLocation() {
        const offlineRadio = document.getElementById('offlineEvent');
        const onlineRadio = document.getElementById('onlineEvent');

        if (!offlineRadio?.checked && !onlineRadio?.checked) {
            this.showAlert('Необходимо указать тип мероприятия (онлайн/офлайн)', 'warning');
            return false;
        }

        if (offlineRadio?.checked) {
            const addressInput = document.querySelector('input[name*="address"]');
            if (!addressInput || !addressInput.value.trim()) {
                this.showAlert('Для офлайн мероприятия необходимо указать адрес', 'warning');
                addressInput?.focus();
                return false;
            }
        }

        if (onlineRadio?.checked) {
            const urlInput = document.querySelector('input[name*="url"]');
            if (!urlInput || !urlInput.value.trim()) {
                this.showAlert('Для онлайн мероприятия необходимо указать ссылку', 'warning');
                urlInput?.focus();
                return false;
            }

            try {
                new URL(urlInput.value);
            } catch {
                this.showAlert('Указанная ссылка имеет неверный формат', 'warning');
                urlInput?.focus();
                return false;
            }
        }

        return true;
    }

    validateEventType() {
        const typeSelect = document.querySelector('select[name*="typeId"]');

        if (!typeSelect || !typeSelect.value) {
            this.showAlert('Необходимо выбрать тип мероприятия', 'warning');
            typeSelect?.focus();
            return false;
        }

        return true;
    }

    // ==========================================
    // ОРГАНИЗАЦИИ
    // ==========================================

    initOrganizationSelection() {
        const organizationSelect = document.querySelector('select[name="organizationIds"]');

        if (organizationSelect) {
            const helpText = document.createElement('small');
            helpText.className = 'form-text text-muted';
            helpText.innerHTML = `
                <i class="fas fa-info-circle me-1"></i>
                Выбрано организаций: <span id="orgCount">0</span>
            `;
            organizationSelect.parentNode.appendChild(helpText);

            organizationSelect.addEventListener('change', () => {
                const selected = Array.from(organizationSelect.selectedOptions).length;
                document.getElementById('orgCount').textContent = selected;
            });

            const initialCount = Array.from(organizationSelect.selectedOptions).length;
            document.getElementById('orgCount').textContent = initialCount;
        }
    }

    // ==========================================
    // ОБРАБОТКА ОТПРАВКИ ФОРМЫ
    // ==========================================

    onFormSubmit(event) {
        console.log('Submitting event form...');

        this.prepareEventData();

        return super.onFormSubmit(event);
    }

    prepareEventData() {
        console.log('Preparing event data for submission');

        this.syncTinyMCEWithForm();

        this.syncFlatpickrDates();

        this.validateAttachments();
    }

    syncFlatpickrDates() {
        const startDate = this.getSelectedStartDate();
        const endDate = this.getSelectedEndDate();

        if (startDate) {
            const startInput = document.querySelector('input[name*="startDate"]');
            if (startInput) {
                startInput.value = this.formatDateForServer(startDate);
            }
        }

        if (endDate) {
            const endInput = document.querySelector('input[name*="endDate"]');
            if (endInput) {
                endInput.value = this.formatDateForServer(endDate);
            }
        }
    }

    formatDateForServer(date) {
        return date.toISOString().slice(0, 19);
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

    validateAttachments() {
        const attachmentsInput = document.querySelector('input[name="attachments"]');
        if (attachmentsInput && attachmentsInput.files.length > 0) {
            const maxSize = 10 * 1024 * 1024;

            for (let file of attachmentsInput.files) {
                if (file.size > maxSize) {
                    this.showAlert(`Файл "${file.name}" превышает максимальный размер 10MB`, 'warning');
                    return false;
                }
            }
        }
        return true;
    }

    // ==========================================
    // ПУБЛИЧНЫЕ МЕТОДЫ
    // ==========================================

    getEventData() {
        const formData = new FormData(document.getElementById(this.options.formId));
        const eventData = {};

        for (let [key, value] of formData.entries()) {
            eventData[key] = value;
        }

        eventData.startDate = this.getSelectedStartDate();
        eventData.endDate = this.getSelectedEndDate();

        return eventData;
    }

    setEventType(isOffline) {
        const offlineRadio = document.getElementById('offlineEvent');
        const onlineRadio = document.getElementById('onlineEvent');

        if (isOffline) {
            offlineRadio.checked = true;
            this.showAddressField();
            this.hideUrlField();
        } else {
            onlineRadio.checked = true;
            this.showUrlField();
            this.hideAddressField();
        }
    }

    setEventDates(startDate, endDate = null) {
        if (startDate) {
            this.setStartDate(new Date(startDate));
        }
        if (endDate) {
            this.setEndDate(new Date(endDate));
        }
    }

    getEventDuration() {
        const startDate = this.getSelectedStartDate();
        const endDate = this.getSelectedEndDate();

        if (startDate && endDate) {
            return Math.round((endDate - startDate) / (1000 * 60 * 60)); // в часах
        }
        return null;
    }

    isMultiDayEvent() {
        const startDate = this.getSelectedStartDate();
        const endDate = this.getSelectedEndDate();

        if (startDate && endDate) {
            return startDate.toDateString() !== endDate.toDateString();
        }
        return false;
    }

    populateForm(eventData) {
        if (eventData.startDate) {
            this.setStartDate(new Date(eventData.startDate));
        }
        if (eventData.endDate) {
            this.setEndDate(new Date(eventData.endDate));
        }

        if (eventData.isOffline !== undefined) {
            this.setEventType(eventData.isOffline);
        }

        if (eventData.translations) {
            eventData.translations.forEach(translation => {
                this.setContentForLanguage(
                    translation.languageCode,
                    translation.title,
                    translation.content
                );
            });
        }

        console.log('Form populated with event data:', eventData);
    }

    onValidationChange(isValid) {
        super.onValidationChange(isValid);

        const submitButton = document.getElementById('submitButton');
        if (submitButton) {
            if (isValid) {
                submitButton.innerHTML = '<i class="fas fa-save me-2"></i>Создать мероприятие';
            } else {
                submitButton.innerHTML = '<i class="fas fa-exclamation-triangle me-2"></i>Заполните форму';
            }
        }
    }

    destroy() {
        super.destroy();
        if (this.startDatePicker) {
            this.startDatePicker.destroy();
        }
        if (this.endDatePicker) {
            this.endDatePicker.destroy();
        }

        console.log('EventCreateManager destroyed');
    }
}

// ==========================================
// ИНИЦИАЛИЗАЦИЯ
// ==========================================

let eventCreateManager;

function initEventCreate() {
    eventCreateManager = new EventCreateManager();
    window.eventCreateManager = eventCreateManager;

    console.log('EventCreateManager initialized successfully');
}

document.addEventListener('DOMContentLoaded', () => {
    initEventCreate();
});

window.addEventListener('beforeunload', () => {
    if (eventCreateManager) {
        eventCreateManager.destroy();
    }
});