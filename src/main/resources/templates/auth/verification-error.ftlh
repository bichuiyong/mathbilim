<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout "Ошибка верификации">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6">
                <div class="text-center">
                    <div class="mb-4 position-relative">
                        <div class="rounded-circle bg-danger bg-opacity-10 d-flex align-items-center justify-content-center mx-auto"
                             style="width: 120px; height: 120px;">
                            <i class="fas fa-link-slash text-danger" style="font-size: 3rem;"></i>
                        </div>
                        <div class="position-absolute" style="bottom: 0; right: 50%; transform: translateX(50%);">
                            <div class="bg-white rounded-circle shadow p-2">
                                <i class="fas fa-exclamation text-warning"></i>
                            </div>
                        </div>
                    </div>

                    <h2 class="mb-3 fw-bold"><@spring.message "verif.error"/></h2>

                    <#if error??>
                        <div class="alert alert-danger mb-4">
                            <i class="fas fa-exclamation-triangle me-2"></i>
                            ${error}
                        </div>
                    <#else>
                        <p class="text-muted fs-5 mb-4">
                            <@spring.message "verif.notApprove"/>
                        </p>
                    </#if>

                    <div class="bg-light rounded-3 p-4 mb-4 text-start">
                        <h5 class="mb-3">
                            <i class="fas fa-question-circle text-primary me-2"></i>
                            <@spring.message "verif.reasons"/>
                        </h5>
                        <div class="d-flex align-items-start mb-2">
                            <i class="fas fa-clock text-muted me-3 mt-1"></i>
                            <div>
                                <strong><@spring.message "verif.outdate"/></strong><br>
                                <small class="text-muted"><@spring.message "verif.link"/></small>
                            </div>
                        </div>
                        <div class="d-flex align-items-start mb-2">
                            <i class="fas fa-sync text-muted me-3 mt-1"></i>
                            <div>
                                <strong><@spring.message "verif.linkUsed"/></strong><br>
                                <small class="text-muted"><@spring.message "verif.once"/></small>
                            </div>
                        </div>
                        <div class="d-flex align-items-start">
                            <i class="fas fa-link text-muted me-3 mt-1"></i>
                            <div>
                                <strong><@spring.message "verif.broken"/></strong><br>
                                <small class="text-muted"><@spring.message "verif.copy"/></small>
                            </div>
                        </div>
                    </div>

                    <div class="alert alert-info mb-4">
                        <i class="fas fa-lightbulb me-2"></i>



                        <strong><@spring.message "verif.whatToDo"/></strong> <@spring.message "verif.requestMail"/>
                    </div>

                    <div class="d-flex flex-column flex-sm-row gap-3 justify-content-center mb-4">
                        <a href="/auth/resend-verification" class="btn btn-primary btn-lg">
                            <i class="fas fa-envelope me-2"></i>
                            <@spring.message "verif.newMail"/>
                        </a>
                        <a href="/auth/login" class="btn btn-outline-primary btn-lg">
                            <i class="fas fa-sign-in-alt me-2"></i>
                            <@spring.message "verif.back"/>
                        </a>
                    </div>

                    <p class="text-muted">
                        <@spring.message "verif.someProblem"/>
                        <a href="/help" class="text-primary fw-bold"><@spring.message "verif.support"/></a>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <style>
        .min-vh-50 {
            min-height: 50vh;
        }

        .alert {
            border: none;
        }

        .alert-info {
            background-color: rgba(37, 99, 235, 0.1);
            color: #1e40af;
        }
    </style>
</@main.layout>