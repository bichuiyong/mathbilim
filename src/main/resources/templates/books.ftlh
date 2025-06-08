<#--1.В будущем показывать блок с тем что книг не найдено по категориям-->
<#--2.Очистку фильтров-->
<#--3.пагинацию сверху можно езе-->
<#--4.возможно добавить в навбаре уведомления, что допустим книга на модерации и позже одобрено оно или нет (ну это так как идея)-->
<#--5.вопрос как будем реализовавать показ книг (текстом, пдф , хз кароче)-->
<#import "layout.ftlh" as main>
<@main.layout>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">

            <aside class="col-md-3 col-lg-2 bg-light sidebar collapse d-md-block">
                <div class="filter-section p-3">
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
                <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap">
                    <h1 class="h2 mb-3 mb-md-0">Книги и методические материалы</h1>

                    <div class="d-md-none">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarCategories" aria-expanded="false" aria-controls="sidebarCategories">
                            ☰ Категории
                        </button>
                    </div>
                </div>

                <aside class="col-md-3 col-lg-2 bg-light sidebar collapse d-md-block" id="sidebarCategories" style="margin-bottom: 20px">
                        <h3 class="h5 fw-bold mb-3">Предметы</h3>
                        <ul class="nav flex-column">
                            <li class="nav-item"><a href="#" class="nav-link">Алгебра</a></li>
                            <li class="nav-item"><a href="#" class="nav-link">Геометрия</a></li>
                            <li class="nav-item"><a href="#" class="nav-link">Теория чисел</a></li>
                            <li class="nav-item"><a href="#" class="nav-link">Анализ</a></li>
                            <li class="nav-item"><a href="#" class="nav-link">Комбинаторика</a></li>
                            <li class="nav-item"><a href="#" class="nav-link">Олимпиадная подготовка</a></li>
                        </ul>
                        <hr>
                </aside>

                <div class="d-flex mb-4 flex-column flex-sm-row gap-2">
                    <div class="flex-grow-1">
                        <input type="text" class="form-control" placeholder="Поиск по названию или описанию...">
                    </div>
                    <div class="d-flex gap-2">
                        <button class="btn btn-primary flex-shrink-0">Найти</button>
                        <button class="btn btn-success flex-shrink-0">Добавить книгу</button>
                    </div>
                </div>

                <div class="list-group mb-4">

                    <div class="list-group-item p-3">
                        <div class="d-flex flex-column flex-md-row w-100 gap-3">
                            <div class="flex-shrink-0 book-cover-placeholder">
                                <div class="book-cover bg-light d-flex align-items-center justify-content-center">
                                    Обложка
                                </div>
                            </div>
                            <div class="flex-grow-1 d-flex flex-column">
                                <div class="d-flex flex-column flex-md-row justify-content-between mb-2">
                                    <h5 class="mb-1 mb-md-0">Алгебра 7 класс</h5>
                                    <span class="badge bg-secondary align-self-start align-self-md-center">Геометрия</span>
                                </div>
                                <p class="mb-2 text-muted">Учебник по алгебре для 7 класса общеобразовательных учреждений.</p>
                                <div class="mt-auto d-flex gap-2">
                                    <button class="btn btn-primary btn-sm">Читать</button>
                                    <button class="btn btn-outline-secondary btn-sm">Скачать</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="list-group-item p-3">
                        <div class="d-flex flex-column flex-md-row w-100 gap-3">
                            <div class="flex-shrink-0 book-cover-placeholder">
                                <div class="book-cover bg-light d-flex align-items-center justify-content-center">
                                    Обложка
                                </div>
                            </div>
                            <div class="flex-grow-1 d-flex flex-column">
                                <div class="d-flex flex-column flex-md-row justify-content-between mb-2">
                                    <h5 class="mb-1 mb-md-0">Комбинаторика</h5>
                                    <span class="badge bg-info text-dark align-self-start align-self-md-center">Теория</span>
                                </div>
                                <p class="mb-2 text-muted">Введение в комбинаторику для начинающих изучение этой дисциплины.</p>
                                <div class="mt-auto d-flex gap-2">
                                    <button class="btn btn-primary btn-sm">Читать</button>
                                    <button class="btn btn-outline-secondary btn-sm">Скачать</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="list-group-item p-3">
                        <div class="d-flex flex-column flex-md-row w-100 gap-3">
                            <div class="flex-shrink-0 book-cover-placeholder">
                                <div class="book-cover bg-light d-flex align-items-center justify-content-center">
                                    Обложка
                                </div>
                            </div>
                            <div class="flex-grow-1 d-flex flex-column">
                                <div class="d-flex flex-column flex-md-row justify-content-between mb-2">
                                    <h5 class="mb-1 mb-md-0">Олимпиадная подготовка</h5>
                                    <span class="badge bg-warning text-dark align-self-start align-self-md-center">Олимпиадная подготовка</span>
                                </div>
                                <p class="mb-2 text-muted">Сборник задач для подготовки к математическим олимпиадам.</p>
                                <div class="mt-auto d-flex gap-2">
                                    <button class="btn btn-primary btn-sm">Читать</button>
                                    <button class="btn btn-outline-secondary btn-sm">Скачать</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="list-group-item p-3">
                        <div class="d-flex flex-column flex-md-row w-100 gap-3">
                            <div class="flex-shrink-0 book-cover-placeholder">
                                <div class="book-cover bg-light d-flex align-items-center justify-content-center">
                                    Обложка
                                </div>
                            </div>
                            <div class="flex-grow-1 d-flex flex-column">
                                <div class="d-flex flex-column flex-md-row justify-content-between mb-2">
                                    <h5 class="mb-1 mb-md-0">Геометрия 10-11</h5>
                                    <span class="badge bg-secondary align-self-start align-self-md-center">Алгебра</span>
                                </div>
                                <p class="mb-2 text-muted">Учебник по геометрии для старших классов.</p>
                                <div class="mt-auto d-flex gap-2">
                                    <button class="btn btn-primary btn-sm">Читать</button>
                                    <button class="btn btn-outline-secondary btn-sm">Скачать</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>


                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </main>
        </div>
    </div>

    <style>
        .sidebar {
            transition: all 0.3s ease;
        }

        .sidebar.collapse:not(.show) {
            display: none;
        }

        .sidebar.collapsing {
            height: 0;
            overflow: hidden;
            transition: height 0.35s ease;
        }

        .book-cover {
            width: 120px;
            height: 160px;
            border-radius: 4px;
        }

        .book-cover-placeholder {
            width: 120px;
            margin: 0 auto;
        }

        @media (max-width: 767.98px) {
            .list-group-item {
                padding: 1rem;
            }

            .book-cover-placeholder {
                margin-bottom: 1rem;
            }
        }

        @media (min-width: 768px) {
            .book-cover-placeholder {
                margin: 0;
            }
        }

        .pagination {
            gap: 0.5rem;
        }

        .page-item {
            border-radius: 10px;
        }

        .page-link {
            min-width: 2.5rem;
            text-align: center;
        }

        @media (min-width: 768px) {
            #sidebarCategories {
                display: none !important;
            }
        }
    </style>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-w76A2z02tPqdjfvYtmO3pC6tE3PaVTK/rb+8q2FxC5VdPfYjdf29tGxCiqbslY+/"
            crossorigin="anonymous"></script>

</@main.layout>