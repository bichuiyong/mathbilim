<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
<style>
    .card {
    border-radius: 8px;
    transition: box-shadow 0.2s ease;
    height: 100%;
    }

    .card:hover {
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    .card-title {
    font-weight: 500;
    margin-bottom: 0.5rem;
    }

    .card-text {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    min-height: 4.5em;
    }

    .badge {
    font-weight: 500;
    }

    .pagination {
    gap: 0.5rem;
    }

    .page-item {
    border-radius: 8px;
    }

    .page-link {
    min-width: 2.5rem;
    text-align: center;
    }

    .btn-primary {
        background-color: #388dff;
    }

    .custom-btn {
        font-weight: 500;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #388dff;
    }

    .btn-outline-secondary {
        white-space: nowrap;
        height: 40px;
    }

</style>

    <#if SPRING_SECURITY_CONTEXT??>
        <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
        <#assign authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)>
        <#assign
        isAdmin = authorities?seq_contains("ADMIN") || authorities?seq_contains("SUPER_ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        isUser = authorities?seq_contains("USER")
        >
    </#if>

    <div class="container mt-4" style="margin-bottom: 50px">
        <div class="row">
            <main class="col-md-12 px-md-4">
                <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap">
                    <h1 class="h2 mb-3 mb-md-0"><@spring.message "test.list.tests"/></h1>
                </div>

                <div class="d-flex mb-4 flex-column flex-sm-row gap-2">
                    <div class="flex-grow-1">
                        <form action="" id="search" method="get">
                            <input type="text" name="keyword" id="search" <#if keyword??>value="${keyword}" </#if> class="form-control" placeholder="<@spring.message"test.list.findBy"/>">
                        </form>
                    </div>
                    <div class="d-flex gap-2">
                        <button form="search" type="submit" class="btn btn-primary flex-shrink-0"><@spring.message "test.list.find"/></button>
                    </div>
                </div>

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <div class="d-flex align-items-center gap-2">
<#--                        <span class="text-muted">Сортировка:</span>-->
<#--                        <select class="form-select form-select-sm" style="width: auto;">-->
<#--                            <option>По дате (новые)</option>-->
<#--                            <option>По дате (старые)</option>-->
<#--                        </select>-->
                    </div>
                    <div class="text-muted"><@spring.message "test.list.finded"/> ${total}</div>
                </div>

                <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-4">


                    <#list tests.content as test>
                    <div class="col">
                        <div class="card h-100 border-0 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">${test.name}</h5>
                                <div class="d-flex align-items-center mb-3">
                                    <span class="badge bg-light text-dark"><i class="bi bi-question-circle"></i> ${test.questionCount} <@spring.message "test.list.ques"/></span>
                                </div>
                                <p class="card-text">${test.description}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <small class="text-muted"><#if test.hasLimit>${test.timeLimit} <@spring.message "test.list.minute"/><#else><@spring.message "test.list.noTime"/></#if></small>
                                </div>
                                <div class="d-flex justify-content-between align-items-center gap-2 mt-2">
                                    <a class="btn btn-primary custom-btn flex-grow-1" href="/tests/${test.id}/pass"><@spring.message "test.list.ready"/></a>
                                    <a class="btn btn-outline-secondary" href="/tests/${test.id}"><@spring.message "test.list.details"/></a>
                                </div>

                            </div>
                        </div>
                    </div>
                    </#list>
                </div>

                <#if total == 0>
                    <div style="display: flex; flex-direction: column; justify-content: center; align-items: center; height: 60vh; text-align: center;">
                        <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M12 6.59097L11.8456 6.42726C9.86801 4.33053 6.59738 4.57698 4.91934 6.94915C3.42999 9.05459 3.78668 12.0335 5.725 13.6776L12 19L18.275 13.6776C20.2133 12.0335 20.57 9.05459 19.0807 6.94915C17.4026 4.57697 14.132 4.33053 12.1544 6.42726L12 6.59097ZM12 6.59097L10.5 8.5L13 11L11 13.5" stroke="#464455" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                        <h4 class="mt-3"><@spring.message "test.list.noTest"/></h4>
                        <p class="text-muted"><@spring.message "test.list.changeSearch"/></p>
                    </div>
                </#if>

                <#if total != 0>
                <div class="pagination justify-content-center">

                    <#if (tests.number > 0)>
                        <a href="?page=${tests.number - 1}<#if keyword??>&keyword=${keyword}</#if>" class="page-link">&laquo;</a>
                    </#if>

                    <#if (tests.number - 1 >= 0)>
                        <a href="?page=${tests.number - 1}<#if keyword??>&keyword=${keyword}</#if>" class="page-link">${tests.number}</a>
                    </#if>

                    <a href="?page=${tests.number}<#if keyword??>&keyword=${keyword}</#if>" class="page-link active">${tests.number + 1}</a>

                    <#if tests.number + 1 < tests.totalPages>
                        <a href="?page=${tests.number + 1}<#if keyword??>&keyword=${keyword}</#if>" class="page-link">${tests.number + 2}</a>
                    </#if>

                    <#if tests.number + 2 < tests.totalPages>
                        <a href="?page=${tests.number + 2}<#if keyword??>&keyword=${keyword}</#if>" class="page-link">${tests.number + 3}</a>
                    </#if>

                    <#if tests.number + 1 < tests.totalPages>
                        <a href="?page=${tests.number + 1}<#if keyword??>&keyword=${keyword}</#if>" class="page-link">&raquo;</a>
                    </#if>

                </div>
            </#if>

            </main>
        </div>
    </div>

    <style>
        .card {
            border-radius: 8px;
            transition: box-shadow 0.2s ease;
        }

        .card:hover {
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .card-title {
            font-weight: 500;
            margin-bottom: 0.5rem;
        }

        .badge {
            font-weight: 500;
        }

        .pagination {
            gap: 0.5rem;
        }

        .page-item {
            border-radius: 8px;
        }

        .page-link {
            min-width: 2.5rem;
            text-align: center;
        }
    </style>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-w76A2z02tPqdjfvYtmO3pC6tE3PaVTK/rb+8q2FxC5VdPfYjdf29tGxCiqbslY+/"
            crossorigin="anonymous"></script>
</@main.layout>