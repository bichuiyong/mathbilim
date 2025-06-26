<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout title="Создать мероприятие">

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card">
                    <div class="card-header">
                        <h2 class="mb-0">Создание мероприятия</h2>
                    </div>
                    <div class="card-body">
                        <form id="eventForm" action="/events/create" method="post" enctype="multipart/form-data">
                            <@main.csrf/>
                            <#if error??>
                                <div class="alert alert-danger">
                                    ${error}
                                </div>
                            </#if>

                            <!-- Главное изображение -->
                            <div class="mb-4">
                                <label class="form-label fw-bold">Главное изображение <span class="text-danger">*</span></label>
                                <div class="image-upload-container">
                                    <input type="file" id="mainImageInput" name="image" accept="image/*" class="form-control mb-3">
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

                            <!-- Основные поля мероприятия -->
                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.event.startDate"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-play-circle me-2 text-success"></i>
                                        Дата и время начала <span class="text-danger">*</span>
                                    </label>
                                    <div class="datetime-input-wrapper">
                                        <input type="text"
                                               id="startDatePicker"
                                               class="form-control datetime-picker <#if spring.status.error>is-invalid</#if>"
                                               name="event.startDate"
                                               value="${spring.status.value!''}"
                                               placeholder="Выберите дату и время начала"
                                               required
                                               readonly>
                                        <i class="fas fa-calendar-alt datetime-icon"></i>
                                    </div>
                                    <#if spring.status.error>
                                        <div class="invalid-feedback">
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.event.endDate"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-stop-circle me-2 text-warning"></i>
                                        Дата и время окончания
                                    </label>
                                    <div class="datetime-input-wrapper">
                                        <input type="text"
                                               id="endDatePicker"
                                               class="form-control datetime-picker <#if spring.status.error>is-invalid</#if>"
                                               name="event.endDate"
                                               value="${spring.status.value!''}"
                                               placeholder="Выберите дату и время окончания"
                                               readonly>
                                        <i class="fas fa-calendar-alt datetime-icon"></i>
                                    </div>
                                    <#if spring.status.error>
                                        <div class="invalid-feedback">
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                    <small class="form-text text-muted">
                                        <i class="fas fa-info-circle me-1"></i>
                                        Необязательно, если мероприятие не имеет четкого времени окончания
                                    </small>
                                </div>
                            </div>

                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.event.typeId"/>
                                    <label class="form-label fw-bold">Тип мероприятия <span class="text-danger">*</span></label>
                                    <select class="form-select <#if spring.status.error>is-invalid</#if>" name="event.typeId" required>
                                        <option value="">-- Выберите тип --</option>
                                        <#list eventsTypes as type>
                                            <option value="${type.id}"
                                                    <#if spring.status.value?? && spring.status.value == type.id?string>selected</#if>>
                                                ${type.translations?filter(t -> t.languageCode == 'ru')?first.translation}
                                            </option>
                                        </#list>
                                    </select>
                                    <#if spring.status.error>
                                        <div class="invalid-feedback">
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.organizationIds"/>
                                    <label class="form-label fw-bold">Организации</label>
                                    <select class="form-select" name="organizationIds" multiple>
                                        <#list organizations as org>
                                            <option value="${org.id}">${org.name}</option>
                                        </#list>
                                    </select>
                                    <small class="form-text text-muted">Удерживайте Ctrl для выбора нескольких</small>
                                </div>
                            </div>

                            <!-- Тип проведения мероприятия -->
                            <div class="mb-4">
                                <label class="form-label fw-bold">Тип проведения <span class="text-danger">*</span></label>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-check">
                                            <@spring.bind "createEventDto.event.isOffline"/>
                                            <input type="radio"
                                                   class="form-check-input"
                                                   id="offlineEvent"
                                                   name="event.isOffline"
                                                   value="true"
                                                   <#if spring.status.value?? && spring.status.value>checked</#if>>
                                            <label class="form-check-label" for="offlineEvent">
                                                <i class="fas fa-map-marker-alt me-2"></i>Офлайн мероприятие
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-check">
                                            <input type="radio"
                                                   class="form-check-input"
                                                   id="onlineEvent"
                                                   name="event.isOffline"
                                                   value="false"
                                                   <#if spring.status.value?? && !spring.status.value>checked</#if>>
                                            <label class="form-check-label" for="onlineEvent">
                                                <i class="fas fa-globe me-2"></i>Онлайн мероприятие
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Поля локации (скрываются/показываются в зависимости от выбора) -->
                            <div class="location-fields">
                                <!-- Адрес для офлайн мероприятий -->
                                <div class="mb-4" id="addressField" style="display: none;">
                                    <@spring.bind "createEventDto.event.address"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-map-marker-alt me-2"></i>Адрес проведения
                                        <span class="text-danger">*</span>
                                    </label>
                                    <input type="text"
                                           class="form-control <#if spring.status.error>is-invalid</#if>"
                                           name="event.address"
                                           value="${spring.status.value!''}"
                                           placeholder="Укажите адрес проведения мероприятия">
                                    <#if spring.status.error>
                                        <div class="invalid-feedback">
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>

                                <!-- URL для онлайн мероприятий -->
                                <div class="mb-4" id="urlField" style="display: none;">
                                    <@spring.bind "createEventDto.event.url"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-link me-2"></i>Ссылка на мероприятие
                                        <span class="text-danger">*</span>
                                    </label>
                                    <input type="url"
                                           class="form-control <#if spring.status.error>is-invalid</#if>"
                                           name="event.url"
                                           value="${spring.status.value!''}"
                                           placeholder="https://example.com/meeting">
                                    <#if spring.status.error>
                                        <div class="invalid-feedback">
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>
                            </div>

                            <!-- Языковые табы -->
                            <div class="card mb-4">
                                <div class="card-header">
                                    <ul class="nav nav-tabs card-header-tabs" id="languageTabs">
                                        <#list createEventDto.event.eventTranslations?sort_by("languageCode") as trans>
                                            <li class="nav-item">
                                                <button class="nav-link <#if trans?is_first>active</#if>"
                                                        type="button"
                                                        data-bs-toggle="tab"
                                                        data-bs-target="#lang-${trans.languageCode}"
                                                        data-lang="${trans.languageCode}">
                                                    ${languages[trans.languageCode]}
                                                    <span class="translation-indicator ms-2" id="indicator-${trans.languageCode}">
                                                        <i class="fas fa-circle text-danger" title="Не заполнено"></i>
                                                    </span>
                                                </button>
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                                <div class="card-body">
                                    <div class="tab-content" id="languageTabContent">
                                        <@spring.bind "createEventDto.event.eventTranslations"/>
                                        <#if spring.status.error>
                                            <div class="alert alert-warning mb-3">
                                                <#list spring.status.errorMessages as error>${error}</#list>
                                            </div>
                                        </#if>

                                        <#list createEventDto.event.eventTranslations as trans>
                                            <div class="tab-pane fade <#if trans?is_first>show active</#if>"
                                                 id="lang-${trans.languageCode}">

                                                <!-- Заголовок -->
                                                <div class="mb-4">
                                                    <@spring.bind "createEventDto.event.eventTranslations[${trans?index}].title"/>
                                                    <label class="form-label fw-bold">Название мероприятия</label>
                                                    <input type="text"
                                                           class="form-control translation-field <#if spring.status.error>is-invalid</#if>"
                                                           name="event.eventTranslations[${trans?index}].title"
                                                           data-lang="${trans.languageCode}"
                                                           value="${spring.status.value!''}"
                                                           placeholder="Введите название мероприятия...">
                                                    <#if spring.status.error>
                                                        <div class="invalid-feedback">
                                                            <#list spring.status.errorMessages as error>${error}</#list>
                                                        </div>
                                                    </#if>
                                                </div>

                                                <!-- Контент -->
                                                <div class="mb-4">
                                                    <@spring.bind "createEventDto.event.eventTranslations[${trans?index}].content"/>
                                                    <label class="form-label fw-bold">Описание мероприятия</label>
                                                    <textarea class="form-control tinymce-editor translation-field <#if spring.status.error>is-invalid</#if>"
                                                              name="event.eventTranslations[${trans?index}].content"
                                                              data-lang="${trans.languageCode}"
                                                              id="content-${trans.languageCode}">${spring.status.value!''}</textarea>
                                                    <#if spring.status.error>
                                                        <div class="invalid-feedback">
                                                            <#list spring.status.errorMessages as error>${error}</#list>
                                                        </div>
                                                    </#if>
                                                </div>

                                                <input type="hidden" name="event.eventTranslations[${trans?index}].languageCode" value="${trans.languageCode}">
                                            </div>
                                        </#list>
                                    </div>
                                </div>
                            </div>

                            <!-- Дополнительные файлы -->
                            <div class="mb-4">
                                <label class="form-label fw-bold">Дополнительные файлы</label>
                                <input type="file" name="attachments" multiple class="form-control">
                                <small class="form-text text-muted">
                                    Можно загрузить несколько файлов (документы, презентации, программы мероприятия)
                                </small>
                            </div>

                            <!-- Кнопки управления -->
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <span class="text-muted">
                                        <i class="fas fa-info-circle me-1"></i>
                                        Заполните хотя бы один перевод для публикации
                                    </span>
                                </div>
                                <div class="d-flex gap-2">
                                    <a href="/events" class="btn btn-secondary">
                                        <i class="fas fa-times me-2"></i>Отменить
                                    </a>
                                    <button type="submit" class="btn btn-primary" id="submitButton">
                                        <i class="fas fa-save me-2"></i>Создать мероприятие
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Подключение TinyMCE -->
    <script src="https://cdn.tiny.cloud/1/7v0gkd2f6uam1hogetosjlogqkf8vz98nsxikg6vsrntra2k/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>

    <!-- Подключение Cropper.js -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.js"></script>

    <!-- Подключение Flatpickr -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/themes/material_blue.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ru.js"></script>

    <!-- Подключение компонентов -->
    <script src="/static/js/components/content-create/ImageCropManager.js"></script>
    <script src="/static/js/components/content-create/TinyMCEManager.js"></script>
    <script src="/static/js/components/content-create/LanguageTabsManager.js"></script>
    <script src="/static/js/components/content-create/TranslationValidationManager.js"></script>
    <script src="/static/js/components/content-create/BaseContentCreateManager.js"></script>

    <!-- Специализированный скрипт для мероприятий -->
    <script src="/static/js/event/EventCreateManager.js"></script>

    <style>
        /* Стили для кропа */
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

        /* Стили для индикаторов заполненности */
        .translation-indicator .fa-circle.text-success {
            color: #10b981 !important;
        }

        .translation-indicator .fa-circle.text-warning {
            color: #f59e0b !important;
        }

        .translation-indicator .fa-circle.text-danger {
            color: #ef4444 !important;
        }

        /* Стили для полей локации */
        .location-fields {
            background: #f8fafc;
            border-radius: 8px;
            padding: 1.5rem;
            border: 1px solid #e2e8f0;
        }

        /* Стили для радио-кнопок */
        .form-check-label {
            cursor: pointer;
            user-select: none;
        }

        .form-check-input:checked + .form-check-label {
            color: #2563eb;
            font-weight: 600;
        }

        /* Стили для полей дат с Flatpickr */
        .datetime-input-wrapper {
            position: relative;
        }

        .datetime-picker {
            padding-right: 2.5rem;
            cursor: pointer;
        }

        .datetime-icon {
            position: absolute;
            right: 0.75rem;
            top: 50%;
            transform: translateY(-50%);
            color: #6b7280;
            pointer-events: none;
            font-size: 1rem;
        }

        .datetime-picker:focus + .datetime-icon,
        .datetime-picker.flatpickr-input.active + .datetime-icon {
            color: #2563eb;
        }

        /* Кастомизация Flatpickr */
        .flatpickr-calendar {
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15) !important;
            border: none !important;
            border-radius: 12px !important;
            overflow: hidden;
        }

        .flatpickr-calendar.open {
            z-index: 1060 !important;
        }

        .flatpickr-day.selected {
            background: #2563eb !important;
            border-color: #2563eb !important;
        }

        .flatpickr-day.selected:hover {
            background: #1d4ed8 !important;
            border-color: #1d4ed8 !important;
        }

        .flatpickr-day:hover {
            background: #e0e7ff !important;
            border-color: #e0e7ff !important;
        }

        .flatpickr-day.today {
            border-color: #2563eb !important;
            color: #2563eb !important;
        }

        .flatpickr-day.today:hover {
            background: #e0e7ff !important;
            color: #1d4ed8 !important;
        }

        .flatpickr-months .flatpickr-month {
            background: #f8fafc !important;
            color: #1e293b !important;
        }

        .flatpickr-weekdays {
            background: #f1f5f9 !important;
        }

        .flatpickr-weekday {
            color: #64748b !important;
            font-weight: 600 !important;
        }

        .flatpickr-time {
            border-top: 1px solid #e2e8f0 !important;
        }

        .flatpickr-time .numInputWrapper {
            background: #f8fafc !important;
        }

        .flatpickr-time input {
            color: #1e293b !important;
            font-weight: 600 !important;
        }

        /* Анимация для появления полей даты */
        .datetime-input-wrapper {
            opacity: 0;
            transform: translateY(10px);
            animation: fadeInUp 0.6s ease forwards;
        }

        .datetime-input-wrapper:nth-child(1) {
            animation-delay: 0.1s;
        }

        .datetime-input-wrapper:nth-child(2) {
            animation-delay: 0.2s;
        }

        @keyframes fadeInUp {
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Анимации для уведомлений */
        @keyframes slideInRight {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes slideOutRight {
            from {
                transform: translateX(0);
                opacity: 1;
            }
            to {
                transform: translateX(100%);
                opacity: 0;
            }
        }

        /* Улучшенные стили для уведомлений */
        .notification-custom {
            backdrop-filter: blur(10px);
            border-left: 4px solid;
        }

        .notification-custom.alert-success {
            border-left-color: #10b981;
            background: rgba(16, 185, 129, 0.1);
        }

        .notification-custom.alert-warning {
            border-left-color: #f59e0b;
            background: rgba(245, 158, 11, 0.1);
        }

        .notification-custom.alert-danger {
            border-left-color: #ef4444;
            background: rgba(239, 68, 68, 0.1);
        }

        .notification-custom.alert-info {
            border-left-color: #2563eb;
            background: rgba(37, 99, 235, 0.1);
        }

        /* Стили для TinyMCE */
        .tox-tinymce {
            border-radius: 8px !important;
        }
    </style>

</@main.layout>