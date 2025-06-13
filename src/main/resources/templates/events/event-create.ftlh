<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Создать событие</title>
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
    <h2>Создание события</h2>
    <form action="/events/create" method="post" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <#if bindingResult?? && bindingResult.hasErrors()>
            <div class="alert alert-danger">
                <@spring.bind "createEventDto"/>
                <#list spring.status.errorMessages as error>
                    <div>${error}</div>
                </#list>
            </div>
        </#if>

        <div class="card mb-4">
            <div class="card-header">
                <ul class="nav nav-tabs card-header-tabs">
                    <#list createEventDto.event.eventTranslations?sort_by("languageCode") as trans>
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
                <@spring.bind "createEventDto.event.eventTranslations"/>
                <#if spring.status.error>
                    <div class="alert alert-warning mb-3">
                        <#list spring.status.errorMessages as error>${error}</#list>
                    </div>
                </#if>

                <#list createEventDto.event.eventTranslations as trans>
                    <div class="tab-pane translation-tab <#if trans?is_first>active</#if>" id="lang-${trans.languageCode}">
                        <@spring.bind "createEventDto.event.eventTranslations[${trans?index}].title"/>
                        <div class="mb-3">
                            <label class="form-label">Заголовок (необязательно)</label>
                            <input type="text"
                                   class="form-control <#if spring.status.error>is-invalid</#if>"
                                   name="event.eventTranslations[${trans?index}].title"
                                   value="${spring.status.value!''}">
                            <#if spring.status.error>
                                <div class="invalid-feedback">
                                    <#list spring.status.errorMessages as error>${error}</#list>
                                </div>
                            </#if>
                        </div>

                        <@spring.bind "createEventDto.event.eventTranslations[${trans?index}].content"/>
                        <div class="mb-3">
                            <label class="form-label">Описание (необязательно)</label>
                            <textarea class="form-control <#if spring.status.error>is-invalid</#if>"
                                      name="event.eventTranslations[${trans?index}].content"
                                      rows="5">${spring.status.value!''}</textarea>
                            <#if spring.status.error>
                                <div class="invalid-feedback">
                                    <#list spring.status.errorMessages as error>${error}</#list>
                                </div>
                            </#if>
                        </div>
                        <input type="hidden" name="event.eventTranslations[${trans?index}].languageCode" value="${trans.languageCode}">
                    </div>
                </#list>
            </div>
        </div>

        <!-- Основные поля -->
        <div class="row">
            <div class="col-md-6">
                <@spring.bind "createEventDto.event.startDate"/>
                <div class="mb-3">
                    <label class="form-label">Дата начала</label>
                    <input type="datetime-local"
                           class="form-control <#if spring.status.error>is-invalid</#if>"
                           name="event.startDate"
                           value="${spring.status.value!''}">
                    <#if spring.status.error>
                        <div class="invalid-feedback">
                            <#list spring.status.errorMessages as error>${error}</#list>
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-md-6">
                <@spring.bind "createEventDto.event.endDate"/>
                <div class="mb-3">
                    <label class="form-label">Дата окончания (необязательно)</label>
                    <input type="datetime-local"
                           class="form-control <#if spring.status.error>is-invalid</#if>"
                           name="event.endDate"
                           value="${spring.status.value!''}">
                    <#if spring.status.error>
                        <div class="invalid-feedback">
                            <#list spring.status.errorMessages as error>${error}</#list>
                        </div>
                    </#if>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <@spring.bind "createEventDto.event.typeId"/>
                <div class="mb-3">
                    <label class="form-label">Тип события</label>
                    <select class="form-select <#if spring.status.error>is-invalid</#if>" name="event.typeId">
                        <option value="">-- Выберите тип --</option>
                        <#list eventsTypes as type>
                            <!-- Исправлено сравнение -->
                            <option value="${type.id}" <#if spring.status.value?? && spring.status.value == type.id?string>selected</#if>>
                                ${type.eventTypeTranslations?filter(t -> t.languageCode == 'ru')?first.translation}
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
            <div class="col-md-6">
                <@spring.bind "createEventDto.organizationIds"/>
                <div class="mb-3">
                    <label class="form-label">Организации</label>
                    <select class="form-select" name="organizationIds" multiple>
                        <#list organizations as org>
                            <option value="${org.id}">${org.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
        </div>

        <div class="mb-3">
            <@spring.bind "createEventDto.event.isOffline"/>
            <div class="form-check">
                <input type="checkbox"
                       class="form-check-input"
                       name="event.isOffline"
                       value="true"
                       <#if spring.status.value?? && spring.status.value>checked</#if>>
                <label class="form-check-label">Офлайн событие</label>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Главное изображение</label>
            <input type="file" class="form-control" name="image">
        </div>

        <div class="mb-3">
            <label class="form-label">Вложения</label>
            <input type="file" class="form-control" name="attachments" multiple>
        </div>

        <button type="submit" class="btn btn-primary">Создать</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();

            document.querySelectorAll('.translation-tab').forEach(tab => {
                tab.classList.remove('active');
            });

            const targetTab = document.querySelector(this.getAttribute('href'));
            targetTab.classList.add('active');

            document.querySelectorAll('.nav-link').forEach(link => {
                link.classList.remove('active');
            });
            this.classList.add('active');
        });
    });
</script>
</body>
</html>