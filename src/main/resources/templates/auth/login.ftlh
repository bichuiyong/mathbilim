<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        .alert-custom {
            position: fixed;
            top: 10px;
            right: 10px;
            z-index: 1050;
            width: 300px;
            border-radius: 10px;
            padding: 15px;
            font-size: 16px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            opacity: 0;
            transition: opacity 0.5s ease-in-out;
        }
        .alert-custom.visible {
            opacity: 1;
        }
    </style>
    <div class="auth-container">
        <div class="auth-header">
            <i class="bi bi-egg-fried"></i>
            <h2>Welcome Back!</h2>
        </div>

        <#if error?? && error>
            <div id="errorAlert" class="alert alert-danger alert-custom" role="alert">
                <strong>Неверная почта или пароль повторите попытку</strong>
            </div>
        </#if>

        <form action="/auth/login" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>
            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="bi bi-envelope"></i>
                    </span>
                    <input type="email" class="form-control" id="email" name="username" placeholder="your@email.com" required>
                </div>
            </div>

            <div class="mb-4">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="bi bi-lock"></i>
                    </span>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
                </div>
            </div>

            <button class="w-100 btn btn-primary btn-lg" type="submit">
                <i class="bi bi-box-arrow-in-right me-2"></i> Sign In
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