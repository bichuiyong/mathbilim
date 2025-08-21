<#import "../layout.ftlh" as layout>
<@layout.layout>
    <link rel="stylesheet" href="/static/scss/main.css">
    <div class="container py-5">
        <div class="card shadow-sm mx-auto" style="max-width: 720px;">
            <div class="card-header bg-danger text-white">
                <h1 class="h4 mb-0">404 — Страница не найдена</h1>
            </div>

            <div class="card-body">
                <div class="d-flex align-items-start mb-4">
                    <i class="bi bi-search text-danger fs-1 me-3"></i>
                    <div>
                        <h2 class="h5 fw-bold mb-1">
                            Упс! Такой страницы не существует.
                        </h2>
                        <p class="text-muted mb-0">
                            Возможно, вы ошиблись в адресе или страница была удалена.
                        </p>
                    </div>
                </div>

                <#if details??>
                    <div class="bg-light rounded p-3 mb-4">
                        <h3 class="h6 mb-3 text-secondary">Технические детали:</h3>
                        <ul class="list-unstyled small">
                            <#if details.serverName??>
                                <li><strong>Хост:</strong> <code>${details.serverName}</code></li>
                            </#if>
                            <#if details.requestURL??>
                                <li><strong>Исходный адрес:</strong> <code>${details.requestURL}</code></li>
                            </#if>
                            <#if details.serverPort??>
                                <li><strong>Порт сервиса:</strong> <code>${details.serverPort?c}</code></li>
                            </#if>
                            <#if details.requestId??>
                                <li><strong>ID запроса:</strong> <code>${details.requestId}</code></li>
                            </#if>
                            <li><strong>Время:</strong> <code>${.now}</code></li>
                        </ul>
                    </div>
                </#if>

                <div class="text-center">
                    <a href="/" class="btn btn-primary">
                        <i class="bi bi-arrow-left"></i>
                        Вернуться на главную
                    </a>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>
