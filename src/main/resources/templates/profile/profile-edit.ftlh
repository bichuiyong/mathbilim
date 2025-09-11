<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout title="Редактирование профиля">

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        authorities = SPRING_SECURITY_CONTEXT.authentication.authorities?map(auth -> auth.authority)
        isAdmin = authorities?seq_contains("ADMIN")
        isModer = authorities?seq_contains("MODER")
        >
    </#if>

    <div class="container mt-4">
        <div class="row">
            <!-- Левая колонка - аватар и быстрые действия -->
            <div class="col-lg-4 mb-4">
                <!-- Карточка редактирования аватара -->
                <div class="card profile-edit-card">
                    <div class="card-header">
                        <h5><i class="fas fa-user-edit me-2"></i><@spring.message "profile.edit.title"/></h5>
                    </div>
                    <div class="card-body">
                        <div class="profile-avatar-section text-center mb-4">
                            <div class="row">
                                <div class="col-12">
                                    <div class="profile-avatar-container-edit mb-3">
                                        <#if userEditDto.avatar??>
                                            <img src="/api/files/${userEditDto.avatar.id}/download"
                                                 alt="<@spring.message 'profile.avatar'/>"
                                                 class="profile-avatar-edit-preview">
                                        <#else>
                                            <div class="profile-avatar-placeholder-edit">
                                                <i class="fas fa-user"></i>
                                            </div>
                                        </#if>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <form method="POST" action="/users/${userEditDto.id}/avatar"
                                          enctype="multipart/form-data" class="avatar-upload-form">
                                        <@main.csrf/>
                                        <label for="avatar-upload" class="btn btn-primary btn-sm w-100 mb-2">
                                            <i class="fas fa-camera me-2"></i><@spring.message "button.changePhoto"/>
                                        </label>
                                        <input id="avatar-upload" name="avatar" type="file"
                                               accept=".jpg, .jpeg, .png" class="d-none"
                                               onchange="this.form.submit()">
                                        <div class="text-muted">
                                            <small><@spring.message "profile.avatar.formatInfo"/></small>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Текущий статус -->
                        <div class="text-center mb-3">
                            <h6 class="mb-2">${userEditDto.name!} ${userEditDto.surname!}</h6>
                            <div class="d-flex justify-content-center gap-2 mb-3">
                                <#if isAdmin>
                                    <span class="badge bg-danger"><@spring.message "role.admin"/></span>
                                <#elseif isModer>
                                    <span class="badge bg-warning"><@spring.message "role.moderator"/></span>
                                <#else>
                                    <span class="badge bg-info"><@spring.message "role.user"/></span>
                                </#if>
                            </div>
                        </div>

                        <hr>

                        <a href="/users/${userEditDto.id}" class="btn btn-outline-secondary btn-sm w-100">
                            <i class="fas fa-arrow-left me-2"></i><@spring.message "button.backToProfile"/>
                        </a>
                    </div>
                </div>

                <!-- Быстрые действия -->
                <div class="card mt-3">
                    <div class="card-body">
                        <h6 class="card-title mb-3"><@spring.message "profile.additionalSettings"/></h6>
                        <div class="d-grid gap-2">
                            <a href="/users/change-password" class="btn btn-outline-warning btn-sm">
                                <i class="fas fa-key me-2"></i><@spring.message "button.changePassword"/>
                            </a>
                            <a href="https://t.me/Math_Bilim_bot?start=${userEditDto.id}" class="btn btn-outline-info btn-sm">
                                <i class="fas fa-bell me-2"></i><@spring.message "button.notifications"/>
                            </a>
                            <#if isAdmin>
                                <a href="/admin/users" class="btn btn-outline-danger btn-sm">
                                    <i class="fas fa-users-cog me-2"></i><@spring.message "button.manageUsers"/>
                                </a>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Правая колонка -->
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <ul class="nav nav-tabs card-header-tabs" id="editTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="basic-tab" data-bs-toggle="tab"
                                        data-bs-target="#basic" type="button" role="tab">
                                    <i class="fas fa-user me-2"></i><@spring.message "tab.basicInfo"/>
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="settings-tab" data-bs-toggle="tab"
                                        data-bs-target="#settings" type="button" role="tab">
                                    <i class="fas fa-cog me-2"></i><@spring.message "tab.accountSettings"/>
                                </button>
                            </li>
                        </ul>
                    </div>

                    <form method="POST" action="/users/${userEditDto.id}/edit">
                        <@main.csrf/>
                        <#if isAdmin || isModer>
                            <input type="hidden" name="id" value="${userEditDto.id}">
                        </#if>

                        <div class="card-body tab-content" id="editTabsContent">
                            <!-- Вкладка "Основная информация" -->
                            <div class="tab-pane fade show active" id="basic" role="tabpanel">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="name" class="form-label">
                                            <i class="fas fa-user me-1 text-primary"></i><@spring.message "label.name"/> *
                                        </label>
                                        <@spring.formInput "userEditDto.name",
                                        'class="form-control" id="name" placeholder="' + springMacroRequestContext.getMessage("placeholder.name") + '"',
                                        "text"/>
                                        <@spring.showErrors '<br>', 'text-danger small mt-1'/>
                                    </div>

                                    <div class="col-md-6 mb-3">
                                        <label for="surname" class="form-label">
                                            <i class="fas fa-user me-1 text-primary"></i><@spring.message "label.surname"/>
                                        </label>
                                        <@spring.formInput "userEditDto.surname",
                                        'class="form-control" id="surname" placeholder="' + springMacroRequestContext.getMessage("placeholder.surname") + '"',
                                        "text"/>
                                        <@spring.showErrors '<br>', 'text-danger small mt-1'/>
                                    </div>
                                </div>

                                <#if !isAdmin && !isModer>
                                    <div class="mb-3">
                                        <label for="typeId" class="form-label">
                                            <i class="fas fa-tag me-1 text-primary"></i><@spring.message "label.accountType"/>
                                        </label>
                                        <#if types?? && types?size gt 0>
                                            <#assign typeMap = {}>
                                            <#list types as type>
                                                <#assign typeMap = typeMap + {type.translations[0].typeId: type.translations[0].translation}>
                                            </#list>
                                            <@spring.formSingleSelect "userEditDto.typeId", typeMap, 'class="form-select" id="typeId"'/>
                                        <#else>
                                            <select class="form-select" id="typeId" disabled>
                                                <option><@spring.message "select.typesNotLoaded"/></option>
                                            </select>
                                        </#if>
                                        <@spring.showErrors '<br>', 'text-danger small mt-1'/>
                                        <div class="form-text"><@spring.message "help.accountType"/></div>
                                    </div>
                                </#if>
                            </div>

                            <!-- Вкладка "Настройки аккаунта" -->
                            <div class="tab-pane fade" id="settings" role="tabpanel">
