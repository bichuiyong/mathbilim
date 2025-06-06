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

<div class="auth-container my-5">
    <div class="auth-header">
        <i class="bi bi-person-plus"></i>
        <h2>Create Your Account</h2>
    </div>

    <#if error?? && errorMessage??>
    <div id="errorAlert" class="alert alert-danger alert-custom" role="alert">
        <strong>${errorMessage}</strong>
    </div>
</#if>

<form action="/auth/register" method="post">
    <#if _csrf??>
    <input type="hidden" name="${_csrf.parameterName!}" value="${_csrf.token!}"/>
</#if>

<div class="mb-3">
    <label for="name" class="form-label">First Name</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-person"></i></span>
        <input type="text" class="form-control" id="name" name="name" value="${userDto.name!''}" >
    </div>
    <#if errors?? && errors.hasFieldErrors("name")>
    <div class="text-danger mt-1">${errors.getFieldError("name").defaultMessage}</div>
</#if>
</div>

<div class="mb-3">
    <label for="surname" class="form-label">Last Name</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-person"></i></span>
        <input type="text" class="form-control" id="surname" name="surname" value="${userDto.surname!''}">
    </div>
    <#if errors?? && errors.hasFieldErrors("surname")>
    <div class="text-danger mt-1">${errors.getFieldError("surname").defaultMessage}</div>
</#if>
</div>

<div class="mb-3">
    <label for="email" class="form-label">Email</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
        <input type="email" class="form-control" id="email" name="email" value="${userDto.email!''}" >
    </div>
    <#if errors?? && errors.hasFieldErrors("email")>
    <div class="text-danger mt-1">${errors.getFieldError("email").defaultMessage}</div>
</#if>
</div>

<div class="mb-3">
    <label for="password" class="form-label">Password</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-lock"></i></span>
        <input type="password" class="form-control" id="password" name="password" >
    </div>
    <#if errors?? && errors.hasFieldErrors("password")>
    <div class="text-danger mt-1">${errors.getFieldError("password").defaultMessage}</div>
</#if>
</div>

<div class="mb-4">
    <label for="type" class="form-label">Account Type</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-person-badge"></i></span>
        <select class="form-select" id="type" name="type.id" >
            <option value="">-- Select account type --</option>
            <#list types as type>
                <option value="${type.id}" <#if userDto.typeId?? && userDto.typeId == type.id>selected</#if>>
                    ${type.name}
                </option>
            </#list>
        </select>
    </div>
    <#if errors?? && errors.hasFieldErrors("type.id")>
        <div class="text-danger mt-1">${errors.getFieldError("type.id").defaultMessage}</div>
    </#if>
</div>


<button class="w-100 btn btn-success btn-lg" type="submit">
    <i class="bi bi-person-check me-2"></i> Register
</button>
</form>

<div class="auth-footer">
    <p>Already have an account? <a href="/auth/login">Sign in</a></p>
</div>
</div>

<#if error?? && errorMessage??>
<script>
    window.onload = function () {
        const alert = document.getElementById('errorAlert');
        alert.classList.add('visible');
        setTimeout(() => alert.classList.remove('visible'), 5000);
    };
</script>
</#if>
</@main.layout>
