<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring/>

<@main.layout>
    <div class="container my-5">
        <div class="card shadow-sm">
            <div class="card-header">
                <h3 class="mb-0">Create New Book</h3>
            </div>
            <div class="card-body">
                <form id="bookForm" action="/books/update/${book.id}" method="post" enctype="multipart/form-data">
                    <@main.csrf/>
                    <input type="hidden" name="id" value="${book.id}"/>

                    <div class="mb-4">
                        <label class="form-label fw-bold">Главное изображение <span class="text-danger">*</span></label>
                        <#-- Show existing main image if it exists -->
                        <#if book.mainImage??>
                            <div class="existing-image-section mb-3 p-3 bg-light rounded">
                                <h6 class="mb-2">🖼️ Current Image:</h6>
                                <div class="existing-image-container">
                                    <img src="/api/files/${book.mainImage.id}/view"
                                         class="img-fluid border rounded"
                                         alt="Current book cover"
                                         style="max-width: 300px; max-height: 200px; object-fit: cover;">
                                </div>
                                <small class="text-muted d-block mt-2">
                                    Upload a new image to replace this one
                                </small>
                            </div>
                        </#if>
                        <div class="image-upload-container">
                            <input type="file" id="mainImageInput" name="mpMainImage" accept="image/*" class="form-control mb-3">
                            <div class="image-preview-container" style="display: none;">
                                <div class="crop-container">
                                    <img id="imagePreview" class="img-fluid">
                                </div>
                                <div class="crop-controls mt-3">
                                    <button type="button" class="btn btn-primary" id="cropButton">Применить кроп</button>
                                    <button type="button" class="btn btn-secondary" id="cancelCrop">Отменить</button>
                                </div>
                            </div>
                            <div class="cropped-preview" style="display: none;">
                                <label class="form-label">Предпросмотр (1200x400):</label>
                                <div class="cropped-image-container">
                                    <img id="croppedImage" class="img-fluid border rounded">
                                </div>
                                <button type="button" class="btn btn-outline-secondary btn-sm mt-2" id="changeCrop">Изменить кроп</button>
                            </div>
                        </div>
                        <small class="form-text text-muted">
                            Изображение будет обрезано до размера 1200x400 пикселей (соотношение 3:1)
                        </small>
                    </div>

                    <div class="mb-3">
                        <label for="name" class="form-label">Book Title</label>
                        <@spring.formInput "book.name" 'class="form-control" id="name" placeholder="Enter book title"'/>
                        <@spring.showErrors '<br>',"text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="authors" class="form-label">Author(s)</label>
                        <@spring.formInput "book.authors" 'class="form-control" id="authors" placeholder="Enter author(s)"'/>
                        <@spring.showErrors '<br>',"text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="isbn" class="form-label">ISBN</label>
                        <@spring.formInput "book.isbn" 'class="form-control" id="isbn" placeholder="e.g. 978-3-16-148410-0"'/>
                        <@spring.showErrors '<br>',"text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <@spring.formTextarea "book.description" 'class="form-control" id="description" rows="4"'/>
                        <@spring.showErrors '<br>',"text-danger small"/>
                    </div>




                    <div class="mb-3">
                        <label for="attachments" class="form-label">Attachments</label>

                        <#-- Show existing file if it exists -->
                        <#if existingFile??>
                            <div class="existing-file-section mb-3 p-3 bg-light rounded">
                                <h6 class="mb-2">📎 Current File:</h6>
                                <div class="d-flex align-items-center justify-content-between">
                                    <div class="file-info">
                                        <strong>${existingFile.filename}</strong>
                                        <br>
                                        <small class="text-muted">
                                            Size: ${(existingFile.size / 1024 / 1024)?string("0.##")} MB
                                            <#if existingFile.type??>
                                                | Type: ${existingFile.type.mimeType}
                                            </#if>
                                        </small>
                                    </div>
                                    <div class="file-actions d-flex gap-2 align-items-center">
                                        <#if existingFile.type?? && existingFile.type.mimeType == "application/pdf">
                                            <a href="/books/${existingFile.id}/pdf-reader"
                                               class="btn btn-sm btn-outline-primary w-100 text-center"
                                               target="_blank"
                                               style="max-width: 130px; max-height: 58px;">
                                                 View File
                                            </a>
                                        </#if>
                                        <a href="/api/files/${existingFile.id}/download"
                                           class="btn btn-sm btn-outline-secondary w-100 text-center"
                                           style="max-width: 130px; max-height: 58px;">
                                             Download File
                                        </a>
                                    </div>




                                </div>
                            </div>
                        </#if>

                        <input type="file" class="form-control" id="attachments" name="attachments"
                               accept=".pdf" <#if !existingFile??>required</#if>>

                        <div class="form-text">
                            <#if existingFile??>
                                <span class="text-info">
                💡 Leave empty to keep current file, or select a new file to replace it
            </span>
                            <#else>
                                <span class="text-danger">
                📎 Supported files: PDF only
            </span>
                            </#if>
                        </div>
                    </div>

                    <div class="mb-3">
                        <#assign categoryOptions = {}>
                        <#list categories as category>
                            <#assign categoryOptions = categoryOptions + {category.id?string: category.translations[0].translation}>
                        </#list>

                        <label for="category" class="form-label">Category</label>
                        <@spring.formSingleSelect
                        "book.category.id",
                        categoryOptions,
                        'class="form-select" id="category"'/>
                        <@spring.showErrors '<br>',"text-danger small"/>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                        </div>
                        <div class="d-flex gap-2">
                            <a href="/books" class="btn btn-secondary">
                                <i class="fas fa-times me-2"></i>Отменить
                            </a>
                            <button type="submit" class="btn btn-primary" id="submitButton">
                                <i class="fas fa-save me-2"></i>Создать книгу
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.tiny.cloud/1/7v0gkd2f6uam1hogetosjlogqkf8vz98nsxikg6vsrntra2k/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.js"></script>

    <script src="/static/js/components/content-create/ImageCropManager.js"></script>
    <script src="/static/js/components/content-create/TinyMCEManager.js"></script>
    <script src="/static/js/components/content-create/LanguageTabsManager.js"></script>
    <script src="/static/js/components/content-create/TranslationValidationManager.js"></script>
    <script src="/static/js/components/content-create/BaseContentCreateManager.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

    <style>
        .image-upload-container {
            border: 2px dashed #e2e8f0;
            border-radius: 8px;
            padding: 1.5rem;
            transition: border-color 0.3s ease;
        }

        .image-upload-container:hover {
            border-color: #2563eb;
        }

        .crop-container {
            max-width: 100%;
            max-height: 400px;
            overflow: hidden;
        }

        .cropped-image-container {
            width: 100%;
            max-width: 600px;
            aspect-ratio: 3/1;
            overflow: hidden;
            border-radius: 8px;
        }

        .cropped-image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .translation-indicator .fa-circle.text-success {
            color: #10b981 !important;
        }

        .translation-indicator .fa-circle.text-warning {
            color: #f59e0b !important;
        }

        .translation-indicator .fa-circle.text-danger {
            color: #ef4444 !important;
        }

        .tox-tinymce {
            border-radius: 8px !important;
        }
        /* Override for book cover preview (2:3 ratio) */
        .cropped-preview .cropped-image-container {
            aspect-ratio: 2/3 !important;  /* Forces 2:3 ratio (height:width) */
            max-width: 400px;              /* Adjust if needed (600px for 3:2) */
        }

        .cropped-preview .cropped-image-container img {
            object-fit: cover;             /* Ensures image fills container */
            width: 100%;
            height: 100%;
        }
        .btn-sm.flex-fill {
            min-width: 130px;
            text-align: center;
        }

    </style>

    <script src="/static/js/book/BookCreateManager.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            window.bookCreateManager = new BookCreateManager();
        });
    </script>


</@main.layout>