<#--                                <#if isAdmin>-->
<#--                                    <div class="alert alert-warning d-flex align-items-center mb-4" role="alert">-->
<#--                                        <i class="fas fa-exclamation-triangle me-3"></i>-->
<#--                                        <div>-->
<#--                                            <strong><@spring.message "admin.settingsTitle"/></strong><br>-->
<#--                                            <@spring.message "admin.settingsDescription"/>-->
<#--                                        </div>-->
<#--                                    </div>-->

<#--                                    <div class="mb-4">-->
<#--                                        <label for="roleId" class="form-label">-->
<#--                                            <i class="fas fa-shield-alt me-1 text-danger"></i><@spring.message "label.userRole"/>-->
<#--                                        </label>-->
<#--                                        <#if roles?? && roles?size gt 0>-->
<#--                                            <#assign roleMap = {}>-->
<#--                                            <#list roles as role>-->
<#--                                                <#assign roleMap = roleMap + {role.id?string: role.name}>-->
<#--                                            </#list>-->
<#--                                            <@spring.formSingleSelect "userEditDto.role.id", roleMap, 'class="form-select admin-select" id="roleId"'/>-->
<#--                                        <#else>-->
<#--                                            <select class="form-select admin-select" id="roleId" disabled>-->
<#--                                                <option><@spring.message "select.rolesNotLoaded"/></option>-->
<#--                                            </select>-->
<#--                                        </#if>-->
<#--                                        <@spring.showErrors '<br>', 'text-danger small mt-1'/>-->
<#--                                        <div class="form-text"><@spring.message "help.rolesDescription"/></div>-->
<#--                                    </div>-->
<#--                                <#else>-->
                                    <div class="text-center py-5">
                                        <i class="fas fa-cog fa-3x text-muted mb-3"></i>
                                        <h5 class="text-muted"><@spring.message "settings.noneTitle"/></h5>
                                        <p class="text-muted"><@spring.message "settings.noneDescription"/></p>
                                    </div>
<#--                                </#if>-->
                            </div>
                        </div>

                        <!-- Действия формы -->
                        <div class="card-footer">
                            <div class="d-flex justify-content-between align-items-center gap-2">
                                <div class="text-muted">
                                    <small><i class="fas fa-info-circle me-1"></i><@spring.message "info.saveChanges"/></small>
                                </div>
                                <div class="d-flex gap-2">
                                    <a href="/users/${userEditDto.id}" class="btn btn-secondary border border-black">
                                        <@spring.message "button.cancel"/>
                                    </a>
                                    <button type="submit" class="btn btn-primary">
                                        <@spring.message "button.save"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <!-- Информационные карточки -->
                <div class="row mt-4">
                    <div class="col-md-6 mb-3">
                        <div class="card border-0 bg-light">
                            <div class="card-body text-center py-4">
                                <i class="fas fa-key fa-2x text-success mb-2"></i>
                                <h6 class="mb-1"><@spring.message "info.securityTitle"/></h6>
                                <p class="text-muted small mb-0"><@spring.message "info.securityDescription"/></p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <div class="card border-0 bg-light">
                            <div class="card-body text-center py-4">
                                <i class="fas fa-clock fa-2x text-info mb-2"></i>
                                <h6 class="mb-1"><@spring.message "info.moderationTitle"/></h6>
                                <p class="text-muted small mb-0"><@spring.message "info.moderationDescription"/></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@main.layout>
