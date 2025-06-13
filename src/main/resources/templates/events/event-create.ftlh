<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as layout>

<@layout.layout>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ru.js">

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">
                            <i class="fas fa-calendar-plus me-2"></i>Создать мероприятие
                        </h4>
                    </div>
                    <div class="card-body">
                        <form action="/events/create" method="post" enctype="multipart/form-data" novalidate>
                            <@layout.csrf />
                            <input type="hidden" name="user.id" value="${user.id}"/>

                            <!-- Основная информация -->
                            <div class="row">
                                <!-- Тип мероприятия -->
                                <select name="type.id" class="form-select" required>
                                    <option value="">Выберите тип</option>
                                    <#list eventsTypes as type>
                                        <option value="${type.id}">${type.eventTypeTranslations[0].translation}</option>
                                    </#list>
                                </select>

                                <!-- Формат мероприятия -->
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="isOffline" class="form-label">Формат мероприятия</label>
                                        <@spring.formSingleSelect "event.isOffline" {"true": "Офлайн", "false": "Онлайн"} 'class="form-select" id="isOffline"' />
                                    </div>
                                </div>
                            </div>

                            <!-- Даты проведения -->
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="startDate" class="form-label">Дата и время начала <span class="text-danger">*</span></label>
                                        <@spring.formInput "event.startDate" 'class="form-control" id="startDate" placeholder="Выберите дату и время начала"' "text" />
                                        <@spring.showErrors "<br>" "text-danger small" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="endDate" class="form-label">Дата и время окончания</label>
                                        <@spring.formInput "event.endDate" 'class="form-control" id="endDate" placeholder="Выберите дату и время окончания"' "text" />
                                        <@spring.showErrors "<br>" "text-danger small" />
                                        <div class="form-text">Оставьте пустым, если мероприятие в один день</div>
                                    </div>
                                </div>
                            </div>

                            <!-- Организации -->
                            <div class="mb-4">
                                <label for="organizations" class="form-label">Организации</label>
                                <div class="organizations-container">
                                    <#list organizations as org>
                                        <div class="organization-item">
                                            <input type="checkbox" name="organizationIds" value="${org.id}" id="org_${org.id}" class="organization-checkbox">
                                            <label for="org_${org.id}" class="organization-label">
                                                <span class="checkmark"></span>
                                                <span class="organization-name">${org.name}</span>
                                            </label>
                                        </div>
                                    </#list>
                                </div>
                                <div class="form-text">Выберите одну или несколько организаций</div>
                            </div>

                            <!-- Главное изображение -->
                            <div class="mb-4">
                                <label for="mainImage" class="form-label">Главное изображение</label>
                                <input type="file" name="mainImageMp" id="mainImage" class="form-control" accept="image/*">
                                <div class="form-text">Рекомендуемый размер: 1200x600 пикселей. Максимальный размер: 5 МБ</div>
                                <!-- Превью изображения -->
                                <div id="imagePreviewContainer" class="mt-3" style="display: none;">
                                    <div class="image-preview-wrapper">
                                        <img id="imagePreview" class="image-preview" alt="Превью изображения">
                                        <button type="button" id="removeImage" class="btn-remove-image" title="Удалить изображение">
                                            <i class="fas fa-times"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <!-- Языковые вкладки -->
                            <div class="mb-4">
                                <label class="form-label">Информация о мероприятии <span class="text-danger">*</span></label>

                                <!-- Навигация по языкам -->
                                <ul class="nav nav-tabs language-tabs" id="languageTabs" role="tablist">
                                    <#list languageEnum as lang>
                                        <li class="nav-item" role="presentation">
                                            <button class="nav-link ${lang?is_first?then('active', '')}"
                                                    id="tab-${lang.code}"
                                                    data-bs-toggle="tab"
                                                    data-bs-target="#content-${lang.code}"
                                                    type="button"
                                                    role="tab"
                                                    aria-controls="content-${lang.code}"
                                                    aria-selected="${lang?is_first?then('true', 'false')}">
                                                <i class="fas fa-globe me-1"></i>
                                                ${lang.displayName}
                                                <span class="language-indicator" id="indicator-${lang.code}"></span>
                                            </button>
                                        </li>
                                    </#list>
                                </ul>

                                <!-- Содержимое вкладок -->
                                <div class="tab-content language-content" id="languageTabsContent">
                                    <#assign translationIndex = 0>
                                    <#list languageEnum as lang>
                                        <div class="tab-pane fade ${lang?is_first?then('show active', '')}"
                                             id="content-${lang.code}"
                                             role="tabpanel"
                                             aria-labelledby="tab-${lang.code}">

                                            <div class="language-form-content">
                                                <!-- Скрытые поля для связи -->
                                                <@spring.formHiddenInput "event.eventTranslations[${translationIndex}].languageCode"/>

                                                <!-- Название мероприятия -->
                                                <div class="mb-3">
                                                    <label for="title-${lang.code}" class="form-label">
                                                        Название мероприятия (${lang.displayName}) <span class="text-danger">*</span>
                                                    </label>
                                                    <@spring.formInput "event.eventTranslations[${translationIndex}].title"
                                                    'class="form-control title-input" id="title-${lang.code}" placeholder="Введите название мероприятия на ${lang.displayName?lower_case}"' />
                                                    <@spring.showErrors "<br>" "text-danger small" />
                                                </div>

                                                <!-- Описание мероприятия -->
                                                <div class="mb-3">
                                                    <label for="content-${lang.code}" class="form-label">
                                                        Описание мероприятия (${lang.displayName}) <span class="text-danger">*</span>
                                                    </label>
                                                    <@spring.formTextarea "event.eventTranslations[${translationIndex}].content"
                                                    'class="form-control content-editor" id="content-${lang.code}" rows="10" placeholder="Введите подробное описание мероприятия на ${lang.displayName?lower_case}"' />
                                                    <@spring.showErrors "<br>" "text-danger small" />
                                                </div>
                                            </div>
                                        </div>
                                        <#assign translationIndex = translationIndex + 1>
                                    </#list>
                                </div>
                            </div>

                            <!-- Прикрепленные файлы -->
                            <div class="mb-4">
                                <label for="attachments" class="form-label">Прикрепленные файлы</label>
                                <input type="file" name="attachments" id="attachments" class="form-control" multiple>
                                <div class="form-text">
                                    Можно загрузить несколько файлов. Поддерживаемые форматы: PDF, DOC, DOCX, XLS, XLSX, JPG, PNG.
                                    Максимальный размер одного файла: 10 МБ
                                </div>
                            </div>

                            <!-- Кнопки управления -->
                            <div class="d-flex justify-content-between">
                                <a href="/events" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-1"></i>Отмена
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-1"></i>Создать мероприятие
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ru.js"></script>
    <script src="https://cdn.tiny.cloud/1/z66zloybrfzhb552oyl9fmnwcuplxmajqn017ne4zn5zg457/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
    <script src="/static/js/event-create.js"></script>

    <!-- Дополнительные стили -->
    <style>
        .form-label {
            font-weight: 600;
            color: #495057;
        }

        .text-danger {
            font-weight: 500;
        }

        .card {
            border: none;
            border-radius: 12px;
        }

        .card-header {
            border-radius: 12px 12px 0 0 !important;
            background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%) !important;
        }

        .form-control:focus,
        .form-select:focus {
            border-color: #4a90e2;
            box-shadow: 0 0 0 0.2rem rgba(74, 144, 226, 0.25);
        }

        .btn-primary {
            background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%);
            border: none;
            transition: all 0.2s;
        }

        .btn-primary:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(74, 144, 226, 0.3);
        }

        .form-text {
            font-size: 0.875rem;
            color: #6c757d;
        }

        /* Языковые вкладки */
        .language-tabs {
            border-bottom: 2px solid #dee2e6;
            margin-bottom: 0;
        }

        .language-tabs .nav-link {
            border: none;
            border-bottom: 3px solid transparent;
            background: none;
            color: #6c757d;
            font-weight: 500;
            padding: 12px 20px;
            transition: all 0.2s ease;
            position: relative;
        }

        .language-tabs .nav-link:hover {
            color: #4a90e2;
            border-bottom-color: rgba(74, 144, 226, 0.3);
        }

        .language-tabs .nav-link.active {
            color: #4a90e2;
            border-bottom-color: #4a90e2;
            background: none;
        }

        .language-content {
            border: 1px solid #dee2e6;
            border-top: none;
            border-radius: 0 0 8px 8px;
        }

        .language-form-content {
            padding: 20px;
        }

        .language-indicator {
            position: absolute;
            top: 5px;
            right: 5px;
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background: #dc3545;
            opacity: 0;
            transition: opacity 0.2s ease;
        }

        .language-indicator.filled {
            opacity: 1;
            background: #28a745;
        }

        /* Стили для организаций */
        .organizations-container {
            max-height: 200px;
            overflow-y: auto;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            padding: 10px;
            background-color: #fff;
        }

        .organization-item {
            margin-bottom: 8px;
            position: relative;
        }

        .organization-item:last-child {
            margin-bottom: 0;
        }

        .organization-checkbox {
            display: none;
        }

        .organization-label {
            display: flex;
            align-items: center;
            padding: 8px 12px;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.2s ease;
            user-select: none;
            border: 1px solid transparent;
            background-color: #f8f9fa;
        }

        .organization-label:hover {
            background-color: #e9ecef;
            border-color: #4a90e2;
        }

        .organization-checkbox:checked + .organization-label {
            background-color: #e3f2fd;
            border-color: #4a90e2;
            color: #1976d2;
        }

        .checkmark {
            width: 20px;
            height: 20px;
            border: 2px solid #dee2e6;
            border-radius: 4px;
            margin-right: 10px;
            position: relative;
            transition: all 0.2s ease;
            flex-shrink: 0;
        }

        .organization-checkbox:checked + .organization-label .checkmark {
            background-color: #4a90e2;
            border-color: #4a90e2;
        }

        .organization-checkbox:checked + .organization-label .checkmark::after {
            content: '';
            position: absolute;
            left: 5px;
            top: 1px;
            width: 6px;
            height: 10px;
            border: solid white;
            border-width: 0 2px 2px 0;
            transform: rotate(45deg);
        }

        .organization-name {
            flex: 1;
            font-weight: 500;
        }

        /* Стили для превью изображения */
        .image-preview-wrapper {
            position: relative;
            display: inline-block;
            max-width: 300px;
            border-radius: 8px;
            overflow: hidden;
            border: 1px solid #dee2e6;
        }

        .image-preview {
            width: 100%;
            height: auto;
            display: block;
            max-height: 200px;
            object-fit: cover;
        }

        .btn-remove-image {
            position: absolute;
            top: 8px;
            right: 8px;
            background: rgba(220, 53, 69, 0.9);
            border: none;
            color: white;
            width: 24px;
            height: 24px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            font-size: 12px;
            transition: all 0.2s ease;
        }

        .btn-remove-image:hover {
            background: rgba(220, 53, 69, 1);
            transform: scale(1.1);
        }

        .tox-tinymce {
            border-radius: 6px !important;
        }
    </style>
</@layout.layout>