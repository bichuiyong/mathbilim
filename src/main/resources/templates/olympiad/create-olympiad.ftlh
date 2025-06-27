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

        .file-upload {
            position: relative;
            border: 1px dashed var(--border);
            border-radius: 0.5rem;
            padding: 2rem;
            text-align: center;
            background-color: #f8fafc;
            transition: all 0.15s ease;
        }

        .file-upload:hover {
            border-color: var(--primary);
            background-color: rgba(37, 99, 235, 0.05);
        }

        .file-upload input {
            position: absolute;
            inset: 0;
            opacity: 0;
            cursor: pointer;
        }

        .file-upload-label {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 0.5rem;
            color: var(--text-light);
            font-size: 0.875rem;
        }

        .file-upload-label svg {
            width: 1.5rem;
            height: 1.5rem;
            color: var(--primary);
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
            align-items: center;
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
            max-height: 300px;
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
                Создание новой олимпиады
            </h1>
        </header>

        <div class="form-content">
            <form id="olympiadForm" action="/olympiad/add" method="POST" enctype="multipart/form-data">
                <div class="olympiad-grid">

                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>

                    <input type="hidden" name="creatorId" value="${user.id}">

                    <div class="form-group full-width">
                        <label for="title" class="olympiad-label required">Название олимпиады</label>
                        <@spring.formInput "olympiadCreateDto.title" "placeholder='Веддите название' class='olympiad-input'"/>
                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                    </div>

                    <div class="form-group full-width">
                        <label for="info" class="olympiad-label required">Описание</label>
                        <@spring.formTextarea "olympiadCreateDto.info" "placeholder='Веддите подробное описание олимпиады' class='olympiad-textarea'"/>
                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                    </div>

                    <div class="form-group">
                        <label for="start_date" class="olympiad-label required">Дата начала</label>
                        <@spring.formInput "olympiadCreateDto.startDate" 'type="date" class="olympiad-input"' 'date'/>
                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                    </div>

                    <div class="form-group">
                        <label for="end_date" class="olympiad-label required">Дата окончания</label>
                        <@spring.formInput "olympiadCreateDto.endDate" 'type="date" class="olympiad-input"' 'date'/>
                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                    </div>

                    <#if olympiadDateError??>
                        <p class="text-danger small">${olympiadDateError}</p>
                    </#if>

                    <div class="form-group full-width">
                        <label for="rules" class="olympiad-label required">Правила участия</label>
                        <@spring.formTextarea "olympiadCreateDto.rules" 'class="olympiad-textarea"'/>
                        <@spring.showErrors "<br>", "text-danger small mt-1"/>
                    </div>

                    <div class="form-group full-width">
                        <label>Изображение</label>
                        <div class="file-upload">
                            <label class="file-upload-label">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                     stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" />
                                </svg>
                                <span>Перетащите изображение или кликните для загрузки</span>
                                <span style="color: var(--primary-light); font-size: 0.75rem;">PNG, JPG (макс.
                                    10MB)</span>
                            </label>
                            <input type="file" id="image" name="imageFile" value="${olympiadCreateDto.imageFile!}" accept="image/png, image/jpeg">
                            <@spring.showErrors "<br>", "text-danger small mt-1"/>
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
                        <div id="organizersContainer" class="checkbox-group">
<#--                            js-->
                        </div>
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
                                </#list>
                            </#if>
                            <#if stageError??>
                                <p class="text-danger small">
                                    ${stageError}</p>
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
        <script>
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

            window.addEventListener('DOMContentLoaded', () => {
                initializeContactTypes();
                loadOrganizations();
                const stageCountSelect = document.getElementById('stageCount');
                const savedStageCount = parseInt(stageCountSelect.value) || 1;
                if (savedStageCount > 1) {
                    stageCountSelect.value = savedStageCount;
                    stageCountSelect.dispatchEvent(new Event('change'));
                }
            });

            async function loadContactTypes(selectElement) {
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
                        selectElement.appendChild(option);
                    });
                } catch (error) {
                    console.error('Error loading contact types:', error);
                    selectElement.innerHTML = '<option value="">Ошибка загрузки типов контактов</option>';
                }
            }

            function initializeContactTypes() {
                document.querySelectorAll('.contact-type-select').forEach(select => {
                    loadContactTypes(select);
                });
            }

            document.getElementById('addContact').addEventListener('click', function () {
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
                });

                reindexContacts();
            });

            document.querySelectorAll('.remove-contact').forEach(btn => {
                btn.addEventListener('click', function () {
                    const contactItem = this.closest('.contact-card');
                    const contactsDiv = document.getElementById('contacts');
                    contactsDiv.removeChild(contactItem);
                    reindexContacts();
                });
            });

            window.addEventListener('DOMContentLoaded', () => {
                initializeContactTypes();
                loadOrganizations();
            });

            const fileInput = document.querySelector('.file-upload input[type="file"]');
            const fileUploadLabel = document.querySelector('.file-upload-label');
            fileInput.addEventListener('change', function () {
                if (this.files.length > 0) {
                    fileUploadLabel.innerHTML = `
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <span>${this.files[0].name}</span>
                <span style="color: var(--primary-light); font-size: 0.75rem;">${(this.files[0].size / 1024 / 1024).toFixed(2)} MB</span>
            `;
                }
            });

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

            function reindexContacts() {
                const contactCards = document.querySelectorAll('#contacts .contact-card');
                contactCards.forEach((card, index) => {
                    const select = card.querySelector('select');
                    const input = card.querySelector('input[type="text"]');
                    select.name = `contacts[${index}].contactType`;
                    input.name = `contacts[${index}].info`;
                });
            }
        </script>
        </#noparse>
</@main.layout>