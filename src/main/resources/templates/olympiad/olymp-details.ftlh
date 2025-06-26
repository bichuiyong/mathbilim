<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container mt-4 mb-5">
        <h1 class="mb-4">${olympiad.title}</h1>


        <ul class="nav nav-tabs mb-4" id="olympiadTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="stages-tab" data-bs-toggle="tab" data-bs-target="#stages"
                        type="button" role="tab" aria-controls="stages" aria-selected="true">Этапы проведения</button>
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
                        type="button" role="tab" aria-controls="results" aria-selected="false">Организации</button>
            </li>
        </ul>




        <div class="tab-content" id="olympiadTabContent" style="margin-bottom: 100px">

            <div class="tab-pane fade show active" id="stages" role="tabpanel" aria-labelledby="stages-tab">
                <h4>Этапы проведения</h4>
                <ul>
                    <#if olympiad.stages??>
                        <#list olympiad.stages as stage>
                            <li>${stage.stageOrder} этап — Онлайн-отбор: ${stage.registrationStart} января
                                – ${stage.registrationEnd} февраля</li>
                        </#list>
                    </#if>
                </ul>
            </div>

            <div class="tab-pane fade" id="results" role="tabpanel" aria-labelledby="results-tab">
                <h4>Результаты</h4>
                <p><a href="#" class="btn btn-outline-success">Смотреть результаты 2025</a></p>
            </div>


            <div class="tab-pane fade" id="contacts" role="tabpanel" aria-labelledby="contacts-tab">
                <h4>Контакты</h4>
                <p>
                    <#if olympiad.contacts??>
                        <#list olympiad.contacts as contact>
                            <strong>${contact.contactType}:</strong> ${contact.info}<br>
                        </#list>
                    <#else>
                    Организатор решил не оставлять контакты :(
                    </#if>
                </p>
            </div>


            <div class="tab-pane fade" id="rules" role="tabpanel" aria-labelledby="rules-tab">
                <h4>Регламент / правила участия</h4>
                <p>
                    ${olympiad.rules}
                </p>
            </div>

            <div class="tab-pane fade" id="organization" role="tabpanel" aria-labelledby="rules-tab">
                <h4>Организации</h4>
                <p>
                    <#if olympiad.organizations??>
                        <#list olympiad.organizations as organization>
                            ${organization.name}
                        </#list>
                    </#if>
                </p>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</@main.layout>
