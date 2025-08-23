<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout title="Создать пост">

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card">
                    <#assign currentLocale = springMacroRequestContext.locale.language>
                    <small class="text-danger">
                        ${imageError!""}
                    </small>
                    <div class="card-header">
                        <h2 class="mb-0">Создание поста</h2>
                    </div>
                    <div class="card-body">
                        <form id="postForm" action="/posts/create" method="post" enctype="multipart/form-data">
                            <@main.csrf/>
                            <#if error??>
                                <div class="alert alert-danger">
                                    ${error}
                                </div>
                            </#if>

                            <div class="mb-4">
                                <label class="form-label fw-bold">Главное изображение</label>
                                <div class="image-upload-container">
                                    <input type="file" id="mainImageInput" name="image" accept="image/*"
                                           class="form-control mb-3">
                                    <div class="image-preview-container" style="display: none;">
                                        <div class="crop-container">
                                            <img id="imagePreview" class="img-fluid">
                                        </div>
                                        <div class="crop-controls mt-3">
                                            <button type="button" class="btn btn-primary" id="cropButton">Применить
                                                кроп
                                            </button>
                                            <button type="button" class="btn btn-secondary" id="cancelCrop">Отменить
                                            </button>
                                        </div>
                                    </div>
                                    <div class="cropped-preview" style="display: none;">
                                        <label class="form-label">Предпросмотр (1200x400):</label>
                                        <div class="cropped-image-container">
                                            <img id="croppedImage" class="img-fluid border rounded">
                                        </div>
                                        <button type="button" class="btn btn-outline-secondary btn-sm mt-2"
                                                id="changeCrop">Изменить кроп
                                        </button>
                                    </div>
                                </div>
                                <small class="form-text text-muted">
                                    Изображение будет обрезано до размера 1200x400 пикселей (соотношение 3:1)
                                </small>
                            </div>

                            <div class="card mb-4">
                                <div class="card-header">
                                    <ul class="nav nav-tabs card-header-tabs" id="languageTabs">
                                        <#list createPostDto.post.postTranslations?sort_by("languageCode") as trans>
                                            <li class="nav-item">
                                                <button class="nav-link <#if trans?is_first>active</#if>"
                                                        type="button"
                                                        data-bs-toggle="tab"
                                                        data-bs-target="#lang-${trans.languageCode}"
                                                        data-lang="${trans.languageCode}">
                                                    ${languages[trans.languageCode]}
                                                    <span class="translation-indicator ms-2"
                                                          id="indicator-${trans.languageCode}">
                                                    <i class="fas fa-circle text-danger"></i>
                                                </span>
                                                </button>
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                                <div class="card-body">
                                    <div class="tab-content">
                                        <@spring.bind "createPostDto.post.postTranslations"/>
                                        <#if spring.status.error>
                                            <div class="alert alert-warning mb-3">
                                                <#list spring.status.errorMessages as error>${error}</#list>
                                            </div>
                                        </#if>

                                        <#list createPostDto.post.postTranslations as trans>
                                            <div class="tab-pane fade <#if trans?is_first>show active</#if>"
                                                 id="lang-${trans.languageCode}">

                                                <div class="mb-4">
                                                    <@spring.bind "createPostDto.post.postTranslations[${trans?index}].title"/>
                                                    <label class="form-label fw-bold">Заголовок</label>
                                                    <input type="text"
                                                           class="form-control translation-field <#if spring.status.error>is-invalid</#if>"
                                                           name="post.postTranslations[${trans?index}].title"
                                                           data-lang="${trans.languageCode}"
                                                           value="${spring.status.value!''}"
                                                           placeholder="Введите заголовок поста...">
                                                    <#if spring.status.error>
                                                        <div class="invalid-feedback">
                                                            <#list spring.status.errorMessages as error>${error}</#list>
                                                        </div>
                                                    </#if>
                                                </div>

                                                <div class="mb-4">
                                                    <@spring.bind "createPostDto.post.postTranslations[${trans?index}].content"/>
                                                    <label class="form-label fw-bold">Содержание</label>
                                                    <textarea
                                                            class="form-control translation-field <#if spring.status.error>is-invalid</#if>"
                                                            name="post.postTranslations[${trans?index}].content"
                                                            data-lang="${trans.languageCode}"
                                                            id="content-${trans.languageCode}">${spring.status.value!''}</textarea>
                                                    <#if spring.status.error>
                                                        <div class="invalid-feedback">
                                                            <#list spring.status.errorMessages as error>${error}</#list>
                                                        </div>
                                                    </#if>
                                                </div>

                                                <input type="hidden"
                                                       name="post.postTranslations[${trans?index}].languageCode"
                                                       value="${trans.languageCode}">
                                            </div>
                                        </#list>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-4">
                                <@spring.bind "createPostDto.post.typeId"/>
                                <label class="form-label fw-bold">Тип поста</label>
                                <select class="form-select <#if spring.status.error>is-invalid</#if>"
                                        name="post.typeId">
                                    <option value="">-- Выберите тип --</option>
                                    <#list postTypes as type>
                                        <#assign translation = (type.translations?filter(t -> t.languageCode == currentLocale)?first)!>
                                        <option value="${type.id}"
                                                <#if spring.status.value?? && spring.status.value.id?? && spring.status.value.id == type.id>selected</#if>>
                                            ${translation?has_content?then(translation.translation, type.translations?first.translation)}
                                        </option>
                                    </#list>
                                </select>
                                <#if spring.status.error>
                                    <div class="invalid-feedback">
                                        <#list spring.status.errorMessages as error>${error}</#list>
                                    </div>
                                </#if>
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-bold">Вложения</label>
                                <input type="file" class="form-control" name="attachments" multiple>
                            </div>

                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                <span class="text-muted">
                                    <i class="fas fa-info-circle me-1"></i>
                                    Заполните хотя бы один перевод для публикации
                                </span>
                                </div>
                                <div class="d-flex gap-2">
                                    <a href="/posts" class="btn btn-secondary">
                                        <i class="fas fa-times me-2"></i>Отменить
                                    </a>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save me-2"></i>Создать пост
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.js"></script>

    <script src="/static/js/components/content-create/ImageCropManager.js"></script>
    <script src="/static/js/components/content-create/TinyMCEManager.js"></script>
    <script src="/static/js/components/content-create/LanguageTabsManager.js"></script>
    <script src="/static/js/components/content-create/TranslationValidationManager.js"></script>
    <script src="/static/js/components/content-create/BaseContentCreateManager.js"></script>

    <script src="/static/js/blog/BlogCreateManager.js"></script>

    <style>
        .image-upload-container {
            border: 2px dashed #e2e8f0;
            border-radius: 8px;
            padding: 1.5rem;
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

        .translation-indicator .fa-circle.text-danger {
            color: #ef4444 !important;
        }

        .tox-tinymce {
            border-radius: 8px !important;
        }
    </style>

</@main.layout>
