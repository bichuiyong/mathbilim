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

                    <div class="splitter text-center text-secondary mt-3">
                        <span>or</span>
                    </div>


                    <div class="d-flex justify-content-center">
                        <a class="btn btn-outline-danger" href="/oauth2/authorization/google">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-google" viewBox="0 0 16 16">
                                <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"/>
                            </svg>
                            Continue with Google
                        </a>
                    </div>

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