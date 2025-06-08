<#import "layout.ftlh" as main>
<@main.layout>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">

            <aside class="col-md-3 col-lg-2 bg-light sidebar collapse d-md-block">
                <div class="filter-section p-3">
                    <h3 class="h5 fw-bold mb-3">Предметы тестов</h3>
                    <ul class="nav flex-column">
                        <li class="nav-item"><a href="#" class="nav-link">Все тесты</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Алгебра</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Геометрия</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Теория чисел</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Анализ</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Комбинаторика</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Олимпиады</a></li>
                    </ul>
                </div>
            </aside>

            <main class="col-md-9 col-lg-10 px-md-4">
                <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap">
                    <h1 class="h2 mb-3 mb-md-0">Тесты и проверочные работы</h1>

                    <div class="d-md-none">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarCategories" aria-expanded="false" aria-controls="sidebarCategories">
                            ☰ Категории тестов
                        </button>
                    </div>
                </div>

                <aside class="col-md-3 col-lg-2 bg-light sidebar collapse d-md-block" id="sidebarCategories" style="margin-bottom: 20px">
                    <h3 class="h5 fw-bold mb-3">Предметы тестов</h3>
                    <ul class="nav flex-column">
                        <li class="nav-item"><a href="#" class="nav-link">Все тесты</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Алгебра</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Геометрия</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Теория чисел</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Анализ</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Комбинаторика</a></li>
                        <li class="nav-item"><a href="#" class="nav-link">Олимпиады</a></li>
                    </ul>
                    <hr>
                </aside>

                <div class="d-flex mb-4 flex-column flex-sm-row gap-2">
                    <div class="flex-grow-1">
                        <input type="text" class="form-control" placeholder="Поиск тестов по названию, предмету...">
                    </div>
                    <div class="d-flex gap-2">
                        <button class="btn btn-primary flex-shrink-0">Найти</button>
                        <button class="btn btn-success flex-shrink-0">Создать тест</button>
                    </div>
                </div>

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <div class="d-flex align-items-center gap-2">
                        <span class="text-muted">Сортировка:</span>
                        <select class="form-select form-select-sm" style="width: auto;">
                            <option>По дате (новые)</option>
                            <option>По дате (старые)</option>
                        </select>
                    </div>
                    <div class="text-muted">Найдено 24 теста</div>
                </div>

                <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-4">

<#--                    <#list> -->
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-header bg-primary text-white">
                                <h5 class="card-title mb-0">Алгебра 7 класс</h5>
                            </div>
                            <div class="card-body d-flex flex-column">
                                <div class="d-flex align-items-center mb-3">
                                    <span class="badge bg-secondary me-2">Алгебра</span>
                                    <span class="badge bg-light text-dark"><i class="bi bi-question-circle"></i> 12 вопросов</span>
                                </div>
                                <p class="card-text flex-grow-1">Контрольная работа по основным темам алгебры 7 класса: уравнения, неравенства, системы уравнений.</p>
                                <div class="mt-auto">
                                    <div class="d-flex justify-content-between align-items-center test-time">
                                        <small class="text-muted">15 мин</small>
                                    </div>
                                    <div class="d-grid gap-2 mt-2">
                                        <button class="btn btn-outline-primary">Начать тест</button>
                                        <button class="btn btn-sm btn-outline-secondary">Подробнее</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
