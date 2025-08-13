<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.layout title="${event.title!'Мероприятие'} - MathBilim.kg">

    <#if event.description??>
        <meta name="description" content="${event.description}">
    </#if>
    <#if event.mainImageId??>
        <meta property="og:image" content="/api/files/${event.mainImageId}/view">
        <meta name="twitter:image" content="/api/files/${event.mainImageId}/view">
    </#if>
    <meta property="og:title" content="${event.title!'Мероприятие'} - MathBilim.kg">
    <meta property="og:description" content="${event.description!''}">
    <meta property="og:url" content="${shareUrl!''}">
    <meta property="og:type" content="event">
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="${event.title!'Мероприятие'} - MathBilim.kg">
    <meta name="twitter:description" content="${event.description!''}">

    <style>
        .hero-section {
            position: relative;
            min-height: 70vh;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
        <#if event.mainImageId??>
            background-image:
                    linear-gradient(135deg, rgba(37, 99, 235, 0.7), rgba(29, 78, 216, 0.7)),
                    url('/api/files/${event.mainImageId}/view');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        </#if>
            display: flex;
            align-items: center;
            color: white;
            overflow: hidden;
        }

        .hero-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(135deg, rgba(37, 99, 235, 0.6), rgba(29, 78, 216, 0.6));
            z-index: 1;
        }

        .hero-content {
            position: relative;
            z-index: 2;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            text-align: center;
        }

        .event-badges {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 1rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }

        .event-badge {
            background: rgba(255, 255, 255, 0.2);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.3);
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 25px;
            font-size: 0.9rem;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .event-title-hero {
            font-size: 3.5rem;
            font-weight: 800;
            color: white;
            line-height: 1.2;
            margin-bottom: 1.5rem;
            text-shadow: 0 4px 20px rgba(0, 0, 0, 0.5);
        }

        .event-description-hero {
            font-size: 1.25rem;
            color: rgba(255, 255, 255, 0.95);
            margin-bottom: 2rem;
            max-width: 700px;
            margin-left: auto;
            margin-right: auto;
            line-height: 1.6;
            text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
        }

        .event-cta-buttons {
            display: flex;
            justify-content: center;
            gap: 1rem;
            flex-wrap: wrap;
        }

        .btn-hero {
            padding: 0.875rem 2rem;
            border-radius: 50px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            border: none;
            cursor: pointer;
            font-size: 1rem;
        }

        .btn-hero.primary {
            background: white;
            color: #2563eb;
            border: 2px solid white;
        }

        .btn-hero.primary:hover {
            background: transparent;
            color: white;
            border-color: white;
            transform: translateY(-2px);
        }

        .btn-hero.secondary {
            background: transparent;
            color: white;
            border: 2px solid rgba(255, 255, 255, 0.5);
        }

        .btn-hero.secondary:hover {
            background: rgba(255, 255, 255, 0.1);
            border-color: white;
            transform: translateY(-2px);
        }

        .main-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 4rem 20px;
        }

        .content-section {
            background: white;
            border-radius: 20px;
            padding: 3rem;
            margin-bottom: 3rem;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
        }

        .section-title {
            font-size: 1.75rem;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .section-icon {
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.1rem;
        }

        .event-details-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-bottom: 3rem;
        }

        .detail-card {
            background: #f8fafc;
            padding: 2rem;
            border-radius: 15px;
            border-left: 4px solid #2563eb;
        }

        .detail-title {
            font-weight: 600;
            color: #1e293b;
            margin-bottom: 0.75rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .detail-value {
            color: #64748b;
            font-size: 1.1rem;
        }

        .event-content-text {
            font-size: 1.1rem;
            line-height: 1.8;
            color: #374151;
        }

        .organizers-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
        }

        .organizer-card {
            background: #f8fafc;
            padding: 2rem;
            border-radius: 15px;
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .organizer-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.1);
        }

        .organizer-avatar {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            object-fit: cover;
            margin: 0 auto 1rem;
            border: 4px solid #2563eb;
        }

        .organizer-avatar.placeholder {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.5rem;
        }

        .organizer-name {
            font-size: 1.25rem;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 0.5rem;
        }

        .organizer-description {
            color: #64748b;
            margin-bottom: 1rem;
        }

        .organizer-link {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            padding: 0.5rem 1.5rem;
            border-radius: 25px;
            text-decoration: none;
            font-weight: 500;
            transition: transform 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }

        .organizer-link:hover {
            transform: translateY(-2px);
            color: white;
        }

        .files-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1rem;
        }

        .file-card {
            background: #f8fafc;
            padding: 1.5rem;
            border-radius: 12px;
            text-decoration: none;
            color: #1e293b;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 1rem;
            border: 2px solid transparent;
        }

        .file-card:hover {
            background: #e2e8f0;
            border-color: #2563eb;
            transform: translateY(-2px);
            color: #1e293b;
        }

        .file-icon {
            width: 50px;
            height: 50px;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.2rem;
        }

        .file-info {
            flex: 1;
        }

        .file-name {
            font-weight: 600;
            margin-bottom: 0.25rem;
        }

        .file-size {
            color: #64748b;
            font-size: 0.9rem;
        }

        .share-section {
            text-align: center;
            padding: 3rem;
            background: linear-gradient(135deg, #f8fafc, #e2e8f0);
            border-radius: 20px;
        }

        .share-title {
            font-size: 1.5rem;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 1rem;
        }

        .social-buttons {
            display: flex;
            justify-content: center;
            gap: 1rem;
            flex-wrap: wrap;
            margin-bottom: 2rem;
        }

        .social-btn {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            color: white;
            font-size: 1.5rem;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
            cursor: pointer;
            border: none;
        }

        .social-btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
            transition: left 0.5s;
        }

        .social-btn:hover::before {
            left: 100%;
        }

        .social-btn:hover {
            transform: scale(1.1) rotate(5deg);
            color: white;
        }

        .social-btn.facebook {
            background: linear-gradient(135deg, #1877f2, #166fe5);
        }

        .social-btn.twitter {
            background: linear-gradient(135deg, #1da1f2, #0d8bd9);
        }

        .social-btn.telegram {
            background: linear-gradient(135deg, #0088cc, #006ba6);
        }

        .social-btn.whatsapp {
            background: linear-gradient(135deg, #25d366, #20b954);
        }

        .social-btn.copy-link {
            background: linear-gradient(135deg, #6b7280, #4b5563);
        }

        .copy-feedback {
            position: absolute;
            top: -50px;
            left: 50%;
            transform: translateX(-50%);
            background: #374151;
            color: white;
            padding: 8px 16px;
            border-radius: 8px;
            font-size: 14px;
            opacity: 0;
            transition: opacity 0.3s ease;
            pointer-events: none;
            white-space: nowrap;
            z-index: 10;
        }

        .social-btn.copied .copy-feedback {
            opacity: 1;
        }

        .share-url-container {
            max-width: 500px;
            margin: 0 auto;
            position: relative;
        }

        .share-url-input {
            width: 100%;
            padding: 1rem 3rem 1rem 1rem;
            border: 2px solid #e2e8f0;
            border-radius: 50px;
            background: white;
            font-size: 0.9rem;
            color: #64748b;
            box-sizing: border-box;
        }

        .copy-url-btn {
            position: absolute;
            right: 5px;
            top: 50%;
            transform: translateY(-50%);
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            border: none;
            border-radius: 50px;
            padding: 0.75rem 1.5rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .copy-url-btn:hover {
            transform: translateY(-50%) scale(1.05);
        }

        .navigation-section {
            background: white;
            padding: 2rem 20px;
            border-top: 1px solid #e2e8f0;
        }

        .navigation-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 1rem;
        }

        .nav-btn {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            padding: 0.875rem 2rem;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .nav-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 30px rgba(37, 99, 235, 0.3);
            color: white;
        }

        .nav-btn.outline {
            background: transparent;
            color: #2563eb;
            border: 2px solid #2563eb;
        }

        .nav-btn.outline:hover {
            background: #2563eb;
            color: white;
        }

        /* Share Modal */
        .share-modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            backdrop-filter: blur(5px);
        }

        .share-modal-content {
            background: white;
            margin: 5% auto;
            padding: 0;
            border-radius: 20px;
            width: 90%;
            max-width: 500px;
            animation: slideIn 0.3s ease;
            overflow: hidden;
        }

        .share-modal-header {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            padding: 2rem;
            text-align: center;
            position: relative;
        }

        .share-modal-title {
            font-size: 1.5rem;
            font-weight: 700;
            margin: 0;
        }

        .close-modal {
            position: absolute;
            right: 1rem;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: white;
            font-size: 1.5rem;
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: background 0.3s ease;
        }

        .close-modal:hover {
            background: rgba(255, 255, 255, 0.2);
        }

        .share-modal-body {
            padding: 2rem;
        }

        /* Toast уведомление */
        .toast {
            position: fixed;
            top: 20px;
            right: 20px;
            background: #10b981;
            color: white;
            padding: 1rem 1.5rem;
            border-radius: 8px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            opacity: 0;
            transform: translateX(100%);
            transition: all 0.3s ease;
            z-index: 1100;
        }

        .toast.show {
            opacity: 1;
            transform: translateX(0);
        }

        .toast.error {
            background: #ef4444;
        }

        @keyframes slideIn {
            from {
                transform: translateY(-50px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .hero-section {
                min-height: 60vh;
            }

            .event-title-hero {
                font-size: 2.5rem;
            }

            .event-description-hero {
                font-size: 1.1rem;
            }

            .main-content {
                padding: 2rem 15px;
            }

            .content-section {
                padding: 2rem 1.5rem;
            }

            .event-details-grid,
            .organizers-grid,
            .files-grid {
                grid-template-columns: 1fr;
            }

            .navigation-content {
                flex-direction: column;
            }

            .event-badges {
                gap: 0.5rem;
            }

            .event-cta-buttons {
                flex-direction: column;
                align-items: center;
            }

            .social-buttons {
                gap: 0.75rem;
            }

            .social-btn {
                width: 50px;
                height: 50px;
                font-size: 1.25rem;
            }
        }

        @media (max-width: 576px) {
            .event-title-hero {
                font-size: 2rem;
            }

            .event-badges {
                flex-direction: column;
                align-items: center;
            }

            .share-modal-content {
                width: 95%;
                margin: 10% auto;
            }
        }
    </style>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="hero-content">
            <div class="event-badges">
                <div class="event-badge">
                    <i class="fas fa-tag"></i>
                    ${eventType.translations[0].translation}
                </div>

                <div class="event-badge">
                    <i class="fas fa-calendar-alt"></i>
                    ${event.formattedStartDate}
                </div>

                <#if event.endDate??>
                    <div class="event-badge">
                        <i class="fas fa-clock"></i>
                        до ${event.formattedEndDate}
                    </div>
                </#if>

                <div class="event-badge">
                    <#if event.isOffline>
                        <i class="fas fa-map-marker-alt"></i>Офлайн
                    <#else>
                        <i class="fas fa-globe"></i>Онлайн
                    </#if>
                </div>

                <div class="event-badge">
                    <i class="fas fa-eye"></i>
                    ${event.viewCount}
                </div>
            </div>

            <h1 class="event-title-hero">${event.title}</h1>

            <#if event.description??>
                <p class="event-description-hero">${event.description}</p>
            </#if>

            <div class="event-cta-buttons">
                <#if !event.isOffline && event.url??>
                    <a href="${event.url}" target="_blank" class="btn-hero primary">
                        <i class="fas fa-external-link-alt"></i>
                        Присоединиться
                    </a>
                </#if>
                <button class="btn-hero secondary" onclick="scrollToShare()">
                    <i class="fas fa-share-alt"></i>
                    Поделиться
                </button>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Event Details -->
        <div class="content-section">
            <h2 class="section-title">
                <div class="section-icon">
                    <i class="fas fa-info-circle"></i>
                </div>
                Детали мероприятия
            </h2>

            <div class="event-details-grid">
                <#if event.startDate??>
                    <div class="detail-card">
                        <div class="detail-title">
                            <i class="fas fa-calendar-start"></i>
                            Дата начала
                        </div>
                        <div class="detail-value">
                            ${event.formattedStartDate}
                        </div>
                    </div>
                </#if>

                <#if event.endDate??>
                    <div class="detail-card">
                        <div class="detail-title">
                            <i class="fas fa-calendar-times"></i>
                            Дата окончания
                        </div>
                        <div class="detail-value">
                            ${event.formattedEndDate}
                        </div>
                    </div>
                </#if>


                <div class="detail-card">
                    <div class="detail-title">
                        <i class="fas fa-map-marker-alt"></i>
                        Формат проведения
                    </div>
                    <div class="detail-value">
                        <#if event.isOffline>
                            Офлайн мероприятие
                            <#if event.address?? && (event.address?trim != "")>
                                <br><small class="text-muted">Адрес: ${event.address}</small>
                            </#if>
                        <#else>
                            Онлайн мероприятие
                        </#if>
                    </div>
                </div>


                <#if event.creator?? && event.creator.id??>
                    <div class="detail-card">
                        <div class="detail-title">
                            <i class="fas fa-user"></i>
                            Создатель
                        </div>
                        <div class="detail-value">
                            <span class="event-author-compact" data-author-id="${event.creator.id}">
                                <span class="author-name">${event.creator.name}</span>
                            </span>
                        </div>
                    </div>
                </#if>
            </div>
        </div>

        <!-- Event Content -->
        <#if event.content??>
            <div class="content-section">
                <h2 class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-file-alt"></i>
                    </div>
                    Описание
                </h2>
                <div class="event-content-text">
                    ${event.content?no_esc}
                </div>
            </div>
        </#if>

        <!-- Organizers -->
        <#if organizations?? && organizations?size gt 0>
            <div class="content-section">
                <h2 class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-users"></i>
                    </div>
                    Организаторы
                </h2>
                <div class="organizers-grid">
                    <#list organizations as org>
                        <div class="organizer-card">
                            <#if org.avatar??>
                                <img src="/api/files/${org.avatar.id}/view"
                                     alt="${org.name}"
                                     class="organizer-avatar">
                            <#else>
                                <div class="organizer-avatar placeholder">
                                    <i class="fas fa-building"></i>
                                </div>
                            </#if>
                            <div class="organizer-name">${org.name}</div>
                            <#if org.description??>
                                <p class="organizer-description">${org.description}</p>
                            </#if>
                            <#if org.url??>
                                <a href="${org.url}" target="_blank" class="organizer-link">
                                    <i class="fas fa-external-link-alt"></i>Перейти на сайт
                                </a>
                            </#if>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>

        <#if event.eventFiles?? && event.eventFiles?size gt 0>
            <div class="content-section">
                <h2 class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-download"></i>
                    </div>
                    Дополнительные материалы
                </h2>
                <div class="files-grid">
                    <#list event.eventFiles as file>
                        <a href="/api/files/${file.id}/download"
                           class="file-card"
                           target="_blank">
                            <div class="file-icon">
                                <i class="fas fa-file"></i>
                            </div>
                            <div class="file-info">
                                <div class="file-name">${file.originalName}</div>
                                <div class="file-size">${file.formattedSize}</div>
                            </div>
                        </a>
                    </#list>
                </div>
            </div>
        </#if>

        <!-- Share Section -->
        <div class="share-section" id="shareSection">
            <h2 class="share-title">Поделиться мероприятием</h2>
            <div class="social-buttons">
                <button onclick="shareToFacebook()" class="social-btn facebook">
                    <i class="fab fa-facebook-f"></i>
                    <div class="copy-feedback">Facebook</div>
                </button>
                <button onclick="shareToTwitter()" class="social-btn twitter">
                    <i class="fab fa-twitter"></i>
                    <div class="copy-feedback">Twitter</div>
                </button>
                <button onclick="shareToTelegram()" class="social-btn telegram">
                    <i class="fab fa-telegram-plane"></i>
                    <div class="copy-feedback">Telegram</div>
                </button>
                <button onclick="shareToWhatsApp()" class="social-btn whatsapp">
                    <i class="fab fa-whatsapp"></i>
                    <div class="copy-feedback">WhatsApp</div>
                </button>
                <button onclick="copyLink()" class="social-btn copy-link" id="copyLinkBtn">
                    <i class="fas fa-link"></i>
                    <div class="copy-feedback">Скопировано!</div>
                </button>
            </div>
            <div class="share-url-container">
                <input type="text" class="share-url-input" value="${shareUrl!''}" readonly id="shareUrlInput">
                <button class="copy-url-btn" onclick="copyShareUrl()">
                    <i class="fas fa-copy"></i>
                </button>
            </div>
        </div>
    </div>

    <!-- Navigation -->
    <div class="navigation-section">
        <div class="navigation-content">
            <a href="/events" class="nav-btn outline">
                <i class="fas fa-arrow-left"></i>К списку мероприятий
            </a>
            <a href="/events/create" class="nav-btn">
                <i class="fas fa-plus"></i>Создать мероприятие
            </a>
        </div>
    </div>

    <!-- Share Modal -->
    <div id="shareModal" class="share-modal">
        <div class="share-modal-content">
            <div class="share-modal-header">
                <h3 class="share-modal-title">Поделиться мероприятием</h3>
                <button class="close-modal" onclick="closeShareModal()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
            <div class="share-modal-body">
                <div class="social-buttons">
                    <button onclick="shareToFacebook()" class="social-btn facebook">
                        <i class="fab fa-facebook-f"></i>
                    </button>
                    <button onclick="shareToTwitter()" class="social-btn twitter">
                        <i class="fab fa-twitter"></i>
                    </button>
                    <button onclick="shareToTelegram()" class="social-btn telegram">
                        <i class="fab fa-telegram-plane"></i>
                    </button>
                    <button onclick="shareToWhatsApp()" class="social-btn whatsapp">
                        <i class="fab fa-whatsapp"></i>
                    </button>
                </div>
                <div class="share-url-container">
                    <input type="text" class="share-url-input" value="${shareUrl!''}" readonly>
                    <button class="copy-url-btn" onclick="copyShareUrl()">Копировать</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Toast уведомления -->
    <div id="toast" class="toast"></div>

    <!-- JavaScript для функционала поделиться -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Получаем данные для шаринга
            var shareUrl = '${shareUrl!''}';
            var shareTitle = '${event.title?js_string}';
            var shareDescription = '${event.description?js_string!'Интересное мероприятие на MathBilim.kg'}';

            // Функция для прокрутки к разделу поделиться
            window.scrollToShare = function() {
                var shareSection = document.getElementById('shareSection');
                if (shareSection) {
                    shareSection.scrollIntoView({
                        behavior: 'smooth',
                        block: 'center'
                    });

                    // Добавляем эффект подсветки
                    shareSection.style.boxShadow = '0 0 30px rgba(37, 99, 235, 0.3)';
                    shareSection.style.transition = 'box-shadow 0.5s ease';

                    setTimeout(function() {
                        shareSection.style.boxShadow = '0 10px 40px rgba(0, 0, 0, 0.1)';
                    }, 1000);
                }
            };

            // Функция для показа уведомлений
            function showToast(message, isError) {
                var toast = document.getElementById('toast');
                if (!toast) {
                    toast = document.createElement('div');
                    toast.id = 'toast';
                    toast.className = 'toast';
                    document.body.appendChild(toast);
                }

                toast.textContent = message;
                toast.className = 'toast' + (isError ? ' error' : '') + ' show';

                setTimeout(function() {
                    toast.classList.remove('show');
                }, 3000);
            }

            // Функция копирования (совместимая со всеми браузерами)
            function copyText(text) {
                var textArea = document.createElement('textarea');
                textArea.value = text;
                textArea.style.position = 'fixed';
                textArea.style.left = '-9999px';
                document.body.appendChild(textArea);
                textArea.focus();
                textArea.select();

                var successful = false;
                try {
                    successful = document.execCommand('copy');
                } catch (err) {
                    console.error('Ошибка копирования:', err);
                }

                document.body.removeChild(textArea);
                return successful;
            }

            // Функции для социальных сетей
            window.shareToFacebook = function() {
                var url = 'https://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent(shareUrl);
                window.open(url, '_blank', 'width=600,height=400,scrollbars=yes,resizable=yes');
            };

            window.shareToTwitter = function() {
                var text = shareTitle + ' - ' + shareDescription;
                var url = 'https://twitter.com/intent/tweet?url=' + encodeURIComponent(shareUrl) + '&text=' + encodeURIComponent(text);
                window.open(url, '_blank', 'width=600,height=400,scrollbars=yes,resizable=yes');
            };

            window.shareToTelegram = function() {
                var text = shareTitle + '\n' + shareDescription + '\n' + shareUrl;
                var url = 'https://t.me/share/url?url=' + encodeURIComponent(shareUrl) + '&text=' + encodeURIComponent(text);
                window.open(url, '_blank', 'width=600,height=400,scrollbars=yes,resizable=yes');
            };

            window.shareToWhatsApp = function() {
                var text = shareTitle + '\n' + shareDescription + '\n' + shareUrl;
                var url = 'https://wa.me/?text=' + encodeURIComponent(text);
                window.open(url, '_blank', 'width=600,height=400,scrollbars=yes,resizable=yes');
            };

            // Функция копирования ссылки
            window.copyLink = function() {
                var success = copyText(shareUrl);
                var btn = document.getElementById('copyLinkBtn');

                if (success) {
                    if (btn) {
                        btn.classList.add('copied');
                        setTimeout(function() {
                            btn.classList.remove('copied');
                        }, 2000);
                    }
                    showToast('Ссылка скопирована в буфер обмена!');
                } else {
                    showToast('Не удалось скопировать ссылку', true);
                }
            };

            // Функция копирования из поля ввода
            window.copyShareUrl = function() {
                var input = document.getElementById('shareUrlInput');
                var success = false;

                if (input) {
                    input.select();
                    input.setSelectionRange(0, 99999); // Для мобильных устройств
                    success = copyText(input.value);
                }

                if (success) {
                    showToast('Ссылка скопирована в буфер обмена!');
                } else {
                    showToast('Не удалось скопировать ссылку', true);
                }
            };

            // Функции модального окна
            window.openShareModal = function() {
                var modal = document.getElementById('shareModal');
                if (modal) {
                    modal.style.display = 'block';
                    document.body.style.overflow = 'hidden';
                }
            };

            window.closeShareModal = function() {
                var modal = document.getElementById('shareModal');
                if (modal) {
                    modal.style.display = 'none';
                    document.body.style.overflow = 'auto';
                }
            };

            // Закрытие модального окна по клику вне его
            window.addEventListener('click', function(event) {
                var modal = document.getElementById('shareModal');
                if (modal && event.target === modal) {
                    closeShareModal();
                }
            });

            // Закрытие модального окна по Escape
            document.addEventListener('keydown', function(event) {
                if (event.key === 'Escape') {
                    var modal = document.getElementById('shareModal');
                    if (modal && modal.style.display === 'block') {
                        closeShareModal();
                    }
                }
            });

            // Автоматическое выделение текста в поле ввода при клике
            var shareInput = document.getElementById('shareUrlInput');
            if (shareInput) {
                shareInput.addEventListener('click', function() {
                    this.select();
                });
            }
        });
    </script>

    <script src="/static/js/components/content-view/AuthorManager.js"></script>
    <script src="/static/js/components/content-view/SocialShareManager.js"></script>
    <script src="/static/js/components/content-view/ContentViewManager.js"></script>
    <script src="/static/js/components/content-view/BaseContentViewManager.js"></script>

</@main.layout>