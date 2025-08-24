<#import "../layout.ftlh" as main>
<@main.layout title="${springMacroRequestContext.getMessage('profile.public.title')}">
    <head>
        <meta name="_csrf" content="${_csrf.token}">
        <title>${springMacroRequestContext.getMessage('profile.public.title')}
            - ${userDto.name!} ${userDto.surname!}</title>
    </head>

    <style>
        .content-card {
            border: 1px solid #dee2e6;
            border-radius: 8px;
            overflow: hidden;
            background: white;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }

        .content-card:hover {
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .content-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
            cursor: pointer;
        }

        .content-info {
            flex: 1;
            padding: 15px;
        }

        .content-title {
            font-size: 16px;
            font-weight: 600;
            color: #212529;
            margin-bottom: 8px;
            cursor: pointer;
        }

        .content-title:hover {
            color: #0d6efd;
        }

        .content-meta {
            color: #6c757d;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .modal-content {
            border-radius: 12px;
        }

        .modal-image {
            width: 100%;
            max-height: 300px;
            object-fit: cover;
            border-radius: 8px;
            margin-bottom: 15px;
        }

        .publication-text {
            max-height: 200px;
            overflow-y: auto;
            line-height: 1.6;
            color: #495057;
        }

        .publication-text.collapsed {
            max-height: 150px;
            overflow: hidden;
            position: relative;
        }

        .publication-text.expanded {
            max-height: none;
        }

        .show-more-btn {
            color: #0d6efd;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
        }

        .show-more-btn:hover {
            text-decoration: underline;
        }

        .no-content {
            text-align: center;
            padding: 60px 20px;
            color: #6c757d;
        }

        .bg-purple {
            background-color: #6f42c1 !important;
        }

        .fa-crown {
            color: gold;
        }

        .profile-card {
            border: none;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            border-radius: 12px;
        }

        .profile-avatar-container {
            position: relative;
            display: inline-block;
        }

        .profile-avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #e9ecef;
        }

        .profile-avatar-placeholder {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 48px;
            border: 4px solid #e9ecef;
        }

        .bg-light-blue {
            background-color: rgba(78, 115, 223, 0.1) !important;
        }

        .content-list {
            min-height: 400px;
        }

        .content-image-wrapper {
            margin-right: 16px;
        }

        /* Pagination styles */
        .pagination-wrapper {
            margin: 20px 0;
        }

        .pagination-wrapper .pagination {
            gap: 8px;
            margin: 0;
            justify-content: center;
        }

        .pagination-wrapper .page-item {
            margin: 0;
        }

        .pagination-wrapper .page-link {
            border: 1px solid #e0e6ed;
            border-radius: 8px;
            color: #374151;
            font-weight: 500;
            font-size: 14px;
            padding: 10px;
            width: 44px;
            height: 44px;
            text-align: center;
            text-decoration: none;
            transition: all 0.2s ease-in-out;
            background-color: #ffffff;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .pagination-wrapper .page-link:hover {
            background-color: #f8fafc;
            border-color: #3b82f6;
            color: #3b82f6;
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.12);
        }

        .pagination-wrapper .page-item.active .page-link {
            background-color: #3b82f6;
            border-color: #3b82f6;
            color: #ffffff;
            font-weight: 600;
            box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
        }

        .pagination-wrapper .page-item.active .page-link:hover {
            background-color: #2563eb;
            border-color: #2563eb;
            transform: translateY(-1px);
        }

        .pagination-wrapper .page-item.disabled .page-link {
            background-color: #f9fafb;
            border-color: #e5e7eb;
            color: #9ca3af;
            cursor: not-allowed;
            box-shadow: none;
        }

        .pagination-wrapper .page-item.disabled .page-link:hover {
            background-color: #f9fafb;
            border-color: #e5e7eb;
            color: #9ca3af;
            transform: none;
            box-shadow: none;
        }

        .pagination-wrapper .page-link i {
            font-size: 14px;
        }

        @media (max-width: 576px) {
            .pagination-wrapper .pagination {
                gap: 4px;
                flex-wrap: wrap;
                justify-content: center;
            }

            .pagination-wrapper .page-link {
                width: 38px;
                height: 38px;
                padding: 8px;
                font-size: 13px;
            }
        }

        .pagination-wrapper .pagination {
            animation: fadeIn 0.3s ease-out;
        }

        .pagination-wrapper .page-link:focus {
            outline: 2px solid #3b82f6;
            outline-offset: 2px;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }

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

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        .content-card {
            animation: fadeInUp 0.3s ease-out;
        }
    </style>

    <div class="container mt-4" data-user-id="${userDto.id!}">
        <div class="row">
            <div class="col-lg-4 mb-4">
                <div class="card profile-card">
                    <div class="card-body text-center">
                        <div class="profile-avatar-container mb-3">
                            <#if userDto.avatar??>
                                <img src="/api/files/${userDto.avatar.id}/download"
                                     alt="Profile Avatar" class="profile-avatar">
                            <#else>
                                <div class="profile-avatar-placeholder">
                                    <i class="fas fa-user"></i>
                                </div>
                            </#if>
                        </div>

                        <h4 class="mb-1">${userDto.name!} ${userDto.surname!}</h4>

                        <#if userDto.type??>
                            <span class="badge bg-light-blue text-primary mb-3">
                                ${userDto.type.translations[0].translation}
                            </span>
                        </#if>
                    </div>
                </div>

                <!-- Информация о пользователе -->
                <div class="card mt-3">
                    <div class="card-body">
                        <h6 class="card-title mb-3">${springMacroRequestContext.getMessage('profile.information')}</h6>
                        <div class="row mb-2">
                            <div class="col-4">
                                <small class="text-muted">${springMacroRequestContext.getMessage('profile.name')}
                                    :</small>
                            </div>
                            <div class="col-8">
                                <span>${userDto.name!}</span>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col-4">
                                <small class="text-muted">${springMacroRequestContext.getMessage('profile.surname')}
                                    :</small>
                            </div>
                            <div class="col-8">
                                <span>${userDto.surname!}</span>
                            </div>
                        </div>
                        <#if userDto.type??>
                            <div class="row">
                                <div class="col-4">
                                    <small class="text-muted">${springMacroRequestContext.getMessage('profile.accountType')}
                                        :</small>
                                </div>
                                <div class="col-8">
                                    <span class="badge bg-light-blue text-primary">
                                        ${userDto.type.translations[0].translation}
                                    </span>
                                </div>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>

            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <ul class="nav nav-tabs card-header-tabs" id="profileTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="content-tab" data-bs-toggle="tab"
                                        data-bs-target="#content" type="button" role="tab">
                                    <i class="fas fa-file-alt me-2"></i>${springMacroRequestContext.getMessage('profile.publishedContent')}
                                </button>
                            </li>
                        </ul>
                    </div>

                    <div class="card-body">
                        <div class="tab-content" id="userContentList">
                            <div class="tab-pane fade show active" id="content" role="tabpanel">
                                <div class="content-filters mb-4">
                                    <div class="row g-2">
                                        <div class="col-md-4">
                                            <select class="form-select form-select-sm" id="contentFilter">
                                                <option value="all">${springMacroRequestContext.getMessage('profile.contentTypes.all')}</option>
                                                <option value="posts">${springMacroRequestContext.getMessage('profile.contentTypes.posts')}</option>
                                                <option value="blog">${springMacroRequestContext.getMessage('profile.contentTypes.blog')}</option>
                                                <option value="events">${springMacroRequestContext.getMessage('profile.contentTypes.events')}</option>
                                                <option value="books">${springMacroRequestContext.getMessage('profile.contentTypes.books')}</option>
                                            </select>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="input-group input-group-sm">
                                                <input type="text" class="form-control" id="contentSearch"
                                                       placeholder="Search content...">
                                                <button class="btn btn-outline-secondary" type="button"
                                                        id="contentSearchBtn">
                                                    <i class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="userContentList" class="content-list">
                                    <div class="text-center text-muted py-5">
                                        <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                        <p>${springMacroRequestContext.getMessage('profile.loading')}</p>
                                    </div>
                                </div>

                                <nav id="contentPagination" class="mt-3" style="display: none;">
                                    <ul class="pagination justify-content-center"></ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="contentModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title"
                        id="modalTitle">${springMacroRequestContext.getMessage('profile.publishedContent')}</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div id="modalContent"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        ${springMacroRequestContext.getMessage('profile.close')}
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/profile-content.js"></script>
</@main.layout>
