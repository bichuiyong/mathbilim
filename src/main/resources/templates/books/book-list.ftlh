<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">
            <!-- Sidebar with collapse ID for toggle -->
            <aside id="sidebarCategories" class="col-md-3 col-lg-2 bg-light sidebar collapse d-md-block">
                <div class="filter-section p-3">
                    <h3 class="h5 fw-bold mb-3">Предметы</h3>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a href="/books" class="nav-link <#if categoryId??>text-primary<#else>fw-bold</#if>">
                                Все предметы
                            </a>
                        </li>
                        <#list categories as category>
                            <li class="nav-item">
                                <a href="/books?categoryId=${category.id}" class="nav-link <#if categoryId?? && categoryId == category.id>fw-bold</#if>">
                                    ${category.translations[0].translation}
                                </a>
                            </li>
                        </#list>
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

                <div class="d-flex mb-4 flex-column flex-sm-row gap-2">
                    <form class="flex-grow-1 d-flex gap-2" action="/books" method="get">
                        <#if categoryId??>
                            <input type="hidden" name="categoryId" value="${categoryId}">
                        </#if>
                        <input type="text"
                               name="query"
                               value="${query!}"
                               class="form-control" placeholder="Поиск по названию или описанию..."
                               style="height: 38px; font-size: 0.875rem;"/>
                        <button type="submit" class="btn btn-primary btn-sm d-flex align-items-center justify-content-center" style="min-width: 130px; height: 38px;">
                            🔍 Найти
                        </button>
                    </form>

                    <a href="/books/create"
                       class="btn btn-primary btn-sm d-flex align-items-center justify-content-center"
                       style="min-width: 130px; height: 38px;">
                        ➕ Добавить книгу
                    </a>
                </div>


                <div class="list-group mb-4">
                    <#if book.content?size == 0>
                        <div class="alert alert-info">
                            Книги не найдены. <a href="/books" class="alert-link">Сбросить все фильтры</a>.
                        </div>
                    <#else>
                        <#list book.content as bookItem>
                            <div class="list-group-item p-3">
                                <div class="d-flex flex-column flex-md-row w-100 gap-3">
                                    <div class="flex-shrink-0 book-cover-placeholder">
                                        <#if bookItem.mainImage??>
                                            <img src="/api/files/${bookItem.mainImage.id}/view" class="book-cover" alt="${bookItem.name}">
                                        <#else>
                                            <div class="book-cover bg-light d-flex align-items-center justify-content-center">
                                                Обложка
                                            </div>
                                        </#if>
                                    </div>
                                    <div class="flex-grow-1 d-flex flex-column">
                                        <div class="d-flex flex-column flex-md-row justify-content-between mb-2">
                                            <h5 class="mb-1 mb-md-0">${bookItem.name}</h5>
                                            <span class="badge bg-secondary align-self-start align-self-md-center">
                                                ${bookItem.category.translations[0].translation}
                                            </span>
                                        </div>
                                        <p class="mb-2 text-muted">${bookItem.description}</p>
                                        <div class="mt-auto d-flex gap-2">
                                            <#if bookItem.file.type.mimeType == "application/pdf">
                                                <a href="/books/${bookItem.file.id}/pdf-reader"
                                                   class="btn btn-sm btn-outline-primary d-flex align-items-center justify-content-center"
                                                   style="min-width: 120px; height: 36px;">
                                                    <i class="bi bi-book me-1"></i> Читать
                                                </a>
                                            <#else>
                                                <a href="/api/files/${bookItem.file.id}/view"
                                                   class="btn btn-sm btn-outline-primary d-flex align-items-center justify-content-center"
                                                   style="min-width: 120px; height: 36px;"
                                                   target="_blank">
                                                    <i class="bi bi-file-earmark-text me-1"></i> Открыть
                                                </a>
                                            </#if>

                                            <a href="/api/files/${bookItem.file.id}/download"
                                               class="btn btn-sm btn-outline-secondary d-flex align-items-center justify-content-center"
                                               style="min-width: 120px; height: 36px;">
                                                <i class="bi bi-download me-1"></i> Скачать
                                            </a>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>


                <#if book.totalPages gt 1>
                    <#assign currentPage = page>
                    <#assign startPage = [1, currentPage - 2]?max>
                    <#assign endPage = [book.totalPages, currentPage + 2]?min>

                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <li class="page-item <#if book.isFirst()>disabled</#if>">
                                <a class="page-link" href="?page=${currentPage - 1}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>

                            <#if startPage gt 1>
                                <li class="page-item">
                                    <a class="page-link" href="?page=1&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}">1</a>
                                </li>
                                <#if startPage gt 2>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </#if>
                            </#if>

                            <#list startPage..endPage as i>
                                <li class="page-item <#if currentPage == i>active</#if>">
                                    <a class="page-link" href="?page=${i}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}">${i}</a>
                                </li>
                            </#list>

                            <#if endPage lt book.totalPages>
                                <#if endPage lt book.totalPages - 1>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </#if>
                                <li class="page-item">
                                    <a class="page-link" href="?page=${book.totalPages}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}">${book.totalPages}</a>
                                </li>
                            </#if>

                            <li class="page-item <#if book.isLast()>disabled</#if>">
                                <a class="page-link" href="?page=${currentPage + 1}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </#if>

            </main>
        </div>
    </div>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

    <style>
        .book-cover {
            width: 120px;
            height: 160px;
            object-fit: cover;
            border-radius: 4px;
        }
        .btn-sm {
            padding: 0.375rem 0.75rem;
            font-size: 0.875rem;
            line-height: 1.25;
            height: 36px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 0.25rem;
        }
    </style>

    <!-- Add Bootstrap 5 JS bundle for collapse -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</@main.layout>
