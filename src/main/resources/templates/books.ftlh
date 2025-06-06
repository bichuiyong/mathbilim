<#--1.В будущем показывать блок с тем что книг не найдено по категориям-->
<#--2.Очистку фильтров-->
<#--3.пагинацию сверху можно езе-->
<#--4.возможно добавить в навбаре уведомления, что допустим книга на модерации и позже одобрено оно или нет (ну это так как идея)-->
<#--5.вопрос как будем реализовавать показ книг (текстом, пдф , хз кароче)-->

<#import "layout.ftlh" as main>
<@main.layout>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">

            <!-- Категорииии -->
            <aside class="col-md-3 col-lg-2 d-md-block bg-light sidebar">

                <div class="filter-section">
                    <h3 class="h5 fw-bold mb-3">Предметы</h3>
                    <ul class="nav flex-column">
                        <li class="nav-item"><a href="#" class="nav-link">Алгебра</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Геометрия</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Теория чисел</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Анализ</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Комбинаторика</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Олимпиадная подготовка</a></li>
                    </ul>
                </div>
            </aside>


            <main class="col-md-9 col-lg-10 px-md-4">
                <h1 class="h2 mb-4">Книги и методические материалы</h1>

                <div class="d-flex mb-4">
                    <input type="text" class="form-control me-2" placeholder="Поиск по названию или описанию...">
                    <button class="btn btn-primary">Найти</button>
                </div>

                <button class="btn btn-primary mb-4">Добавить книгу</button>



                <div class="list-group mb-4">

                    <#--<#list>-->
                    <div class="list-group-item">
                        <div class="d-flex w-100">
                            <div class="flex-shrink-0 me-3" style="width: 120px; height: 160px; background: #eee; display: flex; align-items: center; justify-content: center;">
                                Обложка
                            </div>
                            <div class="flex-grow-1">
                                <div class="d-flex justify-content-between">
                                    <h5 class="mb-1">Алгебра 7 класс</h5>
                                    <small class="text-muted">Школьный учебник</small>
                                </div>
                                <p class="mb-1">Учебник по алгебре для 7 класса общеобразовательных учреждений.</p>
                                <small class="text-muted">Добавлено: 12.05.2023</small>
                            </div>
                        </div>
                    </div>
                    <#--</#list>-->


                    <div class="list-group-item">
                        <div class="d-flex w-100">
                            <div class="flex-shrink-0 me-3" style="width: 120px; height: 160px; background: #eee; display: flex; align-items: center; justify-content: center;">
                                Обложка
                            </div>
                            <div class="flex-grow-1">
                                <div class="d-flex justify-content-between">
                                    <h5 class="mb-1">Основы комбинаторики</h5>
                                    <small class="text-muted">Теория</small>
                                </div>
                                <p class="mb-1">Введение в комбинаторику для начинающих изучение этой дисциплины.</p>
                                <small class="text-muted">Добавлено: 05.04.2023</small>
                            </div>
                        </div>
                    </div>


                    <div class="list-group-item">
                        <div class="d-flex w-100">
                            <div class="flex-shrink-0 me-3" style="width: 120px; height: 160px; background: #eee; display: flex; align-items: center; justify-content: center;">
                                Обложка
                            </div>
                            <div class="flex-grow-1">
                                <div class="d-flex justify-content-between">
                                    <h5 class="mb-1">Олимпиадные задачи</h5>
                                    <small class="text-muted">Олимпиадная подготовка</small>
                                </div>
                                <p class="mb-1">Сборник задач для подготовки к математическим олимпиадам.</p>
                                <small class="text-muted">Добавлено: 22.03.2023</small>
                            </div>
                        </div>
                    </div>


                    <div class="list-group-item">
                        <div class="d-flex w-100">
                            <div class="flex-shrink-0 me-3" style="width: 120px; height: 160px; background: #eee; display: flex; align-items: center; justify-content: center;">
                                Обложка
                            </div>
                            <div class="flex-grow-1">
                                <div class="d-flex justify-content-between">
                                    <h5 class="mb-1">Геометрия 10-11</h5>
                                    <small class="text-muted">Школьный учебник</small>
                                </div>
                                <p class="mb-1">Учебник по геометрии для старших классов.</p>
                                <small class="text-muted">Добавлено: 15.02.2023</small>
                            </div>
                        </div>
                    </div>
                </div>


                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Назад</a>
                        </li>
                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#">Вперед</a>
                        </li>
                    </ul>
                </nav>

                <style>

                    .pagination {
                        display: flex;
                        padding-left: 0;
                        list-style: none;
                        gap: 8px;
                        margin: 20px 0;
                    }

                    .page-item {
                        margin: 0 2px;
                    }

                    .page-link {
                        position: relative;
                        display: block;
                        padding: 8px 16px;
                        text-decoration: none;
                        color: #333;
                        background-color: #fff;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                    }

                    .page-link:focus {
                        outline: none;
                        box-shadow: none;
                    }

                    .page-link:hover {
                        background-color: #f0f0f0;
                    }

                    .page-item.active .page-link {
                        background-color: #4285f4;
                        border-color: #4285f4;
                        color: white;
                        cursor: default;
                    }

                    .page-item.disabled .page-link {
                        color: #aaa;
                        pointer-events: none;
                        cursor: not-allowed;
                        background-color: #f8f9fa;
                        border-color: #ddd;
                    }

                </style>
            </main>
        </div>
    </div>
</@main.layout>