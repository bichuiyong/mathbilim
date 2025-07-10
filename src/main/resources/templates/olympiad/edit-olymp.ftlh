<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <style>
        :root {
            --primary: #2563eb;
            --primary-light: #3b82f6;
            --primary-dark: #1d4ed8;
            --bg: #f8fafc;
            --card: #ffffff;
            --text: #1e293b;
            --text-light: #64748b;
            --border: #e2e8f0;
            --error: #dc2626;
        }

        .olympiad-body {
            background-color: var(--bg);
            color: var(--text);
            line-height: 1.5;
            padding: 2rem;
            min-height: 100vh;
        }

        .olympiad-container {
            max-width: 900px;
            margin: 0 auto;
            background-color: var(--card);
            border-radius: 1rem;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .olympiad-header {
            padding: 1.5rem 2rem;
            border-bottom: 1px solid var(--border);
        }

        .olympiad-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: var(--primary);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .olympiad-title svg {
            width: 1.5rem;
            height: 1.5rem;
        }

        .form-content {
            padding: 2rem;
        }

        .olympiad-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1.5rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group.full-width {
            grid-column: span 2;
        }

        .form-group.has-error .olympiad-input,
        .form-group.has-error .olympiad-select,
        .form-group.has-error .olympiad-textarea {
            border-color: var(--error);
            box-shadow: 0 0 0 3px rgba(220, 38, 38, 0.1);
        }

        .olympiad-label {
            display: block;
            margin-bottom: 0.5rem;
            font-size: 0.875rem;
            font-weight: 500;
            color: var(--text);
        }

        .olympiad-label.required::after {
            content: '*';
            color: var(--error);
            margin-left: 0.25rem;
        }

        .olympiad-input,
        .olympiad-select,
        .olympiad-textarea {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid var(--border);
            border-radius: 0.5rem;
            font-size: 0.875rem;
            transition: all 0.15s ease;
        }

        .olympiad-input:focus,
        .olympiad-select:focus,
        .olympiad-textarea:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
        }

        .olympiad-textarea {
            min-height: 120px;
            resize: vertical;
        }

        .olympiad-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.75rem 1.25rem;
            border-radius: 0.5rem;
            font-size: 0.875rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.15s ease;
            border: none;
        }

        .olympiad-btn-primary {
            background-color: var(--primary);
            color: white;
        }

        .olympiad-btn-primary:hover {
            background-color: var(--primary-dark);
        }

        .olympiad-btn-outline {
            background-color: transparent;
            border: 1px solid var(--border);
            color: var(--text);
        }

        .olympiad-btn-outline:hover {
            background-color: #f1f5f9;
        }

        .olympiad-btn svg {
            width: 1rem;
            height: 1rem;
            margin-right: 0.5rem;
        }

        .olympiad-btn-sm {
            padding: 0.5rem 0.75rem;
            font-size: 0.75rem;
            line-height: 1;
        }

        .olympiad-btn-sm svg {
            width: 0.875rem;
            height: 0.875rem;
            margin-right: 0.25rem;
        }

        .stages-container {
            margin-top: 1rem;
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .stage-card {
            padding: 1.25rem;
            border: 1px solid var(--border);
            border-radius: 0.5rem;
            background-color: white;
        }

        .stage-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 1rem;
        }

        .stage-title {
            font-weight: 600;
            color: var(--primary);
        }

        .contacts-container {
            margin-top: 1rem;
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .contact-card {
            display: grid;
            grid-template-columns: 1fr 2fr auto;
            gap: 1rem;
            align-items: start;
        }

        .checkbox-group {
            padding: 0;
            border: 1px solid var(--border);
            border-radius: 0.5rem;
            background-color: white;
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.3s ease-in-out, padding 0.3s ease-in-out;
        }

        .checkbox-group.open {
            max-height: 500px;
            padding: 1rem;
        }

        .checkbox-item {
            display: flex;
            align-items: center;
            gap: 0.75rem;
            padding: 0.5rem 0;
            font-size: 0.875rem;
        }

        .checkbox-item input[type="checkbox"] {
            width: 1.25rem;
            height: 1.25rem;
            accent-color: var(--primary);
            cursor: pointer;
        }

        .checkbox-item label {
            cursor: pointer;
            flex-grow: 1;
        }

        .submit-btn {
            width: 100%;
            padding: 1rem;
            font-size: 1rem;
            margin-top: 1.5rem;
        }

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
            position: relative;
        }

        .cropped-image-container {
            width: 100%;
            max-width: 600px;
            aspect-ratio: 1.2 / 1;
            overflow: hidden;
            border-radius: 8px;
        }

        .cropped-image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .crop-hint {
            font-size: 0.875rem;
            color: #64748b;
            margin-bottom: 1rem;
            text-align: center;
        }

        .cropper-crop-box {
            border: 2px solid #2563eb;
        }

        .cropper-view-box {
            border: 1px dashed #3b82f6;
        }

        .error {
            color: var(--error);
            font-size: small;
        }

        .text-danger {
            color: var(--error) !important;
            display: block !important;
            font-size: 0.875rem !important;
            margin-top: 0.25rem !important;
        }

        .error-message {
            color: var(--error);
            font-size: 0.875rem;
            margin-top: 0.25rem;
            display: block;
        }

        @media (max-width: 768px) {
            .olympiad-grid {
                grid-template-columns: 1fr;
            }

            .form-group.full-width {
                grid-column: span 1;
            }

            .contact-card {
                grid-template-columns: 1fr;
            }
        }
    </style>

    <div class="olympiad-body">
        <div class="olympiad-container">
            <header class="olympiad-header">
                <h1 class="olympiad-title">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    Редактирование олимпиады
                </h1>
                <p style="font-size: 14px; margin-top: 10px; color: gray">*После начала олимпиады, некоторые параметры не редактируемы</p>
            </header>

            <div class="form-content">
                <form id="olympiadForm" action="/olympiad/edit?=${olympiadCreateDto.id}" method="POST" enctype="multipart/form-data">
                    <div class="olympiad-grid">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <input type="hidden" name="id" value="${olympiadCreateDto.id!''}">
                        <input type="hidden" name="creatorId" value="${olympiadCreateDto.creatorId!''}">


                        <div class="form-group full-width">
                            <label for="title" class="olympiad-label required">Название олимпиады</label>
                            <@spring.formInput "olympiadCreateDto.title" "placeholder='Введите название' class='olympiad-input' readonly"/>
                            <@spring.showErrors "olympiadCreateDto.title", "text-danger"/>
                        </div>

                        <div class="form-group full-width">
                            <label for="info" class="olympiad-label required">Описание</label>
                            <@spring.formTextarea "olympiadCreateDto.info" "placeholder='Введите подробное описание олимпиады' class='olympiad-textarea rich-text-editor'"/>
                            <@spring.showErrors "info", "text-danger"/>
                        </div>

                        <div class="form-group">
                        <label for="start_date" class="olympiad-label required">Дата начала</label>
                        <input
                                name="startDate" type="date" class="olympiad-input" value="${olympiadCreateDto.startDate!''}" <#if olympiadCreateDto.hasStarted> readonly </#if>>
                        <@spring.showErrors "startDate" "text-danger small mt-1"/>
                    </div>

                    <div class="form-group">
                        <label for="end_date" class="olympiad-label required">Дата окончания</label>
                        <input
                                name="endDate" type="date" class="olympiad-input" value="${olympiadCreateDto.endDate!''}" <#if olympiadCreateDto.hasEnded> readonly </#if>>
                        <@spring.showErrors "endDate" "text-danger small mt-1"/>
                    </div>

                        <#if olympiadDateError??>
                            <div class="form-group full-width">
                                <div class="text-danger">${olympiadDateError}</div>
                            </div>
                        </#if>

                        <div class="form-group full-width">
                            <label for="rules" class="olympiad-label required">Правила участия</label>
                            <@spring.formTextarea "olympiadCreateDto.rules" "class='olympiad-textarea rich-text-editor'"/>
                            <@spring.showErrors "olympiadCreateDto.rules", "text-danger"/>
                        </div>

                        <div class="form-group full-width">
                            <label class="olympiad-label required">Новое главное изображение</label>
                            <div class="image-upload-container">
                                <input type="file" id="mainImageInput" name="imageFile" accept="image/*" class="form-control mb-3">
                                <div class="image-preview-container" style="display: none;">
                                    <p class="crop-hint">Перетаскивайте или масштабируйте, чтобы выбрать область обрезки (1200x1000)</p>
                                    <div class="crop-container">
                                        <img id="imagePreview" class="img-fluid">
                                    </div>
                                    <div class="crop-controls mt-3">
                                        <button type="button" class="olympiad-btn olympiad-btn-primary" id="cropButton">Применить кроп</button>
                                        <button type="button" class="olympiad-btn olympiad-btn-outline" id="cancelCrop">Отменить</button>
                                    </div>
                                </div>
                                <div class="cropped-preview" style="display: none;">
                                    <label class="olympiad-label">Предпросмотр:</label>
                                    <div class="cropped-image-container">
                                        <img id="croppedImage" class="img-fluid border rounded">
                                    </div>
                                    <button type="button" class="olympiad-btn olympiad-btn-outline olympiad-btn-sm mt-2" id="changeCrop">Изменить кроп</button>
                                    <p id="imageError" class="text-danger small mt-1" style="display: none;"></p>
                                </div>
                                <small class="form-text text-muted">
                                    Изображение будет обрезано до размера 1200x1000. Максимальный размер файла: 5MB.
                                </small>
                            </div>
                        </div>

                        <div class="form-group full-width">
                            <label>Организаторы</label>
                            <button type="button" id="toggleOrganizers" class="olympiad-btn olympiad-btn-outline olympiad-btn-sm"
                                    style="margin-bottom: 0.5rem;">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                     stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M19 9l-7 7-7-7" />
                                </svg>
                                Показать организаторов
                            </button>
                            <div id="organizersContainer" class="checkbox-group" style="display: none">
                                <#if organizations?? && organizations?has_content>
                                    <#list organizations as org>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="organizationIds" value="${org.id}"
                                                   <#if olympiadCreateDto.organizationIds?seq_contains(org.id)>checked</#if> >
                                            <label class="form-check-label" for="org1">${org.name}</label>
                                        </div>
                                    </#list>
                                <#else>
                                    <p>Организаторы не найдены либо отсутствуют</p>
                                </#if>
                            </div>
                        </div>

                        <div class="form-group full-width">
                            <label>Контакты</label>
                            <div id="contacts" class="contacts-container">
                                <#list olympiadCreateDto.contacts as contact>
                                    <div class="contact-card">
                                        <div>
                                            <select name="contacts[${contact_index}].contactType" class="olympiad-select contact-type-select" required>
                                                <option value="" disabled>Выберите тип контакта</option>
                                                <#if contactTypes??>
                                                    <#list contactTypes as type>
                                                        <option value="${type.id}" <#if contact.contactType?? && contact.contactType == type.id>selected</#if>>${type.name}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                            <@spring.showErrors "olympiadCreateDto.contacts[${contact_index}].contactType", "text-danger"/>
                                        </div>
                                        <div>
                                            <input type="text" name="contacts[${contact_index}].info" value="${contact.info!''}"
                                                   placeholder="Ссылка на контакт" class="olympiad-input">
                                            <@spring.showErrors "olympiadCreateDto.contacts[${contact_index}].info", "text-danger"/>
                                        </div>
                                        <button type="button" class="olympiad-btn olympiad-btn-outline remove-contact">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                 stroke="currentColor">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                            </svg>
                                            Удалить
                                        </button>
                                    </div>
                                </#list>
                            </div>
                            <button type="button" id="addContact" class="olympiad-btn olympiad-btn-outline" style="margin-top: 0.5rem;">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                     stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                                </svg>
                                Добавить контакт
                            </button>
                        </div>

                        <div class="form-group full-width">
                            <label style="margin-bottom: 10px">Этапы олимпиады</label>
                            <div class="stages-container" id="stagesContainer">
                                <#list olympiadCreateDto.stages as stage>
                                    <div class="stage-card">
                                        <div class="stage-header">
                                            <span class="stage-title">Этап ${stage.stageOrder}</span>
                                        </div>
                                        <div class="olympiad-grid" style="gap: 1rem;">
                                            <@spring.formInput "olympiadCreateDto.stages[${stage_index}].stageOrder" 'hidden'/>
                                            <input hidden="hidden" name="stages[${stage_index}].id" value="${stage.id}"/>

                                            <div class="form-group">
                                                <label class="olympiad-label required">Дата начала</label>
                                                <input
                                                        name="stages[${stage_index}].eventStartDate" type="date" class="olympiad-input" value="${stage.eventStartDate!''}"
                                                        <#if stage.hasStarted> readonly </#if>>
                                                <@spring.showErrors "olympiadCreateDto.stages[${stage_index}].eventStartDate"/>
                                            </div>
                                            <div class="form-group">
                                                <label class="olympiad-label required">Дата окончания</label>
                                                <input
                                                        name="stages[${stage_index}].eventEndDate" type="date" class="olympiad-input" value="${stage.eventEndDate!''}"
                                                        <#if stage.hasEnded> readonly </#if>>
                                                <@spring.showErrors "olympiadCreateDto.stages[${stage_index}].eventEndDate" "text-danger small mt-1"/>
                                            </div>
                                            <div class="form-group">
                                                <label class="olympiad-label required">Начало регистрации</label>
                                                <input
                                                        name="stages[${stage_index}].registrationStart" type="date" class="olympiad-input" value="${stage.registrationStart!''}"
                                                        <#if stage.hasStarted> readonly </#if>>
                                                <@spring.showErrors "olympiadCreateDto.stages[${stage_index}].registrationStart"/>
                                            </div>
                                            <div class="form-group">
                                                <label class="olympiad-label required">Конец регистрации</label>
                                                <input
                                                        name="stages[${stage_index}].registrationEnd" type="date" class="olympiad-input" value="${stage.registrationEnd!''}"
                                                        <#if stage.hasStarted> readonly </#if>>
                                                <@spring.showErrors "olympiadCreateDto.stages[${stage_index}].registrationEnd"/>
                                            </div>
                                            <#if olympiadDateError??>
                                                <p class="text-danger small">${olympiadDateError}</p>
                                            </#if>
                                        </div>
                                    </div>
                                </#list>
                            </div>

                            <#if stageError??>
                                <div class="text-danger">${stageError}</div>
                            </#if>
                            <#if dateError??>
                                <div class="text-danger">${dateError}</div>
                            </#if>
                            <#if dateRangeErrors?? && dateRangeErrors?size != 0>
                                <#list dateRangeErrors as err>
                                    <div class="text-danger">${err}</div>
                                </#list>
                            </#if>
                        </div>
                    </div>

                    <button type="submit" class="olympiad-btn olympiad-btn-primary submit-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M8 7H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-3m-1 4l-3 3m0 0l-3-3m3 3V4" />
                        </svg>
                        Обновить олимпиаду
                    </button>
            </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.tiny.cloud/1/7v0gkd2f6uam1hogetosjlogqkf8vz98nsxikg6vsrntra2k/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.js"></script>
    <script src="/static/js/components/content-create/ImageCropManager.js"></script>
        <script>
            const contactTypes = [
                <#if contactTypes??>
                <#list contactTypes as type>
            { id: "${type.id}", name: "${type.name?js_string}" }<#if type_has_next>,</#if>
            </#list>
            </#if>
            ];

            <#noparse>

    tinymce.init({
    selector: 'textarea.rich-text-editor',
    menubar: false,
    plugins: 'lists link image code preview',
    toolbar: 'undo redo | bold italic underline | bullist numlist | link | preview',
    height: 300,
    language: 'ru',
    branding: false,
    content_style: 'body { font-family: Arial, sans-serif; font-size: 14px; color: #1e293b; }'
    });

    let imageCropManager;

    document.addEventListener('DOMContentLoaded', () => {
    imageCropManager = new ImageCropManager({
    inputId: 'mainImageInput',
    previewId: 'imagePreview',
    previewContainerSelector: '.image-preview-container',
    croppedPreviewSelector: '.cropped-preview',
    cropButtonId: 'cropButton',
    cancelCropId: 'cancelCrop',
    changeCropId: 'changeCrop',
    croppedImageId: 'croppedImage',
    aspectRatio: 1.2,
    autoCropArea: 0.8,
    cropBoxResizable: true,
    maxFileSize: 5 * 1024 * 1024,
    outputWidth: 1200,
    outputHeight: 1000,
    outputQuality: 0.9,
    onError: function(message) {
    const errorElement = document.getElementById('imageError');
    errorElement.textContent = message;
    errorElement.style.display = 'block';
    },
    onCropComplete: function(blob, dataUrl) {
    const errorElement = document.getElementById('imageError');
    errorElement.style.display = 'none';
    }
    });

        const toggleBtn = document.getElementById('toggleOrganizers');
        const container = document.getElementById('organizersContainer');

        toggleBtn.addEventListener('click', () => {
            const isHidden = container.style.display === 'none';
            container.style.display = isHidden ? 'block' : 'none';
            container.classList.toggle('open', isHidden);

            const pathD = isHidden ? 'M19 14l-7-7-7 7' : 'M19 9l-7 7-7-7';
            const labelText = isHidden ? 'Скрыть организаторов' : 'Показать организаторов';
            toggleBtn.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="${pathD}" />
        </svg>
        ${labelText}
    `;
        });

        document.getElementById('addContact').addEventListener('click', function() {
            const contactsDiv = document.getElementById('contacts');
            const contactCards = contactsDiv.querySelectorAll('.contact-card');
            const contactCount = contactCards.length;

            const newContact = document.createElement('div');
            newContact.className = 'contact-card';
            newContact.innerHTML = `
                <div>
                    <select name="contacts[${contactCount}].contactType" class="olympiad-select contact-type-select" required>
                        <option value="" disabled selected>Выберите тип контакта</option>
                        ${contactTypes.map(type => `<option value="${type.id}">${type.name}</option>`).join('')}
                    </select>
                </div>
                <div>
                    <input type="text" name="contacts[${contactCount}].info" placeholder="Ссылка или контакт" class="olympiad-input">
                </div>
                <button type="button" class="olympiad-btn olympiad-btn-outline remove-contact">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                    Удалить
                </button>
            `;
            contactsDiv.appendChild(newContact);
            reindexContacts();
        });


        document.getElementById('contacts').addEventListener('click', function(e) {
            if (e.target.closest('.remove-contact')) {
                const contactItem = e.target.closest('.contact-card');
                const contactsDiv = document.getElementById('contacts');
                if (contactItem) {
                    contactsDiv.removeChild(contactItem);
                    reindexContacts();
                }
            }
        });

        function reindexContacts() {
            const contactCards = document.querySelectorAll('#contacts .contact-card');
            contactCards.forEach(function(card, index) {
                const select = card.querySelector('select');
                const input = card.querySelector('input[type="text"]');
                select.name = `contacts[${index}].contactType`;
                input.name = `contacts[${index}].info`;
            });
        }
    });

  </script>
    </#noparse>
</@main.layout>