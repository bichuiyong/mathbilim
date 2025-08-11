<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="row justify-content-center mt-4">
        <div class="col-lg-8 col-md-10">
            <div class="card shadow-sm">
                <div class="card-header  bg-light text-dark">
                    <h4 class="mb-0">
                        <i class="fas fa-building me-2"></i>
                        Создание новой организации
                    </h4>
                </div>

                <div class="card-body p-4">
                    <form action="/organizations/create" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>

                        <div class="mb-4">
                            <label for="name" class="form-label fw-semibold">
                                Название организации <span class="text-danger">*</span>
                            </label>
                            <@spring.formInput "organizationDto.name", 'class="form-control form-control-lg" placeholder="Введите название организации"'/>
                            <@spring.showErrors "organizationDto.name", "text-danger small"/>
                            <div class="form-text">
                                Полное официальное название вашей организации
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="description" class="form-label fw-semibold">
                                Описание организации <span class="text-danger">*</span>
                            </label>
                            <@spring.formTextarea "organizationDto.description", 'class="form-control" rows="5" placeholder="Расскажите о деятельности, целях и достижениях вашей организации..."'/>
                            <@spring.showErrors "organizationDto.description", "text-danger small"/>
                            <div class="form-text">
                                Подробное описание деятельности организации (минимум 50 символов)
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="url" class="form-label fw-semibold">
                                Веб-сайт организации
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-globe"></i>
                                </span>
                                <@spring.formInput "organizationDto.url", 'class="form-control" placeholder="https://example.com" type="url"'/>
                            </div>
                            <@spring.showErrors '<br>',"text-danger small"/>
                            <div class="form-text">
                                Официальный сайт организации (необязательно)
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="avatarFile" class="form-label fw-semibold">
                                Логотип организации
                            </label>
                            <div class="upload-area border-2 border-dashed rounded p-4 text-center bg-light">
                                <div class="mb-3">
                                    <i class="fas fa-cloud-upload-alt fa-3x text-muted"></i>
                                </div>
                                <input type="file"
                                       name="avatarFile"
                                       id="avatarFile"
                                       class="form-control d-none"
                                       accept="image/*"
                                       onchange="previewImage(event)">
                                <label for="avatarFile" class="btn btn-outline-primary mb-2">
                                    <i class="fas fa-upload me-2"></i>
                                    Выберите файл
                                </label>
                                <div class="text-muted small">
                                    Поддерживаются форматы: JPG, PNG, GIF (макс. 5 МБ)
                                </div>

                                <!-- Предварительный просмотр -->
                                <div id="imagePreview" class="mt-3 d-none">
                                    <img id="previewImg" class="img-thumbnail" style="max-width: 200px; max-height: 200px;" alt="Предварительный просмотр">
                                    <div class="mt-2">
                                        <button type="button" class="btn btn-sm btn-danger" onclick="removeImage()">
                                            <i class="fas fa-trash me-1"></i>
                                            Удалить
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <@spring.showErrors '<div class="text-danger mt-1 small">' "</div>"/>
                        </div>

                        <div class="alert alert-info">
                            <i class="fas fa-info-circle me-2"></i>
                            <strong>Обратите внимание:</strong>
                            После создания организация будет отправлена на модерацию.
                            Вы получите уведомление о результатах проверки.
                        </div>

                        <div class="d-flex gap-3 justify-content-end">
                            <a href="/organizations" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-2"></i>
                                Отмена
                            </a>
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus me-2"></i>
                                Создать организацию
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <style>
        html, body {
            overflow-x: hidden;
        }

        .upload-area {
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .upload-area:hover {
            border-color: #0d6efd !important;
            background-color: #f8f9fa !important;
        }

        .form-control:focus {
            border-color: #0d6efd;
            box-shadow: 0 0 0 0.2rem rgba(13, 110, 253, 0.25);
        }

        .error {
            color: #dc3545;
            font-size: 0.875rem;
            margin-top: 0.25rem;
        }

        .card {
            border: none;
            border-radius: 0.75rem;
        }

        .card-header {
            border-radius: 0.75rem 0.75rem 0 0 !important;
        }
    </style>

    <script>
        function previewImage(event) {
            const file = event.target.files[0];
            if (file) {
                if (file.size > 5 * 1024 * 1024) {
                    alert('Размер файла превышает 5 МБ');
                    event.target.value = '';
                    return;
                }

                if (!file.type.startsWith('image/')) {
                    alert('Пожалуйста, выберите изображение');
                    event.target.value = '';
                    return;
                }

                const reader = new FileReader();
                reader.onload = function(e) {
                    const preview = document.getElementById('imagePreview');
                    const img = document.getElementById('previewImg');
                    img.src = e.target.result;
                    preview.classList.remove('d-none');
                };
                reader.readAsDataURL(file);
            }
        }

        function removeImage() {
            document.getElementById('avatarFile').value = '';
            document.getElementById('imagePreview').classList.add('d-none');
        }
    </script>

</@main.layout>