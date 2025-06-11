<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as layout>

<@layout.layout>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">
                            <i class="fas fa-calendar-plus me-2"></i>Создать мероприятие
                        </h4>
                    </div>
                    <div class="card-body">
                        <form action="/events/create" method="post" enctype="multipart/form-data" novalidate>
                            <@layout.csrf />
                            <@spring.formHiddenInput "event.user.id"/>


                            <div class="mb-3">
                                <label for="name" class="form-label">Название мероприятия <span
                                            class="text-danger">*</span></label>
                                <@spring.formInput "event.name" 'class="form-control" id="name" placeholder="Введите название мероприятия"' />
                                <@spring.showErrors "<br>" "text-danger small" />
                            </div>

                            <!-- Тип мероприятия -->
                            <div class="mb-3">
                                <#assign typeMap = {}>
                                <#list eventsTypes as type>
                                    <#assign typeMap = typeMap + {type.id?string: type.name}>
                                </#list>

                                <label for="type" class="form-label">Тип мероприятия <span class="text-danger">*</span></label>
                                <@spring.formSingleSelect "event.type.id" typeMap 'class="form-select" id="type"' />
                                <@spring.showErrors "<br>" "text-danger small" />
                            </div>

                            <!-- Организация -->
                            <div class="mb-3">
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

                            <!-- Даты проведения -->
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="startDate" class="form-label">Дата начала <span class="text-danger">*</span></label>
                                        <@spring.formInput "event.startDate",
                                        'class="form-control" id="startDate"',
                                        "text" />
                                        <@spring.showErrors "<br>" "text-danger small" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="endDate" class="form-label">Дата окончания</label>
                                        <@spring.formInput
                                        "event.endDate",
                                        'class="form-control" id="endDate"',
                                        "text" />
                                        <@spring.showErrors "<br>" "text-danger small" />
                                        <div class="form-text">Оставьте пустым, если мероприятие в один день</div>
                                    </div>
                                </div>
                            </div>

                            <!-- Главное изображение -->
                            <div class="mb-3">
                                <label for="mainImage" class="form-label">Главное изображение</label>
                                <input type="file" name="mainImageMp" id="mainImage" class="form-control"
                                       accept="image/*">
                                <div class="form-text">Рекомендуемый размер: 1200x600 пикселей. Максимальный размер: 5
                                    МБ
                                </div>
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

                            <!-- Описание мероприятия -->
                            <div class="mb-3">
                                <label for="content" class="form-label">Описание мероприятия <span
                                            class="text-danger">*</span></label>
                                <@spring.formTextarea "event.content" 'class="form-control" id="content" rows="10" placeholder="Введите подробное описание мероприятия"' />
                                <@spring.showErrors "<br>" "text-danger small" />
                            </div>

                            <!-- Прикрепленные файлы -->
                            <div class="mb-4">
                                <label for="attachments" class="form-label">Прикрепленные файлы</label>
                                <input type="file" name="attachments" id="attachments" class="form-control" multiple>
                                <div class="form-text">
                                    Можно загрузить несколько файлов. Поддерживаемые форматы: PDF, DOC, DOCX, XLS, XLSX,
                                    JPG, PNG.
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

    <!-- TinyMCE и скрипты -->
    <script src="https://cdn.tiny.cloud/1/z66zloybrfzhb552oyl9fmnwcuplxmajqn017ne4zn5zg457/tinymce/6/tinymce.min.js"
            referrerpolicy="origin"></script>
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

        .tox-tinymce {
            border-radius: 6px !important;
        }

        #organizations {
            min-height: 120px;
        }

        .form-text {
            font-size: 0.875rem;
            color: #6c757d;
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
    </style>

    <script>
        document.getElementById('mainImage').addEventListener('change', function(e) {
            const file = e.target.files[0];
            const previewContainer = document.getElementById('imagePreviewContainer');
            const previewImage = document.getElementById('imagePreview');

            if (file) {
                if (file.size > 5 * 1024 * 1024) {
                    alert('Размер файла не должен превышать 5 МБ');
                    this.value = '';
                    previewContainer.style.display = 'none';
                    return;
                }

                const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
                if (!allowedTypes.includes(file.type)) {
                    alert('Поддерживаются только изображения (JPG, PNG, GIF, WebP)');
                    this.value = '';
                    previewContainer.style.display = 'none';
                    return;
                }

                // Создание превью
                const reader = new FileReader();
                reader.onload = function(e) {
                    previewImage.src = e.target.result;
                    previewContainer.style.display = 'block';
                };
                reader.readAsDataURL(file);
            } else {
                previewContainer.style.display = 'none';
            }
        });

        document.getElementById('removeImage').addEventListener('click', function() {
            document.getElementById('mainImage').value = '';
            document.getElementById('imagePreviewContainer').style.display = 'none';
        });

        document.querySelectorAll('.organization-checkbox').forEach(checkbox => {
            checkbox.addEventListener('change', function() {
                const selectedCount = document.querySelectorAll('.organization-checkbox:checked').length;
                const organizationsContainer = document.querySelector('.organizations-container');

                organizationsContainer.setAttribute('data-selected', selectedCount);
            });
        });
    </script>
</@layout.layout>