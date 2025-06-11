<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <link rel="stylesheet" href="../static/scss/main.css">

    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-12">
                <div class="login-card">
                    <div class="login-header">
                        <div class="icon">
                            <i class="bi bi-box-arrow-in-right"></i>
                        </div>
                        <h2>Welcome Back!</h2>
                        <p>Sign in to your account</p>
                    </div>

                    <#if success??>
                        <div class="alert alert-success alert-custom" role="alert">
                            <i class="bi bi-check-circle-fill me-2"></i>
                            ${success}
                        </div>
                    </#if>

                    <#if emailNotVerified?? && emailNotVerified>
                        <div class="alert alert-warning alert-custom" role="alert">
                            <div class="d-flex align-items-start">
                                <i class="bi bi-exclamation-triangle-fill me-2 mt-1"></i>
                                <div class="flex-grow-1">
                                    <strong>Email не подтвержден!</strong>
                                    <p class="mb-2">Проверьте почту и перейдите по ссылке подтверждения.</p>
                                    <div class="mt-2">
                                        <a href="/auth/resend-verification">Подтверждение почты</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#if>

                    <#if error?? && error && !(emailNotVerified?? && emailNotVerified)>
                        <div class="alert alert-danger alert-custom" role="alert">
                            <i class="bi bi-exclamation-circle-fill me-2"></i>
                            <strong>Invalid email or password. Please try again.</strong>
                        </div>
                    </#if>

                    <form action="/auth/login" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <div class="input-wrapper">
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="your@email.com" value="${userEmail!}" required>
                                <i class="bi bi-envelope input-icon"></i>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <div class="input-wrapper">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="Enter your password" required>
                                <i class="bi bi-lock input-icon"></i>
                            </div>
                            <div class="forgot-password">
                                <a href="/auth/forgot-password">Forgot Password?</a>
                            </div>

                        </div>

                        <button class="login-btn" type="submit">
                            <i class="bi bi-box-arrow-in-right me-2"></i>
                            Sign In
                        </button>
                    </form>

                    <div class="auth-footer">
                        <p>New user? <a href="/auth/register">Create an account</a></p>
                        <p>Confirm Email? <a href="/auth/resend-verification">Click</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const alerts = document.querySelectorAll('.alert-custom');
            alerts.forEach(function(alert) {
                setTimeout(function() {
                    alert.style.transition = 'opacity 0.5s ease';
                    alert.style.opacity = '0';
                    setTimeout(function() {
                        alert.style.display = 'none';
                    }, 500);
                }, 7000);
            });
        });
    </script>
</@main.layout>