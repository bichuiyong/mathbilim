<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.layout>
    <link rel="stylesheet" href="/static/scss/main.css">
    <div class="container py-5">
        <div class="card shadow-sm mx-auto" style="max-width: 720px;">
            <div class="card-header bg-dark text-white">
                <h1 class="h4 mb-0"><@spring.message "error.500.title"/></h1>
            </div>

            <div class="card-body">
                <div class="d-flex align-items-start mb-4">
                    <i class="bi bi-bug-fill text-dark fs-1 me-3"></i>
                    <div>
                        <h2 class="h5 fw-bold mb-1">
                            <@spring.message "error.500.subtitle"/>
                        </h2>
                        <p class="text-muted mb-0">
                            <@spring.message "error.500.message"/>
                        </p>
                    </div>
                </div>

                <#if details??>
                    <div class="bg-light rounded p-3 mb-4">
                        <h3 class="h6 mb-3 text-secondary">
                            <@spring.message "error.technicalDetails"/>
                        </h3>
                        <ul class="list-unstyled small">
                            <#if details.serverName??>
                                <li><strong><@spring.message "error.serverName"/>:</strong> <code>${details.serverName}</code></li>
                            </#if>
                            <#if details.requestURL??>
                                <li><strong><@spring.message "error.requestURL"/>:</strong> <code>${details.requestURL}</code></li>
                            </#if>
                            <#if details.serverPort??>
                                <li><strong><@spring.message "error.serverPort"/>:</strong> <code>${details.serverPort?c}</code></li>
                            </#if>
                            <#if details.requestId??>
                                <li><strong><@spring.message "error.requestId"/>:</strong> <code>${details.requestId}</code></li>
                            </#if>
                            <li><strong><@spring.message "error.timestamp"/>:</strong> <code>${.now}</code></li>
                        </ul>
                    </div>
                </#if>

                <div class="text-center">
                    <a href="/" class="btn btn-primary">
                        <i class="bi bi-arrow-left"></i>
                        <@spring.message "error.returnHome"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>