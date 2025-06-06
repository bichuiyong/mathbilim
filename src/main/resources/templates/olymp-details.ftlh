<#import "layout.ftlh" as main>
<@main.layout>
    <div class="container mt-4 mb-5">
        <h1 class="mb-4">TSI Programming Contest 2025</h1>


        <ul class="nav nav-tabs mb-4" id="olympiadTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="stages-tab" data-bs-toggle="tab" data-bs-target="#stages"
                        type="button" role="tab" aria-controls="stages" aria-selected="true">Этапы проведения</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="archive-tab" data-bs-toggle="tab" data-bs-target="#archive"
                        type="button" role="tab" aria-controls="archive" aria-selected="false">Архив задач</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="materials-tab" data-bs-toggle="tab" data-bs-target="#materials"
                        type="button" role="tab" aria-controls="materials" aria-selected="false">Материалы</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="results-tab" data-bs-toggle="tab" data-bs-target="#results"
                        type="button" role="tab" aria-controls="results" aria-selected="false">Результаты</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="contacts-tab" data-bs-toggle="tab" data-bs-target="#contacts"
                        type="button" role="tab" aria-controls="contacts" aria-selected="false">Контакты</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="rules-tab" data-bs-toggle="tab" data-bs-target="#rules"
                        type="button" role="tab" aria-controls="rules" aria-selected="false">Регламент</button>
            </li>
        </ul>




        <div class="tab-content" id="olympiadTabContent" style="margin-bottom: 100px">

            <div class="tab-pane fade show active" id="stages" role="tabpanel" aria-labelledby="stages-tab">
                <h4>Этапы проведения</h4>
                <ul>
                    <li>1 этап — Онлайн-отбор: 15 января – 15 февраля</li>
                    <li>2 этап — Очный тур: 25 февраля в г. Бишкек</li>
                    <li>3 этап — Финал и награждение: 1 марта</li>
                </ul>
            </div>


            <div class="tab-pane fade" id="archive" role="tabpanel" aria-labelledby="archive-tab">
                <h4>Архив задач</h4>
                <p><a href="#" class="btn btn-outline-primary" target="_blank">Скачать архив задач (PDF)</a></p>
            </div>


            <div class="tab-pane fade" id="materials" role="tabpanel" aria-labelledby="materials-tab">
                <h4>Подготовительные материалы</h4>
                <p>
                    Ознакомьтесь с <a href="#" target="_blank">методическими рекомендациями</a><br>
                    и <a href="#" target="_blank">примерами задач</a>.
                </p>
            </div>


            <div class="tab-pane fade" id="results" role="tabpanel" aria-labelledby="results-tab">
                <h4>Результаты</h4>
                <p><a href="#" class="btn btn-outline-success">Смотреть результаты 2025</a></p>
            </div>


            <div class="tab-pane fade" id="contacts" role="tabpanel" aria-labelledby="contacts-tab">
                <h4>Контакты</h4>
                <p>
                    <strong>Email:</strong> info@olympiads.kg<br>
                    <strong>Телефон:</strong> +996 312 123 456
                </p>
            </div>


            <div class="tab-pane fade" id="rules" role="tabpanel" aria-labelledby="rules-tab">
                <h4>Регламент / правила участия</h4>
                <p><a href="/rules/tsi-2025.pdf" class="btn btn-outline-secondary" target="_blank">Скачать регламент</a></p>
            </div>


        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</@main.layout>
