class BookCreateManager extends BaseContentCreateManager {
    initComponents() {
        this.imageCropManager = new ImageCropManager({
            onCropComplete: (blob, dataURL) => this.onImageCropped(blob, dataURL),
            onError: (message) => this.showAlert(message, 'danger'),
            aspectRatio: 2 / 3,
            ...this.options.imageCropOptions
        });

        this.tinyMCEManager = null;
        this.languageTabsManager = null;
        this.validationManager = null;
    }

    onFormSubmit(event) {
        this.syncImageCrop();
        return true;
    }

}