<#--                    </#list>-->

                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-header bg-success text-white">
                                <h5 class="card-title mb-0">Геометрические задачи</h5>
                            </div>
                            <div class="card-body d-flex flex-column">
                                <div class="d-flex align-items-center mb-3">
                                    <span class="badge bg-success me-2">Геометрия</span>
                                    <span class="badge bg-light text-dark"><i class="bi bi-question-circle"></i> 15 вопросов</span>
                                </div>
                                <p class="card-text flex-grow-1">Тест на проверку знаний по планиметрии: треугольники, окружности, площади фигур, теоремы.</p>
                                <div class="mt-auto">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <small class="text-muted">20 мин</small>
                                    </div>
                                    <div class="d-grid gap-2 mt-2">
                                        <button class="btn btn-outline-success">Начать тест</button>
                                        <button class="btn btn-sm btn-outline-secondary">Подробнее</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-header bg-warning text-dark">
                                <h5 class="card-title mb-0">Олимпиадные задачи</h5>
                            </div>
                            <div class="card-body d-flex flex-column">
                                <div class="d-flex align-items-center mb-3">
                                    <span class="badge bg-warning text-dark me-2">Олимпиады</span>
                                    <span class="badge bg-light text-dark"><i class="bi bi-question-circle"></i> 5 задач</span>
                                </div>
                                <p class="card-text flex-grow-1">Сборник олимпиадных задач муниципального этапа с решениями и пояснениями.</p>
                                <div class="mt-auto">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <small class="text-muted">45 мин</small>
                                    </div>
                                    <div class="d-grid gap-2 mt-2">
                                        <button class="btn btn-outline-warning">Начать тест</button>
                                        <button class="btn btn-sm btn-outline-secondary">Подробнее</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-header bg-info text-white">
                                <h5 class="card-title mb-0">Теория чисел</h5>
                            </div>
                            <div class="card-body d-flex flex-column">
                                <div class="d-flex align-items-center mb-3">
                                    <span class="badge bg-info me-2">Теория чисел</span>
                                    <span class="badge bg-light text-dark"><i class="bi bi-question-circle"></i> 8 вопросов</span>
                                </div>
                                <p class="card-text flex-grow-1">Тест по основам теории чисел: делимость, НОД, НОК, простые числа, сравнения.</p>
                                <div class="mt-auto">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <small class="text-muted">15 мин</small>
                                    </div>
                                    <div class="d-grid gap-2 mt-2">
                                        <button class="btn btn-outline-info">Начать тест</button>
                                        <button class="btn btn-sm btn-outline-secondary">Подробнее</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-header bg-danger text-white">
                                <h5 class="card-title mb-0">Комбинаторика</h5>
                            </div>
                            <div class="card-body d-flex flex-column">
                                <div class="d-flex align-items-center mb-3">
                                    <span class="badge bg-danger me-2">Комбинаторика</span>
                                    <span class="badge bg-light text-dark"><i class="bi bi-question-circle"></i> 10 вопросов</span>
                                </div>
                                <p class="card-text flex-grow-1">Основы комбинаторики: перестановки, сочетания, размещения, принцип Дирихле.</p>
                                <div class="mt-auto">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <small class="text-muted">25 мин</small>
                                    </div>
                                    <div class="d-grid gap-2 mt-2">
                                        <button class="btn btn-outline-danger">Начать тест</button>
                                        <button class="btn btn-sm btn-outline-secondary">Подробнее</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-header bg-secondary text-white">
                                <h5 class="card-title mb-0">Математический анализ</h5>
                            </div>
                            <div class="card-body d-flex flex-column">
                                <div class="d-flex align-items-center mb-3">
                                    <span class="badge bg-secondary me-2">Анализ</span>
                                    <span class="badge bg-light text-dark"><i class="bi bi-question-circle"></i> 20 вопросов</span>
                                </div>
                                <p class="card-text flex-grow-1">Тест по основам математического анализа: пределы, производные, интегралы, ряды.</p>
                                <div class="mt-auto">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <small class="text-muted">30 мин</small>
                                    </div>
                                    <div class="d-grid gap-2 mt-2">
                                        <button class="btn btn-outline-secondary">Начать тест</button>
                                        <button class="btn btn-sm btn-outline-secondary">Подробнее</button>
                                    </div>
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
                        <li class="page-item"><a class="page-link" href="#">4</a></li>
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

        .card {
            transition: transform 0.2s ease, box-shadow 0.2s ease;
            border-radius: 10px;
            overflow: hidden;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important;
        }

        .card-header {
            font-weight: 600;
        }

        .rating {
            font-size: 0.9rem;
        }

        .badge {
            font-weight: 500;
        }

        @media (max-width: 767.98px) {
            .card {
                margin-bottom: 1rem;
            }
        }

        @media (min-width: 768px) {
            #sidebarCategories {
                display: none !important;
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

        .test-time {
            margin-top: 20px;
        }
    </style>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-w76A2z02tPqdjfvYtmO3pC6tE3PaVTK/rb+8q2FxC5VdPfYjdf29tGxCiqbslY+/"
            crossorigin="anonymous"></script>
</@main.layout>