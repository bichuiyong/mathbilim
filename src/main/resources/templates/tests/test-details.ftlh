<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <style>
        .test-header {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 2rem;
            margin-bottom: 2rem;
        }
        .subject-badge {
            font-size: 1rem;
            padding: 0.5rem 1rem;
        }
        .test-info-card {
            border-left: 3px solid #388dff;
            border-radius: 0 8px 8px 0;
        }
        .btn-start-test {
            padding: 0.75rem 2rem;
            font-size: 1.1rem;
            font-weight: 500;
        }
        .rules-list {
            list-style-type: none;
            padding-left: 1.5rem;
        }
        .rules-list li {
            position: relative;
            margin-bottom: 0.5rem;
        }
        .rules-list li:before {
            content: "•";
            color: #0d6efd;
            font-weight: bold;
            position: absolute;
            left: -1rem;
        }
        .additional-info {
            background-color: #f1f8ff;
            border-radius: 8px;
            padding: 1.5rem;
            margin-top: 2rem;
        }
        .custom {
            background-color: #388dff;
        }
        .folder-list .folder-item {
            padding: 0.5rem 0.75rem;
            border-radius: 6px;
            background-color: #f8f9fa;
            transition: background-color 0.2s ease;
        }

        .folder-list .folder-item:hover {
            background-color: #e2f0ff;
        }

        .folder-item i.bi-folder-fill {
            font-size: 1.1rem;
        }


    </style>
    <body>
    <div class="container py-5">
        <div class="test-header text-center mb-5">
            <h1 class="display-5 fw-bold mb-3"><@spring.message "test.details.test"/> ${test.name}</h1>
            <p class="lead mb-4"><@spring.message "test.details.checkBrain"/></p>
        </div>

        <div class="row">
            <div class="col-lg-8">
                <div class="card mb-4 border-0 shadow-sm">
                    <div class="card-body">
                        <h2 class="h4 fw-bold mb-3"><@spring.message "test.details.welcome"/> ${test.name}</h2>
                        <p class="mb-4"><@spring.message "test.details.consistOf"/> ${test.questionCount} <@spring.message "test.details.tasks"/><#if test.hasLimit> <@spring.message "test.details.execution"/> ${test.timeLimit} <@spring.message "test.details.minute"/>.<#else><@spring.message "test.details.unlimitedTime"/></#if> <@spring.message "test.details.message1"/></p>

                        <p class="mb-4"><@spring.message "test.details.message2"/></p>

                        <h3 class="h5 fw-bold mt-4 mb-3"><@spring.message "test.details.rules"/></h3>
                        <ul class="rules-list">
                            <#if test.hasLimit><li><@spring.message "test.details.rule1"/></li></#if>
                            <li><@spring.message "test.details.rule2"/></li>
                            <li><@spring.message "test.details.rule3"/></li>
                            <li><@spring.message "test.details.rule4"/></li>
                            <li><@spring.message "test.details.rule5"/></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-lg-4">
                <div class="card mb-4 test-info-card shadow-sm">
                    <div class="card-body">
                        <h3 class="h5 fw-bold mb-4"><@spring.message "test.details.info"/></h3>
                        <div class="d-flex align-items-center mb-3">
                            <i class="bi bi-clock me-2 text-primary"></i>
                            <div>
                                <p class="mb-0 fw-bold"><@spring.message "test.details.time"/></p>
                                <#if test.hasLimit>
                                    <p class="mb-0">${test.timeLimit} <@spring.message "test.details.minute"/></p>
                                <#else>
                                    <@spring.message "test.details.unlimited"/>
                                </#if>
                            </div>
                        </div>
                        <div class="d-flex align-items-center mb-3">
                            <i class="bi bi-list-task me-2 text-primary"></i>
                            <div>
                                <p class="mb-0 fw-bold"><@spring.message "test.details.tasksCount"/></p>
                                <p class="mb-0">${test.questionCount}</p>
                            </div>
                        </div>
                        <a class="btn btn-primary custom btn-start-test w-100 py-2" href="/tests/${test.id}/pass">
                            <@spring.message "test.details.ready"/>
                        </a>
                    </div>
                </div>
            </div>

            <div class="card mb-4 shadow-sm">
                <div class="card-body">
                    <h3 class="h5 fw-bold mb-4"><@spring.message "test.details.top5"/></h3>
                    <div class="folder">
                        <#if lastResults??>
                            <ul class="folder-list list-unstyled mb-0">
                                <#list lastResults as resultItem>
                                    <#assign attemptId = resultItem?keys[0]?string>
                                    <#assign finishedAt = resultItem[attemptId]>

                                    <li class="folder-item mb-2 d-flex justify-content-between align-items-center">
                                    <span>
                                        <i class="bi bi-folder-fill text-warning me-2"></i>
                                        <a href="/tests/attempts/${attemptId}/result" class="text-decoration-none">
                                            ${finishedAt}
                                        </a>
                                    </span>
                                    </li>
                                </#list>
                            </ul>

                        <#else>
                            <p class="mb-0 text-muted"><@spring.message "test.details.noresults"/></p>
                        </#if>
                    </div>
                </div>
            </div>

        </div>

        <div class="additional-info">
            <h3 class="h5 fw-bold mb-3"><@spring.message "test.details.more"/></h3>
            <p><@spring.message "test.details.info1"/></p>
            <p class="mb-0"><@spring.message "test.details.info2"/></p>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@main.layout>