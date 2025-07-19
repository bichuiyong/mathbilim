<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        .tab-pane {
            min-height: 300px;
        }

        .btn-xs {
            padding: 0.1rem 0.25rem;
            font-size: 0.75rem;
            line-height: 1;
            border-radius: 0.15rem;
            background-color: #696969;
        }

        .btn-xs :hover {
            background-color: #343434;
        }

        @media (max-width: 576px) {
            .table-responsive {
                overflow-x: auto;
                -webkit-overflow-scrolling: touch;
            }

            .table {
                font-size: 0.85rem;
                transform: scale(0.9);
                transform-origin: top left;
            }

            .table th, .table td {
                padding: 0.5rem;
                white-space: nowrap;
            }

            .btn-sm, .btn-xs {
                font-size: 0.7rem;
                padding: 0.1rem 0.2rem;
            }
        }
    </style>

    <#if SPRING_SECURITY_CONTEXT??>
        <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
        <#assign authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)>
        <#assign
        isAdmin = authorities?seq_contains("ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        isUser = authorities?seq_contains("USER")
        >
    </#if>

    <div class="container mt-4 mb-5">
        <h1 class="mb-4">${olympiad.title}</h1>

        <ul class="nav nav-tabs mb-4" id="olympiadTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="description-tab" data-bs-toggle="tab" data-bs-target="#description"
                        type="button" role="tab" aria-controls="description" aria-selected="true">Подробности</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="stages-tab" data-bs-toggle="tab" data-bs-target="#stages"
                        type="button" role="tab" aria-controls="stages" aria-selected="false">Этапы проведения</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="contacts-tab" data-bs-toggle="tab" data-bs-target="#contacts"
                        type="button" role="tab" aria-controls="contacts" aria-selected="false">Контакты</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="rules-tab" data-bs-toggle="tab" data-bs-target="#rules"
                        type="button" role="tab" aria-controls="rules" aria-selected="false">Регламент</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="results-tab" data-bs-toggle="tab" data-bs-target="#results"
                        type="button" role="tab" aria-controls="results" aria-selected="false">Результаты</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="organization-tab" data-bs-toggle="tab" data-bs-target="#organization"
                        type="button" role="tab" aria-controls="organization" aria-selected="false">Организации</button>
            </li>
        </ul>

        <div class="tab-content" id="olympiadTabContent" style="margin-bottom: 100px">

            <div class="tab-pane fade show active" id="description" role="tabpanel" aria-labelledby="description-tab">
                <h4>Подробности / описание</h4>
                <#if olympiad.info??>
                    <p>
                        ${olympiad.info?no_esc}
                    </p>
                </#if>
            </div>

            <div class="tab-pane fade" id="stages" role="tabpanel" aria-labelledby="stages-tab">
                <h4>Этапы проведения</h4>
                <#if olympiad.stages?? && (olympiad.stages?size > 0)>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover table-sm small" style="margin-top: 20px;
                         width: 80%">
                            <thead class="table-light">
                            <tr>
                                <th>#</th>
                                <th>Начало регистрации</th>
                                <th>Окончание регистрации</th>
                                <th>Дата начала этапа</th>
                                <th>Дата окончания этапа</th>
                                <th>Обновлено</th>
                                <th>Результаты</th>
                                <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                    <th>Действие</th>
                                </#if>
                            </tr>
                            </thead>
                            <tbody>
                            <#list olympiad.stages as stage>
                                <tr>
                                    <td>${stage.stageOrder}</td>
                                    <td>${stage.registrationStart}</td>
                                    <td>${stage.registrationEnd}</td>
                                    <td>${stage.startDate}</td>
                                    <td>${stage.endDate}</td>
                                    <td>
                                        <#if stage.updatedAt??>
                                            ${stage.updatedAtFormatted}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if stage.result?? && (stage.result?size > 0)>
                                            <a href="#" class="btn btn-sm btn-primary">
                                                результаты
                                            </a>
                                        <#else>
                                            отсутствует
                                        </#if>
                                    </td>
                                    <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                        <td>
                                            <a href="#" class="btn btn-primary btn-xs">
                                                Добавить результаты
                                            </a>
                                        </td>
                                    </#if>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                <#else>
                    <p>Этапы ещё не добавлены.</p>
                </#if>
            </div>

            <div class="tab-pane fade" id="results" role="tabpanel" aria-labelledby="results-tab">
                <h4>Результаты</h4>
                <p><a href="#" class="btn btn-outline-success">Смотреть результаты 2025</a></p>
            </div>

            <div class="tab-pane fade" id="contacts" role="tabpanel" aria-labelledby="contacts-tab">
                <h4>Контакты</h4>
                <p>
                    <#if olympiad.contacts?? && (olympiad.contacts?size > 0)>
                        <#list olympiad.contacts as contact>
                            <#if contact.contactType?starts_with("Telegram")>
                                <strong>${contact.contactType}:</strong>
                                <a href="${contact.info}" target="_blank" class="telegram-link">
                                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style="vertical-align: middle; margin-right: 5px;">
                                        <path d="M12 0C5.373 0 0 5.373 0 12s5.373 12 12 12 12-5.373 12-12S18.627 0 12 0zm4.664 7.788l-1.543 7.282c-.114.537-.415.672-.842.417l-2.304-1.695-1.114 1.073c-.123.119-.227.218-.462.218-.297 0-.373-.134-.373-.424v-.002L9.5 10.61l-4.308 1.34c-.492.153-.906-.304-.706-.756l8.216-7.963c.366-.344.906-.094.756.424z" fill="#0088cc"/>
                                    </svg>
                                </a><br>
                            <#else>
                                <strong>${contact.contactType}:</strong> ${contact.info}<br>
                            </#if>
                        </#list>
                    <#else>
                        Организатор решил не оставлять контакты :(
                    </#if>
                </p>
            </div>

            <div class="tab-pane fade" id="rules" role="tabpanel" aria-labelledby="rules-tab">
                <h4>Регламент / правила участия</h4>
                <p>
                    ${olympiad.rules?no_esc}
                </p>
            </div>

            <div class="tab-pane fade" id="organization" role="tabpanel" aria-labelledby="rules-tab">
                <h4>Организации</h4>
                <#if olympiad.organizations??>
                    <#list olympiad.organizations as organization>
                        <p>${organization.name}</p>
                    </#list>
                </#if>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</@main.layout>