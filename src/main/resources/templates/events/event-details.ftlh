<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.layout title="${event.title!'Мероприятие'} - MathBilim.kg">
    <#if event.description??>
        <meta name="description" content="${event.description}">
    </#if>
    <#if event.mainImage??>
        <meta property="og:image" content="/api/files/${event.mainImage.id}/view">
        <meta name="twitter:image" content="/api/files/${event.mainImage.id}/view">
    </#if>
    <meta property="og:title" content="${event.title!'Мероприятие'} - MathBilim.kg">
    <meta property="og:description" content="${event.description!''}">
    <meta property="og:url" content="${shareUrl!''}">
    <meta property="og:type" content="event">
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="${event.title!'Мероприятие'} - MathBilim.kg">
    <meta name="twitter:description" content="${event.description!''}">

    <div class="blog-view-container">
        <article class="blog-article">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 col-xl-7">
                        <header class="blog-header">
                            <!-- Метаинформация -->
                            <div class="blog-meta">
                                <div class="event-type-badge">
                                    <i class="fas fa-tag me-1"></i>
                                    ${eventType.translations[0].translation}
                                </div>

                                <time class="event-date" datetime="${event.startDate}">
                                    <i class="fas fa-calendar-alt me-2"></i>
                                    ${event.startDate}
                                </time>

                                <#if event.endDate??>
                                    <time class="event-end-date" datetime="${event.endDate}">
                                        <i class="fas fa-clock me-2"></i>
                                        до ${event.endDate}
                                    </time>
                                </#if>

                                <div class="event-location-badge">
                                    <#if event.isOffline>
                                        <i class="fas fa-map-marker-alt me-1"></i>Офлайн
                                    <#else>
                                        <i class="fas fa-globe me-1"></i>Онлайн
                                    </#if>
                                </div>

                                <#if event.creator?? && event.creator.id??>
                                    <span class="event-author-compact" data-author-id="${event.creator.id}">
                                        <i class="fas fa-user me-2"></i>
                                        <span class="author-name">Загрузка...</span>
                                    </span>
                                </#if>

                                <div class="event-stats">
                                    <span class="stat-item">
                                        <i class="fas fa-eye me-1"></i>
                                        ${event.viewCount}
                                    </span>
                                    <span class="stat-item">
                                        <i class="fas fa-share me-1"></i>
                                        ${event.shareCount}
                                    </span>
                                </div>
                            </div>

                            <h1 class="event-title">${event.title}</h1>

                            <!-- Локация -->
                            <#if (event.isOffline && event.address??) || (!event.isOffline && event.url??)>
                                <div class="event-location">
                                    <#if event.isOffline && event.address??>
                                        <div class="location-info offline">
                                            <i class="fas fa-map-marker-alt me-2"></i>
                                            <span class="location-text">${event.address}</span>
                                        </div>
                                    <#elseif !event.isOffline && event.url??>
                                        <div class="location-info online">
                                            <i class="fas fa-globe me-2"></i>
                                            <span class="location-text">Онлайн мероприятие</span>
                                            <a href="${event.url}"
                                               target="_blank"
                                               class="btn btn-primary ms-2">
                                                <i class="fas fa-external-link-alt me-1"></i> Присоединиться
                                            </a>
                                        </div>
                                    </#if>
                                </div>
                            </#if>
                        </header>

                        <#if event.mainImage??>
                            <div class="event-main-image">
                                <img src="/api/files/${event.mainImage.id}/view"
                                     alt="${event.title}"
                                     class="img-fluid rounded shadow-sm">
                            </div>
                        </#if>

                        <div class="event-content">
                            ${event.content?no_esc}
                        </div>

                        <!-- Организаторы -->
                        <#if organizations?? && organizations?size gt 0>
                            <div class="event-organizers">
                                <h3>Организаторы</h3>
                                <div class="organizers-list">
                                    <#list organizations as org>
                                        <div class="organizer-item">
                                            <#if org.avatar??>
                                                <img src="/api/files/${org.avatar.id}/view"
                                                     alt="${org.name}"
                                                     class="organizer-avatar">
                                            <#else>
                                                <div class="organizer-avatar placeholder">
                                                    <i class="fas fa-building"></i>
                                                </div>
                                            </#if>
                                            <div class="organizer-info">
                                                <h4 class="organizer-name">${org.name}</h4>
                                                <p class="organizer-description">${org.description}</p>
                                                <#if org.url??>
                                                    <a href="${org.url}" target="_blank" class="organizer-link">
                                                        <i class="fas fa-external-link-alt me-1"></i>Сайт
                                                    </a>
                                                </#if>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            </div>
                        </#if>

                        <!-- Дополнительные файлы -->
                        <#if event.eventFiles?? && event.eventFiles?size gt 0>
                            <div class="event-files">
                                <h3>Дополнительные материалы</h3>
                                <div class="files-list">
                                    <#list event.eventFiles as file>
                                        <a href="/api/files/${file.id}/download"
                                           class="file-item"
                                           target="_blank">
                                            <i class="fas fa-file me-2"></i>
                                            <span class="file-name">${file.originalName}</span>
                                            <span class="file-size">(${file.formattedSize})</span>
                                        </a>
                                    </#list>
                                </div>
                            </div>
                        </#if>

                        <div class="blog-actions">
                            <div class="social-share">
                                <span class="share-label">Поделиться в:</span>
                                <div class="social-buttons">
                                    <a href="#" onclick="shareToFacebook(); return false;" class="social-btn facebook">
                                        <i class="fab fa-facebook-f"></i>
                                    </a>
                                    <a href="#" onclick="shareToTwitter(); return false;" class="social-btn twitter">
                                        <i class="fab fa-twitter"></i>
                                    </a>
                                    <a href="#" onclick="shareToTelegram(); return false;" class="social-btn telegram">
                                        <i class="fab fa-telegram-plane"></i>
                                    </a>
                                    <a href="#" onclick="shareToWhatsApp(); return false;" class="social-btn whatsapp">
                                        <i class="fab fa-whatsapp"></i>
                                    </a>
                                    <a href="#" onclick="copyLink(); return false;" class="social-btn copy-link" id="copyLinkBtn">
                                        <i class="fas fa-link"></i>
                                        <span class="copy-feedback">Скопировано!</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </article>

        <div class="blog-navigation">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 col-xl-7">
                        <div class="nav-actions">
                            <a href="/events" class="btn btn-outline-primary">
                                <i class="fas fa-arrow-left me-2"></i>К списку мероприятий
                            </a>
                            <a href="/events/create" class="btn btn-primary">
                                <i class="fas fa-plus me-2"></i>Создать мероприятие
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/components/content-view/AuthorManager.js"></script>
    <script src="/static/js/components/content-view/SocialShareManager.js"></script>
    <script src="/static/js/components/content-view/ContentViewManager.js"></script>
    <script src="/static/js/components/content-view/BaseContentViewManager.js"></script>

    <script src="/static/js/event/EventViewManager.js"></script>

    <script>
        window.eventData = {
            id: ${event.id},
            title: '${event.title?js_string}',
            shareUrl: '${shareUrl?js_string}',
            description: '${event.description?js_string!""}',
            startDate: '${event.startDate}',
            <#if event.endDate??>endDate: '${event.endDate}',</#if>
            isOffline: ${event.isOffline?c},
            <#if event.address??>address: '${event.address?js_string}',</#if>
            <#if event.url??>url: '${event.url?js_string}'</#if>
        };
    </script>

    <style>
        .event-meta {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 2rem;
            margin-bottom: 1.5rem;
            flex-wrap: wrap;
        }

        .event-type-badge,
        .event-location-badge {
            background: #2563eb;
            color: white;
            padding: 0.25rem 0.75rem;
            border-radius: 20px;
            font-size: 0.875rem;
            font-weight: 500;
        }

        .event-location-badge {
            background: #10b981;
        }

        .event-date,
        .event-end-date {
            color: #64748b;
            font-size: 0.9rem;
            font-weight: 500;
            display: flex;
            align-items: center;
        }

        .event-date i,
        .event-end-date i {
            color: #2563eb;
        }

        .event-author-compact {
            color: #64748b;
            font-size: 0.9rem;
            font-weight: 500;
            display: flex;
            align-items: center;
        }

        .event-author-compact i {
            color: #2563eb;
        }

        .event-author-compact .author-name {
            color: #1e293b;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .event-author-compact .author-name:hover {
            color: #2563eb;
        }

        .event-stats {
            display: flex;
            gap: 1rem;
        }

        .stat-item {
            color: #64748b;
            font-size: 0.875rem;
            display: flex;
            align-items: center;
        }

        .stat-item i {
            color: #9ca3af;
            font-size: 0.8rem;
        }

        .event-title {
            font-size: 2.5rem;
            font-weight: 800;
            color: #1e293b;
            line-height: 1.2;
            margin-bottom: 2rem;
            text-align: center;
        }

        /* Локация */
        .event-location {
            margin-bottom: 2rem;
        }

        .location-info {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 1rem;
            border-radius: 8px;
            font-weight: 500;
        }

        .location-info.offline {
            background: #fef3c7;
            color: #92400e;
        }

        .location-info.online {
            background: #d1fae5;
            color: #065f46;
        }

        /* Организаторы */
        .event-organizers {
            margin: 3rem 0;
            padding: 2rem;
            background: #f8fafc;
            border-radius: 8px;
        }

        .organizers-list {
            display: grid;
            gap: 1.5rem;
        }

        .organizer-item {
            display: flex;
            gap: 1rem;
            align-items: flex-start;
        }

        .organizer-avatar {
            width: 60px;
            height: 60px;
            border-radius: 8px;
            object-fit: cover;
        }

        .organizer-avatar.placeholder {
            background: #e2e8f0;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #64748b;
        }

        .organizer-name {
            margin: 0 0 0.5rem 0;
            font-size: 1.125rem;
            font-weight: 600;
        }

        .organizer-description {
            margin: 0 0 0.5rem 0;
            color: #64748b;
        }

        .organizer-link {
            color: #2563eb;
            text-decoration: none;
            font-size: 0.875rem;
        }

        /* Файлы */
        .event-files {
            margin: 3rem 0;
        }

        .files-list {
            display: grid;
            gap: 0.75rem;
        }

        .file-item {
            display: flex;
            align-items: center;
            padding: 0.75rem 1rem;
            background: #f8fafc;
            border-radius: 6px;
            text-decoration: none;
            color: #1e293b;
            transition: background-color 0.2s ease;
        }

        .file-item:hover {
            background: #e2e8f0;
            color: #1e293b;
        }

        .file-size {
            margin-left: auto;
            color: #64748b;
            font-size: 0.875rem;
        }

        .social-share {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .social-buttons {
            display: flex;
            gap: 0.5rem;
        }

        .social-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            text-decoration: none;
            color: white;
            transition: transform 0.2s ease;
        }

        .social-btn:hover {
            transform: scale(1.1);
            color: white;
        }

        .social-btn.facebook { background: #1877f2; }
        .social-btn.twitter { background: #1da1f2; }
        .social-btn.telegram { background: #0088cc; }
        .social-btn.whatsapp { background: #25d366; }
        .social-btn.copy-link { background: #6b7280; position: relative; }

        .copy-feedback {
            position: absolute;
            top: -35px;
            left: 50%;
            transform: translateX(-50%);
            background: #374151;
            color: white;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            opacity: 0;
            transition: opacity 0.3s ease;
            pointer-events: none;
        }

        .social-btn.copied .copy-feedback {
            opacity: 1;
        }

        @media (max-width: 768px) {
            .event-meta {
                flex-direction: column;
                gap: 1rem;
            }

            .event-title {
                font-size: 2rem;
            }


            .social-share {
                justify-content: center;
            }
        }

        @media (max-width: 576px) {
            .event-title {
                font-size: 1.75rem;
            }

            .event-meta {
                gap: 0.75rem;
            }

            .event-date,
            .event-end-date,
            .event-author-compact {
                font-size: 0.85rem;
            }

            .stat-item {
                font-size: 0.8rem;
            }
        }
    </style>

</@main.layout>