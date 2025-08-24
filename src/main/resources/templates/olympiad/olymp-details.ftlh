<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <style>
        .container {
            max-width: 1400px;
        }

        .tab-pane {
            min-height: 300px;
        }

        .btn-xs {
            padding: 0.1rem 0.25rem;
            font-size: 0.75rem;
            line-height: 1;
            border-radius: 0.15rem;
            background-color: #696969;
        }

        .btn-xs:hover {
            background-color: #343434;
        }

        .edit {
            position: absolute;
            right: 0;
            top: 0;
        }

        .table {
            text-align: center;
            vertical-align: middle;
            border-collapse: separate;
            border-spacing: 0;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .table th, .table td {
            padding: 12px;
            border: none;
        }

        .table thead {
            background-color: #4a90e2;
            color: white;
        }

        .table tbody tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        .table tbody tr:hover {
            background-color: #e9ecef;
            transition: background-color 0.3s ease;
        }

        .table th {
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        @media (max-width: 1000px) {
            .edit {
                position: relative;
            }
        }

        @media (max-width: 576px) {
            .edit {
                position: relative;
            }

            .table-responsive {
                overflow-x: auto;
                -webkit-overflow-scrolling: touch;
            }

            .table {
                font-size: 0.85rem;
                transform: scale(0.9);
                transform-origin: top left;
            }

            .table th, .table td {
                padding: 0.5rem;
                white-space: nowrap;
            }

            .btn-sm, .btn-xs {
                font-size: 0.7rem;
                padding: 0.1rem 0.2rem;
            }
        }

        .mybtn {
            display: inline-block;
            padding: 6px 12px; /* Slightly larger padding */
            background-color: #77a6ff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 14px;
            line-height: 1;
            cursor: pointer;
            user-select: none;
            border: none;
            outline: none;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .mybtn-off {
            display: inline-block;
            padding: 6px 12px;
            background-color: #a9a8a8;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 14px;
            line-height: 1;
            cursor: not-allowed;
            user-select: none;
            border: none;
            outline: none;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .mybtn:hover,
        .mybtn:focus {
            background-color: #5388fb;
            outline: none;
            color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }

        .mybtn:active {
            background-color: #a9a8a8;
            color: white;
        }

        .mybtn-admin {
            display: inline-block;
            padding: 6px 12px;
            background-color: #b56df1;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 14px;
            line-height: 1;
            cursor: pointer;
            user-select: none;
            border: none;
            outline: none;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .mybtn-admin:hover,
        .mybtn-admin:focus {
            background-color: #a94eed;
            outline: none;
            color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }

        .mybtn-admin:active {
            background-color: #a9a8a8;
            color: white;
        }

        .olympiad-notification {
            position: fixed;
            top: 20px;
            right: 20px;
            background: rgba(229, 62, 62, 0.85);
            color: white;
            padding: 16px 24px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(229, 62, 62, 0.3);
            z-index: 1000;
            animation: slideIn 0.4s ease-out, fadeOut 0.4s ease-in 4.6s forwards;
            font-size: 15px;
            font-weight: 500;
            max-width: 320px;
            backdrop-filter: blur(4px);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        @keyframes slideIn {
            from {
                transform: translateX(120%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes fadeOut {
            to {
                opacity: 0;
                transform: translateX(120%);
            }
        }

        .olympiad-notification.success {
            background: rgba(56, 161, 105, 0.85);
            box-shadow: 0 4px 12px rgba(56, 161, 105, 0.3);
        }

        .olympiad-notification.warning {
            background: rgba(236, 201, 75, 0.85);
            box-shadow: 0 4px 12px rgba(236, 201, 75, 0.3);
        }

        .olympiad-notification.error {
            background: rgba(229, 62, 62, 0.85);
            box-shadow: 0 4px 12px rgba(229, 62, 62, 0.3);
        }

        .h4head {
            display: inline-block;
            position: relative;
        }

        .h4head::after {
            content: '';
            display: block;
            height: 1px;
            background-color: #efefef;
            transform: scaleX(1.05);
            transform-origin: left center;
            margin-top: 15px;
        }

        .no-data-placeholder {
            padding: 30px 0;
            text-align: center;
            opacity: 0.7;
            transition: all 0.3s ease;
        }

        .no-data-placeholder:hover {
            opacity: 1;
        }

        .no-data-placeholder svg {
            filter: grayscale(50%);
            transition: all 0.3s ease;
            max-width: 300px;
            height: auto;
        }

        .no-data-placeholder:hover svg {
            filter: grayscale(0%);
        }

        .text-muted {
            color: #6c757d !important;
        }

        .mt-3 {
            margin-top: 1rem !important;
        }

        .img-fluid {
            max-width: 100%;
            height: auto;
        }

        .social-link {
            display: inline-flex;
            align-items: center;
            text-decoration: none;
            transition: all 0.3s ease;
            border-radius: 6px;
            padding: 4px;
            margin: 2px;
        }

        .social-link:hover {
            transform: scale(1.05);
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        .contact-icon {
            width: 32px;
            height: 32px;
            margin-right: 5px;
            vertical-align: middle;
        }

        .contact-card {
            display: flex;
            align-items: center;
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 8px;
            margin: 5px 0;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .contact-card:hover {
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
            transform: translateY(-1px);
        }

        .contact-image {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 10px;
            border: 1px solid #dee2e6;
        }

        .contact-placeholder {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 10px;
            font-size: 18px;
            color: #6c757d;
        }

        .contact-info {
            flex-grow: 1;
            font-size: 14px;
        }

        .contact-info p {
            margin: 0;
            line-height: 1.4;
        }

        .contact-type {
            font-weight: 600;
            color: #343a40;
            display: inline;
        }

        .contact-details {
            color: #6c757d;
            display: inline;
            margin-left: 5px;
        }

        .organization-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .organization-card {
            background-color: #f8f9fa;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            text-align: center;
        }

        .organization-card:hover {
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            transform: translateY(-3px);
        }

        .organization-image {
            width: 120px;
            height: 120px;
            border-radius: 12px;
            object-fit: cover;
            margin-bottom: 15px;
            border: 2px solid #dee2e6;
        }

        .organization-placeholder {
            width: 120px;
            height: 120px;
            border-radius: 12px;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 15px;
            font-size: 48px;
            color: #6c757d;
            border: 2px solid #dee2e6;
        }

        .organization-name {
            font-size: 20px;
            font-weight: 700;
            color: #343a40;
            margin: 0;
            line-height: 1.3;
        }

        @media (max-width: 576px) {
            .contact-card {
                flex-direction: column;
                align-items: flex-start;
                padding: 6px;
            }

            .contact-image, .contact-placeholder {
                margin: 0 auto 5px;
            }

            .contact-info {
                text-align: center;
            }

            .contact-type, .contact-details {
                display: block;
                margin-left: 0;
            }

            .organization-grid {
                grid-template-columns: 1fr;
                gap: 15px;
            }

            .organization-card {
                padding: 15px;
            }

            .organization-image, .organization-placeholder {
                width: 100px;
                height: 100px;
                font-size: 40px;
            }

            .organization-name {
                font-size: 18px;
            }
        }

        .table-responsive {
            padding: 0 10px;
        }

        .table tbody tr:hover {
            background-color: rgba(255, 255, 255, 0.98);
            transition: all 0.2s ease;
            transform: translateX(1px);
        }

        .table thead {
            background-color: #d6ecff;
            color: #343a40;
        }

        .table {
            width: 97%;
            font-size: 0.79rem;
        }

        .table thead th {
            font-size: 0.7rem;
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

    <#if message?? && message?has_content>
        <div id="notification-root"></div>

        <script type="text/babel">
            function Notification() {
                const [showNotification, setShowNotification] = React.useState(true);

                React.useEffect(() => {
                    const timer = setTimeout(() => {
                        setShowNotification(false);
                    }, 5000);
                    return () => clearTimeout(timer);
                }, []);

                return showNotification ? (
                    <div className={"olympiad-notification ${messageType!'error'}"}>
                        ${message}
                    </div>
                ) : null;
            }

            ReactDOM.render(<Notification />, document.getElementById("notification-root"));
        </script>
    </#if>


    <div class="container mt-4 mb-5">
        <h1 class="mb-4">${olympiad.title}</h1>

        <ul style="position: relative" class="nav nav-tabs mb-4" id="olympiadTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="stages-tab" data-bs-toggle="tab" data-bs-target="#stages"
                        type="button" role="tab" aria-controls="stages" aria-selected="false">
                    <@spring.message "olympiad.tab.stages">Этапы проведения</@spring.message>
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="description-tab" data-bs-toggle="tab" data-bs-target="#description"
                        type="button" role="tab" aria-controls="description" aria-selected="true">
                    <@spring.message "olympiad.tab.details">Подробности</@spring.message>
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="rules-tab" data-bs-toggle="tab" data-bs-target="#rules"
                        type="button" role="tab" aria-controls="rules" aria-selected="false">
                    <@spring.message "olympiad.tab.rules">Регламент</@spring.message>
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="contacts-tab" data-bs-toggle="tab" data-bs-target="#contacts"
                        type="button" role="tab" aria-controls="contacts" aria-selected="false">
                    <@spring.message "olympiad.tab.contacts">Контакты</@spring.message>
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="organization-tab" data-bs-toggle="tab" data-bs-target="#organization"
                        type="button" role="tab" aria-controls="organization" aria-selected="false">
                    <@spring.message "olympiad.tab.organizations">Организации</@spring.message>
                </button>
            </li>

            <#if (user??) && ((isModer!false) || (isAdmin!false))>
                <li class="nav-item edit" role="presentation">
                    <button class="nav-link" id="edit-tab" type="button" role="tab">
                        <a style="color: #ff6464; text-decoration: none;" href="/olympiad/edit?id=${olympiad.id}">
                            <@spring.message "olympiad.button.edit">Редактировать</@spring.message>
                        </a>
                    </button>
                </li>
                <#if currentUser?? && (isAdmin || (olympiad.creator?? && olympiad.creator.id == currentUser.id))>
                    <form action="/olympiad/delete/${olympiad.id}" method="post" style="">
                        <@main.csrf/>
                        <input type="hidden" name="_method" value="delete"/>
                        <button type="submit" class="contact-card btn btn-sm d-flex align-items-center justify-content-center" style="background: linear-gradient(135deg, #dc2626, #b91c1c);">
                            🗑 <@spring.message "test.details.delete"/>
                        </button>
                    </form>
                </#if>
            </#if>
        </ul>

        <div class="tab-content" id="olympiadTabContent" style="margin-bottom: 100px">

            <div class="tab-pane fade" id="description" role="tabpanel" aria-labelledby="description-tab">
                <h4 class="h4head"><@spring.message "olympiad.heading.details">Подробности / описание</@spring.message></h4>
                <#if olympiad.info??>
                    <p>${olympiad.info?no_esc}</p>
                </#if>
            </div>

            <div class="tab-pane fade show active" id="stages" role="tabpanel" aria-labelledby="stages-tab">
                <h4 class="h4head"><@spring.message "olympiad.heading.stages">Этапы проведения</@spring.message></h4>
                <#if olympiad.stages?? && (olympiad.stages?size > 0)>
                    <div class="table-responsive">
                        <table class="table table-hover table-sm" style="margin-top: 20px;">
                            <thead class="table-light">
                            <tr>
                                <th><@spring.message "olympiad.stage.col.stage">Этап</@spring.message></th>
                                <th><@spring.message "olympiad.stage.col.regStart">Начало регистрации</@spring.message></th>
                                <th><@spring.message "olympiad.stage.col.regEnd">Окончание регистрации</@spring.message></th>
                                <th><@spring.message "olympiad.stage.col.startDate">Дата начала этапа</@spring.message></th>
                                <th><@spring.message "olympiad.stage.col.endDate">Дата окончания этапа</@spring.message></th>
                                <th><@spring.message "olympiad.stage.col.updatedAt">Обновлено</@spring.message></th>
                                <th><@spring.message "olympiad.stage.col.results">Результаты</@spring.message></th>
                                <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                    <th><@spring.message "olympiad.stage.col.resultAction">Результат</@spring.message></th>
                                </#if>
                                <th><@spring.message "olympiad.stage.col.participantList">Список участников</@spring.message></th>
                                <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                    <th><@spring.message "olympiad.stage.col.participants">Участники</@spring.message></th>
                                </#if>
                                <th><@spring.message "olympiad.stage.col.registration">Регистрация</@spring.message></th>
                                <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                    <th><@spring.message "olympiad.stage.col.data">Данные</@spring.message></th>
                                </#if>
                            </tr>
                            </thead>
                            <tbody>
                            <#list olympiad.stages as stage>
                                <tr>
                                    <td>${stage.stageOrder}</td>
                                    <td>${stage.registrationStart}</td>
                                    <td>${stage.registrationEnd}</td>
                                    <td>${stage.startDate}</td>
                                    <td>${stage.endDate}</td>
                                    <td>
                                        <#if stage.updatedAt??>
                                            ${stage.updatedAtFormatted}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if stage.result?? && (stage.result?size > 0)>
                                            <a href="скачать результаты" onclick="window.location.href='/api/files/${stage.result[0].file.id}/download'; return false;" class="mybtn">
                                                <@spring.message "olympiad.stage.results.link">Результаты</@spring.message>
                                            </a>
                                        <#else>
                                            <span class="mybtn-off"><@spring.message "olympiad.stage.results.none">Отсутствует</@spring.message></span>
                                        </#if>
                                    </td>
                                    <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                        <td>
                                            <form action="/olympiad/add-result" method="post" enctype="multipart/form-data" class="uploadForm">
                                                <#if _csrf??>
                                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                                           value="${(_csrf.token)!'csrf-token'}"/>
                                                </#if>
                                                <input type="file" name="file" class="uploadFile" style="display:none" required />
                                                <input type="number" name="stageId" value="${stage.id}" hidden="hidden" />
                                                <#if stage.result?? && (stage.result?size > 0)>
                                                    <button type="button" class="uploadBtn mybtn-admin">
                                                        <@spring.message "olympiad.stage.results.changeBtn">Поменять</@spring.message>
                                                    </button>
                                                <#else>
                                                    <button type="button" class="uploadBtn mybtn-admin">
                                                        <@spring.message "olympiad.stage.results.addBtn">Добавить</@spring.message>
                                                    </button>
                                                </#if>
                                            </form>
                                        </td>
                                    </#if>
                                    <td>
                                        <#if stage.approvedList?? && (stage.approvedList?size > 0)>
                                            <a href="скачать список участников" onclick="window.location.href='/api/files/${stage.approvedList[0].file.id}/download'; return false;" class="mybtn">
                                                <@spring.message "olympiad.stage.participants.link">Список</@spring.message>
                                            </a>
                                        <#else>
                                            <span class="mybtn-off"><@spring.message "olympiad.stage.participants.none">Отсутствует</@spring.message></span>
                                        </#if>
                                    </td>
                                    <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                        <td>
                                            <form action="/olympiad/add-list" method="post" enctype="multipart/form-data" class="uploadForm">
                                                <#if _csrf??>
                                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                                           value="${(_csrf.token)!'csrf-token'}"/>
                                                </#if>
                                                <input type="file" name="file" class="uploadFile" style="display:none" required />
                                                <input type="number" name="stageId" value="${stage.id}" hidden="hidden" />
                                                <#if stage.approvedList?? && (stage.approvedList?size > 0)>
                                                    <button type="button" class="uploadBtn mybtn-admin">
                                                        <@spring.message "olympiad.stage.participants.changeBtn">Поменять</@spring.message>
                                                    </button>
                                                <#else>
                                                    <button type="button" class="uploadBtn mybtn-admin">
                                                        <@spring.message "olympiad.stage.participants.addBtn">Добавить</@spring.message>
                                                    </button>
                                                </#if>
                                            </form>
                                        </td>
                                    </#if>
                                    <#if (stage.registrationDate?long <= today?long) && (stage.registrationEndDate?long >= today?long)>
                                        <td>
                                            <a href="/olympiad/registration?stageId=${stage.id}" class="mybtn">
                                                <@spring.message "olympiad.stage.registration.registerBtn">Зарегистрироваться</@spring.message>
                                            </a>
                                        </td>
                                    <#else>
                                        <td>
                                            <span class="mybtn-off"><@spring.message "olympiad.stage.registration.unavailable">Недоступно</@spring.message></span>
                                        </td>
                                    </#if>
                                    <#if (user??) && ((isModer!false) || (isAdmin!false))>
                                        <td>
                                            <a href="/olympiad/stage/${stage.id}/register-list" style="color: inherit; text-decoration: none;">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                                                     viewBox="0 0 16 16">
                                                    <path fill-rule="evenodd"
                                                          d="M9.146 4.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L11.293 8H2.5a.5.5 0 0 1 0-1h8.793L9.146 4.854a.5.5 0 0 1 0-.708z"/>
                                                </svg>
                                            </a>
                                        </td>
                                    </#if>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                <#else>
                    <p><@spring.message "olympiad.stage.none">Этапы ещё не добавлены.</@spring.message></p>
                </#if>
            </div>

            <div class="tab-pane fade" id="rules" role="tabpanel" aria-labelledby="rules-tab">
                <h4 class="h4head"><@spring.message "olympiad.heading.rules">Регламент / правила участия</@spring.message></h4>
                <p>${olympiad.rules?no_esc}</p>
            </div>

            <div class="tab-pane fade" id="contacts" role="tabpanel" aria-labelledby="contacts-tab">
                <h4 class="h4head"><@spring.message "olympiad.heading.contacts">Контакты</@spring.message></h4>
                <#if olympiad.contacts?? && (olympiad.contacts?size > 0)>
                    <#list olympiad.contacts as contact>
                        <div class="contact-card">
                            <#if contact.imageUrl??>
                                <img src="${contact.imageUrl}" alt="${contact.contactType} avatar" class="contact-image">
                            <#else>
                                <div class="contact-placeholder">${contact.contactType[0]}</div>
                            </#if>
                            <div class="contact-info">
                                <p>
                                    <span class="contact-type">${contact.contactType}:</span>
                                    <span class="contact-details">
                                    <#if contact.contactType == "Telegram">
                                        <a href="https://${contact.info}" target="_blank" class="social-link">${contact.info}</a>
                                    <#elseif contact.contactType == "WhatsApp">
                                        <a href="https://${contact.info}" target="_blank" class="social-link">${contact.info}</a>
                                    <#elseif contact.contactType == "Instagram">
                                        <a href="https://${contact.info}" target="_blank" class="social-link">${contact.info}</a>
                                    <#elseif contact.contactType == "Facebook">
                                        <a href="https://${contact.info}" target="_blank" class="social-link">${contact.info}</a>
                                    <#elseif contact.contactType == "TikTok">
                                        <a href="https://${contact.info}" target="_blank" class="social-link">${contact.info}</a>
                                    <#elseif contact.contactType == "Email">
                                        <a href="mailto:${contact.info}" target="_blank" class="social-link">${contact.info}</a>
                                    <#elseif contact.contactType == "Phone">
                                        <a href="tel:${contact.info}" target="_blank" class="social-link">${contact.info}</a>
                                    <#else>
                                        ${contact.info}
                                    </#if>
                                </span>
                                </p>
                            </div>
                        </div>
                    </#list>
                <#else>
                    <div class="no-data-placeholder">
                        <svg width="180" height="180" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="img-fluid">
                            <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z" fill="#f8f9fa" stroke="#dee2e6" stroke-width="1"/>
                            <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z" fill="none" stroke="#6c757d" stroke-width="0.5" stroke-dasharray="3,3"/>
                            <circle cx="12" cy="12" r="10" fill="none" stroke="#6c757d" stroke-width="0.5"/>
                            <path d="M1 1l22 22" stroke="#dc3545" stroke-width="1.5" stroke-linecap="round"/>
                        </svg>
                        <p class="text-muted mt-3"><@spring.message "olympiad.contacts.none">Контактная информация не предоставлена</@spring.message></p>
                    </div>
                </#if>
            </div>

            <div class="tab-pane fade" id="organization" role="tabpanel" aria-labelledby="organization-tab">
                <h4 class="h4head"><@spring.message "olympiad.heading.organizations">Организации</@spring.message></h4>
                <#if olympiad.organizations?? && (olympiad.organizations?size > 0)>
                    <div class="organization-grid">
                        <#list olympiad.organizations as organization>
                            <div class="organization-card">
                                <#if organization.avatar??>
                                    <img src="/api/files/${organization.avatar.id}/view" alt="${organization.name} logo" class="organization-image">
                                <#else>
                                    <div class="organization-placeholder">${organization.name[0]}</div>
                                </#if>
                                <p class="organization-name">${organization.name}</p>
                            </div>
                        </#list>
                    </div>
                <#else>
                    <div class="no-data-placeholder">
                        <svg width="180" height="180" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="img-fluid">
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" fill="#f8f9fa" stroke="#dee2e6" stroke-width="1"/>
                            <circle cx="9" cy="7" r="4" fill="#f8f9fa" stroke="#dee2e6" stroke-width="1"/>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87" fill="none" stroke="#6c757d" stroke-width="0.5" stroke-dasharray="3,3"/>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75" fill="none" stroke="#6c757d" stroke-width="0.5" stroke-dasharray="3,3"/>
                            <circle cx="12" cy="12" r="10" fill="none" stroke="#6c757d" stroke-width="0.5"/>
                            <path d="M1 1l22 22" stroke="#dc3545" stroke-width="1.5" stroke-linecap="round"/>
                        </svg>
                        <p class="text-muted mt-3"><@spring.message "olympiad.organizations.none">Информация об организациях отсутствует</@spring.message></p>
                    </div>
                </#if>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.querySelectorAll('.uploadBtn').forEach(btn => {
            btn.addEventListener('click', () => {
                const form = btn.closest('.uploadForm');
                const inputFile = form.querySelector('.uploadFile');
                inputFile.click();

                inputFile.addEventListener('change', () => {
                    if (inputFile.files.length > 0) {
                        form.submit();
                    }
                }, { once: true });
            });
        });
    </script>
</@main.layout>