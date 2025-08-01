<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <style>
        :root {
            --primary: #3b82f6;
            --primary-light: #60a5fa;
            --primary-dark: #2563eb;
            --bg: #f1f5f9;
            --card: #ffffff;
            --text: #1e293b;
            --text-light: #6b7280;
            --border: #e5e7eb;
            --error: #ef4444;
            --highlight-bg: #eff6ff;
            --highlight-border: #bfdbfe;
            --shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -2px rgba(0, 0, 0, 0.05);
            --transition: all 0.2s ease-in-out;
        }

        .olympiad-body {
            background: linear-gradient(180deg, var(--bg) 0%, #e5e7eb 100%);
            color: var(--text);
            line-height: 1.6;
            padding: 2.5rem;
            min-height: 100vh;
            font-family: 'Inter', system-ui, sans-serif;
        }

        .olympiad-container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: var(--card);
            border-radius: 1.25rem;
            box-shadow: var(--shadow);
            overflow: hidden;
        }

        .olympiad-header {
            padding: 2rem;
            border-bottom: 1px solid var(--border);
            background: linear-gradient(90deg, #f8fafc, #ffffff);
        }

        .olympiad-title {
            font-size: 1.75rem;
            font-weight: 700;
            color: var(--primary);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .olympiad-title svg {
            width: 1.75rem;
            height: 1.75rem;
            stroke: var(--primary-dark);
        }

        .form-content {
            padding: 2.5rem;
        }

        .olympiad-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 2rem;
        }

        .form-group {
            margin-bottom: 1.75rem;
        }

        .form-group.full-width {
            grid-column: span 2;
        }

        .olympiad-label {
            display: block;
            margin-bottom: 0.5rem;
            font-size: 0.9rem;
            font-weight: 600;
            color: var(--text);
            letter-spacing: -0.01em;
        }

        .olympiad-label.required::after {
            content: '*';
            color: var(--error);
            margin-left: 0.3rem;
        }

        .olympiad-input,
        .olympiad-select,
        .olympiad-textarea {
            width: 100%;
            padding: 0.875rem 1.25rem;
            border: 1px solid var(--border);
            border-radius: 0.75rem;
            font-size: 0.9rem;
            transition: var(--transition);
            background-color: #fff;
        }

        .olympiad-input:focus,
        .olympiad-select:focus,
        .olympiad-textarea:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
        }

        .olympiad-textarea.rich-text-editor {
            min-height: 180px;
            resize: vertical;
            background-color: var(--highlight-bg);
            border: 2px solid var(--highlight-border);
            font-size: 1.05rem;
            box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.05);
        }

        .olympiad-textarea.rich-text-editor:focus {
            border-color: var(--primary-light);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
        }

        .olympiad-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.875rem 1.5rem;
            border-radius: 0.75rem;
            font-size: 0.9rem;
            font-weight: 500;
            cursor: pointer;
            transition: var(--transition);
            border: none;
        }

        .olympiad-btn-primary {
            background-color: var(--primary);
            color: white;
        }

        .olympiad-btn-primary:hover {
            background-color: var(--primary-dark);
            transform: translateY(-1px);
            box-shadow: var(--shadow);
        }

        .olympiad-btn-outline {
            background-color: transparent;
            border: 1px solid var(--border);
            color: var(--text);
        }

        .olympiad-btn-outline:hover {
            background-color: #f8fafc;
            border-color: var(--primary-light);
            color: var(--primary);
        }

        .olympiad-btn svg {
            width: 1.1rem;
            height: 1.1rem;
            margin-right: 0.5rem;
        }

        .olympiad-btn-sm {
            padding: 0.625rem 1rem;
            font-size: 0.8rem;
            line-height: 1;
        }

        .olympiad-btn-sm svg {
            width: 0.9rem;
            height: 0.9rem;
            margin-right: 0.3rem;
        }

        .stages-container {
            margin-top: 1.5rem;
            display: flex;
            flex-direction: column;
            gap: 1.25rem;
        }

        .stage-card {
            padding: 1.5rem;
            border: 1px solid var(--border);
            border-radius: 0.75rem;
            background-color: white;
            transition: var(--transition);
        }

        .stage-card:hover {
            box-shadow: var(--shadow);
        }

        .stage-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 1.25rem;
        }

        .stage-title {
            font-weight: 600;
            color: var(--primary-dark);
            font-size: 1.1rem;
        }

        .contacts-container {
            margin-top: 1.5rem;
            display: flex;
            flex-direction: column;
            gap: 1.25rem;
        }

        .contact-card {
            display: grid;
            grid-template-columns: 1fr 2fr auto;
            gap: 1.25rem;
            align-items: center;
        }

        .checkbox-group {
            padding: 0;
            border: 1px solid var(--border);
            border-radius: 0.75rem;
            background-color: white;
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.3s ease-in-out, padding 0.3s ease-in-out;
        }

        .checkbox-group.open {
            max-height: 300px;
            padding: 1.25rem;
        }

        .checkbox-item {
            display: flex;
            align-items: center;
            gap: 0.75rem;
            padding: 0.5rem 0;
            font-size: 0.9rem;
        }

        .checkbox-item input[type="checkbox"] {
            width: 1.25rem;
            height: 1.25rem;
            accent-color: var(--primary);
            cursor: pointer;
            border-radius: 4px;
        }

        .checkbox-item label {
            cursor: pointer;
            flex-grow: 1;
        }

        .submit-btn {
            width: 100%;
            padding: 1.25rem;
            font-size: 1rem;
            margin-top: 2rem;
            border-radius: 0.75rem;
        }

        .image-upload-container {
            border: 2px dashed var(--border);
            border-radius: 10px;
            padding: 2rem;
            transition: var(--transition);
        }

        .image-upload-container:hover {
            border-color: var(--primary-light);
            background-color: #f8fafc;
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
            border-radius: 10px;
        }

        .cropped-image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .crop-hint {
            font-size: 0.9rem;
            color: var(--text-light);
            margin-bottom: 1.25rem;
            text-align: center;
        }

        .cropper-crop-box {
            border: 2px solid var(--primary);
        }

        .cropper-view-box {
            border: 1px dashed var(--primary-light);
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

            .olympiad-body {
                padding: 1.5rem;
            }
        }
    </style>

    <div class="olympiad-body">
        <div class="olympiad-container">
            <header class="olympiad-header">
                <h1 class="olympiad-title">
                    Создание новой олимпиады
                </h1>
            </header>

            <div class="form-content">
                <form id="olympiadForm" action="/olympiad/create" method="POST" enctype="multipart/form-data">
                    <div class="olympiad-grid">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <input type="hidden" name="creatorId" value="${user.id}">

                        <div class="form-group full-width">
                            <label for="title" class="olympiad-label required">Название олимпиады</label>
                            <@spring.formInput "olympiadCreateDto.title" "placeholder='Введите название' class='olympiad-input'"/>
                            <@spring.showErrors "<br>", "text-danger small mt-1"/>
                        </div>

                        <div class="form-group full-width">
                            <label for="info" class="olympiad-label required">Описание</label>
                            <@spring.formTextarea "olympiadCreateDto.info" "placeholder='Введите подробное описание олимпиады' class='olympiad-textarea rich-text-editor'"/>
                            <@spring.showErrors "<br>", "text-danger small mt-1"/>
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
                            <p class="text-danger small">${olympiadDateError}</p>
                        </#if>

                        <div class="form-group full-width">
                            <label for="rules" class="olympiad-label required">Правила участия</label>
                            <@spring.formTextarea "olympiadCreateDto.rules" 'class="olympiad-textarea rich-text-editor"'/>
                            <@spring.showErrors "<br>", "text-danger small mt-1"/>
                        </div>

                        <div class="form-group full-width">
                            <label class="olympiad-label required">Главное изображение</label>
                            <div class="image-upload-container">
                                <input type="file" id="mainImageInput" name="imageFile" accept="image/*" class="form-control mb-3" required>
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
                            <div id="organizersContainer" class="checkbox-group"></div>
                        </div>

                        <div class="form-group full-width">
                            <label>Контакты</label>
                            <div id="contacts" class="contacts-container">
                                <div class="contact-card">
                                    <select name="contacts[0].contactType" class="olympiad-select contact-type-select" required>
                                        <option value="" disabled>Выберите тип контакта</option>
                                        <#if contactTypes??>
                                            <#list contactTypes as type>
                                                <option value="${type.id}" <#if olympiadCreateDto.contacts?? && (olympiadCreateDto.contacts?size > 0) && olympiadCreateDto.contacts[0].contactType?? && olympiadCreateDto.contacts[0].contactType.id == type.id>selected</#if>>${type.name}</option>
                                            </#list>
                                        </#if>
                                    </select>
                                    <input type="text" name="contacts[0].info" value="<#if olympiadCreateDto.contacts?? && (olympiadCreateDto.contacts?size > 0)>${olympiadCreateDto.contacts[0].info!}</#if>"
                                           placeholder="Ссылка или контакт" class="olympiad-input">
                                    <button type="button" class="olympiad-btn olympiad-btn-outline remove-contact">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                             stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                  d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                        </svg>
                                        Удалить
                                    </button>
                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                </div>
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
                            <label style="margin-bottom: 10px" for="stageCount">Количество этапов</label>
                            <select id="stageCount" name="stage_count" class="olympiad-select">
                                <option value="" disabled selected>Выберите количество этапов</option>
                                <option value="1">1 этап</option>
                                <option value="2">2 этапа</option>
                                <option value="3">3 этапа</option>
                                <option value="4">4 этапа</option>
                                <option value="5">5 этапа</option>
                                <option value="6">6 этапа</option>
                                <option value="7">7 этапа</option>
                            </select>

                            <div class="stages-container" id="stagesContainer">
                                <#if olympiadCreateDto.stages?? && (olympiadCreateDto.stages?size > 0)>
                                    <#list olympiadCreateDto.stages as stage>
                                        <div class="stage-card">
                                            <div class="stage-header">
                                                <span class="stage-title">Этап ${stage.stageOrder}</span>
                                            </div>
                                            <div class="olympiad-grid" style="gap: 1rem;">
                                                <input name="stages[${stage_index}].stageOrder" value="${stage.stageOrder}" type="hidden">
                                                <div class="form-group">
                                                    <label class="olympiad-label required">Дата начала</label>
                                                    <input class="olympiad-input" name="stages[${stage_index}].eventStartDate" type="date" value="${stage.eventStartDate!}" required>
                                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                                </div>
                                                <div class="form-group">
                                                    <label class="olympiad-label required">Дата окончания</label>
                                                    <input class="olympiad-input" name="stages[${stage_index}].eventEndDate" type="date" value="${stage.eventEndDate!}" required>
                                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                                </div>
                                                <div class="form-group">
                                                    <label class="olympiad-label required">Начало регистрации</label>
                                                    <input class="olympiad-input" name="stages[${stage_index}].registrationStart" type="date" value="${stage.registrationStart!}" required>
                                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                                </div>
                                                <div class="form-group">
                                                    <label class="olympiad-label required">Конец регистрации</label>
                                                    <input class="olympiad-input" name="stages[${stage_index}].registrationEnd" type="date" value="${stage.registrationEnd!}" required>
                                                    <@spring.showErrors "<br>", "text-danger small mt-1"/>
                                                </div>
                                                <#if olympiadDateError??>
                                                    <p class="text-danger small">${olympiadDateError}</p>
                                                </#if>
                                            </div>
                                        </div>
                                        <#if dateError??>
                                            <p class="text-danger small">${dateError}</p>
                                        </#if>
                                        <#if dateRangeErrors?? && dateRangeErrors?size != 0>
                                            <#list dateRangeErrors as err>
                                                <p class="text-danger small">${err}</p>
                                            </#list>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if stageError??>
                                    <p class="text-danger small">${stageError}</p>
                                </#if>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="olympiad-btn olympiad-btn-primary submit-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M8 7H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-3m-1 4l-3 3m0 0l-3-3m3 3V4" />
                        </svg>
                        Сохранить олимпиаду
                    </button>
                </form>
            </div>
        </div>
    </div>

    <#noparse>
        <script src="https://cdn.tiny.cloud/1/7v0gkd2f6uam1hogetosjlogqkf8vz98nsxikg6vsrntra2k/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.6.1/cropper.min.js"></script>
        <script src="/static/js/components/content-create/ImageCropManager.js"></script>

        <script>
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

            function updateContactTypeOptions() {
                const contactSelects = document.querySelectorAll('.contact-type-select');
                const selectedTypes = new Set();

                contactSelects.forEach(select => {
                    if (select.value) {
                        selectedTypes.add(select.value);
                    }
                });

                contactSelects.forEach(select => {
                    const currentValue = select.value;
                    const options = select.querySelectorAll('option');

                    options.forEach(option => {
                        if (option.value === '' || option.disabled) {
                            return;
                        }

                        if (selectedTypes.has(option.value) && option.value !== currentValue) {
                            option.disabled = true;
                            option.style.color = '#999';
                            option.textContent = option.textContent.replace(' (уже выбрано)', '') + ' (уже выбрано)';
                        } else {
                            option.disabled = false;
                            option.style.color = '';
                            option.textContent = option.textContent.replace(' (уже выбрано)', '');
                        }
                    });
                });
            }

            async function loadContactTypes(selectElement, selectedValue = null) {
                try {
                    const response = await fetch('/api/contactType', {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    });

                    if (!response.ok) {
                        throw new Error('Failed to fetch contact types');
                    }

                    const contactTypes = await response.json();
                    selectElement.innerHTML = '<option value="" disabled selected>Выберите тип контакта</option>';

                    if (!contactTypes || contactTypes.length === 0) {
                        selectElement.innerHTML = '<option value="">Нет доступных типов контактов</option>';
                        return;
                    }

                    contactTypes.forEach(type => {
                        const option = document.createElement('option');
                        option.value = type.id;
                        option.textContent = type.name;

                        if (selectedValue && selectedValue == type.id) {
                            option.selected = true;
                        }

                        selectElement.appendChild(option);
                    });

                    selectElement.addEventListener('change', updateContactTypeOptions);
                    updateContactTypeOptions();

                } catch (error) {
                    console.error('Error loading contact types:', error);
                    selectElement.innerHTML = '<option value="">Ошибка загрузки типов контактов</option>';
                }
            }

            function initializeContactTypes() {
                document.querySelectorAll('.contact-type-select').forEach(select => {
                    const currentValue = select.value;
                    loadContactTypes(select, currentValue);
                });
            }

            function addContactHandler() {
                const contactsDiv = document.getElementById('contacts');
                const contactCards = contactsDiv.querySelectorAll('.contact-card');
                const contactCount = contactCards.length;

                const newContact = document.createElement('div');
                newContact.className = 'contact-card';
                newContact.innerHTML = `
        <select name="contacts[${contactCount}].contactType" class="olympiad-select contact-type-select" required></select>
        <input type="text" name="contacts[${contactCount}].info" placeholder="Ссылка или контакт" class="olympiad-input">
        <button type="button" class="olympiad-btn olympiad-btn-outline remove-contact">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
            </svg>
            Удалить
        </button>
    `;
                contactsDiv.appendChild(newContact);

                loadContactTypes(newContact.querySelector('.contact-type-select'));

                newContact.querySelector('.remove-contact').addEventListener('click', function () {
                    contactsDiv.removeChild(newContact);
                    reindexContacts();
                    updateContactTypeOptions();
                });

                reindexContacts();
            }

            function initializeRemoveContactHandlers() {
                document.querySelectorAll('.remove-contact').forEach(btn => {
                    btn.addEventListener('click', function () {
                        const contactItem = this.closest('.contact-card');
                        const contactsDiv = document.getElementById('contacts');
                        contactsDiv.removeChild(contactItem);
                        reindexContacts();
                        updateContactTypeOptions();
                    });
                });
            }

            function reindexContacts() {
                const contactCards = document.querySelectorAll('#contacts .contact-card');
                contactCards.forEach((card, index) => {
                    const select = card.querySelector('select');
                    const input = card.querySelector('input[type="text"]');
                    select.name = `contacts[${index}].contactType`;
                    input.name = `contacts[${index}].info`;
                });
            }

            async function loadOrganizations() {
                const container = document.getElementById('organizersContainer');
                container.innerHTML = '<p>Загрузка организаторов...</p>';

                try {
                    const response = await fetch('/api/organizations/name', {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    });

                    if (!response.ok) {
                        throw new Error('Failed to fetch organizations');
                    }

                    const organizations = await response.json();
                    container.innerHTML = '';

                    if (!organizations || organizations.length === 0) {
                        container.innerHTML = '<p>Нет доступных организаций</p>';
                        return;
                    }

                    organizations.forEach(org => {
                        const checkboxItem = document.createElement('div');
                        checkboxItem.className = 'checkbox-item';
                        checkboxItem.innerHTML = `
                <input type="checkbox" name="organizationIds" value="${org.id}" id="org_${org.id}">
                <label for="org_${org.id}">${org.name}</label>
            `;
                        container.appendChild(checkboxItem);
                    });
                } catch (error) {
                    console.error('Error loading organizations:', error);
                    container.innerHTML = '<p>Ошибка загрузки организаторов</p>';
                }
            }

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
                    onError: (message) => {
                        const errorElement = document.getElementById('imageError');
                        errorElement.textContent = message;
                        errorElement.style.display = 'block';
                    },
                    onCropComplete: (blob, dataUrl) => {
                        const errorElement = document.getElementById('imageError');
                        errorElement.style.display = 'none';
                    }
                });

                document.getElementById('olympiadForm').addEventListener('submit', (e) => {
                    if (!imageCropManager.hasCroppedImage()) {
                        e.preventDefault();
                        const errorElement = document.getElementById('imageError');
                        errorElement.textContent = 'Пожалуйста, выберите и обрежьте изображение перед сохранением.';
                        errorElement.style.display = 'block';
                    }
                });

                document.getElementById('toggleOrganizers').addEventListener('click', function () {
                    const organizersContainer = document.getElementById('organizersContainer');
                    const isHidden = organizersContainer.classList.contains('open');
                    organizersContainer.classList.toggle('open', !isHidden);
                    this.innerHTML = `
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="${isHidden ? 'M19 9l-7 7-7-7' : 'M19 14l-7-7-7 7'}" />
            </svg>
            ${isHidden ? 'Показать организаторов' : 'Скрыть организаторов'}
        `;
                });

                document.getElementById('stageCount').addEventListener('change', function () {
                    const count = parseInt(this.value);
                    const container = document.getElementById('stagesContainer');
                    container.innerHTML = '';

                    for (let i = 0; i < count; i++) {
                        const stageCard = document.createElement('div');
                        stageCard.className = 'stage-card';
                        stageCard.innerHTML = `
                <div class="stage-header">
                    <span class="stage-title">Этап ${i + 1}</span>
                </div>
                <div class="olympiad-grid" style="gap: 1rem;">
                    <input name="stages[${i}].stageOrder" value="${i + 1}" type="hidden">
                    <div class="form-group">
                        <label class="olympiad-label required">Дата начала</label>
                        <input class="olympiad-input" name="stages[${i}].eventStartDate" type="date" required>
                    </div>
                    <div class="form-group">
                        <label class="olympiad-label required">Дата окончания</label>
                        <input class="olympiad-input" name="stages[${i}].eventEndDate" type="date" required>
                    </div>
                    <div class="form-group">
                        <label class="olympiad-label required">Начало регистрации</label>
                        <input class="olympiad-input" name="stages[${i}].registrationStart" type="date" required>
                    </div>
                    <div class="form-group">
                        <label class="olympiad-label required">Конец регистрации</label>
                        <input class="olympiad-input" name="stages[${i}].registrationEnd" type="date" required>
                    </div>
                </div>
            `;
                        container.appendChild(stageCard);
                    }
                });

                initializeContactTypes();

                loadOrganizations();

                const stageCountSelect = document.getElementById('stageCount');
                const savedStageCount = parseInt(stageCountSelect.value) || 1;
                if (savedStageCount > 1) {
                    stageCountSelect.value = savedStageCount;
                    stageCountSelect.dispatchEvent(new Event('change'));
                }

                document.getElementById('addContact').addEventListener('click', addContactHandler);
                initializeRemoveContactHandlers();
            });
        </script>
    </#noparse>
</@main.layout>