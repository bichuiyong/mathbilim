class ImageCropManager {
    constructor(options = {}) {
        this.options = {
            inputId: 'mainImageInput',
            previewId: 'imagePreview',
            previewContainerSelector: '.image-preview-container',
            croppedPreviewSelector: '.cropped-preview',
            cropButtonId: 'cropButton',
            cancelCropId: 'cancelCrop',
            changeCropId: 'changeCrop',
            croppedImageId: 'croppedImage',
            aspectRatio: 3, // 3:1 для всех типов контента
            maxFileSize: 5 * 1024 * 1024,
            outputWidth: 1200,
            outputHeight: 400,
            outputQuality: 0.9,
            ...options
        };

        this.cropper = null;
        this.croppedImageData = null;
        this.onCropComplete = options.onCropComplete || (() => {});
        this.onError = options.onError || ((message) => console.error(message));

        this.init();
    }

    init() {
        this.bindEvents();
    }

    bindEvents() {
        const imageInput = document.getElementById(this.options.inputId);
        const cropButton = document.getElementById(this.options.cropButtonId);
        const cancelCrop = document.getElementById(this.options.cancelCropId);
        const changeCrop = document.getElementById(this.options.changeCropId);

        if (!imageInput) {
            console.warn(`ImageCropManager: Input element with id '${this.options.inputId}' not found`);
            return;
        }

        imageInput.addEventListener('change', (e) => {
            const file = e.target.files[0];
            if (file) {
                this.loadImageForCrop(file);
            }
        });

        cropButton?.addEventListener('click', () => this.applyCrop());
        cancelCrop?.addEventListener('click', () => this.cancelCrop());
        changeCrop?.addEventListener('click', () => this.changeCrop());
    }

    loadImageForCrop(file) {
        if (file.size > this.options.maxFileSize) {
            this.onError(`Размер изображения не должен превышать ${this.formatFileSize(this.options.maxFileSize)}`);
            return;
        }

        if (!file.type.startsWith('image/')) {
            this.onError('Пожалуйста, выберите изображение');
            return;
        }

        const reader = new FileReader();
        reader.onload = (e) => {
            const imagePreview = document.getElementById(this.options.previewId);
            const previewContainer = document.querySelector(this.options.previewContainerSelector);

            if (!imagePreview || !previewContainer) {
                this.onError('Элементы для предпросмотра не найдены');
                return;
            }

            imagePreview.src = e.target.result;
            previewContainer.style.display = 'block';

            if (this.cropper) {
                this.cropper.destroy();
            }

            this.cropper = new Cropper(imagePreview, {
                aspectRatio: this.options.aspectRatio,
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

    applyCrop() {
        if (!this.cropper) return;

        const canvas = this.cropper.getCroppedCanvas({
            width: this.options.outputWidth,
            height: this.options.outputHeight,
            imageSmoothingQuality: 'high'
        });

        canvas.toBlob((blob) => {
            this.croppedImageData = blob;

            const croppedImage = document.getElementById(this.options.croppedImageId);
            const previewContainer = document.querySelector(this.options.previewContainerSelector);
            const croppedPreview = document.querySelector(this.options.croppedPreviewSelector);

            if (croppedImage) {
                croppedImage.src = canvas.toDataURL();
            }

            if (previewContainer) previewContainer.style.display = 'none';
            if (croppedPreview) croppedPreview.style.display = 'block';

            this.replaceImageFile();

            // Вызываем callback
            this.onCropComplete(blob, canvas.toDataURL());

        }, 'image/jpeg', this.options.outputQuality);
    }

    cancelCrop() {
        if (this.cropper) {
            this.cropper.destroy();
            this.cropper = null;
        }

        const previewContainer = document.querySelector(this.options.previewContainerSelector);
        const imageInput = document.getElementById(this.options.inputId);

        if (previewContainer) previewContainer.style.display = 'none';
        if (imageInput) imageInput.value = '';

        this.croppedImageData = null;
    }

    changeCrop() {
        const previewContainer = document.querySelector(this.options.previewContainerSelector);
        const croppedPreview = document.querySelector(this.options.croppedPreviewSelector);

        if (croppedPreview) croppedPreview.style.display = 'none';
        if (previewContainer) previewContainer.style.display = 'block';

        this.croppedImageData = null;
    }

    replaceImageFile() {
        if (!this.croppedImageData) return;

        const input = document.getElementById(this.options.inputId);
        if (!input) return;

        const dataTransfer = new DataTransfer();
        const file = new File([this.croppedImageData], 'cropped-image.jpg', {
            type: 'image/jpeg'
        });

        dataTransfer.items.add(file);
        input.files = dataTransfer.files;
    }

    formatFileSize(bytes) {
        const units = ['B', 'KB', 'MB', 'GB'];
        let size = bytes;
        let unitIndex = 0;

        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        return `${Math.round(size)}${units[unitIndex]}`;
    }

    getCroppedImageData() {
        return this.croppedImageData;
    }

    hasCroppedImage() {
        return !!this.croppedImageData;
    }

    destroy() {
        if (this.cropper) {
            this.cropper.destroy();
            this.cropper = null;
        }
        this.croppedImageData = null;
    }
}