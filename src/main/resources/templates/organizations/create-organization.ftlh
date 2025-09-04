<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <div class="row justify-content-center mt-4">
        <div class="col-lg-8 col-md-10">
            <div class="card shadow-sm">
                <div class="card-header bg-light text-dark">
                    <h4 class="mb-0">
                        <i class="fas fa-building me-2"></i>
                        <@spring.message "organization.create.title">Создание новой организации</@spring.message>
                    </h4>
                </div>

                <div class="card-body p-4">
                    <form action="/organizations/create" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>

                        <div class="mb-4">
                            <label for="name" class="form-label fw-semibold">
                                <@spring.message "organization.create.name">Название организации</@spring.message> <span class="text-danger">*</span>
                            </label>
                            <div class="form-group">
                                <@spring.formInput "organizationDto.name" "class=form-control" springMacroRequestContext.getMessage("organization.create.name")/>
                                <@spring.showErrors "organizationDto.name", "text-danger small"/>
                            </div>
                            <div class="form-text">
                                <@spring.message "organization.create.name.help">Полное официальное название вашей организации</@spring.message>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="description" class="form-label fw-semibold">
                                <@spring.message "organization.create.description">Описание организации</@spring.message> <span class="text-danger">*</span>
                            </label>
                            <@spring.formTextarea "organizationDto.description", 'class="form-control" rows="5" placeholder="' + springMacroRequestContext.getMessage("organization.create.description.placeholder","Расскажите о деятельности, целях и достижениях вашей организации...") + '"'/>
                            <@spring.showErrors "organizationDto.description", "text-danger small"/>
                            <div class="form-text">
                                <@spring.message "organization.create.description.help">Подробное описание деятельности организации (минимум 50 символов)</@spring.message>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="url" class="form-label fw-semibold">
                                <@spring.message "organization.create.website">Веб-сайт организации</@spring.message>
                            </label>
                            <div class="input-group">
                            <span class="input-group-text">
                                <i class="fas fa-globe"></i>
                            </span>
                                <@spring.formInput "organizationDto.url", 'class="form-control" placeholder="' +springMacroRequestContext.getMessage("organization.create.website.placeholder","https://example.com") + '" type="url"'/>
                            </div>
                            <@spring.showErrors '<br>',"text-danger small"/>
                            <div class="form-text">
                                <@spring.message "organization.create.website.help">Официальный сайт организации (необязательно)</@spring.message>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label for="avatarFile" class="form-label fw-semibold">
                                <@spring.message "organization.create.logo">Логотип организации</@spring.message>
                            </label>
                            <div class="upload-area border-2 border-dashed rounded p-4 text-center bg-light">
                                <div class="mb-3">
                                    <i class="fas fa-cloud-upload-alt fa-3x text-muted"></i>
                                </div>
                                <input type="file"
                                       name="avatarFile"
                                       id="avatarFile"
                                       class="form-control d-none"
                                       accept=".jpg, .jpeg, .png"
                                       onchange="previewImage(event)">
                                <label for="avatarFile" class="btn btn-outline-primary mb-2">
                                    <i class="fas fa-upload me-2"></i>
                                    <@spring.message "organization.create.chooseFile">Выберите файл</@spring.message>
                                </label>
                                <div class="text-muted small">
                                    <@spring.message "organization.create.logo.help">Поддерживаются форматы: JPG, PNG, GIF (макс. 5 МБ)</@spring.message>
                                </div>

                                <!-- Предварительный просмотр -->
                                <div id="imagePreview" class="mt-3 d-none">
                                    <img id="previewImg" class="img-thumbnail" style="max-width: 200px; max-height: 200px;" alt="<@spring.message 'organization.create.previewAlt'>Предварительный просмотр</@spring.message>">
                                    <div class="mt-2">
                                        <button type="button" class="btn btn-sm btn-danger" onclick="removeImage()">
                                            <i class="fas fa-trash me-1"></i>
                                            <@spring.message "organization.create.delete">Удалить</@spring.message>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <small class="text-danger">
                                ${imageError!""}
                            </small>
                            <@spring.showErrors '<div class="text-danger mt-1 small">' "</div>"/>
                        </div>

                        <div class="alert alert-info">
                            <i class="fas fa-info-circle me-2"></i>
                            <strong><@spring.message "organization.create.noticeTitle">Обратите внимание:</@spring.message></strong>
                            <@spring.message "organization.create.noticeText">После создания организация будет отправлена на модерацию. Вы получите уведомление о результатах проверки.</@spring.message>
                        </div>

                        <div class="d-flex gap-3 justify-content-end">
                            <a href="/organizations" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-2"></i>
                                <@spring.message "organization.create.cancel">Отмена</@spring.message>
                            </a>
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus me-2"></i>
                                <@spring.message "organization.create.submit">Создать организацию</@spring.message>
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