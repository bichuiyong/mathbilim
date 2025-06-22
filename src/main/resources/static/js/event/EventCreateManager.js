class EventCreateManager extends BaseContentCreateManager {
    constructor() {
        super({
            formId: 'eventForm',
            contentType: 'event',
            imageCropOptions: {
                inputId: 'image'
            },
            validationOptions: {
                titleFieldSelector: 'input[name*="eventTranslations"][name*="title"]'
            }
        });
    }

    validateAdditionalFields() {
        return this.validateEventDates() &&
               this.validateEventLocation() && 
               this.validateEventType();
    }

    validateEventDates() {
        const startDateInput = document.querySelector('input[name*="startDate"]');
        const endDateInput = document.querySelector('input[name*="endDate"]');

        if (!startDateInput || !startDateInput.value) {
            this.showAlert('Дата начала мероприятия обязательна', 'warning');
            return false;
        }

        const startDate = new Date(startDateInput.value);
        const now = new Date();

        if (startDate <= now) {
            this.showAlert('Дата начала мероприятия должна быть в будущем', 'warning');
            return false;
        }

        if (endDateInput && endDateInput.value) {
            const endDate = new Date(endDateInput.value);
            if (endDate <= startDate) {
                this.showAlert('Дата окончания должна быть позже даты начала', 'warning');
                return false;
            }
        }

        return true;
    }

    validateEventLocation() {
        const isOfflineInput = document.querySelector('input[name*="isOffline"]');
        const addressInput = document.querySelector('input[name*="address"]');
        const urlInput = document.querySelector('input[name*="url"]');

        if (!isOfflineInput) {
            this.showAlert('Необходимо указать тип мероприятия (онлайн/офлайн)', 'warning');
            return false;
        }

        const isOffline = isOfflineInput.checked || isOfflineInput.value === 'true';

        if (isOffline) {
            if (!addressInput || !addressInput.value.trim()) {
                this.showAlert('Для офлайн мероприятия необходимо указать адрес', 'warning');
                return false;
            }
        } else {
            if (!urlInput || !urlInput.value.trim()) {
                this.showAlert('Для онлайн мероприятия необходимо указать ссылку', 'warning');
                return false;
            }

            try {
                new URL(urlInput.value);
            } catch {
                this.showAlert('Указанная ссылка имеет неверный формат', 'warning');
                return false;
            }
        }

        return true;
    }

    validateEventType() {
        const typeInput = document.querySelector('select[name*="typeId"]');
        
        if (!typeInput || !typeInput.value) {
            this.showAlert('Необходимо выбрать тип мероприятия', 'warning');
            return false;
        }

        return true;
    }

    onFormSubmit(event) {
        this.handleEventAttachments();
        this.handleOrganizations();
        return super.onFormSubmit(event);
    }

    handleEventAttachments() {
        const attachmentsInput = document.querySelector('input[name="attachments"]');
        if (attachmentsInput && attachmentsInput.files.length > 0) {
            console.log(`Uploading ${attachmentsInput.files.length} attachments for event`);
        }
    }

    handleOrganizations() {
        const organizationInputs = document.querySelectorAll('input[name="organizationIds"]');
        if (organizationInputs.length > 0) {
            const selectedOrgs = Array.from(organizationInputs)
                .filter(input => input.checked)
                .map(input => input.value);
            console.log(`Selected organizations: ${selectedOrgs.join(', ')}`);
        }
    }
}
