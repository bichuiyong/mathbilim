<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout "Сообщение">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6">
                <div class="text-center">
                    <#if message??>
                        <#if message?contains("successfully")>

                            <div class="mb-4">
                                <div class="position-relative d-inline-block">
                                    <div class="rounded-circle bg-success bg-opacity-10 d-flex align-items-center justify-content-center"
                                         style="width: 120px; height: 120px; animation: scaleIn 0.5s ease-out;">
                                        <i class="fas fa-check text-success" style="font-size: 3.5rem;"></i>
                                    </div>
                                    <div class="position-absolute" style="top: -10px; right: -10px;">
                                        <span class="badge bg-success rounded-pill px-3 py-2">
                                            <i class="fas fa-sparkles me-1"></i>
                                            <@spring.message "ms.ready!"/>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <h2 class="mb-3 fw-bold"><@spring.message "ms.nice!"/></h2>
                            <p class="text-muted fs-5 mb-4">${message}</p>

                            <div class="bg-light rounded-3 p-4 mb-4">
                                <i class="fas fa-shield-alt text-success mb-2" style="font-size: 2rem;"></i>
                                <h5 class="mb-2"><@spring.message "ms.secureAcc"/></h5>
                                <p class="text-muted mb-0"><@spring.message "ms.success"/></p>
                            </div>

                            <a href="/auth/login" class="btn btn-primary btn-lg px-5">
                                <i class="fas fa-sign-in-alt me-2"></i>
                                <@spring.message "ms.login"/>
                            </a>
                        <#else>

                            <div class="mb-4">
                                <div class="rounded-circle bg-danger bg-opacity-10 d-flex align-items-center justify-content-center mx-auto"
                                     style="width: 120px; height: 120px; animation: shake 0.5s;">
                                    <i class="fas fa-times text-danger" style="font-size: 3.5rem;"></i>
                                </div>
                            </div>

                            <h2 class="mb-3 fw-bold"><@spring.message "ms.error"/></h2>
                            <p class="text-muted fs-5 mb-4">${message}</p>

                            <div class="alert alert-danger d-inline-flex align-items-center mb-4">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <span><@spring.message "ms.error2"/></span>
                            </div>

                            <div class="d-flex flex-column flex-sm-row gap-3 justify-content-center">
                                <a href="/auth/forgot-password" class="btn btn-primary">
                                    <i class="fas fa-redo me-2"></i>
                                    <@spring.message "ms.again"/>
                                </a>
                                <a href="/auth/login" class="btn btn-outline-primary">
                                    <i class="fas fa-home me-2"></i>
                                    <@spring.message "ms.back-main"/>
                                </a>
                            </div>
                        </#if>
                    </#if>
                </div>

                <div class="text-center mt-5">
                    <div class="d-inline-flex align-items-center bg-light rounded-pill px-4 py-2">
                        <i class="fas fa-question-circle text-primary me-2"></i>
                        <span class="text-muted"><@spring.message "ms.help"/> <a href="/help" class="text-primary"><@spring.message "ms.support"/></a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        @keyframes scaleIn {
            0% {
                transform: scale(0);
                opacity: 0;
            }
            50% {
                transform: scale(1.1);
            }
            100% {
                transform: scale(1);
                opacity: 1;
            }
        }

        @keyframes shake {
            0%, 100% {
                transform: translateX(0);
            }
            10%, 30%, 50%, 70%, 90% {
                transform: translateX(-5px);
            }
            20%, 40%, 60%, 80% {
                transform: translateX(5px);
            }
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>
</@main.layout>