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
                                <select name="organizationIds" id="organizations" class="form-select" multiple>
                                    <#list organizations as org>
                                        <option value="${org.id}">${org.name}</option>
                                    </#list>
                                </select>
                                <div class="form-text">Удерживайте Ctrl для выбора нескольких организаций</div>
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
    </style>
</@layout.layout>