<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Админ-панель: Управление пользователями</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            display: flex;
            min-height: 100vh;
        }

        #sidebar {
            min-width: 250px;
            max-width: 250px;
            background: #343a40;
            color: #fff;
            transition: all 0.3s;
            padding-top: 20px;
        }

        #sidebar .sidebar-header {
            padding: 20px;
            background: #212529;
            text-align: center;
        }

        #sidebar ul.components {
            padding: 20px 0;
            border-bottom: 1px solid #47748b;
        }

        #sidebar ul li a {
            padding: 10px;
            font-size: 1.1em;
            display: block;
            color: #ccc;
            text-decoration: none;
        }

        #sidebar ul li a:hover {
            color: #fff;
            background: #47748b;
        }

        #content {
            width: 100%;
            padding: 20px;
        }

        .user-status-active {
            color: green;
            font-weight: bold;
        }

        .user-status-blocked {
            color: red;
            font-weight: bold;
        }
    </style>
</head>

<body>
<nav id="sidebar">
    <div class="sidebar-header">
        <h3>Админ-панель</h3>
    </div>
    <ul class="list-unstyled components">
        <li class="active">
            <a href="#">Управление пользователями</a>
        </li>
        <li>
            <a href="#">Статистика</a>
        </li>
        <li>
            <a href="#">Настройки</a>
        </li>
    </ul>
</nav>

<div id="content">
    <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Dashboard</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Администратор</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Выйти</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <h1 class="mb-4">Управление пользователями</h1>

    <div class="row mb-3 align-items-center">
        <div class="col-md-4">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createUserModal">
                Создать нового пользователя
            </button>
        </div>
        <div class="col-md-4">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Поиск по имени или email..."
                       aria-label="Поиск пользователей" id="searchInput">
                <button class="btn btn-outline-secondary" type="button" id="searchButton">Поиск</button>
            </div>
        </div>
        <div class="col-md-4">
            <select class="form-select" aria-label="Фильтр по роли">
                <option selected>Фильтр по роли</option>
                <option value="all">Все</option>
                <option value="admin">Администратор</option>
                <option value="moderator">Модератор</option>
                <option value="user">Пользователь</option>
            </select>
        </div>
    </div>

    <div class="table-responsive">
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Имя пользователя</th>
                <th>Email</th>
                <th>Роль</th>
                <th>Статус</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody id="resultTable">
            <tr>
                <td>1</td>
                <td>user_admin</td>
                <td>admin@example.com</td>
                <td><span class="badge bg-danger">Администратор</span></td>
                <td><span class="user-status-active">Активен</span></td>
                <td>
                    <button class="btn btn-sm btn-info me-1" data-bs-toggle="modal"
                            data-bs-target="#editUserModal">Изменить</button>
                    <button class="btn btn-sm btn-danger me-1" data-bs-toggle="modal"
                            data-bs-target="#deleteUserModal">Удалить</button>
                    <button class="btn btn-sm btn-warning">Заблокировать</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <nav aria-label="Навигация по страницам пользователей">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Предыдущая</a>
            </li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Следующая</a>
            </li>
        </ul>
    </nav>

</div>

<div class="modal fade" id="createUserModal" tabindex="-1" aria-labelledby="createUserModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createUserModalLabel">Создать нового пользователя</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="createNewUser">
                    <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName!}" value="${_csrf.token!}"/>
                    </#if>
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First name</label>
                        <input type="text" class="form-control" id="firstName" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last name</label>
                        <input type="text" class="form-control" id="lastName" name="surname" required>
                    </div>
                    <div class="mb-3">
                        <label for="newEmail" class="form-label">Email</label>
                        <input type="email" class="form-control" id="newEmail" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="newPassword" class="form-label">Пароль</label>
                        <input type="password" class="form-control" id="newPassword" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="newUserRole" class="form-label">Роль</label>
                        <select class="form-select" id="newUserRole" name="role">
                            <option value="user" selected>Пользователь</option>
                            <option value="moderator">Модератор</option>
                            <option value="admin">Администратор</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="type" class="form-label">Тип аккаунта</label>
                        <div class="account-type-select">
                            <@spring.formSingleSelect "userDto.type" types 'class="account-type-dropdown" id="type"'/>
                            <i class="bi bi-chevron-down dropdown-icon"></i>
                        </div>
                        <@spring.showErrors "<br>" "error text-danger"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                <button type="button" class="btn btn-primary" id="createUserBtn">Создать</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editUserModalLabel">Изменить пользователя</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label for="editUserId" class="form-label">ID пользователя</label>
                        <input type="text" class="form-control" id="editUserId" value="1" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="editUsername" class="form-label">Имя пользователя</label>
                        <input type="text" class="form-control" id="editUsername" value="user_admin">
                    </div>
                    <div class="mb-3">
                        <label for="editEmail" class="form-label">Email</label>
                        <input type="email" class="form-control" id="editEmail" value="admin@example.com">
                    </div>
                    <div class="mb-3">
                        <label for="editUserRole" class="form-label">Роль</label>
                        <select class="form-select" id="editUserRole">
                            <option value="user">Пользователь</option>
                            <option value="moderator">Модератор</option>
                            <option value="admin" selected>Администратор</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="editUserStatus" class="form-label">Статус</label>
                        <select class="form-select" id="editUserStatus">
                            <option value="active" selected>Активен</option>
                            <option value="blocked">Заблокирован</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                <button type="button" class="btn btn-primary" >Сохранить изменения</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deleteUserModal" tabindex="-1" aria-labelledby="deleteUserModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteUserModalLabel">Подтвердить удаление</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Вы уверены, что хотите удалить пользователя "user_admin" (ID: 1)? Это действие необратимо.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                <button type="button" class="btn btn-danger">Удалить</button>
            </div>
        </div>
    </div>
</div>
<script src="/static/js/admin.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
</script>
</body>

</html>