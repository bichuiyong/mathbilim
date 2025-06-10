<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <link rel="stylesheet" href="/static/css/login.css">

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

        <#if error?? && error>
            <div class="alert alert-danger alert-custom" role="alert">
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
                    <input type="email" class="form-control" id="email" name="email" placeholder="your@email.com"
                    >
                    <i class="bi bi-envelope input-icon"></i>
                </div>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <div class="input-wrapper">
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="Enter your password">
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
        </div>
    </div>
    <#if error?? && error>
        <script>
            window.onload = function () {
                const alert = document.getElementById('errorAlert');
                alert.classList.add('visible');
                setTimeout(function () {
                    alert.classList.remove('visible');
                }, 5000);
            };
        </script>
    </#if>
</@main.layout>