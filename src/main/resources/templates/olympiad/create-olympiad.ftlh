<#import "../layout.ftlh" as main>
<#import "spring.ftl" as spring>
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

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
        }

        body {
            background-color: var(--bg);
            color: var(--text);
            line-height: 1.5;
            padding: 2rem;
            min-height: 100vh;
        }

        .container {
            max-width: 900px;
            margin: 0 auto;
            background-color: var(--card);
            border-radius: 1rem;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        header {
            padding: 1.5rem 2rem;
            border-bottom: 1px solid var(--border);
        }

        h1 {
            font-size: 1.5rem;
            font-weight: 600;
            color: var(--primary);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        h1 svg {
            width: 1.5rem;
            height: 1.5rem;
        }

        .form-content {
            padding: 2rem;
        }

        .grid {
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

        label {
            display: block;
            margin-bottom: 0.5rem;
            font-size: 0.875rem;
            font-weight: 500;
            color: var(--text);
        }

        label.required::after {
            content: '*';
            color: var(--error);
            margin-left: 0.25rem;
        }

        input,
        select,
        textarea {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid var(--border);
            border-radius: 0.5rem;
            font-size: 0.875rem;
            transition: all 0.15s ease;
        }

        input:focus,
        select:focus,
        textarea:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
        }

        textarea {
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

        .btn {
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

        .btn-primary {
            background-color: var(--primary);
            color: white;
        }

        .btn-primary:hover {
            background-color: var(--primary-dark);
        }

        .btn-outline {
            background-color: transparent;
            border: 1px solid var(--border);
            color: var(--text);
        }

        .btn-outline:hover {
            background-color: #f1f5f9;
        }

        .btn svg {
            width: 1rem;
            height: 1rem;
            margin-right: 0.5rem;
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

        .organizers-container {
            margin-top: 1rem;
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .organizer-card {
            display: grid;
            grid-template-columns: 2fr 1.5fr auto;
            gap: 1rem;
            align-items: center;
        }

        .submit-btn {
            width: 100%;
            padding: 1rem;
            font-size: 1rem;
            margin-top: 1.5rem;
        }

        @media (max-width: 768px) {
            .grid {
                grid-template-columns: 1fr;
            }

            .form-group.full-width {
                grid-column: span 1;
            }

            .organizer-card {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>
<div class="container">
    <header>
        <h1>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            Создание новой олимпиады
        </h1>
    </header>

    <div class="form-content">
        <form id="olympiadForm" action="/api/olympiads" method="POST" enctype="multipart/form-data">
            <div class="grid">
                <!-- Основная информация -->
                <div class="form-group full-width">
                    <label for="title" class="required">Название олимпиады</label>
                    <input type="text" id="title" name="title" required placeholder="Введите название">
                </div>

                <div class="form-group full-width">
                    <label for="info" class="required">Описание</label>
                    <textarea id="info" name="info" required placeholder="Подробное описание олимпиады"></textarea>
                </div>

                <div class="form-group">
                    <label for="start_date" class="required">Дата начала</label>
                    <input type="date" id="start_date" name="start_date" required>
                </div>

                <div class="form-group">
                    <label for="end_date" class="required">Дата окончания</label>
                    <input type="date" id="end_date" name="end_date" required>
                </div>

                <div class="form-group full-width">
                    <label for="rules" class="required">Правила участия</label>
                    <textarea id="rules" name="rules" required
                              placeholder="Условия и требования для участников"></textarea>
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
                        <input type="file" id="image" name="image" accept="image/png, image/jpeg">
                    </div>
                </div>

                <!-- Организаторы -->
                <div class="form-group full-width">
                    <label>Организаторы</label>
                    <div id="organizers" class="organizers-container">
                        <div class="organizer-card">
                            <input type="text" name="organizers[]" placeholder="Название организации">
                            <input type="text" name="organizer_contacts[]" placeholder="Контактные данные">
                            <button type="button" class="btn btn-outline remove-organizer">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                     stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                </svg>
                                Удалить
                            </button>
                        </div>
                    </div>
                    <button type="button" id="addOrganizer" class="btn btn-outline" style="margin-top: 0.5rem;">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                        </svg>
                        Добавить организатора
                    </button>
                </div>

                <!-- Контакты -->
                <div class="form-group full-width">
                    <label for="contacts" class="required">Контактная информация</label>
                    <textarea id="contacts" name="contacts" required
                              placeholder="Email, телефон, адрес для связи"></textarea>
                </div>

                <!-- Этапы -->
                <div class="form-group full-width">
                    <label for="stageCount">Количество этапов</label>
                    <select id="stageCount" name="stage_count">
                        <option value="1">1 этап</option>
                        <option value="2">2 этапа</option>
                        <option value="3">3 этапа</option>
                        <option value="4">4 этапа</option>
                    </select>

                    <div class="stages-container" id="stagesContainer">
                        <div class="stage-card">
                            <div class="stage-header">
                                <span class="stage-title">Этап 1</span>
                            </div>
                            <div class="grid" style="gap: 1rem;">
                                <div class="form-group">
                                    <label class="required">Дата начала</label>
                                    <input type="date" name="stage_start[]" required>
                                </div>
                                <div class="form-group">
                                    <label class="required">Дата окончания</label>
                                    <input type="date" name="stage_end[]" required>
                                </div>
                                <div class="form-group full-width">
                                    <label>Описание этапа</label>
                                    <textarea name="stage_description[]" placeholder="Детали этапа"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-primary submit-btn">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M8 7H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-3m-1 4l-3 3m0 0l-3-3m3 3V4" />
                </svg>
                Сохранить олимпиаду
            </button>
        </form>
    </div>
</div>

<script>
    // Динамическое добавление этапов
    document.getElementById('stageCount').addEventListener('change', function () {
        const count = parseInt(this.value);
        const container = document.getElementById('stagesContainer');

        // Очищаем контейнер
        container.innerHTML = '';

        // Добавляем нужное количество этапов
        for (let i = 0; i < count; i++) {
            const stageCard = document.createElement('div');
            stageCard.className = 'stage-card';
            stageCard.innerHTML = `
                    <div class="stage-header">
                        <span class="stage-title">Этап ${i+1}</span>
                    </div>
                    <div class="grid" style="gap: 1rem;">
                        <div class="form-group">
                            <label class="required">Дата начала</label>
                            <input type="date" name="stage_start[]" required>
                        </div>
                        <div class="form-group">
                            <label class="required">Дата окончания</label>
                            <input type="date" name="stage_end[]" required>
                        </div>
                        <div class="form-group full-width">
                            <label>Описание этапа</label>
                            <textarea name="stage_description[]" placeholder="Детали этапа"></textarea>
                        </div>
                    </div>
                `;
            container.appendChild(stageCard);
        }
    });

    // Добавление организаторов
    document.getElementById('addOrganizer').addEventListener('click', function () {
        const organizersDiv = document.getElementById('organizers');
        const newOrganizer = document.createElement('div');
        newOrganizer.className = 'organizer-card';
        newOrganizer.innerHTML = `
                <input type="text" name="organizers[]" placeholder="Название организации">
                <input type="text" name="organizer_contacts[]" placeholder="Контактные данные">
                <button type="button" class="btn btn-outline remove-organizer">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                    Удалить
                </button>
            `;
        organizersDiv.appendChild(newOrganizer);

        // Добавляем обработчик для новой кнопки удаления
        newOrganizer.querySelector('.remove-organizer').addEventListener('click', function () {
            organizersDiv.removeChild(newOrganizer);
        });
    });

    // Обработчики для существующих кнопок удаления организаторов
    document.querySelectorAll('.remove-organizer').forEach(btn => {
        btn.addEventListener('click', function () {
            const organizerItem = this.closest('.organizer-card');
            document.getElementById('organizers').removeChild(organizerItem);
        });
    });

    // Обработка загрузки файла
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
</script>
</body>
</@main.layout>