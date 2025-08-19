<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout "Регистрация успешна">
    <div class="container my-5">
        <div class="row justify-content-center align-items-center min-vh-50">
            <div class="col-md-8 col-lg-6">
                <div class="text-center">

                    <div class="position-relative d-inline-block mb-4">
                        <div class="rounded-circle bg-light-blue d-flex align-items-center justify-content-center"
                             style="width: 120px; height: 120px; animation: pulse 2s infinite;">
                            <i class="fas fa-envelope-open-text text-primary" style="font-size: 3.5rem;"></i>
                        </div>
                        <div class="position-absolute bottom-0 end-0">
                            <div class="rounded-circle bg-success text-white d-flex align-items-center justify-content-center"
                                 style="width: 40px; height: 40px; animation: scaleIn 0.5s ease-out;">
                                <i class="fas fa-check"></i>
                            </div>
                        </div>
                    </div>

                    <h2 class="mb-3 fw-bold"><@spring.message "success-regis.ready!"/></h2>

                    <p class="text-muted mb-4 fs-5">
                        <@spring.message "success-regis.send-mail"/>
                    </p>

                    <div class="bg-light rounded-3 p-4 mb-4 text-start">
                        <h5 class="mb-3">
                            <i class="fas fa-tasks text-primary me-2"></i>
                            <@spring.message "success-regis.doNext"/>
                        </h5>
                        <ol class="mb-0">
                            <li class="mb-2"><@spring.message "success-regis.open-mail"/></li>
                            <li class="mb-2"><@spring.message "success-regis.find-mail"/></li>
                            <li class="mb-2"><@spring.message "success-regis.tap-btn"/></li>
                            <li><@spring.message "success-regis.to-login"/></li>
                        </ol>
                    </div>

                    <div class="alert alert-warning d-flex align-items-start mb-4">
                        <i class="fas fa-lightbulb me-3 mt-1"></i>
                        <div class="text-start">
                            <strong><@spring.message "success-regis.dont-see"/></strong><br>
                            <@spring.message "success-regis.check-spam"/>
                        </div>
                    </div>

                    <div class="d-flex flex-column flex-sm-row gap-3 justify-content-center">
                        <a href="/auth/login" class="btn btn-primary">
                            <i class="fas fa-sign-in-alt me-2"></i>
                            <@spring.message "success-regis.back-login"/>
                        </a>
                        <a href="/auth/resend-verification" class="btn btn-outline-primary">
                            <i class="fas fa-redo me-2"></i>
                            <@spring.message "success-regis.again"/>
                        </a>
                    </div>

                    <div class="mt-4">
                        <small class="text-muted">
                            <@spring.message "success-regis.wait"/>
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        @keyframes pulse {
            0% {
                box-shadow: 0 0 0 0 rgba(37, 99, 235, 0.4);
            }
            70% {
                box-shadow: 0 0 0 20px rgba(37, 99, 235, 0);
            }
            100% {
                box-shadow: 0 0 0 0 rgba(37, 99, 235, 0);
            }
        }

        @keyframes scaleIn {
            0% {
                transform: scale(0);
                opacity: 0;
            }
            50% {
                transform: scale(1.2);
            }
            100% {
                transform: scale(1);
                opacity: 1;
            }
        }

        .min-vh-50 {
            min-height: 50vh;
        }
    </style>
</@main.layout>