<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout "Восстановление пароля">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="bg-white rounded shadow-lg overflow-hidden">
                    <div class="bg-primary text-white p-4 text-center"
                         style="background: linear-gradient(135deg, #2563eb 0%, #3b82f6 100%);">
                        <i class="fas fa-key mb-3" style="font-size: 3rem;"></i>
                        <h3 class="mb-0"><@spring.message "auth.recovery"/></h3>
                    </div>

                    <div class="p-4 p-md-5">
                        <#if error??>
                            <div class="alert alert-danger d-flex align-items-center" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <div>${error}</div>
                            </div>
                        </#if>

                        <#if message??>
                            <div class="alert alert-success" role="alert">
                                <div class="d-flex align-items-start">
                                    <i class="fas fa-check-circle me-2 mt-1"></i>
                                    <div>
                                        <strong><@spring.message "auth.send-mail"/></strong><br>
                                        ${message}
                                    </div>
                                </div>
                            </div>
                        </#if>

                        <p class="text-muted mb-4">
                            <@spring.message "auth.message-1"/>
                        </p>

                        <form action="/auth/forgot-password" method="post">
                            <@main.csrf/>

                            <div class="mb-4">
                                <label for="email" class="form-label small text-muted">Email <@spring.message "address"/></label>
                                <div class="input-group input-group-lg">
                                    <span class="input-group-text bg-light border-end-0">
                                        <i class="fas fa-envelope text-muted"></i>
                                    </span>
                                    <input type="email"
                                           class="form-control border-start-0"
                                           id="email"
                                           name="email"
                                           placeholder="example@email.com"
                                           required
                                           autofocus>
                                </div>
                                <small class="text-muted"><@spring.message "auth.message-2"/></small>
                            </div>

                            <div class="d-grid mb-4">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-paper-plane me-2"></i>
                                    <@spring.message "auth.send-instructions"/>
                                </button>
                            </div>
                        </form>

                        <div class="text-center">
                            <p class="mb-2">
                                <a href="/auth/login" class="text-muted text-decoration-none">
                                    <i class="fas fa-arrow-left me-1"></i>
                                    <@spring.message "auth.back-main"/>
                                </a>
                            </p>
                            <p class="mb-0 small text-muted">
                                <@spring.message "auth.remember"/>
                                <a href="/auth/login" class="text-primary fw-bold text-decoration-none"><@spring.message "sign-in"/></a>
                            </p>
                        </div>
                    </div>

                    <div class="bg-light p-3 text-center">
                        <small class="text-muted">
                            <i class="fas fa-info-circle me-1"></i>
                            <@spring.message "auth.24hours"/>
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        .input-group-lg .form-control,
        .input-group-lg .input-group-text {
            font-size: 1rem;
            padding: 0.75rem 1rem;
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>
</@main.layout>