<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout>

    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Создать пост</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .translation-tab {
                display: none;
            }

            .translation-tab.active {
                display: block;
            }
        </style>
    </head>
    <body>
    <div class="container mt-5">
        <h2>Создание поста</h2>
        <form action="/posts/create" method="post" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <#if bindingResult?? && bindingResult.hasErrors()>
                <div class="alert alert-danger">
                    <@spring.bind "createPostDto"/>
                    <#list spring.status.errorMessages as error>
                        <div>${error}</div>
                    </#list>
                </div>
            </#if>

            <!-- Переводы (вкладки) -->
            <div class="card mb-4">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs">
                        <#list createPostDto.post.postTranslations?sort_by("languageCode") as trans>
                            <li class="nav-item">
                                <a class="nav-link <#if trans?is_first>active</#if>"
                                   data-bs-toggle="tab"
                                   href="#lang-${trans.languageCode}">
                                    ${languages[trans.languageCode]}
                                </a>
                            </li>
                        </#list>
                    </ul>
                </div>
                <div class="card-body tab-content">
                    <@spring.bind "createPostDto.post.postTranslations"/>
                    <#if spring.status.error>
                        <div class="alert alert-warning mb-3">
                            <#list spring.status.errorMessages as error>${error}</#list>
                        </div>
                    </#if>

                    <#list createPostDto.post.postTranslations as trans>
                        <div class="tab-pane translation-tab <#if trans?is_first>active</#if>"
                             id="lang-${trans.languageCode}">
                            <@spring.bind "createPostDto.post.postTranslations[${trans?index}].title"/>
                            <div class="mb-3">
                                <label class="form-label">Заголовок (необязательно)</label>
                                <input type="text"
                                       class="form-control <#if spring.status.error>is-invalid</#if>"
                                       name="post.postTranslations[${trans?index}].title"
                                       value="${spring.status.value!''}">
                                <#if spring.status.error>
                                    <div class="invalid-feedback">
                                        <#list spring.status.errorMessages as error>${error}</#list>
                                    </div>
                                </#if>
                            </div>

                            <@spring.bind "createPostDto.post.postTranslations[${trans?index}].content"/>
                            <div class="mb-3">
                                <label class="form-label">Содержание (необязательно)</label>
                                <textarea class="form-control <#if spring.status.error>is-invalid</#if>"
                                          name="post.postTranslations[${trans?index}].content"
                                          rows="10">${spring.status.value!''}</textarea>
                                <#if spring.status.error>
                                    <div class="invalid-feedback">
                                        <#list spring.status.errorMessages as error>${error}</#list>
                                    </div>
                                </#if>
                            </div>
                            <input type="hidden" name="post.postTranslations[${trans?index}].languageCode"
                                   value="${trans.languageCode}">
                        </div>
                    </#list>
                </div>
            </div>

            <!-- Основные поля -->
            <div class="row">
                <div class="col-md-6">
                    <@spring.bind "createPostDto.post.typeId"/>
                    <div class="mb-3">
                        <label class="form-label">Тип поста</label>
                        <select class="form-select <#if spring.status.error>is-invalid</#if>" name="post.typeId">
                            <option value="">-- Выберите тип --</option>
                            <#list postTypes as type>
                                <option value="${type.id}"
                                        <#if spring.status.value?? && spring.status.value.id?? && spring.status.value.id == type.id>selected</#if>>
                                    ${type.postTypeTranslations?filter(t -> t.languageCode == 'ru')?first.translation}
                                </option>
                            </#list>
                        </select>
                        <#if spring.status.error>
                            <div class="invalid-feedback">
                                <#list spring.status.errorMessages as error>${error}</#list>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>

            <!-- Файлы -->
            <div class="mb-3">
                <label class="form-label">Главное изображение</label>
                <input type="file" class="form-control" name="image" accept="image/*">
            </div>

            <div class="mb-3">
                <label class="form-label">Вложения</label>
                <input type="file" class="form-control" name="attachments" multiple>
            </div>

            <div>
                <div class="g-recaptcha" data-sitekey="6LcuuF8rAAAAACaF1yxHhWoVxHHgjUF7BuOogWpO"></div>
            </div>

            <!-- Кнопки -->
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary">Создать пост</button>
                <a href="/posts" class="btn btn-secondary">Отмена</a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/static/js/switchLangTabs.js"></script>
    </body>
    </html>
</@main.layout>
