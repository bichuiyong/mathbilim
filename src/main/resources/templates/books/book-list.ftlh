<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ADMIN") || authorities?seq_contains("SUPER_ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        >
    </#if>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">
            <!-- Sidebar with collapse ID for toggle -->
            <aside id="sidebarCategories" class="col-md-3 col-lg-2 bg-light sidebar collapse d-md-block">
                <div class="filter-section p-3">
                    <h3 class="h5 fw-bold mb-3"><@spring.message "books.sidebar.title"/></h3>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a href="/books" class="nav-link <#if categoryId??>text-primary<#else>fw-bold</#if>">
                                <@spring.message "books.sidebar.all"/>
                            </a>
                        </li>
                        <#list categories as category>
                            <li class="nav-item">
                                <a href="/books?categoryId=${category.id}"
                                   class="nav-link <#if categoryId?? && categoryId == category.id>fw-bold</#if>">
                                    ${category.translations[0].translation}
                                </a>
                            </li>
                        </#list>
                    </ul>
                </div>
            </aside>

            <main class="col-md-9 col-lg-10 px-md-4">
                <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap">
                    <h1 class="h2 mb-3 mb-md-0"><@spring.message "books.page.title"/></h1>
                    <div class="d-md-none">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse"
                                data-bs-target="#sidebarCategories" aria-expanded="false"
                                aria-controls="sidebarCategories">
                            ☰ <@spring.message "books.sidebar.toggle"/>
                        </button>
                    </div>
                </div>

                <div class="d-flex mb-4 flex-column flex-sm-row gap-2">
                    <form class="flex-grow-1 d-flex gap-2 search-form" action="/books" method="get">
                        <#if categoryId??>
                            <input type="hidden" name="categoryId" value="${categoryId}">
                        </#if>
                        <input type="text"
                               name="query"
                               value="${query!}"
                               class="form-control" placeholder="<@spring.message 'books.search.placeholder'/>"
                               style="height: 38px; font-size: 0.875rem;"/>
                        <button type="submit"
                                class="btn btn-primary btn-sm d-flex align-items-center justify-content-center"
                                style="min-width: 130px; height: 38px;">
                            🔍 <@spring.message "books.search.button"/>
                        </button>
                    </form>

                    <a href="/books/create"
                       class="btn btn-primary btn-sm d-flex align-items-center justify-content-center"
                       style="min-width: 130px; height: 38px;">
                        ➕ <@spring.message "books.create"/>
                    </a>
                </div>

                <div class="list-group mb-4">
                    <#if book.content?size == 0>
                        <div class="alert alert-info">
                            <@spring.message "books.notfound"/> <a href="/books"
                                                                   class="alert-link"><@spring.message "books.reset"/></a>.
                        </div>
                    <#else>
                        <#list book.content as bookItem>
                            <div class="book-card" data-book-id="${bookItem.id}">
                                <div class="d-flex flex-column flex-md-row w-100 gap-3">
                                    <div class="flex-shrink-0 book-cover-container">
                                        <#if bookItem.mainImage??>
                                            <img src="/api/files/${bookItem.mainImage.id}/view" class="book-cover"
                                                 alt="${bookItem.name}">
                                        <#else>
                                            <div class="book-cover bg-light d-flex align-items-center justify-content-center">
                                                <@spring.message "books.cover"/>
                                            </div>
                                        </#if>
                                        <div class="book-cover-overlay">
                                            <span class="view-badge">${bookItem.viewCount}</span>
                                        </div>
                                    </div>
                                    <div class="flex-grow-1 d-flex flex-column book-info">
                                        <div class="d-flex flex-column flex-md-row justify-content-between mb-2">
                                            <h5 class="mb-1 mb-md-0">${bookItem.name}</h5>
                                            <span class="badge bg-secondary align-self-start align-self-md-center">
                                              <#assign currentLocale = springMacroRequestContext.getLocale()>
                                                <#assign currentLang = currentLocale.language>


                                                <#assign categoryName = "">

                                                <#if bookItem.category?? && bookItem.category.translations??>
                                                    <#list bookItem.category.translations as t>
                                                        <#if t.languageCode?? && t.languageCode == currentLang && t.translation?? && t.translation != "">
                                                            <#assign categoryName = t.translation>
                                                            <#assign foundTranslation = true>
                                                            <#break>
                                                        </#if>
                                                    </#list>
                                                </#if>

                                             ${categoryName?has_content?then(categoryName, "No category")}
                                        </span>

                                        </div>
                                        <p class="mb-2 text-muted">${bookItem.description}</p>
                                        <div class="mt-auto d-flex gap-2">
                                            <#if bookItem.file.type.mimeType == "application/pdf">
                                                <a href="/books/${bookItem.file.id}/pdf-reader"
                                                   class="btn btn-sm btn-outline-primary d-flex align-items-center justify-content-center"
                                                   style="min-width: 120px; height: 36px;">
                                                    <i class="bi bi-book me-1"></i> <@spring.message "books.read"/>
                                                </a>
                                            <#else>
                                                <a href="/api/files/${bookItem.file.id}/view"
                                                   class="btn btn-sm btn-outline-primary d-flex align-items-center justify-content-center"
                                                   style="min-width: 120px; height: 36px;"
                                                   target="_blank">
                                                    <i class="bi bi-file-earmark-text me-1"></i> <@spring.message "books.open"/>
                                                </a>
                                            </#if>

                                            <a href="/api/files/${bookItem.file.id}/download"
                                               class="btn btn-sm btn-outline-secondary d-flex align-items-center justify-content-center"
                                               style="min-width: 120px; height: 36px;">
                                                <i class="bi bi-download me-1"></i> <@spring.message "books.download"/>
                                            </a>
                                            <#if currentUser?? && (isAdmin || (bookItem.creator?? && bookItem.creator.id == currentUser.id))>
                                                <form action="/books/delete/${bookItem.id}" method="post" style="">
                                                    <@main.csrf/>
                                                    <input type="hidden" name="_method" value="delete"/>
                                                    <button type="submit" class=" btn btn-sm" style="background: linear-gradient(135deg, #dc2626, #b91c1c);">
                                                        🗑 <@spring.message "test.details.delete"/>
                                                    </button>
                                                </form>
                                            </#if>
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

                    <nav aria-label="Page navigation" class="pagination-nav">
                        <ul class="pagination justify-content-center">
                            <li class="page-item <#if book.isFirst()>disabled</#if>">
                                <a class="page-link"
                                   href="?page=${currentPage - 1}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}"
                                   aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>

                            <#if startPage gt 1>
                                <li class="page-item">
                                    <a class="page-link"
                                       href="?page=1&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}">1</a>
                                </li>
                                <#if startPage gt 2>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </#if>
                            </#if>

                            <#list startPage..endPage as i>
                                <li class="page-item <#if currentPage == i>active</#if>">
                                    <a class="page-link"
                                       href="?page=${i}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}">${i}</a>
                                </li>
                            </#list>

                            <#if endPage lt book.totalPages>
                                <#if endPage lt book.totalPages - 1>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                </#if>
                                <li class="page-item">
                                    <a class="page-link"
                                       href="?page=${book.totalPages}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}">${book.totalPages}</a>
                                </li>
                            </#if>

                            <li class="page-item <#if book.isLast()>disabled</#if>">
                                <a class="page-link"
                                   href="?page=${currentPage + 1}&size=${book.size}<#if query??>&query=${query}</#if><#if categoryId??>&categoryId=${categoryId}</#if>&sortBy=${sortBy}&sortDirection=${sortDirection}"
                                   aria-label="Next">
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
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes slideInLeft {
            from {
                opacity: 0;
                transform: translateX(-20px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }

        @keyframes pulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.02);
            }
        }

        .page-title {
            animation: slideInLeft 0.6s ease-out;
            color: #2c3e50;
        }

        .sidebar-wrapper {
            position: relative;
        }

        .sidebar-content {
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .categories-list {
            max-height: none;
            overflow: visible;
        }

        .category-link {
            transition: all 0.3s ease;
            border-radius: 6px;
            margin: 2px 0;
        }

        .category-link:hover {
            background-color: #f8f9fa;
            transform: translateX(5px);
            padding-left: 1rem;
        }

        .active-category {
            background-color: #e3f2fd;
            color: #1976d2 !important;
        }

        .search-form {
            animation: fadeInUp 0.6s ease-out 0.2s both;
        }

        .search-input {
            transition: all 0.3s ease;
            border: 1px solid #dee2e6;
        }

        .search-input:focus {
            border-color: #0d6efd;
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.15);
            transform: translateY(-1px);
        }

        .search-btn, .add-book-btn, .categories-toggle {
            transition: all 0.3s ease;
        }

        .search-btn:hover, .categories-toggle:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(13, 110, 253, 0.3);
        }

        .add-book-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(25, 135, 84, 0.3);
        }

        .books-grid {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .book-card {
            background: #ffffff;
            border: 1px solid #e9ecef;
            border-radius: 8px;
            padding: 1.25rem;
            transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
            animation: fadeInUp 0.6s ease-out;
            animation-fill-mode: both;
            position: relative;
            overflow: hidden;
        }

        .book-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.4), transparent);
            transition: left 0.5s ease;
        }

        .book-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
            border-color: #0d6efd;
        }

        .book-card:hover::before {
            left: 100%;
        }

        .book-card:nth-child(1) { animation-delay: 0.1s; }
        .book-card:nth-child(2) { animation-delay: 0.2s; }
        .book-card:nth-child(3) { animation-delay: 0.3s; }
        .book-card:nth-child(4) { animation-delay: 0.4s; }
        .book-card:nth-child(5) { animation-delay: 0.5s; }
        .book-card:nth-child(n+6) { animation-delay: 0.6s; }

        /* Обложка книги */
        .book-cover-container {
            position: relative;
        }

        .book-cover {
            width: 120px;
            height: 160px;
            object-fit: cover;
            border-radius: 6px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .book-cover-placeholder {
            width: 120px;
            height: 160px;
            border-radius: 6px;
            font-size: 2rem;
            color: #6c757d;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .book-cover-overlay {
            position: absolute;
            bottom: 5px;
            right: 5px;
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .book-card:hover .book-cover-overlay {
            opacity: 1;
        }

        .view-badge {
            background: rgba(0,0,0,0.7);
            color: white;
            padding: 2px 6px;
            border-radius: 10px;
            font-size: 0.75rem;
        }

        .book-card:hover .book-cover {
            transform: scale(1.05);
        }

        .book-info {
            animation: slideInLeft 0.6s ease-out 0.1s both;
        }

        .book-title {
            color: #2c3e50;
            transition: color 0.3s ease;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
            font-size: 1.25rem;
            line-height: 1.2;
            max-height: 1.5em;
        }

        .book-card:hover .book-title {
            color: #0d6efd;
        }

        .book-description {
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
            line-height: 1.4;
            max-height: 2.8em;
            font-size: 0.9rem;
        }

        .category-badge {
            transition: all 0.3s ease;
            font-size: 0.75rem;
        }

        .book-card:hover .category-badge {
            background-color: #0d6efd !important;
            transform: scale(1.05);
        }

        .book-meta {
            border-top: 1px solid #f8f9fa;
            padding-top: 0.5rem;
            margin-top: 0.5rem;
        }

        /* Кнопка подробнее */
        .details-btn {
            transition: all 0.3s ease;
            border-radius: 6px;
            font-weight: 500;
            padding: 0.375rem 0.75rem;
            font-size: 0.875rem;
            line-height: 1.25;
            height: 32px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .details-btn:hover {
            background-color: #0d6efd;
            border-color: #0d6efd;
            color: white;
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(13, 110, 253, 0.3);
        }

        .no-books-alert {
            animation: fadeInUp 0.6s ease-out;
            border-radius: 8px;
            border-left: 4px solid #17a2b8;
        }

        .pagination-nav {
            animation: fadeInUp 0.6s ease-out 0.4s both;
        }

        .pagination .page-link {
            transition: all 0.3s ease;
            border-radius: 6px;
            margin: 0 2px;
        }

        .pagination .page-link:hover {
            transform: translateY(-1px);
            box-shadow: 0 2px 8px rgba(13, 110, 253, 0.2);
        }

        .pagination .page-item.active .page-link {
            background-color: #0d6efd;
            border-color: #0d6efd;
            transform: scale(1.1);
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
            border-radius: 6px;
        }

        /* Адаптивность */
        @media (max-width: 768px) {
            .book-card {
                padding: 1rem;
            }

            .book-cover, .book-cover-placeholder {
                width: 90px;
                height: 120px;
            }

            .book-title {
                font-size: 1.1rem;
            }

            .search-form {
                flex-direction: row;
                align-items: stretch;
            }

            .search-form input {
                flex: 1;
            }

            .search-btn, .add-book-btn {
                width: auto;
                min-width: 80px;
                flex-shrink: 0;
            }

            .categories-toggle {
                width: 100%;
                margin-bottom: 1rem;
            }

            .book-meta span {
                display: block;
                margin-bottom: 0.25rem;
            }

            .details-btn {
                width: 100%;
            }

            .sidebar-wrapper {
                position: fixed;
                top: auto;
                bottom: 20px;
                left: 20px;
                right: 20px;
                width: auto;
                height: auto;
                max-height: 300px;
                z-index: 1050;
                background: transparent;
                backdrop-filter: none;
            }

            .sidebar-wrapper:not(.show) {
                display: none !important;
            }

            .sidebar-wrapper.show {
                display: block !important;
                animation: slideUpFadeIn 0.3s ease-out;
            }

            @keyframes slideUpFadeIn {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .sidebar-content {
                width: 100%;
                height: auto;
                max-height: 300px;
                margin: 0;
                border-radius: 12px;
                box-shadow: 0 10px 40px rgba(0,0,0,0.2);
                position: relative;
                overflow: hidden;
                background: white;
                border: 1px solid #dee2e6;
            }

            .filter-section {
                height: auto;
                max-height: 300px;
                overflow-y: auto;
                padding: 1rem !important;
            }

            .categories-list {
                max-height: 250px;
                overflow-y: auto;
                padding-right: 8px;
                margin: 0;
            }

            .categories-list::-webkit-scrollbar {
                width: 4px;
            }

            .categories-list::-webkit-scrollbar-track {
                background: #f8f9fa;
                border-radius: 2px;
            }

            .categories-list::-webkit-scrollbar-thumb {
                background: #dee2e6;
                border-radius: 2px;
            }

            .categories-list::-webkit-scrollbar-thumb:hover {
                background: #adb5bd;
            }

            .categories-list .nav-item {
                margin-bottom: 4px;
            }

            .categories-list .category-link {
                padding: 8px 12px;
                border-radius: 6px;
                font-size: 0.9rem;
                transition: all 0.2s ease;
            }

            .categories-list .category-link:hover {
                background-color: #f8f9fa;
                transform: none;
                padding-left: 12px;
            }

            .sidebar-content::before {
                display: none;
            }
        }

        @media (max-width: 576px) {
            .book-card .d-flex {
                flex-direction: column;
                align-items: center;
                text-align: center;
            }

            .book-info {
                width: 100%;
                margin-top: 1rem;
            }

            .book-cover, .book-cover-placeholder {
                width: 120px;
                height: 160px;
            }

            .category-badge {
                align-self: center !important;
                margin-top: 0.5rem;
            }

            .sidebar-content {
                width: 100%;
                max-width: 320px;
                margin: 0 auto;
            }
        }

        @media (max-width: 767.98px) {
            .sidebar-wrapper.show {
                display: block !important;
            }

            .sidebar-wrapper:not(.show) {
                display: none !important;
            }
        }

        html {
            scroll-behavior: smooth;
        }

        .container-fluid {
            animation: fadeInUp 0.8s ease-out;
        }
    </style>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            function truncateText(element, maxLength) {
                const originalText = element.textContent;
                if (originalText.length > maxLength) {
                    element.textContent = originalText.substring(0, maxLength) + '...';
                    element.setAttribute('title', originalText); // Показываем полный текст в подсказке
                }
            }

            document.querySelectorAll('.book-title').forEach(title => {
                truncateText(title, 20);
            });

            document.querySelectorAll('.book-description').forEach(description => {
                truncateText(description, 20);
            });

            const observerOptions = {
                threshold: 0.1,
                rootMargin: '0px 0px -50px 0px'
            };

            const observer = new IntersectionObserver(function(entries) {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.style.opacity = '1';
                        entry.target.style.transform = 'translateY(0)';
                    }
                });
            }, observerOptions);

            document.querySelectorAll('.book-card').forEach(card => {
                observer.observe(card);
            });

            document.querySelectorAll('.book-card').forEach(card => {
                card.addEventListener('mouseenter', function() {
                    this.style.transform = 'translateY(-3px) scale(1.02)';
                });

                card.addEventListener('mouseleave', function() {
                    this.style.transform = 'translateY(0) scale(1)';
                });
            });

            document.querySelectorAll('.view-badge').forEach(badge => {
                const text = badge.textContent.trim();
                const number = parseInt(text);
                if (!isNaN(number) && number > 0) {
                    let current = 0;
                    const increment = Math.ceil(number / 30);
                    const timer = setInterval(() => {
                        current += increment;
                        if (current >= number) {
                            badge.textContent = number;
                            clearInterval(timer);
                        } else {
                            badge.textContent = current;
                        }
                    }, 50);
                }
            });

            const sidebar = document.getElementById('sidebarCategories');
            const toggleButton = document.querySelector('[data-bs-target="#sidebarCategories"]');

            if (sidebar && toggleButton) {
                document.addEventListener('click', function(e) {
                    if (window.innerWidth <= 768 &&
                        sidebar.classList.contains('show') &&
                        !sidebar.contains(e.target) &&
                        !toggleButton.contains(e.target)) {

                        const bsCollapse = new bootstrap.Collapse(sidebar);
                        bsCollapse.hide();
                    }
                });

                document.addEventListener('keydown', function(e) {
                    if (e.key === 'Escape' && window.innerWidth <= 768 && sidebar.classList.contains('show')) {
                        const bsCollapse = new bootstrap.Collapse(sidebar);
                        bsCollapse.hide();
                    }
                });

                sidebar.addEventListener('click', function(e) {
                    if (window.innerWidth <= 768 && e.target.classList.contains('category-link')) {
                        setTimeout(() => {
                            const bsCollapse = new bootstrap.Collapse(sidebar);
                            bsCollapse.hide();
                        }, 150);
                    }
                });
            }

            document.querySelectorAll('a[href^="#"]').forEach(anchor => {
                anchor.addEventListener('click', function (e) {
                    e.preventDefault();
                    const target = document.querySelector(this.getAttribute('href'));
                    if (target) {
                        target.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start'
                        });
                    }
                });
            });
        });
    </script>

</@main.layout>
