<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout title="Создать мероприятие">
    <div class="container mt-5">
        <#assign currentLocale = springMacroRequestContext.locale.language>

        <div id="notificationsContainer" class="position-fixed top-0 end-0 p-3" style="z-index: 9999; max-width: 400px;"></div>

        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card">
                    <div class="card-header">
                        <h2 class="mb-0"><@spring.message "events.create.title"/></h2>
                    </div>
                    <div class="card-body">
                        <form id="eventForm" action="/events/create" method="post" enctype="multipart/form-data">
                            <@main.csrf/>

                            <!-- Общие ошибки формы -->
                            <#if error??>
                                <div class="alert alert-danger alert-dismissible fade show notification-custom mb-4" role="alert">
                                    <div class="d-flex align-items-center">
                                        <i class="fas fa-exclamation-circle me-2 fs-5"></i>
                                        <strong>${error}</strong>
                                    </div>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </#if>

                            <!-- Основные поля мероприятия -->
                            <div class="mb-4">
                                <label class="form-label fw-bold">
                                    <@spring.message "events.cover"/>
                                    <span class="text-danger">*</span>
                                </label>
                                <div class="image-upload-container">
                                    <input type="file" id="mainImageInput" name="image" accept=".jpg, .jpeg, .png" class="form-control mb-3">
                                    <div class="image-preview-container" style="display: none;">
                                        <div class="crop-container">
                                            <img id="imagePreview" class="img-fluid">
                                        </div>
                                        <div class="crop-controls mt-3">
                                            <button type="button" class="btn btn-primary" id="cropButton">
                                                <@spring.message "events.crop.apply"/>
                                            </button>
                                            <button type="button" class="btn btn-secondary" id="cancelCrop">
                                                <@spring.message "events.crop.cancel"/>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="cropped-preview" style="display: none;">
                                        <label class="form-label">
                                            <@spring.message "events.preview"/> (1200x400):
                                        </label>
                                        <div class="cropped-image-container">
                                            <img id="croppedImage" class="img-fluid border rounded">
                                        </div>
                                        <button type="button" class="btn btn-outline-secondary btn-sm mt-2" id="changeCrop">
                                            <@spring.message "events.crop.change"/>
                                        </button>
                                    </div>
                                </div>
                                <small class="form-text text-muted">
                                    <@spring.message "events.cover.aspectRatio"/>
                                </small>
                            </div>

                            <!-- Дата и время -->
                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.event.startDate"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-play-circle me-2 text-success"></i>
                                        <@spring.message "events.startDate"/> <span class="text-danger">*</span>
                                    </label>
                                    <div class="datetime-input-wrapper">
                                        <input type="text"
                                               id="startDatePicker"
                                               class="form-control datetime-picker <#if spring.status.error>is-invalid</#if>"
                                               name="event.startDate"
                                               value="${spring.status.value!''}"
                                               placeholder="${springMacroRequestContext.getMessage('events.startDate.placeholder')}"
                                               required
                                               readonly>
                                        <i class="fas fa-calendar-alt datetime-icon"></i>
                                    </div>
                                    <#if spring.status.error>
                                        <div class="invalid-feedback d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle me-2"></i>
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.event.endDate"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-stop-circle me-2 text-warning"></i>
                                        <@spring.message "events.endDate"/>
                                    </label>
                                    <div class="datetime-input-wrapper">
                                        <input type="text"
                                               id="endDatePicker"
                                               class="form-control datetime-picker <#if spring.status.error>is-invalid</#if>"
                                               name="event.endDate"
                                               value="${spring.status.value!''}"
                                               placeholder="${springMacroRequestContext.getMessage('events.endDate.placeholder')}"
                                               readonly>
                                        <i class="fas fa-calendar-alt datetime-icon"></i>
                                    </div>
                                    <#if spring.status.error>
                                        <div class="invalid-feedback d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle me-2"></i>
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                    <small class="form-text text-muted">
                                        <i class="fas fa-info-circle me-1"></i>
                                        <@spring.message "events.endDate.optional"/>
                                    </small>
                                </div>
                            </div>

                            <!-- Тип и организации -->
                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.event.typeId"/>
                                    <label class="form-label fw-bold">
                                        <@spring.message "events.type"/> <span class="text-danger">*</span>
                                    </label>
                                    <select class="form-select <#if spring.status.error>is-invalid</#if>" name="event.typeId" required>
                                        <option value="">-- <@spring.message "events.type.select"/> --</option>
                                        <#list eventsTypes as type>
                                            <#assign translations = type.translations![]>
                                            <#assign translation = translations?filter(t -> t.languageCode == currentLocale)?first!>
                                            <option value="${type.id}"
                                                    <#if spring.status.value?? && (spring.status.value?string == type.id?string)>selected</#if>>
                                                <#if translation??>
                                                    ${translation.translation}
                                                <#elseif translations?size > 0>
                                                    ${translations[0].translation}
                                                <#else>
                                                    [Без перевода]
                                                </#if>
                                            </option>
                                        </#list>
                                    </select>
                                    <#if spring.status.error>
                                        <div class="invalid-feedback d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle me-2"></i>
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>
                                <div class="col-md-6">
                                    <@spring.bind "createEventDto.organizationIds"/>
                                    <label class="form-label fw-bold">
                                        <@spring.message "events.organizations"/>
                                    </label>
                                    <select class="form-select <#if spring.status.error>is-invalid</#if>" name="organizationIds" multiple>
                                        <#if organizations?has_content>
                                            <#list organizations as org>
                                                <option value="${org.id}">${org.name}</option>
                                            </#list>
                                        <#else>
                                            <option disabled><@spring.message "events.organizations.notFound"/></option>
                                        </#if>
                                    </select>
                                    <#if spring.status.error>
                                        <div class="invalid-feedback d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle me-2"></i>
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                    <small class="form-text text-muted">
                                        <@spring.message "events.organizations.multiple"/>
                                    </small>
                                </div>
                            </div>

                            <!-- Формат мероприятия -->
                            <div class="mb-4">
                                <label class="form-label fw-bold">
                                    <@spring.message "events.format"/> <span class="text-danger">*</span>
                                </label>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-check">
                                            <@spring.bind "createEventDto.event.isOffline"/>
                                            <input type="radio"
                                                   class="form-check-input"
                                                   id="offlineEvent"
                                                   name="event.isOffline"
                                                   value="true"
                                                   <#if spring.status.error>aria-invalid="true"</#if>
                                                    <#if spring.status.value?? && spring.status.value == "true">checked</#if>>
                                            <label class="form-check-label" for="offlineEvent">
                                                <i class="fas fa-map-marker-alt me-2"></i>
                                                <@spring.message "events.format.offline"/>
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
                                                   <#if spring.status.error>aria-invalid="true"</#if>
                                                    <#if spring.status.value?? && spring.status.value == "false">checked</#if>>
                                            <label class="form-check-label" for="onlineEvent">
                                                <i class="fas fa-globe me-2"></i>
                                                <@spring.message "events.format.online"/>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <#if spring.status.error>
                                    <div class="text-danger mt-2 d-flex align-items-center">
                                        <i class="fas fa-exclamation-circle me-2"></i>
                                        <#list spring.status.errorMessages as error>${error}</#list>
                                    </div>
                                </#if>
                            </div>

                            <!-- Поля локации -->
                            <div class="location-fields">
                                <div class="mb-4" id="addressField" style="display: none;">
                                    <@spring.bind "createEventDto.event.address"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-map-marker-alt me-2"></i>
                                        <@spring.message "events.address"/>
                                        <span class="text-danger">*</span>
                                    </label>
                                    <input type="text"
                                           class="form-control <#if spring.status.error>is-invalid</#if>"
                                           name="event.address"
                                           value="${spring.status.value!''}"
                                           placeholder="${springMacroRequestContext.getMessage('events.address.placeholder')}">
                                    <#if spring.status.error>
                                        <div class="invalid-feedback d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle me-2"></i>
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>

                                <div class="mb-4" id="urlField" style="display: none;">
                                    <@spring.bind "createEventDto.event.url"/>
                                    <label class="form-label fw-bold">
                                        <i class="fas fa-link me-2"></i>
                                        <@spring.message "events.url"/>
                                        <span class="text-danger">*</span>
                                    </label>
                                    <input type="url"
                                           class="form-control <#if spring.status.error>is-invalid</#if>"
                                           name="event.url"
                                           value="${spring.status.value!''}"
                                           placeholder="https://example.com/meeting">
                                    <#if spring.status.error>
                                        <div class="invalid-feedback d-flex align-items-center">
                                            <i class="fas fa-exclamation-circle me-2"></i>
                                            <#list spring.status.errorMessages as error>${error}</#list>
                                        </div>
                                    </#if>
                                </div>
                            </div>

                            <!-- Переводы -->
                            <div class="card mb-4">
                                <div class="card-header">
                                    <ul class="nav nav-tabs card-header-tabs" id="languageTabs">
                                        <#list createEventDto.event.eventTranslations as trans>
                                            <li class="nav-item">
                                                <button class="nav-link <#if trans?is_first>active</#if>"
                                                        type="button"
                                                        data-bs-toggle="tab"
                                                        data-bs-target="#lang-${trans.languageCode}"
                                                        data-lang="${trans.languageCode}">
                                                    ${languages[trans.languageCode]}
                                                    <span class="translation-indicator ms-2" id="indicator-${trans.languageCode}">
                                                    <i class="fas fa-circle text-danger" title="<@spring.message 'events.translation.notFilled'/>"></i>
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

                                                <div class="mb-4">
                                                    <@spring.bind "createEventDto.event.eventTranslations[${trans?index}].title"/>
                                                    <label class="form-label fw-bold">
                                                        <@spring.message "events.title"/>
                                                    </label>
                                                    <input type="text"
                                                           class="form-control translation-field <#if spring.status.error>is-invalid</#if>"
                                                           name="event.eventTranslations[${trans?index}].title"
                                                           data-lang="${trans.languageCode}"
                                                           value="${spring.status.value!''}"
                                                           placeholder="${springMacroRequestContext.getMessage('events.title.placeholder')}">
                                                    <#if spring.status.error>
                                                        <div class="invalid-feedback d-flex align-items-center">
                                                            <i class="fas fa-exclamation-circle me-2"></i>
                                                            <#list spring.status.errorMessages as error>${error}</#list>
                                                        </div>
                                                    </#if>
                                                </div>

                                                <div class="mb-4">
                                                    <@spring.bind "createEventDto.event.eventTranslations[${trans?index}].content"/>
                                                    <label class="form-label fw-bold">
                                                        <@spring.message "events.description"/>
                                                    </label>
                                                    <textarea class="form-control rich-text-editor translation-field <#if spring.status.error>is-invalid</#if>"
                                                              name="event.eventTranslations[${trans?index}].content"
                                                              data-lang="${trans.languageCode}"
                                                              id="content-${trans.languageCode}">${spring.status.value!''}</textarea>
                                                    <#if spring.status.error>
                                                        <div class="invalid-feedback d-flex align-items-center">
                                                            <i class="fas fa-exclamation-circle me-2"></i>
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

                            <div class="mb-4">
                                <@spring.bind "createEventDto.attachments"/>
                                <label class="form-label fw-bold">
                                    <@spring.message "events.attachments"/>
                                </label>
                                <input type="file" name="attachments" multiple class="form-control <#if spring.status.error>is-invalid</#if>" accept=".jpg, .jpeg, .png">
                                <#if spring.status.error>
                                    <div class="invalid-feedback d-flex align-items-center">
                                        <i class="fas fa-exclamation-circle me-2"></i>
                                        <#list spring.status.errorMessages as error>${error}</#list>
                                    </div>
                                </#if>
                                <small class="form-text text-muted">
                                    <@spring.message "events.attachments.description"/>
                                </small>
                            </div>

                            <!-- Кнопки -->
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                <span class="text-muted">
                                    <i class="fas fa-info-circle me-1"></i>
                                    <@spring.message "events.translation.required"/>
                                </span>
                                </div>
                                <div class="d-flex gap-2">
                                    <a href="/events" class="btn btn-secondary">
                                        <i class="fas fa-times me-2"></i>
                                        <@spring.message "events.cancel"/>
                                    </a>
                                    <button type="submit" class="btn btn-primary" id="submitButton">
                                        <i class="fas fa-save me-2"></i>
                                        <@spring.message "events.create.button"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Подключение Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Подключение Froala Editor -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.2.1/css/froala_editor.pkgd.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.2.1/js/froala_editor.pkgd.min.js"></script>

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
    <script src="/static/js/components/content-create/LanguageTabsManager.js"></script>
    <script src="/static/js/components/content-create/TranslationValidationManager.js"></script>
    <script src="/static/js/components/content-create/BaseContentCreateManager.js"></script>
    <script src="/static/js/event/EventCreateManager.js"></script>

    <style>
        /* Стили для ошибок */
        .is-invalid {
            border-color: #dc3545 !important;
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 12 12' width='12' height='12' fill='none' stroke='%23dc3545'%3e%3ccircle cx='6' cy='6' r='4.5'/%3e%3cpath d='m5.8 3.6.4.4.4-.4'/%3e%3cpath d='M6 7v2'/%3e%3c/svg%3e");
            background-repeat: no-repeat;
            background-position: right calc(0.375em + 0.1875rem) center;
            background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
            padding-right: calc(1.5em + 0.75rem);
        }

        .invalid-feedback {
            display: flex;
            align-items: center;
            color: #dc3545;
            font-weight: 500;
            margin-top: 0.5rem;
            padding: 0.5rem;
            background: rgba(220, 53, 69, 0.05);
            border-radius: 0.375rem;
            border-left: 3px solid #dc3545;
        }

        .invalid-feedback i {
            font-size: 0.875rem;
        }

        /* Анимация для появления ошибок */
        @keyframes shakeError {
            0%, 100% { transform: translateX(0); }
            10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
            20%, 40%, 60%, 80% { transform: translateX(5px); }
        }

        .is-invalid {
            animation: shakeError 0.6s ease;
        }

        /* Стили для уведомлений */
        .notification-custom {
            backdrop-filter: blur(10px);
            border: none;
            border-radius: 0.75rem;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
        }

        .notification-custom.alert-danger {
            border-left: 4px solid #dc3545;
            background: linear-gradient(135deg, rgba(220, 53, 69, 0.1) 0%, rgba(220, 53, 69, 0.05) 100%);
        }

        .notification-custom.alert-warning {
            border-left: 4px solid #ffc107;
            background: linear-gradient(135deg, rgba(255, 193, 7, 0.1) 0%, rgba(255, 193, 7, 0.05) 100%);
        }

        .notification-custom.alert-success {
            border-left: 4px solid #198754;
            background: linear-gradient(135deg, rgba(25, 135, 84, 0.1) 0%, rgba(25, 135, 84, 0.05) 100%);
        }

        /* Анимации для уведомлений */
        .alert {
            animation: slideInRight 0.5s ease forwards;
        }

        .alert.fade {
            animation: slideOutRight 0.5s ease forwards;
        }

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

        /* Дополнительные стили для полей */
        .form-control:focus.is-invalid {
            box-shadow: 0 0 0 0.25rem rgba(220, 53, 69, 0.25);
        }

        .form-select:focus.is-invalid {
            box-shadow: 0 0 0 0.25rem rgba(220, 53, 69, 0.25);
        }

        /* Стили для радио-кнопок с ошибками */
        .form-check-input.is-invalid {
            border-color: #dc3545;
        }

        .form-check-input.is-invalid:checked {
            background-color: #dc3545;
            border-color: #dc3545;
        }

        .text-danger {
            color: #dc3545 !important;
            font-weight: 500;
        }
    </style>

    <script>
        function showNotification(message, type = 'danger') {
            const container = document.getElementById('notificationsContainer');
            const notificationId = 'notification-' + Date.now();

            const notification = document.createElement('div');
            notification.className = `alert alert-`+ type+` alert-dismissible fade show notification-custom mb-3`;
            notification.role = 'alert';
            notification.id = notificationId;

            notification.innerHTML = `
                <div class="d-flex align-items-center">
                    <i class="fas `+ type === 'danger' ? 'fa-exclamation-circle' : type === 'warning' ? 'fa-exclamation-triangle' : 'fa-check-circle'+ ` me-2"></i>
                    <strong>`+ message +`</strong>
                </div>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            `;

            container.appendChild(notification);

            setTimeout(() => {
                const alert = bootstrap.Alert.getOrCreateInstance(notification);
                alert.close();
            }, 5000);
        }

        document.addEventListener('DOMContentLoaded', function() {
            <#if error??>
            showNotification('${error}', 'danger');
            </#if>

            const invalidFields = document.querySelectorAll('.is-invalid');
            invalidFields.forEach(field => {
                field.scrollIntoView({ behavior: 'smooth', block: 'center' });
            });
        });

        document.getElementById('eventForm').addEventListener('submit', function(e) {
            const submitButton = document.getElementById('submitButton');
            submitButton.disabled = true;
            submitButton.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i> Сохранение...';
        });
    </script>

</@main.layout>