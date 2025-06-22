class NewsCreateManager extends BaseContentCreateManager {
    constructor() {
        super({
            formId: 'newsForm',
            contentType: 'news',
            imageCropOptions: {
                inputId: 'image'
            },
            validationOptions: {
                titleFieldSelector: 'input[name*="newsTranslations"][name*="title"]'
            }
        });
    }

    validateAdditionalFields() {
        return true;
    }

    onFormSubmit(event) {
        this.handleAttachments();
        return super.onFormSubmit(event);
    }

    handleAttachments() {
        const attachmentsInput = document.querySelector('input[name="attachments"]');
        if (attachmentsInput && attachmentsInput.files.length > 0) {
            console.log(`Uploading ${attachmentsInput.files.length} attachments for news`);
        }
    }
}
