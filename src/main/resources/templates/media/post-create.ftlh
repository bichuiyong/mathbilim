<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>
    <link rel="stylesheet" href="/static/css/post-create.css">

    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-12 col-lg-10 col-xl-8">
                <div class="post-create-card">
                    <div class="post-header">
                        <div class="icon">
                            <i class="fas fa-edit"></i>
                        </div>
                        <h2>Создание поста</h2>
                        <p>Заполните информацию для создания нового поста</p>
                    </div>

                    <form action="/posts/create" method="post" enctype="multipart/form-data" id="postForm">
                        <@main.csrf/>
                        <@spring.formHiddenInput "post.user.id"/>

                        <div class="form-grid">
                            <!-- Название поста -->
                            <div class="form-group">
                                <label for="title" class="form-label">
                                    <i class="fas fa-heading me-2 text-primary"></i>
                                    Название поста
                                </label>
                                <@spring.formInput
                                "post.title"
                                'type="text" id="title" class="form-control" placeholder="Введите название поста" maxlength="500"'
                                />
                                <@spring.showErrors "<br>" "error text-danger"/>
                            </div>

                            <!-- Тип поста -->
                            <div class="form-group">
                                <label for="type.id" class="form-label">
                                    <i class="fas fa-tags me-2 text-primary"></i>
                                    Тип поста
                                </label>
                                <select name="type.id" id="typeId" class="form-control" required>
                                    <option value="">Выберите тип поста</option>
                                    <#list postTypes as postType>
                                        <option value="${postType.id}"
                                                <#if post.type?? && post.type.id == postType.id>selected</#if>>
                                            ${postType.name}
                                        </option>
                                    </#list>
                                </select>
                                <@spring.showErrors "<br>" "error text-danger"/>
                            </div>
                        </div>

                        <!-- URL slug -->
                        <div class="form-group">
                            <label for="slug" class="form-label">
                                <i class="fas fa-link me-2 text-primary"></i>
                                URL адрес (slug)
                            </label>
                            <div class="input-with-prefix">
                                <span class="input-prefix">edu.gov.kg/posts/</span>
                                <@spring.formInput
                                "post.slug"
                                'type="text" id="slug" class="form-control" placeholder="url-adresa-posta" maxlength="500"'
                                />
                            </div>
                            <div class="form-text">
                                Только строчные буквы, цифры и дефисы. Автоматически генерируется из названия.
                            </div>
                            <@spring.showErrors "<br>" "error text-danger"/>
                        </div>

                        <!-- Контент -->
                        <div class="form-group">
                            <label for="content" class="form-label">
                                <i class="fas fa-file-text me-2 text-primary"></i>
                                Содержание поста
                            </label>
                            <@spring.formTextarea
                            "post.content"
                            'id="content" class="form-control" rows="15"'
                            />
                            <div class="form-text">
                                Используйте панель инструментов для форматирования текста
                            </div>
                            <@spring.showErrors "<br>" "error text-danger"/>
                        </div>

                        <div class="form-group">
                            <label for="attachments" class="form-label">
                                <i class="fas fa-paperclip me-2 text-primary"></i>
                                Прикрепленные файлы (опционально)
                            </label>
                            <input
                                    type="file"
                                    class="form-control"
                                    id="attachments"
                                    name="attachments"
                                    multiple
                                    accept=".pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.zip,.rar,.7z,.jpg,.jpeg,.png,.gif,.bmp,.svg,.webp,.mp3,.wav,.flac,.mp4,.avi,.mkv,.mov">
                            <div class="form-text">
                                📎 Поддерживаемые файлы: документы, изображения, архивы, аудио, видео<br>
                                💡 Изображения можно вставлять в текст через редактор или прикреплять как файлы
                            </div>
                            <div id="filePreview" class="mt-3"></div>
                        </div>

                        <!-- Кнопки действий -->
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-save me-2"></i>
                                Создать пост
                            </button>

                            <button type="button" class="btn btn-secondary btn-lg" id="saveAsDraft">
                                <i class="fas fa-file-alt me-2"></i>
                                Сохранить как черновик
                            </button>

                            <button type="button" class="btn btn-outline-primary btn-lg" id="previewBtn">
                                <i class="fas fa-eye me-2"></i>
                                Предварительный просмотр
                            </button>

                            <a href="/posts" class="btn btn-outline-danger btn-lg">
                                <i class="fas fa-times me-2"></i>
                                Отмена
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Модальное окно предварительного просмотра -->
    <div class="modal fade" id="previewModal" tabindex="-1">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Предварительный просмотр</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div id="previewContent">
                        <h1 id="previewTitle" class="mb-3"></h1>
                        <div id="previewBody"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.tiny.cloud/1/z66zloybrfzhb552oyl9fmnwcuplxmajqn017ne4zn5zg457/tinymce/6/tinymce.min.js"
            referrerpolicy="origin"></script>
    <script src="/static/js/post-create.js"></script>
</@main.layout>