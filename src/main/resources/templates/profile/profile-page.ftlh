<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout title="Профиль">
    <head>
        <meta name="_csrf" content="${_csrf.token}">
        <title></title>
    </head>

    <style>
        content-item {
            transition: all 0.3s ease;
        }

        .content-item:hover {
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .btn-group .btn {
            border-radius: 0.25rem !important;
            margin-left: 2px;
        }

        .btn-group .btn:first-child {
            margin-left: 0;
        }

        .alert.position-fixed {
            animation: slideInRight 0.3s ease-out;
        }

        @keyframes slideInRight {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }
        .collapsed {
            max-height: 150px;
            overflow: hidden;
            position: relative;
        }

        .moderation-image-wrapper {
            margin-right: 16px;
        }

        .moderation-image {
            width: 85px;
            height: 80px;
            object-fit: cover;

            cursor: pointer;
            padding-top: 10px;
            padding-left: 16px;
        }


        .moderation-card {
            display: flex;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            overflow: hidden;
            background: white;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }

        .expanded {
            max-height: none;
        }

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

        .status-badge {
            font-size: 12px;
            padding: 6px 12px;
            border-radius: 20px;
            font-weight: 500;
            display: inline-block;
            margin-right: 10px;
        }

        .status-pending {
            background-color: #fff3cd;
            color: #856404;
        }

        .status-approved {
            background-color: #d1edff;
            color: #0c63e4;
        }

        .status-rejected {
            background-color: #f8d7da;
            color: #721c24;
        }

        .action-buttons {
            display: flex;
            gap: 8px;
            align-items: center;
            padding: 15px;
            border-left: 1px solid #dee2e6;
        }

        .btn-approve {
            background: linear-gradient(135deg, #48bb78 0%, #38a169 100%);
            border: none;
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-approve:hover {
            background: linear-gradient(135deg, #38a169 0%, #2f855a 100%);
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(72, 187, 120, 0.4);
            color: white;
        }

        .btn-reject {
            background: linear-gradient(135deg, #f56565 0%, #e53e3e 100%);
            border: none;
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-reject:hover {
            background: linear-gradient(135deg, #e53e3e 0%, #c53030 100%);
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(245, 101, 101, 0.4);
            color: white;
        }

        /* Улучшенные стили для модального окна */
        .modal-content {
            border: none;
            border-radius: 16px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
            overflow: hidden;
        }

        .modal-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 24px;
            border-bottom: none;
            position: relative;
        }

        .modal-header::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 1px;
            background: rgba(255, 255, 255, 0.2);
        }

        .modal-header .modal-title {
            font-weight: 600;
            font-size: 1.25rem;
            margin: 0;
        }

        .modal-header .btn-close {
            filter: brightness(0) invert(1);
            opacity: 0.8;
            font-size: 1.1rem;
            margin: 0;
        }

        .modal-header .btn-close:hover {
            opacity: 1;
            transform: scale(1.1);
        }

        .modal-body {
            padding: 24px;
            max-height: 70vh;
            overflow-y: auto;
            scrollbar-width: thin;
            scrollbar-color: #cbd5e0 #f8f9fa;
        }

        .modal-body::-webkit-scrollbar {
            width: 6px;
        }

        .modal-body::-webkit-scrollbar-track {
            background: #f8f9fa;
            border-radius: 3px;
        }

        .modal-body::-webkit-scrollbar-thumb {
            background: #cbd5e0;
            border-radius: 3px;
        }

        .modal-body::-webkit-scrollbar-thumb:hover {
            background: #a0aec0;
        }

        .modal-image {
            width: 100%;
            max-height: 280px;
            object-fit: cover;
            border-radius: 12px;
            margin-bottom: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .modal-image:hover {
            transform: scale(1.02);
        }

        .content-detail-item {
            margin-bottom: 16px;
            padding: 12px 0;
            border-bottom: 1px solid #f1f5f9;
        }

        .content-detail-item:last-child {
            border-bottom: none;
            margin-bottom: 0;
        }

        .content-detail-label {
            font-weight: 600;
            color: #2d3748;
            font-size: 0.9rem;
            margin-bottom: 6px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .content-detail-value {
            color: #4a5568;
            line-height: 1.6;
            font-size: 1rem;
        }

        .badge.bg-secondary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
        }

        /* Улучшенные стили для текстового контента */
        .publication-text {
            line-height: 1.7;
            color: #2d3748;
            font-size: 1rem;
            word-wrap: break-word;
            position: relative;
            transition: all 0.3s ease;
        }

        .publication-text.collapsed {
            max-height: 150px;
            overflow: hidden;
            position: relative;
        }

        .publication-text.collapsed::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 40px;
            background: linear-gradient(to bottom, transparent, white);
            pointer-events: none;
        }

        .publication-text.expanded {
            max-height: none;
        }

        .publication-text.expanded::after {
            display: none;
        }

        .show-more-btn {
            color: #667eea;
            cursor: pointer;
            font-size: 0.9rem;
            font-weight: 600;
            margin-top: 12px;
            padding: 8px 16px;
            border: 2px solid #667eea;
            border-radius: 20px;
            background: white;
            transition: all 0.3s ease;
            display: inline-block;
            text-decoration: none;
        }

        .show-more-btn:hover {
            background: #667eea;
            color: white;
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
        }

        .show-more-btn i {
            margin-left: 6px;
            transition: transform 0.3s ease;
        }

        .show-more-btn.expanded i {
            transform: rotate(180deg);
        }

        /* Стили для дополнительной информации */
        .additional-info {
            background: #f8fafc;
            border-radius: 12px;
            padding: 16px;
            margin: 16px 0;
            border-left: 4px solid #667eea;
        }

        .additional-info .info-item {
            display: flex;
            align-items: flex-start;
            margin-bottom: 8px;
        }

        .additional-info .info-item:last-child {
            margin-bottom: 0;
        }

        .additional-info .info-label {
            font-weight: 600;
            color: #2d3748;
            min-width: 100px;
            margin-right: 12px;
        }

        .additional-info .info-value {
            color: #4a5568;
            flex: 1;
        }

        /* Улучшенные стили для футера модального окна */
        .modal-footer {
            background: #f8fafc;
            padding: 16px 24px;
            border-top: 1px solid #e2e8f0;
        }

        .no-content {
            text-align: center;
            padding: 60px 20px;
            color: #6c757d;
        }

        .navbar-nav .nav-link.active {
            color: #0d6efd !important;
            font-weight: 500;
        }


        /* Красивые стили для пагинации */
        #usersPagination, #allContentPagination {
            margin: 20px 0;
        }

        #usersPagination .pagination, #allContentPagination .pagination {
            gap: 8px;
            margin: 0;
        }

        #usersPagination .page-item, #allContentPagination .page-item {
            margin: 0;
        }

        #usersPagination .page-link, #allContentPagination .page-link {
            border: 1px solid #e0e6ed;
            border-radius: 8px;
            color: #374151;
            font-weight: 500;
            font-size: 14px;
            padding: 10px 16px;
            min-width: 44px;
            text-align: center;
            text-decoration: none;
            transition: all 0.2s ease-in-out;
            background-color: #ffffff;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        #usersPagination .page-link:hover, #allContentPagination .page-link:hover {
            background-color: #f8fafc;
            border-color: #3b82f6;
            color: #3b82f6;
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.12);
        }

        #usersPagination .page-item.active .page-link, #allContentPagination .page-item.active .page-link {
            background-color: #3b82f6;
            border-color: #3b82f6;
            color: #ffffff;
            font-weight: 600;
            box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
        }

        #usersPagination .page-item.active .page-link:hover, #allContentPagination .page-item.active .page-link:hover {
            background-color: #2563eb;
            border-color: #2563eb;
            transform: translateY(-1px);
        }

        #usersPagination .page-item.disabled .page-link,
        #usersPagination .page-item.disabled span,
        #allContentPagination .page-item.disabled .page-link,
        #allContentPagination .page-item.disabled span {
            background-color: #f9fafb;
            border-color: #e5e7eb;
            color: #9ca3af;
            cursor: not-allowed;
            box-shadow: none;
        }

        #usersPagination .page-item.disabled .page-link:hover,
        #usersPagination .page-item.disabled span:hover,
        #allContentPagination .page-item.disabled .page-link:hover,
        #allContentPagination .page-item.disabled span:hover {
            background-color: #f9fafb;
            border-color: #e5e7eb;
            color: #9ca3af;
            transform: none;
            box-shadow: none;
        }

        /* Стили для навигационных кнопок */
        #usersPagination .page-link.nav-link, #allContentPagination .page-link.nav-link {
            font-weight: 500;
            padding: 10px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
        }

        #usersPagination .page-link.nav-link:hover, #allContentPagination .page-link.nav-link:hover {
            background: linear-gradient(135deg, #5a67d8 0%, #6b46c1 100%);
            color: white;
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }

        /* Многоточие */
        #usersPagination .page-item.disabled span, #allContentPagination .page-item.disabled span {
            border: none;
            background: transparent;
            color: #9ca3af;
            padding: 10px 8px;
            box-shadow: none;
            font-weight: bold;
        }

        /* Адаптивность */
        @media (max-width: 576px) {
            #usersPagination .pagination, #allContentPagination .pagination {
                gap: 4px;
                flex-wrap: wrap;
                justify-content: center;
            }

            #usersPagination .page-link, #allContentPagination .page-link {
                padding: 8px 12px;
                min-width: 38px;
                font-size: 13px;
            }

            #usersPagination .page-link.nav-link, #allContentPagination .page-link.nav-link {
                padding: 8px 16px;
            }

            .modal-dialog {
                margin: 10px;
                max-width: calc(100% - 20px);
            }

            .modal-body {
                padding: 16px;
                max-height: 60vh;
            }

            .modal-header {
                padding: 16px;
            }

            .modal-footer {
                padding: 12px 16px;
                flex-direction: column;
                gap: 8px;
            }

            .modal-footer .btn {
                width: 100%;
            }
        }

        /* Анимации */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        #usersPagination .pagination, #allContentPagination .pagination {
            animation: fadeIn 0.3s ease-out;
        }

        #usersPagination .page-link:focus, #allContentPagination .page-link:focus {
            outline: 2px solid #3b82f6;
            outline-offset: 2px;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }

        .bg-purple {
            background-color: #6f42c1 !important;
        }

        .fa-crown {
            color: gold;
        }


        #organizationsContentList .card {
            border: 1px solid #e3e6f0;
            border-radius: 12px;
            transition: all 0.3s ease;
            background: #fff;
        }

        #organizationsContentList .card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            border-color: #4e73df;
        }

        .organization-avatar img {
            border: 2px solid #e3e6f0;
            transition: border-color 0.3s ease;
        }

        .organization-avatar .bg-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
        }

        .btn-group .btn {
            min-width: 44px;
            height: 38px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-width: 1px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-group .btn:first-child {
            border-top-right-radius: 0;
            border-bottom-right-radius: 0;
        }

        .btn-group .btn:last-child {
            border-top-left-radius: 0;
            border-bottom-left-radius: 0;
            border-left: 0;
        }

        .btn-outline-primary:hover {
            background-color: #4e73df;
            border-color: #4e73df;
            transform: translateY(-1px);
        }

        .btn-outline-danger:hover {
            background-color: #e74a3b;
            border-color: #e74a3b;
            transform: translateY(-1px);
        }

        .organization-info h5 {
            font-weight: 600;
            color: #2c3e50;
        }

        .organization-info .card-text {
            line-height: 1.4;
        }

        /* Стили для пагинации */
        #organizationsPagination .pagination {
            gap: 4px;
        }

        #organizationsPagination .page-link {
            border-radius: 8px;
            border: 1px solid #e3e6f0;
            color: #6c757d;
            padding: 8px 12px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        #organizationsPagination .page-link:hover {
            background-color: #4e73df;
            border-color: #4e73df;
            color: white;
            transform: translateY(-1px);
        }

        #organizationsPagination .page-item.active .page-link {
            background-color: #4e73df;
            border-color: #4e73df;
            color: white;
        }

        .spinner-border {
            width: 3rem;
            height: 3rem;
        }

        #organizationsSearch, #allContentSearch {
            border-radius: 8px;
            border: 1px solid #e3e6f0;
            padding: 8px 16px;
            transition: all 0.3s ease;
        }

        #organizationsSearch:focus, #allContentSearch:focus {
            border-color: #4e73df;
            box-shadow: 0 0 0 0.2rem rgba(78, 115, 223, 0.25);
        }

        #organizationsSearchBtn, #allContentSearchBtn {
            border-radius: 8px;
            border: 1px solid #e3e6f0;
            background-color: #f8f9fc;
            color: #6c757d;
            transition: all 0.3s ease;
        }

        #organizationsSearchBtn:hover, #allContentSearchBtn:hover {
            background-color: #4e73df;
            border-color: #4e73df;
            color: white;
        }

        .alert {
            border-radius: 8px;
            font-weight: 500;
        }

        .alert-success {
            background-color: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
        }

        .alert-danger {
            background-color: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
        }

        .text-center i[style*="font-size: 4rem"] {
            margin-bottom: 1rem;
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

        #organizationsContentList .card, #allContentList .card {
            animation: fadeInUp 0.3s ease-out;
        }

        .content-card.removing {
            animation: fadeOut 0.3s ease-out forwards;
        }

        @keyframes fadeOut {
            from {
                opacity: 1;
                transform: scale(1);
            }
            to {
                opacity: 0;
                transform: scale(0.8);
            }
        }

        /* Стили для автора контента */
        .content-author {
            display: flex;
            align-items: center;
            margin-bottom: 8px;
            font-size: 14px;
        }

        .content-author .author-avatar {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            margin-right: 8px;
            object-fit: cover;
        }

        .content-author .author-name {
            color: #6c757d;
            font-weight: 500;
        }

        .content-author .author-placeholder {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            background: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 8px;
            color: #6c757d;
            font-size: 12px;
        }
    </style>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ADMIN") || authorities?seq_contains("SUPER_ADMIN")
        isModer = authorities?seq_contains("MODER") || isAdmin
        >
    </#if>

    <span id="currentLocale" style="display: none;">${.locale}</span>

    <div class="container mt-4" data-user-id="${user.id!}">
        <div class="row">
            <div class="col-lg-4 mb-4">
                <div class="card profile-card">
                    <div class="card-body text-center">
                        <div class="profile-avatar-container mb-3">
                            <#if user.avatar??>
                                <img src="/api/files/${user.avatar.id}/download"
                                     alt="<@spring.message "profile.avatar.alt"/>" class="profile-avatar">
                            <#else>
                                <div class="profile-avatar-placeholder">
                                    <i class="fas fa-user"></i>
                                </div>
                            </#if>
                        </div>

                        <h4 class="mb-1">${user.name!} ${user.surname!}</h4>

                        <#if user.type??>
                            <span class="badge bg-light-blue text-primary mb-3">
                            ${user.type.translations[0].translation}
                        </span>
                        </#if>

                        <div class="d-flex justify-content-center gap-2 mb-3">
                            <#if authorities?seq_contains("SUPER_ADMIN")>
                                <span class="badge bg-purple text-white">
                                    <i class="fas fa-crown me-1"></i> <@spring.message "role.super_admin"/>
                                </span>
                            <#elseif authorities?seq_contains("ADMIN")>
                                <span class="badge bg-danger"><@spring.message "role.admin"/></span>
                            <#elseif authorities?seq_contains("MODER")>
                                <span class="badge bg-warning"><@spring.message "role.moderator"/></span>
                            <#else>
                                <span class="badge bg-info"><@spring.message "role.user"/></span>
                            </#if>
                        </div>

                        <div class="profile-stats">
                            <div class="row text-center">
                                <div class="col-4">
                                    <div class="stat-value" id="totalContent">${total}</div>
                                    <div class="stat-label"><@spring.message "profile.content.total"/></div>
                                </div>
                                <div class="col-4">
                                    <div class="stat-value" id="approvedContent">${accepted}</div>
                                    <div class="stat-label"><@spring.message "profile.content.approved"/></div>
                                </div>
                                <div class="col-4">
                                    <div class="stat-value" id="pendingContent">${pending}</div>
                                    <div class="stat-label"><@spring.message "profile.content.pending"/></div>
                                </div>
                            </div>
                        </div>

                        <hr>

                        <a href="/users/${user.id}/edit" class="btn btn-outline-primary btn-sm w-100 mb-2">
                            <i class="fas fa-edit me-2"></i><@spring.message "button.edit.profile"/>
                        </a>

                        <a href="https://t.me/Math_Bilim_bot?start=${user.id}"
                           target="_blank" class="btn btn-outline-primary btn-sm w-100">
                            📲 <@spring.message "button.connect.telegram"/>
                        </a>
                    </div>
                </div>

                <!-- Быстрые действия -->
                <div class="card mt-3">
                    <div class="card-body">
                        <h6 class="card-title mb-3"><@spring.message "content.create.title"/></h6>
                        <div class="d-grid gap-2">
                            <a href="/posts/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-newspaper me-2"></i><@spring.message "content.type.post"/>
                            </a>
                            <a href="/blog/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-blog me-2"></i><@spring.message "content.type.blog"/>
                            </a>
                            <a href="/events/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-calendar-alt me-2"></i><@spring.message "content.type.event"/>
                            </a>
                            <a href="/books/create" class="btn btn-outline-primary btn-sm">
                                <i class="fas fa-book me-2"></i><@spring.message "content.type.book"/>
                            </a>
                            <#if isModer || isAdmin>
                                <a href="/organizations/create" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-book me-2"></i><@spring.message "content.type.organization"/>
                                </a>
                            </#if>
                            <#if isModer || isAdmin>
                                <a href="/olympiad/create" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-book me-2"></i><@spring.message "content.type.olympiad"/>
                                </a>
                                <a href="/news/create" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-book me-2"></i><@spring.message "content.type.news"/>
                                </a>
                            </#if>
                            <#if isModer || isAdmin>
                                <a href="/tests/create" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-book me-2"></i><@spring.message "test"/>
                                </a>
                            </#if>
                        </div>
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
                                    <i class="fas fa-file-alt me-2"></i><@spring.message "tab.my.content"/>
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="history-tab" data-bs-toggle="tab"
                                        data-bs-target="#history" type="button" role="tab">
                                    <i class="fas fa-history me-2"></i><@spring.message "tab.my.history"/>
                                </button>
                            </li>
                            <#if isModer>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="moderation-tab" data-bs-toggle="tab"
                                            data-bs-target="#moderation" type="button" role="tab">
                                        <i class="fas fa-tasks me-2"></i><@spring.message "tab.moderation"/>
                                        <span class="badge bg-danger ms-1" id="moderationCount">0</span>
                                    </button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="all-content-tab" data-bs-toggle="tab"
                                            data-bs-target="#all-content" type="button" role="tab">
                                        <i class="fas fa-globe me-2"></i><@spring.message "tab.all.content"/>
                                    </button>
                                </li>
                            </#if>
                            <#if isModer || isAdmin>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="organizations-tab" data-bs-toggle="tab"
                                            data-bs-target="#organizations" type="button" role="tab">
                                        <i class="fas fa-building me-2"></i><@spring.message "tab.organizations"/>
                                    </button>
                                </li>
                            </#if>
                            <#if isAdmin>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="users-tab" data-bs-toggle="tab" data-bs-target="#users"
                                            type="button" role="tab">
                                        <i class="fas fa-users me-2"></i><@spring.message "tab.users"/>
                                    </button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="category-tab" data-bs-toggle="tab"
                                            data-bs-target="#category"
                                            type="button" role="tab">
                                        <i class="fa-solid fa-clipboard me-2"></i><@spring.message "tab.categories"/>
                                    </button>
                                </li>
                            </#if>
                        </ul>
                    </div>

                    <div class="card-body">
                        <div class="tab-content" id="profileTabsContent">
                            <!-- Вкладка "Мой контент" -->
                            <div class="tab-pane fade show active" id="content" role="tabpanel">
                                <!-- Фильтры -->
                                <div class="moderation-filters mb-4">
                                    <div class="row g-2">
                                        <div class="col-md-4">
                                            <select class="form-select form-select-sm" id="contentFilter">
                                                <option value="all"><@spring.message "filter.all.types"/></option>
                                                <option value="posts"><@spring.message "content.type.posts"/></option>
                                                <option value="blog"><@spring.message "content.type.blog"/></option>
                                                <option value="events"><@spring.message "content.type.events"/></option>
                                                <option value="books"><@spring.message "content.type.books"/></option>
                                            </select>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="input-group input-group-sm">
                                                <input type="text" class="form-control" id="contentSearch"
                                                       placeholder="<@spring.message "placeholder.search"/>">
                                                <button class="btn btn-outline-secondary" type="button"
                                                        id="moderationSearchBtn">
                                                    <i class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Список контента -->
                                <div id="userContentList" class="content-list">
                                    <div class="text-center text-muted py-5">
                                        <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                        <p><@spring.message "message.loading.content"/></p>
                                    </div>
                                </div>

                                <!-- Пагинация -->
                                <nav id="contentPagination" class="mt-3" style="display: none;">
                                    <ul class="pagination justify-content-center"></ul>
                                </nav>
                            </div>

                            <!-- Вкладка "Моя история" -->
                            <div class="tab-pane fade" id="history" role="tabpanel">
                                <!-- Фильтры для истории -->
                                <div class="history-filters mb-4">
                                    <div class="row g-2">
                                        <div class="col-md-3">
                                            <select class="form-select form-select-sm" id="historyTypeFilter">
                                                <option value="all"><@spring.message "filter.all.types"/></option>
                                                <option value="post"><@spring.message "content.type.post"/></option>
                                                <option value="blog"><@spring.message "content.type.blog"/></option>
                                                <option value="event"><@spring.message "content.type.event"/></option>
                                                <option value="book"><@spring.message "content.type.book"/></option>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <select class="form-select form-select-sm" id="historyStatusFilter">
                                                <option value=" "><@spring.message "filter.all.statuses"/></option>
                                                <option value="APPROVED"><@spring.message "status.approved"/></option>
                                                <option value="REJECTED"><@spring.message "status.rejected"/></option>
                                                <option value="PENDING_REVIEW"><@spring.message "status.pending"/></option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="input-group input-group-sm">
                                                <input type="text" class="form-control" id="historySearch"
                                                       placeholder="<@spring.message "placeholder.search.history"/>">
                                                <button class="btn btn-outline-secondary" type="button"
                                                        id="historySearchBtn">
                                                    <i class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="historyContentList" class="content-list">
                                    <div class="text-center text-muted py-5">
                                        <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                        <p><@spring.message "message.loading.history"/></p>
                                    </div>
                                </div>

                                <!-- Пагинация -->
                                <nav id="historyPagination" class="mt-3" style="display: none;">
                                    <ul class="pagination justify-content-center"></ul>
                                </nav>
                            </div>

                            <!-- Вкладка "Модерация" (для модераторов) -->
                            <#if isModer>
                                <div class="tab-pane fade" id="moderation" role="tabpanel">
                                    <!-- Фильтры для модерации -->
                                    <div class="moderation-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-4">
                                                <select class="form-select form-select-sm" id="moderationTypeFilter">
                                                    <option value="all"><@spring.message "filter.all.types"/></option>
                                                    <option value="post"><@spring.message "content.type.post"/></option>
                                                    <option value="blog"><@spring.message "content.type.blog"/></option>
                                                    <option value="event"><@spring.message "content.type.event"/></option>
                                                    <option value="book"><@spring.message "content.type.book"/></option>
                                                </select>
                                            </div>
                                            <div class="col-md-8">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="moderationSearch"
                                                           placeholder="<@spring.message "placeholder.search"/>">
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            id="moderationSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="moderationContentList" class="content-list">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                            <p><@spring.message "message.loading.moderation"/></p>
                                        </div>
                                    </div>

                                    <nav id="moderationPagination" class="mt-3" style="display: none;">
                                        <ul class="pagination justify-content-center"></ul>
                                    </nav>
                                </div>

                                <!-- Вкладка "Контент весь" (для модераторов и администраторов) -->
                                <div class="tab-pane fade" id="all-content" role="tabpanel">
                                    <!-- Фильтры для всего контента -->
                                <div class="all-content-filters mb-4">
                                    <div class="row g-2">
                                        <div class="col-md-6">
                                            <select class="form-select form-select-sm" id="allContentTypeFilter">
                                                <option value="all"><@spring.message "filter.all.types"/></option>
                                                <option value="post"><@spring.message "content.type.post"/></option>
                                                <option value="blog"><@spring.message "content.type.blog"/></option>
                                                <option value="event"><@spring.message "content.type.event"/></option>
                                                <option value="book"><@spring.message "content.type.book"/></option>
                                                <option value="news"><@spring.message "content.type.news"/></option>
                                                <option value="olympiad"><@spring.message "content.type.olympiad"/></option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="input-group input-group-sm">
                                                <input type="text" class="form-control" id="allContentSearch"
                                                       placeholder="<@spring.message "placeholder.search.all.content"/>">
                                                <button class="btn btn-outline-secondary" type="button"
                                                        id="allContentSearchBtn">
                                                    <i class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="allContentList" class="content-list">
                                    <div class="text-center text-muted py-5">
                                        <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                        <p><@spring.message "message.loading.all.content"/></p>
                                    </div>
                                </div>

                                <nav id="allContentPagination" class="mt-3" style="display: none;">
                                    <ul class="pagination justify-content-center"></ul>
                                </nav>
                            </div>
                            </#if>

                            <#if isModer || isAdmin>
                                <div class="tab-pane fade" id="organizations" role="tabpanel">
                                    <div class="organizations-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-12">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="organizationsSearch"
                                                           placeholder="<@spring.message "placeholder.search"/>">
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            id="organizationsSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="organizationsContentList" class="content-list">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                            <p><@spring.message "message.loading"/></p>
                                        </div>
                                    </div>

                                    <nav id="organizationsPagination" class="mt-3" style="display: none;">
                                        <ul class="pagination justify-content-center"></ul>
                                    </nav>
                                </div>
                            </#if>

                            <!-- Вкладка "Пользователи" (для администраторов) -->
                            <#if isAdmin>
                                <div class="tab-pane fade" id="users" role="tabpanel">
                                    <!-- Действия с пользователями -->
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h5 class="mb-0"><@spring.message "users.management.title"/></h5>
                                        <button class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                                data-bs-target="#createUserModal">
                                            <i class="fas fa-user-plus me-2"></i><@spring.message "button.create.user"/>
                                        </button>
                                    </div>

                                    <!-- Фильтры пользователей -->
                                    <div class="users-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-12">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="usersSearch"
                                                           placeholder="<@spring.message "placeholder.search.users"/>">
                                                    <button class="btn btn-outline-secondary" type="button"
                                                            id="usersSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Список пользователей -->
                                    <div id="usersContentList" class="users-list">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                            <p><@spring.message "message.loading.users"/></p>
                                        </div>
                                    </div>

                                    <!-- Пагинация -->
                                    <nav id="usersPagination" class="mt-3">
                                        <ul class="pagination justify-content-center"></ul>
                                    </nav>
                                </div>
                                <div class="tab-pane fade" id="category" role="tabpanel">
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h5 class="mb-0"><@spring.message "categories.management.title"/></h5>
                                        <button class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                                data-bs-target="#createTypeModal" onclick="openCreateModal()">
                                            <i class="fa-solid fa-clipboard me-2"></i><@spring.message "button.create"/>
                                        </button>
                                    </div>
                                    <div class="users-filters mb-4">
                                        <div class="row g-2">
                                            <div class="col-md-4">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" class="form-control" id="typeSearch"
                                                           placeholder="<@spring.message "placeholder.search.types"/>">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <select class="form-select form-select-sm" id="languageTypeSortBy">
                                                    <option disabled><@spring.message "filter.language"/></option>
                                                    <option value="en"><@spring.message "language.english"/></option>
                                                    <option value="ky"><@spring.message "language.kyrgyz"/></option>
                                                    <option value="ru"><@spring.message "language.russian"/></option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="input-group input-group-sm">
                                                    <select class="form-select form-select-sm" id="staticTypeSortBy">
                                                        <option value="categories"><@spring.message "type.category"/></option>
                                                        <option value="eventTypes"><@spring.message "type.event"/></option>
                                                        <option value="postTypes"><@spring.message "type.post"/></option>
                                                        <option value="userTypes"><@spring.message "type.user"/></option>
                                                    </select>
                                                    <button class="btn btn-outline-secondary ms-3" type="button"
                                                            id="typeSearchBtn">
                                                        <i class="fas fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="users-list" id="typeContentList">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                                            <p><@spring.message "message.loading"/></p>
                                        </div>
                                    </div>

                                    <!-- Пагинация -->
                                    <nav class="mt-3">
                                        <ul class="pagination justify-content-center"></ul>
                                    </nav>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Модальные окна -->
    <div class="modal fade" id="deleteUserModal" tabindex="-1" aria-labelledby="deleteUserModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteUserModalLabel"><@spring.message "modal.delete.user.title"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="deleteUserModalBody">
                    <input type="hidden" value="" id="deleteUserInput">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary"
                            data-bs-dismiss="modal"><@spring.message "button.cancel"/></button>
                    <button type="button" class="btn btn-danger"
                            id="deleteUserBtn"><@spring.message "button.delete"/></button>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit User Modal -->
    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editUserModalLabel"><@spring.message "modal.edit.user.title"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editUserForm">
                        <input type="hidden" id="editUserId" name="id" value="">
                        <div class="mb-3">
                            <label for="editUserName" class="form-label"><@spring.message "label.first.name"/></label>
                            <input type="text" class="form-control" id="editUserName" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="editUserSurname" class="form-label"><@spring.message "label.last.name"/></label>
                            <input type="text" class="form-control" id="editUserSurname" name="surname" required>
                        </div>
                        <div class="mb-3">
                            <label for="editUserRole" class="form-label"><@spring.message "label.role"/></label>
                            <select class="form-select" id="editUserRole" name="role">
                                <#list roles as role>
                                    <option value="${role.name}">${role.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="editUserType" class="form-label"><@spring.message "label.account.type"/></label>
                            <div class="account-type-select">
                                <select class="form-select" id="editUserType" name="type">
                                    <option selected disabled
                                            value=""><@spring.message "option.select.account.type"/></option>
                                    <#list types as type>
                                        <option value="${type.translations[0].typeId}"
                                                data-name="${type.translations[0].translation}">${type.translations[0].translation}</option>
                                    </#list>
                                </select>
                                <i class="bi bi-chevron-down dropdown-icon"></i>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary"
                            data-bs-dismiss="modal"><@spring.message "button.cancel"/></button>
                    <button type="button" class="btn btn-primary"
                            id="editUserBtn"><@spring.message "button.save.changes"/></button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="createUserModal" tabindex="-1" aria-labelledby="createUserModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createUserModalLabel"><@spring.message "modal.create.user.title"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="createNewUser">
                        <div class="mb-3">
                            <label for="name" class="form-label"><@spring.message "label.first.name"/></label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="surname" class="form-label"><@spring.message "label.last.name"/></label>
                            <input type="text" class="form-control" id="surname" name="surname" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label"><@spring.message "label.email"/></label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label"><@spring.message "label.password"/></label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="mb-3">
                            <label for="newUserRole" class="form-label"><@spring.message "label.role"/></label>
                            <select class="form-select" id="newUserRole" name="role">
                                <#list roles as role>
                                    <option value="${role.name}">${role.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="type" class="form-label"><@spring.message "label.account.type"/></label>
                            <div class="account-type-select">
                                <select class="form-select" id="type" name="type">
                                    <option selected disabled
                                            value=""><@spring.message "option.select.account.type"/></option>
                                    <#list types as type>
                                        <option value="${type.translations[0].typeId}"
                                                data-name="${type.translations[0].translation}">${type.translations[0].translation}</option>
                                    </#list>
                                </select>
                                <i class="bi bi-chevron-down dropdown-icon"></i>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary"
                            data-bs-dismiss="modal"><@spring.message "button.cancel"/></button>
                    <button type="button" class="btn btn-primary"
                            id="createUserBtn"><@spring.message "button.create"/></button>
                </div>
            </div>
        </div>
    </div>

    <!-- Модальное окно контента -->
    <div class="modal fade" id="contentModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalTitle"><@spring.message "modal.content.details"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div id="modalContent">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary"
                            data-bs-dismiss="modal"><@spring.message "button.close"/></button>
                    <button type="button" class="btn btn-reject" id="modalRejectBtn">
                        <i class="fas fa-times"></i> <@spring.message "button.reject"/>
                    </button>
                    <button type="button" class="btn btn-approve" id="modalApproveBtn">
                        <i class="fas fa-check"></i> <@spring.message "button.approve"/>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="createTypeModal" tabindex="-1" aria-labelledby="createTypeModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="categoryForm">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createTypeModalLabel">
                            <@spring.message "modal.createType.title"/>
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="<@spring.message 'button.close'/>"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="nameRu" class="form-label">
                                <@spring.message "modal.createType.nameRu"/>
                            </label>
                            <input type="text" class="form-control" id="nameRu" name="nameRu" required>
                        </div>
                        <div class="mb-3">
                            <label for="nameEn" class="form-label">
                                <@spring.message "modal.createType.nameEn"/>
                            </label>
                            <input type="text" class="form-control" id="nameEn" name="nameEn" required>
                        </div>
                        <div class="mb-3">
                            <label for="nameKy" class="form-label">
                                <@spring.message "modal.createType.nameKy"/>
                            </label>
                            <input type="text" class="form-control" id="nameKy" name="nameKy" required>
                        </div>
                        <div class="mb-3" id="selectWrapper">
                            <label for="type" class="form-label">
                                <@spring.message "modal.createType.question"/>
                            </label>
                            <div class="account-type-select">
                                <select class="form-select" id="staticType">
                                    <option selected disabled value="">
                                        <@spring.message "modal.createType.selectPrompt"/>
                                    </option>
                                    <option value="categories"><@spring.message "modal.createType.option.category"/></option>
                                    <option value="event_type"><@spring.message "modal.createType.option.eventType"/></option>
                                    <option value="post_type"><@spring.message "modal.createType.option.postType"/></option>
                                    <option value="user_type"><@spring.message "modal.createType.option.userType"/></option>
                                </select>
                                <i class="bi bi-chevron-down dropdown-icon"></i>
                            </div>
                        </div>
                        <input type="hidden" id="typeIdForChange">
                        <input type="hidden" id="staticTypeValue" name="staticTypeValue">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <@spring.message "button.cancel"/>
                        </button>
                        <button type="button" class="btn btn-primary" id="createTypeBtn">
                            <@spring.message "button.save"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="modal fade" id="deleteConfirmModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Подтверждение удаления</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>Вы уверены, что хотите удалить этот контент?</p>
                    <p class="text-danger"><strong>Внимание:</strong> Это действие нельзя отменить!</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteBtn">Удалить</button>
                </div>
            </div>
        </div>

    <script src="/static/js/admin.js"></script>
    <script src="/static/js/profile-content.js"></script>
    <script src="/static/js/profile/controlOrganizations.js"></script>
    <script src="/static/js/profile/controlUsers.js"></script>
    <script src="/static/js/profile/controlTypes.js"></script>
    <script src="/static/js/profile/controlAllContent.js"></script>
</@main.layout>